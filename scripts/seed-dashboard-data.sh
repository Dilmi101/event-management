#!/bin/bash
# Seeds events + web analytics traffic against the LIVE cluster and verifies the
# low-seats -> Lambda -> S3 notification fired. Feeds the Metabase dashboards
# described in METABASE.md. Safe to re-run -- each run creates a fresh
# "Low Seats Demo Event", and the 4 general events just accumulate more views.
#
# Requires: hurl, jq, aws cli, kubectl (all pointed at the live cluster already).
set -euo pipefail

REGION=ap-south-1
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
BUCKET="cwk-em-low-seats-notifications-${ACCOUNT_ID}"
RUN_ID=$(date +%s)

for cmd in hurl jq aws kubectl; do
  command -v "$cmd" >/dev/null || { echo "Missing required tool: $cmd"; exit 1; }
done

BASE_HOST=$(kubectl get ingress -n event-management event-management \
  -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
BASE_URL="http://${BASE_HOST}"
echo "=== Target: ${BASE_URL}  (run id: ${RUN_ID}) ==="

echo "=== Seeding events, analytics, and registrations ==="
hurl --test \
  --variable base_url="$BASE_URL" \
  --variable run_id="$RUN_ID" \
  "$(dirname "$0")/../hurl/seed-dashboard-data.hurl"

echo
echo "=== Locating the low-seats demo event from this run ==="
EVENT_JSON=$(curl -s "$BASE_URL/api/events" \
  | jq -c --arg title "Low Seats Demo Event ${RUN_ID}" '.[] | select(.title == $title)')
EVENT_ID=$(echo "$EVENT_JSON" | jq -r '.eventId')
SEATS=$(echo "$EVENT_JSON" | jq -r '.seatsAvailable')
echo "Event ${EVENT_ID}: seatsAvailable=${SEATS}"

if [ "$SEATS" -ge 10 ]; then
  echo "WARNING: seatsAvailable ($SEATS) is not below the default threshold (10) -- the Lambda won't have fired."
fi

echo
echo "=== Checking S3 for the resulting notification (Lambda invoke is async, allowing a few seconds) ==="
sleep 8
if aws s3 ls "s3://${BUCKET}/notifications/${EVENT_ID}/" --recursive 2>/dev/null | grep -q .; then
  aws s3 ls "s3://${BUCKET}/notifications/${EVENT_ID}/" --recursive
else
  echo "No notification object found yet. Check CloudWatch Logs:"
  echo "  aws logs tail /aws/lambda/event-low-seats-notifier --since 5m --region ${REGION}"
fi

echo
echo "=== ClickHouse row counts by event_type (should all be non-zero) ==="
kubectl exec -n event-management clickhouse-0 -- clickhouse-client --query \
  "SELECT event_type, count() FROM analytics.web_events GROUP BY event_type ORDER BY event_type"

echo
echo "Done. Open Metabase to see the dashboards populated:"
echo "  kubectl port-forward -n event-management svc/metabase 3001:3000"
echo "  then http://localhost:3001"
