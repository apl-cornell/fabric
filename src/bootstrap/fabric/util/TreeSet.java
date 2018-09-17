package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * This class provides a TreeMap-backed implementation of the SortedSet
 * interface. The elements will be sorted according to their <i>natural
 * order</i>, or according to the provided <code>Comparator</code>.<p>
 *
 * Most operations are O(log n), but there is so much overhead that this
 * makes small sets expensive. Note that the ordering must be <i>consistent
 * with equals</i> to correctly implement the Set interface. If this
 * condition is violated, the set is still well-behaved, but you may have
 * suprising results when comparing it to other sets.<p>
 *
 * This implementation is not synchronized. If you need to share this between
 * multiple threads, do something like:<br>
 * <code>SortedSet s
 *       = Collections.synchronizedSortedSet(new TreeSet(...));</code><p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * <code>ConcurrentModificationException</code> rather than exhibit
 * non-deterministic behavior.
 *
 * @author Jon Zeppieri
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Set
 * @see HashSet
 * @see LinkedHashSet
 * @see Comparable
 * @see Comparator
 * @see Collections#synchronizedSortedSet(SortedSet)
 * @see TreeMap
 * @since 1.2
 * @status updated to 1.4
 */
public interface TreeSet
  extends fabric.util.SortedSet, fabric.util.AbstractSet
{
    
    public fabric.util.SortedMap get$map();
    
    public fabric.util.SortedMap set$map(fabric.util.SortedMap val);
    
    /**
     * Construct a new TreeSet whose backing TreeMap using the "natural"
     * ordering of keys. Elements that are not mutually comparable will cause
     * ClassCastExceptions down the road.
     *
     * @see Comparable
     */
    public fabric.util.TreeSet fabric$util$TreeSet$();
    
    /**
     * Construct a new TreeSet whose backing TreeMap uses the supplied
     * Comparator. Elements that are not mutually comparable will cause
     * ClassCastExceptions down the road.
     *
     * @param comparator the Comparator this Set will use
     */
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Comparator comparator);
    
    /**
     * Construct a new TreeSet whose backing TreeMap uses the "natural"
     * orering of the keys and which contains all of the elements in the
     * supplied Collection. This runs in n*log(n) time.
     *
     * @param collection the new Set will be initialized with all
     *        of the elements in this Collection
     * @throws ClassCastException if the elements of the collection are not
     *         comparable
     * @throws NullPointerException if the collection is null
     * @see Comparable
     */
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.Collection collection);
    
    /**
     * Construct a new TreeSet, using the same key ordering as the supplied
     * SortedSet and containing all of the elements in the supplied SortedSet.
     * This constructor runs in linear time.
     *
     * @param sortedSet the new TreeSet will use this SortedSet's comparator
     *        and will initialize itself with all its elements
     * @throws NullPointerException if sortedSet is null
     */
    public fabric.util.TreeSet fabric$util$TreeSet$(
      fabric.util.SortedSet sortedSet);
    
    /**
     * Adds the spplied Object to the Set if it is not already in the Set;
     * returns true if the element is added, false otherwise.
     *
     * @param obj the Object to be added to this Set
     * @throws ClassCastException if the element cannot be compared with objects
     *         already in the set
     */
    public boolean add(fabric.lang.Object obj);
    
    /**
     * Adds all of the elements in the supplied Collection to this TreeSet.
     *
     * @param c The collection to add
     * @return true if the Set is altered, false otherwise
     * @throws NullPointerException if c is null
     * @throws ClassCastException if an element in c cannot be compared with
     *         objects already in the set
     */
    public boolean addAll(fabric.util.Collection c);
    
    /**
     * Removes all elements in this Set.
     */
    public void clear();
    
    /**
     * Returns this Set's comparator.
     *
     * @return the comparator, or null if the set uses natural ordering
     */
    public fabric.util.Comparator comparator();
    
    /**
     * Returns true if this Set contains the supplied Object, false otherwise.
     *
     * @param obj the Object to check for
     * @return true if it is in the set
     * @throws ClassCastException if obj cannot be compared with objects
     *         already in the set
     */
    public boolean contains(fabric.lang.Object obj);
    
    /**
     * Returns the first (by order) element in this Set.
     *
     * @return the first element
     * @throws NoSuchElementException if the set is empty
     */
    public fabric.lang.Object first();
    
    /**
     * Returns a view of this Set including all elements less than
     * <code>to</code>. The returned set is backed by the original, so changes
     * in one appear in the other. The subset will throw an
     * {@link IllegalArgumentException} for any attempt to access or add an
     * element beyond the specified cutoff. The returned set does not include
     * the endpoint; if you want inclusion, pass the successor element.
     *
     * @param to the (exclusive) cutoff point
     * @return a view of the set less than the cutoff
     * @throws ClassCastException if <code>to</code> is not compatible with
     *         the comparator (or is not Comparable, for natural ordering)
     * @throws NullPointerException if to is null, but the comparator does not
     *         tolerate null elements
     */
    public fabric.util.SortedSet headSet(fabric.lang.Object to);
    
    /**
     * Returns true if this Set has size 0, false otherwise.
     *
     * @return true if the set is empty
     */
    public boolean isEmpty();
    
    /**
     * Returns in Iterator over the elements in this TreeSet, which traverses
     * in ascending order.
     *
     * @return an iterator
     */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
     * Returns the last (by order) element in this Set.
     *
     * @return the last element
     * @throws NoSuchElementException if the set is empty
     */
    public fabric.lang.Object last();
    
    /**
     * If the supplied Object is in this Set, it is removed, and true is
     * returned; otherwise, false is returned.
     *
     * @param obj the Object to remove from this Set
     * @return true if the set was modified
     * @throws ClassCastException if obj cannot be compared to set elements
     */
    public boolean remove(fabric.lang.Object obj);
    
    /**
     * Returns the number of elements in this Set
     *
     * @return the set size
     */
    public int size();
    
    /**
     * Returns a view of this Set including all elements greater or equal to
     * <code>from</code> and less than <code>to</code> (a half-open interval).
     * The returned set is backed by the original, so changes in one appear in
     * the other. The subset will throw an {@link IllegalArgumentException}
     * for any attempt to access or add an element beyond the specified cutoffs.
     * The returned set includes the low endpoint but not the high; if you want
     * to reverse this behavior on either end, pass in the successor element.
     *
     * @param from the (inclusive) low cutoff point
     * @param to the (exclusive) high cutoff point
     * @return a view of the set between the cutoffs
     * @throws ClassCastException if either cutoff is not compatible with
     *         the comparator (or is not Comparable, for natural ordering)
     * @throws NullPointerException if from or to is null, but the comparator
     *         does not tolerate null elements
     * @throws IllegalArgumentException if from is greater than to
     */
    public fabric.util.SortedSet subSet(fabric.lang.Object from,
                                        fabric.lang.Object to);
    
    /**
     * Returns a view of this Set including all elements greater or equal to
     * <code>from</code>. The returned set is backed by the original, so
     * changes in one appear in the other. The subset will throw an
     * {@link IllegalArgumentException} for any attempt to access or add an
     * element beyond the specified cutoff. The returned set includes the
     * endpoint; if you want to exclude it, pass in the successor element.
     *
     * @param from the (inclusive) low cutoff point
     * @return a view of the set above the cutoff
     * @throws ClassCastException if <code>from</code> is not compatible with
     *         the comparator (or is not Comparable, for natural ordering)
     * @throws NullPointerException if from is null, but the comparator
     *         does not tolerate null elements
     */
    public fabric.util.SortedSet tailSet(fabric.lang.Object from);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.TreeSet
    {
        
        public fabric.util.SortedMap get$map() {
            return ((fabric.util.TreeSet._Impl) fetch()).get$map();
        }
        
        public fabric.util.SortedMap set$map(fabric.util.SortedMap val) {
            return ((fabric.util.TreeSet._Impl) fetch()).set$map(val);
        }
        
        public native fabric.util.TreeSet fabric$util$TreeSet$();
        
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator arg1);
        
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Collection arg1);
        
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedSet arg1);
        
        public native fabric.util.Comparator comparator();
        
        public native fabric.lang.Object first();
        
        public native fabric.util.SortedSet headSet(fabric.lang.Object arg1);
        
        public native fabric.lang.Object last();
        
        public native fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        public native fabric.util.SortedSet tailSet(fabric.lang.Object arg1);
        
        public _Proxy(TreeSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.TreeSet
    {
        
        public fabric.util.SortedMap get$map() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.map;
        }
        
        public fabric.util.SortedMap set$map(fabric.util.SortedMap val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.map = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The SortedMap which backs this Set.
         */
        private fabric.util.SortedMap map;
        
        /**
         * Construct a new TreeSet whose backing TreeMap using the "natural"
         * ordering of keys. Elements that are not mutually comparable will
         cause
         * ClassCastExceptions down the road.
         *
         * @see Comparable
         */
        public native fabric.util.TreeSet fabric$util$TreeSet$();
        
        /**
         * Construct a new TreeSet whose backing TreeMap uses the supplied
         * Comparator. Elements that are not mutually comparable will cause
         * ClassCastExceptions down the road.
         *
         * @param comparator the Comparator this Set will use
         */
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator comparator);
        
        /**
         * Construct a new TreeSet whose backing TreeMap uses the "natural"
         * orering of the keys and which contains all of the elements in the
         * supplied Collection. This runs in n*log(n) time.
         *
         * @param collection the new Set will be initialized with all
         *        of the elements in this Collection
         * @throws ClassCastException if the elements of the collection are not
         *         comparable
         * @throws NullPointerException if the collection is null
         * @see Comparable
         */
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Collection collection);
        
        /**
         * Construct a new TreeSet, using the same key ordering as the supplied
         * SortedSet and containing all of the elements in the supplied
         SortedSet.
         * This constructor runs in linear time.
         *
         * @param sortedSet the new TreeSet will use this SortedSet's comparator
         *        and will initialize itself with all its elements
         * @throws NullPointerException if sortedSet is null
         */
        public native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedSet sortedSet);
        
        /**
         * This private constructor is used to implement the subSet() calls
         around
         * a backing TreeMap.SubMap.
         *
         * @param backingMap the submap
         */
        private native fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedMap backingMap);
        
        /**
         * Adds the spplied Object to the Set if it is not already in the Set;
         * returns true if the element is added, false otherwise.
         *
         * @param obj the Object to be added to this Set
         * @throws ClassCastException if the element cannot be compared with
         objects
         *         already in the set
         */
        public native boolean add(fabric.lang.Object obj);
        
        /**
         * Adds all of the elements in the supplied Collection to this TreeSet.
         *
         * @param c The collection to add
         * @return true if the Set is altered, false otherwise
         * @throws NullPointerException if c is null
         * @throws ClassCastException if an element in c cannot be compared with
         *         objects already in the set
         */
        public native boolean addAll(fabric.util.Collection c);
        
        /**
         * Removes all elements in this Set.
         */
        public native void clear();
        
        /**
         * Returns this Set's comparator.
         *
         * @return the comparator, or null if the set uses natural ordering
         */
        public native fabric.util.Comparator comparator();
        
        /**
         * Returns true if this Set contains the supplied Object, false
         otherwise.
         *
         * @param obj the Object to check for
         * @return true if it is in the set
         * @throws ClassCastException if obj cannot be compared with objects
         *         already in the set
         */
        public native boolean contains(fabric.lang.Object obj);
        
        /**
         * Returns the first (by order) element in this Set.
         *
         * @return the first element
         * @throws NoSuchElementException if the set is empty
         */
        public native fabric.lang.Object first();
        
        /**
         * Returns a view of this Set including all elements less than
         * <code>to</code>. The returned set is backed by the original, so
         changes
         * in one appear in the other. The subset will throw an
         * {@link IllegalArgumentException} for any attempt to access or add an
         * element beyond the specified cutoff. The returned set does not
         include
         * the endpoint; if you want inclusion, pass the successor element.
         *
         * @param to the (exclusive) cutoff point
         * @return a view of the set less than the cutoff
         * @throws ClassCastException if <code>to</code> is not compatible with
         *         the comparator (or is not Comparable, for natural ordering)
         * @throws NullPointerException if to is null, but the comparator does
         not
         *         tolerate null elements
         */
        public native fabric.util.SortedSet headSet(fabric.lang.Object to);
        
        /**
         * Returns true if this Set has size 0, false otherwise.
         *
         * @return true if the set is empty
         */
        public native boolean isEmpty();
        
        /**
         * Returns in Iterator over the elements in this TreeSet, which
         traverses
         * in ascending order.
         *
         * @return an iterator
         */
        public native fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
         * Returns the last (by order) element in this Set.
         *
         * @return the last element
         * @throws NoSuchElementException if the set is empty
         */
        public native fabric.lang.Object last();
        
        /**
         * If the supplied Object is in this Set, it is removed, and true is
         * returned; otherwise, false is returned.
         *
         * @param obj the Object to remove from this Set
         * @return true if the set was modified
         * @throws ClassCastException if obj cannot be compared to set elements
         */
        public native boolean remove(fabric.lang.Object obj);
        
        /**
         * Returns the number of elements in this Set
         *
         * @return the set size
         */
        public native int size();
        
        /**
         * Returns a view of this Set including all elements greater or equal to
         * <code>from</code> and less than <code>to</code> (a half-open
         interval).
         * The returned set is backed by the original, so changes in one appear
         in
         * the other. The subset will throw an {@link IllegalArgumentException}
         * for any attempt to access or add an element beyond the specified
         cutoffs.
         * The returned set includes the low endpoint but not the high; if you
         want
         * to reverse this behavior on either end, pass in the successor
         element.
         *
         * @param from the (inclusive) low cutoff point
         * @param to the (exclusive) high cutoff point
         * @return a view of the set between the cutoffs
         * @throws ClassCastException if either cutoff is not compatible with
         *         the comparator (or is not Comparable, for natural ordering)
         * @throws NullPointerException if from or to is null, but the
         comparator
         *         does not tolerate null elements
         * @throws IllegalArgumentException if from is greater than to
         */
        public native fabric.util.SortedSet subSet(fabric.lang.Object from,
                                                   fabric.lang.Object to);
        
        /**
         * Returns a view of this Set including all elements greater or equal to
         * <code>from</code>. The returned set is backed by the original, so
         * changes in one appear in the other. The subset will throw an
         * {@link IllegalArgumentException} for any attempt to access or add an
         * element beyond the specified cutoff. The returned set includes the
         * endpoint; if you want to exclude it, pass in the successor element.
         *
         * @param from the (inclusive) low cutoff point
         * @return a view of the set above the cutoff
         * @throws ClassCastException if <code>from</code> is not compatible
         with
         *         the comparator (or is not Comparable, for natural ordering)
         * @throws NullPointerException if from is null, but the comparator
         *         does not tolerate null elements
         */
        public native fabric.util.SortedSet tailSet(fabric.lang.Object from);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.TreeSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.map, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, observers, treaties, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.map = (fabric.util.SortedMap)
                         $readRef(fabric.util.SortedMap._Proxy.class,
                                  (fabric.common.RefTypeEnum) refTypes.next(),
                                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.TreeSet._Impl src = (fabric.util.TreeSet._Impl) other;
            this.map = src.map;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.TreeSet._Static
        {
            
            public _Proxy(fabric.util.TreeSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.TreeSet._Static $instance;
            
            static {
                fabric.
                  util.
                  TreeSet.
                  _Static.
                  _Impl impl =
                  (fabric.util.TreeSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.TreeSet._Static._Impl.class);
                $instance = (fabric.util.TreeSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeSet._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.metrics.treaties.TreatySet treaties, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, observers, treaties, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
