package fabric.worker.metrics;

import static fabric.common.Logging.METRICS_LOGGER;

import java.util.Arrays;
import java.util.logging.Level;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;

/**
 * Class for encapsulating all of the optimization math for doing an optimal
 * strategy for a SumMetric.
 *
 * TODO: Much more documentation.
 */
public class SumStrategy {

  /**
   * Return a function to minimize in the event that average velocity is
   * negative or to find the zero of if the average velocity is positive.
   *
   * This function assumes the rates are set so that the effective velocities
   * are equal across the nodes.
   */
  private static UnivariateFunction createEqualityVAverage(final double v1,
      final double r1, final double s1, final double v2, final double r2,
      final double s2, final double slack, final double n) {

    if ((v1 - r1) <= 0) {
      // Function to minimize
      return new UnivariateFunction() {

        // Full ln(E[1/T]) for node 1
        private double term1(double a) {
          // Effective boundary for node 2
          double a1 = a;
          // Heading towards the boundary.
          return (s1 / (a1 * a1)) - ((v1 - r1) / a1);
        }

        // Full ln(E[1/T]) for node 2
        private double term2(double a) {
          // Effective boundary for node 2
          double a2 = slack - a;
          // Heading towards the boundary.
          return (s2 / (a2 * a2)) - ((v2 - r2) / a2);
        }

        @Override
        public double value(double a) {
          // Full E[1/T] for node 1
          double term1 = term1(a);

          // Full E[1/T] for node 2
          double term2 = term2(a);

          // Difference between the log of each, squared
          return (term1 - term2) * (term1 - term2);
        }
      };
    } else {
      return new UnivariateFunction() {
        @Override
        public double value(double a) {
          double vHat = v1 + v2 - (r1 + r2);
          return (
                a * a * (slack + (n * s1 / vHat) - a) *
                Math.exp((2.0 / n) *
                  (((s1 + s2) * vHat) / (s1 * s2)) *
                  (a - ((s1 * slack) / (s1 + s2))))
              ) - (
                (a - slack) *
                (a - slack) * (a + (n * s2 / vHat))
              );
        }
      };
    }
  }

  /**
   * Return a function to minimize with respect to starting slack.
   *
   * This function assumes nothing about rates given.
   */
  private static UnivariateFunction createEquality(final double v1,
      final double r1, final double s1, final double v2, final double r2,
      final double s2, final double slack) {

    return new UnivariateFunction() {

      // Full ln(E[1/T]) for node 1
      private double term1(double a) {
        // Effective boundary for node 2
        double a1 = a;
        if (s1 <= 0.0) {
          if (v1 - r1 < 0.0) {
            // Heading towards the boundary.
            return Math.log(-(v1 - r1) / a1);
          } else {
            // Moving away from the boundary.
            // This will return -Infinity.
            return Math.log(0.0);
          }
        } else {
          // Useful atoms for node 1
          double absV1 = Math.abs(v1 - r1);
          double noise1 = s1;
          double expConst1 = (-(v1 - r1 + Math.abs(v1 - r1)) / s1);

          // Exponential term for node 1
          //double e1 = Math.exp(expConst1 * a1);

          // nonexponential term for node 1
          double rest1 = (absV1 * a1 + noise1) / (a1 * a1);

          double lnRest1 = Math.log(rest1);
          if (Double.isInfinite(lnRest1)) return lnRest1;

          return lnRest1 + expConst1 * a1;
        }
      }

      // Full ln(E[1/T]) for node 2
      private double term2(double a) {
        // Effective boundary for node 2
        double a2 = slack - a;
        if (s2 <= 0.0) {
          if (v2 - r2 < 0.0) {
            // Heading towards the boundary.
            return Math.log(-(v2 - r2) / a2);
          } else {
            // Moving away from the boundary.
            // This will return -Infinity.
            return Math.log(0.0);
          }
        } else {
          // Useful atoms for node 2
          double absV2 = Math.abs(v2 - r2);
          double noise2 = s2;
          double expConst2 = (-(v2 - r2 + Math.abs(v2 - r2)) / s2);

          // Exponential term for node 2
          //double e2 = Math.exp(expConst2 * a2);

          // nonexponential term for node 2
          double rest2 = (absV2 * a2 + noise2) / (a2 * a2);
          double lnRest2 = Math.log(rest2);
          // This shouldn't happen unless a2 = 0 in which case this is positive.
          if (Double.isInfinite(lnRest2)) return lnRest2;

          // Full E[1/T] for node 2
          return lnRest2 + expConst2 * a2;
        }
      }

      @Override
      public double value(double a) {
        // Full E[1/T] for node 1
        double term1 = term1(a);

        // Full E[1/T] for node 2
        double term2 = term2(a);

        // If either is infinite and positive, we're at 0 or slack, which we
        // should indicate is no good with a very large value.
        if ((Double.isInfinite(term1) && term1 > 0.0)
            || (Double.isInfinite(term2) && term2 > 0.0)) {
          // Let's just return a huge value, I'm not sure if the optimizer
          // handles infinity.
          return 1e100;
        }

        // If either is infinite and negative, it's safe to assume it's due to
        // features entirely orthogonal to the choice of a.  This only happens
        // if there's 0 effective noise.
        if (Double.isInfinite(term1) && term1 < 0.0) {
          return term2;
        }

        if (Double.isInfinite(term2) && term2 < 0.0) {
          return term1;
        }

        // Difference between the log of each, squared
        return (term1 - term2) * (term1 - term2);
      }
    };
  }

