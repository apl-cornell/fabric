package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.Label;

public interface charArray extends Object {
  char get(int i);

  char set(int i, char value);

  public static class _Impl extends Object._Impl implements charArray {
    public _Impl(Store store, Label label, Label accessLabel, int length) {
      super(store, label, accessLabel);
    }

    public native char get(int i);

    public native char set(int i, char value);
  }
}
