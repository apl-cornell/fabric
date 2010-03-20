package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.RemoteCore;
import fabric.worker.debug.Timing;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.net.RemoteNode;

public class UnauthenticatedAbortTransactionMessage extends
    Message<RemoteCore, UnauthenticatedAbortTransactionMessage.Response> {

  public static class Response implements Message.Response {
    private Response() {
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) {
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(DataOutput out) {
    }
  }

  /**
   * The tid for the transaction that is aborting.
   */
  public final TransactionID tid;

  public UnauthenticatedAbortTransactionMessage(TransactionID tid) {
    super(MessageType.UNAUTHENTICATED_ABORT_TRANSACTION);
    this.tid = tid;
  }

  /**
   * Deserialization constructor.
   */
  protected UnauthenticatedAbortTransactionMessage(DataInput in)
      throws IOException {
    this(new TransactionID(in));
  }

  @Override
  public Response dispatch(fabric.core.MessageHandlerThread w) throws AccessException {
    w.handle(this);
    return new Response();
  }

  public Response send(RemoteCore core) {
    try {
      Timing.CORE.begin();
      return send(core, true);
    } catch (FabricException e) {
      throw new InternalError(e);
    } finally {
      Timing.CORE.end();
    }
  }

  @Override
  public Response response(RemoteCore core, DataInput in) {
    return new Response(core, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
  }

}
