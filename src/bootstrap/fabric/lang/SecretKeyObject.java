package fabric.lang;

import javax.crypto.SecretKey;

import fabric.worker.Store;

public interface SecretKeyObject extends Object {
  SecretKey getKey();
  
  public static class _Proxy extends Object._Proxy implements SecretKeyObject {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native SecretKey getKey();
  }
}
