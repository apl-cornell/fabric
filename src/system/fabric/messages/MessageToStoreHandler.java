package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

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
  public abstract void handle(RemoteIdentity<RemoteWorker> client,
      AbortTransactionMessage msg) throws ProtocolError;

  @Override
  public abstract AllocateMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, AllocateMessage msg)
      throws AccessException;

  @Override
  public abstract void handle(RemoteIdentity<RemoteWorker> client,
      CommitTransactionMessage msg) throws ProtocolError;

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      StoreCommittedMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      WorkerCommittedMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public abstract DissemReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, DissemReadMessage msg)
      throws AccessException;

  @Override
  public abstract GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg);

  @Override
  public abstract ReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ReadMessage msg)
      throws AccessException;

  @Override
  public abstract MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
      throws FabricGeneralSecurityException;

  @Override
  public abstract StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage msg)
      throws AccessException;

  @Override
  public abstract void handle(RemoteIdentity<RemoteWorker> client,
      UnsubscribeMessage msg);

  @Override
  public abstract void handle(RemoteIdentity<RemoteWorker> client,
      PrepareTransactionMessage msg) throws ProtocolError;

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      ObjectUpdateMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      StorePrepareSuccessMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      StorePrepareFailedMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      WorkerPrepareSuccessMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final void handle(RemoteIdentity<RemoteWorker> client,
      WorkerPrepareFailedMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DirtyReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, DirtyReadMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.NonAtomicCallMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, NonAtomicCallMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.RemoteCallMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, RemoteCallMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.TakeOwnershipMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, TakeOwnershipMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.InterWorkerStalenessMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, InterWorkerStalenessMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to store: " + msg);
  }

  private final ProtocolError error(AsyncMessage msg) {
    return new ProtocolError("Invalid message to store: " + msg);
  }
}
