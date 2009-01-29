package jif.lang;

import fabric.client.Core;
import fabric.client.UnreachableCoreException;

public interface WriterPolicy extends AbstractPolicy, IntegPolicy {
  public static class $Impl extends AbstractPolicy.$Impl implements
      WriterPolicy {
    public $Impl(Core core, Label label, Principal owner, Principal writer)
        throws UnreachableCoreException {
      super(core, label);
    }
  }
}
