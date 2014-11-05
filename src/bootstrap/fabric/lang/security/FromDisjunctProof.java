package fabric.lang.security;

import fabric.common.VersionWarranty;

public interface FromDisjunctProof extends fabric.lang.security.ActsForProof {

  public fabric.util.Map get$disjunctProofs();

  public fabric.util.Map getDisjunctProofs();

  @Override
  public void gatherDelegationDependencies(java.util.Set s);

  public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.FromDisjunctProof {

    @Override
    native public fabric.util.Map get$disjunctProofs();

    @Override
    native public fabric.util.Map getDisjunctProofs();

    public _Proxy(FromDisjunctProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends
      fabric.lang.security.ActsForProof._Impl implements
      fabric.lang.security.FromDisjunctProof {

    @Override
    native public fabric.util.Map get$disjunctProofs();

    _Impl(fabric.worker.Store $location) {
      super($location);
    }

    @Override
    native public fabric.util.Map getDisjunctProofs();

    @Override
    native public void gatherDelegationDependencies(java.util.Set s);

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.FromDisjunctProof._Static {

      public _Proxy(fabric.lang.security.FromDisjunctProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.FromDisjunctProof._Static {

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
