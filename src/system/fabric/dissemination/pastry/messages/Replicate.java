package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.exceptions.BadSignatureException;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;

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

  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  public short getType() {
    return MessageType.REPLICATE;
  }

  @Override
  public String toString() {
    String s = "Replicate " + level + " [";

    for (Core core : skip.coreSet()) {
      for (LongKeyMap.Entry<Long> entry : skip.get(core).entrySet()) {
        s += "(" + core + ", " + entry.getKey() + ", " + entry.getValue() + ")";
      }
    }

    return s + "]";
  }

  public void serialize(OutputBuffer buf) throws IOException {
    buf.writeInt(level);
    
    Set<Core> coreSet = skip.coreSet();
    buf.writeInt(coreSet.size());

    for (Core core : coreSet) {
      LongKeyMap<Long> submap = skip.get(core);
      buf.writeUTF(core.name());
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
    Client client = Client.getClient();
    this.sender = sender;
    level = buf.readInt();
    int numCores = buf.readInt();
    skip = new OidKeyHashMap<Long>();

    for (int i = 0; i < numCores; i++) {
      Core core = client.getCore(buf.readUTF());
      int numEntries = buf.readInt();
      
      for (int j = 0; j < numEntries; j++) {
        skip.put(core, buf.readLong(), buf.readLong());
      }
    }
  }

  /**
   * A reply to a replicate message, carrying the requested objects. This is
   * sent by a decider node which decides what objects the sender of the
   * replicate message should receive base on object popularity.
   */
  public static class Reply implements RawMessage {

    private final Map<Pair<Core, Long>, Glob> globs;

    public Reply(Map<Pair<Core, Long>, Glob> globs) {
      this.globs = globs;
    }

    public Map<Pair<Core, Long>, Glob> globs() {
      return globs;
    }

    public int getPriority() {
      return MEDIUM_PRIORITY;
    }

    public short getType() {
      return MessageType.REPLICATE_REPLY;
    }

    @Override
    public String toString() {
      String s = "Replicate.Reply [";

      for (Pair<Core, Long> p : globs.keySet()) {
        s = s + p;
      }

      return s + "]";
    }

    public void serialize(OutputBuffer buf) throws IOException {
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeInt(globs.size());

      for (Map.Entry<Pair<Core, Long>, Glob> e : globs.entrySet()) {
        out.writeUTF(e.getKey().first.name());
        out.writeLong(e.getKey().second);
        e.getValue().write(out);
      }
    }

    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf) throws IOException {
      DataInputBuffer in = new DataInputBuffer(buf);
      Client client = Client.getClient();
      int n = in.readInt();
      globs = new HashMap<Pair<Core, Long>, Glob>(n);

      for (int i = 0; i < n; i++) {
        RemoteCore core = client.getCore(in.readUTF());
        long onum = in.readLong();
        try {
          Glob g = new Glob(core.getPublicKey(), in);
          globs.put(new Pair<Core, Long>(core, onum), g);
        } catch (BadSignatureException e) {
        }
      }
    }

  }

}
