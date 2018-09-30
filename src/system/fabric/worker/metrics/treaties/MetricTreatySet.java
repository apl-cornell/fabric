package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.DeltaMap;
import fabric.common.util.SortedDeltaMap;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.Store;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;

/**
 * Utility class to easily express a set of treaties associated with a Metric.
 *
 * XXX: there's an awkward/delicate hack here for avoiding unnecessary
 * copying/bloat for optimistic updates.  Modifications check if the update can
 * be done in place on an existing "mask" copy (using DeltaMaps) and provides a
 * flatten functionality that the Log must use when shedding history that is no
 * longer needed.
 */
public class MetricTreatySet extends TreatySet {

  private TreatiesBoxRef owner;
  private SortedMap<Long, MetricTreaty> items;
  private long nextId;
  private Map<TreatyStatement, MetricTreaty> statementMap;

  public MetricTreatySet(TreatiesBoxRef owner) {
    super(TreatySet.Kind.METRIC);
    this.owner = owner;
    this.items = new TreeMap<>();
    this.statementMap = new HashMap<>();
    this.nextId = 0;
  }

  public MetricTreatySet(TreatiesBox owner) {
    super(TreatySet.Kind.METRIC);
    this.owner = new TreatiesBoxRef(owner);
    this.items = new TreeMap<>();
    this.statementMap = new HashMap<>();
    this.nextId = 0;
  }

  private MetricTreatySet(MetricTreatySet original) {
    super(TreatySet.Kind.METRIC);
    this.owner = original.owner;
    this.items = new SortedDeltaMap<>(original.items);
    this.statementMap = new DeltaMap<>(original.statementMap);
    this.nextId = original.nextId;
  }

  /** @return the number of treaties. */
  @Override
  public int size() {
    return items.size();
  }

  @Override
  public Iterator<MetricTreaty> iterator() {
    return items.values().iterator();
  }

  /** @return a value to use for an empty vector */
  public static TreatySet emptySet(TreatiesBox owner) {
    return new MetricTreatySet(owner);
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj || (obj instanceof MetricTreatySet
        && this.owner.equals(((MetricTreatySet) obj).owner)
        && this.items.equals(((MetricTreatySet) obj).items));
  }

  @Override
  public int hashCode() {
    return this.owner.hashCode() ^ this.items.hashCode();
  }

