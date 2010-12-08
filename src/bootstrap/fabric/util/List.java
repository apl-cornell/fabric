package fabric.util;


public interface List extends fabric.util.Collection, fabric.lang.Object {
    
    fabric.lang.JifObject get(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    fabric.lang.JifObject set(final int index,
                              final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    void add(final int index, final fabric.lang.JifObject element)
          throws java.lang.IndexOutOfBoundsException;
    
    fabric.lang.JifObject remove(final int index)
          throws java.lang.IndexOutOfBoundsException;
    
    int indexOf(final fabric.lang.JifObject o);
    
    int lastIndexOf(final fabric.lang.JifObject o);
    
    int indexOf(final fabric.lang.security.Label lbl,
                final fabric.lang.JifObject o);
    
    int lastIndexOf(final fabric.lang.security.Label lbl,
                    final fabric.lang.JifObject o);
    
    fabric.lang.security.Label jif$getfabric_util_List_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.List
    {
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject set(int arg1,
                                                fabric.lang.JifObject arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public void add(int arg1, fabric.lang.JifObject arg2)
              throws java.lang.IndexOutOfBoundsException;
        
        native public fabric.lang.JifObject remove(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public int indexOf(fabric.lang.JifObject arg1);
        
        native public int lastIndexOf(fabric.lang.JifObject arg1);
        
        native public int indexOf(fabric.lang.security.Label arg1,
                                  fabric.lang.JifObject arg2);
        
        native public int lastIndexOf(fabric.lang.security.Label arg1,
                                      fabric.lang.JifObject arg2);
        
        native public fabric.lang.security.Label jif$getfabric_util_List_L();
        
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
