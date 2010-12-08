package fabric.lang;

public interface ToStringable extends fabric.lang.Object {
    
    java.lang.String toString();
    
    fabric.lang.security.Label jif$getfabric_lang_ToStringable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.ToStringable
    {
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
