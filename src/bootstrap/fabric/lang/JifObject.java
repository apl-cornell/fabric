package fabric.lang;

public interface JifObject
  extends fabric.lang.IDComparable, fabric.lang.Hashable,
          fabric.lang.ToStringable, fabric.lang.Object
{
    
    fabric.lang.security.Label jif$getfabric_lang_JifObject_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.JifObject
    {
        
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
