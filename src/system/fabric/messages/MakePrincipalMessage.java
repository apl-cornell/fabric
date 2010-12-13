package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.NodePrincipal;

public final class MakePrincipalMessage
           extends Message<MakePrincipalMessage.Response, FabricGeneralSecurityException>
{
  
  //////////////////////////////////////////////////////////////////////////////
  // message contents                                                         //
  //////////////////////////////////////////////////////////////////////////////

  public final PublicKey requesterKey;
  
  public MakePrincipalMessage(PublicKey requesterKey) {
    super(MessageType.MAKE_PRINCIPAL, FabricGeneralSecurityException.class);
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

  @Override
  public Response dispatch(NodePrincipal p, MessageHandler handler)
      throws ProtocolError, FabricGeneralSecurityException {
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
    super(MessageType.MAKE_PRINCIPAL, FabricGeneralSecurityException.class);
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
