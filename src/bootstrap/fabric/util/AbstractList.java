package fabric.util;

import fabric.common.VersionWarranty;

public interface AbstractList extends fabric.util.List,
    fabric.util.AbstractCollection {

  public int get$modCount();

  public int set$modCount(int val);

  public int postInc$modCount();

  public int postDec$modCount();

  public fabric.util.AbstractList fabric$util$AbstractList$();

  @Override
  abstract public fabric.lang.Object get(int index);

  @Override
  public void add(int index, fabric.lang.Object o);

  @Override
  public boolean add(fabric.lang.Object o);

  @Override
  public boolean addAll(int index, fabric.util.Collection c);

  @Override
  public void clear();

  @Override
  public boolean equals(fabric.lang.Object o);

  @Override
  public int hashCode();

  @Override
  public int indexOf(fabric.lang.Object o);

  @Override
  public fabric.util.Iterator iterator(fabric.worker.Store store);

  public static interface AnonymousIterator extends fabric.util.Iterator,
      fabric.lang.Object {

    public fabric.util.AbstractList get$out$();

    public int get$initSize();

    public int set$initSize(int val);

    public int postInc$initSize();

    public int postDec$initSize();

    public int get$initModCount();

    public int set$initModCount(int val);

    public int postInc$initModCount();

    public int postDec$initModCount();

    public int get$pos();

    public int set$pos(int val);

    public int postInc$pos();

    public int postDec$pos();

    public int get$size();

    public int set$size(int val);

    public int postInc$size();

    public int postDec$size();

    public int get$last();

    public int set$last(int val);

    public int postInc$last();

    public int postDec$last();

    public int get$knownMod();

    public int set$knownMod(int val);

    public int postInc$knownMod();

    public int postDec$knownMod();

    public fabric.util.AbstractList.AnonymousIterator fabric$util$AbstractList$AnonymousIterator$();

    @Override
    public boolean hasNext();

    @Override
    public fabric.lang.Object next();

    @Override
    public void remove();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractList.AnonymousIterator {

      @Override
      native public fabric.util.AbstractList get$out$();

      @Override
      native public int get$initSize();

      @Override
      native public int set$initSize(int val);

      @Override
      native public int postInc$initSize();

      @Override
      native public int postDec$initSize();

      @Override
      native public int get$initModCount();

      @Override
      native public int set$initModCount(int val);

      @Override
      native public int postInc$initModCount();

      @Override
      native public int postDec$initModCount();

      @Override
      native public int get$pos();

      @Override
      native public int set$pos(int val);

      @Override
      native public int postInc$pos();

      @Override
      native public int postDec$pos();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

      @Override
      native public int get$last();

      @Override
      native public int set$last(int val);

      @Override
      native public int postInc$last();

      @Override
      native public int postDec$last();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.AbstractList.AnonymousIterator fabric$util$AbstractList$AnonymousIterator$();

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(AnonymousIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractList.AnonymousIterator {

      @Override
      native public fabric.util.AbstractList get$out$();

      @Override
      native public int get$initSize();

      @Override
      native public int set$initSize(int val);

      @Override
      native public int postInc$initSize();

      @Override
      native public int postDec$initSize();

      @Override
      native public int get$initModCount();

      @Override
      native public int set$initModCount(int val);

      @Override
      native public int postInc$initModCount();

      @Override
      native public int postDec$initModCount();

      @Override
      native public int get$pos();

      @Override
      native public int set$pos(int val);

      @Override
      native public int postInc$pos();

      @Override
      native public int postDec$pos();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

      @Override
      native public int get$last();

      @Override
      native public int set$last(int val);

      @Override
      native public int postInc$last();

      @Override
      native public int postDec$last();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.AbstractList.AnonymousIterator fabric$util$AbstractList$AnonymousIterator$();

      native private void checkMod();

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location,
          final fabric.util.AbstractList out$) {
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
          fabric.util.AbstractList.AnonymousIterator._Static {

        public _Proxy(
            fabric.util.AbstractList.AnonymousIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractList.AnonymousIterator._Static {

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
  public int lastIndexOf(fabric.lang.Object o);

  @Override
  public fabric.util.ListIterator listIterator(fabric.worker.Store store);

  @Override
  public fabric.util.ListIterator listIterator(fabric.worker.Store store,
      final int index);

  @Override
  public fabric.lang.Object remove(int index);

  public void removeRange(int fromIndex, int toIndex);

  @Override
  public fabric.lang.Object set(int index, fabric.lang.Object o);

  @Override
  public fabric.util.List subList(int fromIndex, int toIndex);

  public static interface AbstractListIterator extends
      fabric.util.ListIterator, fabric.lang.Object {

    public fabric.util.AbstractList get$out$();

    public int get$index();

    public int set$index(int val);

    public int postInc$index();

    public int postDec$index();

    public int get$knownMod();

    public int set$knownMod(int val);

    public int postInc$knownMod();

    public int postDec$knownMod();

    public int get$position();

    public int set$position(int val);

    public int postInc$position();

    public int postDec$position();

    public int get$lastReturned();

    public int set$lastReturned(int val);

    public int postInc$lastReturned();

    public int postDec$lastReturned();

    public int get$size();

    public int set$size(int val);

    public int postInc$size();

    public int postDec$size();

    @Override
    public boolean hasNext();

    @Override
    public boolean hasPrevious();

    @Override
    public fabric.lang.Object next();

    @Override
    public fabric.lang.Object previous();

    @Override
    public int nextIndex();

    @Override
    public int previousIndex();

    @Override
    public void remove();

    @Override
    public void set(fabric.lang.Object o);

    @Override
    public void add(fabric.lang.Object o);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractList.AbstractListIterator {

      @Override
      native public fabric.util.AbstractList get$out$();

      @Override
      native public int get$index();

      @Override
      native public int set$index(int val);

      @Override
      native public int postInc$index();

      @Override
      native public int postDec$index();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public int get$position();

      @Override
      native public int set$position(int val);

      @Override
      native public int postInc$position();

      @Override
      native public int postDec$position();

      @Override
      native public int get$lastReturned();

      @Override
      native public int set$lastReturned(int val);

      @Override
      native public int postInc$lastReturned();

      @Override
      native public int postDec$lastReturned();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

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
      native public void remove();

      @Override
      native public void set(fabric.lang.Object arg1);

      @Override
      native public void add(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(AbstractListIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractList.AbstractListIterator {

      @Override
      native public fabric.util.AbstractList get$out$();

      @Override
      native public int get$index();

      @Override
      native public int set$index(int val);

      @Override
      native public int postInc$index();

      @Override
      native public int postDec$index();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public int get$position();

      @Override
      native public int set$position(int val);

      @Override
      native public int postInc$position();

      @Override
      native public int postDec$position();

      @Override
      native public int get$lastReturned();

      @Override
      native public int set$lastReturned(int val);

      @Override
      native public int postInc$lastReturned();

      @Override
      native public int postDec$lastReturned();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

      native private fabric.util.AbstractList.AbstractListIterator fabric$util$AbstractList$AbstractListIterator$(
          int index);

      native private void checkMod();

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
      native public void remove();

      @Override
      native public void set(fabric.lang.Object o);

      @Override
      native public void add(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location,
          final fabric.util.AbstractList out$) {
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
          fabric.util.AbstractList.AbstractListIterator._Static {

        public _Proxy(
            fabric.util.AbstractList.AbstractListIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractList.AbstractListIterator._Static {

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

  public static interface SubList extends fabric.util.AbstractList {

    public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$();

    public static interface SubListIterator extends fabric.util.ListIterator,
        fabric.lang.Object {

      public fabric.util.AbstractList.SubList get$out$();

      public int get$index();

      public int set$index(int val);

      public int postInc$index();

      public int postDec$index();

      public fabric.util.ListIterator get$i();

      public fabric.util.ListIterator set$i(fabric.util.ListIterator val);

      public int get$position();

      public int set$position(int val);

      public int postInc$position();

      public int postDec$position();

      @Override
      public boolean hasNext();

      @Override
      public boolean hasPrevious();

      @Override
      public fabric.lang.Object next();

      @Override
      public fabric.lang.Object previous();

      @Override
      public int nextIndex();

      @Override
      public int previousIndex();

      @Override
      public void remove();

      @Override
      public void set(fabric.lang.Object o);

      @Override
      public void add(fabric.lang.Object o);

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractList.SubList.SubListIterator {

        @Override
        native public fabric.util.AbstractList.SubList get$out$();

        @Override
        native public int get$index();

        @Override
        native public int set$index(int val);

        @Override
        native public int postInc$index();

        @Override
        native public int postDec$index();

        @Override
        native public fabric.util.ListIterator get$i();

        @Override
        native public fabric.util.ListIterator set$i(
            fabric.util.ListIterator val);

        @Override
        native public int get$position();

        @Override
        native public int set$position(int val);

        @Override
        native public int postInc$position();

        @Override
        native public int postDec$position();

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
        native public void remove();

        @Override
        native public void set(fabric.lang.Object arg1);

        @Override
        native public void add(fabric.lang.Object arg1);

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(SubListIterator._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList.SubList.SubListIterator {

        @Override
        native public fabric.util.AbstractList.SubList get$out$();

        @Override
        native public int get$index();

        @Override
        native public int set$index(int val);

        @Override
        native public int postInc$index();

        @Override
        native public int postDec$index();

        @Override
        native public fabric.util.ListIterator get$i();

        @Override
        native public fabric.util.ListIterator set$i(
            fabric.util.ListIterator val);

        @Override
        native public int get$position();

        @Override
        native public int set$position(int val);

        @Override
        native public int postInc$position();

        @Override
        native public int postDec$position();

        native private fabric.util.AbstractList.SubList.SubListIterator fabric$util$AbstractList$SubListIterator$(
            int index);

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
        native public void remove();

        @Override
        native public void set(fabric.lang.Object o);

        @Override
        native public void add(fabric.lang.Object o);

        @Override
        native public fabric.lang.Object $initLabels();

        public _Impl(fabric.worker.Store $location,
            final fabric.util.AbstractList.SubList out$) {
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
            fabric.util.AbstractList.SubList.SubListIterator._Static {

          public _Proxy(
              fabric.util.AbstractList.SubList.SubListIterator._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.AbstractList.SubList.SubListIterator._Static {

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

    public fabric.util.AbstractList get$backingList();

    public fabric.util.AbstractList set$backingList(fabric.util.AbstractList val);

    public int get$offset();

    public int set$offset(int val);

    public int postInc$offset();

    public int postDec$offset();

    public int get$size();

    public int set$size(int val);

    public int postInc$size();

    public int postDec$size();

    public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$(
        fabric.util.AbstractList backing, int fromIndex, int toIndex);

    public void checkMod();

    @Override
    public int size();

    @Override
    public fabric.lang.Object set(int index, fabric.lang.Object o);

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public void add(int index, fabric.lang.Object o);

    @Override
    public fabric.lang.Object remove(int index);

    @Override
    public void removeRange(int fromIndex, int toIndex);

    @Override
    public boolean addAll(int index, fabric.util.Collection c);

    @Override
    public boolean addAll(fabric.util.Collection c);

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
        final int index);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList._Proxy
        implements fabric.util.AbstractList.SubList {

      @Override
      native public fabric.util.AbstractList get$backingList();

      @Override
      native public fabric.util.AbstractList set$backingList(
          fabric.util.AbstractList val);

      @Override
      native public int get$offset();

      @Override
      native public int set$offset(int val);

      @Override
      native public int postInc$offset();

      @Override
      native public int postDec$offset();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

      @Override
      native public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$();

      @Override
      native public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$(
          fabric.util.AbstractList arg1, int arg2, int arg3);

      @Override
      native public void checkMod();

      @Override
      native public int size();

      @Override
      native public boolean addAll(fabric.util.Collection arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(SubList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractList._Impl implements
        fabric.util.AbstractList.SubList {

      @Override
      native public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$();

      @Override
      native public fabric.util.AbstractList get$backingList();

      @Override
      native public fabric.util.AbstractList set$backingList(
          fabric.util.AbstractList val);

      @Override
      native public int get$offset();

      @Override
      native public int set$offset(int val);

      @Override
      native public int postInc$offset();

      @Override
      native public int postDec$offset();

      @Override
      native public int get$size();

      @Override
      native public int set$size(int val);

      @Override
      native public int postInc$size();

      @Override
      native public int postDec$size();

      @Override
      native public fabric.util.AbstractList.SubList fabric$util$AbstractList$SubList$(
          fabric.util.AbstractList backing, int fromIndex, int toIndex);

      @Override
      native public void checkMod();

      native private void checkBoundsInclusive(int index);

      native private void checkBoundsExclusive(int index);

      @Override
      native public int size();

      @Override
      native public fabric.lang.Object set(int index, fabric.lang.Object o);

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public void add(int index, fabric.lang.Object o);

      @Override
      native public fabric.lang.Object remove(int index);

      @Override
      native public void removeRange(int fromIndex, int toIndex);

      @Override
      native public boolean addAll(int index, fabric.util.Collection c);

      @Override
      native public boolean addAll(fabric.util.Collection c);

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store, final int index);

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
          VersionWarranty warranty, long label, long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, label, accessLabel, in, refTypes,
            intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractList.SubList._Static {

        public _Proxy(fabric.util.AbstractList.SubList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractList.SubList._Static {

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

  public static interface RandomAccessSubList extends fabric.util.RandomAccess,
      fabric.util.AbstractList.SubList {

    public fabric.util.AbstractList.RandomAccessSubList fabric$util$AbstractList$RandomAccessSubList$(
        fabric.util.AbstractList backing, int fromIndex, int toIndex);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList.SubList._Proxy
        implements fabric.util.AbstractList.RandomAccessSubList {

      @Override
      native public fabric.util.AbstractList.RandomAccessSubList fabric$util$AbstractList$RandomAccessSubList$(
          fabric.util.AbstractList arg1, int arg2, int arg3);

      public _Proxy(RandomAccessSubList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends
        fabric.util.AbstractList.SubList._Impl implements
        fabric.util.AbstractList.RandomAccessSubList {

      @Override
      native public fabric.util.AbstractList.RandomAccessSubList fabric$util$AbstractList$RandomAccessSubList$(
          fabric.util.AbstractList backing, int fromIndex, int toIndex);

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
          VersionWarranty warranty, long label, long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, label, accessLabel, in, refTypes,
            intraStoreRefs, interStoreRefs);
      }
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractList.RandomAccessSubList._Static {

        public _Proxy(
            fabric.util.AbstractList.RandomAccessSubList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractList.RandomAccessSubList._Static {

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

  public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList {

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

    @Override
    native public fabric.util.AbstractList fabric$util$AbstractList$();

    @Override
    native public fabric.lang.Object get(int arg1);

    @Override
    native public void add(int arg1, fabric.lang.Object arg2);

    @Override
    native public boolean addAll(int arg1, fabric.util.Collection arg2);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public int hashCode();

    @Override
    native public int indexOf(fabric.lang.Object arg1);

    @Override
    native public int lastIndexOf(fabric.lang.Object arg1);

    @Override
    native public fabric.util.ListIterator listIterator(fabric.worker.Store arg1);

    @Override
    native public fabric.util.ListIterator listIterator(
        fabric.worker.Store arg1, int arg2);

    @Override
    native public fabric.lang.Object remove(int arg1);

    @Override
    native public void removeRange(int arg1, int arg2);

    @Override
    native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);

    @Override
    native public fabric.util.List subList(int arg1, int arg2);

    public _Proxy(AbstractList._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends
      fabric.util.AbstractCollection._Impl implements fabric.util.AbstractList {

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

    @Override
    native public fabric.util.AbstractList fabric$util$AbstractList$();

    @Override
    abstract public fabric.lang.Object get(int index);

    @Override
    native public void add(int index, fabric.lang.Object o);

    @Override
    native public boolean add(fabric.lang.Object o);

    @Override
    native public boolean addAll(int index, fabric.util.Collection c);

    @Override
    native public void clear();

    @Override
    native public boolean equals(fabric.lang.Object o);

    @Override
    native public int hashCode();

    @Override
    native public int indexOf(fabric.lang.Object o);

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    native public int lastIndexOf(fabric.lang.Object o);

    @Override
    native public fabric.util.ListIterator listIterator(
        fabric.worker.Store store);

    @Override
    native public fabric.util.ListIterator listIterator(
        fabric.worker.Store store, final int index);

    @Override
    native public fabric.lang.Object remove(int index);

    @Override
    native public void removeRange(int fromIndex, int toIndex);

    @Override
    native public fabric.lang.Object set(int index, fabric.lang.Object o);

    @Override
    native public fabric.util.List subList(int fromIndex, int toIndex);

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

    public fabric.worker.Store get$LOCAL_STORE();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractList._Static {

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      public _Proxy(fabric.util.AbstractList._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractList._Static {

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
