package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ServerSocketFactory;


/**
 * Server-side multiplexed socket implementation. The API mirrors that of
 * java.net.ServerSocket.
 * 
 * @author mdgeorge
 */
public final class SubServerSocket {
  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  SubServerSocket(ServerSocketFactory factory) {
    this.state   = new Unbound(factory);
  }
  
  public SubSocket accept() throws IOException {
    return state.accept();
  }
  
  public void bind(int port) throws IOException {
    bind(port, 50);
  }
  
  public void bind(int port, int backlog) throws IOException {
    bind(new InetSocketAddress(port), backlog);
  }
  
  public void bind(InetSocketAddress addr) throws IOException {
    bind(addr, 50);
  }
  
  public void bind(InetSocketAddress addr, int backlog) throws IOException {
    state.bind(addr, backlog);
  }
  
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
    
    public SubSocket accept() throws IOException {
      throw new IOException("Cannot accept a connection because server socket " + this, cause);
    }
    
    public void bind(InetSocketAddress address, int backlog) throws IOException {
      throw new IOException("Cannot bind to local address " + address + " because server socket " + this, cause);
    }
    
    public void close() throws IOException {
      throw new IOException("Cannot close server socket because it " + this, cause);
    }
  }
  
  /**
   * implementation of methods in the unbound state.
   */
  private final class Unbound extends State {
    @Override public String toString() { return "is unbound"; }

    private final ServerSocketFactory factory;
    
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
    
    public Unbound(ServerSocketFactory factory) {
      this.factory = factory;
    }
  }

  /**
   * implementation of methods in the bound(channel) state.
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
   * implementation of methods in the closed state.
   */
  private final class Closed extends State {
    @Override public String toString() { return "is closed"; }
  }
  
  /**
   * implementation of methods in an error state
   */
  private final class ErrorState extends State {
    @Override public String toString() { return "has recieved an exception"; }
    
    public ErrorState(Exception exc) {
      super();
      this.cause = exc;
    }
  }
}

