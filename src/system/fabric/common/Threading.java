package fabric.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import fabric.common.exceptions.InternalError;

/**
 * This is the home for all things threading.
 *
 * @author mdgeorge
 */
public class Threading {
  private static ExecutorService pool = null;
  
  /**
   * Initialize the thread pool.
   * 
   * @param poolSize the size of the thread pool.  If poolSize < 1, then the
   *                 thread pool will be allowed to grow as needed.
   * @throws InternalError if the pool is alread initialized.                   
   */
  public static void init(int poolSize) throws InternalError {
    if (Threading.pool != null) 
      throw new InternalError("Threading initialized twice");
    
    ThreadFactory factory = new FabricThreadFactory();
    
    if (poolSize < 1)
      Threading.pool = Executors.newCachedThreadPool(factory);
    else
      Threading.pool = Executors.newFixedThreadPool(poolSize, factory);
  }
  
  
  /**
   * Get the thread pool.
   * 
   * @throws InternalError if the thread pool is not initialized.
   */
  public static ExecutorService getPool() throws InternalError {
    if (Threading.pool == null)
      throw new InternalError("Threading not initialized");
    
    return Threading.pool;
  }
  
  
  /**
   * A ThreadFactory that creats FabricThread.Impls.
   */
  private static class FabricThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
      return new FabricThread.Impl(r, "Idle thread");
    }
  }

  
  /** Convenience class for creating runnables that set the name of the thread.
   * Subclasses should override runImpl instead of run.  For example:
   * 
   * <code>
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
    
    public NamedRunnable(String name) {
      this.name = name;
    }
    
    public final void run() {
      Thread current = Thread.currentThread();
      String oldName = current.getName();
      current.setName(this.name);
      runImpl();
      current.setName(oldName);
    }
    
    protected abstract void runImpl();
  }
  
  
  /** Convenience class for creating Callables that set the name of the thread.
   * NamedCallable is to Callable as NamedRunnable (above) is to Runnable.  For
   * example:
   * 
   * <code>
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
