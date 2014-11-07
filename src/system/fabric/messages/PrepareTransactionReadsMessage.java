package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareTransactionReadsMessage</code> represents a transaction
 * PREPARE_READS request to a remote node.
 */
public class PrepareTransactionReadsMessage
    extends
    Message<PrepareTransactionReadsMessage.Response, TransactionPrepareFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long commitTime;

  /**
   * A flag to indicate whether the transaction is read-only. A transaction is
   * read-only if it does not modify any persistent objects. If this value is
   * true, then the store will commit the transaction as soon as it is prepared.
   */
  public final boolean readOnly;

  public final LongKeyMap<Integer> reads;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionReadsMessage(long tid, long commitTime) {
    this(tid, commitTime, false, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionReadsMessage(long tid, boolean readOnly,
      LongKeyMap<Integer> reads, long commitTime) {
    this(tid, commitTime, readOnly, reads);
  }

  private PrepareTransactionReadsMessage(long tid, long commitTime,
      boolean readOnly, LongKeyMap<Integer> reads) {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.commitTime = commitTime;
    this.readOnly = readOnly;
    this.reads = reads;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final LongKeyMap<VersionWarranty> newWarranties;
    public final LongKeyMap<RWLease> newLeases;

    public Response() {
      this.newWarranties = null;
      this.newLeases = null;
    }

    public Response(LongKeyMap<VersionWarranty> newWarranties, LongKeyMap<RWLease> newLeases) {
      this.newWarranties = newWarranties;
      this.newLeases = newLeases;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws TransactionPrepareFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid and commit time.
    out.writeLong(tid);
    out.writeLong(commitTime);

    // Serialize read-only flag.
    out.writeBoolean(readOnly);

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
  }

  /* readMessage */
  protected PrepareTransactionReadsMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    // Read the TID and commit time.
    this.tid = in.readLong();
    this.commitTime = in.readLong();

    // Read the read-only flag.
    this.readOnly = in.readBoolean();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<Integer>();
    } else {
      reads = new LongKeyHashMap<Integer>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.newWarranties == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeInt(r.newWarranties.size());
      for (LongKeyMap.Entry<VersionWarranty> entry : r.newWarranties.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeLong(entry.getValue().expiry());
      }
    }

    if (r.newLeases == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeInt(r.newLeases.size());
      for (LongKeyMap.Entry<RWLease> entry : r.newLeases.entrySet()) {
        out.writeLong(entry.getKey());
        if (entry.getValue().getOwner() != null) {
          out.writeBoolean(true);
          out.writeUTF(entry.getValue().getOwner().store.name());
          out.writeLong(entry.getValue().getOwner().onum);
        } else {
          out.writeBoolean(false);
        }
        out.writeLong(entry.getValue().expiry());
      }
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    LongKeyMap<VersionWarranty> newWarranties = null;
    LongKeyMap<RWLease> newLeases = null;

    if (in.readBoolean()) {
      // Read in warranties
      int warrantiesSize = in.readInt();
      newWarranties = new LongKeyHashMap<>(warrantiesSize);
      for (int i = 0; i < warrantiesSize; i++) {
        long onum = in.readLong();
        newWarranties.put(onum, new VersionWarranty(in.readLong()));
      }
    }

    if (in.readBoolean()) {
      // Read in leases
      int leasesSize = in.readInt();
      newLeases = new LongKeyHashMap<>(leasesSize);
      for (int i = 0; i < leasesSize; i++) {
        long onum = in.readLong();
        if (in.readBoolean()) {
          Store ownerStore = Worker.getWorker().getStore(in.readUTF());
          Oid owner = new Oid(ownerStore, in.readLong());
          newLeases.put(onum, new RWLease(in.readLong(), owner));
        } else {
          newLeases.put(onum, new RWLease(in.readLong()));
        }
      }
    }

    return new Response(newWarranties, newLeases);
  }
}
