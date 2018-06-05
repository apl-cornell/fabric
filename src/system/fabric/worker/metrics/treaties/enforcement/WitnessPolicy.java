package fabric.worker.metrics.treaties.enforcement;

import static fabric.common.Logging.METRICS_LOGGER;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;
import fabric.worker.transaction.TransactionManager;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
public class WitnessPolicy extends EnforcementPolicy {

  private final TreatyRef[] witnesses;
  private String str;

  public WitnessPolicy(MetricTreaty[] witnesses) {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = new TreatyRef[witnesses.length];
    for (int i = 0; i < witnesses.length; i++) {
      this.witnesses[i] = new TreatyRef(witnesses[i]);
    }
    str = Arrays.toString(witnesses);
  }

  public WitnessPolicy(DataInput in) throws IOException {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = new TreatyRef[in.readInt()];
    for (int i = 0; i < this.witnesses.length; i++) {
      this.witnesses[i] = new TreatyRef(in);
    }
    this.str = in.readUTF();
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated,
          witness.get() == null ? 0 : witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated,
          witness.get() == null ? 0 : witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    out.writeInt(witnesses.length);
    for (TreatyRef witness : witnesses) {
      witness.write(out);
    }
    out.writeUTF(str);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof WitnessPolicy)) return false;
    WitnessPolicy other = (WitnessPolicy) obj;
    return Arrays.equals(witnesses, other.witnesses);
  }

  @Override
  public int hashCode() {
    return EnforcementPolicy.Kind.WITNESS.ordinal()
        ^ Arrays.deepHashCode(witnesses);
  }

  @Override
  public String toString() {
    return "enforced by " + str;
  }

  @Override
  public void activate(StatsMap weakStats) {
    for (TreatyRef witness : witnesses) {
      // Don't worry about missing witnesses, it's possible they were cleared
      // out and we're still resolving this.
      if (witness.get() == null) continue;
      witness.get().update(false, weakStats);
    }
  }

  @Override
  public void apply(MetricTreaty t) {
    // Observe the witnesses
    for (TreatyRef witness : witnesses) {
      if (witness == null) METRICS_LOGGER.log(Level.SEVERE,
          "A witness was null applying to {0}", t);
      if (witness.get() == null) METRICS_LOGGER.log(Level.SEVERE,
          "Witness {0} was a dead reference applying to {1} in {2}",
          new Object[] { witness, t,
              TransactionManager.getInstance().getCurrentTid() });
      witness.get().addObserver(t.getMetric(), t.getId());
    }
  }

  @Override
  public void unapply(MetricTreaty t) {
    // Stop observing the metric.
    for (TreatyRef witness : witnesses) {
      // Don't worry about missing witnesses, it's possible they were cleared
      // out anticipating this.
      if (witness.get() == null) continue;
      witness.get().removeObserver(t.getMetric(), t.getId());
    }
  }
}
