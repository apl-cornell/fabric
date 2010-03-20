package fabric.lang;

import javax.crypto.SecretKey;

import fabric.worker.Core;

public interface SecretKeyObject extends Object {
  SecretKey getKey();
  
  public static class _Proxy extends Object._Proxy implements SecretKeyObject {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native SecretKey getKey();
  }
}
