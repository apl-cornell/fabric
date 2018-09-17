package fabric.lang.security;

/**
 * This code is mostly copied from Jif.
 */
public interface ReflexiveProof extends fabric.lang.security.ActsForProof {

  /**
   * Either p == q or p and q are non null and p.equals(q) and q.equals(p)
   *
   * @param p
   * @param q
   */
  public fabric.lang.security.ReflexiveProof fabric$lang$security$ReflexiveProof$(
      fabric.lang.security.Principal p, fabric.lang.security.Principal q);

  public void gatherDelegationDependencies(java.util.Set s);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.ReflexiveProof {

    public native fabric.lang.security.ReflexiveProof fabric$lang$security$ReflexiveProof$(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.Principal arg2);

    public _Proxy(ReflexiveProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl
      extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.ReflexiveProof {

    /**
     * Either p == q or p and q are non null and p.equals(q) and q.equals(p)
     *
     * @param p
     * @param q
     */
    public native fabric.lang.security.ReflexiveProof fabric$lang$security$ReflexiveProof$(
        fabric.lang.security.Principal p, fabric.lang.security.Principal q);

    public native void gatherDelegationDependencies(java.util.Set s);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.ReflexiveProof._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, associates, observers, treaties, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.ReflexiveProof._Static {

      public _Proxy(fabric.lang.security.ReflexiveProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.ReflexiveProof._Static $instance;

      static {
        fabric.lang.security.ReflexiveProof._Static._Impl impl =
            (fabric.lang.security.ReflexiveProof._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.ReflexiveProof._Static._Impl.class);
        $instance =
            (fabric.lang.security.ReflexiveProof._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.ReflexiveProof._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, associates, observers, treaties, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.ReflexiveProof._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
