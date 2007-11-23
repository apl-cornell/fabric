package fabric.lang;

import fabric.client.Core;

/**
 * A marker interface for Java objects that are inlineable.
 */
public interface JavaInlineable extends Inlineable {
  public static class $Proxy extends Object.$Proxy implements JavaInlineable {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
