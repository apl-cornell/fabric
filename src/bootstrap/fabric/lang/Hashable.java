package fabric.lang;

public interface Hashable extends fabric.lang.Object {
    
    int hashCode();
    
    fabric.lang.security.Label jif$getfabric_lang_Hashable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.Hashable
    {
        
        native public int hashCode();
        
        native public fabric.lang.security.Label jif$getfabric_lang_Hashable_L(
          );
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
