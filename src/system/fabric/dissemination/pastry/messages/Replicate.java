package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.util.Base64;
import fabric.common.Crypto;
import fabric.common.SerializationUtil;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.dissemination.pastry.Disseminator;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * This represents a Replicate message in the beehive system. Node A sends node
 * B a replicate message with level i to request that B push objects with
 * replication level i or lower to A. B is the level i decider for A.
 */
public class Replicate extends AbstractRawMessage {

  private transient final NodeHandle sender;
  private final int level;
  private final OidKeyHashMap<Long> skip;

  public Replicate(NodeHandle sender, int level, OidKeyHashMap<Long> skip) {
    super(RawMessageType.REPLICATE);
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
  public void dispatch(Disseminator disseminator) {
    disseminator.replicate(this);
  }

  @Override
  public String toString() {
    // Instead of including the string representation of skip, use a secure
    // hash. Pastry calls toString() when this message is sent and uses the
    // result as a key in a hash map. Using the secure hash avoids creating an
    // overly large string that would then need to be GCed.
    return "Replicate " + level + " [skipDigest=" + skipDigest() + "]";
  }

  private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  private String skipDigest() {
    try {
      byte[] buf = new byte[256];
      MessageDigest md = Crypto.digestInstance();
      for (Store store : skip.storeSet()) {
        md.update(store.name().getBytes(UTF8_CHARSET));

        for (LongKeyMap.Entry<Long> entry : skip.get(store).entrySet()) {
          SerializationUtil.setLongAt(buf, 0, entry.getKey());
          SerializationUtil.setLongAt(buf, 8, entry.getValue());
          md.update(buf, 0, 16);
        }
      }

      md.digest(buf, 0, 256);
      return Base64.encodeBytes(buf);
    } catch (DigestException e) {
      throw new InternalError(e);
    }
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
    super(RawMessageType.REPLICATE);

    Worker worker = Worker.getWorker();
    this.sender = sender;
    level = buf.readInt();
    int numStores = buf.readInt();
    skip = new OidKeyHashMap<>();

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
  public static class Reply extends AbstractRawMessage {

    private final Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyGlob>> globs;

    public Reply(
        Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyGlob>> globs) {
      super(RawMessageType.REPLICATE_REPLY);
      this.globs = globs;
    }

    public Map<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyGlob>> globs() {
      return globs;
    }

    @Override
    public int getPriority() {
      return MEDIUM_PRIORITY;
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
    public void dispatch(Disseminator disseminator) {
      disseminator.replicate(this);
    }

    @Override
    public void serialize(OutputBuffer buf) throws IOException {
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeInt(globs.size());

      for (Entry<Pair<RemoteStore, Long>, Pair<ObjectGlob, WarrantyGlob>> e : globs
          .entrySet()) {
        out.writeUTF(e.getKey().first.name());
        out.writeLong(e.getKey().second);

        Pair<ObjectGlob, WarrantyGlob> globs = e.getValue();
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
      super(RawMessageType.REPLICATE_REPLY);

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
          WarrantyGlob warrantyGlob =
              in.readBoolean() ? new WarrantyGlob(in) : null;

          // Verify signatures.
          objectGlob.verifySignature(store.getPublicKey());

          if (warrantyGlob != null) {
            try {
              warrantyGlob.verifySignature(store.getPublicKey());
            } catch (GeneralSecurityException e) {
              // Warranty glob was corrupted, so ignore it.
              warrantyGlob = null;
            }
          }

          globs.put(new Pair<>(store, onum), new Pair<>(objectGlob,
              warrantyGlob));
        } catch (GeneralSecurityException e) {
          // Object glob was corrupted. Ignore this group completely.
        }
      }
    }

  }

}
