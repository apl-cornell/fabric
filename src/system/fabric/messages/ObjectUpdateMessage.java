package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;

import fabric.client.Client;
import fabric.client.remote.RemoteClient;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.BadSignatureException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.Glob;

/**
 * Represents push notification that an object has been updated.
 */
public class ObjectUpdateMessage extends
    Message<RemoteClient, ObjectUpdateMessage.Response> {

  public static class Response implements Message.Response {
    public final boolean resubscribe;

    public Response(boolean resubscribe) {
      this.resubscribe = resubscribe;
    }

    /**
     * Deserialization constructor.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteClient node, DataInput in) throws IOException {
      this.resubscribe = in.readBoolean();
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(resubscribe);
    }
  }

  public final String core;
  public final long onum;
  public final Glob glob;
  public final ObjectGroup group;

  private ObjectUpdateMessage(String core, long onum, Glob glob,
      ObjectGroup group) {
    super(MessageType.OBJECT_UPDATE);
    this.core = core;
    this.onum = onum;
    this.glob = glob;
    this.group = group;

    // Exactly one of glob and group needs to be null.
    if ((glob == null) == (group == null)) {
      throw new InternalError();
    }
  }

  public ObjectUpdateMessage(String core, long onum, Glob update) {
    this(core, onum, update, null);
  }

  public ObjectUpdateMessage(long onum, ObjectGroup update) {
    this(null, onum, null, update);
  }

  /**
   * Deserialization constructor.
   */
  protected ObjectUpdateMessage(DataInput in) throws IOException,
      BadSignatureException {
    super(MessageType.OBJECT_UPDATE);

    this.onum = in.readLong();

    if (in.readBoolean()) {
      this.core = in.readUTF();
      PublicKey key = Client.getClient().getCore(core).getPublicKey();
      this.glob = new Glob(key, in);
      this.group = null;
    } else {
      this.core = null;
      this.glob = null;
      this.group = new ObjectGroup(in);
    }
  }

  @Override
  public Response dispatch(fabric.client.remote.Worker w) {
    return w.handle(this);
  }

  public Response send(RemoteClient client) {
    try {
      boolean encrypt = group != null;
      return send(client, encrypt);
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from client.", e);
    }
  }

  @Override
  public Response response(RemoteClient node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(onum);

    if (group == null) {
      out.writeBoolean(true);
      out.writeUTF(core);
      glob.write(out);
    } else {
      out.writeBoolean(false);
      group.write(out);
    }
  }

}
