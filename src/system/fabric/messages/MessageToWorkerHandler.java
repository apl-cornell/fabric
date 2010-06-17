package fabric.messages;

import fabric.common.exceptions.FabricException;


public interface MessageToWorkerHandler {
  public   AbortTransactionMessage.Response handle(AbortTransactionMessage   msg) throws FabricException;
  public  CommitTransactionMessage.Response handle(CommitTransactionMessage  msg) throws FabricException;
  public       ObjectUpdateMessage.Response handle(ObjectUpdateMessage       msg) throws FabricException;
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg) throws FabricException;
  public          DirtyReadMessage.Response handle(DirtyReadMessage          msg) throws FabricException;
  public       GetPrincipalMessage.Response handle(GetPrincipalMessage       msg) throws FabricException;
  public         RemoteCallMessage.Response handle(RemoteCallMessage         msg) throws FabricException;
  public      TakeOwnershipMessage.Response handle(TakeOwnershipMessage      msg) throws FabricException;
}
