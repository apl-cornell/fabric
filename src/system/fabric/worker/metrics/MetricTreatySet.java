package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.metrics.Metric;

/**
 * Utility class to easily express a set of treaties associated with a Metric.
 */
public class MetricTreatySet extends TreatySet {

  private OidRef<Metric> owner;
  private LongKeyMap<MetricTreaty> items;

  public MetricTreatySet(Metric owner) {
    super(TreatySet.Kind.METRIC);
    this.owner = new OidRef<>(owner);
    this.items = new LongKeyHashMap<>();
  }

  public MetricTreatySet(DataInput in) throws IOException {
    super(TreatySet.Kind.METRIC);
    this.owner = new OidRef<>(in);
    int size = in.readInt();
    this.items = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      MetricTreaty m = new MetricTreaty(this.owner, in);
      this.items.put(m.getId(), m);
    }
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
}
