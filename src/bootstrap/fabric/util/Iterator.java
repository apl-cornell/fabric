package fabric.util;

public interface Iterator extends fabric.lang.Object {
    
    boolean hasNext();
    
    fabric.lang.Object next();
    
    void remove();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator
    {
        
        native public boolean hasNext();
        
        native public fabric.lang.Object next();
        
        native public void remove();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
