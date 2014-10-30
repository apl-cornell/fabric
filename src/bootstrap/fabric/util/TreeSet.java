package fabric.util;

public interface TreeSet extends fabric.util.SortedSet, fabric.util.AbstractSet {

  public fabric.util.SortedMap get$map();

  public fabric.util.SortedMap set$map(fabric.util.SortedMap val);

  public fabric.util.TreeSet fabric$util$TreeSet$();

  public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Comparator comparator);

  public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Collection collection);

  public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.SortedSet sortedSet);

  @Override
  public boolean add(fabric.lang.Object obj);

  @Override
  public boolean addAll(fabric.util.Collection c);

  @Override
  public void clear();

  @Override
  public fabric.util.Comparator comparator();

  @Override
  public boolean contains(fabric.lang.Object obj);

  @Override
  public fabric.lang.Object first();

  @Override
  public fabric.util.SortedSet headSet(fabric.lang.Object to);

  @Override
  public boolean isEmpty();

  @Override
  public fabric.util.Iterator iterator(fabric.worker.Store store);

  @Override
  public fabric.lang.Object last();

  @Override
  public boolean remove(fabric.lang.Object obj);

  @Override
  public int size();

  @Override
  public fabric.util.SortedSet subSet(fabric.lang.Object from,
      fabric.lang.Object to);

  @Override
  public fabric.util.SortedSet tailSet(fabric.lang.Object from);

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
      fabric.util.TreeSet {

    @Override
    native public fabric.util.SortedMap get$map();

    @Override
    native public fabric.util.SortedMap set$map(fabric.util.SortedMap val);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$();

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.Comparator arg1);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.Collection arg1);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.SortedSet arg1);

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public void clear();

    @Override
    native public fabric.util.Comparator comparator();

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object first();

    @Override
    native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public fabric.lang.Object last();

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public int size();

    @Override
    native public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    public _Proxy(TreeSet._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractSet._Impl implements
      fabric.util.TreeSet {

    @Override
    native public fabric.util.SortedMap get$map();

    @Override
    native public fabric.util.SortedMap set$map(fabric.util.SortedMap val);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$();

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.Comparator comparator);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.Collection collection);

    @Override
    native public fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.SortedSet sortedSet);

    native private fabric.util.TreeSet fabric$util$TreeSet$(
        fabric.util.SortedMap backingMap);

    @Override
    native public boolean add(fabric.lang.Object obj);

    @Override
    native public boolean addAll(fabric.util.Collection c);

    @Override
    native public void clear();

    @Override
    native public fabric.util.Comparator comparator();

    @Override
    native public boolean contains(fabric.lang.Object obj);

    @Override
    native public fabric.lang.Object first();

    @Override
    native public fabric.util.SortedSet headSet(fabric.lang.Object to);

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    native public fabric.lang.Object last();

    @Override
    native public boolean remove(fabric.lang.Object obj);

    @Override
    native public int size();

    @Override
    native public fabric.util.SortedSet subSet(fabric.lang.Object from,
        fabric.lang.Object to);

    @Override
    native public fabric.util.SortedSet tailSet(fabric.lang.Object from);

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
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.TreeSet._Static {

      public _Proxy(fabric.util.TreeSet._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.TreeSet._Static {

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
