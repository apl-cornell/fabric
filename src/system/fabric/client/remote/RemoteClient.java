package fabric.client.remote;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.client.*;
import fabric.client.remote.messages.*;
import fabric.client.transaction.Log;
import fabric.client.transaction.TransactionManager;
import fabric.client.transaction.TransactionRegistry;
import fabric.common.InternalError;
import fabric.common.NoSuchNodeError;
import fabric.common.TransactionID;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;
import fabric.lang.NodePrincipal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.PrepareTransactionMessage;

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
  private DataInputStream in;
  private DataOutputStream out;

  /**
   * This should only be called by fabric.client.Client. If you want a
   * RemoteClient, use fabric.client.Client.getClient() instead.
   */
  public RemoteClient(String name) {
    this.name = name;
  }

  public DataInputStream dataInputStream() {
    return in;
  }

  public DataInputStream dataInputStream(boolean useSSL) {
    return in;
  }

  public DataOutputStream dataOutputStream() {
    return out;
  }

  public DataOutputStream dataOutputStream(boolean useSSL) {
    return out;
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
      SSLSocket sslSocket;
      synchronized (client.sslSocketFactory) {
        sslSocket =
            (SSLSocket) client.sslSocketFactory.createSocket(socket, name, host
                .getPort(), true);
      }
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
          new DataOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      out.flush();
      in =
          new DataInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));
      conn = sslSocket;
    } else {
      out =
          new DataOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      out.writeUTF(client.javaPrincipal.getName());
      out.flush();

      in =
          new DataInputStream(
              new BufferedInputStream(socket.getInputStream()));

      conn = socket;
    }

    // Send a pointer to our principal object.
    NodePrincipal principal = client.getPrincipal();
    InterClientMessage.writeRef(($Proxy) principal, out);
  }

  public boolean isConnected(boolean useSSL) {
    return isConnected();
  }

  public boolean isConnected() {
    return conn != null && !conn.isClosed();
  }

  public Object issueRemoteCall($Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args)
      throws UnreachableNodeException, RemoteCallException {
    TransactionManager tm = TransactionManager.getInstance();
    tm.registerRemoteCall(this);

    TransactionID tid = tm.getCurrentTid();
    UpdateMap updateMap = tm.getUpdateMap();

    Class<?> receiverClass =
        receiver.fetch().$getProxy().getClass().getEnclosingClass();

    RemoteCallMessage.Response response =
        new RemoteCallMessage(tid, updateMap, receiverClass, receiver,
            methodName, parameterTypes, args).send(this);

    // Commit any outstanding subtransactions that occurred as a result of the
    // remote call.
    Log innermost = TransactionRegistry.getInnermostLog(tid.topTid);
    tm.associateLog(innermost);
    for (int i = innermost.getTid().depth; i > tid.depth; i--)
      tm.commitTransaction();

    // Merge in the update map we got.
    if (response.updateMap != null)
      tm.getUpdateMap().putAll(response.updateMap);

    return response.result;
  }

  public void prepareTransaction(long tid, long commitTime) throws UnreachableNodeException,
      TransactionPrepareFailedException {
    PrepareTransactionMessage.Response response =
        new PrepareTransactionMessage(tid, commitTime).send(this);
    if (!response.success)
      throw new TransactionPrepareFailedException(response.message);
  }

  public void commitTransaction(long tid) throws UnreachableNodeException,
      TransactionCommitFailedException {
    CommitTransactionMessage.Response response =
        new CommitTransactionMessage(tid).send(this);
    if (!response.success)
      throw new TransactionCommitFailedException(response.message);
  }

  /**
   * Informs the remote client that a transaction is aborting.
   * 
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid)
      throws UnreachableNodeException {
    new AbortTransactionMessage(tid).send(this);
  }

  /**
   * Reads the given object from the remote client, updating the object's state.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void readObject(TransactionID tid, $Impl obj) {
    $Impl remoteObj = readObject(tid, obj.$getCore(), obj.$getOnum());

    if (remoteObj == null)
      throw new InternalError("Inter-client object read failed.");
    obj.$copyAppStateFrom(remoteObj);
  }

  public $Impl readObject(TransactionID tid, Core core, long onum) {
    ReadMessage.Response response = new ReadMessage(tid, core, onum).send(this);
    return response.obj;
  }

  /**
   * Unsets the ownership bit for the given object at the remote client.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void takeOwnership(TransactionID tid, Core core, long onum) {
    new TakeOwnershipMessage(tid, core, onum).send(this);
  }

  public String name() {
    return name;
  }

  /**
   * @return the principal associated with the remote client.
   */
  public NodePrincipal getPrincipal() {
    GetPrincipalMessage.Response response =
        new GetPrincipalMessage().send(this);
    final NodePrincipal principal = response.principal;

    boolean authenticated =
        Client.runInTransaction(null, new Client.Code<Boolean>() {
          public Boolean run() {
            return principal.name().equals(name);
          }
        });

    if (authenticated)
      return principal;
    else return null;
  }

  @Override
  public String toString() {
    return name;
  }
}
