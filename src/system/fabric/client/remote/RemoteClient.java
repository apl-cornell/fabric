package fabric.client.remote;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.client.Client;
import fabric.client.RemoteNode;
import fabric.common.NoSuchNodeError;
import fabric.lang.Object.$Proxy;

/**
 * Encapsulates a remote client. This class maintains the connection to the
 * remote client and manages all communication. RemoteClients can only be
 * obtained through the <code>RemoteCallManager.getClient()</code> interface.
 * For each remote client, there should be at most one <code>RemoteClient</code>
 * object representing that client.
 */
public final class RemoteClient implements RemoteNode {
  private Socket conn;

  /**
   * The DNS host name of the client.
   */
  public final String name;

  /**
   * The connection to the actual client.
   */
  private ObjectInputStream in;
  private ObjectOutputStream out;

  protected RemoteClient(String name) {
    this.name = name;
  }

  public ObjectInputStream objectInputStream() {
    return in;
  }

  public ObjectInputStream objectInputStream(boolean useSSL) {
    return objectInputStream();
  }

  public ObjectOutputStream objectOutputStream() {
    return out;
  }

  public ObjectOutputStream objectOutputStream(boolean useSSL) {
    return objectOutputStream();
  }

  public void connect(boolean useSSL, InetSocketAddress host,
      Principal remotePrincipal) throws NoSuchNodeError, IOException {
    connect(host, remotePrincipal);
  }

  /**
   * <p>
   * Establishes a connection with a client node at the given host. A helper for
   * <code>client.remote.messages.Message.send(RemoteClient)</code>.
   * </p>
   * <p>
   * NOTE: If you fix a bug in this method, then you'll probably want to fix a
   * bug in RemoteCore.connect() as well.
   * </p>
   */
  public void connect(InetSocketAddress host, Principal remotePrincipal)
      throws NoSuchNodeError, IOException {
    Client client = Client.getClient();

    Socket socket = new Socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Connect to the client node and identify the client we're interested in.
    socket.connect(host, client.timeout);
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(name);
    dataOut.flush();

    // Determine whether the client exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    if (client.useSSL) {
      // Start encrypting.
      SSLSocket sslSocket =
          (SSLSocket) client.sslSocketFactory.createSocket(socket, name, host
              .getPort(), true);
      sslSocket.setUseClientMode(true);
      sslSocket.startHandshake();

      // Make sure we're talking to the right node.
      X500Principal peer =
          (X500Principal) sslSocket.getSession().getPeerPrincipal();
      if (!peer.equals(remotePrincipal)) {
        Logger.getLogger(this.getClass().getName()).info(
            "Rejecting connection to " + host + ": got principal " + peer
                + " when we expected " + remotePrincipal);
        socket.close();
        throw new IOException();
      }

      out =
          new ObjectOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      out.flush();
      in =
          new ObjectInputStream(
              new BufferedInputStream(socket.getInputStream()));
      conn = socket;
    } else {
      out =
          new ObjectOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      out.writeUTF(client.javaPrincipal.getName());
      out.flush();

      in =
          new ObjectInputStream(
              new BufferedInputStream(socket.getInputStream()));

      conn = socket;
    }
  }
  
  public boolean isConnected(boolean useSSL) {
    return isConnected();
  }
  
  public boolean isConnected() {
    return conn != null && !conn.isClosed();
  }

  protected $Proxy issueRemoteCall($Proxy receiver, String methodName,
      $Proxy[] args) {
    // RemoteCallMessage.Response response =
    // new RemoteCallMessage(receiver, methodName, args);
    return null;
  }

  public String name() {
    return name;
  }
}
