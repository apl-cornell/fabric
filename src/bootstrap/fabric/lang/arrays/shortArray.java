package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface shortArray extends Object {
  short get(int i);

  short set(int i, short value);

  public static class $Impl extends Object.$Impl implements shortArray {
    public $Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native short get(int i);

    public native short set(int i, short value);
  }
}
