package fabric.lang;

public interface IDComparable extends fabric.lang.Object {
    
    boolean equals(final fabric.lang.IDComparable obj);
    
    boolean equals(final fabric.lang.security.Label lbl,
                   final fabric.lang.IDComparable obj);
    
    fabric.lang.security.Label jif$getfabric_lang_IDComparable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.IDComparable
    {
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
