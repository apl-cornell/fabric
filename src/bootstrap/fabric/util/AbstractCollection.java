package fabric.util;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface AbstractCollection extends fabric.util.Collection,
    fabric.lang.Object {

  public fabric.util.AbstractCollection fabric$util$AbstractCollection$();

  @Override
  abstract public fabric.util.Iterator iterator(fabric.worker.Store store);

  @Override
  public fabric.util.Iterator iterator();

  @Override
  abstract public int size();

  @Override
  public boolean add(fabric.lang.Object o);

  @Override
  public boolean addAll(fabric.util.Collection c);

  @Override
  public void clear();

  @Override
  public boolean contains(fabric.lang.Object o);

  @Override
  public boolean containsAll(fabric.util.Collection c);

  @Override
  public boolean isEmpty();

  @Override
  public boolean remove(fabric.lang.Object o);

  @Override
  public boolean removeAll(fabric.util.Collection c);

  public boolean removeAllInternal(fabric.util.Collection c);

  @Override
  public boolean retainAll(fabric.util.Collection c);

  public boolean retainAllInternal(fabric.util.Collection c);

  @Override
  public fabric.lang.arrays.ObjectArray toArray();

  @Override
  public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);

  @Override
  public java.lang.String toString();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.AbstractCollection {

    @Override
    native public fabric.util.AbstractCollection fabric$util$AbstractCollection$();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public fabric.util.Iterator iterator();

    @Override
    native public int size();

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public void clear();

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean isEmpty();

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public boolean removeAll(fabric.util.Collection arg1);

    @Override
    native public boolean removeAllInternal(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAllInternal(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    @Override
    native public java.lang.String toString();

    final native public static boolean equals(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    final native public static int hashCode(fabric.lang.Object arg1);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public int hashCode();

    public _Proxy(AbstractCollection._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.util.AbstractCollection {

    @Override
    native public fabric.util.AbstractCollection fabric$util$AbstractCollection$();

    @Override
    abstract public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    native public fabric.util.Iterator iterator();

    @Override
    abstract public int size();

    @Override
    native public boolean add(fabric.lang.Object o);

    @Override
    native public boolean addAll(fabric.util.Collection c);

    @Override
    native public void clear();

    @Override
    native public boolean contains(fabric.lang.Object o);

    @Override
    native public boolean containsAll(fabric.util.Collection c);

    @Override
    native public boolean isEmpty();

    @Override
    native public boolean remove(fabric.lang.Object o);

    @Override
    native public boolean removeAll(fabric.util.Collection c);

    @Override
    native public boolean removeAllInternal(fabric.util.Collection c);

    @Override
    native public boolean retainAll(fabric.util.Collection c);

    @Override
    native public boolean retainAllInternal(fabric.util.Collection c);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray a);

    @Override
    native public java.lang.String toString();

    final native public static boolean equals(fabric.lang.Object o1,
        fabric.lang.Object o2);

    final native public static int hashCode(fabric.lang.Object o);

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
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public fabric.worker.Store get$LOCAL_STORE();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractCollection._Static {

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      public _Proxy(fabric.util.AbstractCollection._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractCollection._Static {

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

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
