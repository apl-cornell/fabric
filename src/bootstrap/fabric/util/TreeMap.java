package fabric.util;

public interface TreeMap extends fabric.util.SortedMap, fabric.util.AbstractMap
{
    
    public fabric.util.TreeMap.Node get$nil();
    
    public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val);
    
    public fabric.util.TreeMap.Node get$root();
    
    public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val);
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.util.Set get$entries();
    
    public fabric.util.Set set$entries(fabric.util.Set val);
    
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    public fabric.util.Comparator get$comparator();
    
    public fabric.util.Comparator set$comparator(fabric.util.Comparator val);
    
    public static interface Node extends fabric.util.AbstractMap.BasicMapEntry {
        
        public int get$color();
        
        public int set$color(int val);
        
        public int postInc$color();
        
        public int postDec$color();
        
        public fabric.util.TreeMap.Node get$left();
        
        public fabric.util.TreeMap.Node set$left(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$right();
        
        public fabric.util.TreeMap.Node set$right(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$parent();
        
        public fabric.util.TreeMap.Node set$parent(
          fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
          fabric.lang.Object key, fabric.lang.Object value, int color);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractMap.BasicMapEntry._Proxy
          implements fabric.util.TreeMap.Node
        {
            
            native public int get$color();
            
            native public int set$color(int val);
            
            native public int postInc$color();
            
            native public int postDec$color();
            
            native public fabric.util.TreeMap.Node get$left();
            
            native public fabric.util.TreeMap.Node set$left(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$right();
            
            native public fabric.util.TreeMap.Node set$right(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$parent();
            
            native public fabric.util.TreeMap.Node set$parent(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
              fabric.lang.Object arg1, fabric.lang.Object arg2, int arg3);
            
            public _Proxy(Node._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl
          implements fabric.util.TreeMap.Node
        {
            
            native public int get$color();
            
            native public int set$color(int val);
            
            native public int postInc$color();
            
            native public int postDec$color();
            
            native public fabric.util.TreeMap.Node get$left();
            
            native public fabric.util.TreeMap.Node set$left(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$right();
            
            native public fabric.util.TreeMap.Node set$right(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$parent();
            
            native public fabric.util.TreeMap.Node set$parent(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
              fabric.lang.Object key, fabric.lang.Object value, int color);
            
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
              implements fabric.util.TreeMap.Node._Static
            {
                
                public _Proxy(fabric.util.TreeMap.Node._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Node._Static
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
    
    
    public fabric.util.TreeMap fabric$util$TreeMap$();
    
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Comparator c);
    
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map map);
    
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.SortedMap sm);
    
    public void clear();
    
    public fabric.util.Comparator comparator();
    
    public boolean containsKey(fabric.lang.Object key);
    
    public boolean containsValue(fabric.lang.Object value);
    
    public fabric.util.Set entrySet();
    
    public fabric.lang.Object firstKey();
    
    public fabric.lang.Object get(fabric.lang.Object key);
    
    public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
    
    public fabric.util.Set keySet();
    
    public fabric.lang.Object lastKey();
    
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
    public void putAll(fabric.util.Map m);
    
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    public int size();
    
    public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                        fabric.lang.Object toKey);
    
    public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
    
    public fabric.util.Collection values();
    
    public int compare(fabric.lang.Object o1, fabric.lang.Object o2);
    
    public fabric.util.TreeMap.Node firstNode();
    
    public fabric.util.TreeMap.Node getNode(fabric.lang.Object key);
    
    public fabric.util.TreeMap.Node highestLessThan(fabric.lang.Object key);
    
    public fabric.util.TreeMap.Node lowestGreaterThan(fabric.lang.Object key,
                                                      boolean first);
    
    public void putKeysLinear(fabric.util.Iterator keys, int count);
    
    public void removeNode(fabric.util.TreeMap.Node node);
    
    public fabric.util.TreeMap.Node successor(fabric.util.TreeMap.Node node);
    
    public static interface TreeIterator
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.TreeMap get$out$();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public fabric.util.TreeMap.Node get$last();
        
        public fabric.util.TreeMap.Node set$last(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$next();
        
        public fabric.util.TreeMap.Node set$next(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$max();
        
        public fabric.util.TreeMap.Node set$max(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.TreeIterator
          fabric$util$TreeMap$TreeIterator$(int type);
        
        public fabric.util.TreeMap.TreeIterator
          fabric$util$TreeMap$TreeIterator$(int type,
                                            fabric.util.TreeMap.Node first,
                                            fabric.util.TreeMap.Node max);
        
        public boolean hasNext();
        
        public fabric.lang.Object next();
        
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.TreeMap.TreeIterator
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.TreeMap.Node get$last();
            
            native public fabric.util.TreeMap.Node set$last(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$next();
            
            native public fabric.util.TreeMap.Node set$next(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$max();
            
            native public fabric.util.TreeMap.Node set$max(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1);
            
            native public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1,
                                                fabric.util.TreeMap.Node arg2,
                                                fabric.util.TreeMap.Node arg3);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(TreeIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeMap.TreeIterator
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int get$type();
            
            native public int set$type(int val);
            
            native public int postInc$type();
            
            native public int postDec$type();
            
            native public int get$knownMod();
            
            native public int set$knownMod(int val);
            
            native public int postInc$knownMod();
            
            native public int postDec$knownMod();
            
            native public fabric.util.TreeMap.Node get$last();
            
            native public fabric.util.TreeMap.Node set$last(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$next();
            
            native public fabric.util.TreeMap.Node set$next(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.Node get$max();
            
            native public fabric.util.TreeMap.Node set$max(
              fabric.util.TreeMap.Node val);
            
            native public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int type);
            
            native public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int type,
                                                fabric.util.TreeMap.Node first,
                                                fabric.util.TreeMap.Node max);
            
            native public boolean hasNext();
            
            native public fabric.lang.Object next();
            
            native public void remove();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.TreeMap out$) {
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
              implements fabric.util.TreeMap.TreeIterator._Static
            {
                
                public _Proxy(fabric.util.TreeMap.TreeIterator._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.TreeIterator._Static
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
    
    public static interface SubMap
      extends fabric.util.SortedMap, fabric.util.AbstractMap
    {
        
        public fabric.util.TreeMap get$out$();
        
        public fabric.lang.Object get$minKey();
        
        public fabric.lang.Object set$minKey(fabric.lang.Object val);
        
        public fabric.lang.Object get$maxKey();
        
        public fabric.lang.Object set$maxKey(fabric.lang.Object val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        public fabric.util.TreeMap.SubMap fabric$util$TreeMap$SubMap$(
          fabric.lang.Object minKey, fabric.lang.Object maxKey);
        
        public boolean keyInRange(fabric.lang.Object key);
        
        public void clear();
        
        public fabric.util.Comparator comparator();
        
        public boolean containsKey(fabric.lang.Object key);
        
        public boolean containsValue(fabric.lang.Object value);
        
        public fabric.util.Set entrySet();
        
        public fabric.lang.Object firstKey();
        
        public fabric.lang.Object get(fabric.lang.Object key);
        
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        public fabric.util.Set keySet();
        
        public fabric.lang.Object lastKey();
        
        public fabric.lang.Object put(fabric.lang.Object key,
                                      fabric.lang.Object value);
        
        public fabric.lang.Object remove(fabric.lang.Object key);
        
        public int size();
        
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                            fabric.lang.Object toKey);
        
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        public fabric.util.Collection values();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$11 extends fabric.util.AbstractSet {
            
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements fabric.util.TreeMap.SubMap.Anonymous$11
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public int size();
                
                native public fabric.util.Iterator iterator(
                  fabric.worker.Store arg1);
                
                native public void clear();
                
                native public boolean contains(fabric.lang.Object arg1);
                
                native public boolean remove(fabric.lang.Object arg1);
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(Anonymous$11._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements fabric.util.TreeMap.SubMap.Anonymous$11
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public int size();
                
                native public fabric.util.Iterator iterator(
                  fabric.worker.Store store);
                
                native public void clear();
                
                native public boolean contains(fabric.lang.Object o);
                
                native public boolean remove(fabric.lang.Object o);
                
                native public fabric.lang.Object $initLabels();
                
                private _Impl(fabric.worker.Store $location,
                              final fabric.util.TreeMap.SubMap out$) {
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
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$11._Static
                {
                    
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$11.
                                    _Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$11._Static
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
        
        public static interface Anonymous$12 extends fabric.util.AbstractSet {
            
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements fabric.util.TreeMap.SubMap.Anonymous$12
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public int size();
                
                native public fabric.util.Iterator iterator(
                  fabric.worker.Store arg1);
                
                native public void clear();
                
                native public boolean contains(fabric.lang.Object arg1);
                
                native public boolean remove(fabric.lang.Object arg1);
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(Anonymous$12._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements fabric.util.TreeMap.SubMap.Anonymous$12
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public int size();
                
                native public fabric.util.Iterator iterator(
                  fabric.worker.Store store);
                
                native public void clear();
                
                native public boolean contains(fabric.lang.Object o);
                
                native public boolean remove(fabric.lang.Object o);
                
                native public fabric.lang.Object $initLabels();
                
                private _Impl(fabric.worker.Store $location,
                              final fabric.util.TreeMap.SubMap out$) {
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
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$12._Static
                {
                    
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$12.
                                    _Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$12._Static
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
        
        public static interface Anonymous$13
          extends fabric.util.AbstractCollection
        {
            
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy
            extends fabric.util.AbstractCollection._Proxy
              implements fabric.util.TreeMap.SubMap.Anonymous$13
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(Anonymous$13._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl
            extends fabric.util.AbstractCollection._Impl
              implements fabric.util.TreeMap.SubMap.Anonymous$13
            {
                
                native public fabric.util.TreeMap.SubMap get$out$();
                
                native public int size();
                
                native public fabric.util.Iterator iterator(
                  fabric.worker.Store store);
                
                native public void clear();
                
                native public fabric.lang.Object $initLabels();
                
                private _Impl(fabric.worker.Store $location,
                              final fabric.util.TreeMap.SubMap out$) {
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
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$13._Static
                {
                    
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$13.
                                    _Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$13._Static
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
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements fabric.util.TreeMap.SubMap
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public fabric.lang.Object get$minKey();
            
            native public fabric.lang.Object set$minKey(fabric.lang.Object val);
            
            native public fabric.lang.Object get$maxKey();
            
            native public fabric.lang.Object set$maxKey(fabric.lang.Object val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.TreeMap.SubMap
              fabric$util$TreeMap$SubMap$(fabric.lang.Object arg1,
                                          fabric.lang.Object arg2);
            
            native public boolean keyInRange(fabric.lang.Object arg1);
            
            native public fabric.util.Comparator comparator();
            
            native public fabric.lang.Object firstKey();
            
            native public fabric.util.SortedMap headMap(
              fabric.lang.Object arg1);
            
            native public fabric.lang.Object lastKey();
            
            native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                       fabric.lang.Object arg2);
            
            native public fabric.util.SortedMap tailMap(
              fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(SubMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        final public static class _Impl extends fabric.util.AbstractMap._Impl
          implements fabric.util.TreeMap.SubMap
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public fabric.lang.Object get$minKey();
            
            native public fabric.lang.Object set$minKey(fabric.lang.Object val);
            
            native public fabric.lang.Object get$maxKey();
            
            native public fabric.lang.Object set$maxKey(fabric.lang.Object val);
            
            native public fabric.util.Set get$entries();
            
            native public fabric.util.Set set$entries(fabric.util.Set val);
            
            native public fabric.util.TreeMap.SubMap
              fabric$util$TreeMap$SubMap$(fabric.lang.Object minKey,
                                          fabric.lang.Object maxKey);
            
            native public boolean keyInRange(fabric.lang.Object key);
            
            native public void clear();
            
            native public fabric.util.Comparator comparator();
            
            native public boolean containsKey(fabric.lang.Object key);
            
            native public boolean containsValue(fabric.lang.Object value);
            
            native public fabric.util.Set entrySet();
            
            native public fabric.lang.Object firstKey();
            
            native public fabric.lang.Object get(fabric.lang.Object key);
            
            native public fabric.util.SortedMap headMap(
              fabric.lang.Object toKey);
            
            native public fabric.util.Set keySet();
            
            native public fabric.lang.Object lastKey();
            
            native public fabric.lang.Object put(fabric.lang.Object key,
                                                 fabric.lang.Object value);
            
            native public fabric.lang.Object remove(fabric.lang.Object key);
            
            native public int size();
            
            native public fabric.util.SortedMap subMap(
              fabric.lang.Object fromKey, fabric.lang.Object toKey);
            
            native public fabric.util.SortedMap tailMap(
              fabric.lang.Object fromKey);
            
            native public fabric.util.Collection values();
            
            native public fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.TreeMap out$) {
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
              implements fabric.util.TreeMap.SubMap._Static
            {
                
                public _Proxy(fabric.util.TreeMap.SubMap._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.SubMap._Static
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
    
    public static interface Anonymous$8 extends fabric.util.AbstractSet {
        
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.TreeMap.Anonymous$8
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$8._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements fabric.util.TreeMap.Anonymous$8
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean remove(fabric.lang.Object o);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
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
              implements fabric.util.TreeMap.Anonymous$8._Static
            {
                
                public _Proxy(fabric.util.TreeMap.Anonymous$8._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$8._Static
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
    
    public static interface Anonymous$9 extends fabric.util.AbstractSet {
        
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object key);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.TreeMap.Anonymous$9
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public boolean remove(fabric.lang.Object arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$9._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements fabric.util.TreeMap.Anonymous$9
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public boolean contains(fabric.lang.Object o);
            
            native public boolean remove(fabric.lang.Object key);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
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
              implements fabric.util.TreeMap.Anonymous$9._Static
            {
                
                public _Proxy(fabric.util.TreeMap.Anonymous$9._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$9._Static
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
    
    public static interface Anonymous$10 extends fabric.util.AbstractCollection
    {
        
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements fabric.util.TreeMap.Anonymous$10
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$10._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements fabric.util.TreeMap.Anonymous$10
        {
            
            native public fabric.util.TreeMap get$out$();
            
            native public int size();
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            native public void clear();
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
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
              implements fabric.util.TreeMap.Anonymous$10._Static
            {
                
                public _Proxy(fabric.util.TreeMap.Anonymous$10._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$10._Static
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
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.TreeMap
    {
        
        native public fabric.util.TreeMap.Node get$nil();
        
        native public fabric.util.TreeMap.Node set$nil(
          fabric.util.TreeMap.Node val);
        
        native public fabric.util.TreeMap.Node get$root();
        
        native public fabric.util.TreeMap.Node set$root(
          fabric.util.TreeMap.Node val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.Set get$entries();
        
        native public fabric.util.Set set$entries(fabric.util.Set val);
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public fabric.util.Comparator get$comparator();
        
        native public fabric.util.Comparator set$comparator(
          fabric.util.Comparator val);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$();
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator arg1);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Map arg1);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap arg1);
        
        native public fabric.util.Comparator comparator();
        
        native public fabric.lang.Object firstKey();
        
        native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);
        
        native public fabric.lang.Object lastKey();
        
        native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);
        
        final native public int compare(fabric.lang.Object arg1,
                                        fabric.lang.Object arg2);
        
        final native public fabric.util.TreeMap.Node firstNode();
        
        final native public fabric.util.TreeMap.Node getNode(
          fabric.lang.Object arg1);
        
        final native public fabric.util.TreeMap.Node highestLessThan(
          fabric.lang.Object arg1);
        
        final native public fabric.util.TreeMap.Node lowestGreaterThan(
          fabric.lang.Object arg1, boolean arg2);
        
        final native public void putKeysLinear(fabric.util.Iterator arg1,
                                               int arg2);
        
        final native public void removeNode(fabric.util.TreeMap.Node arg1);
        
        final native public fabric.util.TreeMap.Node successor(
          fabric.util.TreeMap.Node arg1);
        
        native public fabric.lang.Object $initLabels();
        
        public _Proxy(TreeMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.TreeMap
    {
        
        native public fabric.util.TreeMap.Node get$nil();
        
        native public fabric.util.TreeMap.Node set$nil(
          fabric.util.TreeMap.Node val);
        
        native public fabric.util.TreeMap.Node get$root();
        
        native public fabric.util.TreeMap.Node set$root(
          fabric.util.TreeMap.Node val);
        
        native public int get$size();
        
        native public int set$size(int val);
        
        native public int postInc$size();
        
        native public int postDec$size();
        
        native public fabric.util.Set get$entries();
        
        native public fabric.util.Set set$entries(fabric.util.Set val);
        
        native public int get$modCount();
        
        native public int set$modCount(int val);
        
        native public int postInc$modCount();
        
        native public int postDec$modCount();
        
        native public fabric.util.Comparator get$comparator();
        
        native public fabric.util.Comparator set$comparator(
          fabric.util.Comparator val);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$();
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator c);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Map map);
        
        native public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap sm);
        
        native public void clear();
        
        native public fabric.util.Comparator comparator();
        
        native public boolean containsKey(fabric.lang.Object key);
        
        native public boolean containsValue(fabric.lang.Object value);
        
        native public fabric.util.Set entrySet();
        
        native public fabric.lang.Object firstKey();
        
        native public fabric.lang.Object get(fabric.lang.Object key);
        
        native public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        native public fabric.util.Set keySet();
        
        native public fabric.lang.Object lastKey();
        
        native public fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        native public void putAll(fabric.util.Map m);
        
        native public fabric.lang.Object remove(fabric.lang.Object key);
        
        native public int size();
        
        native public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                                   fabric.lang.Object toKey);
        
        native public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        native public fabric.util.Collection values();
        
        final native public int compare(fabric.lang.Object o1,
                                        fabric.lang.Object o2);
        
        native private void deleteFixup(fabric.util.TreeMap.Node node,
                                        fabric.util.TreeMap.Node parent);
        
        native private void fabricateTree(final int count);
        
        final native public fabric.util.TreeMap.Node firstNode();
        
        final native public fabric.util.TreeMap.Node getNode(
          fabric.lang.Object key);
        
        final native public fabric.util.TreeMap.Node highestLessThan(
          fabric.lang.Object key);
        
        native private void insertFixup(fabric.util.TreeMap.Node n);
        
        native private fabric.util.TreeMap.Node lastNode();
        
        final native public fabric.util.TreeMap.Node lowestGreaterThan(
          fabric.lang.Object key, boolean first);
        
        native private fabric.util.TreeMap.Node predecessor(
          fabric.util.TreeMap.Node node);
        
        final native public void putKeysLinear(fabric.util.Iterator keys,
                                               int count);
        
        final native public void removeNode(fabric.util.TreeMap.Node node);
        
        native private void rotateLeft(fabric.util.TreeMap.Node node);
        
        native private void rotateRight(fabric.util.TreeMap.Node node);
        
        final native public fabric.util.TreeMap.Node successor(
          fabric.util.TreeMap.Node node);
        
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
        
        public int get$RED();
        
        public int get$BLACK();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.TreeMap._Static
        {
            
            native public int get$RED();
            
            native public int get$BLACK();
            
            public _Proxy(fabric.util.TreeMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeMap._Static
        {
            
            native public int get$RED();
            
            native public int get$BLACK();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
