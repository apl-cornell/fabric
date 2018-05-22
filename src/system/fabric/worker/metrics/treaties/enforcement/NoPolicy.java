package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Stand-in when there is no method of enforcement being used.
 */
public class NoPolicy extends EnforcementPolicy {

  public NoPolicy() {
    super(EnforcementPolicy.Kind.NONE);
  }

  public NoPolicy(DataInput in) {
    super(EnforcementPolicy.Kind.NONE);
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty) {
    return 0;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty) {
    return 0;
  }

  @Override
  protected void writeData(DataOutput out) throws IOException {
  }
}
