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
    if (localPrincipal == null) {
      this.principalStoreName = null;
      this.principalOnum = 0;
    } else {
      this.principalStoreName = localPrincipal.$getStore().name();
      this.principalOnum = localPrincipal.$getOnum();
    }
    this.fieldsInitialized = true;
  }

  @Override
  public ShakenSocket initiateImpl(String name, Socket s) throws IOException {
    if (!fieldsInitialized) initFields();

    DataInputStream in = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());

    out.writeUTF(name);
    if (principalStoreName == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeUTF(principalStoreName);
      out.writeLong(principalOnum);
    }
    out.flush();

    NodePrincipal remotePrincipal = null;
    if (in.readBoolean()) {
      Store remotePrincipalStore = Worker.getWorker().getStore(in.readUTF());
      long remotePrincipalOnum = in.readLong();
      remotePrincipal =
          new NodePrincipal._Proxy(remotePrincipalStore, remotePrincipalOnum);
    }

    return new ShakenSocket(name, remotePrincipal, s);
  }

  @Override
  public ShakenSocket receive(Socket s) throws IOException {
    if (!fieldsInitialized) initFields();

    DataInputStream in = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());

    if (principalStoreName == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeUTF(principalStoreName);
      out.writeLong(principalOnum);
    }
    out.flush();

    String name = in.readUTF();
    
    NodePrincipal remotePrincipal = null;
    if (in.readBoolean()) {
      Store remotePrincipalStore = Worker.getWorker().getStore(in.readUTF());
      long remotePrincipalOnum = in.readLong();
      remotePrincipal =
          new NodePrincipal._Proxy(remotePrincipalStore, remotePrincipalOnum);
    }

    return new ShakenSocket(name, remotePrincipal, s);
  }
}
