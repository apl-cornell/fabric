package fabric.lang.security;

import fabric.common.VersionWarranty;

public interface JoinPolicy extends fabric.lang.security.Policy,
    fabric.lang.security.AbstractPolicy {

  public fabric.util.Set get$components();

  public fabric.util.Set set$components(fabric.util.Set val);

  public fabric.worker.Store get$localStore();

  public fabric.util.Set joinComponents();

  @Override
  public boolean relabelsTo(fabric.lang.security.Policy pol, java.util.Set s);

  @Override
  public boolean equals(fabric.lang.Object o);

  @Override
  public int hashCode();

  @Override
  public java.lang.String toString();

  public static class _Proxy extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.JoinPolicy {

    @Override
    native public fabric.util.Set get$components();

    @Override
    native public fabric.util.Set set$components(fabric.util.Set val);

    @Override
    native public fabric.worker.Store get$localStore();

    @Override
    native public fabric.util.Set joinComponents();

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    @Override
    final native public java.lang.String toString();

    public _Proxy(JoinPolicy._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends
      fabric.lang.security.AbstractPolicy._Impl implements
      fabric.lang.security.JoinPolicy {

    @Override
    native public fabric.util.Set get$components();

    @Override
    native public fabric.util.Set set$components(fabric.util.Set val);

    @Override
    native public fabric.worker.Store get$localStore();

    _Impl(fabric.worker.Store $location, fabric.util.Set policies) {
      super($location);
    }

    @Override
    native public fabric.util.Set joinComponents();

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy pol,
        java.util.Set s);

    @Override
    native public boolean equals(fabric.lang.Object o);

    @Override
    final native public int hashCode();

    @Override
    final native public java.lang.String toString();

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
        fabric.lang.security.JoinPolicy._Static {

      public _Proxy(fabric.lang.security.JoinPolicy._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.JoinPolicy._Static {

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
