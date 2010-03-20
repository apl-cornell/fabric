package fabric.lang.arrays;

import fabric.worker.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface byteArray extends Object {
  byte get(int i);

  byte set(int i, byte value);

  public static class _Impl extends Object._Impl implements byteArray {
    public _Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native byte get(int i);

    public native byte set(int i, byte value);
  }
}
