package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.Label;

public interface booleanArray extends Object {
  boolean get(int i);

  boolean set(int i, boolean value);

  public static class _Impl extends Object._Impl implements booleanArray {
    public _Impl(Store store, Label label, Label accessLabel, int length) {
      super(store, label, accessLabel);
    }

    public native boolean get(int i);

    public native boolean set(int i, boolean value);
  }
}
