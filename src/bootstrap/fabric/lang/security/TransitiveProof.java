package fabric.lang.security;

public interface TransitiveProof extends fabric.lang.security.ActsForProof {
    
    public fabric.lang.security.ActsForProof get$actorToP();
    
    public fabric.lang.security.ActsForProof get$pToGranter();
    
    public fabric.lang.security.Principal get$p();
    
    public fabric.lang.security.ActsForProof getActorToP();
    
    public fabric.lang.security.ActsForProof getPToGranter();
    
    public fabric.lang.security.Principal getP();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.TransitiveProof
    {
        
        native public fabric.lang.security.ActsForProof get$actorToP();
        
        native public fabric.lang.security.ActsForProof get$pToGranter();
        
        native public fabric.lang.security.Principal get$p();
        
        native public fabric.lang.security.ActsForProof getActorToP();
        
        native public fabric.lang.security.ActsForProof getPToGranter();
        
        native public fabric.lang.security.Principal getP();
        
        public _Proxy(TransitiveProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.TransitiveProof
    {
        
        native public fabric.lang.security.ActsForProof get$actorToP();
        
        native public fabric.lang.security.ActsForProof get$pToGranter();
        
        native public fabric.lang.security.Principal get$p();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.ActsForProof actorToP,
                     fabric.lang.security.Principal p,
                     fabric.lang.security.ActsForProof pToGranter) {
            super($location,
                  !fabric.lang.Object._Proxy.idEquals(actorToP, null)
                    ? actorToP.getActor()
                    : null,
                  !fabric.lang.Object._Proxy.idEquals(pToGranter, null)
                    ? pToGranter.getGranter()
                    : null);
        }
        
        native public fabric.lang.security.ActsForProof getActorToP();
        
        native public fabric.lang.security.ActsForProof getPToGranter();
        
        native public fabric.lang.security.Principal getP();
        
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
          implements fabric.lang.security.TransitiveProof._Static
        {
            
            public _Proxy(fabric.lang.security.TransitiveProof._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.TransitiveProof._Static
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
