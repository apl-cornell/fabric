package fabric.messages;

import java.io.*;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.TransactionCommitFailedException;
import fabric.client.UnreachableNodeException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.common.ProtocolError;
import fabric.core.Worker;

public class CommitTransactionMessage extends
    Message<RemoteCore, CommitTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public Response() {
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param core
     *                The core from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(Core core, DataInput in) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) {
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

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws TransactionCommitFailedException,
      ProtocolError {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core, boolean)
   */
  public Response send(RemoteCore core) throws UnreachableNodeException,
      TransactionCommitFailedException {
    try {
      return super.send(core, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (TransactionCommitFailedException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  @Override
  public Response response(RemoteCore c, DataInput in) {
    return new Response(c, in);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(transactionID);
  }

}
