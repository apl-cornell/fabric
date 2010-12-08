package fabric.util;


public interface Collections extends fabric.lang.Object {
    
    public void jif$invokeDefConstructor();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections
    {
        
        native public static fabric.util.Set EMPTY_SET(
          fabric.lang.security.Label arg1);
        
        native public static fabric.util.Iterator EMPTY_ITERATOR(
          fabric.lang.security.Label arg1);
        
        native public static fabric.util.Collection unmodifiableCollection(
          fabric.lang.security.Label arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public static fabric.util.Set unmodifiableSet(
          fabric.lang.security.Label arg1, fabric.util.Set arg2)
              throws java.lang.NullPointerException;
        
        native public void jif$invokeDefConstructor();
        
        public _Proxy(Collections._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections
    {
        
        native private fabric.util.Collections fabric$util$Collections$();
        
        native public static fabric.util.Set EMPTY_SET(
          final fabric.lang.security.Label lbl);
        
        native public static fabric.util.Iterator EMPTY_ITERATOR(
          final fabric.lang.security.Label lbl);
        
        native public static fabric.util.Collection unmodifiableCollection(
          final fabric.lang.security.Label lbl, final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public static fabric.util.Set unmodifiableSet(
          final fabric.lang.security.Label lbl, final fabric.util.Set s)
              throws java.lang.NullPointerException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Collections._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections._Static
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

interface Collections$EmptySet extends fabric.util.AbstractSet {
    
    public fabric.util.Collections$EmptySet fabric$util$Collections$EmptySet$();
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.Iterator iterator();
    
    public fabric.util.Iterator iterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public boolean contains(final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public boolean containsAll(final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    public boolean containsAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    public boolean equals(final fabric.lang.JifObject o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean removeAll(final fabric.util.Collection c);
    
    public boolean removeAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c);
    
    public boolean retainAll(final fabric.util.Collection c);
    
    public boolean retainAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c);
    
    public boolean retainAll(final fabric.lang.security.Label lbl,
                             final fabric.util.Collection c);
    
    public boolean retainAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.util.Collection c);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label
      get$jif$fabric_util_Collections$EmptySet_L();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.Collections$EmptySet
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$EmptySet_L();
        
        native public fabric.util.Collections$EmptySet
          fabric$util$Collections$EmptySet$();
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean containsAll(fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public boolean equals(fabric.lang.JifObject arg1);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.JifObject arg2);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean remove_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.JifObject arg2);
        
        native public boolean remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public boolean retainAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean retainAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean retainAll(fabric.lang.security.Label arg1,
                                        fabric.util.Collection arg2);
        
        native public boolean retainAll_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.util.Collection arg3);
        
        native public boolean retainAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.util.Collection arg3);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collections$EmptySet
          jif$cast$fabric_util_Collections$EmptySet(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(Collections$EmptySet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.Collections$EmptySet
    {
        
        native public fabric.util.Collections$EmptySet
          fabric$util$Collections$EmptySet$();
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean contains(final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public boolean containsAll(final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public boolean equals(final fabric.lang.JifObject o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean removeAll(final fabric.util.Collection c);
        
        native public boolean removeAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c);
        
        native public boolean retainAll(final fabric.util.Collection c);
        
        native public boolean retainAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c);
        
        native public boolean retainAll(final fabric.lang.security.Label lbl,
                                        final fabric.util.Collection c);
        
        native public boolean retainAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.util.Collection c);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collections$EmptySet
          jif$cast$fabric_util_Collections$EmptySet(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$EmptySet_L();
        
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
          implements fabric.util.Collections$EmptySet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections$EmptySet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections$EmptySet._Static
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

interface Collections$EmptyIterator
  extends fabric.util.Iterator, fabric.lang.Object
{
    
    public boolean hasNext();
    
    public boolean hasNext_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject next()
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.JifObject next_remote(
      final fabric.lang.security.Principal worker$principal)
          throws fabric.util.NoSuchElementException;
    
    public void remove() throws java.lang.IllegalStateException;
    
    public void remove_remote(
      final fabric.lang.security.Principal worker$principal)
          throws java.lang.IllegalStateException;
    
    public fabric.util.Collections$EmptyIterator
      fabric$util$Collections$EmptyIterator$();
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label
      get$jif$fabric_util_Collections$EmptyIterator_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_Iterator_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections$EmptyIterator
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$EmptyIterator_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Iterator_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
          fabric.lang.security.Label val);
        
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
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public void remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public fabric.util.Collections$EmptyIterator
          fabric$util$Collections$EmptyIterator$();
        
        native public void jif$invokeDefConstructor();
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collections$EmptyIterator
          jif$cast$fabric_util_Collections$EmptyIterator(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Iterator_L();
        
        public _Proxy(Collections$EmptyIterator._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections$EmptyIterator
    {
        
        native public boolean hasNext();
        
        native public boolean hasNext_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          final fabric.lang.security.Principal worker$principal)
              throws fabric.util.NoSuchElementException;
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(
          final fabric.lang.security.Principal worker$principal)
              throws java.lang.IllegalStateException;
        
        native public fabric.util.Collections$EmptyIterator
          fabric$util$Collections$EmptyIterator$();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collections$EmptyIterator
          jif$cast$fabric_util_Collections$EmptyIterator(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$EmptyIterator_L();
        
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
          implements fabric.util.Collections$EmptyIterator._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections$EmptyIterator._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections$EmptyIterator._Static
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

interface Collections$UnmodifiableCollection
  extends fabric.util.Collection, fabric.lang.Object
{
    
    public fabric.util.Collection get$c();
    
    public fabric.util.Collection set$c(fabric.util.Collection val);
    
    public fabric.util.Collections$UnmodifiableCollection
      fabric$util$Collections$UnmodifiableCollection$(
      final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    public boolean add(final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean add_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean addAll(final fabric.util.Collection c)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean addAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public void clear();
    
    public void clear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean contains(final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public boolean containsAll(final fabric.util.Collection c1)
          throws java.lang.NullPointerException;
    
    public boolean containsAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c1)
          throws java.lang.NullPointerException;
    
    public boolean isEmpty();
    
    public boolean isEmpty_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.Iterator iterator();
    
    public fabric.util.Iterator iterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean removeAll(final fabric.util.Collection c);
    
    public boolean removeAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c);
    
    public boolean retainAll(final fabric.util.Collection c);
    
    public boolean retainAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c);
    
    public boolean retainAll(final fabric.lang.security.Label lbl,
                             final fabric.util.Collection c);
    
    public boolean retainAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.util.Collection c);
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public boolean add(final java.lang.String o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean add_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean remove(final java.lang.String o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String o);
    
    public boolean contains(final java.lang.String o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String o);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final java.lang.String o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final java.lang.String o);
    
    public boolean equals(final fabric.lang.IDComparable other);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable other);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable obj);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable obj);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.security.Label
      get$jif$fabric_util_Collections$UnmodifiableCollection_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_Collection_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Collection_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Collection_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifObject_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_JifObject_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_JifObject_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_IDComparable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_Hashable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_ToStringable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_ToStringable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_ToStringable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections$UnmodifiableCollection
    {
        
        native public fabric.util.Collection get$c();
        
        native public fabric.util.Collection set$c(fabric.util.Collection val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableCollection_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collection_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_util_Collection_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        native public fabric.util.Collections$UnmodifiableCollection
          fabric$util$Collections$UnmodifiableCollection$(
          fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(fabric.lang.security.Principal arg1,
                                         fabric.lang.JifObject arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll(fabric.util.Collection arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll_remote(fabric.lang.security.Principal arg1,
                                            fabric.util.Collection arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public void clear();
        
        native public void clear_remote(fabric.lang.security.Principal arg1);
        
        native public void clear$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean containsAll(fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public boolean isEmpty();
        
        native public boolean isEmpty_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean isEmpty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean remove_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.JifObject arg2);
        
        native public boolean remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean removeAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean removeAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public boolean retainAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean retainAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2);
        
        native public boolean retainAll(fabric.lang.security.Label arg1,
                                        fabric.util.Collection arg2);
        
        native public boolean retainAll_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.util.Collection arg3);
        
        native public boolean retainAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.util.Collection arg3);
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean add(java.lang.String arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(fabric.lang.security.Principal arg1,
                                         java.lang.String arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(java.lang.String arg1);
        
        native public boolean remove_remote(fabric.lang.security.Principal arg1,
                                            java.lang.String arg2);
        
        native public boolean remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean contains(java.lang.String arg1);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       java.lang.String arg2);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          java.lang.String arg3);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          java.lang.String arg3);
        
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
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collections$UnmodifiableCollection
          jif$cast$fabric_util_Collections$UnmodifiableCollection(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Collection_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(Collections$UnmodifiableCollection._Impl impl) {
            super(impl);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections$UnmodifiableCollection
    {
        
        native public fabric.util.Collection get$c();
        
        native public fabric.util.Collection set$c(fabric.util.Collection val);
        
        native public fabric.util.Collections$UnmodifiableCollection
          fabric$util$Collections$UnmodifiableCollection$(
          final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public boolean add(final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll(final fabric.util.Collection c)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public void clear();
        
        native public void clear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean contains(final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public boolean containsAll(final fabric.util.Collection c1)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c1)
              throws java.lang.NullPointerException;
        
        native public boolean isEmpty();
        
        native public boolean isEmpty_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean removeAll(final fabric.util.Collection c);
        
        native public boolean removeAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c);
        
        native public boolean retainAll(final fabric.util.Collection c);
        
        native public boolean retainAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c);
        
        native public boolean retainAll(final fabric.lang.security.Label lbl,
                                        final fabric.util.Collection c);
        
        native public boolean retainAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.util.Collection c);
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean add(final java.lang.String o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(final java.lang.String o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String o);
        
        native public boolean contains(final java.lang.String o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String o);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final java.lang.String o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final java.lang.String o);
        
        native public boolean equals(final fabric.lang.IDComparable other);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable other);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable obj);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable obj);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collections$UnmodifiableCollection
          jif$cast$fabric_util_Collections$UnmodifiableCollection(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableCollection_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collection_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_util_Collection_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Collection_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
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
          implements fabric.util.Collections$UnmodifiableCollection._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections$UnmodifiableCollection.
                            _Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections$UnmodifiableCollection._Static
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

interface Collections$UnmodifiableIterator
  extends fabric.util.Iterator, fabric.lang.Object
{
    
    public fabric.util.Iterator get$i();
    
    public fabric.util.Iterator set$i(fabric.util.Iterator val);
    
    public fabric.util.Collections$UnmodifiableIterator
      fabric$util$Collections$UnmodifiableIterator$(
      final fabric.util.Iterator i)
          throws java.lang.NullPointerException;
    
    public fabric.lang.JifObject next()
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.JifObject next_remote(
      final fabric.lang.security.Principal worker$principal)
          throws fabric.util.NoSuchElementException;
    
    public boolean hasNext();
    
    public boolean hasNext_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void remove() throws java.lang.IllegalStateException;
    
    public void remove_remote(
      final fabric.lang.security.Principal worker$principal)
          throws java.lang.IllegalStateException;
    
    public fabric.lang.security.Label
      get$jif$fabric_util_Collections$UnmodifiableIterator_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_Iterator_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections$UnmodifiableIterator
    {
        
        native public fabric.util.Iterator get$i();
        
        native public fabric.util.Iterator set$i(fabric.util.Iterator val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableIterator_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Iterator_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
          fabric.lang.security.Label val);
        
        native public fabric.util.Collections$UnmodifiableIterator
          fabric$util$Collections$UnmodifiableIterator$(
          fabric.util.Iterator arg1)
              throws java.lang.NullPointerException;
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws fabric.util.NoSuchElementException;
        
        native public boolean hasNext();
        
        native public boolean hasNext_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean hasNext$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public void remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws java.lang.IllegalStateException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collections$UnmodifiableIterator
          jif$cast$fabric_util_Collections$UnmodifiableIterator(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Iterator_L();
        
        public _Proxy(Collections$UnmodifiableIterator._Impl impl) {
            super(impl);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections$UnmodifiableIterator
    {
        
        native public fabric.util.Iterator get$i();
        
        native public fabric.util.Iterator set$i(fabric.util.Iterator val);
        
        native public fabric.util.Collections$UnmodifiableIterator
          fabric$util$Collections$UnmodifiableIterator$(
          final fabric.util.Iterator i)
              throws java.lang.NullPointerException;
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          final fabric.lang.security.Principal worker$principal)
              throws fabric.util.NoSuchElementException;
        
        native public boolean hasNext();
        
        native public boolean hasNext_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public void remove_remote(
          final fabric.lang.security.Principal worker$principal)
              throws java.lang.IllegalStateException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collections$UnmodifiableIterator
          jif$cast$fabric_util_Collections$UnmodifiableIterator(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableIterator_L();
        
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
          implements fabric.util.Collections$UnmodifiableIterator._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections$UnmodifiableIterator._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections$UnmodifiableIterator._Static
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

interface Collections$UnmodifiableSet
  extends fabric.util.Set, fabric.util.Collections$UnmodifiableCollection
{
    
    public fabric.util.Collections$UnmodifiableSet
      fabric$util$Collections$UnmodifiableSet$(final fabric.util.Set s)
          throws java.lang.NullPointerException;
    
    public fabric.lang.security.Label
      get$jif$fabric_util_Collections$UnmodifiableSet_L();
    
    public fabric.lang.security.Label get$jif$fabric_util_Set_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Set_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Set_L();
    
    public static class _Proxy
    extends fabric.util.Collections$UnmodifiableCollection._Proxy
      implements fabric.util.Collections$UnmodifiableSet
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableSet_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Set_L();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Set_L(
          fabric.lang.security.Label val);
        
        native public fabric.util.Collections$UnmodifiableSet
          fabric$util$Collections$UnmodifiableSet$(fabric.util.Set arg1)
              throws java.lang.NullPointerException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collections$UnmodifiableSet
          jif$cast$fabric_util_Collections$UnmodifiableSet(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label jif$getfabric_util_Set_L(
          );
        
        public _Proxy(Collections$UnmodifiableSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.util.Collections$UnmodifiableCollection._Impl
      implements fabric.util.Collections$UnmodifiableSet
    {
        
        native public fabric.util.Collections$UnmodifiableSet
          fabric$util$Collections$UnmodifiableSet$(final fabric.util.Set s)
              throws java.lang.NullPointerException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collections$UnmodifiableSet
          jif$cast$fabric_util_Collections$UnmodifiableSet(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Collections$UnmodifiableSet_L();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Set_L();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Set_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label jif$getfabric_util_Set_L(
          );
        
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
          implements fabric.util.Collections$UnmodifiableSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Collections$UnmodifiableSet._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections$UnmodifiableSet._Static
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
