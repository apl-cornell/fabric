package fabric.messages;

import java.io.*;
import java.security.cert.Certificate;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.NodePrincipal;
import fabric.net.UnreachableNodeException;
import fabric.worker.RemoteStore;
import fabric.worker.debug.Timing;

/**
 * A request to get the certificate chain that certifies a store's public SSL
 * key.
 */
public class GetCertChainMessage
     extends Message<GetCertChainMessage.Response>
  implements MessageToStore
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public GetCertChainMessage() {
    super(MessageType.GET_CERT_CHAIN);
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final Certificate[] certificateChain;

    public Response(Certificate[] certificateChain) {
      this.certificateChain = certificateChain;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler h) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // convenience method for sending                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Response send(RemoteStore store) throws UnreachableNodeException {
    try {
      Timing.STORE.begin();
      return send(store, false);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from store.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) {
  }

  /* readMessage */
  protected GetCertChainMessage(DataInput in) {
    this();
  }
  
  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    writeObject(out, r.certificateChain);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    Certificate[] certChain = readObject(in, Certificate[].class);
    return new Response(certChain);
  }

}
