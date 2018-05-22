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
      protected EnforcementPolicy read(DataInput in) throws IOException {
        // TODO
        return null;
      }
    },
    WITNESS {
      @Override
      protected EnforcementPolicy read(DataInput in) throws IOException {
        // TODO
        return null;
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
  public void write(DataOutput out) throws IOException {
    out.writeByte(kind.ordinal());
    writeData(out);
  }

  protected abstract void writeData(DataOutput out) throws IOException;

  /**
   * Utility for getting the policy determined expiration.
   */
  public abstract long calculateExpiry(MetricTreaty treaty);

  /**
   * Utility for getting the policy determined expiration.
   */
  public abstract long updatedExpiry(MetricTreaty oldTreaty);
}
