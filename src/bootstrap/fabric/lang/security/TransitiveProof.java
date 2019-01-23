package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface TransitiveProof extends fabric.lang.security.ActsForProof {

  public fabric.lang.security.ActsForProof get$actorToP();

  public fabric.lang.security.ActsForProof set$actorToP(
      fabric.lang.security.ActsForProof val);

  public fabric.lang.security.ActsForProof get$pToGranter();

  public fabric.lang.security.ActsForProof set$pToGranter(
      fabric.lang.security.ActsForProof val);

  public fabric.lang.security.Principal get$p();

  public fabric.lang.security.Principal set$p(
      fabric.lang.security.Principal val);

  public fabric.lang.security.TransitiveProof fabric$lang$security$TransitiveProof$(
      fabric.lang.security.ActsForProof actorToP,
      fabric.lang.security.Principal p,
      fabric.lang.security.ActsForProof pToGranter);

  public fabric.lang.security.ActsForProof getActorToP();

  public fabric.lang.security.ActsForProof getPToGranter();

  public fabric.lang.security.Principal getP();

  public void gatherDelegationDependencies(java.util.Set s);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.TransitiveProof {

    public fabric.lang.security.ActsForProof get$actorToP() {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch())
          .get$actorToP();
    }

    public fabric.lang.security.ActsForProof set$actorToP(
        fabric.lang.security.ActsForProof val) {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch())
          .set$actorToP(val);
    }

    public fabric.lang.security.ActsForProof get$pToGranter() {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch())
          .get$pToGranter();
    }

    public fabric.lang.security.ActsForProof set$pToGranter(
        fabric.lang.security.ActsForProof val) {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch())
          .set$pToGranter(val);
    }

    public fabric.lang.security.Principal get$p() {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch()).get$p();
    }

    public fabric.lang.security.Principal set$p(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.TransitiveProof._Impl) fetch()).set$p(val);
    }

    public native fabric.lang.security.TransitiveProof fabric$lang$security$TransitiveProof$(
        fabric.lang.security.ActsForProof arg1,
        fabric.lang.security.Principal arg2,
        fabric.lang.security.ActsForProof arg3);

    public native fabric.lang.security.ActsForProof getActorToP();

    public native fabric.lang.security.ActsForProof getPToGranter();

    public native fabric.lang.security.Principal getP();

    public _Proxy(TransitiveProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl
      extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.TransitiveProof {

    public fabric.lang.security.ActsForProof get$actorToP() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.actorToP;
    }

    public fabric.lang.security.ActsForProof set$actorToP(
        fabric.lang.security.ActsForProof val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.actorToP = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.ActsForProof actorToP;

    public fabric.lang.security.ActsForProof get$pToGranter() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.pToGranter;
    }

    public fabric.lang.security.ActsForProof set$pToGranter(
        fabric.lang.security.ActsForProof val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.pToGranter = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.ActsForProof pToGranter;

    public fabric.lang.security.Principal get$p() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.p;
    }

    public fabric.lang.security.Principal set$p(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.p = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.Principal p;

    public native fabric.lang.security.TransitiveProof fabric$lang$security$TransitiveProof$(
        fabric.lang.security.ActsForProof actorToP,
        fabric.lang.security.Principal p,
        fabric.lang.security.ActsForProof pToGranter);

    public native fabric.lang.security.ActsForProof getActorToP();

    public native fabric.lang.security.ActsForProof getPToGranter();

    public native fabric.lang.security.Principal getP();

    public native void gatherDelegationDependencies(java.util.Set s);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.TransitiveProof._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.actorToP, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.pToGranter, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.p, refTypes, out, intraStoreRefs,
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
      this.actorToP = (fabric.lang.security.ActsForProof) $readRef(
          fabric.lang.security.ActsForProof._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.pToGranter = (fabric.lang.security.ActsForProof) $readRef(
          fabric.lang.security.ActsForProof._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.p = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.TransitiveProof._Impl src =
          (fabric.lang.security.TransitiveProof._Impl) other;
      this.actorToP = src.actorToP;
      this.pToGranter = src.pToGranter;
      this.p = src.p;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.TransitiveProof._Static {

      public _Proxy(fabric.lang.security.TransitiveProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.TransitiveProof._Static $instance;

      static {
        fabric.lang.security.TransitiveProof._Static._Impl impl =
            (fabric.lang.security.TransitiveProof._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.TransitiveProof._Static._Impl.class);
        $instance =
            (fabric.lang.security.TransitiveProof._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.TransitiveProof._Static {

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
        return new fabric.lang.security.TransitiveProof._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
