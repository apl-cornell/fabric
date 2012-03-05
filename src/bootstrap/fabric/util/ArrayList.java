package fabric.util;

public interface ArrayList
  extends fabric.util.List, fabric.util.RandomAccess, fabric.util.AbstractList
{
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.lang.arrays.ObjectArray get$data();
    
    public fabric.lang.arrays.ObjectArray set$data(
      fabric.lang.arrays.ObjectArray val);
    
    public fabric.util.ArrayList fabric$util$ArrayList$(int capacity);
    
    public fabric.util.ArrayList fabric$util$ArrayList$();
    
    public fabric.util.ArrayList fabric$util$ArrayList$(
      fabric.util.Collection c);
    
    public void trimToSize();
    
    public void ensureCapacity(int minCapacity);
    
    public int size();
    
    public boolean isEmpty();
    
    public boolean contains(fabric.lang.Object e);
    
    public int indexOf(fabric.lang.Object e);
    
    public int lastIndexOf(fabric.lang.Object e);
    
    public fabric.lang.Object get(int index);
    
    public fabric.lang.Object set(int index, fabric.lang.Object e);
    
    public boolean add(fabric.lang.Object e);
    
    public void add(int index, fabric.lang.Object e);
    
    public fabric.lang.Object remove(int index);
    
    public void clear();
    
    public boolean addAll(fabric.util.Collection c);
    
    public boolean addAll(int index, fabric.util.Collection c);
    
    public void removeRange(int fromIndex, int toIndex);
    
    public boolean removeAllInternal(fabric.util.Collection c);
    
    public boolean retainAllInternal(fabric.util.Collection c);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.ArrayList
    {
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.lang.arrays.ObjectArray get$data();
        
        native public fabric.lang.arrays.ObjectArray set$data(
          fabric.lang.arrays.ObjectArray val);
        
        native public fabric.util.ArrayList fabric$util$ArrayList$(int arg1);
        
        native public fabric.util.ArrayList fabric$util$ArrayList$();
        
        native public fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection arg1);
        
        native public void trimToSize();
        
        native public void ensureCapacity(int arg1);
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public boolean removeAllInternal(fabric.util.Collection arg1);
        
        native public boolean retainAllInternal(fabric.util.Collection arg1);
        
        native public fabric.lang.Object $initLabels();
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        native public fabric.util.Iterator iterator();
        
        public _Proxy(ArrayList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.ArrayList
    {
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.lang.arrays.ObjectArray get$data();
        
        native public fabric.lang.arrays.ObjectArray set$data(
          fabric.lang.arrays.ObjectArray val);
        
        native public fabric.util.ArrayList fabric$util$ArrayList$(
          int capacity);
        
        native public fabric.util.ArrayList fabric$util$ArrayList$();
        
        native public fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection c);
        
        native public void trimToSize();
        
        native public void ensureCapacity(int minCapacity);
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public boolean contains(fabric.lang.Object e);
        
        native public int indexOf(fabric.lang.Object e);
        
        native public int lastIndexOf(fabric.lang.Object e);
        
        native public fabric.lang.Object get(int index);
        
        native public fabric.lang.Object set(int index, fabric.lang.Object e);
        
        native public boolean add(fabric.lang.Object e);
        
        native public void add(int index, fabric.lang.Object e);
        
        native public fabric.lang.Object remove(int index);
        
        native public void clear();
        
        native public boolean addAll(fabric.util.Collection c);
        
        native public boolean addAll(int index, fabric.util.Collection c);
        
        native public void removeRange(int fromIndex, int toIndex);
        
        native private void checkBoundInclusive(int index);
        
        native private void checkBoundExclusive(int index);
        
        native public boolean removeAllInternal(fabric.util.Collection c);
        
        native public boolean retainAllInternal(fabric.util.Collection c);
        
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
        
        public long get$serialVersionUID();
        
        public int get$DEFAULT_CAPACITY();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.ArrayList._Static
        {
            
            native public long get$serialVersionUID();
            
            native public int get$DEFAULT_CAPACITY();
            
            public _Proxy(fabric.util.ArrayList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.ArrayList._Static
        {
            
            native public long get$serialVersionUID();
            
            native public int get$DEFAULT_CAPACITY();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
