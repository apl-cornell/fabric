package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
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
  // Map from onum to new, longer, expiration time
  public final LongKeyMap<Long> longerContracts;

  /**
   * Used to prepare transactions at remote workers.
   */
  public StorePrepareSuccessMessage(long tid, long time,
      LongKeyMap<Long> longerContracts) {
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
    out.writeInt(longerContracts.size());
    for (LongKeyMap.Entry<Long> entry : longerContracts.entrySet()) {
      out.writeLong(entry.getKey());
      out.writeLong(entry.getValue());
    }
  }

  /* readMessage */
  protected StorePrepareSuccessMessage(DataInput in) throws IOException {
    super(MessageType.STORE_PREPARE_SUCCESS);
    this.tid = in.readLong();
    this.time = in.readLong();
    int size = in.readInt();
    this.longerContracts = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      this.longerContracts.put(onum, in.readLong());
    }
  }
}
