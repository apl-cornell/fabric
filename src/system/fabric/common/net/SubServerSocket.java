package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ServerSocketFactory;


/**
 * Server-side multiplexed socket implementation. The API mirrors that of
 * java.net.ServerSocket.
 * 
 * @see java.net.ServerSocket
 * @author mdgeorge
 */
public class SubServerSocket {
  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  /** @see SubServerSocketFactory */
  protected SubServerSocket(SubServerSocketFactory factory) {
    this.state   = new Unbound(factory);
  }
  
  /** @see java.net.ServerSocket#accept() */
  public SubSocket accept() throws IOException {
    return state.accept();
  }
  
  /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
  public void bind(int port) throws IOException {
    bind(port, 50);
  }
  
  /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
  public void bind(int port, int backlog) throws IOException {
    bind(new InetSocketAddress(port), backlog);
  }
  
  /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
  public void bind(InetSocketAddress addr) throws IOException {
    bind(addr, 50);
  }
  
  /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
  public void bind(InetSocketAddress addr, int backlog) throws IOException {
    state.bind(addr, backlog);
  }
  
  /** @see java.net.ServerSocket#close() */
  public void close() throws IOException {
    state.close();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation                                      //
  //                                                                          //
  //                 bind                  close                              //
  //      unbound  --------->    bound    ------->  closed                    //
  //         |                     |                  |                       //
  //         +---------------------+------------------+---------------> error //
  //                                                       exception          //
  //////////////////////////////////////////////////////////////////////////////
  
  private State state;
  
  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    protected Exception cause = null;
    
    /** @see SubServerSocket#accept() */
    public SubSocket accept() throws IOException {
      throw new IOException("Cannot accept a connection because server socket " + this, cause);
    }
    
    /** @see SubServerSocket#bind(InetSocketAddress, int) */
    public void bind(InetSocketAddress address, int backlog) throws IOException {
      throw new IOException("Cannot bind to local address " + address + " because server socket " + this, cause);
    }
    
    /** @see SubServerSocket#close() */
    public void close() throws IOException {
      throw new IOException("Cannot close server socket because it " + this, cause);
    }
  }
  
  /**
   * implementation of state methods in the unbound state.
   */
  private final class Unbound extends State {
    @Override public String toString() { return "is unbound"; }

    private final SubServerSocketFactory factory;
    
    @Override
    public void bind(InetSocketAddress address, int backlog) throws IOException {
      try {
        Acceptor acceptor = new Acceptor(factory, address, backlog);
        state = new Bound(acceptor);
      } catch (final Exception exc) {
        IOException wrapped = new IOException("failed to bind to local address " + address, exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }
    
    public Unbound(SubServerSocketFactory factory) {
      this.factory = factory;
    }
  }

  /**
   * implementation of state methods in the bound(channel) state.
   */
  private final class Bound extends State {
    final Acceptor acceptor;
    
    @Override public String toString() {
      return "is bound to " + acceptor.getAddress();
    }

    @Override
    public SubSocket accept() throws IOException {
      return acceptor.accept();
    }

    @Override
    public void close() throws IOException {
      try {
        acceptor.close();
        state = new Closed();
      } catch(final Exception exc) {
        IOException wrapped = new IOException("failed to close server socket", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }
    
    public Bound(Acceptor acceptor) {
      this.acceptor = acceptor;
    }
  }
  
  /**
   * implementation of state methods in the closed state.
   */
  private final class Closed extends State {
    @Override public String toString() { return "is closed"; }
  }
  
  /**
   * implementation of state methods in an error state
   */
  private final class ErrorState extends State {
    @Override public String toString() { return "has recieved an exception"; }
    
    public ErrorState(Exception exc) {
      super();
      this.cause = exc;
    }
  }
}

