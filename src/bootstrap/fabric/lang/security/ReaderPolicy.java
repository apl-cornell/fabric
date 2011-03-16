package fabric.lang.security;

public interface ReaderPolicy
  extends fabric.lang.security.ConfPolicy, fabric.lang.security.AbstractPolicy
{
    
    public fabric.lang.security.Principal get$owner();
    
    public fabric.lang.security.Principal get$reader();
    
    public fabric.lang.security.Principal owner();
    
    public fabric.lang.security.Principal reader();
    
    public boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.lang.security.ConfPolicy p, java.util.Set s);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.lang.security.ConfPolicy p, java.util.Set s);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.lang.security.ConfPolicy p);
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.ReaderPolicy
    {
        
        native public fabric.lang.security.Principal get$owner();
        
        native public fabric.lang.security.Principal get$reader();
        
        native public fabric.lang.security.Principal owner();
        
        native public fabric.lang.security.Principal reader();
        
        native public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         java.util.Set arg2);
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy arg1);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy arg1);
        
        public _Proxy(ReaderPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.ReaderPolicy
    {
        
        native public fabric.lang.security.Principal get$owner();
        
        native public fabric.lang.security.Principal get$reader();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     fabric.lang.security.Principal owner,
                     fabric.lang.security.Principal reader) {
            super($location, $label);
        }
        
        native public fabric.lang.security.Principal owner();
        
        native public fabric.lang.security.Principal reader();
        
        native public boolean relabelsTo(fabric.lang.security.Policy p,
                                         java.util.Set s);
        
        native public int hashCode();
        
        native public boolean equals(fabric.lang.Object o);
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy p, java.util.Set s);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy p);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy p, java.util.Set s);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy p);
        
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
          implements fabric.lang.security.ReaderPolicy._Static
        {
            
            public _Proxy(fabric.lang.security.ReaderPolicy._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ReaderPolicy._Static
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
