package fabric.worker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import fabric.worker.transaction.TransactionManager;
import fabric.common.FabricThread;

/**
 * The thread in which the Fabric program executes. This is here to ensure that
 * the program executes in a FabricThread.
 */
public final class MainThread extends Thread implements FabricThread {
  private TransactionManager tm;
  private final Options opts;
  private final Method main;
  private final Object args;
  private Throwable uncaughtException;

  private MainThread(Options opts, Method main, Object args) {
    super("Main worker application");
    this.tm = null;
    this.opts = opts;
    this.main = main;
    this.args = args;
    this.uncaughtException = null;

    setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void uncaughtException(Thread t, Throwable e) {
        uncaughtException = e;
      }
    });
  }

  @Override
  public void run() {
    try {
      main.invoke(null, args);
      
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      // Trim the stack trace to omit stuff dealing with the worker framework.
      List<StackTraceElement> trace = new ArrayList<StackTraceElement>();
      for (StackTraceElement elt : cause.getStackTrace())
        trace.add(elt);

      for (ListIterator<StackTraceElement> it =
          trace.listIterator(trace.size()); it.hasPrevious();) {
        StackTraceElement elt = it.previous();
        if (elt.getClassName().equals(opts.app[0] + "$_Impl")) break;
        it.remove();
      }

      StackTraceElement[] traceArray = new StackTraceElement[trace.size()];
      cause.setStackTrace(trace.toArray(traceArray));
      uncaughtException = cause;
    } catch (Throwable t) {
      uncaughtException = t;
    }
  }

  public static void invoke(Options opts, Method main, Object args)
      throws Throwable {
    MainThread thread = new MainThread(opts, main, args);
    thread.start();
    while (true) {
      try {
        thread.join();
        break;
      } catch (InterruptedException e) {
      }
    }

    Throwable uncaught = thread.uncaughtException;
    if (uncaught != null) throw uncaught;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.FabricThread#getTransactionManager()
   */
  public final TransactionManager getTransactionManager() {
    return tm;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.FabricThread#setTransactionManager(fabric.worker.transaction.TransactionManager)
   */
  public final void setTransactionManager(TransactionManager tm) {
    this.tm = tm;
  }
}
