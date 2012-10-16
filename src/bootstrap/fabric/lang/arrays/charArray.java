package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface charArray extends Object {
  char get(int i);

  char set(int i, char value);

  public static class _Impl extends Object._Impl implements charArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    @Override
    public native char get(int i);

    @Override
    public native char set(int i, char value);
  }
}
