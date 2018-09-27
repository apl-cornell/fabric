package fabric.common.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Utility for making maps that can easily act as "optimistic" modifications,
 * holding potential changes to the underlying map, which can be committed (or
 * not!) to the backingMap at the end of a transaction.
 */
public class SortedDeltaMap<K, V> implements SortedMap<K, V> {

  public final SortedMap<K, V> backingMap;
  private final SortedSet<K> removedKeys;
  private final SortedMap<K, V> updates;
  private final SortedMap<K, V> adds;
  private final Comparator<? super K> comparator;

  public SortedDeltaMap(SortedMap<K, V> base) {
    this.backingMap = base;
    this.comparator = base.comparator();
    this.removedKeys = new TreeSet<>(comparator);
    this.updates = new TreeMap<>(comparator);
    this.adds = new TreeMap<>(comparator);
  }

  private int compareKeys(K k1, K k2) {
    if (comparator != null) return comparator.compare(k1, k2);
    return ((Comparable<K>) k1).compareTo(k2);
  }

  /**
   * Push the updates to the backing map and return that map.
   * This map should *not* be used after this operation.
   * @return the updated backing map.
   */
  public SortedMap<K, V> commitChanges() {
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
    if (updates.containsValue(value)) return true;
    for (Map.Entry<K, V> entry : backingMap.entrySet()) {
      if (removedKeys.contains(entry.getKey())) continue;
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
          private Iterator<Map.Entry<K, V>> addedIterator =
              adds.entrySet().iterator();

          private Map.Entry<K, V> nextPreexisting = null;

          private Map.Entry<K, V> nextAdded = null;

          private Map.Entry<K, V> last = null;

          @Override
          public boolean hasNext() {
            while (nextPreexisting == null && backingIterator.hasNext()) {
              nextPreexisting = backingIterator.next();
              if (removedKeys.contains(nextPreexisting.getKey())) {
                nextPreexisting = null;
              } else {
                K key = nextPreexisting.getKey();
                nextPreexisting = new Map.Entry<K, V>() {
                  @Override
                  public K getKey() {
                    return key;
                  }

                  @Override
                  public V getValue() {
                    return SortedDeltaMap.this.get(key);
                  }

                  @Override
                  public V setValue(V newValue) {
                    return SortedDeltaMap.this.put(key, newValue);
                  }
                };
              }
            }
            while (nextAdded == null && addedIterator.hasNext()) {
              nextAdded = addedIterator.next();
              if (removedKeys.contains(nextAdded.getKey())) {
                nextAdded = null;
              } else {
                K key = nextAdded.getKey();
                nextAdded = new Map.Entry<K, V>() {
                  @Override
                  public K getKey() {
                    return key;
                  }

                  @Override
                  public V getValue() {
                    return SortedDeltaMap.this.get(key);
                  }

                  @Override
                  public V setValue(V newValue) {
                    return SortedDeltaMap.this.put(key, newValue);
                  }
                };
              }
            }
            return nextPreexisting != null || nextAdded != null;
          }

          @Override
          public Map.Entry<K, V> next() {
            if (hasNext()) {
              if (nextPreexisting == null || (nextPreexisting != null
                  && nextAdded != null && compareKeys(nextPreexisting.getKey(),
                      nextAdded.getKey()) > 0)) {
                last = nextAdded;
                nextAdded = null;
              } else {
                last = nextPreexisting;
                nextPreexisting = null;
              }
              return last;
            }
            throw new NoSuchElementException();
          }

          @Override
          public void remove() {
            if (last == null) throw new IllegalStateException();
            SortedDeltaMap.this.remove(last.getKey());
            last = null;
          }
        };
      }

      @Override
      public int size() {
        return SortedDeltaMap.this.size();
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
        return SortedDeltaMap.this.size();
      }
    };
  }

  @Override
  public V put(K key, V value) {
    if (key == null || value == null) throw new IllegalArgumentException(
        "Null keys and values are not supported.");
    if (removedKeys.contains(key)) {
      removedKeys.remove(key);
      updates.put(key, value);
      return null;
    } else if (updates.containsKey(key)) {
      return updates.put(key, value);
    } else if (backingMap.containsKey(key)) {
      V oldValue = backingMap.get(key);
      updates.put(key, value);
      return oldValue;
    } else {
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
    if (removedKeys.contains(key)) return null;
    V existing = adds.remove(key);
    if (existing == null) {
      existing = updates.remove(key);
      if (existing == null) {
        existing = backingMap.get(key);
      }
      if (existing != null) {
        removedKeys.add((K) key);
      }
    }
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
        return SortedDeltaMap.this.size();
      }
    };
  }

  @Override
  public Comparator<? super K> comparator() {
    return comparator;
  }

  @Override
  public K firstKey() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public NavigableMap<K, V> headMap(K toKey) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public K lastKey() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public NavigableMap<K, V> subMap(K fromKey, K toKey) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public NavigableMap<K, V> tailMap(K fromKey) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }
}
