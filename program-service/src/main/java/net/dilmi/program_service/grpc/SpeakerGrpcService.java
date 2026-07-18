package net.dilmi.program_service.grpc;

import net.dilmi.program_service.service.SpeakerService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class SpeakerGrpcService extends SpeakerServiceGrpc.SpeakerServiceImplBase {

    private final SpeakerService speakerService;

    public SpeakerGrpcService(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @Override
    public void getSpeaker(GetSpeakerRequest request, io.grpc.stub.StreamObserver<Speaker> responseObserver) {
        UUID speakerId = UUID.fromString(request.getSpeakerId());
        speakerService.getSpeakerById(speakerId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Speaker not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listSpeakers(ListSpeakersRequest request, io.grpc.stub.StreamObserver<ListSpeakersResponse> responseObserver) {
        ListSpeakersResponse response = ListSpeakersResponse.newBuilder()
                .addAllSpeakers(speakerService.getAllSpeakers().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createSpeaker(CreateSpeakerRequest request, io.grpc.stub.StreamObserver<Speaker> responseObserver) {
        net.dilmi.program_service.model.Speaker speaker = new net.dilmi.program_service.model.Speaker();
        speaker.setEventId(UUID.fromString(request.getEventId()));
        speaker.setName(request.getName());
        speaker.setRole(request.getRole());
        speaker.setBio(request.getBio());
        speaker.setPhotoUrl(request.getPhotoUrl());

        net.dilmi.program_service.model.Speaker created = speakerService.createSpeaker(speaker);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateSpeaker(UpdateSpeakerRequest request, io.grpc.stub.StreamObserver<Speaker> responseObserver) {
        UUID speakerId = UUID.fromString(request.getSpeakerId());
        net.dilmi.program_service.model.Speaker speaker = new net.dilmi.program_service.model.Speaker();
        speaker.setEventId(UUID.fromString(request.getEventId()));
        speaker.setName(request.getName());
        speaker.setRole(request.getRole());
        speaker.setBio(request.getBio());
        speaker.setPhotoUrl(request.getPhotoUrl());

        speakerService.updateSpeaker(speakerId, speaker);
        speakerService.getSpeakerById(speakerId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSpeaker(DeleteSpeakerRequest request, io.grpc.stub.StreamObserver<DeleteSpeakerResponse> responseObserver) {
        UUID speakerId = UUID.fromString(request.getSpeakerId());
        speakerService.deleteSpeaker(speakerId);
        responseObserver.onNext(DeleteSpeakerResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Speaker toProto(net.dilmi.program_service.model.Speaker entity) {
        Speaker.Builder builder = Speaker.newBuilder()
                .setSpeakerId(entity.getSpeakerId().toString())
                .setEventId(entity.getEventId().toString())
                .setName(entity.getName() != null ? entity.getName() : "")
                .setRole(entity.getRole() != null ? entity.getRole() : "")
                .setBio(entity.getBio() != null ? entity.getBio() : "")
                .setPhotoUrl(entity.getPhotoUrl() != null ? entity.getPhotoUrl() : "");

        if (entity.getCreatedAt() != null) {
            builder.setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getCreatedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
