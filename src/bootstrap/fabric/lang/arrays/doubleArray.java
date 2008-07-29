package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import fabric.lang.auth.Label;

public interface doubleArray extends Object {
  double get(int i);

  double set(int i, double value);

  public static class $Impl extends Object.$Impl implements doubleArray {
    public $Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native double get(int i);

    public native double set(int i, double value);
  }
}
