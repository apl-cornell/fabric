package fabric.lang.security;

import javax.crypto.SecretKey;

import fabric.worker.Store;

public interface SecretKeyObject extends fabric.lang.Object {
  SecretKey getKey();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      SecretKeyObject {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native SecretKey getKey();
  }
}
