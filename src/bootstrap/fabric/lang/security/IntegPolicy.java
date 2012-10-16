package fabric.lang.security;

public interface IntegPolicy extends fabric.lang.security.Policy,
    fabric.lang.Object {

  fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set dependencies);

  fabric.lang.security.IntegPolicy join(fabric.lang.security.IntegPolicy p,
      java.util.Set dependencies);

  fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p, java.util.Set dependencies);

  fabric.lang.security.IntegPolicy meet(fabric.lang.security.IntegPolicy p,
      java.util.Set dependencies);

  fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p);

  fabric.lang.security.IntegPolicy join(fabric.lang.security.IntegPolicy p);

  fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
      fabric.lang.security.IntegPolicy p);

  fabric.lang.security.IntegPolicy meet(fabric.lang.security.IntegPolicy p);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.IntegPolicy {

    @Override
    native public fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.IntegPolicy meet(
        fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);

    @Override
    native public fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy arg1);

    @Override
    native public fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);

    @Override
    native public fabric.lang.security.IntegPolicy meet(
        fabric.lang.security.IntegPolicy arg1);

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
