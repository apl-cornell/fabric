package fabric.lang.security;

import fabric.worker.Store;

public interface ConfPolicy extends Policy {
  public static class _Proxy extends Policy._Proxy implements ConfPolicy {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }
  }
}
