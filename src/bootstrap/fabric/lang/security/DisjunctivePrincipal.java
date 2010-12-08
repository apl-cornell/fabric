package fabric.lang.security;


public interface DisjunctivePrincipal extends fabric.lang.security.Principal {
    
    public fabric.util.Set get$disjuncts();
    
    public java.lang.Integer get$hashCode();
    
    public java.lang.Integer set$hashCode(java.lang.Integer val);
    
    public java.lang.String name();
    
    public boolean delegatesTo(fabric.lang.security.Principal p);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.security.Principal p);
    
    public boolean isAuthorized(java.lang.Object authPrf,
                                fabric.lang.security.Closure closure,
                                fabric.lang.security.Label lb,
                                boolean executeNow);
    
    public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);
    
    public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);
    
    public static class _Proxy extends fabric.lang.security.Principal._Proxy
      implements fabric.lang.security.DisjunctivePrincipal
    {
        
        native public fabric.util.Set get$disjuncts();
        
        native public java.lang.Integer get$hashCode();
        
        native public java.lang.Integer set$hashCode(java.lang.Integer val);
        
        native public int hashCode();
        
        public _Proxy(DisjunctivePrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl extends fabric.lang.security.Principal._Impl
      implements fabric.lang.security.DisjunctivePrincipal
    {
        
        native public fabric.util.Set get$disjuncts();
        
        native public java.lang.Integer get$hashCode();
        
        native public java.lang.Integer set$hashCode(java.lang.Integer val);
        
        _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label,
              fabric.util.Set disjuncts) {
            super($location, $label);
        }
        
        native public java.lang.String name();
        
        native public boolean delegatesTo(fabric.lang.security.Principal p);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.security.Principal p);
        
        native public boolean isAuthorized(java.lang.Object authPrf,
                                           fabric.lang.security.Closure closure,
                                           fabric.lang.security.Label lb,
                                           boolean executeNow);
        
        native public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState);
        
        native public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState);
        
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
        
        public fabric.worker.Store get$localStore();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.DisjunctivePrincipal._Static
        {
            
            native public fabric.worker.Store get$localStore();
            
            public _Proxy(fabric.lang.security.DisjunctivePrincipal._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.DisjunctivePrincipal._Static
        {
            
            native public fabric.worker.Store get$localStore();
            
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
