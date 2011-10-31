package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.worker.transaction.ReadMapEntry;
import fabric.lang.Object._Impl;

public class FabricSoftRef extends SoftReference<_Impl> {
  
  private static final ReferenceQueue<_Impl> queue = new ReferenceQueue<_Impl>();
  
  static {
    new RefCollector().start();
  }
  
  public Store store;
  public long onum;
  public ReadMapEntry readMapEntry;

  public FabricSoftRef(Store store, long onum, _Impl impl) {
    super(impl, queue);
    this.store = store;
    this.onum = onum;
  }
  
  /**
   * Evicts the Impl associated with this soft reference from the worker's cache.
   * 
   * @return true if the impl was found in cache.
   */
  public boolean evict() {
    if (store instanceof LocalStore) {
      throw new InternalError("evicting local store object");
    }
    
    clear();
    boolean result = store.notifyEvict(onum);
    if (readMapEntry != null && readMapEntry.depin()) readMapEntry = null;
    return result;
  }
  
  public void readMapEntry(ReadMapEntry readMapEntry) {
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
          ref.store.notifyEvict(ref.onum);
          if (ref.readMapEntry.depin()) ref.readMapEntry = null;
        } catch (InterruptedException e) {}
      }
    }
  }

}
