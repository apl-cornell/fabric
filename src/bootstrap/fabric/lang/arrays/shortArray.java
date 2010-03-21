package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import jif.lang.Label;

public interface shortArray extends Object {
  short get(int i);

  short set(int i, short value);

  public static class _Impl extends Object._Impl implements shortArray {
    public _Impl(Store store, Label label, int length) {
      super(store, label);
    }

    public native short get(int i);

    public native short set(int i, short value);
  }
}
