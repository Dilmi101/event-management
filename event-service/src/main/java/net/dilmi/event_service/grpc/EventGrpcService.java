package net.dilmi.event_service.grpc;

import net.dilmi.event_service.service.EventService;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        net.dilmi.event_service.model.Event event = new net.dilmi.event_service.model.Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenueName(request.getVenueName());
        event.setVenueStreet(request.getVenueStreet());
        event.setVenueCity(request.getVenueCity());
        event.setVenuePhone(request.getVenuePhone());
        event.setEventDate(LocalDate.parse(request.getEventDate()));
        event.setEventTime(LocalTime.parse(request.getEventTime()));
        event.setTicketPrice(BigDecimal.valueOf(request.getTicketPrice()));
        event.setCapacity(request.getCapacity());
        event.setSeatsAvailable(request.getSeatsAvailable());

        net.dilmi.event_service.model.Event created = eventService.createEvent(event);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateEvent(UpdateEventRequest request, io.grpc.stub.StreamObserver<Event> responseObserver) {
        UUID eventId = UUID.fromString(request.getEventId());
        net.dilmi.event_service.model.Event event = new net.dilmi.event_service.model.Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenueName(request.getVenueName());
        event.setVenueStreet(request.getVenueStreet());
        event.setVenueCity(request.getVenueCity());
        event.setVenuePhone(request.getVenuePhone());
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
                .setTitle(entity.getTitle() != null ? entity.getTitle() : "")
                .setDescription(entity.getDescription() != null ? entity.getDescription() : "")
                .setVenueName(entity.getVenueName() != null ? entity.getVenueName() : "")
                .setVenueStreet(entity.getVenueStreet() != null ? entity.getVenueStreet() : "")
                .setVenueCity(entity.getVenueCity() != null ? entity.getVenueCity() : "")
                .setVenuePhone(entity.getVenuePhone() != null ? entity.getVenuePhone() : "")
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
