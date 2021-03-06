package fabric.messages;

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TakeOwnershipFailedException;

/**
 * This interface acts as a visitor for Messages. It also documents the list of
 * all messages that can be handled. The methods all have the form:<br>
 *
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 *
 * and there is one such method for each message type that can be handled.
 */
public interface MessageHandler {
  /////////////////////////////////////////////////////////////////////
  // Asynchronous Messages
  /////////////////////////////////////////////////////////////////////

  public void handle(RemoteIdentity<RemoteWorker> client,
      ObjectUpdateMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      UnsubscribeMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      PrepareTransactionMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      StorePrepareFailedMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      StorePrepareSuccessMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      WorkerPrepareFailedMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      WorkerPrepareSuccessMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      CommitTransactionMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      StoreCommittedMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      WorkerCommittedMessage msg) throws ProtocolError;

  public void handle(RemoteIdentity<RemoteWorker> client,
      AbortTransactionMessage msg) throws ProtocolError;

  /////////////////////////////////////////////////////////////////////
  // Synchronous Messages
  /////////////////////////////////////////////////////////////////////

  public AllocateMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      AllocateMessage msg) throws ProtocolError, AccessException;

  public DissemReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DissemReadMessage msg) throws ProtocolError, AccessException;

  public GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg)
      throws ProtocolError;

  public ReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      ReadMessage msg) throws ProtocolError, AccessException;

  public MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
      throws ProtocolError, FabricGeneralSecurityException;

  public StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage msg)
      throws ProtocolError, AccessException;

  public DirtyReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DirtyReadMessage msg) throws ProtocolError, AccessException;

  public NonAtomicCallMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, NonAtomicCallMessage msg)
      throws ProtocolError, RemoteCallException;

  public RemoteCallMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      RemoteCallMessage msg) throws ProtocolError, RemoteCallException;

  public TakeOwnershipMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, TakeOwnershipMessage msg)
      throws ProtocolError, TakeOwnershipFailedException;

  public InterWorkerStalenessMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, InterWorkerStalenessMessage msg)
      throws ProtocolError;
}
