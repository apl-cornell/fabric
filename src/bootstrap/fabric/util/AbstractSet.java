package fabric.util;

public interface AbstractSet
  extends fabric.util.Set, fabric.util.AbstractCollection
{
    
    public fabric.util.AbstractSet fabric$util$AbstractSet$();
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public boolean removeAll(fabric.util.Collection c);
    
    public fabric.util.Iterator iterator();
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractSet
    {
        
        native public fabric.util.AbstractSet fabric$util$AbstractSet$();
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        public _Proxy(AbstractSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractSet
    {
        
        native public fabric.util.AbstractSet fabric$util$AbstractSet$();
        
        native public boolean equals(fabric.lang.Object o);
        
        native public int hashCode();
        
        native public boolean removeAll(fabric.util.Collection c);
        
        native public fabric.util.Iterator iterator();
        
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSet._Static
        {
            
            public _Proxy(fabric.util.AbstractSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSet._Static
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
