package fabric.common.net;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;

/**
 * A factory for creating SubSockets. The factory decorates a
 * javax.net.SocketFactory, which is used for creating the underlying channels.
 * 
 * @author mdgeorge
 */
public final class SubSocketFactory {
  private final Protocol protocol;
  private final NameService nameService;
  private final Map<String, ClientChannel> channels;
  private final int maxOpenConnectionsPerChannel;

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory. Note
   * that SubSockets created from different SubSocketFactories will not attempt
   * to share channels (as these channels may have different underlying socket
   * implementations).
   */
  public SubSocketFactory(Protocol protocol, NameService nameService) {
    this(protocol, nameService, Channel.DEFAULT_MAX_OPEN_CONNECTIONS);
  }

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory. Note
   * that SubSockets created from different SubSocketFactories will not attempt
   * to share channels (as these channels may have different underlying socket
   * implementations).
   */
  public SubSocketFactory(Protocol protocol, NameService nameService,
      int maxOpenConnectionsPerChannel) {
    this.protocol = protocol;
    this.nameService = nameService;
    this.channels = new HashMap<String, ClientChannel>();
    this.maxOpenConnectionsPerChannel = maxOpenConnectionsPerChannel;
  }

  /**
   * Create an unconnected socket.
   */
  public SubSocket createSocket() {
    return new SubSocket(this);
  }

  /**
   * Convenience method. Resolves the name using the NameService and calls
   * createSocket.
   */
  public SubSocket createSocket(String name) throws IOException {
    SubSocket result = createSocket();
    result.connect(name);
    return result;
  }

  /**
   * return a channel associated with the given address, creating it if
   * necessary.
   */
  ClientChannel getChannel(String name) throws IOException {
    synchronized (channels) {
      ClientChannel result = channels.get(name);
      if (null == result) {
        NETWORK_CONNECTION_LOGGER.log(Level.INFO,
            "establishing new connection to \"{0}\"", name);
        SocketAddress addr = nameService.resolve(name);

        Socket s = new Socket(addr.getAddress(), addr.getPort());
        s.setSoLinger(false, 0);
        s.setTcpNoDelay(true);

        result = new ClientChannel(name, s, maxOpenConnectionsPerChannel);
        channels.put(name, result);
        NETWORK_CONNECTION_LOGGER.log(Level.INFO,
            "connection to {0} established.", name);
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
  class ClientChannel extends Channel {
    /* key for SubSocketFactory.this.channels */
    private final String name;

    /* the next sequence number to be created */
    private int nextSequenceNumber;

    public ClientChannel(String name, Socket s, int maxOpenConnections)
        throws IOException {
      super(protocol.initiate(name, s), maxOpenConnections);

      this.name = name;
      nextSequenceNumber = 1;

      setName("demultiplexer for " + toString());
    }

    /** initiate a new substream */
    public Connection connect() throws IOException {
      return new Connection(nextSequenceNumber++);
    }

    @Override
    protected Connection accept(int sequence) throws IOException {
//      throw new IOException("unexpected accept request on client channel");
      
      // Temporary gunk until we're done tuning performance.
      int x;
      synchronized (connections) {
        while (!connections.containsKey(sequence)) {
          try {
            connections.wait();
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        return connections.get(sequence);
      }
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
