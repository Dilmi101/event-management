# EKS Deployment — Provisioning Instructions

## Architecture

```
Browser
  │
  ▼
ALB (internet-facing)
  │
  ├── /api/events/*     ──► event-service:8081
  ├── /api/sponsors      ──► event-service:8081
  ├── /api/contact       ──► event-service:8081
  ├── /api/speakers/*    ──► program-service:8082
  ├── /api/sessions/*    ──► program-service:8082
  ├── /api/registrations/* ─► registration-service:8083
  ├── /api/analytics      ──► analytics-service:8085
  └── /*                 ──► frontend:80 (static SPA)

Internal gRPC:
  registration-service ──gRPC──► event-service:9091 (via K8s DNS)

Databases (single RDS PostgreSQL instance):
  eventdb        (owner: event_user)
  programsdb     (owner: programs_user)
  registrationdb (owner: registration_user)
  metabasedb     (owner: metabase_user)   -- Metabase's own app metadata, not analytics data

ClickHouse (self-hosted, in-cluster StatefulSet, single node):
  analytics.web_events   (owner: analytics user)

Metabase (self-hosted, in-cluster Deployment, port-forward only — no ALB path):
  reads analytics.web_events from ClickHouse to build dashboards

Secrets: SSM Parameter Store ──ESO──► K8s Secrets
```

---

## Prerequisites

Tools required on your machine:

```bash
aws        # CLI v2 — configured with credentials
kubectl    # matched to your EKS cluster version
eksctl     # latest
helm       # v3+
docker     # or podman
```

---

## Step 1 — Provision EKS Cluster

```bash
eksctl create cluster \
  --name event-management \
  --region us-east-1 \
  --nodegroup-name standard \
  --node-type t3.medium \
  --nodes 2 \
  --managed
```

This creates:
- VPC with public/private subnets
- EKS cluster control plane
- Managed node group (2 x t3.medium)
- IAM OIDC provider (required for IRSA)

Update kubeconfig:

```bash
aws eks update-kubeconfig --name event-management --region us-east-1
```

---

## Step 2 — IAM Roles (IRSA)

### 2a. Policy for External Secrets Operator

```bash
aws iam create-policy \
  --policy-name ESO-SSM-ReadPolicy \
  --policy-document '{
    "Version": "2012-10-17",
    "Statement": [{
      "Effect": "Allow",
      "Action": ["ssm:GetParameter", "ssm:GetParameters", "ssm:GetParametersByPath"],
      "Resource": "arn:aws:ssm:us-east-1:<ACCOUNT_ID>:parameter/event-management/*"
    }]
  }'
```

### 2b. Policy for AWS Load Balancer Controller

Download the official policy and create it:

```bash
curl -o iam-policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/main/docs/install/iam_policy.json
aws iam create-policy \
  --policy-name AWSLoadBalancerControllerIAMPolicy \
  --policy-document file://iam-policy.json
```

### 2c. Create IRSA service accounts

```bash
# ESO service account
eksctl create iamserviceaccount \
  --name external-secrets \
  --namespace external-secrets \
  --cluster event-management \
  --attach-policy-arn arn:aws:iam::<ACCOUNT_ID>:policy/ESO-SSM-ReadPolicy \
  --approve \
  --override-existing-serviceaccounts

# ALB Controller service account
eksctl create iamserviceaccount \
  --name aws-load-balancer-controller \
  --namespace kube-system \
  --cluster event-management \
  --attach-policy-arn arn:aws:iam::<ACCOUNT_ID>:policy/AWSLoadBalancerControllerIAMPolicy \
  --approve \
  --override-existing-serviceaccounts
```

### 2d. S3 bucket, Lambda function, and IRSA for event-service low-seats notifications

Coursework requirement: when an event's `seatsAvailable` drops below a threshold, event-service
triggers a serverless function that writes a notification (event ID, timestamp, remaining seats)
to S3. This is the **first app-level IRSA role** in the cluster — until now IRSA has only backed
cluster-infrastructure service accounts (ESO, ALB controller, EBS CSI driver).

Unlike the other IAM setup in this section, this one is scripted rather than copy-pasted —
run the idempotent bootstrap script (safe to re-run any time, e.g. after rotating the Lambda's
code or if a resource was deleted out of band):

