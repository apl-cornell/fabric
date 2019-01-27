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
    
    public static final byte[] $classHash = new byte[] { -4, 100, 4, 109, 21, 6,
    92, -126, -105, -54, 104, -13, 4, -94, 24, 49, -86, -126, -106, -64, -53, 3,
    86, 105, 15, -6, -5, -76, -43, 56, -18, -76 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
