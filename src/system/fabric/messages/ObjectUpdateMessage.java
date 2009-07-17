package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;

import fabric.client.Client;
import fabric.client.remote.RemoteClient;
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

  private final String core;
  private final long onum;
  private final Glob update;

  public ObjectUpdateMessage(String core, long onum, Glob update) {
    super(MessageType.OBJECT_UPDATE);
    this.core = core;
    this.onum = onum;
    this.update = update;
  }

  /**
   * Deserialization constructor.
   */
  protected ObjectUpdateMessage(DataInput in) throws IOException,
      BadSignatureException {
    super(MessageType.OBJECT_UPDATE);

    this.core = in.readUTF();
    this.onum = in.readLong();

    PublicKey key = Client.getClient().getCore(core).getPublicKey();
    this.update = new Glob(key, in);
  }

  @Override
  public Response dispatch(fabric.client.remote.Worker w) {
    return w.handle(this);
  }

  public Response send(RemoteClient client) {
    try {
      return send(client, false);
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
    out.writeUTF(core);
    out.writeLong(onum);
    update.write(out);
  }

}
