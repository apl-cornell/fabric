package fabric.lang.arrays;

import fabric.worker.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface booleanArray extends Object {
  boolean get(int i);

  boolean set(int i, boolean value);

  public static class _Impl extends Object._Impl implements booleanArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native boolean get(int i);

    public native boolean set(int i, boolean value);
  }
}
