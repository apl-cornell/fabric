package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.messages.ObjectUpdateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;

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
  public abstract AbortTransactionMessage.Response handle(
      RemoteIdentity client, AbortTransactionMessage msg)
      throws AccessException;

  @Override
  public abstract AllocateMessage.Response handle(RemoteIdentity client,
      AllocateMessage msg) throws AccessException;

  @Override
  public abstract CommitTransactionMessage.Response handle(
      RemoteIdentity client, CommitTransactionMessage msg)
      throws TransactionCommitFailedException;

  @Override
  public abstract DissemReadMessage.Response handle(RemoteIdentity client,
      DissemReadMessage msg) throws AccessException;

  @Override
  public abstract GetCertChainMessage.Response handle(RemoteIdentity client,
      GetCertChainMessage msg);

  @Override
  public abstract PrepareTransactionMessage.Response handle(
      RemoteIdentity client, PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException;

  @Override
  public abstract ReadMessage.Response handle(RemoteIdentity client,
      ReadMessage msg) throws AccessException;

  @Override
  public abstract MakePrincipalMessage.Response handle(RemoteIdentity client,
      MakePrincipalMessage msg) throws FabricGeneralSecurityException;

  @Override
  public abstract StalenessCheckMessage.Response handle(RemoteIdentity client,
      StalenessCheckMessage msg) throws AccessException;

  @Override
  public final Response handle(RemoteIdentity client, ObjectUpdateMessage msg)
      throws ProtocolError {
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
