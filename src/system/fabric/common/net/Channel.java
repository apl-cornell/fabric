package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.handshake.ShakenSocket;


/**
 * A channel manages a single socket, allowing it to be multiplexed across
 * multiple SubSockets.
 * 
 * @author mdgeorge
 */
abstract class Channel extends Thread {
  private final DataOutputStream out;
  private final DataInputStream  in;
  protected final Socket           sock;

  private final Map<Integer, Connection> connections;

  // channel protocol:
  //
  // a message is one of the following:
  //  channel   close (sendClose()   method): 0  0
  //  subsocket close (sendClose(ID) method): ID 0
  //  subsocket send  (sendData      method): ID length data[len]
  //
  // any unrecognized or previously closed stream ID should create a new stream
  // (thus the subsocket close message should be the last sent by a subsocket).

  protected Channel(ShakenSocket s) throws IOException {
    super();
    this.sock = s.sock;
    this.out  = new DataOutputStream(this.sock.getOutputStream());
    this.in   = new DataInputStream(this.sock.getInputStream());
    this.connections = new HashMap<Integer, Connection>();

    start();
  }

  @Override public abstract String toString();
  
  /** called to create a Connection to an unknown stream id */
  protected abstract Connection accept(int streamID) throws IOException;
  
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
  public synchronized void sendData(int streamID, byte[] data, int offset, int len) throws IOException {
    NETWORK_CHANNEL_LOGGER.log(Level.FINE, "sending " + len
        + " bytes of data on {0}", this);
    
    out.writeInt(streamID);
    out.writeInt(len);
    out.write(data, offset, len);
    out.flush();
  }

  /** called on receipt of a channel close message */ 
  public synchronized void recvClose() {
    throw new NotImplementedException();
  }

  /** called on receipt of subsocket close message */
  public synchronized void recvClose(int streamID) throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveClose();
  }

  /** called on receipt of data message */
  public synchronized void recvData(int streamID, byte[] data) throws IOException {
    Connection listener = getReceiver(streamID);
    listener.receiveData(data);
  }

  /**
   * returns the Connection associated with a given stream id, creating
   * it if necessary
   * */
  private synchronized Connection getReceiver(int streamID) throws IOException {
    Connection result = connections.get(streamID);
    if (result == null) {
      result = accept(streamID);
    }
    return result;
  }

  /**
   * Reads data off of the input stream and dispatches it to the
   * appropriate reader.
   */
  @Override
  public void run() {
    try {
      while(true) {
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
        in.read(buf);
        
        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "received " + len
            + " bytes on {0}", this);
        
        recvData(streamID, buf);
      }
    } catch (final IOException exc) {
      // TODO cleanup
      throw new NotImplementedException(exc);
    }
  }

  /**
   * this contains all of the state for an open connection.
   */
  class Connection {
    final public int streamID;
    final public InputStream  in;
    final public OutputStream out;

    final public OutputStream sink;

    public Connection(int streamID) throws IOException {
      this.streamID = streamID;
      this.out      = new BufferedOutputStream(new MuxedOutputStream(streamID));

      PipedInputStream in = new PipedInputStream();
      this.sink           = new PipedOutputStream(in);
      this.in             = in;
      connections.put(this.streamID, this);
    }

    @Override
    public String toString() {
      return "stream " + streamID + " on " + Channel.this.toString();
    }

    /** this method is called by SubSocket.close(). */
    public void close() throws IOException {
      sendClose(streamID);
      connections.remove(streamID);
    }

    /** this method called by recvClose in response to a close message */
    public void receiveClose() throws IOException {
      connections.remove(this);
      sink.close();
    }

    /** forward data to the reading thread */
    public void receiveData(byte[] b) throws IOException {
      NETWORK_CHANNEL_LOGGER.fine("putting data in pipe");
      sink.write(b);
      sink.flush();
    }
  }

  /**
   * an OutputStream that expands written data to include the stream id,
   * and writes to the channel's output stream. These should be wrapped in
   * BufferedOutputStreams before being returned.
   */
  private class MuxedOutputStream extends OutputStream {
    private final int streamID;

    public MuxedOutputStream(int streamID) {
      this.streamID = streamID;
    }
    
    @Override
    public void write(int arg0) throws IOException {
      throw new IOException("MuxedOutputStreams do not support writing unbuffered data");
    }

    @Override
    public void write(byte[] buf, int offset, int len) throws IOException {
      sendData(streamID, buf, offset, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
      write (b, 0, b.length);
    }
  }
}

