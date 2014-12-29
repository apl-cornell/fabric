package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.messages.AllocateMessage.Response;
import fabric.worker.remote.RemoteWorker;

/**
 * This abstract class acts as a visitor for MessagesToWorker. It also documents
 * the list of all messages that a worker needs to be able to handle. The
 * methods all have the form: <br>
 *
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 *
 * and there is one such method for each message type that the worker handles.
 */
public abstract class MessageToWorkerHandler extends AbstractMessageServer {

  public MessageToWorkerHandler(String name) {
    super(name, Logging.WORKER_LOGGER);
  }

  @Override
  public final Response handle(RemoteIdentity<RemoteWorker> client,
      AllocateMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DissemReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, DissemReadMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReuseCallMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ReuseCallMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ReadMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage msg)
          throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
