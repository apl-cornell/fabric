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
  private final double intervalEst;
  private final double velocityEst;
  private final double noiseEst;
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
    velocityEst = startDelta / startInterval;
    noiseEst = 0;
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
    value = newValue;

    if (useEstimation) {
      if (old.samples == 0) {
        // XXX: Don't do much here, we haven't seen any updates beyond the
        // starting value.
        intervalEst = startInterval;
        velocityEst = startDelta / startInterval;
        noiseEst = 0.0;
      } else if (old.samples == 1) {
        // Avoid divide by 0 for the first interval, assume it's been half a ms if
        // it's a zero diff.
        double dt = Math.max(0.5, curTime - old.lastUpdate);
        intervalEst = dt;
        velocityEst = dx / dt;
        noiseEst = 0.0;
      } else {
        // No need to worry about 0, we're now averaging and guaranteeing first
        // value is nonzero.
        double dt = curTime - old.lastUpdate;
        double alpha = old.getCurAlpha();
        intervalEst = (1.0 - alpha) * old.intervalEst + alpha * dt;
        //double newV = dx / Math.max(Math.min(intervalEst, 0.5), dt);
        double newV = dx / intervalEst;
        double oldVelocity = old.velocityEst;
        velocityEst = (1.0 - alpha) * old.velocityEst + alpha * newV;
        noiseEst = (1.0 - alpha) * old.noiseEst
            + alpha * (newV - velocityEst) * (newV - oldVelocity);
        //noiseEst = (1.0 - alpha) * noiseEst +
        //  alpha * Math.pow(dx - velocityEst * dt, 2) / intervalEst;
      }
      lastUpdate = curTime;
      samples = old.samples + 1;
    } else {
      intervalEst = old.intervalEst;
      velocityEst = old.velocityEst;
      noiseEst = old.noiseEst;
      lastUpdate = curTime;
      samples = old.samples;
    }
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
   * Compute an estimated velocity, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated velocity.
   */
  public double getVelocityEstimate() {
    // Account for time that's passed since last observation.
    if (samples <= 1) return 0.0;
    return updatedVelocity(velocityEst, intervalEst, samples, lastUpdate,
        System.currentTimeMillis());
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
    if (samples <= 1) return 0.0;
    return updatedNoise(velocityEst, noiseEst, intervalEst, samples, lastUpdate,
        System.currentTimeMillis());
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

  private double getCurAlpha() {
    return getAlpha(samples);
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
    this.lastUpdate =
        (long) (System.currentTimeMillis() - (0.5 * this.intervalEst));
    this.startInterval = intervalEst;

    // Velocity
    this.velocityEst = presets.getVelocityEst();
    this.startDelta = velocityEst * intervalEst;

    // Estimation
    this.noiseEst = presets.getNoiseEst();
    this.samples = presets.getSamples();
  }

  /**
   * Utility for converting a stale velocity estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedVelocity(double velocityEst, double intervalEst,
      long samples, long lastUpdate, long curTime) {
    if (samples <= 1) return velocityEst;
    if ((curTime - lastUpdate) <= (intervalEst / 2.0)) return velocityEst;
    double alpha = getAlpha(samples);
    return (1.0 - alpha) * velocityEst;
  }

  /**
   * Utility for converting a stale noise estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedNoise(double velocityEst, double noiseEst,
      double intervalEst, long samples, long lastUpdate, long curTime) {
    if (samples <= 1) return noiseEst;
    if ((curTime - lastUpdate) <= (intervalEst / 2.0)) return noiseEst;
    double alpha = getAlpha(samples);
    // Assume we're halfway into a window with no updates at this point.
    double v = (1.0 - alpha) * velocityEst;
    return (1.0 - alpha) * noiseEst + alpha * v * velocityEst;
  }
}
