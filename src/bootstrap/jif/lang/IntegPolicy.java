package jif.lang;

import fabric.worker.Store;

public interface IntegPolicy extends Policy {
  public static class _Proxy extends Policy._Proxy implements IntegPolicy {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }
  }
}
