package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Method of enforcement for a treaty.
 */
public abstract class EnforcementPolicy implements FastSerializable {
  public static enum Kind {
    NONE {
      @Override
      protected NoPolicy read(DataInput in) throws IOException {
        return NoPolicy.singleton;
      }
    },
    DIRECT {
      @Override
      protected DirectPolicy read(DataInput in) throws IOException {
        return DirectPolicy.singleton;
      }
    },
    WITNESS {
      @Override
      protected WitnessPolicy read(DataInput in) throws IOException {
        return new WitnessPolicy(in);
      }
    };

    protected abstract EnforcementPolicy read(DataInput in) throws IOException;
  }

  private final Kind kind;

  public EnforcementPolicy(Kind kind) {
    this.kind = kind;
  }

  public static EnforcementPolicy read(DataInput in) throws IOException {
    return Kind.values()[in.readByte()].read(in);
  }

  @Override
  public final void write(DataOutput out) throws IOException {
    out.writeByte(kind.ordinal());
    writePolicyData(out);
  }

  protected abstract void writePolicyData(DataOutput out) throws IOException;

  /**
   * Utility for getting the policy determined expiration, no consideration for
   * existing advertised expiry.
   */
  public abstract long calculateExpiry(MetricTreaty treaty, StatsMap weakStats);

  /**
   * Utility for getting the policy determined expiration, assuming this is an
   * update from a state already using this policy.
   */
  public abstract long updatedExpiry(MetricTreaty oldTreaty,
      StatsMap weakStats);

  /**
   * Activate anything that needs activation for this policy to work.
   */
  public abstract void activate(StatsMap weakStats);

  /**
   * Add the given treaty as an observer of the values being used for this
   * policy.
   */
  public abstract void apply(MetricTreaty t);

  /**
   * Remove the given treaty as an observer of the values being used for this
   * policy.
   */
  public abstract void unapply(MetricTreaty t);
}
