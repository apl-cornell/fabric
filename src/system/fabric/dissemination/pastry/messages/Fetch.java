package fabric.dissemination.pastry.messages;

import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import fabric.common.util.LongKeyMap;
import fabric.core.SerializedObject;
import fabric.dissemination.Glob;

/**
 * A Fetch message represents a request to fetch a particular object.
 */
public class Fetch implements Message {
  
  private final NodeHandle sender;
  private final Id id;
  private final String core;
  private final long onum;
  private boolean refresh;
  
  private transient Reply reply;
  
  public Fetch(NodeHandle sender, Id id, String core, long onum) {
    this.sender = sender;
    this.id = id;
    this.core = core;
    this.onum = onum;
  }
  
  /** The sender of this message. */
  public NodeHandle sender() {
    return sender;
  }
  
  /** The random id of this message. */
  public Id id() {
    return id;
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
  public class Reply implements Message {
    
    private Glob glob;
    
    public Reply(SerializedObject obj, LongKeyMap<SerializedObject> related) {
      glob = new Glob(obj, related);
    }
    
    /** The glob returned. */
    public Glob glob() {
      return glob;
    }
    
    /** The object requested. */
    public SerializedObject obj() {
      return glob.obj();
    }
    
    /** The related objects. */
    public LongKeyMap<SerializedObject> related() {
      return glob.related();
    }

    /** The random id of this message. */
    public Id id() {
      return id;
    }

    /** The core where the requested object resides. */
    public String core() {
      return core;
    }
    
    /** The object number of the requested object. */
    public long onum() {
      return onum;
    }

    public int getPriority() {
      return MEDIUM_PRIORITY;
    }
    
  }
}
