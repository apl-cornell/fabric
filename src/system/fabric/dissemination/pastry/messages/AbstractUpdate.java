package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.security.GeneralSecurityException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import fabric.common.FastSerializable;
import fabric.dissemination.AbstractGlob;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.dissemination.pastry.Disseminator;
import fabric.worker.Worker;

/**
 * Represents a push notification that a glob has been updated.
 */
public abstract class AbstractUpdate<UpdateType extends FastSerializable>
extends AbstractRawMessage {

  private transient final NodeHandle sender;
  private final Id id;
  private final String store;
  private final long onum;
  private final AbstractGlob<UpdateType> update;

  public AbstractUpdate(RawMessageType messageType, NodeHandle sender, Id id,
      String store, long onum, AbstractGlob<UpdateType> update) {
    super(messageType);
    this.sender = sender;
    this.id = id;
    this.store = store;
    this.onum = onum;
    this.update = update;
  }

  @SuppressWarnings("unchecked")
  public static <T extends FastSerializable> AbstractUpdate<T> getInstance(
      NodeHandle sender, Id id, String store, long onum, AbstractGlob<T> update) {
    // Yuck.
    if (update instanceof ObjectGlob) {
      return (AbstractUpdate<T>) new ObjectUpdate(sender, id, store, onum,
          (ObjectGlob) update);
    }

    if (update instanceof WarrantyGlob) {
      return (AbstractUpdate<T>) new WarrantyUpdate(sender, id, store, onum,
          (WarrantyGlob) update);
    }

    throw new InternalError("Unknown update type: " + update.getClass());
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
  public AbstractGlob<UpdateType> update() {
    return update;
  }

  @Override
  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  @Override
  public void dispatch(Disseminator disseminator) {
    disseminator.updateCache(this);
  }

  @Override
  public String toString() {
    return store + "/" + onum;
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
  public AbstractUpdate(RawMessageType messageType, InputBuffer buf,
      Endpoint endpoint, NodeHandle sender) throws IOException {
    super(messageType);

    DataInputBuffer in = new DataInputBuffer(buf);
    this.sender = sender;
    id = endpoint.readId(in, in.readShort());
    store = in.readUTF();
    onum = in.readLong();

    AbstractGlob<UpdateType> glob;
    try {
      glob = deserializeUpdate(in);
      glob.verifySignature(Worker.getWorker().getStore(store).getPublicKey());
    } catch (GeneralSecurityException e) {
      glob = null;
    }
    this.update = glob;
  }

  abstract AbstractGlob<UpdateType> deserializeUpdate(DataInputBuffer in)
      throws IOException;

  /**
   * A reply to an update message. Indicates whether the subscriber is
   * interested in further updates.
   */
  public static class Reply extends AbstractRawMessage {

    private transient final NodeHandle sender;
    private final Id id;
    private final String store;
    private final long onum;
    private final boolean resubscribe;

    public Reply(NodeHandle sender, AbstractUpdate<?> parent,
        boolean resubscribe) {
      super(RawMessageType.UPDATE_REPLY);
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
    public void dispatch(Disseminator disseminator) {
      disseminator.updateCache(this);
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
      super(RawMessageType.UPDATE_REPLY);
      this.sender = sender;
      this.id = endpoint.readId(buf, buf.readShort());
      this.store = buf.readUTF();
      this.onum = buf.readLong();
      this.resubscribe = buf.readBoolean();
    }
  }

}
