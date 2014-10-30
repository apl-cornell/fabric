package fabric.lang.security;

public interface AbstractPrincipal extends
    fabric.lang.security.DelegatingPrincipal {

  public java.lang.String get$name();

  public java.lang.String set$name(java.lang.String val);

  public fabric.util.Map get$superiors();

  public fabric.util.Map set$superiors(fabric.util.Map val);

  public fabric.lang.security.AbstractPrincipal fabric$lang$security$AbstractPrincipal$(
      final java.lang.String name);

  @Override
  public java.lang.String name();

  @Override
  public boolean delegatesTo(fabric.lang.security.Principal p);

  @Override
  public void addDelegatesTo(fabric.lang.security.Principal p);

  @Override
  public void removeDelegatesTo(fabric.lang.security.Principal p);

  public boolean superiorsContains(fabric.lang.security.Principal p);

  @Override
  public boolean isAuthorized(java.lang.Object authPrf,
      fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
      boolean executeNow);

  @Override
  public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);

  @Override
  public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);

  @Override
  public int hashCode();

  @Override
  public boolean equals(fabric.lang.Object o);

  @Override
  public boolean equals(fabric.lang.security.Principal p);

  public static class _Proxy extends
      fabric.lang.security.DelegatingPrincipal._Proxy implements
      fabric.lang.security.AbstractPrincipal {

    @Override
    native public java.lang.String get$name();

    @Override
    native public java.lang.String set$name(java.lang.String val);

    @Override
    native public fabric.util.Map get$superiors();

    @Override
    native public fabric.util.Map set$superiors(fabric.util.Map val);

    @Override
    native public fabric.lang.security.AbstractPrincipal fabric$lang$security$AbstractPrincipal$(
        java.lang.String arg1);

    native public fabric.lang.security.AbstractPrincipal fabric$lang$security$AbstractPrincipal$(
        java.lang.String arg1, fabric.lang.security.Principal arg2);

    @Override
    native public java.lang.String name();

    @Override
    native public boolean delegatesTo(fabric.lang.security.Principal arg1);

    @Override
    native public boolean superiorsContains(fabric.lang.security.Principal arg1);

    @Override
    native public boolean isAuthorized(java.lang.Object arg1,
        fabric.lang.security.Closure arg2, fabric.lang.security.Label arg3,
        boolean arg4);

    @Override
    native public fabric.lang.security.ActsForProof findProofDownto(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        java.lang.Object arg3);

    @Override
    native public fabric.lang.security.ActsForProof findProofUpto(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        java.lang.Object arg3);

    @Override
    native public int hashCode();

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public boolean equals(fabric.lang.security.Principal arg1);

    public _Proxy(AbstractPrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends
      fabric.lang.security.DelegatingPrincipal._Impl implements
      fabric.lang.security.AbstractPrincipal {

    @Override
    native public java.lang.String get$name();

    @Override
    native public java.lang.String set$name(java.lang.String val);

    @Override
    native public fabric.util.Map get$superiors();

    @Override
    native public fabric.util.Map set$superiors(fabric.util.Map val);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    native private void jif$init();

    @Override
    native public fabric.lang.security.AbstractPrincipal fabric$lang$security$AbstractPrincipal$(
        final java.lang.String name);

    native public fabric.lang.security.AbstractPrincipal fabric$lang$security$AbstractPrincipal$(
        final java.lang.String name, final fabric.lang.security.Principal p);

    @Override
    native public java.lang.String name();

    @Override
    native public boolean delegatesTo(fabric.lang.security.Principal p);

    @Override
    native public void addDelegatesTo(fabric.lang.security.Principal p);

    @Override
    native public void removeDelegatesTo(fabric.lang.security.Principal p);

    @Override
    native public boolean superiorsContains(fabric.lang.security.Principal p);

    @Override
    native public boolean isAuthorized(java.lang.Object authPrf,
        fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
        boolean executeNow);

    @Override
    native public fabric.lang.security.ActsForProof findProofDownto(
        fabric.worker.Store store, fabric.lang.security.Principal q,
        java.lang.Object searchState);

    @Override
    native public fabric.lang.security.ActsForProof findProofUpto(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object searchState);

    @Override
    native public int hashCode();

    @Override
    native public boolean equals(fabric.lang.Object o);

    @Override
    native public boolean equals(fabric.lang.security.Principal p);

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
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.AbstractPrincipal._Static {

      public _Proxy(fabric.lang.security.AbstractPrincipal._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.AbstractPrincipal._Static {

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
