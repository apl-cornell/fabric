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
