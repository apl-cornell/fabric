package fabric.messages;

import java.io.*;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.FabricException;
import fabric.core.Worker;

public class AbortTransactionMessage extends
    Message<AbortTransactionMessage.Response> {

  public static class Response implements Message.Response {
    private Response() {
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
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(DataOutput out) {
    }
  }

  public final int transactionID;

  public AbortTransactionMessage(int transactionID) {
    super(MessageType.ABORT_TRANSACTION);
    this.transactionID = transactionID;
  }

  /**
   * Deserialization constructor.
   */
  protected AbortTransactionMessage(DataInput in) throws IOException {
    this(in.readInt());
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

  @Override
  public Response response(Core c, DataInput in) {
    return new Response(c, in);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.ObjectOutputStream)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(transactionID);
  }

}
