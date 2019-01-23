package fabric.lang.security;

/**
 * A Label is the runtime representation of a Fabric label. A Label consists of
 * a set of components, each of which is a {@link fabric.lang.security.Policy
 Policy}.
 * This code is mostly copied from Jif.
 */
public interface AbstractPolicy
    extends fabric.lang.security.Policy, fabric.lang.Object {

  public fabric.lang.security.AbstractPolicy fabric$lang$security$AbstractPolicy$();

  public abstract boolean equals(fabric.lang.Object that);

  public abstract int hashCode();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.AbstractPolicy {

    public native fabric.lang.security.AbstractPolicy fabric$lang$security$AbstractPolicy$();

    public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2) {
      return ((fabric.lang.security.AbstractPolicy) fetch()).relabelsTo(arg1,
          arg2);
    }

    public _Proxy(AbstractPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.AbstractPolicy {

    public native fabric.lang.security.AbstractPolicy fabric$lang$security$AbstractPolicy$();

    public abstract boolean equals(fabric.lang.Object that);

    public abstract int hashCode();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.AbstractPolicy._Proxy(this);
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
        implements fabric.lang.security.AbstractPolicy._Static {

      public _Proxy(fabric.lang.security.AbstractPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.AbstractPolicy._Static $instance;

      static {
        fabric.lang.security.AbstractPolicy._Static._Impl impl =
            (fabric.lang.security.AbstractPolicy._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.AbstractPolicy._Static._Impl.class);
        $instance =
            (fabric.lang.security.AbstractPolicy._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.AbstractPolicy._Static {

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
        return new fabric.lang.security.AbstractPolicy._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
