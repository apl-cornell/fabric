package fabric.util;

public interface Set extends fabric.util.Collection, fabric.lang.Object {
    
    boolean add(fabric.lang.Object o);
    
    boolean addAll(fabric.util.Collection c);
    
    void clear();
    
    boolean contains(fabric.lang.Object o);
    
    boolean containsAll(fabric.util.Collection c);
    
    boolean equals(fabric.lang.Object o);
    
    int hashCode();
    
    boolean isEmpty();
    
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    fabric.util.Iterator iterator();
    
    boolean remove(fabric.lang.Object o);
    
    boolean removeAll(fabric.util.Collection c);
    
    boolean retainAll(fabric.util.Collection c);
    
    int size();
    
    fabric.lang.arrays.ObjectArray toArray();
    
    fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Set
    {
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public fabric.util.Iterator iterator();
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public int size();
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
