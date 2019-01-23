package fabric.lang.security;

/**
 * Represents the join of integrity policies. This code is mostly copied from
 * Jif.
 */
public interface JoinIntegPolicy
    extends fabric.lang.security.IntegPolicy, fabric.lang.security.JoinPolicy {

  public fabric.lang.security.JoinIntegPolicy fabric$lang$security$JoinIntegPolicy$(
      fabric.util.Set policies);

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

  public static class _Proxy extends fabric.lang.security.JoinPolicy._Proxy
      implements fabric.lang.security.JoinIntegPolicy {

    public native fabric.lang.security.JoinIntegPolicy fabric$lang$security$JoinIntegPolicy$(
        fabric.util.Set arg1);

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

    public _Proxy(JoinIntegPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.security.JoinPolicy._Impl
      implements fabric.lang.security.JoinIntegPolicy {

    public native fabric.lang.security.JoinIntegPolicy fabric$lang$security$JoinIntegPolicy$(
        fabric.util.Set policies);

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
      return new fabric.lang.security.JoinIntegPolicy._Proxy(this);
    }

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
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.JoinIntegPolicy._Static {

      public _Proxy(fabric.lang.security.JoinIntegPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.JoinIntegPolicy._Static $instance;

      static {
        fabric.lang.security.JoinIntegPolicy._Static._Impl impl =
            (fabric.lang.security.JoinIntegPolicy._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.JoinIntegPolicy._Static._Impl.class);
        $instance =
            (fabric.lang.security.JoinIntegPolicy._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.JoinIntegPolicy._Static {

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
        return new fabric.lang.security.JoinIntegPolicy._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
