package fabric.util;

public interface Map extends fabric.lang.Object {
    
    void clear();
    
    boolean containsKey(fabric.lang.Object key);
    
    boolean containsValue(fabric.lang.Object value);
    
    fabric.util.Set entrySet();
    
    boolean equals(fabric.lang.Object o);
    
    fabric.lang.Object get(fabric.lang.Object key);
    
    fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
    
    int hashCode();
    
    boolean isEmpty();
    
    fabric.util.Set keySet();
    
    void putAll(fabric.util.Map m);
    
    fabric.lang.Object remove(fabric.lang.Object key);
    
    int size();
    
    fabric.util.Collection values();
    
    public static interface Entry extends fabric.lang.Object {
        
        fabric.lang.Object getKey();
        
        fabric.lang.Object getValue();
        
        fabric.lang.Object setValue(fabric.lang.Object value);
        
        int hashCode();
        
        boolean equals(fabric.lang.Object o);
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Map.Entry
        {
            
            native public fabric.lang.Object getKey();
            
            native public fabric.lang.Object getValue();
            
            native public fabric.lang.Object setValue(fabric.lang.Object arg1);
            
            native public int hashCode();
            
            native public boolean equals(fabric.lang.Object arg1);
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Map
    {
        
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
