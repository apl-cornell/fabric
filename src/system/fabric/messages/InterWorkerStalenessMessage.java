package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/**
 * Represents a request to check staleness of data in a transaction.
 */
public final class InterWorkerStalenessMessage
           extends Message<InterWorkerStalenessMessage.Response, RuntimeException>
        implements MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public final TransactionID tid;
  
  public InterWorkerStalenessMessage(TransactionID tid) {
    super(MessageType.INTERWORKER_STALENESS, RuntimeException.class);
    this.tid = tid;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    /** True iff stale objects were found. */
    public final boolean result;
    
    public Response(boolean result) {
      this.result = result;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    this.tid.write(out);
  }

  /* readMessage */
  protected InterWorkerStalenessMessage(DataInput in) throws IOException {
    this(new TransactionID(in));
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.result);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response(in.readBoolean());
  }

}
