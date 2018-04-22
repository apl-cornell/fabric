package fabric.worker.metrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.metrics.contracts.Contract;

/**
 * Utility class to easily express an immutable vector of items.
 */
public class ImmutableContractsVector implements Serializable {
  /** The array backing this vector */
  private Contract._Proxy[] items;

  /** Provided since you can't mark constructors as native for the fabil
   * signatures
   */
  public static ImmutableContractsVector createVector(Contract[] items) {
    return new ImmutableContractsVector(items);
  }

  public ImmutableContractsVector(Contract[] items) {
    this.items = new Contract._Proxy[items.length];
    for (int i = 0; i < items.length; i++) {
      this.items[i] = (Contract._Proxy) items[i].$getProxy();
    }
  }

  /** @return the <code>i</code>th item. */
  public Contract get(int i) {
    return items[i];
  }

  /** @return the length. */
  public int length() {
    return items.length;
  }

  /** @return a copy of this vector. */
  public ImmutableContractsVector copy() {
    return new ImmutableContractsVector(items);
  }

  /** @return a copy of the underlying array. */
  public Contract[] array() {
    //return Arrays.copyOf(items, items.length);
    return items;
  }

  private static final ImmutableContractsVector EMPTY =
      new ImmutableContractsVector(new Contract[0]);

  /** @return a value to use for an empty vector */
  public static ImmutableContractsVector emptyVector() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) {
    try {
      out.writeInt(items.length);
      for (Contract item : items) {
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
      items = new Contract._Proxy[size];
      for (int i = 0; i < size; i++) {
        Store s = Worker.getWorker().getStore(in.readUTF());
        long onum = in.readLong();
        items[i] = new fabric.metrics.contracts.Contract._Proxy(s, onum);
      }
    } catch (IOException e) {
      // TODO
    }
  }

  private void readObjectNoData() {
    items = new Contract._Proxy[0];
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableContractsVector) &&
      Arrays.equals(this.items, ((ImmutableContractsVector) obj).items);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.items);
  }
}