```bash
./scripts/provision-low-seats-notification.sh
```

It creates/verifies, in order: the S3 bucket (`cwk-em-low-seats-notifications-<account-id>`,
public access blocked, versioning on), the Lambda execution role + inline `s3:PutObject`
policy, the `event-low-seats-notifier` function itself (from
`lambda/event-low-seats-notifier/index.js`), the `event-service-irsa-role` (trust scoped to
`system:serviceaccount:event-management:event-service`, permission scoped to
`lambda:InvokeFunction` on this one function), and a `lambda:UpdateFunctionCode` grant on the
CI role (`gh-ecr-push`) so the `deploy-lambda` GitHub Actions job (Step 8/`release.yml`) can
push future code changes on its own.

> **Note**: `k8s/event-service/serviceaccount.yaml` hardcodes the role ARN this script
> produces (`arn:aws:iam::<account-id>:role/event-service-irsa-role`). If you run this in a
> different account, update that manifest's `eks.amazonaws.com/role-arn` annotation to match.
> The role must exist **before** `k8s/event-service/` is applied (Step 9) or the pod's IRSA
> token exchange fails — harmless (the Lambda invoke call is caught and logged, seat
> reservation still succeeds), but the notification won't reach S3 until fixed.
>
> Lambda code changes after the initial bootstrap deploy automatically via CI on every tagged
> release (`deploy-lambda` job in `.github/workflows/release.yml`) — no manual redeploy step
> needed day-to-day.

### 2e. IRSA service account for the EBS CSI Driver

ClickHouse is the first stateful in-cluster workload in this project (RDS Postgres runs outside
the cluster), so its PVC needs the AWS EBS CSI driver, which isn't installed by `eksctl create
cluster` by default. This uses AWS's own managed policy rather than a hand-written one:

```bash
eksctl create iamserviceaccount \
  --name ebs-csi-controller-sa \
  --namespace kube-system \
  --cluster event-management \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --override-existing-serviceaccounts
```

---

## Step 3 — Tag Subnets (ALB Auto-Discovery)

The AWS Load Balancer Controller discovers subnets by tags. Tag your VPC subnets:

```bash
# Get the VPC ID
VPC_ID=$(aws eks describe-cluster --name event-management --query "cluster.resourcesVpcConfig.vpcId" --output text)

# Tag public subnets (ALB will be internet-facing)
aws ec2 describe-subnets \
  --filters "Name=vpc-id,Values=$VPC_ID" "Name:map-public-ip-on-launch,Values=true" \
  --query 'Subnets[*].SubnetId' --output text \
  | xargs -n1 aws ec2 create-tags \
    --resources \
    --tags Key=kubernetes.io/role/elb,Value=1

# Tag private subnets (for internal NLBs if needed later)
aws ec2 describe-subnets \
  --filters "Name=vpc-id,Values=$VPC_ID" "Name:map-public-ip-on-launch,Values=false" \
  --query 'Subnets[*].SubnetId' --output text \
  | xargs -n1 aws ec2 create-tags \
    --resources \
    --tags Key=kubernetes.io/role/internal-elb,Value=1
```

---

## Step 4 — RDS PostgreSQL

### 4a. Create a security group for RDS

```bash
# Get EKS VPC CIDR
VPC_CIDR=$(aws ec2 describe-vpcs --vpc-ids $VPC_ID --query 'Vpcs[0].CidrBlock' --output text)

# Create security group
RDS_SG_ID=$(aws ec2 create-security-group \
  --group-name event-management-rds \
  --description "RDS access from EKS cluster" \
  --vpc-id $VPC_ID \
  --query 'GroupId' --output text)

# Allow PostgreSQL from EKS VPC CIDR
aws ec2 authorize-security-group-ingress \
  --group-id $RDS_SG_ID \
  --protocol tcp --port 5432 \
  --cidr $VPC_CIDR
