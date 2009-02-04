package fabric.client;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.client.transaction.ReadMapEntry;
import fabric.lang.Object.$Impl;

public class FabricSoftRef extends SoftReference<$Impl> {
  
  private static final ReferenceQueue<$Impl> queue = new ReferenceQueue<$Impl>();
  private static final RefCollector collector;
  
  static {
    collector = new RefCollector();
    collector.start();
  }
  
  public Core core;
  public long onum;
  public ReadMapEntry readMapEntry;
  
  /**
   * This destroys the background thread responsible for cleaning up collected
   * soft references.
   */
  public static void destroy() {
    collector.destroy();
  }

  public FabricSoftRef(Core core, long onum, $Impl impl) {
    super(impl, queue);
    this.core = core;
    this.onum = onum;
  }
  
  public void readMapEntry(ReadMapEntry readMapEntry) {
    this.readMapEntry = readMapEntry;
  }
  
  private static class RefCollector extends Thread {
    private boolean destroyed;
    
    @Override
    public void destroy() {
      destroyed = true;
      interrupt();
    }
    
    @Override
    public void run() {
      while (!destroyed) {
        try {
          FabricSoftRef ref = (FabricSoftRef) queue.remove();
          ref.core.notifyEvict(ref.onum);
          if (ref.readMapEntry.depin()) ref.readMapEntry = null;
        } catch (InterruptedException e) {}
      }
    }
  }

}
