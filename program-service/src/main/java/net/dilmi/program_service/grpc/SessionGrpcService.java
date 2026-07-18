package net.dilmi.program_service.grpc;

import net.dilmi.program_service.service.SessionService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class SessionGrpcService extends SessionServiceGrpc.SessionServiceImplBase {

    private final SessionService sessionService;

    public SessionGrpcService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void getSession(GetSessionRequest request, io.grpc.stub.StreamObserver<Session> responseObserver) {
        UUID sessionId = UUID.fromString(request.getSessionId());
        sessionService.getSessionById(sessionId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Session not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listSessions(ListSessionsRequest request, io.grpc.stub.StreamObserver<ListSessionsResponse> responseObserver) {
        ListSessionsResponse response = ListSessionsResponse.newBuilder()
                .addAllSessions(sessionService.getAllSessions().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createSession(CreateSessionRequest request, io.grpc.stub.StreamObserver<Session> responseObserver) {
        net.dilmi.program_service.model.Session session = new net.dilmi.program_service.model.Session();
        session.setTrackId(UUID.fromString(request.getTrackId()));
        session.setEventId(UUID.fromString(request.getEventId()));
        session.setDay(request.getDay());
        session.setSessionTitle(request.getSessionTitle());
        session.setSpeakerName(request.getSpeakerName());
        session.setStartTime(LocalTime.parse(request.getStartTime()));
        session.setEndTime(LocalTime.parse(request.getEndTime()));
        session.setRoom(request.getRoom());

        net.dilmi.program_service.model.Session created = sessionService.createSession(session);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateSession(UpdateSessionRequest request, io.grpc.stub.StreamObserver<Session> responseObserver) {
        UUID sessionId = UUID.fromString(request.getSessionId());
        net.dilmi.program_service.model.Session session = new net.dilmi.program_service.model.Session();
        session.setTrackId(UUID.fromString(request.getTrackId()));
        session.setEventId(UUID.fromString(request.getEventId()));
        session.setDay(request.getDay());
        session.setSessionTitle(request.getSessionTitle());
        session.setSpeakerName(request.getSpeakerName());
        session.setStartTime(LocalTime.parse(request.getStartTime()));
        session.setEndTime(LocalTime.parse(request.getEndTime()));
        session.setRoom(request.getRoom());

        sessionService.updateSession(sessionId, session);
        sessionService.getSessionById(sessionId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSession(DeleteSessionRequest request, io.grpc.stub.StreamObserver<DeleteSessionResponse> responseObserver) {
        UUID sessionId = UUID.fromString(request.getSessionId());
        sessionService.deleteSession(sessionId);
        responseObserver.onNext(DeleteSessionResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Session toProto(net.dilmi.program_service.model.Session entity) {
        Session.Builder builder = Session.newBuilder()
                .setSessionId(entity.getSessionId().toString())
                .setTrackId(entity.getTrackId().toString())
                .setEventId(entity.getEventId().toString())
                .setDay(entity.getDay())
                .setSessionTitle(entity.getSessionTitle())
                .setSpeakerName(entity.getSpeakerName())
                .setStartTime(entity.getStartTime().toString())
                .setEndTime(entity.getEndTime().toString())
                .setRoom(entity.getRoom() != null ? entity.getRoom() : "");

        if (entity.getCreatedAt() != null) {
            builder.setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getCreatedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
