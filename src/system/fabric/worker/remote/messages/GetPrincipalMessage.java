package fabric.worker.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Core;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.MessageHandlerThread;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.NodePrincipal;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;

public class GetPrincipalMessage extends
    InterWorkerMessage<GetPrincipalMessage.Response> {

  public static class Response implements Message.Response {
    public final NodePrincipal principal;

    public Response(NodePrincipal principal) {
      this.principal = principal;
    }

    Response(DataInput in) throws IOException {
      Core core = Worker.getWorker().getCore(in.readUTF());
      this.principal = new NodePrincipal._Proxy(core, in.readLong());
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
  public Response dispatch(MessageHandlerThread handler) {
    return handler.handle(this);
  }

  public Response send(RemoteWorker remoteWorker)
      throws UnreachableNodeException {
    try {
      return super.send(remoteWorker, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker.", e);
    }
  }

  @Override
  public Response response(RemoteWorker worker, DataInput in)
      throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) {
  }

}
