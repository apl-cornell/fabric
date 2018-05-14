package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.SerializedObject;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareSuccessMessage</code> represents a failed prepare for the
 * given tid.
 */
public class StorePrepareFailedMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;

  public final LongKeyMap<SerializedObject> conflicts;

  public final List<String> messages;

  /**
   * Used to prepare transactions at remote workers.
   */
  public StorePrepareFailedMessage(long tid,
      TransactionPrepareFailedException e) {
    super(MessageType.STORE_PREPARE_FAILED);
    this.tid = tid;
    this.conflicts = e.versionConflicts;
    this.messages = e.messages;
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

    out.writeInt(conflicts.size());
    for (LongKeyMap.Entry<SerializedObject> entry : conflicts.entrySet()) {
      out.writeLong(entry.getKey());
      entry.getValue().write(out);
    }

    out.writeInt(messages.size());
    for (String msg : messages) {
      out.writeUTF(msg);
    }
  }

  /* readMessage */
  protected StorePrepareFailedMessage(DataInput in) throws IOException {
    super(MessageType.STORE_PREPARE_FAILED);
    this.tid = in.readLong();

    int size = in.readInt();
    this.conflicts = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      conflicts.put(onum, new SerializedObject(in));
    }

    size = in.readInt();
    this.messages = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      messages.add(in.readUTF());
    }
  }

  @Override
  public String toString() {
    return "Failed to prepare at store with messages: " + messages.toString()
        + " and conflicts: " + conflicts.toString();
  }
}
