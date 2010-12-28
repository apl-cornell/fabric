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

  private String store; // null for bottom
  private long   onum;
  
  public HandshakeBogus(String store, long onum) {
    this.store = store;
    this.onum  = onum;
  }
  
  public static class BottomFactory implements Protocol.Factory {
    public HandshakeBogus create() {
      return new HandshakeBogus(null, 0);
    }
  }

  public static class FixedFactory implements Protocol.Factory {
    private final String store;
    private final long   onum;
    
    public FixedFactory(String store, long onum) {
      this.store = store;
      this.onum  = onum;
    }
    
    public HandshakeBogus create() {
      return new HandshakeBogus(store, onum);
    }
  }

  public static class WorkerFactory implements Protocol.Factory {
    public HandshakeBogus create() {
      NodePrincipal p = Worker.getWorker().getPrincipal();
      return new HandshakeBogus(p.$getStore().name(), p.$getOnum());
    }
  }
  
  public ShakenSocket initiate(String name, Socket s)
      throws IOException {
    DataInputStream  in  = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());

    out.writeUTF(name);
    writePrincipal(out);
    
    return new ShakenSocket(name, readPrincipal(in), s);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream  in  = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());

    String name = in.readUTF();
    writePrincipal(out);
    
    return new ShakenSocket(name, readPrincipal(in), s);
  }
  
  private void writePrincipal(DataOutputStream out) throws IOException {
    if (store == null) {
      out.writeBoolean(false);
    } else {
      out.writeBoolean(true);
      out.writeUTF(store);
      out.writeLong(onum);
    }
    
    out.flush();
  }
  
  private NodePrincipal readPrincipal(DataInputStream in) throws IOException {
    NodePrincipal result = null;
    if (in.readBoolean()) {
      Store store = Worker.getWorker().getStore(in.readUTF());
      long  onum  = in.readLong();
      result = new NodePrincipal._Proxy(store, onum);
    }
    
    return result;
  }
}
