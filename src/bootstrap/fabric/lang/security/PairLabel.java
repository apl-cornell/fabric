package fabric.lang.security;

public interface PairLabel
  extends fabric.lang.security.Label, fabric.lang.Object
{
    
    public fabric.lang.security.ConfPolicy get$confPol();
    
    public fabric.lang.security.IntegPolicy get$integPol();
    
    public fabric.lang.security.SecretKeyObject get$keyObject();
    
    public boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.Label join(fabric.worker.Store store,
                                           fabric.lang.security.Label l);
    
    public fabric.lang.security.Label join(fabric.lang.security.Label l);
    
    public fabric.lang.security.Label meet(fabric.worker.Store store,
                                           fabric.lang.security.Label l);
    
    public fabric.lang.security.Label meet(fabric.lang.security.Label l);
    
    public fabric.lang.security.ConfPolicy confPolicy();
    
    public fabric.lang.security.IntegPolicy integPolicy();
    
    public fabric.lang.security.SecretKeyObject keyObject();
    
    public void clobberThisPlaceholders(Principal replacement);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PairLabel
    {
        
        native public fabric.lang.security.ConfPolicy get$confPol();
        
        native public fabric.lang.security.IntegPolicy get$integPol();
        
        native public fabric.lang.security.SecretKeyObject get$keyObject();
        
        native public boolean relabelsTo(fabric.lang.security.Label arg1,
                                         java.util.Set arg2);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object arg1);
        
        final native public java.lang.String toString();
        
        final native public fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2);
        
        final native public fabric.lang.security.Label join(
          fabric.lang.security.Label arg1);
        
        native public fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2);
        
        native public fabric.lang.security.Label meet(
          fabric.lang.security.Label arg1);
        
        native public fabric.lang.security.ConfPolicy confPolicy();
        
        native public fabric.lang.security.IntegPolicy integPolicy();
        
        native public fabric.lang.security.SecretKeyObject keyObject();
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        public _Proxy(PairLabel._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PairLabel
    {
        
        native public fabric.lang.security.ConfPolicy get$confPol();
        
        native public fabric.lang.security.IntegPolicy get$integPol();
        
        native public fabric.lang.security.SecretKeyObject get$keyObject();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.ConfPolicy confPol,
                     fabric.lang.security.IntegPolicy integPol) {
            super($location);
        }
        
        native public boolean relabelsTo(fabric.lang.security.Label l,
                                         java.util.Set s);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object o);
        
        final native public java.lang.String toString();
        
        final native public fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l);
        
        final native public fabric.lang.security.Label join(
          fabric.lang.security.Label l);
        
        native public fabric.lang.security.Label meet(
          fabric.worker.Store store, fabric.lang.security.Label l);
        
        native public fabric.lang.security.Label meet(
          fabric.lang.security.Label l);
        
        native public fabric.lang.security.ConfPolicy confPolicy();
        
        native public fabric.lang.security.IntegPolicy integPolicy();
        
        native public fabric.lang.security.SecretKeyObject keyObject();
        
        native public void clobberThisPlaceholders(Principal replacement);
        
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
          implements fabric.lang.security.PairLabel._Static
        {
            
            public _Proxy(fabric.lang.security.PairLabel._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PairLabel._Static
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
