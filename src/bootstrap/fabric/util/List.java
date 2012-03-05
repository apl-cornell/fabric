package fabric.util;

public interface List extends fabric.util.Collection, fabric.lang.Object {
    
    void add(int index, fabric.lang.Object o);
    
    boolean add(fabric.lang.Object o);
    
    boolean addAll(int index, fabric.util.Collection c);
    
    boolean addAll(fabric.util.Collection c);
    
    void clear();
    
    boolean contains(fabric.lang.Object o);
    
    boolean containsAll(fabric.util.Collection c);
    
    boolean equals(fabric.lang.Object o);
    
    fabric.lang.Object get(int index);
    
    int hashCode();
    
    int indexOf(fabric.lang.Object o);
    
    boolean isEmpty();
    
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    int lastIndexOf(fabric.lang.Object o);
    
    fabric.util.ListIterator listIterator(fabric.worker.Store store);
    
    fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);
    
    fabric.lang.Object remove(int index);
    
    boolean remove(fabric.lang.Object o);
    
    boolean removeAll(fabric.util.Collection c);
    
    boolean retainAll(fabric.util.Collection c);
    
    fabric.lang.Object set(int index, fabric.lang.Object o);
    
    int size();
    
    fabric.util.List subList(int fromIndex, int toIndex);
    
    fabric.lang.arrays.ObjectArray toArray();
    
    fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.List
    {
        
        native public void add(int arg1, fabric.lang.Object arg2);
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public boolean addAll(int arg1, fabric.util.Collection arg2);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public fabric.lang.Object get(int arg1);
        
        native public int hashCode();
        
        native public int indexOf(fabric.lang.Object arg1);
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public int lastIndexOf(fabric.lang.Object arg1);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1, int arg2);
        
        native public fabric.lang.Object remove(int arg1);
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);
        
        native public int size();
        
        native public fabric.util.List subList(int arg1, int arg2);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        native public fabric.util.Iterator iterator();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
