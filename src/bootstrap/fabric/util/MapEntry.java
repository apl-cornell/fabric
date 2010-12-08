package fabric.util;


public interface MapEntry extends fabric.lang.JifObject, fabric.lang.Object {
    
    fabric.lang.JifObject getKey();
    
    fabric.lang.JifObject getValue();
    
    fabric.lang.JifObject setValue(final fabric.lang.JifObject value);
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_K();
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_V();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.MapEntry
    {
        
        native public fabric.lang.JifObject getKey();
        
        native public fabric.lang.JifObject getValue();
        
        native public fabric.lang.JifObject setValue(
          fabric.lang.JifObject arg1);
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_K(
          );
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_V(
          );
        
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
