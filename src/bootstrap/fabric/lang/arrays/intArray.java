package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;

public interface intArray extends Object {
  int get(int i);

  int set(int i, int value);

  public static class _Impl extends Object._Impl implements intArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    public native int get(int i);

    public native int set(int i, int value);
  }
}
