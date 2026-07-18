# Plan: Integrate Events Table Schema into event-service

**Goal:** Implement the full data layer, REST API, and gRPC service for the `events` table schema in the event-service Spring Boot project.  
**Research input:** None  
**Date:** 2026-07-18

---

## Summary

The event-service is a freshly scaffolded Spring Boot 4.1.0 project with Spring Data JDBC, Liquibase, and gRPC dependencies declared but no business logic implemented. This plan adds: a PostgreSQL driver, Liquibase migration for the `events` table, a Spring Data JDBC entity, repository, service layer, REST controller, and gRPC service with proto definitions. Environment variables are used for datasource configuration.

## Changes

### 1. `event-service/build.gradle` (modify)

**Why:** Add the missing PostgreSQL JDBC driver dependency and Spring Boot Web starter for REST controller support.

```diff
--- a/event-service/build.gradle
+++ b/event-service/build.gradle
@@ -21,6 +21,8 @@
 dependencies {
+	implementation 'org.springframework.boot:spring-boot-starter-web'
 	implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
 	implementation 'org.springframework.boot:spring-boot-starter-grpc-client'
 	implementation 'org.springframework.boot:spring-boot-starter-grpc-server'
 	implementation 'org.springframework.boot:spring-boot-starter-liquibase'
+	implementation 'org.postgresql:postgresql'
 	testImplementation 'org.springframework.boot:spring-boot-starter-data-jdbc-test'
```

### 2. `event-service/src/main/resources/application.yaml` (modify)

**Why:** Configure the PostgreSQL datasource via environment variables, Liquibase, server port, and gRPC server port.

```diff
--- a/event-service/src/main/resources/application.yaml
+++ b/event-service/src/main/resources/application.yaml
@@ -1,3 +1,20 @@
 spring:
   application:
     name: event-service
+  datasource:
+    url: ${DB_URL:jdbc:postgresql://localhost:5432/eventdb}
+    username: ${DB_USERNAME:postgres}
+    password: ${DB_PASSWORD:postgres}
+    driver-class-name: org.postgresql.Driver
+  liquibase:
+    change-log: classpath:db/changelog/db.changelog-master.yaml
+
+server:
+  port: ${SERVER_PORT:8081}
+
+grpc:
+  server:
+    port: ${GRPC_SERVER_PORT:9091}
```

### 3. `event-service/src/main/resources/db/changelog/db.changelog-master.yaml` (new file)

**Why:** Liquibase master changelog that includes the events table migration.

```yaml
databaseChangeLog:
  - include:
      file: db/changelog/001-create-events-table.yaml
```

### 4. `event-service/src/main/resources/db/changelog/001-create-events-table.yaml` (new file)

**Why:** Liquibase changeset that creates the `events` table matching the provided SQL schema, using PostgreSQL-compatible syntax.

```yaml
databaseChangeLog:
  - changeSet:
      id: 001-create-events-table
      author: dilmi
      changes:
        - createTable:
            tableName: events
            columns:
              - column:
                  name: event_id
                  type: uuid
                  defaultValueComputed: gen_random_uuid()
                  constraints:
                    primaryKey: true
                    nullable: false
              - column:
                  name: title
                  type: varchar(255)
                  constraints:
                    nullable: false
              - column:
                  name: venue
                  type: varchar(255)
                  constraints:
                    nullable: false
              - column:
                  name: event_date
                  type: date
                  constraints:
                    nullable: false
              - column:
                  name: event_time
                  type: time
                  constraints:
                    nullable: false
              - column:
                  name: ticket_price
                  type: decimal(10,2)
                  constraints:
                    nullable: false
              - column:
                  name: capacity
                  type: int
                  constraints:
                    nullable: false
              - column:
                  name: seats_available
                  type: int
                  constraints:
                    nullable: false
              - column:
                  name: created_at
                  type: timestamp
                  defaultValueComputed: current_timestamp
              - column:
                  name: updated_at
                  type: timestamp
                  defaultValueComputed: current_timestamp
        - sql:
            sql: ALTER TABLE events ADD CONSTRAINT chk_seats CHECK (seats_available >= 0)
        - sql:
            sql: ALTER TABLE events ADD CONSTRAINT chk_capacity CHECK (capacity > 0)
        - sql:
            sql: ALTER TABLE events ADD CONSTRAINT chk_seats_capacity CHECK (seats_available <= capacity)
```

