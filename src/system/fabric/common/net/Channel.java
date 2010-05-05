package fabric.common.net;

import static fabric.common.Logging.NETWORK_CHANNEL_LOGGER;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

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
  //  subsocket close (sendClose(SN) method): SN 0
  //  subsocket send  (sendData      method): SN length data[len]
  //
  // any unrecognized or previously closed SN should create a new stream (thus
  // the subsocket close message should be the last sent by a subsocket).

  protected Channel(ShakenSocket s) throws IOException {
    super();
    this.sock = s.sock;
    this.out  = new DataOutputStream(this.sock.getOutputStream());
    this.in   = new DataInputStream(this.sock.getInputStream());
    this.connections = new HashMap<Integer, Connection>();

    start();
  }

  @Override public abstract String toString();
  
  /** called to create a Connection to an unknown sequence number */
  protected abstract Connection accept(int sequence) throws IOException;
  
  /** called to notify the container that there are no remaining open sockets */
  protected abstract void cleanup();

  /** send channel close message */
  public synchronized void sendClose() throws IOException {
    out.writeInt(0);
    out.flush();
  }

  /** send subsocket close message */
  public synchronized void sendClose(int sequence) throws IOException {
    out.writeInt(sequence);
    out.writeInt(0);
    out.flush();
  }

  /** send data */
  public synchronized void sendData(int sequence, byte[] data, int offset, int len) throws IOException {
    NETWORK_CHANNEL_LOGGER.log(Level.FINE, "sending " + len
        + " bytes of data on {0}", this);
    
    out.writeInt(sequence);
    out.writeInt(len);
    out.write(data, offset, len);
    out.flush();
  }

  /** called on receipt of a channel close message */ 
  public synchronized void recvClose() {
    throw new NotImplementedException();
  }

  /** called on receipt of subsocket close message */
  public synchronized void recvClose(int sequence) throws IOException {
    Connection listener = getReceiver(sequence);
    listener.close();
  }

  /** called on receipt of data message */
  public synchronized void recvData(int sequence, byte[] data) throws IOException {
    Connection listener = getReceiver(sequence);
    listener.receiveData(data);
  }

  /**
   * returns the Connection associated with a given sequence number, creating
   * it if necessary
   * */
  private synchronized Connection getReceiver(int sequence) throws IOException {
    Connection result = connections.get(sequence);
    if (result == null) {
      result = accept(sequence);
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
        int sequenceNumber = in.readInt();
        if (sequenceNumber == 0) {
          recvClose();
          continue;
        }

        int len = in.readInt();
        if (len == 0) {
          // error - deliver to reader
          recvClose(sequenceNumber);
          continue;
        }

        byte[] buf = new byte[len];
        in.read(buf);
        
        NETWORK_CHANNEL_LOGGER.log(Level.FINE, "received " + len
            + " bytes on {0}", this);
        
        recvData(sequenceNumber, buf);
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
    final public int sequenceNum;
    final public InputStream  in;
    final public OutputStream out;

    final public OutputStream sink;

    public Connection(int sequenceNum) throws IOException {
      this.sequenceNum = sequenceNum;
      this.out         = new BufferedOutputStream(new MuxedOutputStream(sequenceNum));

      PipedInputStream in = new PipedInputStream();
      this.sink           = new PipedOutputStream(in);
      this.in             = in;
      connections.put(this.sequenceNum, this);
    }

    @Override
    public String toString() {
      return "stream " + sequenceNum + " on " + Channel.this.toString();
    }

    /** this method is called by SubSocket.close(). */
    public void close() throws IOException {
      throw new NotImplementedException();
      //in.close();
      //out.close();
      //sendClose(sequenceNum);
    }

    /** this method called by recvClose in response to a close message */
    public void receiveClose() throws IOException {
      throw new NotImplementedException();
      //connections.remove(this);
      //sink.close();
    }

    /** forward data to the reading thread */
    public void receiveData(byte[] b) throws IOException {
      NETWORK_CHANNEL_LOGGER.fine("putting data in pipe");
      sink.write(b);
      sink.flush();
    }
  }

  /**
   * an OutputStream that expands written data to include the sequence number,
   * and writes to the channel's output stream. These should be wrapped in
   * BufferedOutputStreams before being returned.
   */
  private class MuxedOutputStream extends OutputStream {
    private final int sequenceNumber;

    public MuxedOutputStream(int sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
    }
    
    @Override
    public void write(int arg0) throws IOException {
      throw new IOException("MuxedOutputStreams do not support writing unbuffered data");
    }

    @Override
    public void write(byte[] buf, int offset, int len) throws IOException {
      sendData(sequenceNumber, buf, offset, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
      write (b, 0, b.length);
    }
  }
}

