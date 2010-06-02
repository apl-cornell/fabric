package fabric.net;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.Stack;
import java.util.logging.Level;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.exceptions.InternalError;
import fabric.messages.Message;
import fabric.worker.transaction.TransactionManager;

/**
 * Abstracts a message-handler thread for processing messages. This implements
 * FabricThread for performance reasons. It will be calling into the in-process
 * worker to perform access control.
 */
public abstract class AbstractMessageHandlerThread<Session extends AbstractMessageHandlerThread.SessionAttributes, MessageHandlerThread extends AbstractMessageHandlerThread<Session, MessageHandlerThread>>
    extends FabricThread.Impl implements MessageHandler {
  private final String threadName;

  // The I/O streams for communicating with the worker.
  private DataInputStream in;
  private DataOutputStream out;

  // The I/O channels for interfacing with the channel multiplexer.
  private SourceChannel source;
  private SinkChannel sink;

  /**
   * The pool that we are a part of.
   */
  protected final Pool<MessageHandlerThread> pool;

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

  protected AbstractMessageHandlerThread(String name,
      Pool<MessageHandlerThread> pool) {
    super(name + " -- initializing");
    this.threadName = name;
    this.pool = pool;
    this.recycle = false;
    this.readyToAssociate = false;
  }

  /**
   * The main execution body of the message-handler thread. This is a wrapper
   * for <code>run_</code> to ensure that all exceptions are properly handled
   * and that the <code>Node</code> is properly notified when this handler is
   * finished with a worker.
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

      try {
        run_();
      } catch (ClosedByInterruptException e) {
      } catch (EOFException e) {
      } catch (final IOException e) {
        Logging.NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
            "Connection closed prematurely", e);
      }

      // Return this handler to the pool.
      if (pool.handlerDone((MessageHandlerThread) this)) break;
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
   * The execution body of the message-handler thread.
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

  public final Session getSession() {
    return session;
  }

  /**
   * Initialises this message handler to handle the given session and signals
   * this thread to start processing the worker's requests. This is invoked by a
   * <code>ConnectionHandler.CallbackHandler</code> to hand off a worker request
   * to this handler.
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

    // Get the message-handler thread running.
    notifyAll();
  }

  private void initPipes() {
    try {
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

  /**
   * Cleans up all connection-specific state to ready this message handler for
   * another connection. This is invoked prior to returning this handler to a
   * thread pool.
   */
  private void cleanup() {
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

    // try {
    // synchronized (source) {
    // source.close();
    // }
    // } catch (IOException e) {
    // }
    source = null;

    try {
      synchronized (sink) {
        sink.close();
      }
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

  public static abstract class SessionAttributes {
    public void endSession() {
    }
  }

  protected static interface Factory<MessageHandlerThread extends AbstractMessageHandlerThread<?, MessageHandlerThread>> {
    MessageHandlerThread createMessageHandler(Pool<MessageHandlerThread> pool);
  }

  public static final class Pool<MessageHandlerThread extends AbstractMessageHandlerThread<?, MessageHandlerThread>> {
    private final Stack<MessageHandlerThread> pool;
    private final int maxSize;
    private final Factory<MessageHandlerThread> factory;
    private boolean destroyed;

    public Pool(int size, Factory<MessageHandlerThread> threadFactory) {
      this.pool = new Stack<MessageHandlerThread>();
      this.maxSize = size;
      this.factory = threadFactory;
      this.destroyed = false;
    }

    /**
     * @return an available <code>MessageHandlerThread</code> object.
     */
    public synchronized MessageHandlerThread get() {
      if (pool.isEmpty()) return factory.createMessageHandler(this);
      return pool.pop();
    }

    /**
     * Invoked by a <code>MessageHandlerThread</code> object to notify the pool
     * that it is done processing a message.
     * 
     * @return true iff the handler thread should kill itself.
     */
    public synchronized boolean handlerDone(MessageHandlerThread handler) {
      handler.cleanup();

      // Add to the thread pool if there is room.
      if (pool.size() == maxSize) return true;

      if (!destroyed) pool.push(handler);
      return destroyed;
    }

    public synchronized void shutdown() {
      destroyed = true;
      while (!pool.empty())
        pool.pop().interrupt();
    }
  }
}
