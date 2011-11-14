package fabric.util;

import fabric.lang.Object;
import fabric.worker.Store;

public interface HashMap extends Map, AbstractMap {
  public static class _Impl extends AbstractMap._Impl implements HashMap {
    public _Impl(Store store) {
      super(store);
    }
    
    public native Object get(Object key);
    
    public native Object put(Object key, Object value);
    
    public native Set keySet();

    public native Set entrySet();

    public native Collection values();
  }
}
