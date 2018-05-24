package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fabric.common.SerializedObject;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>TreatyExtensionMessage</code> represents a request to
 * refresh a treaty's expiry at the store.
 */
public class TreatyExtensionMessage extends AsyncMessage {
  /* message contents */

  /**
   * The extensions to be run (onums)
   */
  public final LongSet extensions;

  /**
   * The various updated values that sparked these extensions.
   */
  public final Map<RemoteStore, Collection<SerializedObject>> updates;

  /**
   * Used to refresh expiries of treaties.
   */
  public TreatyExtensionMessage(LongSet extensions,
      Map<RemoteStore, Collection<SerializedObject>> updates) {
    super(MessageType.TREATY_EXTENSION);
    this.extensions = extensions;
    this.updates = updates;
  }

  @Override
  public void dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    h.handle(client, this);
  }

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize onum.
    out.writeInt(extensions.size());
    for (LongIterator it = extensions.iterator(); it.hasNext();) {
      out.writeLong(it.next());
    }

    out.writeInt(updates.size());
    for (Map.Entry<RemoteStore, Collection<SerializedObject>> e : updates
        .entrySet()) {
      out.writeUTF(e.getKey().name());
      out.writeInt(e.getValue().size());
      for (SerializedObject o : e.getValue()) {
        o.write(out);
      }
    }
  }

  /* readMessage */
  protected TreatyExtensionMessage(DataInput in) throws IOException {
    super(MessageType.TREATY_EXTENSION);
    int size = in.readInt();
    extensions = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      extensions.add(onum);
    }

    size = in.readInt();
    updates = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      RemoteStore s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      List<SerializedObject> vals = new ArrayList<>(size);
      for (int j = 0; j < size2; j++) {
        vals.add(new SerializedObject(in));
      }
      updates.put(s, vals);
    }
  }
}
