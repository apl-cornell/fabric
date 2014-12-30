package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import fabric.common.Logging;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.handshake.ShakenSocket;
import fabric.net.RemoteNode;

/**
 * A channel manages a single socket, allowing it to be multiplexed across
 * multiple SubSockets.
 *
 * @param <Node> the type of node at the remote endpoint.
 */
abstract class Channel<Node extends RemoteNode<Node>> extends Thread {
  static final int DEFAULT_MAX_OPEN_CONNECTIONS = 0;
  private static final boolean USE_COMPRESSION = false;

  private final DataOutputStream out;
  private final DataInputStream in;
  protected final Socket sock;

  /**
   * Contains connections for which the remote end-point has not yet closed.
   */
  protected final Map<Integer, Connection> connections;

  /**
   * Maximum size of <code>connections</code>.
   */
  private final int maxOpenConnections;
  private final RemoteIdentity<Node> remoteIdentity;

  // channel protocol:
  //
  // a message is one of the following:
  // channel close (sendClose() method): 0 0
  // subsocket close (sendClose(ID) method): ID 0
  // subsocket send (sendData method): ID length data[len]
  //
  // any unrecognized or previously closed stream ID should create a new stream
  // (thus the subsocket close message should be the last sent by a subsocket).

  /**
   * @param maxOpenConnections if zero, then an unlimited number of open
   *          connections is permitted on this channel.
   */
  protected Channel(ShakenSocket<Node> s, int maxOpenConnections)
      throws IOException {
    setDaemon(true);

    if (maxOpenConnections < 0) {
      throw new IllegalArgumentException(
          "maxOpenConnections cannot be negative.");
    }

    this.sock = s.sock;
    this.remoteIdentity = s.remoteIdentity;

    OutputStream out = this.sock.getOutputStream();
    InputStream in = this.sock.getInputStream();

    if (USE_COMPRESSION) {
      out = new GZIPOutputStream(out, true);
      in = new GZIPInputStream(in);
    }

    this.out =
        new DataOutputStream(new BufferedOutputStream(out,
            sock.getSendBufferSize()));

    this.in =
        new DataInputStream(new BufferedInputStream(in,
            sock.getReceiveBufferSize()));

    this.connections = new HashMap<>();
    this.maxOpenConnections = maxOpenConnections;

    start();
  }

  @Override
  public abstract String toString();

  /** Called to create a Connection to an unknown stream ID. */
  protected abstract Connection accept(int streamID) throws IOException;

  /** called to clean up a channel that has been closed */
  protected abstract void cleanup();

  /** send subsocket close message */
  private void sendClose(int streamID) throws IOException {
    synchronized (out) {
      out.writeInt(streamID);
      out.writeInt(0);
      out.flush();
    }
  }

  /** send data */
  private void sendData(int streamID, byte[] data, int offset, int len)
      throws IOException {
    Logging.log(NETWORK_CHANNEL_LOGGER, Level.FINE,
        "sending {0} bytes of data on {1}", len, this);

    synchronized (out) {
      out.writeInt(streamID);
      out.writeInt(len);
      out.write(data, offset, len);
      out.flush();
    }
  }

  /** called on receipt of a channel close message */
  private void recvClose() {
    NETWORK_CHANNEL_LOGGER.log(Level.INFO,
        "cleaning up {0} after channel close", this);

    synchronized (connections) {
      while (!connections.isEmpty()) {
        connections.values().iterator().next().receiveClose();
      }
    }

    cleanup();
  }

