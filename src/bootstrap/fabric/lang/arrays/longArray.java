package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import fabric.lang.auth.Label;

public interface longArray extends Object {
  long get(int i);

  long set(int i, long value);

  public static class $Impl extends Object.$Impl implements longArray {
    public $Impl(Core core, Label label, int length) {
      super(core, label);
    }

    public native long get(int i);

    public native long set(int i, long value);
  }
}
