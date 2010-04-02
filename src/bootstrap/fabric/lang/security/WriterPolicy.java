package fabric.lang.security;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;
import fabric.lang.Principal;

public interface WriterPolicy extends AbstractPolicy, IntegPolicy {
  Principal owner();

  Principal writer();

  public static class _Impl extends AbstractPolicy._Impl implements
      WriterPolicy {
    public _Impl(Store store, Label label, Principal owner, Principal writer)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native Principal owner();

    public native Principal writer();
  }
}
