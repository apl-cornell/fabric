package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>WaitForUpdateMessage</code> represents a request from a worker to
 * wait for an update past the currently known version of an object on the
 * store.
 */
public class WaitForUpdateMessage
    extends Message<WaitForUpdateMessage.Response, AccessException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The onums and versions of the object to wait for. */
  public final LongKeyMap<Integer> onumsAndVersions;

  public WaitForUpdateMessage(LongKeyMap onumsAndVersions) {
    super(MessageType.WAIT_FOR_UPDATE, AccessException.class);
    this.onumsAndVersions = onumsAndVersions;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<SerializedObject> updates;

    public Response(List<SerializedObject> updates) {
      this.updates = updates;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws ProtocolError, AccessException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeInt(onumsAndVersions.size());
    for (LongKeyMap.Entry<Integer> e : onumsAndVersions.entrySet()) {
      out.writeLong(e.getKey());
      out.writeInt(e.getValue());
    }
  }

  /* readMessage */
  protected WaitForUpdateMessage(DataInput in) throws IOException {
    super(MessageType.WAIT_FOR_UPDATE, AccessException.class);
    int size = in.readInt();
    onumsAndVersions = new LongKeyHashMap<>();
    for (int i = 0; i < size; i++) {
      onumsAndVersions.put(in.readLong(), in.readInt());
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.updates.size());
    for (SerializedObject o : r.updates) {
      o.write(out);
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<SerializedObject> updates = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      updates.add(new SerializedObject(in));
    }
    return new Response(updates);
  }
}
