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
    
    public static final byte[] $classHash = new byte[] { 10, -93, -128, 46, 115,
    -105, -120, 86, -66, 25, 58, -15, -30, -19, -104, -5, -87, -48, 71, -37,
    -30, 0, 123, -45, 94, -61, 41, -40, -32, 101, -54, 16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
