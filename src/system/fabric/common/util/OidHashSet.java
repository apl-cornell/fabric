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
import fabric.lang.Object;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A map keyed on OIDs. Supports null keys.
 */
public final class OidHashSet implements Iterable<Oid>, FastSerializable {
  HashMap<Store, LongHashSet> map;

  boolean hasNull;

  public OidHashSet() {
    map = new HashMap<>();
    hasNull = false;
  }

  /**
   * Deserialization constructor.
   */
  public OidHashSet(DataInput in) throws IOException {
    int size = in.readInt();
    map = new HashMap<>();
    for (int i = 0; i < size; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int size2 = in.readInt();
      LongHashSet submap = new LongHashSet(size2);
      map.put(s, submap);
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

    this.hasNull = other.hasNull;
  }

  public LongSet get(Store store) {
    return map.get(store);
  }

  public void clear() {
    map.clear();
    hasNull = false;
  }

  public boolean contains(Object obj) {
    return obj == null ? hasNull : contains(obj.$getStore(), obj.$getOnum());
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
      boolean val = hasNull;
      hasNull = true;
      return val;
    }

    LongHashSet submap = map.get(obj.$getStore());
    if (submap == null) {
      submap = new LongHashSet();
      map.put(obj.$getStore(), submap);
    }
    return submap.add(obj.$getOnum());
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
      boolean result = hasNull;
      hasNull = false;
      return result;
    }

    LongHashSet submap = map.get(obj.$getStore());
    return submap != null && submap.remove(obj.$getOnum());
  }

  public boolean remove(Store store, long onum) {
    LongHashSet submap = map.get(store);
    return submap != null && submap.remove(onum);
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
    return !hasNull && map.isEmpty();
  }

  public int size() {
    int result = hasNull ? 1 : 0;

    for (LongHashSet submap : this.map.values())
      result += submap.size();

    return result;
  }

  public void putAll(OidHashSet m) {
    if (m.hasNull) {
      hasNull = true;
    }

    for (Map.Entry<Store, LongHashSet> entry : m.map.entrySet()) {
      Store store = entry.getKey();

      if (map.containsKey(store)) {
        map.get(store).addAll(entry.getValue());
      } else {
        map.put(store, new LongHashSet(entry.getValue()));
      }
    }
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
}
