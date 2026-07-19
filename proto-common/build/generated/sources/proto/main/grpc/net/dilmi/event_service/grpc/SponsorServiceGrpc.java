package net.dilmi.event_service.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SponsorServiceGrpc {

  private SponsorServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "event_service.SponsorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getGetSponsorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSponsor",
      requestType = net.dilmi.event_service.grpc.GetSponsorRequest.class,
      responseType = net.dilmi.event_service.grpc.Sponsor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getGetSponsorMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetSponsorRequest, net.dilmi.event_service.grpc.Sponsor> getGetSponsorMethod;
    if ((getGetSponsorMethod = SponsorServiceGrpc.getGetSponsorMethod) == null) {
      synchronized (SponsorServiceGrpc.class) {
        if ((getGetSponsorMethod = SponsorServiceGrpc.getGetSponsorMethod) == null) {
          SponsorServiceGrpc.getGetSponsorMethod = getGetSponsorMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.GetSponsorRequest, net.dilmi.event_service.grpc.Sponsor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSponsor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.GetSponsorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Sponsor.getDefaultInstance()))
              .setSchemaDescriptor(new SponsorServiceMethodDescriptorSupplier("GetSponsor"))
              .build();
        }
      }
    }
    return getGetSponsorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListSponsorsRequest,
      net.dilmi.event_service.grpc.ListSponsorsResponse> getListSponsorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListSponsors",
      requestType = net.dilmi.event_service.grpc.ListSponsorsRequest.class,
      responseType = net.dilmi.event_service.grpc.ListSponsorsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListSponsorsRequest,
      net.dilmi.event_service.grpc.ListSponsorsResponse> getListSponsorsMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListSponsorsRequest, net.dilmi.event_service.grpc.ListSponsorsResponse> getListSponsorsMethod;
    if ((getListSponsorsMethod = SponsorServiceGrpc.getListSponsorsMethod) == null) {
      synchronized (SponsorServiceGrpc.class) {
        if ((getListSponsorsMethod = SponsorServiceGrpc.getListSponsorsMethod) == null) {
          SponsorServiceGrpc.getListSponsorsMethod = getListSponsorsMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.ListSponsorsRequest, net.dilmi.event_service.grpc.ListSponsorsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListSponsors"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListSponsorsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListSponsorsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SponsorServiceMethodDescriptorSupplier("ListSponsors"))
              .build();
        }
      }
    }
    return getListSponsorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getCreateSponsorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSponsor",
      requestType = net.dilmi.event_service.grpc.CreateSponsorRequest.class,
      responseType = net.dilmi.event_service.grpc.Sponsor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getCreateSponsorMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateSponsorRequest, net.dilmi.event_service.grpc.Sponsor> getCreateSponsorMethod;
    if ((getCreateSponsorMethod = SponsorServiceGrpc.getCreateSponsorMethod) == null) {
      synchronized (SponsorServiceGrpc.class) {
        if ((getCreateSponsorMethod = SponsorServiceGrpc.getCreateSponsorMethod) == null) {
          SponsorServiceGrpc.getCreateSponsorMethod = getCreateSponsorMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.CreateSponsorRequest, net.dilmi.event_service.grpc.Sponsor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSponsor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.CreateSponsorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Sponsor.getDefaultInstance()))
              .setSchemaDescriptor(new SponsorServiceMethodDescriptorSupplier("CreateSponsor"))
              .build();
        }
      }
    }
    return getCreateSponsorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getUpdateSponsorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateSponsor",
      requestType = net.dilmi.event_service.grpc.UpdateSponsorRequest.class,
      responseType = net.dilmi.event_service.grpc.Sponsor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateSponsorRequest,
      net.dilmi.event_service.grpc.Sponsor> getUpdateSponsorMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateSponsorRequest, net.dilmi.event_service.grpc.Sponsor> getUpdateSponsorMethod;
    if ((getUpdateSponsorMethod = SponsorServiceGrpc.getUpdateSponsorMethod) == null) {
      synchronized (SponsorServiceGrpc.class) {
        if ((getUpdateSponsorMethod = SponsorServiceGrpc.getUpdateSponsorMethod) == null) {
          SponsorServiceGrpc.getUpdateSponsorMethod = getUpdateSponsorMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.UpdateSponsorRequest, net.dilmi.event_service.grpc.Sponsor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateSponsor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.UpdateSponsorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Sponsor.getDefaultInstance()))
              .setSchemaDescriptor(new SponsorServiceMethodDescriptorSupplier("UpdateSponsor"))
              .build();
        }
      }
    }
    return getUpdateSponsorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteSponsorRequest,
      net.dilmi.event_service.grpc.DeleteSponsorResponse> getDeleteSponsorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteSponsor",
      requestType = net.dilmi.event_service.grpc.DeleteSponsorRequest.class,
      responseType = net.dilmi.event_service.grpc.DeleteSponsorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteSponsorRequest,
      net.dilmi.event_service.grpc.DeleteSponsorResponse> getDeleteSponsorMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteSponsorRequest, net.dilmi.event_service.grpc.DeleteSponsorResponse> getDeleteSponsorMethod;
    if ((getDeleteSponsorMethod = SponsorServiceGrpc.getDeleteSponsorMethod) == null) {
      synchronized (SponsorServiceGrpc.class) {
        if ((getDeleteSponsorMethod = SponsorServiceGrpc.getDeleteSponsorMethod) == null) {
          SponsorServiceGrpc.getDeleteSponsorMethod = getDeleteSponsorMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.DeleteSponsorRequest, net.dilmi.event_service.grpc.DeleteSponsorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteSponsor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteSponsorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteSponsorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SponsorServiceMethodDescriptorSupplier("DeleteSponsor"))
              .build();
        }
      }
    }
    return getDeleteSponsorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SponsorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SponsorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SponsorServiceStub>() {
        @java.lang.Override
        public SponsorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SponsorServiceStub(channel, callOptions);
        }
      };
    return SponsorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SponsorServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SponsorServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SponsorServiceBlockingV2Stub>() {
        @java.lang.Override
        public SponsorServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SponsorServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return SponsorServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SponsorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SponsorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SponsorServiceBlockingStub>() {
        @java.lang.Override
        public SponsorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SponsorServiceBlockingStub(channel, callOptions);
        }
      };
    return SponsorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SponsorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SponsorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SponsorServiceFutureStub>() {
        @java.lang.Override
        public SponsorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SponsorServiceFutureStub(channel, callOptions);
        }
      };
    return SponsorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getSponsor(net.dilmi.event_service.grpc.GetSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSponsorMethod(), responseObserver);
    }

    /**
     */
    default void listSponsors(net.dilmi.event_service.grpc.ListSponsorsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListSponsorsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListSponsorsMethod(), responseObserver);
    }

    /**
     */
    default void createSponsor(net.dilmi.event_service.grpc.CreateSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSponsorMethod(), responseObserver);
    }

    /**
     */
    default void updateSponsor(net.dilmi.event_service.grpc.UpdateSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSponsorMethod(), responseObserver);
    }

    /**
     */
    default void deleteSponsor(net.dilmi.event_service.grpc.DeleteSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteSponsorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteSponsorMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SponsorService.
   */
  public static abstract class SponsorServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SponsorServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SponsorService.
   */
  public static final class SponsorServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SponsorServiceStub> {
    private SponsorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SponsorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SponsorServiceStub(channel, callOptions);
    }

    /**
     */
    public void getSponsor(net.dilmi.event_service.grpc.GetSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSponsorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listSponsors(net.dilmi.event_service.grpc.ListSponsorsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListSponsorsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListSponsorsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createSponsor(net.dilmi.event_service.grpc.CreateSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSponsorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateSponsor(net.dilmi.event_service.grpc.UpdateSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSponsorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteSponsor(net.dilmi.event_service.grpc.DeleteSponsorRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteSponsorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteSponsorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SponsorService.
   */
  public static final class SponsorServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SponsorServiceBlockingV2Stub> {
    private SponsorServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SponsorServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SponsorServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor getSponsor(net.dilmi.event_service.grpc.GetSponsorRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListSponsorsResponse listSponsors(net.dilmi.event_service.grpc.ListSponsorsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListSponsorsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor createSponsor(net.dilmi.event_service.grpc.CreateSponsorRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCreateSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor updateSponsor(net.dilmi.event_service.grpc.UpdateSponsorRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getUpdateSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteSponsorResponse deleteSponsor(net.dilmi.event_service.grpc.DeleteSponsorRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDeleteSponsorMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service SponsorService.
   */
  public static final class SponsorServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SponsorServiceBlockingStub> {
    private SponsorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SponsorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SponsorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor getSponsor(net.dilmi.event_service.grpc.GetSponsorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListSponsorsResponse listSponsors(net.dilmi.event_service.grpc.ListSponsorsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListSponsorsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor createSponsor(net.dilmi.event_service.grpc.CreateSponsorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Sponsor updateSponsor(net.dilmi.event_service.grpc.UpdateSponsorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSponsorMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteSponsorResponse deleteSponsor(net.dilmi.event_service.grpc.DeleteSponsorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteSponsorMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SponsorService.
   */
  public static final class SponsorServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SponsorServiceFutureStub> {
    private SponsorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SponsorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SponsorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Sponsor> getSponsor(
        net.dilmi.event_service.grpc.GetSponsorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSponsorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ListSponsorsResponse> listSponsors(
        net.dilmi.event_service.grpc.ListSponsorsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListSponsorsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Sponsor> createSponsor(
        net.dilmi.event_service.grpc.CreateSponsorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSponsorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Sponsor> updateSponsor(
        net.dilmi.event_service.grpc.UpdateSponsorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSponsorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.DeleteSponsorResponse> deleteSponsor(
        net.dilmi.event_service.grpc.DeleteSponsorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteSponsorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SPONSOR = 0;
  private static final int METHODID_LIST_SPONSORS = 1;
  private static final int METHODID_CREATE_SPONSOR = 2;
  private static final int METHODID_UPDATE_SPONSOR = 3;
  private static final int METHODID_DELETE_SPONSOR = 4;

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
        case METHODID_GET_SPONSOR:
          serviceImpl.getSponsor((net.dilmi.event_service.grpc.GetSponsorRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor>) responseObserver);
          break;
        case METHODID_LIST_SPONSORS:
          serviceImpl.listSponsors((net.dilmi.event_service.grpc.ListSponsorsRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListSponsorsResponse>) responseObserver);
          break;
        case METHODID_CREATE_SPONSOR:
          serviceImpl.createSponsor((net.dilmi.event_service.grpc.CreateSponsorRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor>) responseObserver);
          break;
        case METHODID_UPDATE_SPONSOR:
          serviceImpl.updateSponsor((net.dilmi.event_service.grpc.UpdateSponsorRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Sponsor>) responseObserver);
          break;
        case METHODID_DELETE_SPONSOR:
          serviceImpl.deleteSponsor((net.dilmi.event_service.grpc.DeleteSponsorRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteSponsorResponse>) responseObserver);
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
          getGetSponsorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.GetSponsorRequest,
              net.dilmi.event_service.grpc.Sponsor>(
                service, METHODID_GET_SPONSOR)))
        .addMethod(
          getListSponsorsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.ListSponsorsRequest,
              net.dilmi.event_service.grpc.ListSponsorsResponse>(
                service, METHODID_LIST_SPONSORS)))
        .addMethod(
          getCreateSponsorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.CreateSponsorRequest,
              net.dilmi.event_service.grpc.Sponsor>(
                service, METHODID_CREATE_SPONSOR)))
        .addMethod(
          getUpdateSponsorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.UpdateSponsorRequest,
              net.dilmi.event_service.grpc.Sponsor>(
                service, METHODID_UPDATE_SPONSOR)))
        .addMethod(
          getDeleteSponsorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.DeleteSponsorRequest,
              net.dilmi.event_service.grpc.DeleteSponsorResponse>(
                service, METHODID_DELETE_SPONSOR)))
        .build();
  }

  private static abstract class SponsorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SponsorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return net.dilmi.event_service.grpc.EventServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SponsorService");
    }
  }

  private static final class SponsorServiceFileDescriptorSupplier
      extends SponsorServiceBaseDescriptorSupplier {
    SponsorServiceFileDescriptorSupplier() {}
  }

  private static final class SponsorServiceMethodDescriptorSupplier
      extends SponsorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SponsorServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SponsorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SponsorServiceFileDescriptorSupplier())
              .addMethod(getGetSponsorMethod())
              .addMethod(getListSponsorsMethod())
              .addMethod(getCreateSponsorMethod())
              .addMethod(getUpdateSponsorMethod())
              .addMethod(getDeleteSponsorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
