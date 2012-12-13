package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.messages.ObjectUpdateMessage.Response;

/**
 * This abstract class acts as a visitor for MessagesToStore. It also documents
 * the list of all messages that a store needs to be able to handle. The methods
 * all have the form:<br>
 * 
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that the store handles.
 */
public abstract class MessageToStoreHandler extends AbstractMessageServer {

  public MessageToStoreHandler(String name) {
    super(name, Logging.STORE_LOGGER);
  }

  @Override
  public final Response handle(Principal p, ObjectUpdateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DirtyReadMessage.Response handle(Principal p,
      DirtyReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.RemoteCallMessage.Response handle(Principal p,
      RemoteCallMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.TakeOwnershipMessage.Response handle(
      Principal p, TakeOwnershipMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.InterWorkerStalenessMessage.Response handle(
      Principal p, InterWorkerStalenessMessage msg) throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to store: " + msg);
  }
}
