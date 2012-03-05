package fabric.util;

public interface Iterable extends fabric.lang.Object {
    
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    fabric.util.Iterator iterator();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterable
    {
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public fabric.util.Iterator iterator();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
