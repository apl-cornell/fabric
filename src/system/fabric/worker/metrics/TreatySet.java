package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;

import fabric.common.FastSerializable;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.metrics.Metric;

/**
 * Utility class to easily express a set of treaties associated with a Metric.
 *
 * TODO:
 * - support updates and lazy cleaning of dead treaties on update.
 * - implement Serializable behavior.
 */
@SuppressWarnings("serial")
public class TreatySet
    implements FastSerializable, Serializable, Iterable<MetricTreaty> {

  private OidRef<Metric> owner;
  private LongKeyMap<MetricTreaty> items;

  public TreatySet(Metric owner) {
    this.owner = new OidRef<>(owner);
    this.items = new LongKeyHashMap<>();
  }

  public TreatySet(DataInput in) throws IOException {
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
  public int size() {
    return items.size();
  }

  @Override
  public Iterator<MetricTreaty> iterator() {
    return items.values().iterator();
  }

  /** @return a value to use for an empty vector */
  public static TreatySet emptySet(Metric owner) {
    return new TreatySet(owner);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof TreatySet)
        && this.items.equals(((TreatySet) obj).items);
  }

  @Override
  public int hashCode() {
    return this.items.hashCode();
  }

  @Override
  public void write(DataOutput out) throws IOException {
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

  /** Serialization Handling. */
  private void writeObject(ObjectOutputStream out) throws IOException {
    this.write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    this.owner = new OidRef<>(in);
    int size = in.readInt();
    this.items = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      MetricTreaty m = new MetricTreaty(this.owner, in);
      this.items.put(m.getId(), m);
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    // Do nothing.
  }
}
