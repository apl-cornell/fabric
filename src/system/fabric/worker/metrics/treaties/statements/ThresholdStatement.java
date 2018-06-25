package fabric.worker.metrics.treaties.statements;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;

/**
 * {@link TreatyStatement} for threshold bounds.
 */
public class ThresholdStatement extends TreatyStatement {

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

  private static boolean checkBound(double rate, double base, double value,
      long time) {
    return value >= (rate * time + base);
  }

  private boolean checkBound(double value, long time) {
    return value >= curBound(time);
  }

  public static long hedgedEstimate(Metric m, double rate, double base,
      long time, StatsMap weakStats) {
    double b = rate * time + base;
    // Use weak stats because this is just an estimate.
    double x = m.value(weakStats);
    double v = m.velocity(weakStats);
    double n = m.noise(weakStats);

    // True expiry: time this would fail with no changes to the current
    // value. This is the latest time we can safely advertise as the
    // expiration.
    long hedgedResult = trueExpiry(rate, base, x, time);

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

  @Override
  public long directExpiry(Metric m, StatsMap weakStats) {
    long time = System.currentTimeMillis();
    double b = rate * time + base;
    // Always use *correct* value for value
    double x = m.value();
    // Use weak v and n, x is the only part that needs to be exact.
    double v = m.velocity(weakStats);
    double n = m.noise(weakStats);

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
  public EnforcementPolicy getNewPolicy(Metric m, StatsMap weakStats) {
    return m.thresholdPolicy(rate, base, weakStats, m.$getStore());
  }

  @Override
  public MetricTreaty getProxy(Metric m, Store s) {
    return m.getProxy(s).getThresholdTreaty(rate, base);
  }
}
