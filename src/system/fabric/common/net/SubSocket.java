package fabric.common.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fabric.common.net.naming.SocketAddress;
import fabric.lang.security.Principal;
import fabric.net.RemoteNode;

/**
 * Client-side multiplexed socket implementation. The API mirrors that of
 * java.net.Socket. This class manages connection state, and provides a
 * front-end API.
 *
 * @see java.net.Socket
 * @param <Node> the type of node at the remote endpoint.
 */
public class SubSocket<Node extends RemoteNode<Node>> {
  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /** @see SubSocketFactory */
  protected SubSocket(SubSocketFactory<Node> factory) {
    this.state = new Unconnected(factory);
  }

  /**
   * Create a connected SubSocket. This is used internally by ServerChannels for
   * accepting incoming streams.
   */
  SubSocket(Channel<Node>.Connection conn) {
    this.state = new Connected(conn);
  }

  /** @see java.net.Socket#close() */
  public synchronized final void close() throws IOException {
    state.close();
  }

  /** @see java.net.Socket#connect(SocketAddress) */
  public synchronized final void connect(Node node) throws IOException {
    state.connect(node);
  }

  public synchronized final int getStreamID() throws IOException {
    return state.getStreamID();
  }

  public synchronized final RemoteIdentity<Node> getRemoteIdentity()
      throws IOException {
    return state.getRemoteIdentity();
  }

  /**
   * Returns an output stream for this SubSocket. Buffering on this output
   * stream should not be necessary.
   *
   * @see java.net.Socket#getOutputStream()
   */
  public synchronized final BufferedOutputStream getOutputStream()
      throws IOException {
    return state.getOutputStream();
  }

  /**
   * Returns an input stream for this SubSocket. Buffering on this input stream
   * should not be necessary.
   *
   * @see java.net.Socket#getInputStream()
   */
  public synchronized final InputStream getInputStream() throws IOException {
    return state.getInputStream();
  }

  /**
   * Return the Principal that represents the remote endpoint of the connection
   */
  public synchronized final Principal getPrincipal() throws IOException {
    return state.getPrincipal();
  }

  @Override
  public String toString() {
    return state.toString();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation //
  // //
  // connect close //
  // unconnected ---------> connected -------> closed //
  // | | | //
  // +-----------------------+------------------+---------------> error //
  // exception //
  // ////////////////////////////////////////////////////////////////////////////

  private State state;

  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    protected Exception cause = null;

    void close() throws IOException {
      throw new IOException("Cannot close socket: " + this, cause);
    }

    int getStreamID() throws IOException {
      throw new IOException("Cannot get stream ID: " + this, cause);
    }

    void connect(Node node) throws IOException {
      throw new IOException("Cannot connect: " + this, cause);
    }

    RemoteIdentity<Node> getRemoteIdentity() throws IOException {
      throw new IOException("Cannot get remote identity: " + this, cause);
    }

    InputStream getInputStream() throws IOException {
      throw new IOException("Cannot get an input stream: " + this, cause);
    }

    BufferedOutputStream getOutputStream() throws IOException {
      throw new IOException("Cannot get an output stream: " + this, cause);
    }

    Principal getPrincipal() throws IOException {
      throw new IOException(
          "There is no principal associated with the socket: " + this, cause);
    }
  }

  /**
   * implementation of methods in the Unconnected state
   */
  private final class Unconnected extends State {
    private final SubSocketFactory<Node> factory;

    @Override
    public String toString() {
      return "unconnected socket";
    }

    @Override
    void connect(Node node) throws IOException {
      try {
        Channel<Node>.Connection conn = factory.getChannel(node).connect();
        state = new Connected(conn);
      } catch (final Exception exc) {
        IOException wrapped =
            new IOException("failed to connect to \"" + node.name + "\"", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }

    private Unconnected(SubSocketFactory<Node> factory) {
      this.factory = factory;
    }
  }

  /**
   * implementation of methods in the Connected(channel) state
   */
  private final class Connected extends State {
    final Channel<Node>.Connection conn;

    @Override
    public String toString() {
      return "connected socket (" + conn.toString() + ")";
    }

    @Override
    void close() throws IOException {
      try {
        conn.close();
        state = new Closed();
      } catch (final Exception exc) {
        IOException wrapped =
            new IOException("failed to close connection", exc);
        state = new ErrorState(wrapped);
        throw wrapped;
      }
    }

    @Override
    RemoteIdentity<Node> getRemoteIdentity() throws IOException {
      return conn.getRemoteIdentity();
    }

    @Override
    InputStream getInputStream() {
      return conn.in;
    }

    @Override
    BufferedOutputStream getOutputStream() {
      return conn.out;
    }

    @Override
    int getStreamID() {
      // TODO Auto-generated method stub
      return conn.streamID;
    }

    @Override
    Principal getPrincipal() {
      return conn.getRemoteIdentity().principal;
    }

    Connected(Channel<Node>.Connection conn) {
      this.conn = conn;
    }
  }

  /**
   * implementation of methods in the Closed state
   */
  private final class Closed extends State {
    @Override
    public String toString() {
      return "socket closed";
    }
  }

  /**
   * implementations of methods in the Error state
   */
  private final class ErrorState extends State {
    @Override
    public String toString() {
      return "socket has received an exception";
    }

    private ErrorState(Exception exc) {
      cause = exc;
    }
  }
}
