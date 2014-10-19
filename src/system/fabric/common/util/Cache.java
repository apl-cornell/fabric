package fabric.common.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import fabric.common.Logging;

/**
 * A map that has soft references to its values and supports concurrent
 * accesses. When a value in the map is garbage collected by the JVM, its
 * corresponding key is removed from the map. Null values are not supported
 * because the underlying ConcurrentMap does not support them.
 */
public class Cache<K, V> {

  // The underlying map.
  private final ConcurrentMap<K, ValueSoftRef<K, V>> map;

  private static final class ValueSoftRef<K, V> extends SoftReference<V> {
    final K key;
    final Cache<K, V> cache;

    public ValueSoftRef(Cache<K, V> cache, K key, V value) {
      super(value, queue);
      this.key = key;
      this.cache = cache;
    }
  }

  private static final ReferenceQueue<Object> queue = new ReferenceQueue<>();

  static {
    new Collector().start();
  }

  public Cache() {
    this.map = new ConcurrentHashMap<>();
  }

  public void clear() {
    map.clear();
  }

  public boolean containsKey(K key) {
    return map.containsKey(key);
  }

  public Set<K> keySet() {
    return map.keySet();
  }

  public V get(K key) {
    ValueSoftRef<K, V> ref = map.get(key);
    if (ref == null) return null;
    return ref.get();
  }

  public V put(K key, V value) {
    ValueSoftRef<K, V> ref = map.put(key, new ValueSoftRef<>(this, key, value));
    if (ref == null) return null;
    return ref.get();
  }

  /**
   * If the specified key is not already associated with a value, associate it
   * with the given value. This is equivalent to
   * <code>
   *   if (!cache.containsKey(key)) return map.put(key, value);
   *   else return map.get(key);
   * </code>
   * except that the action is performed atomically.
   *
   * @return the previous value associated with the specified key, or null if
   *          there was no mapping for the key.
   */
  public V putIfAbsent(K key, V value) {
    while (true) {
      // First use get to avoid acquiring a lock.
      ValueSoftRef<K, V> ref = map.get(key);
      if (ref == null) {
        // Didn't get anything, attempt to put.
        ref = map.putIfAbsent(key, new ValueSoftRef<>(this, key, value));
        if (ref == null) return null;
      }

      // Found an existing entry. See if the soft ref is still valid.
      V result = ref.get();
      if (result != null) return result;

      // Found a broken soft ref. Attempt to replace.
      if (map.replace(key, ref, new ValueSoftRef<>(this, key, value)))
        return null;

      // Replacement failed. Start over.
    }
  }

  /**
   * Replaces the entry for a key only if currently mapped to some value. This
   * is equivalent to
   * <code>
   *   if (cache.containsKey(key)) {
   *     return cache.put(key, value);
   *   } else return null;
   * </code>
   * except that the action is performed atomically.
   *
   * @param key key with which the specified value is associated.
   * @param value value to be associated with the specified key.
   * @return the previous value associated with the specified key, or null if
   *          there was no mapping for the key.
   */
  public V replace(K key, V value) {
    while (true) {
      ValueSoftRef<K, V> curRef = map.get(key);
      V curValue = null;
      if (curRef != null) curValue = curRef.get();

      if (map.replace(key, curRef, new ValueSoftRef<>(this, key, value))) {
        return curValue;
      }
    }
  }

  /**
   * Replaces the entry for a key only if currently mapped to a given value.
   * This is equivalent to
   * <code>
   *   if (cache.containsKey(key) && cache.get(key).equals(oldValue)) {
   *     cache.put(key, newValue);
   *     return true;
   *   } else return false;
   * </code>
   * except that the action is performed atomically.
   *
   * @param key key with which the specified value is associated.
   * @param oldValue value expected to be associated with the specified key.
   * @param newValue value to be associated with the specified key.
   * @return true iff the value was replaced.
   */
  public boolean replace(K key, V oldValue, V newValue) {
    ValueSoftRef<K, V> curRef = map.get(key);
    V curValue = curRef == null ? null : curRef.get();
    if (oldValue == null) {
      if (curValue != oldValue) return false;
      return putIfAbsent(key, newValue) == null;
    }

    if (oldValue != curValue && !oldValue.equals(curValue)) return false;
    return map.replace(key, curRef, new ValueSoftRef<>(this, key, newValue));
  }

  public V remove(K key) {
    ValueSoftRef<K, V> ref = map.remove(key);
    if (ref == null) return null;
    return ref.get();
  }

  /**
   * Removes the entry for a key only if currently mapped to a given value.
   * This is equivalent to
   * <code>
   *   if (cache.containsKey(key) && cache.get(key).equals(value)) {
   *     cache.remove(key);
   *     return true;
   *   } else return false;
   * </code>
   * except that the action is performed atomically.
   *
   * @param key key with which the specified value is associated.
   * @param value value expected to be associated with the specified key.
   * @return true iff the value was removed.
   */
  public boolean remove(K key, V value) {
    ValueSoftRef<K, V> curRef = map.get(key);
    V curValue = curRef == null ? null : curRef.get();
    if (value == null) {
      if (curValue != value) return false;
      return map.remove(key, null);
    }

    if (value != curValue && !value.equals(curValue)) return false;
    return map.remove(key, curRef);
  }

  /**
   * Returns a snapshot of the keys currently in the cache. This set is NOT
   * backed by the underlying map. If new keys are inserted or removed from the
   * cache, they will not be reflected by the set returned. However, no
   * synchronization is needed for working with the set.
   */
  public Set<K> keys() {
    return new HashSet<>(map.keySet());
  }

  private static final class Collector extends Thread {
    private Collector() {
      super("Cache entry collector");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        try {
          ValueSoftRef<?, ?> ref = (ValueSoftRef<?, ?>) queue.remove();
          ref.cache.map.remove(ref.key, ref);
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }

}
