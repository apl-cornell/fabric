package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import fabric.lang.security.NodePrincipal;

public final class MakePrincipalMessage
           extends Message<MakePrincipalMessage.Response, GeneralSecurityException>
        implements MessageToStore<GeneralSecurityException>
{
  
  //////////////////////////////////////////////////////////////////////////////
  // message contents                                                         //
  //////////////////////////////////////////////////////////////////////////////

  public final PublicKey requesterKey;
  
  public MakePrincipalMessage(PublicKey requesterKey) {
    super(MessageType.MAKE_PRINCIPAL, GeneralSecurityException.class);
    this.requesterKey = requesterKey;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    /**
     * The onum for the Principal that was created.
     */
    public final long onum;
    
    /**
     * A certificate binding the requester Key to the OID of the generated principal.
     */
    public final X509Certificate cert;
    
    public Response(long onum, X509Certificate cert) {
      this.onum = onum;
      this.cert = cert;
    }
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler handler)
  throws GeneralSecurityException {
    return handler.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    writeObject(out, requesterKey);
  }
  
  /* readMessage */
  protected MakePrincipalMessage(DataInput in) throws IOException {
    super(MessageType.MAKE_PRINCIPAL, GeneralSecurityException.class);
    this.requesterKey = readObject(in, PublicKey.class);
  }

  @Override
  protected void writeResponse(DataOutput out, Response response)
      throws IOException {
    out.writeLong(response.onum);
    writeObject(out, response.cert);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long onum = in.readLong();
    X509Certificate cert = readObject(in, X509Certificate.class);
    return new Response(onum, cert);
  }

}
