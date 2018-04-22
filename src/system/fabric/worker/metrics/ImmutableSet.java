package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of items.
 */
public class ImmutableSet
    implements Serializable, Iterable<fabric.lang.Object._Proxy> {
  /** The array backing this set */
  private OidKeyHashMap<fabric.lang.Object._Proxy> items;

  /** Provided since you can't mark constructors as native for the fabil
   * signatures
   */
  public static ImmutableSet createSet(fabric.lang.Object[] items) {
    return new ImmutableSet(items);
  }

  public ImmutableSet(fabric.lang.Object[] items) {
    this.items = new OidKeyHashMap<>();
    for (fabric.lang.Object item : items) {
      this.items.put(item, item.$getProxy());
    }
  }

  private ImmutableSet(OidKeyHashMap<fabric.lang.Object._Proxy> items) {
    this.items = new OidKeyHashMap<>(items);
  }

  /** @return the length. */
  public int size() {
    return items.size();
  }

  /** @return a new set with the given item added. */
  public ImmutableSet add(fabric.lang.Object obs) {
    ImmutableSet updated = new ImmutableSet(items);
    updated.items.put(obs, obs.$getProxy());
    return updated;
  }

  /** @return a new set with the given item removed. */
  public ImmutableSet remove(fabric.lang.Object obs) {
    ImmutableSet updated = new ImmutableSet(items);
    updated.items.remove(obs);
    return updated;
  }

  /** @return true iff the given observer is in the set */
  public boolean contains(fabric.lang.Object obs) {
    return items.containsKey(obs);
  }

  /** @return true iff the set is empty */
  public boolean isEmpty() {
    return items.isEmpty();
  }

  @Override
  public Iterator<fabric.lang.Object._Proxy> iterator() {
    return items.values().iterator();
  }

  private static final ImmutableSet EMPTY =
      new ImmutableSet(new fabric.lang.Object[0]);

  /** @return a value to use for an empty vector */
  public static ImmutableSet emptySet() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) {
    try {
      out.writeInt(items.size());
      for (Store s : items.storeSet()) {
        LongKeyMap<fabric.lang.Object._Proxy> subMap = items.get(s);
        out.writeUTF(s.name());
        out.writeInt(subMap.size());
        for (LongIterator iter = subMap.keySet().iterator(); iter.hasNext();) {
          out.writeLong(iter.next());
        }
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObject(ObjectInputStream in) {
    try {
      int size = in.readInt();
      items = new OidKeyHashMap<>();
      for (int i = 0; i < size; i++) {
        Store s = Worker.getWorker().getStore(in.readUTF());
        int subSize = in.readInt();
        for (int j = 0; j < subSize; j++) {
          long onum = in.readLong();
          items.put(s, onum, new fabric.lang.Object._Proxy(s, onum));
        }
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObjectNoData() {
    items = new OidKeyHashMap<>();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableSet)
        && this.items.equals(((ImmutableSet) obj).items);
  }

  @Override
  public int hashCode() {
    return this.items.hashCode();
  }
}
