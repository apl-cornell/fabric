package fabric.common.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.Logging;

/**
 * A map that has soft references to its values and supports concurrent
 * accesses. When a value in the map is garbage collected by the JVM, its
 * corresponding key is removed from the map. Null values are not supported
 * because the underlying ConcurrentLongKeyMap does not support them.
 */
public class LongKeyCache<V> {

  // The underlying map.
  private final ConcurrentLongKeyMap<ValueSoftRef<V>> map;

  private static final class ValueSoftRef<V> extends SoftReference<V> {
    final long key;
    final LongKeyCache<V> cache;

    public ValueSoftRef(LongKeyCache<V> cache, long key, V value) {
      super(value, queue);
      this.key = key;
      this.cache = cache;
    }
  }

  private static final ReferenceQueue<Object> queue = new ReferenceQueue<>();

  static {
    new Collector().start();
  }

  public LongKeyCache() {
    this.map = new ConcurrentLongKeyHashMap<>();
  }

  public void clear() {
    map.clear();
  }

  public boolean containsKey(long key) {
    return map.containsKey(key);
  }

  public LongSet keySet() {
    return map.keySet();
  }

  public V get(long key) {
    ValueSoftRef<V> ref = map.get(key);
    if (ref == null) return null;
    return ref.get();
  }

  public V put(long key, V value) {
    ValueSoftRef<V> ref = null;
    if (value != null) {
      ref = new ValueSoftRef<>(this, key, value);
    }

    ref = map.put(key, ref);
    if (ref == null) return null;
    return ref.get();
  }

  /**
   * If the specified key is not already associated with a value, associate it
   * with the given value. This is equivalent to
   * <code>
   *   if (!cache.containsKey(key)) return cache.put(key, value);
   *   else return cache.get(key);
   * </code>
   * except that the action is performed atomically.
   *
   * @return the previous value associated with the specified key, or null if
   *          there was no mapping for the key.
   */
  public V putIfAbsent(long key, V value) {
    ValueSoftRef<V> ref =
        map.putIfAbsent(key, new ValueSoftRef<>(this, key, value));
    if (ref == null) return null;
    return ref.get();
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
  public V replace(long key, V value) {
    while (true) {
      ValueSoftRef<V> curRef = map.get(key);
      V curValue = null;
      if (curRef != null) curValue = curRef.get();
      if (curValue == null) return null;

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
  public boolean replace(long key, V oldValue, V newValue) {
    ValueSoftRef<V> curRef = map.get(key);
    V curValue = curRef == null ? null : curRef.get();
    if (oldValue == null) {
      if (curValue != oldValue) return false;
      return putIfAbsent(key, newValue) == null;
    }

    if (oldValue != curValue && !oldValue.equals(curValue)) return false;
    return map.replace(key, curRef, new ValueSoftRef<>(this, key, newValue));
  }

  public V remove(long key) {
    ValueSoftRef<V> ref = map.remove(key);
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
  public boolean remove(long key, V value) {
    ValueSoftRef<V> curRef = map.get(key);
    V curValue = curRef == null ? null : curRef.get();
    if (value == null) {
      if (curValue != value) return false;
      return map.remove(key, null);
    }

    if (value != curValue && !value.equals(curValue)) return false;
    return map.remove(key, curRef);
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
          ValueSoftRef<?> ref = (ValueSoftRef<?>) queue.remove();
          ref.cache.map.remove(ref.key, ref);
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }

}
