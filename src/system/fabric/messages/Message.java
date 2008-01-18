package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.List;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.core.Worker;

public abstract class Message<R extends Message.Response> {

  public static interface Response {
    void write(ObjectOutputStream out) throws IOException;
  }

  protected static enum MessageType {
    ALLOCATE_ONUMS(AllocateMessage.class), READ_ONUM(ReadMessage.class), PREPARE_TRANSACTION(
        PrepareTransactionMessage.class), COMMIT_TRANSACTION(
        CommitTransactionMessage.class), ABORT_TRANSACTION(
        AbortTransactionMessage.class);

    private final Class<? extends Message<?>> messageClass;

    MessageType(Class<? extends Message<?>> messageClass) {
      this.messageClass = messageClass;
    }
  }

  /**
   * The <code>MessageType</code> corresponding to this class.
   */
  protected final MessageType messageType;

  /**
   * Type tag for the reply message.
   */
  private transient final Class<R> resultType;

  protected Message(MessageType messageType, Class<R> resultType) {
    this.messageType = messageType;
    this.resultType = resultType;
  }

  /**
   * Sends this message to the given core.
   * 
   * @param message
   *                The message to send.
   * @return The reply from the core.
   * @throws FabricException
   *                 if an error occurs at the core while handling the message.
   * @throws UnreachableCoreException
   *                 if unable to connect to the core.
   */
  protected R send(RemoteCore core) throws FabricException,
      UnreachableCoreException {
    // XXX Won't always send to the same core node. Is this a problem?
    // XXX This is pretty ugly. Can it be cleaned up?
    // XXX Is this code in the right place?

    boolean needToConnect = !core.isConnected();
    Client client = Client.getClient();
    final int retries = client.retries;

    int hostIdx = 0;

    // These will be filled in with real values if needed.
    List<InetSocketAddress> hosts = null;
    Principal corePrincipal = null;
    int numHosts = 0;
    int startHostIdx = 0;

    for (int retry = 0; retries < 0 || retry < retries;) {
      try {
        if (needToConnect) {
          if (hosts == null) {
            Pair<List<InetSocketAddress>, Principal> entry =
                client.nameService.lookupCore(core);
            hosts = entry.first;
            corePrincipal = entry.second;

            numHosts = hosts.size();
            startHostIdx = Client.RAND.nextInt(numHosts);
          }

          // Attempt to establish a connection.
          int hostNum = (startHostIdx + hostIdx) % numHosts;
          core.connect(client, core, hosts.get(hostNum), corePrincipal);
        } else {
          // Set the flag for the next loop iteration in case we fail.
          needToConnect = true;
        }

        // Attempt to send our message and obtain a reply.
        return send(core, core.objectInputStream(), core.objectOutputStream());
      } catch (NoSuchCoreError e) {
        // Connected to a node that doesn't host the core we're interested in.
        // Increment loop counter variables.
        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (retries >= 0) retry++;
        }
        continue;
      } catch (IOException e) {
        // Retry.
        if (hosts == null) {
          // Attempt to reuse an existing connection failed. Just restart the
          // loop.
          continue;
        }

        // Increment loop counter variables.
        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (retries >= 0) retry++;
        }
        continue;
      }
    }

    throw new UnreachableCoreException(core);
  }

  /**
   * Sends this message to a core node. Used only by the client.
   * 
   * @param core
   *                the core to which the object is being sent.
   * @param in
   *                the input stream for sending objects to the core.
   * @param out
   *                the output stream on which to obtain response objects.
   * @return the response from the core.
   * @throws FabricException
   *                 if an error occurred at the core while handling this
   *                 request.
   * @throws IOException
   *                 if an I/O error occurs during
   *                 serialization/deserialization.
   */
  private R send(Core core, ObjectInputStream in, ObjectOutputStream out)
      throws FabricException, IOException {
    // Write this message out.
    out.writeByte(messageType.ordinal());
    write(out);
    out.flush();

    // Read in the reply. Determine if an error occurred.
    if (in.readBoolean()) {
      try {
        // We have an error.
        FabricException exc = (FabricException) in.readObject();
        exc.fillInStackTrace();
        throw exc;
      } catch (ClassNotFoundException e) {
        throw new InternalError("Unexpected response from core", e);
      }
    }

    // Read the response.
    try {
      try {
        return resultType.getDeclaredConstructor(Core.class,
            ObjectInputStream.class).newInstance(core, in);
      } catch (InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof IOException) throw (IOException) cause;
        throw e;
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * This reads a <code>Message</code> from the provided input stream,
   * dispatches it to the given <code>Worker</code>, and writes the response
   * to the provided OutputStream. Used only by the core.
   * 
   * @param in
   *                The input stream to read the incoming message from.
   * @param out
   *                The output stream to write the result to.
   * @param handler
   *                The worker that will handle the message and generate the
   *                response
   * @throws IOException
   *                 If a malformed message is sent, or in the case of a failure
   *                 in the i/o streams provided.
   * @throws ClassNotFoundException
   */
  public static void receive(ObjectInputStream in, ObjectOutputStream out,
      Worker handler) throws IOException {

    try {
      MessageType messageType = MessageType.values()[in.readByte()];
      Class<? extends Message<?>> messageClass = messageType.messageClass;
      Message<?> m;
      try {
        m =
            messageClass.getDeclaredConstructor(ObjectInputStream.class)
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
      out.reset();
      out.flush();
    } catch (final FabricException e) {
      // Clear out the stack trace before sending the exception to the client.
      e.setStackTrace(new StackTraceElement[0]);

      // Signal that an error occurred and write out the exception.
      out.writeBoolean(true);
      out.writeUnshared(e);
      out.flush();
    }
  }

  /**
   * Calls the appropriate <code>handle(...)</code> method on the worker.
   * 
   * @param handler
   * @return the result computed by the handler
   * @throws FabricException
   */
  public abstract R dispatch(Worker handler) throws FabricException;

  /**
   * Writes this message out on the given output stream. Only used by the
   * client.
   * 
   * @throws IOException
   *                 if the output stream throws an IOException.
   */
  public abstract void write(ObjectOutputStream out) throws IOException;
}
