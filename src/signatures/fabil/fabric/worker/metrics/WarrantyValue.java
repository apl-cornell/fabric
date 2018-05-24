package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.lang.WrappedJavaInlineable;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link TreatyRef}s implying their validity.
 */
public class WarrantyValue implements JavaInlineable {

  /** The result value. */
  public final fabric.lang.Object value;

  /**
   * A {@link TreatyRef} which, when valid, implies the value is current.
   */
  public final TreatyRef treaty;

  /**
   * A {@link StatsMap} which is to be used if activating treaty.
   */
  public final StatsMap weakStats;

  public static native WarrantyValue newValue(fabric.lang.Object value, TreatyRef treaty);

  public static native WarrantyValue newValue(fabric.lang.Object value, TreatyRef treaty, StatsMap weakStats);

  public static native WarrantyValue newValue(fabric.lang.Object value, MetricTreaty treaty);

  public static native WarrantyValue newValue(fabric.lang.Object value, MetricTreaty treaty, StatsMap weakStats);
}
