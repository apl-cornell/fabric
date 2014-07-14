package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.TransactionID;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TakeOwnershipFailedException;

/**
 * Represents a request from a worker to take ownership of an object from
 * another worker.
 */
public class TakeOwnershipMessage extends
Message<TakeOwnershipMessage.Response, TakeOwnershipFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  public final Store store;
  public final long onum;

  public TakeOwnershipMessage(TransactionID tid, Store store, long onum) {
    super(MessageType.TAKE_OWNERSHIP, TakeOwnershipFailedException.class);

    this.tid = tid;
    this.store = store;
    this.onum = onum;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> worker, MessageHandler h)
      throws ProtocolError, TakeOwnershipFailedException {
    return h.handle(worker, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

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
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response();
  }

}
