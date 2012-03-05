package fabric.util;

public interface HashMap extends fabric.util.Map, fabric.util.AbstractMap {
    
    public int get$threshold();
    
    public int set$threshold(int val);
    
    public int postInc$threshold();
    
    public int postDec$threshold();
    
    public float get$loadFactor();
    
    public float set$loadFactor(float val);
    
    public float postInc$loadFactor();
    
    public float postDec$loadFactor();
    
    public fabric.lang.arrays.ObjectArray get$buckets();
    
    public fabric.lang.arrays.ObjectArray set$buckets(
      fabric.lang.arrays.ObjectArray val);
    
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.util.Set get$entries();
    
    public fabric.util.Set set$entries(fabric.util.Set val);
    
    public static interface HashEntry
      extends fabric.util.AbstractMap.BasicMapEntry
    {
        
        public fabric.util.HashMap.HashEntry get$next();
        
        public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);
        
        public fabric.util.HashMap.HashEntry fabric$util$HashMap$HashEntry$(
          fabric.lang.Object key, fabric.lang.Object value);
        
        public void access();
        
        public fabric.lang.Object cleanup();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractMap.BasicMapEntry._Proxy
          implements fabric.util.HashMap.HashEntry
        {
            
            native public fabric.util.HashMap.HashEntry get$next();
            
            native public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashEntry
              fabric$util$HashMap$HashEntry$(fabric.lang.Object arg1,
                                             fabric.lang.Object arg2);
            
            native public void access();
            
            native public fabric.lang.Object cleanup();
            
            public _Proxy(HashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl
          implements fabric.util.HashMap.HashEntry
        {
            
            native public fabric.util.HashMap.HashEntry get$next();
            
            native public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashEntry
              fabric$util$HashMap$HashEntry$(fabric.lang.Object key,
                                             fabric.lang.Object value);
            
            native public void access();
            
            native public fabric.lang.Object cleanup();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out,
                                          java.util.List refTypes,
                                          java.util.List intraStoreRefs,
                                          java.util.List interStoreRefs)
                  throws java.io.IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs);
            }
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.HashEntry._Static
            {
                
                public _Proxy(fabric.util.HashMap.HashEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.HashEntry._Static
            {
                
                public _Impl(fabric.worker.Store store)
                      throws fabric.net.UnreachableNodeException {
                    super(store);
                }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    
    public fabric.util.HashMap fabric$util$HashMap$();
    
    public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map m);
    
    public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity);
    
    public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity,
                                                    float loadFactor);
    
    public int size();
    
    public boolean isEmpty();
    
    public fabric.lang.Object get(fabric.lang.Object key);
    
    public boolean containsKey(fabric.lang.Object key);
    
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
    public void putAll(fabric.util.Map m);
    
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    public void clear();
    
    public boolean containsValue(fabric.lang.Object value);
    
    public fabric.util.Set keySet();
    
    public fabric.util.Collection values();
    
    public fabric.util.Set entrySet();
    
