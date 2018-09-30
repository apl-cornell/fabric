package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>BulkReadMessage</code> represents a request from a worker to read
 * multiple objects at a store.
 */
public class BulkReadMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The onum of the object to read. */
  public final LongSet onums;

  public BulkReadMessage(LongSet onums) {
    super(MessageType.BULK_READ_ONUM);
    this.onums = onums;
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
    out.writeInt(onums.size());
    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      out.writeLong(iter.next());
    }
  }

  /* readMessage */
  protected BulkReadMessage(DataInput in) throws IOException {
    this(new LongHashSet());
    int size = in.readInt();
    for (int i = 0; i < size; i++) {
      this.onums.add(in.readLong());
    }
  }
}
