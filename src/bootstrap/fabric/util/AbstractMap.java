package fabric.util;


public interface AbstractMap extends fabric.util.Map, fabric.lang.Object {
    
    public fabric.util.AbstractMap fabric$util$AbstractMap$();
    
    public boolean isEmpty();
    
    public boolean isEmpty_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean containsKey(final fabric.lang.JifObject key);
    
    public boolean containsKey_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject key);
    
    public fabric.lang.JifObject get(final fabric.lang.JifObject key);
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject key);
    
    abstract public fabric.lang.JifObject put(
      final fabric.lang.JifObject key, final fabric.lang.JifObject value);
    
    abstract public fabric.lang.JifObject remove(
      final fabric.lang.JifObject key);
    
    public boolean containsKey(final java.lang.String key);
    
    public boolean containsKey_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String key);
    
    public fabric.lang.JifObject get(final java.lang.String key);
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String key);
    
    public fabric.lang.JifObject get(final fabric.lang.security.Label lbl,
                                     final java.lang.String key);
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final java.lang.String key);
    
    public fabric.lang.JifObject put(final java.lang.String key,
                                     final fabric.lang.JifObject value);
    
    public fabric.lang.JifObject put_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String key, final fabric.lang.JifObject value);
    
    public fabric.lang.JifObject remove(final java.lang.String key);
    
    public fabric.lang.JifObject remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String key);
    
    public boolean equals(final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable o);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable o);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_AbstractMap_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_AbstractMap_V();
    
    public fabric.lang.security.Label get$jif$fabric_util_Map_K();
    
    public fabric.lang.security.Label set$jif$fabric_util_Map_K(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Map_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_Map_V();
    
    public fabric.lang.security.Label set$jif$fabric_util_Map_V(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Map_V();
    
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
      implements fabric.util.AbstractMap
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractMap_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractMap_V();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Map_K();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Map_K(
          fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_util_Map_V();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Map_V(
          fabric.lang.security.Label val);
        
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
        
        native public fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        native public boolean isEmpty();
        
        native public boolean isEmpty_remote(
          fabric.lang.security.Principal arg1);
        
        native public boolean isEmpty$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean containsKey(fabric.lang.JifObject arg1);
        
        native public boolean containsKey_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public boolean containsKey$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get(fabric.lang.JifObject arg1);
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject put(fabric.lang.JifObject arg1,
                                                fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject remove(fabric.lang.JifObject arg1);
        
        final native public boolean containsKey(java.lang.String arg1);
        
        final native public boolean containsKey_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean containsKey$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        final native public fabric.lang.JifObject get(java.lang.String arg1);
        
        final native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        final native public fabric.lang.JifObject get(
          fabric.lang.security.Label arg1, java.lang.String arg2);
        
        final native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          java.lang.String arg3);
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          java.lang.String arg3);
        
        final native public fabric.lang.JifObject put(
          java.lang.String arg1, fabric.lang.JifObject arg2);
        
        final native public fabric.lang.JifObject put_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2,
          fabric.lang.JifObject arg3);
        
        native public fabric.lang.JifObject put$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2,
          fabric.lang.JifObject arg3);
        
        final native public fabric.lang.JifObject remove(java.lang.String arg1);
        
        final native public fabric.lang.JifObject remove_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public fabric.lang.JifObject remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
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
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
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
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.AbstractMap
          jif$cast$fabric_util_AbstractMap(fabric.lang.security.Label arg1,
                                           fabric.lang.security.Label arg2,
                                           java.lang.Object arg3);
        
        final native public fabric.lang.security.Label jif$getfabric_util_Map_K(
          );
        
        final native public fabric.lang.security.Label jif$getfabric_util_Map_V(
          );
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        native public int size();
        
        native public boolean containsKey(fabric.lang.security.Label arg1,
                                          fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get(fabric.lang.security.Label arg1,
                                                fabric.lang.JifObject arg2);
        
        native public void clear();
        
        native public fabric.util.Set entrySet();
        
        public _Proxy(AbstractMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractMap
    {
        
        native public fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        native public boolean isEmpty();
        
        native public boolean isEmpty_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean containsKey(final fabric.lang.JifObject key);
        
        native public boolean containsKey_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject key);
        
        native public fabric.lang.JifObject get(
          final fabric.lang.JifObject key);
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject key);
        
        abstract public fabric.lang.JifObject put(
          final fabric.lang.JifObject key, final fabric.lang.JifObject value);
        
        abstract public fabric.lang.JifObject remove(
          final fabric.lang.JifObject key);
        
        final native public boolean containsKey(final java.lang.String key);
        
        final native public boolean containsKey_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String key);
        
        final native public fabric.lang.JifObject get(
          final java.lang.String key);
        
        final native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String key);
        
        final native public fabric.lang.JifObject get(
          final fabric.lang.security.Label lbl, final java.lang.String key);
        
        final native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final java.lang.String key);
        
        final native public fabric.lang.JifObject put(
          final java.lang.String key, final fabric.lang.JifObject value);
        
        final native public fabric.lang.JifObject put_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String key, final fabric.lang.JifObject value);
        
        final native public fabric.lang.JifObject remove(
          final java.lang.String key);
        
        final native public fabric.lang.JifObject remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String key);
        
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
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$K,
                     final fabric.lang.security.Label jif$V) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.AbstractMap
          jif$cast$fabric_util_AbstractMap(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractMap_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_AbstractMap_V();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Map_K();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Map_K(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label jif$getfabric_util_Map_K(
          );
        
        native public fabric.lang.security.Label get$jif$fabric_util_Map_V();
        
        native public fabric.lang.security.Label set$jif$fabric_util_Map_V(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label jif$getfabric_util_Map_V(
          );
        
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
          implements fabric.util.AbstractMap._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.AbstractMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractMap._Static
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
