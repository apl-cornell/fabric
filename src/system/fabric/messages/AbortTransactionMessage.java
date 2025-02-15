package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.TransactionID;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

public class AbortTransactionMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The tid for the transaction that is aborting. */
  public final TransactionID tid;

  public AbortTransactionMessage(TransactionID tid) {
    super(MessageType.ABORT_TRANSACTION);
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
    tid.write(out);
  }

  /* readMessage */
  protected AbortTransactionMessage(DataInput in) throws IOException {
    this(new TransactionID(in));
  }
}
