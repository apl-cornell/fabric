package fabric.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fabric.common.net.Channel.Connection;
import fabric.common.net.handshake.HandshakeProtocol;
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
  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  /** Creates a new SubServerSocketFactory decorating the given
   * ServerSocketFactory.
   * 
   * @param factory the ServerSocketFactory that will be used to create the
   *        ServerSockets used to implement SubServerSockets returned by this
   */
  public SubServerSocketFactory(HandshakeProtocol handshake, NameService nameService) {
    this.handshake   = handshake;
    this.nameService = nameService;
    
    this.acceptors   = new HashMap<SocketAddress, Acceptor> ();
  }

  /** create an unbound server socket. */
  public SubServerSocket createServerSocket() {
    return new SubServerSocket(this);
  }

  /** convenience method */
  public SubServerSocket createServerSocket(String host) throws IOException {
    return createServerSocket(host, 50);
  }

  /** create a server socket to await connections to the given local host name
   * and port number.
   *
   * @param name    the local name
   * @param backlog the number of waiting connections to allow on this socket
   * @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress)
   */
  public SubServerSocket createServerSocket(String name, int backlog) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(name, backlog);
    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // implementation                                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  private final HandshakeProtocol            handshake;
  private final NameService                  nameService;
  private final Map<SocketAddress, Acceptor> acceptors;
  
  Acceptor.ConnectionQueue bind (String name, int backlog) throws IOException {
    throw new NotImplementedException();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Acceptor  (corresponds single to java.net.ServerSocket)                  //
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * An acceptor encapsulates a single java.net.ServerSocket.  It functions as a
   * producer-consumer of SubSockets (via the connected(s) and s accept() methods)
   * and runs a thread in the background which awaits incoming connections and
   * spawns new ServerChannels to handle them.  
   * 
   * @author mdgeorge
   */
  class Acceptor extends Thread {
    //
    // The implementation of Acceptor mirrors that of Channel: while a channel
    // wraps a Socket and dispatches bytes to multiple SubSockets, an Acceptor
    // wraps a ServerSocket and dispatches connections to multiple
    // SubServerSockets.
    //
    // The implementation correspondence is as follows:
    // Acceptor               is similar to      Channel
    // A.ConnectionQueue      is similar to      C.Connection
    //
    // Acceptors are still a bit simpler since Channels need to both send and
    // receive, while Acceptors only receive.

    private final ServerSocket                 socket;
    private final SubServerSocketFactory       factory;
    private final Map<String, ConnectionQueue> queues;

    public Acceptor(SubServerSocketFactory factory, SocketAddress addr) throws IOException {
      super("connection dispatcher for " + addr);
      
      this.socket  = new ServerSocket(addr.getPort(), 0, addr.getAddress());
      this.factory = factory;
      this.queues  = new HashMap<String, ConnectionQueue> ();
      start();
    }
    
    private synchronized ConnectionQueue getReceiver(String name) {
      ConnectionQueue result = queues.get(name);
      if (result == null) {
        result = new ConnectionQueue(name);
        queues.put(name, result);
      }
      return result;
    }

    /**
     * Listens for incoming TCP connections and spawns new ServerChannels to deal
     * with them.
     */
    @Override
    public void run() {
      throw new NotImplementedException();
      /*
        try {
          while (true) { new ServerChannel(factory.receive(socket.accept()), Acceptor.this); }
        } catch (IOException exc) {
          throw new NotImplementedException();
        }
       */
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Connection Queue (corresponds to SubServerSocket)                      //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Contains all of the state for a listening SubSocket.
     */
    class ConnectionQueue {
      private final String                   name;
      private final BlockingQueue<SubSocket> connections;
      private final Set<ServerChannel>       channels;
      
      public ConnectionQueue(String name) {
        this.name = name;
        
        this.channels    = new HashSet<ServerChannel> ();
        this.connections = new ArrayBlockingQueue<SubSocket>(50);
      }
      
      public void close() {
        throw new NotImplementedException();
      }
      
      //////////////////////////////////////////////////////////////////////////
      // ServerChannel (corresponds to java.net.Socket                        //
      //////////////////////////////////////////////////////////////////////////

      /**
       * A server channel is capable of receiving new incoming connections, but not
       * of making new outgoing connections.  It is associated both with a local
       * SocketAddress (IP + port) and a remote IP address.
       *  
       * @author mdgeorge
       */
      public class ServerChannel extends Channel {
        private final Acceptor.ConnectionQueue queue;

        public ServerChannel(ShakenSocket sock, Acceptor.ConnectionQueue queue) throws IOException {
          super(sock);
          this.queue= queue;
        }

        /** create a new subsocket for an incoming connection and notify the acceptor */
        @Override
        public Connection accept(int sequence) throws IOException {
          throw new NotImplementedException();
          /*
          Connection conn = new Connection(sequence);
          queue.connected(new SubSocket(conn));
          return conn;
          */
        }

        @Override
        public String toString() {
          throw new NotImplementedException();
          // return "channel from " + sock.getInetAddress() + " to " + queue.getAddress();
        }
      }
    }
  }
}

