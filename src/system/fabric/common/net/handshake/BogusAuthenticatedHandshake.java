package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fabric.common.net.naming.SocketAddress;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A handshake protocol with bogus authentication for testing purposes.
 */
public class BogusAuthenticatedHandshake implements HandshakeProtocol {

  // /////////////////////////////
  // Protocol
  // ~~~~~~~~
  // C->S: server's host name
  // C->S: client's principal oid
  // S->C: server's principal oid
  // /////////////////////////////

  private final String principalStoreName;
  private final long principalOnum;

  /**
   * @param principal
   *          the local node's principal object.
   */
  public BogusAuthenticatedHandshake() {
    Principal localPrincipal = Worker.getWorker().getPrincipal();
    this.principalStoreName = localPrincipal.$getStore().name();
    this.principalOnum = localPrincipal.$getOnum();
  }

  public ShakenSocket initiate(String name, SocketAddress addr)
      throws IOException {
    Socket s = new Socket(addr.getAddress(), addr.getPort());
    fixSocket(s);

    DataInputStream in = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());

    out.writeUTF(name);
    out.writeUTF(principalStoreName);
    out.writeLong(principalOnum);
    out.flush();

    Store remotePrincipalStore = Worker.getWorker().getStore(in.readUTF());
    long remotePrincipalOnum = in.readLong();
    NodePrincipal remotePrincipal =
        new NodePrincipal._Proxy(remotePrincipalStore, remotePrincipalOnum);

    return new ShakenSocket(name, remotePrincipal, s);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    fixSocket(s);

    DataInputStream in = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    
    out.writeUTF(principalStoreName);
    out.writeLong(principalOnum);
    out.flush();

    String name = in.readUTF();
    Store remotePrincipalStore = Worker.getWorker().getStore(in.readUTF());
    long remotePrincipalOnum = in.readLong();
    NodePrincipal remotePrincipal =
        new NodePrincipal._Proxy(remotePrincipalStore, remotePrincipalOnum);
    
    return new ShakenSocket(name, remotePrincipal, s);
  }

  private void fixSocket(Socket s) throws IOException {
    s.setSoLinger(false, 0);
    s.setTcpNoDelay(true);
  }
}
