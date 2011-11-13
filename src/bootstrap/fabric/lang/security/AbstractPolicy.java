package fabric.lang.security;

public interface AbstractPolicy
  extends fabric.lang.security.Policy, fabric.lang.Object
{
    
    abstract public boolean equals(fabric.lang.Object that);
    
    abstract public int hashCode();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.AbstractPolicy
    {
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        native public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         java.util.Set arg2);
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        public _Proxy(AbstractPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.AbstractPolicy
    {
        
        protected _Impl(fabric.worker.Store $location,
                        fabric.lang.security.Label $label, Label accessLabel) {
            super($location, $label, accessLabel);
        }
        
        abstract public boolean equals(fabric.lang.Object that);
        
        abstract public int hashCode();
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.AbstractPolicy._Static
        {
            
            public _Proxy(fabric.lang.security.AbstractPolicy._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.AbstractPolicy._Static
        {
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label, Label accessLabel)
                  throws fabric.net.UnreachableNodeException {
                super(store, label, accessLabel);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
