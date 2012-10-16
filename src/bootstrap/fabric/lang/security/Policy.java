package fabric.lang.security;

public interface Policy extends fabric.lang.Object {

  boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.Policy {

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
