package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Store;
import fabric.worker.Worker;

public class GetPrincipalMessage
     extends Message<GetPrincipalMessage.Response, fabric.messages.Message.NoException>
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public GetPrincipalMessage() {
    super(MessageType.GET_PRINCIPAL, NoException.class);
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

  @Override
  public Response dispatch(NodePrincipal p, MessageHandler h) throws ProtocolError {
    return h.handle(p, this);
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
