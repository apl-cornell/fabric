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
 * @param <R> The class of responses.
 */
public abstract class Message<R extends Message.Response> {

  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  /** Marker interface for Message responses. */
  public static interface Response {
  }

  /**
   * Read a Message from the given <code>DataInput</code>
   * 
   * @throws IOException
   *           If a malformed message is sent, or in the case of a failure in
   *           the <code>DataInput</code> provided.
   */
  public static Message<?> receive(DataInput in) throws IOException {
    try {
      MessageType messageType = MessageType.values()[in.readByte()];
      
      return messageType.parse(in);
    } catch (final ArrayIndexOutOfBoundsException e) {
      throw new IOException("Unrecognized message");
    }
  }
  
  /**
   * Send a successful response this message.
   *
   * @param out
   *            the channel on which to send the response
   * @param r
   *            the response to send.
   * @throws IOException
   *            if the provided <code>DataOutput</code> fails.
   */
  public void respond(DataOutput out, R r) throws IOException {
    // Signal that no error occurred.
    out.writeBoolean(false);

    // Write out the response.
    writeResponse(out, r);
  }
  
  /**
   * Send a response to this message that indicates an exception.
   * 
   * @param out
   *            the channel on which to send the response
   * @param e
   *            the exception to send
   * @throws IOException
   *            if the provided <code>DataOutput</code> fails.
   */
  public void respond(DataOutput out, Exception e) throws IOException {
    // Clear out the stack trace before sending an exception out.
    e.setStackTrace(new StackTraceElement[0]);
    
    // Signal that an error occurred and write out the exception.
    out.writeBoolean(true);
    
    // write out the exception
    writeObject(out, e);
  }

  //////////////////////////////////////////////////////////////////////////////
  // API for concrete message implementations                                 //
  //////////////////////////////////////////////////////////////////////////////
  
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

  /** The <code>MessageType</code> corresponding to this class. */
  private final MessageType messageType;

  /** Constructs a message of the given <code>MessageType</code> */
  protected Message(MessageType messageType) {
    this.messageType = messageType;
  }

  /**
   * Sends this message to the given node and awaits a response.
   * 
   * @param message
   *          The message to send.
   * @return The reply from the node.
   * @throws FabricException
   *           if an error occurs at the remote node while handling the message.
   */
  protected final R send(RemoteNode node, boolean useSSL)
           throws FabricException {
    
    Stream stream = node.openStream(useSSL);
    DataInputStream  in  = stream.in;
    DataOutputStream out = stream.out;
    
    try {
      // Write this message out.
      out.writeByte(messageType.ordinal());
      writeMessage(out);
      out.flush();

      // Read in the reply. Determine if an error occurred.
      if (in.readBoolean()) {
        // We have an error.
        FabricException exc = readObject(in, FabricException.class);
        exc.fillInStackTrace();
        throw exc;
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
    }
  }

  /**
   * Serializes a fabric object reference.
   */
  protected void writeRef(_Proxy ref, DataOutput out) throws IOException {
    out.writeUTF(ref.$getStore().name());
    out.writeLong(ref.$getOnum());
  }

  /**
   * Deserializes a fabric object reference.
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
   * Serialize a java object to a DataOutput
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
   * Deserialize a java object from a DataOutput
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

  //////////////////////////////////////////////////////////////////////////////
  // abstract serialization methods                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * Writes this message out on the given output stream.
   * @throws IOException
   *           if the <code>DataOutput</code> fails.
   */
  protected abstract void writeMessage(DataOutput out) throws IOException;

  /**
   * Each subclass should have a constructor of the form:
   * 
   * protected Message(DataInput in) throws IOException
   * 
   * that constructs a message of the given type, reading the data from the
   * provided <code>DataInput</code>.
   * 
   * @throws IOException
   *            if the message is malformed, or if the <code>DataInput</code>
   *            fails.
   */
  /* readMessage */
  // protected Message(DataInput in) throws IOException
  
  /**
   * Creates a Response message of the appropriate type using the provided
   * <code>DataOutput</code>
   * 
   * @throws IOException
   *            if the response is malformed, or if the <code>DataInput</code>
   *            fails.
   */
  protected abstract R readResponse(DataInput in) throws IOException;

  /**
   * Writes a Response message of the appropriate type using the provided
   * <code>DataOutput</code>.
   * 
   * @throws IOException
   *            if the <code>DataOutput</code> fails.
   */
  protected abstract void writeResponse(DataOutput out, R response) throws IOException;
}
