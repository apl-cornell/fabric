package fabric.util;

import fabric.common.VersionWarranty;

public interface ArrayList extends fabric.util.List, fabric.util.RandomAccess,
    fabric.util.AbstractList {

  public int get$size();

  public int set$size(int val);

  public int postInc$size();

  public int postDec$size();

  public fabric.lang.arrays.ObjectArray get$data();

  public fabric.lang.arrays.ObjectArray set$data(
      fabric.lang.arrays.ObjectArray val);

  public fabric.util.ArrayList fabric$util$ArrayList$(int capacity);

  public fabric.util.ArrayList fabric$util$ArrayList$();

  public fabric.util.ArrayList fabric$util$ArrayList$(fabric.util.Collection c);

  public void trimToSize();

  public void ensureCapacity(int minCapacity);

  @Override
  public int size();

  @Override
  public boolean isEmpty();

  @Override
  public boolean contains(fabric.lang.Object e);

  @Override
  public int indexOf(fabric.lang.Object e);

  @Override
  public int lastIndexOf(fabric.lang.Object e);

  @Override
  public fabric.lang.Object get(int index);

  @Override
  public fabric.lang.Object set(int index, fabric.lang.Object e);

  @Override
  public boolean add(fabric.lang.Object e);

  @Override
  public void add(int index, fabric.lang.Object e);

  @Override
  public fabric.lang.Object remove(int index);

  @Override
  public void clear();

  @Override
  public boolean addAll(fabric.util.Collection c);

  @Override
  public boolean addAll(int index, fabric.util.Collection c);

  @Override
  public void removeRange(int fromIndex, int toIndex);

  @Override
  public boolean removeAllInternal(fabric.util.Collection c);

  @Override
  public boolean retainAllInternal(fabric.util.Collection c);

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.util.AbstractList._Proxy implements
      fabric.util.ArrayList {

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.lang.arrays.ObjectArray get$data();

    @Override
    native public fabric.lang.arrays.ObjectArray set$data(
        fabric.lang.arrays.ObjectArray val);

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$(int arg1);

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$();

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$(
        fabric.util.Collection arg1);

    @Override
    native public void trimToSize();

    @Override
    native public void ensureCapacity(int arg1);

    @Override
    native public int size();

    @Override
    native public boolean isEmpty();

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public boolean removeAllInternal(fabric.util.Collection arg1);

    @Override
    native public boolean retainAllInternal(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public boolean removeAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    @Override
    native public fabric.util.Iterator iterator();

    public _Proxy(ArrayList._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractList._Impl implements
      fabric.util.ArrayList {

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.lang.arrays.ObjectArray get$data();

    @Override
    native public fabric.lang.arrays.ObjectArray set$data(
        fabric.lang.arrays.ObjectArray val);

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$(int capacity);

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$();

    @Override
    native public fabric.util.ArrayList fabric$util$ArrayList$(
        fabric.util.Collection c);

    @Override
    native public void trimToSize();

    @Override
    native public void ensureCapacity(int minCapacity);

    @Override
    native public int size();

    @Override
    native public boolean isEmpty();

    @Override
    native public boolean contains(fabric.lang.Object e);

    @Override
    native public int indexOf(fabric.lang.Object e);

    @Override
    native public int lastIndexOf(fabric.lang.Object e);

    @Override
    native public fabric.lang.Object get(int index);

    @Override
    native public fabric.lang.Object set(int index, fabric.lang.Object e);

    @Override
    native public boolean add(fabric.lang.Object e);

    @Override
    native public void add(int index, fabric.lang.Object e);

    @Override
    native public fabric.lang.Object remove(int index);

    @Override
    native public void clear();

    @Override
    native public boolean addAll(fabric.util.Collection c);

    @Override
    native public boolean addAll(int index, fabric.util.Collection c);

    @Override
    native public void removeRange(int fromIndex, int toIndex);

    native private void checkBoundInclusive(int index);

    native private void checkBoundExclusive(int index);

    @Override
    native public boolean removeAllInternal(fabric.util.Collection c);

    @Override
    native public boolean retainAllInternal(fabric.util.Collection c);

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

    public long get$serialVersionUID();

    public int get$DEFAULT_CAPACITY();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.ArrayList._Static {

      @Override
      native public long get$serialVersionUID();

      @Override
      native public int get$DEFAULT_CAPACITY();

      public _Proxy(fabric.util.ArrayList._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.ArrayList._Static {

      @Override
      native public long get$serialVersionUID();

      @Override
      native public int get$DEFAULT_CAPACITY();

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
