package fabric.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.common.exceptions.NoSuchNodeError;
import fabric.lang.NodePrincipal;

/**
 * Abstracts remote cores and remote clients.
 */
public abstract class RemoteNode {

  /**
   * The DNS hostname of the node.
   */
  public final String name;

  /**
   * The connections to the actual node.
   */
  private transient Socket sslConn;
  private transient Socket unencryptedConn;
  private transient DataInputStream sslIn;
  private transient DataOutputStream sslOut;
  private transient DataInputStream unencryptedIn;
  private transient DataOutputStream unencryptedOut;

  protected RemoteNode(String name) {
    this.name = name;
    this.commThread = new CommThread();
    this.commThread.start();
  }

  /**
   * @return the node's hostname.
   */
  public abstract String name();

  /**
   * @return true iff the node type supports unencrypted connections.
   */
  protected abstract boolean supportsUnencrypted();

  /**
   * @param useSSL
   *          whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return true iff there is an active connection with the node.
   */
  public final boolean isConnected(boolean useSSL) {
    if (useSSL) return sslConn != null && !sslConn.isClosed();
    return unencryptedConn != null && !unencryptedConn.isClosed();
  }

  /**
   * <p>
   * Establishes a connection with a node at the given host. A helper for
   * <code>Message.send(Core)</code>.
   * </p>
   * 
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @param addr
   *          The host to connect to.
   * @param remotePrincipal
   *          The principal associated with the node we're connecting to.
   * @throws NoSuchNodeError
   *           If the node is not hosted at the given address.
   * @throws IOException
   *           If there was an I/O error.
   */
  public final void connect(boolean useSSL, InetSocketAddress addr,
      Principal remotePrincipal) throws NoSuchNodeError, IOException {
    if (!useSSL && !supportsUnencrypted())
      throw new IllegalArgumentException(getClass()
          + " does not support unencrypted connections");
    
    Client client = Client.getClient();
    Socket socket = new Socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Connect to the core node and identify the core we're interested in.
    socket.connect(addr, client.timeout);
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(name);

    // Specify whether we're encrypting.
    dataOut.writeBoolean(useSSL);
    dataOut.flush();

    // Determine whether the core exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    if (useSSL && client.useSSL) {
      // Start encrypting.
      SSLSocket sslSocket;
      synchronized (client.sslSocketFactory) {
        sslSocket =
            (SSLSocket) client.sslSocketFactory.createSocket(socket, name, addr
                .getPort(), true);
      }
      sslSocket.setUseClientMode(true);
      sslSocket.startHandshake();

      // Make sure we're talking to the right node.
      X500Principal peer =
          (X500Principal) sslSocket.getSession().getPeerPrincipal();
      if (!peer.equals(remotePrincipal)) {
        Logger.getLogger(this.getClass().getName()).info(
            "Rejecting connection to " + addr + ": got principal " + peer
                + " when we expected " + remotePrincipal);
        sslSocket.close();
        throw new IOException();
      }

      sslOut =
          new DataOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      sslOut.flush();
      sslIn =
          new DataInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));

      sslConn = sslSocket;
    } else {
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      if (useSSL) out.writeUTF(client.javaPrincipal.getName());
      out.flush();
      DataInputStream in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));

      if (useSSL) {
        sslOut = out;
        sslIn = in;
        sslConn = socket;
      } else {
        unencryptedOut = out;
        unencryptedIn = in;
        unencryptedConn = socket;
      }
    }

    if (useSSL) {
      // Send to the core a pointer to our principal object.
      NodePrincipal principal = client.getPrincipal();
      sslOut.writeBoolean(principal != null);
      if (principal != null) {
        sslOut.writeUTF(principal.$getCore().name());
        sslOut.writeLong(principal.$getOnum());
      }
    }
  }

  /**
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return the data input stream to use for communicating with the node.
   */
  public final DataInputStream dataInputStream(boolean useSSL) {
    return useSSL ? sslIn : unencryptedIn;
  }

  /**
   * @param useSSL
   *          Whether SSL is being used.
   * @return the data output stream to use for communicating with the node.
   */
  public final DataOutputStream dataOutputStream(boolean useSSL) {
    return useSSL ? sslOut : unencryptedOut;
  }

  /**
   * A communication thread for sending commands to the node over the SSL
   * connection.
   */
  private transient final CommThread commThread;

  private class CommThread extends Thread {
    private volatile boolean destroyed;

    CommThread() {
      super("Communication thread for node " + name());
      destroyed = false;
    }

    @Override
    public void run() {
      while (!destroyed) {
        // XXX TODO
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
      }
    }
  }

  public void destroy() {
    commThread.destroyed = true;
    commThread.interrupt();
  }
}
