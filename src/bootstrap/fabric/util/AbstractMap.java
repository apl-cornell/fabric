package fabric.util;

import fabric.lang.Object;
import fabric.worker.Store;

public interface AbstractMap extends Map, Object {
  public abstract static class _Impl extends Object._Impl {
    public _Impl(Store store) {
      super(store);
    }
  }
}
