package fabric.util;

import fabric.common.VersionWarranty;

public interface AbstractMap extends fabric.util.Map, fabric.lang.Object {

  public fabric.util.Set get$keys();

  public fabric.util.Set set$keys(fabric.util.Set val);

  public fabric.util.Collection get$values();

  public fabric.util.Collection set$values(fabric.util.Collection val);

  public fabric.util.AbstractMap fabric$util$AbstractMap$();

  @Override
  abstract public fabric.util.Set entrySet();

  @Override
  public void clear();

  @Override
  public boolean containsKey(fabric.lang.Object key);

  @Override
  public boolean containsValue(fabric.lang.Object value);

  @Override
  public boolean equals(fabric.lang.Object o);

  @Override
  public fabric.lang.Object get(fabric.lang.Object key);

  @Override
  public int hashCode();

  @Override
  public boolean isEmpty();

  @Override
  public fabric.util.Set keySet();

  @Override
  public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);

  @Override
  public void putAll(fabric.util.Map m);

  @Override
  public fabric.lang.Object remove(fabric.lang.Object key);

  @Override
  public int size();

  @Override
  public java.lang.String toString();

  @Override
  public fabric.util.Collection values();

  public static interface BasicMapEntry extends fabric.util.Map.Entry,
      fabric.lang.Object {

    public fabric.lang.Object get$key();

    public fabric.lang.Object set$key(fabric.lang.Object val);

    public fabric.lang.Object get$value();

    public fabric.lang.Object set$value(fabric.lang.Object val);

    public fabric.util.AbstractMap.BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
        fabric.lang.Object newKey, fabric.lang.Object newValue);

    @Override
    public boolean equals(fabric.lang.Object o);

    @Override
    public fabric.lang.Object getKey();

    @Override
    public fabric.lang.Object getValue();

    @Override
    public int hashCode();

    @Override
    public fabric.lang.Object setValue(fabric.lang.Object newVal);

    @Override
    public java.lang.String toString();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractMap.BasicMapEntry {

      @Override
      native public fabric.lang.Object get$key();

      @Override
      native public fabric.lang.Object set$key(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$value();

      @Override
      native public fabric.lang.Object set$value(fabric.lang.Object val);

      @Override
      native public fabric.util.AbstractMap.BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
          fabric.lang.Object arg1, fabric.lang.Object arg2);

      @Override
      final native public boolean equals(fabric.lang.Object arg1);

      @Override
      final native public fabric.lang.Object getKey();

      @Override
      final native public fabric.lang.Object getValue();

      @Override
      final native public int hashCode();

      @Override
      native public fabric.lang.Object setValue(fabric.lang.Object arg1);

      @Override
      final native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(BasicMapEntry._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractMap.BasicMapEntry {

      @Override
      native public fabric.lang.Object get$key();

      @Override
      native public fabric.lang.Object set$key(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$value();

      @Override
      native public fabric.lang.Object set$value(fabric.lang.Object val);

      @Override
      native public fabric.util.AbstractMap.BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
          fabric.lang.Object newKey, fabric.lang.Object newValue);

      @Override
      final native public boolean equals(fabric.lang.Object o);

      @Override
      final native public fabric.lang.Object getKey();

      @Override
      final native public fabric.lang.Object getValue();

      @Override
      final native public int hashCode();

      @Override
      native public fabric.lang.Object setValue(fabric.lang.Object newVal);

      @Override
      final native public java.lang.String toString();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, long label, long accessLabel,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, label, accessLabel, in, refTypes,
            intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractMap.BasicMapEntry._Static {

        public _Proxy(fabric.util.AbstractMap.BasicMapEntry._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractMap.BasicMapEntry._Static {

        public _Impl(fabric.worker.Store store)
            throws fabric.net.UnreachableNodeException {
          super(store);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        native private void $init();
      }

    }

  }

  public static interface Anonymous$0 extends fabric.util.AbstractSet {

    public fabric.util.AbstractMap get$out$();

    @Override
    public int size();

    @Override
    public boolean contains(fabric.lang.Object key);

    public static interface KeysIterator extends fabric.util.Iterator,
        fabric.lang.Object {

      public fabric.util.AbstractMap.Anonymous$0 get$out$();

      public fabric.util.AbstractMap.Anonymous$0.KeysIterator fabric$util$AbstractMap$KeysIterator$(
          fabric.worker.Store store);

      public fabric.util.Iterator get$map_iterator();

      public fabric.util.Iterator set$map_iterator(fabric.util.Iterator val);

      @Override
      public boolean hasNext();

      @Override
      public fabric.lang.Object next();

      @Override
      public void remove();

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractMap.Anonymous$0.KeysIterator {

        @Override
        native public fabric.util.AbstractMap.Anonymous$0 get$out$();

        @Override
        native public fabric.util.Iterator get$map_iterator();

        @Override
        native public fabric.util.Iterator set$map_iterator(
            fabric.util.Iterator val);

        @Override
        native public fabric.util.AbstractMap.Anonymous$0.KeysIterator fabric$util$AbstractMap$KeysIterator$(
            fabric.worker.Store arg1);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(KeysIterator._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractMap.Anonymous$0.KeysIterator {

        @Override
        native public fabric.util.AbstractMap.Anonymous$0 get$out$();

        @Override
        native public fabric.util.AbstractMap.Anonymous$0.KeysIterator fabric$util$AbstractMap$KeysIterator$(
            fabric.worker.Store store);

        @Override
        native public fabric.util.Iterator get$map_iterator();

        @Override
        native public fabric.util.Iterator set$map_iterator(
            fabric.util.Iterator val);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Impl(fabric.worker.Store $location,
            final fabric.util.AbstractMap.Anonymous$0 out$) {
          super($location);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        @Override
        native public void $serialize(java.io.ObjectOutput out,
            java.util.List refTypes, java.util.List intraStoreRefs,
            java.util.List interStoreRefs) throws java.io.IOException;

        public _Impl(fabric.worker.Store store, long onum, int version,
            VersionWarranty warranty, long label, long accessLabel,
            java.io.ObjectInput in, java.util.Iterator refTypes,
            java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
            throws java.io.IOException, java.lang.ClassNotFoundException {
          super(store, onum, version, warranty, label, accessLabel, in,
              refTypes, intraStoreRefs, interStoreRefs);
        }

        @Override
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
      }

      interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy implements
            fabric.util.AbstractMap.Anonymous$0.KeysIterator._Static {

          public _Proxy(
              fabric.util.AbstractMap.Anonymous$0.KeysIterator._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.AbstractMap.Anonymous$0.KeysIterator._Static {

          public _Impl(fabric.worker.Store store)
              throws fabric.net.UnreachableNodeException {
            super(store);
          }

          @Override
          native protected fabric.lang.Object._Proxy $makeProxy();

          native private void $init();
        }

      }

    }

    @Override
    public fabric.util.Iterator iterator(final fabric.worker.Store store);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
        fabric.util.AbstractMap.Anonymous$0 {

      @Override
      native public fabric.util.AbstractMap get$out$();

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$0._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractSet._Impl implements
        fabric.util.AbstractMap.Anonymous$0 {

      @Override
      native public fabric.util.AbstractMap get$out$();

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object key);

      @Override
      native public fabric.util.Iterator iterator(
          final fabric.worker.Store store);

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.AbstractMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, long label, long accessLabel,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, label, accessLabel, in, refTypes,
            intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractMap.Anonymous$0._Static {

        public _Proxy(fabric.util.AbstractMap.Anonymous$0._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractMap.Anonymous$0._Static {

        public _Impl(fabric.worker.Store store)
            throws fabric.net.UnreachableNodeException {
          super(store);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        native private void $init();
      }

    }

  }

  public static interface Anonymous$1 extends fabric.util.AbstractCollection {

    public fabric.util.AbstractMap get$out$();

    @Override
    public int size();

    @Override
    public boolean contains(fabric.lang.Object value);

    public static interface ValuesIterator extends fabric.util.Iterator,
        fabric.lang.Object {

      public fabric.util.AbstractMap.Anonymous$1 get$out$();

      public fabric.util.AbstractMap.Anonymous$1.ValuesIterator fabric$util$AbstractMap$ValuesIterator$(
          fabric.worker.Store store);

      public fabric.util.Iterator get$map_iterator();

      public fabric.util.Iterator set$map_iterator(fabric.util.Iterator val);

      @Override
      public boolean hasNext();

      @Override
      public fabric.lang.Object next();

      @Override
      public void remove();

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractMap.Anonymous$1.ValuesIterator {

        @Override
        native public fabric.util.AbstractMap.Anonymous$1 get$out$();

        @Override
        native public fabric.util.Iterator get$map_iterator();

        @Override
        native public fabric.util.Iterator set$map_iterator(
            fabric.util.Iterator val);

        @Override
        native public fabric.util.AbstractMap.Anonymous$1.ValuesIterator fabric$util$AbstractMap$ValuesIterator$(
            fabric.worker.Store arg1);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(ValuesIterator._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractMap.Anonymous$1.ValuesIterator {

        @Override
        native public fabric.util.AbstractMap.Anonymous$1 get$out$();

        @Override
        native public fabric.util.AbstractMap.Anonymous$1.ValuesIterator fabric$util$AbstractMap$ValuesIterator$(
            fabric.worker.Store store);

        @Override
        native public fabric.util.Iterator get$map_iterator();

        @Override
        native public fabric.util.Iterator set$map_iterator(
            fabric.util.Iterator val);

        @Override
        native public boolean hasNext();

        @Override
        native public fabric.lang.Object next();

        @Override
        native public void remove();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Impl(fabric.worker.Store $location,
            final fabric.util.AbstractMap.Anonymous$1 out$) {
          super($location);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        @Override
        native public void $serialize(java.io.ObjectOutput out,
            java.util.List refTypes, java.util.List intraStoreRefs,
            java.util.List interStoreRefs) throws java.io.IOException;

        public _Impl(fabric.worker.Store store, long onum, int version,
            VersionWarranty warranty, long label, long accessLabel,
            java.io.ObjectInput in, java.util.Iterator refTypes,
            java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
            throws java.io.IOException, java.lang.ClassNotFoundException {
          super(store, onum, version, warranty, label, accessLabel, in,
              refTypes, intraStoreRefs, interStoreRefs);
        }

        @Override
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
      }

      interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy implements
            fabric.util.AbstractMap.Anonymous$1.ValuesIterator._Static {

          public _Proxy(
              fabric.util.AbstractMap.Anonymous$1.ValuesIterator._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.AbstractMap.Anonymous$1.ValuesIterator._Static {

          public _Impl(fabric.worker.Store store)
              throws fabric.net.UnreachableNodeException {
            super(store);
          }

          @Override
          native protected fabric.lang.Object._Proxy $makeProxy();

          native private void $init();
        }

      }

    }

    @Override
    public fabric.util.Iterator iterator(final fabric.worker.Store store);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
        implements fabric.util.AbstractMap.Anonymous$1 {

      @Override
      native public fabric.util.AbstractMap get$out$();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$1._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractCollection._Impl
        implements fabric.util.AbstractMap.Anonymous$1 {

      @Override
      native public fabric.util.AbstractMap get$out$();

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object value);

      @Override
      native public fabric.util.Iterator iterator(
          final fabric.worker.Store store);

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.AbstractMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, long label, long accessLabel,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, label, accessLabel, in, refTypes,
            intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.AbstractMap.Anonymous$1._Static {

        public _Proxy(fabric.util.AbstractMap.Anonymous$1._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.AbstractMap.Anonymous$1._Static {

        public _Impl(fabric.worker.Store store)
            throws fabric.net.UnreachableNodeException {
          super(store);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        native private void $init();
      }

    }

  }

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.AbstractMap {

    @Override
    native public fabric.util.Set get$keys();

    @Override
    native public fabric.util.Set set$keys(fabric.util.Set val);

    @Override
    native public fabric.util.Collection get$values();

    @Override
    native public fabric.util.Collection set$values(fabric.util.Collection val);

    @Override
    native public fabric.util.AbstractMap fabric$util$AbstractMap$();

    @Override
    native public fabric.util.Set entrySet();

    @Override
    native public void clear();

    @Override
    native public boolean containsKey(fabric.lang.Object arg1);

    @Override
    native public boolean containsValue(fabric.lang.Object arg1);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object get(fabric.lang.Object arg1);

    @Override
    native public int hashCode();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Set keySet();

    @Override
    native public fabric.lang.Object put(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    native public void putAll(fabric.util.Map arg1);

    @Override
    native public fabric.lang.Object remove(fabric.lang.Object arg1);

    @Override
    native public int size();

    @Override
    native public java.lang.String toString();

    @Override
    native public fabric.util.Collection values();

    final native public static boolean equals(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    final native public static int hashCode(fabric.lang.Object arg1);

    public _Proxy(AbstractMap._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  abstract public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.util.AbstractMap {

    @Override
    native public fabric.util.Set get$keys();

    @Override
    native public fabric.util.Set set$keys(fabric.util.Set val);

    @Override
    native public fabric.util.Collection get$values();

    @Override
    native public fabric.util.Collection set$values(fabric.util.Collection val);

    @Override
    native public fabric.util.AbstractMap fabric$util$AbstractMap$();

    @Override
    abstract public fabric.util.Set entrySet();

    @Override
    native public void clear();

    @Override
    native public boolean containsKey(fabric.lang.Object key);

    @Override
    native public boolean containsValue(fabric.lang.Object value);

    @Override
    native public boolean equals(fabric.lang.Object o);

    @Override
    native public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    native public int hashCode();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Set keySet();

    @Override
    native public fabric.lang.Object put(fabric.lang.Object key,
        fabric.lang.Object value);

    @Override
    native public void putAll(fabric.util.Map m);

    @Override
    native public fabric.lang.Object remove(fabric.lang.Object key);

    @Override
    native public int size();

    @Override
    native public java.lang.String toString();

    @Override
    native public fabric.util.Collection values();

    final native public static boolean equals(fabric.lang.Object o1,
        fabric.lang.Object o2);

    final native public static int hashCode(fabric.lang.Object o);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public int get$KEYS();

    public int get$VALUES();

    public int get$ENTRIES();

    public fabric.worker.Store get$LOCAL_STORE();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.AbstractMap._Static {

      @Override
      native public int get$KEYS();

      @Override
      native public int get$VALUES();

      @Override
      native public int get$ENTRIES();

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      public _Proxy(fabric.util.AbstractMap._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.AbstractMap._Static {

      @Override
      native public int get$KEYS();

      @Override
      native public int get$VALUES();

      @Override
      native public int get$ENTRIES();

      @Override
      native public fabric.worker.Store get$LOCAL_STORE();

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
