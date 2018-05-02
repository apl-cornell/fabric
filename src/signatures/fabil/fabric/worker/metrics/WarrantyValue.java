package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.lang.WrappedJavaInlineable;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link Contract}s implying their validity.
 */
public class WarrantyValue implements JavaInlineable {

  /** The result value. */
  public final fabric.lang.Object value;

  /**
   * A {@link Contract} which, when valid, implies the value is current.
   */
  public final Contract contract;

  public static native WarrantyValue newValue(fabric.lang.Object value, Contract contract);
}
