package fabric.worker.metrics;

import fabric.metrics.SampledMetric;
import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Represents a potential update to a metric.  Allows for checking if an update
 * can be applied while maintaining a postcondition.
 */
public class MetricUpdate {
  public final SampledMetric m;
  public final double newVal;

  public MetricUpdate(SampledMetric m, double newVal) {
    this.m = m;
    this.newVal = newVal;
  }

  /**
   * Check if the update would violate any local treaties.
   */
  public boolean violatesExistingTreaties() {
    for (MetricTreaty treaty : m.get$$treaties()) {
      // Update would require a retraction, so it "violates" an existing treaty.
      if (treaty.statement.trueExpiry(newVal) < treaty.expiry) return true;
    }
    return false;
  }

  /**
   * Apply the update
   */
  public void applyUpdate() {
    this.m.setValue(newVal);
  }
}
