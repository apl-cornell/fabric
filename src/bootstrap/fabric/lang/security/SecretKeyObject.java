package fabric.lang.security;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface SecretKeyObject extends fabric.lang.Object {

  public javax.crypto.SecretKey get$key();

  public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);

  public javax.crypto.SecretKey getKey();

  public javax.crypto.SecretKey rekey();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.SecretKeyObject {

    @Override
    native public javax.crypto.SecretKey get$key();

    @Override
    native public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);

    @Override
    native public javax.crypto.SecretKey getKey();

    @Override
    native public javax.crypto.SecretKey rekey();

    public _Proxy(SecretKeyObject._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.SecretKeyObject {

    @Override
    native public javax.crypto.SecretKey get$key();

    @Override
    native public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);

    @Override
    native public javax.crypto.SecretKey getKey();

    @Override
    native public javax.crypto.SecretKey rekey();

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
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.SecretKeyObject._Static {

      public _Proxy(fabric.lang.security.SecretKeyObject._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.SecretKeyObject._Static {

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
