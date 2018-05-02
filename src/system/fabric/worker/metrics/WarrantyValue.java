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
public class WarrantyValue implements Serializable {

  /** The result value. */
  public fabric.lang.Object value;

  /**
   * A {@link Contract} which, when valid, implies the value is current.
   */
  public Contract contract;

  /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param contract
   *        the value's associated {@link Contract}
   */
  public WarrantyValue(fabric.lang.Object value, Contract contract) {
    this.value = value;
    this.contract = contract;
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      Contract contract) {
    return new WarrantyValue(value, contract);
  }

  @Override
  public String toString() {
    return "WarrantyVal(" + value + ", " + contract + ")";
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    if (value != null) {
      out.writeBoolean(true);
      if (value instanceof WrappedJavaInlineable) {
        out.writeBoolean(true);
        out.writeObject(((WrappedJavaInlineable<?>) value).$unwrap());
      } else {
        out.writeBoolean(false);
        out.writeUTF(value.$getStore().name());
        out.writeLong(value.$getOnum());
      }
    } else {
      out.writeBoolean(false);
    }
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    if (in.readBoolean()) {
      if (in.readBoolean()) {
        value = WrappedJavaInlineable.$wrap(in.readObject());
      } else {
        Store s = Worker.getWorker().getStore(in.readUTF());
        value = new fabric.lang.Object._Proxy(s, in.readLong());
      }
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    value = null;
    contract = null;
  }
}
