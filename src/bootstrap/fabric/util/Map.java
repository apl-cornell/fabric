package fabric.util;

import fabric.worker.Store;
import fabric.lang.Object;

public interface Map extends Object {
  public Object get(Object key);

  public Set keySet();
  
  public Set entrySet();

  public Collection values();

  public static class _Proxy extends Object._Proxy implements Map {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native Object get(Object key);
    
    public native Set keySet();
    
    public native Set entrySet();

    public native Collection values();
  }
  interface Entry
  {
    Object getKey();
    Object getValue();
  }
}
