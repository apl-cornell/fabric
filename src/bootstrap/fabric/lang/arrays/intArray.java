package fabric.lang.arrays;

import fabric.worker.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface intArray extends Object {
  int get(int i);

  int set(int i, int value);

  public static class _Impl extends Object._Impl implements intArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native int get(int i);

    public native int set(int i, int value);
  }
}
