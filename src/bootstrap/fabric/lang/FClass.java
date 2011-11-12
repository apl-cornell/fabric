package fabric.lang;

public interface FClass extends fabric.lang.Object {
    
    public fabric.lang.Codebase get$codebase();
    
    public java.lang.String get$name();
    
    public java.lang.String get$source();
    
    public fabric.lang.arrays.byteArray get$bytecode();
        
    public fabric.lang.Codebase getCodebase();
    
    public java.lang.String getName();
    
    public java.lang.String getSource();
    
    public fabric.lang.arrays.byteArray getBytecode();
        
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.Codebase get$codebase();
        
        native public java.lang.String get$name();
        
        native public java.lang.String get$source();
        
        native public fabric.lang.arrays.byteArray get$bytecode();
                
        native public fabric.lang.Codebase getCodebase();
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public fabric.lang.arrays.byteArray getBytecode();
                
        public _Proxy(FClass._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.Codebase get$codebase();
        
        native public java.lang.String get$name();
        
        native public java.lang.String get$source();
        
        native public fabric.lang.arrays.byteArray get$bytecode();
                
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     fabric.lang.security.Label $accesslabel,
                     fabric.lang.Codebase codebase, java.lang.String name,
                     java.lang.String source,
                     fabric.lang.arrays.byteArray bytecode) {
            super($location, $label, $accesslabel);
        }
        
        native public fabric.lang.Codebase getCodebase();
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public fabric.lang.arrays.byteArray getBytecode();
                
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.FClass._Static
        {
            
            public _Proxy(fabric.lang.FClass._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.FClass._Static
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
