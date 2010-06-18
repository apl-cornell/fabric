package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

public class CommitTransactionMessage
     extends Message<CommitTransactionMessage.Response>
  implements MessageToStore, MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final long transactionID;

  public CommitTransactionMessage(long transactionID) {
    super(MessageType.COMMIT_TRANSACTION);
    this.transactionID = transactionID;
  }


  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

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
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler h) throws FabricException {
    return h.handle(p, this);
  }

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(transactionID);
  }

  /* readMessage */
  protected CommitTransactionMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.success);
    if (r.message != null) {
      out.writeBoolean(true);
      out.writeUTF(r.message);
    }
    else
      out.writeBoolean(false);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    boolean success = in.readBoolean();
    if (in.readBoolean())
      return new Response(success, in.readUTF());
    else 
      return new Response(success);
  }

}
