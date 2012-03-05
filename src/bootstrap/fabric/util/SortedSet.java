package fabric.util;

public interface SortedSet extends fabric.util.Set, fabric.lang.Object {
    
    fabric.util.Comparator comparator();
    
    fabric.lang.Object first();
    
    fabric.util.SortedSet headSet(fabric.lang.Object toElement);
    
    fabric.lang.Object last();
    
    fabric.util.SortedSet subSet(fabric.lang.Object fromElement,
                                 fabric.lang.Object toElement);
    
    fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.SortedSet
    {
        
        native public fabric.util.Comparator comparator();
        
        native public fabric.lang.Object first();
        
        native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);
        
        native public fabric.lang.Object last();
        
        native public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);
        
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
