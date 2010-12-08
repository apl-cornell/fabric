package fabric.lang.security;

public interface ExternalPrincipal
  extends fabric.lang.security.AbstractPrincipal
{
    
    public fabric.lang.security.ExternalPrincipal
      fabric$lang$security$ExternalPrincipal$(final java.lang.String name);
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPrincipal._Proxy
      implements fabric.lang.security.ExternalPrincipal
    {
        
        native public fabric.lang.security.ExternalPrincipal
          fabric$lang$security$ExternalPrincipal$(java.lang.String arg1);
        
        public _Proxy(ExternalPrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.lang.security.AbstractPrincipal._Impl
      implements fabric.lang.security.ExternalPrincipal
    {
        
        native public fabric.lang.security.ExternalPrincipal
          fabric$lang$security$ExternalPrincipal$(final java.lang.String name);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
        native private void jif$init();
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ExternalPrincipal._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.lang.security.ExternalPrincipal._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ExternalPrincipal._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
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
