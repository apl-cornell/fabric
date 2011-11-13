package fabric.lang.security;

public interface WriterPolicy
  extends fabric.lang.security.IntegPolicy, fabric.lang.security.AbstractPolicy
{
    
    public fabric.lang.security.Principal get$owner();
    
    public fabric.lang.security.Principal get$writer();
    
    public fabric.lang.security.Principal owner();
    
    public fabric.lang.security.Principal writer();
    
    public boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public void clobberThisPlaceholders(Principal replacement);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.lang.security.IntegPolicy p, java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.lang.security.IntegPolicy p, java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.lang.security.IntegPolicy p);
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.WriterPolicy
    {
        
        native public fabric.lang.security.Principal get$owner();
        
        native public fabric.lang.security.Principal get$writer();
        
        native public fabric.lang.security.Principal owner();
        
        native public fabric.lang.security.Principal writer();
        
        native public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         java.util.Set arg2);
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy arg1);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy arg1);
        
        public _Proxy(WriterPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.WriterPolicy
    {
        
        native public fabric.lang.security.Principal get$owner();
        
        native public fabric.lang.security.Principal get$writer();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label, fabric.lang.security.Label accessLabel,
                     fabric.lang.security.Principal owner,
                     fabric.lang.security.Principal writer) {
            super($location, $label, accessLabel);
        }
        
        native public fabric.lang.security.Principal owner();
        
        native public fabric.lang.security.Principal writer();
        
        native public boolean relabelsTo(fabric.lang.security.Policy p,
                                         java.util.Set s);
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object o);
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy p, java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy p, java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy p);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy p);
        
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
          implements fabric.lang.security.WriterPolicy._Static
        {
            
            public _Proxy(fabric.lang.security.WriterPolicy._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.WriterPolicy._Static
        {
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label, fabric.lang.security.Label accessLabel)
                  throws fabric.net.UnreachableNodeException {
                super(store, label, accessLabel);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
