package fabric.lang.security;

public interface SecretKeyObject extends fabric.lang.Object {
    
    public javax.crypto.SecretKey get$key();
    
    public javax.crypto.SecretKey set$key(javax.crypto.SecretKey val);
    
    public javax.crypto.SecretKey getKey();
    
    public javax.crypto.SecretKey rekey();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.SecretKeyObject
    {
        
        native public javax.crypto.SecretKey get$key();
        
        native public javax.crypto.SecretKey set$key(
          javax.crypto.SecretKey val);
        
        native public javax.crypto.SecretKey getKey();
        
        native public javax.crypto.SecretKey rekey();
        
        public _Proxy(SecretKeyObject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.SecretKeyObject
    {
        
        native public javax.crypto.SecretKey get$key();
        
        native public javax.crypto.SecretKey set$key(
          javax.crypto.SecretKey val);
        
        native public javax.crypto.SecretKey getKey();
        
        native public javax.crypto.SecretKey rekey();
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label, fabric.lang.security.Label accessLabel) {
            super($location, $label, accessLabel);
        }
        
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
          implements fabric.lang.security.SecretKeyObject._Static
        {
            
            public _Proxy(fabric.lang.security.SecretKeyObject._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.SecretKeyObject._Static
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
