# Architecture

ASCII diagrams of the New Event platform, covering the two views the coursework report asks
for — solution architecture (request/data flows) and deployment architecture (K8s topology +
CI/CD) — plus supporting views for secrets and observability.

## 1. Solution Architecture — Request Flow & Data Flow

```
                              +-------------------+
                              |      Browser      |
                              |   (End User / SPA) |
                              +---------+---------+
                                        |
                                        | HTTPS :80
                                        v
                    +---------------------------------------+
                    |         AWS Application Load Balancer  |
                    |     (provisioned by ALB Ingress Ctrl)   |
                    |  k8s-eventman-...elb.amazonaws.com      |
                    +---------------------+-------------------+
                                          |
                     path-based routing (k8s/ingress.yaml)
      +--------------+--------------+------------+---------------+-------------+
      |              |              |            |               |             |
      v              v              v            v               v             v
  /api/events    /api/speakers  /api/regis-   /api/analytics    /api/         /  (all
  /api/sponsors  /api/sessions  trations                        contact       else)
  /api/contact
      |              |              |            |               |             |
      v              v              v            v               v             v
+-----------+  +-----------+  +-------------+ +-------------+ +-----------+  +----------+
|  event-   |  | program-  |  | registration| | analytics-  | | event-    |  | frontend |
|  service  |  |  service  |  |  -service   | |  service    | | service   |  | (nginx + |
| :8081/9091|  |  :8082    |  |   :8083     | |   :8085     | | :8081     |  |  React)  |
+-----+-----+  +-----+-----+  +------+------+ +------+------+ +-----------+  |   :80    |
      |              |               |               |                       +----------+
      | JDBC         | JDBC          | JDBC          | JDBC
      |              |               | gRPC :9091    |
      v              v               v (calls event- v
+------------------------------+     service)  +------------------+
|   AWS RDS PostgreSQL          |<----+          |  ClickHouse       |
|   (single instance, ap-s-1)   |               |  StatefulSet       |
|   +-----------+ +-----------+ |               |  (in-cluster,      |
|   | eventdb   | | programsdb| |               |   1 replica)       |
|   +-----------+ +-----------+ |               |  :8123 http        |
|   +-----------------------+   |               |  :9000 native      |
|   | registrationdb        |   |               |  PVC: gp3 5Gi      |
|   +-----------------------+   |               |  (EKS Auto Mode    |
+--------------------------------+               |   EBS CSI driver) |
                                                  |                    |
                                                  |  analytics.web_    |
                                                  |  events (MergeTree)|
                                                  +--------------------+
```

**Web analytics data flow (3 captured event types):**

```
Browser --fetch POST /api/analytics--> ALB --> analytics-service
       --JDBC INSERT--> ClickHouse.analytics.web_events

Captured events: event_view | contact_submit | registration
```

**Low-seats serverless notification flow:**

```
registration-service --gRPC reserveSeats--> event-service
                                                  |
                                     seats_available < threshold?
                                     (conditional UPDATE ... WHERE
                                      low_seats_notified_at IS NULL
                                      -- debounce, fires once per crossing)
                                                  |
                                                  v  IRSA (event-service-irsa-role)
                                                     LambdaClient.invoke()
                                                     (async, InvocationType.EVENT)
                                     +----------------------------------+
                                     |  AWS Lambda                       |
                                     |  event-low-seats-notifier (Node.js)|
                                     +-----------------+------------------+
                                                        | PutObject
                                                        v
                                     +----------------------------------+
                                     |  S3: cwk-em-low-seats-             |
                                     |  notifications-<account-id>        |
                                     |  notifications/<eventId>/<ts>.json |
                                     +----------------------------------+
```

**Dashboard / visualization read path:**

```
+--------------------+       SQL queries        +--------------------+
|      Metabase       | ------------------------> |     ClickHouse      |
|  (in-cluster, no     |   (ClickHouse driver,     |  analytics.web_    |
|   ALB path)          |    bundled since v54)     |  events            |
+--------------------+                            +--------------------+
         ^
         | kubectl port-forward svc/metabase 3001:3000
         | (viva/demo access only -- same pattern as Grafana,
         |  no public Ingress path for this admin tool)
+--------------------+
|      Browser       |
| (person doing demo)|
+--------------------+
```

## 2. Secrets Flow

```
  AWS SSM Parameter Store                External Secrets Operator         K8s Secrets
  /event-management/                     (namespace: external-secrets)     (namespace: event-management)
    event-service/*         ---+                                          +--> event-service-db
    program-service/*          |         +----------------------+         |
    registration-service/*     +-------->|  ClusterSecretStore  |---------+--> program-service-db
    analytics-service/*        |         |      "aws-ssm"       |         |
    clickhouse/*                |         |  (IRSA auth, 1h sync)|         +--> registration-service-db
    metabase/*                --+         +----------------------+         |
                                                                            +--> analytics-service-db
                                                                            |
                                                                            +--> clickhouse-credentials
                                                                            |
                                                                            +--> metabase-db
```

Each Secret is consumed via `envFrom.secretRef` in its matching Deployment/StatefulSet.

## 3. Observability

```
  event-service  --+
  program-service --+--/actuator/prometheus--> Prometheus (kube-prometheus-stack)
  registration-svc-+                                |         (namespace: observability)
  analytics-service+                                v
                                                  Grafana dashboards
                                        (event-management-services.json,
                                          JVM Micrometer per service)
```

## 4. Deployment Architecture — EKS Cluster Topology (EKS Auto Mode / Karpenter)

