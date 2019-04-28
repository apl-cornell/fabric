package fabric.worker.metrics;

import java.io.Serializable;
import java.util.Map;

import fabric.worker.Worker;

/**
 * Utility class for tracking the sample mean and sample standard deviation of a
 * value that is updated periodically.
 * <p>
 * This was originally based on a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 * <p>
 * That post assumed a stable distribution. This has since been modified to use
 * EWMA.
 */
public class RunningMetricStats implements Serializable {

  private final double value;
  private final double startDelta;
  private final double startInterval;
  /** E[dt] */
  private final double intervalEst;
  /** Var[dt] */
  private final double intervalNoise;
  /** E[dx] */
  private final double dxEst;
  /** Var[dx] */
  private final double dxNoise;
  /** E[dx * dt] */
  private final double prodEst;
  private final long lastUpdate;
  private final long samples;

  @Override
  public String toString() {
    return "[" + " value " + value + " samples " + samples + " lastUpdate "
        + lastUpdate + " intervalEst " + getIntervalEstimate() + " velocityEst "
        + getVelocityEstimate() + " noiseEst " + getNoiseEstimate() + " ]";
  }

  /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
  public RunningMetricStats(double startValue, double startDelta,
      double startInterval) {
    this.value = startValue;
    this.startDelta = startDelta;
    this.startInterval = startInterval;
    intervalEst = startInterval;
    dxEst = startDelta;
    prodEst = startInterval * startDelta;
    intervalNoise = 0;
    dxNoise = 0;
    lastUpdate = System.currentTimeMillis() - 1;
    samples = 0;
  }

  /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
  public static RunningMetricStats createRunningMetricStats(double startValue,
      double startDelta, double startInterval) {
    return new RunningMetricStats(startValue, startDelta, startInterval);
  }

  /**
   * Internal constructor for producing update copies.
   */
  private RunningMetricStats(RunningMetricStats old, double newValue,
      boolean useEstimation) {
    this.startDelta = old.startDelta;
    this.startInterval = old.startInterval;
    // TODO: Use later of creation and boot time for lastUpdate if this is the
    // first sample.
    // Update samples count
    long curTime = System.currentTimeMillis();
    double dx = newValue - old.value;
    double dT = curTime - old.lastUpdate;
    value = newValue;

    if (useEstimation) {
      double alpha = getAlpha(old.samples + 1);

      intervalEst = updatedMean(old.intervalEst, old.intervalNoise, dT, alpha);
      intervalNoise =
          updatedNoise(old.intervalEst, old.intervalNoise, dT, alpha);

      dxEst = updatedMean(old.dxEst, old.dxNoise, dx, alpha);
      dxNoise = updatedNoise(old.dxEst, old.dxNoise, dx, alpha);

      prodEst = updatedMean(old.prodEst, 0, dx * dT, alpha);

      lastUpdate = curTime;
      samples = old.samples + 1;
    } else {
      intervalEst = old.intervalEst;
      intervalNoise = old.intervalNoise;
      dxEst = old.dxEst;
      dxNoise = old.dxNoise;
      prodEst = old.prodEst;
      lastUpdate = curTime;
      samples = old.samples;
    }
  }

  /**
   * Updated mean
   */
  public static double updatedMean(double oldEst, double oldNoise,
      double newVal, double alpha) {
    return (1.0 - alpha) * oldEst + alpha * newVal;
  }

  /**
   * Updated variance
   */
  public static double updatedNoise(double oldEst, double oldNoise,
      double newVal, double alpha) {
    double delta1 = newVal - oldEst;
    double delta2 = newVal - updatedMean(oldEst, oldNoise, newVal, alpha);
    return (1.0 - alpha) * (oldNoise + alpha * delta1 * delta2);
  }

  /**
   * Reset estimation to just the startValue.
   */
  public RunningMetricStats reset() {
    return new RunningMetricStats(value, startDelta, startInterval);
  }

  /**
   * @return the current value.
   */
  public double getValue() {
    return value;
  }

  /**
   * Get estimate of the current dx vs dt covariance.
   */
  public double getDxDtCovariance() {
    return prodEst - (dxEst * intervalEst);
  }

  /**
   * Compute an estimated velocity, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated velocity.
   */
  public double getVelocityEstimate() {
    // Account for time that's passed since last observation.
    if (samples <= 1) return 0.0;
    return dxEst / intervalEst;
    //return (dxEst
    //    - (getDxDtCovariance() - (intervalNoise * dxEst) / intervalEst)
    //        / intervalEst)
    //    / intervalEst;
  }

  /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
  public double getNoiseEstimate() {
    // Assuming that there's 0 covariance between update intervals and update
    // values.
    //if (samples <= 1) return Double.MAX_VALUE;
    if (samples <= 1) return 0;
    return ((dxEst * dxEst) / (intervalEst * intervalEst))
        * ((dxNoise / (dxEst * dxEst))
            - (2 * getDxDtCovariance() / (dxEst * intervalEst))
            + (intervalNoise / (intervalEst * intervalEst)));
  }

  /**
   * @return the last update time.
   */
  public long getLastUpdate() {
    return lastUpdate;
  }

  /**
   * @return the last update time.
   */
  public double getIntervalEstimate() {
    return intervalEst;
  }

  /**
   * @return the number of samples taken.
   */
  public long getSamples() {
    return samples;
  }

  /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
  public RunningMetricStats update(double newVal) {
    return new RunningMetricStats(this, newVal,
        Worker.getWorker().config.useEstimation);
  }

  private static double getAlpha(long samples) {
    return Math.max(Worker.getWorker().config.alpha,
        1.0 / Math.max(1, samples));
  }

