package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

public class BulkReadReply extends AsyncMessage {
  public final Collection<ObjectGroup> groups;

  public BulkReadReply(Collection<ObjectGroup> groups) {
    super(MessageType.BULK_READ_REPLY);
    this.groups = groups;
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
    if (groups != null && groups.size() > 0) {
      out.writeBoolean(true);
      out.writeInt(groups.size());
      for (ObjectGroup group : groups) {
        group.write(out);
      }
    } else out.writeBoolean(false);
  }

  public BulkReadReply(DataInput in) throws IOException {
    super(MessageType.BULK_READ_REPLY);
    if (in.readBoolean()) {
      int size = in.readInt();
      this.groups = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        groups.add(new ObjectGroup(in));
      }
    } else {
      this.groups = Collections.<ObjectGroup> emptyList();
    }
  }
}
