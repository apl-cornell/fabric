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
 * Represents message to unsubscribe a worker from updates.
 */
public class UnsubscribeMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final LongSet unsubscribes;

  /**
   * TODO
   */
  public UnsubscribeMessage(LongSet unsubscribes) {
    super(MessageType.UNSUBSCRIBE);
    this.unsubscribes = unsubscribes;
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
    out.writeInt(unsubscribes.size());
    for (LongIterator iter = unsubscribes.iterator(); iter.hasNext();) {
      out.writeLong(iter.next());
    }
  }

  /* readMessage */
  protected UnsubscribeMessage(DataInput in) throws IOException {
    super(MessageType.UNSUBSCRIBE);

    int size = in.readInt();
    unsubscribes = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      unsubscribes.add(in.readLong());
    }
  }
}
