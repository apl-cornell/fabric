package fabric.util;

public interface Comparator extends fabric.lang.Object {
    
    int compare(fabric.lang.Object o1, fabric.lang.Object o2);
    
    boolean equals(fabric.lang.Object obj);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Comparator
    {
        
        native public int compare(fabric.lang.Object arg1,
                                  fabric.lang.Object arg2);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
