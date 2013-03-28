package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
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
  public final Response handle(RemoteIdentity client, ObjectUpdateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.WarrantyRefreshMessage.Response handle(
      RemoteIdentity client, WarrantyRefreshMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DirtyReadMessage.Response handle(
      RemoteIdentity client, DirtyReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.RemoteCallMessage.Response handle(
      RemoteIdentity client, RemoteCallMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.TakeOwnershipMessage.Response handle(
      RemoteIdentity client, TakeOwnershipMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.InterWorkerStalenessMessage.Response handle(
      RemoteIdentity client, InterWorkerStalenessMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to store: " + msg);
  }
}
