package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fabric.common.FastSerializable;
import fabric.common.exceptions.AccessException;
import fabric.common.util.Triple;
import fabric.metrics.util.Observer;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of observers.
 */
@SuppressWarnings("serial")
public class ImmutableObserverSet implements FastSerializable, Serializable,
    Iterable<Triple<Observer._Proxy, Boolean, SortedSet<Long>>> {

  public static class ObserverGroup implements FastSerializable {
    public final boolean includesOwner;
    public final SortedSet<Long> treaties;

    public static final ObserverGroup EMPTY =
        new ObserverGroup(false, new TreeSet<>());

    /**
     * @param includesOwner
     * @param treaties
     */
    public ObserverGroup(boolean includesOwner, SortedSet<Long> treaties) {
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
      this.treaties = new TreeSet<>();
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
      SortedSet<Long> updated = new TreeSet<>(treaties);
      updated.add(id);
      return new ObserverGroup(includesOwner, updated);
    }

    public ObserverGroup removeTreaty(long id) {
      SortedSet<Long> updated = new TreeSet<>(treaties);
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
      for (long l : treaties) {
        out.writeLong(l);
      }
    }

    @Override
    public String toString() {
      return "owner:" + includesOwner + ",treaties:" + treaties;
    }

    @Override
    public boolean equals(Object o) {
      return o == this || ((o instanceof ObserverGroup)
          && ((ObserverGroup) o).includesOwner == includesOwner
          && ((ObserverGroup) o).treaties.equals(treaties));
    }

    @Override
    public int hashCode() {
      return Boolean.hashCode(includesOwner) ^ treaties.hashCode();
    }
  }

  // 2 level map, store name -> onum -> ObserverGroup
  private SortedMap<String, SortedMap<Long, ObserverGroup>> map;

  private ImmutableObserverSet() {
    this.map = new TreeMap<>();
  }

  private ImmutableObserverSet(ImmutableObserverSet orig) {
    this.map = new TreeMap<>();
    for (Map.Entry<String, SortedMap<Long, ObserverGroup>> e : orig.map
        .entrySet()) {
      this.map.put(e.getKey(), new TreeMap<>(e.getValue()));
    }
  }

  /** @return the length. */
  public int size() {
    int total = 0;
    for (SortedMap<Long, ObserverGroup> val : map.values()) {
      total += val.size();
    }
    return total;
  }

  /** @return a new set with the given observer added. */
  public ImmutableObserverSet add(Observer obs) {
    if (contains(obs)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get or Add First Level
    SortedMap<Long, ObserverGroup> submap =
        updated.map.get(obs.$getStore().name());
    if (submap == null) {
      submap = new TreeMap<>();
      updated.map.put(obs.$getStore().name(), submap);
    }

    // Get or Add Second Level
    ObserverGroup orig = submap.get(obs.$getOnum());
    if (orig == null) orig = ObserverGroup.EMPTY;

    // Add Third Level.
    submap.put(obs.$getOnum(), orig.addOwner());
    return updated;
  }

  /** @return a new set with the given observer's treaty added. */
  public ImmutableObserverSet add(Observer obs, long id) {
    if (contains(obs, id)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get or Add First Level
    SortedMap<Long, ObserverGroup> submap =
        updated.map.get(obs.$getStore().name());
    if (submap == null) {
      submap = new TreeMap<>();
      updated.map.put(obs.$getStore().name(), submap);
    }

    // Get or Add Second Level
    ObserverGroup orig = submap.get(obs.$getOnum());
    if (orig == null) orig = ObserverGroup.EMPTY;

    // Add Third Level.
    submap.put(obs.$getOnum(), orig.addTreaty(id));
    return updated;
  }

  /**
   * Add all items in the given ImmutableObserverSet.
   * @return the updated observer set.
   */
  public ImmutableObserverSet addAll(ImmutableObserverSet other) {
    ImmutableObserverSet result = this;
    for (Triple<Observer._Proxy, Boolean, SortedSet<Long>> item : other) {
      if (item.second) result = result.add(item.first);
      for (long treatyId : item.third) {
        result = result.add(item.first, treatyId);
      }
    }
    return result;
  }

  /** @return a new set with the given observer removed. */
  public ImmutableObserverSet remove(Observer obs) {
    if (!contains(obs)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get First Level
    SortedMap<Long, ObserverGroup> submap =
        updated.map.get(obs.$getStore().name());

    // Get Second Level
    ObserverGroup orig = submap.get(obs.$getOnum());

    // Remove Third Level
    ObserverGroup updatedGrp = orig.removeOwner();

    // Remove or Update Second Level
    if (updatedGrp.isEmpty()) {
      submap.remove(obs.$getOnum());
    } else {
      submap.put(obs.$getOnum(), updatedGrp);
    }

    // Remove First Level
    if (submap.isEmpty()) updated.map.remove(obs.$getStore().name());

    return updated;
  }

  /** @return a new set with the given observer's treaty removed. */
  public ImmutableObserverSet remove(Observer obs, long id) {
    if (!contains(obs, id)) return this;

    ImmutableObserverSet updated = new ImmutableObserverSet(this);

    // Get First Level
    SortedMap<Long, ObserverGroup> submap =
        updated.map.get(obs.$getStore().name());

    // Get Second Level
    ObserverGroup orig = submap.get(obs.$getOnum());

    // Remove Third Level
    ObserverGroup updatedGrp = orig.removeTreaty(id);

    // Remove or Update Second Level
    if (updatedGrp.isEmpty()) {
      submap.remove(obs.$getOnum());
    } else {
      submap.put(obs.$getOnum(), updatedGrp);
    }

    // Remove First Level
    if (submap.isEmpty()) updated.map.remove(obs.$getStore().name());

    return updated;
  }

  /**
   * Remove all items in the given ImmutableObserverSet.
   * @return the updated observer set.
   */
  public ImmutableObserverSet removeAll(ImmutableObserverSet other) {
    ImmutableObserverSet result = this;
    for (Triple<Observer._Proxy, Boolean, SortedSet<Long>> item : other) {
      if (item.second) result = result.remove(item.first);
      for (long treatyId : item.third) {
        result = result.remove(item.first, treatyId);
      }
    }
    return result;
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

  /** @return the treaties for a given obs */
  public SortedSet<Long> getTreaties(Observer obs) {
    if (map.containsKey(obs.$getStore().name())
        && map.get(obs.$getStore().name()).containsKey(obs.$getOnum()))
      return map.get(obs.$getStore().name()).get(obs.$getOnum()).treaties;
    return new TreeSet<>();
  }

  /** @return true iff the given set is fully contained in this set */
  public boolean containsAll(ImmutableObserverSet other) {
    // Superset of stores
    if (!map.keySet().containsAll(other.map.keySet())) return false;
    for (Map.Entry<String, SortedMap<Long, ObserverGroup>> e : other.map
        .entrySet()) {
      // Superset of onums for store
      if (!map.get(e.getKey()).keySet().containsAll(e.getValue().keySet()))
        return false;
      for (Map.Entry<Long, ObserverGroup> e2 : e.getValue().entrySet()) {
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
  public Iterator<Triple<Observer._Proxy, Boolean, SortedSet<Long>>> iterator() {
    return new Iterator<Triple<Observer._Proxy, Boolean, SortedSet<Long>>>() {
      Iterator<Map.Entry<String, SortedMap<Long, ObserverGroup>>> topIter =
          map.entrySet().iterator();
      Store curStore = null;
      Iterator<Map.Entry<Long, ObserverGroup>> subIter = null;

      @Override
      public boolean hasNext() {
        return (subIter != null && subIter.hasNext()) || topIter.hasNext();
      }

      @Override
      public Triple<Observer._Proxy, Boolean, SortedSet<Long>> next() {
        if (subIter == null || !subIter.hasNext()) {
          Map.Entry<String, SortedMap<Long, ObserverGroup>> nextBunch =
              topIter.next();
          curStore = Worker.getWorker().getStore(nextBunch.getKey());
          subIter = nextBunch.getValue().entrySet().iterator();
        }
        Map.Entry<Long, ObserverGroup> nextItem = subIter.next();
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

  public ImmutableObserverSet(DataInput in) throws IOException {
    int size1 = in.readInt();
    this.map = new TreeMap<>();
    for (int i = 0; i < size1; i++) {
      String storeName = in.readUTF();
      int size2 = in.readInt();
      SortedMap<Long, ObserverGroup> submap = new TreeMap<>();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        submap.put(onum, new ObserverGroup(in));
      }
      this.map.put(storeName, submap);
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
      SortedMap<Long, ObserverGroup> submap = new TreeMap<>();
      for (int j = 0; j < size2; j++) {
        long onum = in.readLong();
        submap.put(onum, new ObserverGroup(in));
      }
      this.map.put(storeName, submap);
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    map = new TreeMap<>();
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
    for (Map.Entry<String, SortedMap<Long, ObserverGroup>> e : map.entrySet()) {
      out.writeUTF(e.getKey());
      out.writeInt(e.getValue().size());
      for (Map.Entry<Long, ObserverGroup> e2 : e.getValue().entrySet()) {
        out.writeLong(e2.getKey());
        e2.getValue().write(out);
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
    return map.get(s) == null ? null : map.get(s).keySet();
  }

  /**
   * Allow for prefetching of the observer objects.
   */
  public void prefetch(Store triggeringStore) {
    // Hack to prefetch observers into cache.
    for (final Map.Entry<String, SortedMap<Long, ObserverGroup>> e : map
        .entrySet()) {
      if (e.getKey().equals(triggeringStore.name())) continue;
      Store s = Worker.getWorker().getStore(e.getKey());
      for (long onum : e.getValue().keySet()) {
        try {
          s.readObjectNoWait(onum);
        } catch (AccessException ex) {
          throw new InternalError(ex);
        }
      }
    }
  }
}
