#!/bin/bash
# Idempotent bootstrap for the low-seats -> S3 serverless notification feature.
# Creates (or verifies) the S3 bucket, Lambda function + execution role, and the
# IRSA role event-service assumes to invoke it. Safe to re-run.
#
# This is infra bootstrap (like eksctl create iamserviceaccount in EKS-PROVISIONING.md
# Step 2) — a one-time/rarely-changed step, distinct from the app CI/CD pipeline which
# deploys k8s manifests and (as of this script) the Lambda's own code on every tagged
# release. Run this once before the first `k8s/event-service/` deploy that references
# the event-service ServiceAccount/IRSA role.

set -euo pipefail

REGION=ap-south-1
CLUSTER=event-management
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
BUCKET="cwk-em-low-seats-notifications-${ACCOUNT_ID}"
FUNCTION_NAME=event-low-seats-notifier
EXEC_ROLE=event-low-seats-notifier-exec-role
IRSA_ROLE=event-service-irsa-role
LAMBDA_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)/lambda/event-low-seats-notifier"

echo "=== Account: ${ACCOUNT_ID}  Region: ${REGION}  Bucket: ${BUCKET} ==="

# --- 1. S3 bucket ---
if aws s3api head-bucket --bucket "$BUCKET" --region "$REGION" 2>/dev/null; then
  echo "S3 bucket ${BUCKET} already exists, skipping create."
else
  echo "Creating S3 bucket ${BUCKET}..."
  aws s3api create-bucket \
    --bucket "$BUCKET" \
    --region "$REGION" \
    --create-bucket-configuration LocationConstraint="$REGION"

  aws s3api put-public-access-block \
    --bucket "$BUCKET" \
    --public-access-block-configuration \
    BlockPublicAcls=true,IgnorePublicAcls=true,BlockPublicPolicy=true,RestrictPublicBuckets=true

  aws s3api put-bucket-versioning \
    --bucket "$BUCKET" \
    --versioning-configuration Status=Enabled
fi

# --- 2. Lambda execution role ---
if aws iam get-role --role-name "$EXEC_ROLE" >/dev/null 2>&1; then
  echo "IAM role ${EXEC_ROLE} already exists, skipping create."
else
  echo "Creating IAM role ${EXEC_ROLE}..."
  TRUST_POLICY=$(cat <<'EOF'
{
  "Version": "2012-10-17",
  "Statement": [
    { "Effect": "Allow", "Principal": { "Service": "lambda.amazonaws.com" }, "Action": "sts:AssumeRole" }
  ]
}
EOF
)
  aws iam create-role \
    --role-name "$EXEC_ROLE" \
    --assume-role-policy-document "$TRUST_POLICY"
fi

aws iam attach-role-policy \
  --role-name "$EXEC_ROLE" \
  --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole

S3_WRITE_POLICY=$(cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    { "Effect": "Allow", "Action": "s3:PutObject", "Resource": "arn:aws:s3:::${BUCKET}/notifications/*" }
  ]
}
EOF
)
aws iam put-role-policy \
  --role-name "$EXEC_ROLE" \
  --policy-name s3-write-notifications \
  --policy-document "$S3_WRITE_POLICY"

# Give IAM a moment to propagate the freshly created role before Lambda references it.
sleep 8

# --- 3. Lambda function ---
rm -f "${LAMBDA_DIR}/function.zip"
(cd "$LAMBDA_DIR" && zip -q function.zip index.js)

if aws lambda get-function --function-name "$FUNCTION_NAME" --region "$REGION" >/dev/null 2>&1; then
  echo "Lambda function ${FUNCTION_NAME} already exists, updating code + config."
  aws lambda update-function-code \
    --function-name "$FUNCTION_NAME" \
    --zip-file "fileb://${LAMBDA_DIR}/function.zip" \
    --region "$REGION" >/dev/null
  aws lambda wait function-updated --function-name "$FUNCTION_NAME" --region "$REGION"
  aws lambda update-function-configuration \
    --function-name "$FUNCTION_NAME" \
    --environment "Variables={NOTIFICATIONS_BUCKET=${BUCKET}}" \
    --region "$REGION" >/dev/null