### 5. `event-service/src/main/java/net/dilmi/event_service/model/Event.java` (new file)

**Why:** Spring Data JDBC entity class mapping to the `events` table with all columns as fields and proper annotations.

```java
package net.dilmi.event_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Table("events")
public class Event {

    @Id
    private UUID eventId;
    private String title;
    private String venue;

    @Column("event_date")
    private LocalDate eventDate;

    @Column("event_time")
    private LocalTime eventTime;

    @Column("ticket_price")
    private BigDecimal ticketPrice;

    private Integer capacity;

    @Column("seats_available")
    private Integer seatsAvailable;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Event() {}

    public Event(UUID eventId, String title, String venue, LocalDate eventDate,
                 LocalTime eventTime, BigDecimal ticketPrice, Integer capacity,
                 Integer seatsAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.eventId = eventId;
        this.title = title;
        this.venue = venue;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.seatsAvailable = seatsAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getEventTime() { return eventTime; }
    public void setEventTime(LocalTime eventTime) { this.eventTime = eventTime; }

    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(Integer seatsAvailable) { this.seatsAvailable = seatsAvailable; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
```

### 6. `event-service/src/main/java/net/dilmi/event_service/repository/EventRepository.java` (new file)

**Why:** Spring Data JDBC repository interface providing CRUD operations for the Event entity.

```java
package net.dilmi.event_service.repository;

import net.dilmi.event_service.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {
}
```

### 7. `event-service/src/main/java/net/dilmi/event_service/service/EventService.java` (new file)

**Why:** Business logic layer that wraps the repository, handles `created_at`/`updated_at` timestamps, and provides the operations consumed by both the REST controller and gRPC service.

```java
package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    public Optional<Event> getEventById(UUID eventId) {
        return eventRepository.findById(eventId);
    }

    public Event createEvent(Event event) {
        event.setEventId(UUID.randomUUID());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public Event updateEvent(UUID eventId, Event updatedEvent) {
        updatedEvent.setEventId(eventId);
        updatedEvent.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(updatedEvent);
    }

    public void deleteEvent(UUID eventId) {
        eventRepository.deleteById(eventId);
    }
}
```

### 8. `event-service/src/main/java/net/dilmi/event_service/controller/EventController.java` (new file)

**Why:** REST controller exposing CRUD endpoints for the events resource at `/api/events`.

```java
package net.dilmi.event_service.controller;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID eventId) {
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventId, @RequestBody Event event) {
        eventService.updateEvent(eventId, event);
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
```

### 9. `event-service/src/main/proto/event_service.proto` (new file)

**Why:** gRPC service definition for the events domain, defining the Event message and CRUD RPCs. The `com.google.protobuf` Gradle plugin will generate Java classes from this file.

```protobuf
syntax = "proto3";

option java_multiple_files = true;
option java_package = "net.dilmi.event_service.grpc";
option java_outer_classname = "EventServiceProto";

package event_service;

import "google/protobuf/timestamp.proto";

message Event {
  string event_id = 1;
  string title = 2;
  string venue = 3;
  string event_date = 4;
  string event_time = 5;
  double ticket_price = 6;
  int32 capacity = 7;
  int32 seats_available = 8;
  google.protobuf.Timestamp created_at = 9;
  google.protobuf.Timestamp updated_at = 10;
}

message GetEventRequest {
  string event_id = 1;
}

message ListEventsRequest {}

message ListEventsResponse {
  repeated Event events = 1;
}

message CreateEventRequest {
  string title = 1;
  string venue = 2;
  string event_date = 3;
  string event_time = 4;
  double ticket_price = 5;
  int32 capacity = 6;
  int32 seats_available = 7;
}

message UpdateEventRequest {
  string event_id = 1;
  string title = 2;
  string venue = 3;
  string event_date = 4;
  string event_time = 5;
  double ticket_price = 6;
  int32 capacity = 7;
  int32 seats_available = 8;
}

message DeleteEventRequest {
  string event_id = 1;
}

message DeleteEventResponse {
  bool success = 1;
}

service EventService {
  rpc GetEvent(GetEventRequest) returns (Event);
  rpc ListEvents(ListEventsRequest) returns (ListEventsResponse);
  rpc CreateEvent(CreateEventRequest) returns (Event);
  rpc UpdateEvent(UpdateEventRequest) returns (Event);
  rpc DeleteEvent(DeleteEventRequest) returns (DeleteEventResponse);
}
```

