package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fabric.common.FastSerializable;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Triple;
import fabric.metrics.util.Observer;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of observers.
 */
@SuppressWarnings("serial")
public class ImmutableObserverSet implements FastSerializable, Serializable,
    Iterable<Triple<Observer._Proxy, Boolean, LongSet>> {

  public static class ObserverGroup implements FastSerializable {
    public final boolean includesOwner;
    public final LongSet treaties;

    public static final ObserverGroup EMPTY =
        new ObserverGroup(false, new LongHashSet());

    /**
     * @param includesOwner
     * @param treaties
     */
    public ObserverGroup(boolean includesOwner, LongSet treaties) {
      this.includesOwner = includesOwner;
      this.treaties = treaties;
    }

    /**
     * @param includesOwner
     * @param treaties
     */
    public ObserverGroup(DataInput in) throws IOException {
      this.includesOwner = in.readBoolean();
      int size = in.readInt();
      this.treaties = new LongHashSet(size);
      for (int i = 0; i < size; i++) {
        this.treaties.add(in.readLong());
      }
    }

    public ObserverGroup addOwner() {
      return new ObserverGroup(true, treaties);
    }

    public ObserverGroup removeOwner() {
      return new ObserverGroup(false, treaties);
    }

    public ObserverGroup addTreaty(long id) {
      LongSet updated = new LongHashSet(treaties);
      updated.add(id);
      return new ObserverGroup(includesOwner, updated);
    }

    public ObserverGroup removeTreaty(long id) {
      LongSet updated = new LongHashSet(treaties);
      updated.remove(id);
      return new ObserverGroup(includesOwner, updated);
    }

    public boolean isEmpty() {
      return !includesOwner && treaties.isEmpty();
    }

    public boolean containsAll(ObserverGroup other) {
      return (includesOwner || !other.includesOwner)
          && treaties.containsAll(other.treaties);
    }

    @Override
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(includesOwner);
      out.writeInt(treaties.size());
      for (LongIterator iter = treaties.iterator(); iter.hasNext();) {
        out.writeLong(iter.next());
      }
    }
  }

  // 2 level map, store name -> onum -> ObserverGroup
  private Map<String, LongKeyMap<ObserverGroup>> map;

  private ImmutableObserverSet() {
    this.map = new HashMap<>();
  }

  public ImmutableObserverSet(DataInput in) throws IOException {
    int size1 = in.readInt();
    this.map = new HashMap<>(size1);
    for (int i = 0; i < size1; i++) {
      String storeName = in.readUTF();
      int size2 = in.readInt();
      LongKeyMap<ObserverGroup> submap = new LongKeyHashMap<>(size2);
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        submap.put(onum, new ObserverGroup(in));
      }
      this.map.put(storeName, submap);
    }
  }

  private ImmutableObserverSet(ImmutableObserverSet orig) {
    this.map = new HashMap<>(orig.map.size());
    for (Map.Entry<String, LongKeyMap<ObserverGroup>> e : orig.map.entrySet()) {
      this.map.put(e.getKey(), new LongKeyHashMap<>(e.getValue()));
    }
  }

  /** @return the length. */
  public int size() {
    int total = 0;
    for (LongKeyMap<ObserverGroup> val : map.values()) {
      total += val.size();
    }
    return total;
  }

  /** @return a new set with the given observer added. */
  public ImmutableObserverSet add(Observer obs) {
    if (contains(obs)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get or Add First Level
    LongKeyMap<ObserverGroup> submap = updated.map.get(obs.$getStore().name());
    if (submap == null) {
      submap = new LongKeyHashMap<>();
      updated.map.put(obs.$getStore().name(), submap);
    }

    // Get or Add Second Level
    ObserverGroup orig =
        updated.map.get(obs.$getStore().name()).get(obs.$getOnum());
    if (orig == null) orig = ObserverGroup.EMPTY;

    // Add Third Level.
    updated.map.get(obs.$getStore().name()).put(obs.$getOnum(),
        orig.addOwner());
    return updated;
  }

  /** @return a new set with the given observer's treaty added. */
  public ImmutableObserverSet add(Observer obs, long id) {
    if (contains(obs, id)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get or Add First Level
    LongKeyMap<ObserverGroup> submap = updated.map.get(obs.$getStore().name());
    if (submap == null) {
      submap = new LongKeyHashMap<>();
      updated.map.put(obs.$getStore().name(), submap);
    }

    // Get or Add Second Level
    ObserverGroup orig =
        updated.map.get(obs.$getStore().name()).get(obs.$getOnum());
    if (orig == null) orig = ObserverGroup.EMPTY;

    // Add Third Level.
    updated.map.get(obs.$getStore().name()).put(obs.$getOnum(),
        orig.addTreaty(id));
    return updated;
  }

  /** @return a new set with the given observer removed. */
  public ImmutableObserverSet remove(Observer obs) {
    if (!contains(obs)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get First Level
    LongKeyMap<ObserverGroup> submap = updated.map.get(obs.$getStore().name());

    // Get Second Level
    ObserverGroup orig =
        updated.map.get(obs.$getStore().name()).get(obs.$getOnum());

    // Remove Third Level
    ObserverGroup updatedGrp = orig.removeOwner();

    // Remove Second Level
    if (updatedGrp.isEmpty()) submap.remove(obs.$getOnum());

    // Remove First Level
    if (submap.isEmpty()) updated.map.remove(obs.$getStore().name());

    return updated;
  }

  /** @return a new set with the given observer's treaty removed. */
  public ImmutableObserverSet remove(Observer obs, long id) {
    if (!contains(obs, id)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get First Level
    LongKeyMap<ObserverGroup> submap = updated.map.get(obs.$getStore().name());

    // Get Second Level
    ObserverGroup orig =
        updated.map.get(obs.$getStore().name()).get(obs.$getOnum());

    // Remove Third Level
    ObserverGroup updatedGrp = orig.removeTreaty(id);

    // Remove Second Level
    if (updatedGrp.isEmpty()) submap.remove(obs.$getOnum());

    // Remove First Level
    if (submap.isEmpty()) updated.map.remove(obs.$getStore().name());

    return updated;
  }

  /** @return true iff the given observer is in the set */
  public boolean contains(Observer obs) {
    return map.containsKey(obs.$getStore().name())
        && map.get(obs.$getStore().name()).containsKey(obs.$getOnum())
        && map.get(obs.$getStore().name()).get(obs.$getOnum()).includesOwner;
  }

  /** @return true iff the given observer treaty is in the set */
  public boolean contains(Observer obs, long id) {
    return map.containsKey(obs.$getStore().name())
        && map.get(obs.$getStore().name()).containsKey(obs.$getOnum())
        && map.get(obs.$getStore().name()).get(obs.$getOnum()).treaties
            .contains(id);
  }

  /** @return true iff the given set is fully contained in this set */
  public boolean containsAll(ImmutableObserverSet other) {
    // Superset of stores
    if (!map.keySet().containsAll(other.map.keySet())) return false;
    for (Map.Entry<String, LongKeyMap<ObserverGroup>> e : other.map
        .entrySet()) {
      // Superset of onums for store
      if (!map.get(e.getKey()).keySet().containsAll(e.getValue().keySet()))
        return false;
      for (LongKeyMap.Entry<ObserverGroup> e2 : e.getValue().entrySet()) {
        // Superset of observer group
        if (!map.get(e.getKey()).get(e2.getKey()).containsAll(e2.getValue()))
          return false;
      }
    }
    return true;
  }

  /** @return true iff the set is empty */
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Iterator<Triple<Observer._Proxy, Boolean, LongSet>> iterator() {
    return new Iterator<Triple<Observer._Proxy, Boolean, LongSet>>() {
      Iterator<Map.Entry<String, LongKeyMap<ObserverGroup>>> topIter =
          map.entrySet().iterator();
      Store curStore = null;
      Iterator<LongKeyMap.Entry<ObserverGroup>> subIter = null;

      @Override
      public boolean hasNext() {
        return (subIter != null && subIter.hasNext()) || topIter.hasNext();
      }

      @Override
      public Triple<Observer._Proxy, Boolean, LongSet> next() {
        if (subIter == null || !subIter.hasNext()) {
          Map.Entry<String, LongKeyMap<ObserverGroup>> nextBunch =
              topIter.next();
          curStore = Worker.getWorker().getStore(nextBunch.getKey());
          subIter = nextBunch.getValue().entrySet().iterator();
        }
        LongKeyMap.Entry<ObserverGroup> nextItem = subIter.next();
        return new Triple<>(new Observer._Proxy(curStore, nextItem.getKey()),
            nextItem.getValue().includesOwner, nextItem.getValue().treaties);
      }
    };
  }

  private static final ImmutableObserverSet EMPTY = new ImmutableObserverSet();

  /** @return a value to use for an empty vector */
  public static ImmutableObserverSet emptySet() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    int size1 = in.readInt();
    this.map = new HashMap<>(size1);
    for (int i = 0; i < size1; i++) {
      String storeName = in.readUTF();
      int size2 = in.readInt();
      LongKeyMap<ObserverGroup> submap = new LongKeyHashMap<>(size2);
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        submap.put(onum, new ObserverGroup(in));
      }
      this.map.put(storeName, submap);
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    map = new HashMap<>();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableObserverSet)
        && this.map.equals(((ImmutableObserverSet) obj).map);
  }

  @Override
  public int hashCode() {
    return this.map.hashCode();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(map.size());
    for (Map.Entry<String, LongKeyMap<ObserverGroup>> e : map.entrySet()) {
      out.writeUTF(e.getKey());
      out.writeInt(e.getValue().size());
      for (LongKeyMap.Entry<ObserverGroup> e2 : e.getValue().entrySet()) {
        out.writeLong(e2.getKey());
        e2.getValue().write(out);
      }
    }
  }

  @Override
  public String toString() {
    return map.toString();
  }

  public LongSet onumsForStore(Store s) {
    return onumsForStore(s.name());
  }

  public LongSet onumsForStore(String s) {
    return map.get(s).keySet();
  }

  /**
   * Allow for prefetching of the observer objects.
   */
  public void prefetch(Store triggeringStore) {
    // Hack to prefetch observers into cache.
    for (final Map.Entry<String, LongKeyMap<ObserverGroup>> e : map
        .entrySet()) {
      if (e.getKey().equals(triggeringStore.name())) continue;
      Threading.getPool().submit(new Runnable() {
        @Override
        public void run() {
          for (LongIterator iter = e.getValue().keySet().iterator(); iter
              .hasNext();) {
            long onum = iter.next();
            try {
              Worker.getWorker().getStore(e.getKey()).readObjectNoWait(onum);
            } catch (AccessException e) {
              throw new InternalError(e);
            }
          }
        }
      });
    }
  }
}
