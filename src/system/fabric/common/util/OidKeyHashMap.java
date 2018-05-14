package fabric.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fabric.common.SysUtil;
import fabric.lang.Object;
import fabric.worker.Store;

/**
 * A map keyed on OIDs. Supports null keys.
 */
public final class OidKeyHashMap<V> implements Iterable<LongKeyMap<V>> {
  Map<Store, LongKeyMap<V>> map;

  boolean hasNullEntry;
  V nullEntry;

  public OidKeyHashMap() {
    map = new HashMap<>();
    hasNullEntry = false;
    nullEntry = null;
  }

  /**
   * Copy constructor.
   */
  public OidKeyHashMap(OidKeyHashMap<V> other) {
    this();

    for (Map.Entry<Store, LongKeyMap<V>> entry : other.map.entrySet()) {
      this.map.put(entry.getKey(), new LongKeyHashMap<>(entry.getValue()));
    }

    this.hasNullEntry = other.hasNullEntry;
    this.nullEntry = other.nullEntry;
  }

  public LongKeyMap<V> get(Store store) {
    return map.get(store);
  }

  public void clear() {
    map.clear();
    hasNullEntry = false;
    nullEntry = null;
  }

  public boolean containsKey(Object obj) {
    return obj == null ? hasNullEntry : containsKey(obj.$getStore(),
        obj.$getOnum());
  }

  public boolean containsKey(Store store, long onum) {
    LongKeyMap<V> submap = map.get(store);
    return submap != null && submap.containsKey(onum);
  }

  public boolean containsKey(Oid oid) {
    return containsKey(oid.store, oid.onum);
  }

  public V get(Object obj) {
    return obj == null ? nullEntry : get(obj.$getStore(), obj.$getOnum());
  }

  public V get(Store store, long onum) {
    LongKeyMap<V> submap = map.get(store);
    return submap == null ? null : submap.get(onum);
  }

  public V get(Oid oid) {
    return get(oid.store, oid.onum);
  }

  public V put(Object obj, V val) {
    if (obj == null) {
      hasNullEntry = true;
      V result = nullEntry;
      nullEntry = val;
      return result;
    }

    return put(obj.$getStore(), obj.$getOnum(), val);
  }

  public V put(Store store, long onum, V val) {
    LongKeyMap<V> submap = map.get(store);
    if (submap == null) {
      submap = new LongKeyHashMap<>();
      map.put(store, submap);
    }

    return submap.put(onum, val);
  }

  public V put(Oid oid, V val) {
    return put(oid.store, oid.onum, val);
  }

  public V remove(Object obj) {
    if (obj == null) {
      V result = nullEntry;
      hasNullEntry = false;
      nullEntry = null;
      return result;
    }

    return remove(obj.$getStore(), obj.$getOnum());
  }

  public V remove(Store store, long onum) {
    LongKeyMap<V> submap = map.get(store);
    if (submap == null) return null;

    V result = submap.remove(onum);
    if (submap.isEmpty()) map.remove(store);
    return result;
  }

  public V remove(Oid oid) {
    return remove(oid.store, oid.onum);
  }

  public Set<Store> storeSet() {
    return map.keySet();
  }

  @Override
  public Iterator<LongKeyMap<V>> iterator() {
    return map.values().iterator();
  }

  public boolean isEmpty() {
    return !hasNullEntry && map.isEmpty();
  }

  public int size() {
    int result = hasNullEntry ? 1 : 0;

    for (LongKeyMap<V> submap : this)
      result += submap.size();

    return result;
  }

  public void putAll(OidKeyHashMap<V> m) {
    if (m.hasNullEntry) {
      hasNullEntry = true;
      nullEntry = m.nullEntry;
    }

    for (Map.Entry<Store, LongKeyMap<V>> entry : m.map.entrySet()) {
      Store store = entry.getKey();

      if (map.containsKey(store)) {
        map.get(store).putAll(entry.getValue());
      } else {
        map.put(store, new LongKeyHashMap<>(entry.getValue()));
      }
    }
  }

  public Iterable<V> values() {
    Collection<V>[] values = new Collection[storeSet().size()];
    int i = 0;
    for (Store store : storeSet()) {
      values[i++] = get(store).values();
    }

    return SysUtil.chain(values);
  }

  @Override
  public String toString() {
    String result = "[";
    boolean first = true;
    for (Store s : storeSet()) {
      for (LongKeyMap.Entry<V> entry : get(s).entrySet()) {
        if (!first)
          result += ", ";
        else
          first = false;
        result += s.name() + "/" + entry.getKey() + ": " + entry.getValue();
      }
    }
    return result + "]";
  }
}