```

### 4b. Provision RDS instance

```bash
aws rds create-db-instance \
  --db-instance-identifier event-management-db \
  --db-instance-class db.t3.micro \
  --engine postgres \
  --engine-version 16 \
  --master-username postgres \
  --master-user-password '<STRONG_PASSWORD>' \
  --allocated-storage 20 \
  --vpc-security-group-ids $RDS_SG_ID \
  --db-subnet-group-name <SUBNET_GROUP> \
  --publicly-accessible false \
  --backup-retention-period 1
```

> **Note**: You'll need a DB subnet group with subnets from the VPC. Create one if it doesn't exist:
> ```bash
> PRIVATE_SUBNETS=$(aws ec2 describe-subnets \
>   --filters "Name=vpc-id,Values=$VPC_ID" "Name:map-public-ip-on-launch,Values=false" \
>   --query 'Subnets[*].SubnetId' --output text | tr '\t' ',')
> aws rds create-db-subnet-group \
>   --db-subnet-group-name event-management-subnet-group \
>   --db-subnet-group-description "Subnets for event management RDS" \
>   --subnet-ids "$PRIVATE_SUBNETS"
> ```

### 4c. Create databases and users

After RDS is available, get its endpoint:

```bash
RDS_ENDPOINT=$(aws rds describe-db-instances \
  --db-instance-identifier event-management-db \
  --query 'DBInstances[0].Endpoint.Address' --output text)
```

Connect and run:

```sql
CREATE USER event_user WITH PASSWORD 'event_secret';
CREATE DATABASE eventdb OWNER event_user;
\c eventdb
GRANT ALL ON SCHEMA public TO event_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO event_user;

CREATE USER programs_user WITH PASSWORD 'programs_secret';
CREATE DATABASE programsdb OWNER programs_user;
\c programsdb
GRANT ALL ON SCHEMA public TO programs_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO programs_user;

CREATE USER registration_user WITH PASSWORD 'registration_secret';
CREATE DATABASE registrationdb OWNER registration_user;
\c registrationdb
GRANT ALL ON SCHEMA public TO registration_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO registration_user;

-- Metabase's own application metadata DB (dashboards, users, saved questions).
-- Metabase does not create this itself — it must already exist before the pod boots.
CREATE USER metabase_user WITH PASSWORD '<STRONG_PASSWORD>';
CREATE DATABASE metabasedb OWNER metabase_user;
\c metabasedb
GRANT ALL ON SCHEMA public TO metabase_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO metabase_user;
```

---

## Step 5 — SSM Parameter Store

Store all secrets as SecureString parameters:

```bash
RDS_ENDPOINT=<your-rds-endpoint>

aws ssm put-parameter \
  --name "/event-management/event-service/db-url" \
  --value "jdbc:postgresql://${RDS_ENDPOINT}:5432/eventdb" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/event-service/db-username" \
  --value "event_user" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/event-service/db-password" \
  --value "event_secret" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/program-service/db-url" \
  --value "jdbc:postgresql://${RDS_ENDPOINT}:5432/programsdb" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/program-service/db-username" \
  --value "programs_user" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/program-service/db-password" \
  --value "programs_secret" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/registration-service/db-url" \
  --value "jdbc:postgresql://${RDS_ENDPOINT}:5432/registrationdb" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/registration-service/db-username" \
  --value "registration_user" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/registration-service/db-password" \
  --value "registration_secret" \
  --type SecureString --overwrite

CLICKHOUSE_PASSWORD='<STRONG_PASSWORD>'   # used in both places below

aws ssm put-parameter \
  --name "/event-management/clickhouse/db" \
  --value "analytics" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/clickhouse/username" \
  --value "analytics" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/clickhouse/password" \
  --value "$CLICKHOUSE_PASSWORD" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/analytics-service/db-url" \
  --value "jdbc:clickhouse://clickhouse:8123/analytics" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/analytics-service/db-username" \
  --value "analytics" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/analytics-service/db-password" \
  --value "$CLICKHOUSE_PASSWORD" \
  --type SecureString --overwrite

METABASE_DB_PASSWORD='<STRONG_PASSWORD>'   # matches the metabase_user password set in Step 4c

aws ssm put-parameter \
  --name "/event-management/metabase/db-host" \
  --value "${RDS_ENDPOINT}" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/metabase/db-port" \
  --value "5432" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/metabase/db-dbname" \
  --value "metabasedb" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/metabase/db-user" \
  --value "metabase_user" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/metabase/db-pass" \
  --value "$METABASE_DB_PASSWORD" \
  --type SecureString --overwrite

