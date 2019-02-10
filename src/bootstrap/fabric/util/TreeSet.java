package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface TreeSet extends fabric.util.SortedSet, fabric.util.AbstractSet
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
    public fabric.util.TreeSet fabric$util$TreeSet$(fabric.util.Comparator comparator);
    
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
    public fabric.util.TreeSet fabric$util$TreeSet$(fabric.util.Collection collection);
    
    /**
   * Construct a new TreeSet, using the same key ordering as the supplied
   * SortedSet and containing all of the elements in the supplied SortedSet.
   * This constructor runs in linear time.
   *
   * @param sortedSet the new TreeSet will use this SortedSet's comparator
   *        and will initialize itself with all its elements
   * @throws NullPointerException if sortedSet is null
   */
    public fabric.util.TreeSet fabric$util$TreeSet$(fabric.util.SortedSet sortedSet);
    
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
    public fabric.util.SortedSet subSet(fabric.lang.Object from, fabric.lang.Object to);
    
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
      implements fabric.util.TreeSet {
        public fabric.util.SortedMap get$map() {
            return ((fabric.util.TreeSet._Impl) fetch()).get$map();
        }
        
        public fabric.util.SortedMap set$map(fabric.util.SortedMap val) {
            return ((fabric.util.TreeSet._Impl) fetch()).set$map(val);
        }
        
        public fabric.util.TreeSet fabric$util$TreeSet$() {
            return ((fabric.util.TreeSet) fetch()).fabric$util$TreeSet$();
        }
        
        public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator arg1) {
            return ((fabric.util.TreeSet) fetch()).fabric$util$TreeSet$(arg1);
        }
        
        public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Collection arg1) {
            return ((fabric.util.TreeSet) fetch()).fabric$util$TreeSet$(arg1);
        }
        
        public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedSet arg1) {
            return ((fabric.util.TreeSet) fetch()).fabric$util$TreeSet$(arg1);
        }
        
        public fabric.util.Comparator comparator() {
            return ((fabric.util.TreeSet) fetch()).comparator();
        }
        
        public fabric.lang.Object first() {
            return ((fabric.util.TreeSet) fetch()).first();
        }
        
        public fabric.util.SortedSet headSet(fabric.lang.Object arg1) {
            return ((fabric.util.TreeSet) fetch()).headSet(arg1);
        }
        
        public fabric.lang.Object last() {
            return ((fabric.util.TreeSet) fetch()).last();
        }
        
        public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                            fabric.lang.Object arg2) {
            return ((fabric.util.TreeSet) fetch()).subSet(arg1, arg2);
        }
        
        public fabric.util.SortedSet tailSet(fabric.lang.Object arg1) {
            return ((fabric.util.TreeSet) fetch()).tailSet(arg1);
        }
        
        public _Proxy(TreeSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.TreeSet {
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
   * ordering of keys. Elements that are not mutually comparable will cause
   * ClassCastExceptions down the road.
   *
   * @see Comparable
   */
        public fabric.util.TreeSet fabric$util$TreeSet$() {
            fabric$util$AbstractSet$();
            this.
              set$map(
                (fabric.util.TreeMap)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap)
                       new fabric.util.TreeMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$TreeMap$()));
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
        /**
   * Construct a new TreeSet whose backing TreeMap uses the supplied
   * Comparator. Elements that are not mutually comparable will cause
   * ClassCastExceptions down the road.
   *
   * @param comparator the Comparator this Set will use
   */
        public fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.Comparator comparator) {
            fabric$util$AbstractSet$();
            this.
              set$map(
                (fabric.util.TreeMap)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap)
                       new fabric.util.TreeMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$TreeMap$(comparator)));
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
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
          fabric.util.Collection collection) {
            fabric$util$AbstractSet$();
            this.
              set$map(
                (fabric.util.TreeMap)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap)
                       new fabric.util.TreeMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$TreeMap$()));
            addAll(collection);
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
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
          fabric.util.SortedSet sortedSet) {
            fabric$util$AbstractSet$();
            this.
              set$map(
                (fabric.util.TreeMap)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap)
                       new fabric.util.TreeMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$TreeMap$(
                                      sortedSet.comparator())));
            fabric.util.Iterator
              itr =
              sortedSet.
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            ((fabric.util.TreeMap)
               fabric.lang.Object._Proxy.$getProxy(this.get$map())).
              putKeysLinear(itr, sortedSet.size());
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
        /**
   * This private constructor is used to implement the subSet() calls around
   * a backing TreeMap.SubMap.
   *
   * @param backingMap the submap
   */
        private fabric.util.TreeSet fabric$util$TreeSet$(
          fabric.util.SortedMap backingMap) {
            fabric$util$AbstractSet$();
            this.set$map(backingMap);
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
        /**
   * Adds the spplied Object to the Set if it is not already in the Set;
   * returns true if the element is added, false otherwise.
   *
   * @param obj the Object to be added to this Set
   * @throws ClassCastException if the element cannot be compared with objects
   *         already in the set
   */
        public boolean add(fabric.lang.Object obj) {
            return fabric.lang.Object._Proxy.
              idEquals(
                this.get$map().put(obj,
                                   fabric.lang.WrappedJavaInlineable.$wrap("")),
                null);
        }
        
        /**
   * Adds all of the elements in the supplied Collection to this TreeSet.
   *
   * @param c The collection to add
   * @return true if the Set is altered, false otherwise
   * @throws NullPointerException if c is null
   * @throws ClassCastException if an element in c cannot be compared with
   *         objects already in the set
   */
        public boolean addAll(fabric.util.Collection c) {
            boolean result = false;
            int pos = c.size();
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            while (--pos >= 0)
                result |=
                  fabric.lang.Object._Proxy.
                    idEquals(
                      this.get$map().put(
                                       itr.next(),
                                       fabric.lang.WrappedJavaInlineable.$wrap(
                                                                           "")),
                      null);
            return result;
        }
        
        /**
   * Removes all elements in this Set.
   */
        public void clear() { this.get$map().clear(); }
        
        /**
   * Returns this Set's comparator.
   *
   * @return the comparator, or null if the set uses natural ordering
   */
        public fabric.util.Comparator comparator() {
            return this.get$map().comparator();
        }
        
        /**
   * Returns true if this Set contains the supplied Object, false otherwise.
   *
   * @param obj the Object to check for
   * @return true if it is in the set
   * @throws ClassCastException if obj cannot be compared with objects
   *         already in the set
   */
        public boolean contains(fabric.lang.Object obj) {
            return this.get$map().containsKey(obj);
        }
        
        /**
   * Returns the first (by order) element in this Set.
   *
   * @return the first element
   * @throws NoSuchElementException if the set is empty
   */
        public fabric.lang.Object first() { return this.get$map().firstKey(); }
        
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
        public fabric.util.SortedSet headSet(fabric.lang.Object to) {
            return (fabric.util.TreeSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeSet._Impl)
                          ((fabric.util.TreeSet)
                             new fabric.util.TreeSet._Impl(
                               this.$getStore()).$getProxy()).fetch()).
                           fabric$util$TreeSet$(this.get$map().headMap(to)));
        }
        
        /**
   * Returns true if this Set has size 0, false otherwise.
   *
   * @return true if the set is empty
   */
        public boolean isEmpty() { return this.get$map().isEmpty(); }
        
        /**
   * Returns in Iterator over the elements in this TreeSet, which traverses
   * in ascending order.
   *
   * @return an iterator
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store) {
            return this.get$map().keySet().iterator(store);
        }
        
        /**
   * Returns the last (by order) element in this Set.
   *
   * @return the last element
   * @throws NoSuchElementException if the set is empty
   */
        public fabric.lang.Object last() { return this.get$map().lastKey(); }
        
        /**
   * If the supplied Object is in this Set, it is removed, and true is
   * returned; otherwise, false is returned.
   *
   * @param obj the Object to remove from this Set
   * @return true if the set was modified
   * @throws ClassCastException if obj cannot be compared to set elements
   */
        public boolean remove(fabric.lang.Object obj) {
            return !fabric.lang.Object._Proxy.idEquals(
                                                this.get$map().remove(obj),
                                                null);
        }
        
        /**
   * Returns the number of elements in this Set
   *
   * @return the set size
   */
        public int size() { return this.get$map().size(); }
        
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
                                            fabric.lang.Object to) {
            return (fabric.util.TreeSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeSet._Impl)
                          ((fabric.util.TreeSet)
                             new fabric.util.TreeSet._Impl(
                               this.$getStore()).$getProxy()).fetch()).
                           fabric$util$TreeSet$(this.get$map().subMap(from,
                                                                      to)));
        }
        
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
        public fabric.util.SortedSet tailSet(fabric.lang.Object from) {
            return (fabric.util.TreeSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeSet._Impl)
                          ((fabric.util.TreeSet)
                             new fabric.util.TreeSet._Impl(
                               this.$getStore()).$getProxy()).fetch()).
                           fabric$util$TreeSet$(this.get$map().tailMap(from)));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.TreeSet) this.$getProxy();
        }
        
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
          implements fabric.util.TreeSet._Static {
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
                  _Impl
                  impl =
                  (fabric.util.TreeSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.TreeSet._Static._Impl.class);
                $instance = (fabric.util.TreeSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeSet._Static {
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
                return new fabric.util.TreeSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -73, -91, -17, 31, -52,
    -64, 59, 108, -37, -74, 65, 62, 2, -43, -33, -36, -19, -50, 81, -95, 95,
    -40, -106, 16, -20, -95, 60, -120, 75, 113, 83, 6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
