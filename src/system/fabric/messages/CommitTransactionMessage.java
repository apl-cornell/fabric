package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.debug.Timing;
import fabric.client.remote.MessageHandlerThread;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

public class CommitTransactionMessage extends
    Message<RemoteNode, CommitTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final boolean success;
    public final String message;

    public Response(boolean success) {
      this(success, null);
    }

    public Response(boolean success, String message) {
      this.success = success;
      this.message = message;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) throws IOException {
      this.success = in.readBoolean();
      if (in.readBoolean())
        this.message = in.readUTF();
      else this.message = null;
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(success);
      if (message != null) {
        out.writeBoolean(true);
        out.writeUTF(message);
      } else out.writeBoolean(false);
    }
  }

  public final long transactionID;

  public CommitTransactionMessage(long transactionID) {
    super(MessageType.COMMIT_TRANSACTION);
    this.transactionID = transactionID;
  }

  /**
   * Deserialization constructor.
   */
  protected CommitTransactionMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  public Response dispatch(fabric.core.MessageHandlerThread w) throws ProtocolError {
    return w.handle(this);
  }

  @Override
  public Response dispatch(MessageHandlerThread handler) throws ProtocolError {
    return handler.handle(this);
  }

  public Response send(RemoteNode node) throws UnreachableNodeException {
    try {
      Timing.CORE.begin();
      return super.send(node, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.CORE.end();
    }
  }

  @Override
  public Response response(RemoteNode node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  /*
   * (non-Javadoc)
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(transactionID);
  }

}
