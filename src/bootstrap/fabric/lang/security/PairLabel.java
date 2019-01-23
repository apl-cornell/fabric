package fabric.lang.security;

/**
 * A Label is the runtime representation of a Jif label. A Label consists of a
 * set of components, each of which is a Policy. This code is mostly copied from
 * Jif.
 */
public interface PairLabel
    extends fabric.lang.security.Label, fabric.lang.Object {

  public fabric.lang.security.ConfPolicy get$confPol();

  public fabric.lang.security.ConfPolicy set$confPol(
      fabric.lang.security.ConfPolicy val);

  public fabric.lang.security.IntegPolicy get$integPol();

  public fabric.lang.security.IntegPolicy set$integPol(
      fabric.lang.security.IntegPolicy val);

  public fabric.lang.security.SecretKeyObject get$keyObject();

  public fabric.lang.security.SecretKeyObject set$keyObject(
      fabric.lang.security.SecretKeyObject val);

  public fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
      fabric.lang.security.ConfPolicy confPol,
      fabric.lang.security.IntegPolicy integPol);

  public boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);

  public int hashCode();

  public boolean equals(fabric.lang.Object o);

  public java.lang.String toString();

  public fabric.lang.security.Label join(fabric.worker.Store store,
      fabric.lang.security.Label l);

  public fabric.lang.security.Label meet(fabric.worker.Store store,
      fabric.lang.security.Label l);

  public fabric.lang.security.Label join(fabric.worker.Store store,
      fabric.lang.security.Label l, boolean simplify);

  public fabric.lang.security.Label meet(fabric.worker.Store store,
      fabric.lang.security.Label l, boolean simplify);

  public fabric.lang.security.ConfPolicy confPolicy();

  public fabric.lang.security.IntegPolicy integPolicy();

  public fabric.lang.security.SecretKeyObject keyObject();

  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PairLabel {

    public fabric.lang.security.ConfPolicy get$confPol() {
      return ((fabric.lang.security.PairLabel._Impl) fetch()).get$confPol();
    }

    public fabric.lang.security.ConfPolicy set$confPol(
        fabric.lang.security.ConfPolicy val) {
      return ((fabric.lang.security.PairLabel._Impl) fetch()).set$confPol(val);
    }

    public fabric.lang.security.IntegPolicy get$integPol() {
      return ((fabric.lang.security.PairLabel._Impl) fetch()).get$integPol();
    }

    public fabric.lang.security.IntegPolicy set$integPol(
        fabric.lang.security.IntegPolicy val) {
      return ((fabric.lang.security.PairLabel._Impl) fetch()).set$integPol(val);
    }

    public fabric.lang.security.SecretKeyObject get$keyObject() {
      return ((fabric.lang.security.PairLabel._Impl) fetch()).get$keyObject();
    }

    public fabric.lang.security.SecretKeyObject set$keyObject(
        fabric.lang.security.SecretKeyObject val) {
      return ((fabric.lang.security.PairLabel._Impl) fetch())
          .set$keyObject(val);
    }

    public native fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.IntegPolicy arg2);

    public native boolean relabelsTo(fabric.lang.security.Label arg1,
        java.util.Set arg2);

    public final native fabric.lang.security.Label join(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2);

    public native fabric.lang.security.Label meet(fabric.worker.Store arg1,
        fabric.lang.security.Label arg2);

    public final native fabric.lang.security.Label join(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        boolean arg3);

    public native fabric.lang.security.Label meet(fabric.worker.Store arg1,
        fabric.lang.security.Label arg2, boolean arg3);

    public native fabric.lang.security.ConfPolicy confPolicy();

    public native fabric.lang.security.IntegPolicy integPolicy();

    public native fabric.lang.security.SecretKeyObject keyObject();

    public _Proxy(PairLabel._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PairLabel {

    public fabric.lang.security.ConfPolicy get$confPol() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.confPol;
    }

    public fabric.lang.security.ConfPolicy set$confPol(
        fabric.lang.security.ConfPolicy val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.confPol = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.ConfPolicy confPol;

    public fabric.lang.security.IntegPolicy get$integPol() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.integPol;
    }

    public fabric.lang.security.IntegPolicy set$integPol(
        fabric.lang.security.IntegPolicy val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.integPol = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.IntegPolicy integPol;

    public fabric.lang.security.SecretKeyObject get$keyObject() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.keyObject;
    }

    public fabric.lang.security.SecretKeyObject set$keyObject(
        fabric.lang.security.SecretKeyObject val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.keyObject = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.security.SecretKeyObject keyObject;

    public native fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
        fabric.lang.security.ConfPolicy confPol,
        fabric.lang.security.IntegPolicy integPol);

    public native boolean relabelsTo(fabric.lang.security.Label l,
        java.util.Set s);

    public native int hashCode();

    public native boolean equals(fabric.lang.Object o);

    public final native java.lang.String toString();

    public final native fabric.lang.security.Label join(
        fabric.worker.Store store, fabric.lang.security.Label l);

    public native fabric.lang.security.Label meet(fabric.worker.Store store,
        fabric.lang.security.Label l);

    public final native fabric.lang.security.Label join(
        fabric.worker.Store store, fabric.lang.security.Label l,
        boolean simplify);

    public native fabric.lang.security.Label meet(fabric.worker.Store store,
        fabric.lang.security.Label l, boolean simplify);

    public native fabric.lang.security.ConfPolicy confPolicy();

    public native fabric.lang.security.IntegPolicy integPolicy();

    public native fabric.lang.security.SecretKeyObject keyObject();

    public native fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.security.PairLabel._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      $writeRef($getStore(), this.confPol, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.integPol, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.keyObject, refTypes, out, intraStoreRefs,
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
      this.confPol = (fabric.lang.security.ConfPolicy) $readRef(
          fabric.lang.security.ConfPolicy._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.integPol = (fabric.lang.security.IntegPolicy) $readRef(
          fabric.lang.security.IntegPolicy._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.keyObject = (fabric.lang.security.SecretKeyObject) $readRef(
          fabric.lang.security.SecretKeyObject._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.security.PairLabel._Impl src =
          (fabric.lang.security.PairLabel._Impl) other;
      this.confPol = src.confPol;
      this.integPol = src.integPol;
      this.keyObject = src.keyObject;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.security.PairLabel._Static {

      public _Proxy(fabric.lang.security.PairLabel._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.security.PairLabel._Static $instance;

      static {
        fabric.lang.security.PairLabel._Static._Impl impl =
            (fabric.lang.security.PairLabel._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.security.PairLabel._Static._Impl.class);
        $instance = (fabric.lang.security.PairLabel._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.PairLabel._Static {

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
        return new fabric.lang.security.PairLabel._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
