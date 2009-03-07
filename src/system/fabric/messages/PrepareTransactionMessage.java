package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import fabric.client.RemoteNode;
import fabric.client.UnreachableNodeException;
import fabric.client.debug.Timing;
import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request to
 * a core.
 */
public class PrepareTransactionMessage extends
    Message<RemoteNode, PrepareTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final boolean success;
    public final String message;

    /**
     * If the remote node is a core, this will indicate whether the client
     * should send a commit/abort message to the core's client to commit/abort a
     * sub-transaction. (This happens when Statistics objects are created during
     * transaction prepare.)
     */
    public final boolean subTransactionCreated;

    /**
     * A set of oids involved in the transaction that were out of date.
     */
    public final Set<Long> versionConflicts;

    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response() {
      this(false);
    }
    
    public Response(boolean subTransactionCreated) {
      this.success = true;
      this.subTransactionCreated = subTransactionCreated;
      this.message = null;
      this.versionConflicts = null;
    }

    /**
     * Creates a Response indicating a failed prepare.
     */
    public Response(String message) {
      this(message, null);
    }

    /**
     * Creates a Response indicating a failed prepare.
     */
    public Response(String message, Set<Long> versionConflicts) {
      this.success = false;
      this.subTransactionCreated = false;
      this.message = message;
      this.versionConflicts = versionConflicts;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) throws IOException {
      this.success = in.readBoolean();
      this.subTransactionCreated = in.readBoolean();
      if (in.readBoolean())
        this.message = in.readUTF();
      else this.message = null;

      int size = in.readInt();
      this.versionConflicts = new HashSet<Long>(size);
      for (int i = 0; i < size; i++)
        versionConflicts.add(in.readLong());
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(success);
      out.writeBoolean(subTransactionCreated);
      if (message != null) {
        out.writeBoolean(true);
        out.writeUTF(message);
      } else out.writeBoolean(false);

      if (versionConflicts == null)
        out.writeInt(0);
      else {
        out.writeInt(versionConflicts.size());
        for (long onum : versionConflicts)
          out.writeLong(onum);
      }
    }
  }

  public final long tid;
  
  public final long commitTime;

  public final LongKeyMap<Integer> reads;

  /**
   * The objects created during the transaction, unserialized. This will only be
   * non-null on the client. The core should use the
   * <code>serializedCreates</code> field instead.
   */
  public final Collection<$Impl> creates;

  /**
   * The objects created during the transaction, serialized. This will only be
   * non-null on the core. The client should use the <code>creates</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedCreates;

  /**
   * The objects modified during the transaction, unserialized. This will only
   * be non-null on the client. The core should use the
   * <code>serializedWrites</code> field instead.
   */
  public final Collection<$Impl> writes;

  /**
   * The objects modified during the transaction, serialized. This will only be
   * non-null on the core. The client should use the <code>writes</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedWrites;

  /**
   * Used to prepare transactions at remote clients.
   */
  public PrepareTransactionMessage(long tid, long commitTime) {
    this(tid, commitTime, null, null, null);
  }

  /**
   * Only used by the client.
   */
  public PrepareTransactionMessage(long tid, long commitTime, Collection<$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<$Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION);

    this.tid = tid;
    this.commitTime = commitTime;
    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  /**
   * Deserialization constructor. Used only by the core.
   * 
   * @throws IOException
   */
  protected PrepareTransactionMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION);
    this.creates = null;
    this.writes = null;

    // Read the TID.
    this.tid = in.readLong();
    this.commitTime = in.readLong();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<Integer>();
    } else {
      reads = new LongKeyHashMap<Integer>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }

    // Read creates.
    size = in.readInt();
    if (size == 0) {
      serializedCreates = Collections.emptyList();
    } else {
      serializedCreates = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        serializedCreates.add(new SerializedObject(in));
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      serializedWrites = Collections.emptyList();
    } else {
      serializedWrites = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        serializedWrites.add(new SerializedObject(in));
    }
  }

  /*
   * (non-Javadoc)
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(fabric.core.Worker w) throws ProtocolError {
    return w.handle(this);
  }

  @Override
  public Response dispatch(fabric.client.remote.Worker handler) {
    return handler.handle(this);
  }

  public Response send(RemoteNode node) throws UnreachableNodeException {
    try {
      Timing.CORE.begin();
      return super.send(node, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.CORE.end();
    }
  }

  @Override
  public Response response(RemoteNode node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  /*
   * (non-Javadoc)
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);
    
    // Serialize commitTime
    out.writeLong(commitTime);

    // Serialize reads.
    if (reads == null) {
      out.writeInt(0);
    } else {
      out.writeInt(reads.size());
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeInt(entry.getValue());
      }
    }

    // Serialize creates.
    if (creates == null) {
      out.writeInt(0);
    } else {
      out.writeInt(creates.size());
      for ($Impl impl : creates)
        SerializedObject.write(impl, out);
    }

    // Serialize writes.
    if (writes == null) {
      out.writeInt(0);
    } else {
      out.writeInt(writes.size());
      for ($Impl impl : writes)
        SerializedObject.write(impl, out);
    }
  }
}
