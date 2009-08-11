package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Client-side multiplexed socket implementation. The API mirrors that of
 * java.net.Socket
 * 
 * @author mdgeorge
 */
public class SubSocket {
  //////////////////////////////////////////////////////////////////////////////
  // public API                                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  public SubSocket() {
    this.state = new Unbound();
  }
  
  public void close() throws IOException {
    state.close();
  }

  public void connect(InetSocketAddress addr, int timeout) throws IOException {
    state.connect(addr, timeout);
  }

  public void connect(InetSocketAddress addr) throws IOException {
    this.connect(addr, 0);
  }

  public OutputStream getOutputStream() throws IOException {
    return state.getOutputStream();
  }
  
  public InputStream getInputStream() throws IOException {
    return state.getInputStream();
  }

  //////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation                                      //
  //////////////////////////////////////////////////////////////////////////////
  
  private State state;
  
  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    void close() throws IOException {
      throw new IOException("Cannot close socket: socket is " + this);
    }

    void connect(InetSocketAddress addr, int timeout) throws IOException {
      throw new IOException("Cannot connect: socket is " + this);
    }

    InputStream getInputStream() throws IOException {
      throw new IOException("Cannot get an input stream: socket is " + this);
    }
    
    OutputStream getOutputStream() throws IOException {
      throw new IOException("Cannot get an output stream: socket is " + this);
    }
  }

  /**
   * implementation of methods in the Connected(address) state
   */
  private final class Connected extends State {
    @Override
    void close() {
      state = new Closed();
      throw new NotImplementedException();
    }
    
    @Override
    public String toString() {
      return "connected to ";
    }
  }

  private final class Closed extends State {
    @Override public String toString() { return "closed"; } 
  }

  private final class Unbound extends State {
    @Override public String toString() { return "unbound"; }
    
    @Override
    void connect(InetSocketAddress addr, int timeout) {
      state = new Connected();
      throw new NotImplementedException();
    }
  }
}
