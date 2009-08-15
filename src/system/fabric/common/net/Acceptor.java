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
 * A channel encapsulates a single 
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
  
  public SocketAddress getAddress() {
    return socket.getLocalSocketAddress();
  }
  
  public void connected(SubSocket s) throws IOException {
    if (!connections.offer(s))
      throw new IOException("too many waiting connections");
  }

  public SubSocket accept() throws IOException {
    try {
      return connections.take();
    } catch(InterruptedException e) {
      throw new IOException("Interrupted while waiting for a connection", e);
    }
  }
  
  public void close() throws IOException {
    // note that this will kill off the Listener thread as well.
    socket.close();
    
    // these are connections that have been initiated but not handed accepted
    for (SubSocket s : connections) {
      s.close();
    }
  }

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