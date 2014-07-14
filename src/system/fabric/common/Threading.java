package fabric.common;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import fabric.common.exceptions.InternalError;

/**
 * This is the home for all things threading.
 *
 * @author mdgeorge
 */
public class Threading {
  private static ExecutorService pool = newCachedThreadPool();

  /**
   * create an ExecutorService that creates threads on demand and caches them
   * for one minute.
   */
  private static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), new FabricThreadFactory()) {
      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
          try {
            ((Future<?>) r).get();
          } catch (CancellationException ce) {
            t = ce;
          } catch (ExecutionException ee) {
            t = ee.getCause();
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // ignore/reset
          }
        }

        if (t != null)
          Logging.MISC_LOGGER.log(Level.SEVERE, "Thread exited with exception",
              t);
      }
    };
  }

  /**
   * Get the thread pool.
   *
   * @throws InternalError
   *           if the thread pool is not initialized.
   */
  public static ExecutorService getPool() throws InternalError {
    if (Threading.pool == null)
      throw new InternalError("Threading not initialized");

    return Threading.pool;
  }

  /**
   * A ThreadFactory that creates FabricThread.Impls for the thread pool.
   */
  private static class FabricThreadFactory implements ThreadFactory {
    private int nextID = 1;
    private static ThreadGroup threadGroup = new ThreadGroup("Thread pool");

    @Override
    public synchronized Thread newThread(Runnable r) {
      return new FabricThread.Impl(threadGroup, r, "Thread-pool thread "
          + (nextID++));
    }
  }

  /**
   * Convenience class for creating runnables that set the name of the thread.
   * Subclasses should override runImpl instead of run. For example: <code>
   * Runnable r = new NamedRunnable("thread for eating cookies") {
   *   void runImpl() {
   *     // eat cookies
   *   }
   * });
   * </code>
   *
   * @author mdgeorge
   */
  public static abstract class NamedRunnable implements Runnable {
    private String name;

    private static final boolean SET_THREAD_NAME = false;

    public NamedRunnable(String name) {
      this.name = name;
    }

    @Override
    public final void run() {
      Thread current = null;
      String oldName = null;
      if (SET_THREAD_NAME) {
        current = Thread.currentThread();
        oldName = current.getName();
        current.setName(this.name);
      }

      try {
        runImpl();
      } catch (RuntimeException e) {
        e.printStackTrace();
      } catch (Error e) {
        e.printStackTrace();
      }

      if (SET_THREAD_NAME) current.setName(oldName);
    }

    protected abstract void runImpl();
  }

  /**
   * Convenience class for creating Callables that set the name of the thread.
   * NamedCallable is to Callable as NamedRunnable (above) is to Runnable. For
   * example: <code>
   * Callable<Cookie> r = new NamedCallable("thread for baking cookies") {
   *   Cookie callImpl() {
   *     // compute ingredients
   *     return new Cookie(ingredients);
   *   }
   * });
   * </code>
   *
   * @author mdgeorge
   */
  public static abstract class NamedCallable<V> implements Callable<V> {
    private String name;

    public NamedCallable(String name) {
      this.name = name;
    }

    @Override
    public final V call() {
      Thread current = Thread.currentThread();
      String oldName = current.getName();
      current.setName(this.name);
      V result = callImpl();
      current.setName(oldName);
      return result;
    }

    protected abstract V callImpl();
  }
}
