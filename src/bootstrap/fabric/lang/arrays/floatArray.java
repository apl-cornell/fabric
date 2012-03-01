package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;

public interface floatArray extends Object {
  float get(int i);

  float set(int i, float value);

  public static class _Impl extends Object._Impl implements floatArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    public native float get(int i);

    public native float set(int i, float value);
  }
}
