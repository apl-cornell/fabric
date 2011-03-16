package fabric.lang.security;

public interface JoinIntegPolicy
  extends fabric.lang.security.IntegPolicy, fabric.lang.security.JoinPolicy
{
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.lang.security.IntegPolicy p, java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.lang.security.IntegPolicy p, java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.lang.security.IntegPolicy p);
    
    public static class _Proxy extends fabric.lang.security.JoinPolicy._Proxy
      implements fabric.lang.security.JoinIntegPolicy
    {
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy arg1);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy arg1);
        
        public _Proxy(JoinIntegPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl
    extends fabric.lang.security.JoinPolicy._Impl
      implements fabric.lang.security.JoinIntegPolicy
    {
        
        _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label,
              fabric.util.Set policies) {
            super($location, $label, policies);
        }
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy p, java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
        
        native public fabric.lang.security.IntegPolicy join(
          fabric.lang.security.IntegPolicy p);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.IntegPolicy meet(
          fabric.lang.security.IntegPolicy p, java.util.Set s);
        
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.JoinIntegPolicy._Static
        {
            
            public _Proxy(fabric.lang.security.JoinIntegPolicy._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.JoinIntegPolicy._Static
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
