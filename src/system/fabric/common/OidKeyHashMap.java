package fabric.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fabric.client.Core;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;

/**
 * A map keyed on OIDs.
 */
public final class OidKeyHashMap<V> implements Iterable<LongKeyMap<V>> {
  Map<Core, LongKeyMap<V>> map;

  boolean hasNullEntry;
  V nullEntry;

  public OidKeyHashMap() {
    map = new HashMap<Core, LongKeyMap<V>>();
    hasNullEntry = false;
    nullEntry = null;
  }

  /**
   * Copy constructor.
   */
  public OidKeyHashMap(OidKeyHashMap<V> other) {
    this();

    for (Map.Entry<Core, LongKeyMap<V>> entry : other.map.entrySet()) {
      this.map.put(entry.getKey(), new LongKeyHashMap<V>(entry.getValue()));
    }

    this.hasNullEntry = other.hasNullEntry;
    this.nullEntry = other.nullEntry;
  }

  public LongKeyMap<V> get(Core core) {
    return map.get(core);
  }

  public void clear() {
    map.clear();
    hasNullEntry = false;
    nullEntry = null;
  }

  public boolean containsKey(Object obj) {
    return obj == null ? hasNullEntry : containsKey(obj.$getCore(), obj
        .$getOnum());
  }

  public boolean containsKey(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    return submap != null && submap.containsKey(onum);
  }

  public V get(Object obj) {
    return obj == null ? nullEntry : get(obj.$getCore(), obj.$getOnum());
  }

  public V get(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    return submap == null ? null : submap.get(onum);
  }

  public V put(Object obj, V val) {
    if (obj == null) {
      hasNullEntry = true;
      V result = nullEntry;
      nullEntry = val;
      return result;
    }
    
    return put(obj.$getCore(), obj.$getOnum(), val);
  }

  public V put(Core core, long onum, V val) {
    LongKeyMap<V> submap = map.get(core);
    if (submap == null) {
      submap = new LongKeyHashMap<V>(4096);
      map.put(core, submap);
    }

    return submap.put(onum, val);
  }

  public V remove(Object obj) {
    if (obj == null) {
      V result = nullEntry;
      hasNullEntry = false;
      nullEntry = null;
      return result;
    }
    
    return remove(obj.$getCore(), obj.$getOnum());
  }

  public V remove(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    if (submap == null) return null;

    V result = submap.remove(onum);
    if (submap.isEmpty()) map.remove(core);
    return result;
  }

  public Set<Core> coreSet() {
    return map.keySet();
  }

  public Iterator<LongKeyMap<V>> iterator() {
    return map.values().iterator();
  }

  public boolean isEmpty() {
    return !hasNullEntry && map.isEmpty();
  }
}
