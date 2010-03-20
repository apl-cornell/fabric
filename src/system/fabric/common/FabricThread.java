package fabric.common;

import fabric.worker.transaction.TransactionManager;

public interface FabricThread {
  TransactionManager getTransactionManager();

  void setTransactionManager(TransactionManager tm);

  public static abstract class AbstractImpl extends Thread implements
      FabricThread {
    private TransactionManager tm;

    protected AbstractImpl() {
    }

    protected AbstractImpl(String name) {
      super(name);
    }

    public TransactionManager getTransactionManager() {
      return tm;
    }

    public void setTransactionManager(TransactionManager tm) {
      this.tm = tm;
    }
  }
}
