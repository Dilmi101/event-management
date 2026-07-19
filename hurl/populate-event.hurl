# =============================================================================
# Hurl script to populate an event with sponsors, tracks, sessions, speakers,
# and registrations across the three microservices.
#
# Prerequisites: all three services running:
#   event-service:8081, program-service:8082, registration-service:8083
#
# Usage:
#   hurl --test hurl/populate-event.hurl
# =============================================================================

# ---------------------------------------------------------------------------
# 1. Create the main event
# ---------------------------------------------------------------------------
POST http://localhost:8081/api/events
Content-Type: application/json

{
    "title": "Cloud Computing Summit 2026",
    "description": "A premier conference bringing together cloud architects, DevOps engineers, and platform builders to explore the latest in cloud-native technologies, Kubernetes, and serverless computing.",
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
event_id: jsonpath "$.eventId"

# ---------------------------------------------------------------------------
# 2. Create sponsors for the event
# ---------------------------------------------------------------------------
POST http://localhost:8081/api/sponsors
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "CloudNative Inc.",
    "logoUrl": "https://example.com/logos/cloudnative.png"
}
HTTP 201
[Captures]
sponsor_1_id: jsonpath "$.sponsorId"

POST http://localhost:8081/api/sponsors
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "DataStream Labs",
    "logoUrl": "https://example.com/logos/datastream.png"
}
HTTP 201
[Captures]
sponsor_2_id: jsonpath "$.sponsorId"

POST http://localhost:8081/api/sponsors
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "SecureNet Solutions",
    "logoUrl": "https://example.com/logos/securenet.png"
}
HTTP 201
[Captures]
sponsor_3_id: jsonpath "$.sponsorId"

# ---------------------------------------------------------------------------
# 3. Create tracks for the event
# ---------------------------------------------------------------------------
POST http://localhost:8082/api/tracks
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Kubernetes & Orchestration"
}
HTTP 201
[Captures]
track_1_id: jsonpath "$.trackId"

POST http://localhost:8082/api/tracks
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Serverless & Event-Driven Architecture"
}
HTTP 201
[Captures]
track_2_id: jsonpath "$.trackId"

POST http://localhost:8082/api/tracks
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Cloud Security & Compliance"
}
HTTP 201
[Captures]
track_3_id: jsonpath "$.trackId"

# ---------------------------------------------------------------------------
# 4. Create speakers for the event
# ---------------------------------------------------------------------------
POST http://localhost:8082/api/speakers
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Dr. Sarah Chen",
    "role": "Keynote Speaker",
    "bio": "Principal Engineer at CloudNative Inc. with 15 years of experience in distributed systems and cloud infrastructure.",
    "photoUrl": "https://example.com/photos/sarah-chen.jpg"
}
HTTP 201
[Captures]
speaker_1_id: jsonpath "$.speakerId"

POST http://localhost:8082/api/speakers
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Marcus Rivera",
    "role": "Workshop Lead",
    "bio": "Senior DevOps Architect specializing in Kubernetes platform engineering and GitOps workflows.",
    "photoUrl": "https://example.com/photos/marcus-rivera.jpg"
}
HTTP 201
[Captures]
speaker_2_id: jsonpath "$.speakerId"

POST http://localhost:8082/api/speakers
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "name": "Priya Patel",
    "role": "Panelist",
    "bio": "Cloud Security Lead at SecureNet Solutions, focusing on zero-trust architectures and cloud compliance frameworks.",
    "photoUrl": "https://example.com/photos/priya-patel.jpg"
}
HTTP 201
[Captures]
speaker_3_id: jsonpath "$.speakerId"

# ---------------------------------------------------------------------------
# 5. Create sessions under tracks
# ---------------------------------------------------------------------------
POST http://localhost:8082/api/sessions
Content-Type: application/json

{
    "trackId": "{{track_1_id}}",
    "eventId": "{{event_id}}",
    "day": 1,
    "sessionTitle": "Kubernetes at Scale: Lessons from Production",
    "speakerName": "Dr. Sarah Chen",
    "startTime": "09:00:00",
    "endTime": "10:30:00",
    "room": "Main Hall A"
}
HTTP 201
[Captures]
session_1_id: jsonpath "$.sessionId"

POST http://localhost:8082/api/sessions
Content-Type: application/json

