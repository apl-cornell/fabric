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

    /**
     * XXX Really gross HACK to make actual transaction commit times visible to
     * the application. This allows us to measure end-to-end application-level
     * transaction latency.
     */
    public long commitTime;

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

    public Impl(ThreadGroup group, String name) {
      super(group, name);
      setDaemon(true);
    }

    public Impl(ThreadGroup group, Runnable target) {
      super(group, target);
      setDaemon(true);
    }

    public Impl(ThreadGroup group, Runnable target, String name) {
      super(group, target, name);
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
