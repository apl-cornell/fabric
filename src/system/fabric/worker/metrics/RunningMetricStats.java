package fabric.worker.metrics;

import java.io.Serializable;
import java.util.Map;

import fabric.worker.Worker;

/**
 * Utility class for tracking the sample mean and sample standard deviation of a
 * value that is updated periodically.
 * <p>
 * This borrowed some ideas in a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 * <p>
 * That post assumed a stable distribution. Here we're using EWMA.
 */
public class RunningMetricStats implements Serializable {

  private final double value;
  private final double startDelta;
  private final double startInterval;
  /** E[dt] */
  private final double intervalEst;
  /** E[dx] */
  private final double dxEst;
  /** E[dx - v * dt] */
  private final double noiseEst;
  /** Var[dx - v * dt] */
  private final double noiseVar;
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
    noiseEst = 0.0;
    noiseVar = 0.0;
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
    // TODO: Might be better to use later of creation and boot time for
    // lastUpdate if this is the first sample.

    // Update samples count
    long curTime = System.currentTimeMillis();
    double dx = newValue - old.value;
    double dT = curTime - old.lastUpdate;
    value = newValue;

    if (useEstimation) {
      double alpha = getAlpha(old.samples);

      intervalEst = updatedMean(old.intervalEst, dT, alpha);
      dxEst = updatedMean(old.dxEst, dx, alpha);

      noiseEst =
          updatedMean(old.noiseEst, dx - old.getVelocityEstimate() * dT, alpha);
      noiseVar = updatedNoise(old.noiseEst, old.noiseVar,
          dx - old.getVelocityEstimate() * dT, alpha);

      lastUpdate = curTime;
      samples = old.samples + 1;
    } else {
      intervalEst = old.intervalEst;
      dxEst = old.dxEst;
      noiseEst = old.noiseEst;
      noiseVar = old.noiseVar;
      lastUpdate = curTime;
      samples = old.samples;
    }
  }

  /**
   * Updated mean
   */
  public static double updatedMean(double oldEst, double newVal, double alpha) {
    return (1.0 - alpha) * oldEst + alpha * newVal;
  }

  /**
   * Updated variance
   */
  public static double updatedNoise(double oldEst, double oldNoise,
      double newVal, double alpha) {
    double delta = newVal - oldEst;
    return (1.0 - alpha) * (oldNoise + alpha * delta * delta);
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
    if (samples <= 1) return 0.0;
    return dxEst / intervalEst;
  }

  /**
   * Compute an estimated noise, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
  public double getNoiseEstimate() {
    if (samples <= 1) return 0;
    return noiseVar / intervalEst;
  }

  /**
   * Compute an estimated noise term, assuming normal distribution of values and
   * exponential distribution of intervals.
   *
   * @return the current estimated noise.
   */
  public double getNoiseTermEstimate() {
    if (samples <= 1) return 0;
    return noiseEst;
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
    this.lastUpdate =
        (long) (System.currentTimeMillis() - (0.5 * this.intervalEst));
    this.startInterval = intervalEst;

    // Value
    this.dxEst = presets.getVelocityEst() * intervalEst;

    this.noiseEst = 0;
    this.noiseVar = presets.getNoiseEst() * intervalEst;
    this.startDelta = this.dxEst;

    // Estimation
    this.samples = presets.getSamples();
  }

  /**
   * Utility for converting a stale velocity estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedVelocity(double velocityEst, double intervalEst,
      long samples, long lastUpdate, long curTime) {
    if (samples <= 1 || (2 * (curTime - lastUpdate)) <= intervalEst)
      return velocityEst;
    // Act as if we got an avg dx update now.
    return velocityEst * intervalEst
        / (updatedMean(intervalEst, curTime - lastUpdate, getAlpha(samples)));
  }

  /**
   * Utility for converting a stale noise estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedNoise(double velocityEst, double noiseEst,
      double noiseTermEst, double intervalEst, long samples, long lastUpdate,
      long curTime) {
    // Act as if we got a 0 dx update now.
    long timeSince = curTime - lastUpdate;
    return updatedNoise(noiseTermEst, noiseEst * intervalEst,
        -velocityEst * timeSince, getAlpha(samples))
        / updatedMean(intervalEst, timeSince, getAlpha(samples));
  }

  /**
   * Utility for converting a stale noise estimate to a properly "decayed"
   * estimate.
   */
  public static double updatedNoiseTerm(double velocityEst, double noiseEst,
      double noiseTermEst, double intervalEst, long samples, long lastUpdate,
      long curTime) {
    // Act as if we got a 0 dx update now.
    long timeSince = curTime - lastUpdate;
    return updatedMean(noiseTermEst, -velocityEst * timeSince,
        getAlpha(samples));
  }
}
