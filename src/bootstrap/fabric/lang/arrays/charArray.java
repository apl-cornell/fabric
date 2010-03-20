package fabric.lang.arrays;

import fabric.worker.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface charArray extends Object {
  char get(int i);

  char set(int i, char value);

  public static class _Impl extends Object._Impl implements charArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native char get(int i);

    public native char set(int i, char value);
  }
}
