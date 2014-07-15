package fabric.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import fabric.lang.Object;
import fabric.worker.Store;

/**
 * A concurrent map keyed on OIDs. Supports null keys but not null values.
 */
public final class ConcurrentOidKeyHashMap<V> implements
    Iterable<ConcurrentLongKeyMap<V>> {
  ConcurrentMap<Store, ConcurrentLongKeyMap<V>> map;

  volatile V nullEntry;
  final ReentrantLock nullEntryLock;

  public ConcurrentOidKeyHashMap() {
    map = new ConcurrentHashMap<>();
    nullEntry = null;
    nullEntryLock = new ReentrantLock();
  }

  /**
   * Copy constructor.
   */
  public ConcurrentOidKeyHashMap(ConcurrentOidKeyHashMap<V> other) {
    this();

    for (Map.Entry<Store, ConcurrentLongKeyMap<V>> entry : other.map.entrySet()) {
      this.map.put(entry.getKey(),
          new ConcurrentLongKeyHashMap<>(entry.getValue()));
    }

    this.nullEntry = other.nullEntry;
  }

  public ConcurrentLongKeyMap<V> get(Store store) {
    return map.get(store);
  }

  public void clear() {
    map.clear();

    if (nullEntry != null) {
      nullEntryLock.lock();
      try {
        nullEntry = null;
      } finally {
        nullEntryLock.unlock();
      }
    }
  }

  public boolean containsKey(Object obj) {
    return obj == null ? nullEntry != null : containsKey(obj.$getStore(),
        obj.$getOnum());
  }

  public boolean containsKey(Store store, long onum) {
    ConcurrentLongKeyMap<V> submap = map.get(store);
    return submap != null && submap.containsKey(onum);
  }

  public boolean containsKey(Oid oid) {
    return containsKey(oid.store, oid.onum);
  }

  /**
   * @return
   *     an entry set for the map. Omits the null-keyed entry.
   */
  public Set<Entry<Store, ConcurrentLongKeyMap<V>>> nonNullEntrySet() {
    return map.entrySet();
  }

  public V get(Object obj) {
    return obj == null ? nullEntry : get(obj.$getStore(), obj.$getOnum());
  }

  public V get(Store store, long onum) {
    ConcurrentLongKeyMap<V> submap = map.get(store);
    return submap == null ? null : submap.get(onum);
  }

  public V get(Oid oid) {
    return get(oid.store, oid.onum);
  }

  public V put(Object obj, V val) {
    if (obj == null) {
      V result;

      nullEntryLock.lock();
      try {
        result = nullEntry;
        nullEntry = val;
      } finally {
        nullEntryLock.unlock();
      }

      return result;
    }

    return put(obj.$getStore(), obj.$getOnum(), val);
  }

  private ConcurrentLongKeyMap<V> ensureSubmap(Store store) {
    ConcurrentLongKeyMap<V> existing = map.get(store);
    if (existing != null) return existing;

    ConcurrentLongKeyMap<V> newMap = new ConcurrentLongKeyHashMap<>();
    existing = map.putIfAbsent(store, newMap);

    return existing == null ? newMap : existing;
  }

  public V put(Store store, long onum, V val) {
    return ensureSubmap(store).put(onum, val);
  }

  public V put(Oid oid, V val) {
    return put(oid.store, oid.onum, val);
  }

  public V putIfAbsent(Store store, long onum, V val) {
    return ensureSubmap(store).putIfAbsent(onum, val);
  }

  public V putIfAbsent(Oid oid, V val) {
    return putIfAbsent(oid.store, oid.onum, val);
  }

  public V remove(Object obj) {
    if (obj == null) {
      V result;

      nullEntryLock.lock();
      try {
        result = nullEntry;
        nullEntry = null;
      } finally {
        nullEntryLock.unlock();
      }

      return result;
    }

    return remove(obj.$getStore(), obj.$getOnum());
  }

  public V remove(Store store, long onum) {
    LongKeyMap<V> submap = map.get(store);
    if (submap == null) return null;

    V result = submap.remove(onum);
    // Disabled due to possible race.
//    if (submap.isEmpty()) map.remove(store, submap);
    return result;
  }

  public V remove(Oid oid) {
    return remove(oid.store, oid.onum);
  }

  public boolean remove(Store store, long onum, V value) {
    ConcurrentLongKeyMap<V> submap = map.get(store);
    if (submap == null) return false;

    boolean result = submap.remove(onum, value);
    // Disabled due to possible race.
//    if (submap.isEmpty()) map.remove(store, submap);
    return result;
  }

  public boolean remove(Oid oid, V value) {
    return remove(oid.store, oid.onum, value);
  }

  public boolean replace(Store store, long onum, V oldValue, V newValue) {
    ConcurrentLongKeyMap<V> submap = map.get(store);
    return submap != null && submap.replace(onum, oldValue, newValue);
  }

  public boolean replace(Oid oid, V oldValue, V newValue) {
    return replace(oid.store, oid.onum, oldValue, newValue);
  }

  public Set<Store> storeSet() {
    return map.keySet();
  }

  @Override
  public Iterator<ConcurrentLongKeyMap<V>> iterator() {
    return map.values().iterator();
  }

  public boolean isEmpty() {
    return nullEntry == null && map.isEmpty();
  }

  public int size() {
    int result = nullEntry == null ? 0 : 1;

    for (LongKeyMap<V> submap : this)
      result += submap.size();

    return result;
  }
}