  private final static boolean USE_OPTIMIZER = true;

  /**
   * Use the optimizer to set the initial slack split.  This is written assuming
   * rates are chosen so that effective velocities are equal across nodes, which
   * allows for some simplified math.
   */
  public static double[] getSplitEqualVelocity(double[] velocities,
      double[] noises, double[] rates, double slack) {

    //System.out.println("Optimization running with "
    //    + Arrays.toString(velocities) + ", " + Arrays.toString(rates) + ", "
    //    + Arrays.toString(noises) + ", " + slack);
    // Get total velocity
    double totalVelocity = velocities[0];
    for (int i = 1; i < velocities.length; i++) {
      totalVelocity += velocities[i];
    }

    // Get total noise
    double totalNoise = noises[0];
    for (int i = 1; i < noises.length; i++) {
      totalNoise += noises[i];
    }

    if (!USE_OPTIMIZER) {
      // I get the sense this will do as well as the optimizer in many cases.
      double[] slacks = new double[rates.length];
      if (velocities[0] - rates[0] < 0) {
        for (int i = 0; i < slacks.length; i++) {
          double share = noises[i] / totalNoise;
          slacks[i] = slack * share;
        }
      } else {
        for (int i = 0; i < slacks.length; i++) {
          double share = 1.0 / slacks.length;
          slacks[i] = slack * share;
        }
      }
      return slacks;
    }

    // Get total rates
    double totalRate = rates[0];
    for (int i = 1; i < rates.length; i++) {
      totalRate += rates[i];
    }

    double[] result = new double[velocities.length];
    if (totalNoise <= 0.00 || slack <= 0.00) {
      // If the total noise is or the slack is negative 0, then we should just
      // default to an even split.
      for (int i = 0; i < velocities.length; i++) {
        result[i] = slack / velocities.length;
      }
    } else {
      double slackRemaining = slack;
      double itemsLeft = velocities.length;
      // Go through, cutting out slack and giving a bound for each entry.
      for (int i = 0; i < velocities.length - 1; i++) {
        double v1 = velocities[i];
        totalVelocity -= v1;
        double s1 = noises[i];
        totalNoise -= s1;
        double r1 = rates[i];
        totalRate -= r1;

        // Use relative tol of 1e-6 and absolute of 1e-6
        if (v1 - r1 <= 0) {
          BrentOptimizer optimizer = new BrentOptimizer(1e-6, 1e-6);
          result[i] = optimizer.optimize(
              new UnivariateObjectiveFunction(createEqualityVAverage(v1, r1, s1,
                  totalVelocity, totalRate, totalNoise, slackRemaining,
                  itemsLeft)),
              new MaxEval(Integer.MAX_VALUE), GoalType.MINIMIZE,
              new SearchInterval(0, slackRemaining),
              new InitialGuess(
                new double[] { slackRemaining / itemsLeft }
                )).getPoint();
          slackRemaining -= result[i];
        } else {
          BrentSolver solver = new BrentSolver(1e-6, 1e-6);
          result[i] =
              solver.solve(
                  Integer.MAX_VALUE, createEqualityVAverage(v1, r1, s1,
                    totalVelocity, totalRate, totalNoise, slackRemaining,
                    itemsLeft),
                  0, slackRemaining, slackRemaining * (s1 / (s1 + totalNoise)));
          slackRemaining -= result[i];
        }
        itemsLeft--;
      }
      result[result.length - 1] = slackRemaining;
    }
    if (METRICS_LOGGER.isLoggable(Level.FINE)) {
      METRICS_LOGGER
          .fine("Optimization running with " + Arrays.toString(velocities)
              + ", " + Arrays.toString(rates) + ", " + Arrays.toString(noises)
              + ", " + slack + " got " + Arrays.toString(result));
    }
    return result;
  }

