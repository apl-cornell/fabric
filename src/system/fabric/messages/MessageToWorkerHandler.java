package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.messages.AllocateMessage.Response;

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
  public final Response handle(Principal p, AllocateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DissemReadMessage.Response handle(Principal p,
      DissemReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.GetCertChainMessage.Response handle(Principal p,
      GetCertChainMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReuseCallMessage.Response handle(Principal p,
      ReuseCallMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReadMessage.Response handle(Principal p,
      ReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.MakePrincipalMessage.Response handle(Principal p,
      MakePrincipalMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.StalenessCheckMessage.Response handle(Principal p,
      StalenessCheckMessage msg) throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
