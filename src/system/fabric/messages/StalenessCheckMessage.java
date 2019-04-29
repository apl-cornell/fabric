package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.ReadVersion;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>StalenessCheckMessage</code> represents a request to a store to check
 * whether a given set of objects is still fresh.
 */
public final class StalenessCheckMessage
    extends Message<StalenessCheckMessage.Response, AccessException> {

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final LongKeyMap<ReadVersion> versions;

  public StalenessCheckMessage(LongKeyMap<ReadVersion> versions) {
    super(MessageType.STALENESS_CHECK, AccessException.class);
    this.versions = versions;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<SerializedObject> staleObjects;

    public Response(List<SerializedObject> staleObjects) {
      this.staleObjects = staleObjects;
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
    out.writeInt(versions.size());
    for (LongKeyMap.Entry<ReadVersion> entry : versions.entrySet()) {
      out.writeLong(entry.getKey());
      entry.getValue().write(out);
    }
  }

  /* readMessage */
  protected StalenessCheckMessage(DataInput in) throws IOException {
    this(readMap(in));
  }

  /* helper method for deserialization constructor */
  private static LongKeyHashMap<ReadVersion> readMap(DataInput in)
      throws IOException {
    int size = in.readInt();
    LongKeyHashMap<ReadVersion> versions = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++)
      versions.put(in.readLong(), new ReadVersion(in));

    return versions;
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.staleObjects.size());
    for (SerializedObject obj : r.staleObjects) {
      obj.write(out);
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<SerializedObject> staleObjects = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      staleObjects.add(new SerializedObject(in));
    }

    return new Response(staleObjects);
  }

}