aws ssm put-parameter \
  --name "/event-management/metabase/encryption-key" \
  --value "$(openssl rand -base64 32)" \
  --type SecureString --overwrite
```

---

## Step 6 — ECR Repository

This project uses a single shared ECR repository (`cwk/event-management`) with **service-prefixed tags** (`event-service-latest`, `program-service-latest`, etc.) rather than one repo per service — this matches `.github/workflows/release.yml`. `proto-common` is never pushed as an image; it's built as a Gradle artifact and copied into the other services' build contexts (see Step 8a).

If the repo doesn't exist yet:

```bash
aws ecr create-repository --repository-name cwk/event-management --region ap-south-1 --image-scanning-configuration scanOnPush=true
```

Get the repository URI:

```bash
aws ecr describe-repositories \
  --region ap-south-1 \
  --repository-names cwk/event-management \
  --query 'repositories[0].repositoryUri' --output text
```

---

## Step 7 — Install Helm Add-Ons

### 7a. External Secrets Operator

```bash
helm repo add external-secrets https://charts.external-secrets.io
helm repo update

helm install external-secrets external-secrets/external-secrets \
  --namespace external-secrets --create-namespace
```

### 7b. AWS Load Balancer Controller

```bash
helm repo add eks https://aws.github.io/eks-charts
helm repo update

helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  --namespace kube-system \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller \
  --set clusterName=event-management
```

### 7c. Observability — kube-prometheus-stack

Prometheus + Grafana + Alertmanager + kube-state-metrics + node-exporter, deployed in-cluster. `event-service`, `program-service` and `registration-service` expose `/actuator/prometheus` (Spring Boot Actuator + Micrometer); each has a matching `ServiceMonitor` under `k8s/observability/`.

> **Handled by CI/CD from here on**: the `deploy` job in `.github/workflows/release.yml` re-applies every manifest under `k8s/` (not just a rollout restart) and runs the `helm upgrade --install` for this stack on every tagged release, so it stays in sync automatically. The commands below are only needed for the first manual bootstrap of a brand-new cluster, or if you're debugging outside the pipeline. The pipeline needs one more repo secret: **`GRAFANA_ADMIN_PASSWORD`** (Settings → Secrets and variables → Actions) — set it before the first push of a `v*` tag.

```bash
# Grafana admin credentials — don't ship the chart's default admin/prom-operator
kubectl create namespace observability
kubectl create secret generic grafana-admin-credentials \
  -n observability \
  --from-literal=admin-user=admin \
  --from-literal=admin-password='<STRONG_PASSWORD>'

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm install kube-prometheus-stack prometheus-community/kube-prometheus-stack \
  --namespace observability \
  -f k8s/observability/kube-prometheus-stack-values.yaml

# ServiceMonitors for the three app services (deployed after Step 9, once
# the Services/Deployments exist)
kubectl apply -f k8s/observability/servicemonitors.yaml
```

Verify targets are up:

```bash
kubectl get pods -n observability
kubectl get servicemonitors -n event-management
```

**Access for demos/viva** (no public ALB path — keep it internal):

```bash
kubectl port-forward -n observability svc/kube-prometheus-stack-grafana 3000:80
# open http://localhost:3000, log in with the secret above

kubectl port-forward -n observability svc/kube-prometheus-stack-prometheus 9090:9090
# open http://localhost:9090 -> Status -> Targets to confirm scraping
```

In Grafana:
- Import dashboard ID `4701` (JVM Micrometer) for each service, plus the bundled "Kubernetes / Compute Resources / Namespace (Pods)" dashboard for pod/node health.
- Import `k8s/observability/dashboards/event-management-services.json` (Dashboards → New → Import → Upload JSON) for a per-service view of request rate, p95 latency, 5xx error rate, JVM heap, pod availability, and pod restarts.

### 7d. AWS EBS CSI Driver

Not a Helm chart despite the section title — it's installed as an EKS-managed addon, using the
IRSA role created in Step 2e. Required before the ClickHouse `StatefulSet`'s PVC (`k8s/clickhouse/`)
can bind, since modern EKS clusters ship no in-tree/default block storage provisioner.

```bash
eksctl create addon \
  --name aws-ebs-csi-driver \
  --cluster event-management \
  --service-account-role-arn arn:aws:iam::<ACCOUNT_ID>:role/<role-created-in-step-2e> \
  --force
