package fabric.util;


public interface AbstractCollection
  extends fabric.util.Collection, fabric.lang.Object
{
    
    public fabric.util.AbstractCollection fabric$util$AbstractCollection$();
    
    abstract public fabric.util.Iterator iterator();
    
    abstract public int size();
    
    public boolean isEmpty();
    
    public boolean isEmpty_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean contains(final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean add(final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean add_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
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
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public boolean containsAll(final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    public boolean containsAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    public boolean addAll(final fabric.util.Collection c)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    public boolean addAll_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Collection c)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
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
    
    public void clear();
    
    public void clear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_AbstractCollection_L(
      );
    
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
      implements fabric.util.AbstractCollection
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractCollection_L();
        
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
        
        native public fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        native public fabric.util.Iterator iterator();
        
        native public int size();
        
        final native public boolean isEmpty();
        
        final native public boolean isEmpty_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean isEmpty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean contains$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
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
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean remove_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.JifObject arg2);
        
        native public boolean remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean containsAll(fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Collection arg2)
              throws java.lang.NullPointerException;
        
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
        
        native public void clear();
        
        native public void clear_remote(fabric.lang.security.Principal arg1);
        
        native public void clear$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public void jif$invokeDefConstructor();
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.AbstractCollection
          jif$cast$fabric_util_AbstractCollection(
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
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public int hashCode();
        
        public _Proxy(AbstractCollection._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractCollection
    {
        
        native public fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        abstract public fabric.util.Iterator iterator();
        
        abstract public int size();
        
        final native public boolean isEmpty();
        
        final native public boolean isEmpty_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean contains(final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean add(final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean add_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
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
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public boolean containsAll(final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public boolean containsAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c)
              throws java.lang.NullPointerException;
        
        native public boolean addAll(final fabric.util.Collection c)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean addAll_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Collection c)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
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
        
        native public void clear();
        
        native public void clear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.AbstractCollection
          jif$cast$fabric_util_AbstractCollection(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractCollection_L();
        
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
          implements fabric.util.AbstractCollection._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.AbstractCollection._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractCollection._Static
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
