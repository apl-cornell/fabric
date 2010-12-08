package fabric.util;


public interface Arrays extends fabric.lang.Object {
    
    public void jif$invokeDefConstructor();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Arrays
    {
        
        native public static fabric.util.List asList(
          fabric.lang.security.Label arg1, fabric.lang.arrays.ObjectArray arg2)
              throws java.lang.NullPointerException;
        
        native public static fabric.util.List asList(
          fabric.lang.security.Label arg1, fabric.lang.JifObject[] arg2)
              throws java.lang.NullPointerException;
        
        native public void jif$invokeDefConstructor();
        
        public _Proxy(Arrays._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Arrays
    {
        
        native private fabric.util.Arrays fabric$util$Arrays$();
        
        native public static fabric.util.List asList(
          final fabric.lang.security.Label lbl,
          final fabric.lang.arrays.ObjectArray a)
              throws java.lang.NullPointerException;
        
        native public static fabric.util.List asList(
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject[] a)
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
          implements fabric.util.Arrays._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Arrays._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Arrays._Static
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

interface Arrays$ArrayList extends fabric.util.AbstractList {
    
    public fabric.lang.JifObject[] get$a();
    
    public fabric.lang.JifObject[] set$a(fabric.lang.JifObject[] val);
    
    public fabric.util.Arrays$ArrayList fabric$util$Arrays$ArrayList$(
      final fabric.lang.JifObject[] a)
          throws java.lang.NullPointerException;
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject set(final int index,
                                     final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject set_remote(
      final fabric.lang.security.Principal worker$principal, final int index,
      final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    public boolean contains(final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int indexOf(final fabric.lang.JifObject o);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public int lastIndexOf(final fabric.lang.JifObject o);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public fabric.lang.security.Label get$jif$fabric_util_Arrays$ArrayList_L();
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.Arrays$ArrayList
    {
        
        native public fabric.lang.JifObject[] get$a();
        
        native public fabric.lang.JifObject[] set$a(
          fabric.lang.JifObject[] val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Arrays$ArrayList_L();
        
        native public fabric.util.Arrays$ArrayList
          fabric$util$Arrays$ArrayList$(fabric.lang.JifObject[] arg1)
              throws java.lang.NullPointerException;
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
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
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Arrays$ArrayList
          jif$cast$fabric_util_Arrays$ArrayList(fabric.lang.security.Label arg1,
                                                java.lang.Object arg2);
        
        public _Proxy(Arrays$ArrayList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.Arrays$ArrayList
    {
        
        native public fabric.lang.JifObject[] get$a();
        
        native public fabric.lang.JifObject[] set$a(
          fabric.lang.JifObject[] val);
        
        native public fabric.util.Arrays$ArrayList
          fabric$util$Arrays$ArrayList$(final fabric.lang.JifObject[] a)
              throws java.lang.NullPointerException;
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject set(
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index, final fabric.lang.JifObject element)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean contains(final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
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
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Arrays$ArrayList
          jif$cast$fabric_util_Arrays$ArrayList(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_Arrays$ArrayList_L();
        
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
          implements fabric.util.Arrays$ArrayList._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.Arrays$ArrayList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Arrays$ArrayList._Static
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
