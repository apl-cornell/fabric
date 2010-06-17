package fabric.messages;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.lang.Object._Proxy;
import fabric.net.RemoteNode;
import fabric.net.Stream;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * @param <N>
 *          The class of nodes to which messages of this type may be sent.
 * @param <R>
 *          The class of responses.
 */
public abstract class Message<R extends Message.Response> {

  /**
   * The <code>MessageType</code> corresponding to this class.
   */
  protected final MessageType messageType;

  protected Message(MessageType messageType) {
    this.messageType = messageType;
  }

  /**
   * Sends this message to the given node.
   * 
   * @param message
   *          The message to send.
   * @return The reply from the node.
   * @throws FabricException
   *           if an error occurs at the remote node while handling the message.
   */
  protected final R send(RemoteNode node, boolean useSSL) throws FabricException {
    Stream stream = node.openStream(useSSL);

    DataInputStream in = stream.in;
    DataOutputStream out = stream.out;

    try {
      // Write this message out.
      out.writeByte(messageType.ordinal());
      writeMessage(out);
      out.flush();

      // Read in the reply. Determine if an error occurred.
      if (in.readBoolean()) {
        try {
          // We have an error.
          FabricException exc = (FabricException) readObject(in);
          exc.fillInStackTrace();
          throw exc;
        } catch (ClassNotFoundException e) {
          throw new InternalError("Unexpected response from remote node", e);
        }
      }
    } catch (IOException e) {
      throw new InternalError(e);
    }

    // Read the response.
    try {
      return readResponse(in);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalError(e);
    } finally {
      try {
        stream.close();
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }
  }

  /**
   * This reads a <code>Message</code> from the provided input stream,
   * dispatches it to the given <code>MessageHandler</code>, and writes the
   * response to the provided OutputStream. Used only by the store.
   * 
   * @param in
   *          The input stream to read the incoming message from.
   * @throws IOException
   *           If a malformed message is sent, or in the case of a failure in
   *           the i/o streams provided.
   */
  public static Message<?> receive(DataInput in) throws IOException {
    try {
      MessageType messageType = MessageType.values()[in.readByte()];
      
      return messageType.parse(in);
    } catch (final ArrayIndexOutOfBoundsException e) {
      throw new IOException("Unrecognized message");
    }
  }
  
  public void respond(DataOutput out, R r) throws IOException {
    // Signal that no error occurred.
    out.writeBoolean(false);

    // Write out the response.
    writeResponse(out, r);
  }
  
  public void respond(DataOutputStream out, Exception e) throws IOException {
    // Clear out the stack trace before sending an exception out.
    e.setStackTrace(new StackTraceElement[0]);
    
    // Signal that an error occurred and write out the exception.
    out.writeBoolean(true);
    
    // write out the exception
    writeObject(out, e);
  }

  private static Object readObject(DataInputStream in) throws IOException,
      ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(in);
    return ois.readObject();
  }

  /**
   * Creates a Response message of the appropriate type using the provided input
   * stream.
   * 
   * @param node
   *          the remote node from which the response originated.
   * @param in
   *          Input stream containing the message.
   * @return A Response message with the appropriate type.
   */
  protected abstract R readResponse(DataInput in) throws IOException,
      FabricException;

  protected abstract void writeResponse(DataOutput out, R response) throws IOException;
  
  /**
   * Writes this message out on the given output stream. Only used by the
   * worker.
   * 
   * @throws IOException
   *           if the output stream throws an IOException.
   */
  protected abstract void writeMessage(DataOutput out) throws IOException;

  /**
   * Used for passing object references between workers.
   * 
   * @param type
   *          The type of the reference being read. This must be the interface
   *          corresponding to the Fabric type, and not the _Proxy or _Impl
   *          classes.
   */
  @SuppressWarnings("unchecked")
  protected static _Proxy readRef(Class<?> type, DataInput in) throws IOException {
    Store store = Worker.getWorker().getStore(in.readUTF());
    Class<? extends _Proxy> proxyType = null;
    for (Class<?> c : type.getClasses()) {
      if (c.getSimpleName().equals("_Proxy")) {
        proxyType = (Class<? extends _Proxy>) c;
        break;
      }
    }
  
    if (proxyType == null)
      throw new InternalError("Unable to find proxy class for " + type);
  
    try {
      Constructor<? extends _Proxy> constructor =
          proxyType.getConstructor(Store.class, long.class);
  
      return constructor.newInstance(store, in.readLong());
    } catch (SecurityException e) {
      throw new InternalError(e);
    } catch (NoSuchMethodException e) {
      throw new InternalError(e);
    } catch (IllegalArgumentException e) {
      throw new InternalError(e);
    } catch (InstantiationException e) {
      throw new InternalError(e);
    } catch (IllegalAccessException e) {
      throw new InternalError(e);
    } catch (InvocationTargetException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Used for passing object references between workers.
   */
  public static void writeRef(_Proxy ref, DataOutput out) throws IOException {
    out.writeUTF(ref.$getStore().name());
    out.writeLong(ref.$getOnum());
  }

  /**
   * Used for serializing java objects to a DataOutput
   */
  protected void writeObject(DataOutput out, Object o) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream    oos  = new ObjectOutputStream(baos);
    oos.writeObject(o);
    oos.flush();
    baos.flush();

    byte[] buf = baos.toByteArray();
    out.write(buf);
  }

  /**
   * Used for deserializing java objects from a DataOutput
   */
  protected <T> T readObject(DataInput in, Class<T> type) throws IOException {
    // TODO
    throw new NotImplementedException();
    /*
    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));
    Certificate[] certificateChain;
    try {
      certificateChain = (Certificate[]) ois.readObject();
    } catch (ClassNotFoundException e) {
      certificateChain = null;
    }

    this.certificateChain = certificateChain;
    */
  }



  public static interface Response {
  }

  /** This enum gives a mapping between message types and ordinals. */
  @SuppressWarnings("all")
  protected static enum MessageType {
    ALLOCATE_ONUMS      {Message parse(DataInput in) throws IOException { return new AllocateMessage           (in); }},
    READ_ONUM           {Message parse(DataInput in) throws IOException { return new ReadMessage               (in); }},
    PREPARE_TRANSACTION {Message parse(DataInput in) throws IOException { return new PrepareTransactionMessage (in); }},
    COMMIT_TRANSACTION  {Message parse(DataInput in) throws IOException { return new CommitTransactionMessage  (in); }},
    ABORT_TRANSACTION   {Message parse(DataInput in) throws IOException { return new AbortTransactionMessage   (in); }},
    DISSEM_READ_ONUM    {Message parse(DataInput in) throws IOException { return new DissemReadMessage         (in); }},
    REMOTE_CALL         {Message parse(DataInput in) throws IOException { return new RemoteCallMessage         (in); }},
    DIRTY_READ          {Message parse(DataInput in) throws IOException { return new DirtyReadMessage          (in); }},
    TAKE_OWNERSHIP      {Message parse(DataInput in) throws IOException { return new TakeOwnershipMessage      (in); }},
    GET_PRINCIPAL       {Message parse(DataInput in) throws IOException { return new GetPrincipalMessage       (in); }},
    OBJECT_UPDATE       {Message parse(DataInput in) throws IOException { return new ObjectUpdateMessage       (in); }},
    GET_CERT_CHAIN      {Message parse(DataInput in) throws IOException { return new GetCertChainMessage       (in); }},
    ;

    /** Read a message of the appropriate type from the given DataInput. */
    abstract Message<?> parse(DataInput in) throws IOException;
  }

}
