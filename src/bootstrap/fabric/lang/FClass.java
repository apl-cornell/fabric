package fabric.lang;

public interface FClass extends fabric.lang.Object {
    
    public fabric.lang.Codebase get$codebase();
    
    public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);
    
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    public java.lang.String get$source();
    
    public java.lang.String set$source(java.lang.String val);
    
    public fabric.lang.arrays.byteArray get$bytecode();
    
    public fabric.lang.arrays.byteArray set$bytecode(
      fabric.lang.arrays.byteArray val);
    
    public fabric.lang.FClass fabric$lang$FClass$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy,
      fabric.lang.Codebase codebase, java.lang.String name,
      java.lang.String source, fabric.lang.arrays.byteArray bytecode);
    
    public fabric.lang.Codebase getCodebase();
    
    public java.lang.String getName();
    
    public java.lang.String getSource();
    
    public fabric.lang.arrays.byteArray getBytecode();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.Codebase get$codebase();
        
        native public fabric.lang.Codebase set$codebase(
          fabric.lang.Codebase val);
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public java.lang.String get$source();
        
        native public java.lang.String set$source(java.lang.String val);
        
        native public fabric.lang.arrays.byteArray get$bytecode();
        
        native public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val);
        
        native public fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.Codebase arg3, java.lang.String arg4,
          java.lang.String arg5, fabric.lang.arrays.byteArray arg6);
        
        native public fabric.lang.Codebase getCodebase();
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public fabric.lang.arrays.byteArray getBytecode();
        
        native public fabric.lang.Object $initLabels();
        
        public _Proxy(FClass._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.FClass
    {
        
        native public fabric.lang.Codebase get$codebase();
        
        native public fabric.lang.Codebase set$codebase(
          fabric.lang.Codebase val);
        
        native public java.lang.String get$name();
        
        native public java.lang.String set$name(java.lang.String val);
        
        native public java.lang.String get$source();
        
        native public java.lang.String set$source(java.lang.String val);
        
        native public fabric.lang.arrays.byteArray get$bytecode();
        
        native public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val);
        
        native public fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          fabric.lang.Codebase codebase, java.lang.String name,
          java.lang.String source, fabric.lang.arrays.byteArray bytecode);
        
        native public fabric.lang.Codebase getCodebase();
        
        native public java.lang.String getName();
        
        native public java.lang.String getSource();
        
        native public fabric.lang.arrays.byteArray getBytecode();
        
        native public fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
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
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
