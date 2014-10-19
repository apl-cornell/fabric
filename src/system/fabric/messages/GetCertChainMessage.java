package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.cert.Certificate;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

/**
 * A request to get the certificate chain that certifies a store's public SSL
 * key.
 */
public class GetCertChainMessage extends
Message<GetCertChainMessage.Response, fabric.messages.Message.NoException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public GetCertChainMessage() {
    super(MessageType.GET_CERT_CHAIN, NoException.class);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final Certificate[] certificateChain;

    public Response(Certificate[] certificateChain) {
      this.certificateChain = certificateChain;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

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
