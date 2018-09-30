package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fabric.common.FastSerializable;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of objects.
 */
@SuppressWarnings("serial")
public class ImmutableObjectSet implements FastSerializable, Serializable,
    Iterable<fabric.lang.Object._Proxy> {

  // store name -> onums
  private SortedMap<String, SortedSet<Long>> map;

  private ImmutableObjectSet() {
    this.map = new TreeMap<>();
  }

  private ImmutableObjectSet(ImmutableObjectSet orig) {
    this.map = new TreeMap<>();
    for (Map.Entry<String, SortedSet<Long>> e : orig.map.entrySet()) {
      this.map.put(e.getKey(), new TreeSet<>(e.getValue()));
    }
  }

  /** @return the length. */
  public int size() {
    int total = 0;
    for (SortedSet<Long> val : map.values()) {
      total += val.size();
    }
    return total;
  }

  /** @return a new set with the given object added. */
  public ImmutableObjectSet add(fabric.lang.Object o) {
    if (contains(o)) return this;

    ImmutableObjectSet updated = new ImmutableObjectSet(this);

    // Get or Add onum set
    SortedSet<Long> subset = updated.map.get(o.$getStore().name());
    if (subset == null) {
      subset = new TreeSet<>();
      updated.map.put(o.$getStore().name(), subset);
    }
    // Add onum
    subset.add(o.$getOnum());

    return updated;
  }

  /**
   * Add all items in the given ImmutableObjectSet.
   * @return the updated object set.
   */
  public ImmutableObjectSet addAll(ImmutableObjectSet other) {
    ImmutableObjectSet result = this;
    for (fabric.lang.Object._Proxy item : other) {
      result = result.add(item);
    }
    return result;
  }

  /** @return a new set with the given object removed. */
  public ImmutableObjectSet remove(fabric.lang.Object o) {
    if (!contains(o)) return this;

    ImmutableObjectSet updated = new ImmutableObjectSet(this);

    // Get onums
    SortedSet<Long> subset = updated.map.get(o.$getStore().name());

    // Remove or Update onums
    subset.remove(o.$getOnum());

    // Clear set if it's now empty.
    if (subset.isEmpty()) updated.map.remove(o.$getStore().name());

    return updated;
  }

  /**
   * Remove all items in the given ImmutableObjectSet.
   * @return the updated object set.
   */
  public ImmutableObjectSet removeAll(ImmutableObjectSet other) {
    ImmutableObjectSet result = this;
    for (fabric.lang.Object._Proxy item : other) {
      result = result.remove(item);
    }
    return result;
  }

  /** @return true iff the given object is in the set */
  public boolean contains(fabric.lang.Object obs) {
    return map.containsKey(obs.$getStore().name())
        && map.get(obs.$getStore().name()).contains(obs.$getOnum());
  }

  /** @return true iff the given set is fully contained in this set */
  public boolean containsAll(ImmutableObjectSet other) {
    // Superset of stores
    if (!map.keySet().containsAll(other.map.keySet())) return false;
    for (Map.Entry<String, SortedSet<Long>> e : other.map.entrySet()) {
      // Superset of onums for store
      if (!map.get(e.getKey()).containsAll(e.getValue())) return false;
    }
    return true;
  }

  /** @return true iff the set is empty */
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Iterator<fabric.lang.Object._Proxy> iterator() {
    return new Iterator<fabric.lang.Object._Proxy>() {
      Iterator<Map.Entry<String, SortedSet<Long>>> topIter =
          map.entrySet().iterator();
      Store curStore = null;
      Iterator<Long> subIter = null;

      @Override
      public boolean hasNext() {
        return (subIter != null && subIter.hasNext()) || topIter.hasNext();
      }

      @Override
      public fabric.lang.Object._Proxy next() {
        if (subIter == null || !subIter.hasNext()) {
          Map.Entry<String, SortedSet<Long>> nextBunch = topIter.next();
          curStore = Worker.getWorker().getStore(nextBunch.getKey());
          subIter = nextBunch.getValue().iterator();
        }
        long nextItem = subIter.next();
        return new fabric.lang.Object._Proxy(curStore, nextItem);
      }
    };
  }

  private static final ImmutableObjectSet EMPTY = new ImmutableObjectSet();

  /** @return a value to use for an empty vector */
  public static ImmutableObjectSet emptySet() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  public ImmutableObjectSet(DataInput in) throws IOException {
    int size1 = in.readInt();
    this.map = new TreeMap<>();
    for (int i = 0; i < size1; i++) {
      String storeName = in.readUTF();
      int size2 = in.readInt();
      SortedSet<Long> subset = new TreeSet<>();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        subset.add(onum);
      }
      this.map.put(storeName, subset);
    }
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    int size1 = in.readInt();
    this.map = new TreeMap<>();
    for (int i = 0; i < size1; i++) {
      String storeName = in.readUTF();
      int size2 = in.readInt();
      SortedSet<Long> subset = new TreeSet<>();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        subset.add(onum);
      }
      this.map.put(storeName, subset);
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    map = new TreeMap<>();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableObjectSet)
        && this.map.equals(((ImmutableObjectSet) obj).map);
  }

  @Override
  public int hashCode() {
    return this.map.hashCode();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(map.size());
    for (Map.Entry<String, SortedSet<Long>> e : map.entrySet()) {
      out.writeUTF(e.getKey());
      out.writeInt(e.getValue().size());
      for (long onum : e.getValue()) {
        out.writeLong(onum);
      }
    }
  }

  @Override
  public String toString() {
    return map.toString();
  }

  public Set<Long> onumsForStore(Store s) {
    return onumsForStore(s.name());
  }

  public Set<Long> onumsForStore(String s) {
    return map.get(s);
  }

  /**
   * Allow for prefetching of the objects.
   */
  public Map<Store, Set<Long>> prefetch(Store triggeringStore) {
    // Hack to prefetch objects into cache.
    Map<Store, Set<Long>> result = new HashMap<>();
    for (final Map.Entry<String, SortedSet<Long>> e : map.entrySet()) {
      if (e.getKey().equals(triggeringStore.name())) continue;
      Store s = Worker.getWorker().getStore(e.getKey());
      //Logging.METRICS_LOGGER.log(Level.INFO,
      //    "PREFETCHING ASSOCIATES FROM {0}: {1}",
      //    new Object[] { s, e.getValue() });
      result.put(s, new HashSet<>(e.getValue()));
    }
    return result;
  }
}
