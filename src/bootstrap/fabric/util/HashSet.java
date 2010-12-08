package fabric.util;


public interface HashSet extends fabric.util.AbstractSet {
    
    public fabric.util.HashMap get$map();
    
    public fabric.util.HashMap set$map(fabric.util.HashMap val);
    
    public fabric.util.HashSet fabric$util$HashSet$(final int initialCapacity,
                                                    final float loadFactor)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.HashSet fabric$util$HashSet$(final int initialCapacity)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.HashSet fabric$util$HashSet$();
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public boolean add(final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean add_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public void clear();
    
    public void clear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable o);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.Iterator iterator();
    
    public fabric.util.Iterator iterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_HashSet_L();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.HashSet
    {
        
        native public fabric.util.HashMap get$map();
        
        native public fabric.util.HashMap set$map(fabric.util.HashMap val);
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashSet_L(
          );
        
        native public fabric.util.HashSet fabric$util$HashSet$(int arg1,
                                                               float arg2)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashSet fabric$util$HashSet$(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashSet fabric$util$HashSet$();
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
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
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean remove_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.JifObject arg2);
        
        native public boolean remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public void clear();
        
        native public void clear_remote(fabric.lang.security.Principal arg1);
        
        native public void clear$remote(
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
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Iterator iterator$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.HashSet jif$cast$fabric_util_HashSet(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(HashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.HashSet
    {
        
        native public fabric.util.HashMap get$map();
        
        native public fabric.util.HashMap set$map(fabric.util.HashMap val);
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          final int initialCapacity, final float loadFactor)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          final int initialCapacity)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashSet fabric$util$HashSet$();
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public boolean add(final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public void clear();
        
        native public void clear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable o);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
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
        
        native public static fabric.util.HashSet jif$cast$fabric_util_HashSet(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashSet_L(
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
          implements fabric.util.HashSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashSet._Static
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

interface HashSetEntryIterator extends fabric.util.HashMapEntrySetIterator {
    
    public fabric.util.HashSetEntryIterator fabric$util$HashSetEntryIterator$(
      final fabric.util.HashMap map);
    
    public fabric.lang.JifObject next()
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.JifObject next_remote(
      final fabric.lang.security.Principal worker$principal)
          throws fabric.util.NoSuchElementException;
    
    public fabric.lang.security.Label
      get$jif$fabric_util_HashSetEntryIterator_L();
    
    public static class _Proxy
    extends fabric.util.HashMapEntrySetIterator._Proxy
      implements fabric.util.HashSetEntryIterator
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashSetEntryIterator_L();
        
        native public fabric.util.HashSetEntryIterator
          fabric$util$HashSetEntryIterator$(fabric.util.HashMap arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.HashSetEntryIterator
          jif$cast$fabric_util_HashSetEntryIterator(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(HashSetEntryIterator._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashMapEntrySetIterator._Impl
      implements fabric.util.HashSetEntryIterator
    {
        
        native public fabric.util.HashSetEntryIterator
          fabric$util$HashSetEntryIterator$(final fabric.util.HashMap map);
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public fabric.lang.JifObject next_remote(
          final fabric.lang.security.Principal worker$principal)
              throws fabric.util.NoSuchElementException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L, jif$L);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.HashSetEntryIterator
          jif$cast$fabric_util_HashSetEntryIterator(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashSetEntryIterator_L();
        
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
          implements fabric.util.HashSetEntryIterator._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashSetEntryIterator._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashSetEntryIterator._Static
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
