package fabric.util;

public interface Collections extends fabric.lang.Object {
    public static interface EmptySet extends java.io.Serializable, fabric.util.AbstractSet
    {
        
        public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$(
          );
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean containsAll(fabric.util.Collection c);
        
        public boolean equals(fabric.lang.Object o);
        
        public int hashCode();
        
        public boolean remove(fabric.lang.Object o);
        
        public boolean removeAll(fabric.util.Collection c);
        
        public boolean retainAll(fabric.util.Collection c);
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy implements fabric.util.Collections.EmptySet
        {
            
            native public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$(
              );
            
            native public int size();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public boolean retainAll(fabric.util.Collection arg1);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray arg1);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(EmptySet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractSet._Impl implements fabric.util.Collections.EmptySet
        {
            
            native public fabric.util.Collections.EmptySet fabric$util$Collections$EmptySet$(
              );
            
            native public int size();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean containsAll(fabric.util.Collection c);
            
            native public boolean equals(fabric.lang.Object o);
            
            native public int hashCode();
            
            native public boolean remove(fabric.lang.Object o);
            
            native public boolean removeAll(fabric.util.Collection c);
            
            native public boolean retainAll(fabric.util.Collection c);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              EmptySet.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.EmptySet._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      EmptySet._Static
            {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface EmptyList extends java.io.Serializable, fabric.util.RandomAccess,
                                              fabric.util.AbstractList {
        
        public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$(
          );
        
        public int size();
        
        public fabric.lang.Object get(int index);
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean containsAll(fabric.util.Collection c);
        
        public boolean equals(fabric.lang.Object o);
        
        public int hashCode();
        
        public int indexOf(fabric.lang.Object o);
        
        public int lastIndexOf(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public boolean removeAll(fabric.util.Collection c);
        
        public boolean retainAll(fabric.util.Collection c);
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy implements fabric.util.Collections.EmptyList
        {
            
            native public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$(
              );
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public boolean removeAll(fabric.util.Collection arg1);
            
            native public boolean retainAll(fabric.util.Collection arg1);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray arg1);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(EmptyList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractList._Impl implements fabric.util.Collections.EmptyList
        {
            
            native public fabric.util.Collections.EmptyList fabric$util$Collections$EmptyList$(
              );
            
            native public int size();
            
            native public fabric.lang.Object get(int index);
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean containsAll(fabric.util.Collection c);
            
            native public boolean equals(fabric.lang.Object o);
            
            native public int hashCode();
            
            native public int indexOf(fabric.lang.Object o);
            
            native public int lastIndexOf(fabric.lang.Object o);
            
            native public boolean remove(fabric.lang.Object o);
            
            native public boolean removeAll(fabric.util.Collection c);
            
            native public boolean retainAll(fabric.util.Collection c);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              EmptyList.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.EmptyList._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      EmptyList.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface EmptyMap extends java.io.Serializable, fabric.util.AbstractMap
    {
        
        public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$(
          );
        
        public fabric.util.Set entrySet();
        
        public boolean containsKey(fabric.lang.Object key);
        
        public boolean containsValue(fabric.lang.Object value);
        
        public boolean equals(fabric.lang.Object o);
        
        public fabric.lang.Object get(fabric.lang.Object o);
        
        public int hashCode();
        
        public fabric.util.Set keySet();
        
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        public int size();
        
        public fabric.util.Collection values();
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy implements fabric.util.Collections.EmptyMap
        {
            
            native public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$(
              );
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(EmptyMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractMap._Impl implements fabric.util.Collections.EmptyMap
        {
            
            native public fabric.util.Collections.EmptyMap fabric$util$Collections$EmptyMap$(
              );
            
            native public fabric.util.Set entrySet();
            
            native public boolean containsKey(fabric.lang.Object key);
            
            native public boolean containsValue(fabric.lang.Object value);
            
            native public boolean equals(fabric.lang.Object o);
            
            native public fabric.lang.Object get(fabric.lang.Object o);
            
            native public int hashCode();
            
            native public fabric.util.Set keySet();
            
            native public fabric.lang.Object remove(fabric.lang.Object o);
            
            native public int size();
            
            native public fabric.util.Collection values();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              EmptyMap.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.EmptyMap._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      EmptyMap._Static
            {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface CopiesList extends java.io.Serializable, fabric.util.RandomAccess,
                                               fabric.util.AbstractList {
        
        public int get$n();
        
        public int set$n(int val);
        
        public int postInc$n();
        
        public int postDec$n();
        
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
          int n, fabric.lang.Object o);
        
        public int size();
        
        public fabric.lang.Object get(int index);
        
        public boolean contains(fabric.lang.Object o);
        
        public int indexOf(fabric.lang.Object o);
        
        public int lastIndexOf(fabric.lang.Object o);
        
        public fabric.util.List subList(int from, int to);
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy implements fabric.util.Collections.CopiesList
        {
            
            native public int get$n();
            
            native public int set$n(int val);
            
            native public int postInc$n();
            
            native public int postDec$n();
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
              int arg1, fabric.lang.Object arg2);
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(CopiesList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractList._Impl implements fabric.util.Collections.CopiesList
        {
            
            native public int get$n();
            
            native public int set$n(int val);
            
            native public int postInc$n();
            
            native public int postDec$n();
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.CopiesList fabric$util$Collections$CopiesList$(
              int n, fabric.lang.Object o);
            
            native public int size();
            
            native public fabric.lang.Object get(int index);
            
            native public boolean contains(fabric.lang.Object o);
            
            native public int indexOf(fabric.lang.Object o);
            
            native public int lastIndexOf(fabric.lang.Object o);
            
            native public fabric.util.List subList(int from, int to);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              CopiesList.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.CopiesList._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      CopiesList.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface ReverseComparator extends fabric.util.Comparator, java.io.Serializable,
                                                      fabric.lang.Object {
        
        public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$(
          );
        
        public int compare(fabric.lang.Object a, fabric.lang.Object b);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.ReverseComparator
        {
            
            native public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$(
              );
            
            native public int compare(fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            native public fabric.lang.Object $initLabels();
            
            native public boolean equals(fabric.lang.Object arg1);
            
            public _Proxy(ReverseComparator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.ReverseComparator
        {
            
            native public fabric.util.Collections.ReverseComparator fabric$util$Collections$ReverseComparator$(
              );
            
            native public int compare(fabric.lang.Object a, fabric.lang.Object b);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              ReverseComparator.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.ReverseComparator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      ReverseComparator.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface SingletonSet extends java.io.Serializable, fabric.util.AbstractSet
    {
        
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
          fabric.lang.Object o);
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean containsAll(fabric.util.Collection c);
        
        public int hashCode();
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$2 extends fabric.util.Iterator, fabric.lang.Object
        {
            
            public fabric.util.Collections.SingletonSet get$out$();
            
            public boolean get$hasNext();
            
            public boolean set$hasNext(boolean val);
            
            public boolean hasNext();
            
            public fabric.lang.Object next();
            
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.SingletonSet.Anonymous$2
            {
                
                native public fabric.util.Collections.SingletonSet get$out$();
                
                native public boolean get$hasNext();
                
                native public boolean set$hasNext(boolean val);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(Anonymous$2._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.SingletonSet.Anonymous$2
            {
                
                native public fabric.util.Collections.SingletonSet get$out$();
                
                native public boolean get$hasNext();
                
                native public boolean set$hasNext(boolean val);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                private _Impl(fabric.worker.Store $location, final fabric.util.Collections.SingletonSet out$) {
                    super($location); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native public void $serialize(java.io.ObjectOutput out, java.util.
                                                List refTypes, java.util.List intraStoreRefs,
                                              java.util.List interStoreRefs) throws java.
                  io.IOException;
                
                public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                             long label, long accessLabel, java.io.ObjectInput in,
                             java.util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                      throws java.io.IOException, java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                          intraStoreRefs); }
                
                native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                                  util.
                                                                                  Collections.
                                                                                  SingletonSet.
                                                                                  Anonymous$2.
                                                                                  _Static
                {
                    
                    public _Proxy(fabric.util.Collections.SingletonSet.Anonymous$2.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                          Collections.
                                                                          SingletonSet.
                                                                          Anonymous$2.
                                                                          _Static
                {
                    
                    public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                        super(store); }
                    
                    native protected fabric.lang.Object._Proxy $makeProxy();
                    
                    native private void $init();
                }
                
            }
            
        }
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy implements fabric.util.Collections.SingletonSet
        {
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
              fabric.lang.Object arg1);
            
            native public int size();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(SingletonSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractSet._Impl implements fabric.util.Collections.SingletonSet
        {
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.SingletonSet fabric$util$Collections$SingletonSet$(
              fabric.lang.Object o);
            
            native public int size();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean containsAll(fabric.util.Collection c);
            
            native public int hashCode();
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              SingletonSet.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.SingletonSet._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      SingletonSet.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface SingletonList extends java.io.Serializable, fabric.util.RandomAccess,
                                                  fabric.util.AbstractList {
        
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
          fabric.lang.Object o);
        
        public int size();
        
        public fabric.lang.Object get(int index);
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean containsAll(fabric.util.Collection c);
        
        public int hashCode();
        
        public int indexOf(fabric.lang.Object o);
        
        public int lastIndexOf(fabric.lang.Object o);
        
        public fabric.util.List subList(int from, int to);
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy implements fabric.util.Collections.SingletonList
        {
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
              fabric.lang.Object arg1);
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(SingletonList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractList._Impl implements fabric.util.Collections.SingletonList
        {
            
            native public fabric.lang.Object get$element();
            
            native public fabric.lang.Object set$element(fabric.lang.Object val);
            
            native public fabric.util.Collections.SingletonList fabric$util$Collections$SingletonList$(
              fabric.lang.Object o);
            
            native public int size();
            
            native public fabric.lang.Object get(int index);
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean containsAll(fabric.util.Collection c);
            
            native public int hashCode();
            
            native public int indexOf(fabric.lang.Object o);
            
            native public int lastIndexOf(fabric.lang.Object o);
            
            native public fabric.util.List subList(int from, int to);
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              SingletonList.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.SingletonList._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      SingletonList.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface SingletonMap extends java.io.Serializable, fabric.util.AbstractMap
    {
        
        public fabric.lang.Object get$k();
        
        public fabric.lang.Object set$k(fabric.lang.Object val);
        
        public fabric.lang.Object get$v();
        
        public fabric.lang.Object set$v(fabric.lang.Object val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
          fabric.lang.Object key, fabric.lang.Object value);
        
        public fabric.util.Set entrySet();
        
        public boolean containsKey(fabric.lang.Object key);
        
        public boolean containsValue(fabric.lang.Object value);
        
        public fabric.lang.Object get(fabric.lang.Object key);
        
        public int hashCode();
        
        public fabric.util.Set keySet();
        
        public int size();
        
        public fabric.util.Collection values();
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$3 extends fabric.util.AbstractMap.BasicMapEntry
        {
            
            public fabric.util.Collections.SingletonMap get$out$();
            
            public fabric.lang.Object setValue(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractMap.BasicMapEntry.
              _Proxy implements fabric.util.Collections.SingletonMap.Anonymous$3
            {
                
                native public fabric.util.Collections.SingletonMap get$out$();
                
                public _Proxy(Anonymous$3._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractMap.BasicMapEntry.
              _Impl implements fabric.util.Collections.SingletonMap.Anonymous$3 {
                
                native public fabric.util.Collections.SingletonMap get$out$();
                
                native public fabric.lang.Object setValue(fabric.lang.Object o);
                
                native public fabric.lang.Object $initLabels();
                
                private _Impl(fabric.worker.Store $location, final fabric.util.Collections.SingletonMap out$) {
                    super($location); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native public void $serialize(java.io.ObjectOutput out, java.util.
                                                List refTypes, java.util.List intraStoreRefs,
                                              java.util.List interStoreRefs) throws java.
                  io.IOException;
                
                public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                             long label, long accessLabel, java.io.ObjectInput in,
                             java.util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                      throws java.io.IOException, java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                          intraStoreRefs); }
                
                native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                                  util.
                                                                                  Collections.
                                                                                  SingletonMap.
                                                                                  Anonymous$3.
                                                                                  _Static
                {
                    
                    public _Proxy(fabric.util.Collections.SingletonMap.Anonymous$3.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                          Collections.
                                                                          SingletonMap.
                                                                          Anonymous$3.
                                                                          _Static
                {
                    
                    public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                        super(store); }
                    
                    native protected fabric.lang.Object._Proxy $makeProxy();
                    
                    native private void $init();
                }
                
            }
            
        }
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy implements fabric.util.Collections.SingletonMap
        {
            
            native public fabric.lang.Object get$k();
            
            native public fabric.lang.Object set$k(fabric.lang.Object val);
            
            native public fabric.lang.Object get$v();
            
            native public fabric.lang.Object set$v(fabric.lang.Object val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
              fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(SingletonMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractMap._Impl implements fabric.util.Collections.SingletonMap
        {
            
            native public fabric.lang.Object get$k();
            
            native public fabric.lang.Object set$k(fabric.lang.Object val);
            
            native public fabric.lang.Object get$v();
            
            native public fabric.lang.Object set$v(fabric.lang.Object val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.Collections.SingletonMap fabric$util$Collections$SingletonMap$(
              fabric.lang.Object key, fabric.lang.Object value);
            
            native public fabric.util.Set entrySet();
            
            native public boolean containsKey(fabric.lang.Object key);
            
            native public boolean containsValue(fabric.lang.Object value);
            
            native public fabric.lang.Object get(fabric.lang.Object key);
            
            native public int hashCode();
            
            native public fabric.util.Set keySet();
            
            native public int size();
            
            native public fabric.util.Collection values();
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              SingletonMap.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.SingletonMap._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      SingletonMap.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableCollection extends fabric.util.Collection,
                                                           java.io.Serializable,
                                                           fabric.lang.Object {
        
        public fabric.util.Collection get$c();
        
        public fabric.util.Collection set$c(fabric.util.Collection val);
        
        public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
          fabric.util.Collection c);
        
        public boolean add(fabric.lang.Object o);
        
        public boolean addAll(fabric.util.Collection c);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean containsAll(fabric.util.Collection c1);
        
        public boolean isEmpty();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public fabric.util.Iterator iterator();
        
        public boolean remove(fabric.lang.Object o);
        
        public boolean removeAll(fabric.util.Collection c);
        
        public boolean retainAll(fabric.util.Collection c);
        
        public int size();
        
        public fabric.lang.arrays.ObjectArray toArray();
        
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.UnmodifiableCollection
        {
            
            native public fabric.util.Collection get$c();
            
            native public fabric.util.Collection set$c(fabric.util.Collection val);
            
            native public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
              fabric.util.Collection arg1);
            
            native public boolean add(fabric.lang.Object arg1);
            
            native public boolean addAll(fabric.util.Collection arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public boolean isEmpty();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
            
            native public fabric.util.Iterator iterator();
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public boolean removeAll(fabric.util.Collection arg1);
            
            native public boolean retainAll(fabric.util.Collection arg1);
            
            native public int size();
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray arg1);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            native public boolean equals(fabric.lang.Object arg1);
            
            native public int hashCode();
            
            public _Proxy(UnmodifiableCollection._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.UnmodifiableCollection
        {
            
            native public fabric.util.Collection get$c();
            
            native public fabric.util.Collection set$c(fabric.util.Collection val);
            
            native public fabric.util.Collections.UnmodifiableCollection fabric$util$Collections$UnmodifiableCollection$(
              fabric.util.Collection c);
            
            native public boolean add(fabric.lang.Object o);
            
            native public boolean addAll(fabric.util.Collection c);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean containsAll(fabric.util.Collection c1);
            
            native public boolean isEmpty();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            native public fabric.util.Iterator iterator();
            
            native public boolean remove(fabric.lang.Object o);
            
            native public boolean removeAll(fabric.util.Collection c);
            
            native public boolean retainAll(fabric.util.Collection c);
            
            native public int size();
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
            
            native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableCollection.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableCollection._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableCollection.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableIterator extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.Iterator get$i();
        
        public fabric.util.Iterator set$i(fabric.util.Iterator val);
        
        public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
          fabric.util.Iterator i);
        
        public fabric.lang.Object next();
        
        public boolean hasNext();
        
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.UnmodifiableIterator
        {
            
            native public fabric.util.Iterator get$i();
            
            native public fabric.util.Iterator set$i(fabric.util.Iterator val);
            
            native public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
              fabric.util.Iterator arg1);
            
            native public fabric.lang.Object next();
            
            native public boolean hasNext();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(UnmodifiableIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.UnmodifiableIterator
        {
            
            native public fabric.util.Iterator get$i();
            
            native public fabric.util.Iterator set$i(fabric.util.Iterator val);
            
            native public fabric.util.Collections.UnmodifiableIterator fabric$util$Collections$UnmodifiableIterator$(
              fabric.util.Iterator i);
            
            native public fabric.lang.Object next();
            
            native public boolean hasNext();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableIterator.
                                                                              _Static
            {
                
                public _Proxy(fabric.util.Collections.UnmodifiableIterator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableIterator.
                                                                      _Static {
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableList extends fabric.util.List, fabric.util.Collections.UnmodifiableCollection
    {
        
        public fabric.util.List get$list();
        
        public fabric.util.List set$list(fabric.util.List val);
        
        public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
          fabric.util.List l);
        
        public void add(int index, fabric.lang.Object o);
        
        public boolean addAll(int index, fabric.util.Collection c);
        
        public boolean equals(fabric.lang.Object o);
        
        public fabric.lang.Object get(int index);
        
        public int hashCode();
        
        public int indexOf(fabric.lang.Object o);
        
        public int lastIndexOf(fabric.lang.Object o);
        
        public fabric.util.ListIterator listIterator(fabric.worker.Store store);
        
        public fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);
        
        public fabric.lang.Object remove(int index);
        
        public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        public fabric.util.List subList(int fromIndex, int toIndex);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableCollection.
          _Proxy implements fabric.util.Collections.UnmodifiableList {
            
            native public fabric.util.List get$list();
            
            native public fabric.util.List set$list(fabric.util.List val);
            
            native public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
              fabric.util.List arg1);
            
            native public void add(int arg1, fabric.lang.Object arg2);
            
            native public boolean addAll(int arg1, fabric.util.Collection arg2);
            
            native public boolean equals(fabric.lang.Object arg1);
            
            native public fabric.lang.Object get(int arg1);
            
            native public int hashCode();
            
            native public int indexOf(fabric.lang.Object arg1);
            
            native public int lastIndexOf(fabric.lang.Object arg1);
            
            native public fabric.util.ListIterator listIterator(fabric.worker.Store arg1);
            
            native public fabric.util.ListIterator listIterator(fabric.worker.Store arg1,
                                                                int arg2);
            
            native public fabric.lang.Object remove(int arg1);
            
            native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);
            
            native public fabric.util.List subList(int arg1, int arg2);
            
            public _Proxy(UnmodifiableList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.Collections.UnmodifiableCollection.
          _Impl implements fabric.util.Collections.UnmodifiableList {
            
            native public fabric.util.List get$list();
            
            native public fabric.util.List set$list(fabric.util.List val);
            
            native public fabric.util.Collections.UnmodifiableList fabric$util$Collections$UnmodifiableList$(
              fabric.util.List l);
            
            native public void add(int index, fabric.lang.Object o);
            
            native public boolean addAll(int index, fabric.util.Collection c);
            
            native public boolean equals(fabric.lang.Object o);
            
            native public fabric.lang.Object get(int index);
            
            native public int hashCode();
            
            native public int indexOf(fabric.lang.Object o);
            
            native public int lastIndexOf(fabric.lang.Object o);
            
            native public fabric.util.ListIterator listIterator(fabric.worker.Store store);
            
            native public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                                int index);
            
            native public fabric.lang.Object remove(int index);
            
            native public fabric.lang.Object set(int index, fabric.lang.Object o);
            
            native public fabric.util.List subList(int fromIndex, int toIndex);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableList.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableList._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableList.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableRandomAccessList extends fabric.util.RandomAccess,
                                                                 fabric.util.Collections.UnmodifiableList
    {
        
        public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
          fabric.util.List l);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableList.
          _Proxy implements fabric.util.Collections.UnmodifiableRandomAccessList
        {
            
            native public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List arg1);
            
            public _Proxy(UnmodifiableRandomAccessList._Impl impl) { super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.Collections.UnmodifiableList.
          _Impl implements fabric.util.Collections.UnmodifiableRandomAccessList {
            
            native public fabric.util.Collections.UnmodifiableRandomAccessList fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List l);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableRandomAccessList.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableRandomAccessList.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableRandomAccessList.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableListIterator extends fabric.util.ListIterator,
                                                             fabric.util.Collections.UnmodifiableIterator
    {
        
        public fabric.util.ListIterator get$li();
        
        public fabric.util.ListIterator set$li(fabric.util.ListIterator val);
        
        public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
          fabric.util.ListIterator li);
        
        public void add(fabric.lang.Object o);
        
        public boolean hasPrevious();
        
        public int nextIndex();
        
        public fabric.lang.Object previous();
        
        public int previousIndex();
        
        public void set(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableIterator.
          _Proxy implements fabric.util.Collections.UnmodifiableListIterator {
            
            native public fabric.util.ListIterator get$li();
            
            native public fabric.util.ListIterator set$li(fabric.util.ListIterator val);
            
            native public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
              fabric.util.ListIterator arg1);
            
            native public void add(fabric.lang.Object arg1);
            
            native public boolean hasPrevious();
            
            native public int nextIndex();
            
            native public fabric.lang.Object previous();
            
            native public int previousIndex();
            
            native public void set(fabric.lang.Object arg1);
            
            public _Proxy(UnmodifiableListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.Collections.UnmodifiableIterator.
          _Impl implements fabric.util.Collections.UnmodifiableListIterator {
            
            native public fabric.util.ListIterator get$li();
            
            native public fabric.util.ListIterator set$li(fabric.util.ListIterator val);
            
            native public fabric.util.Collections.UnmodifiableListIterator fabric$util$Collections$UnmodifiableListIterator$(
              fabric.util.ListIterator li);
            
            native public void add(fabric.lang.Object o);
            
            native public boolean hasPrevious();
            
            native public int nextIndex();
            
            native public fabric.lang.Object previous();
            
            native public int previousIndex();
            
            native public void set(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableListIterator.
                                                                              _Static
            {
                
                public _Proxy(fabric.util.Collections.UnmodifiableListIterator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableListIterator.
                                                                      _Static {
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableMap extends fabric.util.Map, java.io.Serializable,
                                                    fabric.lang.Object {
        
        public fabric.util.Map get$m();
        
        public fabric.util.Map set$m(fabric.util.Map val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        public fabric.util.Set get$keys();
        
        public fabric.util.Set set$keys(fabric.util.Set val);
        
        public fabric.util.Collection get$values();
        
        public fabric.util.Collection set$values(fabric.util.Collection val);
        
        public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
          fabric.util.Map m);
        
        public void clear();
        
        public boolean containsKey(fabric.lang.Object key);
        
        public boolean containsValue(fabric.lang.Object value);
        
        public fabric.util.Set entrySet();
        
        public static interface UnmodifiableEntrySet extends java.io.Serializable,
                                                             fabric.util.Collections.UnmodifiableSet
        {
            public static interface UnmodifiableMapEntry extends fabric.util.Map.Entry,
                                                                 fabric.lang.Object
            {
                
                public fabric.util.Map.Entry get$e();
                
                public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);
                
                public boolean equals(fabric.lang.Object o);
                
                public fabric.lang.Object getKey();
                
                public fabric.lang.Object getValue();
                
                public int hashCode();
                
                public fabric.lang.Object setValue(fabric.lang.Object value);
                
                public java.lang.String toString();
                
                public fabric.lang.Object $initLabels();
                
                public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry
                {
                    
                    native public fabric.util.Map.Entry get$e();
                    
                    native public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);
                    
                    native public boolean equals(fabric.lang.Object arg1);
                    
                    native public fabric.lang.Object getKey();
                    
                    native public fabric.lang.Object getValue();
                    
                    native public int hashCode();
                    
                    native public fabric.lang.Object setValue(fabric.lang.Object arg1);
                    
                    native public java.lang.String toString();
                    
                    native public fabric.lang.Object $initLabels();
                    
                    public _Proxy(UnmodifiableMapEntry._Impl impl) { super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                onum);
                    }
                }
                
                final public static class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry
                {
                    
                    native public fabric.util.Map.Entry get$e();
                    
                    native public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);
                    
                    native private fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry
                      fabric$util$Collections$UnmodifiableMapEntry$(fabric.util.Map.Entry e);
                    
                    native public boolean equals(fabric.lang.Object o);
                    
                    native public fabric.lang.Object getKey();
                    
                    native public fabric.lang.Object getValue();
                    
                    native public int hashCode();
                    
                    native public fabric.lang.Object setValue(fabric.lang.Object value);
                    
                    native public java.lang.String toString();
                    
                    native public fabric.lang.Object $initLabels();
                    
                    public _Impl(fabric.worker.Store $location) { super($location);
                    }
                    
                    native protected fabric.lang.Object._Proxy $makeProxy();
                    
                    native public void $serialize(java.io.ObjectOutput out, java.
                                                    util.List refTypes, java.util.
                                                    List intraStoreRefs, java.util.
                                                    List interStoreRefs) throws java.
                      io.IOException;
                    
                    public _Impl(fabric.worker.Store store, long onum, int version,
                                 long expiry, long label, long accessLabel, java.
                                   io.ObjectInput in, java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs) throws java.
                      io.IOException, java.lang.ClassNotFoundException { super(store,
                                                                               onum,
                                                                               version,
                                                                               expiry,
                                                                               label,
                                                                               accessLabel,
                                                                               in,
                                                                               refTypes,
                                                                               intraStoreRefs);
                    }
                    
                    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
                }
                
                interface _Static extends fabric.lang.Object, Cloneable {
                    final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                                      util.
                                                                                      Collections.
                                                                                      UnmodifiableMap.
                                                                                      UnmodifiableEntrySet.
                                                                                      UnmodifiableMapEntry.
                                                                                      _Static
                    {
                        
                        public _Proxy(fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                        UnmodifiableMapEntry._Static._Impl impl) {
                            super(impl); }
                        
                        public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                    onum);
                        }
                    }
                    
                    class _Impl extends fabric.lang.Object._Impl implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableMap.
                                                                              UnmodifiableEntrySet.
                                                                              UnmodifiableMapEntry.
                                                                              _Static
                    {
                        
                        public _Impl(fabric.worker.Store store) throws fabric.net.
                          UnreachableNodeException { super(store); }
                        
                        native protected fabric.lang.Object._Proxy $makeProxy();
                        
                        native private void $init();
                    }
                    
                }
                
            }
            
            
            public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet fabric$util$Collections$UnmodifiableEntrySet$(
              fabric.util.Set s);
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public fabric.lang.arrays.ObjectArray toArray();
            
            public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray array);
            
            public fabric.lang.Object $initLabels();
            
            public static interface Anonymous$4 extends fabric.util.Collections.UnmodifiableIterator
            {
                
                public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  get$out$();
                
                public fabric.lang.Object next();
                
                public fabric.lang.Object $initLabels();
                
                public static class _Proxy extends fabric.util.Collections.UnmodifiableIterator.
                  _Proxy implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4
                {
                    
                    native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                      get$out$();
                    
                    public _Proxy(Anonymous$4._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                onum);
                    }
                }
                
                public static class _Impl extends fabric.util.Collections.UnmodifiableIterator.
                  _Impl implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4
                {
                    
                    native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                      get$out$();
                    
                    native public fabric.lang.Object next();
                    
                    native public fabric.lang.Object $initLabels();
                    
                    private _Impl(fabric.worker.Store $location, final fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet out$) {
                        super($location); }
                    
                    native protected fabric.lang.Object._Proxy $makeProxy();
                    
                    native public void $serialize(java.io.ObjectOutput out, java.
                                                    util.List refTypes, java.util.
                                                    List intraStoreRefs, java.util.
                                                    List interStoreRefs) throws java.
                      io.IOException;
                    
                    public _Impl(fabric.worker.Store store, long onum, int version,
                                 long expiry, long label, long accessLabel, java.
                                   io.ObjectInput in, java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs) throws java.
                      io.IOException, java.lang.ClassNotFoundException { super(store,
                                                                               onum,
                                                                               version,
                                                                               expiry,
                                                                               label,
                                                                               accessLabel,
                                                                               in,
                                                                               refTypes,
                                                                               intraStoreRefs);
                    }
                    
                    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
                }
                
                interface _Static extends fabric.lang.Object, Cloneable {
                    final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                                      util.
                                                                                      Collections.
                                                                                      UnmodifiableMap.
                                                                                      UnmodifiableEntrySet.
                                                                                      Anonymous$4.
                                                                                      _Static
                    {
                        
                        public _Proxy(fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                        Anonymous$4._Static._Impl impl) { super(impl);
                        }
                        
                        public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                    onum);
                        }
                    }
                    
                    class _Impl extends fabric.lang.Object._Impl implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableMap.
                                                                              UnmodifiableEntrySet.
                                                                              Anonymous$4.
                                                                              _Static
                    {
                        
                        public _Impl(fabric.worker.Store store) throws fabric.net.
                          UnreachableNodeException { super(store); }
                        
                        native protected fabric.lang.Object._Proxy $makeProxy();
                        
                        native private void $init();
                    }
                    
                }
                
            }
            
            public static class _Proxy extends fabric.util.Collections.UnmodifiableSet.
              _Proxy implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
            {
                
                native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(fabric.util.Set arg1);
                
                native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
                
                native public fabric.lang.arrays.ObjectArray toArray();
                
                native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray arg1);
                
                public _Proxy(UnmodifiableEntrySet._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            final public static class _Impl extends fabric.util.Collections.UnmodifiableSet.
              _Impl implements fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
            {
                
                native public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(fabric.util.Set s);
                
                native public fabric.util.Iterator iterator(fabric.worker.Store store);
                
                native public fabric.lang.arrays.ObjectArray toArray();
                
                native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray array);
                
                native public fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location) { super($location); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native public void $serialize(java.io.ObjectOutput out, java.util.
                                                List refTypes, java.util.List intraStoreRefs,
                                              java.util.List interStoreRefs) throws java.
                  io.IOException;
                
                public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                             long label, long accessLabel, java.io.ObjectInput in,
                             java.util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                      throws java.io.IOException, java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                          intraStoreRefs); }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                
                public long get$serialVersionUID();
                
                final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                                  util.
                                                                                  Collections.
                                                                                  UnmodifiableMap.
                                                                                  UnmodifiableEntrySet.
                                                                                  _Static
                {
                    
                    native public long get$serialVersionUID();
                    
                    public _Proxy(fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                                onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                          Collections.
                                                                          UnmodifiableMap.
                                                                          UnmodifiableEntrySet.
                                                                          _Static
                {
                    
                    native public long get$serialVersionUID();
                    
                    public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                        super(store); }
                    
                    native protected fabric.lang.Object._Proxy $makeProxy();
                    
                    native private void $init();
                }
                
            }
            
        }
        
        
        public boolean equals(fabric.lang.Object o);
        
        public fabric.lang.Object get(fabric.lang.Object key);
        
        public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
        
        public int hashCode();
        
        public boolean isEmpty();
        
        public fabric.util.Set keySet();
        
        public void putAll(fabric.util.Map m);
        
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        public int size();
        
        public java.lang.String toString();
        
        public fabric.util.Collection values();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections.UnmodifiableMap
        {
            
            native public fabric.util.Map get$m();
            
            native public fabric.util.Map set$m(fabric.util.Map val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.Set get$keys();
            
            native public fabric.util.Set set$keys(fabric.util.Set val);
            
            native public fabric.util.Collection get$values();
            
            native public fabric.util.Collection set$values(fabric.util.Collection val);
            
            native public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
              fabric.util.Map arg1);
            
            native public void clear();
            
            native public boolean containsKey(fabric.lang.Object arg1);
            
            native public boolean containsValue(fabric.lang.Object arg1);
            
            native public fabric.util.Set entrySet();
            
            native public boolean equals(fabric.lang.Object arg1);
            
            native public fabric.lang.Object get(fabric.lang.Object arg1);
            
            native public fabric.lang.Object put(fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            native public int hashCode();
            
            native public boolean isEmpty();
            
            native public fabric.util.Set keySet();
            
            native public void putAll(fabric.util.Map arg1);
            
            native public fabric.lang.Object remove(fabric.lang.Object arg1);
            
            native public int size();
            
            native public java.lang.String toString();
            
            native public fabric.util.Collection values();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(UnmodifiableMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.UnmodifiableMap
        {
            
            native public fabric.util.Map get$m();
            
            native public fabric.util.Map set$m(fabric.util.Map val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.Set get$keys();
            
            native public fabric.util.Set set$keys(fabric.util.Set val);
            
            native public fabric.util.Collection get$values();
            
            native public fabric.util.Collection set$values(fabric.util.Collection val);
            
            native public fabric.util.Collections.UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
              fabric.util.Map m);
            
            native public void clear();
            
            native public boolean containsKey(fabric.lang.Object key);
            
            native public boolean containsValue(fabric.lang.Object value);
            
            native public fabric.util.Set entrySet();
            
            native public boolean equals(fabric.lang.Object o);
            
            native public fabric.lang.Object get(fabric.lang.Object key);
            
            native public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
            
            native public int hashCode();
            
            native public boolean isEmpty();
            
            native public fabric.util.Set keySet();
            
            native public void putAll(fabric.util.Map m);
            
            native public fabric.lang.Object remove(fabric.lang.Object o);
            
            native public int size();
            
            native public java.lang.String toString();
            
            native public fabric.util.Collection values();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableMap.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableMap._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableMap.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableSet extends fabric.util.Set, fabric.util.Collections.UnmodifiableCollection
    {
        
        public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
          fabric.util.Set s);
        
        public boolean equals(fabric.lang.Object o);
        
        public int hashCode();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableCollection.
          _Proxy implements fabric.util.Collections.UnmodifiableSet {
            
            native public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
              fabric.util.Set arg1);
            
            native public boolean equals(fabric.lang.Object arg1);
            
            native public int hashCode();
            
            public _Proxy(UnmodifiableSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.Collections.UnmodifiableCollection.
          _Impl implements fabric.util.Collections.UnmodifiableSet {
            
            native public fabric.util.Collections.UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
              fabric.util.Set s);
            
            native public boolean equals(fabric.lang.Object o);
            
            native public int hashCode();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableSet.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableSet._Static._Impl impl) {
                    super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableSet.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableSortedMap extends fabric.util.SortedMap,
                                                          fabric.util.Collections.UnmodifiableMap
    {
        
        public fabric.util.SortedMap get$sm();
        
        public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);
        
        public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
          fabric.util.SortedMap sm);
        
        public fabric.util.Comparator comparator();
        
        public fabric.lang.Object firstKey();
        
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        public fabric.lang.Object lastKey();
        
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey, fabric.lang.Object toKey);
        
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableMap.
          _Proxy implements fabric.util.Collections.UnmodifiableSortedMap {
            
            native public fabric.util.SortedMap get$sm();
            
            native public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);
            
            native public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
              fabric.util.SortedMap arg1);
            
            native public fabric.util.Comparator comparator();
            
            native public fabric.lang.Object firstKey();
            
            native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);
            
            native public fabric.lang.Object lastKey();
            
            native public fabric.util.SortedMap subMap(fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);
            
            public _Proxy(UnmodifiableSortedMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.Collections.UnmodifiableMap.
          _Impl implements fabric.util.Collections.UnmodifiableSortedMap {
            
            native public fabric.util.SortedMap get$sm();
            
            native public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);
            
            native public fabric.util.Collections.UnmodifiableSortedMap fabric$util$Collections$UnmodifiableSortedMap$(
              fabric.util.SortedMap sm);
            
            native public fabric.util.Comparator comparator();
            
            native public fabric.lang.Object firstKey();
            
            native public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
            
            native public fabric.lang.Object lastKey();
            
            native public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                                       fabric.lang.Object toKey);
            
            native public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableSortedMap.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableSortedMap.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    public static interface UnmodifiableSortedSet extends fabric.util.SortedSet,
                                                          fabric.util.Collections.UnmodifiableSet
    {
        
        public fabric.util.SortedSet get$ss();
        
        public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);
        
        public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
          fabric.util.SortedSet ss);
        
        public fabric.util.Comparator comparator();
        
        public fabric.lang.Object first();
        
        public fabric.util.SortedSet headSet(fabric.lang.Object toElement);
        
        public fabric.lang.Object last();
        
        public fabric.util.SortedSet subSet(fabric.lang.Object fromElement, fabric.lang.Object toElement);
        
        public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.Collections.UnmodifiableSet.
          _Proxy implements fabric.util.Collections.UnmodifiableSortedSet {
            
            native public fabric.util.SortedSet get$ss();
            
            native public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);
            
            native public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
              fabric.util.SortedSet arg1);
            
            native public fabric.util.Comparator comparator();
            
            native public fabric.lang.Object first();
            
            native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);
            
            native public fabric.lang.Object last();
            
            native public fabric.util.SortedSet subSet(fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);
            
            native public boolean add(fabric.lang.Object arg1);
            
            native public boolean addAll(fabric.util.Collection arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean containsAll(fabric.util.Collection arg1);
            
            native public boolean isEmpty();
            
            native public fabric.util.Iterator iterator(fabric.worker.Store arg1);
            
            native public fabric.util.Iterator iterator();
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public boolean removeAll(fabric.util.Collection arg1);
            
            native public boolean retainAll(fabric.util.Collection arg1);
            
            native public int size();
            
            native public fabric.lang.arrays.ObjectArray toArray();
            
            native public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray arg1);
            
            public _Proxy(UnmodifiableSortedSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.Collections.UnmodifiableSet.
          _Impl implements fabric.util.Collections.UnmodifiableSortedSet {
            
            native public fabric.util.SortedSet get$ss();
            
            native public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);
            
            native public fabric.util.Collections.UnmodifiableSortedSet fabric$util$Collections$UnmodifiableSortedSet$(
              fabric.util.SortedSet ss);
            
            native public fabric.util.Comparator comparator();
            
            native public fabric.lang.Object first();
            
            native public fabric.util.SortedSet headSet(fabric.lang.Object toElement);
            
            native public fabric.lang.Object last();
            
            native public fabric.util.SortedSet subSet(fabric.lang.Object fromElement,
                                                       fabric.lang.Object toElement);
            
            native public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                          java.util.List intraStoreRefs, java.util.
                                            List interStoreRefs) throws java.io.
              IOException;
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         long label, long accessLabel, java.io.ObjectInput in, java.
                           util.Iterator refTypes, java.util.Iterator intraStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in, refTypes,
                      intraStoreRefs); }
            
            native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            
            public long get$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy implements fabric.
                                                                              util.
                                                                              Collections.
                                                                              UnmodifiableSortedSet.
                                                                              _Static
            {
                
                native public long get$serialVersionUID();
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedSet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) { super(store,
                                                                            onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl implements fabric.util.
                                                                      Collections.
                                                                      UnmodifiableSortedSet.
                                                                      _Static {
                
                native public long get$serialVersionUID();
                
                public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                    super(store); }
                
                native protected fabric.lang.Object._Proxy $makeProxy();
                
                native private void $init();
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.Collections
    {
        
        final native public static int compare(fabric.lang.Object arg1, fabric.lang.Object arg2,
                                               fabric.util.Comparator arg3);
        
        native public static int binarySearch(fabric.util.List arg1, fabric.lang.Object arg2);
        
        native public static int binarySearch(fabric.util.List arg1, fabric.lang.Object arg2,
                                              fabric.util.Comparator arg3);
        
        native public static void copy(fabric.util.List arg1, fabric.util.List arg2);
        
        native public static void fill(fabric.util.List arg1, fabric.lang.Object arg2);
        
        native public static int indexOfSubList(fabric.util.List arg1, fabric.util.List arg2);
        
        native public static int lastIndexOfSubList(fabric.util.List arg1, fabric.util.List arg2);
        
        native public static fabric.util.ArrayList list(fabric.util.Enumeration arg1);
        
        native public static fabric.lang.Object max(fabric.util.Collection arg1);
        
        native public static fabric.lang.Object max(fabric.util.Collection arg1,
                                                    fabric.util.Comparator arg2);
        
        native public static fabric.lang.Object min(fabric.util.Collection arg1);
        
        native public static fabric.lang.Object min(fabric.util.Collection arg1,
                                                    fabric.util.Comparator arg2);
        
        native public static fabric.util.List nCopies(int arg1, fabric.lang.Object arg2);
        
        native public static boolean replaceAll(fabric.util.List arg1, fabric.lang.Object arg2,
                                                fabric.lang.Object arg3);
        
        native public static void reverse(fabric.util.List arg1);
        
        native public static fabric.util.Comparator reverseOrder();
        
        native public static void rotate(fabric.util.List arg1, int arg2);
        
        native public static void shuffle(fabric.util.List arg1);
        
        native public static void shuffle(fabric.util.List arg1, fabric.util.Random arg2);
        
        native public static fabric.util.Set singleton(fabric.lang.Object arg1);
        
        native public static fabric.util.List singletonList(fabric.lang.Object arg1);
        
        native public static fabric.util.Map singletonMap(fabric.lang.Object arg1,
                                                          fabric.lang.Object arg2);
        
        native public static void sort(fabric.util.List arg1);
        
        native public static void sort(fabric.util.List arg1, fabric.util.Comparator arg2);
        
        native public static void swap(fabric.util.List arg1, int arg2, int arg3);
        
        native public static fabric.util.Collection unmodifiableCollection(fabric.worker.Store arg1,
                                                                           fabric.util.Collection arg2);
        
        native public static fabric.util.List unmodifiableList(fabric.worker.Store arg1,
                                                               fabric.util.List arg2);
        
        native public static fabric.util.Map unmodifiableMap(fabric.worker.Store arg1,
                                                             fabric.util.Map arg2);
        
        native public static fabric.util.Set unmodifiableSet(fabric.worker.Store arg1,
                                                             fabric.util.Set arg2);
        
        native public static fabric.util.SortedMap unmodifiableSortedMap(fabric.worker.Store arg1,
                                                                         fabric.util.SortedMap arg2);
        
        native public static fabric.util.SortedSet unmodifiableSortedSet(fabric.worker.Store arg1,
                                                                         fabric.util.SortedSet arg2);
        
        native public fabric.lang.Object $initLabels();
        
        public _Proxy(Collections._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections
    {
        
        native private static boolean isSequential(fabric.util.List l);
        
        private _Impl(fabric.worker.Store $location) { super($location); }
        
        final native public static int compare(fabric.lang.Object o1, fabric.lang.Object o2,
                                               fabric.util.Comparator c);
        
        native public static int binarySearch(fabric.util.List l, fabric.lang.Object key);
        
        native public static int binarySearch(fabric.util.List l, fabric.lang.Object key,
                                              fabric.util.Comparator c);
        
        native public static void copy(fabric.util.List dest, fabric.util.List source);
        
        native public static void fill(fabric.util.List l, fabric.lang.Object val);
        
        native public static int indexOfSubList(fabric.util.List source, fabric.util.List target);
        
        native public static int lastIndexOfSubList(fabric.util.List source, fabric.util.List target);
        
        native public static fabric.util.ArrayList list(fabric.util.Enumeration e);
        
        native public static fabric.lang.Object max(fabric.util.Collection c);
        
        native public static fabric.lang.Object max(fabric.util.Collection c, fabric.util.Comparator order);
        
        native public static fabric.lang.Object min(fabric.util.Collection c);
        
        native public static fabric.lang.Object min(fabric.util.Collection c, fabric.util.Comparator order);
        
        native public static fabric.util.List nCopies(final int n, final fabric.lang.Object o);
        
        native public static boolean replaceAll(fabric.util.List list, fabric.lang.Object oldval,
                                                fabric.lang.Object newval);
        
        native public static void reverse(fabric.util.List l);
        
        native public static fabric.util.Comparator reverseOrder();
        
        native public static void rotate(fabric.util.List list, int distance);
        
        native public static void shuffle(fabric.util.List l);
        
        native public static void shuffle(fabric.util.List l, fabric.util.Random r);
        
        native public static fabric.util.Set singleton(fabric.lang.Object o);
        
        native public static fabric.util.List singletonList(fabric.lang.Object o);
        
        native public static fabric.util.Map singletonMap(fabric.lang.Object key,
                                                          fabric.lang.Object value);
        
        native public static void sort(fabric.util.List l);
        
        native public static void sort(fabric.util.List l, fabric.util.Comparator c);
        
        native public static void swap(fabric.util.List l, int i, int j);
        
        native public static fabric.util.Collection unmodifiableCollection(fabric.worker.Store store,
                                                                           fabric.util.Collection c);
        
        native public static fabric.util.List unmodifiableList(fabric.worker.Store store,
                                                               fabric.util.List l);
        
        native public static fabric.util.Map unmodifiableMap(fabric.worker.Store store,
                                                             fabric.util.Map m);
        
        native public static fabric.util.Set unmodifiableSet(fabric.worker.Store store,
                                                             fabric.util.Set s);
        
        native public static fabric.util.SortedMap unmodifiableSortedMap(fabric.worker.Store store,
                                                                         fabric.util.SortedMap m);
        
        native public static fabric.util.SortedSet unmodifiableSortedSet(fabric.worker.Store store,
                                                                         fabric.util.SortedSet s);
        
        native public fabric.lang.Object $initLabels();
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                      java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                     long label, long accessLabel, java.io.ObjectInput in, java.
                       util.Iterator refTypes, java.util.Iterator intraStoreRefs)
              throws java.io.IOException, java.lang.ClassNotFoundException { super(store,
                                                                                   onum,
                                                                                   version,
                                                                                   expiry,
                                                                                   label,
                                                                                   accessLabel,
                                                                                   in,
                                                                                   refTypes,
                                                                                   intraStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        public int get$LARGE_LIST_SIZE();
        
        public fabric.util.Set get$EMPTY_SET();
        
        public fabric.util.List get$EMPTY_LIST();
        
        public fabric.util.Map get$EMPTY_MAP();
        
        public fabric.util.Collections.ReverseComparator get$rcInstance();
        
        public fabric.util.Random get$defaultRandom();
        
        public fabric.util.Random set$defaultRandom(fabric.util.Random val);
        
        final class _Proxy extends fabric.lang.Object._Proxy implements fabric.util.
                                                                          Collections.
                                                                          _Static
        {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            native public int get$LARGE_LIST_SIZE();
            
            native public fabric.util.Set get$EMPTY_SET();
            
            native public fabric.util.List get$EMPTY_LIST();
            
            native public fabric.util.Map get$EMPTY_MAP();
            
            native public fabric.util.Collections.ReverseComparator get$rcInstance(
              );
            
            native public fabric.util.Random get$defaultRandom();
            
            native public fabric.util.Random set$defaultRandom(fabric.util.Random val);
            
            public _Proxy(fabric.util.Collections._Static._Impl impl) { super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements fabric.util.Collections.
                                                                  _Static {
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            native public int get$LARGE_LIST_SIZE();
            
            native public fabric.util.Set get$EMPTY_SET();
            
            native public fabric.util.List get$EMPTY_LIST();
            
            native public fabric.util.Map get$EMPTY_MAP();
            
            native public fabric.util.Collections.ReverseComparator get$rcInstance(
              );
            
            native public fabric.util.Random get$defaultRandom();
            
            native public fabric.util.Random set$defaultRandom(fabric.util.Random val);
            
            public _Impl(fabric.worker.Store store) throws fabric.net.UnreachableNodeException {
                super(store); }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
