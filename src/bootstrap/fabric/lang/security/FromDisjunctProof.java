package fabric.lang.security;

public interface FromDisjunctProof extends fabric.lang.security.ActsForProof {
    
    public fabric.util.Map get$disjunctProofs();
    
    public fabric.util.Map getDisjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.FromDisjunctProof
    {
        
        native public fabric.util.Map get$disjunctProofs();
        
        native public fabric.util.Map getDisjunctProofs();
        
        public _Proxy(FromDisjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.FromDisjunctProof
    {
        
        native public fabric.util.Map get$disjunctProofs();
        
        _Impl(fabric.worker.Store $location) {
            super($location);
        }
        
        native public fabric.util.Map getDisjunctProofs();
        
        native public void gatherDelegationDependencies(java.util.Set s);
        
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
          implements fabric.lang.security.FromDisjunctProof._Static
        {
            
            public _Proxy(fabric.lang.security.FromDisjunctProof._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.FromDisjunctProof._Static
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
