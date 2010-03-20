package jif.lang;

import fabric.worker.Core;

public interface ConfPolicy extends Policy {
  public static class _Proxy extends Policy._Proxy implements ConfPolicy {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
