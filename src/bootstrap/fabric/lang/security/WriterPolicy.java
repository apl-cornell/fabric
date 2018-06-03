package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface WriterPolicy extends fabric.lang.security.IntegPolicy,
    fabric.lang.security.AbstractPolicy {

  public fabric.lang.security.Principal get$owner();

  public fabric.lang.security.Principal set$owner(
      fabric.lang.security.Principal val);

  public fabric.lang.security.Principal get$writer();

  public fabric.lang.security.Principal set$writer(
      fabric.lang.security.Principal val);

  public fabric.lang.security.WriterPolicy fabric$lang$security$WriterPolicy$(
      fabric.lang.security.Principal owner,
      fabric.lang.security.Principal writer);

  public fabric.lang.security.Principal owner();

  public fabric.lang.security.Principal writer();

  public boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);

  public int hashCode();

  public boolean equals(fabric.lang.Object o);

  public java.lang.String toString();

  public fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set s);

  public fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set s);

  public fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p);

  public fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p);

  public fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, boolean simplify);

  public fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, boolean simplify);

  public fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set s, boolean simplify);

  public fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set s, boolean simplify);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.WriterPolicy {

    public fabric.lang.security.Principal get$owner() {
      return ((fabric.lang.security.WriterPolicy._Impl) fetch()).get$owner();
    }

    public fabric.lang.security.Principal set$owner(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.WriterPolicy._Impl) fetch()).set$owner(val);
    }

    public fabric.lang.security.Principal get$writer() {
      return ((fabric.lang.security.WriterPolicy._Impl) fetch()).get$writer();
    }

    public fabric.lang.security.Principal set$writer(
        fabric.lang.security.Principal val) {
      return ((fabric.lang.security.WriterPolicy._Impl) fetch())
          .set$writer(val);
    }

    public native fabric.lang.security.WriterPolicy fabric$lang$security$WriterPolicy$(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public native fabric.lang.security.Principal owner();

    public native fabric.lang.security.Principal writer();

    public native boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        boolean arg3);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        boolean arg3);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3, boolean arg4);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3, boolean arg4);

    public _Proxy(WriterPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.WriterPolicy {

    public fabric.lang.security.Principal get$owner() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.owner;
    }

    public fabric.lang.security.Principal set$owner(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.owner = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.Principal owner;

    public fabric.lang.security.Principal get$writer() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.writer;
    }

    public fabric.lang.security.Principal set$writer(
        fabric.lang.security.Principal val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.writer = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.Principal writer;

    public native fabric.lang.security.WriterPolicy fabric$lang$security$WriterPolicy$(
        fabric.lang.security.Principal owner,
        fabric.lang.security.Principal writer);

    public native fabric.lang.security.Principal owner();

    public native fabric.lang.security.Principal writer();

    public native boolean relabelsTo(fabric.lang.security.Policy p,
        java.util.Set s);

    public native int hashCode();

    public native boolean equals(fabric.lang.Object o);

    public native java.lang.String toString();

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        java.util.Set s);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        java.util.Set s);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        boolean simplify);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        boolean simplify);

    public native fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        java.util.Set s, boolean simplify);

    public native fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
        java.util.Set s, boolean simplify);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.WriterPolicy._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.writer, refTypes, out, intraStoreRefs,
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
      this.owner = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.writer = (fabric.lang.security.Principal) $readRef(
          fabric.lang.security.Principal._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.WriterPolicy._Impl src =
          (fabric.lang.security.WriterPolicy._Impl) other;
      this.owner = src.owner;
      this.writer = src.writer;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.WriterPolicy._Static {

      public _Proxy(fabric.lang.security.WriterPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.WriterPolicy._Static $instance;

      static {
        fabric.lang.security.WriterPolicy._Static._Impl impl =
            (fabric.lang.security.WriterPolicy._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.WriterPolicy._Static._Impl.class);
        $instance =
            (fabric.lang.security.WriterPolicy._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.WriterPolicy._Static {

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
        return new fabric.lang.security.WriterPolicy._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
