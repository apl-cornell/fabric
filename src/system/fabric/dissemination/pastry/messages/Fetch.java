package fabric.dissemination.pastry.messages;

import java.util.Map;

import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import fabric.core.SerializedObject;

public class Fetch implements Message {
  
  private final NodeHandle sender;
  private final String core;
  private final long onum;
  
  private Reply reply;
  
  public Fetch(NodeHandle sender, String core, long onum) {
    this.sender = sender;
    this.core = core;
    this.onum = onum;
  }
  
  public NodeHandle sender() {
    return sender;
  }
  
  public String core() {
    return core;
  }
  
  public long onum() {
    return onum;
  }

  public Reply reply() {
    return reply;
  }

  public void reply(Reply reply) {
    this.reply = reply;
  }
  
  public int getPriority() {
    return MEDIUM_PRIORITY;
  }
  
  @Override
  public String toString() {
    return core + "/" + onum;
  }

  public static class Reply implements Message {
    
    private SerializedObject obj;
    private Map<Long, SerializedObject> related;
    
    public Reply(SerializedObject obj, Map<Long, SerializedObject> related) {
      this.obj = obj;
      this.related = related;
    }
    
    public SerializedObject obj() {
      return obj;
    }
    
    public Map<Long, SerializedObject> related() {
      return related;
    }

    public int getPriority() {
      return MEDIUM_PRIORITY;
    }
    
  }

}
