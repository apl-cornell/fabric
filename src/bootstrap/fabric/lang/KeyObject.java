package fabric.lang;

import javax.crypto.SecretKey;

import fabric.client.Core;

public interface KeyObject extends Object {
  SecretKey getKey();
  
  public static class $Proxy extends Object.$Proxy implements KeyObject {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native SecretKey getKey();
  }
}
