package fabric.worker.metrics;

import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.treaties.Treaty;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link Treaty}s implying their validity.
 */
public class WarrantyValue implements JavaInlineable {

  /** The result value. */
  public final fabric.lang.Object value;

  /**
   * A {@link Treaty} which, when valid, implies the value is current.
   */
  public final Treaty treaty;

  public static native WarrantyValue newValue(fabric.lang.Object value, Treaty treaty);
}
