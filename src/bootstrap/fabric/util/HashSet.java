package fabric.util;

public interface HashSet extends fabric.util.Set, fabric.util.AbstractSet {

  public fabric.util.HashMap get$map();

  public fabric.util.HashMap set$map(fabric.util.HashMap val);

  public fabric.util.HashSet fabric$util$HashSet$();

  public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);

  public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity,
      float loadFactor);

  public fabric.util.HashSet fabric$util$HashSet$(fabric.util.Collection c);

  @Override
  public boolean add(fabric.lang.Object o);

  @Override
  public void clear();

  @Override
  public boolean contains(fabric.lang.Object o);

  @Override
  public boolean isEmpty();

  @Override
  public fabric.util.Iterator iterator(fabric.worker.Store store);

  @Override
  public boolean remove(fabric.lang.Object o);

  @Override
  public int size();

  public fabric.util.HashMap init(int capacity, float load);

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
      fabric.util.HashSet {

    @Override
    native public fabric.util.HashMap get$map();

    @Override
    native public fabric.util.HashMap set$map(fabric.util.HashMap val);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$();

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(int arg1);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(int arg1, float arg2);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(
        fabric.util.Collection arg1);

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public void clear();

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public int size();

    @Override
    native public fabric.util.HashMap init(int arg1, float arg2);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    public _Proxy(HashSet._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractSet._Impl implements
      fabric.util.HashSet {

    @Override
    native public fabric.util.HashMap get$map();

    @Override
    native public fabric.util.HashMap set$map(fabric.util.HashMap val);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$();

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity,
        float loadFactor);

    @Override
    native public fabric.util.HashSet fabric$util$HashSet$(
        fabric.util.Collection c);

    @Override
    native public boolean add(fabric.lang.Object o);

    @Override
    native public void clear();

    @Override
    native public boolean contains(fabric.lang.Object o);

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    native public boolean remove(fabric.lang.Object o);

    @Override
    native public int size();

    @Override
    native public fabric.util.HashMap init(int capacity, float load);

    @Override
    native public fabric.lang.Object $initLabels();

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

    public long get$serialVersionUID();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.HashSet._Static {

      @Override
      native public long get$serialVersionUID();

      public _Proxy(fabric.util.HashSet._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.HashSet._Static {

      @Override
      native public long get$serialVersionUID();

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
