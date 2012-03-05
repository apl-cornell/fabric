package fabric.util;

public interface HashSet extends fabric.util.Set, fabric.util.AbstractSet {
    
    public fabric.util.HashMap get$map();
    
    public fabric.util.HashMap set$map(fabric.util.HashMap val);
    
    public fabric.util.HashSet fabric$util$HashSet$();
    
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);
    
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity,
                                                    float loadFactor);
    
    public fabric.util.HashSet fabric$util$HashSet$(fabric.util.Collection c);
    
    public boolean add(fabric.lang.Object o);
    
    public void clear();
    
    public boolean contains(fabric.lang.Object o);
    
    public boolean isEmpty();
    
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public boolean remove(fabric.lang.Object o);
    
    public int size();
    
    public fabric.util.HashMap init(int capacity, float load);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.HashSet
    {
        
        native public fabric.util.HashMap get$map();
        
        native public fabric.util.HashMap set$map(fabric.util.HashMap val);
        
        native public fabric.util.HashSet fabric$util$HashSet$();
        
        native public fabric.util.HashSet fabric$util$HashSet$(int arg1);
        
        native public fabric.util.HashSet fabric$util$HashSet$(int arg1,
                                                               float arg2);
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          fabric.util.Collection arg1);
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public int size();
        
        native public fabric.util.HashMap init(int arg1, float arg2);
        
        native public fabric.lang.Object $initLabels();
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
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
        
        native public fabric.util.HashSet fabric$util$HashSet$();
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          int initialCapacity);
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          int initialCapacity, float loadFactor);
        
        native public fabric.util.HashSet fabric$util$HashSet$(
          fabric.util.Collection c);
        
        native public boolean add(fabric.lang.Object o);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object o);
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        native public boolean remove(fabric.lang.Object o);
        
        native public int size();
        
        native public fabric.util.HashMap init(int capacity, float load);
        
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
          implements fabric.util.HashSet._Static
        {
            
            native public long get$serialVersionUID();
            
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
