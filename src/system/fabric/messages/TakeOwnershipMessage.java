package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/**
 * Represents a request from a worker to take ownership of an object from
 * another worker.
 */
public class TakeOwnershipMessage
     extends Message<TakeOwnershipMessage.Response>
  implements MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  public final Store store;
  public final long onum;

  public TakeOwnershipMessage(TransactionID tid, Store store, long onum) {
    super(MessageType.TAKE_OWNERSHIP);

    this.tid = tid;
    this.store = store;
    this.onum = onum;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final boolean success;

    public Response(boolean success) {
      this.success = success;
    }

  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    tid.write(out);
    out.writeUTF(store.name());
    out.writeLong(onum);
  }

  /* readMessage */
  protected TakeOwnershipMessage(DataInput in) throws IOException {
    this(new TransactionID(in), Worker.getWorker().getStore(in.readUTF()), in
        .readLong());
  }

  @Override
  public void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.success);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response(in.readBoolean());
  }

}
