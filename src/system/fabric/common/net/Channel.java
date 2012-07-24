package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.exceptions.InternalError;
import fabric.common.net.handshake.ShakenSocket;
import fabric.lang.security.Principal;

/**
 * A channel manages a single socket, allowing it to be multiplexed across
 * multiple SubSockets.
 * 
 * @author mdgeorge
 */
abstract class Channel extends Thread {
  private final DataOutputStream out;
  private final DataInputStream in;
  protected final Socket sock;

  private final Map<Integer, Connection> connections;
  private final Principal remotePrincipal;

  // channel protocol:
  //
  // a message is one of the following:
  // channel close (sendClose() method): 0 0
  // subsocket close (sendClose(ID) method): ID 0
  // subsocket send (sendData method): ID length data[len]
  //
  // any unrecognized or previously closed stream ID should create a new stream
  // (thus the subsocket close message should be the last sent by a subsocket).

  protected Channel(ShakenSocket s) throws IOException {
    super();
    setDaemon(true);

    this.sock = s.sock;
    this.remotePrincipal = s.principal;
    this.out = new DataOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
    this.in = new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
    this.connections = new HashMap<Integer, Connection>();

    start();
  }

  @Override
  public abstract String toString();

  /** called to create a Connection to an unknown stream id */
  protected abstract Connection accept(int streamID) throws IOException;

  /** called to clean up a channel that has been closed */
  protected abstract void cleanup();

  /** send channel close message */
  public synchronized void sendClose() throws IOException {
    out.writeInt(0);
    out.flush();
  }

  /** send subsocket close message */
  public synchronized void sendClose(int streamID) throws IOException {
    out.writeInt(streamID);
    out.writeInt(0);
    out.flush();
  }

  /** send data */
  public synchronized void sendData(int streamID, byte[] data, int offset,
      int len) throws IOException {
    NETWORK_CHANNEL_LOGGER.log(Level.FINE, "sending " + len
        + " bytes of data on {0}", this);

    out.writeInt(streamID);
    out.writeInt(len);
    out.write(data, offset, len);
    out.flush();
  }

  /** called on receipt of a channel close message */
  public synchronized void recvClose() {
    NETWORK_CHANNEL_LOGGER.log(Level.INFO,
        "cleaning up {0} after channel close", this);

    for (Connection c : connections.values())
      c.receiveClose();

    cleanup();
  }

  /** called on receipt of subsocket close message */
  public synchronized void recvClose(int streamID) throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveClose();
  }

  /** called on receipt of data message */
  public synchronized void recvData(int streamID, byte[] data)
      throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveData(data);
  }

  /**
   * returns the Connection associated with a given stream id, creating it if
   * necessary
   */
  private synchronized Connection getReceiver(int streamID) throws IOException {
    Connection result = connections.get(streamID);
    if (result == null) {
      result = accept(streamID);
    }
    return result;
  }

  /**
   * Reads data off of the input stream and dispatches it to the appropriate
   * reader.
   */
  @Override
  public void run() {
    try {
      while (true) {
        int streamID = in.readInt();
        if (streamID == 0) {
          recvClose();
          continue;
        }

        int len = in.readInt();
        if (len == 0) {
          // error - deliver to reader
          recvClose(streamID);
          continue;
        }

        byte[] buf = new byte[len];
        in.readFully(buf);

        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "received " + len
            + " bytes on {0}", this);

        recvData(streamID, buf);
      }
    } catch (final IOException exc) {
      recvClose();
    }
  }

  /**
   * this contains all of the state for an open connection.
   */
  class Connection {
    final public int streamID;
    final public InputStream in;
    final public OutputStream out;

    final public OutputStream sink;

    public Connection(int streamID) throws IOException {
      this.streamID = streamID;
      this.out = new BufferedOutputStream(new MuxedOutputStream(streamID));

      PipedInputStream in = new PipedInputStream();
      this.sink = new PipedOutputStream(in);
      this.in = in;
      connections.put(this.streamID, this);
    }

    @Override
    public String toString() {
      return "stream " + streamID + " on " + Channel.this.toString();
    }

    public Principal getPrincipal() {
      return Channel.this.remotePrincipal;
    }

    /** this method is called by SubSocket.close(). */
    public void close() throws IOException {
      sendClose(streamID);
      connections.remove(streamID);
    }

    /** this method called by recvClose in response to a close message */
    public void receiveClose() {
      connections.remove(this);
      try {
        sink.close();
      } catch (final IOException e) {
        throw new InternalError("Internal pipe failed unexpectedly", e);
      }
    }

    /** forward data to the reading thread */
    public void receiveData(byte[] b) throws IOException {
      NETWORK_CHANNEL_LOGGER.fine("putting " + b.length + " bytes in pipe");
      sink.write(b);
      sink.flush();
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
