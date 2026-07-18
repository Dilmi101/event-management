package net.dilmi.registration_service.grpc;

import net.dilmi.registration_service.service.RegistrationService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class RegistrationGrpcService extends RegistrationServiceGrpc.RegistrationServiceImplBase {

    private final RegistrationService registrationService;

    public RegistrationGrpcService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void getRegistration(GetRegistrationRequest request, io.grpc.stub.StreamObserver<Registration> responseObserver) {
        UUID registrationId = UUID.fromString(request.getRegistrationId());
        registrationService.getRegistrationById(registrationId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Registration not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listRegistrations(ListRegistrationsRequest request, io.grpc.stub.StreamObserver<ListRegistrationsResponse> responseObserver) {
        ListRegistrationsResponse response = ListRegistrationsResponse.newBuilder()
                .addAllRegistrations(registrationService.getAllRegistrations().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createRegistration(CreateRegistrationRequest request, io.grpc.stub.StreamObserver<Registration> responseObserver) {
        net.dilmi.registration_service.model.Registration registration = new net.dilmi.registration_service.model.Registration();
        registration.setEventId(UUID.fromString(request.getEventId()));
        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setTicketCount(request.getTicketCount());

        net.dilmi.registration_service.model.Registration created = registrationService.createRegistration(registration);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateRegistration(UpdateRegistrationRequest request, io.grpc.stub.StreamObserver<Registration> responseObserver) {
        UUID registrationId = UUID.fromString(request.getRegistrationId());
        net.dilmi.registration_service.model.Registration registration = new net.dilmi.registration_service.model.Registration();
        registration.setEventId(UUID.fromString(request.getEventId()));
        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setTicketCount(request.getTicketCount());

        registrationService.updateRegistration(registrationId, registration);
        registrationService.getRegistrationById(registrationId)
                .ifPresent(updated -> responseObserver.onNext(toProto(updated)));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteRegistration(DeleteRegistrationRequest request, io.grpc.stub.StreamObserver<DeleteRegistrationResponse> responseObserver) {
        UUID registrationId = UUID.fromString(request.getRegistrationId());
        registrationService.deleteRegistration(registrationId);
        responseObserver.onNext(DeleteRegistrationResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private Registration toProto(net.dilmi.registration_service.model.Registration entity) {
        Registration.Builder builder = Registration.newBuilder()
                .setRegistrationId(entity.getRegistrationId().toString())
                .setEventId(entity.getEventId().toString())
                .setFirstName(entity.getFirstName() != null ? entity.getFirstName() : "")
                .setLastName(entity.getLastName() != null ? entity.getLastName() : "")
                .setPhone(entity.getPhone() != null ? entity.getPhone() : "")
                .setEmail(entity.getEmail() != null ? entity.getEmail() : "")
                .setTicketCount(entity.getTicketCount());

        if (entity.getRegisteredAt() != null) {
            builder.setRegisteredAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getRegisteredAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getRegisteredAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
