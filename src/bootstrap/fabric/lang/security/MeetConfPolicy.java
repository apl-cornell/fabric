package fabric.lang.security;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface MeetConfPolicy extends fabric.lang.security.ConfPolicy,
    fabric.lang.security.MeetPolicy {

  @Override
  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  @Override
  public fabric.lang.security.ConfPolicy join(
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  @Override
  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  @Override
  public fabric.lang.security.ConfPolicy meet(
      fabric.lang.security.ConfPolicy p, java.util.Set s);

  @Override
  public fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  @Override
  public fabric.lang.security.ConfPolicy join(fabric.lang.security.ConfPolicy p);

  @Override
  public fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  @Override
  public fabric.lang.security.ConfPolicy meet(fabric.lang.security.ConfPolicy p);

  public static class _Proxy extends fabric.lang.security.MeetPolicy._Proxy
      implements fabric.lang.security.MeetConfPolicy {

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy arg1);

    public _Proxy(MeetConfPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends fabric.lang.security.MeetPolicy._Impl
      implements fabric.lang.security.MeetConfPolicy {

    _Impl(fabric.worker.Store $location, fabric.util.Set policies) {
      super($location, policies);
    }

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy p, java.util.Set s);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
        java.util.Set s);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy p, java.util.Set s);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy p);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy p);

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
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.MeetConfPolicy._Static {

      public _Proxy(fabric.lang.security.MeetConfPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.MeetConfPolicy._Static {

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
