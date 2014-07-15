package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.Logging;
import fabric.lang.Object._Impl;
import fabric.worker.transaction.ReadMap;

public class FabricSoftRef extends SoftReference<_Impl> {

  private static final ReferenceQueue<_Impl> queue = new ReferenceQueue<>();

  static {
    new RefCollector().start();
  }

  public final Store store;
  public long onum;
  private ReadMap.Entry readMapEntry;

  public FabricSoftRef(Store store, long onum, _Impl impl) {
    super(impl, queue);
    this.store = store;
    this.onum = onum;
  }

  /**
   * Evicts the _Impl associated with this soft reference from the worker's
   * cache.
   */
  public void evict() {
    store.evict(onum);
  }

  /**
   * Decrements the associated ReadMapEntry's pin count and does garbage
   * collection if possible.
   */
  public void depin() {
    if (readMapEntry != null && readMapEntry.depin()) readMapEntry = null;
  }

  public ReadMap.Entry readMapEntry() {
    return readMapEntry;
  }

  public void readMapEntry(ReadMap.Entry readMapEntry) {
    this.readMapEntry = readMapEntry;
  }

  private static class RefCollector extends Thread {
    RefCollector() {
      super("Reference collector");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        try {
          FabricSoftRef ref = (FabricSoftRef) queue.remove();
          ref.depin();
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }

}
