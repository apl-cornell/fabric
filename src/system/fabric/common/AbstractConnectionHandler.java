package fabric.common;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

import fabric.client.Client;
import fabric.client.Core;
import fabric.common.AbstractWorkerThread.Pool;
import fabric.common.AbstractWorkerThread.SessionAttributes;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.lang.NodePrincipal;

/**
 * Abstraction for initializing incoming network connections and handing them
 * off to a ChannelMultiplexerThread.
 * 
 * @see fabric.client.CommManager#connect()
 * @param <Node>
 *          a class for representing the nodes that can be connected to.
 * @param <Session>
 *          a class of session objects.
 */
public abstract class AbstractConnectionHandler<Node, Session extends SessionAttributes, Worker extends AbstractWorkerThread<Session, Worker>> {
  private boolean destroyed;
  private final AbstractWorkerThread.Pool<Worker> threadPool;
  private final Set<ChannelMultiplexerThread> activeMuxThreads;

  protected AbstractConnectionHandler(int poolSize,
      AbstractWorkerThread.Factory<Worker> workerFactory) {
    this.threadPool = new Pool<Worker>(poolSize, workerFactory);
    this.activeMuxThreads = new HashSet<ChannelMultiplexerThread>();
    this.destroyed = false;
  }

  /**
   * @return an object representing the node with the given name. Can be null if
   *         the node doesn't exist at this host.
   */
  protected abstract Node getNodeByName(String name);

  /**
   * @return a Session object representing an unauthenticated, unencrypted
   *         session. A null value is returned if this node doesn't support this
   *         type of session.
   * @param node
   *          the local node with which the session was established.
   */
  protected Session newUnauthenticatedSession(Node node) {
    return null;
  }

  /**
   * @return a Session object representing an authenticated, encrypted session.
   * @param node
   *          the local node with which the session was established.
   * @param remoteNodeName
   *          the name of the remote node.
   * @param remoteNodePrincipal
   *          the NodePrincipal corresponding to the remote node.
   */
  protected abstract Session newAuthenticatedSession(Node node,
      String remoteNodeName, NodePrincipal remoteNodePrincipal);

  /**
   * Logs an authentication failure of the remote host.
   */
  protected abstract void logAuthenticationFailure();

  /**
   * Logs a successful connection.
   */
  protected abstract void logSession(SocketAddress remote, Session session);

  /**
   * Returns the name for the worker thread that will be handling the given
   * connection.
   */
  protected abstract String getThreadName(SocketAddress remote, Session session);

  public synchronized final void handle(SocketChannel connection)
      throws IOException {
    if (destroyed) return;

    Session session = initializeConnection(connection);
    if (session == null) {
      // Connection setup failed.
      logAuthenticationFailure();
      connection.close();
      return;
    }

    SocketAddress remote = connection.socket().getRemoteSocketAddress();
    logSession(remote, session);

    ChannelMultiplexerThread mux =
        new ChannelMultiplexerThread(new CallbackHandler(session),
            getThreadName(remote, session), connection);
    activeMuxThreads.add(mux);
    mux.start();
  }

  public synchronized void shutdown() {
    destroyed = true;
    for (ChannelMultiplexerThread mux : activeMuxThreads) {
      mux.shutdown();
    }

    threadPool.shutdown();
  }

  private Session initializeConnection(SocketChannel connection)
      throws IOException {
    // Get the name of the node that the remote host is talking to and obtain a
    // representation of that node.
    DataInput dataIn =
        new DataInputStream(connection.socket().getInputStream());
    OutputStream out = connection.socket().getOutputStream();
    String nodeName = dataIn.readUTF();
    Node node = getNodeByName(nodeName);

    if (node == null) {
      // Indicate that the node doesn't exist here.
      out.write(0);
      out.flush();
      return null;
    }

    // Indicate that the node exists.
    out.write(1);
    out.flush();

    return initializeSession(node, dataIn);
  }

