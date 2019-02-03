package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.metrics.treaties.Treaty;
import fabric.worker.metrics.StatsMap;

/**
 * Method of enforcement for a treaty.
 */
@SuppressWarnings("serial")
public abstract class EnforcementPolicy
    implements FastSerializable, Serializable {
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

  public static EnforcementPolicy read(DataInput in) throws IOException {
    return Kind.values()[in.readByte()].read(in);
  }

  @Override
  public final void write(DataOutput out) throws IOException {
    writeKind(out);
    writePolicyData(out);
  }

  /** Write out the byte to identify the type. */
  protected abstract void writeKind(DataOutput out) throws IOException;

  /** Write out the contents. */
  protected abstract void writePolicyData(DataOutput out) throws IOException;

  /**
   * Utility for getting the policy determined expiration, no consideration for
   * existing advertised expiry.
   */
  public abstract long calculateExpiry(Treaty treaty, StatsMap weakStats);

  /**
   * Utility for getting the policy determined expiration, assuming this is an
   * update from a state already using this policy.
   */
  public abstract long updatedExpiry(Treaty oldTreaty,
      StatsMap weakStats);

  /**
   * Activate anything that needs activation for this policy to work.
   */
  public abstract void activate(StatsMap weakStats);

  /**
   * Add the given treaty as an observer of the values being used for this
   * policy.
   */
  public abstract void apply(Treaty t);

  /**
   * Remove the given treaty as an observer of the values being used for this
   * policy.
   */
  public abstract void unapply(Treaty t);

  /**
   * Shift from this policy to the given policy on the given Treaty.
   */
  public abstract void shiftPolicies(Treaty t,
      EnforcementPolicy newPolicy);
}
