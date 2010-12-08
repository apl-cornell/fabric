package fabric.util;


public interface LinkedHashSet extends fabric.util.HashSet {
    
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
      final int initialCapacity, final float loadFactor)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
      final int initialCapacity)
          throws java.lang.IllegalArgumentException;
    
    public fabric.lang.security.Label get$jif$fabric_util_LinkedHashSet_L();
    
    public static class _Proxy extends fabric.util.HashSet._Proxy
      implements fabric.util.LinkedHashSet
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedHashSet_L();
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1, float arg2)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.LinkedHashSet
          jif$cast$fabric_util_LinkedHashSet(fabric.lang.security.Label arg1,
                                             java.lang.Object arg2);
        
        public _Proxy(LinkedHashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashSet._Impl
      implements fabric.util.LinkedHashSet
    {
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          final int initialCapacity, final float loadFactor)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          final int initialCapacity)
              throws java.lang.IllegalArgumentException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.LinkedHashSet
          jif$cast$fabric_util_LinkedHashSet(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedHashSet_L();
        
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
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.util.LinkedHashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
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
