package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface doubleArray extends Object {
  double get(int i);

  double set(int i, double value);

  public static class _Impl extends Object._Impl implements doubleArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native double get(int i);

    public native double set(int i, double value);
  }
}
