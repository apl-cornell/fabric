package fabric.lang.security;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface PrincipalSet extends fabric.lang.Object {

  public fabric.util.Set get$set();

  public fabric.util.Set set$set(fabric.util.Set val);

  public fabric.lang.security.PrincipalSet add(fabric.lang.security.Principal p);

  public fabric.util.Set getSet();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.PrincipalSet {

    @Override
    native public fabric.util.Set get$set();

    @Override
    native public fabric.util.Set set$set(fabric.util.Set val);

    @Override
    native public fabric.lang.security.PrincipalSet add(
        fabric.lang.security.Principal arg1);

    @Override
    native public fabric.util.Set getSet();

    public _Proxy(PrincipalSet._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.PrincipalSet {

    @Override
    native public fabric.util.Set get$set();

    @Override
    native public fabric.util.Set set$set(fabric.util.Set val);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    @Override
    native public fabric.lang.security.PrincipalSet add(
        fabric.lang.security.Principal p);

    @Override
    native public fabric.util.Set getSet();

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

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.PrincipalSet._Static {

      public _Proxy(fabric.lang.security.PrincipalSet._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.PrincipalSet._Static {

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
