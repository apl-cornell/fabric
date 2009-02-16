package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;

public interface WriterPolicy extends AbstractPolicy, IntegPolicy {
  public static class $Impl extends AbstractPolicy.$Impl implements
      WriterPolicy {
    public $Impl(Core core, Label label, Principal owner, Principal writer)
        throws UnreachableNodeException {
      super(core, label);
    }
  }
}
