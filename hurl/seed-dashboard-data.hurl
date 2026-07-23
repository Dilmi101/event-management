# =============================================================================
# Seeds events + realistic web analytics traffic against the LIVE cluster (via
# the ALB), and pushes one event's seats below threshold to exercise the
# low-seats -> Lambda -> S3 notification flow.
#
# Feeds all 4 Metabase dashboard charts (METABASE.md):
#   1. Event volume over time, by type   -- every request below contributes
#   2. Top viewed pages                  -- event_view hits, varied per event
#   3. View-to-registration conversion   -- event_view vs registration_submit
#   4. Browser/device breakdown          -- varied `browser` per analytics hit
#
# Usage (run via scripts/seed-dashboard-data.sh, not directly -- that script
# resolves --variable base_url/run_id and does the post-seed AWS/ClickHouse
# checks this file can't do on its own):
#   hurl --test --variable base_url="http://<alb-hostname>" \
#        --variable run_id="$(date +%s)" \
#        hurl/seed-dashboard-data.hurl
# =============================================================================

# ---------------------------------------------------------------------------
# Regular events -- general browsing volume, varied per-event view counts
# ---------------------------------------------------------------------------
POST {{base_url}}/api/events
Content-Type: application/json

{
    "title": "Cloud Computing Summit 2026",
    "description": "A premier conference on cloud-native technologies, Kubernetes, and serverless computing.",
    "venueName": "Grand Convention Center",
    "venueStreet": "42 Innovation Drive",
    "venueCity": "San Francisco",
    "venuePhone": "+1-415-555-0198",
    "eventDate": "2026-11-15",
    "eventTime": "09:00:00",
    "ticketPrice": 299.99,
    "capacity": 500,
    "seatsAvailable": 500
}
HTTP 201
[Captures]
event1_id: jsonpath "$.eventId"

POST {{base_url}}/api/events
Content-Type: application/json

{
    "title": "Kubernetes Security Workshop",
    "description": "Hands-on workshop covering zero-trust architectures and cluster hardening.",
    "venueName": "Tech Hub Downtown",
    "venueStreet": "18 Container Lane",
    "venueCity": "Austin",
    "venuePhone": "+1-512-555-0110",
    "eventDate": "2026-10-05",
    "eventTime": "10:00:00",
    "ticketPrice": 149.00,
    "capacity": 200,
    "seatsAvailable": 200
}
HTTP 201
[Captures]
event2_id: jsonpath "$.eventId"

POST {{base_url}}/api/events
Content-Type: application/json

{
    "title": "Serverless Architecture Conference",
    "description": "Deep dive into event-driven, serverless, and function-as-a-service patterns.",
    "venueName": "Bayview Expo Hall",
    "venueStreet": "7 Function Way",
    "venueCity": "Seattle",
    "venuePhone": "+1-206-555-0133",
    "eventDate": "2026-09-20",
    "eventTime": "09:30:00",
    "ticketPrice": 199.50,
    "capacity": 350,
    "seatsAvailable": 350
}
HTTP 201
[Captures]
event3_id: jsonpath "$.eventId"

POST {{base_url}}/api/events
Content-Type: application/json

{
    "title": "DevOps Engineering Meetup",
    "description": "Community meetup on GitOps, CI/CD pipelines, and platform engineering.",
    "venueName": "Riverside Community Center",
    "venueStreet": "3 Pipeline Ave",
    "venueCity": "Denver",
    "venuePhone": "+1-303-555-0177",
    "eventDate": "2026-08-12",
    "eventTime": "18:00:00",
    "ticketPrice": 0,
    "capacity": 100,
    "seatsAvailable": 100
}
HTTP 201
[Captures]
event4_id: jsonpath "$.eventId"

# ---------------------------------------------------------------------------
# Low-seats demo event -- small capacity, deliberately pushed below threshold
# ---------------------------------------------------------------------------
POST {{base_url}}/api/events
Content-Type: application/json

{
    "title": "Low Seats Demo Event {{run_id}}",
    "description": "Seeded to demonstrate the low-seats notification pipeline.",
    "venueName": "Small Room",
    "venueStreet": "1 Demo St",
    "venueCity": "Demo City",
    "venuePhone": "+1-000-555-0100",
    "eventDate": "2026-08-01",
    "eventTime": "12:00:00",
    "ticketPrice": 10.00,
    "capacity": 20,
    "seatsAvailable": 20
}
HTTP 201
[Captures]
low_seats_event_id: jsonpath "$.eventId"

