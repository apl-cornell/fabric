package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.security.GeneralSecurityException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import rice.p2p.commonapi.rawserialization.RawMessage;
import fabric.dissemination.AbstractGlob;
import fabric.dissemination.ObjectGlob;
import fabric.worker.Worker;

/**
 * Represents a push notification that an object group has been updated.
 */
public class UpdateCache implements RawMessage {

  private transient final NodeHandle sender;
  private final Id id;
  private final String store;
  private final long onum;
  private final AbstractGlob<?> update;

  public UpdateCache(NodeHandle sender, Id id, String store, long onum,
      AbstractGlob<?> update) {
    this.sender = sender;
    this.id = id;
    this.store = store;
    this.onum = onum;
    this.update = update;
  }

  /** The sender of this message. */
  public NodeHandle sender() {
    return sender;
  }

  /** The random id of this message. */
  public Id id() {
    return id;
  }

  /** The store where the updated object resides. */
  public String store() {
    return store;
  }

  /** The object number of the updated object. */
  public long onum() {
    return onum;
  }

  /** The updated glob. */
  public AbstractGlob<?> update() {
    return update;
  }

  @Override
  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  @Override
  public String toString() {
    return store + "/" + onum;
  }

  @Override
  public short getType() {
    return MessageType.UPDATE;
  }

  @Override
  public void serialize(OutputBuffer buf) throws IOException {
    DataOutputBuffer out = new DataOutputBuffer(buf);
    out.writeShort(id.getType());
    id.serialize(out);
    out.writeUTF(store);
    out.writeLong(onum);
    update.write(out);
  }

  /**
   * Deserialization constructor.
   */
  public UpdateCache(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
      throws IOException {
    DataInputBuffer in = new DataInputBuffer(buf);
    this.sender = sender;
    id = endpoint.readId(in, in.readShort());
    store = in.readUTF();
    onum = in.readLong();

    ObjectGlob glob;
    try {
      glob = new ObjectGlob(in);
      glob.verifySignature(Worker.getWorker().getStore(store).getPublicKey());
    } catch (GeneralSecurityException e) {
      glob = null;
    }
    this.update = glob;
  }

  /**
   * A reply to a Fetch message. Should carry the object requested by the
   * original fetch message.
   */
  public static class Reply implements RawMessage {

    private transient final NodeHandle sender;
    private final Id id;
    private final String store;
    private final long onum;
    private final boolean resubscribe;

    public Reply(NodeHandle sender, UpdateCache parent, boolean resubscribe) {
      this.sender = sender;
      this.id = parent.id();
      this.store = parent.store();
      this.onum = parent.onum();
      this.resubscribe = resubscribe;
    }

    /** The sender of this message. */
    public NodeHandle sender() {
      return sender;
    }

    /** The id of this message. */
    public Id id() {
      return id;
    }

    public String store() {
      return store;
    }

    public long onum() {
      return onum;
    }

    /** Whether the sender wants to resubscribe. */
    public boolean resubscribe() {
      return resubscribe;
    }

    @Override
    public int getPriority() {
      return MEDIUM_PRIORITY;
    }

    @Override
    public short getType() {
      return MessageType.UPDATE_REPLY;
    }

    @Override
    public void serialize(OutputBuffer buf) throws IOException {
      buf.writeShort(id.getType());
      id.serialize(buf);
      buf.writeUTF(store);
      buf.writeLong(onum);
      buf.writeBoolean(resubscribe);
    }

    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
        throws IOException {
      this.sender = sender;
      this.id = endpoint.readId(buf, buf.readShort());
      this.store = buf.readUTF();
      this.onum = buf.readLong();
      this.resubscribe = buf.readBoolean();
    }

  }

}
