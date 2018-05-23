package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

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
  public long calculateExpiry(MetricTreaty treaty) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated, witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty) {
    long calculated = Long.MAX_VALUE;
    for (TreatyRef witness : witnesses) {
      calculated = Math.min(calculated, witness.get().getExpiry());
    }
    return calculated;
  }

  @Override
  protected void writeData(DataOutput out) throws IOException {
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
}
