package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface ActsForProof extends fabric.lang.Object {

  public fabric.lang.security.Principal get$actor();

  public fabric.lang.security.Principal set$actor(
      fabric.lang.security.Principal val);

  public fabric.lang.security.Principal get$granter();

  public fabric.lang.security.Principal set$granter(
      fabric.lang.security.Principal val);

  public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);

  public fabric.lang.security.Principal getActor();

  public fabric.lang.security.Principal getGranter();

  public abstract void gatherDelegationDependencies(java.util.Set s);

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ActsForProof {

    public fabric.lang.security.Principal get$actor() {
      return ((fabric.lang.security.ActsForProof._Impl) fetch()).get$actor();
    }

    public fabric.lang.security.Principal set$actor(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.ActsForProof._Impl) fetch()).set$actor(val);
    }

    public fabric.lang.security.Principal get$granter() {
      return ((fabric.lang.security.ActsForProof._Impl) fetch()).get$granter();
    }

    public fabric.lang.security.Principal set$granter(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.ActsForProof._Impl) fetch())
          .set$granter(val);
    }

    public native fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public native fabric.lang.security.Principal getActor();

    public native fabric.lang.security.Principal getGranter();

    public void gatherDelegationDependencies(java.util.Set arg1) {
      ((fabric.lang.security.ActsForProof) fetch())
          .gatherDelegationDependencies(arg1);
    }

    public _Proxy(ActsForProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.ActsForProof {

    public fabric.lang.security.Principal get$actor() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.actor;
    }

    public fabric.lang.security.Principal set$actor(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.actor = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.Principal actor;

    public fabric.lang.security.Principal get$granter() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.granter;
    }

    public fabric.lang.security.Principal set$granter(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.granter = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.Principal granter;

    public native fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
        fabric.lang.security.Principal actor,
        fabric.lang.security.Principal granter);

    public native fabric.lang.security.Principal getActor();

    public native fabric.lang.security.Principal getGranter();

    public abstract void gatherDelegationDependencies(java.util.Set s);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.ActsForProof._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.actor, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.granter, refTypes, out, intraStoreRefs,
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
      this.actor = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.granter = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.ActsForProof._Impl src =
          (fabric.lang.security.ActsForProof._Impl) other;
      this.actor = src.actor;
      this.granter = src.granter;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.ActsForProof._Static {

      public _Proxy(fabric.lang.security.ActsForProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.ActsForProof._Static $instance;

      static {
        fabric.lang.security.ActsForProof._Static._Impl impl =
            (fabric.lang.security.ActsForProof._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.ActsForProof._Static._Impl.class);
        $instance =
            (fabric.lang.security.ActsForProof._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.ActsForProof._Static {

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
        return new fabric.lang.security.ActsForProof._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
