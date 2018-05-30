package fabric.lang.security;

/**
 * A disjunction of two (non-null) principals. This code is mostly copied from
 * Jif.
 */
public interface DisjunctivePrincipal extends fabric.lang.security.Principal {

  public fabric.util.Set get$disjuncts();

  public fabric.util.Set set$disjuncts(fabric.util.Set val);

  public java.lang.Integer get$hashCode();

  public java.lang.Integer set$hashCode(java.lang.Integer val);

  public fabric.lang.security.DisjunctivePrincipal fabric$lang$security$DisjunctivePrincipal$(
      fabric.util.Set disjuncts);

  public java.lang.String name();

  public boolean delegatesTo(fabric.lang.security.Principal p);

  public int hashCode();

  public boolean equals(fabric.lang.security.Principal p);

  public boolean isAuthorized(java.lang.Object authPrf,
      fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
      boolean executeNow);

  public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);

  public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.security.Principal._Proxy
      implements fabric.lang.security.DisjunctivePrincipal {

    public fabric.util.Set get$disjuncts() {
      return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch())
          .get$disjuncts();
    }

    public fabric.util.Set set$disjuncts(fabric.util.Set val) {
      return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch())
          .set$disjuncts(val);
    }

    public java.lang.Integer get$hashCode() {
      return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch())
          .get$hashCode();
    }

    public java.lang.Integer set$hashCode(java.lang.Integer val) {
      return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch())
          .set$hashCode(val);
    }

    public native fabric.lang.security.DisjunctivePrincipal fabric$lang$security$DisjunctivePrincipal$(
        fabric.util.Set arg1);

    public _Proxy(DisjunctivePrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.security.Principal._Impl
      implements fabric.lang.security.DisjunctivePrincipal {

    public fabric.util.Set get$disjuncts() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.disjuncts;
    }

    public fabric.util.Set set$disjuncts(fabric.util.Set val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.disjuncts = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    fabric.util.Set disjuncts;

    public java.lang.Integer get$hashCode() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.hashCode;
    }

    public java.lang.Integer set$hashCode(java.lang.Integer val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.hashCode = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private java.lang.Integer hashCode;

    public native fabric.lang.security.DisjunctivePrincipal fabric$lang$security$DisjunctivePrincipal$(
        fabric.util.Set disjuncts);

    public native java.lang.String name();

    public native boolean delegatesTo(fabric.lang.security.Principal p);

    public native int hashCode();

    public native boolean equals(fabric.lang.security.Principal p);

    public native boolean isAuthorized(java.lang.Object authPrf,
        fabric.lang.security.Closure closure, fabric.lang.security.Label lb,
        boolean executeNow);

    public native fabric.lang.security.ActsForProof findProofUpto(
        fabric.worker.Store store, fabric.lang.security.Principal p,
        java.lang.Object searchState);

    public native fabric.lang.security.ActsForProof findProofDownto(
        fabric.worker.Store store, fabric.lang.security.Principal q,
        java.lang.Object searchState);

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.DisjunctivePrincipal._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.disjuncts, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeInline(out, this.hashCode);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObserverSet observers,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, observers, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      this.disjuncts = (fabric.util.Set) $readRef(fabric.util.Set._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.hashCode = (java.lang.Integer) in.readObject();
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.DisjunctivePrincipal._Impl src =
          (fabric.lang.security.DisjunctivePrincipal._Impl) other;
      this.disjuncts = src.disjuncts;
      this.hashCode = src.hashCode;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.DisjunctivePrincipal._Static {

      public _Proxy(
          fabric.lang.security.DisjunctivePrincipal._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.DisjunctivePrincipal._Static $instance;

      static {
        fabric.lang.security.DisjunctivePrincipal._Static._Impl impl =
            (fabric.lang.security.DisjunctivePrincipal._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.DisjunctivePrincipal._Static._Impl.class);
        $instance = (fabric.lang.security.DisjunctivePrincipal._Static) impl
            .$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.DisjunctivePrincipal._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObserverSet observers,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, observers, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.DisjunctivePrincipal._Static._Proxy(
            this);
      }

      private void $init() {
      }
    }

  }

}
