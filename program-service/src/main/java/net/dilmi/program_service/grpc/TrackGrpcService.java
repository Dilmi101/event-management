package net.dilmi.program_service.grpc;

import net.dilmi.program_service.service.TrackService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class TrackGrpcService extends TrackServiceGrpc.TrackServiceImplBase {

    private final TrackService trackService;

    public TrackGrpcService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void getTrack(GetTrackRequest request, io.grpc.stub.StreamObserver<Track> responseObserver) {
        UUID trackId = UUID.fromString(request.getTrackId());
        trackService.getTrackById(trackId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Track not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listTracks(ListTracksRequest request, io.grpc.stub.StreamObserver<ListTracksResponse> responseObserver) {
        ListTracksResponse response = ListTracksResponse.newBuilder()
                .addAllTracks(trackService.getAllTracks().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createTrack(CreateTrackRequest request, io.grpc.stub.StreamObserver<Track> responseObserver) {
        net.dilmi.program_service.model.Track track = new net.dilmi.program_service.model.Track();
        track.setEventId(UUID.fromString(request.getEventId()));
        track.setName(request.getName());

        net.dilmi.program_service.model.Track created = trackService.createTrack(track);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateTrack(UpdateTrackRequest request, io.grpc.stub.StreamObserver<Track> responseObserver) {
        UUID trackId = UUID.fromString(request.getTrackId());
        net.dilmi.program_service.model.Track track = new net.dilmi.program_service.model.Track();
        track.setEventId(UUID.fromString(request.getEventId()));
        track.setName(request.getName());

        trackService.updateTrack(trackId, track);
        trackService.getTrackById(trackId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTrack(DeleteTrackRequest request, io.grpc.stub.StreamObserver<DeleteTrackResponse> responseObserver) {
        UUID trackId = UUID.fromString(request.getTrackId());
        trackService.deleteTrack(trackId);
        responseObserver.onNext(DeleteTrackResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Track toProto(net.dilmi.program_service.model.Track entity) {
        Track.Builder builder = Track.newBuilder()
                .setTrackId(entity.getTrackId().toString())
                .setEventId(entity.getEventId().toString())
                .setName(entity.getName());

        if (entity.getCreatedAt() != null) {
            builder.setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getCreatedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
