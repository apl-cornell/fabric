package fabric.common.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Utility for making maps that can easily act as "optimistic" modifications,
 * holding potential changes to the underlying map, which can be committed (or
 * not!) to the backingMap at the end of a transaction.
 */
public class DeltaMap<K, V> implements Map<K, V> {

  public final Map<K, V> backingMap;
  private final Set<K> removedKeys;
  private final Map<K, V> updates;
  private final Map<K, V> adds;

  public DeltaMap(Map<K, V> base) {
    this.backingMap = base;
    this.removedKeys = new HashSet<>();
    this.updates = new HashMap<>();
    this.adds = new HashMap<>();
  }

  /**
   * Push the updates to the backing map and return that map.
   * This map should *not* be used after this operation.
   * @return the updated backing map.
   */
  public Map<K, V> commitChanges() {
    for (Object removed : removedKeys) {
      this.backingMap.remove(removed);
    }
    for (Map.Entry<K, V> update : updates.entrySet()) {
      this.backingMap.put(update.getKey(), update.getValue());
    }
    for (Map.Entry<K, V> update : adds.entrySet()) {
      this.backingMap.put(update.getKey(), update.getValue());
    }
    this.removedKeys.clear();
    this.updates.clear();
    this.adds.clear();
    return this.backingMap;
  }

  @Override
  public void clear() {
    updates.clear();
    adds.clear();
    removedKeys.addAll(backingMap.keySet());
  }

  @Override
  public boolean containsKey(Object arg0) {
    return !removedKeys.contains(arg0) && (updates.containsKey(arg0)
        || adds.containsKey(arg0) || backingMap.containsKey(arg0));
  }

  @Override
  public boolean containsValue(Object value) {
    if (updates.containsValue(value) || adds.containsValue(value)) return true;
    for (Map.Entry<K, V> entry : backingMap.entrySet()) {
      if (removedKeys.contains(entry.getKey()) ||
          updates.containsKey(entry.getKey())) continue;
      if (value == null && entry.getValue() == null)
        return true;
      else if (value != null && value.equals(entry.getValue())) return true;
    }
    return false;
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    return new AbstractSet<Map.Entry<K, V>>() {
      @Override
      public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {

          private Iterator<Map.Entry<K, V>> backingIterator =
              backingMap.entrySet().iterator();
          private Iterator<Map.Entry<K, V>> addIterator =
              adds.entrySet().iterator();

          private Map.Entry<K, V> next = null;

          private Map.Entry<K, V> last = null;

          @Override
          public boolean hasNext() {
            while (next == null
                && (backingIterator.hasNext() || addIterator.hasNext())) {
              if (backingIterator.hasNext()) {
                next = backingIterator.next();
                if (removedKeys.contains(next.getKey())) {
                  next = null;
                } else {
                  K key = next.getKey();
                  next = new Map.Entry<K, V>() {
                    @Override
                    public K getKey() {
                      return key;
                    }

                    @Override
                    public V getValue() {
                      return DeltaMap.this.get(key);
                    }

                    @Override
                    public V setValue(V newValue) {
                      return DeltaMap.this.put(key, newValue);
                    }
                  };
                }
              } else if (addIterator.hasNext()) {
                next = addIterator.next();
              }
            }
            return next != null;
          }

          @Override
          public Map.Entry<K, V> next() {
            if (hasNext()) {
              last = next;
              next = null;
              return last;
            }
            throw new NoSuchElementException();
          }

          @Override
          public void remove() {
            if (last == null) throw new IllegalStateException();
            DeltaMap.this.remove(last.getKey());
            last = null;
          }
        };
      }

      @Override
      public int size() {
        return DeltaMap.this.size();
      }
    };
  }

  @Override
  public V get(Object key) {
    if (removedKeys.contains(key)) return null;
    if (updates.containsKey(key)) return updates.get(key);
    if (adds.containsKey(key)) return adds.get(key);
    return backingMap.get(key);
  }

  @Override
  public boolean isEmpty() {
    return updates.isEmpty() && adds.isEmpty()
        && removedKeys.containsAll(backingMap.keySet());
  }

  @Override
  public Set<K> keySet() {
    return new AbstractSet<K>() {
      @Override
      public Iterator<K> iterator() {
        return new Iterator<K>() {

          private Iterator<Map.Entry<K, V>> entryIterator =
              entrySet().iterator();

          @Override
          public boolean hasNext() {
            return entryIterator.hasNext();
          }

          @Override
          public K next() {
            return entryIterator.next().getKey();
          }

          @Override
          public void remove() {
            entryIterator.remove();
          }
        };
      }

      @Override
      public int size() {
        return DeltaMap.this.size();
      }
    };
  }

  @Override
  public V put(K key, V value) {
    if (key == null || value == null) throw new IllegalArgumentException(
        "Null keys and values are not supported.");
    if (removedKeys.contains(key)) {
      // putting a key that pre-existed and was removed in this delta.
      removedKeys.remove(key);
      updates.put(key, value);
      return null;
    } else if (updates.containsKey(key)) {
      // putting a key that pre-existed and was previously updated in this
      // delta.
      return updates.put(key, value);
    } else if (backingMap.containsKey(key)) {
      // putting a key that pre-existed
      V oldValue = backingMap.get(key);
      updates.put(key, value);
      return oldValue;
    } else {
      // putting a key that didn't exist before.
      return adds.put(key, value);
    }
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public V remove(Object key) {
    // Already removed
    if (removedKeys.contains(key)) return null;

    // Removed a key added in this delta
    V existing = adds.remove(key);
    if (existing != null) return existing;

    // Removed an updated pre-existing key.
    existing = updates.remove(key);
    if (existing == null) existing = backingMap.get(key);
    if (existing != null) removedKeys.add((K) key);

    return existing;
  }

  @Override
  public int size() {
    return adds.size() + backingMap.size() - removedKeys.size();
  }

  @Override
  public Collection<V> values() {
    return new AbstractCollection<V>() {
      @Override
      public Iterator<V> iterator() {
        return new Iterator<V>() {

          private Iterator<Map.Entry<K, V>> entryIterator =
              entrySet().iterator();

          @Override
          public boolean hasNext() {
            return entryIterator.hasNext();
          }

          @Override
          public V next() {
            return entryIterator.next().getValue();
          }

          @Override
          public void remove() {
            entryIterator.remove();
          }
        };
      }

      @Override
      public int size() {
        return DeltaMap.this.size();
      }
    };
  }
}
