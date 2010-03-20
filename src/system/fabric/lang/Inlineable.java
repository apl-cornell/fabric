package fabric.lang;

import fabric.worker.Core;

/**
 * A marker interface for immutable inlineable objects.
 */
public interface Inlineable extends Object {
  public static class _Proxy extends Object._Proxy implements Inlineable {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
