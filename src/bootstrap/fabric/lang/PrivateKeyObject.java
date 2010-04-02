package fabric.lang;

import java.security.PrivateKey;

import fabric.lang.security.Label;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;

public interface PrivateKeyObject extends Object {
  PrivateKey getKey();
  
  public static class _Proxy extends Object._Proxy implements PrivateKeyObject {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native PrivateKey getKey();
  }
  
  public static class _Impl extends Object._Impl implements PrivateKeyObject {
    public _Impl(Store store, Label label, PrivateKey key)
        throws UnreachableNodeException {
      super(store, label);
    }
    
    public native PrivateKey getKey();
  }
}
