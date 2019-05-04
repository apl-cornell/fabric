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
    
    public static final byte[] $classHash = new byte[] { 34, -40, 98, -10, -125,
    -61, 98, 48, -32, 43, -120, 101, 94, -107, 122, 89, -62, 73, -85, 91, -128,
    126, 92, 28, -39, 98, 17, 119, -58, 21, 42, 107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
