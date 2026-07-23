# event-management

A microservices-based platform for the "New Event" conference site (CMM707 Cloud Computing
coursework): a React frontend backed by four Spring Boot services, deployed on EKS.

Services:
- `event-service` — events, sponsors, contact messages (+ the low-seats → Lambda → S3
  notification trigger)
- `program-service` — tracks, speakers, sessions
- `registration-service` — attendee registrations
- `analytics-service` — web analytics, streamed into ClickHouse

## Documentation

- [`ARCHITECTURE.md`](./ARCHITECTURE.md) — solution/deployment architecture diagrams
- [`EKS-PROVISIONING.md`](./EKS-PROVISIONING.md) — full from-scratch cluster/AWS bootstrap
- [`RUNBOOK.md`](./RUNBOOK.md) — deploy a change and verify it end to end
- [`METABASE.md`](./METABASE.md) — connect Metabase to ClickHouse and build the dashboard

## Seeding demo data for the dashboards

`scripts/seed-dashboard-data.sh` (using `hurl/seed-dashboard-data.hurl`) creates a handful of
events and fires realistic web-analytics traffic and registrations against the **live**
cluster, so the Metabase dashboards and the low-seats → S3 notification flow have real data to
show. Safe to re-run — each run adds more traffic on top of what's already there.

**Prerequisites** (on your machine, not in the cluster):

```bash
# macOS
brew install hurl jq awscli kubernetes-cli

# Debian/Ubuntu
sudo apt install jq
# hurl: https://hurl.dev/docs/installation.html
# aws cli v2: https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html
# kubectl: https://kubernetes.io/docs/tasks/tools/
```

You also need `aws` and `kubectl` pointed at the right place:

```bash
aws sts get-caller-identity   # confirm you're authenticated to account 959740417723

aws eks update-kubeconfig --name event-management --region ap-south-1
kubectl get ns event-management   # should show "Active"
```

**Run it:**

```bash
cd /path/to/event-management   # repo root
./scripts/seed-dashboard-data.sh
```

The script figures out the ALB hostname itself — no arguments needed. It will:

1. Run `hurl/seed-dashboard-data.hurl` against the live ALB (creates events, posts
   `event_view`/`contact_submit`/`registration_submit` analytics across several simulated
   browsers, submits real registrations — including enough on one event to push it below the
   low-seats threshold).
2. Look up that low-seats event and print its remaining `seatsAvailable` (should be `8`).
3. Wait a few seconds, then check S3 for the notification the Lambda should have written
   (`s3://cwk-em-low-seats-notifications-<account-id>/notifications/<event-id>/`). If nothing's
   there yet, it prints the exact `aws logs tail` command to check the Lambda's CloudWatch logs
   instead.
4. Print row counts per `event_type` from ClickHouse, as a quick sanity check that data is
   actually landing.

**See the results in Metabase:**

```bash
kubectl port-forward -n event-management svc/metabase 3001:3000
# open http://localhost:3001
```

**If something looks wrong:**

- `hurl` step fails outright → check `kubectl get pods -n event-management` first; the seed
  script assumes all services are already deployed and healthy.
- Low-seats event's `seatsAvailable` prints but is `>= 10` → the threshold or debounce logic
  isn't behaving as expected; check `kubectl logs deploy/event-service -n event-management`.
- No S3 object and nothing in CloudWatch logs at all → `event-service`'s IRSA credentials
  aren't working; see `RUNBOOK.md`'s troubleshooting table.
