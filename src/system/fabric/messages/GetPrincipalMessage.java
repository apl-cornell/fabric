package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.remote.RemoteWorker;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.NodePrincipal;
import fabric.net.UnreachableNodeException;

public class GetPrincipalMessage
     extends Message<GetPrincipalMessage.Response>
  implements MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public GetPrincipalMessage() {
    super(MessageType.GET_PRINCIPAL);
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final NodePrincipal principal;

    public Response(NodePrincipal principal) {
      this.principal = principal;
    }

  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // convenience method for sending                                           //
  //////////////////////////////////////////////////////////////////////////////

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

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) {
  }

  /* readMessage */
  protected GetPrincipalMessage(DataInput in) {
    this();
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeUTF(r.principal.$getStore().name());
    out.writeLong(r.principal.$getOnum());
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    Store store = Worker.getWorker().getStore(in.readUTF());
    NodePrincipal principal = new NodePrincipal._Proxy(store, in.readLong());

    return new Response(principal);
  }

}
