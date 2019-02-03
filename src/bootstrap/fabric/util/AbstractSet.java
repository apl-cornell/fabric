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
    
    public static final byte[] $classHash = new byte[] { 91, 124, -75, 55, 17,
    -3, 20, -64, -99, -84, 70, 116, -69, 28, -93, -2, 123, 89, -45, 121, 34, 15,
    -80, -128, -39, 106, -87, -53, -72, 23, 16, -20 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
