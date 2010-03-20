package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.RemoteCore;
import fabric.worker.debug.Timing;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

public class UnauthenticatedCommitTransactionMessage extends
    Message<RemoteCore, UnauthenticatedCommitTransactionMessage.Response> {

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
     * Deserialization constructor, used by the worker.
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

  public UnauthenticatedCommitTransactionMessage(long transactionID) {
    super(MessageType.UNAUTHENTICATED_COMMIT_TRANSACTION);
    this.transactionID = transactionID;
  }

  /**
   * Deserialization constructor.
   */
  protected UnauthenticatedCommitTransactionMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  public Response dispatch(fabric.core.MessageHandlerThread w) {
    return w.handle(this);
  }

  public Response send(RemoteCore core) throws UnreachableNodeException {
    try {
      Timing.CORE.begin();
      return super.send(core, false);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.CORE.end();
    }
  }

  @Override
  public Response response(RemoteCore core, DataInput in) throws IOException {
    return new Response(core, in);
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
