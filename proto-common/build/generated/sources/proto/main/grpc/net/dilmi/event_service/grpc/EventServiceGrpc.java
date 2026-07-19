package net.dilmi.event_service.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class EventServiceGrpc {

  private EventServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "event_service.EventService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetEventRequest,
      net.dilmi.event_service.grpc.Event> getGetEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEvent",
      requestType = net.dilmi.event_service.grpc.GetEventRequest.class,
      responseType = net.dilmi.event_service.grpc.Event.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetEventRequest,
      net.dilmi.event_service.grpc.Event> getGetEventMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.GetEventRequest, net.dilmi.event_service.grpc.Event> getGetEventMethod;
    if ((getGetEventMethod = EventServiceGrpc.getGetEventMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getGetEventMethod = EventServiceGrpc.getGetEventMethod) == null) {
          EventServiceGrpc.getGetEventMethod = getGetEventMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.GetEventRequest, net.dilmi.event_service.grpc.Event>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.GetEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Event.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("GetEvent"))
              .build();
        }
      }
    }
    return getGetEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListEventsRequest,
      net.dilmi.event_service.grpc.ListEventsResponse> getListEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListEvents",
      requestType = net.dilmi.event_service.grpc.ListEventsRequest.class,
      responseType = net.dilmi.event_service.grpc.ListEventsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListEventsRequest,
      net.dilmi.event_service.grpc.ListEventsResponse> getListEventsMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ListEventsRequest, net.dilmi.event_service.grpc.ListEventsResponse> getListEventsMethod;
    if ((getListEventsMethod = EventServiceGrpc.getListEventsMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getListEventsMethod = EventServiceGrpc.getListEventsMethod) == null) {
          EventServiceGrpc.getListEventsMethod = getListEventsMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.ListEventsRequest, net.dilmi.event_service.grpc.ListEventsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListEventsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ListEventsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("ListEvents"))
              .build();
        }
      }
    }
    return getListEventsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateEventRequest,
      net.dilmi.event_service.grpc.Event> getCreateEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateEvent",
      requestType = net.dilmi.event_service.grpc.CreateEventRequest.class,
      responseType = net.dilmi.event_service.grpc.Event.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateEventRequest,
      net.dilmi.event_service.grpc.Event> getCreateEventMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.CreateEventRequest, net.dilmi.event_service.grpc.Event> getCreateEventMethod;
    if ((getCreateEventMethod = EventServiceGrpc.getCreateEventMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getCreateEventMethod = EventServiceGrpc.getCreateEventMethod) == null) {
          EventServiceGrpc.getCreateEventMethod = getCreateEventMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.CreateEventRequest, net.dilmi.event_service.grpc.Event>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.CreateEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Event.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("CreateEvent"))
              .build();
        }
      }
    }
    return getCreateEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateEventRequest,
      net.dilmi.event_service.grpc.Event> getUpdateEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateEvent",
      requestType = net.dilmi.event_service.grpc.UpdateEventRequest.class,
      responseType = net.dilmi.event_service.grpc.Event.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateEventRequest,
      net.dilmi.event_service.grpc.Event> getUpdateEventMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.UpdateEventRequest, net.dilmi.event_service.grpc.Event> getUpdateEventMethod;
    if ((getUpdateEventMethod = EventServiceGrpc.getUpdateEventMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getUpdateEventMethod = EventServiceGrpc.getUpdateEventMethod) == null) {
          EventServiceGrpc.getUpdateEventMethod = getUpdateEventMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.UpdateEventRequest, net.dilmi.event_service.grpc.Event>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.UpdateEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.Event.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("UpdateEvent"))
              .build();
        }
      }
    }
    return getUpdateEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteEventRequest,
      net.dilmi.event_service.grpc.DeleteEventResponse> getDeleteEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteEvent",
      requestType = net.dilmi.event_service.grpc.DeleteEventRequest.class,
      responseType = net.dilmi.event_service.grpc.DeleteEventResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteEventRequest,
      net.dilmi.event_service.grpc.DeleteEventResponse> getDeleteEventMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.DeleteEventRequest, net.dilmi.event_service.grpc.DeleteEventResponse> getDeleteEventMethod;
    if ((getDeleteEventMethod = EventServiceGrpc.getDeleteEventMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getDeleteEventMethod = EventServiceGrpc.getDeleteEventMethod) == null) {
          EventServiceGrpc.getDeleteEventMethod = getDeleteEventMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.DeleteEventRequest, net.dilmi.event_service.grpc.DeleteEventResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.DeleteEventResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("DeleteEvent"))
              .build();
        }
      }
    }
    return getDeleteEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReserveSeatsRequest,
      net.dilmi.event_service.grpc.ReserveSeatsResponse> getReserveSeatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserveSeats",
      requestType = net.dilmi.event_service.grpc.ReserveSeatsRequest.class,
      responseType = net.dilmi.event_service.grpc.ReserveSeatsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReserveSeatsRequest,
      net.dilmi.event_service.grpc.ReserveSeatsResponse> getReserveSeatsMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReserveSeatsRequest, net.dilmi.event_service.grpc.ReserveSeatsResponse> getReserveSeatsMethod;
    if ((getReserveSeatsMethod = EventServiceGrpc.getReserveSeatsMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getReserveSeatsMethod = EventServiceGrpc.getReserveSeatsMethod) == null) {
          EventServiceGrpc.getReserveSeatsMethod = getReserveSeatsMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.ReserveSeatsRequest, net.dilmi.event_service.grpc.ReserveSeatsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserveSeats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ReserveSeatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ReserveSeatsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("ReserveSeats"))
              .build();
        }
      }
    }
    return getReserveSeatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReleaseSeatsRequest,
      net.dilmi.event_service.grpc.ReleaseSeatsResponse> getReleaseSeatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReleaseSeats",
      requestType = net.dilmi.event_service.grpc.ReleaseSeatsRequest.class,
      responseType = net.dilmi.event_service.grpc.ReleaseSeatsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReleaseSeatsRequest,
      net.dilmi.event_service.grpc.ReleaseSeatsResponse> getReleaseSeatsMethod() {
    io.grpc.MethodDescriptor<net.dilmi.event_service.grpc.ReleaseSeatsRequest, net.dilmi.event_service.grpc.ReleaseSeatsResponse> getReleaseSeatsMethod;
    if ((getReleaseSeatsMethod = EventServiceGrpc.getReleaseSeatsMethod) == null) {
      synchronized (EventServiceGrpc.class) {
        if ((getReleaseSeatsMethod = EventServiceGrpc.getReleaseSeatsMethod) == null) {
          EventServiceGrpc.getReleaseSeatsMethod = getReleaseSeatsMethod =
              io.grpc.MethodDescriptor.<net.dilmi.event_service.grpc.ReleaseSeatsRequest, net.dilmi.event_service.grpc.ReleaseSeatsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReleaseSeats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ReleaseSeatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  net.dilmi.event_service.grpc.ReleaseSeatsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EventServiceMethodDescriptorSupplier("ReleaseSeats"))
              .build();
        }
      }
    }
    return getReleaseSeatsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EventServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventServiceStub>() {
        @java.lang.Override
        public EventServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventServiceStub(channel, callOptions);
        }
      };
    return EventServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static EventServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventServiceBlockingV2Stub>() {
        @java.lang.Override
        public EventServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return EventServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EventServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventServiceBlockingStub>() {
        @java.lang.Override
        public EventServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventServiceBlockingStub(channel, callOptions);
        }
      };
    return EventServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EventServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventServiceFutureStub>() {
        @java.lang.Override
        public EventServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventServiceFutureStub(channel, callOptions);
        }
      };
    return EventServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getEvent(net.dilmi.event_service.grpc.GetEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEventMethod(), responseObserver);
    }

    /**
     */
    default void listEvents(net.dilmi.event_service.grpc.ListEventsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListEventsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListEventsMethod(), responseObserver);
    }

    /**
     */
    default void createEvent(net.dilmi.event_service.grpc.CreateEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateEventMethod(), responseObserver);
    }

    /**
     */
    default void updateEvent(net.dilmi.event_service.grpc.UpdateEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateEventMethod(), responseObserver);
    }

    /**
     */
    default void deleteEvent(net.dilmi.event_service.grpc.DeleteEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteEventResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteEventMethod(), responseObserver);
    }

    /**
     */
    default void reserveSeats(net.dilmi.event_service.grpc.ReserveSeatsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReserveSeatsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReserveSeatsMethod(), responseObserver);
    }

    /**
     */
    default void releaseSeats(net.dilmi.event_service.grpc.ReleaseSeatsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReleaseSeatsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReleaseSeatsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EventService.
   */
  public static abstract class EventServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EventServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EventService.
   */
  public static final class EventServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EventServiceStub> {
    private EventServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventServiceStub(channel, callOptions);
    }

    /**
     */
    public void getEvent(net.dilmi.event_service.grpc.GetEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listEvents(net.dilmi.event_service.grpc.ListEventsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListEventsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListEventsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createEvent(net.dilmi.event_service.grpc.CreateEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateEvent(net.dilmi.event_service.grpc.UpdateEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteEvent(net.dilmi.event_service.grpc.DeleteEventRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteEventResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reserveSeats(net.dilmi.event_service.grpc.ReserveSeatsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReserveSeatsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReserveSeatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void releaseSeats(net.dilmi.event_service.grpc.ReleaseSeatsRequest request,
        io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReleaseSeatsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReleaseSeatsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EventService.
   */
  public static final class EventServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<EventServiceBlockingV2Stub> {
    private EventServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event getEvent(net.dilmi.event_service.grpc.GetEventRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListEventsResponse listEvents(net.dilmi.event_service.grpc.ListEventsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListEventsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event createEvent(net.dilmi.event_service.grpc.CreateEventRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCreateEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event updateEvent(net.dilmi.event_service.grpc.UpdateEventRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getUpdateEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteEventResponse deleteEvent(net.dilmi.event_service.grpc.DeleteEventRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDeleteEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ReserveSeatsResponse reserveSeats(net.dilmi.event_service.grpc.ReserveSeatsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getReserveSeatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ReleaseSeatsResponse releaseSeats(net.dilmi.event_service.grpc.ReleaseSeatsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getReleaseSeatsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service EventService.
   */
  public static final class EventServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EventServiceBlockingStub> {
    private EventServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event getEvent(net.dilmi.event_service.grpc.GetEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ListEventsResponse listEvents(net.dilmi.event_service.grpc.ListEventsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListEventsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event createEvent(net.dilmi.event_service.grpc.CreateEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.Event updateEvent(net.dilmi.event_service.grpc.UpdateEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.DeleteEventResponse deleteEvent(net.dilmi.event_service.grpc.DeleteEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ReserveSeatsResponse reserveSeats(net.dilmi.event_service.grpc.ReserveSeatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReserveSeatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public net.dilmi.event_service.grpc.ReleaseSeatsResponse releaseSeats(net.dilmi.event_service.grpc.ReleaseSeatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReleaseSeatsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EventService.
   */
  public static final class EventServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EventServiceFutureStub> {
    private EventServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Event> getEvent(
        net.dilmi.event_service.grpc.GetEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ListEventsResponse> listEvents(
        net.dilmi.event_service.grpc.ListEventsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListEventsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Event> createEvent(
        net.dilmi.event_service.grpc.CreateEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.Event> updateEvent(
        net.dilmi.event_service.grpc.UpdateEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.DeleteEventResponse> deleteEvent(
        net.dilmi.event_service.grpc.DeleteEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ReserveSeatsResponse> reserveSeats(
        net.dilmi.event_service.grpc.ReserveSeatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReserveSeatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.dilmi.event_service.grpc.ReleaseSeatsResponse> releaseSeats(
        net.dilmi.event_service.grpc.ReleaseSeatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReleaseSeatsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_EVENT = 0;
  private static final int METHODID_LIST_EVENTS = 1;
  private static final int METHODID_CREATE_EVENT = 2;
  private static final int METHODID_UPDATE_EVENT = 3;
  private static final int METHODID_DELETE_EVENT = 4;
  private static final int METHODID_RESERVE_SEATS = 5;
  private static final int METHODID_RELEASE_SEATS = 6;

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
        case METHODID_GET_EVENT:
          serviceImpl.getEvent((net.dilmi.event_service.grpc.GetEventRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event>) responseObserver);
          break;
        case METHODID_LIST_EVENTS:
          serviceImpl.listEvents((net.dilmi.event_service.grpc.ListEventsRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ListEventsResponse>) responseObserver);
          break;
        case METHODID_CREATE_EVENT:
          serviceImpl.createEvent((net.dilmi.event_service.grpc.CreateEventRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event>) responseObserver);
          break;
        case METHODID_UPDATE_EVENT:
          serviceImpl.updateEvent((net.dilmi.event_service.grpc.UpdateEventRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.Event>) responseObserver);
          break;
        case METHODID_DELETE_EVENT:
          serviceImpl.deleteEvent((net.dilmi.event_service.grpc.DeleteEventRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.DeleteEventResponse>) responseObserver);
          break;
        case METHODID_RESERVE_SEATS:
          serviceImpl.reserveSeats((net.dilmi.event_service.grpc.ReserveSeatsRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReserveSeatsResponse>) responseObserver);
          break;
        case METHODID_RELEASE_SEATS:
          serviceImpl.releaseSeats((net.dilmi.event_service.grpc.ReleaseSeatsRequest) request,
              (io.grpc.stub.StreamObserver<net.dilmi.event_service.grpc.ReleaseSeatsResponse>) responseObserver);
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
          getGetEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.GetEventRequest,
              net.dilmi.event_service.grpc.Event>(
                service, METHODID_GET_EVENT)))
        .addMethod(
          getListEventsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.ListEventsRequest,
              net.dilmi.event_service.grpc.ListEventsResponse>(
                service, METHODID_LIST_EVENTS)))
        .addMethod(
          getCreateEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.CreateEventRequest,
              net.dilmi.event_service.grpc.Event>(
                service, METHODID_CREATE_EVENT)))
        .addMethod(
          getUpdateEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.UpdateEventRequest,
              net.dilmi.event_service.grpc.Event>(
                service, METHODID_UPDATE_EVENT)))
        .addMethod(
          getDeleteEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.DeleteEventRequest,
              net.dilmi.event_service.grpc.DeleteEventResponse>(
                service, METHODID_DELETE_EVENT)))
        .addMethod(
          getReserveSeatsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.ReserveSeatsRequest,
              net.dilmi.event_service.grpc.ReserveSeatsResponse>(
                service, METHODID_RESERVE_SEATS)))
        .addMethod(
          getReleaseSeatsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              net.dilmi.event_service.grpc.ReleaseSeatsRequest,
              net.dilmi.event_service.grpc.ReleaseSeatsResponse>(
                service, METHODID_RELEASE_SEATS)))
        .build();
  }

  private static abstract class EventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EventServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return net.dilmi.event_service.grpc.EventServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EventService");
    }
  }

  private static final class EventServiceFileDescriptorSupplier
      extends EventServiceBaseDescriptorSupplier {
    EventServiceFileDescriptorSupplier() {}
  }

  private static final class EventServiceMethodDescriptorSupplier
      extends EventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EventServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EventServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EventServiceFileDescriptorSupplier())
              .addMethod(getGetEventMethod())
              .addMethod(getListEventsMethod())
              .addMethod(getCreateEventMethod())
              .addMethod(getUpdateEventMethod())
              .addMethod(getDeleteEventMethod())
              .addMethod(getReserveSeatsMethod())
              .addMethod(getReleaseSeatsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
