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
    /* key for SubServerSocketFactory.this.acceptors */
    public final SocketAddress address;

    /* children keyed by name */
    private final Map<String, ConnectionQueue> queues;
    
    /* the exception that caused this Acceptor to fail, or null if this is running. */
    private IOException error;
    
    Acceptor(SocketAddress addr) {
      super("connection dispatcher for " + addr);

      this.address = addr;
      this.error   = null;
      this.queues  = new HashMap<String, ConnectionQueue> ();
      
      start();
    }

    ConnectionQueue makeQueue(String name) throws IOException {
      throw new NotImplementedException();
    }
    
    /**
     * Listens for incoming TCP connections and spawns new ServerChannels to deal
     * with them.
     */
    @Override
    public void run() {
      throw new NotImplementedException();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Connection Queue (corresponds to SubServerSocket)                      //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Contains all of the state for a listening SubSocket.
     */
    class ConnectionQueue {
      /* key for Acceptor.this.queues */
      public final String                   name;
      
      /* children */
      private final Set<ServerChannel>       channels;
      
      /* queue of connections that are ready to be accepted by a SubServerSocket */
      private final BlockingQueue<SubSocket> connections;
      
      public ConnectionQueue(String name) {
        this.name = name;
        
        this.channels    = new HashSet<ServerChannel> ();
        this.connections = new ArrayBlockingQueue<SubSocket>(50);
      }
      
      public void close() {
        throw new NotImplementedException();
      }
      
      public SubSocket accept() throws IOException {
        throw new NotImplementedException();
      }
      
      public void receive(SubSocket s) throws IOException {
        throw new NotImplementedException();
      }
      
      @Override
      public String toString() {
        return name + " [" + address + "]";
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
        public ServerChannel(ShakenSocket sock) throws IOException {
          super(sock);
        }

        /** create a new subsocket for an incoming connection and notify the acceptor */
        @Override
        public Connection accept(int sequence) throws IOException {
          throw new NotImplementedException();
        }
        
        /** remove self from the connectionqueue */
        @Override
        public void cleanup() {
          throw new NotImplementedException();
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

