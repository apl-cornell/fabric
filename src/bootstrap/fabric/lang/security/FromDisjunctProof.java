package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface FromDisjunctProof extends fabric.lang.security.ActsForProof {

  public fabric.util.Map get$disjunctProofs();

  public fabric.util.Map set$disjunctProofs(fabric.util.Map val);

  public fabric.lang.security.FromDisjunctProof fabric$lang$security$FromDisjunctProof$(
      fabric.lang.security.DisjunctivePrincipal actor,
      fabric.lang.security.Principal granter, fabric.util.Map disjunctProofs);

  public fabric.util.Map getDisjunctProofs();

  public void gatherDelegationDependencies(java.util.Set s);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.FromDisjunctProof {

    public fabric.util.Map get$disjunctProofs() {
      return ((fabric.lang.security.FromDisjunctProof._Impl) fetch())
          .get$disjunctProofs();
    }

    public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
      return ((fabric.lang.security.FromDisjunctProof._Impl) fetch())
          .set$disjunctProofs(val);
    }

    public native fabric.lang.security.FromDisjunctProof fabric$lang$security$FromDisjunctProof$(
        fabric.lang.security.DisjunctivePrincipal arg1,
        fabric.lang.security.Principal arg2, fabric.util.Map arg3);

    public native fabric.util.Map getDisjunctProofs();

    public _Proxy(FromDisjunctProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl
      extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.FromDisjunctProof {

    public fabric.util.Map get$disjunctProofs() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.disjunctProofs;
    }

    public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.disjunctProofs = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.util.Map disjunctProofs;

    public native fabric.lang.security.FromDisjunctProof fabric$lang$security$FromDisjunctProof$(
        fabric.lang.security.DisjunctivePrincipal actor,
        fabric.lang.security.Principal granter, fabric.util.Map disjunctProofs);

    public native fabric.util.Map getDisjunctProofs();

    public native void gatherDelegationDependencies(java.util.Set s);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.FromDisjunctProof._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.disjunctProofs, refTypes, out, intraStoreRefs,
          interStoreRefs);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      this.disjunctProofs =
          (fabric.util.Map) $readRef(fabric.util.Map._Proxy.class,
              (fabric.common.RefTypeEnum) refTypes.next(), in, store,
              intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.FromDisjunctProof._Impl src =
          (fabric.lang.security.FromDisjunctProof._Impl) other;
      this.disjunctProofs = src.disjunctProofs;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.FromDisjunctProof._Static {

      public _Proxy(fabric.lang.security.FromDisjunctProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.FromDisjunctProof._Static $instance;

      static {
        fabric.lang.security.FromDisjunctProof._Static._Impl impl =
            (fabric.lang.security.FromDisjunctProof._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.FromDisjunctProof._Static._Impl.class);
        $instance =
            (fabric.lang.security.FromDisjunctProof._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.FromDisjunctProof._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.FromDisjunctProof._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
