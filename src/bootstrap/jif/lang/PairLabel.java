package jif.lang;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;
import fabric.lang.SecretKeyObject;

public interface PairLabel extends Label {
  public static class _Impl extends fabric.lang.Object._Impl implements
      PairLabel {
    public _Impl(Store store, Label label, ConfPolicy conf, IntegPolicy integ)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native ConfPolicy confPolicy();

    public native IntegPolicy integPolicy();

    public native SecretKeyObject keyObject();
  }
}
