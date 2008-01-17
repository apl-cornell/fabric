package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.List;

import fabric.client.Client;
import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.core.Worker;

public abstract class Message<R extends Message.Response> implements
    Serializable {

  public static interface Response extends Serializable {
  }

  protected static enum MessageType {
    ALLOCATE_ONUMS, READ_ONUM, PREPARE_TRANSACTION, COMMIT_TRANSACTION,
    ABORT_TRANSACTION
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
        return send(core.objectInputStream(), core.objectOutputStream());
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
  private R send(ObjectInputStream in, ObjectOutputStream out)
      throws FabricException, IOException {

    try {
      out.writeUnshared(this);
      out.flush();
      Object result = in.readUnshared();

      if (resultType.isInstance(result)) return resultType.cast(result);
      if (result instanceof FabricException) {
        FabricException exc = (FabricException) result;
        exc.fillInStackTrace();
        throw exc;
      }
      throw new InternalError("Unexpected response from Core: "
          + result.getClass());
    } catch (final ClassNotFoundException exc) {
      throw new InternalError("Unexpected response from Core", exc);
    }
  }

  /**
   * This reads a <code>Message</code> from the provided input stream,
   * dispatches it to a <code>Worker</code>, and writes the response to the
   * provided OutputStream. Used only by the core.
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
      Message<?> m;
      try {
        m = (Message<?>) in.readUnshared();
      } catch (ClassNotFoundException e) {
        throw new ProtocolError("Unknown request type.");
      }
      Response r = m.dispatch(handler);

      out.writeUnshared(r);
      out.reset();
      out.flush();
    } catch (final FabricException e) {
      // Clear out the stack trace before sending the exception to the client.
      e.setStackTrace(new StackTraceElement[0]);
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
}
