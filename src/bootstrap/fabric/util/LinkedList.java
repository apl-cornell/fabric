package fabric.util;

public interface LinkedList
  extends fabric.util.List, fabric.util.AbstractSequentialList
{
    
    public fabric.util.LinkedList.Entry get$first();
    
    public fabric.util.LinkedList.Entry set$first(
      fabric.util.LinkedList.Entry val);
    
    public fabric.util.LinkedList.Entry get$last();
    
    public fabric.util.LinkedList.Entry set$last(
      fabric.util.LinkedList.Entry val);
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public static interface Entry extends fabric.lang.Object {
        
        public fabric.lang.Object get$data();
        
        public fabric.lang.Object set$data(fabric.lang.Object val);
        
        public fabric.util.LinkedList.Entry get$next();
        
        public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$previous();
        
        public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry fabric$util$LinkedList$Entry$(
          fabric.lang.Object data);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedList.Entry
        {
            
            native public fabric.lang.Object get$data();
            
            native public fabric.lang.Object set$data(fabric.lang.Object val);
            
            native public fabric.util.LinkedList.Entry get$next();
            
            native public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$previous();
            
            native public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry
              fabric$util$LinkedList$Entry$(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Entry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList.Entry
        {
            
            native public fabric.lang.Object get$data();
            
            native public fabric.lang.Object set$data(fabric.lang.Object val);
            
            native public fabric.util.LinkedList.Entry get$next();
            
            native public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$previous();
            
            native public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry
              fabric$util$LinkedList$Entry$(fabric.lang.Object data);
            
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
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedList.Entry._Static
            {
                
                public _Proxy(fabric.util.LinkedList.Entry._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedList.Entry._Static
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
    
    
    public fabric.util.LinkedList.Entry getEntry(int n);
    
    public void removeEntry(fabric.util.LinkedList.Entry e);
    
    public fabric.util.LinkedList fabric$util$LinkedList$();
    
    public fabric.util.LinkedList fabric$util$LinkedList$(
      fabric.util.Collection c);
    
    public fabric.lang.Object getFirst();
    
    public fabric.lang.Object getLast();
    
    public fabric.lang.Object removeFirst();
    
    public fabric.lang.Object removeLast();
    
    public void addFirst(fabric.lang.Object o);
    
    public void addLast(fabric.lang.Object o);
    
    public boolean contains(fabric.lang.Object o);
    
    public int size();
    
    public boolean add(fabric.lang.Object o);
    
    public boolean remove(fabric.lang.Object o);
    
    public boolean addAll(fabric.util.Collection c);
    
    public boolean addAll(int index, fabric.util.Collection c);
    
    public void clear();
    
    public fabric.lang.Object get(int index);
    
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    public void add(int index, fabric.lang.Object o);
    
    public fabric.lang.Object remove(int index);
    
    public int indexOf(fabric.lang.Object o);
    
    public int lastIndexOf(fabric.lang.Object o);
    
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                 int index);
    
    public fabric.lang.arrays.ObjectArray toArray();
    
    public fabric.lang.arrays.ObjectArray toArray(
      fabric.lang.arrays.ObjectArray a);
    
    public static interface LinkedListItr
      extends fabric.util.ListIterator, fabric.lang.Object
    {
        
        public fabric.util.LinkedList get$out$();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public fabric.util.LinkedList.Entry get$next();
        
        public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$previous();
        
        public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$lastReturned();
        
        public fabric.util.LinkedList.Entry set$lastReturned(
          fabric.util.LinkedList.Entry val);
        
        public int get$position();
        
        public int set$position(int val);
        
        public int postInc$position();
        
        public int postDec$position();
        
        public fabric.util.LinkedList.LinkedListItr
          fabric$util$LinkedList$LinkedListItr$(int index);
        
        public int nextIndex();
        
        public int previousIndex();
        
        public boolean hasNext();
        
        public boolean hasPrevious();
        
        public fabric.lang.Object next();
        
        public fabric.lang.Object previous();
        
        public void remove();
        
        public void add(fabric.lang.Object o);
        
        public void set(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedList.LinkedListItr
        {
            
            native public fabric.util.LinkedList get$out$();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.LinkedList.Entry get$next();
            
            native public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$previous();
            
            native public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$lastReturned();
            
            native public fabric.util.LinkedList.Entry set$lastReturned(
              fabric.util.LinkedList.Entry val);
            
            native public int get$position();
            
            native public int set$position(int val);
            
            native public int postInc$position();
            
            native public int postDec$position();
            
            native public fabric.util.LinkedList.LinkedListItr
              fabric$util$LinkedList$LinkedListItr$(int arg1);
            
            native public int nextIndex();
            
            native public int previousIndex();
            
            native public boolean hasNext();
            
            native public boolean hasPrevious();
            
            native public fabric.lang.Object next();
            
            native public fabric.lang.Object previous();
            
            native public void remove();
            
            native public void add(fabric.lang.Object arg1);
            
            native public void set(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(LinkedListItr._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList.LinkedListItr
        {
            
            native public fabric.util.LinkedList get$out$();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.LinkedList.Entry get$next();
            
            native public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$previous();
            
            native public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val);
            
            native public fabric.util.LinkedList.Entry get$lastReturned();
            
            native public fabric.util.LinkedList.Entry set$lastReturned(
              fabric.util.LinkedList.Entry val);
            
            native public int get$position();
            
            native public int set$position(int val);
            
            native public int postInc$position();
            
            native public int postDec$position();
            
            native public fabric.util.LinkedList.LinkedListItr
              fabric$util$LinkedList$LinkedListItr$(int index);
            
            native private void checkMod();
            
            native public int nextIndex();
            
            native public int previousIndex();
            
            native public boolean hasNext();
            
            native public boolean hasPrevious();
            
            native public fabric.lang.Object next();
            
            native public fabric.lang.Object previous();
            
            native public void remove();
            
            native public void add(fabric.lang.Object o);
            
            native public void set(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedList out$) {
                super($location);
            }
            
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
            
            native public void $copyAppStateFrom(
              fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedList.LinkedListItr._Static
            {
                
                public _Proxy(fabric.util.LinkedList.LinkedListItr._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedList.LinkedListItr._Static
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
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSequentialList._Proxy
      implements fabric.util.LinkedList
    {
        
        native public fabric.util.LinkedList.Entry get$first();
        
        native public fabric.util.LinkedList.Entry set$first(
          fabric.util.LinkedList.Entry val);
        
        native public fabric.util.LinkedList.Entry get$last();
        
        native public fabric.util.LinkedList.Entry set$last(
          fabric.util.LinkedList.Entry val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.LinkedList.Entry getEntry(int arg1);
        
        native public void removeEntry(fabric.util.LinkedList.Entry arg1);
        
        native public fabric.util.LinkedList fabric$util$LinkedList$();
        
        native public fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection arg1);
        
        native public fabric.lang.Object getFirst();
        
        native public fabric.lang.Object getLast();
        
        native public fabric.lang.Object removeFirst();
        
        native public fabric.lang.Object removeLast();
        
        native public void addFirst(fabric.lang.Object arg1);
        
        native public void addLast(fabric.lang.Object arg1);
        
        native public boolean contains(fabric.lang.Object arg1);
        
        native public int size();
        
        native public boolean add(fabric.lang.Object arg1);
        
        native public boolean remove(fabric.lang.Object arg1);
        
        native public boolean addAll(fabric.util.Collection arg1);
        
        native public void clear();
        
        native public int indexOf(fabric.lang.Object arg1);
        
        native public int lastIndexOf(fabric.lang.Object arg1);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        native public fabric.lang.Object $initLabels();
        
        native public boolean containsAll(fabric.util.Collection arg1);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        native public boolean isEmpty();
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1);
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public fabric.util.List subList(int arg1, int arg2);
        
        native public fabric.util.Iterator iterator();
        
        public _Proxy(LinkedList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSequentialList._Impl
      implements fabric.util.LinkedList
    {
        
        native public fabric.util.LinkedList.Entry get$first();
        
        native public fabric.util.LinkedList.Entry set$first(
          fabric.util.LinkedList.Entry val);
        
        native public fabric.util.LinkedList.Entry get$last();
        
        native public fabric.util.LinkedList.Entry set$last(
          fabric.util.LinkedList.Entry val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.LinkedList.Entry getEntry(int n);
        
        native public void removeEntry(fabric.util.LinkedList.Entry e);
        
        native private void checkBoundsInclusive(int index);
        
        native private void checkBoundsExclusive(int index);
        
        native public fabric.util.LinkedList fabric$util$LinkedList$();
        
        native public fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection c);
        
        native public fabric.lang.Object getFirst();
        
        native public fabric.lang.Object getLast();
        
        native public fabric.lang.Object removeFirst();
        
        native public fabric.lang.Object removeLast();
        
        native public void addFirst(fabric.lang.Object o);
        
        native public void addLast(fabric.lang.Object o);
        
        native private void addLastEntry(fabric.util.LinkedList.Entry e);
        
        native public boolean contains(fabric.lang.Object o);
        
        native public int size();
        
        native public boolean add(fabric.lang.Object o);
        
        native public boolean remove(fabric.lang.Object o);
        
        native public boolean addAll(fabric.util.Collection c);
        
        native public boolean addAll(int index, fabric.util.Collection c);
        
        native public void clear();
        
        native public fabric.lang.Object get(int index);
        
        native public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        native public void add(int index, fabric.lang.Object o);
        
        native public fabric.lang.Object remove(int index);
        
        native public int indexOf(fabric.lang.Object o);
        
        native public int lastIndexOf(fabric.lang.Object o);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);
        
        native public fabric.lang.arrays.ObjectArray toArray();
        
        native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);
        
        native private void writeObject(java.io.ObjectOutputStream s)
              throws java.io.IOException;
        
        native private void readObject(java.io.ObjectInputStream s)
              throws java.io.IOException, java.lang.ClassNotFoundException;
        
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
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedList._Static
        {
            
            native public long get$serialVersionUID();
            
            public _Proxy(fabric.util.LinkedList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList._Static
        {
            
            native public long get$serialVersionUID();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
