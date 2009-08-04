package fabric.common;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.client.transaction.TransactionManager;
import fabric.common.exceptions.InternalError;
import fabric.messages.Message;

/**
 * Abstracts a worker thread for processing messages. This implements
 * FabricThread for performance reasons. It will be calling into the in-process
 * client to perform access control.
 */
public abstract class AbstractWorkerThread<Session extends AbstractWorkerThread.SessionAttributes, Worker extends AbstractWorkerThread<Session, Worker>>
    extends FabricThread.AbstractImpl implements MessageHandler {
  private final String threadName;

  // The I/O streams for communicating with the client.
  private DataInputStream in;
  private DataOutputStream out;

  // The I/O channels for interfacing with the channel multiplexer.
  private SourceChannel source;
  private SinkChannel sink;

  /**
   * The pool that we are a part of.
   */
  protected final Pool<Worker> pool;

  /**
   * The session for which requests are being handled.
   */
  protected Session session;

  /**
   * Whether this thread should be recycled back in the thread pool.
   */
  private boolean recycle;

  /**
   * Whether this thread is ready to be associated with a session.
   */
  private boolean readyToAssociate;

  protected AbstractWorkerThread(String name, Pool<Worker> pool) {
    super(name + " -- initializing");
    this.threadName = name;
    this.pool = pool;
    this.recycle = false;
    this.readyToAssociate = false;
  }

  /**
   * The main execution body of the worker thread. This is a wrapper for
   * <code>run_</code> to ensure that all exceptions are properly handled and
   * that the <code>Node</code> is properly notified when this worker is
   * finished with a client.
   */
  @SuppressWarnings("unchecked")
  @Override
  public final synchronized void run() {
    while (!shuttingDown()) {
      Thread.currentThread().setName(threadName + " -- idle");

      // Notify that we are ready to be associated with a session.
      readyToAssociate = true;
      notifyAll();

      // Wait for the node to signal this thread (done via a call to
      // associateSession()).
      try {
        wait();
      } catch (InterruptedException e) {
        recycle = false;
        continue;
      }

      Thread.currentThread().setName(threadName + " -- active");

      resetStats();

      try {
        run_();
      } catch (ClosedByInterruptException e) {
      } catch (final IOException e) {
        getLogger().log(Level.WARNING, "Connection closed prematurely", e);
      }

      logStats();

      // Return this worker to the pool.
      if (pool.workerDone((Worker) this)) break;
    }

    TransactionManager.getInstance().deregisterThread(this);
  }

  /**
   * Determines whether the thread should shut down.
   */
  protected boolean shuttingDown() {
    return false;
  }

  /**
   * Returns a Logger to use for debug logging.
   */
  protected abstract Logger getLogger();

  /**
   * Resets any bookkeeping information for debugging/monitoring purposes.
   */
  protected void resetStats() {
  }

  /**
   * Commits any statistics that may have been logged.
   */
  protected void logStats() {
  }

  /**
   * The execution body of the worker thread.
   */
  private final void run_() throws IOException {
    while (true) {
      try {
        Message.receive(in, out, this);
      } catch (ClosedByInterruptException e) {
        if (!recycle) throw e;
        recycle = false;
        break;
      }
    }
  }

  /**
   * Initialises this worker to handle the given session and signals this thread
   * to start processing the client's requests. This is invoked by a
   * <code>ConnectionHandler.CallbackHandler</code> to hand off a client request
   * to this worker.
   */
  public final synchronized void associateSession(Session session) {
    while (!readyToAssociate) {
      try {
        wait();
      } catch (InterruptedException e) {
        continue;
      }
    }

    this.session = session;
    initPipes();
    readyToAssociate = false;

    // Get the worker thread running.
    notifyAll();
  }

  protected abstract void diaginit();

  private void initPipes() {
    try {
      diaginit();
      Pipe inbound = Pipe.open();
      this.sink = inbound.sink();

      SourceChannel inboundSource = inbound.source();
      inboundSource.configureBlocking(true);
      this.in =
          new DataInputStream(new BufferedInputStream(Channels
              .newInputStream(inboundSource)));

      Pipe outbound = Pipe.open();
      this.source = outbound.source();

      SinkChannel outboundSink = outbound.sink();
      outboundSink.configureBlocking(true);
      this.out =
          new DataOutputStream(new BufferedOutputStream(Channels
              .newOutputStream(outboundSink)));
    } catch (IOException e) {
      System.out.println("Comm man locals: "
          + Util.numCommManThreadLocals.value);
      System.out.println("AWT client creates: "
          + Util.numAWTClientPipeCreates.value);
      System.out.println("AWT client cleans:  "
          + Util.numAWTClientPipeCleanups.value);
      System.out.println("AWT core   creates: "
          + Util.numAWTCorePipeCreates.value);
      System.out.println("AWT core   cleans:  "
          + Util.numAWTCorePipeCleanups.value);
      throw new InternalError(e);
    }
  }

  /**
   * Interrupts this thread from service and puts it back in its thread pool.
   */
  public final void recycle() {
    recycle = true;
    interrupt();
  }

  protected abstract void diagcleanup();

  /**
   * Cleans up all connection-specific state to ready this worker for another
   * connection. This is invoked prior to returning this worker to a thread
   * pool.
   */
  private void cleanup() {
    diagcleanup();
    session = null;

    try {
      in.close();
    } catch (IOException e) {
    }
    in = null;

    try {
      out.close();
    } catch (IOException e) {
    }
    out = null;

    try {
      source.close();
    } catch (IOException e) {
    }
    source = null;

    try {
      sink.close();
    } catch (IOException e) {
    }
    sink = null;
  }

  public SourceChannel source() {
    return source;
  }

  public SinkChannel sink() {
    return sink;
  }

  public static interface SessionAttributes {
  }

  protected static interface Factory<Worker extends AbstractWorkerThread<?, Worker>> {
    Worker createWorker(Pool<Worker> pool);
  }

  public static final class Pool<Worker extends AbstractWorkerThread<?, Worker>> {
    private final Stack<Worker> pool;
    private final int maxSize;
    private final Factory<Worker> factory;
    private boolean destroyed;

    public Pool(int size, Factory<Worker> threadFactory) {
      this.pool = new Stack<Worker>();
      this.maxSize = size;
      this.factory = threadFactory;
      this.destroyed = false;
    }

    /**
     * @return an available <code>Worker</code> object.
     */
    public synchronized Worker get() {
      if (pool.isEmpty()) return factory.createWorker(this);
      return pool.pop();
    }

    /**
     * Invoked by a <code>WorkerThread</code> object to notify the pool that it
     * is done processing a message.
     * 
     * @return true iff the worker thread should kill itself.
     */
    public synchronized boolean workerDone(Worker worker) {
      worker.cleanup();

      // Add to the thread pool if there is room.
      if (pool.size() == maxSize) return true;

      if (!destroyed) pool.push(worker);
      return destroyed;
    }

    public synchronized void shutdown() {
      destroyed = true;
      while (!pool.empty())
        pool.pop().interrupt();
    }
  }
}
