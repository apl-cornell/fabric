package fabric.util;

import fabric.common.VersionWarranty;

public interface LinkedList extends fabric.util.List,
    fabric.util.AbstractSequentialList {

  public fabric.util.LinkedList.Entry get$first();

  public fabric.util.LinkedList.Entry set$first(fabric.util.LinkedList.Entry val);

  public fabric.util.LinkedList.Entry get$last();

  public fabric.util.LinkedList.Entry set$last(fabric.util.LinkedList.Entry val);

  public int get$size();

  public int set$size(int val);

  public int postInc$size();

  public int postDec$size();

  public static interface Entry extends fabric.lang.Object {

    public fabric.lang.Object get$data();

    public fabric.lang.Object set$data(fabric.lang.Object val);

    public fabric.util.LinkedList.Entry get$next();

    public fabric.util.LinkedList.Entry set$next(
        fabric.util.LinkedList.Entry val);

    public fabric.util.LinkedList.Entry get$previous();

    public fabric.util.LinkedList.Entry set$previous(
        fabric.util.LinkedList.Entry val);

    public fabric.util.LinkedList.Entry fabric$util$LinkedList$Entry$(
        fabric.lang.Object data);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.LinkedList.Entry {

      @Override
      native public fabric.lang.Object get$data();

      @Override
      native public fabric.lang.Object set$data(fabric.lang.Object val);

      @Override
      native public fabric.util.LinkedList.Entry get$next();

      @Override
      native public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$previous();

      @Override
      native public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry fabric$util$LinkedList$Entry$(
          fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Entry._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.LinkedList.Entry {

      @Override
      native public fabric.lang.Object get$data();

      @Override
      native public fabric.lang.Object set$data(fabric.lang.Object val);

      @Override
      native public fabric.util.LinkedList.Entry get$next();

      @Override
      native public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$previous();

      @Override
      native public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry fabric$util$LinkedList$Entry$(
          fabric.lang.Object data);

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
          fabric.util.LinkedList.Entry._Static {

        public _Proxy(fabric.util.LinkedList.Entry._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.LinkedList.Entry._Static {

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

  public fabric.util.LinkedList.Entry getEntry(int n);

  public void removeEntry(fabric.util.LinkedList.Entry e);

  public fabric.util.LinkedList fabric$util$LinkedList$();

  public fabric.util.LinkedList fabric$util$LinkedList$(fabric.util.Collection c);

  public fabric.lang.Object getFirst();

  public fabric.lang.Object getLast();

  public fabric.lang.Object removeFirst();

  public fabric.lang.Object removeLast();

  public void addFirst(fabric.lang.Object o);

  public void addLast(fabric.lang.Object o);

  @Override
  public boolean contains(fabric.lang.Object o);

  @Override
  public int size();

  @Override
  public boolean add(fabric.lang.Object o);

  @Override
  public boolean remove(fabric.lang.Object o);

  @Override
  public boolean addAll(fabric.util.Collection c);

  @Override
  public boolean addAll(int index, fabric.util.Collection c);

  @Override
  public void clear();

  @Override
  public fabric.lang.Object get(int index);

  @Override
  public fabric.lang.Object set(int index, fabric.lang.Object o);

  @Override
  public void add(int index, fabric.lang.Object o);

  @Override
  public fabric.lang.Object remove(int index);

  @Override
  public int indexOf(fabric.lang.Object o);

  @Override
  public int lastIndexOf(fabric.lang.Object o);

  @Override
  public fabric.util.ListIterator listIterator(fabric.worker.Store store,
      int index);

  @Override
  public fabric.lang.arrays.ObjectArray toArray();

  @Override
  public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);

  public static interface LinkedListItr extends fabric.util.ListIterator,
      fabric.lang.Object {

    public fabric.util.LinkedList get$out$();

    public int get$knownMod();

    public int set$knownMod(int val);

    public int postInc$knownMod();

    public int postDec$knownMod();

    public fabric.util.LinkedList.Entry get$next();

    public fabric.util.LinkedList.Entry set$next(
        fabric.util.LinkedList.Entry val);

    public fabric.util.LinkedList.Entry get$previous();

    public fabric.util.LinkedList.Entry set$previous(
        fabric.util.LinkedList.Entry val);

    public fabric.util.LinkedList.Entry get$lastReturned();

    public fabric.util.LinkedList.Entry set$lastReturned(
        fabric.util.LinkedList.Entry val);

    public int get$position();

    public int set$position(int val);

    public int postInc$position();

    public int postDec$position();

    public fabric.util.LinkedList.LinkedListItr fabric$util$LinkedList$LinkedListItr$(
        int index);

    @Override
    public int nextIndex();

    @Override
    public int previousIndex();

    @Override
    public boolean hasNext();

    @Override
    public boolean hasPrevious();

    @Override
    public fabric.lang.Object next();

    @Override
    public fabric.lang.Object previous();

    @Override
    public void remove();

    @Override
    public void add(fabric.lang.Object o);

    @Override
    public void set(fabric.lang.Object o);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.LinkedList.LinkedListItr {

      @Override
      native public fabric.util.LinkedList get$out$();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.LinkedList.Entry get$next();

      @Override
      native public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$previous();

      @Override
      native public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$lastReturned();

      @Override
      native public fabric.util.LinkedList.Entry set$lastReturned(
          fabric.util.LinkedList.Entry val);

      @Override
      native public int get$position();

      @Override
      native public int set$position(int val);

      @Override
      native public int postInc$position();

      @Override
      native public int postDec$position();

      @Override
      native public fabric.util.LinkedList.LinkedListItr fabric$util$LinkedList$LinkedListItr$(
          int arg1);

      @Override
      native public int nextIndex();

      @Override
      native public int previousIndex();

      @Override
      native public boolean hasNext();

      @Override
      native public boolean hasPrevious();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public fabric.lang.Object previous();

      @Override
      native public void remove();

      @Override
      native public void add(fabric.lang.Object arg1);

      @Override
      native public void set(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(LinkedListItr._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.LinkedList.LinkedListItr {

      @Override
      native public fabric.util.LinkedList get$out$();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.LinkedList.Entry get$next();

      @Override
      native public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$previous();

      @Override
      native public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);

      @Override
      native public fabric.util.LinkedList.Entry get$lastReturned();

      @Override
      native public fabric.util.LinkedList.Entry set$lastReturned(
          fabric.util.LinkedList.Entry val);

      @Override
      native public int get$position();

      @Override
      native public int set$position(int val);

      @Override
      native public int postInc$position();

      @Override
      native public int postDec$position();

      @Override
      native public fabric.util.LinkedList.LinkedListItr fabric$util$LinkedList$LinkedListItr$(
          int index);

      native private void checkMod();

      @Override
      native public int nextIndex();

      @Override
      native public int previousIndex();

      @Override
      native public boolean hasNext();

      @Override
      native public boolean hasPrevious();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public fabric.lang.Object previous();

      @Override
      native public void remove();

      @Override
      native public void add(fabric.lang.Object o);

      @Override
      native public void set(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location,
          final fabric.util.LinkedList out$) {
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
          fabric.util.LinkedList.LinkedListItr._Static {

        public _Proxy(fabric.util.LinkedList.LinkedListItr._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.LinkedList.LinkedListItr._Static {

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

  public static class _Proxy extends fabric.util.AbstractSequentialList._Proxy
      implements fabric.util.LinkedList {

    @Override
    native public fabric.util.LinkedList.Entry get$first();

    @Override
    native public fabric.util.LinkedList.Entry set$first(
        fabric.util.LinkedList.Entry val);

    @Override
    native public fabric.util.LinkedList.Entry get$last();

    @Override
    native public fabric.util.LinkedList.Entry set$last(
        fabric.util.LinkedList.Entry val);

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.util.LinkedList.Entry getEntry(int arg1);

    @Override
    native public void removeEntry(fabric.util.LinkedList.Entry arg1);

    @Override
    native public fabric.util.LinkedList fabric$util$LinkedList$();

    @Override
    native public fabric.util.LinkedList fabric$util$LinkedList$(
        fabric.util.Collection arg1);

    @Override
    native public fabric.lang.Object getFirst();

    @Override
    native public fabric.lang.Object getLast();

    @Override
    native public fabric.lang.Object removeFirst();

    @Override
    native public fabric.lang.Object removeLast();

    @Override
    native public void addFirst(fabric.lang.Object arg1);

    @Override
    native public void addLast(fabric.lang.Object arg1);

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public int size();

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public void clear();

    @Override
    native public int indexOf(fabric.lang.Object arg1);

    @Override
    native public int lastIndexOf(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public int hashCode();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.ListIterator listIterator(fabric.worker.Store arg1);

    @Override
    native public boolean removeAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public fabric.util.List subList(int arg1, int arg2);

    @Override
    native public fabric.util.Iterator iterator();

    public _Proxy(LinkedList._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractSequentialList._Impl
      implements fabric.util.LinkedList {

    @Override
    native public fabric.util.LinkedList.Entry get$first();

    @Override
    native public fabric.util.LinkedList.Entry set$first(
        fabric.util.LinkedList.Entry val);

    @Override
    native public fabric.util.LinkedList.Entry get$last();

    @Override
    native public fabric.util.LinkedList.Entry set$last(
        fabric.util.LinkedList.Entry val);

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.util.LinkedList.Entry getEntry(int n);

    @Override
    native public void removeEntry(fabric.util.LinkedList.Entry e);

    native private void checkBoundsInclusive(int index);

    native private void checkBoundsExclusive(int index);

    @Override
    native public fabric.util.LinkedList fabric$util$LinkedList$();

    @Override
    native public fabric.util.LinkedList fabric$util$LinkedList$(
        fabric.util.Collection c);

    @Override
    native public fabric.lang.Object getFirst();

    @Override
    native public fabric.lang.Object getLast();

    @Override
    native public fabric.lang.Object removeFirst();

    @Override
    native public fabric.lang.Object removeLast();

    @Override
    native public void addFirst(fabric.lang.Object o);

    @Override
    native public void addLast(fabric.lang.Object o);

    native private void addLastEntry(fabric.util.LinkedList.Entry e);

    @Override
    native public boolean contains(fabric.lang.Object o);

    @Override
    native public int size();

    @Override
    native public boolean add(fabric.lang.Object o);

    @Override
    native public boolean remove(fabric.lang.Object o);

    @Override
    native public boolean addAll(fabric.util.Collection c);

    @Override
    native public boolean addAll(int index, fabric.util.Collection c);

    @Override
    native public void clear();

    @Override
    native public fabric.lang.Object get(int index);

    @Override
    native public fabric.lang.Object set(int index, fabric.lang.Object o);

    @Override
    native public void add(int index, fabric.lang.Object o);

    @Override
    native public fabric.lang.Object remove(int index);

    @Override
    native public int indexOf(fabric.lang.Object o);

    @Override
    native public int lastIndexOf(fabric.lang.Object o);

    @Override
    native public fabric.util.ListIterator listIterator(
        fabric.worker.Store store, int index);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray a);

    native private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException;

    native private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, java.lang.ClassNotFoundException;

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
        fabric.util.LinkedList._Static {

      @Override
      native public long get$serialVersionUID();

      public _Proxy(fabric.util.LinkedList._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.LinkedList._Static {

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
