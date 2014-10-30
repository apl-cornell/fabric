package fabric.lang.security;

public interface PrincipalUtil extends fabric.lang.Object {
  public static interface ProofSearchState extends fabric.lang.Object {

    public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack();

    public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
        fabric.lang.security.SecurityCache.ActsForPair[] val);

    public boolean contains(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.PrincipalUtil.ProofSearchState {

      @Override
      native public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack();

      @Override
      native public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
          fabric.lang.security.SecurityCache.ActsForPair[] val);

      @Override
      native public boolean contains(fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2);

      public _Proxy(ProofSearchState._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.PrincipalUtil.ProofSearchState {

      @Override
      native public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack();

      @Override
      native public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
          fabric.lang.security.SecurityCache.ActsForPair[] val);

      public _Impl(fabric.worker.Store $location,
          fabric.lang.security.Principal p, fabric.lang.security.Principal q) {
        super($location);
      }

      private _Impl(fabric.worker.Store $location,
          fabric.lang.security.PrincipalUtil.ProofSearchState ss,
          fabric.lang.security.Principal p, fabric.lang.security.Principal q) {
        super($location);
      }

      @Override
      native public boolean contains(fabric.lang.security.Principal p,
          fabric.lang.security.Principal q);

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
          fabric.lang.security.PrincipalUtil.ProofSearchState._Static {

        public _Proxy(
            fabric.lang.security.PrincipalUtil.ProofSearchState._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.lang.security.PrincipalUtil.ProofSearchState._Static {

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

  public static interface TopPrincipal extends fabric.lang.security.Principal {

    @Override
    public java.lang.String name();

    @Override
    public boolean delegatesTo(fabric.lang.security.Principal p);

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
        implements fabric.lang.security.PrincipalUtil.TopPrincipal {

      public _Proxy(TopPrincipal._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends
        fabric.lang.security.Principal._Impl implements
        fabric.lang.security.PrincipalUtil.TopPrincipal {

      public _Impl(fabric.worker.Store $location) {
        super($location);
      }

      native public TopPrincipal fabric$lang$security$PrincipalUtil$TopPrincipal$();

      @Override
      native public java.lang.String name();

      @Override
      native public boolean delegatesTo(fabric.lang.security.Principal p);

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.lang.security.PrincipalUtil.TopPrincipal._Static {

        public _Proxy(
            fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.lang.security.PrincipalUtil.TopPrincipal._Static {

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

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.PrincipalUtil {

    native public static boolean acts_for(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    native public static boolean actsFor(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.ActsForProof actsForProof(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static void notifyNewDelegation(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static void notifyRevokeDelegation(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.ActsForProof findActsForProof(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3, java.lang.Object arg4);

    native public static boolean verifyProof(
        fabric.lang.security.ActsForProof arg1,
        fabric.lang.security.Principal arg2, fabric.lang.security.Principal arg3);

    native public static boolean delegatesTo(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static boolean equivalentTo(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static boolean equals(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    native public static java.lang.Object execute(
        fabric.lang.security.Principal arg1, java.lang.Object arg2,
        fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4);

    native public static fabric.lang.security.Capability authorize(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        java.lang.Object arg3, fabric.lang.security.Closure arg4,
        fabric.lang.security.Label arg5);

    native public static fabric.lang.security.Capability authorize(
        fabric.lang.security.Principal arg1, java.lang.Object arg2,
        fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4);

    native public static fabric.lang.security.Principal nullPrincipal();

    native public static fabric.lang.security.Principal bottomPrincipal();

    native public static fabric.lang.security.Principal bottomPrincipal(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.Principal topPrincipal();

    native public static fabric.lang.security.Principal topPrincipal(
        fabric.worker.Store arg1);

    native public static boolean isTopPrincipal(
        fabric.lang.security.Principal arg1);

    native public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.lang.security.Principal arg1);

    native public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.lang.security.Principal arg1);

    native public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.Principal disjunction(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.Principal disjunction(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.Principal conjunction(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.Principal conjunction(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.Principal disjunction(
        fabric.util.Collection arg1);

    native public static fabric.lang.security.Principal disjunction(
        fabric.worker.Store arg1, fabric.util.Collection arg2);

    native public static fabric.lang.security.Principal conjunction(
        fabric.util.Collection arg1);

    native public static fabric.lang.security.Principal conjunction(
        fabric.worker.Store arg1, fabric.util.Collection arg2);

    native public static java.lang.String toString(
        fabric.lang.security.Principal arg1);

    native public static java.lang.String stringValue(
        fabric.lang.security.Principal arg1);

    public _Proxy(PrincipalUtil._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.PrincipalUtil {

    native public static boolean acts_for(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static boolean actsFor(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static fabric.lang.security.ActsForProof actsForProof(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static void notifyNewDelegation(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    native public static void notifyRevokeDelegation(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    native public static fabric.lang.security.ActsForProof findActsForProof(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        fabric.lang.security.Principal q, java.lang.Object searchState);

    native private static boolean eq(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static boolean verifyProof(
        fabric.lang.security.ActsForProof prf,
        fabric.lang.security.Principal actor,
        fabric.lang.security.Principal granter);

    native public static boolean delegatesTo(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    native public static boolean equivalentTo(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static boolean equals(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    native public static java.lang.Object execute(
        fabric.lang.security.Principal p, java.lang.Object authPrf,
        fabric.lang.security.Closure c, fabric.lang.security.Label lb);

    native public static fabric.lang.security.Capability authorize(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object authPrf, fabric.lang.security.Closure c,
        fabric.lang.security.Label lb);

    native public static fabric.lang.security.Capability authorize(
        fabric.lang.security.Principal p, java.lang.Object authPrf,
        fabric.lang.security.Closure c, fabric.lang.security.Label lb);

    native private static fabric.lang.security.Capability authorize(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object authPrf, fabric.lang.security.Closure c,
        fabric.lang.security.Label lb, boolean executeNow);

    native public static fabric.lang.security.Principal nullPrincipal();

    native public static fabric.lang.security.Principal bottomPrincipal();

    native public static fabric.lang.security.Principal bottomPrincipal(
        fabric.worker.Store c);

    native public static fabric.lang.security.Principal topPrincipal();

    native public static fabric.lang.security.Principal topPrincipal(
        fabric.worker.Store store);

    native public static boolean isTopPrincipal(fabric.lang.security.Principal p);

    native public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.lang.security.Principal p);

    native public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal p);

    native public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.lang.security.Principal p);

    native public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal p);

    native public static fabric.lang.security.Principal disjunction(
        fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    native public static fabric.lang.security.Principal disjunction(
        fabric.worker.Store store, fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    native public static fabric.lang.security.Principal conjunction(
        fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    native public static fabric.lang.security.Principal conjunction(
        fabric.worker.Store store, fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    native public static fabric.lang.security.Principal disjunction(
        fabric.util.Collection principals);

    native public static fabric.lang.security.Principal disjunction(
        fabric.worker.Store store, fabric.util.Collection principals);

    native public static fabric.lang.security.Principal conjunction(
        fabric.util.Collection principals);

    native public static fabric.lang.security.Principal conjunction(
        fabric.worker.Store store, fabric.util.Collection principals);

    native public static java.lang.String toString(
        fabric.lang.security.Principal p);

    native public static java.lang.String stringValue(
        fabric.lang.security.Principal p);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

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
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.PrincipalUtil._Static {

      public _Proxy(fabric.lang.security.PrincipalUtil._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.PrincipalUtil._Static {

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
