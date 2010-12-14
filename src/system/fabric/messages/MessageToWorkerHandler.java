package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.NodePrincipal;
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
 * public Response handle(NodePrincipal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that the worker handles.
 */
public abstract class MessageToWorkerHandler extends AbstractMessageServer {

  public MessageToWorkerHandler(String name) {
    super(name, Logging.WORKER_LOGGER);
  }
  
  public abstract     AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage     msg) throws AccessException;
  public abstract    CommitTransactionMessage.Response handle(NodePrincipal p, CommitTransactionMessage    msg) throws TransactionCommitFailedException;
  public abstract         ObjectUpdateMessage.Response handle(NodePrincipal p, ObjectUpdateMessage         msg);
  public abstract   PrepareTransactionMessage.Response handle(NodePrincipal p, PrepareTransactionMessage   msg) throws TransactionPrepareFailedException;
  public abstract            DirtyReadMessage.Response handle(NodePrincipal p, DirtyReadMessage            msg) throws AccessException;
  public abstract           RemoteCallMessage.Response handle(NodePrincipal p, RemoteCallMessage           msg) throws RemoteCallException;
  public abstract        TakeOwnershipMessage.Response handle(NodePrincipal p, TakeOwnershipMessage        msg) throws TakeOwnershipFailedException;
  public abstract InterWorkerStalenessMessage.Response handle(NodePrincipal p, InterWorkerStalenessMessage msg);
  
  public final Response handle(NodePrincipal p, AllocateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }
  
  public final fabric.messages.DissemReadMessage.Response handle(NodePrincipal p,
      DissemReadMessage msg) throws ProtocolError {
    throw error(msg);
  }
  
  public fabric.messages.GetCertChainMessage.Response handle(NodePrincipal p,
      GetCertChainMessage msg) throws ProtocolError {
    throw error(msg);
  }
  
  public fabric.messages.ReadMessage.Response handle(NodePrincipal p,
      ReadMessage msg) throws ProtocolError {
    throw error(msg);
  }
  
  public fabric.messages.MakePrincipalMessage.Response handle(NodePrincipal p,
      MakePrincipalMessage msg) throws ProtocolError {
    throw error(msg);
  }
  
  public fabric.messages.StalenessCheckMessage.Response handle(NodePrincipal p,
      StalenessCheckMessage msg) throws ProtocolError {
    throw error(msg);
  }
  
  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
