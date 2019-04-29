package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.transaction.TransactionManager;

/**
 * {@link TreatyStatement} for threshold bounds.
 */
public class ThresholdStatement extends TreatyStatement
    implements Serializable {

  // Number of standard deviations to consider for hedging.
  private static final double HEDGE_FACTOR = 3.0;

  private double rate;
  private double base;

  public ThresholdStatement(double rate, double base) {
    this.rate = rate;
    this.base = base;
  }

  public ThresholdStatement(double rate, double base, long startTime) {
    this(rate, base - (rate * startTime));
  }

  public ThresholdStatement(DataInput in) throws IOException {
    this.rate = in.readDouble();
    this.base = in.readDouble();
  }

  /**
   * Utility factory method because signatures don't allow native constructors.
   */
  public static ThresholdStatement create(double rate, double base) {
    return new ThresholdStatement(rate, base);
  }

  /**
   * Utility factory method because signatures don't allow native constructors.
   */
  public static ThresholdStatement create(double rate, double base,
      long startTime) {
    return new ThresholdStatement(rate, base, startTime);
  }

  public double rate() {
    return rate;
  }

  public double base() {
    return base;
  }

  @Override
  public void writeStatementKind(DataOutput out) throws IOException {
    out.writeByte(Kind.THRESHOLD.ordinal());
  }

  @Override
  public void writeStatementData(DataOutput out) throws IOException {
    out.writeDouble(rate);
    out.writeDouble(base);
  }

  private double curBound(long time) {
    return rate * time + base;
  }

  private static boolean checkBound(double rate, double base, double value,
      long time) {
    return value >= (rate * time + base);
  }

  private boolean checkBound(double value, long time) {
    return value >= curBound(time);
  }

  /**
   * Hedged expiry time that will be used, avoiding retractions due to expected
   * updates.
   *
   * Essentially a static version of {@link #directExpiry(Metric,StatsMap)}
   */
  public static long hedgedExpiry(Metric m, double rate, double base, long time,
      StatsMap weakStats) {
    // Use weak stats because this is just an estimate.
    double x = m.value(weakStats);
    double v = m.velocity(weakStats);
    double n = m.noise(weakStats);
    double b = rate * time + base;

    // True expiry: time this would fail with no changes to the current
    // value. This is the latest time we can safely advertise as the
    // expiration.
    long hedgedResult = trueExpiry(rate, base, x, time);

    // Account for the desired number of standard deviations and scale based
    // on how long trueExpiry is from here.
    n *= (HEDGE_FACTOR * HEDGE_FACTOR);
    //if (hedgedResult < Long.MAX_VALUE) {
    //  // Use enough standard deviations that we actually might cross the
    //  // boundary, if the set amount wasn't enough.
    //  n = Math.max(n, 4 * (v - rate) * (x - b));
    //}

    // Solving for extremal point
    double minYs = 0.0;
    long min = -1;
    if (v > 0) {
      minYs = x - (n / (4 * v));
      min = trueExpiry(rate, base, minYs, time);

      // If the extremal point on the hedged curve is valid and below the
      // current point, use the time for that.
      if (minYs < x && checkBound(rate, base, minYs, time)) {
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
        double first = factor * (constant + discriminant);
        double second = factor * (constant - discriminant);
        if (first > 0 || second > 0) {
          if (first < 0) {
            intersect = (long) second;
          } else if (second < 0) {
            intersect = (long) first;
          } else {
            intersect = (long) Math.min(first, second);
          }
          hedgedResult = Math.min(time + intersect, hedgedResult);
        }
      }
    } else if (n > 0) {
      // Intersection found in notes.
      hedgedResult = Math.min(hedgedResult, (time + (long) (mb * mb / n)));
    }

    return hedgedResult;
  }

  /**
   * Estimated time the treaty will last, given current trends.
   */
  public static long hedgedEstimate(Metric m, double rate, double base,
      long time, StatsMap weakStats) {
    // Use weak stats because this is just an estimate.
    double x = m.value(weakStats);
    double v = m.velocity(weakStats);
    double n = m.noise(weakStats);
    double b = rate * time + base;

    // If the threshold already is above the value, it's dead.
    if (x < b) return 0;
    if (x == b) return time;

    // Use max long in the absence of any intersects.
    long hedgedResult = Long.MAX_VALUE;

    // Account for the desired number of standard deviations
    n *= (HEDGE_FACTOR * HEDGE_FACTOR);

    // Unlike real expiry, don't bother with inflating the noise.
    // Unlike real expiry, don't bother with min, that's just to hedge better.

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
        double first = factor * (constant + discriminant);
        double second = factor * (constant - discriminant);
        if (first > 0 || second > 0) {
          if (first < 0) {
            intersect = (long) second;
          } else if (second < 0) {
            intersect = (long) first;
          } else {
            intersect = (long) Math.min(first, second);
          }
          hedgedResult = Math.min(time + intersect, hedgedResult);
        }
      }
    } else if (n > 0) {
      // Intersection found in notes.
      hedgedResult = Math.min(hedgedResult, (time + (long) (mb * mb / n)));
    }

    return hedgedResult;
  }

  /**
   * The hedged expiry set to avoid expected updates triggering a retraction.
   *
   * The trajectory curve mentioned below is the curve which describes the
   * metric's velocity, starting from the current value, and subtracting
   * HEDGE_FACTOR standard deviations, as determined by the noise.
   *
   * This uses the following logic:
   * <ol>
   *    <li>If the metric is already below the threshold, return 0.</li>
   *    <li>If there's a local minima on the curve for the metric's trajectory,
   *    return the time at which this threshold would pass that value.</li>
   *    <li>If there's an intersection between the trajectory curve, return that
   *    time.</li>
   *    <li>If the threshold has positive rate, return the time at which this
   *    threshold would pass the current value</li>
   *    <li>Otherwise, return Long.MAX_VALUE.</li>
   * </ol>
   */
  @Override
  public long directExpiry(Metric m, StatsMap weakStats) {
    long time = System.currentTimeMillis();
    // Always use *correct* value for value
    double x = m.value();
    // Use weak v and n, x is the only part that needs to be exact.
    double v = m.velocity(weakStats);
    double n = m.noise(weakStats);
    double b = rate * time + base;

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
    //if (hedgedResult < Long.MAX_VALUE) {
    //  // Use enough standard deviations that we actually might cross the
    //  // boundary, if the set amount wasn't enough.
    //  n = Math.max(n, 4 * (v - rate) * (x - b));
    //}

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
        double first = factor * (constant + discriminant);
        double second = factor * (constant - discriminant);
        if (first > 0 || second > 0) {
          if (first < 0) {
            intersect = (long) second;
          } else if (second < 0) {
            intersect = (long) first;
          } else {
            intersect = (long) Math.min(first, second);
          }
          hedgedResult = Math.min(time + intersect, hedgedResult);
        }
      }
    } else if (n > 0) {
      // Intersection found in notes.
      hedgedResult = Math.min(hedgedResult, (time + (long) (mb * mb / n)));
    }

    return hedgedResult;
  }

  /**
   * The expiry if there are no further updates.
   */
  public static long trueExpiry(double rate, double base, double value,
      long time) {
    double curBound = rate * time + base;
    if (value < curBound) {
      return 0;
    } else if (rate <= 0) {
      return Long.MAX_VALUE;
    } else {
      return time + (long) Math.floor((value - curBound) / rate);
    }
  }

  /**
   * The expiry if there are no further updates.
   */
  public long trueExpiry(double value, long time) {
    double curBound = rate * time + base;
    if (value < curBound) {
      return 0;
    } else if (rate <= 0) {
      return Long.MAX_VALUE;
    } else {
      return time + (long) Math.floor((value - curBound) / rate);
    }
  }

  /**
   * The expiry if there are no further updates.
   */
  @Override
  public long trueExpiry(Metric m, StatsMap weakStats) {
    return trueExpiry(m.value(), System.currentTimeMillis());
  }

  @Override
  public boolean implies(TreatyStatement stmt) {
    if (stmt instanceof ThresholdStatement) {
      ThresholdStatement other = (ThresholdStatement) stmt;
      return base > other.base && rate > other.rate;
    }
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof ThresholdStatement
        && rate == ((ThresholdStatement) obj).rate
        && base == ((ThresholdStatement) obj).base);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(rate) ^ Double.hashCode(base);
  }

  @Override
  public String toString() {
    return ">= " + rate + " * t + " + base;
  }

  @Override
  public EnforcementPolicy getNewPolicy(Metric m, long currentTime,
      StatsMap weakStats) {
    return m.thresholdPolicy(rate, base, currentTime, weakStats, m.$getStore());
  }

  @Override
  public Treaty getProxy(Metric m, Store s) {
    return m.getProxy(s).getThresholdTreaty(rate, base);
  }

  @Override
  public long trueExpiry(double v) {
    return trueExpiry(v, System.currentTimeMillis());
  }

  @Override
  public boolean check(Metric m) {
    double value = m.value();
    boolean result =
        checkBound(this.rate, this.base, value, System.currentTimeMillis());
    if (result) {
      TransactionManager.getInstance().registerExpiryUse(trueExpiry(value));
    }
    return result;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeDouble(this.rate);
    out.writeDouble(this.base);
  }

  private void readObject(java.io.ObjectInputStream in) throws IOException {
    this.rate = in.readDouble();
    this.base = in.readDouble();
  }

  private void readObjectNoData() {
    // This shouldn't happen.
    this.rate = 0;
    this.base = 0;
  }

  @Override
  public long hedgedEstimate(Metric m, long currentTime, StatsMap weakStats) {
    return hedgedEstimate(m, this.rate, this.base, currentTime, weakStats);
  }

  @Override
  public long hedgedExpiry(Metric m, long currentTime, StatsMap weakStats) {
    return hedgedExpiry(m, this.rate, this.base, currentTime, weakStats);
  }
}
