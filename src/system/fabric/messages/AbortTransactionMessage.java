package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.RemoteNode;
import fabric.common.*;
import fabric.common.InternalError;

public class AbortTransactionMessage extends
    Message<RemoteNode, AbortTransactionMessage.Response> {

  public static class Response implements Message.Response {
    private Response() {
    }

    /**
     * Deserialization constructor, used by the client.
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

  public final TransactionID tid;

  public AbortTransactionMessage(TransactionID tid) {
    super(MessageType.ABORT_TRANSACTION);
    this.tid = tid;
  }

  /**
   * Deserialization constructor.
   */
  protected AbortTransactionMessage(DataInput in) throws IOException {
    this(new TransactionID(in));
  }

  @Override
  public Response dispatch(fabric.core.Worker w) throws AccessException,
      ProtocolError {
    w.handle(this);
    return new Response();
  }

  @Override
  public Response dispatch(fabric.client.remote.Worker handler) {
    handler.handle(this);
    return new Response();
  }

  public Response send(RemoteNode node) {
    try {
      return send(node, true);
    } catch (FabricException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public Response response(RemoteNode node, DataInput in) {
    return new Response(node, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
  }

}
