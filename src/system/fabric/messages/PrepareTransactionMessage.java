package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fabric.common.SerializedObject;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request to
 * a store.
 */
public class PrepareTransactionMessage extends
    Message<PrepareTransactionMessage.Response, TransactionPrepareFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;

  /**
   * A flag to indicate whether objects from just a single (persistent) store
   * are involved in this transaction. This is always false if the transaction
   * is distributed. If this is true, then the store will commit the transaction
   * as soon as it is prepared.
   */
  public final boolean singleStore;

  /**
   * A flag to indicate whether the transaction is read-only. A transaction is
   * read-only if it does not modify any persistent objects. If this value is
   * true, then the store will commit the transaction as soon as it is prepared.
   */
  public final boolean readOnly;

  /**
   * Time stamp to check for expiration if the transaction is single store.
   */
  public final long expiryToCheck;

  public final LongKeyMap<Pair<Integer, Long>> reads;

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
   * The extensions performed during this transaction.
   */
  public final Collection<ExpiryExtension> extensions;

  /**
   * The extensions that will be triggered if the updates commit.
   */
  public final LongKeyMap<Set<Oid>> extensionsTriggered;

  /**
   * Triggerless extensions.
   */
  public final LongSet delayedExtensions;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionMessage(long tid) {
    this(tid, false, false, 0, null, null, null, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionMessage(long tid, boolean singleStore,
      boolean readOnly, long expiryToCheck, Collection<_Impl> toCreate,
      LongKeyMap<Pair<Integer, Long>> reads, Collection<_Impl> writes,
      Collection<ExpiryExtension> extensions,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions) {
    super(MessageType.PREPARE_TRANSACTION,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.singleStore = singleStore;
    this.readOnly = readOnly;
    this.expiryToCheck = expiryToCheck;
    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.extensions = extensions;
    this.extensionsTriggered = extensionsTriggered;
    this.delayedExtensions = delayedExtensions;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    /** Time to use for the expiry check for contracts. */
    public final long time;
    /** Longer contracts to update cache with. */
    public final LongKeyMap<Long> longerContracts;

    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response(long time, LongKeyMap<Long> longerContracts) {
      this.time = time;
      this.longerContracts = longerContracts;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws TransactionPrepareFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);

    // Serialize single-store flag.
    out.writeBoolean(singleStore);

    // Serialize read-only flag.
    out.writeBoolean(readOnly);

    // Serialize the expiry to check.
    out.writeLong(expiryToCheck);

    // Serialize reads.
    if (reads == null) {
      out.writeInt(0);
    } else {
      out.writeInt(reads.size());
      for (LongKeyMap.Entry<Pair<Integer, Long>> entry : reads.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeInt(entry.getValue().first);
        out.writeLong(entry.getValue().second);
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
      for (_Impl o : writes) {
        SerializedObject.write(o, out);
      }
    }

    // Serialize extensions.
    if (extensions == null) {
      out.writeInt(0);
    } else {
      out.writeInt(extensions.size());
      for (ExpiryExtension e : extensions) {
        e.write(out);
      }
    }

    // Serialize extensions triggered.
    if (extensionsTriggered == null) {
      out.writeInt(0);
    } else {
      out.writeInt(extensionsTriggered.size());
      for (LongKeyMap.Entry<Set<Oid>> e : extensionsTriggered.entrySet()) {
        out.writeLong(e.getKey());
        out.writeInt(e.getValue().size());
        for (Oid o : e.getValue()) {
          out.writeUTF(o.store.name());
          out.writeLong(o.onum);
        }
      }
    }

    // Other extensions
    if (delayedExtensions == null) {
      out.writeInt(0);
    } else {
      out.writeInt(delayedExtensions.size());
      for (LongIterator it = delayedExtensions.iterator(); it.hasNext();) {
        out.writeLong(it.next());
      }
    }
  }

  /* readMessage */
  protected PrepareTransactionMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION,
        TransactionPrepareFailedException.class);
    this.creates = null;
    this.writes = null;

    // Read the TID.
    this.tid = in.readLong();

    // Read the single-store flag.
    this.singleStore = in.readBoolean();

    // Read the read-only flag.
    this.readOnly = in.readBoolean();

    // Read the expriy to check
    this.expiryToCheck = in.readLong();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<>();
    } else {
      reads = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), new Pair<>(in.readInt(), in.readLong()));
    }

    // Read creates.
    size = in.readInt();
    if (size == 0) {
      // XXX: this list must be mutable since it can be added
      //      to by the surrogate manager
      serializedCreates = new ArrayList<>(0);
    } else {
      serializedCreates = new ArrayList<>(size);
      for (int i = 0; i < size; i++)
        serializedCreates.add(new SerializedObject(in));
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      serializedWrites = Collections.emptyList();
    } else {
      serializedWrites = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        serializedWrites.add(new SerializedObject(in));
      }
    }

    // Read extensions
    size = in.readInt();
    if (size == 0) {
      extensions = Collections.emptyList();
    } else {
      extensions = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        extensions.add(new ExpiryExtension(in));
      }
    }

    // Read extensions triggered
    size = in.readInt();
    this.extensionsTriggered = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long key = in.readLong();
      int size2 = in.readInt();
      Set<Oid> value = new HashSet<>(size2);
      for (int j = 0; j < size2; j++) {
        Store s = Worker.getWorker().getStore(in.readUTF());
        long onum = in.readLong();
        value.add(new Oid(s, onum));
      }
      extensionsTriggered.put(key, value);
    }

    // Read other extensions
    size = in.readInt();
    this.delayedExtensions = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      delayedExtensions.add(in.readLong());
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeLong(r.time);
    out.writeInt(r.longerContracts.size());
    for (LongKeyMap.Entry<Long> e : r.longerContracts.entrySet()) {
      out.writeLong(e.getKey());
      out.writeLong(e.getValue());
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long time = in.readLong();
    int size = in.readInt();
    LongKeyMap<Long> longerContracts = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      long expiry = in.readLong();
      longerContracts.put(onum, expiry);
    }
    return new Response(time, longerContracts);
  }
}
