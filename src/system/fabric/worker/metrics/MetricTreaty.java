package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import fabric.common.FastSerializable;
import fabric.metrics.Metric;

/**
 * An inlineable representation of a treaty defined as a statement about a
 * metric.
 */
@SuppressWarnings("serial")
public class MetricTreaty implements FastSerializable, Serializable {

  /** The metric this treaty is over. */
  private final OidRef<Metric> metric;

  /**
   * The id of this treaty within the metric.  Used to reference treaties
   * through their associated metrics.
   */
  private final long id;

  /**
   * Kinds of statements treaties can express on a metric, to be associated with
   * the different subclasses of {@link MetricTreaty}.
   */
  public static abstract class TreatyStatement
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

    private final Kind kind;

    public TreatyStatement(Kind kind) {
      this.kind = kind;
    }

    /**
     * @param m the metric we want a direct expiry relative to.
     * @return the expiry to be used as determined by the value of m
     */
    public abstract long directExpiry(Metric m);

    /**
     * @param m the metric we want a true expiry relative to.
     * @return the "true" expiry at which the statement will become false in the
     * absence of updates to m.
     */
    public abstract long trueExpiry(Metric m);

    /**
     * Write out the specifics of the statement type.
     */
    public abstract void writeStatementData(DataOutput out) throws IOException;

    /**
     * Utility to read a TreatyStatement off a DataInput (for example, when
     * reading off of a network message).
     */
    public static TreatyStatement read(DataInput in) throws IOException {
      Kind k = Kind.values()[in.readByte()];
      return k.read(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
      out.writeByte(kind.ordinal());
      writeStatementData(out);
    }
  }

  /**
   * {@link TreatyStatement} for threshold bounds.
   */
  public static class ThresholdStatement extends TreatyStatement {

    // Number of standard deviations to consider for hedging.
    private static final double HEDGE_FACTOR = 3;

    private final double rate;
    private final double base;

    public ThresholdStatement(double rate, double base) {
      super(TreatyStatement.Kind.THRESHOLD);
      this.rate = rate;
      this.base = base;
    }

    public ThresholdStatement(double rate, double base, long startTime) {
      this(rate, base - (rate * startTime));
    }

    public ThresholdStatement(DataInput in) throws IOException {
      super(TreatyStatement.Kind.THRESHOLD);
      this.rate = in.readDouble();
      this.base = in.readDouble();
    }

    @Override
    public void writeStatementData(DataOutput out) throws IOException {
      out.writeDouble(rate);
      out.writeDouble(base);
    }

    private double curBound(long time) {
      return rate * time + base;
    }

    private boolean checkBound(double value, long time) {
      return value >= curBound(time);
    }

