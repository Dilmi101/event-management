package net.dilmi.event_service.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class ContactMessageServiceGrpc {

  private ContactMessageServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "event_service.ContactMessageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetContactMessageRequest,
      net.dilmi.event_service.grpc.ContactMessage> getGetContactMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetContactMessage",
      requestType = net.dilmi.event_service.grpc.GetContactMessageRequest.class,
      responseType = net.dilmi.event_service.grpc.ContactMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetContactMessageRequest,
      net.dilmi.event_service.grpc.ContactMessage> getGetContactMessageMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetContactMessageRequest, net.dilmi.event_service.grpc.ContactMessage> getGetContactMessageMethod;
    if ((getGetContactMessageMethod = ContactMessageServiceGrpc.getGetContactMessageMethod) == null) {
      synchronized (ContactMessageServiceGrpc.class) {
        if ((getGetContactMessageMethod = ContactMessageServiceGrpc.getGetContactMessageMethod) == null) {
          ContactMessageServiceGrpc.getGetContactMessageMethod = getGetContactMessageMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.GetContactMessageRequest, net.dilmi.event_service.grpc.ContactMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetContactMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.GetContactMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ContactMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ContactMessageServiceMethodDescriptorSupplier("GetContactMessage"))
              .build();
        }
      }
    }
    return getGetContactMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListContactMessagesRequest,
      net.dilmi.event_service.grpc.ListContactMessagesResponse> getListContactMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListContactMessages",
      requestType = net.dilmi.event_service.grpc.ListContactMessagesRequest.class,
      responseType = net.dilmi.event_service.grpc.ListContactMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListContactMessagesRequest,
      net.dilmi.event_service.grpc.ListContactMessagesResponse> getListContactMessagesMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListContactMessagesRequest, net.dilmi.event_service.grpc.ListContactMessagesResponse> getListContactMessagesMethod;
    if ((getListContactMessagesMethod = ContactMessageServiceGrpc.getListContactMessagesMethod) == null) {
      synchronized (ContactMessageServiceGrpc.class) {
        if ((getListContactMessagesMethod = ContactMessageServiceGrpc.getListContactMessagesMethod) == null) {
          ContactMessageServiceGrpc.getListContactMessagesMethod = getListContactMessagesMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.ListContactMessagesRequest, net.dilmi.event_service.grpc.ListContactMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListContactMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListContactMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListContactMessagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ContactMessageServiceMethodDescriptorSupplier("ListContactMessages"))
              .build();
        }
      }
    }
    return getListContactMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateContactMessageRequest,
      net.dilmi.event_service.grpc.ContactMessage> getCreateContactMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateContactMessage",
      requestType = net.dilmi.event_service.grpc.CreateContactMessageRequest.class,
      responseType = net.dilmi.event_service.grpc.ContactMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateContactMessageRequest,
      net.dilmi.event_service.grpc.ContactMessage> getCreateContactMessageMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateContactMessageRequest, net.dilmi.event_service.grpc.ContactMessage> getCreateContactMessageMethod;
    if ((getCreateContactMessageMethod = ContactMessageServiceGrpc.getCreateContactMessageMethod) == null) {
      synchronized (ContactMessageServiceGrpc.class) {
        if ((getCreateContactMessageMethod = ContactMessageServiceGrpc.getCreateContactMessageMethod) == null) {
          ContactMessageServiceGrpc.getCreateContactMessageMethod = getCreateContactMessageMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.CreateContactMessageRequest, net.dilmi.event_service.grpc.ContactMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateContactMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.CreateContactMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ContactMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ContactMessageServiceMethodDescriptorSupplier("CreateContactMessage"))
              .build();
        }
      }
    }
    return getCreateContactMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteContactMessageRequest,
      net.dilmi.event_service.grpc.DeleteContactMessageResponse> getDeleteContactMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteContactMessage",
      requestType = net.dilmi.event_service.grpc.DeleteContactMessageRequest.class,
      responseType = net.dilmi.event_service.grpc.DeleteContactMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteContactMessageRequest,
      net.dilmi.event_service.grpc.DeleteContactMessageResponse> getDeleteContactMessageMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteContactMessageRequest, net.dilmi.event_service.grpc.DeleteContactMessageResponse> getDeleteContactMessageMethod;
    if ((getDeleteContactMessageMethod = ContactMessageServiceGrpc.getDeleteContactMessageMethod) == null) {
      synchronized (ContactMessageServiceGrpc.class) {
        if ((getDeleteContactMessageMethod = ContactMessageServiceGrpc.getDeleteContactMessageMethod) == null) {
          ContactMessageServiceGrpc.getDeleteContactMessageMethod = getDeleteContactMessageMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.DeleteContactMessageRequest, net.dilmi.event_service.grpc.DeleteContactMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteContactMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteContactMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteContactMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ContactMessageServiceMethodDescriptorSupplier("DeleteContactMessage"))
              .build();
        }
      }
    }
    return getDeleteContactMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ContactMessageServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceStub>() {
        @java.lang.Override
        public ContactMessageServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactMessageServiceStub(channel, callOptions);
        }
      };
    return ContactMessageServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static ContactMessageServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceBlockingV2Stub>() {
        @java.lang.Override
        public ContactMessageServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactMessageServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return ContactMessageServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ContactMessageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceBlockingStub>() {
        @java.lang.Override
        public ContactMessageServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactMessageServiceBlockingStub(channel, callOptions);
        }
      };
    return ContactMessageServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ContactMessageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactMessageServiceFutureStub>() {
        @java.lang.Override
        public ContactMessageServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactMessageServiceFutureStub(channel, callOptions);
        }
      };
    return ContactMessageServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getContactMessage(net.dilmi.event_service.grpc.GetContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetContactMessageMethod(), responseObserver);
    }

    /**
     */
    default void listContactMessages(net.dilmi.event_service.grpc.ListContactMessagesRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListContactMessagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListContactMessagesMethod(), responseObserver);
    }

    /**
     */
    default void createContactMessage(net.dilmi.event_service.grpc.CreateContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateContactMessageMethod(), responseObserver);
    }

    /**
     */
    default void deleteContactMessage(net.dilmi.event_service.grpc.DeleteContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteContactMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteContactMessageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ContactMessageService.
   */
  public static abstract class ContactMessageServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ContactMessageServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ContactMessageService.
   */
  public static final class ContactMessageServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ContactMessageServiceStub> {
    private ContactMessageServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactMessageServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactMessageServiceStub(channel, callOptions);
    }

    /**
     */
    public void getContactMessage(net.dilmi.event_service.grpc.GetContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetContactMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listContactMessages(net.dilmi.event_service.grpc.ListContactMessagesRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListContactMessagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListContactMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createContactMessage(net.dilmi.event_service.grpc.CreateContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateContactMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteContactMessage(net.dilmi.event_service.grpc.DeleteContactMessageRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteContactMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteContactMessageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ContactMessageService.
   */
  public static final class ContactMessageServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<ContactMessageServiceBlockingV2Stub> {
    private ContactMessageServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactMessageServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactMessageServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ContactMessage getContactMessage(net.dilmi.event_service.grpc.GetContactMessageRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetContactMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListContactMessagesResponse listContactMessages(net.dilmi.event_service.grpc.ListContactMessagesRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListContactMessagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ContactMessage createContactMessage(net.dilmi.event_service.grpc.CreateContactMessageRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCreateContactMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteContactMessageResponse deleteContactMessage(net.dilmi.event_service.grpc.DeleteContactMessageRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDeleteContactMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service ContactMessageService.
   */
  public static final class ContactMessageServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ContactMessageServiceBlockingStub> {
    private ContactMessageServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactMessageServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactMessageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ContactMessage getContactMessage(net.dilmi.event_service.grpc.GetContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetContactMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListContactMessagesResponse listContactMessages(net.dilmi.event_service.grpc.ListContactMessagesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListContactMessagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ContactMessage createContactMessage(net.dilmi.event_service.grpc.CreateContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateContactMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteContactMessageResponse deleteContactMessage(net.dilmi.event_service.grpc.DeleteContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteContactMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ContactMessageService.
   */
  public static final class ContactMessageServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ContactMessageServiceFutureStub> {
    private ContactMessageServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactMessageServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactMessageServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ContactMessage> getContactMessage(
        net.dilmi.event_service.grpc.GetContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetContactMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ListContactMessagesResponse> listContactMessages(
        net.dilmi.event_service.grpc.ListContactMessagesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListContactMessagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ContactMessage> createContactMessage(
        net.dilmi.event_service.grpc.CreateContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateContactMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.DeleteContactMessageResponse> deleteContactMessage(
        net.dilmi.event_service.grpc.DeleteContactMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteContactMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CONTACT_MESSAGE = 0;
  private static final int METHODID_LIST_CONTACT_MESSAGES = 1;
  private static final int METHODID_CREATE_CONTACT_MESSAGE = 2;
  private static final int METHODID_DELETE_CONTACT_MESSAGE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CONTACT_MESSAGE:
          serviceImpl.getContactMessage((net.dilmi.event_service.grpc.GetContactMessageRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage>) responseObserver);
          break;
        case METHODID_LIST_CONTACT_MESSAGES:
          serviceImpl.listContactMessages((net.dilmi.event_service.grpc.ListContactMessagesRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListContactMessagesResponse>) responseObserver);
          break;
        case METHODID_CREATE_CONTACT_MESSAGE:
          serviceImpl.createContactMessage((net.dilmi.event_service.grpc.CreateContactMessageRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ContactMessage>) responseObserver);
          break;
        case METHODID_DELETE_CONTACT_MESSAGE:
          serviceImpl.deleteContactMessage((net.dilmi.event_service.grpc.DeleteContactMessageRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteContactMessageResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetContactMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.GetContactMessageRequest,
              net.dilmi.event_service.grpc.ContactMessage>(
                service, METHODID_GET_CONTACT_MESSAGE)))
        .addMethod(
          getListContactMessagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.ListContactMessagesRequest,
              net.dilmi.event_service.grpc.ListContactMessagesResponse>(
                service, METHODID_LIST_CONTACT_MESSAGES)))
        .addMethod(
          getCreateContactMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.CreateContactMessageRequest,
              net.dilmi.event_service.grpc.ContactMessage>(
                service, METHODID_CREATE_CONTACT_MESSAGE)))
        .addMethod(
          getDeleteContactMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.DeleteContactMessageRequest,
              net.dilmi.event_service.grpc.DeleteContactMessageResponse>(
                service, METHODID_DELETE_CONTACT_MESSAGE)))
        .build();
  }

  private static abstract class ContactMessageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ContactMessageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return net.dilmi.event_service.grpc.EventServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ContactMessageService");
    }
  }

  private static final class ContactMessageServiceFileDescriptorSupplier
      extends ContactMessageServiceBaseDescriptorSupplier {
    ContactMessageServiceFileDescriptorSupplier() {}
  }

  private static final class ContactMessageServiceMethodDescriptorSupplier
      extends ContactMessageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ContactMessageServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ContactMessageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ContactMessageServiceFileDescriptorSupplier())
              .addMethod(getGetContactMessageMethod())
              .addMethod(getListContactMessagesMethod())
              .addMethod(getCreateContactMessageMethod())
              .addMethod(getDeleteContactMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
