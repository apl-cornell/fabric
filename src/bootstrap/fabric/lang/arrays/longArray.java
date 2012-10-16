package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface longArray extends Object {
  long get(int i);

  long set(int i, long value);

  public static class _Impl extends Object._Impl implements longArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    @Override
    public native long get(int i);

    @Override
    public native long set(int i, long value);
  }
}
