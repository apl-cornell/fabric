package fabric.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fabric.common.net.naming.SocketAddress;

/**
 * An acceptor encapsulates a single java.net.ServerSocket.  It functions as a
 * producer-consumer of SubSockets (via the connected(s) and s accept() methods)
 * and runs a thread in the background which awaits incoming connections and
 * spawns new ServerChannels to handle them.  
 * 
 * @author mdgeorge
 */
class Acceptor extends Thread {
  //
  // The implementation of Acceptor mirrors that of Channel: while a channel
  // wraps a Socket and dispatches bytes to multiple SubSockets, an Acceptor
  // wraps a ServerSocket and dispatches connections to multiple
  // SubServerSockets.
  //
  // The implementation correspondence is as follows:
  // Acceptor               is similar to      Channel
  // A.ConnectionQueue      is similar to      C.Connection
  //
  // Acceptors are still a bit simpler since Channels need to both send and
  // receive, while Acceptors only receive.

  private final ServerSocket                 socket;
  private final SubServerSocketFactory       factory;
  private final Map<String, ConnectionQueue> queues;

  public Acceptor(SubServerSocketFactory factory, SocketAddress addr) throws IOException {
    super("connection dispatcher for " + addr);
    
    this.socket  = new ServerSocket(addr.getPort(), 0, addr.getAddress());
    this.factory = factory;
    this.queues  = new HashMap<String, ConnectionQueue> ();
    start();
  }
  
  private synchronized ConnectionQueue getReceiver(String name) {
    ConnectionQueue result = queues.get(name);
    if (result == null) {
      result = new ConnectionQueue(name);
      queues.put(name, result);
    }
    return result;
  }
  

  /**
   * Contains all of the state for a listening SubSocket.
   */
  public class ConnectionQueue {
    final public String name;
    final public BlockingQueue<SubSocket> connections;
    
    public ConnectionQueue(String name) {
      this.name = name;
      this.connections = new ArrayBlockingQueue<SubSocket>(50);
    }
    
    public void close() {
      throw new NotImplementedException();
    }
  }

  /**
   * Listens for incoming TCP connections and spawns new ServerChannels to deal
   * with them.
   */
  @Override
  public void run() {
    throw new NotImplementedException();
    /*
      try {
        while (true) { new ServerChannel(factory.receive(socket.accept()), Acceptor.this); }
      } catch (IOException exc) {
        throw new NotImplementedException();
      }
     */
  }
}