{
    "trackId": "{{track_1_id}}",
    "eventId": "{{event_id}}",
    "day": 1,
    "sessionTitle": "GitOps Workflows with ArgoCD",
    "speakerName": "Marcus Rivera",
    "startTime": "11:00:00",
    "endTime": "12:30:00",
    "room": "Main Hall A"
}
HTTP 201
[Captures]
session_2_id: jsonpath "$.sessionId"

POST http://localhost:8082/api/sessions
Content-Type: application/json

{
    "trackId": "{{track_2_id}}",
    "eventId": "{{event_id}}",
    "day": 1,
    "sessionTitle": "Building Event-Driven Systems with Kafka",
    "speakerName": "Dr. Sarah Chen",
    "startTime": "14:00:00",
    "endTime": "15:30:00",
    "room": "Breakout Room B"
}
HTTP 201
[Captures]
session_3_id: jsonpath "$.sessionId"

POST http://localhost:8082/api/sessions
Content-Type: application/json

{
    "trackId": "{{track_3_id}}",
    "eventId": "{{event_id}}",
    "day": 2,
    "sessionTitle": "Zero Trust Architecture in Multi-Cloud Environments",
    "speakerName": "Priya Patel",
    "startTime": "09:00:00",
    "endTime": "10:30:00",
    "room": "Breakout Room C"
}
HTTP 201
[Captures]
session_4_id: jsonpath "$.sessionId"

POST http://localhost:8082/api/sessions
Content-Type: application/json

{
    "trackId": "{{track_2_id}}",
    "eventId": "{{event_id}}",
    "day": 2,
    "sessionTitle": "Serverless Patterns for Microservices",
    "speakerName": "Marcus Rivera",
    "startTime": "11:00:00",
    "endTime": "12:30:00",
    "room": "Breakout Room B"
}
HTTP 201
[Captures]
session_5_id: jsonpath "$.sessionId"

# ---------------------------------------------------------------------------
# 6. Create registrations for the event
# ---------------------------------------------------------------------------
POST http://localhost:8083/api/registrations
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "firstName": "Alice",
    "lastName": "Johnson",
    "phone": "+1-415-555-0101",
    "email": "alice.johnson@example.com",
    "ticketCount": 2
}
HTTP 201
[Captures]
registration_1_id: jsonpath "$.registrationId"

POST http://localhost:8083/api/registrations
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "firstName": "Bob",
    "lastName": "Smith",
    "phone": "+1-415-555-0102",
    "email": "bob.smith@example.com",
    "ticketCount": 1
}
HTTP 201
[Captures]
registration_2_id: jsonpath "$.registrationId"

POST http://localhost:8083/api/registrations
Content-Type: application/json

{
    "eventId": "{{event_id}}",
    "firstName": "Carol",
    "lastName": "Williams",
    "phone": "+1-415-555-0103",
    "email": "carol.williams@example.com",
    "ticketCount": 3
}
HTTP 201
[Captures]
registration_3_id: jsonpath "$.registrationId"

# ---------------------------------------------------------------------------
# 7. Verify: fetch created event and check fields
# ---------------------------------------------------------------------------
GET http://localhost:8081/api/events/{{event_id}}
HTTP 200
[Asserts]
jsonpath "$.title" == "Cloud Computing Summit 2026"
jsonpath "$.description" contains "cloud-native"
jsonpath "$.venueName" == "Grand Convention Center"
jsonpath "$.venueCity" == "San Francisco"
jsonpath "$.eventDate" == "2026-11-15"
jsonpath "$.eventTime" == "09:00:00"
jsonpath "$.ticketPrice" == 299.99
jsonpath "$.capacity" == 500
jsonpath "$.seatsAvailable" == 494

# ---------------------------------------------------------------------------
# 8. Verify: check counts on all list endpoints
# ---------------------------------------------------------------------------

GET http://localhost:8081/api/sponsors
HTTP 200
[Asserts]
jsonpath "$[?(@.eventId == '{{event_id}}')]" count == 3

GET http://localhost:8082/api/tracks
HTTP 200
[Asserts]
jsonpath "$[?(@.eventId == '{{event_id}}')]" count == 3

GET http://localhost:8082/api/sessions
HTTP 200
[Asserts]
jsonpath "$[?(@.eventId == '{{event_id}}')]" count == 5

GET http://localhost:8082/api/speakers
HTTP 200
[Asserts]
jsonpath "$[?(@.eventId == '{{event_id}}')]" count == 3

GET http://localhost:8083/api/registrations
HTTP 200
[Asserts]
jsonpath "$[?(@.eventId == '{{event_id}}')]" count == 3
