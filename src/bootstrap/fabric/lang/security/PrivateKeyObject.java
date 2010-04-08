package fabric.lang.security;

import java.security.PrivateKey;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;

public interface PrivateKeyObject extends fabric.lang.Object {
  PrivateKey getKey();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      PrivateKeyObject {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native PrivateKey getKey();
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      PrivateKeyObject {
    public _Impl(Store store, Label label, PrivateKey key)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native PrivateKey getKey();
  }
}
