package fabric.util;


public interface JifUtil extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.JifUtil
    {
        
        native public static int hashCode(fabric.lang.security.Label arg1,
                                          fabric.lang.Object arg2);
        
        native public static java.lang.String toString(
          fabric.lang.security.Label arg1, fabric.lang.Object arg2);
        
        native public static boolean equals(fabric.lang.Object arg1,
                                            fabric.lang.security.Label arg2,
                                            fabric.lang.Object arg3);
        
        native public static fabric.lang.Object unwrap(
          fabric.lang.JifObject arg1);
        
        public _Proxy(JifUtil._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.JifUtil
    {
        
        native public static int hashCode(fabric.lang.security.Label lbl,
                                          fabric.lang.Object o);
        
        native public static java.lang.String toString(
          fabric.lang.security.Label lbl, fabric.lang.Object o);
        
        native public static boolean equals(fabric.lang.Object o1,
                                            fabric.lang.security.Label lbl,
                                            fabric.lang.Object o2);
        
        native public static fabric.lang.Object unwrap(
          fabric.lang.JifObject jobj);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
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
          implements fabric.util.JifUtil._Static
        {
            
            public _Proxy(fabric.util.JifUtil._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.JifUtil._Static
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
