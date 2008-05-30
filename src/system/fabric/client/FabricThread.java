package fabric.client;

import fabric.client.transaction.TransactionManager;

public interface FabricThread {
  TransactionManager getTransactionManager();
  void setTransactionManager(TransactionManager tm);
}
