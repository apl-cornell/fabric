package fabric.util;

public interface Comparator extends fabric.lang.Object {

  int compare(fabric.lang.Object o1, fabric.lang.Object o2);

  @Override
  boolean equals(fabric.lang.Object obj);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Comparator {

    @Override
    native public int compare(fabric.lang.Object arg1, fabric.lang.Object arg2);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
