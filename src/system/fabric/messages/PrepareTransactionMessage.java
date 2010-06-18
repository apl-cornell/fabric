package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import fabric.worker.debug.Timing;
import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object._Impl;
import fabric.lang.security.NodePrincipal;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request to
 * a store.
 */
public class PrepareTransactionMessage
     extends Message<PrepareTransactionMessage.Response>
  implements MessageToWorker, MessageToStore
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long commitTime;
  public final LongKeyMap<Integer> reads;

  /**
   * The objects created during the transaction, unserialized. This will only be
   * non-null on the worker. The store should use the
   * <code>serializedCreates</code> field instead.
   */
  public final Collection<_Impl> creates;

  /**
   * The objects created during the transaction, serialized. This will only be
   * non-null on the store. The worker should use the <code>creates</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedCreates;

  /**
   * The objects modified during the transaction, unserialized. This will only
   * be non-null on the worker. The store should use the
   * <code>serializedWrites</code> field instead.
   */
  public final Collection<_Impl> writes;

  /**
   * The objects modified during the transaction, serialized. This will only be
   * non-null on the store. The worker should use the <code>writes</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedWrites;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionMessage(long tid, long commitTime) {
    this(tid, commitTime, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionMessage(long tid, long commitTime,
      Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION);

    this.tid = tid;
    this.commitTime = commitTime;
    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final boolean success;
    public final String message;

    /**
     * If the remote node is a store, this will indicate whether the worker
     * should send a commit/abort message to the store's worker to commit/abort
     * a sub-transaction. (This happens when Statistics objects are created
     * during transaction prepare.)
     */
    public final boolean subTransactionCreated;

    /**
     * A set of objects involved in the transaction that were out of date.
     */
    public final LongKeyMap<SerializedObject> versionConflicts;

    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response() {
      this(false);
    }

    public Response(boolean subTX) {
      this(/* success */ true, subTX, /* message */ null, /* conflicts */ null);
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
    public Response(String message,
        LongKeyMap<SerializedObject> versionConflicts) {
      this(/* success = */ false, /* subTX = */ false, message, versionConflicts);
    }

    protected Response(boolean success,
                       boolean subTXCreated,
                       String message,
                       LongKeyMap<SerializedObject> conflicts) {
      this.success               = success;
      this.subTransactionCreated = subTXCreated;
      this.message               = message;
      this.versionConflicts      = conflicts;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler h) throws FabricException {
    return h.handle(p, this);
  }

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // convenience method for sending                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Response send(RemoteNode node) throws UnreachableNodeException {
    try {
      Timing.STORE.begin();
      return super.send(node, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
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
      for (_Impl impl : creates)
        SerializedObject.write(impl, out);
    }

    // Serialize writes.
    if (writes == null) {
      out.writeInt(0);
    } else {
      out.writeInt(writes.size());
      for (_Impl impl : writes)
        SerializedObject.write(impl, out);
    }
  }

  /* readMessage */
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

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.success);
    out.writeBoolean(r.subTransactionCreated);
    if (r.message != null) {
      out.writeBoolean(true);
      out.writeUTF(r.message);
    } else out.writeBoolean(false);

    if (r.versionConflicts == null)
      out.writeInt(0);
    else {
      out.writeInt(r.versionConflicts.size());
      for (SerializedObject obj : r.versionConflicts.values()) {
        obj.write(out);
      }
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    boolean success               = in.readBoolean();
    boolean subTransactionCreated = in.readBoolean();
    String  message               = in.readBoolean() ? in.readUTF() : null;


    int size = in.readInt();
    LongKeyHashMap<SerializedObject> versionConflicts = new LongKeyHashMap<SerializedObject>(size);
    for (int i = 0; i < size; i++) {
      SerializedObject obj = new SerializedObject(in);
      versionConflicts.put(obj.getOnum(), obj);
    }

    return new Response(success, subTransactionCreated, message, versionConflicts);
  }

}
