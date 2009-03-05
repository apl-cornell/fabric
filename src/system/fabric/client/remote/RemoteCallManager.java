package fabric.client.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import fabric.client.Client;

/**
 * A thread that handles incoming requests from other clients.
 */
public class RemoteCallManager extends Thread {
  private final int POOL_SIZE = 10;
  private final int MAX_CONNECTS = 25;

  volatile boolean shuttingDown;

  /**
   * The number of additional connections the node can handle.
   */
  private int availConns;

  /**
   * The thread pool.
   */
  private final Stack<Worker> pool;

  /**
   * The set of running (active and idle) worker threads.
   */
  final Set<Worker> workers;

  public RemoteCallManager() {
    super("remote call manager");
    this.shuttingDown = false;
    this.workers = new HashSet<Worker>();

    // Set up the thread pool.
    this.availConns = MAX_CONNECTS;
    this.pool = new Stack<Worker>();
    for (int i = 0; i < POOL_SIZE; i++)
      pool.push(new Worker(this));
  }

  @Override
  public void run() {
    try {
      // Start listening.
      ServerSocket server = new ServerSocket(Client.getClient().port);
      server.setSoTimeout(1000);

      // The main server loop.
      while (!shuttingDown) {
        try {
          // Accept a connection and give it to a worker thread.
          Socket client = server.accept();
          getWorker().handle(client);
        } catch (SocketTimeoutException e) {
          continue;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void shutdown() {
    this.shuttingDown = true;
    synchronized (workers) {
      for (Worker worker : workers)
        worker.interrupt();
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
    if (shuttingDown || pool.size() == POOL_SIZE) return true;
    worker.cleanup();
    pool.push(worker);
    return false;
  }
}
