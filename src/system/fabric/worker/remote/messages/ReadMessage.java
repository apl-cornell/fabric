package fabric.worker.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.MessageHandlerThread;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.Object._Impl;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;

/**
 * Represents a request from a worker to read an object owned by another worker.
 */
public class ReadMessage extends InterWorkerMessage<ReadMessage.Response> {

  public final TransactionID tid;
  public final Store store;
  public final long onum;

  public static class Response implements Message.Response {

    public final _Impl obj;

    public Response(_Impl obj) {
      this.obj = obj;
    }

    Response(DataInput in) throws IOException {
      if (!in.readBoolean()) {
        this.obj = null;
        return;
      }

      Store store = Worker.getWorker().getStore(in.readUTF());
      SerializedObject serialized = new SerializedObject(in);
      this.obj = serialized.deserialize(store);
    }

    public void write(DataOutput out) throws IOException {
      if (obj != null) {
        out.writeBoolean(true);
        out.writeUTF(obj.$getStore().name());
        SerializedObject.write(obj, out);
      } else {
        out.writeBoolean(false);
      }
    }
  }

  public ReadMessage(TransactionID tid, Store store, long onum) {
    super(MessageType.INTERWORKER_READ);

    this.tid = tid;
    this.store = store;
    this.onum = onum;
  }

  public ReadMessage(DataInput in) throws IOException {
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
