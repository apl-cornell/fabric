package fabric.util;

import fabric.worker.Core;
import fabric.lang.Object;

public interface Map extends Object {
  public Object get(Object key);
  
  public static class _Proxy extends Object._Proxy implements Map {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native Object get(Object key);
  }
}
