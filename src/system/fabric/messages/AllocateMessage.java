package fabric.messages;

import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.Worker;

/**
 * An <code>AllocateMessage</code> represents a request to allocate a number
 * of object IDs at a core.
 */
public final class AllocateMessage extends Message<AllocateMessage.Response> {

  public static class Response implements Message.Response {
    public long[] oids;

    public Response(long[] onums) {
      this.oids = onums;
    }
  }

  public int num;

  /**
   * @param num
   *          The number of object IDs to allocate.
   */
  public AllocateMessage(int num) {
    super(Response.class);
    this.num = num;
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
