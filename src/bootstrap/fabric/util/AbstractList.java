package fabric.util;


public interface AbstractList
  extends fabric.util.List, fabric.util.AbstractCollection
{
    
    public fabric.util.AbstractList fabric$util$AbstractList$();
    
    abstract public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final fabric.lang.JifObject elem);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject elem);
    
    public void add(final int index, final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public void add_remote(
      final fabric.lang.security.Principal worker$principal, final int index,
      final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject remove(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject remove_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public int indexOf(final fabric.lang.JifObject o);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int indexOf(final fabric.lang.security.Label lbl,
                       final fabric.lang.JifObject o);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public int lastIndexOf(final fabric.lang.JifObject o);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int lastIndexOf(final fabric.lang.security.Label lbl,
                           final fabric.lang.JifObject o);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public fabric.util.Iterator iterator();
    
    public fabric.util.Iterator iterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.ListIterator listIterator();
    
    public fabric.util.ListIterator listIterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.ListIterator listIterator(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.util.ListIterator listIterator_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean equals(final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable o);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable o);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_AbstractList_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_List_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_List_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_List_L();
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractList_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_List_L();
        
        native public fabric.lang.security.Label set$jif$fabric_util_List_L(
          fabric.lang.security.Label val);
        
        native public fabric.util.AbstractList fabric$util$AbstractList$();
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public void add(int arg1, fabric.lang.JifObject arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add_remote(fabric.lang.security.Principal arg1,
                                      int arg2, fabric.lang.JifObject arg3)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2,
          fabric.lang.JifObject arg3)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int indexOf(fabric.lang.JifObject arg1);
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         fabric.lang.JifObject arg2);
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public int indexOf(fabric.lang.security.Label arg1,
                                  fabric.lang.JifObject arg2);
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         fabric.lang.security.Label arg2,
                                         fabric.lang.JifObject arg3);
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public int lastIndexOf(fabric.lang.JifObject arg1);
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public int lastIndexOf(fabric.lang.security.Label arg1,
                                      fabric.lang.JifObject arg2);
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public fabric.util.Iterator iterator_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.ListIterator listIterator();
        
        native public fabric.util.ListIterator listIterator_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.ListIterator listIterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.ListIterator listIterator(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.util.ListIterator listIterator_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.util.ListIterator listIterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.IDComparable arg2);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.IDComparable arg2);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.security.Label arg2,
                                            fabric.lang.IDComparable arg3);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.IDComparable arg3);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.AbstractList
          jif$cast$fabric_util_AbstractList(fabric.lang.security.Label arg1,
                                            java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_List_L();
        
        native public fabric.lang.JifObject set(int arg1,
                                                fabric.lang.JifObject arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        public _Proxy(AbstractList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractList
    {
        
        native public fabric.util.AbstractList fabric$util$AbstractList$();
        
        abstract public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final fabric.lang.JifObject elem);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.JifObject elem);
        
        native public void add(final int index,
                               final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int indexOf(final fabric.lang.JifObject o);
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public int indexOf(final fabric.lang.security.Label lbl,
                                  final fabric.lang.JifObject o);
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public int lastIndexOf(final fabric.lang.JifObject o);
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public int lastIndexOf(final fabric.lang.security.Label lbl,
                                      final fabric.lang.JifObject o);
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.ListIterator listIterator();
        
        native public fabric.util.ListIterator listIterator_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.ListIterator listIterator(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.util.ListIterator listIterator_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean equals(final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable o);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable o);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.AbstractList
          jif$cast$fabric_util_AbstractList(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractList_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_List_L();
        
        native public fabric.lang.security.Label set$jif$fabric_util_List_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_List_L();
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractList._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.AbstractList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}

interface ListItr extends fabric.util.ListIterator, fabric.lang.Object {
    
    public int get$nextIndex();
    
    public int set$nextIndex(int val);
    
    public int postInc$nextIndex();
    
    public int postDec$nextIndex();
    
    public int get$lastIndexReturned();
    
    public int set$lastIndexReturned(int val);
    
    public int postInc$lastIndexReturned();
    
    public int postDec$lastIndexReturned();
    
    public fabric.util.List get$list();
    
    public fabric.util.List set$list(fabric.util.List val);
    
    public fabric.util.ListItr fabric$util$ListItr$(final fabric.util.List l);
    
    public fabric.util.ListItr fabric$util$ListItr$(final fabric.util.List l,
                                                    final int index);
    
    public boolean hasNext();
    
    public boolean hasNext_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject next()
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.JifObject next_remote(
      final fabric.lang.security.Principal worker$principal)
          throws fabric.util.NoSuchElementException;
    
    public boolean hasPrevious();
    
    public boolean hasPrevious_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject previous()
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.JifObject previous_remote(
      final fabric.lang.security.Principal worker$principal)
          throws fabric.util.NoSuchElementException;
    
    public int nextIndex();
    
    public int nextIndex_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int previousIndex();
    
    public int previousIndex_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void remove() throws java.lang.IllegalStateException;
    
    public void remove_remote(
      final fabric.lang.security.Principal worker$principal)
          throws java.lang.IllegalStateException;
    
    public void set(final fabric.lang.JifObject o)
          throws java.lang.IllegalStateException;
    
    public void set_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o)
          throws java.lang.IllegalStateException;
    
    public void add(final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public void add_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public fabric.lang.security.Label get$jif$fabric_util_ListItr_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_ListIterator_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_ListIterator_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_ListIterator_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_Iterator_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListItr
    {
        
        native public int get$nextIndex();
        
        native public int set$nextIndex(int val);
        
        native public int postInc$nextIndex();
        
        native public int postDec$nextIndex();
        
        native public int get$lastIndexReturned();
        
        native public int set$lastIndexReturned(int val);
        
        native public int postInc$lastIndexReturned();
        
        native public int postDec$lastIndexReturned();
        
        native public fabric.util.List get$list();
        
        native public fabric.util.List set$list(fabric.util.List val);
        
        native public fabric.lang.security.Label get$jif$fabric_util_ListItr_L(
          );
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_ListIterator_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_util_ListIterator_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_util_Iterator_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
          fabric.lang.security.Label val);
        
        native public fabric.util.ListItr fabric$util$ListItr$(
          fabric.util.List arg1);
        
        native public fabric.util.ListItr fabric$util$ListItr$(
          fabric.util.List arg1, int arg2);
        
        native public boolean hasNext();
        
        native public boolean hasNext_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean hasNext$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public boolean hasPrevious();
        
        native public boolean hasPrevious_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean hasPrevious$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject previous()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject previous_remote(
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject previous$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public int nextIndex();
        
        native public int nextIndex_remote(fabric.lang.security.Principal arg1);
        
        native public int nextIndex$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int previousIndex();
        
        native public int previousIndex_remote(
          fabric.lang.security.Principal arg1);
        
        native public int previousIndex$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public void remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public void set(fabric.lang.JifObject arg1)
              throws java.lang.IllegalStateException;
        
        native public void set_remote(fabric.lang.security.Principal arg1,
                                      fabric.lang.JifObject arg2)
              throws java.lang.IllegalStateException;
        
        native public void set$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2)
              throws java.lang.IllegalStateException;
        
        native public void add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public void add_remote(fabric.lang.security.Principal arg1,
                                      fabric.lang.JifObject arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public void add$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.ListItr jif$cast$fabric_util_ListItr(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_ListIterator_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Iterator_L();
        
        public _Proxy(ListItr._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.ListItr
    {
        
        native public int get$nextIndex();
        
        native public int set$nextIndex(int val);
        
        native public int postInc$nextIndex();
        
        native public int postDec$nextIndex();
        
        native public int get$lastIndexReturned();
        
        native public int set$lastIndexReturned(int val);
        
        native public int postInc$lastIndexReturned();
        
        native public int postDec$lastIndexReturned();
        
        native public fabric.util.List get$list();
        
        native public fabric.util.List set$list(fabric.util.List val);
        
        native public fabric.util.ListItr fabric$util$ListItr$(
          final fabric.util.List l);
        
        native public fabric.util.ListItr fabric$util$ListItr$(
          final fabric.util.List l, final int index);
        
        native public boolean hasNext();
        
        native public boolean hasNext_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          final fabric.lang.security.Principal worker$principal)
              throws fabric.util.NoSuchElementException;
        
        native public boolean hasPrevious();
        
        native public boolean hasPrevious_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject previous()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject previous_remote(
          final fabric.lang.security.Principal worker$principal)
              throws fabric.util.NoSuchElementException;
        
        native public int nextIndex();
        
        native public int nextIndex_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int previousIndex();
        
        native public int previousIndex_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(
          final fabric.lang.security.Principal worker$principal)
              throws java.lang.IllegalStateException;
        
        native public void set(final fabric.lang.JifObject o)
              throws java.lang.IllegalStateException;
        
        native public void set_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o)
              throws java.lang.IllegalStateException;
        
        native public void add(final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public void add_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.ListItr jif$cast$fabric_util_ListItr(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label get$jif$fabric_util_ListItr_L(
          );
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_ListIterator_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_util_ListIterator_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_ListIterator_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Iterator_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Iterator_L();
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.ListItr._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.ListItr._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.ListItr._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
