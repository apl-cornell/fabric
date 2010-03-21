package fabric.worker.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.MessageHandlerThread;
import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;

/**
 * Represents a request from a worker to take ownership of an object from
 * another worker.
 */
public class TakeOwnershipMessage extends
    InterWorkerMessage<TakeOwnershipMessage.Response> {

  public final TransactionID tid;
  public final Store store;
  public final long onum;

  public static class Response implements Message.Response {
    public final boolean success;

    public Response(boolean success) {
      this.success = success;
    }

    Response(DataInput in) throws IOException {
      this.success = in.readBoolean();
    }

    public void write(DataOutput out) throws IOException {
      out.writeBoolean(success);
    }
  }

  public TakeOwnershipMessage(TransactionID tid, Store store, long onum) {
    super(MessageType.TAKE_OWNERSHIP);

    this.tid = tid;
    this.store = store;
    this.onum = onum;
  }

  public TakeOwnershipMessage(DataInput in) throws IOException {
    this(new TransactionID(in), Worker.getWorker().getStore(in.readUTF()), in
        .readLong());
  }

  @Override
  public Response dispatch(MessageHandlerThread handler) throws ProtocolError {
    return handler.handle(this);
  }

  public Response send(RemoteWorker remoteWorker)
      throws UnreachableNodeException {
    try {
      return super.send(remoteWorker, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker.", e);
    }
  }

  @Override
  public Response response(RemoteWorker worker, DataInput in)
      throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
    out.writeUTF(store.name());
    out.writeLong(onum);
  }

}
