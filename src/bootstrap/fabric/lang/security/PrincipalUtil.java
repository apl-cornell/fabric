package fabric.lang.security;

/**
 * Utility methods for principals. This code is mostly copied from Jif.
 */
public interface PrincipalUtil extends fabric.lang.Object {
  public static interface ProofSearchState extends fabric.lang.Object {

    public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack();

    public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
        fabric.lang.security.SecurityCache.ActsForPair[] val);

    public ProofSearchState fabric$lang$security$PrincipalUtil$ProofSearchState$(
        fabric.lang.security.Principal p, fabric.lang.security.Principal q);

    public boolean contains(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy
        implements ProofSearchState {

      public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack() {
        return ((fabric.lang.security.PrincipalUtil.ProofSearchState._Impl) fetch())
            .get$goalstack();
      }

      public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
          fabric.lang.security.SecurityCache.ActsForPair[] val) {
        return ((fabric.lang.security.PrincipalUtil.ProofSearchState._Impl) fetch())
            .set$goalstack(val);
      }

      public native fabric.lang.security.PrincipalUtil.ProofSearchState fabric$lang$security$PrincipalUtil$ProofSearchState$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2);

      public native boolean contains(fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2);

      public _Proxy(ProofSearchState._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl
        implements ProofSearchState {

      public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack() {
        fabric.worker.transaction.TransactionManager.getInstance()
            .registerRead(this);
        return this.goalstack;
      }

      public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
          fabric.lang.security.SecurityCache.ActsForPair[] val) {
        fabric.worker.transaction.TransactionManager tm =
            fabric.worker.transaction.TransactionManager.getInstance();
        boolean transactionCreated = tm.registerWrite(this);
        this.goalstack = val;
        if (transactionCreated) tm.commitTransaction();
        return val;
      }

      private fabric.lang.security.SecurityCache.ActsForPair[] goalstack;

      public native ProofSearchState fabric$lang$security$PrincipalUtil$ProofSearchState$(
          fabric.lang.security.Principal p, fabric.lang.security.Principal q);

      private native ProofSearchState fabric$lang$security$PrincipalUtil$ProofSearchState$(
          ProofSearchState ss, fabric.lang.security.Principal p,
          fabric.lang.security.Principal q);

      public native boolean contains(fabric.lang.security.Principal p,
          fabric.lang.security.Principal q);

      public native fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location) {
        super($location);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.PrincipalUtil.ProofSearchState._Proxy(
            this);
      }

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, observers, treaties, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
        super.$copyAppStateFrom(other);
        fabric.lang.security.PrincipalUtil.ProofSearchState._Impl src =
            (fabric.lang.security.PrincipalUtil.ProofSearchState._Impl) other;
        this.goalstack = src.goalstack;
      }
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

        public static final fabric.lang.security.PrincipalUtil.ProofSearchState._Static $instance;

        static {
          fabric.lang.security.PrincipalUtil.ProofSearchState._Static._Impl impl =
              (fabric.lang.security.PrincipalUtil.ProofSearchState._Static._Impl) fabric.lang.Object._Static._Proxy
                  .$makeStaticInstance(
                      fabric.lang.security.PrincipalUtil.ProofSearchState._Static._Impl.class);
          $instance =
              (fabric.lang.security.PrincipalUtil.ProofSearchState._Static) impl
                  .$getProxy();
          impl.$init();
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.lang.security.PrincipalUtil.ProofSearchState._Static {

        public void $serialize(java.io.ObjectOutput out,
            java.util.List refTypes, java.util.List intraStoreRefs,
            java.util.List interStoreRefs) throws java.io.IOException {
          super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }

        public _Impl(fabric.worker.Store store, long onum, int version,
            fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
            fabric.worker.Store labelStore, long labelOnum,
            fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
            java.io.ObjectInput in, java.util.Iterator refTypes,
            java.util.Iterator intraStoreRefs,
            java.util.Iterator interStoreRefs)
            throws java.io.IOException, java.lang.ClassNotFoundException {
          super(store, onum, version, observers, treaties, labelStore, labelOnum,
              accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
              interStoreRefs);
        }

        public _Impl(fabric.worker.Store store) {
          super(store);
        }

        protected fabric.lang.Object._Proxy $makeProxy() {
          return new fabric.lang.security.PrincipalUtil.ProofSearchState._Static._Proxy(
              this);
        }

        private void $init() {
        }
      }

    }

  }

  public static interface TopPrincipal extends fabric.lang.security.Principal {

    public TopPrincipal fabric$lang$security$PrincipalUtil$TopPrincipal$();

    public java.lang.String name();

    public boolean delegatesTo(fabric.lang.security.Principal p);

    public boolean equals(fabric.lang.security.Principal p);

    public boolean isAuthorized(java.lang.Object authPrf,
        fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
        boolean executeNow);

    public fabric.lang.security.ActsForProof findProofUpto(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object searchState);

    public fabric.lang.security.ActsForProof findProofDownto(
        fabric.worker.Store store, fabric.lang.security.Principal q,
        java.lang.Object searchState);

    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.security.Principal._Proxy
        implements TopPrincipal {

      public native fabric.lang.security.PrincipalUtil.TopPrincipal fabric$lang$security$PrincipalUtil$TopPrincipal$();

      public _Proxy(TopPrincipal._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static final class _Impl extends fabric.lang.security.Principal._Impl
        implements TopPrincipal {

      public native TopPrincipal fabric$lang$security$PrincipalUtil$TopPrincipal$();

      public native java.lang.String name();

      public native boolean delegatesTo(fabric.lang.security.Principal p);

      public native boolean equals(fabric.lang.security.Principal p);

      public native boolean isAuthorized(java.lang.Object authPrf,
          fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
          boolean executeNow);

      public native fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState);

      public native fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState);

      public native fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location) {
        super($location);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.PrincipalUtil.TopPrincipal._Proxy(this);
      }

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, observers, treaties, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.PrincipalUtil.TopPrincipal._Static {

        public _Proxy(
            fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }

        public static final fabric.lang.security.PrincipalUtil.TopPrincipal._Static $instance;

        static {
          fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Impl impl =
              (fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Impl) fabric.lang.Object._Static._Proxy
                  .$makeStaticInstance(
                      fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Impl.class);
          $instance =
              (fabric.lang.security.PrincipalUtil.TopPrincipal._Static) impl
                  .$getProxy();
          impl.$init();
        }
      }

      class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PrincipalUtil.TopPrincipal._Static {

        public void $serialize(java.io.ObjectOutput out,
            java.util.List refTypes, java.util.List intraStoreRefs,
            java.util.List interStoreRefs) throws java.io.IOException {
          super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }

        public _Impl(fabric.worker.Store store, long onum, int version,
            fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
            fabric.worker.Store labelStore, long labelOnum,
            fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
            java.io.ObjectInput in, java.util.Iterator refTypes,
            java.util.Iterator intraStoreRefs,
            java.util.Iterator interStoreRefs)
            throws java.io.IOException, java.lang.ClassNotFoundException {
          super(store, onum, version, observers, treaties, labelStore, labelOnum,
              accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
              interStoreRefs);
        }

        public _Impl(fabric.worker.Store store) {
          super(store);
        }

        protected fabric.lang.Object._Proxy $makeProxy() {
          return new fabric.lang.security.PrincipalUtil.TopPrincipal._Static._Proxy(
              this);
        }

        private void $init() {
        }
      }

    }

  }

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrincipalUtil {

    public static native boolean acts_for(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native boolean actsFor(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native fabric.lang.security.ActsForProof actsForProof(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    public static native void notifyNewDelegation(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native void notifyRevokeDelegation(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native fabric.lang.security.ActsForProof findActsForProof(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3, java.lang.Object arg4);

    public static native boolean verifyProof(
        fabric.lang.security.ActsForProof arg1,
        fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    public static native boolean delegatesTo(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native boolean equivalentTo(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native boolean equals(fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public static native java.lang.Object execute(
        fabric.lang.security.Principal arg1, java.lang.Object arg2,
        fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4);

    public static native fabric.lang.security.Capability authorize(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        java.lang.Object arg3, fabric.lang.security.Closure arg4,
        fabric.lang.security.Label arg5);

    public static native fabric.lang.security.Capability authorize(
        fabric.lang.security.Principal arg1, java.lang.Object arg2,
        fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4);

    public static native fabric.lang.security.Principal nullPrincipal();

    public static native fabric.lang.security.Principal bottomPrincipal();

    public static native fabric.lang.security.Principal topPrincipal();

    public static native boolean isTopPrincipal(
        fabric.lang.security.Principal arg1);

    public static native fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2);

    public static native fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2);

    public static native fabric.lang.security.Principal disjunction(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    public static native fabric.lang.security.Principal conjunction(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    public static native fabric.lang.security.Principal disjunction(
        fabric.worker.Store arg1, fabric.util.Collection arg2);

    public static native fabric.lang.security.Principal conjunction(
        fabric.worker.Store arg1, fabric.util.Collection arg2);

    public static native java.lang.String toString(
        fabric.lang.security.Principal arg1);

    public static native java.lang.String stringValue(
        fabric.lang.security.Principal arg1);

    public _Proxy(PrincipalUtil._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrincipalUtil {

    /**
     * Returns true if and only if the principal p acts for the principal q.
     A
     * synonym for the <code>actsFor</code> method.
     */
    public static native boolean acts_for(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    /**
     * Returns true if and only if the principal p acts for the principal q.
     */
    public static native boolean actsFor(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    /**
     * Returns an actsfor proof if and only if the principal p acts for the
     * principal q.
     */
    public static native fabric.lang.security.ActsForProof actsForProof(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    /**
     * Notification that a new delegation has been created.
     */
    public static native void notifyNewDelegation(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    /**
     * Notification that an existing delegation has been revoked.
     */
    public static native void notifyRevokeDelegation(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    /**
     * Search for an ActsForProof between p and q. An ActsForProof between p
     and q
     * is a a checkable proof object.
     *
     * @param p
     * @param q
     * @param searchState
     *          records the goals that we are in the middle of attempting
     * @return An ActsForPoorf between p and q, or null if none can be
     found.
     */
    public static native fabric.lang.security.ActsForProof findActsForProof(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        fabric.lang.security.Principal q, java.lang.Object searchState);

    /**
     * Return whether principals p and q are equal. p and q must either be
     * references to the same object, both be null, or agree that they are
     equal
     * to the other.
     */
    private static native boolean eq(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    /**
     * Verify that the chain is a valid delegates-chain between p and q.
     That is,
     * q == chain[n], chain[n] delegates to chain[n-1], ..., chain[0] == p,
     i.e.,
     * p acts for q.
     */
    public static native boolean verifyProof(
        fabric.lang.security.ActsForProof prf,
        fabric.lang.security.Principal actor,
        fabric.lang.security.Principal granter);

    public static native boolean delegatesTo(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    public static native boolean equivalentTo(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    public static native boolean equals(fabric.lang.security.Principal p,
        fabric.lang.security.Principal q);

    /**
     * Execute the given closure, if the principal agrees.
     */
    public static native java.lang.Object execute(
        fabric.lang.security.Principal p, java.lang.Object authPrf,
        fabric.lang.security.Closure c, fabric.lang.security.Label lb);

    /**
     * Obtain a Capability for the given principal and closure.
     */
    public static native fabric.lang.security.Capability authorize(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object authPrf, fabric.lang.security.Closure c,
        fabric.lang.security.Label lb);

    public static native fabric.lang.security.Capability authorize(
        fabric.lang.security.Principal p, java.lang.Object authPrf,
        fabric.lang.security.Closure c, fabric.lang.security.Label lb);

    private static native fabric.lang.security.Capability authorize(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object authPrf, fabric.lang.security.Closure c,
        fabric.lang.security.Label lb, boolean executeNow);

    /**
     * returns the null principal, the principal that every other principal
     can
     * act for.
     */
    public static native fabric.lang.security.Principal nullPrincipal();

    public static native fabric.lang.security.Principal bottomPrincipal();

    public static native fabric.lang.security.Principal topPrincipal();

    public static native boolean isTopPrincipal(
        fabric.lang.security.Principal p);

    public static native fabric.lang.security.ConfPolicy readableByPrinPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal p);

    public static native fabric.lang.security.IntegPolicy writableByPrinPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal p);

    public static native fabric.lang.security.Principal disjunction(
        fabric.worker.Store store, fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    public static native fabric.lang.security.Principal conjunction(
        fabric.worker.Store store, fabric.lang.security.Principal left,
        fabric.lang.security.Principal right);

    public static native fabric.lang.security.Principal disjunction(
        fabric.worker.Store store, fabric.util.Collection principals);

    public static native fabric.lang.security.Principal conjunction(
        fabric.worker.Store store, fabric.util.Collection principals);

    public static native java.lang.String toString(
        fabric.lang.security.Principal p);

    public static native java.lang.String stringValue(
        fabric.lang.security.Principal p);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.PrincipalUtil._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, observers, treaties, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.PrincipalUtil._Static {

      public _Proxy(fabric.lang.security.PrincipalUtil._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.PrincipalUtil._Static $instance;

      static {
        fabric.lang.security.PrincipalUtil._Static._Impl impl =
            (fabric.lang.security.PrincipalUtil._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.PrincipalUtil._Static._Impl.class);
        $instance =
            (fabric.lang.security.PrincipalUtil._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.PrincipalUtil._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, observers, treaties, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.PrincipalUtil._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
