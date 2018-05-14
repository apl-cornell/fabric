package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
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

  /**
   * Used to prepare transactions at remote workers.
   */
  public WorkerPrepareSuccessMessage(long tid) {
    super(MessageType.WORKER_PREPARE_SUCCESS);
    this.tid = tid;
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
  }

  /* readMessage */
  protected WorkerPrepareSuccessMessage(DataInput in) throws IOException {
    super(MessageType.WORKER_PREPARE_SUCCESS);
    this.tid = in.readLong();
  }
}
