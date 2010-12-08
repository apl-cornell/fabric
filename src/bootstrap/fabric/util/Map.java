package fabric.util;


public interface Map extends fabric.lang.JifObject, fabric.lang.Object {
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final fabric.lang.JifObject key);
    
    boolean containsKey(final fabric.lang.security.Label lbl,
                        final fabric.lang.JifObject key);
    
    fabric.lang.JifObject get(final fabric.lang.JifObject key);
    
    fabric.lang.JifObject get(final fabric.lang.security.Label lbl,
                              final fabric.lang.JifObject key);
    
    fabric.lang.JifObject put(final fabric.lang.JifObject key,
                              final fabric.lang.JifObject value);
    
    fabric.lang.JifObject remove(final fabric.lang.JifObject key);
    
    boolean containsKey(final java.lang.String key);
    
    fabric.lang.JifObject get(final java.lang.String key);
    
    fabric.lang.JifObject get(final fabric.lang.security.Label lbl,
                              final java.lang.String key);
    
    fabric.lang.JifObject put(final java.lang.String key,
                              final fabric.lang.JifObject value);
    
    fabric.lang.JifObject remove(final java.lang.String key);
    
    void clear();
    
    fabric.util.Set entrySet();
    
    fabric.lang.security.Label jif$getfabric_util_Map_K();
    
    fabric.lang.security.Label jif$getfabric_util_Map_V();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Map
    {
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public boolean containsKey(fabric.lang.JifObject arg1);
        
        native public boolean containsKey(fabric.lang.security.Label arg1,
                                          fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject get(fabric.lang.JifObject arg1);
        
        native public fabric.lang.JifObject get(fabric.lang.security.Label arg1,
                                                fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject put(fabric.lang.JifObject arg1,
                                                fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject remove(fabric.lang.JifObject arg1);
        
        native public boolean containsKey(java.lang.String arg1);
        
        native public fabric.lang.JifObject get(java.lang.String arg1);
        
        native public fabric.lang.JifObject get(fabric.lang.security.Label arg1,
                                                java.lang.String arg2);
        
        native public fabric.lang.JifObject put(java.lang.String arg1,
                                                fabric.lang.JifObject arg2);
        
        native public fabric.lang.JifObject remove(java.lang.String arg1);
        
        native public void clear();
        
        native public fabric.util.Set entrySet();
        
        native public fabric.lang.security.Label jif$getfabric_util_Map_K();
        
        native public fabric.lang.security.Label jif$getfabric_util_Map_V();
        
        native public fabric.lang.security.Label jif$getfabric_lang_JifObject_L(
          );
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public int hashCode();
        
        native public fabric.lang.security.Label jif$getfabric_lang_Hashable_L(
          );
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
