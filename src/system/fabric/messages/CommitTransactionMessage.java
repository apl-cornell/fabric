package fabric.messages;

import fabric.client.RemoteCore;
import fabric.client.TransactionCommitFailedException;
import fabric.client.UnreachableCoreException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.Worker;

public class CommitTransactionMessage extends
    Message<CommitTransactionMessage.Response> {

  public static class Response implements Message.Response {
  }

  public final int transactionID;
  
  public CommitTransactionMessage(int transactionID) {
    super(MessageType.COMMIT_TRANSACTION, Response.class);
    this.transactionID = transactionID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws TransactionCommitFailedException {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws UnreachableCoreException, TransactionCommitFailedException {
    try {
      return super.send(core);
    } catch (UnreachableCoreException e) {
      throw e;
    } catch (TransactionCommitFailedException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

}
