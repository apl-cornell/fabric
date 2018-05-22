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
import fabric.worker.metrics.TreatySet;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>StorePrepareSuccessMessage</code> represents a successful prepare for the
 * given tid.
 */
public class StorePrepareSuccessMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long time;
  // Map from onum to new, longer treaties.
  public final OidKeyHashMap<TreatySet> longerContracts;

  /**
   * Used to prepare transactions at remote workers.
   */
  public StorePrepareSuccessMessage(long tid, long time,
      OidKeyHashMap<TreatySet> longerContracts) {
    super(MessageType.STORE_PREPARE_SUCCESS);
    this.tid = tid;
    this.time = time;
    this.longerContracts = longerContracts;
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

    // Serialize longer contracts.
    out.writeInt(longerContracts.storeSet().size());
    for (Store s : longerContracts.storeSet()) {
      out.writeUTF(s.name());
      out.writeInt(longerContracts.get(s).size());
      for (LongKeyMap.Entry<TreatySet> entry : longerContracts.get(s)
          .entrySet()) {
        out.writeLong(entry.getKey());
        entry.getValue().write(out);
      }
    }
  }

  /* readMessage */
  protected StorePrepareSuccessMessage(DataInput in) throws IOException {
    super(MessageType.STORE_PREPARE_SUCCESS);
    this.tid = in.readLong();
    this.time = in.readLong();
    int size = in.readInt();
    this.longerContracts = new OidKeyHashMap<>();
    for (int i = 0; i < size; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        this.longerContracts.put(s, onum, TreatySet.read(in));
      }
    }
  }
}