    @Override
    public long directExpiry(Metric m) {
      long time = System.currentTimeMillis();
      double b = rate * time + base;
      // Always use *correct* value for value
      double x = m.value();
      // Use weak v and n, x is the only part that needs to be exact.
      // TODO: reenable support for weak parameters.
      double v = m.velocity(/*useWeakParameters*/);
      double n = m.noise(/*useWeakParameters*/);

      // True expiry: time this would fail with no changes to the current
      // value. This is the latest time we can safely advertise as the
      // expiration.
      long hedgedResult = trueExpiry(m.value(), time);

      // Non-positive rate bounds don't need to be hedged, they should always
      // stay true in the absence of updates.
      if (rate <= 0) {
        return hedgedResult;
      }

      // Account for the desired number of standard deviations and scale based
      // on how long trueExpiry is from here.
      n *= (HEDGE_FACTOR * HEDGE_FACTOR);
      if (hedgedResult < Long.MAX_VALUE) {
        // Use enough standard deviations that we actually might cross the
        // boundary, if the set amount wasn't enough.
        n = Math.max(n, 4 * (v - rate) * (x - b));
      }

      // Solving for extremal point
      double minYs = 0.0;
      long min = -1;
      if (v > 0) {
        minYs = x - (n / (4 * v));
        min = trueExpiry(minYs, time);

        // If the extremal point on the hedged curve is valid and below the
        // current point, use the time for that.
        if (minYs < x && checkBound(minYs, time)) {
          hedgedResult = Math.min(min, hedgedResult);
          // Fast path, a min point will be earlier than an intersect, if
          // it exists and is above the bound.
          return hedgedResult;
        }
      }

      // Solving intersection by using rotated parametric formula
      double mb = x - b;
      double vr = v - rate;
      long intersect = -1;
      if (vr != 0) {
        // Intersection calculation found in notes.
        double factor = 1.0 / (2.0 * vr * vr);
        double constant = n - 2 * mb * vr;
        double discriminant = Math.sqrt(n) * Math.sqrt(n - 4.0 * mb * vr);

        if (!Double.isNaN(discriminant)) {
          double first = factor * (constant + discriminant) + time;
          double second = factor * (constant - discriminant) + time;
          if (first > 0 || second > 0) {
            if (first < 0) {
              intersect = (long) second;
            } else if (second < 0) {
              intersect = (long) first;
            } else {
              intersect = (long) Math.min(first, second);
            }
            hedgedResult = Math.min(intersect, hedgedResult);
          }
        }
      } else if (n > 0) {
        // Intersection found in notes.
        hedgedResult = Math.min(hedgedResult, ((long) (mb * mb / n)));
      }

      return hedgedResult;
    }

    public long trueExpiry(double value, long time) {
      double curBound = rate * time + base;
      if (value < curBound) {
        return 0;
      } else if (rate <= 0) {
        return Long.MAX_VALUE;
      } else {
        return (long) Math.floor((value - curBound) / rate);
      }
    }

    @Override
    public long trueExpiry(Metric m) {
      return trueExpiry(m.value(), System.currentTimeMillis());
    }
  }

  /**
   * {@link TreatyStatement} for equality bounds.
   */
  public static class EqualityStatement extends TreatyStatement {
    private final double value;

    public EqualityStatement(double value) {
      super(TreatyStatement.Kind.EQUALITY);
      this.value = value;
    }

    public EqualityStatement(DataInput in) throws IOException {
      this(in.readDouble());
    }

    @Override
    public void writeStatementData(DataOutput out) throws IOException {
      out.writeDouble(this.value);
    }

    @Override
    public long directExpiry(Metric m) {
      return trueExpiry(m);
    }

    @Override
    public long trueExpiry(Metric m) {
      if (m.value() < value) {
        return 0;
      } else {
        return Long.MAX_VALUE;
      }
    }
  }

  private final TreatyStatement statement;

  /** The observers of this treaty. */
  private final ImmutableObserverSet observers;

  /**
   * Method of enforcement.
   */
  public static enum EnforcementPolicy {
    /** No enforcement method, the treaty is dead. */
    NONE {
      @Override
      protected long calculateExpiry(MetricTreaty treaty) {
        return 0;
      }

      @Override
      protected long updatedExpiry(MetricTreaty oldTreaty) {
        return oldTreaty.expiry;
      }
    },
    /** Directly checks the associated metric's value. */
    DIRECT {
      @Override
      protected long calculateExpiry(MetricTreaty treaty) {
        return treaty.statement.directExpiry(treaty.getMetric());
      }

      @Override
      protected long updatedExpiry(MetricTreaty oldTreaty) {
        // Update if either we're advertising too optimistic of an expiry right now
        // or we're close enough to the advertised expiry to start ramping up.
        return (oldTreaty.expiry > oldTreaty.statement
            .trueExpiry(oldTreaty.getMetric())
            || oldTreaty.expiry - System.currentTimeMillis() < UPDATE_THRESHOLD)
                ? calculateExpiry(oldTreaty)
                : oldTreaty.expiry;
      }
    },
    /** Rely on other treaties. */
    WITNESS {
      @Override
      protected long calculateExpiry(MetricTreaty treaty) {
        long calculated = Long.MAX_VALUE;
        for (TreatyRef witness : treaty.policyData) {
          calculated = Math.min(calculated, witness.get().getExpiry());
        }
        return calculated;
      }

      @Override
      protected long updatedExpiry(MetricTreaty oldTreaty) {
        long calculated = Long.MAX_VALUE;
        for (TreatyRef witness : oldTreaty.policyData) {
          calculated = Math.min(calculated, witness.get().getExpiry());
        }
        return calculated;
      }
    };

