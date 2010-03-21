package jif.lang;

import fabric.worker.Store;
import fabric.lang.Object;

public interface Policy extends Object {
  public static class _Proxy extends Object._Proxy implements Policy {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }
  }
}
