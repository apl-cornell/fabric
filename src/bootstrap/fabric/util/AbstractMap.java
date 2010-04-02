package fabric.util;

import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.lang.Object;

public interface AbstractMap extends Map, Object {
  public abstract static class _Impl extends Object._Impl {
    public _Impl(Store store, Label label) {
      super(store, label);
    }
  }
}
