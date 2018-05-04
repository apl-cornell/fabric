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
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
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
  public final LongKeyMap<LongSet> associatedOnums;

  private ObjectUpdateMessage(String store, LongKeyMap<ObjectGlob> globs,
      List<Long> onums, List<ObjectGroup> groups,
      LongKeyMap<LongSet> associatedOnums) {
    super(MessageType.OBJECT_UPDATE, NoException.class);
    this.store = store;
    this.globs = globs;
    this.onums = onums;
    this.groups = groups;
    this.associatedOnums = associatedOnums;

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
  public ObjectUpdateMessage(String store, LongKeyMap<ObjectGlob> updates,
      LongKeyMap<LongSet> associatedOnums) {
    this(store, updates, null, null, associatedOnums);
  }

  /**
   * Creates an object-update notification to be sent to a worker node.
   *
   * @param onums
   *          the onums being updated to which the worker has subscribed.
   * @param updates
   *          the set of object updates.
   */
  public ObjectUpdateMessage(List<Long> onums, List<ObjectGroup> updates,
      LongKeyMap<LongSet> associatedOnums) {
    this(null, null, onums, updates, associatedOnums);
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
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws ProtocolError {
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
    out.writeInt(associatedOnums.size());
    for (LongKeyMap.Entry<LongSet> e : associatedOnums.entrySet()) {
      out.writeLong(e.getKey());
      out.writeInt(e.getValue().size());
      for (LongIterator iter = e.getValue().iterator(); iter.hasNext();) {
        out.writeLong(iter.next());
      }
    }
  }

  /* readMessage */
  protected ObjectUpdateMessage(DataInput in) throws IOException {
    super(MessageType.OBJECT_UPDATE, NoException.class);

    int size = 0;
    if (in.readBoolean()) {
      store = null;
      globs = null;

      size = in.readInt();
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

      size = in.readInt();
      globs = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++) {
        long key = in.readLong();
        ObjectGlob glob = new ObjectGlob(in);

        globs.put(key, glob);
      }
    }

    size = in.readInt();
    associatedOnums = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      int size2 = in.readInt();
      LongSet s = new LongHashSet();
      for (int j = 0; j < size2; j++) {
        s.add(in.readLong());
      }
      associatedOnums.put(onum, s);
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
