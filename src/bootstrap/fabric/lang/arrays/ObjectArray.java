package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;
import jif.lang.Label;

public interface ObjectArray extends Object {
  Object get(int i);

  Object set(int i, Object value);

  public static class _Impl extends Object._Impl implements ObjectArray {
    public _Impl(Core core, Label label, Class<?> proxyClass, int length) {
      super(core, label);
    }

    public native Object get(int i);

    public native Object set(int i, Object value);
  }
}
