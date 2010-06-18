package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.debug.Timing;
import fabric.common.*;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.NodePrincipal;
import fabric.net.RemoteNode;

public class AbortTransactionMessage
     extends Message<AbortTransactionMessage.Response>
  implements MessageToStore, MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  /** The tid for the transaction that is aborting.  */
  public final TransactionID tid;

  public AbortTransactionMessage(TransactionID tid) {
    super(MessageType.ABORT_TRANSACTION);
    this.tid = tid;
  }


  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public Response() {
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler h) throws FabricException {
    return h.handle(p, this);
  }
  
  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // convenience method for sending                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Response send(RemoteNode node) {
    try {
      Timing.STORE.begin();
      return send(node, true);
    } catch (FabricException e) {
      throw new InternalError(e);
    } finally {
      Timing.STORE.end();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    tid.write(out);
  }

  /* readMessage */
  protected AbortTransactionMessage(DataInput in) throws IOException {
    this(new TransactionID(in));
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) {
  }

  @Override
  protected Response readResponse(DataInput in) {
    return new Response();
  }
}
