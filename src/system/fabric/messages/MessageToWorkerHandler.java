package fabric.messages;

import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/**
 * This interface acts as a visitor for MessagesToWorker.  It also documents the
 * list of all messages that a worker needs to be able to handle. The methods
 * all have the form:
 * <br>
 * <pre>
 * public Response handle(NodePrincipal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message that the worker handles.
 * 
 * @author mdgeorge
 */
public interface MessageToWorkerHandler {
  public   AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage   msg) throws FabricException;
  public  CommitTransactionMessage.Response handle(NodePrincipal p, CommitTransactionMessage  msg) throws FabricException;
  public       ObjectUpdateMessage.Response handle(NodePrincipal p, ObjectUpdateMessage       msg) throws FabricException;
  public PrepareTransactionMessage.Response handle(NodePrincipal p, PrepareTransactionMessage msg) throws FabricException;
  public          DirtyReadMessage.Response handle(NodePrincipal p, DirtyReadMessage          msg) throws FabricException;
  public       GetPrincipalMessage.Response handle(NodePrincipal p, GetPrincipalMessage       msg) throws FabricException;
  public         RemoteCallMessage.Response handle(NodePrincipal p, RemoteCallMessage         msg) throws FabricException;
  public      TakeOwnershipMessage.Response handle(NodePrincipal p, TakeOwnershipMessage      msg) throws FabricException;
}
