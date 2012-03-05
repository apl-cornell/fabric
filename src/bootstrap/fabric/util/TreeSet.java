package fabric.util;

public interface TreeSet extends fabric.util.SortedSet, fabric.util.AbstractSet
{
    
    public fabric.util.SortedMap get$map();
    
    public fabric.util.SortedMap set$map(fabric.util.SortedMap val);
    
    public fabric.util.TreeSet fabric$util$TreeSet$();
    
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Comparator comparator);
    
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Collection collection);
    
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.SortedSet sortedSet);
    
    public boolean add(fabric.lang.Object obj);
    
    public boolean addAll(fabric.util.Collection c);
    
    public void clear();
    
    public fabric.util.Comparator comparator();
    
    public boolean contains(fabric.lang.Object obj);
    
    public fabric.lang.Object first();
    
    public fabric.util.SortedSet headSet(fabric.lang.Object to);
    
    public boolean isEmpty();
    
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public fabric.lang.Object last();
    
    public boolean remove(fabric.lang.Object obj);
    
    public int size();
    
    public fabric.util.SortedSet subSet(fabric.lang.Object from,
                                        fabric.lang.Object to);
    
    public fabric.util.SortedSet tailSet(fabric.lang.Object from);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.TreeSet
    {
        
        native public fabric.util.SortedMap get$map();
        
        native public fabric.util.SortedMap set$map(fabric.util.SortedMap val);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$();
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator arg1);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Collection arg1);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedSet arg1);
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public void clear();
        
        native public fabric.util.Comparator comparator();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public fabric.lang.Object first();
        
        native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public fabric.lang.Object last();
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public int size();
        
        native public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);
        
        native public fabric.lang.Object $initLabels();
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        public _Proxy(TreeSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.TreeSet
    {
        
        native public fabric.util.SortedMap get$map();
        
        native public fabric.util.SortedMap set$map(fabric.util.SortedMap val);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$();
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator comparator);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Collection collection);
        
        native public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedSet sortedSet);
        
        native private fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedMap backingMap);
        
        native public boolean add(fabric.lang.Object obj);
        
        native public boolean addAll(fabric.util.Collection c);
        
        native public void clear();
        
        native public fabric.util.Comparator comparator();
        
        native public boolean contains(fabric.lang.Object obj);
        
        native public fabric.lang.Object first();
        
        native public fabric.util.SortedSet headSet(fabric.lang.Object to);
        
        native public boolean isEmpty();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        native public fabric.lang.Object last();
        
        native public boolean remove(fabric.lang.Object obj);
        
        native public int size();
        
        native public fabric.util.SortedSet subSet(fabric.lang.Object from,
                                                   fabric.lang.Object to);
        
        native public fabric.util.SortedSet tailSet(fabric.lang.Object from);
        
        native public fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.TreeSet._Static
        {
            
            public _Proxy(fabric.util.TreeSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeSet._Static
        {
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
