package fabric.messages;

import fabric.common.exceptions.FabricException;

/**
 * This interface acts as a visitor for MessagesToStore.  It also documents the
 * list of all messages that a store needs to be able to handle.
 *
 * @author mdgeorge
 */
public interface MessageToStoreHandler {
  public   AbortTransactionMessage.Response handle(AbortTransactionMessage   msg) throws FabricException;
  public           AllocateMessage.Response handle(AllocateMessage           msg) throws FabricException;
  public  CommitTransactionMessage.Response handle(CommitTransactionMessage  msg) throws FabricException;
  public         DissemReadMessage.Response handle(DissemReadMessage         msg) throws FabricException;
  public       GetCertChainMessage.Response handle(GetCertChainMessage       msg) throws FabricException;
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg) throws FabricException;
  public               ReadMessage.Response handle(ReadMessage               msg) throws FabricException;
}
