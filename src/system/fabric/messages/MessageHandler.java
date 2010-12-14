package fabric.messages;

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.NodePrincipal;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.transaction.TakeOwnershipFailedException;

/**
 * This interface acts as a visitor for Messages. It also documents the list of
 * all messages that can be handled. The methods all have the form:<br>
 * 
 * <pre>
 * public Response handle(NodePrincipal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that can be handled.
 */
public interface MessageHandler {
  public     AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage     msg) throws AccessException;
  public             AllocateMessage.Response handle(NodePrincipal p, AllocateMessage             msg) throws ProtocolError, AccessException;
  public    CommitTransactionMessage.Response handle(NodePrincipal p, CommitTransactionMessage    msg) throws TransactionCommitFailedException;
  public           DissemReadMessage.Response handle(NodePrincipal p, DissemReadMessage           msg) throws ProtocolError, AccessException;
  public         GetCertChainMessage.Response handle(NodePrincipal p, GetCertChainMessage         msg) throws ProtocolError;
  public   PrepareTransactionMessage.Response handle(NodePrincipal p, PrepareTransactionMessage   msg) throws TransactionPrepareFailedException;
  public                 ReadMessage.Response handle(NodePrincipal p, ReadMessage                 msg) throws ProtocolError, AccessException;
  public        MakePrincipalMessage.Response handle(NodePrincipal p, MakePrincipalMessage        msg) throws ProtocolError, FabricGeneralSecurityException;
  public       StalenessCheckMessage.Response handle(NodePrincipal p, StalenessCheckMessage       msg) throws ProtocolError, AccessException;
  public         ObjectUpdateMessage.Response handle(NodePrincipal p, ObjectUpdateMessage         msg) throws ProtocolError;
  public            DirtyReadMessage.Response handle(NodePrincipal p, DirtyReadMessage            msg) throws ProtocolError, AccessException;
  public           RemoteCallMessage.Response handle(NodePrincipal p, RemoteCallMessage           msg) throws ProtocolError, RemoteCallException;
  public        TakeOwnershipMessage.Response handle(NodePrincipal p, TakeOwnershipMessage        msg) throws ProtocolError, TakeOwnershipFailedException;
  public InterWorkerStalenessMessage.Response handle(NodePrincipal p, InterWorkerStalenessMessage msg) throws ProtocolError;
}
