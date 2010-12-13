package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.Object._Impl;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Represents a request from a worker to read an object owned by another worker.
 */
public class DirtyReadMessage extends
    Message<DirtyReadMessage.Response, AccessException> {
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  public final Store store;
  public final long onum;

  public DirtyReadMessage(TransactionID tid, Store store, long onum) {
    super(MessageType.DIRTY_READ, AccessException.class);

    this.tid = tid;
    this.store = store;
    this.onum = onum;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final _Impl obj;

    public Response(_Impl obj) {
      this.obj = obj;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(NodePrincipal p, MessageHandler h)
      throws ProtocolError, AccessException {
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
  protected DirtyReadMessage(DataInput in) throws IOException {
    this(new TransactionID(in), Worker.getWorker().getStore(in.readUTF()), in
        .readLong());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.obj != null) {
      out.writeBoolean(true);
      out.writeUTF(r.obj.$getStore().name());
      SerializedObject.write(r.obj, out);
    } else {
      out.writeBoolean(false);
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    if (!in.readBoolean()) {
      return new Response(null);
    }

    Store store = Worker.getWorker().getStore(in.readUTF());
    SerializedObject serialized = new SerializedObject(in);
    _Impl deserialized = serialized.deserialize(store);

    return new Response(deserialized);
  }

}
