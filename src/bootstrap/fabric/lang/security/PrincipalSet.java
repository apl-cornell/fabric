package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface PrincipalSet extends fabric.lang.Object {

  public fabric.util.Set get$set();

  public fabric.util.Set set$set(fabric.util.Set val);

  public fabric.lang.security.PrincipalSet fabric$lang$security$PrincipalSet$();

  public fabric.lang.security.PrincipalSet add(
      fabric.lang.security.Principal p);

  public fabric.util.Set getSet();

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrincipalSet {

    public fabric.util.Set get$set() {
      return ((fabric.lang.security.PrincipalSet._Impl) fetch()).get$set();
    }

    public fabric.util.Set set$set(fabric.util.Set val) {
      return ((fabric.lang.security.PrincipalSet._Impl) fetch()).set$set(val);
    }

    public native fabric.lang.security.PrincipalSet fabric$lang$security$PrincipalSet$();

    public native fabric.lang.security.PrincipalSet add(
        fabric.lang.security.Principal arg1);

    public native fabric.util.Set getSet();

    public _Proxy(PrincipalSet._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrincipalSet {

    public fabric.util.Set get$set() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.set;
    }

    public fabric.util.Set set$set(fabric.util.Set val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.set = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.util.Set set;

    public native fabric.lang.security.PrincipalSet fabric$lang$security$PrincipalSet$();

    public native fabric.lang.security.PrincipalSet add(
        fabric.lang.security.Principal p);

    public native fabric.util.Set getSet();

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.PrincipalSet._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.set, refTypes, out, intraStoreRefs,
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
      this.set = (fabric.util.Set) $readRef(fabric.util.Set._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.PrincipalSet._Impl src =
          (fabric.lang.security.PrincipalSet._Impl) other;
      this.set = src.set;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.PrincipalSet._Static {

      public _Proxy(fabric.lang.security.PrincipalSet._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.PrincipalSet._Static $instance;

      static {
        fabric.lang.security.PrincipalSet._Static._Impl impl =
            (fabric.lang.security.PrincipalSet._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.PrincipalSet._Static._Impl.class);
        $instance =
            (fabric.lang.security.PrincipalSet._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.PrincipalSet._Static {

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
        return new fabric.lang.security.PrincipalSet._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
