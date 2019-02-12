package fabric.common.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import fabric.common.FastSerializable;
import fabric.common.Surrogate;
import fabric.lang.Object;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A map keyed on OIDs. Does not supports null keys.
 */
public final class OidHashSet implements Iterable<Oid>, FastSerializable {
  // Using strings to avoid trying to get the store object before the worker is
  // initialized (while deserializing objects from disk).
  HashMap<Store, LongHashSet> map;

  public OidHashSet() {
    map = new HashMap<>();
  }

  /**
   * Deserialization constructor.
   */
  public OidHashSet(DataInput in) throws IOException {
    int size = in.readInt();
    map = new HashMap<>();
    for (int i = 0; i < size; i++) {
      String s = in.readUTF();
      Store store = Worker.getWorker().getStore(s);
      int size2 = in.readInt();
      LongHashSet submap = new LongHashSet(size2);
      map.put(store, submap);
      for (int j = 0; j < size2; j++) {
        submap.add(in.readLong());
      }
    }
  }

  /**
   * Copy constructor.
   */
  public OidHashSet(OidHashSet other) {
    this();

    for (Map.Entry<Store, LongHashSet> entry : other.map.entrySet()) {
      this.map.put(entry.getKey(), new LongHashSet(entry.getValue()));
    }
  }

  public LongSet get(Store store) {
    return map.get(store);
  }

  public void clear() {
    map.clear();
  }

  public boolean contains(Object obj) {
    if (obj instanceof Surrogate) {
      throw new InternalError("Surrogates should not be passed to OidHashSet");
    }

    return obj != null && contains(obj.$getStore(), obj.$getOnum());
  }

  public boolean contains(Store store, long onum) {
    LongHashSet submap = map.get(store);
    return submap != null && submap.contains(onum);
  }

  public boolean contains(Oid oid) {
    return contains(oid.store, oid.onum);
  }

  public boolean add(Object obj) {
    if (obj == null) {
      throw new NullPointerException();
    }

    if (obj instanceof Surrogate) {
      throw new InternalError("Surrogates should not be passed to OidHashSet");
    }

    return add(obj.$getStore(), obj.$getOnum());
  }

  public boolean add(Store store, long onum) {
    LongHashSet submap = map.get(store);
    if (submap == null) {
      submap = new LongHashSet();
      map.put(store, submap);
    }
    return submap.add(onum);
  }

  public boolean add(Oid oid) {
    return add(oid.store, oid.onum);
  }

  public boolean remove(Object obj) {
    if (obj == null) {
      throw new NullPointerException();
    }

    if (obj instanceof Surrogate) {
      throw new InternalError("Surrogates should not be passed to OidHashSet");
    }

    return remove(obj.$getStore(), obj.$getOnum());
  }

  public boolean remove(Store store, long onum) {
    LongHashSet submap = map.get(store);
    boolean result = submap != null && submap.remove(onum);
    if (result && submap.isEmpty()) {
      map.remove(store);
    }
    return result;
  }

  public boolean remove(Oid oid) {
    return remove(oid.store, oid.onum);
  }

  public Set<Store> storeSet() {
    return map.keySet();
  }

  @Override
  public Iterator<Oid> iterator() {
    return new Iterator<Oid>() {
      private Store curStore = null;
      private Iterator<Store> storeIter = map.keySet().iterator();
      private LongIterator subIter = null;

      /**
       * Skip over any empty iters. After this method returns, either
       * current is null (and there are no further elements to return), or
       * current.hasNext().
       */
      private void advance() {
        while (subIter == null && storeIter.hasNext()) {
          curStore = storeIter.next();
          subIter = map.get(curStore).iterator();
          if (!subIter.hasNext()) subIter = null;
        }
      }

      @Override
      public boolean hasNext() {
        advance();
        return subIter != null;
      }

      @Override
      public Oid next() {
        advance();
        if (subIter == null) throw new NoSuchElementException();
        long onum = subIter.next();
        if (!subIter.hasNext()) subIter = null;
        return new Oid(curStore, onum);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public int size() {
    int result = 0;

    for (LongHashSet submap : this.map.values())
      result += submap.size();

    return result;
  }

  public void addAll(OidHashSet m) {
    for (Map.Entry<Store, LongHashSet> entry : m.map.entrySet()) {
      Store store = entry.getKey();

      if (map.containsKey(store)) {
        map.get(store).addAll(entry.getValue());
      } else {
        map.put(store, new LongHashSet(entry.getValue()));
      }
    }
  }

  public boolean containsAll(OidHashSet m) {
    for (Map.Entry<Store, LongHashSet> entry : m.map.entrySet()) {
      Store store = entry.getKey();

      if (!map.containsKey(store)
          || !map.get(store).containsAll(entry.getValue()))
        return false;
    }
    return true;
  }

  @Override
  public boolean equals(java.lang.Object other) {
    if (!(other instanceof OidHashSet)) {
      return false;
    }
    OidHashSet map2 = (OidHashSet) other;
    if (!storeSet().equals(map2.storeSet())) {
      return false;
    }
    for (Store s : storeSet()) {
      if (!get(s).equals(map2.get(s))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(map.size());
    for (Map.Entry<Store, LongHashSet> e : map.entrySet()) {
      out.writeUTF(e.getKey().name());
      out.writeInt(e.getValue().size());
      for (LongIterator iter = e.getValue().iterator(); iter.hasNext();) {
        out.writeLong(iter.next());
      }
    }
  }

  @Override
  public String toString() {
    boolean first = true;
    String result = "[";
    for (Map.Entry<Store, LongHashSet> e : map.entrySet()) {
      Store store = e.getKey();
      for (LongIterator iter = e.getValue().iterator(); iter.hasNext();) {
        long onum = iter.next();
        if (first) {
          first = false;
        } else {
          result += ", ";
        }
        result += "<" + store.name() + "#" + onum + ">";
      }
    }
    return result + "]";
  }
}
