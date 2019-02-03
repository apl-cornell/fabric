package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.lang.WrappedJavaInlineable;
import fabric.metrics.Metric;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.treaties.Treaty;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.treaties.statements.TreatyStatement;

/**
 * A utility class for proposing a {@link WarrantyValue} to use as a {@link
 * WarrantyComp} result
 */
@SuppressWarnings("serial")
public class WarrantyProposal implements Serializable {

  /** The result value. */
  public fabric.lang.Object value;

  /**
   * The {@link Metric} for the proposed {@link Treaty} which, if valid, implies
   * the value is current.
   */
  public Metric metric;

  /**
   * The {@link TreatyStatement} for the proposed {@link Treaty} which, if
   * valid, implies the value is current.
   */
  public TreatyStatement predicate;

  /**
   * A set of weak statistics to use if activating the treaty for the first
   * time.
   */
  public StatsMap weakStats;

  /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyProposal}
   * @param treaty
   *        the value's associated {@link Treaty}
   */
  public WarrantyProposal(fabric.lang.Object value, Metric metric,
      TreatyStatement predicate, StatsMap weakStats) {
    this.value = value;
    this.metric = metric;
    this.predicate = predicate;
    this.weakStats = weakStats;
  }

  public static WarrantyProposal newProposal(fabric.lang.Object value,
      Metric metric, TreatyStatement predicate) {
    return new WarrantyProposal(value, metric, predicate,
        StatsMap.emptyStats());
  }

  public static WarrantyProposal newProposal(fabric.lang.Object value,
      Metric metric, TreatyStatement predicate, StatsMap weakStats) {
    return new WarrantyProposal(value, metric, predicate, weakStats);
  }

  @Override
  public String toString() {
    return "WarrantyVal(" + value + ", " + metric + ", " + predicate + ")";
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
    if (metric != null) {
      out.writeBoolean(true);
      out.writeUTF(metric.$getStore().name());
      out.writeLong(metric.$getOnum());
    } else {
      out.writeBoolean(false);
    }
    if (predicate != null) {
      out.writeBoolean(true);
      predicate.write(out);
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
      Store s = Worker.getWorker().getStore(in.readUTF());
      metric = new Metric._Proxy(s, in.readLong());
    }
    if (in.readBoolean()) {
      predicate = TreatyStatement.read(in);
    }
    weakStats = new StatsMap(in);
  }

  private void readObjectNoData() throws ObjectStreamException {
    value = null;
    metric = null;
    predicate = null;
    weakStats = StatsMap.emptyStats();
  }
}
