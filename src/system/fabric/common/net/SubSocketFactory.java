package fabric.common.net;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.Logging;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.NameService.PortType;
import fabric.common.net.naming.SocketAddress;
import fabric.net.RemoteNode;
import fabric.worker.Worker;

/**
 * A factory for creating SubSockets. The factory decorates a
 * javax.net.SocketFactory, which is used for creating the underlying channels.
 *
 * @param <Node> the type of node at the remote endpoint.
 */
public final class SubSocketFactory<Node extends RemoteNode<Node>> {
  private final ConfigProperties config;
  private final Protocol<Node> protocol;
  private final NameService nameService;
  private final PortType portType;
  private final Map<String, ClientChannel> channels;
  private final int maxOpenConnectionsPerChannel;

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory. Note
   * that SubSockets created from different SubSocketFactories will not attempt
   * to share channels (as these channels may have different underlying socket
   * implementations).
   */
  public SubSocketFactory(ConfigProperties config, Protocol<Node> protocol,
      NameService nameService, PortType portType) {
    this(config, protocol, nameService, portType,
        Channel.DEFAULT_MAX_OPEN_CONNECTIONS);
  }

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory. Note
   * that SubSockets created from different SubSocketFactories will not attempt
   * to share channels (as these channels may have different underlying socket
   * implementations).
   */
  public SubSocketFactory(ConfigProperties config, Protocol<Node> protocol,
      NameService nameService, PortType portType,
      int maxOpenConnectionsPerChannel) {
    this.config = config;
    this.protocol = protocol;
    this.nameService = nameService;
    this.portType = portType;
    this.channels = new HashMap<>();
    this.maxOpenConnectionsPerChannel = maxOpenConnectionsPerChannel;
  }

  /**
   * Create an unconnected socket.
   */
  public SubSocket<Node> createSocket() {
    return new SubSocket<>(this);
  }

  /**
   * Convenience method. Resolves the name using the NameService and calls
   * createSocket.
   */
  public SubSocket<Node> createSocket(Node node) throws IOException {
    SubSocket<Node> result = createSocket();
    result.connect(node);
    return result;
  }

  /**
   * return a channel associated with the given node, creating it if
   * necessary.
   */
  ClientChannel getChannel(Node node) throws IOException {
    synchronized (channels) {
      ClientChannel result = channels.get(node.name);
      if (null == result) {
        Logging.log(NETWORK_CONNECTION_LOGGER, Level.INFO,
            "establishing new connection from \"{0}\" to \"{1}\"",
            Worker.getWorkerName(), node.name);
        SocketAddress addr = nameService.resolve(node.name, portType);
        SocketAddress local =
            nameService.resolve(Worker.getWorkerName(), portType);

        Socket s = new Socket(addr.getAddress(), addr.getPort(),
            local.getAddress(), 0);
        s.setSoLinger(false, 0);
        s.setTcpNoDelay(true);

        result = new ClientChannel(node, s, maxOpenConnectionsPerChannel);
        channels.put(node.name, result);
        NETWORK_CONNECTION_LOGGER.log(Level.INFO,
            "connection to {0} established.", node.name);
      }

      return result;
    }
  }

  /**
   * Client channels are capable of making outgoing requests, but not of
   * receiving new incoming requests. They are only associated with a remote
   * address, and have no distinguished local address.
   *
   * @author mdgeorge
   */
  class ClientChannel extends Channel<Node> {
    /**
     * The name of the remote endpoint. This is a key for
     * SubSocketFactory.this.channels.
     */
    private final String name;

    /* the next sequence number to be created */
    private AtomicInteger nextSequenceNumber;

    /**
     * @param host the host at the remote endpoint.
     */
    public ClientChannel(Node host, Socket s, int maxOpenConnections)
        throws IOException {
      super(config, protocol.initiate(host, s), maxOpenConnections);

      this.name = host.name;
      nextSequenceNumber = new AtomicInteger(1);

      setName("demultiplexer for " + toString());
    }

    /** initiate a new substream */
    public Connection connect() throws IOException {
      return new Connection(nextSequenceNumber.getAndIncrement());
    }

    @Override
    protected Connection accept(int sequence) throws IOException {
      throw new IOException("unexpected accept request on client channel");
    }

    @Override
    protected void cleanup() {
      synchronized (SubSocketFactory.this.channels) {
        SubSocketFactory.this.channels.remove(this);
      }
    }

    @Override
    public String toString() {
      return "channel to \"" + name + "\"";
    }
  }
}
