package fabric.util;


public interface Iterator extends fabric.lang.Object {
    
    boolean hasNext();
    
    fabric.lang.JifObject next() throws fabric.util.NoSuchElementException;
    
    void remove() throws java.lang.IllegalStateException;
    
    fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator
    {
        
        native public boolean hasNext();
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public fabric.lang.security.Label jif$getfabric_util_Iterator_L(
          );
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
