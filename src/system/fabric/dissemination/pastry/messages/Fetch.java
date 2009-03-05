package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.client.Client;
import fabric.common.exceptions.BadSignatureException;
import fabric.dissemination.Glob;

/**
 * A Fetch message represents a request to fetch a particular object.
 */
public class Fetch implements RawMessage {
  
  private transient final NodeHandle sender;
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
    buf.writeShort(id.getType());
    id.serialize(buf);
    buf.writeUTF(core);
    buf.writeLong(onum);
    buf.writeBoolean(refresh);
  }
  
  /**
   * Deserialization constructor.
   */
  public Fetch(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
      throws IOException {
    this.sender = sender;
    id = endpoint.readId(buf, buf.readShort());
    core = buf.readUTF();
    onum = buf.readLong();
    refresh = buf.readBoolean();
  }
  
  /**
   * A reply to a Fetch message. Should carry the object requested by the
   * original fetch message.
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
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeShort(id.getType());
      id.serialize(out);
      out.writeUTF(core);
      out.writeLong(onum);
      glob.write(out);
    }

    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf, Endpoint endpoint) throws IOException {
      DataInputBuffer in = new DataInputBuffer(buf);
      id = endpoint.readId(in, in.readShort());
      core = in.readUTF();
      onum = in.readLong();
      
      Glob glob;
      try {
        glob = new Glob(Client.getClient().getCore(core).getPublicKey(), in);
      } catch (BadSignatureException e) {
        glob = null;
      }
      this.glob = glob;
    }

  }

}
