package fabric.messages;

import java.io.*;
import java.lang.reflect.Method;

import fabric.common.ClassRef.FabricClassRef;
import fabric.common.TransactionID;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.UpdateMap;

public class RemoteCallMessage extends
    Message<RemoteCallMessage.Response, RemoteCallException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  public final UpdateMap updateMap;
  public final FabricClassRef receiverType;
  public final _Proxy receiver;
  public final String methodName;
  public final Class<?>[] parameterTypes;
  public final Object[] args;

  /**
   * @param tid
   *          The identifier for the transaction in which the remote call is
   *          being made.
   * @param receiver
   *          The object that is receiving the call.
   * @param methodName
   *          The name of the method to be called.
   * @param args
   *          The arguments to the method.
   */
  public RemoteCallMessage(TransactionID tid, UpdateMap updateMap,
      FabricClassRef receiverType, _Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args) {
    super(MessageType.REMOTE_CALL, RemoteCallException.class);

    if (parameterTypes == null ? args != null
        : parameterTypes.length != args.length)
      throw new IllegalArgumentException();

    this.tid = tid;
    this.updateMap = updateMap;
    this.receiverType = receiverType;
    this.receiver = receiver;
    this.methodName = methodName;
    this.parameterTypes = parameterTypes;
    this.args = args;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final Object result;
    public final UpdateMap updateMap;

    public Response(Object result, UpdateMap updateMap) {
      this.result = result;
      this.updateMap = updateMap;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h) throws ProtocolError,
      RemoteCallException {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeBoolean(tid != null);
    if (tid != null) tid.write(out);

    out.writeBoolean(updateMap != null);
    if (updateMap != null) updateMap.write(out);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    receiverType.write(oos);
    writeRef(receiver, oos);

    oos.writeUTF(methodName);
    oos.writeInt(args == null ? 0 : args.length);

    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        oos.writeObject(parameterTypes[i]);
        if (args[i] instanceof _Proxy) {
          oos.writeBoolean(true);
          writeRef((_Proxy) args[i], oos);
        } else {
          oos.writeBoolean(false);
          oos.writeObject(args[i]);
        }
      }
    }

    oos.flush();
    baos.flush();

    byte[] buf = baos.toByteArray();
    out.writeInt(buf.length);
    out.write(buf);
  }

  /* readMessage */
  protected RemoteCallMessage(DataInput in) throws IOException {
    super(MessageType.REMOTE_CALL, RemoteCallException.class);

    if (in.readBoolean())
      this.tid = new TransactionID(in);
    else this.tid = null;

    if (in.readBoolean())
      this.updateMap = new UpdateMap(in);
    else this.updateMap = null;

    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));

    try {
      this.receiverType = new FabricClassRef(ois);
      this.receiver = Message.readRef(receiverType.toClass(), ois);

      this.methodName = ois.readUTF();
      this.parameterTypes = new Class<?>[ois.readInt()];
      this.args = new Object[parameterTypes.length];

      for (int i = 0; i < args.length; i++) {
        parameterTypes[i] = (Class<?>) ois.readObject();
        if (ois.readBoolean())
          args[i] = Message.readRef(parameterTypes[i], ois);
        else args[i] = ois.readObject();
      }
    } catch (final ClassNotFoundException e) {
      throw new IOException("Invalid class in remote call", e);
    }
  }

  public Method getMethod() throws SecurityException, NoSuchMethodException {
    Class<?>[] mangledParamTypes = new Class<?>[parameterTypes.length + 1];
    mangledParamTypes[0] = Principal.class;
    for (int i = 0; i < parameterTypes.length; i++)
      mangledParamTypes[i + 1] = parameterTypes[i];

    // Get the receiver's _Proxy class.
    Class<? extends fabric.lang.Object._Proxy> proxyType =
        receiverType.toProxyClass();

    if (proxyType == null) {
      throw new InternalError("Unable to find _Proxy class for " + receiverType);
    }

    return proxyType.getMethod(methodName + "_remote", mangledParamTypes);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {

    Object result;
    UpdateMap updateMap;

    if (in.readBoolean())
      result = Message.readRef(fabric.lang.Object.class, in);
    else result = readObject(in, Object.class);

    if (in.readBoolean())
      updateMap = new UpdateMap(in);
    else updateMap = null;

    return new Response(result, updateMap);
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.result instanceof _Proxy);
    if (r.result instanceof _Proxy)
      writeRef((_Proxy) r.result, out);
    else {
      writeObject(out, r.result);
    }

    out.writeBoolean(updateMap != null);
    if (updateMap != null) updateMap.write(out);
  }

}
