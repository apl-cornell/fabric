package fabric.lang;

import javax.crypto.SecretKey;

import fabric.client.Core;

public interface SecretKeyObject extends Object {
  SecretKey getKey();
  
  public static class $Proxy extends Object.$Proxy implements SecretKeyObject {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native SecretKey getKey();
  }
}
