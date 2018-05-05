package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.ObjectGroup;
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
 * Represents push notification that some objects have been updated.
 */
public class ObjectUpdateMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final String store;
  public final LongKeyMap<ObjectGlob> globs;
  public final LongSet onums;
  public final List<ObjectGroup> groups;
  public final LongKeyMap<LongSet> associatedOnums;

  /**
   * Creates an object-update notification to be sent to a dissemination node.
   *
   * @param store
   *          the store from which the notification originated.
   * @param globs
   *          the set of encrypted object updates, keyed by the head object's
   *          onum.
   * @param onums
   *          the unencrypted onums being updated to which the worker has
   *          subscribed.
   * @param groups
   *          the set of object updates.
   */
  public ObjectUpdateMessage(String store, LongKeyMap<ObjectGlob> globs,
      LongSet onums, List<ObjectGroup> groups,
      LongKeyMap<LongSet> associatedOnums) {
    super(MessageType.OBJECT_UPDATE);
    this.store = store;
    this.globs = globs;
    this.onums = onums;
    this.groups = groups;
    this.associatedOnums = associatedOnums;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public void dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeUTF(store);

    out.writeInt(globs.size());
    for (LongKeyMap.Entry<ObjectGlob> entry : globs.entrySet()) {
      out.writeLong(entry.getKey());
      entry.getValue().write(out);
    }

    out.writeInt(onums.size());
    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      out.writeLong(iter.next());
    }

    out.writeInt(groups.size());
    for (ObjectGroup group : groups) {
      group.write(out);
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
    super(MessageType.OBJECT_UPDATE);

    int size = 0;

    store = in.readUTF();

    size = in.readInt();
    globs = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long key = in.readLong();
      ObjectGlob glob = new ObjectGlob(in);

      globs.put(key, glob);
    }

    size = in.readInt();
    onums = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      onums.add(in.readLong());
    }

    size = in.readInt();
    groups = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      groups.add(new ObjectGroup(in));
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
}
