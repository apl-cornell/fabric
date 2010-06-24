package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.lang.security.NodePrincipal;

public final class MakePrincipalMessage
           extends Message<MakePrincipalMessage.Response, FabricException>
        implements MessageToStore
{
  
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  PublicKey requesterKey;
  
  public MakePrincipalMessage() {
    super(MessageType.MAKE_PRINCIPAL, FabricException.class);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    /* The Principal that was created */
    public final NodePrincipal     result;
    
    /* A certificate chain from the CA binding the requester Key to the OID of result */
    public final X509Certificate[] certChain;
    
    public Response(NodePrincipal result, X509Certificate[] certChain) {
      this.result    = result;
      this.certChain = certChain;
    }
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler handler)
  throws FabricException {
    return handler.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }
  
  /* readMessage */
  protected MakePrincipalMessage(DataInput in) throws IOException {
    this();
    throw new NotImplementedException();
  }

  @Override
  protected void writeResponse(DataOutput out, Response response)
      throws IOException {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }

}
