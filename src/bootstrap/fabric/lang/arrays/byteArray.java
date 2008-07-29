package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import fabric.lang.auth.Label;

public interface byteArray extends Object {
  byte get(int i);

  byte set(int i, byte value);

  public static class $Impl extends Object.$Impl implements byteArray {
    public $Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native byte get(int i);

    public native byte set(int i, byte value);
  }
}
