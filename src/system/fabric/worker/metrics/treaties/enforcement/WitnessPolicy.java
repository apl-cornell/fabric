package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
public class WitnessPolicy extends EnforcementPolicy {

  private final TreatyRef[] witnesses;

  public WitnessPolicy(TreatyRef[] witnesses) {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = Arrays.copyOf(witnesses, witnesses.length);
  }

  public WitnessPolicy(MetricTreaty[] witnesses) {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = new TreatyRef[witnesses.length];
    for (int i = 0; i < witnesses.length; i++) {
      this.witnesses[i] = new TreatyRef(witnesses[i]);
    }
  }

  public WitnessPolicy(DataInput in) throws IOException {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = new TreatyRef[in.readInt()];
    for (int i = 0; i < this.witnesses.length; i++) {
      this.witnesses[i] = new TreatyRef(in);
    }
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated, witness.get() == null ? 0 : witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated, witness.get() == null ? 0 : witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    out.writeInt(witnesses.length);
    for (TreatyRef witness : witnesses) {
      witness.write(out);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof WitnessPolicy)) return false;
    WitnessPolicy other = (WitnessPolicy) obj;
    return Arrays.equals(witnesses, other.witnesses);
  }

  @Override
  public int hashCode() {
    return EnforcementPolicy.Kind.WITNESS.ordinal();
  }

  @Override
  public String toString() {
    return "enforced by " + Arrays.toString(witnesses);
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
