package fabric.util;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface HashMap extends fabric.util.Map, fabric.util.AbstractMap {

  public int get$threshold();

  public int set$threshold(int val);

  public int postInc$threshold();

  public int postDec$threshold();

  public float get$loadFactor();

  public float set$loadFactor(float val);

  public float postInc$loadFactor();

  public float postDec$loadFactor();

  public fabric.lang.arrays.ObjectArray get$buckets();

  public fabric.lang.arrays.ObjectArray set$buckets(
      fabric.lang.arrays.ObjectArray val);

  public int get$modCount();

  public int set$modCount(int val);

  public int postInc$modCount();

  public int postDec$modCount();

  public int get$size();

  public int set$size(int val);

  public int postInc$size();

  public int postDec$size();

  public fabric.util.Set get$entries();

  public fabric.util.Set set$entries(fabric.util.Set val);

  public static interface HashEntry extends
      fabric.util.AbstractMap.BasicMapEntry {

    public fabric.util.HashMap.HashEntry get$next();

    public fabric.util.HashMap.HashEntry set$next(
        fabric.util.HashMap.HashEntry val);

    public fabric.util.HashMap.HashEntry fabric$util$HashMap$HashEntry$(
        fabric.lang.Object key, fabric.lang.Object value);

    public void access();

    public fabric.lang.Object cleanup();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends
        fabric.util.AbstractMap.BasicMapEntry._Proxy implements
        fabric.util.HashMap.HashEntry {

      @Override
      native public fabric.util.HashMap.HashEntry get$next();

      @Override
      native public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashEntry fabric$util$HashMap$HashEntry$(
          fabric.lang.Object arg1, fabric.lang.Object arg2);

      @Override
      native public void access();

      @Override
      native public fabric.lang.Object cleanup();

      public _Proxy(HashEntry._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends
        fabric.util.AbstractMap.BasicMapEntry._Impl implements
        fabric.util.HashMap.HashEntry {

      @Override
      native public fabric.util.HashMap.HashEntry get$next();

      @Override
      native public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashEntry fabric$util$HashMap$HashEntry$(
          fabric.lang.Object key, fabric.lang.Object value);

      @Override
      native public void access();

      @Override
      native public fabric.lang.Object cleanup();

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
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.HashMap.HashEntry._Static {

        public _Proxy(fabric.util.HashMap.HashEntry._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.HashMap.HashEntry._Static {

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

  public fabric.util.HashMap fabric$util$HashMap$();

  public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map m);

  public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity);

  public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity,
      float loadFactor);

  @Override
  public int size();

  @Override
  public boolean isEmpty();

  @Override
  public fabric.lang.Object get(fabric.lang.Object key);

  @Override
  public boolean containsKey(fabric.lang.Object key);

  @Override
  public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);

  @Override
  public void putAll(fabric.util.Map m);

  @Override
  public fabric.lang.Object remove(fabric.lang.Object key);

  @Override
  public void clear();

  @Override
  public boolean containsValue(fabric.lang.Object value);

  @Override
  public fabric.util.Set keySet();

  @Override
  public fabric.util.Collection values();

  @Override
  public fabric.util.Set entrySet();

  public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
      int idx, boolean callRemove);

  public fabric.util.HashMap.HashEntry getEntry(fabric.lang.Object o);

  public int hash(fabric.lang.Object key);

  public fabric.util.Iterator iterator(fabric.worker.Store store, int type);

  public void putAllInternal(fabric.util.Map m);

  public static interface HashIterator extends fabric.util.Iterator,
      fabric.lang.Object {

    public fabric.util.HashMap get$out$();

    public int get$type();

    public int set$type(int val);

    public int postInc$type();

    public int postDec$type();

    public int get$knownMod();

    public int set$knownMod(int val);

    public int postInc$knownMod();

    public int postDec$knownMod();

    public int get$count();

    public int set$count(int val);

    public int postInc$count();

    public int postDec$count();

    public int get$idx();

    public int set$idx(int val);

    public int postInc$idx();

    public int postDec$idx();

    public fabric.util.HashMap.HashEntry get$last();

    public fabric.util.HashMap.HashEntry set$last(
        fabric.util.HashMap.HashEntry val);

    public fabric.util.HashMap.HashEntry get$next();

    public fabric.util.HashMap.HashEntry set$next(
        fabric.util.HashMap.HashEntry val);

    public fabric.util.HashMap.HashIterator fabric$util$HashMap$HashIterator$(
        int type);

    @Override
    public boolean hasNext();

    @Override
    public fabric.lang.Object next();

    @Override
    public void remove();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.HashMap.HashIterator {

      @Override
      native public fabric.util.HashMap get$out$();

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
      native public int get$count();

      @Override
      native public int set$count(int val);

      @Override
      native public int postInc$count();

      @Override
      native public int postDec$count();

      @Override
      native public int get$idx();

      @Override
      native public int set$idx(int val);

      @Override
      native public int postInc$idx();

      @Override
      native public int postDec$idx();

      @Override
      native public fabric.util.HashMap.HashEntry get$last();

      @Override
      native public fabric.util.HashMap.HashEntry set$last(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashEntry get$next();

      @Override
      native public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashIterator fabric$util$HashMap$HashIterator$(
          int arg1);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(HashIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.HashMap.HashIterator {

      @Override
      native public fabric.util.HashMap get$out$();

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
      native public int get$count();

      @Override
      native public int set$count(int val);

      @Override
      native public int postInc$count();

      @Override
      native public int postDec$count();

      @Override
      native public int get$idx();

      @Override
      native public int set$idx(int val);

      @Override
      native public int postInc$idx();

      @Override
      native public int postDec$idx();

      @Override
      native public fabric.util.HashMap.HashEntry get$last();

      @Override
      native public fabric.util.HashMap.HashEntry set$last(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashEntry get$next();

      @Override
      native public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);

      @Override
      native public fabric.util.HashMap.HashIterator fabric$util$HashMap$HashIterator$(
          int type);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location, final fabric.util.HashMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.HashMap.HashIterator._Static {

        public _Proxy(fabric.util.HashMap.HashIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.HashMap.HashIterator._Static {

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

  public static interface Anonymous$5 extends fabric.util.AbstractSet {

    public fabric.util.HashMap get$out$();

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
        fabric.util.HashMap.Anonymous$5 {

      @Override
      native public fabric.util.HashMap get$out$();

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

      public _Proxy(Anonymous$5._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractSet._Impl implements
        fabric.util.HashMap.Anonymous$5 {

      @Override
      native public fabric.util.HashMap get$out$();

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
          final fabric.util.HashMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.HashMap.Anonymous$5._Static {

        public _Proxy(fabric.util.HashMap.Anonymous$5._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.HashMap.Anonymous$5._Static {

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

  public static interface Anonymous$6 extends fabric.util.AbstractCollection {

    public fabric.util.HashMap get$out$();

    @Override
    public int size();

    @Override
    public fabric.util.Iterator iterator(fabric.worker.Store store);

    @Override
    public void clear();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
        implements fabric.util.HashMap.Anonymous$6 {

      @Override
      native public fabric.util.HashMap get$out$();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(Anonymous$6._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractCollection._Impl
        implements fabric.util.HashMap.Anonymous$6 {

      @Override
      native public fabric.util.HashMap get$out$();

      @Override
      native public int size();

      @Override
      native public fabric.util.Iterator iterator(fabric.worker.Store store);

      @Override
      native public void clear();

      @Override
      native public fabric.lang.Object $initLabels();

      private _Impl(fabric.worker.Store $location,
          final fabric.util.HashMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.HashMap.Anonymous$6._Static {

        public _Proxy(fabric.util.HashMap.Anonymous$6._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.HashMap.Anonymous$6._Static {

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

  public static interface Anonymous$7 extends fabric.util.AbstractSet {

    public fabric.util.HashMap get$out$();

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
        fabric.util.HashMap.Anonymous$7 {

      @Override
      native public fabric.util.HashMap get$out$();

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

      public _Proxy(Anonymous$7._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.AbstractSet._Impl implements
        fabric.util.HashMap.Anonymous$7 {

      @Override
      native public fabric.util.HashMap get$out$();

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
          final fabric.util.HashMap out$) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {
      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.HashMap.Anonymous$7._Static {

        public _Proxy(fabric.util.HashMap.Anonymous$7._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.HashMap.Anonymous$7._Static {

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
      fabric.util.HashMap {

    @Override
    native public int get$threshold();

    @Override
    native public int set$threshold(int val);

    @Override
    native public int postInc$threshold();

    @Override
    native public int postDec$threshold();

    @Override
    native public float get$loadFactor();

    @Override
    native public float set$loadFactor(float val);

    @Override
    native public float postInc$loadFactor();

    @Override
    native public float postDec$loadFactor();

    @Override
    native public fabric.lang.arrays.ObjectArray get$buckets();

    @Override
    native public fabric.lang.arrays.ObjectArray set$buckets(
        fabric.lang.arrays.ObjectArray val);

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

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
    native public fabric.util.HashMap fabric$util$HashMap$();

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map arg1);

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(int arg1);

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(int arg1, float arg2);

    @Override
    native public void addEntry(fabric.lang.Object arg1,
        fabric.lang.Object arg2, int arg3, boolean arg4);

    @Override
    final native public fabric.util.HashMap.HashEntry getEntry(
        fabric.lang.Object arg1);

    @Override
    final native public int hash(fabric.lang.Object arg1);

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1,
        int arg2);

    @Override
    native public void putAllInternal(fabric.util.Map arg1);

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(HashMap._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.AbstractMap._Impl implements
      fabric.util.HashMap {

    @Override
    native public int get$threshold();

    @Override
    native public int set$threshold(int val);

    @Override
    native public int postInc$threshold();

    @Override
    native public int postDec$threshold();

    @Override
    native public float get$loadFactor();

    @Override
    native public float set$loadFactor(float val);

    @Override
    native public float postInc$loadFactor();

    @Override
    native public float postDec$loadFactor();

    @Override
    native public fabric.lang.arrays.ObjectArray get$buckets();

    @Override
    native public fabric.lang.arrays.ObjectArray set$buckets(
        fabric.lang.arrays.ObjectArray val);

    @Override
    native public int get$modCount();

    @Override
    native public int set$modCount(int val);

    @Override
    native public int postInc$modCount();

    @Override
    native public int postDec$modCount();

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
    native public fabric.util.HashMap fabric$util$HashMap$();

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map m);

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity);

    @Override
    native public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity,
        float loadFactor);

    @Override
    native public int size();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    native public boolean containsKey(fabric.lang.Object key);

    @Override
    native public fabric.lang.Object put(fabric.lang.Object key,
        fabric.lang.Object value);

    @Override
    native public void putAll(fabric.util.Map m);

    @Override
    native public fabric.lang.Object remove(fabric.lang.Object key);

    @Override
    native public void clear();

    @Override
    native public boolean containsValue(fabric.lang.Object value);

    @Override
    native public fabric.util.Set keySet();

    @Override
    native public fabric.util.Collection values();

    @Override
    native public fabric.util.Set entrySet();

    @Override
    native public void addEntry(fabric.lang.Object key,
        fabric.lang.Object value, int idx, boolean callRemove);

    @Override
    final native public fabric.util.HashMap.HashEntry getEntry(
        fabric.lang.Object o);

    @Override
    final native public int hash(fabric.lang.Object key);

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store store,
        int type);

    @Override
    native public void putAllInternal(fabric.util.Map m);

    native private void rehash();

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
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public int get$DEFAULT_CAPACITY();

    public float get$DEFAULT_LOAD_FACTOR();

    public long get$serialVersionUID();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.HashMap._Static {

      @Override
      native public int get$DEFAULT_CAPACITY();

      @Override
      native public float get$DEFAULT_LOAD_FACTOR();

      @Override
      native public long get$serialVersionUID();

      public _Proxy(fabric.util.HashMap._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.HashMap._Static {

      @Override
      native public int get$DEFAULT_CAPACITY();

      @Override
      native public float get$DEFAULT_LOAD_FACTOR();

      @Override
      native public long get$serialVersionUID();

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
