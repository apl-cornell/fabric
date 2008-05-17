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
  
  public final Core core;
  public final long onum;
  public ReadMapEntry rme;
  
  /**
   * This destroys the background thread responsible for cleaning up collected
   * soft references.
   */
  public static void destroy() {
    collector.destroy();
  }

  public FabricSoftRef($Impl impl) {
    super(impl, queue);
    core = impl.$getCore();
    onum = impl.$getOnum();
  }
  
  public void rme(ReadMapEntry rme) {
    this.rme = rme;
  }
  
  private static class RefCollector extends Thread {
    
    private boolean destroyed;
    
    @Override
    public void destroy() {
      destroyed = true;
      interrupt();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
      while (!destroyed) {
        try {
          FabricSoftRef ref = (FabricSoftRef) queue.remove();
          ref.core.notifyEvict(ref.onum);
          ref.rme.depin();
        } catch (InterruptedException e) {}
      }
    }
    
  }

}
