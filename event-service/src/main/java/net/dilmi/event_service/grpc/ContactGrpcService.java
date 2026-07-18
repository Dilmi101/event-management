package net.dilmi.event_service.grpc;

import net.dilmi.event_service.service.ContactMessageService;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
public class ContactGrpcService extends ContactMessageServiceGrpc.ContactMessageServiceImplBase {

    private final ContactMessageService contactMessageService;

    public ContactGrpcService(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @Override
    public void getContactMessage(GetContactMessageRequest request, io.grpc.stub.StreamObserver<ContactMessage> responseObserver) {
        UUID messageId = UUID.fromString(request.getMessageId());
        contactMessageService.getMessageById(messageId)
                .map(this::toProto)
                .ifPresentOrElse(
                        responseObserver::onNext,
                        () -> responseObserver.onError(
                                io.grpc.Status.NOT_FOUND
                                        .withDescription("Contact message not found")
                                        .asRuntimeException())
                );
        responseObserver.onCompleted();
    }

    @Override
    public void listContactMessages(ListContactMessagesRequest request, io.grpc.stub.StreamObserver<ListContactMessagesResponse> responseObserver) {
        ListContactMessagesResponse response = ListContactMessagesResponse.newBuilder()
                .addAllMessages(contactMessageService.getAllMessages().stream().map(this::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createContactMessage(CreateContactMessageRequest request, io.grpc.stub.StreamObserver<ContactMessage> responseObserver) {
        net.dilmi.event_service.model.ContactMessage message = new net.dilmi.event_service.model.ContactMessage();
        message.setName(request.getName());
        message.setEmail(request.getEmail());
        message.setMessage(request.getMessage());

        net.dilmi.event_service.model.ContactMessage created = contactMessageService.createMessage(message);
        responseObserver.onNext(toProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteContactMessage(DeleteContactMessageRequest request, io.grpc.stub.StreamObserver<DeleteContactMessageResponse> responseObserver) {
        UUID messageId = UUID.fromString(request.getMessageId());
        contactMessageService.deleteMessage(messageId);
        responseObserver.onNext(DeleteContactMessageResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private ContactMessage toProto(net.dilmi.event_service.model.ContactMessage entity) {
        ContactMessage.Builder builder = ContactMessage.newBuilder()
                .setMessageId(entity.getMessageId().toString())
                .setName(entity.getName() != null ? entity.getName() : "")
                .setEmail(entity.getEmail() != null ? entity.getEmail() : "")
                .setMessage(entity.getMessage() != null ? entity.getMessage() : "");

        if (entity.getReceivedAt() != null) {
            builder.setReceivedAt(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(entity.getReceivedAt().toEpochSecond(ZoneOffset.UTC))
                    .setNanos(entity.getReceivedAt().getNano())
                    .build());
        }

        return builder.build();
    }
}
