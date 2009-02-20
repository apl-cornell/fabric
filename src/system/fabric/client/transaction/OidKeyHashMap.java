package fabric.client.transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fabric.client.Core;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * A map keyed on OIDs.
 */
final class OidKeyHashMap<V> implements Iterable<LongKeyMap<V>> {
  Map<Core, LongKeyMap<V>> map;

  OidKeyHashMap() {
    map = new HashMap<Core, LongKeyMap<V>>();
  }

  public LongKeyMap<V> get(Core core) {
    return map.get(core);
  }
  
  public void clear() {
    map.clear();
  }

  public V get(Core core, long onum) {
    LongKeyMap<V> submap = map.get(core);
    return submap == null ? null : submap.get(onum);
  }

  public V put(Core core, long onum, V val) {
    LongKeyMap<V> submap = map.get(core);
    if (submap == null) {
      submap = new LongKeyHashMap<V>(4096);
      map.put(core, submap);
    }

    return submap.put(onum, val);
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
