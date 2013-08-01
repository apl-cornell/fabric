package fabric.lang;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;

/**
 * 
 */
public interface Ref extends Object {
  public static class _Impl extends Object._Impl implements _Static {
    public _Impl(Store store) throws UnreachableNodeException {
      super(store);
    }

    public static int hash(fabric.lang.Object ref) {
        Store store = ref.$getStore();
        long onum = ref.$getOnum();
        return store.name().hashCode() ^ ((Long) onum).hashCode();
    }
  }
}
