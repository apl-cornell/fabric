package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.lang.Principal;

public interface WriterPolicy extends AbstractPolicy, IntegPolicy {
  Principal owner();

  Principal writer();

  public static class $Impl extends AbstractPolicy.$Impl implements
      WriterPolicy {
    public $Impl(Core core, Label label, Principal owner, Principal writer)
        throws UnreachableNodeException {
      super(core, label);
    }

    public native Principal owner();

    public native Principal writer();
  }
}
