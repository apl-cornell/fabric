package fabric.common.net;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubServerSocketFactory.Acceptor.ConnectionQueue;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.handshake.ShakenSocket;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;

/**
 * factory for creating SubServerSockets. This class decorates a
 * javax.net.ServerSocketFactory, which is used for instantiating the underlying
 * channels.
 * 
 * @author mdgeorge
 */
public class SubServerSocketFactory {
  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * Creates a new SubServerSocketFactory decorating the given
   * ServerSocketFactory.
   * 
   * @param factory
   *          the ServerSocketFactory that will be used to create the
   *          ServerSockets used to implement SubServerSockets returned by this
   */
  public SubServerSocketFactory(Protocol handshake, NameService nameService) {
    this(handshake, nameService, Channel.DEFAULT_MAX_OPEN_CONNECTIONS);
  }

  /**
   * Creates a new SubServerSocketFactory decorating the given
   * ServerSocketFactory.
   * 
   * @param factory
   *          the ServerSocketFactory that will be used to create the
   *          ServerSockets used to implement SubServerSockets returned by this
   */
  public SubServerSocketFactory(Protocol handshake, NameService nameService,
      int maxOpenConnectionsPerChannel) {
    this.handshake = handshake;
    this.nameService = nameService;
    this.maxOpenConnectionsPerChannel = maxOpenConnectionsPerChannel;

    this.acceptors = new HashMap<SocketAddress, Acceptor>();
  }

  /** create an unbound server socket. */
  public SubServerSocket createServerSocket() {
    return new SubServerSocket(this);
  }

  /** convenience method */
  public SubServerSocket createServerSocket(String host) throws IOException {
    return createServerSocket(host, 50);
  }

  /**
   * create a server socket to await connections to the given local host name
   * and port number.
   * 
   * @param name
   *          the local name
   * @param backlog
   *          the number of waiting connections to allow on this socket
   * @see javax.net.ServerSocketFactory#createServerSocket(int, int,
   *      InetAddress)
   */
  public SubServerSocket createServerSocket(String name, int backlog)
      throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(name, backlog);
    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // implementation //
  // ////////////////////////////////////////////////////////////////////////////

  private final Protocol handshake;
  private final NameService nameService;
  private final Map<SocketAddress, Acceptor> acceptors;
  private final int maxOpenConnectionsPerChannel;

  /**
   * creates a new ConnectionQueue for the local name. Uses the name service to
   * resolve the name to an address.
   * 
   * @param backlog
   *          the size of the queue
   * @throws IOException
   *           if a queue with the given name already exists
   */
  synchronized Acceptor.ConnectionQueue bind(String name, int backlog)
      throws IOException {
    SocketAddress addr = nameService.localResolve(name);

    Acceptor a = acceptors.get(addr);
    if (a != null) {
      // note that an exception is only thrown if the acceptor previously
      // existed, so no cleanup is necessary
      return a.makeQueue(name, backlog);
    }

    a = new Acceptor(addr);
    ConnectionQueue result = a.makeQueue(name, backlog);
    a.start();
    acceptors.put(addr, a);

    return result;
  }