```

Verify it's ready before deploying ClickHouse:

```bash
kubectl get pods -n kube-system -l app.kubernetes.io/name=aws-ebs-csi-driver
kubectl get storageclass
```

---

## Step 8 — Build & Push Docker Images

### 8a. Build proto-common artifact and copy into service contexts

```bash
# Build proto-common and extract artifact
docker build -t proto-common:latest ./proto-common
CID=$(docker create proto-common:latest)
docker cp "$CID":/repo ./proto-common/build/repo
docker rm "$CID"

# Copy into event-service and registration-service build contexts
for SERVICE in event-service registration-service; do
  rm -rf "./$SERVICE/proto-repo"
  cp -r ./proto-common/build/repo "./$SERVICE/proto-repo"
done
```

### 8b. Login to ECR and push images

Single shared repo, service-prefixed tags (matches `.github/workflows/release.yml`):

```bash
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
REGION=ap-south-1
ECR_REPO="cwk/event-management"
REGISTRY="${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"

aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $REGISTRY

# Build and push each service
docker build -t ${REGISTRY}/${ECR_REPO}:event-service-latest ./event-service
docker push ${REGISTRY}/${ECR_REPO}:event-service-latest

docker build -t ${REGISTRY}/${ECR_REPO}:program-service-latest ./program-service
docker push ${REGISTRY}/${ECR_REPO}:program-service-latest

docker build -t ${REGISTRY}/${ECR_REPO}:registration-service-latest ./registration-service
docker push ${REGISTRY}/${ECR_REPO}:registration-service-latest

docker build -t ${REGISTRY}/${ECR_REPO}:analytics-service-latest ./analytics-service
docker push ${REGISTRY}/${ECR_REPO}:analytics-service-latest

# Build frontend with VITE_* as empty strings (browser uses relative paths, ALB routes them)
docker build \
  --build-arg VITE_EVENT_SERVICE_URL="" \
  --build-arg VITE_PROGRAM_SERVICE_URL="" \
  --build-arg VITE_REGISTRATION_SERVICE_URL="" \
  --build-arg VITE_ANALYTICS_SERVICE_URL="" \
  -t ${REGISTRY}/${ECR_REPO}:event-view-fe-latest ./event-view-fe
docker push ${REGISTRY}/${ECR_REPO}:event-view-fe-latest
```

---

## Step 9 — Deploy Kubernetes Manifests

> **Note**: Since images live in the single shared repo (Step 6/8b), each Deployment's `image:` field must reference `<ACCOUNT_ID>.dkr.ecr.ap-south-1.amazonaws.com/cwk/event-management:<service>-latest` (e.g. `:event-service-latest`) — not a separate per-service repo URI.

> **Note**: `k8s/secrets/` is applied manually here, once. The CI role used by `.github/workflows/release.yml` intentionally has no RBAC on `external-secrets.io` resources, so the pipeline does not re-apply this directory — only re-run this step by hand if you change an `ExternalSecret`/`ClusterSecretStore`.

> **Note**: `k8s/event-service/serviceaccount.yaml` requires the `event-service-irsa-role` IAM
> role from Step 2d to already exist — run that step first, or the pod will start without valid
> AWS credentials and the low-seats Lambda invocation will silently fail (logged, but the
> reservation itself still succeeds).

```bash
# Create namespace first
kubectl apply -f k8s/namespace.yaml

# Create external secrets (ESO syncs from SSM) — one-time, not part of CI/CD
kubectl apply -f k8s/secrets/

# Deploy ClickHouse (requires the EBS CSI driver from Step 7d)
kubectl apply -f k8s/clickhouse/

# Deploy Metabase (requires the metabase_user/metabasedb from Step 4c to
# already exist on RDS, or the pod will crash-loop on first boot)
kubectl apply -f k8s/metabase/

