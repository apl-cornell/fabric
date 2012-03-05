package fabric.util;

public interface AbstractMap extends fabric.util.Map, fabric.lang.Object {
    
    public fabric.util.Set get$keys();
    
    public fabric.util.Set set$keys(fabric.util.Set val);
    
    public fabric.util.Collection get$values();
    
    public fabric.util.Collection set$values(fabric.util.Collection val);
    
    public fabric.util.AbstractMap fabric$util$AbstractMap$();
    
    abstract public fabric.util.Set entrySet();
    
    public void clear();
    
    public boolean containsKey(fabric.lang.Object key);
    
    public boolean containsValue(fabric.lang.Object value);
    
    public boolean equals(fabric.lang.Object o);
    
    public fabric.lang.Object get(fabric.lang.Object key);
    
    public int hashCode();
    
    public boolean isEmpty();
    
    public fabric.util.Set keySet();
    
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
    public void putAll(fabric.util.Map m);
    
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    public int size();
    
    public java.lang.String toString();
    
    public fabric.util.Collection values();
    
    public static interface BasicMapEntry
      extends fabric.util.Map.Entry, fabric.lang.Object
    {
        
        public fabric.lang.Object get$key();
        
        public fabric.lang.Object set$key(fabric.lang.Object val);
        
        public fabric.lang.Object get$value();
        
        public fabric.lang.Object set$value(fabric.lang.Object val);
        
        public fabric.util.AbstractMap.BasicMapEntry
          fabric$util$AbstractMap$BasicMapEntry$(fabric.lang.Object newKey,
                                                 fabric.lang.Object newValue);
        
        public boolean equals(fabric.lang.Object o);
        
        public fabric.lang.Object getKey();
        
        public fabric.lang.Object getValue();
        
        public int hashCode();
        
        public fabric.lang.Object setValue(fabric.lang.Object newVal);
        
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractMap.BasicMapEntry
        {
            
            native public fabric.lang.Object get$key();
            
            native public fabric.lang.Object set$key(fabric.lang.Object val);
            
            native public fabric.lang.Object get$value();
            
            native public fabric.lang.Object set$value(fabric.lang.Object val);
            
            native public fabric.util.AbstractMap.BasicMapEntry
              fabric$util$AbstractMap$BasicMapEntry$(fabric.lang.Object arg1,
                                                     fabric.lang.Object arg2);
            
            final native public boolean equals(fabric.lang.Object arg1);
            
            final native public fabric.lang.Object getKey();
            
            final native public fabric.lang.Object getValue();
            
            final native public int hashCode();
            
            native public fabric.lang.Object setValue(fabric.lang.Object arg1);
            
            final native public java.lang.String toString();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(BasicMapEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractMap.BasicMapEntry
        {
            
            native public fabric.lang.Object get$key();
            
            native public fabric.lang.Object set$key(fabric.lang.Object val);
            
            native public fabric.lang.Object get$value();
            
            native public fabric.lang.Object set$value(fabric.lang.Object val);
            
            native public fabric.util.AbstractMap.BasicMapEntry
              fabric$util$AbstractMap$BasicMapEntry$(
              fabric.lang.Object newKey, fabric.lang.Object newValue);
            
            final native public boolean equals(fabric.lang.Object o);
            
            final native public fabric.lang.Object getKey();
            
            final native public fabric.lang.Object getValue();
            
            final native public int hashCode();
            
            native public fabric.lang.Object setValue(
              fabric.lang.Object newVal);
            
            final native public java.lang.String toString();
            
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
              implements fabric.util.AbstractMap.BasicMapEntry._Static
            {
                
                public _Proxy(fabric.util.AbstractMap.BasicMapEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.BasicMapEntry._Static
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
    
    public static interface Anonymous$0 extends fabric.util.AbstractSet {
        
        public fabric.util.AbstractMap get$out$();
        
        public int size();
        
        public boolean contains(fabric.lang.Object key);
        
        public static interface KeysIterator
          extends fabric.util.Iterator, fabric.lang.Object
        {
            
            public fabric.util.AbstractMap.Anonymous$0 get$out$();
            
            public fabric.util.AbstractMap.Anonymous$0.KeysIterator
              fabric$util$AbstractMap$KeysIterator$(fabric.worker.Store store);
            
            public fabric.util.Iterator get$map_iterator();
            
            public fabric.util.Iterator set$map_iterator(
              fabric.util.Iterator val);
            
            public boolean hasNext();
            
            public fabric.lang.Object next();
            
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractMap.Anonymous$0.KeysIterator
            {
                
                native public fabric.util.AbstractMap.Anonymous$0 get$out$();
                
                native public fabric.util.Iterator get$map_iterator();
                
                native public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val);
                
                native public fabric.util.AbstractMap.Anonymous$0.KeysIterator
                  fabric$util$AbstractMap$KeysIterator$(
                  fabric.worker.Store arg1);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(KeysIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$0.KeysIterator
            {
                
                native public fabric.util.AbstractMap.Anonymous$0 get$out$();
                
                native public fabric.util.AbstractMap.Anonymous$0.KeysIterator
                  fabric$util$AbstractMap$KeysIterator$(
                  fabric.worker.Store store);
                
                native public fabric.util.Iterator get$map_iterator();
                
                native public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location,
                             final fabric.util.AbstractMap.Anonymous$0 out$) {
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
                  implements fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                               _Static
                {
                    
                    public _Proxy(fabric.util.AbstractMap.Anonymous$0.
                                    KeysIterator._Static._Impl impl) {
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
                  implements fabric.util.AbstractMap.Anonymous$0.KeysIterator.
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
        
        
        public fabric.util.Iterator iterator(final fabric.worker.Store store);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.AbstractMap.Anonymous$0
        {
            
            native public fabric.util.AbstractMap get$out$();
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object arg1);
            
            native public fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$0._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements fabric.util.AbstractMap.Anonymous$0
        {
            
            native public fabric.util.AbstractMap get$out$();
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object key);
            
            native public fabric.util.Iterator iterator(
              final fabric.worker.Store store);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.AbstractMap out$) {
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
              implements fabric.util.AbstractMap.Anonymous$0._Static
            {
                
                public _Proxy(fabric.util.AbstractMap.Anonymous$0._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$0._Static
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
    
    public static interface Anonymous$1 extends fabric.util.AbstractCollection {
        
        public fabric.util.AbstractMap get$out$();
        
        public int size();
        
        public boolean contains(fabric.lang.Object value);
        
        public static interface ValuesIterator
          extends fabric.util.Iterator, fabric.lang.Object
        {
            
            public fabric.util.AbstractMap.Anonymous$1 get$out$();
            
            public fabric.util.AbstractMap.Anonymous$1.ValuesIterator
              fabric$util$AbstractMap$ValuesIterator$(
              fabric.worker.Store store);
            
            public fabric.util.Iterator get$map_iterator();
            
            public fabric.util.Iterator set$map_iterator(
              fabric.util.Iterator val);
            
            public boolean hasNext();
            
            public fabric.lang.Object next();
            
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator
            {
                
                native public fabric.util.AbstractMap.Anonymous$1 get$out$();
                
                native public fabric.util.Iterator get$map_iterator();
                
                native public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val);
                
                native public fabric.util.AbstractMap.Anonymous$1.ValuesIterator
                  fabric$util$AbstractMap$ValuesIterator$(
                  fabric.worker.Store arg1);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                public _Proxy(ValuesIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator
            {
                
                native public fabric.util.AbstractMap.Anonymous$1 get$out$();
                
                native public fabric.util.AbstractMap.Anonymous$1.ValuesIterator
                  fabric$util$AbstractMap$ValuesIterator$(
                  fabric.worker.Store store);
                
                native public fabric.util.Iterator get$map_iterator();
                
                native public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val);
                
                native public boolean hasNext();
                
                native public fabric.lang.Object next();
                
                native public void remove();
                
                native public fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location,
                             final fabric.util.AbstractMap.Anonymous$1 out$) {
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
                  implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                               _Static
                {
                    
                    public _Proxy(fabric.util.AbstractMap.Anonymous$1.
                                    ValuesIterator._Static._Impl impl) {
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
                  implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
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
        
        
        public fabric.util.Iterator iterator(final fabric.worker.Store store);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements fabric.util.AbstractMap.Anonymous$1
        {
            
            native public fabric.util.AbstractMap get$out$();
            
            native public fabric.lang.Object $initLabels();
            
            public _Proxy(Anonymous$1._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements fabric.util.AbstractMap.Anonymous$1
        {
            
            native public fabric.util.AbstractMap get$out$();
            
            native public int size();
            
            native public boolean contains(fabric.lang.Object value);
            
            native public fabric.util.Iterator iterator(
              final fabric.worker.Store store);
            
            native public fabric.lang.Object $initLabels();
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.AbstractMap out$) {
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
              implements fabric.util.AbstractMap.Anonymous$1._Static
            {
                
                public _Proxy(fabric.util.AbstractMap.Anonymous$1._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$1._Static
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
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractMap
    {
        
        native public fabric.util.Set get$keys();
        
        native public fabric.util.Set set$keys(fabric.util.Set val);
        
        native public fabric.util.Collection get$values();
        
        native public fabric.util.Collection set$values(
          fabric.util.Collection val);
        
        native public fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        native public fabric.util.Set entrySet();
        
        native public void clear();
        
        native public boolean containsKey(fabric.lang.Object arg1);
        
        native public boolean containsValue(fabric.lang.Object arg1);
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public fabric.lang.Object get(fabric.lang.Object arg1);
        
        native public int hashCode();
        
        native public boolean isEmpty();
        
        native public fabric.util.Set keySet();
        
        native public fabric.lang.Object put(fabric.lang.Object arg1,
                                             fabric.lang.Object arg2);
        
        native public void putAll(fabric.util.Map arg1);
        
        native public fabric.lang.Object remove(fabric.lang.Object arg1);
        
        native public int size();
        
        native public java.lang.String toString();
        
        native public fabric.util.Collection values();
        
        final native public static boolean equals(fabric.lang.Object arg1,
                                                  fabric.lang.Object arg2);
        
        final native public static int hashCode(fabric.lang.Object arg1);
        
        public _Proxy(AbstractMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractMap
    {
        
        native public fabric.util.Set get$keys();
        
        native public fabric.util.Set set$keys(fabric.util.Set val);
        
        native public fabric.util.Collection get$values();
        
        native public fabric.util.Collection set$values(
          fabric.util.Collection val);
        
        native public fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        abstract public fabric.util.Set entrySet();
        
        native public void clear();
        
        native public boolean containsKey(fabric.lang.Object key);
        
        native public boolean containsValue(fabric.lang.Object value);
        
        native public boolean equals(fabric.lang.Object o);
        
        native public fabric.lang.Object get(fabric.lang.Object key);
        
        native public int hashCode();
        
        native public boolean isEmpty();
        
        native public fabric.util.Set keySet();
        
        native public fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        native public void putAll(fabric.util.Map m);
        
        native public fabric.lang.Object remove(fabric.lang.Object key);
        
        native public int size();
        
        native public java.lang.String toString();
        
        native public fabric.util.Collection values();
        
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
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public int get$KEYS();
        
        public int get$VALUES();
        
        public int get$ENTRIES();
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractMap._Static
        {
            
            native public int get$KEYS();
            
            native public int get$VALUES();
            
            native public int get$ENTRIES();
            
            native public fabric.worker.Store get$LOCAL_STORE();
            
            public _Proxy(fabric.util.AbstractMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractMap._Static
        {
            
            native public int get$KEYS();
            
            native public int get$VALUES();
            
            native public int get$ENTRIES();
            
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
