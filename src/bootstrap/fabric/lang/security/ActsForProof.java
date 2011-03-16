package fabric.lang.security;

public interface ActsForProof extends fabric.lang.Object {
    
    public fabric.lang.security.Principal get$actor();
    
    public fabric.lang.security.Principal get$granter();
    
    public fabric.lang.security.Principal getActor();
    
    public fabric.lang.security.Principal getGranter();
    
    abstract public void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ActsForProof
    {
        
        native public fabric.lang.security.Principal get$actor();
        
        native public fabric.lang.security.Principal get$granter();
        
        native public fabric.lang.security.Principal getActor();
        
        native public fabric.lang.security.Principal getGranter();
        
        native public void gatherDelegationDependencies(java.util.Set arg1);
        
        public _Proxy(ActsForProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.ActsForProof
    {
        
        native public fabric.lang.security.Principal get$actor();
        
        native public fabric.lang.security.Principal get$granter();
        
        _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label,
              fabric.lang.security.Principal actor,
              fabric.lang.security.Principal granter) {
            super($location, $label);
        }
        
        native public fabric.lang.security.Principal getActor();
        
        native public fabric.lang.security.Principal getGranter();
        
        abstract public void gatherDelegationDependencies(java.util.Set s);
        
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ActsForProof._Static
        {
            
            public _Proxy(fabric.lang.security.ActsForProof._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ActsForProof._Static
        {
            
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
