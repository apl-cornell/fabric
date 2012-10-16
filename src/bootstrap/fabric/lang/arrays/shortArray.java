package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface shortArray extends Object {
  short get(int i);

  short set(int i, short value);

  public static class _Impl extends Object._Impl implements shortArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    @Override
    public native short get(int i);

    @Override
    public native short set(int i, short value);
  }
}
