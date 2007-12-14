package fabric.dissemination.pastry.messages;

import java.util.Map;

import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import fabric.core.SerializedObject;

/**
 * A Fetch message represents a request to fetch a particular object.
 */
public class Fetch implements Message {
  
  private final NodeHandle sender;
  private final String core;
  private final long onum;
  private boolean refresh;
  
  private transient Reply reply;
  
  public Fetch(NodeHandle sender, String core, long onum) {
    this.sender = sender;
    this.core = core;
    this.onum = onum;
  }
  
  /** The sender of this message. */
  public NodeHandle sender() {
    return sender;
  }
  
  /** The core where the requested object resides. */
  public String core() {
    return core;
  }
  
  /** The object number of the requested object. */
  public long onum() {
    return onum;
  }

  /** 
   * A hint as to whether we want to explicitly fetch the latest version from 
   * the core.
   */
  public boolean refresh() {
    return refresh;
  }

  /** Set whether we want to refresh. */
  public void refresh(boolean refresh) {
    this.refresh = refresh;
  }

  /** The reply message. */
  public Reply reply() {
    return reply;
  }

  /** Sets the reply message. */
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

  /**
   * A reply to a Fetch message.
   */
  public static class Reply implements Message {
    
    private SerializedObject obj;
    private Map<Long, SerializedObject> related;
    
    public Reply(SerializedObject obj, Map<Long, SerializedObject> related) {
      this.obj = obj;
      this.related = related;
    }
    
    /** The object requested. */
    public SerializedObject obj() {
      return obj;
    }
    
    /** The related objects. */
    public Map<Long, SerializedObject> related() {
      return related;
    }

    public int getPriority() {
      return MEDIUM_PRIORITY;
    }
    
  }

}
