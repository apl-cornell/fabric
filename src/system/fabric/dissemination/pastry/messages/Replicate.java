package fabric.dissemination.pastry.messages;

import java.util.Map;

import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import fabric.common.Pair;
import fabric.dissemination.Glob;

/**
 * This represents a Replicate message in the beehive system. Node A sends node
 * B a replicate message with level i to request that B push objects with
 * replication level i or lower to A. B is the level i decider for A.
 */
public class Replicate implements Message {

  private final NodeHandle sender;
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
  
  /**
   * A reply to a replicate message, carrying the requested objects.
   */
  public static class Reply implements Message {
    
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
    
  }

}
