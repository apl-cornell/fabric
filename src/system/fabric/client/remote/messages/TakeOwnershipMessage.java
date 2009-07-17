package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.Worker;
import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.messages.Message;

/**
 * Represents a request from a client to take ownership of an object from
 * another client.
 */
public class TakeOwnershipMessage extends
    InterClientMessage<TakeOwnershipMessage.Response> {

  public final TransactionID tid;
  public final Core core;
  public final long onum;

  public static class Response implements Message.Response {
    public final boolean success;

    public Response(boolean success) {
      this.success = success;
    }

    Response(DataInput in) throws IOException {
      this.success = in.readBoolean();
    }

    public void write(DataOutput out) throws IOException {
      out.writeBoolean(success);
    }
  }

  public TakeOwnershipMessage(TransactionID tid, Core core, long onum) {
    super(MessageType.TAKE_OWNERSHIP);

    this.tid = tid;
    this.core = core;
    this.onum = onum;
  }

  public TakeOwnershipMessage(DataInput in) throws IOException {
    this(new TransactionID(in), Client.getClient().getCore(in.readUTF()), in
        .readLong());
  }

  @Override
  public Response dispatch(Worker handler) throws ProtocolError {
    return handler.handle(this);
  }

  public Response send(RemoteClient remoteClient)
      throws UnreachableNodeException {
    try {
      return super.send(remoteClient, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from client.", e);
    }
  }

  @Override
  public Response response(RemoteClient client, DataInput in)
      throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
    out.writeUTF(core.name());
    out.writeLong(onum);
  }

}
