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

  // Field initialization is delayed because it depends on the worker's
  // initialization, and the constructor is called before the worker is fully
  // initialized.
  private boolean fieldsInitialized;
  private String principalStoreName;
  private long principalOnum;

  /**
   * @param principal
   *          the local node's principal object.
   */
  public BogusAuthenticatedHandshake() {
    super(ProtocolType.BOGUS);
    this.fieldsInitialized = false;
  }
  
  private void initFields() {
    Principal localPrincipal = Worker.getWorker().getPrincipal();
    this.principalStoreName = localPrincipal.$getStore().name();
    this.principalOnum = localPrincipal.$getOnum();
    this.fieldsInitialized = true;
  }

  @Override
  public ShakenSocket initiateImpl(String name, Socket s)
      throws IOException {
    if (!fieldsInitialized) initFields();
    
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
    if (!fieldsInitialized) initFields();
    
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
