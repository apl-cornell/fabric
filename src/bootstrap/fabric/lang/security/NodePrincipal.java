package fabric.lang.security;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface NodePrincipal extends fabric.lang.security.AbstractPrincipal {
  public static class _Proxy extends
      fabric.lang.security.AbstractPrincipal._Proxy implements
      fabric.lang.security.NodePrincipal {

    public _Proxy(NodePrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends
      fabric.lang.security.AbstractPrincipal._Impl implements
      fabric.lang.security.NodePrincipal {

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    native public NodePrincipal fabric$lang$security$NodePrincipal$(String name);

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.NodePrincipal._Static {

      public _Proxy(fabric.lang.security.NodePrincipal._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.NodePrincipal._Static {

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
