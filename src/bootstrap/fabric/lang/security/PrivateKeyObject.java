package fabric.lang.security;

/**
 * This wraps a Java PrivateKey object.
 */
public interface PrivateKeyObject extends fabric.lang.Object {

  public fabric.lang.security.Principal get$principal();

  public fabric.lang.security.Principal set$principal(
      fabric.lang.security.Principal val);

  public java.security.PrivateKey get$key();

  public java.security.PrivateKey set$key(java.security.PrivateKey val);

  /**
   * @param p the principal to which this key belongs.
   */
  public fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
      fabric.lang.security.Principal p, java.security.PrivateKey key);

  public fabric.lang.Object $initLabels();

  public java.security.PrivateKey getKey();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrivateKeyObject {

    public fabric.lang.security.Principal get$principal() {
      return ((fabric.lang.security.PrivateKeyObject._Impl) fetch())
          .get$principal();
    }

    public fabric.lang.security.Principal set$principal(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.PrivateKeyObject._Impl) fetch())
          .set$principal(val);
    }

    public java.security.PrivateKey get$key() {
      return ((fabric.lang.security.PrivateKeyObject._Impl) fetch()).get$key();
    }

    public java.security.PrivateKey set$key(java.security.PrivateKey val) {
      return ((fabric.lang.security.PrivateKeyObject._Impl) fetch())
          .set$key(val);
    }

    public native fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
        fabric.lang.security.Principal arg1, java.security.PrivateKey arg2);

    public native java.security.PrivateKey getKey();

    public _Proxy(PrivateKeyObject._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrivateKeyObject {

    public fabric.lang.security.Principal get$principal() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.principal;
    }

    public fabric.lang.security.Principal set$principal(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.principal = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    /**
     * The principal to which this key belongs.
     */
    private fabric.lang.security.Principal principal;

    public java.security.PrivateKey get$key() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.key;
    }

    public java.security.PrivateKey set$key(java.security.PrivateKey val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.key = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private java.security.PrivateKey key;

    /**
     * @param p the principal to which this key belongs.
     */
    public native fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
        fabric.lang.security.Principal p, java.security.PrivateKey key);

    public native fabric.lang.Object $initLabels();

    public native java.security.PrivateKey getKey();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.PrivateKeyObject._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.principal, refTypes, out, intraStoreRefs,
          interStoreRefs);
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
      this.principal = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.PrivateKeyObject._Impl src =
          (fabric.lang.security.PrivateKeyObject._Impl) other;
      this.principal = src.principal;
      this.key = src.key;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.PrivateKeyObject._Static {

      public _Proxy(fabric.lang.security.PrivateKeyObject._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.PrivateKeyObject._Static $instance;

      static {
        fabric.lang.security.PrivateKeyObject._Static._Impl impl =
            (fabric.lang.security.PrivateKeyObject._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.PrivateKeyObject._Static._Impl.class);
        $instance =
            (fabric.lang.security.PrivateKeyObject._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.PrivateKeyObject._Static {

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
        return new fabric.lang.security.PrivateKeyObject._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
