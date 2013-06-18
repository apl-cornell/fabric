package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.WarrantyRefreshGroup;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.WarrantyRefreshGlob;
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
  public final WarrantyRefreshGroup warranties;

  public final LongKeyMap<WarrantyRefreshGlob> warrantyGlobs;

  private WarrantyRefreshMessage(String store, WarrantyRefreshGroup warranties,
      LongKeyMap<WarrantyRefreshGlob> warrantyGlobs) {
    super(MessageType.WARRANTY_REFRESH, NoException.class);
    this.store = store;
    this.warranties = warranties;
    this.warrantyGlobs = warrantyGlobs;
  }

  /**
   * Creates a warranty-refresh notification to be sent to a dissemination node.
   * 
   * @param store
   *          the store from which the notification originated.
   *          
   * @param warrantyGlobs
   *          the set of encrypted refreshed warranties, keyed by the head
   *          object's onum.
   */
  public WarrantyRefreshMessage(String store,
      LongKeyMap<WarrantyRefreshGlob> warrantyGlobs) {
    this(store, null, warrantyGlobs);
  }

  /**
   * Creates a warranty-refresh notification to be sent to a worker node.
   * 
   * @param warranties
   *          the set of refreshed warranties.
   */
  public WarrantyRefreshMessage(WarrantyRefreshGroup warranties) {
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
      out.writeInt(warrantyGlobs.size());
      for (LongKeyMap.Entry<WarrantyRefreshGlob> entry : warrantyGlobs
          .entrySet()) {
        long onum = entry.getKey();
        WarrantyRefreshGlob glob = entry.getValue();

        out.writeLong(onum);
        glob.write(out);
      }
    } else {
      out.writeBoolean(false);
      warranties.write(out);
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
      this.warrantyGlobs = new LongKeyHashMap<WarrantyRefreshGlob>(mapSize);
      for (int i = 0; i < mapSize; i++) {
        long onum = in.readLong();
        WarrantyRefreshGlob glob = new WarrantyRefreshGlob(in);
        warrantyGlobs.put(onum, glob);
      }
    } else {
      this.store = null;
      this.warrantyGlobs = null;
      this.warranties = new WarrantyRefreshGroup(in);
    }
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
