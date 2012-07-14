package fabric.common;

import fabric.worker.transaction.TransactionManager;

/**
 * A FabricThread is a thread that has an associated TransactionManager. This
 * class exists because java.lang.ThreadLocal has a poor implementation that is
 * too slow. FabricThread is an interface because fabric code may extend a class
 * which is itself a subclass of Thread. In this case, the class cannot also be
 * made a subclass of FabricThread (if it were a class) without rewriting the
 * parent class.
 */
public interface FabricThread {
  TransactionManager getTransactionManager();

  void setTransactionManager(TransactionManager tm);

  public static class Impl extends Thread implements FabricThread {
    private TransactionManager tm;

    public Impl() {
      setDaemon(true);
    }

    public Impl(String name) {
      super(name);
      setDaemon(true);
    }

    public Impl(Runnable target) {
      super(target);
      setDaemon(true);
    }

    public Impl(Runnable target, String name) {
      super(target, name);
      setDaemon(true);
    }

    @Override
    public TransactionManager getTransactionManager() {
      return this.tm;
    }

    @Override
    public void setTransactionManager(TransactionManager tm) {
      this.tm = tm;
    }
  }
}
