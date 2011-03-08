package fabric.lang;

public interface FClass extends fabric.lang.Object {
    
    public fabric.lang.arrays.byteArray get$bytecode();
    
    public fabric.lang.arrays.byteArray set$bytecode(
      fabric.lang.arrays.byteArray val);
    
    public fabric.lang.Codebase get$codebase();
    
    public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);
    
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    public java.lang.String get$source();
    
    public java.lang.String set$source(java.lang.String val);
    
    public fabric.util.Set get$dependencies();
    
    public fabric.util.Set set$dependencies(fabric.util.Set val);
    
    public java.lang.String getName();
    
    public java.lang.String getSource();
    
    public void setCodebase(fabric.lang.Codebase c);
    
    public fabric.lang.Codebase getCodebase();
    
    public fabric.lang.arrays.byteArray getBytecode();
    
    public fabric.util.Set dependencies();
    
    public void addDependency(java.lang.String dep);
    
    public boolean dependsOn(java.lang.String dep);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.arrays.byteArray get$bytecode();
        
        native public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val);
        
        native public fabric.lang.Codebase get$codebase();
        
        native public fabric.lang.Codebase set$codebase(
          fabric.lang.Codebase val);
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public java.lang.String get$source();
        
        native public java.lang.String set$source(java.lang.String val);
        
        native public fabric.util.Set get$dependencies();
        
        native public fabric.util.Set set$dependencies(fabric.util.Set val);
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public void setCodebase(fabric.lang.Codebase arg1);
        
        native public fabric.lang.Codebase getCodebase();
        
        native public fabric.lang.arrays.byteArray getBytecode();
        
        native public fabric.util.Set dependencies();
        
        native public void addDependency(java.lang.String arg1);
        
        native public boolean dependsOn(java.lang.String arg1);
        
        public _Proxy(FClass._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.arrays.byteArray get$bytecode();
        
        native public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val);
        
        native public fabric.lang.Codebase get$codebase();
        
        native public fabric.lang.Codebase set$codebase(
          fabric.lang.Codebase val);
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public java.lang.String get$source();
        
        native public java.lang.String set$source(java.lang.String val);
        
        native public fabric.util.Set get$dependencies();
        
        native public fabric.util.Set set$dependencies(fabric.util.Set val);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label, java.lang.String name,
                     fabric.lang.arrays.byteArray bytecode,
                     java.lang.String source) {
            super($location, $label);
        }
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public void setCodebase(fabric.lang.Codebase c);
        
        native public fabric.lang.Codebase getCodebase();
        
        native public fabric.lang.arrays.byteArray getBytecode();
        
        native public fabric.util.Set dependencies();
        
        native public void addDependency(java.lang.String dep);
        
        native public boolean dependsOn(java.lang.String dep);
        
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
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
