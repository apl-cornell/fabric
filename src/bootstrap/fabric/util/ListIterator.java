package fabric.util;

public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {
    
    boolean hasNext();
    
    boolean hasPrevious();
    
    fabric.lang.Object next();
    
    fabric.lang.Object previous();
    
    int nextIndex();
    
    int previousIndex();
    
    void add(fabric.lang.Object o);
    
    void remove();
    
    void set(fabric.lang.Object o);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator
    {
        
        native public boolean hasNext();
        
        native public boolean hasPrevious();
        
        native public fabric.lang.Object next();
        
        native public fabric.lang.Object previous();
        
        native public int nextIndex();
        
        native public int previousIndex();
        
        native public void add(fabric.lang.Object arg1);
        
        native public void remove();
        
        native public void set(fabric.lang.Object arg1);
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
