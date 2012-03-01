package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;

public interface ObjectArray extends Object {
  Object get(int i);

  Object set(int i, Object value);

  public static class _Impl extends Object._Impl implements ObjectArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel,
        Class<?> proxyClass, int length) {
      super(store);
    }

    public native Object get(int i);

    public native Object set(int i, Object value);
  }
}
