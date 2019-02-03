package fabric.worker.metrics.treaties.enforcement;

import java.io.DataOutput;
import java.io.IOException;

import fabric.metrics.treaties.Treaty;
import fabric.worker.metrics.StatsMap;

/**
 * Stand-in when there is no method of enforcement being used.
 */
@SuppressWarnings("serial")
public class NoPolicy extends EnforcementPolicy {

  public static final NoPolicy singleton = new NoPolicy();

  // Hide this.
  private NoPolicy() {}

  @Override
  public long calculateExpiry(Treaty treaty, StatsMap weakStats) {
    return 0;
  }

  @Override
  public long updatedExpiry(Treaty oldTreaty, StatsMap weakStats) {
    return 0;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    // Do nothing.
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof NoPolicy;
  }

  @Override
  public int hashCode() {
    return EnforcementPolicy.Kind.NONE.ordinal();
  }

  @Override
  public String toString() {
    return "unenforced";
  }

  @Override
  public void activate(StatsMap weakStats) {
    // Do nothing.
  }

  @Override
  public void apply(Treaty t) {
    // Do nothing.
  }

  @Override
  public void unapply(Treaty t) {
    // Do nothing.
  }

  @Override
  public void shiftPolicies(Treaty t, EnforcementPolicy newPolicy) {
    // Just apply the new policy.
    newPolicy.apply(t);
  }

  @Override
  protected void writeKind(DataOutput out) throws IOException {
    out.writeByte(Kind.NONE.ordinal());
  }
}
