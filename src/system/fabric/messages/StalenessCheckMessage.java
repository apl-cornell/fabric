package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.RWLease;
import fabric.common.SerializedObject;
import fabric.common.SerializedObjectAndTokens;
import fabric.common.VersionWarranty;
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
public final class StalenessCheckMessage extends
    Message<StalenessCheckMessage.Response, AccessException> {

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final LongKeyMap<Integer> versions;

  public StalenessCheckMessage(LongKeyMap<Integer> versions) {
    super(MessageType.STALENESS_CHECK, AccessException.class);
    this.versions = versions;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<SerializedObjectAndTokens> staleObjects;

    public Response(List<SerializedObjectAndTokens> staleObjects) {
      this.staleObjects = staleObjects;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError, AccessException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeInt(versions.size());
    for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
      out.writeLong(entry.getKey());
      out.writeInt(entry.getValue());
    }
  }

  /* readMessage */
  protected StalenessCheckMessage(DataInput in) throws IOException {
    this(readMap(in));
  }

  /* helper method for deserialization constructor */
  private static LongKeyHashMap<Integer> readMap(DataInput in)
      throws IOException {
    int size = in.readInt();
    LongKeyHashMap<Integer> versions = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++)
      versions.put(in.readLong(), in.readInt());

    return versions;
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.staleObjects.size());
    for (SerializedObjectAndTokens obj : r.staleObjects) {
      obj.getSerializedObject().write(out);
      out.writeLong(obj.getWarranty().expiry());
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<SerializedObjectAndTokens> staleObjects = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      SerializedObject obj = new SerializedObject(in);
      VersionWarranty warranty = new VersionWarranty(in.readLong());
      //TODO: Change it so that lease is included in network protocol
      staleObjects.add(new SerializedObjectAndTokens(obj, warranty,
          new RWLease(0)));
    }

    return new Response(staleObjects);
  }

}
