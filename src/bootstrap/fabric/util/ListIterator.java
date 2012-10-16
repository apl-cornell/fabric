package fabric.util;

public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {

  @Override
  boolean hasNext();

  boolean hasPrevious();

  @Override
  fabric.lang.Object next();

  fabric.lang.Object previous();

  int nextIndex();

  int previousIndex();

  void add(fabric.lang.Object o);

  @Override
  void remove();

  void set(fabric.lang.Object o);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.ListIterator {

    @Override
    native public boolean hasNext();

    @Override
    native public boolean hasPrevious();

    @Override
    native public fabric.lang.Object next();

    @Override
    native public fabric.lang.Object previous();

    @Override
    native public int nextIndex();

    @Override
    native public int previousIndex();

    @Override
    native public void add(fabric.lang.Object arg1);

    @Override
    native public void remove();

    @Override
    native public void set(fabric.lang.Object arg1);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
