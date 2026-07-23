# Runbook — Deploy & Test

Assumes the cluster and AWS resources already exist (see `EKS-PROVISIONING.md` for a
from-scratch bootstrap). This covers shipping a change and confirming the platform actually
works end to end.

## 1. Deploy

```bash
# Commit your changes, then cut a release — this is what triggers everything.
git tag v0.1.6
git push origin v0.1.6
```

Pushing a `v*` tag triggers `.github/workflows/release.yml`: builds `proto-common`, builds
and pushes each service's Docker image to ECR, deploys the Lambda code
(`event-low-seats-notifier`), applies all `k8s/` manifests, runs any pending ClickHouse
migrations (`clickhouse-migrations/*.sql`), rolls out the updated Deployments, and re-applies
the observability stack.

Watch it run:

```bash
gh run watch --repo Dilmi101/event-management
```

All jobs should finish green. If `deploy` fails on the observability step with
`another operation (install/upgrade/rollback) is in progress`, a previous Helm release is
stuck — check `helm history kube-prometheus-stack -n observability` and
`helm rollback kube-prometheus-stack <last deployed revision> -n observability`.

## 2. Verify the deployment landed

```bash
kubectl get pods -n event-management
# every pod should be Running/Ready — event-service, program-service,
# registration-service, analytics-service, frontend, clickhouse-0, metabase

kubectl get ingress -n event-management event-management \
  -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'
```

Open that ALB hostname in a browser — the New Event frontend should load.

## 3. Test the core functional path

1. **Browse an event page** on the frontend (drives an `event_view` analytics event).
2. **Register for the event** with a small ticket count (drives `registration_submit` and
   decrements `seatsAvailable` via `event-service`).
3. **Repeat registrations** on the same event until `seatsAvailable` drops below the
   threshold (default 10; check/set `LOW_SEATS_THRESHOLD` on the `event-service` Deployment
   if you want a smaller number for a live demo).

## 4. Test the low-seats → S3 notification

```bash
EVENT_ID=<the event you registered against>
aws s3 ls s3://cwk-em-low-seats-notifications-959740417723/notifications/$EVENT_ID/ --recursive
aws s3 cp s3://cwk-em-low-seats-notifications-959740417723/notifications/$EVENT_ID/<timestamp>.json -
```

Expect one JSON object with `eventId`, `timestamp`, `seatsAvailable`. Registering again on the
same event should **not** produce a second object (debounce) — confirm via CloudWatch Logs:

```bash
aws logs tail /aws/lambda/event-low-seats-notifier --since 15m --region ap-south-1
```

## 5. Test web analytics → ClickHouse → Metabase

```bash
kubectl exec -n event-management clickhouse-0 -- clickhouse-client \
  --query "SELECT event_type, count() FROM analytics.web_events GROUP BY event_type"
```

Should show non-zero counts for `event_view`, `registration_submit`, and `contact_submit`
(the three captured analytics types) from the browsing you did in Step 3.

```bash
kubectl port-forward -n event-management svc/metabase 3001:3000
# open http://localhost:3001, log in, open the assembled dashboard
```

Confirm the dashboard's charts reflect the fresh activity (see `METABASE.md` for the exact
questions/charts to check).

## 6. Test observability

```bash
kubectl port-forward -n observability svc/kube-prometheus-stack-grafana 3000:80
# open http://localhost:3000
```

Confirm the `event-management-services` dashboard shows request rate / latency / JVM metrics
for the four backend services, and that all `ServiceMonitor` targets are up
(`kubectl get servicemonitors -n event-management`).

## If something fails

| Symptom | Likely cause | Check |
|---|---|---|
| Pod stuck `CrashLoopBackOff` | Bad config/secret | `kubectl logs <pod> -n event-management` |
| `event-service` can't invoke Lambda (logged error, reservation still succeeds) | IRSA role/ServiceAccount misconfigured | `kubectl exec -it deploy/event-service -n event-management -- env \| grep AWS` |
| Metabase query errors `Unknown table expression identifier` | Query/connection not scoped to the `analytics` database | Qualify as `analytics.web_events`, or fix the connection's "Database name" field |
| `deploy` job fails on Helm step | Stuck release from an interrupted prior run | `helm rollback kube-prometheus-stack <revision> -n observability` |
