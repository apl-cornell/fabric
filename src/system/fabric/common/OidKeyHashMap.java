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

  public OidKeyHashMap() {
    map = new HashMap<Core, LongKeyMap<V>>();
  }
  
  /**
   * Copy constructor.
   */
  public OidKeyHashMap(OidKeyHashMap<V> other) {
    this();
    
    for (Map.Entry<Core, LongKeyMap<V>> entry : other.map.entrySet()) {
      this.map.put(entry.getKey(), new LongKeyHashMap<V>(entry.getValue()));
    }
  }

  public LongKeyMap<V> get(Core core) {
    return map.get(core);
  }
  
  public void clear() {
    map.clear();
  }
  
  public boolean containsKey(Object obj) {
    return containsKey(obj.$getCore(), obj.$getOnum());
  }
  
  public boolean containsKey(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    return submap != null && submap.containsKey(onum);
  }
  
  public V get(Object obj) {
    return get(obj.$getCore(), obj.$getOnum());
  }

  public V get(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    return submap == null ? null : submap.get(onum);
  }
  
  public V put(Object obj, V val) {
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
}
