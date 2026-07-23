#!/bin/bash
# Pauses the event-management cluster to save cost (e.g. between now and the viva) without
# tearing anything down: scales all workloads to 0, releases the ALB, and stops RDS. ClickHouse's
# PVC and RDS's data are untouched. The EKS control-plane fee (~$0.10/hr) keeps running -- only a
# full `eksctl delete cluster` avoids that, and that's not worth the rebuild risk here. Reverse
# with cluster-up.sh. Safe to re-run.
set -euo pipefail

REGION=ap-south-1
NAMESPACE=event-management
DB_INSTANCE=event-management-db

for cmd in kubectl aws; do
  command -v "$cmd" >/dev/null || { echo "Missing required tool: $cmd"; exit 1; }
done

echo "=== Deleting HPAs (so they don't fight the scale-down) ==="
for svc in event-service program-service registration-service analytics-service frontend; do
  kubectl delete -f "$(dirname "$0")/../k8s/${svc}/hpa.yaml" --ignore-not-found
done

echo
echo "=== Deleting Ingress (releases the ALB -- a new hostname is issued on cluster-up.sh) ==="
kubectl delete -f "$(dirname "$0")/../k8s/ingress.yaml" --ignore-not-found

echo
echo "=== Scaling all Deployments to 0 ==="
kubectl scale deployment --all --replicas=0 -n "$NAMESPACE"

echo
echo "=== Scaling ClickHouse StatefulSet to 0 (PVC/data preserved) ==="
kubectl scale statefulset clickhouse --replicas=0 -n "$NAMESPACE"

echo
echo "=== Stopping RDS instance ${DB_INSTANCE} ==="
STATUS=$(aws rds describe-db-instances --db-instance-identifier "$DB_INSTANCE" --region "$REGION" \
  --query 'DBInstances[0].DBInstanceStatus' --output text)
if [ "$STATUS" = "available" ]; then
  aws rds stop-db-instance --db-instance-identifier "$DB_INSTANCE" --region "$REGION" >/dev/null
  echo "Stop requested."
else
  echo "RDS instance is already in state '${STATUS}' -- skipping stop request."
fi

echo
echo "=== Done ==="
echo "Pods/StatefulSet scaled to 0; Karpenter will deprovision the now-idle EC2 nodes"
echo "automatically over the next few minutes (check with: kubectl get nodes)."
echo "RDS is stopping (AWS auto-restarts it after 7 days if left stopped that long)."
echo "Note: the EKS control-plane fee keeps billing until the cluster itself is deleted --"
echo "this script does not touch that."
echo "Run ./scripts/cluster-up.sh to bring everything back before the viva."
