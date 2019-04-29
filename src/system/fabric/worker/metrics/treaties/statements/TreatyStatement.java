package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;

/**
 * A statement treaties can express on a metric
 */
public abstract class TreatyStatement
    implements FastSerializable, Serializable {

  /**
   * Utility enum for differentiating kinds of TreatyStatement in serialized
   * representation.
   */
  public static enum Kind {
    THRESHOLD {
      @Override
      protected ThresholdStatement read(DataInput in) throws IOException {
        return new ThresholdStatement(in);
      }
    },
    EQUALITY {
      @Override
      protected EqualityStatement read(DataInput in) throws IOException {
        return new EqualityStatement(in);
      }
    };

    /**
     * Deserialize a specific kind of TreatyStatement.
     */
    protected abstract TreatyStatement read(DataInput in) throws IOException;
  }

  /**
   * Hedged expiry that will be assigned for the given metric and weak stats.
   */
  public abstract long hedgedExpiry(Metric m, long currentTime,
      StatsMap weakStats);

  /**
   * Projected "true" expiry for the given metric and weak stats.
   */
  public abstract long hedgedEstimate(Metric m, long currentTime,
      StatsMap weakStats);

  /**
   * @param m the metric we want a direct expiry relative to.
   * @return the expiry to be used as determined by the value of m
   */
  public abstract long directExpiry(Metric m, StatsMap weakStats);

  /**
   * @param m the metric we want a true expiry relative to.
   * @return the "true" expiry at which the statement will become false in the
   * absence of updates to m.
   */
  public abstract long trueExpiry(Metric m, StatsMap weakStats);

  /**
   * @param v the value we want a true expiry relative to.
   * @return the "true" expiry at which the statement will become false relative
   * to v.
   */
  public abstract long trueExpiry(double v);

  /**
   * Write out the byte specifying the statement kind.
   */
  public abstract void writeStatementKind(DataOutput out) throws IOException;

  /**
   * Write out the specifics of the statement type.
   */
  public abstract void writeStatementData(DataOutput out) throws IOException;

  /**
   * Check if this statement implies another statement.
   */
  public abstract boolean implies(TreatyStatement stmt);

  /**
   * Get a new policy for enforcing this statement on the given metric.
   */
  public abstract EnforcementPolicy getNewPolicy(Metric m, long currentTime,
      StatsMap weakStats);

  /**
   * Get a new policy for enforcing this statement on the given metric.
   */
  public EnforcementPolicy getNewPolicy(Metric m, StatsMap weakStats) {
    return getNewPolicy(m, System.currentTimeMillis(), weakStats);
  }

  /**
   * Get a proxy treaty for the same statement on the given store.
   */
  public abstract Treaty getProxy(Metric m, Store s);

  /**
   * Utility to read a TreatyStatement off a DataInput (for example, when
   * reading off of a network message).
   */
  public static TreatyStatement read(DataInput in) throws IOException {
    return Kind.values()[in.readByte()].read(in);
  }

  @Override
  public final void write(DataOutput out) throws IOException {
    writeStatementKind(out);
    writeStatementData(out);
  }

  /**
   * Check if the statement is true of the given metric.
   */
  public abstract boolean check(Metric m);
}
