package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;

public interface ReaderPolicy extends AbstractPolicy, ConfPolicy {
  public static class $Impl extends AbstractPolicy.$Impl implements
      ReaderPolicy {
    public $Impl(Core core, Label label, Principal owner, Principal reader)
        throws UnreachableNodeException {
      super(core, label);
    }
  }
}
