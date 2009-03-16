package fabric.util;

import jif.lang.Label;
import fabric.client.Core;
import fabric.lang.Object;

public interface AbstractMap extends Map, Object {
  public abstract static class _Impl extends Object._Impl {
    public _Impl(Core core, Label label) {
      super(core, label);
    }
  }
}
