package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.common.util.LongKeyMap;
import fabric.core.SerializedObject;
import fabric.dissemination.Glob;

/**
 * A Fetch message represents a request to fetch a particular object.
 */
public class Fetch implements RawMessage {
  
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

  public short getType() {
    return MessageType.FETCH;
  }

  public void serialize(OutputBuffer buf) throws IOException {
    sender.serialize(buf);
    buf.writeShort(id.getType());
    id.serialize(buf);
    buf.writeUTF(core);
    buf.writeLong(onum);
    buf.writeBoolean(refresh);
  }
  
  /**
   * Deserialization constructor.
   * 
   * @param buf The input buffer containing the serialized message.
   * @param endpoint The endpoint where this message is to be deserialized.
   * @throws IOException If an error occurs reading from buf.
   */
  public Fetch(InputBuffer buf, Endpoint endpoint) throws IOException {
    sender = endpoint.readNodeHandle(buf);
    id = endpoint.readId(buf, buf.readShort());
    core = buf.readUTF();
    onum = buf.readLong();
    refresh = buf.readBoolean();
  }
  
  /**
   * A reply to a Fetch message.
   */
  public static class Reply implements RawMessage {
    
    private final Id id;
    private final String core;
    private final long onum;
    private final Glob glob;
    
    public Reply(Fetch parent, Glob glob) {
      id = parent.id();
      core = parent.core();
      onum = parent.onum();
      this.glob = glob;
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

    /** The id of this message. */
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

    public short getType() {
      return MessageType.FETCH_REPLY;
    }

    public void serialize(OutputBuffer buf) throws IOException {
      // TODO Auto-generated method stub
      buf.writeShort(id.getType());
      id.serialize(buf);
      buf.writeUTF(core);
      buf.writeLong(onum);
      
      // TODO serialized glob
    }

    public Reply(InputBuffer buf, Endpoint endpoint) throws IOException {
      id = endpoint.readId(buf, buf.readShort());
      core = buf.readUTF();
      onum = buf.readLong();
      
      // TODO deserialize glob
      glob = null;
    }
    
  }

}
