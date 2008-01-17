package fabric.messages;

import fabric.client.RemoteCore;
import fabric.common.FabricException;
import fabric.core.Worker;

public class AbortTransactionMessage extends
    Message<AbortTransactionMessage.Response> {

  public static class Response implements Message.Response {
  }

  public final int transactionID;
  
  public AbortTransactionMessage(int transactionID) {
    super(MessageType.ABORT_TRANSACTION, Response.class);
    this.transactionID = transactionID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) {
    w.handle(this);
    return new Response();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) {
    try {
      return super.send(core);
    } catch (FabricException e) {
      // Nothing to do here. Sending abort messages is more of a courtesy than
      // anything.
      return new Response();
    }
  }

}