  public MetricTreatySet(DataInput in) throws IOException {
    super(TreatySet.Kind.METRIC);
    this.owner = new TreatiesBoxRef(in);
    this.nextId = in.readLong();
    int size = in.readInt();
    this.items = new TreeMap<>();
    this.statementMap = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      MetricTreaty m = new MetricTreaty(this.owner, in);
      this.items.put(m.getId(), m);
      this.statementMap.put(m.statement, m);
    }
  }

  @Override
  public void writeData(DataOutput out) throws IOException {
    owner.write(out);
    out.writeLong(this.nextId);
    out.writeInt(this.items.size());
    if (this.items.size() != this.statementMap.size())
      throw new InternalError("Invariant violated!");
    // Don't bother writing out the keys, it's already included in the values.
    for (MetricTreaty treaty : this.items.values()) {
      treaty.write(out);
    }
  }

  @Override
  public String toString() {
    return items.toString();
  }

  // Dumb hack to allow for easy grab of an object's currently associated treaty
  // set (optimistic possibly) without invoking a read operation.
  // There's an assumption that this only gets used on things we already hold a
  // lock for.
  private static MetricTreatySet getAssociatedTreaties(
      fabric.lang.Object._Impl impl) {
    // Check if there's an associated extension.  If so, use that.
    ExpiryExtension extension =
        TransactionManager.getInstance().getPendingExtension(impl);
    return (MetricTreatySet) (extension == null ? impl.$treaties
        : extension.treaties);
  }

  @Override
  public void add(MetricTreaty treaty) {
    // Don't do anything if there's no actual change.
    if (items.containsKey(treaty.getId())
        && items.get(treaty.getId()).equals(treaty))
      return;

    Log curLog = TransactionManager.getInstance().getCurrentLog();
    fabric.lang.Object._Impl curBox =
        ((fabric.lang.Object._Impl) owner.get().get$treatiesBox().fetch());
    fabric.lang.Object._Impl boxHistory =
        ((TreatiesBox._Impl) owner.get().get$treatiesBox().fetch()).$history;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...??");
      // Make sure we clobber an extension if this in-place update isn't an
      // extension.
      MetricTreaty oldTreaty = items.put(treaty.getId(), treaty);
      if (oldTreaty != null && !(treaty.equals(oldTreaty)
          || treaty.isStrictExtensionOf(oldTreaty))) {
        curLog.clearExtension(curBox);
      }
      statementMap.put(treaty.statement, treaty);
      owner.get().get$treatiesBox().set$$treaties(this);
    } else {
      MetricTreatySet updated = new MetricTreatySet(this);
      updated.items.put(treaty.getId(), treaty);
      MetricTreaty old = updated.statementMap.put(treaty.statement, treaty);
      if (old != null && old.getId() != treaty.getId())
        Logging.METRICS_LOGGER.log(Level.SEVERE,
            "MASKED TREATY {0} WITH {3} IN {1} {2}",
            new Object[] { old,
                TransactionManager.getInstance().getCurrentTid(),
                Thread.currentThread(), treaty });
      owner.get().get$treatiesBox().set$$treaties(updated);
    }
  }

  @Override
  public void remove(MetricTreaty treaty) {
    // Don't do anything if there's no actual change.
    if (!items.containsKey(treaty.getId())) return;

    Logging.METRICS_LOGGER.log(Level.FINEST, "REMOVING TREATY {0} IN {1} {2}",
        new Object[] { treaty, TransactionManager.getInstance().getCurrentTid(),
            Thread.currentThread() });
    Log curLog = TransactionManager.getInstance().getCurrentLog();
    fabric.lang.Object._Impl curBox =
        ((fabric.lang.Object._Impl) owner.get().get$treatiesBox().fetch());
    fabric.lang.Object._Impl boxHistory =
        ((TreatiesBox._Impl) owner.get().get$treatiesBox().fetch()).$history;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
      // Make sure we clobber an extension if this in-place update isn't an
      // extension.
      items.remove(treaty.getId());
      curLog.clearExtension(curBox);
      statementMap.remove(treaty.statement);
      owner.get().get$treatiesBox().set$$treaties(this);
    } else {
      // TODO check that it's a proper garbage collection?
      MetricTreatySet updated = new MetricTreatySet(this);
      MetricTreaty val = updated.items.remove(treaty.getId());
      updated.statementMap.remove(treaty.statement, val);
      owner.get().get$treatiesBox().set$$treaties(updated);
    }
  }

  @Override
  public MetricTreaty get(long id) {
    return items.get(id);
  }

  @Override
  public MetricTreaty get(TreatyStatement stmt) {
    return statementMap.get(stmt);
  }

  @Override
  public MetricTreaty create(TreatyStatement stmt) {
    if (statementMap.containsKey(stmt)) return statementMap.get(stmt);

    Log curLog = TransactionManager.getInstance().getCurrentLog();
    fabric.lang.Object._Impl curBox =
        ((fabric.lang.Object._Impl) owner.get().get$treatiesBox().fetch());
    fabric.lang.Object._Impl boxHistory =
        ((TreatiesBox._Impl) owner.get().get$treatiesBox().fetch()).$history;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
      MetricTreaty newTreaty = new MetricTreaty(owner.get(), nextId++, stmt);
      // Make sure we clobber an extension if this in-place update isn't an
      // extension.
      MetricTreaty oldTreaty = items.put(newTreaty.getId(), newTreaty);
      if (oldTreaty != null && !(newTreaty.equals(oldTreaty)
          || newTreaty.isStrictExtensionOf(oldTreaty))) {
        curLog.clearExtension(curBox);
      }
      statementMap.put(newTreaty.statement, newTreaty);
      owner.get().get$treatiesBox().set$$treaties(this);
      return newTreaty;
    } else {
      MetricTreatySet updated = new MetricTreatySet(this);
      MetricTreaty newTreaty =
          new MetricTreaty(updated.owner.get(), updated.nextId++, stmt);
      updated.items.put(newTreaty.getId(), newTreaty);
      MetricTreaty old =
          updated.statementMap.put(newTreaty.statement, newTreaty);
      if (old != null && old.getId() != newTreaty.getId())
        Logging.METRICS_LOGGER.log(Level.SEVERE,
            "MASKED TREATY {0} WITH {3} IN {1} {2}",
            new Object[] { old,
                TransactionManager.getInstance().getCurrentTid(),
                Thread.currentThread(), newTreaty });
      owner.get().get$treatiesBox().set$$treaties(updated);
      return newTreaty;
    }
  }

  @Override
  public ImmutableObserverSet getObservers() {
    ImmutableObserverSet result = ImmutableObserverSet.emptySet();
    for (MetricTreaty t : items.values()) {
      result = result.addAll(t.getObservers());
    }
    return result;
  }

  @Override
  public boolean isStrictExtensionOf(TreatySet t) {
    if (t instanceof MetricTreatySet) {
      MetricTreatySet other = (MetricTreatySet) t;
      if (other.items.keySet().equals(this.items.keySet())) {
        boolean hasExtension = false;
        for (MetricTreaty oldTreaty : other) {
          MetricTreaty newTreaty = items.get(oldTreaty.getId());
          if (newTreaty.isStrictExtensionOf(oldTreaty)) {
            hasExtension = true;
          } else if (!newTreaty.equals(oldTreaty)) {
            return false;
          }
        }
        return hasExtension;
      }
    }
    return false;
  }

  @Override
  public void mergeExtensions(TreatySet from) {
    if (from instanceof MetricTreatySet) {
      MetricTreatySet other = (MetricTreatySet) from;
      for (MetricTreaty newTreaty : other) {
        MetricTreaty oldTreaty = items.get(newTreaty.getId());
        if (newTreaty.isStrictExtensionOf(oldTreaty)) {
          items.put(oldTreaty.getId(), newTreaty);
          statementMap.put(oldTreaty.statement, newTreaty);
        }
      }
    }
  }

  @Override
  public boolean isExtensionOf(TreatySet from) {
    if (from instanceof MetricTreatySet) {
      MetricTreatySet other = (MetricTreatySet) from;
      if (other.items.keySet().equals(this.items.keySet())) {
        for (MetricTreaty oldTreaty : other) {
          MetricTreaty newTreaty = items.get(oldTreaty.getId());
          if (!newTreaty.equals(oldTreaty)
              && !newTreaty.isStrictExtensionOf(oldTreaty)) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public Map<Store, Set<Long>> prefetch(Store triggeringStore) {
    Map<Store, Set<Long>> result = new HashMap<>();
    for (MetricTreaty t : this) {
      for (Map.Entry<Store, Set<Long>> e : t.getObservers()
          .prefetch(triggeringStore).entrySet()) {
        if (result.containsKey(e.getKey()))
          result.get(e.getKey()).addAll(e.getValue());
        else result.put(e.getKey(), e.getValue());
      }
    }
    return result;
  }

  @Override
  public void flattenUpdates() {
    fabric.lang.Object._Impl historyObj = ((fabric.lang.Object._Impl) owner
        .get().get$treatiesBox().fetch()).$history;
    if (historyObj != null) {
      MetricTreatySet historyMap = getAssociatedTreaties(historyObj);
      // Merge changes to items
      while (items != historyMap.items && items instanceof SortedDeltaMap
          && ((SortedDeltaMap<Long, MetricTreaty>) items).backingMap != historyMap.items)
        items = ((SortedDeltaMap<Long, MetricTreaty>) items).commitChanges();
      // Merge changes to statementMap
      while (statementMap != historyMap.statementMap
          && statementMap instanceof DeltaMap
          && ((DeltaMap<TreatyStatement, MetricTreaty>) statementMap).backingMap != historyMap.statementMap)
        statementMap = ((DeltaMap<TreatyStatement, MetricTreaty>) statementMap)
            .commitChanges();
    } else {
      // Merge changes to items
      while (items instanceof SortedDeltaMap)
        items = ((SortedDeltaMap<Long, MetricTreaty>) items).commitChanges();
      // Merge changes to statementMap
      while (statementMap instanceof DeltaMap)
        statementMap = ((DeltaMap<TreatyStatement, MetricTreaty>) statementMap)
            .commitChanges();
    }
  }

  @Override
  public TreatySet makeCopy() {
    MetricTreatySet copy = new MetricTreatySet(owner);
    copy.items.putAll(items);
    copy.statementMap.putAll(statementMap);
    copy.nextId = nextId;
    return copy;
  }
}
