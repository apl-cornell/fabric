package fabric.util;

import fabric.client.Core;
import fabric.lang.Object;

public interface Map extends Object {
  public Object get(Object key);
  
  public static class $Proxy extends Object.$Proxy implements Map {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native Object get(Object key);
  }
}
