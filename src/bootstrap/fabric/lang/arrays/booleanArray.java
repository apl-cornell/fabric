package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface booleanArray extends Object {
  boolean get(int i);

  boolean set(int i, boolean value);

  public static class _Impl extends Object._Impl implements booleanArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    @Override
    public native boolean get(int i);

    @Override
    public native boolean set(int i, boolean value);
  }
}
