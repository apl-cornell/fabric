package fabric.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.common.ChannelMultiplexerThread;
import fabric.common.Options;
import fabric.common.ChannelMultiplexerThread.CallbackHandler;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NoSuchNodeError;
import fabric.common.util.Pair;
import fabric.lang.NodePrincipal;

/**
 * <p>
 * Manages a connection with a remote node. The connection is used to send
 * requests to and receive responses from the node.
 * </p>
 * <p>
 * XXX Assumes connections never get dropped.
 * </p>
 */
class CommManager {
  private final static Logger LOGGER = Logger.getLogger("fabric.messages");
  private final RemoteNode node;
  private final ChannelMultiplexerThread muxer;
  private final boolean useSSL;

  /**
   * A thread-local pair of streams for the app to communicate with the remote
   * node. XXX TODO Ought to be able to maintain this information by attaching
   * the appropriate objects to the appropriate selection keys.
   */
  private final ThreadLocal<Pair<DataInputStream, DataOutputStream>> streams;

  /**
   * @param useSSL
   *          whether the data should be sent on the network with SSL
   *          encryption.
   */
  CommManager(RemoteNode remoteNode, boolean useSSL) {
    this.node = remoteNode;
    this.useSSL = useSSL;

    // Set up the connection.
    SocketChannel socketChannel = connect();

    CallbackHandler handler = new CallbackHandler() {
      public void newStream(ChannelMultiplexerThread muxer, int streamID) {
        // We are creating all the streams here.
        throw new InternalError("Unexpected stream ID: " + streamID);
      }

      public void connectionClosed() {
        throw new InternalError("Connection to " + node
            + "unexpectedly closed.");
      }
    };
    try {
      this.muxer =
          new ChannelMultiplexerThread(handler,
              "Thread for communicating with node " + remoteNode.name,
              socketChannel);
    } catch (IOException e) {
      throw new InternalError(e);
    }
    this.streams = new ThreadLocal<Pair<DataInputStream, DataOutputStream>>() {
      @Override
      public Pair<DataInputStream, DataOutputStream> initialValue() {
        try {
          // APP --[outbound]--> CommManager
          // APP <--[inbound ]-- CommManager
          Pipe inbound = Pipe.open();
          Pipe outbound = Pipe.open();
          muxer.registerChannels(outbound.source(), inbound.sink());

          inbound.source().configureBlocking(true);
          outbound.sink().configureBlocking(true);

          DataInputStream in =
              new DataInputStream(new BufferedInputStream(Channels
                  .newInputStream(inbound.source())));
          DataOutputStream out =
              new DataOutputStream(new BufferedOutputStream(Channels
                  .newOutputStream(outbound.sink())));

          return new Pair<DataInputStream, DataOutputStream>(in, out);
        } catch (IOException e) {
          throw new InternalError(e);
        }
      }
    };

    muxer.start();
  }

  /**
   * Connects to the remote node and initializes the session.
   * 
   * @see fabric.common.AbstractConnectionHandler
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect() {
    Client client = Client.getClient();
    int hostIdx = 0;

    // These will be filled in with real values if needed.
    List<InetSocketAddress> hosts = null;
    Principal nodePrincipal = null;
    int numHosts = 0;
    int startHostIdx = 0;

    for (int retry = 0; client.retries < 0 || retry < client.retries;) {
      try {
        if (hosts == null) {
          Pair<List<InetSocketAddress>, Principal> entry =
              client.nameService.lookup(node);
          hosts = entry.first;
          nodePrincipal = entry.second;

          numHosts = hosts.size();
          startHostIdx = Client.RAND.nextInt(numHosts);
        }

        // Attempt to establish a connection.
        int hostNum = (startHostIdx + hostIdx) % numHosts;
        return connect(hosts.get(hostNum), nodePrincipal);
      } catch (NoSuchNodeError e) {
        // Connected to a system that doesn't host the node we're interested
        // in.
        // Increment loop counter variables.

        LOGGER.log(Level.WARNING, "Failed to connect", e);

        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (client.retries >= 0) retry++;
        }
        continue;
      } catch (IOException e) {
        // Retry.
        LOGGER.log(Level.WARNING, "Failed to connect", e);

        if (hosts == null) {
          // Attempt to reuse an existing connection failed. Just restart the
          // loop.
          continue;
        }

        // Increment loop counter variables.
        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (client.retries >= 0) retry++;
        }
        continue;
      }
    }

    throw new UnreachableNodeException(node);
  }

  /**
   * <p>
   * Establishes a connection with a node at the given host. A helper for
   * <code>send(ByteBuffer)</code>.
   * </p>
   * 
   * @param addr
   *          The host to connect to.
   * @param remotePrincipal
   *          The principal associated with the node we're connecting to.
   * @throws NoSuchNodeError
   *           If the node is not hosted at the given address.
   * @throws IOException
   *           If there was an I/O error.
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect(InetSocketAddress addr,
      Principal remotePrincipal) throws NoSuchNodeError, IOException {
    Client client = Client.getClient();
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(true);

    Socket socket = socketChannel.socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Attempt to connect to the remote host.
    socketChannel.socket().connect(addr, client.timeout);

    // Give the name of the node we're interested in.
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(node.name);

    // Specify whether we're encrypting.
    dataOut.writeBoolean(useSSL);
    dataOut.flush();

    // Determine whether the core exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    return initializeSession(socketChannel, dataOut);
  }

  private SocketChannel initializeSession(SocketChannel connection,
      DataOutputStream out) throws IOException {
    Client client = Client.getClient();

    // Nothing to do if we're connecting as a dissemination node.
    if (!useSSL) return connection;

    if (!Options.DEBUG_NO_SSL) {
      // XXX TODO Start encrypting.
      // SSLSocket sslSocket;
      // synchronized (client.sslSocketFactory) {
      // sslSocket =
      // (SSLSocket) client.sslSocketFactory.createSocket(socket, node.name,
      // addr.getPort(), true);
      // }
      // sslSocket.setUseClientMode(true);
      // sslSocket.startHandshake();
      //
      // // Make sure we're talking to the right node.
      // X500Principal peer =
      // (X500Principal) sslSocket.getSession().getPeerPrincipal();
      // if (!peer.equals(remotePrincipal)) {
      // Logger.getLogger(this.getClass().getName()).info(
      // "Rejecting connection to " + addr + ": got principal " + peer
      // + " when we expected " + remotePrincipal);
      // sslSocket.close();
      // throw new IOException();
      // }
      out.writeUTF(client.javaPrincipal.getName());
      out.flush();
    } else {
      out.writeUTF(client.javaPrincipal.getName());
      out.flush();
    }

    // Send to the core a pointer to our principal object.
    NodePrincipal principal = client.getPrincipal();
    out.write(principal != null ? 1 : 0);
    if (principal != null) {
      out.writeUTF(principal.$getCore().name());
      out.writeLong(principal.$getOnum());
    }
    out.flush();

    return connection;
  }

  /**
   * @return the DataInputStream/DataOutputStream pair for the substream that is
   *         associated with the currently running thread.
   */
  public Pair<DataInputStream, DataOutputStream> getStreams() {
    return streams.get();
  }

  public void shutdown() {
    muxer.shutdown();
  }
}
