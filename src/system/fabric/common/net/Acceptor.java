/**
 * 
 */
package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.net.ServerSocketFactory;

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
  
  public Acceptor(ServerSocketFactory factory, InetSocketAddress addr, int backlog) throws IOException {
    this.socket = factory.createServerSocket(addr.getPort(), backlog, addr.getAddress());
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
  
  /** release the resouces associated with this Acceptor. */
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