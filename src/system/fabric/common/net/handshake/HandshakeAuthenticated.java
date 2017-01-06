package fabric.common.net.handshake;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

import fabric.common.Crypto;
import fabric.common.KeyMaterial;
import fabric.common.Logging;
import fabric.common.SysUtil;
import fabric.common.net.RemoteIdentity;
import fabric.lang.security.NodePrincipal;
import fabric.net.RemoteNode;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * @see https://apl.cs.cornell.edu/wiki/Fabric:Fabric_Communication_Layer
 * @author mdgeorge
 */
public class HandshakeAuthenticated<Node extends RemoteNode<Node>>
    implements Protocol<Node> {

  // ////////////////////////////////////////////////////////////////////////////
  // //
  // Authenticated Handshake Protocol: //
  // //
  // 1. A -> B: "B" //
  // 2. SSL Handshake: //
  // B -> A: cert chain certifying that B acts for "B" //
  // A -> B: a's store/onum, signed by a's store //
  // A -> B: cert chain for a's store //
  // 3. Principal exchange: //
  // B -> A: b's store/onum, signed by b's store //
  // B -> A: cert chain for b's store //
  // //
  // ////////////////////////////////////////////////////////////////////////////

  private final static class Receiver {
    public final KeyMaterial keys;
    public final SSLSocketFactory factory;

    public Receiver(KeyMaterial keys, SSLSocketFactory factory) {
      this.keys = keys;
      this.factory = factory;
    }
  }

  private final Map<String, Receiver> receivers;
  private final SSLSocketFactory initiatorFactory;
  private final Set<TrustAnchor> initiatorTrust;

  public HandshakeAuthenticated(KeyMaterial... endpoints)
      throws GeneralSecurityException {
    this.receivers = new HashMap<>(endpoints.length);

    for (KeyMaterial keys : endpoints) {
      SSLSocketFactory factory = createSSLFactory(keys);

      Receiver receiver = new Receiver(keys, factory);

      receivers.put(keys.getName(), receiver);
      NETWORK_CONNECTION_LOGGER.log(Level.INFO, "Adding receiver: {0}",
          keys.getName());
    }

    this.initiatorFactory = createSSLFactory(endpoints[0]);
    this.initiatorTrust = endpoints[0].getTrustedCerts();
  }

  @Override
  public ShakenSocket<Node> initiate(Node remoteNode, SocketChannel s)
      throws IOException {
    DataOutputStream clearOut =
        new DataOutputStream(s.socket().getOutputStream());
    clearOut.writeUTF(remoteNode.name);
    clearOut.flush();

    SSLSocket sock = (SSLSocket) initiatorFactory.createSocket(s,
        remoteNode.name, s.socket().getPort(), true);
    sock.setUseClientMode(true);
    sock.setNeedClientAuth(true);
    Logging.log(Logging.TIMING_LOGGER, Level.INFO,
        "Establishing SSL connection to {0}", remoteNode.name);
    sock.startHandshake();

    // Check that the name in the peer's certificate matches the node we're
    // supposed to be talking to.
    Certificate[] peerChain = sock.getSession().getPeerCertificates();
    X500Principal peerX500Principal =
        ((X509Certificate) peerChain[0]).getSubjectX500Principal();
    if (!remoteNode.name.equals(Crypto.getCN(peerX500Principal.getName()))) {
      throw new IOException(
          "Peer used a certificate for the wrong X.500 principal");
    }

    DataInputStream in = new DataInputStream(sock.getInputStream());
    X509Certificate[] peerPrincipalChain = readCertificateChain(in);

    RemoteIdentity<Node> peer =
        getAndVerifyRemoteIdentity(remoteNode, peerPrincipalChain, peerChain);
    return new ShakenSocket<>(remoteNode.name, peer, sock);
  }

  @Override
  public ShakenSocket<RemoteWorker> receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    Receiver receiver = receivers.get(name);

    if (null == receiver) throw new IOException(
        "Connection destined for non-listening server " + name);

    SSLSocket sock =
        (SSLSocket) receiver.factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(false);
    sock.setNeedClientAuth(true);
    sock.startHandshake();

    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    writeCertificateChain(out, receiver.keys.getPrincipalChain());
    out.flush();

    RemoteIdentity<RemoteWorker> peer =
        getRemoteWorkerIdentity(sock.getSession().getPeerCertificates());

    return new ShakenSocket<>(name, peer, sock);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // fabric trust and key managers //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * Simple key manager - clients always choose ".principal" alias,
   * corresponding to keys.getPrincipalChain(), while servers always choose
   * keys.getName() as an alias, corresponding to keys.getNameChain().
   */
  private static class FabricKeyManager implements X509KeyManager {
    private final KeyMaterial keys;

    public FabricKeyManager(KeyMaterial keys) {
      this.keys = keys;
    }

    @Override
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers,
        Socket s) {
      return ".principal";
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers,
        Socket s) {
      return keys.getName();
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
      if (alias.equals(".principal"))
        return keys.getPrincipalChain();
      else if (alias.equals(keys.getName()))
        return keys.getNameChain();
      else return null;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
      return new String[] { ".principal" };
    }

    @Override
    public PrivateKey getPrivateKey(String keyType) {
      if (keyType.equals(".principal"))
        return keys.getPrivateKey();
      else if (keyType.equals(keys.getName()))
        return keys.getPrivateKey();
      else return null;
    }

    @Override
    public String[] getServerAliases(String arg0, Principal[] arg1) {
      return new String[] { keys.getName() };
    }
  }

  /**
   * Simple trust manager - certificate chains are validated using PKIX with the
   * root of trust given by keys.getTrusted()
   */
  private static class FabricTrustManager implements X509TrustManager {
    private final KeyMaterial keys;

    public FabricTrustManager(KeyMaterial keys) {
      this.keys = keys;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      checkTrusted(chain);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      checkTrusted(chain);
    }

    private void checkTrusted(X509Certificate[] chain)
        throws CertificateException {
      try {
        Crypto.validateCertificateChain(chain, keys.getTrustedCerts());
      } catch (GeneralSecurityException e) {
        throw new CertificateException("failed to validate certificate", e);
      }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      X509Certificate[] result =
          new X509Certificate[keys.getTrustedCerts().size()];

      int i = 0;
      for (TrustAnchor a : keys.getTrustedCerts())
        result[i++] = a.getTrustedCert();

      return result;
    }
  }

  private static SSLSocketFactory createSSLFactory(KeyMaterial keys)
      throws GeneralSecurityException {
    SSLContext context = SSLContext.getInstance("SSL");
    KeyManager[] kms = new KeyManager[] { new FabricKeyManager(keys) };
    TrustManager[] tms = new TrustManager[] { new FabricTrustManager(keys) };
    context.init(kms, tms, null);

    return context.getSocketFactory();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // private helper methods //
  // ////////////////////////////////////////////////////////////////////////////

  private RemoteIdentity<Node> getAndVerifyRemoteIdentity(Node remoteNode,
      X509Certificate[] peerChain, Certificate[] sockChain) throws IOException {
    try {
      Crypto.validateCertificateChain(peerChain, initiatorTrust);
    } catch (GeneralSecurityException e) {
      throw new IOException(
          "failed to validate peer principal certificate chain", e);
    }

    // check that peerChain and ssl chain correspond to the same key.
    PublicKey sslKey = ((X509Certificate) sockChain[0]).getPublicKey();
    PublicKey certKey = peerChain[0].getPublicKey();

    if (!sslKey.equals(certKey)) {
      throw new IOException("Failed to authenticate peer principal.");
    }

    String workerCN =
        Crypto.getCN(peerChain[0].getSubjectX500Principal().getName());
    try {
      SysUtil.getPrincipalOnum(workerCN);
      String certNodeName = SysUtil.getNodeName(workerCN);
      if (!certNodeName.equals(remoteNode.name)) {
        throw new IOException("Failed to authenticate peer principal.");
      }
    } catch (IllegalArgumentException e) {
      throw new IOException("Failed to authenticate peer principal.", e);
    }

    return new RemoteIdentity<>(remoteNode, getPrincipal(peerChain));
  }

  private RemoteIdentity<RemoteWorker> getRemoteWorkerIdentity(
      Certificate[] chain) {
    X509Certificate head = (X509Certificate) chain[0];
    String workerCN = Crypto.getCN(head.getSubjectX500Principal().getName());

    String remoteWorkerName = SysUtil.getNodeName(workerCN);
    RemoteWorker remoteWorker = Worker.getWorker().getWorker(remoteWorkerName);

    return new RemoteIdentity<>(remoteWorker, getPrincipal(chain));
  }

  private NodePrincipal getPrincipal(Certificate[] chain) {
    X509Certificate head = (X509Certificate) chain[0];
    String storeName = Crypto.getCN(head.getIssuerX500Principal().getName());
    String workerCN = Crypto.getCN(head.getSubjectX500Principal().getName());

    Store store = Worker.getWorker().getStore(storeName);
    long onum = SysUtil.getPrincipalOnum(workerCN);
    return new NodePrincipal._Proxy(store, onum);
  }

  private static void writeCertificateChain(DataOutputStream out,
      X509Certificate[] chain) throws IOException {
    out.writeInt(chain.length);
    for (X509Certificate cert : chain)
      writeCertificate(out, cert);
  }

  private static X509Certificate[] readCertificateChain(DataInputStream in)
      throws IOException {
    int length = in.readInt();
    X509Certificate[] chain = new X509Certificate[length];
    for (int i = 0; i < length; i++)
      chain[i] = readCertificate(in);
    return chain;
  }

  private static void writeCertificate(DataOutputStream out,
      X509Certificate cert) throws IOException {
    try {
      byte[] encoded = cert.getEncoded();
      out.writeInt(encoded.length);
      out.write(encoded);
    } catch (final CertificateEncodingException e) {
      throw new IOException("failed to write certificate", e);
    }
  }

  private static X509Certificate readCertificate(DataInputStream in)
      throws IOException {
    try {
      int length = in.readInt();
      byte[] encoded = new byte[length];
      in.readFully(encoded);

      ByteArrayInputStream bytes = new ByteArrayInputStream(encoded);
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      return (X509Certificate) cf.generateCertificate(bytes);
    } catch (final CertificateException e) {
      throw new IOException("failed to read certificate", e);
    }
  }
}