# Deploy backend services
kubectl apply -f k8s/event-service/
kubectl apply -f k8s/program-service/
kubectl apply -f k8s/registration-service/
kubectl apply -f k8s/analytics-service/

# Deploy frontend
kubectl apply -f k8s/frontend/

# Create Ingress (triggers ALB provisioning)
kubectl apply -f k8s/ingress.yaml

# Wire up Prometheus scraping for the three backend services (Step 7c)
kubectl apply -f k8s/observability/servicemonitors.yaml
```

---

## Step 10 — Verify

```bash
# Check pods are running
kubectl get pods -n event-management

# Check services
kubectl get svc -n event-management

# Check ClickHouse's PVC bound successfully
kubectl get pvc -n event-management
kubectl get pods -n event-management -l app=clickhouse

# Check Metabase came up (may take a minute or two on first boot — it runs
# app-DB migrations against metabasedb the first time it starts)
kubectl get pods -n event-management -l app=metabase

# Get ALB DNS name
kubectl get ingress -n event-management event-management -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'

# Check external secrets synced
kubectl get secrets -n event-management
kubectl get externalsecrets -n event-management
```

Access the application at the ALB DNS name in your browser.

---

## Step 11 — Access Metabase & Build the Dashboard

Like Grafana, Metabase is an internal admin tool with **no public ALB path** — access it via
port-forward only (using local port `3001` here since Grafana's port-forward already claims
`3000` locally):

```bash
kubectl port-forward -n event-management svc/metabase 3001:3000
# open http://localhost:3001 — first run walks through Metabase's setup wizard
```

Full steps to connect ClickHouse as a data source and build the analytics dashboard (Questions,
charts, dashboard assembly) are in [`METABASE.md`](./METABASE.md) — that part is UI-driven and
not something `kubectl apply` can do.

---

## Cleanup

```bash
# Delete all K8s resources — cascades to the ClickHouse PVC, and since its
# StorageClass has reclaimPolicy: Delete, the underlying EBS volume too.
kubectl delete namespace event-management

# Delete observability stack
helm uninstall kube-prometheus-stack -n observability
kubectl delete namespace observability

# Delete ESO service account
eksctl delete iamserviceaccount --cluster event-management --namespace external-secrets --name external-secrets

# Delete ALB controller service account
eksctl delete iamserviceaccount --cluster event-management --namespace kube-system --name aws-load-balancer-controller

# Delete EBS CSI driver service account
eksctl delete iamserviceaccount --cluster event-management --namespace kube-system --name ebs-csi-controller-sa

# Delete low-seats notification Lambda, its bucket, and both IAM roles (Step 2d)
aws lambda delete-function --function-name event-low-seats-notifier --region ap-south-1
aws s3 rm s3://cwk-em-low-seats-notifications-<ACCOUNT_ID> --recursive
aws s3api delete-bucket --bucket cwk-em-low-seats-notifications-<ACCOUNT_ID> --region ap-south-1
aws iam delete-role-policy --role-name event-low-seats-notifier-exec-role --policy-name s3-write-notifications
aws iam detach-role-policy --role-name event-low-seats-notifier-exec-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
aws iam delete-role --role-name event-low-seats-notifier-exec-role
aws iam delete-role-policy --role-name event-service-irsa-role --policy-name invoke-low-seats-notifier
aws iam delete-role --role-name event-service-irsa-role

# Delete EKS cluster
eksctl delete cluster --name event-management

# Delete RDS
aws rds delete-db-instance --db-instance-identifier event-management-db --skip-final-snapshot

# Delete SSM parameters
aws ssm delete-parameters \
  --names $(aws ssm get-parameters-by-path --path /event-management --query 'Parameters[].Name' --output text)

# Delete ECR repo
aws ecr delete-repository --repository-name cwk/event-management --region ap-south-1 --force

# Delete IAM policies
aws iam delete-policy --policy-arn arn:aws:iam::<ACCOUNT_ID>:policy/ESO-SSM-ReadPolicy
aws iam delete-policy --policy-arn arn:aws:iam::<ACCOUNT_ID>:policy/AWSLoadBalancerControllerIAMPolicy
```
