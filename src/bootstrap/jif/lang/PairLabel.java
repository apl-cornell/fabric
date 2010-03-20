package jif.lang;

import fabric.worker.Core;
import fabric.net.UnreachableNodeException;
import fabric.lang.SecretKeyObject;

public interface PairLabel extends Label {
  public static class _Impl extends fabric.lang.Object._Impl implements
      PairLabel {
    public _Impl(Core core, Label label, ConfPolicy conf, IntegPolicy integ)
        throws UnreachableNodeException {
      super(core, label);
    }

    public native ConfPolicy confPolicy();

    public native IntegPolicy integPolicy();

    public native SecretKeyObject keyObject();
  }
}
