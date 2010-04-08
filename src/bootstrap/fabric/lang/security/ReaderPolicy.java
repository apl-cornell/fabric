package fabric.lang.security;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;

public interface ReaderPolicy extends AbstractPolicy, ConfPolicy {
  Principal owner();
  
  Principal reader();
  
  public static class _Impl extends AbstractPolicy._Impl implements
      ReaderPolicy {
    public _Impl(Store store, Label label, Principal owner, Principal reader)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native Principal owner();

    public native Principal reader();
  }
}
