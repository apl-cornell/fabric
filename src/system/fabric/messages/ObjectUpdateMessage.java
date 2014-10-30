package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.ObjectGlob;
import fabric.worker.remote.RemoteWorker;

/**
 * Represents push notification that an object has been updated.
 */
public class ObjectUpdateMessage extends
    Message<ObjectUpdateMessage.Response, fabric.messages.Message.NoException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final String store;
  public final LongKeyMap<ObjectGlob> globs;
  public final List<Long> onums;
  public final List<ObjectGroup> groups;

  private ObjectUpdateMessage(String store, LongKeyMap<ObjectGlob> globs,
      List<Long> onums, List<ObjectGroup> groups) {
    super(MessageType.OBJECT_UPDATE, NoException.class);
    this.store = store;
    this.globs = globs;
    this.onums = onums;
    this.groups = groups;

    // Exactly one of glob and group needs to be null.
    if ((globs == null) == (groups == null)) {
      throw new InternalError();
    }
  }

  /**
   * Creates an object-update notification to be sent to a dissemination node.
   *
   * @param store
   *          the store from which the notification originated.
   * @param updates
   *          the set of encrypted object updates, keyed by the head object's
   *          onum.
   */
  public ObjectUpdateMessage(String store, LongKeyMap<ObjectGlob> updates) {
    this(store, updates, null, null);
  }

  /**
   * Creates an object-update notification to be sent to a worker node.
   *
   * @param onums
   *          the onums being updated to which the worker has subscribed.
   * @param updates
   *          the set of object updates.
   */
  public ObjectUpdateMessage(List<Long> onums, List<ObjectGroup> updates) {
    this(null, null, onums, updates);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<Long> resubscriptions;

    public Response(List<Long> resubscribes) {
      this.resubscriptions = resubscribes;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    if (store == null) {
      out.writeBoolean(true);

      out.writeInt(onums.size());
      for (long onum : onums) {
        out.writeLong(onum);
      }

      out.writeInt(groups.size());
      for (ObjectGroup group : groups) {
        group.write(out);
      }
    } else {
      out.writeBoolean(false);

      out.writeUTF(store);

      out.writeInt(globs.size());
      for (LongKeyMap.Entry<ObjectGlob> entry : globs.entrySet()) {
        out.writeLong(entry.getKey());
        entry.getValue().write(out);
      }
    }
  }

  /* readMessage */
  protected ObjectUpdateMessage(DataInput in) throws IOException {
    super(MessageType.OBJECT_UPDATE, NoException.class);

    if (in.readBoolean()) {
      store = null;
      globs = null;

      int size = in.readInt();
      onums = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        onums.add(in.readLong());
      }

      size = in.readInt();
      groups = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        groups.add(new ObjectGroup(in));
      }
    } else {
      onums = null;
      groups = null;

      store = in.readUTF();

      int size = in.readInt();
      globs = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++) {
        long key = in.readLong();
        ObjectGlob glob = new ObjectGlob(in);

        globs.put(key, glob);
      }
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.resubscriptions.size());
    for (long onum : r.resubscriptions) {
      out.writeLong(onum);
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<Long> resubscribes = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      resubscribes.add(in.readLong());
    }

    return new Response(resubscribes);
  }

}
