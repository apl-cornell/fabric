package fabric.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import fabric.common.ClassRef;
import fabric.common.TransactionID;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

public class RemoteCallMessage extends
Message<RemoteCallMessage.Response, RemoteCallException> {
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  public final WriterMap writerMap;
  public final ClassRef receiverType;
  public final _Proxy receiver;
  public final String methodName;
  public final Class<?>[] parameterTypes;
  public final Object[] args;

  /**
   * @param tid
   *          The identifier for the transaction that is making the remote call.
   * @param receiver
   *          The object that is receiving the call.
   * @param methodName
   *          The name of the method to be called.
   * @param args
   *          The arguments to the method.
   */
  public RemoteCallMessage(TransactionID tid, WriterMap writerMap,
      ClassRef receiverType, _Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args) {
    super(MessageType.REMOTE_CALL, RemoteCallException.class);

    if (parameterTypes == null ? args != null
        : parameterTypes.length != args.length)
      throw new IllegalArgumentException();

    this.tid = tid;
    this.writerMap = writerMap;
    this.receiverType = receiverType;
    this.receiver = receiver;
    this.methodName = methodName;
    this.parameterTypes = parameterTypes;
    this.args = args;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final Object result;
    public final WriterMap writerMap;

    public Response(Object result, WriterMap writerMap) {
      this.result = result;
      this.writerMap = writerMap;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError, RemoteCallException {
    return h.handle(client, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeBoolean(tid != null);
    if (tid != null) tid.write(out);

    out.writeBoolean(writerMap != null);
    if (writerMap != null) writerMap.write(out);

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
      this.writerMap = new WriterMap(in);
    else this.writerMap = null;

    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));

    try {
      this.receiverType = ClassRef.deserialize(ois);
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
    WriterMap writerMap;

    if (in.readBoolean())
      result = Message.readRef(fabric.lang.Object.class, in);
    else result = readObject(in, Object.class);

    if (in.readBoolean())
      writerMap = new WriterMap(in);
    else writerMap = null;

    return new Response(result, writerMap);
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.result instanceof _Proxy);
    if (r.result instanceof _Proxy)
      writeRef((_Proxy) r.result, out);
    else {
      writeObject(out, r.result);
    }

    out.writeBoolean(r.writerMap != null);
    if (r.writerMap != null) r.writerMap.write(out);
  }

}
