package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.FastSerializable;
import fabric.worker.metrics.treaties.MetricTreaty;

/**
 * Method of enforcement for a treaty.
 */
public abstract class EnforcementPolicy implements FastSerializable {
  public static enum Kind {
    NONE {
      @Override
      protected NoPolicy read(DataInput in) throws IOException {
        return new NoPolicy(in);
      }
    },
    DIRECT {
      @Override
      protected DirectPolicy read(DataInput in) throws IOException {
        return new DirectPolicy(in);
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
   * Utility for getting the policy determined expiration.
   */
  public abstract long calculateExpiry(MetricTreaty treaty);

  /**
   * Utility for getting the policy determined expiration.
   */
  public abstract long updatedExpiry(MetricTreaty oldTreaty);
}
