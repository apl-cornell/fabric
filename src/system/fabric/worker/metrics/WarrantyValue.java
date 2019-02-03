package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.lang.WrappedJavaInlineable;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.treaties.Treaty;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link Treaty}s implying their validity.
 */
@SuppressWarnings("serial")
public class WarrantyValue implements Serializable {

  /** The result value. */
  public fabric.lang.Object value;

  /**
   * A {@link Treaty} which, when valid, implies the value is current.
   */
  public Treaty treaty;

  /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param treaty
   *        the value's associated {@link Treaty}
   */
  public WarrantyValue(fabric.lang.Object value, Treaty treaty) {
    this.value = value;
    this.treaty = treaty;
  }

  public static WarrantyValue newValue(fabric.lang.Object value,
      Treaty treaty) {
    return new WarrantyValue(value, treaty);
  }

  @Override
  public String toString() {
    return "WarrantyVal(" + value + ", " + treaty + ")";
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
    if (treaty != null) {
      out.writeBoolean(true);
      out.writeUTF(treaty.$getStore().name());
      out.writeLong(treaty.$getOnum());
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
    if (in.readBoolean()) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      treaty = new Treaty._Proxy(s, in.readLong());
    }
  }

  private void readObjectNoData() throws ObjectStreamException {
    value = null;
    treaty = null;
  }
}