# ---------------------------------------------------------------------------
# event_view analytics -- 8 / 5 / 3 / 6 views across the 4 regular events,
# rotating browsers so the pie chart isn't a single wedge
# ---------------------------------------------------------------------------
POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event1_id}}","pageTitle":"Cloud Computing Summit 2026","sessionId":"{{newUuid}}","browser":"Edge"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event2_id}}","pageTitle":"Kubernetes Security Workshop","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event2_id}}","pageTitle":"Kubernetes Security Workshop","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event2_id}}","pageTitle":"Kubernetes Security Workshop","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event2_id}}","pageTitle":"Kubernetes Security Workshop","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event2_id}}","pageTitle":"Kubernetes Security Workshop","sessionId":"{{newUuid}}","browser":"Edge"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event3_id}}","pageTitle":"Serverless Architecture Conference","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event3_id}}","pageTitle":"Serverless Architecture Conference","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event3_id}}","pageTitle":"Serverless Architecture Conference","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"event_view","page":"{{event4_id}}","pageTitle":"DevOps Engineering Meetup","sessionId":"{{newUuid}}","browser":"Edge"}
HTTP 200

# ---------------------------------------------------------------------------
# contact_submit analytics -- matches ContactSection.tsx (page is always the
# literal string "contact", no pageTitle)
# ---------------------------------------------------------------------------
POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"contact_submit","page":"contact","pageTitle":"","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"contact_submit","page":"contact","pageTitle":"","sessionId":"{{newUuid}}","browser":"Firefox"}
HTTP 200

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"contact_submit","page":"contact","pageTitle":"","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

# A couple of real contact messages too, for completeness
POST {{base_url}}/api/contact
Content-Type: application/json
{"name":"Dana Lee","email":"dana.lee@example.com","message":"Is there a student discount for the Cloud Computing Summit?"}
HTTP 201

POST {{base_url}}/api/contact
Content-Type: application/json
{"name":"Omar Farouk","email":"omar.farouk@example.com","message":"Can you share the workshop's prerequisite reading list?"}
HTTP 201

# ---------------------------------------------------------------------------
# registration_submit analytics + real registrations -- deliberately a
# partial conversion (fewer registrations than views) on events 1 and 2, so
# the conversion chart shows a realistic ratio rather than 100%
# ---------------------------------------------------------------------------
POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"registration_submit","page":"{{event1_id}}","pageTitle":"","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{event1_id}}","firstName":"Alice","lastName":"Johnson","phone":"+1-415-555-0101","email":"alice.johnson@example.com","ticketCount":2}
HTTP 201

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"registration_submit","page":"{{event1_id}}","pageTitle":"","sessionId":"{{newUuid}}","browser":"Safari"}
HTTP 200

POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{event1_id}}","firstName":"Bob","lastName":"Smith","phone":"+1-415-555-0102","email":"bob.smith@example.com","ticketCount":1}
HTTP 201

POST {{base_url}}/api/analytics
Content-Type: application/json
{"eventType":"registration_submit","page":"{{event2_id}}","pageTitle":"","sessionId":"{{newUuid}}","browser":"Chrome"}
HTTP 200

POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{event2_id}}","firstName":"Carol","lastName":"Williams","phone":"+1-512-555-0103","email":"carol.williams@example.com","ticketCount":3}
HTTP 201

# ---------------------------------------------------------------------------
# Low-seats flow: 3 registrations of 4 tickets each (20 -> 16 -> 12 -> 8).
# The 3rd call crosses the default threshold (10) -- and only the 3rd, which
# also exercises the debounce (no double notification across the 3 calls).
# ---------------------------------------------------------------------------
POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{low_seats_event_id}}","firstName":"Eve","lastName":"Adams","phone":"+1-000-555-0201","email":"eve.adams@example.com","ticketCount":4}
HTTP 201

POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{low_seats_event_id}}","firstName":"Frank","lastName":"Nguyen","phone":"+1-000-555-0202","email":"frank.nguyen@example.com","ticketCount":4}
HTTP 201

POST {{base_url}}/api/registrations
Content-Type: application/json
{"eventId":"{{low_seats_event_id}}","firstName":"Grace","lastName":"Kim","phone":"+1-000-555-0203","email":"grace.kim@example.com","ticketCount":4}
HTTP 201

# ---------------------------------------------------------------------------
# Verify: low-seats event is actually below threshold now
# ---------------------------------------------------------------------------
GET {{base_url}}/api/events/{{low_seats_event_id}}
HTTP 200
[Asserts]
jsonpath "$.seatsAvailable" == 8
