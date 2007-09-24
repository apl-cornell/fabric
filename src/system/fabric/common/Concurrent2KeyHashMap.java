package fabric.common;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Provides a two-key hash map. XXX Only the outer map is a ConcurrentMap.
 */
public class Concurrent2KeyHashMap<T, U, V> implements Cloneable {

  private ConcurrentMap<T, HashMap<U, V>> map;

  public Concurrent2KeyHashMap() {
    map = new ConcurrentHashMap<T, HashMap<U, V>>();
  }

  public void clear() {
    map.clear();
  }

  public boolean containsKey(T key) {
    return map.containsKey(key);
  }

  public boolean containsKey(T key1, U key2) {
    return map.containsKey(key1) && map.get(key1).containsKey(key2);
  }

  public boolean containsValue(V value) {
    for (Map<U, V> map : this.map.values()) {
      if (map.containsValue(value)) return true;
    }

    return false;
  }

  public boolean containsValue(U key2, V value) {
    for (Map<U, V> map : this.map.values()) {
      if (map.containsKey(key2)) {
        if (value == null) return map.get(key2) == null;
        return value.equals(map.get(key2));
      }
    }

    return false;
  }

  public Collection<V> values() {
    return new AbstractCollection<V>() {

      /*
       * (non-Javadoc)
       * 
       * @see java.util.AbstractCollection#iterator()
       */
      @Override
      public Iterator<V> iterator() {
        return new Iterator<V>() {
          Iterator<HashMap<U, V>> outerIter = map.values().iterator();
          Iterator<V> innerIter = null;

          /*
           * (non-Javadoc)
           * 
           * @see java.util.Iterator#hasNext()
           */
          public boolean hasNext() {
            if (outerIter.hasNext()) return true;
            return innerIter != null && innerIter.hasNext();
          }

          /*
           * (non-Javadoc)
           * 
           * @see java.util.Iterator#next()
           */
          public V next() {
            if (innerIter == null || !innerIter.hasNext())
              innerIter = outerIter.next().values().iterator();
            return innerIter.next();
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }

      /*
       * (non-Javadoc)
       * 
       * @see java.util.AbstractCollection#size()
       */
      @Override
      public int size() {
        return Concurrent2KeyHashMap.this.size();
      }

    };
  }

  public Map<U, V> get(T key1) {
    return Collections.unmodifiableMap(map.get(key1));
  }

  public V get(T key1, U key2) {
    if (!map.containsKey(key1)) return null;
    return map.get(key1).get(key2);
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public V put(T key1, U key2, V value) {
    HashMap<U, V> map = this.map.get(key1);
    if (map == null) {
      map = new HashMap<U, V>();
      this.map.put(key1, map);
    }

    return map.put(key2, value);
  }

  public void putAll(Concurrent2KeyHashMap<T, U, V> other) {
    for (Map.Entry<T, HashMap<U, V>> entry : other.map.entrySet()) {
      T key = entry.getKey();
      HashMap<U, V> map = this.map.get(key);
      if (map == null) {
        map = new HashMap<U, V>(entry.getValue());
        this.map.put(key, map);
      } else {
        map.putAll(entry.getValue());
      }
    }
  }

  public Map<U, V> remove(T key1) {
    return map.remove(key1);
  }

  public V remove(T key1, U key2) {
    Map<U, V> map = this.map.get(key1);
    if (map == null) return null;
    V result = map.remove(key2);
    if (map.isEmpty()) this.map.remove(key1);
    return result;
  }

  public int size() {
    int result = 0;
    for (Map<U, V> map : this.map.values())
      result += map.size();
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Concurrent2KeyHashMap<T, U, V> clone() {
    Concurrent2KeyHashMap<T, U, V> result = null;
    try {
      result = (Concurrent2KeyHashMap<T, U, V>) super.clone();
      result.map = new ConcurrentHashMap<T, HashMap<U, V>>(result.map);
      for (Map.Entry<T, HashMap<U, V>> entry : result.map.entrySet()) {
        entry.setValue((HashMap<U, V>) entry.getValue().clone());
      }
    } catch (CloneNotSupportedException e) {
    }

    return result;
  }
}
