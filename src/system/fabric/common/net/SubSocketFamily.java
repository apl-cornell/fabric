package fabric.common.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.net.SocketFactory;

public class SubSocketFamily extends BaseSocketFamily {

  /**
   * A channel encapsulates a single java.net.ServerSocket.  It functions as a
   * producer-consumer of SubSockets (via the connected(s) and s accept() methods)
   * and runs a thread in the background which awaits incoming connections and
   * spawns new ServerChannels to handle them.  
   * 
   * @author mdgeorge
   */
  class Acceptor {
    private ServerSocket             socket;
    private BlockingQueue<SubSocket> connections;
    
    public Acceptor(SubServerSocketFactory factory, InetSocketAddress addr, int backlog) throws IOException {
      this.socket = factory.createSocketImpl(addr.getPort(), backlog, addr.getAddress());
      this.connections = new ArrayBlockingQueue<SubSocket>(backlog);
      new Listener().start();
    }
    
    /** return the local socket address of this acceptor */
    public SocketAddress getAddress() {
      return socket.getLocalSocketAddress();
    }
    
    /** Called by a ServerChannel when a new substream is connected */
    public void connected(SubSocket s) throws IOException {
      if (!connections.offer(s))
        throw new IOException("too many waiting connections");
    }

    /** block until a new substream connects, and then return it. */
    public SubSocket accept() throws IOException {
      try {
        return connections.take();
      } catch(InterruptedException e) {
        throw new IOException("Interrupted while waiting for a connection", e);
      }
    }
    
    /** release the resources associated with this Acceptor. */
    public void close() throws IOException {
      // note that this will kill off the Listener thread as well.
      socket.close();
      
      // these are connections that have been initiated but not handed accepted
      for (SubSocket s : connections) {
        s.close();
      }
    }

    /** A thread that listens for incoming TCP connections and spawns new
     * ServerChannels to deal with them.
     */
    private class Listener extends Thread {
      @Override
      public void run() {
        try {
    while (true) { new ServerChannel(socket.accept(), Acceptor.this); }
        } catch (IOException exc) {
    throw new NotImplementedException();
        }
      }
      
      public Listener() {
        super("connection acceptor for " + getAddress());
      }
    }
  }

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
    //  channel   close (sendClose()   method): 0  0
    //  subsocket close (sendClose(SN) method): SN 0
    //  subsocket send  (sendData      method): SN length data[len]
    //
    // any unrecognized or previously closed SN should create a new stream (thus
    // the subsocket close message should be the last sent by a subsocket).
    
    protected Channel(Socket s) throws IOException {
      this.sock = s;
      this.out  = new DataOutputStream(s.getOutputStream());
      this.in   = new DataInputStream(s.getInputStream());
      this.connections = new HashMap<Integer, Connection>();
      new Demuxer().start();
    }

    @Override public abstract String toString();

    /** called to create a Connection to an unknown sequence number */
    public abstract Connection accept(int sequence) throws IOException;

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
      out.writeInt(sequence);
      out.writeInt(data.length);
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

      /** this method is called by SubSocket.close(). */
      public void close() throws IOException {
        in.close();
        out.close();
        sendClose(sequenceNum);
      }
      
      /** this method called by recvClose in response to a close message */
      public void receiveClose() throws IOException {
        connections.remove(this);
        sink.close();
      }
      
