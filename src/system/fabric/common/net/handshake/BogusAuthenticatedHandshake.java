package fabric.common.net.handshake;

import java.io.*;
import java.net.Socket;

import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A handshake protocol with bogus authentication for testing purposes.
 */
public class BogusAuthenticatedHandshake extends HandshakeProtocol {

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
    super(ProtocolType.BOGUS);
    Principal localPrincipal = Worker.getWorker().getPrincipal();
    this.principalStoreName = localPrincipal.$getStore().name();
    this.principalOnum = localPrincipal.$getOnum();
  }

  @Override
  public ShakenSocket initiateImpl(String name, Socket s)
      throws IOException {
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

  @Override
  public ShakenSocket receive(Socket s) throws IOException {
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
}
