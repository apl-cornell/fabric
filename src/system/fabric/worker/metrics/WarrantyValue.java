package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.lang.WrappedJavaInlineable;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link MetricTreaty}s implying their validity.
 */
public class WarrantyValue implements Serializable {

  /** The result value. */
  public fabric.lang.Object value;

  /**
   * A {@link TreatyRef} which, when valid, implies the value is current.
   */
  public TreatyRef treaty;

  /**
   * A set of weak statistics to use if activating the treaty for the first
   * time.
   */
  public StatsMap weakStats;

  /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param treaty
   *        the value's associated {@link MetricTreaty}
   */
  public WarrantyValue(fabric.lang.Object value, TreatyRef treaty,
      StatsMap weakStats) {
    this.value = value;
    this.treaty = treaty;
    this.weakStats = weakStats;
  }

  /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param treaty
   *        the value's associated {@link MetricTreaty}
   */
  public WarrantyValue(fabric.lang.Object value, MetricTreaty treaty,
      StatsMap weakStats) {
    this(value, new TreatyRef(treaty), weakStats);
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      TreatyRef treaty) {
    return new WarrantyValue(value, treaty, StatsMap.emptyStats());
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      MetricTreaty treaty) {
    return new WarrantyValue(value, treaty, StatsMap.emptyStats());
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      TreatyRef treaty, StatsMap weakStats) {
    return new WarrantyValue(value, treaty, weakStats);
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      MetricTreaty treaty, StatsMap weakStats) {
    return new WarrantyValue(value, treaty, weakStats);
  }

  @Override
  public String toString() {
    return "WarrantyVal(" + value + ", " + treaty + ")";
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    if (value != null) {
      out.writeBoolean(true);
      if (value instanceof WrappedJavaInlineable) {
        out.writeBoolean(true);
        out.writeObject(((WrappedJavaInlineable<?>) value).$unwrap());
      } else {
        out.writeBoolean(false);
        out.writeUTF(value.$getStore().name());
        out.writeLong(value.$getOnum());
      }
    } else {
      out.writeBoolean(false);
    }
    if (treaty != null) {
      out.writeBoolean(true);
      out.writeUTF(value.$getStore().name());
      out.writeLong(value.$getOnum());
    } else {
      out.writeBoolean(false);
    }
    weakStats.write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    if (in.readBoolean()) {
      if (in.readBoolean()) {
        value = WrappedJavaInlineable.$wrap(in.readObject());
      } else {
        Store s = Worker.getWorker().getStore(in.readUTF());
        value = new fabric.lang.Object._Proxy(s, in.readLong());
      }
    }
    if (in.readBoolean()) {
      treaty = new TreatyRef(in);
    }
    weakStats = new StatsMap(in);
  }

  private void readObjectNoData() throws ObjectStreamException {
    value = null;
    treaty = null;
    weakStats = StatsMap.emptyStats();
  }
}
