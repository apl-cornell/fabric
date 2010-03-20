package fabric.lang.arrays;

import fabric.worker.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface longArray extends Object {
  long get(int i);

  long set(int i, long value);

  public static class _Impl extends Object._Impl implements longArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native long get(int i);

    public native long set(int i, long value);
  }
}
