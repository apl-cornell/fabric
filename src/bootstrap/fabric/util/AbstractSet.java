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
    
    public static final byte[] $classHash = new byte[] { -63, -10, -119, 21, 9,
    115, -117, 106, -10, -6, -48, 25, 37, -4, 58, 53, 33, 3, -124, 87, -42, -55,
    105, -117, -85, 75, -125, 82, -97, 85, -66, 28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
