package fabric.lang.security;

public interface JoinPolicy
  extends fabric.lang.security.Policy, fabric.lang.security.AbstractPolicy
{
    
    public fabric.util.Set get$components();
    
    public fabric.util.Set set$components(fabric.util.Set val);
    
    public fabric.worker.Store get$localStore();
    
    public fabric.util.Set joinComponents();
    
    public boolean relabelsTo(fabric.lang.security.Policy pol, java.util.Set s);
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public java.lang.String toString();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.JoinPolicy
    {
        
        native public fabric.util.Set get$components();
        
        native public fabric.util.Set set$components(fabric.util.Set val);
        
        native public fabric.worker.Store get$localStore();
        
        native public fabric.util.Set joinComponents();
        
        native public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         java.util.Set arg2);
        
        final native public java.lang.String toString();
        
        public _Proxy(JoinPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl
    extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.JoinPolicy
    {
        
        native public fabric.util.Set get$components();
        
        native public fabric.util.Set set$components(fabric.util.Set val);
        
        native public fabric.worker.Store get$localStore();
        
        _Impl(fabric.worker.Store $location, fabric.lang.security.Label $label, fabric.lang.security.Label accessLabel,
              fabric.util.Set policies) {
            super($location, $label, accessLabel);
        }
        
        native public fabric.util.Set joinComponents();
        
        native public boolean relabelsTo(fabric.lang.security.Policy pol,
                                         java.util.Set s);
        
        native public boolean equals(fabric.lang.Object o);
        
        final native public int hashCode();
        
        final native public java.lang.String toString();
        
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
          implements fabric.lang.security.JoinPolicy._Static
        {
            
            public _Proxy(fabric.lang.security.JoinPolicy._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.JoinPolicy._Static
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
