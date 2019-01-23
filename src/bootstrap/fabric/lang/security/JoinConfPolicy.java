package fabric.lang.security;

/**
 * Represents the join of confidentiality policies. This code is mostly copied
 * from Jif.
 */
public interface JoinConfPolicy
    extends fabric.lang.security.ConfPolicy, fabric.lang.security.JoinPolicy {

  public fabric.lang.security.JoinConfPolicy fabric$lang$security$JoinConfPolicy$(
      fabric.util.Set policies);

  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, boolean simplify);

  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, boolean simplify);

  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s, boolean simplify);

  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s, boolean simplify);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.JoinPolicy._Proxy
      implements fabric.lang.security.JoinConfPolicy {

    public native fabric.lang.security.JoinConfPolicy fabric$lang$security$JoinConfPolicy$(
        fabric.util.Set arg1);

    public native fabric.lang.security.ConfPolicy join(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3);

    public native fabric.lang.security.ConfPolicy meet(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3);

    public native fabric.lang.security.ConfPolicy join(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2);

    public native fabric.lang.security.ConfPolicy meet(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2);

    public native fabric.lang.security.ConfPolicy join(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, boolean arg3);

    public native fabric.lang.security.ConfPolicy meet(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, boolean arg3);

    public native fabric.lang.security.ConfPolicy join(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3, boolean arg4);

    public native fabric.lang.security.ConfPolicy meet(fabric.worker.Store arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3, boolean arg4);

    public _Proxy(JoinConfPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.security.JoinPolicy._Impl
      implements fabric.lang.security.JoinConfPolicy {

    public native fabric.lang.security.JoinConfPolicy fabric$lang$security$JoinConfPolicy$(
        fabric.util.Set policies);

    public native fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s);

    public native fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s);

    public native fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p);

    public native fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p);

    public native fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        boolean simplify);

    public native fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        boolean simplify);

    public native fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s, boolean simplify);

    public native fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s, boolean simplify);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.JoinConfPolicy._Proxy(this);
    }

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
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.JoinConfPolicy._Static {

      public _Proxy(fabric.lang.security.JoinConfPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.JoinConfPolicy._Static $instance;

      static {
        fabric.lang.security.JoinConfPolicy._Static._Impl impl =
            (fabric.lang.security.JoinConfPolicy._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.JoinConfPolicy._Static._Impl.class);
        $instance =
            (fabric.lang.security.JoinConfPolicy._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.JoinConfPolicy._Static {

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
        return new fabric.lang.security.JoinConfPolicy._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
