package fabric.util;


public interface Collection extends fabric.lang.JifObject, fabric.lang.Object {
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final fabric.lang.JifObject o);
    
    boolean contains(final fabric.lang.security.Label lbl,
                     final fabric.lang.JifObject o);
    
    fabric.util.Iterator iterator();
    
    boolean add(final fabric.lang.JifObject o)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    boolean remove(final fabric.lang.JifObject o);
    
    boolean containsAll(final fabric.util.Collection c)
          throws java.lang.NullPointerException;
    
    boolean addAll(final fabric.util.Collection c)
          throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    boolean removeAll(final fabric.util.Collection c);
    
    boolean retainAll(final fabric.util.Collection c);
    
    boolean retainAll(final fabric.lang.security.Label lbl,
                      final fabric.util.Collection c);
    
    void clear();
    
    fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    boolean add(final java.lang.String o) throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    boolean remove(final java.lang.String o);
    
    boolean contains(final java.lang.String o);
    
    boolean contains(final fabric.lang.security.Label lbl,
                     final java.lang.String o);
    
    fabric.lang.security.Label jif$getfabric_util_Collection_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collection
    {
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public fabric.util.Iterator iterator();
        
        native public boolean add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean addAll(fabric.util.Collection arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.lang.security.Label arg1,
                                        fabric.util.Collection arg2);
        
        native public void clear();
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean add(java.lang.String arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(java.lang.String arg1);
        
        native public boolean contains(java.lang.String arg1);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       java.lang.String arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_util_Collection_L();
        
        native public fabric.lang.security.Label jif$getfabric_lang_JifObject_L(
          );
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public int hashCode();
        
        native public fabric.lang.security.Label jif$getfabric_lang_Hashable_L(
          );
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
