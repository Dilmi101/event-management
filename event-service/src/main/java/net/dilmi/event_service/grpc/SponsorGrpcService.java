package net.dilmi.event_service.grpc;

import net.dilmi.event_service.service.SponsorService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class SponsorGrpcService extends SponsorServiceGrpc.SponsorServiceImplBase {

    private final SponsorService sponsorService;

    public SponsorGrpcService(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @Override
    public void getSponsor(GetSponsorRequest request, io.grpc.stub.StreamObserver<Sponsor> responseObserver) {
        UUID sponsorId = UUID.fromString(request.getSponsorId());
        sponsorService.getSponsorById(sponsorId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Sponsor not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listSponsors(ListSponsorsRequest request, io.grpc.stub.StreamObserver<ListSponsorsResponse> responseObserver) {
        ListSponsorsResponse response = ListSponsorsResponse.newBuilder()
                .addAllSponsors(sponsorService.getAllSponsors().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createSponsor(CreateSponsorRequest request, io.grpc.stub.StreamObserver<Sponsor> responseObserver) {
        net.dilmi.event_service.model.Sponsor sponsor = new net.dilmi.event_service.model.Sponsor();
        sponsor.setEventId(UUID.fromString(request.getEventId()));
        sponsor.setName(request.getName());
        sponsor.setLogoUrl(request.getLogoUrl());

        net.dilmi.event_service.model.Sponsor created = sponsorService.createSponsor(sponsor);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateSponsor(UpdateSponsorRequest request, io.grpc.stub.StreamObserver<Sponsor> responseObserver) {
        UUID sponsorId = UUID.fromString(request.getSponsorId());
        net.dilmi.event_service.model.Sponsor sponsor = new net.dilmi.event_service.model.Sponsor();
        sponsor.setEventId(UUID.fromString(request.getEventId()));
        sponsor.setName(request.getName());
        sponsor.setLogoUrl(request.getLogoUrl());

        sponsorService.updateSponsor(sponsorId, sponsor);
        sponsorService.getSponsorById(sponsorId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSponsor(DeleteSponsorRequest request, io.grpc.stub.StreamObserver<DeleteSponsorResponse> responseObserver) {
        UUID sponsorId = UUID.fromString(request.getSponsorId());
        sponsorService.deleteSponsor(sponsorId);
        responseObserver.onNext(DeleteSponsorResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Sponsor toProto(net.dilmi.event_service.model.Sponsor entity) {
        Sponsor.Builder builder = Sponsor.newBuilder()
                .setSponsorId(entity.getSponsorId().toString())
                .setEventId(entity.getEventId().toString())
                .setName(entity.getName() != null ? entity.getName() : "")
                .setLogoUrl(entity.getLogoUrl() != null ? entity.getLogoUrl() : "");

        if (entity.getCreatedAt() != null) {
            builder.setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getCreatedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
