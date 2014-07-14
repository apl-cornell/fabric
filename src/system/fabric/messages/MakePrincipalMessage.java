package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

public final class MakePrincipalMessage extends
Message<MakePrincipalMessage.Response, FabricGeneralSecurityException> {

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final PublicKey requesterKey;

  public MakePrincipalMessage(PublicKey requesterKey) {
    super(MessageType.MAKE_PRINCIPAL, FabricGeneralSecurityException.class);
    this.requesterKey = requesterKey;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    /**
     * The onum for the Principal that was created.
     */
    public final long onum;

    /**
     * A certificate binding the requester Key to the OID of the generated
     * principal. This is the first element in a certificate chain. The rest of
     * the chain is in <code>certChain</code>.
     */
    public final X509Certificate cert;

    /**
     * The rest of the certificate chain.
     */
    public final X509Certificate[] certChain;

    public Response(long onum, X509Certificate cert, X509Certificate[] certChain) {
      this.onum = onum;
      this.cert = cert;
      this.certChain = certChain;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler handler) throws ProtocolError,
      FabricGeneralSecurityException {
    return handler.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

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
    out.writeInt(response.certChain.length);
    for (X509Certificate cert : response.certChain)
      writeObject(out, cert);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long onum = in.readLong();
    X509Certificate cert = readObject(in, X509Certificate.class);
    X509Certificate[] certChain = new X509Certificate[in.readInt()];
    for (int i = 0; i < certChain.length; i++)
      certChain[i] = readObject(in, X509Certificate.class);

    return new Response(onum, cert, certChain);
  }

}