  synchronized void closeAcceptor(Acceptor a) {
    // TODO
    throw new NotImplementedException();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // Acceptor (corresponds single to java.net.ServerSocket) //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * An acceptor encapsulates a single java.net.ServerSocket. It functions as a
   * producer-consumer of SubSockets (via the connected(s) and s accept()
   * methods) and runs a thread in the background which awaits incoming
   * connections and spawns new ServerChannels to handle them.
   * 
   * @author mdgeorge
   */
  class Acceptor extends Thread {
    /* key for SubServerSocketFactory.this.acceptors */
    private final SocketAddress address;

    /* children keyed by name */
    private final Map<String, ConnectionQueue> queues;

    Acceptor(SocketAddress addr) {
      super("connection dispatcher for " + addr);

      this.address = addr;
      this.queues = new HashMap<String, ConnectionQueue>();
    }

    /**
     * Creates a ConnectionQueue for the given name on this acceptor.
     * 
     * @param size
     *          the size of the queue
     * @throws IOException
     *           if the queue already exists
     */
    ConnectionQueue makeQueue(String name, int size) throws IOException {
      synchronized (queues) {
        if (queues.containsKey(name))
          throw new IOException("attempted to bind multiple SubServerSockets to "
              + name + " @ " + address);

        ConnectionQueue queue = new ConnectionQueue(name, size);
        queues.put(name, queue);
        return queue;
      }
    }

    /** handle an incoming connection */
    private void recvConnection(Socket s) {
      try {
        NETWORK_CONNECTION_LOGGER.log(Level.INFO,
            "receiving new connection from \"{0}\"", s.getInetAddress());

        s.setTcpNoDelay(true);

        ShakenSocket conn = handshake.receive(s);

        ConnectionQueue queue;
        synchronized (queues) {
          queue = queues.get(conn.name);
        }

        if (null == queue) {
          // TODO: close the connection.
          throw new NotImplementedException();
        }

        queue.open(conn);
      } catch (IOException e) {
        // TODO: failed to initiate, close s.
        throw new NotImplementedException(e);
      }
    }

    /** handle a failure of the underlying socket */
    private void recvException(IOException e) {
      // TODO
      throw new NotImplementedException(e);
    }

    /**
     * Listens for incoming TCP connections and spawns new ServerChannels to
     * deal with them.
     */
    @Override
    public void run() {
      ServerSocket sock = null;
      try {
        sock = new ServerSocket(address.getPort(), 0, address.getAddress());
        while (true) {
          try {
            recvConnection(sock.accept());
          } catch (IOException e) {
            recvException(e);
          }
        }
      } catch (IOException exc) {
        // TODO
        throw new NotImplementedException(exc);
      } finally {
        if (sock != null) {
          try {
            sock.close();
          } catch (IOException e) {
          }
        }
      }
    }

    // //////////////////////////////////////////////////////////////////////////
    // Connection Queue (corresponds to SubServerSocket) //
    // //////////////////////////////////////////////////////////////////////////

    /**
     * Contains all of the state for a listening SubSocket.
     */
    class ConnectionQueue {
      /* key for Acceptor.this.queues */
      private final String name;

      /* children */
      private final Set<ServerChannel> channels;

      /* queue of connections that are ready to be accepted by a SubServerSocket */
      private final BlockingQueue<SubSocket> connections;

      ConnectionQueue(String name, int size) {
        this.name = name;

        this.channels = new HashSet<ServerChannel>();
        this.connections = new ArrayBlockingQueue<SubSocket>(size);
      }

      /** cleanup when associated SubServerSocket is closed. */
      void close() {
        throw new NotImplementedException();
      }

      /** wait for an incoming SubSocket connection */
      SubSocket accept() {
        try {
          return connections.take();
        } catch (InterruptedException e) {
          throw new NotImplementedException(e);
        }
      }

      /**
       * create a new ServerChannel (in response to a new incoming socket
       * connection)
       */
      void open(ShakenSocket s) throws IOException {
        synchronized (channels) {
          channels.add(new ServerChannel(s));
        }
      }

      /** receive an incoming subsocket connection */
      private void receive(SubSocket s) {
        try {
          connections.put(s);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      @Override
      public String toString() {
        return name + " [" + address + "]";
      }

      // ////////////////////////////////////////////////////////////////////////
      // ServerChannel (corresponds to java.net.Socket) //
      // ////////////////////////////////////////////////////////////////////////

      /**
       * A server channel is capable of receiving new incoming connections, but
       * not of making new outgoing connections. It is associated both with a
       * local SocketAddress (IP + port) and a remote IP address.
       * 
       * @author mdgeorge
       */
      class ServerChannel extends Channel {
        ServerChannel(ShakenSocket sock)
            throws IOException {
          super(sock, maxOpenConnectionsPerChannel);

          setName("demultiplexer for " + toString());
        }

        /**
         * create a new subsocket for an incoming connection and notify the
         * acceptor
         */
        @Override
        protected Connection accept(int sequence) throws IOException {
          Connection result = new Connection(sequence);
          SubSocket socket = new SubSocket(result);
          receive(socket);
          return result;
        }

        @Override
        protected void cleanup() {
          synchronized (ConnectionQueue.this.channels) {
            ConnectionQueue.this.channels.remove(this);
          }
        }

        @Override
        public String toString() {
          return "channel from " + sock.getInetAddress() + " to \""
              + ConnectionQueue.this.toString() + "\"";
        }
      }
    }
  }
}