  /**
   * Support preloading based on a long key.
   *
   * Should be called right before the next update to ensure the lastUpdate
   * configuration is roughly accurate.
   */
  public RunningMetricStats preload(long key) {
    // Only bother if this is a fresh slate
    if (samples == 0) {
      Map.Entry<Long, PresetMetricStatistics> entry =
          Worker.getWorker().config.presets.floorEntry(key);
      // If there's a preset, load it in.
      if (entry != null && entry.getValue() != null) {
        return new RunningMetricStats(entry.getValue(), value);
      }
    }
    return this;
  }

  /**
   * Internal constructor for producing preloaded stats.
   */
  private RunningMetricStats(PresetMetricStatistics presets, double value) {
    // Value
    this.value = value;

    // Interval
    this.intervalEst = presets.getIntervalEst();
    this.intervalNoise = 0;
    this.lastUpdate =
        (long) (System.currentTimeMillis() - (0.5 * this.intervalEst));
    this.startInterval = intervalEst;

    // Value
    this.dxEst = presets.getVelocityEst() * intervalEst;
    // Assume 0 covariance and 0 interval Noise.
    this.dxNoise = presets.getNoiseEst() * intervalEst * intervalEst;
    this.startDelta = this.dxEst;

    // Estimation
    this.prodEst = this.intervalEst * this.dxEst; // Ensure 0 covariance.
    this.samples = presets.getSamples();
  }

  public static double updateInterval(final double velocityEst,
      final double intervalEst, final double noiseEst, final long samples,
      final long lastUpdate, final long updateTime, final double dx) {
    if (samples <= 0) {
      // XXX: Don't do much here, we haven't seen any updates beyond the
      // starting value.
      return intervalEst;
    } else if (samples == 1) {
      // Avoid divide by 0 for the first interval, assume it's been half a ms if
      // it's a zero diff.
      return Math.max(0.5, updateTime - lastUpdate);
    } else {
      double alpha = getAlpha(samples);
      double dt = updateTime - lastUpdate;
      return (1.0 - alpha) * intervalEst + alpha * dt;
    }
  }

  /*
  public static double updateInterval(RunningMetricStats old, long updateTime,
      double dx) {
    return updateInterval(old.velocityEst, old.intervalEst, old.noiseEst,
        old.samples, old.lastUpdate, updateTime, dx);
  }*/

  public static double updateVelocity(final double velocityEst,
      final double intervalEst, final double noiseEst, final long samples,
      final long lastUpdate, final long updateTime, final double dx) {
    if (samples == 0) {
      // XXX: Don't do much here, we haven't seen any updates beyond the
      // starting value.
      return velocityEst;
    } else if (samples == 1) {
      // Avoid divide by 0 for the first interval, assume it's been half a ms if
      // it's a zero diff.
      double dt = Math.max(0.5, updateTime - lastUpdate);
      return dx / dt;
    } else {
      double alpha = getAlpha(samples);

      double dt = updateTime - lastUpdate;
      double oldDx = velocityEst * intervalEst;
      double newIntervalEst = (1.0 - alpha) * intervalEst + alpha * dt;

      return ((1.0 - alpha) * oldDx + alpha * dx) / newIntervalEst;
      //return velocityEst + alpha * ((dx / Math.max(Math.min(0.5, newIntervalEst), dt)) - velocityEst);
    }
  }

  /*
  public static double updateVelocity(RunningMetricStats old, long updateTime,
      double dx) {
    return updateVelocity(old.velocityEst, old.intervalEst, old.noiseEst,
        old.samples, old.lastUpdate, updateTime, dx);
  }
  */

  public static double updateNoise(final double velocityEst,
      final double intervalEst, final double noiseEst, final long samples,
      final long lastUpdate, final long updateTime, final double dx) {
    if (samples <= 1) {
      return 0.0;
    } else {
      double alpha = getAlpha(samples);

      double dt = updateTime - lastUpdate;
      double oldDx = velocityEst * intervalEst;
      double newIntervalEst = (1.0 - alpha) * intervalEst + alpha * dt;

      double newVelocityEst =
          ((1.0 - alpha) * oldDx + alpha * dx) / newIntervalEst;

      // Noise is estimated population variance of dx / dt, so update as if
      // you're doing variance of dx (so difference between expected and
      // observed update based on time since last update) / estimate of dt
      return (1.0 - alpha) * noiseEst + (alpha
          * Math.abs((dx - newVelocityEst * dt) * (dx - velocityEst * dt))
          / newIntervalEst);
    }
  }

  /*
  public static double updateNoise(RunningMetricStats old, long updateTime,
      double dx) {
    return updateNoise(old.velocityEst, old.intervalEst, old.noiseEst,
        old.samples, old.lastUpdate, updateTime, dx);
  }
  */

  /**
   * Utility for converting a stale velocity estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedVelocity(double velocityEst, double intervalEst,
      long samples, long lastUpdate, long curTime) {
    if (samples <= 1) return velocityEst;
    long timeSince = curTime - lastUpdate;
    if (2 * timeSince <= intervalEst) return velocityEst;
    // Act as if we got a 0 dx update.
    return updateVelocity(velocityEst, intervalEst, 0, samples, lastUpdate,
        curTime, 0);
  }

  /**
   * Utility for converting a stale noise estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedNoise(double velocityEst, double noiseEst,
      double intervalEst, long samples, long lastUpdate, long curTime) {
    if (samples <= 1) return noiseEst;
    long timeSince = curTime - lastUpdate;
    if (2 * timeSince <= intervalEst) return noiseEst;
    // Pretend we're halfway into the window of an update that follows the
    // current estimated velocity.
    return updateNoise(velocityEst, intervalEst, noiseEst, samples, lastUpdate,
        curTime, 0);
  }
}
