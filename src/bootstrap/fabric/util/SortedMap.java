package fabric.util;

public interface SortedMap extends fabric.util.Map, fabric.lang.Object {
    
    fabric.util.Comparator comparator();
    
    fabric.lang.Object firstKey();
    
    fabric.util.SortedMap headMap(fabric.lang.Object toKey);
    
    fabric.lang.Object lastKey();
    
    fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                 fabric.lang.Object toKey);
    
    fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.SortedMap
    {
        
        native public fabric.util.Comparator comparator();
        
        native public fabric.lang.Object firstKey();
        
        native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);
        
        native public fabric.lang.Object lastKey();
        
        native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);
        
        native public void clear();
        
        native public boolean containsKey(fabric.lang.Object arg1);
        
        native public boolean containsValue(fabric.lang.Object arg1);
        
        native public fabric.util.Set entrySet();
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public fabric.lang.Object get(fabric.lang.Object arg1);
        
        native public fabric.lang.Object put(fabric.lang.Object arg1,
                                             fabric.lang.Object arg2);
        
        native public int hashCode();
        
        native public boolean isEmpty();
        
        native public fabric.util.Set keySet();
        
        native public void putAll(fabric.util.Map arg1);
        
        native public fabric.lang.Object remove(fabric.lang.Object arg1);
        
        native public int size();
        
        native public fabric.util.Collection values();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
