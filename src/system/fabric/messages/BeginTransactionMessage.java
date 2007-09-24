package fabric.messages;

import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.Worker;

/**
 * A <code>BeginTransactionMessage</code> represents a request to start a new
 * (top-level) transaction at a core.
 */
public class BeginTransactionMessage extends
    Message<BeginTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final int transactionID;

    public Response(int transactionID) {
      this.transactionID = transactionID;
    }
  }

  /**
   * @param resultType
   */
  public BeginTransactionMessage() {
    super(Response.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws UnreachableCoreException {
    try {
      return super.send(core);
    } catch (UnreachableCoreException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

}
