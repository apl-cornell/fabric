package fabric.common.util;

import java.util.HashMap;
import java.util.Map;

public class TwoKeyHashMap {
  public static <T, U, V> boolean containsKey(Map<T, HashMap<U, V>> map, T t,
      U u) {
    HashMap<U, V> inner = map.get(t);
    return inner != null && inner.containsKey(u);
  }

  public static <T, V> V get(Map<T, LongKeyHashMap<V>> map, T t, long u) {
    LongKeyHashMap<V> inner = map.get(t);
    return inner == null ? null : inner.get(u);
  }

  public static <T, V> V put(Map<T, LongKeyHashMap<V>> map, T t, long u, V v) {
    LongKeyHashMap<V> inner = map.get(t);
    if (inner == null) {
      inner = new LongKeyHashMap<>();
      map.put(t, inner);
    }

    return inner.put(u, v);
  }
}