  private Session initializeSession(Node node, DataInput dataIn)
      throws IOException {
    boolean usingSSL = dataIn.readBoolean();
    if (!usingSSL) {
      return newUnauthenticatedSession(node);
    }

    // Encrypted connection.
    String remoteNodeName;
    if (!Options.DEBUG_NO_SSL) {
      // XXX TODO Start encrypting.
      // // Initiate the SSL handshake and initialize the fields.
      // SSLSocketFactory sslSocketFactory = node.getSSLSocketFactory(coreName);
      // synchronized (sslSocketFactory) {
      // sslSocket =
      // (SSLSocket) sslSocketFactory.createSocket(socket, null, 0, true);
      // }
      // sslSocket.setUseClientMode(false);
      // sslSocket.setNeedClientAuth(true);
      // sslSocket.startHandshake();
      // this.out =
      // new DataOutputStream(new BufferedOutputStream(sslSocket
      // .getOutputStream()));
      // this.out.flush();
      // this.in =
      // new DataInputStream(new BufferedInputStream(sslSocket
      // .getInputStream()));
      // this.clientName = sslSocket.getSession().getPeerPrincipal().getName();
      remoteNodeName = dataIn.readUTF();
    } else {
      remoteNodeName = dataIn.readUTF();
    }

    // Read in the pointer to the principal object.
    NodePrincipal remoteNodePrincipal = null;
    if (dataIn.readBoolean()) {
      String principalCoreName = dataIn.readUTF();
      Core principalCore = Client.getClient().getCore(principalCoreName);
      long principalOnum = dataIn.readLong();
      remoteNodePrincipal =
          new NodePrincipal._Proxy(principalCore, principalOnum);
    }

    Pair<Boolean, NodePrincipal> authResult =
        authenticateRemote(remoteNodePrincipal, remoteNodeName);
    if (authResult.first)
      return newAuthenticatedSession(node, remoteNodeName, authResult.second);

    return null;
  }

  /**
   * Determines whether the given node principal matches the given name.
   * 
   * @return a pair indicating whether the authentication succeeded, and the
   *         principal for which the node was successfully authenticated.
   */
  private Pair<Boolean, NodePrincipal> authenticateRemote(
      final NodePrincipal principal, final String name) {
    // Bypass authentication if we have a null client.
    // This is to allow bootstrapping the client principal.
    // This is safe because everyone acts for null anyway.
    if (principal == null) return new Pair<Boolean, NodePrincipal>(true, null);

    return Client.runInTransaction(null,
        new Client.Code<Pair<Boolean, NodePrincipal>>() {
          public Pair<Boolean, NodePrincipal> run() {
            boolean success = false;
            NodePrincipal authenticatedPrincipal = null;
            try {
              if (principal.name().equals(name)) {
                success = true;
                authenticatedPrincipal = principal;
              }
            } catch (ClassCastException e) {
            } catch (InternalError e) {
              // XXX If the client principal doesn't exist, authenticate as the
              // XXX bottom principal. This is for ease of debugging so we don't
              // XXX need to keep editing client property files every time we
              // XXX re-create the client principal on the core.
              success = true;
            }

            return new Pair<Boolean, NodePrincipal>(success,
                authenticatedPrincipal);
          }
        });
  }

  private final class CallbackHandler implements
      ChannelMultiplexerThread.CallbackHandler {

    private final Session session;
    private final List<Worker> workers;

    public CallbackHandler(Session session) {
      this.session = session;
      this.workers = new ArrayList<Worker>();
    }

    public void connectionClosed() {
      for (Worker worker : workers)
        worker.recycle();
      workers.clear();
    }

    public void newStream(ChannelMultiplexerThread muxer, int streamID) {
      // Get a new worker thread and assign it to the new sub-stream.
      Worker worker = threadPool.get();
      worker.associateSession(session);
      workers.add(worker);
      try {
        muxer.registerChannels(streamID, worker.source(), worker.sink());
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }
  }
}
