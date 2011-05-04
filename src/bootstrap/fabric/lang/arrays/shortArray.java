package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.Label;

public interface shortArray extends Object {
  short get(int i);

  short set(int i, short value);

  public static class _Impl extends Object._Impl implements shortArray {
    public _Impl(Store store, Label label, int length) {
      super(store, label);
    }

    public _Impl(Store store, Label label, Label accessLabel, int length) {
      super(store, label, accessLabel);
    }

    public native short get(int i);

    public native short set(int i, short value);
  }
}