  /** called on receipt of subsocket close message */
  private void recvClose(int streamID) throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveClose();
  }

  /** called on receipt of data message */
  private void recvData(int streamID, byte[] data) throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveData(data);
  }

  /**
   * Returns the Connection associated with a given stream id, creating it if
   * necessary.
   */
  private Connection getReceiver(int streamID) throws IOException {
    synchronized (connections) {
      Connection result = connections.get(streamID);
      if (result == null) {
        result = accept(streamID);
      }
      return result;
    }
  }

  /**
   * Reads data from the input stream and dispatches it to the appropriate
   * reader.
   */
  @Override
  public void run() {
    try {
      while (true) {
        int streamID = in.readInt();
        int len = in.readInt();

        if (len == 0) {
          recvClose(streamID);
          continue;
        }

        byte[] buf = new byte[len];
        in.readFully(buf);

        Logging.log(NETWORK_CHANNEL_LOGGER, Level.FINE,
            "received {0} bytes on {1}", len, this);

        recvData(streamID, buf);
      }
    } catch (final EOFException exc) {
      recvClose();
    } catch (final SocketException e) {
      if ("Connection reset".equalsIgnoreCase(e.getMessage())) {
        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "Connection reset", e);
        recvClose();
      } else {
        throw new NotImplementedException(e);
      }
    } catch (final IOException exc) {
      throw new NotImplementedException(exc);
    }
  }

  /**
   * this contains all of the state for an open connection.
   */
  class Connection {
    final public int streamID;

    /**
     * The application-facing input stream.
     */
    final public Pipe.InputStream in;

    /**
     * The application-facing output stream.
     */
    final public BufferedOutputStream out;

    /**
     * The output stream for sending data to the application.
     */
    final public Pipe.OutputStream sink;

    /**
     * Size of stream headers. Currently two ints: streamID and packet length.
     */
    public static final int STREAM_HEADER_SIZE = 8;

    /**
     * Whether the connection has been closed locally. The connection is not
     * fully closed until it is closed by both end points.
     */
    private boolean locallyClosed;

    public Connection(int streamID) throws IOException {
      this.locallyClosed = false;

      this.streamID = streamID;

      int bufSize = sock.getSendBufferSize() - STREAM_HEADER_SIZE;
      if (bufSize > 8192) bufSize = 8192;

      this.out =
          new BufferedOutputStream(new MuxedOutputStream(streamID), bufSize);

      Pipe buf = new Pipe();
      this.in = buf.getInputStream();
      this.sink = buf.getOutputStream();
      synchronized (connections) {
        while (maxOpenConnections > 0
            && connections.size() >= maxOpenConnections) {
          try {
            connections.wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
        connections.put(this.streamID, this);
        connections.notifyAll();
      }
    }

    @Override
    public String toString() {
      return "stream " + streamID + " on " + Channel.this.toString();
    }

    public RemoteIdentity<Node> getRemoteIdentity() {
      return Channel.this.remoteIdentity;
    }

    /**
     * Locally closes the connection. This method is called by SubSocket.close().
     */
    public synchronized void close() throws IOException {
      if (locallyClosed) return;
      locallyClosed = true;

      try {
        in.close();
      } catch (IOException e) {
        throw new InternalError(
            "Unexpected error while closing application-facing input stream", e);
      }

      try {
        out.close();
      } catch (IOException e) {
        throw new InternalError(
            "Unexpected error while closing application-facing output stream",
            e);
      }

      sink.close();

      sendClose(streamID);
    }

    /**
     * This method called by recvClose in response to a close message.
     */
    public synchronized void receiveClose() {
      synchronized (connections) {
        connections.remove(streamID);
        connections.notifyAll();
      }

      sink.close();
    }

    /**
     * Forwards the given buffer to the reading thread. The caller should not
     * modify the buffer after calling this method.
     */
    public synchronized void receiveData(byte[] b) throws IOException {
      if (!locallyClosed) {
        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "putting {0} bytes in pipe",
            b.length);
        sink.write(b);
      } else {
        NETWORK_CHANNEL_LOGGER.log(Level.FINE,
            "discarding {0} bytes (pipe closed)", b.length);
      }
    }
  }

  /**
   * an OutputStream that expands written data to include the stream id, and
   * writes to the channel's output stream. These should be wrapped in
   * BufferedOutputStreams before being returned.
   */
  private class MuxedOutputStream extends OutputStream {
    private final int streamID;

    public MuxedOutputStream(int streamID) {
      this.streamID = streamID;
    }

    @Override
    public void write(int arg0) throws IOException {
      throw new IOException(
          "MuxedOutputStreams do not support writing unbuffered data");
    }

    @Override
    public void write(byte[] buf, int offset, int len) throws IOException {
      sendData(streamID, buf, offset, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
  }
}
