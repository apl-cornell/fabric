package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fabric.lang.security.NodePrincipal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A handshake protocol with bogus authentication for testing purposes.
 */
public class HandshakeBogus implements Protocol {

  // /////////////////////////////
  // Protocol
  // ~~~~~~~~
  // C->S: server's host name
  // C->S: client's principal oid
  // S->C: server's principal oid
  // /////////////////////////////

  private String  principalStoreName;
  private long    principalOnum;

  /**
   * @param principal
   *          the local node's principal object.
   */
  public HandshakeBogus(fabric.lang.security.NodePrincipal principal) {
    this.principalStoreName = principal.$getStore().name();
    this.principalOnum      = principal.$getOnum();
  }
  
  public HandshakeBogus(String store, long onum) {
    this.principalStoreName = store;
    this.principalOnum      = onum;
  }

  public ShakenSocket initiate(String name, Socket s)
      throws IOException {
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

  public ShakenSocket receive(Socket s) throws IOException {
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
