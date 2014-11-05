package fabric.util;

import fabric.common.VersionWarranty;

public interface Collections extends fabric.lang.Object {
  public static interface EmptySet extends java.io.Serializable,
      fabric.util.AbstractSet {

    public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$();

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean containsAll(fabric.util.Collection c);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public int hashCode();

    @Override
    public boolean remove(fabric.lang.Object o);

    @Override
    public boolean removeAll(fabric.util.Collection c);

    @Override
    public boolean retainAll(fabric.util.Collection c);

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray a);

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
        fabric.util.Collections.EmptySet {

      @Override
      native public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public boolean containsAll(fabric.util.Collection arg1);

      @Override
      native public boolean remove(fabric.lang.Object arg1);

      @Override
      native public boolean retainAll(fabric.util.Collection arg1);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(EmptySet._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractSet._Impl
        implements fabric.util.Collections.EmptySet {

      @Override
      native public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean containsAll(fabric.util.Collection c);

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public int hashCode();

      @Override
      native public boolean remove(fabric.lang.Object o);

      @Override
      native public boolean removeAll(fabric.util.Collection c);

      @Override
      native public boolean retainAll(fabric.util.Collection c);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);

      @Override
      native public java.lang.String toString();

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.EmptySet._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.EmptySet._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.EmptySet._Static {

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

  public static interface EmptyList extends java.io.Serializable,
      fabric.util.RandomAccess, fabric.util.AbstractList {

    public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$();

    @Override
    public int size();

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean containsAll(fabric.util.Collection c);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public int hashCode();

    @Override
    public int indexOf(fabric.lang.Object o);

    @Override
    public int lastIndexOf(fabric.lang.Object o);

    @Override
    public boolean remove(fabric.lang.Object o);

    @Override
    public boolean removeAll(fabric.util.Collection c);

    @Override
    public boolean retainAll(fabric.util.Collection c);

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray a);

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList._Proxy
        implements fabric.util.Collections.EmptyList {

      @Override
      native public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$();

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

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
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(EmptyList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractList._Impl
        implements fabric.util.Collections.EmptyList {

      @Override
      native public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$();

      @Override
      native public int size();

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean containsAll(fabric.util.Collection c);

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public int hashCode();

      @Override
      native public int indexOf(fabric.lang.Object o);

      @Override
      native public int lastIndexOf(fabric.lang.Object o);

      @Override
      native public boolean remove(fabric.lang.Object o);

      @Override
      native public boolean removeAll(fabric.util.Collection c);

      @Override
      native public boolean retainAll(fabric.util.Collection c);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);

      @Override
      native public java.lang.String toString();

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.EmptyList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.EmptyList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.EmptyList._Static {

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

  public static interface EmptyMap extends java.io.Serializable,
      fabric.util.AbstractMap {

    public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$();

    @Override
    public fabric.util.Set entrySet();

    @Override
    public boolean containsKey(fabric.lang.Object key);

    @Override
    public boolean containsValue(fabric.lang.Object value);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public fabric.lang.Object get(fabric.lang.Object o);

    @Override
    public int hashCode();

    @Override
    public fabric.util.Set keySet();

    @Override
    public fabric.lang.Object remove(fabric.lang.Object o);

    @Override
    public int size();

    @Override
    public fabric.util.Collection values();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractMap._Proxy implements
        fabric.util.Collections.EmptyMap {

      @Override
      native public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(EmptyMap._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractMap._Impl
        implements fabric.util.Collections.EmptyMap {

      @Override
      native public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$();

      @Override
      native public fabric.util.Set entrySet();

      @Override
      native public boolean containsKey(fabric.lang.Object key);

      @Override
      native public boolean containsValue(fabric.lang.Object value);

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object get(fabric.lang.Object o);

      @Override
      native public int hashCode();

      @Override
      native public fabric.util.Set keySet();

      @Override
      native public fabric.lang.Object remove(fabric.lang.Object o);

      @Override
      native public int size();

      @Override
      native public fabric.util.Collection values();

      @Override
      native public java.lang.String toString();

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.EmptyMap._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.EmptyMap._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.EmptyMap._Static {

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

  public static interface CopiesList extends java.io.Serializable,
      fabric.util.RandomAccess, fabric.util.AbstractList {

    public int get$n();

    public int set$n(int val);

    public int postInc$n();

    public int postDec$n();

    public fabric.lang.Object get$element();

    public fabric.lang.Object set$element(fabric.lang.Object val);

    public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
        int n, fabric.lang.Object o);

    @Override
    public int size();

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public int indexOf(fabric.lang.Object o);

    @Override
    public int lastIndexOf(fabric.lang.Object o);

    @Override
    public fabric.util.List subList(int from, int to);

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList._Proxy
        implements fabric.util.Collections.CopiesList {

      @Override
      native public int get$n();

      @Override
      native public int set$n(int val);

      @Override
      native public int postInc$n();

      @Override
      native public int postDec$n();

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
          int arg1, fabric.lang.Object arg2);

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(CopiesList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractList._Impl
        implements fabric.util.Collections.CopiesList {

      @Override
      native public int get$n();

      @Override
      native public int set$n(int val);

      @Override
      native public int postInc$n();

      @Override
      native public int postDec$n();

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
          int n, fabric.lang.Object o);

      @Override
      native public int size();

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public int indexOf(fabric.lang.Object o);

      @Override
      native public int lastIndexOf(fabric.lang.Object o);

      @Override
      native public fabric.util.List subList(int from, int to);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.CopiesList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.CopiesList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.CopiesList._Static {

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

  public static interface ReverseComparator extends fabric.util.Comparator,
      java.io.Serializable, fabric.lang.Object {

    public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$();

    @Override
    public int compare(fabric.lang.Object a, fabric.lang.Object b);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Collections.ReverseComparator {

      @Override
      native public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$();

      @Override
      native public int compare(fabric.lang.Object arg1, fabric.lang.Object arg2);

      @Override
      native public fabric.lang.Object $initLabels();

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      public _Proxy(ReverseComparator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Collections.ReverseComparator {

      @Override
      native public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$();

      @Override
      native public int compare(fabric.lang.Object a, fabric.lang.Object b);

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.ReverseComparator._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.ReverseComparator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.ReverseComparator._Static {

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

  public static interface SingletonSet extends java.io.Serializable,
      fabric.util.AbstractSet {

    public fabric.lang.Object get$element();

    public fabric.lang.Object set$element(fabric.lang.Object val);

    public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
        fabric.lang.Object o);

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean containsAll(fabric.util.Collection c);

    @Override
    public int hashCode();

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static interface Anonymous$2 extends fabric.util.Iterator,
        fabric.lang.Object {

      public fabric.util.Collections.SingletonSet get$out$();

      public boolean get$hasNext();

      public boolean set$hasNext(boolean val);

      @Override
      public boolean hasNext();

      @Override
      public fabric.lang.Object next();

      @Override
      public void remove();

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.SingletonSet.Anonymous$2 {

        @Override
        native public fabric.util.Collections.SingletonSet get$out$();

        @Override
        native public boolean get$hasNext();

        @Override
        native public boolean set$hasNext(boolean val);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(Anonymous$2._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.SingletonSet.Anonymous$2 {

        @Override
        native public fabric.util.Collections.SingletonSet get$out$();

        @Override
        native public boolean get$hasNext();

        @Override
        native public boolean set$hasNext(boolean val);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        private _Impl(fabric.worker.Store $location,
            final fabric.util.Collections.SingletonSet out$) {
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
          super(store, onum, version, warranty, label, accessLabel, in,
              refTypes, intraStoreRefs, interStoreRefs);
        }

        @Override
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
      }

      interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy implements
            fabric.util.Collections.SingletonSet.Anonymous$2._Static {

          public _Proxy(
              fabric.util.Collections.SingletonSet.Anonymous$2._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.Collections.SingletonSet.Anonymous$2._Static {

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

    public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
        fabric.util.Collections.SingletonSet {

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
          fabric.lang.Object arg1);

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public boolean containsAll(fabric.util.Collection arg1);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(SingletonSet._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractSet._Impl
        implements fabric.util.Collections.SingletonSet {

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
          fabric.lang.Object o);

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean containsAll(fabric.util.Collection c);

      @Override
      native public int hashCode();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.SingletonSet._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.SingletonSet._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.SingletonSet._Static {

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

  public static interface SingletonList extends java.io.Serializable,
      fabric.util.RandomAccess, fabric.util.AbstractList {

    public fabric.lang.Object get$element();

    public fabric.lang.Object set$element(fabric.lang.Object val);

    public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
        fabric.lang.Object o);

    @Override
    public int size();

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean containsAll(fabric.util.Collection c);

    @Override
    public int hashCode();

    @Override
    public int indexOf(fabric.lang.Object o);

    @Override
    public int lastIndexOf(fabric.lang.Object o);

    @Override
    public fabric.util.List subList(int from, int to);

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList._Proxy
        implements fabric.util.Collections.SingletonList {

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
          fabric.lang.Object arg1);

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public boolean containsAll(fabric.util.Collection arg1);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(SingletonList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractList._Impl
        implements fabric.util.Collections.SingletonList {

      @Override
      native public fabric.lang.Object get$element();

      @Override
      native public fabric.lang.Object set$element(fabric.lang.Object val);

      @Override
      native public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
          fabric.lang.Object o);

      @Override
      native public int size();

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean containsAll(fabric.util.Collection c);

      @Override
      native public int hashCode();

      @Override
      native public int indexOf(fabric.lang.Object o);

      @Override
      native public int lastIndexOf(fabric.lang.Object o);

      @Override
      native public fabric.util.List subList(int from, int to);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public java.lang.String toString();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.SingletonList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.SingletonList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.SingletonList._Static {

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

  public static interface SingletonMap extends java.io.Serializable,
      fabric.util.AbstractMap {

    public fabric.lang.Object get$k();

    public fabric.lang.Object set$k(fabric.lang.Object val);

    public fabric.lang.Object get$v();

    public fabric.lang.Object set$v(fabric.lang.Object val);

    public fabric.util.Set get$entries();

    public fabric.util.Set set$entries(fabric.util.Set val);

    public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
        fabric.lang.Object key, fabric.lang.Object value);

    @Override
    public fabric.util.Set entrySet();

    @Override
    public boolean containsKey(fabric.lang.Object key);

    @Override
    public boolean containsValue(fabric.lang.Object value);

    @Override
    public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    public int hashCode();

    @Override
    public fabric.util.Set keySet();

    @Override
    public int size();

    @Override
    public fabric.util.Collection values();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static interface Anonymous$3 extends
        fabric.util.AbstractMap.BasicMapEntry {

      public fabric.util.Collections.SingletonMap get$out$();

      @Override
      public fabric.lang.Object setValue(fabric.lang.Object o);

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends
          fabric.util.AbstractMap.BasicMapEntry._Proxy implements
          fabric.util.Collections.SingletonMap.Anonymous$3 {

        @Override
        native public fabric.util.Collections.SingletonMap get$out$();

        public _Proxy(Anonymous$3._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends
          fabric.util.AbstractMap.BasicMapEntry._Impl implements
          fabric.util.Collections.SingletonMap.Anonymous$3 {

        @Override
        native public fabric.util.Collections.SingletonMap get$out$();

        @Override
        native public fabric.lang.Object setValue(fabric.lang.Object o);

        @Override
        native public fabric.lang.Object $initLabels();

        private _Impl(fabric.worker.Store $location,
            final fabric.util.Collections.SingletonMap out$) {
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
          super(store, onum, version, warranty, label, accessLabel, in,
              refTypes, intraStoreRefs, interStoreRefs);
        }

        @Override
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
      }

      interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy implements
            fabric.util.Collections.SingletonMap.Anonymous$3._Static {

          public _Proxy(
              fabric.util.Collections.SingletonMap.Anonymous$3._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.Collections.SingletonMap.Anonymous$3._Static {

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

    public static class _Proxy extends fabric.util.AbstractMap._Proxy implements
        fabric.util.Collections.SingletonMap {

      @Override
      native public fabric.lang.Object get$k();

      @Override
      native public fabric.lang.Object set$k(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$v();

      @Override
      native public fabric.lang.Object set$v(fabric.lang.Object val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
          fabric.lang.Object arg1, fabric.lang.Object arg2);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(SingletonMap._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractMap._Impl
        implements fabric.util.Collections.SingletonMap {

      @Override
      native public fabric.lang.Object get$k();

      @Override
      native public fabric.lang.Object set$k(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$v();

      @Override
      native public fabric.lang.Object set$v(fabric.lang.Object val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
          fabric.lang.Object key, fabric.lang.Object value);

      @Override
      native public fabric.util.Set entrySet();

      @Override
      native public boolean containsKey(fabric.lang.Object key);

      @Override
      native public boolean containsValue(fabric.lang.Object value);

      @Override
      native public fabric.lang.Object get(fabric.lang.Object key);

      @Override
      native public int hashCode();

      @Override
      native public fabric.util.Set keySet();

      @Override
      native public int size();

      @Override
      native public fabric.util.Collection values();

      @Override
      native public java.lang.String toString();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.SingletonMap._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.SingletonMap._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.SingletonMap._Static {

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

  public static interface UnmodifiableCollection extends
      fabric.util.Collection, java.io.Serializable, fabric.lang.Object {

    public fabric.util.Collection get$c();

    public fabric.util.Collection set$c(fabric.util.Collection val);

    public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
        fabric.util.Collection c);

    @Override
    public boolean add(fabric.lang.Object o);

    @Override
    public boolean addAll(fabric.util.Collection c);

    @Override
    public void clear();

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean containsAll(fabric.util.Collection c1);

    @Override
    public boolean isEmpty();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public fabric.util.Iterator iterator();

    @Override
    public boolean remove(fabric.lang.Object o);

    @Override
    public boolean removeAll(fabric.util.Collection c);

    @Override
    public boolean retainAll(fabric.util.Collection c);

    @Override
    public int size();

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray a);

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Collections.UnmodifiableCollection {

      @Override
      native public fabric.util.Collection get$c();

      @Override
      native public fabric.util.Collection set$c(fabric.util.Collection val);

      @Override
      native public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
          fabric.util.Collection arg1);

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
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public fabric.util.Iterator iterator();

      @Override
      native public boolean remove(fabric.lang.Object arg1);

      @Override
      native public boolean removeAll(fabric.util.Collection arg1);

      @Override
      native public boolean retainAll(fabric.util.Collection arg1);

      @Override
      native public int size();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      @Override
      native public int hashCode();

      public _Proxy(UnmodifiableCollection._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Collections.UnmodifiableCollection {

      @Override
      native public fabric.util.Collection get$c();

      @Override
      native public fabric.util.Collection set$c(fabric.util.Collection val);

      @Override
      native public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
          fabric.util.Collection c);

      @Override
      native public boolean add(fabric.lang.Object o);

      @Override
      native public boolean addAll(fabric.util.Collection c);

      @Override
      native public void clear();

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean containsAll(fabric.util.Collection c1);

      @Override
      native public boolean isEmpty();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public fabric.util.Iterator iterator();

      @Override
      native public boolean remove(fabric.lang.Object o);

      @Override
      native public boolean removeAll(fabric.util.Collection c);

      @Override
      native public boolean retainAll(fabric.util.Collection c);

      @Override
      native public int size();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);

      @Override
      native public java.lang.String toString();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableCollection._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.UnmodifiableCollection._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableCollection._Static {

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

  public static interface UnmodifiableIterator extends fabric.util.Iterator,
      fabric.lang.Object {

    public fabric.util.Iterator get$i();

    public fabric.util.Iterator set$i(fabric.util.Iterator val);

    public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
        fabric.util.Iterator i);

    @Override
    public fabric.lang.Object next();

    @Override
    public boolean hasNext();

    @Override
    public void remove();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Collections.UnmodifiableIterator {

      @Override
      native public fabric.util.Iterator get$i();

      @Override
      native public fabric.util.Iterator set$i(fabric.util.Iterator val);

      @Override
      native public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
          fabric.util.Iterator arg1);

      @Override
      native public fabric.lang.Object next();

      @Override
      native public boolean hasNext();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(UnmodifiableIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Collections.UnmodifiableIterator {

      @Override
      native public fabric.util.Iterator get$i();

      @Override
      native public fabric.util.Iterator set$i(fabric.util.Iterator val);

      @Override
      native public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
          fabric.util.Iterator i);

      @Override
      native public fabric.lang.Object next();

      @Override
      native public boolean hasNext();

      @Override
      native public void remove();

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
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableIterator._Static {

        public _Proxy(
            fabric.util.Collections.UnmodifiableIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableIterator._Static {

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

  public static interface UnmodifiableList extends fabric.util.List,
      fabric.util.Collections.UnmodifiableCollection {

    public fabric.util.List get$list();

    public fabric.util.List set$list(fabric.util.List val);

    public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
        fabric.util.List l);

    @Override
    public void add(int index, fabric.lang.Object o);

    @Override
    public boolean addAll(int index, fabric.util.Collection c);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public int hashCode();

    @Override
    public int indexOf(fabric.lang.Object o);

    @Override
    public int lastIndexOf(fabric.lang.Object o);

    @Override
    public fabric.util.ListIterator listIterator(fabric.worker.Store store);

    @Override
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
        int index);

    @Override
    public fabric.lang.Object remove(int index);

    @Override
    public fabric.lang.Object set(int index, fabric.lang.Object o);

    @Override
    public fabric.util.List subList(int fromIndex, int toIndex);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableCollection._Proxy implements
        fabric.util.Collections.UnmodifiableList {

      @Override
      native public fabric.util.List get$list();

      @Override
      native public fabric.util.List set$list(fabric.util.List val);

      @Override
      native public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
          fabric.util.List arg1);

      @Override
      native public void add(int arg1, fabric.lang.Object arg2);

      @Override
      native public boolean addAll(int arg1, fabric.util.Collection arg2);

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object get(int arg1);

      @Override
      native public int hashCode();

      @Override
      native public int indexOf(fabric.lang.Object arg1);

      @Override
      native public int lastIndexOf(fabric.lang.Object arg1);

      @Override
      native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1);

      @Override
      native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1, int arg2);

      @Override
      native public fabric.lang.Object remove(int arg1);

      @Override
      native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);

      @Override
      native public fabric.util.List subList(int arg1, int arg2);

      public _Proxy(UnmodifiableList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends
        fabric.util.Collections.UnmodifiableCollection._Impl implements
        fabric.util.Collections.UnmodifiableList {

      @Override
      native public fabric.util.List get$list();

      @Override
      native public fabric.util.List set$list(fabric.util.List val);

      @Override
      native public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
          fabric.util.List l);

      @Override
      native public void add(int index, fabric.lang.Object o);

      @Override
      native public boolean addAll(int index, fabric.util.Collection c);

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public int hashCode();

      @Override
      native public int indexOf(fabric.lang.Object o);

      @Override
      native public int lastIndexOf(fabric.lang.Object o);

      @Override
      native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store);

      @Override
      native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);

      @Override
      native public fabric.lang.Object remove(int index);

      @Override
      native public fabric.lang.Object set(int index, fabric.lang.Object o);

      @Override
      native public fabric.util.List subList(int fromIndex, int toIndex);

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.UnmodifiableList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableList._Static {

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

  public static interface UnmodifiableRandomAccessList extends
      fabric.util.RandomAccess, fabric.util.Collections.UnmodifiableList {

    public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
        fabric.util.List l);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableList._Proxy implements
        fabric.util.Collections.UnmodifiableRandomAccessList {

      @Override
      native public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
          fabric.util.List arg1);

      public _Proxy(UnmodifiableRandomAccessList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends
        fabric.util.Collections.UnmodifiableList._Impl implements
        fabric.util.Collections.UnmodifiableRandomAccessList {

      @Override
      native public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
          fabric.util.List l);

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableRandomAccessList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.UnmodifiableRandomAccessList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableRandomAccessList._Static {

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

  public static interface UnmodifiableListIterator extends
      fabric.util.ListIterator, fabric.util.Collections.UnmodifiableIterator {

    public fabric.util.ListIterator get$li();

    public fabric.util.ListIterator set$li(fabric.util.ListIterator val);

    public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
        fabric.util.ListIterator li);

    @Override
    public void add(fabric.lang.Object o);

    @Override
    public boolean hasPrevious();

    @Override
    public int nextIndex();

    @Override
    public fabric.lang.Object previous();

    @Override
    public int previousIndex();

    @Override
    public void set(fabric.lang.Object o);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableIterator._Proxy implements
        fabric.util.Collections.UnmodifiableListIterator {

      @Override
      native public fabric.util.ListIterator get$li();

      @Override
      native public fabric.util.ListIterator set$li(fabric.util.ListIterator val);

      @Override
      native public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
          fabric.util.ListIterator arg1);

      @Override
      native public void add(fabric.lang.Object arg1);

      @Override
      native public boolean hasPrevious();

      @Override
      native public int nextIndex();

      @Override
      native public fabric.lang.Object previous();

      @Override
      native public int previousIndex();

      @Override
      native public void set(fabric.lang.Object arg1);

      public _Proxy(UnmodifiableListIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends
        fabric.util.Collections.UnmodifiableIterator._Impl implements
        fabric.util.Collections.UnmodifiableListIterator {

      @Override
      native public fabric.util.ListIterator get$li();

      @Override
      native public fabric.util.ListIterator set$li(fabric.util.ListIterator val);

      @Override
      native public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
          fabric.util.ListIterator li);

      @Override
      native public void add(fabric.lang.Object o);

      @Override
      native public boolean hasPrevious();

      @Override
      native public int nextIndex();

      @Override
      native public fabric.lang.Object previous();

      @Override
      native public int previousIndex();

      @Override
      native public void set(fabric.lang.Object o);

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
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableListIterator._Static {

        public _Proxy(
            fabric.util.Collections.UnmodifiableListIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableListIterator._Static {

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

  public static interface UnmodifiableMap extends fabric.util.Map,
      java.io.Serializable, fabric.lang.Object {

    public fabric.util.Map get$m();

    public fabric.util.Map set$m(fabric.util.Map val);

    public fabric.util.Set get$entries();

    public fabric.util.Set set$entries(fabric.util.Set val);

    public fabric.util.Set get$keys();

    public fabric.util.Set set$keys(fabric.util.Set val);

    public fabric.util.Collection get$values();

    public fabric.util.Collection set$values(fabric.util.Collection val);

    public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
        fabric.util.Map m);

    @Override
    public void clear();

    @Override
    public boolean containsKey(fabric.lang.Object key);

    @Override
    public boolean containsValue(fabric.lang.Object value);

    @Override
    public fabric.util.Set entrySet();

    public static interface UnmodifiableEntrySet extends java.io.Serializable,
        fabric.util.Collections.UnmodifiableSet {
      public static interface UnmodifiableMapEntry extends
          fabric.util.Map.Entry, fabric.lang.Object {

        public fabric.util.Map.Entry get$e();

        public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);

        @Override
        public boolean equals(fabric.lang.Object o);

        @Override
        public fabric.lang.Object getKey();

        @Override
        public fabric.lang.Object getValue();

        @Override
        public int hashCode();

        @Override
        public fabric.lang.Object setValue(fabric.lang.Object value);

        @Override
        public java.lang.String toString();

        @Override
        public fabric.lang.Object $initLabels();

        public static class _Proxy extends fabric.lang.Object._Proxy
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry {

          @Override
          native public fabric.util.Map.Entry get$e();

          @Override
          native public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);

          @Override
          native public boolean equals(fabric.lang.Object arg1);

          @Override
          native public fabric.lang.Object getKey();

          @Override
          native public fabric.lang.Object getValue();

          @Override
          native public int hashCode();

          @Override
          native public fabric.lang.Object setValue(fabric.lang.Object arg1);

          @Override
          native public java.lang.String toString();

          @Override
          native public fabric.lang.Object $initLabels();

          public _Proxy(UnmodifiableMapEntry._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        final public static class _Impl extends fabric.lang.Object._Impl
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry {

          @Override
          native public fabric.util.Map.Entry get$e();

          @Override
          native public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);

          native private fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry fabric$util$Collections$UnmodifiableMapEntry$(
              fabric.util.Map.Entry e);

          @Override
          native public boolean equals(fabric.lang.Object o);

          @Override
          native public fabric.lang.Object getKey();

          @Override
          native public fabric.lang.Object getValue();

          @Override
          native public int hashCode();

          @Override
          native public fabric.lang.Object setValue(fabric.lang.Object value);

          @Override
          native public java.lang.String toString();

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
              java.util.Iterator intraStoreRefs,
              java.util.Iterator interStoreRefs) throws java.io.IOException,
              java.lang.ClassNotFoundException {
            super(store, onum, version, warranty, label, accessLabel, in,
                refTypes, intraStoreRefs, interStoreRefs);
          }

          @Override
          native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }

        interface _Static extends fabric.lang.Object, Cloneable {
          final class _Proxy extends fabric.lang.Object._Proxy
              implements
              fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry._Static {

            public _Proxy(
                fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry._Static._Impl impl) {
              super(impl);
            }

            public _Proxy(fabric.worker.Store store, long onum) {
              super(store, onum);
            }
          }

          class _Impl extends fabric.lang.Object._Impl
              implements
              fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry._Static {

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

      public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet fabric$util$Collections$UnmodifiableEntrySet$(
          fabric.util.Set s);

      @Override
      public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      public fabric.lang.arrays.ObjectArray toArray();

      @Override
      public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray array);

      @Override
      public fabric.lang.Object $initLabels();

      public static interface Anonymous$4 extends
          fabric.util.Collections.UnmodifiableIterator {

        public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet get$out$();

        @Override
        public fabric.lang.Object next();

        @Override
        public fabric.lang.Object $initLabels();

        public static class _Proxy extends
            fabric.util.Collections.UnmodifiableIterator._Proxy
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4 {

          @Override
          native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet get$out$();

          public _Proxy(Anonymous$4._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        public static class _Impl extends
            fabric.util.Collections.UnmodifiableIterator._Impl
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4 {

          @Override
          native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet get$out$();

          @Override
          native public fabric.lang.Object next();

          @Override
          native public fabric.lang.Object $initLabels();

          private _Impl(
              fabric.worker.Store $location,
              final fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet out$) {
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
              java.util.Iterator intraStoreRefs,
              java.util.Iterator interStoreRefs) throws java.io.IOException,
              java.lang.ClassNotFoundException {
            super(store, onum, version, warranty, label, accessLabel, in,
                refTypes, intraStoreRefs, interStoreRefs);
          }

          @Override
          native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }

        interface _Static extends fabric.lang.Object, Cloneable {
          final class _Proxy extends fabric.lang.Object._Proxy
              implements
              fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4._Static {

            public _Proxy(
                fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4._Static._Impl impl) {
              super(impl);
            }

            public _Proxy(fabric.worker.Store store, long onum) {
              super(store, onum);
            }
          }

          class _Impl extends fabric.lang.Object._Impl
              implements
              fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4._Static {

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

      public static class _Proxy extends
          fabric.util.Collections.UnmodifiableSet._Proxy implements
          fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet {

        @Override
        native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet fabric$util$Collections$UnmodifiableEntrySet$(
            fabric.util.Set arg1);

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

        @Override
        native public fabric.lang.arrays.ObjectArray toArray();

        @Override
        native public fabric.lang.arrays.ObjectArray toArray(
            fabric.lang.arrays.ObjectArray arg1);

        public _Proxy(UnmodifiableEntrySet._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      final public static class _Impl extends
          fabric.util.Collections.UnmodifiableSet._Impl implements
          fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet {

        @Override
        native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet fabric$util$Collections$UnmodifiableEntrySet$(
            fabric.util.Set s);

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store store);

        @Override
        native public fabric.lang.arrays.ObjectArray toArray();

        @Override
        native public fabric.lang.arrays.ObjectArray toArray(
            fabric.lang.arrays.ObjectArray array);

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
          super(store, onum, version, warranty, label, accessLabel, in,
              refTypes, intraStoreRefs, interStoreRefs);
        }
      }

      interface _Static extends fabric.lang.Object, Cloneable {

        public long get$serialVersionUID();

        final class _Proxy extends fabric.lang.Object._Proxy
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet._Static {

          @Override
          native public long get$serialVersionUID();

          public _Proxy(
              fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl
            implements
            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet._Static {

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

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    public fabric.lang.Object put(fabric.lang.Object key,
        fabric.lang.Object value);

    @Override
    public int hashCode();

    @Override
    public boolean isEmpty();

    @Override
    public fabric.util.Set keySet();

    @Override
    public void putAll(fabric.util.Map m);

    @Override
    public fabric.lang.Object remove(fabric.lang.Object o);

    @Override
    public int size();

    @Override
    public java.lang.String toString();

    @Override
    public fabric.util.Collection values();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Collections.UnmodifiableMap {

      @Override
      native public fabric.util.Map get$m();

      @Override
      native public fabric.util.Map set$m(fabric.util.Map val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.Set get$keys();

      @Override
      native public fabric.util.Set set$keys(fabric.util.Set val);

      @Override
      native public fabric.util.Collection get$values();

      @Override
      native public fabric.util.Collection set$values(fabric.util.Collection val);

      @Override
      native public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
          fabric.util.Map arg1);

      @Override
      native public void clear();

      @Override
      native public boolean containsKey(fabric.lang.Object arg1);

      @Override
      native public boolean containsValue(fabric.lang.Object arg1);

      @Override
      native public fabric.util.Set entrySet();

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object get(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object put(fabric.lang.Object arg1,
          fabric.lang.Object arg2);

      @Override
      native public int hashCode();

      @Override
      native public boolean isEmpty();

      @Override
      native public fabric.util.Set keySet();

      @Override
      native public void putAll(fabric.util.Map arg1);

      @Override
      native public fabric.lang.Object remove(fabric.lang.Object arg1);

      @Override
      native public int size();

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.util.Collection values();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(UnmodifiableMap._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Collections.UnmodifiableMap {

      @Override
      native public fabric.util.Map get$m();

      @Override
      native public fabric.util.Map set$m(fabric.util.Map val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.Set get$keys();

      @Override
      native public fabric.util.Set set$keys(fabric.util.Set val);

      @Override
      native public fabric.util.Collection get$values();

      @Override
      native public fabric.util.Collection set$values(fabric.util.Collection val);

      @Override
      native public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
          fabric.util.Map m);

      @Override
      native public void clear();

      @Override
      native public boolean containsKey(fabric.lang.Object key);

      @Override
      native public boolean containsValue(fabric.lang.Object value);

      @Override
      native public fabric.util.Set entrySet();

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object get(fabric.lang.Object key);

      @Override
      native public fabric.lang.Object put(fabric.lang.Object key,
          fabric.lang.Object value);

      @Override
      native public int hashCode();

      @Override
      native public boolean isEmpty();

      @Override
      native public fabric.util.Set keySet();

      @Override
      native public void putAll(fabric.util.Map m);

      @Override
      native public fabric.lang.Object remove(fabric.lang.Object o);

      @Override
      native public int size();

      @Override
      native public java.lang.String toString();

      @Override
      native public fabric.util.Collection values();

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableMap._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.UnmodifiableMap._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableMap._Static {

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

  public static interface UnmodifiableSet extends fabric.util.Set,
      fabric.util.Collections.UnmodifiableCollection {

    public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
        fabric.util.Set s);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public int hashCode();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableCollection._Proxy implements
        fabric.util.Collections.UnmodifiableSet {

      @Override
      native public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
          fabric.util.Set arg1);

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      @Override
      native public int hashCode();

      public _Proxy(UnmodifiableSet._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends
        fabric.util.Collections.UnmodifiableCollection._Impl implements
        fabric.util.Collections.UnmodifiableSet {

      @Override
      native public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
          fabric.util.Set s);

      @Override
      native public boolean equals(fabric.lang.Object o);

      @Override
      native public int hashCode();

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
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableSet._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Collections.UnmodifiableSet._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableSet._Static {

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

  public static interface UnmodifiableSortedMap extends fabric.util.SortedMap,
      fabric.util.Collections.UnmodifiableMap {

    public fabric.util.SortedMap get$sm();

    public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);

    public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
        fabric.util.SortedMap sm);

    @Override
    public fabric.util.Comparator comparator();

    @Override
    public fabric.lang.Object firstKey();

    @Override
    public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

    @Override
    public fabric.lang.Object lastKey();

    @Override
    public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
        fabric.lang.Object toKey);

    @Override
    public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableMap._Proxy implements
        fabric.util.Collections.UnmodifiableSortedMap {

      @Override
      native public fabric.util.SortedMap get$sm();

      @Override
      native public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);

      @Override
      native public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
          fabric.util.SortedMap arg1);

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public fabric.lang.Object firstKey();

      @Override
      native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object lastKey();

      @Override
      native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
          fabric.lang.Object arg2);

      @Override
      native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);

      public _Proxy(UnmodifiableSortedMap._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends
        fabric.util.Collections.UnmodifiableMap._Impl implements
        fabric.util.Collections.UnmodifiableSortedMap {

      @Override
      native public fabric.util.SortedMap get$sm();

      @Override
      native public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);

      @Override
      native public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
          fabric.util.SortedMap sm);

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public fabric.lang.Object firstKey();

      @Override
      native public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

      @Override
      native public fabric.lang.Object lastKey();

      @Override
      native public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
          fabric.lang.Object toKey);

      @Override
      native public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableSortedMap._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.UnmodifiableSortedMap._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableSortedMap._Static {

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

  public static interface UnmodifiableSortedSet extends fabric.util.SortedSet,
      fabric.util.Collections.UnmodifiableSet {

    public fabric.util.SortedSet get$ss();

    public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);

    public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
        fabric.util.SortedSet ss);

    @Override
    public fabric.util.Comparator comparator();

    @Override
    public fabric.lang.Object first();

    @Override
    public fabric.util.SortedSet headSet(fabric.lang.Object toElement);

    @Override
    public fabric.lang.Object last();

    @Override
    public fabric.util.SortedSet subSet(fabric.lang.Object fromElement,
        fabric.lang.Object toElement);

    @Override
    public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.Collections.UnmodifiableSet._Proxy implements
        fabric.util.Collections.UnmodifiableSortedSet {

      @Override
      native public fabric.util.SortedSet get$ss();

      @Override
      native public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);

      @Override
      native public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
          fabric.util.SortedSet arg1);

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public fabric.lang.Object first();

      @Override
      native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object last();

      @Override
      native public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
          fabric.lang.Object arg2);

      @Override
      native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);

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
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public fabric.util.Iterator iterator();

      @Override
      native public boolean remove(fabric.lang.Object arg1);

      @Override
      native public boolean removeAll(fabric.util.Collection arg1);

      @Override
      native public boolean retainAll(fabric.util.Collection arg1);

      @Override
      native public int size();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);

      public _Proxy(UnmodifiableSortedSet._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends
        fabric.util.Collections.UnmodifiableSet._Impl implements
        fabric.util.Collections.UnmodifiableSortedSet {

      @Override
      native public fabric.util.SortedSet get$ss();

      @Override
      native public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);

      @Override
      native public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
          fabric.util.SortedSet ss);

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public fabric.lang.Object first();

      @Override
      native public fabric.util.SortedSet headSet(fabric.lang.Object toElement);

      @Override
      native public fabric.lang.Object last();

      @Override
      native public fabric.util.SortedSet subSet(
          fabric.lang.Object fromElement, fabric.lang.Object toElement);

      @Override
      native public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);

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

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Collections.UnmodifiableSortedSet._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(
            fabric.util.Collections.UnmodifiableSortedSet._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Collections.UnmodifiableSortedSet._Static {

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

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Collections {

    final native public static int compare(fabric.lang.Object arg1,
        fabric.lang.Object arg2, fabric.util.Comparator arg3);

    native public static int binarySearch(fabric.util.List arg1,
        fabric.lang.Object arg2);

    native public static int binarySearch(fabric.util.List arg1,
        fabric.lang.Object arg2, fabric.util.Comparator arg3);

    native public static void copy(fabric.util.List arg1, fabric.util.List arg2);

    native public static void fill(fabric.util.List arg1,
        fabric.lang.Object arg2);

    native public static int indexOfSubList(fabric.util.List arg1,
        fabric.util.List arg2);

    native public static int lastIndexOfSubList(fabric.util.List arg1,
        fabric.util.List arg2);

    native public static fabric.util.ArrayList list(fabric.util.Enumeration arg1);

    native public static fabric.lang.Object max(fabric.util.Collection arg1);

    native public static fabric.lang.Object max(fabric.util.Collection arg1,
        fabric.util.Comparator arg2);

    native public static fabric.lang.Object min(fabric.util.Collection arg1);

    native public static fabric.lang.Object min(fabric.util.Collection arg1,
        fabric.util.Comparator arg2);

    native public static fabric.util.List nCopies(int arg1,
        fabric.lang.Object arg2);

    native public static boolean replaceAll(fabric.util.List arg1,
        fabric.lang.Object arg2, fabric.lang.Object arg3);

    native public static void reverse(fabric.util.List arg1);

    native public static fabric.util.Comparator reverseOrder();

    native public static void rotate(fabric.util.List arg1, int arg2);

    native public static void shuffle(fabric.util.List arg1);

    native public static void shuffle(fabric.util.List arg1,
        fabric.util.Random arg2);

    native public static fabric.util.Set singleton(fabric.lang.Object arg1);

    native public static fabric.util.List singletonList(fabric.lang.Object arg1);

    native public static fabric.util.Map singletonMap(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    native public static void sort(fabric.util.List arg1);

    native public static void sort(fabric.util.List arg1,
        fabric.util.Comparator arg2);

    native public static void swap(fabric.util.List arg1, int arg2, int arg3);

    native public static fabric.util.Collection unmodifiableCollection(
        fabric.worker.Store arg1, fabric.util.Collection arg2);

    native public static fabric.util.List unmodifiableList(
        fabric.worker.Store arg1, fabric.util.List arg2);

    native public static fabric.util.Map unmodifiableMap(
        fabric.worker.Store arg1, fabric.util.Map arg2);

    native public static fabric.util.Set unmodifiableSet(
        fabric.worker.Store arg1, fabric.util.Set arg2);

    native public static fabric.util.SortedMap unmodifiableSortedMap(
        fabric.worker.Store arg1, fabric.util.SortedMap arg2);

    native public static fabric.util.SortedSet unmodifiableSortedSet(
        fabric.worker.Store arg1, fabric.util.SortedSet arg2);

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(Collections._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.util.Collections {

    native private static boolean isSequential(fabric.util.List l);

    private _Impl(fabric.worker.Store $location) {
      super($location);
    }

    final native public static int compare(fabric.lang.Object o1,
        fabric.lang.Object o2, fabric.util.Comparator c);

    native public static int binarySearch(fabric.util.List l,
        fabric.lang.Object key);

    native public static int binarySearch(fabric.util.List l,
        fabric.lang.Object key, fabric.util.Comparator c);

    native public static void copy(fabric.util.List dest,
        fabric.util.List source);

    native public static void fill(fabric.util.List l, fabric.lang.Object val);

    native public static int indexOfSubList(fabric.util.List source,
        fabric.util.List target);

    native public static int lastIndexOfSubList(fabric.util.List source,
        fabric.util.List target);

    native public static fabric.util.ArrayList list(fabric.util.Enumeration e);

    native public static fabric.lang.Object max(fabric.util.Collection c);

    native public static fabric.lang.Object max(fabric.util.Collection c,
        fabric.util.Comparator order);

    native public static fabric.lang.Object min(fabric.util.Collection c);

    native public static fabric.lang.Object min(fabric.util.Collection c,
        fabric.util.Comparator order);

    native public static fabric.util.List nCopies(final int n,
        final fabric.lang.Object o);

    native public static boolean replaceAll(fabric.util.List list,
        fabric.lang.Object oldval, fabric.lang.Object newval);

    native public static void reverse(fabric.util.List l);

    native public static fabric.util.Comparator reverseOrder();

    native public static void rotate(fabric.util.List list, int distance);

    native public static void shuffle(fabric.util.List l);

    native public static void shuffle(fabric.util.List l, fabric.util.Random r);

    native public static fabric.util.Set singleton(fabric.lang.Object o);

    native public static fabric.util.List singletonList(fabric.lang.Object o);

    native public static fabric.util.Map singletonMap(fabric.lang.Object key,
        fabric.lang.Object value);

    native public static void sort(fabric.util.List l);

    native public static void sort(fabric.util.List l, fabric.util.Comparator c);

    native public static void swap(fabric.util.List l, int i, int j);

    native public static fabric.util.Collection unmodifiableCollection(
        fabric.worker.Store store, fabric.util.Collection c);

    native public static fabric.util.List unmodifiableList(
        fabric.worker.Store store, fabric.util.List l);

    native public static fabric.util.Map unmodifiableMap(
        fabric.worker.Store store, fabric.util.Map m);

    native public static fabric.util.Set unmodifiableSet(
        fabric.worker.Store store, fabric.util.Set s);

    native public static fabric.util.SortedMap unmodifiableSortedMap(
        fabric.worker.Store store, fabric.util.SortedMap m);

    native public static fabric.util.SortedSet unmodifiableSortedSet(
        fabric.worker.Store store, fabric.util.SortedSet s);

    @Override
    native public fabric.lang.Object $initLabels();

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
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public fabric.worker.Store get$LOCAL_STORE();

    public int get$LARGE_LIST_SIZE();

    public fabric.util.Set get$EMPTY_SET();

    public fabric.util.List get$EMPTY_LIST();

    public fabric.util.Map get$EMPTY_MAP();

    public fabric.util.Collections.ReverseComparator get$rcInstance();

    public fabric.util.Random get$defaultRandom();

    public fabric.util.Random set$defaultRandom(fabric.util.Random val);

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Collections._Static {

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      @Override
      native public int get$LARGE_LIST_SIZE();

      @Override
      native public fabric.util.Set get$EMPTY_SET();

      @Override
      native public fabric.util.List get$EMPTY_LIST();

      @Override
      native public fabric.util.Map get$EMPTY_MAP();

      @Override
      native public fabric.util.Collections.ReverseComparator get$rcInstance();

      @Override
      native public fabric.util.Random get$defaultRandom();

      @Override
      native public fabric.util.Random set$defaultRandom(fabric.util.Random val);

      public _Proxy(fabric.util.Collections._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Collections._Static {

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      @Override
      native public int get$LARGE_LIST_SIZE();

      @Override
      native public fabric.util.Set get$EMPTY_SET();

      @Override
      native public fabric.util.List get$EMPTY_LIST();

      @Override
      native public fabric.util.Map get$EMPTY_MAP();

      @Override
      native public fabric.util.Collections.ReverseComparator get$rcInstance();

      @Override
      native public fabric.util.Random get$defaultRandom();

      @Override
      native public fabric.util.Random set$defaultRandom(fabric.util.Random val);

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
