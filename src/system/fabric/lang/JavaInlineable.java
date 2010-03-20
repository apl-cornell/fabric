package fabric.lang;

import fabric.worker.Core;

/**
 * A marker interface for Java objects that are inlineable.
 */
public interface JavaInlineable extends Inlineable {
  public static class _Proxy extends Object._Proxy implements JavaInlineable {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
