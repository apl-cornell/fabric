package fabric.lang;

public interface Codebase extends fabric.lang.Object {
    
    public fabric.util.Map get$classes();
    
    public fabric.util.Map set$classes(fabric.util.Map val);
    
    public fabric.lang.FClass resolveClassName(java.lang.String name);
    
    public void insertClass(java.lang.String name, fabric.lang.FClass fcls);
    
    public fabric.util.Map getClasses();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.Codebase
    {
        
        native public fabric.util.Map get$classes();
        
        native public fabric.util.Map set$classes(fabric.util.Map val);
        
        native public fabric.lang.FClass resolveClassName(
          java.lang.String arg1);
        
        native public void insertClass(java.lang.String arg1,
                                       fabric.lang.FClass arg2);
        
        native public fabric.util.Map getClasses();
        
        public _Proxy(Codebase._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.Codebase
    {
        
        native public fabric.util.Map get$classes();
        
        native public fabric.util.Map set$classes(fabric.util.Map val);
        
        native public fabric.lang.FClass resolveClassName(
          java.lang.String name);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     fabric.util.Map classes) {
            super($location, $label);
        }
        
        native public void insertClass(java.lang.String name,
                                       fabric.lang.FClass fcls);
        
        native public fabric.util.Map getClasses();
        
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
          implements fabric.lang.Codebase._Static
        {
            
            public _Proxy(fabric.lang.Codebase._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.Codebase._Static
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
