package fabric.lang.security;


public interface ToConjunctProof extends fabric.lang.security.ActsForProof {
    
    public fabric.util.Map get$conjunctProofs();
    
    public fabric.util.Map getConjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.ToConjunctProof
    {
        
        native public fabric.util.Map get$conjunctProofs();
        
        native public fabric.util.Map getConjunctProofs();
        
        public _Proxy(ToConjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.ToConjunctProof
    {
        
        native public fabric.util.Map get$conjunctProofs();
        
        _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label,
              fabric.lang.security.Principal actor,
              fabric.lang.security.ConjunctivePrincipal granter,
              fabric.util.Map conjunctProofs) {
            super($location, $label, actor, granter);
        }
        
        native public fabric.util.Map getConjunctProofs();
        
        native public void gatherDelegationDependencies(java.util.Set s);
        
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
          implements fabric.lang.security.ToConjunctProof._Static
        {
            
            public _Proxy(fabric.lang.security.ToConjunctProof._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ToConjunctProof._Static
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
