package fabric.util;


public interface LinkedList extends fabric.util.AbstractList {
    
    public fabric.util.LinkedListEntry get$head();
    
    public fabric.util.LinkedListEntry set$head(
      fabric.util.LinkedListEntry val);
    
    public fabric.util.LinkedListEntry get$tail();
    
    public fabric.util.LinkedListEntry set$tail(
      fabric.util.LinkedListEntry val);
    
    public fabric.util.LinkedList fabric$util$LinkedList$();
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean add(final fabric.lang.JifObject o);
    
    public boolean add_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public fabric.lang.JifObject remove(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject remove_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject getFirst()
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject getFirst_remote(
      final fabric.lang.security.Principal worker$principal)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject getLast()
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject getLast_remote(
      final fabric.lang.security.Principal worker$principal)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject set(final int index,
                                     final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject set_remote(
      final fabric.lang.security.Principal worker$principal, final int index,
      final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public void add(final int index, final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public void add_remote(
      final fabric.lang.security.Principal worker$principal, final int index,
      final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public int indexOf(final fabric.lang.JifObject o);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int lastIndexOf(final fabric.lang.JifObject o);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_LinkedList_L();
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.LinkedList
    {
        
        native public fabric.util.LinkedListEntry get$head();
        
        native public fabric.util.LinkedListEntry set$head(
          fabric.util.LinkedListEntry val);
        
        native public fabric.util.LinkedListEntry get$tail();
        
        native public fabric.util.LinkedListEntry set$tail(
          fabric.util.LinkedListEntry val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedList_L();
        
        native public fabric.util.LinkedList fabric$util$LinkedList$();
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean add(fabric.lang.JifObject arg1);
        
        native public boolean add_remote(fabric.lang.security.Principal arg1,
                                         fabric.lang.JifObject arg2);
        
        native public boolean add$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getFirst()
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getFirst_remote(
          fabric.lang.security.Principal arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getFirst$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getLast()
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getLast_remote(
          fabric.lang.security.Principal arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getLast$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set(int arg1,
                                                fabric.lang.JifObject arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set_remote(
          fabric.lang.security.Principal arg1, int arg2,
          fabric.lang.JifObject arg3)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2,
          fabric.lang.JifObject arg3)
              throws java.lang.IndexOutOfBoundsException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.LinkedList
          jif$cast$fabric_util_LinkedList(fabric.lang.security.Label arg1,
                                          java.lang.Object arg2);
        
        public _Proxy(LinkedList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.LinkedList
    {
        
        native public fabric.util.LinkedListEntry get$head();
        
        native public fabric.util.LinkedListEntry set$head(
          fabric.util.LinkedListEntry val);
        
        native public fabric.util.LinkedListEntry get$tail();
        
        native public fabric.util.LinkedListEntry set$tail(
          fabric.util.LinkedListEntry val);
        
        native public fabric.util.LinkedList fabric$util$LinkedList$();
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean add(final fabric.lang.JifObject o);
        
        native public boolean add_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public fabric.lang.JifObject remove(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getFirst()
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getFirst_remote(
          final fabric.lang.security.Principal worker$principal)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getLast()
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject getLast_remote(
          final fabric.lang.security.Principal worker$principal)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set(
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add(final int index,
                               final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int indexOf(final fabric.lang.JifObject o);
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public int lastIndexOf(final fabric.lang.JifObject o);
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.LinkedList
          jif$cast$fabric_util_LinkedList(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedList_L();
        
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
          implements fabric.util.LinkedList._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.LinkedList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList._Static
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

interface LinkedListEntry extends fabric.lang.Object {
    
    public fabric.util.LinkedListEntry fabric$util$LinkedListEntry$();
    
    public fabric.util.LinkedListEntry get$next();
    
    public fabric.util.LinkedListEntry set$next(
      fabric.util.LinkedListEntry val);
    
    public fabric.util.LinkedListEntry get$prev();
    
    public fabric.util.LinkedListEntry set$prev(
      fabric.util.LinkedListEntry val);
    
    public fabric.lang.JifObject get$data();
    
    public fabric.lang.JifObject set$data(fabric.lang.JifObject val);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_LinkedListEntry_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.LinkedListEntry
    {
        
        native public fabric.util.LinkedListEntry get$next();
        
        native public fabric.util.LinkedListEntry set$next(
          fabric.util.LinkedListEntry val);
        
        native public fabric.util.LinkedListEntry get$prev();
        
        native public fabric.util.LinkedListEntry set$prev(
          fabric.util.LinkedListEntry val);
        
        native public fabric.lang.JifObject get$data();
        
        native public fabric.lang.JifObject set$data(fabric.lang.JifObject val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedListEntry_L();
        
        native public fabric.util.LinkedListEntry fabric$util$LinkedListEntry$(
          );
        
        native public void jif$invokeDefConstructor();
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.LinkedListEntry
          jif$cast$fabric_util_LinkedListEntry(fabric.lang.security.Label arg1,
                                               java.lang.Object arg2);
        
        public _Proxy(LinkedListEntry._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.LinkedListEntry
    {
        
        native public fabric.util.LinkedListEntry fabric$util$LinkedListEntry$(
          );
        
        native public fabric.util.LinkedListEntry get$next();
        
        native public fabric.util.LinkedListEntry set$next(
          fabric.util.LinkedListEntry val);
        
        native public fabric.util.LinkedListEntry get$prev();
        
        native public fabric.util.LinkedListEntry set$prev(
          fabric.util.LinkedListEntry val);
        
        native public fabric.lang.JifObject get$data();
        
        native public fabric.lang.JifObject set$data(fabric.lang.JifObject val);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.LinkedListEntry
          jif$cast$fabric_util_LinkedListEntry(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedListEntry_L();
        
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
          implements fabric.util.LinkedListEntry._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.LinkedListEntry._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedListEntry._Static
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
