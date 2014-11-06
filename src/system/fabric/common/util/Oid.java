package fabric.common.util;

import fabric.worker.Store;

public class Oid {
  public final Store store;
  public final long onum;

  public Oid(Store store, long onum) {
    if (store == null) {
      throw new IllegalArgumentException("store cannot be null");
    }

    this.store = store;
    this.onum = onum;
  }

  public Oid(fabric.lang.Object o) {
    this(o.$getStore(), o.$getOnum());
  }

  @Override
  public int hashCode() {
    return store.hashCode() ^ (int) (onum ^ (onum >>> 32));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Oid)) return false;
    Oid other = (Oid) obj;
    return onum == other.onum && store.equals(other.store);
  }

  @Override
  public String toString() {
    return "fab://" + store.name() + "/" + onum;
  }
}
