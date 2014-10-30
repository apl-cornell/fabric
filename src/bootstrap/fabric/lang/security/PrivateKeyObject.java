package fabric.lang.security;

public interface PrivateKeyObject extends fabric.lang.Object {

  public fabric.lang.security.Principal get$principal();

  public fabric.lang.security.Principal set$principal(
      fabric.lang.security.Principal val);

  public java.security.PrivateKey get$key();

  public java.security.PrivateKey set$key(java.security.PrivateKey val);

  public fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
      fabric.lang.security.Principal p, java.security.PrivateKey key);

  @Override
  public fabric.lang.Object $initLabels();

  public java.security.PrivateKey getKey();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.PrivateKeyObject {

    @Override
    native public fabric.lang.security.Principal get$principal();

    @Override
    native public fabric.lang.security.Principal set$principal(
        fabric.lang.security.Principal val);

    @Override
    native public java.security.PrivateKey get$key();

    @Override
    native public java.security.PrivateKey set$key(java.security.PrivateKey val);

    @Override
    native public fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
        fabric.lang.security.Principal arg1, java.security.PrivateKey arg2);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public java.security.PrivateKey getKey();

    public _Proxy(PrivateKeyObject._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.PrivateKeyObject {

    @Override
    native public fabric.lang.security.Principal get$principal();

    @Override
    native public fabric.lang.security.Principal set$principal(
        fabric.lang.security.Principal val);

    @Override
    native public java.security.PrivateKey get$key();

    @Override
    native public java.security.PrivateKey set$key(java.security.PrivateKey val);

    @Override
    native public fabric.lang.security.PrivateKeyObject fabric$lang$security$PrivateKeyObject$(
        fabric.lang.security.Principal p, java.security.PrivateKey key);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public java.security.PrivateKey getKey();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        long expiry, long label, long accessLabel, java.io.ObjectInput in,
        java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
	java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.PrivateKeyObject._Static {

      public _Proxy(fabric.lang.security.PrivateKeyObject._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.PrivateKeyObject._Static {

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
