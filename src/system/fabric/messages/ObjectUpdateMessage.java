package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
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
  public final Map<ObjectGlob, LongSet> globs;
  public final LongSet onums;
  public final Collection<ObjectGroup> groups;

  /**
   * Creates an object-update notification to be sent to a dissemination node.
   *
   * @param store
   *          the store from which the notification originated.
   * @param globs
   *          the set of encrypted object updates, mapped to the set of onums
   *          contained that the store believes the worker is subscribed to.
   * @param onums
   *          the unencrypted onums being updated to which the worker has
   *          subscribed.
   * @param groups
   *          the set of object updates.
   */
  public ObjectUpdateMessage(String store, Map<ObjectGlob, LongSet> globs,
      LongSet onums, Collection<ObjectGroup> groups) {
    super(MessageType.OBJECT_UPDATE);
    this.store = store;
    this.globs = globs;
    this.onums = onums;
    this.groups = groups;
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
    // Store
    out.writeUTF(store);

    // Globs
    out.writeInt(globs.size());
    for (Map.Entry<ObjectGlob, LongSet> entry : globs.entrySet()) {
      entry.getKey().write(out);
      out.writeInt(entry.getValue().size());
      for (LongIterator iter = entry.getValue().iterator(); iter.hasNext();) {
        out.writeLong(iter.next());
      }
    }

    // Onums
    out.writeInt(onums.size());
    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      out.writeLong(iter.next());
    }

    // Groups
    out.writeInt(groups.size());
    for (ObjectGroup group : groups) {
      group.write(out);
    }
  }

  /* readMessage */
  protected ObjectUpdateMessage(DataInput in) throws IOException {
    super(MessageType.OBJECT_UPDATE);
    int size = 0;

    // Store
    store = in.readUTF();

    // Globs
    size = in.readInt();
    globs = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      ObjectGlob key = new ObjectGlob(in);
      int size2 = in.readInt();
      LongSet values = new LongHashSet(size2);
      for (int j = 0; j < size2; j++) {
        values.add(in.readLong());
      }
      globs.put(key, values);
    }

    // Onums
    size = in.readInt();
    onums = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      onums.add(in.readLong());
    }

    // Groups
    size = in.readInt();
    groups = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      groups.add(new ObjectGroup(in));
    }
  }
}
