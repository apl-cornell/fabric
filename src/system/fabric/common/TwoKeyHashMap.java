package fabric.common;

import java.util.HashMap;
import java.util.Map;

public class TwoKeyHashMap {
  public static <T, U, V> boolean containsKey(Map<T, HashMap<U,V>> map, T t, U u) {
    HashMap<U, V> inner = map.get(t);
    return inner != null && inner.containsKey(u);
  }
  
  public static <T, U, V> V get(Map<T, HashMap<U, V>> map, T t, U u) {
    HashMap<U, V> inner = map.get(t);
    return inner == null ? null : inner.get(u);
  }
  
  public static <T, U, V> V put(Map<T, HashMap<U,V>> map , T t, U u, V v) {
    HashMap<U, V> inner = map.get(t);
    if (inner == null) {
      inner = new HashMap<U,V>();
      map.put(t, inner);
    }
    
    return inner.put(u, v);
  }
}
