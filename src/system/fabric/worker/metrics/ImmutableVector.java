package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility class to easily express an immutable vector of items.
 */
public class ImmutableVector implements Serializable {
  /** The array backing this vector */
  private fabric.lang.Object._Proxy[] items;

  /** Provided since you can't mark constructors as native for the fabil
   * signatures
   */
  public static ImmutableVector createVector(fabric.lang.Object[] items) {
    return new ImmutableVector(items);
  }

  public ImmutableVector(fabric.lang.Object[] items) {
    this.items = new fabric.lang.Object._Proxy[items.length];
    for (int i = 0; i < items.length; i++) {
      this.items[i] = items[i].$getProxy();
    }
  }

  /** @return the <code>i</code>th item. */
  public Object get(int i) {
    return items[i];
  }

  /** @return the length. */
  public int length() {
    return items.length;
  }

  /** @return a copy of this vector. */
  public ImmutableVector copy() {
    return new ImmutableVector(items);
  }

  /** @return a copy of the underlying array. */
  public Object[] array() {
    //return Arrays.copyOf(items, items.length);
    return items;
  }

  private static final ImmutableVector EMPTY =
      new ImmutableVector(new fabric.lang.Object._Proxy[0]);

  /** @return a value to use for an empty vector */
  public static ImmutableVector emptyVector() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) {
    try {
      out.writeInt(items.length);
      for (fabric.lang.Object._Proxy item : items) {
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
      items = new fabric.lang.Object._Proxy[size];
      for (int i = 0; i < size; i++) {
        Store s = Worker.getWorker().getStore(in.readUTF());
        long onum = in.readLong();
        items[i] = new fabric.lang.Object._Proxy(s, onum);
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObjectNoData() {
    items = new fabric.lang.Object._Proxy[0];
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableVector)
        && Arrays.equals(this.items, ((ImmutableVector) obj).items);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.items);
  }
}
