package fabric.worker.metrics;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.metrics.Metric;

/**
 * Utility class for an inlineable representation of a mapping from Metrics to a
 * set of "weakly consistent" stats that were grabbed for the metrics at some
 * point. This is useful for allowing weakly consistent/old statistics for
 * metrics when creating treaties.
 */
public class StatsMap implements FastSerializable, Serializable {

  @Override
  public void write(DataOutput out) throws IOException {
    // TODO Auto-generated method stub

  }

  /**
   * Factory constructor for a blank map because signatures for FabIL don't
   * appear to support constructors.
   */
  public static StatsMap emptyStats() {
    // TODO
    return null;
  }

  /**
   * @param m a {@link Metric}
   * @return true iff there are stats in this map for m.
   */
  public boolean containsKey(Metric m) {
    // TODO
    return false;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached value of m, or a default value of 0.
   */
  public double getValue(Metric m) {
    // TODO
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached sample count of m, or a default value of 0.
   */
  public long getSamples(Metric m) {
    // TODO
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached last update time of m, or a default value of now.
   */
  public long getLastUpdate(Metric m) {
    // TODO
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached update interval of m, or a default value of max long.
   */
  public double getUpdateInterval(Metric m) {
    // TODO
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached velocity of m, or a default value of 0.
   */
  public double getVelocity(Metric m) {
    // TODO
    return 0;
  }

  /**
   * @param m a {@link Metric}
   * @return the cached noise of m, or a default value of 0.
   */
  public double getNoise(Metric m) {
    // TODO
    return 0;
  }

  /**
   * Put an entry for the given metric with the given stats.
   * @param m the new key
   * @param value the value of m
   * @param samples the sample count of m
   * @param lastUpdate the last update time of m
   * @param updateInterval the update interval of m
   * @param velocity the velocity of m
   * @param noise the noise of m
   * @return the updated map.
   */
  public StatsMap put(Metric m, double value, long samples, long lastUpdate,
      double updateInterval, double velocity, double noise) {
    // TODO
    return this;
  }

  /**
   * Merge entries in another map missing from this map.
   * @param other the other map to be merged in.
   * @return the updated map.
   */
  public StatsMap merge(StatsMap other) {
    // TODO
    return this;
  }
}
