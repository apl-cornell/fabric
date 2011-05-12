package fabric.lang.security;

public interface AbstractPrincipal
  extends fabric.lang.security.DelegatingPrincipal
{
    
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    public fabric.util.Map get$superiors();
    
    public fabric.util.Map set$superiors(fabric.util.Map val);
    
    public fabric.lang.security.AbstractPrincipal
      fabric$lang$security$AbstractPrincipal$(final java.lang.String name);
    
    public fabric.lang.security.AbstractPrincipal
      fabric$lang$security$AbstractPrincipal$(
      final java.lang.String name, final fabric.lang.security.Principal p);
    
    public java.lang.String name();
    
    public boolean delegatesTo(fabric.lang.security.Principal p);
    
    public void addDelegatesTo(fabric.lang.security.Principal p);
    
    public void removeDelegatesTo(fabric.lang.security.Principal p);
    
    public boolean superiorsContains(fabric.lang.security.Principal p);
    
    public boolean isAuthorized(java.lang.Object authPrf,
                                fabric.lang.security.Closure closure,
                                fabric.lang.security.Label lb,
                                boolean executeNow);
    
    public fabric.lang.security.ActsForProof findProofDownto(
      fabric.worker.Store store, fabric.lang.security.Principal q,
      java.lang.Object searchState);
    
    public fabric.lang.security.ActsForProof findProofUpto(
      fabric.worker.Store store, fabric.lang.security.Principal p,
      java.lang.Object searchState);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public boolean equals(fabric.lang.security.Principal p);
    
    public static class _Proxy
    extends fabric.lang.security.DelegatingPrincipal._Proxy
      implements fabric.lang.security.AbstractPrincipal
    {
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public fabric.util.Map get$superiors();
        
        native public fabric.util.Map set$superiors(fabric.util.Map val);
        
        native public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(java.lang.String arg1);
        
        native public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          java.lang.String arg1, fabric.lang.security.Principal arg2);
        
        native public java.lang.String name();
        
        native public boolean delegatesTo(fabric.lang.security.Principal arg1);
        
        native public boolean superiorsContains(
          fabric.lang.security.Principal arg1);
        
        native public boolean isAuthorized(java.lang.Object arg1,
                                           fabric.lang.security.Closure arg2,
                                           fabric.lang.security.Label arg3,
                                           boolean arg4);
        
        native public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          java.lang.Object arg3);
        
        native public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          java.lang.Object arg3);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public boolean equals(fabric.lang.security.Principal arg1);
        
        public _Proxy(AbstractPrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl
    extends fabric.lang.security.DelegatingPrincipal._Impl
      implements fabric.lang.security.AbstractPrincipal
    {
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public fabric.util.Map get$superiors();
        
        native public fabric.util.Map set$superiors(fabric.util.Map val);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
		     fabric.lang.security.Label $accessLabel) {
            super($location, $label, $accessLabel);
        }
        
        native private void jif$init();
        
        native public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(final java.lang.String name);
        
        native public fabric.lang.security.AbstractPrincipal
          fabric$lang$security$AbstractPrincipal$(
          final java.lang.String name, final fabric.lang.security.Principal p);
        
        protected _Impl(fabric.worker.Store $location,
                        fabric.lang.security.Label $label,
			fabric.lang.security.Label $accessLabel,
                        java.lang.String name) {
            this($location, $label, $accessLabel);
        }
        
        native public java.lang.String name();
        
        native public boolean delegatesTo(fabric.lang.security.Principal p);
        
        native public void addDelegatesTo(fabric.lang.security.Principal p);
        
        native public void removeDelegatesTo(fabric.lang.security.Principal p);
        
        native public boolean superiorsContains(
          fabric.lang.security.Principal p);
        
        native public boolean isAuthorized(java.lang.Object authPrf,
                                           fabric.lang.security.Closure closure,
                                           fabric.lang.security.Label lb,
                                           boolean executeNow);
        
        native public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState);
        
        native public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object o);
        
        native public boolean equals(fabric.lang.security.Principal p);
        
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
          implements fabric.lang.security.AbstractPrincipal._Static
        {
            
            public _Proxy(fabric.lang.security.AbstractPrincipal._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.AbstractPrincipal._Static
        {
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label,
			 fabric.lang.security.Label accessLabel)
                  throws fabric.net.UnreachableNodeException {
                super(store, label, accessLabel);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
