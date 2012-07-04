package fabnfs;

import fabric.worker.AbortException;
import fabric.worker.Worker;

class NFSOperation extends java.lang.Object implements Runnable, NFSConsts,
    RPCConsts {
  MTList input;
  NFSHandler handler;

  NFSOperation(MTList inputq, NFSHandler nfsh) {
    input = inputq;
    handler = nfsh;
  }

  public void run() {
    while (true) {
      final NFSItem next = (NFSItem) input.Get();
      try {
        // System.out.print("thread waiting on nfs item queue\n");
        // System.out.println("NFSOperation got item from queue xid=" +
        // next.xid);
  
        // wrapping this up in a transaction of its own
        Worker.runInTopLevelTransaction(new Worker.Code<Void>() {
          public Void run() {
            handler.Run(next.port, next.xid, next.procedure, next.packet);
            return null;
          }  
        }, false);
      } catch (AbortException e) {
         System.out.println("Aborted NFS item " + next);
      }
    }
  }
};
