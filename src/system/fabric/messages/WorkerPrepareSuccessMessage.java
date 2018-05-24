package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>WorkerPrepareSuccessMessage</code> represents a successful prepare for the
 * given tid.
 */
public class WorkerPrepareSuccessMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long time;
  // Map from oid to new, longer treaties.
  public final OidKeyHashMap<TreatySet> longerTreaties;

  /**
   * Used to prepare transactions at remote workers.
   */
  public WorkerPrepareSuccessMessage(long tid, long time,
      OidKeyHashMap<TreatySet> longerTreaties) {
    super(MessageType.WORKER_PREPARE_SUCCESS);
    this.tid = tid;
    this.time = time;
    this.longerTreaties = longerTreaties;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public void dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);

    // Serialize time.
    out.writeLong(time);

    // Serialize longer treaties.
    out.writeInt(longerTreaties.storeSet().size());
    for (Store s : longerTreaties.storeSet()) {
      out.writeUTF(s.name());
      out.writeInt(longerTreaties.get(s).size());
      for (LongKeyMap.Entry<TreatySet> entry : longerTreaties.get(s)
          .entrySet()) {
        out.writeLong(entry.getKey());
        entry.getValue().write(out);
      }
    }
  }

  /* readMessage */
  protected WorkerPrepareSuccessMessage(DataInput in) throws IOException {
    super(MessageType.WORKER_PREPARE_SUCCESS);
    this.tid = in.readLong();
    this.time = in.readLong();
    this.longerTreaties = new OidKeyHashMap<>();
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        this.longerTreaties.put(s, onum, TreatySet.read(in));
      }
    }
  }
}