else
  echo "Creating Lambda function ${FUNCTION_NAME}..."
  aws lambda create-function \
    --function-name "$FUNCTION_NAME" \
    --runtime nodejs20.x \
    --role "arn:aws:iam::${ACCOUNT_ID}:role/${EXEC_ROLE}" \
    --handler index.handler \
    --zip-file "fileb://${LAMBDA_DIR}/function.zip" \
    --timeout 10 \
    --memory-size 128 \
    --region "$REGION" \
    --environment "Variables={NOTIFICATIONS_BUCKET=${BUCKET}}" >/dev/null
fi

# --- 4. IRSA role for event-service ---
OIDC_ID=$(aws eks describe-cluster --name "$CLUSTER" --region "$REGION" \
  --query "cluster.identity.oidc.issuer" --output text | sed 's|https://||')

if aws iam get-role --role-name "$IRSA_ROLE" >/dev/null 2>&1; then
  echo "IAM role ${IRSA_ROLE} already exists, skipping create."
else
  echo "Creating IAM role ${IRSA_ROLE}..."
  IRSA_TRUST_POLICY=$(cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": { "Federated": "arn:aws:iam::${ACCOUNT_ID}:oidc-provider/${OIDC_ID}" },
      "Action": "sts:AssumeRoleWithWebIdentity",
      "Condition": {
        "StringEquals": {
          "${OIDC_ID}:sub": "system:serviceaccount:event-management:event-service",
          "${OIDC_ID}:aud": "sts.amazonaws.com"
        }
      }
    }
  ]
}
EOF
)
  aws iam create-role \
    --role-name "$IRSA_ROLE" \
    --assume-role-policy-document "$IRSA_TRUST_POLICY"
fi

INVOKE_POLICY=$(cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    { "Effect": "Allow", "Action": "lambda:InvokeFunction", "Resource": "arn:aws:lambda:${REGION}:${ACCOUNT_ID}:function:${FUNCTION_NAME}" }
  ]
}
EOF
)
aws iam put-role-policy \
  --role-name "$IRSA_ROLE" \
  --policy-name invoke-low-seats-notifier \
  --policy-document "$INVOKE_POLICY"

# --- 5. CI role permission to deploy Lambda code updates (release.yml "deploy-lambda" job) ---
CI_ROLE=gh-ecr-push
if aws iam get-role --role-name "$CI_ROLE" >/dev/null 2>&1; then
  CI_DEPLOY_POLICY=$(cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["lambda:UpdateFunctionCode", "lambda:GetFunction"],
      "Resource": "arn:aws:lambda:${REGION}:${ACCOUNT_ID}:function:${FUNCTION_NAME}"
    }
  ]
}
EOF
)
  aws iam put-role-policy \
    --role-name "$CI_ROLE" \
    --policy-name deploy-low-seats-notifier-lambda \
    --policy-document "$CI_DEPLOY_POLICY"
  echo "Granted ${CI_ROLE} permission to deploy ${FUNCTION_NAME} code updates."
else
  echo "WARNING: CI role ${CI_ROLE} not found — skipping CI deploy permission grant." \
       "The 'deploy-lambda' GitHub Actions job will fail until this role/permission exists."
fi

echo "=== Done ==="
echo "S3 bucket:            s3://${BUCKET}"
echo "Lambda function:      ${FUNCTION_NAME}"
echo "Lambda exec role:     arn:aws:iam::${ACCOUNT_ID}:role/${EXEC_ROLE}"
echo "event-service IRSA:   arn:aws:iam::${ACCOUNT_ID}:role/${IRSA_ROLE}"
echo
echo "k8s/event-service/serviceaccount.yaml already references this IRSA role ARN."
echo "Roll out event-service (via the normal CI release, i.e. git tag + push) to pick"
echo "up the ServiceAccount and new env vars."