      /** forward data to the reading thread */
      public void receiveData(byte[] b) throws IOException {
        sink.write(b);
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

  /**
   * Client channels are capable of making outgoing requests, but not of receiving
   * new incoming requests.  They are only associated with a remote address, and
   * have no distinguished local address.
   * 
   * @author mdgeorge
   */
  class ClientChannel extends Channel {
    private int nextSequenceNumber;
    
    public ClientChannel(Socket s) throws IOException {
      super(s);
      nextSequenceNumber = 0;
    }

    /** initiate a new substream */
    public Connection connect() throws IOException {
      return new Connection(nextSequenceNumber++); 
    }
    
    @Override
    public Connection accept(int sequence) throws IOException {
      throw new IOException("unexpected accept request on client channel");
    }

    @Override
    public String toString() {
      return "channel to " + sock.getRemoteSocketAddress();
    }
  }

  /**
   * A server channel is capable of receiving new incoming connections, but not
   * of making new outgoing connections.  It is associated both with a local
   * SocketAddress (IP + port) and a remote IP address.
   *  
   * @author mdgeorge
   */
  public class ServerChannel extends Channel {
    private final Acceptor acc;
    
    public ServerChannel(Socket sock, Acceptor acc) throws IOException {
      super(sock);
      this.acc = acc;
    }
    
    /** create a new subsocket for an incoming connection and notify the acceptor */
    @Override
    public Connection accept(int sequence) throws IOException {
      Connection conn = new Connection(sequence);
      acc.connected(new SubSocket(conn));
      return conn;
    }

    @Override
    public String toString() {
      return "channel from " + sock.getInetAddress() + " to " + acc.getAddress();
    }

  }

  /**
   * factory for creating SubServerSockets. This class decorates a
   * javax.net.ServerSocketFactory, which is used for instantiating the underlying
   * channels.
   * 
   * @author mdgeorge
   */
  public class SubServerSocketFactory implements _ServerSocketFactory<InetSocketAddress, Integer, SubServerSocket,SubServerSocketFactory,SubSocket,SubSocketFactory> {
    private final javax.net.ServerSocketFactory factory;
    
    /** Creates a new SubServerSocketFactory decorating the given
     * ServerSocketFactory.
     * 
     * @param factory the ServerSocketFactory that will be used to create the
     *        ServerSockets used to implement SubServerSockets returned by this
     */
    public SubServerSocketFactory(javax.net.ServerSocketFactory factory) {
      this.factory = factory;
    }
    
    /** @see javax.net.ServerSocketFactory#createServerSocket() */
    public SubServerSocket createServerSocket() {
      return new SubServerSocket(this);
    }
    
    /** @see javax.net.ServerSocketFactory#createServerSocket(int) */
    public SubServerSocket createServerSocket(Integer port) throws IOException {
      SubServerSocket result = new SubServerSocket(this);
      result.bind(port);
      return result;
    }

    /** @see javax.net.ServerSocketFactory#createServerSocket(int, int) */
    public SubServerSocket createServerSocket(Integer port, int backlog) throws IOException {
      SubServerSocket result = new SubServerSocket(this);
      result.bind(port, backlog);
      return result;
    }

    /** @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress) */
    public SubServerSocket createServerSocket(Integer port, int backlog, InetAddress ifAddress) throws IOException {
      SubServerSocket result = new SubServerSocket(this);
      result.bind(new InetSocketAddress(ifAddress, port), backlog);
      return result;
    }
    
    java.net.ServerSocket createSocketImpl(Integer port, int backlog, InetAddress ifAddress) throws IOException {
      return factory.createServerSocket(port, backlog, ifAddress);
    }
  }

  /**
   * Server-side multiplexed socket implementation. The API mirrors that of
   * java.net.ServerSocket.
   * 
   * @see java.net.ServerSocket
   * @author mdgeorge
   */
  public class SubServerSocket implements _ServerSocket<InetSocketAddress, Integer, SubServerSocket, SubServerSocketFactory, SubSocket, SubSocketFactory>{
    //////////////////////////////////////////////////////////////////////////////
    // public API                                                               //
    //////////////////////////////////////////////////////////////////////////////
    
    /** @see SubServerSocketFactory */
    protected SubServerSocket(SubServerSocketFactory factory) {
      this.state   = new Unbound(factory);
    }
    
    /** @see java.net.ServerSocket#accept() */
    public SubSocket accept() throws IOException {
      return state.accept();
    }
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
    public void bind(Integer port) throws IOException {
      bind(port, 50);
    }
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
    public void bind(Integer port, int backlog) throws IOException {
      bind(new InetSocketAddress(port), backlog);
    }
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
    public void bind(InetSocketAddress addr) throws IOException {
      bind(addr, 50);
    }
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
    public void bind(InetSocketAddress addr, int backlog) throws IOException {
      state.bind(addr, backlog);
    }
    
    /** @see java.net.ServerSocket#close() */
    public void close() throws IOException {
      state.close();
    }
    
    //////////////////////////////////////////////////////////////////////////////
    // State design pattern implementation                                      //
    //                                                                          //
    //                 bind                  close                              //
    //      unbound  --------->    bound    ------->  closed                    //
    //         |                     |                  |                       //
    //         +---------------------+------------------+---------------> error //
    //                                                       exception          //
    //////////////////////////////////////////////////////////////////////////////
    
    private State state;
    
    /**
     * default implementations of state methods - throws errors or returns default
     * values as appropriate.
     */
    private abstract class State {
      protected Exception cause = null;
      
      /** @see SubServerSocket#accept() */
      public SubSocket accept() throws IOException {
        throw new IOException("Cannot accept a connection because server socket " + this, cause);
      }
      
      /** @see SubServerSocket#bind(InetSocketAddress, int) */
      public void bind(InetSocketAddress address, int backlog) throws IOException {
        throw new IOException("Cannot bind to local address " + address + " because server socket " + this, cause);
      }
      
      /** @see SubServerSocket#close() */
      public void close() throws IOException {
        throw new IOException("Cannot close server socket because it " + this, cause);
      }
    }
    
    /**
     * implementation of state methods in the unbound state.
     */
    private final class Unbound extends State {
      @Override public String toString() { return "is unbound"; }

      private final SubServerSocketFactory factory;
      
      @Override
      public void bind(InetSocketAddress address, int backlog) throws IOException {
        try {
          Acceptor acceptor = new Acceptor(factory, address, backlog);
          state = new Bound(acceptor);
        } catch (final Exception exc) {
          IOException wrapped = new IOException("failed to bind to local address " + address, exc);
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
      final Acceptor acceptor;
      
      @Override public String toString() {
        return "is bound to " + acceptor.getAddress();
      }

      @Override
      public SubSocket accept() throws IOException {
        return acceptor.accept();
      }

      @Override
      public void close() throws IOException {
        try {
          acceptor.close();
          state = new Closed();
        } catch(final Exception exc) {
          IOException wrapped = new IOException("failed to close server socket", exc);
          state = new ErrorState(wrapped);
          throw wrapped;
        }
      }
      
      public Bound(Acceptor acceptor) {
        this.acceptor = acceptor;
      }
    }
    
    /**
     * implementation of state methods in the closed state.
     */
    private final class Closed extends State {
      @Override public String toString() { return "is closed"; }
    }
    
    /**
     * implementation of state methods in an error state
     */
    private final class ErrorState extends State {
      @Override public String toString() { return "has recieved an exception"; }
      
      public ErrorState(Exception exc) {
        super();
        this.cause = exc;
      }
    }
  }

  /**
   * A factory for creating SubSockets. The factory decorates a
   * javax.net.SocketFactory, which is used for creating the underlying channels.
   * 
   * @author mdgeorge
   */
  public class SubSocketFactory implements _SocketFactory<InetSocketAddress, Integer, SubServerSocket, SubServerSocketFactory, SubSocket, SubSocketFactory>{
    private final javax.net.SocketFactory     factory;
    private final Map<InetSocketAddress, ClientChannel> channels;
    
    /**
     * Create a new SubSocket factory that decorates the given SocketFactory.
     * Note that SubSockets created from different SubSocketFactories will not
     * attempt to share channels (as these channels may have different underlying
     * socket implementations).
     */ 
    public SubSocketFactory(SocketFactory factory) {
      this.factory  = factory;
      this.channels = new HashMap<InetSocketAddress, ClientChannel>();
    }
    
    /** @see javax.net.SocketFactory#createSocket() */
    public SubSocket createSocket() {
      return new SubSocket(this);
    }
    
    /** @see javax.net.SocketFactory#createSocket(String, int) */
    public SubSocket createSocket(String host, int port) throws IOException {
      return createSocket(new InetSocketAddress(host, port));
    }

    /** @see javax.net.SocketFactory#createSocket(InetAddress, int) */
    public SubSocket createSocket(InetAddress host, int port) throws IOException {
      return createSocket(new InetSocketAddress(host, port));
    }
    
    public SubSocket createSocket(InetSocketAddress addr) throws IOException {
      SubSocket result = createSocket();
      result.connect(addr);
      return result;
    }

    /**
     * return a channel associated with the given address, creating it if necessary.
     */
    synchronized ClientChannel getChannel(InetSocketAddress addr) throws IOException {
      ClientChannel result = channels.get(addr);
      if (null == result) {
        Socket sock = factory.createSocket(addr.getAddress(), addr.getPort());
        result = new ClientChannel(sock);
        channels.put(addr, result);
      }
      
      return result;
    }
  }
  /**
   * Client-side multiplexed socket implementation. The API mirrors that of
   * java.net.Socket. This class manages connection state, and provides a
   * front-end API.
   * 
   * @see java.net.Socket
   * @author mdgeorge
   */
  public class SubSocket implements _Socket<InetSocketAddress, Integer, SubServerSocket, SubServerSocketFactory, SubSocket, SubSocketFactory>{
    //////////////////////////////////////////////////////////////////////////////
    // public API                                                               //
    //////////////////////////////////////////////////////////////////////////////
    
    /** @see SubSocketFactory */
    protected SubSocket(SubSocketFactory factory) {
      this.state = new Unconnected(factory); 
    }
    
    /**
     * Create a connected SubSocket.  This is used internally by ServerChannels
     * for accepting incoming streams.
     */ 
    SubSocket(Channel.Connection conn) {
      this.state = new Connected(conn);
    }

    /** @see java.net.Socket#close() */
    public final void close() throws IOException {
      state.close();
    }

    /** @see java.net.Socket#connect(SocketAddress) */
    public final void connect(InetSocketAddress addr) throws IOException {
      state.connect(addr);
    }

    /** @see java.net.Socket#getOutputStream() */
    public final OutputStream getOutputStream() throws IOException {
      return state.getOutputStream();
    }
    
    /** @see java.net.Socket#getInputStream() */
    public final InputStream getInputStream() throws IOException {
      return state.getInputStream();
    }
    
    //////////////////////////////////////////////////////////////////////////////
    // State design pattern implementation                                      //
    //                                                                          //
    //                connect                close                              //
    //  unconnected  --------->  connected  ------->  closed                    //
    //       |                       |                  |                       //
    //       +-----------------------+------------------+---------------> error //
    //                                                       exception          //
    //////////////////////////////////////////////////////////////////////////////
    
    private State state;
    
    /**
     * default implementations of state methods - throws errors or returns default
     * values as appropriate.
     */
    protected abstract class State {
      protected Exception cause = null;
      
      public void close() throws IOException {
        throw new IOException("Cannot close socket: socket " + this, cause);
      }
      
      public void connect(InetSocketAddress addr) throws IOException {
        throw new IOException("Cannot connect: socket " + this, cause);
      }

      public InputStream getInputStream() throws IOException {
        throw new IOException("Cannot get an input stream: socket " + this, cause);
      }
      
      public OutputStream getOutputStream() throws IOException {
        throw new IOException("Cannot get an output stream: socket " + this, cause);
      }
    }

    /**
     * implementation of methods in the Unconnected state
     */
    protected final class Unconnected extends State {
      private final SubSocketFactory factory;
      
      @Override public String toString() { return "is unconnected"; }
      
      @Override
      public void connect(InetSocketAddress addr) throws IOException {
        try {
          Channel.Connection conn = factory.getChannel(addr).connect(); 
          state = new Connected(conn);
        } catch (final Exception exc) {
          IOException wrapped = new IOException("failed to connect to " + addr, exc);
          state = new ErrorState(wrapped);
          throw wrapped;
        }
      }
      
      public Unconnected(SubSocketFactory factory) {
        this.factory = factory;
      }
    }

    /**
     * implementation of methods in the Connected(channel) state
     */
    protected final class Connected extends State {
      final Channel.Connection   conn;
      
      @Override
      public String toString() {
        return "is connected (" + conn.toString() + ")";
      }
      
      @Override
      public void close() throws IOException {
        try {
          conn.close();
          state = new Closed();
        } catch (final Exception exc) {
          IOException wrapped = new IOException("failed to close connection", exc);
          state = new ErrorState(wrapped);
          throw wrapped;
        }
      }
      
      @Override
      public InputStream getInputStream() {
        return conn.in;
      }

      @Override
      public OutputStream getOutputStream() {
        return conn.out;
      }

      public Connected(Channel.Connection conn) {
        this.conn = conn;
      }
    }

    /**
     * implementation of methods in the Closed state
     */
    protected final class Closed extends State {
      @Override public String toString() { return "is closed"; }
    }
    
    /**
     * implementations of methods in the Error state
     */
    protected final class ErrorState extends State {
      @Override public String toString() { return "has recieved an exception"; }
      
      public ErrorState(Exception exc) {
        super();
        cause = exc;
      }
    }
  }

}

/*
** vim: ts=2 sw=2 cindent et
*/