### 10. `event-service/src/main/java/net/dilmi/event_service/grpc/EventGrpcService.java` (new file)

**Why:** gRPC service implementation that delegates to the same `EventService` used by the REST controller, converting between proto messages and domain objects.

```java
package net.dilmi.event_service.grpc;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.service.EventService;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class EventGrpcService extends EventServiceGrpc.EventServiceImplBase {

    private final EventService eventService;

    public EventGrpcService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void getEvent(GetEventRequest request, io.grpc.stub.StreamObserver<Event> responseObserver) {
        UUID eventId = UUID.fromString(request.getEventId());
        eventService.getEventById(eventId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Event not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listEvents(ListEventsRequest request, io.grpc.stub.StreamObserver<ListEventsResponse> responseObserver) {
        ListEventsResponse response = ListEventsResponse.newBuilder()
                .addAllEvents(eventService.getAllEvents().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createEvent(CreateEventRequest request, io.grpc.stub.StreamObserver<Event> responseObserver) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setVenue(request.getVenue());
        event.setEventDate(LocalDate.parse(request.getEventDate()));
        event.setEventTime(LocalTime.parse(request.getEventTime()));
        event.setTicketPrice(BigDecimal.valueOf(request.getTicketPrice()));
        event.setCapacity(request.getCapacity());
        event.setSeatsAvailable(request.getSeatsAvailable());

        Event created = eventService.createEvent(event);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateEvent(UpdateEventRequest request, io.grpc.stub.StreamObserver<Event> responseObserver) {
        UUID eventId = UUID.fromString(request.getEventId());
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setVenue(request.getVenue());
        event.setEventDate(LocalDate.parse(request.getEventDate()));
        event.setEventTime(LocalTime.parse(request.getEventTime()));
        event.setTicketPrice(BigDecimal.valueOf(request.getTicketPrice()));
        event.setCapacity(request.getCapacity());
        event.setSeatsAvailable(request.getSeatsAvailable());

        eventService.updateEvent(eventId, event);
        eventService.getEventById(eventId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEvent(DeleteEventRequest request, io.grpc.stub.StreamObserver<DeleteEventResponse> responseObserver) {
        UUID eventId = UUID.fromString(request.getEventId());
        eventService.deleteEvent(eventId);
        responseObserver.onNext(DeleteEventResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Event toProto(net.dilmi.event_service.model.Event entity) {
        Event.Builder builder = Event.newBuilder()
                .setEventId(entity.getEventId().toString())
                .setTitle(entity.getTitle())
                .setVenue(entity.getVenue())
                .setEventDate(entity.getEventDate().toString())
                .setEventTime(entity.getEventTime().toString())
                .setTicketPrice(entity.getTicketPrice().doubleValue())
                .setCapacity(entity.getCapacity())
                .setSeatsAvailable(entity.getSeatsAvailable());

        if (entity.getCreatedAt() != null) {
            builder.setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getCreatedAt().getNano())
                    .build());
        }
        if (entity.getUpdatedAt() != null) {
            builder.setUpdatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getUpdatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getUpdatedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
```

---

## Files not changing

- `EventServiceApplication.java` — `@SpringBootApplication` scans `net.dilmi.event_service` and sub-packages automatically.
- `EventServiceApplicationTests.java` — Already excludes DataSource and Liquibase auto-config; no changes needed.
- `src/test/resources/application.yaml` — No changes needed.
- `.gitignore`, `.gitattributes`, `settings.gradle`, `gradle/` — No changes needed.

---

## Open questions

1. **gRPC service base class name** — Generated stub class name depends on the `service` name in the proto file. With `service EventService`, the generated class is `EventServiceGrpc.EventServiceImplBase`. Verify after proto compilation.
2. **Spring gRPC annotation** — `@GrpcService` is from `org.springframework.grpc.server.service`. Verify the exact import against Spring gRPC 1.x docs.
3. **Proto file compilation** — The `com.google.protobuf` Gradle plugin should auto-compile proto files, but the `protobuf` block may need configuration for the gRPC plugin (`protobuf-gradle-plugin` with `grpc` option). May need to add `protobuf { ... }` block to `build.gradle` if compilation fails.

---
*This document is a plan only. No changes have been applied.*
