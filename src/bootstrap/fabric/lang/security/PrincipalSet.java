package fabric.lang.security;

public interface PrincipalSet extends fabric.lang.Object {
    
    public fabric.util.Set get$set();
    
    public fabric.util.Set set$set(fabric.util.Set val);
    
    public fabric.lang.security.PrincipalSet add(
      fabric.lang.security.Principal p);
    
    public fabric.util.Set getSet();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrincipalSet
    {
        
        native public fabric.util.Set get$set();
        
        native public fabric.util.Set set$set(fabric.util.Set val);
        
        native public fabric.lang.security.PrincipalSet add(
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Set getSet();
        
        public _Proxy(PrincipalSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrincipalSet
    {
        
        native public fabric.util.Set get$set();
        
        native public fabric.util.Set set$set(fabric.util.Set val);
        
        public _Impl(fabric.worker.Store $location) {
            super($location);
        }
        
        native public fabric.lang.security.PrincipalSet add(
          fabric.lang.security.Principal p);
        
        native public fabric.util.Set getSet();
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                  intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.PrincipalSet._Static
        {
            
            public _Proxy(fabric.lang.security.PrincipalSet._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PrincipalSet._Static
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
