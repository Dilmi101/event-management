#!/bin/bash
# Resumes the event-management cluster after cluster-down.sh: starts RDS, scales ClickHouse and
# the app Deployments back up, recreates the HPAs and Ingress, and prints the new ALB hostname
# (it changes every time the Ingress/ALB is recreated). Safe to re-run.
set -euo pipefail

REGION=ap-south-1
NAMESPACE=event-management
DB_INSTANCE=event-management-db
K8S_DIR="$(dirname "$0")/../k8s"

for cmd in kubectl aws; do
  command -v "$cmd" >/dev/null || { echo "Missing required tool: $cmd"; exit 1; }
done

echo "=== Starting RDS instance ${DB_INSTANCE} ==="
STATUS=$(aws rds describe-db-instances --db-instance-identifier "$DB_INSTANCE" --region "$REGION" \
  --query 'DBInstances[0].DBInstanceStatus' --output text)
if [ "$STATUS" = "stopped" ]; then
  aws rds start-db-instance --db-instance-identifier "$DB_INSTANCE" --region "$REGION" >/dev/null
  echo "Start requested, waiting for it to become available (this can take a few minutes)..."
  aws rds wait db-instance-available --db-instance-identifier "$DB_INSTANCE" --region "$REGION"
else
  echo "RDS instance is already in state '${STATUS}' -- skipping start request."
fi
echo "RDS is available."

echo
echo "=== Scaling ClickHouse StatefulSet back up ==="
kubectl scale statefulset clickhouse --replicas=1 -n "$NAMESPACE"
kubectl wait --for=condition=ready pod -l app=clickhouse -n "$NAMESPACE" --timeout=180s

echo
echo "=== Reapplying HPAs (brings their Deployments back up to minReplicas) ==="
for svc in event-service program-service registration-service analytics-service frontend; do
  kubectl apply -f "${K8S_DIR}/${svc}/hpa.yaml"
done

echo
echo "=== Scaling metabase back up (no HPA) ==="
kubectl scale deployment metabase --replicas=1 -n "$NAMESPACE"

echo
echo "=== Reapplying Ingress ==="
kubectl apply -f "${K8S_DIR}/ingress.yaml"

echo
echo "=== Waiting for rollouts ==="
for dep in event-service program-service registration-service analytics-service frontend metabase; do
  kubectl rollout status deployment/"$dep" -n "$NAMESPACE" --timeout=180s
done

echo
echo "=== Waiting for the new ALB hostname ==="
HOSTNAME=""
for _ in $(seq 1 20); do
  HOSTNAME=$(kubectl get ingress -n "$NAMESPACE" event-management \
    -o jsonpath='{.status.loadBalancer.ingress[0].hostname}' 2>/dev/null || true)
  [ -n "$HOSTNAME" ] && break
  sleep 15
done

echo
echo "=== Done ==="
if [ -n "$HOSTNAME" ]; then
  echo "App is reachable at: http://${HOSTNAME}"
  echo "(ALB DNS can take a couple more minutes to fully propagate/health-check.)"
else
  echo "ALB hostname not assigned yet -- check with:"
  echo "  kubectl get ingress -n ${NAMESPACE} event-management"
fi
