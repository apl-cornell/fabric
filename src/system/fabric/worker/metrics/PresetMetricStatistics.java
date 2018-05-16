package fabric.worker.metrics;

/**
 * Definition for some predefined settings for metric stats, to preload.
 */
public class PresetMetricStatistics {
  private final double intervalEst;
  private final double velocityEst;
  private final double noiseEst;
  private final int samples;

  public PresetMetricStatistics(double intervalEst, double velocityEst,
      double noiseEst, int samples) {
    this.intervalEst = intervalEst;
    this.velocityEst = velocityEst;
    this.noiseEst = noiseEst;
    this.samples = samples;
  }

  /**
   * @return the intervalEst
   */
  public double getIntervalEst() {
    return intervalEst;
  }

  /**
   * @return the velocityEst
   */
  public double getVelocityEst() {
    return velocityEst;
  }

  /**
   * @return the noiseEst
   */
  public double getNoiseEst() {
    return noiseEst;
  }

  /**
   * @return the samples
   */
  public int getSamples() {
    return samples;
  }
}
