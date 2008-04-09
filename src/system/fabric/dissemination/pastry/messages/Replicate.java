package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.common.Pair;
import fabric.dissemination.Glob;

/**
 * This represents a Replicate message in the beehive system. Node A sends node
 * B a replicate message with level i to request that B push objects with
 * replication level i or lower to A. B is the level i decider for A.
 */
public class Replicate implements RawMessage {

  private transient final NodeHandle sender;
  private final int level;
  
  public Replicate(NodeHandle sender, int level) {
    this.sender = sender;
    this.level = level;
  }
  
  public NodeHandle sender() {
    return sender;
  }
  
  public int level() {
    return level;
  }

  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  public short getType() {
    return MessageType.REPLICATE;
  }

  public void serialize(OutputBuffer buf) throws IOException {
    buf.writeInt(level);
  }
  
  /**
   * Deserialization constructor.
   */
  public Replicate(InputBuffer buf, NodeHandle sender) throws IOException {
    this.sender = sender;
    level = buf.readInt();
  }

  /**
   * A reply to a replicate message, carrying the requested objects.
   */
  public static class Reply implements RawMessage {
    
    private final Map<Pair<String, Long>, Glob> globs;
    
    public Reply(Map<Pair<String, Long>, Glob> globs) {
      this.globs = globs;
    }
    
    public Map<Pair<String, Long>, Glob> globs() {
      return globs;
    }

    public int getPriority() {
      return MEDIUM_PRIORITY;
    }

    public short getType() {
      return MessageType.REPLICATE_REPLY;
    }

    public void serialize(OutputBuffer buf) throws IOException {
      // TODO Auto-generated method stub
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeInt(globs.size());
      
      for (Map.Entry<Pair<String, Long>, Glob> e : globs.entrySet()) {
        out.writeUTF(e.getKey().first);
        out.writeLong(e.getKey().second);
        e.getValue().write(out);
      }
    }
    
    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf) throws IOException {
      DataInputBuffer in = new DataInputBuffer(buf);
      int n = in.readInt();
      globs = new HashMap<Pair<String, Long>, Glob>(n);
      
      for (int i = 0; i < n; i++) {
        String core = in.readUTF();
        long onum = in.readLong();
        Glob g = new Glob(in);
        globs.put(new Pair<String, Long>(core, onum), g);
      }
    }
    
  }

}
