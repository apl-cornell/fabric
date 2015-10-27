package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
  extends fabric.util.Set, fabric.util.AbstractCollection
{
    
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
     * Returns a hash code for this Set. The hash code of a Set is the sum of
     the
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
      implements fabric.util.AbstractSet
    {
        
        public native fabric.util.AbstractSet fabric$util$AbstractSet$();
        
        public _Proxy(AbstractSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractSet
    {
        
        /**
         * The main constructor, for use by subclasses.
         */
        public native fabric.util.AbstractSet fabric$util$AbstractSet$();
        
        /**
         * Tests whether the given object is equal to this Set. This
         implementation
         * first checks whether this set <em>is</em> the given object, and
         returns
         * true if so. Otherwise, if o is a Set and is the same size as this
         one, it
         * returns the result of calling containsAll on the given Set.
         Otherwise, it
         * returns false.
         *
         * @param o the Object to be tested for equality with this Set
         * @return true if the given object is equal to this Set
         */
        public native boolean equals(fabric.lang.Object o);
        
        /**
         * Returns a hash code for this Set. The hash code of a Set is the sum
         of the
         * hash codes of all its elements, except that the hash code of null is
         * defined to be zero. This implementation obtains an Iterator over the
         Set,
         * and sums the results.
         *
         * @return a hash code for this Set
         */
        public native int hashCode();
        
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
        public native boolean removeAll(fabric.util.Collection c);
        
        public native fabric.util.Iterator iterator();
        
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
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSet._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.AbstractSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractSet._Static._Impl.class);
                $instance = (fabric.util.AbstractSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSet._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
