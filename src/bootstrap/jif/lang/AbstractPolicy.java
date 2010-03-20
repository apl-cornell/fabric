package jif.lang;

import fabric.worker.Core;
import fabric.net.UnreachableNodeException;
import fabric.lang.Object;

public interface AbstractPolicy extends Policy {
  public static class _Impl extends Object._Impl {
    public _Impl(Core core, Label label)
        throws UnreachableNodeException {
      super(core, label);
    }
  }
}
