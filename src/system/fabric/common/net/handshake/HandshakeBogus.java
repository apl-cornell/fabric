package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Util.Thunk;
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

  private Thunk<String> store; // null for bottom
  private Thunk<Long>   onum;
  
  public HandshakeBogus(final String store, final long onum) {
    printWarning();
    this.store = new Thunk<String>() {
      @Override protected String create() { return store; }
    };
    this.onum = new Thunk<Long> () {
      @Override protected Long create() { return onum; }
    };
    
  }
  
  public HandshakeBogus() {
    printWarning();
    this.store = new Thunk<String>() {
      @Override protected String create() {
        return Worker.getWorker().getPrincipal().$getStore().name();
      }
    };
    this.onum  = new Thunk<Long> () {
      @Override protected Long create() {
        return Worker.getWorker().getPrincipal().$getOnum();
      }
    };
  }
  
  private void printWarning() {
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING, "!!! An insecure channel is being treated as a secure channel !!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING, "!!! set useSSL to true unless you are testing performance    !!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
      out.writeUTF(store.get());
      out.writeLong(onum.get());
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
