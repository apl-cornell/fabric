package fabric.util;

public interface AbstractList
  extends fabric.util.List, fabric.util.AbstractCollection
{
    
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    public fabric.util.AbstractList fabric$util$AbstractList$();
    
    abstract public fabric.lang.Object get(int index);
    
    public void add(int index, fabric.lang.Object o);
    
    public boolean add(fabric.lang.Object o);
    
    public boolean addAll(int index, fabric.util.Collection c);
    
    public void clear();
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public int indexOf(fabric.lang.Object o);
    
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public static interface AnonymousIterator
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.AbstractList get$out$();
        
        public int get$initSize();
        
        public int set$initSize(int val);
        
        public int postInc$initSize();
        
        public int postDec$initSize();
        
        public int get$initModCount();
        
        public int set$initModCount(int val);
        
        public int postInc$initModCount();
        
        public int postDec$initModCount();
        
        public int get$pos();
        
        public int set$pos(int val);
        
        public int postInc$pos();
        
        public int postDec$pos();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        public int get$last();
        
        public int set$last(int val);
        
        public int postInc$last();
        
        public int postDec$last();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public fabric.util.AbstractList.AnonymousIterator
          fabric$util$AbstractList$AnonymousIterator$();
        
        public boolean hasNext();
        
        public fabric.lang.Object next();
        
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractList.AnonymousIterator
        {
            
            native public fabric.util.AbstractList get$out$();
            
            native public int get$initSize();
            
            native public int set$initSize(int val);
            
            native public int postInc$initSize();
            
            native public int postDec$initSize();
            
            native public int get$initModCount();
            
            native public int set$initModCount(int val);
            
            native public int postInc$initModCount();
            
            native public int postDec$initModCount();
            
            native public int get$pos();
            
            native public int set$pos(int val);
            
            native public int postInc$pos();
            
            native public int postDec$pos();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native public int get$last();
            
            native public int set$last(int val);
            
            native public int postInc$last();
            
            native public int postDec$last();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.AbstractList.AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$();
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(AnonymousIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList.AnonymousIterator
        {
            
            native public fabric.util.AbstractList get$out$();
            
            native public int get$initSize();
            
            native public int set$initSize(int val);
            
            native public int postInc$initSize();
            
            native public int postDec$initSize();
            
            native public int get$initModCount();
            
            native public int set$initModCount(int val);
            
            native public int postInc$initModCount();
            
            native public int postDec$initModCount();
            
            native public int get$pos();
            
            native public int set$pos(int val);
            
            native public int postInc$pos();
            
            native public int postDec$pos();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native public int get$last();
            
            native public int set$last(int val);
            
            native public int postInc$last();
            
            native public int postDec$last();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.AbstractList.AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$();
            
            native private void checkMod();
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.AbstractList out$) {
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
              implements fabric.util.AbstractList.AnonymousIterator._Static
            {
                
                public _Proxy(fabric.util.AbstractList.AnonymousIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.AnonymousIterator._Static
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
    
    
    public int lastIndexOf(fabric.lang.Object o);
    
    public fabric.util.ListIterator listIterator(fabric.worker.Store store);
    
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                 final int index);
    
    public fabric.lang.Object remove(int index);
    
    public void removeRange(int fromIndex, int toIndex);
    
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    public fabric.util.List subList(int fromIndex, int toIndex);
    
    public static interface AbstractListIterator
      extends fabric.util.ListIterator, fabric.lang.Object
    {
        
        public fabric.util.AbstractList get$out$();
        
        public int get$index();
        
        public int set$index(int val);
        
        public int postInc$index();
        
        public int postDec$index();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$position();
        
        public int set$position(int val);
        
        public int postInc$position();
        
        public int postDec$position();
        
        public int get$lastReturned();
        
        public int set$lastReturned(int val);
        
        public int postInc$lastReturned();
        
        public int postDec$lastReturned();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        public boolean hasNext();
        
        public boolean hasPrevious();
        
        public fabric.lang.Object next();
        
        public fabric.lang.Object previous();
        
        public int nextIndex();
        
        public int previousIndex();
        
        public void remove();
        
        public void set(fabric.lang.Object o);
        
        public void add(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractList.AbstractListIterator
        {
            
            native public fabric.util.AbstractList get$out$();
            
            native public int get$index();
            
            native public int set$index(int val);
            
            native public int postInc$index();
            
            native public int postDec$index();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$position();
            
            native public int set$position(int val);
            
            native public int postInc$position();
            
            native public int postDec$position();
            
            native public int get$lastReturned();
            
            native public int set$lastReturned(int val);
            
            native public int postInc$lastReturned();
            
            native public int postDec$lastReturned();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native public boolean hasNext();
            
            native public boolean hasPrevious();
            
            native public fabric.lang.Object next();
            
            native public fabric.lang.Object previous();
            
            native public int nextIndex();
            
            native public int previousIndex();
            
            native public void remove();
            
            native public void set(fabric.lang.Object arg1);
            
            native public void add(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(AbstractListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList.AbstractListIterator
        {
            
            native public fabric.util.AbstractList get$out$();
            
            native public int get$index();
            
            native public int set$index(int val);
            
            native public int postInc$index();
            
            native public int postDec$index();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public int get$position();
            
            native public int set$position(int val);
            
            native public int postInc$position();
            
            native public int postDec$position();
            
            native public int get$lastReturned();
            
            native public int set$lastReturned(int val);
            
            native public int postInc$lastReturned();
            
            native public int postDec$lastReturned();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native private fabric.util.AbstractList.AbstractListIterator
              fabric$util$AbstractList$AbstractListIterator$(int index);
            
            native private void checkMod();
            
            native public boolean hasNext();
            
            native public boolean hasPrevious();
            
            native public fabric.lang.Object next();
            
            native public fabric.lang.Object previous();
            
            native public int nextIndex();
            
            native public int previousIndex();
            
            native public void remove();
            
            native public void set(fabric.lang.Object o);
            
            native public void add(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.AbstractList out$) {
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
              implements fabric.util.AbstractList.AbstractListIterator._Static
            {
                
                public _Proxy(fabric.util.AbstractList.AbstractListIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.AbstractListIterator._Static
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
    
    public static interface SubList extends fabric.util.AbstractList {
        
        public fabric.util.AbstractList.SubList
          fabric$util$AbstractList$SubList$();
        
        public static interface SubListIterator
          extends fabric.util.ListIterator, fabric.lang.Object
        {
            
            public fabric.util.AbstractList.SubList get$out$();
            
            public int get$index();
            
            public int set$index(int val);
            
            public int postInc$index();
            
            public int postDec$index();
            
            public fabric.util.ListIterator get$i();
            
            public fabric.util.ListIterator set$i(fabric.util.ListIterator val);
            
            public int get$position();
            
            public int set$position(int val);
            
            public int postInc$position();
            
            public int postDec$position();
            
            public boolean hasNext();
            
            public boolean hasPrevious();
            
            public fabric.lang.Object next();
            
            public fabric.lang.Object previous();
            
            public int nextIndex();
            
            public int previousIndex();
            
            public void remove();
            
            public void set(fabric.lang.Object o);
            
            public void add(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.SubList.SubListIterator
            {
                
                native public fabric.util.AbstractList.SubList get$out$();
                
                native public int get$index();
                
                native public int set$index(int val);
                
                native public int postInc$index();
                
                native public int postDec$index();
                
                native public fabric.util.ListIterator get$i();
                
                native public fabric.util.ListIterator set$i(
                  fabric.util.ListIterator val);
                
                native public int get$position();
                
                native public int set$position(int val);
                
                native public int postInc$position();
                
                native public int postDec$position();
                
                native public boolean hasNext();
                
                native public boolean hasPrevious();
                
                native public fabric.lang.Object next();
                
                native public fabric.lang.Object previous();
                
                native public int nextIndex();
                
                native public int previousIndex();
                
                native public void remove();
                
                native public void set(fabric.lang.Object arg1);
                
                native public void add(fabric.lang.Object arg1);
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(SubListIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            final public static class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.SubList.SubListIterator
            {
                
                native public fabric.util.AbstractList.SubList get$out$();
                
                native public int get$index();
                
                native public int set$index(int val);
                
                native public int postInc$index();
                
                native public int postDec$index();
                
                native public fabric.util.ListIterator get$i();
                
                native public fabric.util.ListIterator set$i(
                  fabric.util.ListIterator val);
                
                native public int get$position();
                
                native public int set$position(int val);
                
                native public int postInc$position();
                
                native public int postDec$position();
                
                native private fabric.util.AbstractList.SubList.SubListIterator
                  fabric$util$AbstractList$SubListIterator$(int index);
                
                native public boolean hasNext();
                
                native public boolean hasPrevious();
                
                native public fabric.lang.Object next();
                
                native public fabric.lang.Object previous();
                
                native public int nextIndex();
                
                native public int previousIndex();
                
                native public void remove();
                
                native public void set(fabric.lang.Object o);
                
                native public void add(fabric.lang.Object o);
                
                native public fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location,
                             final fabric.util.AbstractList.SubList out$) {
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
                             java.io.ObjectInput in,
                             java.util.Iterator refTypes,
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
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.AbstractList.SubList.SubListIterator.
                               _Static
                {
                    
                    public _Proxy(fabric.util.AbstractList.SubList.
                                    SubListIterator._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.AbstractList.SubList.SubListIterator.
                               _Static
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
        
        
        public fabric.util.AbstractList get$backingList();
        
        public fabric.util.AbstractList set$backingList(
          fabric.util.AbstractList val);
        
        public int get$offset();
        
        public int set$offset(int val);
        
        public int postInc$offset();
        
        public int postDec$offset();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        public fabric.util.AbstractList.SubList
          fabric$util$AbstractList$SubList$(fabric.util.AbstractList backing,
                                            int fromIndex, int toIndex);
        
        public void checkMod();
        
        public int size();
        
        public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        public fabric.lang.Object get(int index);
        
        public void add(int index, fabric.lang.Object o);
        
        public fabric.lang.Object remove(int index);
        
        public void removeRange(int fromIndex, int toIndex);
        
        public boolean addAll(int index, fabric.util.Collection c);
        
        public boolean addAll(fabric.util.Collection c);
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                     final int index);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements fabric.util.AbstractList.SubList
        {
            
            native public fabric.util.AbstractList get$backingList();
            
            native public fabric.util.AbstractList set$backingList(
              fabric.util.AbstractList val);
            
            native public int get$offset();
            
            native public int set$offset(int val);
            
            native public int postInc$offset();
            
            native public int postDec$offset();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$();
            
            native public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$(fabric.util.AbstractList arg1,
                                                int arg2, int arg3);
            
            native public void checkMod();
            
            native public int size();
            
            native public boolean addAll(fabric.util.Collection arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(SubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractList._Impl
          implements fabric.util.AbstractList.SubList
        {
            
            native public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$();
            
            native public fabric.util.AbstractList get$backingList();
            
            native public fabric.util.AbstractList set$backingList(
              fabric.util.AbstractList val);
            
            native public int get$offset();
            
            native public int set$offset(int val);
            
            native public int postInc$offset();
            
            native public int postDec$offset();
            
            native public int get$size();
            
            native public int set$size(int val);
            
            native public int postInc$size();
            
            native public int postDec$size();
            
            native public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex);
            
            native public void checkMod();
            
            native private void checkBoundsInclusive(int index);
            
            native private void checkBoundsExclusive(int index);
            
            native public int size();
            
            native public fabric.lang.Object set(int index,
                                                 fabric.lang.Object o);
            
            native public fabric.lang.Object get(int index);
            
            native public void add(int index, fabric.lang.Object o);
            
            native public fabric.lang.Object remove(int index);
            
            native public void removeRange(int fromIndex, int toIndex);
            
            native public boolean addAll(int index, fabric.util.Collection c);
            
            native public boolean addAll(fabric.util.Collection c);
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public fabric.util.ListIterator listIterator(
              fabric.worker.Store store, final int index);
            
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
              implements fabric.util.AbstractList.SubList._Static
            {
                
                public _Proxy(fabric.util.AbstractList.SubList._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.SubList._Static
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
    
    public static interface RandomAccessSubList
      extends fabric.util.RandomAccess, fabric.util.AbstractList.SubList
    {
        
        public fabric.util.AbstractList.RandomAccessSubList
          fabric$util$AbstractList$RandomAccessSubList$(
          fabric.util.AbstractList backing, int fromIndex, int toIndex);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractList.SubList._Proxy
          implements fabric.util.AbstractList.RandomAccessSubList
        {
            
            native public fabric.util.AbstractList.RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList arg1, int arg2, int arg3);
            
            public _Proxy(RandomAccessSubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl
        extends fabric.util.AbstractList.SubList._Impl
          implements fabric.util.AbstractList.RandomAccessSubList
        {
            
            native public fabric.util.AbstractList.RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex);
            
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
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.RandomAccessSubList._Static
            {
                
                public _Proxy(fabric.util.AbstractList.RandomAccessSubList.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.RandomAccessSubList._Static
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
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList
    {
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public fabric.util.AbstractList fabric$util$AbstractList$();
        
        native public fabric.lang.Object get(int arg1);
        
        native public void add(int arg1, fabric.lang.Object arg2);
        
        native public boolean addAll(int arg1, fabric.util.Collection arg2);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        native public int indexOf(fabric.lang.Object arg1);
        
        native public int lastIndexOf(fabric.lang.Object arg1);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1, int arg2);
        
        native public fabric.lang.Object remove(int arg1);
        
        native public void removeRange(int arg1, int arg2);
        
        native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);
        
        native public fabric.util.List subList(int arg1, int arg2);
        
        public _Proxy(AbstractList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractList
    {
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public fabric.util.AbstractList fabric$util$AbstractList$();
        
        abstract public fabric.lang.Object get(int index);
        
        native public void add(int index, fabric.lang.Object o);
        
        native public boolean add(fabric.lang.Object o);
        
        native public boolean addAll(int index, fabric.util.Collection c);
        
        native public void clear();
        
        native public boolean equals(fabric.lang.Object o);
        
        native public int hashCode();
        
        native public int indexOf(fabric.lang.Object o);
        
        native public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        native public int lastIndexOf(fabric.lang.Object o);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store);
        
        native public fabric.util.ListIterator listIterator(
          fabric.worker.Store store, final int index);
        
        native public fabric.lang.Object remove(int index);
        
        native public void removeRange(int fromIndex, int toIndex);
        
        native public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        native public fabric.util.List subList(int fromIndex, int toIndex);
        
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
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractList._Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Proxy(fabric.util.AbstractList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList._Static
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
