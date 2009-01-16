package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface intArray extends Object {
  int get(int i);

  int set(int i, int value);

  public static class $Impl extends Object.$Impl implements intArray {
    public $Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native int get(int i);

    public native int set(int i, int value);
  }
}
