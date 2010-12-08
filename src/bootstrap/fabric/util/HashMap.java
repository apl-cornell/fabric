package fabric.util;


interface HMConsts extends fabric.lang.Object {
    
    public fabric.util.HMConsts fabric$util$HMConsts$();
    
    public void jif$invokeDefConstructor();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.HMConsts
    {
        
        native public fabric.util.HMConsts fabric$util$HMConsts$();
        
        native public void jif$invokeDefConstructor();
        
        public _Proxy(HMConsts._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.HMConsts
    {
        
        native public fabric.util.HMConsts fabric$util$HMConsts$();
        
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
        
        public int get$DEFAULT_INITIAL_CAPACITY();
        
        public int get$MAXIMUM_CAPACITY();
        
        public float get$DEFAULT_LOAD_FACTOR();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HMConsts._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public int get$DEFAULT_INITIAL_CAPACITY();
            
            native public int get$MAXIMUM_CAPACITY();
            
            native public float get$DEFAULT_LOAD_FACTOR();
            
            public _Proxy(fabric.util.HMConsts._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HMConsts._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public int get$DEFAULT_INITIAL_CAPACITY();
            
            native public int get$MAXIMUM_CAPACITY();
            
            native public float get$DEFAULT_LOAD_FACTOR();
            
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

public interface HashMap extends fabric.util.AbstractMap {
    
    public fabric.lang.arrays.ObjectArray get$table();
    
    public fabric.lang.arrays.ObjectArray set$table(
      fabric.lang.arrays.ObjectArray val);
    
    public fabric.util.HashMapEntry get$header();
    
    public fabric.util.HashMapEntry set$header(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntrySet get$entrySet();
    
    public fabric.util.HashMapEntrySet set$entrySet(
      fabric.util.HashMapEntrySet val);
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public int get$threshold();
    
    public int set$threshold(int val);
    
    public int postInc$threshold();
    
    public int postDec$threshold();
    
    public float get$loadFactor();
    
    public float set$loadFactor(float val);
    
    public float postInc$loadFactor();
    
    public float postDec$loadFactor();
    
    public fabric.util.HashMap fabric$util$HashMap$(final int initialCapacity,
                                                    final float loadFactor)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.HashMap fabric$util$HashMap$(final int initialCapacity)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.HashMap fabric$util$HashMap$();
    
    public void init();
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean isEmpty();
    
    public boolean isEmpty_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final fabric.lang.security.Label lbl,
                                     final fabric.lang.JifObject key);
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject key);
    
    public boolean containsKey(final fabric.lang.security.Label lbl,
                               final fabric.lang.JifObject key);
    
    public boolean containsKey_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject key);
    
    public fabric.util.HashMapEntry getEntry(
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject key);
    
    public fabric.lang.JifObject put(final fabric.lang.JifObject key,
                                     final fabric.lang.JifObject value);
    
    public fabric.lang.JifObject put_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject key, final fabric.lang.JifObject value);
    
    public void resize(final int newCapacity);
    
    public void transfer(final fabric.lang.arrays.ObjectArray newTable);
    
    public fabric.lang.JifObject remove(final fabric.lang.JifObject key);
    
    public fabric.lang.JifObject remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject key);
    
    public fabric.util.HashMapEntry removeEntryForKey(
      final fabric.lang.JifObject key);
    
    public fabric.util.HashMapEntry removeMapping(
      final fabric.lang.JifObject o);
    
    public void clear();
    
    public void clear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void addEntry(final int hash, final fabric.lang.JifObject key,
                         final fabric.lang.JifObject value,
                         final int bucketIndex);
    
    public void createEntry(final int hash, final fabric.lang.JifObject key,
                            final fabric.lang.JifObject value,
                            final int bucketIndex);
    
    public fabric.util.Set entrySet();
    
    public fabric.util.Set entrySet_remote(
      final fabric.lang.security.Principal worker$principal);
    
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
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMap_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMap_V();
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.HashMap
    {
        
        native public fabric.lang.arrays.ObjectArray get$table();
        
        native public fabric.lang.arrays.ObjectArray set$table(
          fabric.lang.arrays.ObjectArray val);
        
        native public fabric.util.HashMapEntry get$header();
        
        native public fabric.util.HashMapEntry set$header(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntrySet get$entrySet();
        
        native public fabric.util.HashMapEntrySet set$entrySet(
          fabric.util.HashMapEntrySet val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public int get$threshold();
        
        native public int set$threshold(int val);
        
        native public int postInc$threshold();
        
        native public int postDec$threshold();
        
        native public float get$loadFactor();
        
        native public float set$loadFactor(float val);
        
        native public float postInc$loadFactor();
        
        native public float postDec$loadFactor();
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashMap_K(
          );
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashMap_V(
          );
        
        native public fabric.util.HashMap fabric$util$HashMap$(int arg1,
                                                               float arg2)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashMap fabric$util$HashMap$(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashMap fabric$util$HashMap$();
        
        native public void init();
        
        native public static int hash(fabric.lang.security.Label arg1,
                                      fabric.lang.security.Label arg2,
                                      fabric.lang.security.Label arg3,
                                      fabric.lang.Hashable arg4);
        
        native public static boolean eq(fabric.lang.security.Label arg1,
                                        fabric.lang.security.Label arg2,
                                        fabric.lang.security.Label arg3,
                                        fabric.lang.IDComparable arg4,
                                        fabric.lang.security.Label arg5,
                                        fabric.lang.IDComparable arg6);
        
        native public static int indexFor(fabric.lang.security.Label arg1,
                                          fabric.lang.security.Label arg2,
                                          int arg3, int arg4);
        
        native public static int indexFor(fabric.lang.security.Label arg1,
                                          fabric.lang.security.Label arg2,
                                          int arg3,
                                          fabric.lang.arrays.ObjectArray arg4);
        
        native public int size();
        
        native public int size_remote(fabric.lang.security.Principal arg1);
        
        native public int size$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject get(fabric.lang.security.Label arg1,
                                                fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public fabric.lang.JifObject get$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean containsKey(fabric.lang.security.Label arg1,
                                          fabric.lang.JifObject arg2);
        
        native public boolean containsKey_remote(
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public boolean containsKey$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.JifObject arg3);
        
        native public fabric.util.HashMapEntry getEntry(
          fabric.lang.security.Label arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject put_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2,
          fabric.lang.JifObject arg3);
        
        native public fabric.lang.JifObject put$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2,
          fabric.lang.JifObject arg3);
        
        native public void resize(int arg1);
        
        native public void transfer(fabric.lang.arrays.ObjectArray arg1);
        
        native public fabric.lang.JifObject remove_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject remove$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.util.HashMapEntry removeEntryForKey(
          fabric.lang.JifObject arg1);
        
        native public fabric.util.HashMapEntry removeMapping(
          fabric.lang.JifObject arg1);
        
        native public void clear();
        
        native public void clear_remote(fabric.lang.security.Principal arg1);
        
        native public void clear$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public void addEntry(int arg1, fabric.lang.JifObject arg2,
                                    fabric.lang.JifObject arg3, int arg4);
        
        native public void createEntry(int arg1, fabric.lang.JifObject arg2,
                                       fabric.lang.JifObject arg3, int arg4);
        
        native public fabric.util.Set entrySet();
        
        native public fabric.util.Set entrySet_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Set entrySet$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.HashMap jif$cast$fabric_util_HashMap(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        public _Proxy(HashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.HashMap
    {
        
        native public fabric.lang.arrays.ObjectArray get$table();
        
        native public fabric.lang.arrays.ObjectArray set$table(
          fabric.lang.arrays.ObjectArray val);
        
        native public fabric.util.HashMapEntry get$header();
        
        native public fabric.util.HashMapEntry set$header(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntrySet get$entrySet();
        
        native public fabric.util.HashMapEntrySet set$entrySet(
          fabric.util.HashMapEntrySet val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public int get$threshold();
        
        native public int set$threshold(int val);
        
        native public int postInc$threshold();
        
        native public int postDec$threshold();
        
        native public float get$loadFactor();
        
        native public float set$loadFactor(float val);
        
        native public float postInc$loadFactor();
        
        native public float postDec$loadFactor();
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          final int initialCapacity, final float loadFactor)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          final int initialCapacity)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.HashMap fabric$util$HashMap$();
        
        native public void init();
        
        native public static int hash(final fabric.lang.security.Label jif$K,
                                      final fabric.lang.security.Label jif$V,
                                      final fabric.lang.security.Label lbl,
                                      final fabric.lang.Hashable x);
        
        native public static boolean eq(final fabric.lang.security.Label jif$K,
                                        final fabric.lang.security.Label jif$V,
                                        final fabric.lang.security.Label lbx,
                                        final fabric.lang.IDComparable x,
                                        final fabric.lang.security.Label lby,
                                        final fabric.lang.IDComparable y);
        
        native public static int indexFor(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final int h,
          final int length);
        
        native public static int indexFor(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final int h,
          final fabric.lang.arrays.ObjectArray table);
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean isEmpty();
        
        native public boolean isEmpty_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(
          final fabric.lang.security.Label lbl,
          final fabric.lang.JifObject key);
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.JifObject key);
        
        native public boolean containsKey(final fabric.lang.security.Label lbl,
                                          final fabric.lang.JifObject key);
        
        native public boolean containsKey_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.JifObject key);
        
        native public fabric.util.HashMapEntry getEntry(
          final fabric.lang.security.Label lbl,
          final fabric.lang.JifObject key);
        
        native public fabric.lang.JifObject put(
          final fabric.lang.JifObject key, final fabric.lang.JifObject value);
        
        native public fabric.lang.JifObject put_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject key, final fabric.lang.JifObject value);
        
        native private void putForCreate(final fabric.lang.JifObject key,
                                         final fabric.lang.JifObject value);
        
        native public void resize(final int newCapacity);
        
        native public void transfer(
          final fabric.lang.arrays.ObjectArray newTable);
        
        native public fabric.lang.JifObject remove(
          final fabric.lang.JifObject key);
        
        native public fabric.lang.JifObject remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject key);
        
        native public fabric.util.HashMapEntry removeEntryForKey(
          final fabric.lang.JifObject key);
        
        native public fabric.util.HashMapEntry removeMapping(
          final fabric.lang.JifObject o);
        
        native public void clear();
        
        native public void clear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public void addEntry(final int hash,
                                    final fabric.lang.JifObject key,
                                    final fabric.lang.JifObject value,
                                    final int bucketIndex);
        
        native public void createEntry(final int hash,
                                       final fabric.lang.JifObject key,
                                       final fabric.lang.JifObject value,
                                       final int bucketIndex);
        
        native public fabric.util.Set entrySet();
        
        native public fabric.util.Set entrySet_remote(
          final fabric.lang.security.Principal worker$principal);
        
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
            super($location, $label, jif$K, jif$V);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.HashMap jif$cast$fabric_util_HashMap(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashMap_K(
          );
        
        native public fabric.lang.security.Label get$jif$fabric_util_HashMap_V(
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
          implements fabric.util.HashMap._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMap._Static
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

interface HashMapEntry extends fabric.util.MapEntry, fabric.lang.Object {
    
    public fabric.lang.JifObject get$key();
    
    public fabric.lang.JifObject set$key(fabric.lang.JifObject val);
    
    public fabric.lang.JifObject get$value();
    
    public fabric.lang.JifObject set$value(fabric.lang.JifObject val);
    
    public int get$hash();
    
    public int set$hash(int val);
    
    public int postInc$hash();
    
    public int postDec$hash();
    
    public fabric.util.HashMapEntry get$next();
    
    public fabric.util.HashMapEntry set$next(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntry get$before();
    
    public fabric.util.HashMapEntry set$before(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntry get$after();
    
    public fabric.util.HashMapEntry set$after(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntry fabric$util$HashMapEntry$(
      final int h, final fabric.lang.JifObject k, final fabric.lang.JifObject v,
      final fabric.util.HashMapEntry n);
    
    public fabric.lang.JifObject getKey();
    
    public fabric.lang.JifObject getKey_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject getValue();
    
    public fabric.lang.JifObject getValue_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject setValue(final fabric.lang.JifObject newValue);
    
    public fabric.lang.JifObject setValue_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject newValue);
    
    public boolean equals(final fabric.lang.IDComparable obj);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable obj);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable obj);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable obj);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public void addBefore(final fabric.util.HashMapEntry existingEntry);
    
    public void recordAccess(final fabric.util.HashMap m);
    
    public void recordRemoval(final fabric.util.HashMap m);
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMapEntry_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMapEntry_V();
    
    public fabric.lang.security.Label get$jif$fabric_util_MapEntry_K();
    
    public fabric.lang.security.Label set$jif$fabric_util_MapEntry_K(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_MapEntry_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_MapEntry_V();
    
    public fabric.lang.security.Label set$jif$fabric_util_MapEntry_V(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_MapEntry_V();
    
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
      implements fabric.util.HashMapEntry
    {
        
        native public fabric.lang.JifObject get$key();
        
        native public fabric.lang.JifObject set$key(fabric.lang.JifObject val);
        
        native public fabric.lang.JifObject get$value();
        
        native public fabric.lang.JifObject set$value(
          fabric.lang.JifObject val);
        
        native public int get$hash();
        
        native public int set$hash(int val);
        
        native public int postInc$hash();
        
        native public int postDec$hash();
        
        native public fabric.util.HashMapEntry get$next();
        
        native public fabric.util.HashMapEntry set$next(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$before();
        
        native public fabric.util.HashMapEntry set$before(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$after();
        
        native public fabric.util.HashMapEntry set$after(
          fabric.util.HashMapEntry val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntry_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntry_V();
        
        native public fabric.lang.security.Label get$jif$fabric_util_MapEntry_K(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_MapEntry_K(
          fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_util_MapEntry_V(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_MapEntry_V(
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
        
        native public fabric.util.HashMapEntry fabric$util$HashMapEntry$(
          int arg1, fabric.lang.JifObject arg2, fabric.lang.JifObject arg3,
          fabric.util.HashMapEntry arg4);
        
        native public fabric.lang.JifObject getKey();
        
        native public fabric.lang.JifObject getKey_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject getKey$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject getValue();
        
        native public fabric.lang.JifObject getValue_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject getValue$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.JifObject setValue(
          fabric.lang.JifObject arg1);
        
        native public fabric.lang.JifObject setValue_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject setValue$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifObject arg2);
        
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
        
        native public void addBefore(fabric.util.HashMapEntry arg1);
        
        native public void recordAccess(fabric.util.HashMap arg1);
        
        native public void recordRemoval(fabric.util.HashMap arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.HashMapEntry
          jif$cast$fabric_util_HashMapEntry(fabric.lang.security.Label arg1,
                                            fabric.lang.security.Label arg2,
                                            java.lang.Object arg3);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_MapEntry_K();
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_MapEntry_V();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(HashMapEntry._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.HashMapEntry
    {
        
        native public fabric.lang.JifObject get$key();
        
        native public fabric.lang.JifObject set$key(fabric.lang.JifObject val);
        
        native public fabric.lang.JifObject get$value();
        
        native public fabric.lang.JifObject set$value(
          fabric.lang.JifObject val);
        
        native public int get$hash();
        
        native public int set$hash(int val);
        
        native public int postInc$hash();
        
        native public int postDec$hash();
        
        native public fabric.util.HashMapEntry get$next();
        
        native public fabric.util.HashMapEntry set$next(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$before();
        
        native public fabric.util.HashMapEntry set$before(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$after();
        
        native public fabric.util.HashMapEntry set$after(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry fabric$util$HashMapEntry$(
          final int h, final fabric.lang.JifObject k,
          final fabric.lang.JifObject v, final fabric.util.HashMapEntry n);
        
        native public fabric.lang.JifObject getKey();
        
        native public fabric.lang.JifObject getKey_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject getValue();
        
        native public fabric.lang.JifObject getValue_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject setValue(
          final fabric.lang.JifObject newValue);
        
        native public fabric.lang.JifObject setValue_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject newValue);
        
        native public boolean equals(final fabric.lang.IDComparable obj);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable obj);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable obj);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable obj);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public void addBefore(
          final fabric.util.HashMapEntry existingEntry);
        
        native public void recordAccess(final fabric.util.HashMap m);
        
        native public void recordRemoval(final fabric.util.HashMap m);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$K,
                     final fabric.lang.security.Label jif$V) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.HashMapEntry
          jif$cast$fabric_util_HashMapEntry(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntry_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntry_V();
        
        native public fabric.lang.security.Label get$jif$fabric_util_MapEntry_K(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_MapEntry_K(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_MapEntry_K();
        
        native public fabric.lang.security.Label get$jif$fabric_util_MapEntry_V(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_MapEntry_V(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_MapEntry_V();
        
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
          implements fabric.util.HashMapEntry._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashMapEntry._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMapEntry._Static
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

interface HashMapEntrySet extends fabric.util.AbstractSet {
    
    public fabric.util.HashMap get$parent();
    
    public fabric.util.HashMap set$parent(fabric.util.HashMap val);
    
    public fabric.util.HashMapEntrySet fabric$util$HashMapEntrySet$(
      final fabric.util.HashMap parent);
    
    public fabric.util.Iterator iterator();
    
    public fabric.util.Iterator iterator_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public fabric.lang.JifObject get_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    public int size();
    
    public int size_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean contains(final fabric.lang.security.Label lbl,
                            final fabric.lang.JifObject o);
    
    public boolean contains_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
    
    public boolean remove(final fabric.lang.JifObject o);
    
    public boolean remove_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifObject o);
    
    public void clear();
    
    public void clear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMapEntrySet_K();
    
    public fabric.lang.security.Label get$jif$fabric_util_HashMapEntrySet_V();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.HashMapEntrySet
    {
        
        native public fabric.util.HashMap get$parent();
        
        native public fabric.util.HashMap set$parent(fabric.util.HashMap val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySet_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySet_V();
        
        native public fabric.util.HashMapEntrySet fabric$util$HashMapEntrySet$(
          fabric.util.HashMap arg1);
        
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
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.HashMapEntrySet
          jif$cast$fabric_util_HashMapEntrySet(fabric.lang.security.Label arg1,
                                               fabric.lang.security.Label arg2,
                                               java.lang.Object arg3);
        
        public _Proxy(HashMapEntrySet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.HashMapEntrySet
    {
        
        native public fabric.util.HashMap get$parent();
        
        native public fabric.util.HashMap set$parent(fabric.util.HashMap val);
        
        native public fabric.util.HashMapEntrySet fabric$util$HashMapEntrySet$(
          final fabric.util.HashMap parent);
        
        native public fabric.util.Iterator iterator();
        
        native public fabric.util.Iterator iterator_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.lang.JifObject get(final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject get_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int size();
        
        native public int size_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean contains(final fabric.lang.security.Label lbl,
                                       final fabric.lang.JifObject o);
        
        native public boolean contains_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl, final fabric.lang.JifObject o);
        
        native public boolean remove(final fabric.lang.JifObject o);
        
        native public boolean remove_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifObject o);
        
        native public void clear();
        
        native public void clear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$K,
                     final fabric.lang.security.Label jif$V) {
            super($location, $label, jif$K);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.HashMapEntrySet
          jif$cast$fabric_util_HashMapEntrySet(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySet_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySet_V();
        
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
          implements fabric.util.HashMapEntrySet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashMapEntrySet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMapEntrySet._Static
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

interface HashMapEntrySetIterator
  extends fabric.util.Iterator, fabric.lang.Object
{
    
    public fabric.util.HashMap get$parent();
    
    public fabric.util.HashMap set$parent(fabric.util.HashMap val);
    
    public fabric.util.HashMapEntry get$current();
    
    public fabric.util.HashMapEntry set$current(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntry get$next();
    
    public fabric.util.HashMapEntry set$next(fabric.util.HashMapEntry val);
    
    public fabric.util.HashMapEntrySetIterator
      fabric$util$HashMapEntrySetIterator$(final fabric.util.HashMap parent);
    
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
    
    public fabric.lang.security.Label
      get$jif$fabric_util_HashMapEntrySetIterator_K();
    
    public fabric.lang.security.Label
      get$jif$fabric_util_HashMapEntrySetIterator_V();
    
    public fabric.lang.security.Label get$jif$fabric_util_Iterator_L();
    
    public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.HashMapEntrySetIterator
    {
        
        native public fabric.util.HashMap get$parent();
        
        native public fabric.util.HashMap set$parent(fabric.util.HashMap val);
        
        native public fabric.util.HashMapEntry get$current();
        
        native public fabric.util.HashMapEntry set$current(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$next();
        
        native public fabric.util.HashMapEntry set$next(
          fabric.util.HashMapEntry val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySetIterator_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySetIterator_V();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Iterator_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_util_Iterator_L(
          fabric.lang.security.Label val);
        
        native public fabric.util.HashMapEntrySetIterator
          fabric$util$HashMapEntrySetIterator$(fabric.util.HashMap arg1);
        
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
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.HashMapEntrySetIterator
          jif$cast$fabric_util_HashMapEntrySetIterator(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        final native public fabric.lang.security.Label
          jif$getfabric_util_Iterator_L();
        
        public _Proxy(HashMapEntrySetIterator._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.HashMapEntrySetIterator
    {
        
        native public fabric.util.HashMap get$parent();
        
        native public fabric.util.HashMap set$parent(fabric.util.HashMap val);
        
        native public fabric.util.HashMapEntry get$current();
        
        native public fabric.util.HashMapEntry set$current(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntry get$next();
        
        native public fabric.util.HashMapEntry set$next(
          fabric.util.HashMapEntry val);
        
        native public fabric.util.HashMapEntrySetIterator
          fabric$util$HashMapEntrySetIterator$(
          final fabric.util.HashMap parent);
        
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
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$K,
                     final fabric.lang.security.Label jif$V) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.HashMapEntrySetIterator
          jif$cast$fabric_util_HashMapEntrySetIterator(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySetIterator_K();
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_HashMapEntrySetIterator_V();
        
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
          implements fabric.util.HashMapEntrySetIterator._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.HashMapEntrySetIterator._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMapEntrySetIterator._Static
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
