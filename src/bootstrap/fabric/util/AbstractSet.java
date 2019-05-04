package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * An abstract implementation of Set to make it easier to create your own
 * implementations. In order to create a Set, subclass AbstractSet and
 * implement the same methods that are required for AbstractCollection
 * (although these methods must of course meet the requirements that Set puts
 * on them - specifically, no element may be in the set more than once). This
 * class simply provides implementations of equals() and hashCode() to fulfil
 * the requirements placed on them by the Set interface.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see AbstractCollection
 * @see Set
 * @see HashSet
 * @see TreeSet
 * @see LinkedHashSet
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractSet
  extends fabric.util.Set, fabric.util.AbstractCollection {
    /**
   * The main constructor, for use by subclasses.
   */
    public fabric.util.AbstractSet fabric$util$AbstractSet$();
    
    /**
   * Tests whether the given object is equal to this Set. This implementation
   * first checks whether this set <em>is</em> the given object, and returns
   * true if so. Otherwise, if o is a Set and is the same size as this one, it
   * returns the result of calling containsAll on the given Set. Otherwise, it
   * returns false.
   *
   * @param o the Object to be tested for equality with this Set
   * @return true if the given object is equal to this Set
   */
    public boolean equals(fabric.lang.Object o);
    
    /**
   * Returns a hash code for this Set. The hash code of a Set is the sum of the
   * hash codes of all its elements, except that the hash code of null is
   * defined to be zero. This implementation obtains an Iterator over the Set,
   * and sums the results.
   *
   * @return a hash code for this Set
   */
    public int hashCode();
    
    /**
   * Removes from this set all elements in the given collection (optional
   * operation). This implementation uses <code>size()</code> to determine
   * the smaller collection.  Then, if this set is smaller, it iterates
   * over the set, calling Iterator.remove if the collection contains
   * the element.  If this set is larger, it iterates over the collection,
   * calling Set.remove for all elements in the collection. Note that
   * this operation will fail if a remove methods is not supported.
   *
   * @param c the collection of elements to remove
   * @return true if the set was modified as a result
   * @throws UnsupportedOperationException if remove is not supported
   * @throws NullPointerException if the collection is null
   * @see AbstractCollection#remove(Object)
   * @see Collection#contains(Object)
   * @see Iterator#remove()
   */
    public boolean removeAll(fabric.util.Collection c);
    
    public fabric.util.Iterator iterator();
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractSet {
        public fabric.util.AbstractSet fabric$util$AbstractSet$() {
            return ((fabric.util.AbstractSet) fetch()).fabric$util$AbstractSet$(
                                                         );
        }
        
        public _Proxy(AbstractSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractSet {
        /**
   * The main constructor, for use by subclasses.
   */
        public fabric.util.AbstractSet fabric$util$AbstractSet$() {
            fabric$util$AbstractCollection$();
            return (fabric.util.AbstractSet) this.$getProxy();
        }
        
        /**
   * Tests whether the given object is equal to this Set. This implementation
   * first checks whether this set <em>is</em> the given object, and returns
   * true if so. Otherwise, if o is a Set and is the same size as this one, it
   * returns the result of calling containsAll on the given Set. Otherwise, it
   * returns false.
   *
   * @param o the Object to be tested for equality with this Set
   * @return true if the given object is equal to this Set
   */
        public boolean equals(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.
              idEquals(o, (fabric.util.AbstractSet) this.$getProxy()) ||
              fabric.lang.Object._Proxy.
              $getProxy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap(o)) instanceof fabric.util.Set &&
              ((fabric.util.Set) fabric.lang.Object._Proxy.$getProxy(o)).size(
                                                                           ) ==
              size() &&
              containsAll((fabric.util.Collection)
                            fabric.lang.Object._Proxy.$getProxy(o));
        }
        
        /**
   * Returns a hash code for this Set. The hash code of a Set is the sum of the
   * hash codes of all its elements, except that the hash code of null is
   * defined to be zero. This implementation obtains an Iterator over the Set,
   * and sums the results.
   *
   * @return a hash code for this Set
   */
        public int hashCode() {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int hash = 0;
            int pos = size();
            while (--pos >= 0)
                hash +=
                  fabric.util.AbstractCollection._Impl.hashCode(itr.next());
            return hash;
        }
        
        /**
   * Removes from this set all elements in the given collection (optional
   * operation). This implementation uses <code>size()</code> to determine
   * the smaller collection.  Then, if this set is smaller, it iterates
   * over the set, calling Iterator.remove if the collection contains
   * the element.  If this set is larger, it iterates over the collection,
   * calling Set.remove for all elements in the collection. Note that
   * this operation will fail if a remove methods is not supported.
   *
   * @param c the collection of elements to remove
   * @return true if the set was modified as a result
   * @throws UnsupportedOperationException if remove is not supported
   * @throws NullPointerException if the collection is null
   * @see AbstractCollection#remove(Object)
   * @see Collection#contains(Object)
   * @see Iterator#remove()
   */
        public boolean removeAll(fabric.util.Collection c) {
            int oldsize = size();
            int count = c.size();
            fabric.util.Iterator i;
            if (oldsize < count) {
                for (i =
                       iterator(
                         fabric.util.AbstractCollection._Static._Proxy.$instance.
                             get$LOCAL_STORE()),
                       count = oldsize;
                     count > 0;
                     count--)
                    if (c.contains(i.next())) i.remove();
            }
            else
                for (i =
                       c.iterator(
                           fabric.util.AbstractCollection._Static._Proxy.$instance.get$LOCAL_STORE(
                                                                                     ));
                     count > 0;
                     count--)
                    remove(i.next());
            return oldsize != size();
        }
        
        public fabric.util.Iterator iterator() {
            return iterator(
                     fabric.util.AbstractCollection._Static._Proxy.$instance.
                         get$LOCAL_STORE());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSet._Static {
            public _Proxy(fabric.util.AbstractSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractSet._Static $instance;
            
            static {
                fabric.
                  util.
                  AbstractSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.AbstractSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractSet._Static._Impl.class);
                $instance = (fabric.util.AbstractSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSet._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -98, -88, 9, -122, -93,
    -47, 66, 11, -17, -108, 38, -49, 121, 108, -113, -44, 61, 18, -9, 20, -16,
    -87, -48, -3, 87, 35, 37, 94, 69, 17, 98, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
