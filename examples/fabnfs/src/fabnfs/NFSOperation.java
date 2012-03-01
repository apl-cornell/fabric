package fabnfs;

class NFSOperation extends java.lang.Object implements Runnable, NFSConsts,
    RPCConsts {
  MTList input;
  NFSHandler handler;

  NFSOperation(MTList inputq, NFSHandler nfsh) {
    input = inputq;
    handler = nfsh;
  }

  public void run() {
    for (;;) {
      // System.out.print("thread waiting on nfs item queue\n");
      NFSItem next = (NFSItem) input.Get();
      // System.out.println("NFSOperation got item from queue xid=" +
      // next.xid);

      // wrapping this up in a transaction of its own
      boolean commit = true;
      fabric.worker.transaction.TransactionManager.getInstance()
          .startTransaction();
      try {
        handler.Run(next.port, next.xid, next.procedure, next.packet);
      } catch (final Throwable t) {
        commit = false;
        throw new fabric.worker.AbortException(t);
      } finally {
        if (commit) {
          fabric.worker.transaction.TransactionManager.getInstance()
              .commitTransaction();
        } else {
          fabric.worker.transaction.TransactionManager.getInstance()
              .abortTransaction();
        }
      }
    }
  }
};
