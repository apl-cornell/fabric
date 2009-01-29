package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableCoreException;

public interface PairLabel extends Label {
  public static class $Impl extends fabric.lang.Object.$Impl implements
      PairLabel {
    public $Impl(Core core, Label label, ConfPolicy conf, IntegPolicy integ)
        throws UnreachableCoreException {
      super(core, label);
    }
  }
}
