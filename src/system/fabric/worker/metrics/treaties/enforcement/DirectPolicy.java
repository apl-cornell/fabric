package fabric.worker.metrics.treaties.enforcement;

import java.io.DataOutput;
import java.io.IOException;

import fabric.metrics.treaties.Treaty;
import fabric.worker.metrics.StatsMap;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
@SuppressWarnings("serial")
public class DirectPolicy extends EnforcementPolicy {

  public static final DirectPolicy singleton = new DirectPolicy();

  // Hide this.
  private DirectPolicy() {
  }

  @Override
  public long calculateExpiry(Treaty treaty, StatsMap weakStats) {
    return treaty.get$predicate().directExpiry(treaty.get$metric(), weakStats);
  }

  @Override
  public long updatedExpiry(Treaty oldTreaty, StatsMap weakStats) {
    // Update if either we're advertising too optimistic of an expiry right
    // now or we're close enough to the advertised expiry to start ramping
    // up.
    long currentTime = System.currentTimeMillis();
    long trueTime =
        oldTreaty.get$predicate().trueExpiry(oldTreaty.get$metric(), weakStats);
    if (trueTime < currentTime && currentTime <= oldTreaty.get$$expiry()) {
      // We can't actually enforce it.
      return 0;
    }
    long hedgedTime = calculateExpiry(oldTreaty, weakStats);
    if (oldTreaty.get$$expiry() <= trueTime) {
      // If the currently advertised time is still good, move to new
      // hedgedTime if it's larger.
      return Math.max(oldTreaty.get$$expiry(), hedgedTime);
    }
    // Otherwise, just move to new expiry.
    return hedgedTime;
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

  @Override
  public void apply(Treaty t) {
    // Observe the metric.
    t.get$metric().addObserver(t);
  }

  @Override
  public void unapply(Treaty t) {
    // Stop observing the metric.
    t.get$metric().removeObserver(t);
  }

  @Override
  public void shiftPolicies(Treaty t, EnforcementPolicy newPolicy) {
    // If the new policy is also direct, don't do anything.
    if (!(newPolicy instanceof DirectPolicy)) {
      unapply(t);
      newPolicy.apply(t);
    }
  }

  @Override
  protected void writeKind(DataOutput out) throws IOException {
    out.writeByte(Kind.DIRECT.ordinal());
  }
}
