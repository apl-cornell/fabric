package fabric.worker.metrics.treaties.enforcement;

import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Stand-in when there is no method of enforcement being used.
 */
public class NoPolicy extends EnforcementPolicy {

  public static final NoPolicy singleton = new NoPolicy();

  private NoPolicy() {
    super(EnforcementPolicy.Kind.NONE);
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty, StatsMap weakStats) {
    return 0;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty, StatsMap weakStats) {
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
}
