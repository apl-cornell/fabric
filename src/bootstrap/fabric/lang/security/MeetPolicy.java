package fabric.lang.security;

/**
 * Abstract class representing the meet of policies. All the policies should be
 * of the same kind, either all IntegPolicies or all ConfPolicies. This code is
 * mostly copied from Jif.
 */
public interface MeetPolicy
    extends fabric.lang.security.Policy, fabric.lang.security.AbstractPolicy {

  public fabric.util.Set get$components();

  public fabric.util.Set set$components(fabric.util.Set val);

  public fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
      fabric.util.Set policies);

  public fabric.util.Set meetComponents();

  public boolean relabelsTo(fabric.lang.security.Policy pol, java.util.Set s);

  public boolean equals(fabric.lang.Object o);

  public int hashCode();

  public java.lang.String toString();

  public static class _Proxy extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.MeetPolicy {

    public fabric.util.Set get$components() {
      return ((fabric.lang.security.MeetPolicy._Impl) fetch()).get$components();
    }

    public fabric.util.Set set$components(fabric.util.Set val) {
      return ((fabric.lang.security.MeetPolicy._Impl) fetch())
          .set$components(val);
    }

    public native fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
        fabric.util.Set arg1);

    public native fabric.util.Set meetComponents();

    public native boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public _Proxy(MeetPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public abstract static class _Impl
      extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.MeetPolicy {

    public fabric.util.Set get$components() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.components;
    }

    public fabric.util.Set set$components(fabric.util.Set val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.components = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.util.Set components;

    public native fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
        fabric.util.Set policies);

    public native fabric.util.Set meetComponents();

    public native boolean relabelsTo(fabric.lang.security.Policy pol,
        java.util.Set s);

    public native boolean equals(fabric.lang.Object o);

    public final native int hashCode();

    public final native java.lang.String toString();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.MeetPolicy._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.components, refTypes, out, intraStoreRefs,
          interStoreRefs);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, associates, treaties, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      this.components = (fabric.util.Set) $readRef(fabric.util.Set._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.MeetPolicy._Impl src =
          (fabric.lang.security.MeetPolicy._Impl) other;
      this.components = src.components;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.MeetPolicy._Static {

      public _Proxy(fabric.lang.security.MeetPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.MeetPolicy._Static $instance;

      static {
        fabric.lang.security.MeetPolicy._Static._Impl impl =
            (fabric.lang.security.MeetPolicy._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.MeetPolicy._Static._Impl.class);
        $instance = (fabric.lang.security.MeetPolicy._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.MeetPolicy._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, associates, treaties, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.MeetPolicy._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
