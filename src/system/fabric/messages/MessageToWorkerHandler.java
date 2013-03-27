package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.messages.AllocateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.transaction.TakeOwnershipFailedException;

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
  public abstract AbortTransactionMessage.Response handle(
      RemoteIdentity client, AbortTransactionMessage msg)
      throws AccessException;

  @Override
  public abstract CommitTransactionMessage.Response handle(
      RemoteIdentity client, CommitTransactionMessage msg)
      throws TransactionCommitFailedException;

  @Override
  public abstract ObjectUpdateMessage.Response handle(RemoteIdentity client,
      ObjectUpdateMessage msg);

  @Override
  public abstract PrepareTransactionMessage.Response handle(
      RemoteIdentity client, PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException;

  @Override
  public abstract DirtyReadMessage.Response handle(RemoteIdentity client,
      DirtyReadMessage msg) throws AccessException;

  @Override
  public abstract RemoteCallMessage.Response handle(RemoteIdentity client,
      RemoteCallMessage msg) throws RemoteCallException;

  @Override
  public abstract TakeOwnershipMessage.Response handle(RemoteIdentity client,
      TakeOwnershipMessage msg) throws TakeOwnershipFailedException;

  @Override
  public abstract InterWorkerStalenessMessage.Response handle(
      RemoteIdentity client, InterWorkerStalenessMessage msg);

  @Override
  public final Response handle(RemoteIdentity client, AllocateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DissemReadMessage.Response handle(
      RemoteIdentity client, DissemReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.GetCertChainMessage.Response handle(
      RemoteIdentity client, GetCertChainMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReadMessage.Response handle(RemoteIdentity client,
      ReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.MakePrincipalMessage.Response handle(
      RemoteIdentity client, MakePrincipalMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.StalenessCheckMessage.Response handle(
      RemoteIdentity client, StalenessCheckMessage msg) throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
