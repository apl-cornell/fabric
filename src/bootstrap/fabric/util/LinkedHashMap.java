package fabric.util;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface LinkedHashMap extends fabric.util.HashMap {

  public fabric.util.LinkedHashMap.LinkedHashEntry get$root();

  public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
      fabric.util.LinkedHashMap.LinkedHashEntry val);

  public boolean get$accessOrder();

  public boolean set$accessOrder(boolean val);

  public static interface LinkedHashEntry extends fabric.util.HashMap.HashEntry {

    public fabric.util.LinkedHashMap get$out$();

    public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();

    public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();

    public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    public fabric.util.LinkedHashMap.LinkedHashEntry fabric$util$LinkedHashMap$LinkedHashEntry$(
        fabric.lang.Object key, fabric.lang.Object value);

    @Override
    public void access();

    @Override
    public fabric.lang.Object cleanup();

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.HashMap.HashEntry._Proxy
        implements fabric.util.LinkedHashMap.LinkedHashEntry {

      @Override
      native public fabric.util.LinkedHashMap get$out$();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry fabric$util$LinkedHashMap$LinkedHashEntry$(
          fabric.lang.Object arg1, fabric.lang.Object arg2);

      public _Proxy(LinkedHashEntry._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    public static class _Impl extends fabric.util.HashMap.HashEntry._Impl
        implements fabric.util.LinkedHashMap.LinkedHashEntry {

      @Override
      native public fabric.util.LinkedHashMap get$out$();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry fabric$util$LinkedHashMap$LinkedHashEntry$(
          fabric.lang.Object key, fabric.lang.Object value);

      @Override
      native public void access();

      @Override
      native public fabric.lang.Object cleanup();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location,
          final fabric.util.LinkedHashMap out$) {
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
          fabric.util.LinkedHashMap.LinkedHashEntry._Static {

        public _Proxy(
            fabric.util.LinkedHashMap.LinkedHashEntry._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.LinkedHashMap.LinkedHashEntry._Static {

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

  public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();

  public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(fabric.util.Map m);

  public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity);

  public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor);

  public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor, boolean accessOrder);

  @Override
  public void clear();

  @Override
  public boolean containsValue(fabric.lang.Object value);

  @Override
  public fabric.lang.Object get(fabric.lang.Object key);

  public boolean removeEldestEntry(fabric.util.Map.Entry eldest);

  @Override
  public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
      int idx, boolean callRemove);

  @Override
  public void putAllInternal(fabric.util.Map m);

  @Override
  public fabric.util.Iterator iterator(fabric.worker.Store store, final int type);

  public static interface LinkedHashIterator extends fabric.util.Iterator,
      fabric.lang.Object {

    public fabric.util.LinkedHashMap get$out$();

    public fabric.util.LinkedHashMap.LinkedHashEntry get$current();

    public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    public fabric.util.LinkedHashMap.LinkedHashEntry get$last();

    public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    public int get$knownMod();

    public int set$knownMod(int val);

    public int postInc$knownMod();

    public int postDec$knownMod();

    public int get$type();

    public int set$type(int val);

    public int postInc$type();

    public int postDec$type();

    public fabric.util.LinkedHashMap.LinkedHashIterator fabric$util$LinkedHashMap$LinkedHashIterator$(
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
        fabric.util.LinkedHashMap.LinkedHashIterator {

      @Override
      native public fabric.util.LinkedHashMap get$out$();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$current();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$last();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public int get$type();

      @Override
      native public int set$type(int val);

      @Override
      native public int postInc$type();

      @Override
      native public int postDec$type();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashIterator fabric$util$LinkedHashMap$LinkedHashIterator$(
          int arg1);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(LinkedHashIterator._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.LinkedHashMap.LinkedHashIterator {

      @Override
      native public fabric.util.LinkedHashMap get$out$();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$current();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry get$last();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
          fabric.util.LinkedHashMap.LinkedHashEntry val);

      @Override
      native public int get$knownMod();

      @Override
      native public int set$knownMod(int val);

      @Override
      native public int postInc$knownMod();

      @Override
      native public int postDec$knownMod();

      @Override
      native public int get$type();

      @Override
      native public int set$type(int val);

      @Override
      native public int postInc$type();

      @Override
      native public int postDec$type();

      @Override
      native public fabric.util.LinkedHashMap.LinkedHashIterator fabric$util$LinkedHashMap$LinkedHashIterator$(
          int type);

      @Override
      native public boolean hasNext();

      @Override
      native public fabric.lang.Object next();

      @Override
      native public void remove();

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location,
          final fabric.util.LinkedHashMap out$) {
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
          fabric.util.LinkedHashMap.LinkedHashIterator._Static {

        public _Proxy(
            fabric.util.LinkedHashMap.LinkedHashIterator._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.LinkedHashMap.LinkedHashIterator._Static {

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

  public static class _Proxy extends fabric.util.HashMap._Proxy implements
      fabric.util.LinkedHashMap {

    @Override
    native public fabric.util.LinkedHashMap.LinkedHashEntry get$root();

    @Override
    native public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    @Override
    native public boolean get$accessOrder();

    @Override
    native public boolean set$accessOrder(boolean val);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        fabric.util.Map arg1);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(int arg1);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        int arg1, float arg2);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        int arg1, float arg2, boolean arg3);

    @Override
    native public boolean removeEldestEntry(fabric.util.Map.Entry arg1);

    public _Proxy(LinkedHashMap._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.util.HashMap._Impl implements
      fabric.util.LinkedHashMap {

    @Override
    native public fabric.util.LinkedHashMap.LinkedHashEntry get$root();

    @Override
    native public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
        fabric.util.LinkedHashMap.LinkedHashEntry val);

    @Override
    native public boolean get$accessOrder();

    @Override
    native public boolean set$accessOrder(boolean val);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        fabric.util.Map m);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        int initialCapacity);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        int initialCapacity, float loadFactor);

    @Override
    native public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
        int initialCapacity, float loadFactor, boolean accessOrder);

    @Override
    native public void clear();

    @Override
    native public boolean containsValue(fabric.lang.Object value);

    @Override
    native public fabric.lang.Object get(fabric.lang.Object key);

    @Override
    native public boolean removeEldestEntry(fabric.util.Map.Entry eldest);

    @Override
    native public void addEntry(fabric.lang.Object key,
        fabric.lang.Object value, int idx, boolean callRemove);

    @Override
    native public void putAllInternal(fabric.util.Map m);

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store store,
        final int type);

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

    public long get$serialVersionUID();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.LinkedHashMap._Static {

      @Override
      native public long get$serialVersionUID();

      public _Proxy(fabric.util.LinkedHashMap._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.LinkedHashMap._Static {

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
