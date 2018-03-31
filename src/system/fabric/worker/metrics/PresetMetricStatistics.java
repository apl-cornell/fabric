package fabric.worker.metrics;

/**
 * Definition for some predefined settings for metric stats, to preload.
 */
public class PresetMetricStatistics {
  private final int samples;
  private final double meanDelta;
  private final double varDelta;
  private final double meanFreq;
  private final double varFreq;

  public PresetMetricStatistics(int samples, double meanDelta, double varDelta,
      double meanFreq, double varFreq) {
    this.samples = samples;
    this.meanDelta = meanDelta;
    this.varDelta = varDelta;
    this.meanFreq = meanFreq;
    this.varFreq = varFreq;
  }

  /**
   * @return the samples
   */
  public int getSamples() {
    return samples;
  }

  /**
   * @return the lastUpdate value that should be used given the current time.
   */
  public long getLastUpdate() {
    return System.currentTimeMillis() - Math.round(1.0 / meanFreq);
  }

  /**
   * @return the lastUpdate value that should be used given the current time.
   */
  public long getFirstUpdate() {
    return System.currentTimeMillis() - Math.round(samples / meanFreq);
  }

  /**
   * @return the meanDelta
   */
  public double getMeanDelta() {
    return meanDelta;
  }

  /**
   * @return the varDelta
   */
  public double getVarDelta() {
    return varDelta;
  }

  /**
   * @return the meanFreq
   */
  public double getMeanFreq() {
    return meanFreq;
  }

  /**
   * @return the meanFreq
   */
  public double getVarFreq() {
    return meanFreq;
  }
}
