package fabric.lang.security;

public interface DisjunctivePrincipal extends fabric.lang.security.Principal {

  public fabric.util.Set get$disjuncts();

  public java.lang.Integer get$hashCode();

  public java.lang.Integer set$hashCode(java.lang.Integer val);

  @Override
  public java.lang.String name();

  @Override
  public boolean delegatesTo(fabric.lang.security.Principal p);

  @Override
  public int hashCode();

  @Override
  public boolean equals(fabric.lang.security.Principal p);

  @Override
  public boolean isAuthorized(java.lang.Object authPrf,
      fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
      boolean executeNow);

  @Override
  public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);

  @Override
  public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);

  public static class _Proxy extends fabric.lang.security.Principal._Proxy
      implements fabric.lang.security.DisjunctivePrincipal {

    @Override
    native public fabric.util.Set get$disjuncts();

    @Override
    native public java.lang.Integer get$hashCode();

    @Override
    native public java.lang.Integer set$hashCode(java.lang.Integer val);

    @Override
    native public int hashCode();

    public _Proxy(DisjunctivePrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends fabric.lang.security.Principal._Impl
      implements fabric.lang.security.DisjunctivePrincipal {

    @Override
    native public fabric.util.Set get$disjuncts();

    @Override
    native public java.lang.Integer get$hashCode();

    @Override
    native public java.lang.Integer set$hashCode(java.lang.Integer val);

    _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label,
        fabric.lang.security.Label accessLabel, fabric.util.Set disjuncts) {
      super($location);
    }

    @Override
    native public java.lang.String name();

    @Override
    native public boolean delegatesTo(fabric.lang.security.Principal p);

    @Override
    native public int hashCode();

    @Override
    native public boolean equals(fabric.lang.security.Principal p);

    @Override
    native public boolean isAuthorized(java.lang.Object authPrf,
        fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
        boolean executeNow);

    @Override
    native public fabric.lang.security.ActsForProof findProofUpto(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object searchState);

    @Override
    native public fabric.lang.security.ActsForProof findProofDownto(
        fabric.worker.Store store, fabric.lang.security.Principal q,
        java.lang.Object searchState);

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        long expiry, long label, long accessLabel, java.io.ObjectInput in,
        java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
	java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public fabric.worker.Store get$localStore();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.DisjunctivePrincipal._Static {

      @Override
      native public fabric.worker.Store get$localStore();

      public _Proxy(fabric.lang.security.DisjunctivePrincipal._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.DisjunctivePrincipal._Static {

      @Override
      native public fabric.worker.Store get$localStore();

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
