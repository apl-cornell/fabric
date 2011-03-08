package fabric.util;

import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.lang.Object;

public interface HashMap extends Map, AbstractMap {
  public static class _Impl extends AbstractMap._Impl implements HashMap {
    public _Impl(Store store, Label label) {
      super(store, label);
    }
    
    public native Object get(Object key);
    
    public native Object put(Object key, Object value);

    public native Set keySet();

    public native Collection values();
  }
}
