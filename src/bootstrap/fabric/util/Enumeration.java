package fabric.util;

public interface Enumeration extends fabric.lang.Object {

  boolean hasMoreElements();

  fabric.lang.Object nextElement();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Enumeration {

    @Override
    native public boolean hasMoreElements();

    @Override
    native public fabric.lang.Object nextElement();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
