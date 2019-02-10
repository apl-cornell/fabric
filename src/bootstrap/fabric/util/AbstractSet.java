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
    
    public static final byte[] $classHash = new byte[] { -32, -17, -30, -117,
    56, -127, -86, -73, 11, 50, -88, 77, 71, -13, 35, 7, 24, -38, -82, -61, -2,
    51, -13, -8, 25, 28, 123, -52, 66, -64, 96, 64 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
