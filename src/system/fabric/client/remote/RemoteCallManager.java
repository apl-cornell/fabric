package fabric.client.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import javax.net.ssl.SSLSocketFactory;

import fabric.client.Client;
import fabric.client.Options;

/**
 * A thread that handles incoming requests from other clients.
 */
public class RemoteCallManager extends Thread {

  final Options opts;

  /**
   * The number of additional connections the node can handle.
   */
  private int availConns;

  /**
   * The thread pool.
   */
  private final Stack<Worker> pool;

  public RemoteCallManager(Options opts) {
    this.opts = opts;

    // Set up the thread pool.
    this.availConns = opts.maxConnect;
    this.pool = new Stack<Worker>();
    for (int i = 0; i < opts.threadPool; i++)
      pool.push(new Worker(this));
  }

  /**
   * Given the host name for a client, returns its corresponding
   * <code>SSLSocketFactory</code>.
   */
  public SSLSocketFactory getSSLSocketFactory(String clientName) {
    // We ought to be able to support virtual hosting of client nodes.
    // For now, just have a single host.
    Client client = Client.getClient();
    if (client.name.equals(clientName)) return client.sslSocketFactory;
    return null;
  }

  @Override
  public void run() {
    try {
      // Start listening.
      ServerSocket server = new ServerSocket(opts.port);

      // The main server loop.
      while (true) {
        // Accept a connection and give it to a worker thread.
        Worker worker = getWorker();
        Socket client = server.accept();

        worker.handle(client);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns an available <code>Worker</code> object.
   */
  private synchronized Worker getWorker() {
    // If we can't handle more connections, block until an existing connection
    // is done.
    while (availConns == 0) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        continue;
      }
    }

    availConns--;

    if (pool.isEmpty()) return new Worker(this);
    return pool.pop();
  }

  /**
   * Invoked by a <code>Worker</code> to notify this node that it is finished
   * serving a client.
   * 
   * @return true iff the worker should kill itself.
   */
  protected synchronized boolean workerDone(Worker worker) {
    availConns++;
    this.notify();

    // Clean up the worker and add it to the thread pool if there's room.
    if (pool.size() == opts.threadPool) return true;
    worker.cleanup();
    pool.push(worker);
    return false;
  }
}
