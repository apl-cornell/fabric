package fabric.util;


public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {
    
    boolean hasPrevious();
    
    fabric.lang.JifObject previous() throws fabric.util.NoSuchElementException;
    
    int nextIndex();
    
    int previousIndex();
    
    void set(final fabric.lang.JifObject o)
          throws java.lang.IllegalStateException;
    
    void add(final fabric.lang.JifObject o) throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    fabric.lang.security.Label jif$getfabric_util_ListIterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator
    {
        
        native public boolean hasPrevious();
        
        native public fabric.lang.JifObject previous()
              throws fabric.util.NoSuchElementException;
        
        native public int nextIndex();
        
        native public int previousIndex();
        
        native public void set(fabric.lang.JifObject arg1)
              throws java.lang.IllegalStateException;
        
        native public void add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public fabric.lang.security.Label
          jif$getfabric_util_ListIterator_L();
        
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
