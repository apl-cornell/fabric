package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import fabric.common.FastSerializable;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

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

  private LongKeyMap<MetricTreaty> items;

  public TreatySet() {
    this.items = new LongKeyHashMap<>();
  }

  public TreatySet(DataInput in) throws IOException {
    int size = in.readInt();
    this.items = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long id = in.readLong();
      MetricTreaty m = new MetricTreaty(in);
      this.items.put(id, m);
    }
  }

  private TreatySet(LongKeyMap<MetricTreaty> items) {
    this.items = new LongKeyHashMap<>(items);
  }

  /** @return the number of treaties. */
  public int size() {
    return items.size();
  }

  @Override
  public Iterator<MetricTreaty> iterator() {
    return items.values().iterator();
  }

  private static TreatySet EMPTY =
      new TreatySet(new LongKeyHashMap<MetricTreaty>());

  /** @return a value to use for an empty vector */
  public static TreatySet emptySet() {
    return EMPTY;
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
    out.writeInt(this.items.size());
    for (LongKeyMap.Entry<MetricTreaty> e : this.items.entrySet()) {
      out.writeLong(e.getKey());
      e.getValue().write(out);
    }
  }

  @Override
  public String toString() {
    return items.toString();
  }
}
