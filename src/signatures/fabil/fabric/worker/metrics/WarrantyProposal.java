package fabric.worker.metrics;

import fabric.metrics.Metric;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.worker.metrics.treaties.statements.TreatyStatement;

/**
 * A utility class for proposing a {@link WarrantyValue} to use as a {@link
 * WarrantyComp} result
 */
public class WarrantyProposal implements JavaInlineable {

  /** The result value. */
  public final fabric.lang.Object value;

  /**
   * The {@link Metric} for the proposed {@link Treaty} which, if valid, implies
   * the value is current.
   */
  public final Metric metric;

  /**
   * The {@link TreatyStatement} for the proposed {@link Treaty} which, if
   * valid, implies the value is current.
   */
  public final TreatyStatement predicate;

  /**
   * A {@link StatsMap} which is to be used if activating treaty.
   */
  public final StatsMap weakStats;

  public static native WarrantyProposal newProposal(fabric.lang.Object value, Metric metric, TreatyStatement predicate);

  public static native WarrantyProposal newProposal(fabric.lang.Object value, Metric metric, TreatyStatement predicate, StatsMap weakStats);
}
