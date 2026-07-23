#!/bin/bash
# Zips index.js and creates/updates the event-low-seats-notifier Lambda.
# Requires the function/role/bucket to already exist per EKS-PROVISIONING.md.
set -e

FUNCTION_NAME=event-low-seats-notifier
REGION=ap-south-1

cd "$(dirname "$0")"
rm -f function.zip
zip function.zip index.js

if aws lambda get-function --function-name "$FUNCTION_NAME" --region "$REGION" >/dev/null 2>&1; then
  echo "=== Updating existing function code ==="
  aws lambda update-function-code \
    --function-name "$FUNCTION_NAME" \
    --zip-file fileb://function.zip \
    --region "$REGION"
else
  echo "=== Function does not exist yet — create it first (see EKS-PROVISIONING.md) ==="
  exit 1
fi
