package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.Worker;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.NodePrincipal;
import fabric.messages.Message;

public class GetPrincipalMessage extends
    InterClientMessage<GetPrincipalMessage.Response> {

  public static class Response implements Message.Response {
    public final NodePrincipal principal;

    public Response(NodePrincipal principal) {
      this.principal = principal;
    }

    Response(DataInput in) throws IOException {
      Core core = Client.getClient().getCore(in.readUTF());
      this.principal = new NodePrincipal.$Proxy(core, in.readLong());
    }

    public void write(DataOutput out) throws IOException {
      out.writeUTF(principal.$getCore().name());
      out.writeLong(principal.$getOnum());
    }
  }

  public GetPrincipalMessage() {
    super(MessageType.GET_PRINCIPAL);
  }

  public GetPrincipalMessage(DataInput in) {
    this();
  }

  @Override
  public Response dispatch(Worker handler) {
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
  public void write(DataOutput out) {
  }

}
