package fabric.util;

import fabric.common.VersionWarranty;

public interface TreeMap extends fabric.util.SortedMap, fabric.util.AbstractMap {

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

    public fabric.util.TreeMap.Node set$parent(fabric.util.TreeMap.Node val);

    public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
        fabric.lang.Object key, fabric.lang.Object value, int color);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.AbstractMap.BasicMapEntry._Proxy implements
        fabric.util.TreeMap.Node {

      @Override
      native public int get$color();

      @Override
      native public int set$color(int val);

      @Override
      native public int postInc$color();

      @Override
      native public int postDec$color();

      @Override
      native public fabric.util.TreeMap.Node get$left();

      @Override
      native public fabric.util.TreeMap.Node set$left(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$right();

      @Override
      native public fabric.util.TreeMap.Node set$right(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$parent();

      @Override
      native public fabric.util.TreeMap.Node set$parent(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
          fabric.lang.Object arg1, fabric.lang.Object arg2, int arg3);

      public _Proxy(Node._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends
        fabric.util.AbstractMap.BasicMapEntry._Impl implements
        fabric.util.TreeMap.Node {

      @Override
      native public int get$color();

      @Override
      native public int set$color(int val);

      @Override
      native public int postInc$color();

      @Override
      native public int postDec$color();

      @Override
      native public fabric.util.TreeMap.Node get$left();

      @Override
      native public fabric.util.TreeMap.Node set$left(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$right();

      @Override
      native public fabric.util.TreeMap.Node set$right(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$parent();

      @Override
      native public fabric.util.TreeMap.Node set$parent(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
          fabric.lang.Object key, fabric.lang.Object value, int color);

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
          fabric.util.TreeMap.Node._Static {

        public _Proxy(fabric.util.TreeMap.Node._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.Node._Static {

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

  public fabric.util.TreeMap fabric$util$TreeMap$();

  public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Comparator c);

  public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map map);

  public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.SortedMap sm);

  @Override
  public void clear();

  @Override
  public fabric.util.Comparator comparator();

  @Override
  public boolean containsKey(fabric.lang.Object key);

  @Override
  public boolean containsValue(fabric.lang.Object value);

  @Override
  public fabric.util.Set entrySet();

  @Override
  public fabric.lang.Object firstKey();

  @Override
  public fabric.lang.Object get(fabric.lang.Object key);

  @Override
  public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

  @Override
  public fabric.util.Set keySet();

  @Override
  public fabric.lang.Object lastKey();

  @Override
  public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);

  @Override
  public void putAll(fabric.util.Map m);

  @Override
  public fabric.lang.Object remove(fabric.lang.Object key);

  @Override
  public int size();

  @Override
  public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
      fabric.lang.Object toKey);

  @Override
  public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

  @Override
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

  public static interface TreeIterator extends fabric.util.Iterator,
      fabric.lang.Object {

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

    public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
        int type);

    public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
        int type, fabric.util.TreeMap.Node first, fabric.util.TreeMap.Node max);

    @Override
    public boolean hasNext();

    @Override
    public fabric.lang.Object next();

    @Override
    public void remove();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.TreeMap.TreeIterator {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int get$type();

      @Override
      native public int set$type(int val);

      @Override
      native public int postInc$type();

      @Override
      native public int postDec$type();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.TreeMap.Node get$last();

      @Override
      native public fabric.util.TreeMap.Node set$last(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$next();

      @Override
      native public fabric.util.TreeMap.Node set$next(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$max();

      @Override
      native public fabric.util.TreeMap.Node set$max(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
          int arg1);

      @Override
      native public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
          int arg1, fabric.util.TreeMap.Node arg2, fabric.util.TreeMap.Node arg3);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(TreeIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.TreeMap.TreeIterator {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int get$type();

      @Override
      native public int set$type(int val);

      @Override
      native public int postInc$type();

      @Override
      native public int postDec$type();

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public fabric.util.TreeMap.Node get$last();

      @Override
      native public fabric.util.TreeMap.Node set$last(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$next();

      @Override
      native public fabric.util.TreeMap.Node set$next(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.Node get$max();

      @Override
      native public fabric.util.TreeMap.Node set$max(
          fabric.util.TreeMap.Node val);

      @Override
      native public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
          int type);

      @Override
      native public fabric.util.TreeMap.TreeIterator fabric$util$TreeMap$TreeIterator$(
          int type, fabric.util.TreeMap.Node first, fabric.util.TreeMap.Node max);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location, final fabric.util.TreeMap out$) {
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
          fabric.util.TreeMap.TreeIterator._Static {

        public _Proxy(fabric.util.TreeMap.TreeIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.TreeIterator._Static {

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

  public static interface SubMap extends fabric.util.SortedMap,
      fabric.util.AbstractMap {

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

    @Override
    public void clear();

    @Override
    public fabric.util.Comparator comparator();

    @Override
    public boolean containsKey(fabric.lang.Object key);

    @Override
    public boolean containsValue(fabric.lang.Object value);

    @Override
    public fabric.util.Set entrySet();

    @Override
    public fabric.lang.Object firstKey();

    @Override
    public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

    @Override
    public fabric.util.Set keySet();

    @Override
    public fabric.lang.Object lastKey();

    @Override
    public fabric.lang.Object put(fabric.lang.Object key,
        fabric.lang.Object value);

    @Override
    public fabric.lang.Object remove(fabric.lang.Object key);

    @Override
    public int size();

    @Override
    public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
        fabric.lang.Object toKey);

    @Override
    public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

    @Override
    public fabric.util.Collection values();

    @Override
    public fabric.lang.Object $initLabels();

    public static interface Anonymous$11 extends fabric.util.AbstractSet {

      public fabric.util.TreeMap.SubMap get$out$();

      @Override
      public int size();

      @Override
      public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      public void clear();

      @Override
      public boolean contains(fabric.lang.Object o);

      @Override
      public boolean remove(fabric.lang.Object o);

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.TreeMap.SubMap.Anonymous$11 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public int size();

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

        @Override
        native public void clear();

        @Override
        native public boolean contains(fabric.lang.Object arg1);

        @Override
        native public boolean remove(fabric.lang.Object arg1);

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(Anonymous$11._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.util.AbstractSet._Impl implements
          fabric.util.TreeMap.SubMap.Anonymous$11 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public int size();

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store store);

        @Override
        native public void clear();

        @Override
        native public boolean contains(fabric.lang.Object o);

        @Override
        native public boolean remove(fabric.lang.Object o);

        @Override
        native public fabric.lang.Object $initLabels();

        private _Impl(fabric.worker.Store $location,
            final fabric.util.TreeMap.SubMap out$) {
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
            fabric.util.TreeMap.SubMap.Anonymous$11._Static {

          public _Proxy(
              fabric.util.TreeMap.SubMap.Anonymous$11._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.TreeMap.SubMap.Anonymous$11._Static {

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

    public static interface Anonymous$12 extends fabric.util.AbstractSet {

      public fabric.util.TreeMap.SubMap get$out$();

      @Override
      public int size();

      @Override
      public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      public void clear();

      @Override
      public boolean contains(fabric.lang.Object o);

      @Override
      public boolean remove(fabric.lang.Object o);

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements fabric.util.TreeMap.SubMap.Anonymous$12 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public int size();

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

        @Override
        native public void clear();

        @Override
        native public boolean contains(fabric.lang.Object arg1);

        @Override
        native public boolean remove(fabric.lang.Object arg1);

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(Anonymous$12._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.util.AbstractSet._Impl implements
          fabric.util.TreeMap.SubMap.Anonymous$12 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public int size();

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store store);

        @Override
        native public void clear();

        @Override
        native public boolean contains(fabric.lang.Object o);

        @Override
        native public boolean remove(fabric.lang.Object o);

        @Override
        native public fabric.lang.Object $initLabels();

        private _Impl(fabric.worker.Store $location,
            final fabric.util.TreeMap.SubMap out$) {
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
            fabric.util.TreeMap.SubMap.Anonymous$12._Static {

          public _Proxy(
              fabric.util.TreeMap.SubMap.Anonymous$12._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.TreeMap.SubMap.Anonymous$12._Static {

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

    public static interface Anonymous$13 extends fabric.util.AbstractCollection {

      public fabric.util.TreeMap.SubMap get$out$();

      @Override
      public int size();

      @Override
      public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      public void clear();

      @Override
      public fabric.lang.Object $initLabels();

      public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements fabric.util.TreeMap.SubMap.Anonymous$13 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public fabric.lang.Object $initLabels();

        public _Proxy(Anonymous$13._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements fabric.util.TreeMap.SubMap.Anonymous$13 {

        @Override
        native public fabric.util.TreeMap.SubMap get$out$();

        @Override
        native public int size();

        @Override
        native public fabric.util.Iterator iterator(fabric.worker.Store store);

        @Override
        native public void clear();

        @Override
        native public fabric.lang.Object $initLabels();

        private _Impl(fabric.worker.Store $location,
            final fabric.util.TreeMap.SubMap out$) {
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
            fabric.util.TreeMap.SubMap.Anonymous$13._Static {

          public _Proxy(
              fabric.util.TreeMap.SubMap.Anonymous$13._Static._Impl impl) {
            super(impl);
          }

          public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
          }
        }

        class _Impl extends fabric.lang.Object._Impl implements
            fabric.util.TreeMap.SubMap.Anonymous$13._Static {

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

    public static class _Proxy extends fabric.util.AbstractMap._Proxy implements
        fabric.util.TreeMap.SubMap {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public fabric.lang.Object get$minKey();

      @Override
      native public fabric.lang.Object set$minKey(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$maxKey();

      @Override
      native public fabric.lang.Object set$maxKey(fabric.lang.Object val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.TreeMap.SubMap fabric$util$TreeMap$SubMap$(
          fabric.lang.Object arg1, fabric.lang.Object arg2);

      @Override
      native public boolean keyInRange(fabric.lang.Object arg1);

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public fabric.lang.Object firstKey();

      @Override
      native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object lastKey();

      @Override
      native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
          fabric.lang.Object arg2);

      @Override
      native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(SubMap._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractMap._Impl
        implements fabric.util.TreeMap.SubMap {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public fabric.lang.Object get$minKey();

      @Override
      native public fabric.lang.Object set$minKey(fabric.lang.Object val);

      @Override
      native public fabric.lang.Object get$maxKey();

      @Override
      native public fabric.lang.Object set$maxKey(fabric.lang.Object val);

      @Override
      native public fabric.util.Set get$entries();

      @Override
      native public fabric.util.Set set$entries(fabric.util.Set val);

      @Override
      native public fabric.util.TreeMap.SubMap fabric$util$TreeMap$SubMap$(
          fabric.lang.Object minKey, fabric.lang.Object maxKey);

      @Override
      native public boolean keyInRange(fabric.lang.Object key);

      @Override
      native public void clear();

      @Override
      native public fabric.util.Comparator comparator();

      @Override
      native public boolean containsKey(fabric.lang.Object key);

      @Override
      native public boolean containsValue(fabric.lang.Object value);

      @Override
      native public fabric.util.Set entrySet();

      @Override
      native public fabric.lang.Object firstKey();

      @Override
      native public fabric.lang.Object get(fabric.lang.Object key);

      @Override
      native public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

      @Override
      native public fabric.util.Set keySet();

      @Override
      native public fabric.lang.Object lastKey();

      @Override
      native public fabric.lang.Object put(fabric.lang.Object key,
          fabric.lang.Object value);

      @Override
      native public fabric.lang.Object remove(fabric.lang.Object key);

      @Override
      native public int size();

      @Override
      native public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
          fabric.lang.Object toKey);

      @Override
      native public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

      @Override
      native public fabric.util.Collection values();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location, final fabric.util.TreeMap out$) {
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
          fabric.util.TreeMap.SubMap._Static {

        public _Proxy(fabric.util.TreeMap.SubMap._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.SubMap._Static {

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
  public fabric.lang.Object $initLabels();

  public static interface Anonymous$8 extends fabric.util.AbstractSet {

    public fabric.util.TreeMap get$out$();

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public void clear();

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean remove(fabric.lang.Object o);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
        fabric.util.TreeMap.Anonymous$8 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public void clear();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public boolean remove(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$8._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractSet._Impl implements
        fabric.util.TreeMap.Anonymous$8 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public void clear();

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean remove(fabric.lang.Object o);

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.TreeMap out$) {
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
          fabric.util.TreeMap.Anonymous$8._Static {

        public _Proxy(fabric.util.TreeMap.Anonymous$8._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.Anonymous$8._Static {

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

  public static interface Anonymous$9 extends fabric.util.AbstractSet {

    public fabric.util.TreeMap get$out$();

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public void clear();

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public boolean remove(fabric.lang.Object key);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractSet._Proxy implements
        fabric.util.TreeMap.Anonymous$9 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

      @Override
      native public void clear();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public boolean remove(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$9._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractSet._Impl implements
        fabric.util.TreeMap.Anonymous$9 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public void clear();

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public boolean remove(fabric.lang.Object key);

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.TreeMap out$) {
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
          fabric.util.TreeMap.Anonymous$9._Static {

        public _Proxy(fabric.util.TreeMap.Anonymous$9._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.Anonymous$9._Static {

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

  public static interface Anonymous$10 extends fabric.util.AbstractCollection {

    public fabric.util.TreeMap get$out$();

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public void clear();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
        implements fabric.util.TreeMap.Anonymous$10 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$10._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractCollection._Impl
        implements fabric.util.TreeMap.Anonymous$10 {

      @Override
      native public fabric.util.TreeMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public void clear();

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.TreeMap out$) {
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
          fabric.util.TreeMap.Anonymous$10._Static {

        public _Proxy(fabric.util.TreeMap.Anonymous$10._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.TreeMap.Anonymous$10._Static {

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

  public static class _Proxy extends fabric.util.AbstractMap._Proxy implements
      fabric.util.TreeMap {

    @Override
    native public fabric.util.TreeMap.Node get$nil();

    @Override
    native public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val);

    @Override
    native public fabric.util.TreeMap.Node get$root();

    @Override
    native public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val);

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.util.Set get$entries();

    @Override
    native public fabric.util.Set set$entries(fabric.util.Set val);

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

    @Override
    native public fabric.util.Comparator get$comparator();

    @Override
    native public fabric.util.Comparator set$comparator(
        fabric.util.Comparator val);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$();

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(
        fabric.util.Comparator arg1);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map arg1);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(
        fabric.util.SortedMap arg1);

    @Override
    native public fabric.util.Comparator comparator();

    @Override
    native public fabric.lang.Object firstKey();

    @Override
    native public fabric.util.SortedMap headMap(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object lastKey();

    @Override
    native public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    native public fabric.util.SortedMap tailMap(fabric.lang.Object arg1);

    @Override
    final native public int compare(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    final native public fabric.util.TreeMap.Node firstNode();

    @Override
    final native public fabric.util.TreeMap.Node getNode(fabric.lang.Object arg1);

    @Override
    final native public fabric.util.TreeMap.Node highestLessThan(
        fabric.lang.Object arg1);

    @Override
    final native public fabric.util.TreeMap.Node lowestGreaterThan(
        fabric.lang.Object arg1, boolean arg2);

    @Override
    final native public void putKeysLinear(fabric.util.Iterator arg1, int arg2);

    @Override
    final native public void removeNode(fabric.util.TreeMap.Node arg1);

    @Override
    final native public fabric.util.TreeMap.Node successor(
        fabric.util.TreeMap.Node arg1);

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(TreeMap._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractMap._Impl implements
      fabric.util.TreeMap {

    @Override
    native public fabric.util.TreeMap.Node get$nil();

    @Override
    native public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val);

    @Override
    native public fabric.util.TreeMap.Node get$root();

    @Override
    native public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val);

    @Override
    native public int get$size();

    @Override
    native public int set$size(int val);

    @Override
    native public int postInc$size();

    @Override
    native public int postDec$size();

    @Override
    native public fabric.util.Set get$entries();

    @Override
    native public fabric.util.Set set$entries(fabric.util.Set val);

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

    @Override
    native public fabric.util.Comparator get$comparator();

    @Override
    native public fabric.util.Comparator set$comparator(
        fabric.util.Comparator val);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$();

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(
        fabric.util.Comparator c);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map map);

    @Override
    native public fabric.util.TreeMap fabric$util$TreeMap$(
        fabric.util.SortedMap sm);

    @Override
    native public void clear();

    @Override
    native public fabric.util.Comparator comparator();

    @Override
    native public boolean containsKey(fabric.lang.Object key);

    @Override
    native public boolean containsValue(fabric.lang.Object value);

    @Override
    native public fabric.util.Set entrySet();

    @Override
    native public fabric.lang.Object firstKey();

    @Override
    native public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    native public fabric.util.SortedMap headMap(fabric.lang.Object toKey);

    @Override
    native public fabric.util.Set keySet();

    @Override
    native public fabric.lang.Object lastKey();

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
    native public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
        fabric.lang.Object toKey);

    @Override
    native public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);

    @Override
    native public fabric.util.Collection values();

    @Override
    final native public int compare(fabric.lang.Object o1, fabric.lang.Object o2);

    native private void deleteFixup(fabric.util.TreeMap.Node node,
        fabric.util.TreeMap.Node parent);

    native private void fabricateTree(final int count);

    @Override
    final native public fabric.util.TreeMap.Node firstNode();

    @Override
    final native public fabric.util.TreeMap.Node getNode(fabric.lang.Object key);

    @Override
    final native public fabric.util.TreeMap.Node highestLessThan(
        fabric.lang.Object key);

    native private void insertFixup(fabric.util.TreeMap.Node n);

    native private fabric.util.TreeMap.Node lastNode();

    @Override
    final native public fabric.util.TreeMap.Node lowestGreaterThan(
        fabric.lang.Object key, boolean first);

    native private fabric.util.TreeMap.Node predecessor(
        fabric.util.TreeMap.Node node);

    @Override
    final native public void putKeysLinear(fabric.util.Iterator keys, int count);

    @Override
    final native public void removeNode(fabric.util.TreeMap.Node node);

    native private void rotateLeft(fabric.util.TreeMap.Node node);

    native private void rotateRight(fabric.util.TreeMap.Node node);

    @Override
    final native public fabric.util.TreeMap.Node successor(
        fabric.util.TreeMap.Node node);

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

    public int get$RED();

    public int get$BLACK();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.TreeMap._Static {

      @Override
      native public int get$RED();

      @Override
      native public int get$BLACK();

      public _Proxy(fabric.util.TreeMap._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.TreeMap._Static {

      @Override
      native public int get$RED();

      @Override
      native public int get$BLACK();

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
