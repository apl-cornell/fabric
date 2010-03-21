package fabric.messages;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import fabric.worker.remote.messages.GetPrincipalMessage;
import fabric.worker.remote.messages.RemoteCallMessage;
import fabric.worker.remote.messages.TakeOwnershipMessage;
import fabric.common.MessageHandler;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FabricRuntimeException;
import fabric.common.exceptions.InternalError;
import fabric.store.MessageHandlerThread;
import fabric.net.RemoteNode;
import fabric.net.Stream;

/**
 * @param <N>
 *          The class of nodes to which messages of this type may be sent.
 * @param <R>
 *          The class of responses.
 */
public abstract class Message<N extends RemoteNode, R extends Message.Response> {

  /**
   * The <code>MessageType</code> corresponding to this class.
   */
  protected final MessageType messageType;
  protected final static Logger logger = Logger.getLogger("fabric.messages");

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
  protected final R send(N node, boolean useSSL) throws FabricException {
    Stream stream = node.openStream(useSSL);

    DataInputStream in = stream.in;
    DataOutputStream out = stream.out;

    try {
      // Write this message out.
      out.writeByte(messageType.ordinal());
      write(out);
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
      return response(node, in);
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
   * @param out
   *          The output stream to write the result to.
   * @param handler
   *          The handler that will handle the message and generate the response
   * @throws IOException
   *           If a malformed message is sent, or in the case of a failure in
   *           the i/o streams provided.
   * @throws ClassNotFoundException
   */
  public static void receive(DataInput in, DataOutputStream out,
      MessageHandler handler) throws IOException {

    try {
      MessageType messageType = MessageType.values()[in.readByte()];
      Class<? extends Message<?, ?>> messageClass = messageType.messageClass;
      Message<?, ?> m;

      try {
        m =
            messageClass.getDeclaredConstructor(DataInput.class)
                .newInstance(in);
      } catch (InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof IOException) throw (IOException) cause;
        throw new FabricException(cause);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new FabricException(e);
      }

      Response r = m.dispatch(handler);

      // Signal that no error occurred.
      out.writeBoolean(false);

      // Write out the response.
      r.write(out);
      out.flush();
    } catch (final FabricException e) {
      // Clear out the stack trace before sending the exception to the worker.
      e.setStackTrace(new StackTraceElement[0]);

      // Signal that an error occurred and write out the exception.
      out.writeBoolean(true);
      writeObject(e, out);
      out.flush();
    } catch (final FabricRuntimeException e) {
      // TODO: this is copied and pasted from above. We need to figure out what
      // exceptions _not_ to catch and then catch all the others.

      // Clear out the stack trace before sending the exception to the worker.
      e.setStackTrace(new StackTraceElement[0]);

      // Signal that an error occurred and write out the exception.
      out.writeBoolean(true);
      writeObject(e, out);
      out.flush();
    }
  }

  private static void writeObject(Object o, DataOutputStream out)
      throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(o);
    oos.flush();
  }

  private static Object readObject(DataInputStream in) throws IOException,
      ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(in);
    return ois.readObject();
  }

  private final R dispatch(MessageHandler handler) throws FabricException {
    if (handler instanceof fabric.worker.remote.MessageHandlerThread) {
      return dispatch((fabric.worker.remote.MessageHandlerThread) handler);
    }

    return dispatch((MessageHandlerThread) handler);
  }

  /**
   * Calls the appropriate <code>handle(...)</code> method on the handler.
   * 
   * @param handler
   * @return the result computed by the handler
   * @throws FabricException
   */
  public R dispatch(MessageHandlerThread handler) throws FabricException {
    throw new InternalError(
        "Invalid, unsupported, or unimplemented store message: " + getClass());
  }

  /**
   * Calls the appropriate <code>handle(...)</code> method on the handler.
   * 
   * @param handler
   * @return the result computed by the handler
   * @throws FabricException
   */
  public R dispatch(fabric.worker.remote.MessageHandlerThread handler) throws FabricException {
    throw new InternalError(
        "Invalid, unsupported, or unimplemented worker message: " + getClass());
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
  public abstract R response(N node, DataInput in) throws IOException,
      FabricException;

  /**
   * Writes this message out on the given output stream. Only used by the
   * worker.
   * 
   * @throws IOException
   *           if the output stream throws an IOException.
   */
  public abstract void write(DataOutput out) throws IOException;

  public static interface Response {
    void write(DataOutput out) throws IOException;
  }

  protected static enum MessageType {
    ALLOCATE_ONUMS(AllocateMessage.class),
    READ_ONUM(fabric.messages.ReadMessage.class),
    PREPARE_TRANSACTION(PrepareTransactionMessage.class),
    COMMIT_TRANSACTION(CommitTransactionMessage.class),
    ABORT_TRANSACTION(AbortTransactionMessage.class),
    DISSEM_READ_ONUM(DissemReadMessage.class),
    REMOTE_CALL(RemoteCallMessage.class),
    INTERWORKER_READ(fabric.worker.remote.messages.ReadMessage.class),
    TAKE_OWNERSHIP(TakeOwnershipMessage.class),
    GET_PRINCIPAL(GetPrincipalMessage.class),
    OBJECT_UPDATE(ObjectUpdateMessage.class),
    UNAUTHENTICATED_PREPARE_TRANSACTION(UnauthenticatedPrepareTransactionMessage.class),
    UNAUTHENTICATED_COMMIT_TRANSACTION(UnauthenticatedCommitTransactionMessage.class),
    UNAUTHENTICATED_ABORT_TRANSACTION(UnauthenticatedAbortTransactionMessage.class);

    private final Class<? extends Message<?, ?>> messageClass;

    MessageType(Class<? extends Message<?, ?>> messageClass) {
      this.messageClass = messageClass;
    }
  }

}
