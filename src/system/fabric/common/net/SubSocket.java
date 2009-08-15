package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Client-side multiplexed socket implementation. The API mirrors that of
 * java.net.Socket. This class manages connection state, and provides a
 * front-end API.
 * 
 * @author mdgeorge
 */
public final class SubSocket {
  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  SubSocket(SubSocketFactory factory) {
    this.state = new Unconnected(factory); 
  }
  
  SubSocket(Channel.Connection conn) {
    this.state = new Connected(conn);
  }

  public void close() throws IOException {
    state.close();
  }

  public void connect(InetSocketAddress addr) throws IOException {
    state.connect(addr);
  }

  public OutputStream getOutputStream() throws IOException {
    return state.getOutputStream();
  }
  
  public InputStream getInputStream() throws IOException {
    return state.getInputStream();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation                                      //
  //                                                                          //
  //                connect                close                              //
  //  unconnected  --------->  connected  ------->  closed                    //
  //       |                       |                  |                       //
  //       +-----------------------+------------------+---------------> error //
  //                                                       exception          //
  //////////////////////////////////////////////////////////////////////////////
  
  private State            state;
  
  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    protected Exception cause = null;
    
    public void close() throws IOException {
      throw new IOException("Cannot close socket: socket " + this, cause);
    }
    
    public void connect(InetSocketAddress addr) throws IOException {
      throw new IOException("Cannot connect: socket " + this, cause);
    }

    public InputStream getInputStream() throws IOException {
      throw new IOException("Cannot get an input stream: socket " + this, cause);
    }
    
    public OutputStream getOutputStream() throws IOException {
      throw new IOException("Cannot get an output stream: socket " + this, cause);
    }
  }

  /**
   * implementation of methods in the Unconnected state
   */
  private final class Unconnected extends State {
    private final SubSocketFactory factory;
    
    @Override public String toString() { return "is unconnected"; }
    
    @Override
    public void connect(InetSocketAddress addr) throws IOException {
      try {
        Channel.Connection conn = factory.getChannel(addr).connect(); 
        state = new Connected(conn);
      } catch (final Exception exc) {
        IOException wrapped = new IOException("failed to connect to " + addr, exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }
    
    public Unconnected(SubSocketFactory factory) {
      this.factory = factory;
    }
  }

  /**
   * implementation of methods in the Connected(channel) state
   */
  private final class Connected extends State {
    final Channel.Connection   conn;
    
    @Override
    public String toString() {
      return "is connected (" + conn.toString() + ")";
    }
    
    @Override
    public void close() throws IOException {
      try {
        conn.close();
        state = new Closed();
      } catch (final Exception exc) {
        IOException wrapped = new IOException("failed to close connection", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }
    
    @Override
    public InputStream getInputStream() {
      return conn.in;
    }

    @Override
    public OutputStream getOutputStream() {
      return conn.out;
    }

    public Connected(Channel.Connection conn) {
      this.conn = conn;
    }
  }

  /**
   * implementation of methods in the Closed state
   */
  private final class Closed extends State {
    @Override public String toString() { return "is closed"; }
  }
  
  /**
   * implementations of methods in the Error state
   */
  private final class ErrorState extends State {
    @Override public String toString() { return "has recieved an exception"; }
    
    public ErrorState(Exception exc) {
      super();
      cause = exc;
    }
  }
}
