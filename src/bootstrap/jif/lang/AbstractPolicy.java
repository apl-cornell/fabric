package jif.lang;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;
import fabric.lang.Object;

public interface AbstractPolicy extends Policy {
  public static class _Impl extends Object._Impl {
    public _Impl(Store store, Label label)
        throws UnreachableNodeException {
      super(store, label);
    }
  }
}