```
  AWS Account 959740417723 / ap-south-1
  +----------------------------------------------------------------------+
  |  EKS Cluster: event-management  (Auto Mode: compute + blockStorage)   |
  |                                                                        |
  |  +------------------------------------------------------------------+ |
  |  |  Namespace: event-management                                     | |
  |  |                                                                    | |
  |  |   [frontend]  [event-service]  [program-service]                 | |
  |  |   [registration-service]  [analytics-service]                    | |
  |  |        each: Deployment + Service + HPA (1-3 replicas, CPU 70%)  | |
  |  |                                                                    | |
  |  |   [event-service] ServiceAccount "event-service" -- IRSA -->     | |
  |  |     event-service-irsa-role (lambda:InvokeFunction, scoped to    | |
  |  |     event-low-seats-notifier only -- first app-level IRSA role) | |
  |  |                                                                    | |
  |  |   [clickhouse-0]  StatefulSet (1 replica) + Service + PVC        | |
  |  |        StorageClass "gp3" -> provisioner ebs.csi.eks.amazonaws.com| |
  |  |        (Auto Mode's built-in EBS driver, NOT the community addon)| |
  |  |                                                                    | |
  |  |   [metabase]  Deployment (1 replica, no HPA) + Service           | |
  |  |        no Ingress path -- port-forward only, mirrors Grafana     | |
  |  |                                                                    | |
  |  |   Ingress "event-management" (class: alb)                        | |
  |  +------------------------------------------------------------------+ |
  |                                                                        |
  |  +------------------------+   +------------------------------------+  |
  |  | ns: external-secrets   |   | ns: observability                  |  |
  |  |  ESO controller         |   |  kube-prometheus-stack             |  |
  |  |  (IRSA -> SSM read)     |   |  (Prometheus, Grafana, Alertmgr)   |  |
  |  +------------------------+   +------------------------------------+  |
  |                                                                        |
  |  Karpenter-managed nodes (nodePools: general-purpose, system)         |
  +----------------------------------------------------------------------+
              |                                    |
              v                                    v
  +---------------------+              +---------------------------+
  |  AWS RDS PostgreSQL   |              |  AWS SSM Parameter Store   |
  |  (outside cluster)    |              +---------------------------+
  +---------------------+

              |
              v  (invoked via IRSA, see above)
  +---------------------+              +---------------------------+
  |  AWS Lambda           | --PutObject-->|  S3: cwk-em-low-seats-    |
  |  event-low-seats-      |              |  notifications-<acct-id>  |
  |  notifier (Node.js)    |              +---------------------------+
  +---------------------+
```

## 5. CI/CD Pipeline — `.github/workflows/release.yml` (trigger: push tag `v*`)

```
  git push origin main
  git tag vX.Y.Z && git push origin vX.Y.Z
            |
            v
  +-----------------------+
  | build-proto-common     |  builds proto-common Gradle artifact
  |  (GitHub Actions job)  |  -> uploaded as workflow artifact "proto-repo"
  +-----------+-----------+
              |
              v
  +----------------------------------------------------------------+
  |  release  (matrix job, 1 per service)                            |
  |    event-service | program-service | registration-service |      |
  |    analytics-service | event-view-fe                             |
  |                                                                    |
  |   for each: docker build -> tag :<service>-<ver> + :<service>-   |
  |             latest -> docker push to ECR                          |
  |             (event-view-fe gets VITE_*_SERVICE_URL="" build args, |
  |              so browser paths stay relative & ALB-routed)         |
  +-----------------------------------+------------------------------+
                                       |
                                       v
                        +---------------------------+
                        |   AWS ECR: cwk/event-      |
                        |   management (shared repo, |
                        |   service-prefixed tags)   |
                        +---------------------------+
                                       |
                                       v
  +----------------------------------------------------------------+
  |  deploy-lambda  (GitHub Actions job, runs in parallel with       |
  |                  release -- no docker image, just code)          |
  |    zip lambda/event-low-seats-notifier/index.js                  |
  |    aws lambda update-function-code --function-name                |
  |        event-low-seats-notifier                                   |
  +----------------------------------------------------------------+

  +----------------------------------------------------------------+
  |  deploy  (GitHub Actions job)                                    |
  |    kubectl apply -f k8s/namespace.yaml                          |
  |    kubectl apply -f k8s/clickhouse/                              |
  |    kubectl apply -f k8s/metabase/                                |
  |    kubectl apply -f k8s/event-service/ program-service/          |
  |               registration-service/ analytics-service/           |
  |    kubectl apply -f k8s/frontend/                                 |
  |    kubectl apply -f k8s/ingress.yaml                              |
  |    kubectl exec clickhouse-0 -- clickhouse-client < each file in |
  |        clickhouse-migrations/*.sql (idempotent, no tracking       |
  |        table -- ClickHouse has no Liquibase equivalent)           |
  |    kubectl rollout restart deployment/* -n event-management      |
  |    kubectl rollout status   deployment/* -n event-management     |
  |    helm upgrade kube-prometheus-stack + apply servicemonitors    |
  |                                                                    |
  |    NOTE: k8s/secrets/ (ExternalSecrets) is applied MANUALLY,      |
  |          once, outside CI — the pipeline's IAM role has no        |
  |          RBAC on external-secrets.io resources by design.         |
  +----------------------------------------------------------------+
                                       |
                                       v
                              Live on EKS cluster
                    (verified: POST /api/analytics -> 200,
                     row confirmed in ClickHouse.web_events)
```

> The `event-low-seats-notifier` Lambda's code (`lambda/event-low-seats-notifier/index.js`) is
> deployed by the `deploy-lambda` job on every tagged release — it zips `index.js` and runs
> `aws lambda update-function-code`, independent of the Docker-image matrix job since the
> Lambda isn't containerized. The AWS-side resources it depends on (function, execution role,
> IRSA role, S3 bucket) are bootstrapped once via `scripts/provision-low-seats-notification.sh`
> (idempotent — safe to re-run), not by CI.
