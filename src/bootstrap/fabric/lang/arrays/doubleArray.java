package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface doubleArray extends Object {
  double get(int i);

  double set(int i, double value);

  public static class _Impl extends Object._Impl implements doubleArray {
    public _Impl(Store store, Label label, ConfPolicy accessPolicy, int length) {
      super(store);
    }

    @Override
    public native double get(int i);

    @Override
    public native double set(int i, double value);
  }
}
