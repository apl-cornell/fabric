package fabric.lang;

import fabric.worker.Store;

/**
 * A marker interface for immutable inlineable objects.
 */
public interface Inlineable extends Object {
  public static class _Proxy extends Object._Proxy implements Inlineable {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }
  }
}
