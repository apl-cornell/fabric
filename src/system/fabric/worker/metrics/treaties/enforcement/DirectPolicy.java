package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
public class DirectPolicy extends EnforcementPolicy {

  public DirectPolicy() {
    super(EnforcementPolicy.Kind.DIRECT);
  }

  public DirectPolicy(DataInput in) {
    super(EnforcementPolicy.Kind.DIRECT);
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty) {
    return treaty.statement.directExpiry(treaty.getMetric());
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty) {
    // Update if either we're advertising too optimistic of an expiry right
    // now or we're close enough to the advertised expiry to start ramping
    // up.
    return (oldTreaty.expiry > oldTreaty.statement
        .trueExpiry(oldTreaty.getMetric()))
        || (oldTreaty.expiry
            - System.currentTimeMillis() < MetricTreaty.UPDATE_THRESHOLD)
                ? calculateExpiry(oldTreaty)
                : oldTreaty.expiry;
  }

  @Override
  protected void writeData(DataOutput out) throws IOException {
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
}
