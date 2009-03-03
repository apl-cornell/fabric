package fabric.lang;

import java.security.PrivateKey;

import jif.lang.Label;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;

public interface PrivateKeyObject extends Object {
  PrivateKey getKey();
  
  public static class $Proxy extends Object.$Proxy implements PrivateKeyObject {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native PrivateKey getKey();
  }
  
  public static class $Impl extends Object.$Impl implements PrivateKeyObject {
    public $Impl(Core core, Label label, PrivateKey key)
        throws UnreachableNodeException {
      super(core, label);
    }
    
    public native PrivateKey getKey();
  }
}
