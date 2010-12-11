package fabric.messages;

import java.io.*;
import java.security.cert.Certificate;

import fabric.common.exceptions.FabricException;
import fabric.lang.security.NodePrincipal;

/**
 * A request to get the certificate chain that certifies a store's public SSL
 * key.
 */
public class GetCertChainMessage
     extends Message<GetCertChainMessage.Response, RuntimeException>
  implements MessageToStore<FabricException>
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public GetCertChainMessage() {
    super(MessageType.GET_CERT_CHAIN, RuntimeException.class);
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
