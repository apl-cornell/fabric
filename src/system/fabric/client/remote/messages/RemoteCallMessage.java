package fabric.client.remote.messages;

import java.io.*;
import java.lang.reflect.Method;

import fabric.client.UnreachableNodeException;
import fabric.client.remote.RemoteCallException;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.Worker;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.common.TransactionID;
import fabric.lang.Object.$Proxy;
import fabric.messages.Message;

public class RemoteCallMessage extends
    InterClientMessage<RemoteCallMessage.Response> {

  public final TransactionID tid;
  public final Class<?> receiverType;
  public final $Proxy receiver;
  public final String methodName;
  public final Class<?>[] parameterTypes;
  public final Object[] args;

  public static class Response implements Message.Response {
    public final Object result;

    public Response(Object result) {
      this.result = result;
    }

    /**
     * Deserialization constructor.
     * 
     * @param client
     *          The client from which the response is being read.
     * @param in
     *          The input stream from which to read the response.
     * @throws ClassNotFoundException
     */
    Response(RemoteClient client, DataInput in) throws IOException,
        RemoteCallException {
      if (in.readBoolean()) {
        this.result = readRef(fabric.lang.Object.class, in);
      } else {
        byte[] buf = new byte[in.readInt()];
        in.readFully(buf);

        ObjectInputStream ois =
            new ObjectInputStream(new ByteArrayInputStream(buf));
        try {
          this.result = ois.readObject();
        } catch (ClassNotFoundException e) {
          throw new RemoteCallException(e);
        }
      }
    }

    public void write(DataOutput out) throws IOException {
      out.writeBoolean(result instanceof $Proxy);
      if (result instanceof $Proxy)
        writeRef(($Proxy) result, out);
      else {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(result);
        oos.flush();
        baos.flush();

        byte[] buf = baos.toByteArray();
        out.writeInt(buf.length);
        out.write(buf);
      }
    }
  }

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
  public RemoteCallMessage(TransactionID tid, Class<?> receiverType,
      $Proxy receiver, String methodName, Class<?>[] parameterTypes,
      Object[] args) {
    super(MessageType.REMOTE_CALL);

    if (parameterTypes.length != args.length)
      throw new IllegalArgumentException();

    this.tid = tid;
    this.receiverType = receiverType;
    this.receiver = receiver;
    this.methodName = methodName;
    this.parameterTypes = parameterTypes;
    this.args = args;
  }

  /**
   * Deserialization constructor.
   */
  public RemoteCallMessage(DataInput in) throws IOException,
      ClassNotFoundException {
    super(MessageType.REMOTE_CALL);

    if (in.readBoolean())
      this.tid = new TransactionID(in);
    else this.tid = null;

    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));

    this.receiverType = (Class<?>) ois.readObject();
    this.receiver = readRef(receiverType, ois);

    this.methodName = ois.readUTF();
    this.parameterTypes = new Class<?>[in.readInt()];
    this.args = new Object[parameterTypes.length];

    for (int i = 0; i < args.length; i++) {
      parameterTypes[i] = (Class<?>) ois.readObject();
      if (ois.readBoolean())
        args[i] = readRef(parameterTypes[i], ois);
      else args[i] = ois.readObject();
    }
  }

  @Override
  public Response dispatch(Worker handler) throws RemoteCallException {
    return handler.handle(this);
  }

  public Response send(RemoteClient client) throws UnreachableNodeException,
      RemoteCallException {
    try {
      return super.send(client, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (RemoteCallException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from client.", e);
    }
  }

  @Override
  public Response response(RemoteClient c, DataInput in) throws IOException,
      RemoteCallException {
    return new Response(c, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeBoolean(tid != null);
    if (tid != null) tid.write(out);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(receiverType);
    writeRef(receiver, oos);

    oos.writeUTF(methodName);
    oos.writeInt(args.length);

    for (int i = 0; i < args.length; i++) {
      oos.writeObject(parameterTypes[i]);
      if (args[i] instanceof $Proxy) {
        oos.writeBoolean(true);
        writeRef(($Proxy) args[i], oos);
      } else {
        oos.writeBoolean(false);
        oos.writeObject(args[i]);
      }
    }

    oos.flush();
    baos.flush();

    byte[] buf = baos.toByteArray();
    out.writeInt(buf.length);
    out.write(buf);
  }

  public Method getMethod() throws SecurityException, NoSuchMethodException {
    return receiverType.getMethod(methodName, parameterTypes);
  }

}
