package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;

/**
 * Utility class to easily express a set of treaties associated with a Metric.
 */
public class MetricTreatySet extends TreatySet {

  private MetricRef owner;
  private LongKeyMap<MetricTreaty> items;
  private long nextId;
  private Map<TreatyStatement, MetricTreaty> statementMap;

  public MetricTreatySet(Metric owner) {
    super(TreatySet.Kind.METRIC);
    this.owner = new MetricRef(owner);
    this.items = new LongKeyHashMap<>();
    this.statementMap = new HashMap<>();
    this.nextId = 0;
  }

  private MetricTreatySet(MetricTreatySet original) {
    super(TreatySet.Kind.METRIC);
    this.owner = original.owner;
    this.items = new LongKeyHashMap<>(original.items);
    this.statementMap = new HashMap<>(original.statementMap);
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
  public static TreatySet emptySet(Metric owner) {
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
    this.owner = new MetricRef(in);
    this.nextId = in.readLong();
    int size = in.readInt();
    this.items = new LongKeyHashMap<>(size);
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

  @Override
  public void add(MetricTreaty treaty) {
    // Don't do anything if there's no actual change.
    if (items.containsKey(treaty.getId())
        && items.get(treaty.getId()).equals(treaty))
      return;

    MetricTreatySet updated = new MetricTreatySet(this);
    updated.items.put(treaty.getId(), treaty);
    MetricTreaty old = updated.statementMap.put(treaty.statement, treaty);
    if (old != null && old.getId() != treaty.getId()) Logging.METRICS_LOGGER
        .log(Level.SEVERE, "MASKED TREATY {0} WITH {3} IN {1} {2}",
            new Object[] { old,
                TransactionManager.getInstance().getCurrentTid(),
                Thread.currentThread(), treaty });
    owner.get().set$$treaties(updated);
  }

  @Override
  public void remove(MetricTreaty treaty) {
    // Don't do anything if there's no actual change.
    if (!items.containsKey(treaty.getId())) return;

    Logging.METRICS_LOGGER.log(Level.FINEST, "REMOVING TREATY {0} IN {1} {2}",
        new Object[] { treaty, TransactionManager.getInstance().getCurrentTid(),
            Thread.currentThread() });
    // TODO check that it's a proper garbage collection?
    MetricTreatySet updated = new MetricTreatySet(this);
    MetricTreaty val = updated.items.remove(treaty.getId());
    updated.statementMap.remove(treaty.statement, val);
    owner.get().set$$treaties(updated);
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

    MetricTreatySet updated = new MetricTreatySet(this);
    MetricTreaty newTreaty =
        new MetricTreaty(updated.owner.get(), updated.nextId++, stmt);
    updated.items.put(newTreaty.getId(), newTreaty);
    updated.statementMap.put(newTreaty.statement, newTreaty);
    owner.get().set$$treaties(updated);
    return newTreaty;
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
  public void prefetch(Store triggeringStore) {
    for (MetricTreaty t : this) {
      t.getObservers().prefetch(triggeringStore);
    }
  }
}
