package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.metrics.Metric;
import fabric.worker.metrics.treaties.statements.TreatyStatement;

/**
 * Utility class to easily express a set of treaties associated with a Metric.
 */
public class MetricTreatySet extends TreatySet {

  private OidRef<Metric> owner;
  private LongKeyMap<MetricTreaty> items;
  private long nextId;

  public MetricTreatySet(Metric owner) {
    super(TreatySet.Kind.METRIC);
    this.owner = new OidRef<>(owner);
    this.items = new LongKeyHashMap<>();
    this.nextId = 0;
  }

  public MetricTreatySet(DataInput in) throws IOException {
    super(TreatySet.Kind.METRIC);
    this.owner = new OidRef<>(in);
    this.nextId = in.readLong();
    int size = in.readInt();
    this.items = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      MetricTreaty m = new MetricTreaty(this.owner, in);
      this.items.put(m.getId(), m);
    }
  }

  private MetricTreatySet(MetricTreatySet original) {
    super(TreatySet.Kind.METRIC);
    this.owner = original.owner;
    this.items = new LongKeyHashMap<>(original.items);
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
    return (obj instanceof MetricTreatySet)
        && this.owner.equals(((MetricTreatySet) obj).owner)
        && this.items.equals(((MetricTreatySet) obj).items);
  }

  @Override
  public int hashCode() {
    return this.items.hashCode();
  }

  @Override
  public void writeData(DataOutput out) throws IOException {
    owner.write(out);
    out.writeLong(this.nextId);
    out.writeInt(this.items.size());
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
  public TreatySet add(MetricTreaty treaty) {
    // TODO check if there's an actual update?
    MetricTreatySet updated = new MetricTreatySet(this);
    updated.items.put(treaty.getId(), treaty);
    return updated;
  }

  @Override
  public MetricTreaty get(long id) {
    return items.get(id);
  }

  @Override
  public Pair<TreatySet, MetricTreaty> create(TreatyStatement stmt) {
    // TODO: could be worth exploring checking if we already have that statement
    // represented?
    MetricTreatySet updated = new MetricTreatySet(this);
    MetricTreaty newTreaty =
        new MetricTreaty(updated.owner.get(), updated.nextId++, stmt);
    updated.items.put(newTreaty.getId(), newTreaty);
    return new Pair<TreatySet, MetricTreaty>(updated, newTreaty);
  }
}
