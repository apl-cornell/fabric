package fabric.util;

public interface AbstractCollection
  extends fabric.util.Collection, fabric.lang.Object
{
    
    public fabric.util.AbstractCollection fabric$util$AbstractCollection$();
    
    abstract public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public fabric.util.Iterator iterator();
    
    abstract public int size();
    
    public boolean add(fabric.lang.Object o);
    
    public boolean addAll(fabric.util.Collection c);
    
    public void clear();
    
    public boolean contains(fabric.lang.Object o);
    
    public boolean containsAll(fabric.util.Collection c);
    
    public boolean isEmpty();
    
    public boolean remove(fabric.lang.Object o);
    
    public boolean removeAll(fabric.util.Collection c);
    
    public boolean removeAllInternal(fabric.util.Collection c);
    
    public boolean retainAll(fabric.util.Collection c);
    
    public boolean retainAllInternal(fabric.util.Collection c);
    
    public fabric.lang.arrays.ObjectArray toArray();
    
    public fabric.lang.arrays.ObjectArray toArray(
      fabric.lang.arrays.ObjectArray a);
    
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractCollection
    {
        
        native public fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
        
        native public fabric.util.Iterator iterator();
        
        native public int size();
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean isEmpty();
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean removeAllInternal(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public boolean retainAllInternal(fabric.util.Collection arg1);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        native public java.lang.String toString();
        
        final native public static boolean equals(fabric.lang.Object arg1,
                                                  fabric.lang.Object arg2);
        
        final native public static int hashCode(fabric.lang.Object arg1);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        public _Proxy(AbstractCollection._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractCollection
    {
        
        native public fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        abstract public fabric.util.Iterator iterator(
          fabric.worker.Store store);
        
        native public fabric.util.Iterator iterator();
        
        abstract public int size();
        
        native public boolean add(fabric.lang.Object o);
        
        native public boolean addAll(fabric.util.Collection c);
        
        native public void clear();
        
        native public boolean contains(fabric.lang.Object o);
        
        native public boolean containsAll(fabric.util.Collection c);
        
        native public boolean isEmpty();
        
        native public boolean remove(fabric.lang.Object o);
        
        native public boolean removeAll(fabric.util.Collection c);
        
        native public boolean removeAllInternal(fabric.util.Collection c);
        
        native public boolean retainAll(fabric.util.Collection c);
        
        native public boolean retainAllInternal(fabric.util.Collection c);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);
        
        native public java.lang.String toString();
        
        final native public static boolean equals(fabric.lang.Object o1,
                                                  fabric.lang.Object o2);
        
        final native public static int hashCode(fabric.lang.Object o);
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractCollection._Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Proxy(fabric.util.AbstractCollection._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractCollection._Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
