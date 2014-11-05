package fabric.lang.security;

import fabric.common.VersionWarranty;

public interface PairLabel extends fabric.lang.security.Label,
    fabric.lang.Object {

  public fabric.lang.security.ConfPolicy get$confPol();

  public fabric.lang.security.IntegPolicy get$integPol();

  public fabric.lang.security.SecretKeyObject get$keyObject();

  @Override
  public boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);

  @Override
  public int hashCode();

  @Override
  public boolean equals(fabric.lang.Object o);

  @Override
  public java.lang.String toString();

  @Override
  public fabric.lang.security.Label join(fabric.worker.Store store,
      fabric.lang.security.Label l);

  public fabric.lang.security.Label join(fabric.lang.security.Label l);

  @Override
  public fabric.lang.security.Label meet(fabric.worker.Store store,
      fabric.lang.security.Label l);

  public fabric.lang.security.Label meet(fabric.lang.security.Label l);

  @Override
  public fabric.lang.security.ConfPolicy confPolicy();

  @Override
  public fabric.lang.security.IntegPolicy integPolicy();

  @Override
  public fabric.lang.security.SecretKeyObject keyObject();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.PairLabel {

    @Override
    native public fabric.lang.security.ConfPolicy get$confPol();

    @Override
    native public fabric.lang.security.IntegPolicy get$integPol();

    @Override
    native public fabric.lang.security.SecretKeyObject get$keyObject();

    @Override
    native public boolean relabelsTo(fabric.lang.security.Label arg1,
        java.util.Set arg2);

    @Override
    native public int hashCode();

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    final native public java.lang.String toString();

    @Override
    final native public fabric.lang.security.Label join(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2);

    @Override
    final native public fabric.lang.security.Label join(
        fabric.lang.security.Label arg1);

    @Override
    native public fabric.lang.security.Label meet(fabric.worker.Store arg1,
        fabric.lang.security.Label arg2);

    @Override
    native public fabric.lang.security.Label meet(
        fabric.lang.security.Label arg1);

    @Override
    native public fabric.lang.security.ConfPolicy confPolicy();

    @Override
    native public fabric.lang.security.IntegPolicy integPolicy();

    @Override
    native public fabric.lang.security.SecretKeyObject keyObject();

    public _Proxy(PairLabel._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.PairLabel {

    @Override
    native public fabric.lang.security.ConfPolicy get$confPol();

    @Override
    native public fabric.lang.security.IntegPolicy get$integPol();

    @Override
    native public fabric.lang.security.SecretKeyObject get$keyObject();

    public _Impl(fabric.worker.Store $location,
        fabric.lang.security.ConfPolicy confPol,
        fabric.lang.security.IntegPolicy integPol) {
      super($location);
    }

    @Override
    native public boolean relabelsTo(fabric.lang.security.Label l,
        java.util.Set s);

    @Override
    native public int hashCode();

    @Override
    native public boolean equals(fabric.lang.Object o);

    @Override
    final native public java.lang.String toString();

    @Override
    final native public fabric.lang.security.Label join(
        fabric.worker.Store store, fabric.lang.security.Label l);

    @Override
    final native public fabric.lang.security.Label join(
        fabric.lang.security.Label l);

    @Override
    native public fabric.lang.security.Label meet(fabric.worker.Store store,
        fabric.lang.security.Label l);

    @Override
    native public fabric.lang.security.Label meet(fabric.lang.security.Label l);

    @Override
    native public fabric.lang.security.ConfPolicy confPolicy();

    @Override
    native public fabric.lang.security.IntegPolicy integPolicy();

    @Override
    native public fabric.lang.security.SecretKeyObject keyObject();

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.PairLabel._Static {

      public _Proxy(fabric.lang.security.PairLabel._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.PairLabel._Static {

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
