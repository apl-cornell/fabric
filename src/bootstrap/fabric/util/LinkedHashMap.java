package fabric.util;

public interface LinkedHashMap extends fabric.util.HashMap {
    
    public fabric.util.LinkedHashMap.LinkedHashEntry get$root();
    
    public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
      fabric.util.LinkedHashMap.LinkedHashEntry val);
    
    public boolean get$accessOrder();
    
    public boolean set$accessOrder(boolean val);
    
    public static interface LinkedHashEntry
      extends fabric.util.HashMap.HashEntry
    {
        
        public fabric.util.LinkedHashMap get$out$();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public fabric.util.LinkedHashMap.LinkedHashEntry
          fabric$util$LinkedHashMap$LinkedHashEntry$(fabric.lang.Object key,
                                                     fabric.lang.Object value);
        
        public void access();
        
        public fabric.lang.Object cleanup();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.HashMap.HashEntry._Proxy
          implements fabric.util.LinkedHashMap.LinkedHashEntry
        {
            
            native public fabric.util.LinkedHashMap get$out$();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry
              fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            public _Proxy(LinkedHashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.HashMap.HashEntry._Impl
          implements fabric.util.LinkedHashMap.LinkedHashEntry
        {
            
            native public fabric.util.LinkedHashMap get$out$();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry
              fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object key, fabric.lang.Object value);
            
            native public void access();
            
            native public fabric.lang.Object cleanup();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedHashMap out$) {
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
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static
            {
                
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static
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
    
    
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
    
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      fabric.util.Map m);
    
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity);
    
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor);
    
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor, boolean accessOrder);
    
    public void clear();
    
    public boolean containsValue(fabric.lang.Object value);
    
    public fabric.lang.Object get(fabric.lang.Object key);
    
    public boolean removeEldestEntry(fabric.util.Map.Entry eldest);
    
    public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
                         int idx, boolean callRemove);
    
    public void putAllInternal(fabric.util.Map m);
    
    public fabric.util.Iterator iterator(fabric.worker.Store store,
                                         final int type);
    
    public static interface LinkedHashIterator
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.LinkedHashMap get$out$();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$current();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$last();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public fabric.util.LinkedHashMap.LinkedHashIterator
          fabric$util$LinkedHashMap$LinkedHashIterator$(int type);
        
        public boolean hasNext();
        
        public fabric.lang.Object next();
        
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashMap.LinkedHashIterator
        {
            
            native public fabric.util.LinkedHashMap get$out$();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$current(
              );
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$last();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public fabric.util.LinkedHashMap.LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int arg1);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(LinkedHashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashMap.LinkedHashIterator
        {
            
            native public fabric.util.LinkedHashMap get$out$();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$current(
              );
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry get$last();
            
            native public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
              fabric.util.LinkedHashMap.LinkedHashEntry val);
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public fabric.util.LinkedHashMap.LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int type);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedHashMap out$) {
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
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static
            {
                
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static
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
    
    public static class _Proxy extends fabric.util.HashMap._Proxy
      implements fabric.util.LinkedHashMap
    {
        
        native public fabric.util.LinkedHashMap.LinkedHashEntry get$root();
        
        native public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        native public boolean get$accessOrder();
        
        native public boolean set$accessOrder(boolean val);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          fabric.util.Map arg1);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2, boolean arg3);
        
        native public boolean removeEldestEntry(fabric.util.Map.Entry arg1);
        
        public _Proxy(LinkedHashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashMap._Impl
      implements fabric.util.LinkedHashMap
    {
        
        native public fabric.util.LinkedHashMap.LinkedHashEntry get$root();
        
        native public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        native public boolean get$accessOrder();
        
        native public boolean set$accessOrder(boolean val);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          fabric.util.Map m);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity, float loadFactor);
        
        native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity, float loadFactor, boolean accessOrder);
        
        native public void clear();
        
        native public boolean containsValue(fabric.lang.Object value);
        
        native public fabric.lang.Object get(fabric.lang.Object key);
        
        native public boolean removeEldestEntry(fabric.util.Map.Entry eldest);
        
        native public void addEntry(fabric.lang.Object key,
                                    fabric.lang.Object value, int idx,
                                    boolean callRemove);
        
        native public void putAllInternal(fabric.util.Map m);
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store,
                                                    final int type);
        
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
        
        public long get$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashMap._Static
        {
            
            native public long get$serialVersionUID();
            
            public _Proxy(fabric.util.LinkedHashMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashMap._Static
        {
            
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
