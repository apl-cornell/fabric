package fabric.util;

public interface AbstractSequentialList extends fabric.util.AbstractList {
    
    public fabric.util.AbstractSequentialList
      fabric$util$AbstractSequentialList$();
    
    abstract public fabric.util.ListIterator listIterator(
      fabric.worker.Store store, int index);
    
    public void add(int index, fabric.lang.Object o);
    
    public boolean addAll(int index, fabric.util.Collection c);
    
    public fabric.lang.Object get(int index);
    
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public fabric.lang.Object remove(int index);
    
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.AbstractSequentialList
    {
        
        native public fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$();
        
        public _Proxy(AbstractSequentialList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.AbstractSequentialList
    {
        
        native public fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$();
        
        abstract public fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);
        
        native public void add(int index, fabric.lang.Object o);
        
        native public boolean addAll(int index, fabric.util.Collection c);
        
        native public fabric.lang.Object get(int index);
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        native public fabric.lang.Object remove(int index);
        
        native public fabric.lang.Object set(int index, fabric.lang.Object o);
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSequentialList._Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Proxy(fabric.util.AbstractSequentialList._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSequentialList._Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
