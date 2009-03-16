package fabric.lang;

import java.security.PrivateKey;

import jif.lang.Label;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;

public interface PrivateKeyObject extends Object {
  PrivateKey getKey();
  
  public static class _Proxy extends Object._Proxy implements PrivateKeyObject {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native PrivateKey getKey();
  }
  
  public static class _Impl extends Object._Impl implements PrivateKeyObject {
    public _Impl(Core core, Label label, PrivateKey key)
        throws UnreachableNodeException {
      super(core, label);
    }
    
    public native PrivateKey getKey();
  }
}
