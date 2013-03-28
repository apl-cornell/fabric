package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.VersionWarranty;
import fabric.common.VersionWarranty.Binding;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.messages.Message.NoException;

/**
 * Represents push notification that warranties have been refreshed.
 */
public class WarrantyRefreshMessage extends
    Message<WarrantyRefreshMessage.Response, NoException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final String store;
  public final List<Binding> warranties;

  public final LongKeyMap<List<Binding>> warrantyGroups;

  private WarrantyRefreshMessage(String store, List<Binding> warranties,
      LongKeyMap<List<Binding>> warrantyGroups) {
    super(MessageType.WARRANTY_REFRESH, NoException.class);
    this.store = store;
    this.warranties = warranties;
    this.warrantyGroups = warrantyGroups;
  }

  public WarrantyRefreshMessage(String store,
      LongKeyMap<List<Binding>> warrantyGroups) {
    this(store, null, warrantyGroups);
  }

  public WarrantyRefreshMessage(List<Binding> warranties) {
    this(null, warranties, null);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<Long> resubscriptions;

    public Response(List<Long> resubscriptions) {
      this.resubscriptions = resubscriptions;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity client, MessageHandler h)
      throws ProtocolError, fabric.messages.Message.NoException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    if (warranties == null) {
      out.writeBoolean(true);
      out.writeUTF(store);

      // Write out warranty groups.
      // XXX this should be encrypted and signed.
      out.writeInt(warrantyGroups.size());
      for (LongKeyMap.Entry<List<Binding>> entry : warrantyGroups.entrySet()) {
        long onum = entry.getKey();
        List<Binding> group = entry.getValue();

        out.writeLong(onum);

        out.writeInt(group.size());
        for (Binding update : group) {
          writeBinding(update, out);
        }
      }
    } else {
      out.writeBoolean(false);
      out.writeInt(warranties.size());
      for (Binding update : warranties) {
        writeBinding(update, out);
      }
    }
  }

  /* readMessage */
  protected WarrantyRefreshMessage(DataInput in) throws IOException {
    super(MessageType.WARRANTY_REFRESH, NoException.class);

    if (in.readBoolean()) {
      this.store = in.readUTF();
      this.warranties = null;

      // Read in the warranty groups.
      // XXX should decrypt and verify once we encrypt and sign this
      int mapSize = in.readInt();
      this.warrantyGroups = new LongKeyHashMap<List<Binding>>(mapSize);
      for (int i = 0; i < mapSize; i++) {
        long onum = in.readLong();

        int listSize = in.readInt();
        List<Binding> updateGroup = new ArrayList<Binding>(listSize);
        for (int j = 0; j < listSize; j++) {
          updateGroup.add(readBinding(in));
        }

        warrantyGroups.put(onum, updateGroup);
      }
    } else {
      this.store = null;
      this.warrantyGroups = null;

      int updateSize = in.readInt();
      this.warranties = new ArrayList<Binding>(updateSize);
      for (int i = 0; i < updateSize; i++) {
        warranties.add(readBinding(in));
      }
    }
  }

  private Binding readBinding(DataInput in) throws IOException {
    return new VersionWarranty(in.readLong()).new Binding(in.readLong(),
        in.readInt());
  }

  private void writeBinding(Binding binding, DataOutput out) throws IOException {
    out.writeLong(binding.expiry());
    out.writeLong(binding.onum);
    out.writeInt(binding.versionNumber);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<Long> resubscriptions = new ArrayList<Long>(size);
    for (int i = 0; i < size; i++) {
      resubscriptions.add(in.readLong());
    }

    return new Response(resubscriptions);
  }

  @Override
  protected void writeResponse(DataOutput out, Response response)
      throws IOException {
    out.writeInt(response.resubscriptions.size());
    for (long onum : response.resubscriptions) {
      out.writeLong(onum);
    }
  }

}
