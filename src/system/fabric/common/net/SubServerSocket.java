package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import fabric.common.net.SubServerSocketFactory.Acceptor;
import fabric.worker.remote.RemoteWorker;

/**
 * Server-side multiplexed socket implementation. The API mirrors that of
 * java.net.ServerSocket.
 *
 * @see java.net.ServerSocket
 */
public class SubServerSocket {
  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /** @see SubServerSocketFactory */
  protected SubServerSocket(SubServerSocketFactory factory) {
    this.state = new Unbound(factory);
  }

  /** @see java.net.ServerSocket#accept() */
  public SubSocket<RemoteWorker> accept() throws IOException {
    return state.accept();
  }

  /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
  public void bind(String name) throws IOException {
    bind(name, 50);
  }

  /**
   * @see java.net.ServerSocket#bind(java.net.SocketAddress, int)
   *
   * @param backlog
   *          the size of the queue. If non-positive, an unbounded queue is
   *          created.
   */
  public void bind(String name, int backlog) throws IOException {
    state.bind(name, backlog);
  }

  /** @see java.net.ServerSocket#close() */
  public void close() throws IOException {
    state.close();
  }

  // ////////////////////////////////////////////////////////////////////////////
  // State design pattern implementation //
  // //
  // bind close //
  // unbound ---------> bound -------> closed //
  // | | | //
  // +---------------------+------------------+---------------> error //
  // exception //
  // ////////////////////////////////////////////////////////////////////////////

  private State state;

  /**
   * default implementations of state methods - throws errors or returns default
   * values as appropriate.
   */
  private abstract class State {
    protected Exception cause = null;

    /** @see SubServerSocket#accept() */
    public SubSocket<RemoteWorker> accept() throws IOException {
      throw new IOException("Cannot accept a connection because server socket "
          + this, cause);
    }

    /**
     * @see SubServerSocket#bind(InetSocketAddress, int)
     *
     * @param backlog
     *          the size of the queue. If non-positive, an unbounded queue is
     *          created.
     */
    public void bind(String name, int backlog) throws IOException {
      throw new IOException("Cannot bind to local address " + name
          + " because server socket " + this, cause);
    }

    /** @see SubServerSocket#close() */
    public void close() throws IOException {
      throw new IOException("Cannot close server socket because it " + this,
          cause);
    }
  }

  /**
   * implementation of state methods in the unbound state.
   */
  private final class Unbound extends State {
    @Override
    public String toString() {
      return "is unbound";
    }

    private final SubServerSocketFactory factory;

    @Override
    public void bind(String name, int backlog) throws IOException {
      try {
        Acceptor.ConnectionQueue queue = factory.bind(name, backlog);
        state = new Bound(queue);
      } catch (final Exception exc) {
        IOException wrapped =
            new IOException("failed to bind to local address " + name, exc);
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
    final Acceptor.ConnectionQueue queue;

    @Override
    public String toString() {
      return "is bound to " + queue;
    }

    @Override
    public SubSocket<RemoteWorker> accept() {
      return queue.accept();
    }

    @Override
    public void close() {
      queue.close();
      state = new Closed();
    }

    public Bound(Acceptor.ConnectionQueue queue) {
      this.queue = queue;
    }
  }

  /**
   * implementation of state methods in the closed state.
   */
  private final class Closed extends State {
    @Override
    public String toString() {
      return "is closed";
    }
  }

  /**
   * implementation of state methods in an error state
   */
  private final class ErrorState extends State {
    @Override
    public String toString() {
      return "has recieved an exception";
    }

    public ErrorState(Exception exc) {
      this.cause = exc;
    }
  }
}
