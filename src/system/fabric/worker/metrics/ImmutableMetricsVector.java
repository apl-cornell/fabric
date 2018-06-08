package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import fabric.metrics.Metric;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of items.
 */
public class ImmutableMetricsVector implements Serializable {
  /** The array backing this vector */
  private Metric._Proxy[] items;

  /** Provided since you can't mark constructors as native for the fabil
   * signatures
   */
  public static ImmutableMetricsVector createVector(Metric[] items) {
    return new ImmutableMetricsVector(items);
  }

  public ImmutableMetricsVector(Metric[] items) {
    this.items = new Metric._Proxy[items.length];
    for (int i = 0; i < items.length; i++) {
      this.items[i] = (Metric._Proxy) items[i].$getProxy();
    }
  }

  /** @return the <code>i</code>th item. */
  public Metric get(int i) {
    return items[i];
  }

  /** @return the length. */
  public int length() {
    return items.length;
  }

  /** @return a copy of this vector. */
  public ImmutableMetricsVector copy() {
    return new ImmutableMetricsVector(items);
  }

  /** @return a copy of the underlying array. */
  public Metric[] array() {
    //return Arrays.copyOf(items, items.length);
    return items;
  }

  private static final ImmutableMetricsVector EMPTY =
      new ImmutableMetricsVector(new Metric[0]);

  /** @return a value to use for an empty vector */
  public static ImmutableMetricsVector emptyVector() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) {
    try {
      out.writeInt(items.length);
      for (Metric item : items) {
        out.writeUTF(item.$getStore().name());
        out.writeLong(item.$getOnum());
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObject(ObjectInputStream in) {
    try {
      int size = in.readInt();
      items = new Metric._Proxy[size];
      for (int i = 0; i < size; i++) {
        Store s = Worker.getWorker().getStore(in.readUTF());
        long onum = in.readLong();
        items[i] = new fabric.metrics.Metric._Proxy(s, onum);
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObjectNoData() {
    items = new Metric._Proxy[0];
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof ImmutableMetricsVector
        && Arrays.equals(this.items, ((ImmutableMetricsVector) obj).items));
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.items);
  }
}