    public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
                         int idx, boolean callRemove);
    
    public fabric.util.HashMap.HashEntry getEntry(fabric.lang.Object o);
    
    public int hash(fabric.lang.Object key);
    
    public fabric.util.Iterator iterator(fabric.worker.Store store, int type);
    
    public void putAllInternal(fabric.util.Map m);
    
    public static interface HashIterator
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.HashMap get$out$();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$count();
        
        public int set$count(int val);
        
        public int postInc$count();
        
        public int postDec$count();
        
        public int get$idx();
        
        public int set$idx(int val);
        
        public int postInc$idx();
        
        public int postDec$idx();
        
        public fabric.util.HashMap.HashEntry get$last();
        
        public fabric.util.HashMap.HashEntry set$last(
          fabric.util.HashMap.HashEntry val);
        
        public fabric.util.HashMap.HashEntry get$next();
        
        public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);
        
        public fabric.util.HashMap.HashIterator
          fabric$util$HashMap$HashIterator$(int type);
        
        public boolean hasNext();
        
        public fabric.lang.Object next();
        
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HashMap.HashIterator
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$count();
            
            native public int set$count(int val);
            
            native public int postInc$count();
            
            native public int postDec$count();
            
            native public int get$idx();
            
            native public int set$idx(int val);
            
            native public int postInc$idx();
            
            native public int postDec$idx();
            
            native public fabric.util.HashMap.HashEntry get$last();
            
            native public fabric.util.HashMap.HashEntry set$last(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashEntry get$next();
            
            native public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashIterator
              fabric$util$HashMap$HashIterator$(int arg1);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(HashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMap.HashIterator
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$count();
            
            native public int set$count(int val);
            
            native public int postInc$count();
            
            native public int postDec$count();
            
            native public int get$idx();
            
            native public int set$idx(int val);
            
            native public int postInc$idx();
            
            native public int postDec$idx();
            
            native public fabric.util.HashMap.HashEntry get$last();
            
            native public fabric.util.HashMap.HashEntry set$last(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashEntry get$next();
            
            native public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val);
            
            native public fabric.util.HashMap.HashIterator
              fabric$util$HashMap$HashIterator$(int type);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.HashMap out$) {
                super($location);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out,
                                          java.util.List refTypes,
                                          java.util.List intraStoreRefs,
                                          java.util.List interStoreRefs)
                  throws java.io.IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs);
            }
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.HashIterator._Static
            {
                
                public _Proxy(fabric.util.HashMap.HashIterator._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.HashIterator._Static
            {
                
                public _Impl(fabric.worker.Store store)
                      throws fabric.net.UnreachableNodeException {
                    super(store);
                }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static interface Anonymous$5 extends fabric.util.AbstractSet {
        
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.HashMap.Anonymous$5
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$5._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements fabric.util.HashMap.Anonymous$5
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean remove(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out,
                                          java.util.List refTypes,
                                          java.util.List intraStoreRefs,
                                          java.util.List interStoreRefs)
                  throws java.io.IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs);
            }
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$5._Static
            {
                
                public _Proxy(fabric.util.HashMap.Anonymous$5._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$5._Static
            {
                
                public _Impl(fabric.worker.Store store)
                      throws fabric.net.UnreachableNodeException {
                    super(store);
                }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface Anonymous$6 extends fabric.util.AbstractCollection {
        
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements fabric.util.HashMap.Anonymous$6
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$6._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements fabric.util.HashMap.Anonymous$6
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out,
                                          java.util.List refTypes,
                                          java.util.List intraStoreRefs,
                                          java.util.List interStoreRefs)
                  throws java.io.IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs);
            }
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$6._Static
            {
                
                public _Proxy(fabric.util.HashMap.Anonymous$6._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$6._Static
            {
                
                public _Impl(fabric.worker.Store store)
                      throws fabric.net.UnreachableNodeException {
                    super(store);
                }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface Anonymous$7 extends fabric.util.AbstractSet {
        
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.HashMap.Anonymous$7
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$7._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements fabric.util.HashMap.Anonymous$7
        {
            
            native public fabric.util.HashMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean remove(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out,
                                          java.util.List refTypes,
                                          java.util.List intraStoreRefs,
                                          java.util.List interStoreRefs)
                  throws java.io.IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs);
            }
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$7._Static
            {
                
                public _Proxy(fabric.util.HashMap.Anonymous$7._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$7._Static
            {
                
                public _Impl(fabric.worker.Store store)
                      throws fabric.net.UnreachableNodeException {
                    super(store);
                }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.HashMap
    {
        
        native public int get$threshold();
        
        native public int set$threshold(int val);
        
        native public int postInc$threshold();
        
        native public int postDec$threshold();
        
        native public float get$loadFactor();
        
        native public float set$loadFactor(float val);
        
        native public float postInc$loadFactor();
        
        native public float postDec$loadFactor();
        
        native public fabric.lang.arrays.ObjectArray get$buckets();
        
        native public fabric.lang.arrays.ObjectArray set$buckets(
          fabric.lang.arrays.ObjectArray val);
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.Set get$entries();
        
        native public fabric.util.Set set$entries(fabric.util.Set val);
        
        native public fabric.util.HashMap fabric$util$HashMap$();
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          fabric.util.Map arg1);
        
        native public fabric.util.HashMap fabric$util$HashMap$(int arg1);
        
        native public fabric.util.HashMap fabric$util$HashMap$(int arg1,
                                                               float arg2);
        
        native public void addEntry(fabric.lang.Object arg1,
                                    fabric.lang.Object arg2, int arg3,
                                    boolean arg4);
        
        final native public fabric.util.HashMap.HashEntry getEntry(
          fabric.lang.Object arg1);
        
        final native public int hash(fabric.lang.Object arg1);
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1,
                                                    int arg2);
        
        native public void putAllInternal(fabric.util.Map arg1);
        
        native public fabric.lang.Object $initLabels();
        
        public _Proxy(HashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.HashMap
    {
        
        native public int get$threshold();
        
        native public int set$threshold(int val);
        
        native public int postInc$threshold();
        
        native public int postDec$threshold();
        
        native public float get$loadFactor();
        
        native public float set$loadFactor(float val);
        
        native public float postInc$loadFactor();
        
        native public float postDec$loadFactor();
        
        native public fabric.lang.arrays.ObjectArray get$buckets();
        
        native public fabric.lang.arrays.ObjectArray set$buckets(
          fabric.lang.arrays.ObjectArray val);
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.Set get$entries();
        
        native public fabric.util.Set set$entries(fabric.util.Set val);
        
        native public fabric.util.HashMap fabric$util$HashMap$();
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          fabric.util.Map m);
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          int initialCapacity);
        
        native public fabric.util.HashMap fabric$util$HashMap$(
          int initialCapacity, float loadFactor);
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public fabric.lang.Object get(fabric.lang.Object key);
        
        native public boolean containsKey(fabric.lang.Object key);
        
        native public fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        native public void putAll(fabric.util.Map m);
        
        native public fabric.lang.Object remove(fabric.lang.Object key);
        
        native public void clear();
        
        native public boolean containsValue(fabric.lang.Object value);
        
        native public fabric.util.Set keySet();
        
        native public fabric.util.Collection values();
        
        native public fabric.util.Set entrySet();
        
        native public void addEntry(fabric.lang.Object key,
                                    fabric.lang.Object value, int idx,
                                    boolean callRemove);
        
        final native public fabric.util.HashMap.HashEntry getEntry(
          fabric.lang.Object o);
        
        final native public int hash(fabric.lang.Object key);
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store,
                                                    int type);
        
        native public void putAllInternal(fabric.util.Map m);
        
        native private void rehash();
        
        native public fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public int get$DEFAULT_CAPACITY();
        
        public float get$DEFAULT_LOAD_FACTOR();
        
        public long get$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HashMap._Static
        {
            
            native public int get$DEFAULT_CAPACITY();
            
            native public float get$DEFAULT_LOAD_FACTOR();
            
            native public long get$serialVersionUID();
            
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
            
            native public int get$DEFAULT_CAPACITY();
            
            native public float get$DEFAULT_LOAD_FACTOR();
            
            native public long get$serialVersionUID();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
