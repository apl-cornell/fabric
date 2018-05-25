package fabric.worker.metrics.treaties.enforcement;

import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
public class DirectPolicy extends EnforcementPolicy {

  public static final DirectPolicy singleton = new DirectPolicy();

  private DirectPolicy() {
    super(EnforcementPolicy.Kind.DIRECT);
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty, StatsMap weakStats) {
    return treaty.statement.directExpiry(treaty.getMetric(), weakStats);
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty, StatsMap weakStats) {
    // Update if either we're advertising too optimistic of an expiry right
    // now or we're close enough to the advertised expiry to start ramping
    // up.
    return (oldTreaty.expiry > oldTreaty.statement
        .trueExpiry(oldTreaty.getMetric(), weakStats))
        || (oldTreaty.expiry
            - System.currentTimeMillis() < MetricTreaty.UPDATE_THRESHOLD)
                ? calculateExpiry(oldTreaty, weakStats)
                : oldTreaty.expiry;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    // Do nothing.
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof DirectPolicy;
  }

  @Override
  public int hashCode() {
    return EnforcementPolicy.Kind.DIRECT.ordinal();
  }

  @Override
  public String toString() {
    return "enforced by watching metric";
  }

  @Override
  public void activate(StatsMap weakStats) {
    // Do nothing.
  }
}