    /**
     * Utility for getting the policy determined expiration.
     */
    protected abstract long calculateExpiry(MetricTreaty treaty);

    /**
     * Utility for getting the policy determined expiration.
     */
    protected abstract long updatedExpiry(MetricTreaty oldTreaty);
  }

  /** The policy being used to enforce this treaty. */
  private final EnforcementPolicy policy;

  /** The set of witnesses, if using WITNESS policy, otherwise null. */
  private final TreatyRef[] policyData;

  /** The expiration time. */
  private final long expiry;

  private MetricTreaty(Metric metric, long id, TreatyStatement statement,
      ImmutableObserverSet observers, EnforcementPolicy policy,
      TreatyRef[] policyData) {
    this.metric = new OidRef<>(metric);
    this.id = id;
    this.statement = statement;
    this.observers = observers;
    this.policy = policy;
    this.policyData = policyData;
    this.expiry = policy.calculateExpiry(this);
  }

  /**
   * Deserialization constructor.
   *
   * Metric is provided on the side to avoid unnecessary repeated references in
   * serialized TreatySet.
   */
  public MetricTreaty(OidRef<Metric> m, DataInput in) throws IOException {
    this.metric = m;
    this.id = in.readLong();
    this.statement = TreatyStatement.read(in);
    this.observers = new ImmutableObserverSet(in);
    this.policy = EnforcementPolicy.values()[in.readByte()];

    int size = in.readInt();
    this.policyData = new TreatyRef[size];
    for (int i = 0; i < size; i++) {
      this.policyData[i] = new TreatyRef(in);
    }

    this.expiry = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    // Don't serialize the metric, it'll be provided on deserialization.
    //this.metric.write(out);
    out.writeLong(id);
    this.statement.write(out);
    this.observers.write(out);
    out.writeByte(this.policy.ordinal());
    out.writeInt(this.policyData.length);
    for (TreatyRef datum : this.policyData) {
      datum.write(out);
    }
    out.writeLong(this.expiry);
  }

  /**
   * Constructor for the update step.
   */
  private MetricTreaty(MetricTreaty original) {
    this.metric = original.metric;
    this.id = original.id;
    this.statement = original.statement;
    this.observers = original.observers;
    this.policy = original.policy;
    this.policyData =
        Arrays.copyOf(original.policyData, original.policyData.length);
    this.expiry = this.policy.updatedExpiry(original);
  }

  /**
   * Do any work needed to update the state of this treaty, returning the new
   * state.  If the state has not changed, it will return "this".
   */
  public MetricTreaty update() {
    // TODO: don't create a copy if there's no change?
    return new MetricTreaty(this);
  }

  /**
   * Check if the treaty is currently true.
   */
  public boolean valid() {
    return (expiry > System.currentTimeMillis());
  }

  /**
   * Maximum time in milliseconds before the currently advertised expiry that an
   * extension should be applied within.
   */
  private static long UPDATE_THRESHOLD = 1000;

  /**
   * @return the expiry.
   */
  public long getExpiry() {
    return expiry;
  }

  /**
   * @return the metric
   */
  public Metric getMetric() {
    return metric.get();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @return the observers
   */
  public ImmutableObserverSet getObservers() {
    return observers;
  }

  /**
   * @return the policy
   */
  public EnforcementPolicy getPolicy() {
    return policy;
  }
}
