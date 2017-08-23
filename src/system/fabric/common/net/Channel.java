package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
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

import fabric.common.ConfigProperties;
import fabric.common.Logging;
import fabric.common.SerializationUtil;
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
  private static final int BUF_SIZE = 64 * 1024;

  /**
   * Connects to the network.
   */
  private final OutputStream out;

  /**
   * Connects to the network.
   */
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
  protected Channel(ConfigProperties config, ShakenSocket<Node> s,
      int maxOpenConnections) throws IOException {
    setDaemon(true);

    if (maxOpenConnections < 0) {
      throw new IllegalArgumentException(
          "maxOpenConnections cannot be negative.");
    }

    this.sock = s.sock;
    this.remoteIdentity = s.remoteIdentity;

    OutputStream out = this.sock.getOutputStream();
    InputStream in = this.sock.getInputStream();

    // Add inbound delay, if configured.
    String remoteNodeName = s.remoteIdentity.node.name;
    if (config.inDelays.containsKey(remoteNodeName)) {
      short delay = config.inDelays.get(remoteNodeName);
      if (delay > 0) {
        in = new DelayedInputStream(remoteNodeName, in, delay);
      }
    }

    if (USE_COMPRESSION) {
      out = new GZIPOutputStream(out, true);
      in = new GZIPInputStream(in);
    }

    this.out = out;
    this.in = new DataInputStream(new BufferedInputStream(in, BUF_SIZE));

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
      byte[] buf = new byte[8];
      SerializationUtil.setIntAt(buf, 0, streamID);
      SerializationUtil.setIntAt(buf, 4, 0);
      out.write(buf);
      out.flush();
    }
  }

  /** send data */
  private void sendData(byte[] data, int offset, int len, boolean flush)
      throws IOException {
    Logging.log(NETWORK_CHANNEL_LOGGER, Level.FINE,
        "sending {0} bytes of data on {1}", len, this);

    synchronized (out) {
      out.write(data, offset, len);
      if (flush) out.flush();
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
   * An buffering OutputStream that automatically adds a header to any data
   * written to the channel's output stream. The header contains the stream id
   * and the size of the data payload.
   *
   * Adapted from java.io.BufferedOutputStream.
   */
  class MuxedOutputStream extends OutputStream {
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

    /**
     * The internal buffer.
     */
    private final byte[] buf;

    /**
     * The number of bytes contained in the buffer.
     */
    private int count;

    private MuxedOutputStream(int streamID) {
      this.buf = new byte[BUF_SIZE];
      initBuffer();

      // Fill in the streamID in the header.
      SerializationUtil.setIntAt(buf, STREAM_ID_POS, streamID);
    }

    /**
     * Reinitializes the buffer, while reserving space for the header.
     */
    private void initBuffer() {
      count = STREAM_HEADER_SIZE;
    }

    /**
     * Flushes the internal buffer.
     *
     * @param flushUnderlying whether to flush the underlying output stream.
     */
    private void flushBuffer(boolean flushUnderlying) throws IOException {
      if (count > STREAM_HEADER_SIZE) {
        // Fill in the size of the payload.
        SerializationUtil.setIntAt(buf, PAYLOAD_SIZE_POS,
            count - STREAM_HEADER_SIZE);

        sendData(buf, 0, count, flushUnderlying);
        initBuffer();
      }
    }

    @Override
    public synchronized void write(int b) throws IOException {
      if (count >= buf.length) {
        flushBuffer(false);
      }

      buf[count++] = (byte) b;
    }

    @Override
    public synchronized void write(byte[] b, int offset, int len)
        throws IOException {
      if (len < 0) throw new IllegalArgumentException("len = " + len);

      while (len > 0) {
        // Ensure there is space in the buffer.
        if (count >= buf.length) {
          flushBuffer(false);
        }

        // Figure out how much to copy.
        int amtToCopy = buf.length - count;
        if (len < amtToCopy) amtToCopy = len;

        // Actually copy.
        System.arraycopy(b, offset, buf, count, amtToCopy);
        offset += amtToCopy;
        len -= amtToCopy;
        count += amtToCopy;
      }
    }

    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }

    @Override
    public synchronized void flush() throws IOException {
      flushBuffer(true);
    }
  }
}
