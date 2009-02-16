package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.lang.Object;

public interface AbstractPolicy extends Policy {
  public static class $Impl extends Object.$Impl {
    public $Impl(Core core, Label label)
        throws UnreachableNodeException {
      super(core, label);
    }
  }
}
