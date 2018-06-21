package fabric.worker.metrics;

import fabric.metrics.Metric;

/**
 * Class for encapsulating all of the optimization math for doing an optimal
 * strategy for a SumMetric.
 *
 * TODO: Much more documentation.
 */
public class SumStrategy {

  /**
   * Use the optimizer to set the initial slack split.  This is written assuming
   * rates are chosen so that effective velocities are equal across nodes, which
   * allows for some simplified math.
   */
  public static native double native [] getSplitEqualVelocity(
      double native [] velocities, double native [] noises,
      double native [] rates, double slack);

  /**
   * Utility to allow for easier conversion from metrics to a split of slack.
   */
  public static native double native [] getSplit(Metric native [] metrics,
      double slack);

  /**
   * Given velocities, noises, total slack, and rates, optimize initial slack
   * allocation according to statistical model.  This makes no assumptions about
   * input data.
   */
  public static native double native [] getSplit(double native [] velocities,
      double native [] noises, double native [] rates, double slack);
}
