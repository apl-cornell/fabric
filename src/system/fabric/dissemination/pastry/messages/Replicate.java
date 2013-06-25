package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyRefreshGlob;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * This represents a Replicate message in the beehive system. Node A sends node
 * B a replicate message with level i to request that B push objects with
 * replication level i or lower to A. B is the level i decider for A.
 */
public class Replicate implements RawMessage {

  private transient final NodeHandle sender;
  private final int level;
  private final OidKeyHashMap<Long> skip;

  public Replicate(NodeHandle sender, int level, OidKeyHashMap<Long> skip) {
    this.sender = sender;
    this.level = level;
    this.skip = skip;
  }

  public NodeHandle sender() {
    return sender;
  }

  public int level() {
    return level;
  }

  public OidKeyHashMap<Long> skip() {
    return skip;
  }

  @Override
  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  @Override
  public short getType() {
    return MessageType.REPLICATE;
  }

  @Override
  public String toString() {
    String s = "Replicate " + level + " [";

    for (Store store : skip.storeSet()) {
      for (LongKeyMap.Entry<Long> entry : skip.get(store).entrySet()) {
        s +=
            "(" + store + ", " + entry.getKey() + ", " + entry.getValue() + ")";
      }
    }

    return s + "]";
  }

  @Override
  public void serialize(OutputBuffer buf) throws IOException {
    buf.writeInt(level);

    Set<Store> storeSet = skip.storeSet();
    buf.writeInt(storeSet.size());

    for (Store store : storeSet) {
      LongKeyMap<Long> submap = skip.get(store);
      buf.writeUTF(store.name());
      buf.writeInt(submap.size());

      for (LongKeyMap.Entry<Long> entry : submap.entrySet()) {
        buf.writeLong(entry.getKey());
        buf.writeLong(entry.getValue());
      }
    }
  }

  /**
   * Deserialization constructor.
   */
  public Replicate(InputBuffer buf, NodeHandle sender) throws IOException {
    Worker worker = Worker.getWorker();
    this.sender = sender;
    level = buf.readInt();
    int numStores = buf.readInt();
    skip = new OidKeyHashMap<Long>();

    for (int i = 0; i < numStores; i++) {
      Store store = worker.getStore(buf.readUTF());
      int numEntries = buf.readInt();

      for (int j = 0; j < numEntries; j++) {
        skip.put(store, buf.readLong(), buf.readLong());
      }
    }
  }

  /**
   * A reply to a replicate message, carrying the requested objects. This is
   * sent by a decider node which decides what objects the sender of the
   * replicate message should receive base on object popularity.
   */
  public static class Reply implements RawMessage {

    private final Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyRefreshGlob>> globs;

    public Reply(
        Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyRefreshGlob>> globs) {
      this.globs = globs;
    }

    public Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyRefreshGlob>> globs() {
      return globs;
    }

    @Override
    public int getPriority() {
      return MEDIUM_PRIORITY;
    }

    @Override
    public short getType() {
      return MessageType.REPLICATE_REPLY;
    }

    @Override
    public String toString() {
      String s = "Replicate.Reply [";

      for (Pair<RemoteStore, Long> p : globs.keySet()) {
        s = s + p;
      }

      return s + "]";
    }

    @Override
    public void serialize(OutputBuffer buf) throws IOException {
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeInt(globs.size());

      for (Entry<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyRefreshGlob>> e : globs
          .entrySet()) {
        out.writeUTF(e.getKey().first.name());
        out.writeLong(e.getKey().second);

        Pair<ObjectGlob, WarrantyRefreshGlob> globs = e.getValue();
        globs.first.write(out);

        if (globs.second == null) {
          out.writeBoolean(false);
        } else {
          out.writeBoolean(true);
          globs.second.write(out);
        }
      }
    }

    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf) throws IOException {
      DataInputBuffer in = new DataInputBuffer(buf);
      Worker worker = Worker.getWorker();
      int n = in.readInt();
      globs = new HashMap<>(n);

      for (int i = 0; i < n; i++) {
        RemoteStore store = worker.getStore(in.readUTF());
        long onum = in.readLong();
        try {
          // Read in entire entry.
          ObjectGlob objectGlob = new ObjectGlob(in);
          WarrantyRefreshGlob warrantyRefreshGlob =
              in.readBoolean() ? new WarrantyRefreshGlob(in) : null;

          // Verify signatures.
          objectGlob.verifySignature(store.getPublicKey());

          if (warrantyRefreshGlob != null) {
            try {
              warrantyRefreshGlob.verifySignature(store.getPublicKey());
            } catch (GeneralSecurityException e) {
              // Warranty-refresh glob was corrupted, so ignore it.
              warrantyRefreshGlob = null;
            }
          }

          globs.put(new Pair<>(store, onum), new Pair<>(objectGlob,
              warrantyRefreshGlob));
        } catch (GeneralSecurityException e) {
          // Object glob was corrupted. Ignore this group completely.
        }
      }
    }

  }

}
