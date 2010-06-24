package fabric.messages;

import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/**
 * This interface acts as a visitor for MessagesToStore.  It also documents the
 * list of all messages that a store needs to be able to handle. The methods all
 * have the form:<br>
 * 
 * <pre>
 * public Response handle(NodePrincipal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message that the worker handles.
 * 
 * @author mdgeorge
 */
public interface MessageToStoreHandler {
  public   AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage   msg) throws FabricException;
  public           AllocateMessage.Response handle(NodePrincipal p, AllocateMessage           msg) throws FabricException;
  public  CommitTransactionMessage.Response handle(NodePrincipal p, CommitTransactionMessage  msg) throws FabricException;
  public         DissemReadMessage.Response handle(NodePrincipal p, DissemReadMessage         msg) throws FabricException;
  public       GetCertChainMessage.Response handle(NodePrincipal p, GetCertChainMessage       msg) throws FabricException;
  public PrepareTransactionMessage.Response handle(NodePrincipal p, PrepareTransactionMessage msg) throws FabricException;
  public               ReadMessage.Response handle(NodePrincipal p, ReadMessage               msg) throws FabricException;
  public      MakePrincipalMessage.Response handle(NodePrincipal p, MakePrincipalMessage      msg) throws FabricException;
}
