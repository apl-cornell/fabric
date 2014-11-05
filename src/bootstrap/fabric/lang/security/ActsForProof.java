package fabric.lang.security;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface ActsForProof extends fabric.lang.Object {

  public fabric.lang.security.Principal get$actor();

  public fabric.lang.security.Principal set$actor(
      fabric.lang.security.Principal val);

  public fabric.lang.security.Principal get$granter();

  public fabric.lang.security.Principal set$granter(
      fabric.lang.security.Principal val);

  public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);

  public fabric.lang.security.Principal getActor();

  public fabric.lang.security.Principal getGranter();

  abstract public void gatherDelegationDependencies(java.util.Set s);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.ActsForProof {

    @Override
    native public fabric.lang.security.Principal get$actor();

    @Override
    native public fabric.lang.security.Principal set$actor(
        fabric.lang.security.Principal val);

    @Override
    native public fabric.lang.security.Principal get$granter();

    @Override
    native public fabric.lang.security.Principal set$granter(
        fabric.lang.security.Principal val);

    @Override
    native public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    @Override
    native public fabric.lang.security.Principal getActor();

    @Override
    native public fabric.lang.security.Principal getGranter();

    @Override
    native public void gatherDelegationDependencies(java.util.Set arg1);

    public _Proxy(ActsForProof._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.ActsForProof {

    @Override
    native public fabric.lang.security.Principal get$actor();

    @Override
    native public fabric.lang.security.Principal set$actor(
        fabric.lang.security.Principal val);

    @Override
    native public fabric.lang.security.Principal get$granter();

    @Override
    native public fabric.lang.security.Principal set$granter(
        fabric.lang.security.Principal val);

    @Override
    native public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
        fabric.lang.security.Principal actor,
        fabric.lang.security.Principal granter);

    @Override
    native public fabric.lang.security.Principal getActor();

    @Override
    native public fabric.lang.security.Principal getGranter();

    @Override
    abstract public void gatherDelegationDependencies(java.util.Set s);

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
        fabric.lang.security.ActsForProof._Static {

      public _Proxy(fabric.lang.security.ActsForProof._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.ActsForProof._Static {

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
