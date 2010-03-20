package jif.lang;

import fabric.worker.Core;

public interface IntegPolicy extends Policy {
  public static class _Proxy extends Policy._Proxy implements IntegPolicy {
    public _Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
