package jif.lang;

import fabric.worker.Core;
import fabric.net.UnreachableNodeException;
import fabric.lang.Principal;

public interface ReaderPolicy extends AbstractPolicy, ConfPolicy {
  Principal owner();
  
  Principal reader();
  
  public static class _Impl extends AbstractPolicy._Impl implements
      ReaderPolicy {
    public _Impl(Core core, Label label, Principal owner, Principal reader)
        throws UnreachableNodeException {
      super(core, label);
    }

    public native Principal owner();

    public native Principal reader();
  }
}
