package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

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
  private static final int BUF_SIZE = 64 * 1024;

  /**
   * Position of the streamID in the header.
   */
  private static final int STREAM_ID_POS = 0;

  /**
   * Length of the streamID in the header. Currently an int.
   */
  private static final int STREAM_ID_LEN = 4;

  /**
   * Position of the payload-size field in the header.
   */
  private static final int PAYLOAD_SIZE_POS = STREAM_ID_POS + STREAM_ID_LEN;

  /**
   * Length of the payload-size field in the header. Currently an int.
   */
  private static final int PAYLOAD_SIZE_LEN = 4;

  /**
   * Size of stream headers.
   */
  private static final int STREAM_HEADER_SIZE =
      PAYLOAD_SIZE_POS + PAYLOAD_SIZE_LEN;

  protected final SocketChannel sock;

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
  protected Channel(ShakenSocket<Node> s, int maxOpenConnections) {
    setDaemon(true);

    if (maxOpenConnections < 0) {
      throw new IllegalArgumentException(
          "maxOpenConnections cannot be negative.");
    }

    this.sock = s.sock;
    this.remoteIdentity = s.remoteIdentity;

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
    ByteBuffer buf = ByteBuffer.allocate(STREAM_HEADER_SIZE);
    buf.putInt(streamID);
    buf.putInt(0);
    buf.flip();
    sock.write(buf);
  }

  /**
   * Sends data on the SocketChannel.
   *
   * @param data
   *         the buffers containing data to be sent.
   *
   * @param length
   *         the number of buffers to send; must be non-negative and no larger
   *         than {@code data.length}.
   */
  private void sendData(ByteBuffer[] data, int length) throws IOException {
    if (NETWORK_CHANNEL_LOGGER.isLoggable(Level.FINE)) {
      int len = 0;
      for (int i = 0; i < length; i++) {
        len += data[i].remaining();
      }
      Logging.log(NETWORK_CHANNEL_LOGGER, Level.FINE,
          "sending {0} bytes of data on {1}", len, this);
    }

    sock.write(data, 0, length);
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
  private void recvData(int streamID, ByteBuffer data) throws IOException {
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
   * Reads from the given {@link SocketChannel} until the given
   * {@link ByteBuffer} is filled.
   */
  private static void readFully(SocketChannel sock, ByteBuffer buf)
      throws IOException {
    read(sock, buf, buf.remaining());
  }

  /**
   * Reads the specified number of bytes from the given socket into the given
   * buffer. If there is not enough space in the buffer, then this method
   * returns when the buffer is filled.
   */
  private static void read(SocketChannel sock, ByteBuffer buf, int len)
      throws IOException {
    int oldLimit = buf.limit();
    int newLimit = buf.position() + len;
    if (newLimit < oldLimit) buf.limit(newLimit);

    while (buf.hasRemaining()) {
      sock.read(buf);
    }

    buf.limit(oldLimit);
  }

  /**
   * Reads data from the input stream and dispatches it to the appropriate
   * reader.
   */
  @Override
  public void run() {
    try {
      ByteBuffer headerBuf = ByteBuffer.allocateDirect(STREAM_HEADER_SIZE);
      while (true) {
        readFully(sock, headerBuf);
        headerBuf.flip();
        int streamID = headerBuf.getInt();
        int len = headerBuf.getInt();
        headerBuf.clear();

        if (len == 0) {
          recvClose(streamID);
          continue;
        }

        ByteBuffer payloadBuf = ByteBuffer.allocate(len);
        read(sock, payloadBuf, len);
        payloadBuf.flip();

        Logging.log(NETWORK_CHANNEL_LOGGER, Level.FINE,
            "received {0} bytes on {1}", len, this);

        recvData(streamID, payloadBuf);
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
    final public MuxedOutputStream out;

    /**
     * The output stream for sending data to the application.
     */
    final public Pipe.OutputStream sink;

    /**
     * Whether the connection has been closed locally. The connection is not
     * fully closed until it is closed by both end points.
     */
    private boolean locallyClosed;

    public Connection(int streamID) {
      this.locallyClosed = false;

      this.streamID = streamID;

      this.out = new MuxedOutputStream(streamID);

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
            "Unexpected error while closing application-facing input stream",
            e);
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
    public synchronized void receiveData(ByteBuffer b) throws IOException {
      if (!locallyClosed) {
        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "putting {0} bytes in pipe",
            b.remaining());
        sink.write(b);
      } else {
        NETWORK_CHANNEL_LOGGER.log(Level.FINE,
            "discarding {0} bytes (pipe closed)", b.remaining());
      }
    }
  }

  /**
   * An buffering OutputStream that automatically adds a header to any data
   * written to the channel's output stream. The header contains the stream id
   * and the size of the data payload.
   *
   * Adapted from java.io.BufferedOutputStream.
   */
  class MuxedOutputStream extends OutputStream {
    /**
     * The internal buffer.
     */
    private final ByteBuffer buf;

    /**
     * A reusable array of buffers.
     */
    private final ByteBuffer[] bufs;

    private MuxedOutputStream(int streamID) {
      this.buf = ByteBuffer.allocateDirect(BUF_SIZE);
      this.bufs = new ByteBuffer[] { buf, null };

      // Fill in the streamID in the header.
      buf.putInt(streamID);

      initBuffer();
    }

    /**
     * Reinitializes the buffer, while reserving space for the header.
     */
    private void initBuffer() {
      buf.clear();
      buf.position(STREAM_HEADER_SIZE);
    }

    /**
     * Flushes the internal buffer.
     */
    private void flushBuffer() throws IOException {
      flushBuffer(null);
    }

    /**
     * Flushes the internal buffer.
     *
     * @param morePayload
     *         if not null, this will be appended to the payload currently in
     *         the buffer.
     */
    private void flushBuffer(ByteBuffer morePayload) throws IOException {
      if (buf.position() > STREAM_HEADER_SIZE
          || morePayload != null && morePayload.hasRemaining()) {
        // Fill in the size of the payload.
        int payloadSize = buf.position() - STREAM_HEADER_SIZE;
        if (morePayload != null) payloadSize += morePayload.remaining();
        buf.putInt(PAYLOAD_SIZE_POS, payloadSize);
        buf.flip();

        bufs[1] = morePayload;
        sendData(bufs, morePayload == null ? 1 : 2);
        bufs[1] = null;

        initBuffer();
      }
    }

    @Override
    public synchronized void write(int b) throws IOException {
      if (!buf.hasRemaining()) {
        flushBuffer();
      }

      buf.put((byte) b);
    }

    @Override
    public synchronized void write(byte[] b, int offset, int len)
        throws IOException {
      if (len < 0) throw new IllegalArgumentException("len = " + len);

      // Just add to the buffer if there is space.
      if (buf.remaining() >= len) {
        buf.put(b, offset, len);
        return;
      }

      // Add to the existing payload and send it out.
      flushBuffer(ByteBuffer.wrap(b, offset, len));
    }

    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }

    @Override
    public synchronized void flush() throws IOException {
      flushBuffer();
    }
  }
}