  /**
   * Given velocities, noises, total slack, and rates, optimize initial slack
   * allocation according to statistical model.  This makes no assumptions about
   * input data.
   */
  public static double[] getSplit(double[] velocities, double[] noises,
      double[] rates, double slack) {

    //System.out.println("Optimization running with "
    //    + Arrays.toString(velocities) + ", " + Arrays.toString(rates) + ", "
    //    + Arrays.toString(noises) + ", " + slack);
    // Get total velocity
    double totalVelocity = velocities[0];
    for (int i = 1; i < velocities.length; i++) {
      totalVelocity += velocities[i];
    }

    // Get total noise
    double totalNoise = noises[0];
    for (int i = 1; i < noises.length; i++) {
      totalNoise += noises[i];
    }

    if (!USE_OPTIMIZER) {
      // I'm inclined to think this will do as well as the optimizer in many
      // cases?
      double totalShare = 0.0;
      for (int i = 0; i < velocities.length; i++) {
        totalShare += (noises[i] / Math.pow(velocities[i], 2));
      }
      double[] slacks = new double[rates.length];
      for (int i = 0; i < slacks.length; i++) {
        double share = (noises[i] / Math.pow(velocities[i], 2)) / totalShare;
        slacks[i] = slack * share;
      }
      return slacks;
    }

    // Get total rates
    double totalRate = rates[0];
    for (int i = 1; i < rates.length; i++) {
      totalRate += rates[i];
    }

    double[] result = new double[velocities.length];
    if (totalNoise <= 0.00 || slack <= 0.00) {
      // If the total noise is or the slack is negative 0, then we should just
      // default to an even split.
      for (int i = 0; i < velocities.length; i++) {
        result[i] = slack / velocities.length;
      }
    } else {
      double slackRemaining = slack;
      double itemsLeft = velocities.length;
      // Go through, cutting out slack and giving a bound for each entry.
      for (int i = 0; i < velocities.length - 1; i++) {
        double v1 = velocities[i];
        totalVelocity -= v1;
        double s1 = noises[i];
        totalNoise -= s1;
        double r1 = rates[i];
        totalRate -= r1;

        // Use relative tol of 1e-6 and absolute of 1e-6
        BrentOptimizer optimizer = new BrentOptimizer(1e-6, 1e-6);
        result[i] = optimizer.optimize(
            new UnivariateObjectiveFunction(createEquality(v1, r1, s1,
                totalVelocity, totalRate, totalNoise, slackRemaining)),
            new MaxEval(Integer.MAX_VALUE), GoalType.MINIMIZE,
            new SearchInterval(0, slackRemaining),
            new InitialGuess(
              new double[] { slackRemaining / itemsLeft }
              )).getPoint();
        slackRemaining -= result[i];
        itemsLeft--;
      }
      result[result.length - 1] = slackRemaining;
    }
    if (METRICS_LOGGER.isLoggable(Level.FINE)) {
      METRICS_LOGGER
          .fine("Optimization running with " + Arrays.toString(velocities)
              + ", " + Arrays.toString(rates) + ", " + Arrays.toString(noises)
              + ", " + slack + " got " + Arrays.toString(result));
    }
    return result;
  }
}
