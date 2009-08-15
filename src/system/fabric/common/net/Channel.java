package fabric.common.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * A channel manages a single socket, allowing it to be multiplexed across
 * multiple SubSockets.
 * 
 * @author mdgeorge
 */
abstract class Channel {
  private final DataOutputStream out;
  private final DataInputStream  in;
  protected final Socket           sock;
  
  private final Map<Integer, Connection> connections;
  
  // channel protocol:
  //
  // a message is one of the following:
  //  channel   close: 0  0
  //  subsocket close: SN 0
  //  subsocket send:  SN length data[len]
  //
  // if a server channel recieves a message with an unknown sequence number, it
  // should create a new subsocket and return it to the next call to accept.
  //
  // if a client channel recieves a message with an unknown sequence number, it
  // should respond with a subsocket close message (this should not happen)
  //
  // a newly connected client needs to send before recieving to avoid deadlock
  
  protected Channel(Socket s) throws IOException {
    this.sock = s;
    this.out  = new DataOutputStream(s.getOutputStream());
    this.in   = new DataInputStream(s.getInputStream());
    this.connections = new HashMap<Integer, Connection>();
    new Demuxer().start();
  }

  public synchronized void sendClose() throws IOException {
    out.writeInt(0);
  }
  
  public synchronized void sendClose(int sequence) throws IOException {
    out.writeInt(sequence);
    out.writeInt(0);
  }
  
  public synchronized void sendData(int sequence, byte[] data, int offset, int len) throws IOException {
    out.writeInt(sequence);
    out.writeInt(data.length);
    out.write(data, offset, len);
  }
  
  public synchronized void recvClose() throws IOException {
    throw new NotImplementedException();
  }
  
  public synchronized void recvClose(int sequence) throws IOException {
    Connection listener = getReceiver(sequence);
    listener.close();
  }
  
  public synchronized void recvData(int sequence, byte[] data) throws IOException {
    Connection listener = getReceiver(sequence);
    listener.receiveData(data);
  }
  
  public abstract Connection accept(int sequence) throws IOException;

  private Connection getReceiver(int sequence) throws IOException {
    Connection result = connections.get(sequence);
    if (result == null) {
      result = accept(sequence);
    }
    return result;
  }
  
  /**
   * a thread that reads data off of the input stream and dispatches it to the
   * appropriate reader.
   */
  private class Demuxer extends Thread {
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
          recvData(sequenceNumber, buf);
        }
      } catch (final IOException exc) {
        // TODO cleanup
        throw new NotImplementedException();
      }
    }
    
    public Demuxer() {
      super("demultiplexer for " + Channel.this.toString());
    }
  }
  
  @Override public abstract String toString();
  
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
      this.out         = new BufferedOutputStream(new MuxedOutputStream());
      
      PipedInputStream in = new PipedInputStream();
      this.sink           = new PipedOutputStream(in);
      this.in             = in;
      connections.put(this.sequenceNum, this);
    }
    
    @Override
    public String toString() {
      return "stream " + sequenceNum + " on " + Channel.this.toString();
    }

    /**
     * this method is called by SubSocket.close().
     */
    public void close() throws IOException {
      in.close();
      out.close();
      sendClose(sequenceNum);
    }
    
    /**
     * this method called by recvClose in response to a close message
     */
    public void receiveClose() throws IOException {
      connections.remove(this);
      sink.close();
    }
    
    public void receiveData(byte[] b) throws IOException {
      sink.write(b);
    }

    public Channel getChannel() {
      return Channel.this;
    }
  }
  
  /**
   * an OutputStream that expands written data to include the sequence number,
   * and writes to the channel's output stream. These should be wrapped in
   * BufferedOutputStreams before being returned.
   */
  private class MuxedOutputStream extends OutputStream {
    private int sequenceNumber;
    
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
