package fabric.util;

import jif.lang.Label;
import fabric.client.Core;
import fabric.lang.Object;

public interface HashMap extends Map, AbstractMap {
  public static class $Impl extends AbstractMap.$Impl implements HashMap {
    public $Impl(Core core, Label label) {
      super(core, label);
    }
    
    public native Object get(Object key);
    
    public native Object put(Object key, Object value);
  }
}
