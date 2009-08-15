/**
 * 
 */
package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.net.ServerSocketFactory;

/**
 * Listens for incoming connections and creates Channels corresponding to them.
 * 
 * @author mdgeorge
 */
class Acceptor {
  private ServerSocket             socket;
  private BlockingQueue<SubSocket> connections;
  private Listener                 thread;
  
  public Acceptor(ServerSocketFactory factory, InetSocketAddress addr, int backlog) throws IOException {
    this.socket = factory.createServerSocket(addr.getPort(), backlog, addr.getAddress());
    this.connections = new ArrayBlockingQueue<SubSocket>(backlog);
    this.thread = new Listener();
    this.thread.start();
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
  
  public void close() {
    throw new NotImplementedException();
  }

  private class Listener extends Thread {
    @Override
    public void run() {
      try {
        while (true) {
          Socket s = socket.accept();
          // create a channel for s, referring to this
          throw new NotImplementedException();
        }
      } catch (IOException exc) {
        throw new NotImplementedException();
      }
    }
  }
}