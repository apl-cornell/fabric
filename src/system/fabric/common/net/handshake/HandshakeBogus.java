package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SysUtil.Thunk;
import fabric.common.net.RemoteIdentity;
import fabric.lang.security.NodePrincipal;
import fabric.net.RemoteNode;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * A handshake protocol with bogus authentication for testing purposes.
 *
 * @param <Node> the type of node at the remote endpoint.
 */
public class HandshakeBogus<Node extends RemoteNode<Node>>
    implements Protocol<Node> {

  // /////////////////////////////
  // Protocol
  // ~~~~~~~~
  // C->S: server's host name
  // C->S: client's principal oid
  // S->C: server's principal oid
  // /////////////////////////////

  private Thunk<String> store; // null for bottom
  private Thunk<Long> onum;

  public HandshakeBogus(final String store, final long onum) {
    printWarning();
    this.store = new Thunk<String>() {
      @Override
      protected String create() {
        return store;
      }
    };
    this.onum = new Thunk<Long>() {
      @Override
      protected Long create() {
        return onum;
      }
    };

  }

  public HandshakeBogus() {
    printWarning();
    this.store = new Thunk<String>() {
      @Override
      protected String create() {
        return Worker.getWorker().getPrincipal().$getStore().name();
      }
    };
    this.onum = new Thunk<Long>() {
      @Override
      protected Long create() {
        return Worker.getWorker().getPrincipal().$getOnum();
      }
    };
  }

  private void printWarning() {
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
        "!!! An insecure channel is being treated as a secure channel !!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
        "!!! set useSSL to true unless you are testing performance    !!!");
    Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  @Override
  public ShakenSocket<Node> initiate(Node node, SocketChannel s)
      throws IOException {
    DataInputStream in = new DataInputStream(s.socket().getInputStream());
    DataOutputStream out = new DataOutputStream(s.socket().getOutputStream());

    out.writeUTF(node.name);
    out.writeUTF(Worker.getWorker().getName());
    writePrincipal(out);

    return new ShakenSocket<>(node.name,
        new RemoteIdentity<>(node, readPrincipal(in)), s);
  }

  @Override
  public ShakenSocket<RemoteWorker> receive(SocketChannel s)
      throws IOException {
    DataInputStream in = new DataInputStream(s.socket().getInputStream());
    DataOutputStream out = new DataOutputStream(s.socket().getOutputStream());

    String name = in.readUTF();
    String remoteWorkerName = in.readUTF();
    writePrincipal(out);

    RemoteWorker remoteWorker = Worker.getWorker().getWorker(remoteWorkerName);
    return new ShakenSocket<>(name,
        new RemoteIdentity<>(remoteWorker, readPrincipal(in)), s);
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
      long onum = in.readLong();
      result = new NodePrincipal._Proxy(store, onum);
    }

    return result;
  }
}
