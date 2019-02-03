package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * This class provides a red-black tree implementation of the SortedMap
 * interface.  Elements in the Map will be sorted by either a user-provided
 * Comparator object, or by the natural ordering of the keys.
 *
 * The algorithms are adopted from Corman, Leiserson, and Rivest's
 * <i>Introduction to Algorithms.</i>  TreeMap guarantees O(log n)
 * insertion and deletion of elements.  That being said, there is a large
 * enough constant coefficient in front of that "log n" (overhead involved
 * in keeping the tree balanced), that TreeMap may not be the best choice
 * for small collections. If something is already sorted, you may want to
 * just use a LinkedHashMap to maintain the order while providing O(1) access.
 *
 * TreeMap is a part of the JDK1.2 Collections API.  Null keys are allowed
 * only if a Comparator is used which can deal with them; natural ordering
 * cannot cope with null.  Null values are always allowed. Note that the
 * ordering must be <i>consistent with equals</i> to correctly implement
 * the Map interface. If this condition is violated, the map is still
 * well-behaved, but you may have suprising results when comparing it to
 * other maps.<p>
 *
 * This implementation is not synchronized. If you need to share this between
 * multiple threads, do something like:<br>
 * <code>SortedMap m
 *       = Collections.synchronizedSortedMap(new TreeMap(...));</code><p>
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
 * @see Map
 * @see HashMap
 * @see Hashtable
 * @see LinkedHashMap
 * @see Comparable
 * @see Comparator
 * @see Collection
 * @see Collections#synchronizedSortedMap(SortedMap)
 * @since 1.2
 * @status updated to 1.4
 */
public interface TreeMap extends fabric.util.SortedMap, fabric.util.AbstractMap
{
    public fabric.util.TreeMap.Node get$nil();
    
    public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val);
    
    public fabric.util.TreeMap.Node get$root();
    
    public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val);
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.util.Set get$entries();
    
    public fabric.util.Set set$entries(fabric.util.Set val);
    
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    public fabric.util.Comparator get$comparator();
    
    public fabric.util.Comparator set$comparator(fabric.util.Comparator val);
    
    /**
   * Class to represent an entry in the tree. Holds a single key-value pair,
   * plus pointers to parent and child nodes.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface Node extends BasicMapEntry {
        public int get$color();
        
        public int set$color(int val);
        
        public int postInc$color();
        
        public int postDec$color();
        
        public fabric.util.TreeMap.Node get$left();
        
        public fabric.util.TreeMap.Node set$left(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$right();
        
        public fabric.util.TreeMap.Node set$right(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$parent();
        
        public fabric.util.TreeMap.Node set$parent(fabric.util.TreeMap.Node val);
        
        /**
     * Simple constructor.
     * @param key the key
     * @param value the value
     */
        public Node fabric$util$TreeMap$Node$(fabric.lang.Object key,
                                              fabric.lang.Object value,
                                              int color);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractMap.BasicMapEntry._Proxy implements Node {
            public int get$color() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).get$color();
            }
            
            public int set$color(int val) {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).set$color(
                                                                    val);
            }
            
            public int postInc$color() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).postInc$color(
                                                                    );
            }
            
            public int postDec$color() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).postDec$color(
                                                                    );
            }
            
            public fabric.util.TreeMap.Node get$left() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).get$left();
            }
            
            public fabric.util.TreeMap.Node set$left(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).set$left(val);
            }
            
            public fabric.util.TreeMap.Node get$right() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).get$right();
            }
            
            public fabric.util.TreeMap.Node set$right(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).set$right(
                                                                    val);
            }
            
            public fabric.util.TreeMap.Node get$parent() {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).get$parent();
            }
            
            public fabric.util.TreeMap.Node set$parent(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.Node._Impl) fetch()).set$parent(
                                                                    val);
            }
            
            public fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
              fabric.lang.Object arg1, fabric.lang.Object arg2, int arg3) {
                return ((fabric.util.TreeMap.Node) fetch()).
                  fabric$util$TreeMap$Node$(arg1, arg2, arg3);
            }
            
            public _Proxy(Node._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl implements Node {
            public int get$color() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.color;
            }
            
            public int set$color(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.color = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$color() {
                int tmp = this.get$color();
                this.set$color((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$color() {
                int tmp = this.get$color();
                this.set$color((int) (tmp - 1));
                return tmp;
            }
            
            /** The color of this node. */
            int color;
            
            public fabric.util.TreeMap.Node get$left() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.left;
            }
            
            public fabric.util.TreeMap.Node set$left(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.left = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The left child node. */
            Node left;
            
            public fabric.util.TreeMap.Node get$right() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.right;
            }
            
            public fabric.util.TreeMap.Node set$right(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.right = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The right child node. */
            Node right;
            
            public fabric.util.TreeMap.Node get$parent() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.parent;
            }
            
            public fabric.util.TreeMap.Node set$parent(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.parent = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The parent node. */
            Node parent;
            
            /**
     * Simple constructor.
     * @param key the key
     * @param value the value
     */
            public Node fabric$util$TreeMap$Node$(fabric.lang.Object key,
                                                  fabric.lang.Object value,
                                                  int color) {
                fabric$util$AbstractMap$BasicMapEntry$(key, value);
                this.set$color((int) color);
                return (Node) this.$getProxy();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Node) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Node._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.color);
                $writeRef($getStore(), this.left, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.right, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.parent, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.color = in.readInt();
                this.left = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.right = (fabric.util.TreeMap.Node)
                               $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
                this.parent = (fabric.util.TreeMap.Node)
                                $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(), in, store,
                                         intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.Node._Impl src =
                  (fabric.util.TreeMap.Node._Impl) other;
                this.color = src.color;
                this.left = src.left;
                this.right = src.right;
                this.parent = src.parent;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Node._Static {
                public _Proxy(fabric.util.TreeMap.Node._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Node._Static $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Node.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Node._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Node._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Node._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Node._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.Node._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 20, -2, 50, 71, 12,
        -69, 94, 53, 11, 119, -112, -46, 107, -10, -84, 58, 79, 7, -89, 89, -41,
        -47, 73, 3, 21, 56, -52, 34, -77, 88, -94, -102 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * Instantiate a new TreeMap with no elements, using the keys' natural
   * ordering to sort. All entries in the map must have a key which implements
   * Comparable, and which are <i>mutually comparable</i>, otherwise map
   * operations may throw a {@link ClassCastException}. Attempts to use
   * a null key will throw a {@link NullPointerException}.
   *
   * @see Comparable
   */
    public fabric.util.TreeMap fabric$util$TreeMap$();
    
    /**
   * Instantiate a new TreeMap with no elements, using the provided comparator
   * to sort. All entries in the map must have keys which are mutually
   * comparable by the Comparator, otherwise map operations may throw a
   * {@link ClassCastException}.
   *
   * @param c the sort order for the keys of this map, or null
   *        for the natural order
   */
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Comparator c);
    
    /**
   * Instantiate a new TreeMap, initializing it with all of the elements in
   * the provided Map.  The elements will be sorted using the natural
   * ordering of the keys. This algorithm runs in n*log(n) time. All entries
   * in the map must have keys which implement Comparable and are mutually
   * comparable, otherwise map operations may throw a
   * {@link ClassCastException}.
   *
   * @param map a Map, whose entries will be put into this TreeMap
   * @throws ClassCastException if the keys in the provided Map are not
   *         comparable
   * @throws NullPointerException if map is null
   * @see Comparable
   */
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map map);
    
    /**
   * Instantiate a new TreeMap, initializing it with all of the elements in
   * the provided SortedMap.  The elements will be sorted using the same
   * comparator as in the provided SortedMap. This runs in linear time.
   *
   * @param sm a SortedMap, whose entries will be put into this TreeMap
   * @throws NullPointerException if sm is null
   */
    public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.SortedMap sm);
    
    /**
   * Clears the Map so it has no keys. This is O(1).
   */
    public void clear();
    
    /**
   * Return the comparator used to sort this map, or null if it is by
   * natural order.
   *
   * @return the map's comparator
   */
    public fabric.util.Comparator comparator();
    
    /**
   * Returns true if the map contains a mapping for the given key.
   *
   * @param key the key to look for
   * @return true if the key has a mapping
   * @throws ClassCastException if key is not comparable to map elements
   * @throws NullPointerException if key is null and the comparator is not
   *         tolerant of nulls
   */
    public boolean containsKey(fabric.lang.Object key);
    
    /**
   * Returns true if the map contains at least one mapping to the given value.
   * This requires linear time.
   *
   * @param value the value to look for
   * @return true if the value appears in a mapping
   */
    public boolean containsValue(fabric.lang.Object value);
    
    /**
   * Returns a "set view" of this TreeMap's entries. The set is backed by
   * the TreeMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.<p>
   *
   * Note that the iterators for all three views, from keySet(), entrySet(),
   * and values(), traverse the TreeMap in sorted sequence.
   *
   * @return a set view of the entries
   * @see #keySet()
   * @see #values()
   * @see Map.Entry
   */
    public fabric.util.Set entrySet();
    
    /**
   * Returns the first (lowest) key in the map.
   *
   * @return the first key
   * @throws NoSuchElementException if the map is empty
   */
    public fabric.lang.Object firstKey();
    
    /**
   * Return the value in this TreeMap associated with the supplied key,
   * or <code>null</code> if the key maps to nothing.  NOTE: Since the value
   * could also be null, you must use containsKey to see if this key
   * actually maps to something.
   *
   * @param key the key for which to fetch an associated value
   * @return what the key maps to, if present
   * @throws ClassCastException if key is not comparable to elements in the map
   * @throws NullPointerException if key is null but the comparator does not
   *         tolerate nulls
   * @see #put(Object, Object)
   * @see #containsKey(Object)
   */
    public fabric.lang.Object get(fabric.lang.Object key);
    
    /**
   * Returns a view of this Map including all entries with keys less than
   * <code>toKey</code>. The returned map is backed by the original, so changes
   * in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoff. The returned map does not include
   * the endpoint; if you want inclusion, pass the successor element.
   *
   * @param toKey the (exclusive) cutoff point
   * @return a view of the map less than the cutoff
   * @throws ClassCastException if <code>toKey</code> is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if toKey is null, but the comparator does not
   *         tolerate null elements
   */
    public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
    
    /**
   * Returns a "set view" of this TreeMap's keys. The set is backed by the
   * TreeMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.
   *
   * @return a set view of the keys
   * @see #values()
   * @see #entrySet()
   */
    public fabric.util.Set keySet();
    
    /**
   * Returns the last (highest) key in the map.
   *
   * @return the last key
   * @throws NoSuchElementException if the map is empty
   */
    public fabric.lang.Object lastKey();
    
    /**
   * Puts the supplied value into the Map, mapped by the supplied key.
   * The value may be retrieved by any object which <code>equals()</code>
   * this key. NOTE: Since the prior value could also be null, you must
   * first use containsKey if you want to see if you are replacing the
   * key's mapping.
   *
   * @param key the key used to locate the value
   * @param value the value to be stored in the Map
   * @return the prior mapping of the key, or null if there was none
   * @throws ClassCastException if key is not comparable to current map keys
   * @throws NullPointerException if key is null, but the comparator does
   *         not tolerate nulls
   * @see #get(Object)
   * @see Object#equals(Object)
   */
    public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
    
    /**
   * Copies all elements of the given map into this TreeMap.  If this map
   * already has a mapping for a key, the new mapping replaces the current
   * one.
   *
   * @param m the map to be added
   * @throws ClassCastException if a key in m is not comparable with keys
   *         in the map
   * @throws NullPointerException if a key in m is null, and the comparator
   *         does not tolerate nulls
   */
    public void putAll(fabric.util.Map m);
    
    /**
   * Removes from the TreeMap and returns the value which is mapped by the
   * supplied key. If the key maps to nothing, then the TreeMap remains
   * unchanged, and <code>null</code> is returned. NOTE: Since the value
   * could also be null, you must use containsKey to see if you are
   * actually removing a mapping.
   *
   * @param key the key used to locate the value to remove
   * @return whatever the key mapped to, if present
   * @throws ClassCastException if key is not comparable to current map keys
   * @throws NullPointerException if key is null, but the comparator does
   *         not tolerate nulls
   */
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    /**
   * Returns the number of key-value mappings currently in this Map.
   *
   * @return the size
   */
    public int size();
    
    /**
   * Returns a view of this Map including all entries with keys greater or
   * equal to <code>fromKey</code> and less than <code>toKey</code> (a
   * half-open interval). The returned map is backed by the original, so
   * changes in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoffs. The returned map includes the low
   * endpoint but not the high; if you want to reverse this behavior on
   * either end, pass in the successor element.
   *
   * @param fromKey the (inclusive) low cutoff point
   * @param toKey the (exclusive) high cutoff point
   * @return a view of the map between the cutoffs
   * @throws ClassCastException if either cutoff is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if fromKey or toKey is null, but the
   *         comparator does not tolerate null elements
   * @throws IllegalArgumentException if fromKey is greater than toKey
   */
    public fabric.util.SortedMap subMap(fabric.lang.Object fromKey, fabric.lang.Object toKey);
    
    /**
   * Returns a view of this Map including all entries with keys greater or
   * equal to <code>fromKey</code>. The returned map is backed by the
   * original, so changes in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoff. The returned map includes the
   * endpoint; if you want to exclude it, pass in the successor element.
   *
   * @param fromKey the (inclusive) low cutoff point
   * @return a view of the map above the cutoff
   * @throws ClassCastException if <code>fromKey</code> is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if fromKey is null, but the comparator
   *         does not tolerate null elements
   */
    public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
    
    /**
   * Returns a "collection view" (or "bag view") of this TreeMap's values.
   * The collection is backed by the TreeMap, so changes in one show up
   * in the other.  The collection supports element removal, but not element
   * addition.
   *
   * @return a bag view of the values
   * @see #keySet()
   * @see #entrySet()
   */
    public fabric.util.Collection values();
    
    /**
   * Compares two elements by the set comparator, or by natural ordering.
   * Package visible for use by nested classes.
   *
   * @param o1 the first object
   * @param o2 the second object
   * @throws ClassCastException if o1 and o2 are not mutually comparable,
   *         or are not Comparable with natural ordering
   * @throws NullPointerException if o1 or o2 is null with natural ordering
   */
    public int compare(fabric.lang.Object o1, fabric.lang.Object o2);
    
    /**
   * Returns the first sorted node in the map, or nil if empty. Package
   * visible for use by nested classes.
   *
   * @return the first node
   */
    public Node firstNode();
    
    /**
   * Return the TreeMap.Node associated with key, or the nil node if no such
   * node exists in the tree. Package visible for use by nested classes.
   *
   * @param key the key to search for
   * @return the node where the key is found, or nil
   */
    public Node getNode(fabric.lang.Object key);
    
    /**
   * Find the "highest" node which is &lt; key. If key is nil, return last
   * node. Package visible for use by nested classes.
   *
   * @param key the upper bound, exclusive
   * @return the previous node
   */
    public Node highestLessThan(fabric.lang.Object key);
    
    /**
   * Find the "lowest" node which is &gt;= key. If key is nil, return either
   * nil or the first node, depending on the parameter first.
   * Package visible for use by nested classes.
   *
   * @param key the lower bound, inclusive
   * @param first true to return the first element instead of nil for nil key
   * @return the next node
   */
    public Node lowestGreaterThan(fabric.lang.Object key, boolean first);
    
    /**
   * Construct a tree from sorted keys in linear time, with values of "".
   * Package visible for use by TreeSet.
   *
   * @param keys the iterator over the sorted keys
   * @param count the number of nodes to insert
   * @see TreeSet#TreeSet(SortedSet)
   */
    public void putKeysLinear(fabric.util.Iterator keys, int count);
    
    /**
   * Remove node from tree. This will increment modCount and decrement size.
   * Node must exist in the tree. Package visible for use by nested classes.
   *
   * @param node the node to remove
   */
    public void removeNode(Node node);
    
    /**
   * Return the node following the given one, or nil if there isn't one.
   * Package visible for use by nested classes.
   *
   * @param node the current node, not nil
   * @return the next node in sorted order
   */
    public Node successor(Node node);
    
    /**
   * Iterate over TreeMap's entries. This implementation is parameterized
   * to give a sequential view of keys, values, or entries.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface TreeIterator
      extends fabric.util.Iterator, fabric.lang.Object {
        public fabric.util.TreeMap get$out$();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public fabric.util.TreeMap.Node get$last();
        
        public fabric.util.TreeMap.Node set$last(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$next();
        
        public fabric.util.TreeMap.Node set$next(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$max();
        
        public fabric.util.TreeMap.Node set$max(fabric.util.TreeMap.Node val);
        
        /**
     * Construct a new TreeIterator with the supplied type.
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     */
        public TreeIterator fabric$util$TreeMap$TreeIterator$(int type);
        
        /**
     * Construct a new TreeIterator with the supplied type. Iteration will
     * be from "first" (inclusive) to "max" (exclusive).
     *
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     * @param first where to start iteration, nil for empty iterator
     * @param max the cutoff for iteration, nil for all remaining nodes
     */
        public TreeIterator fabric$util$TreeMap$TreeIterator$(int type,
                                                              Node first, Node max);
        
        /**
     * Returns true if the Iterator has more elements.
     * @return true if there are more elements
     */
        public boolean hasNext();
        
        /**
     * Returns the next element in the Iterator's sequential view.
     * @return the next element
     * @throws ConcurrentModificationException if the TreeMap was modified
     * @throws NoSuchElementException if there is none
     */
        public fabric.lang.Object next();
        
        /**
     * Removes from the backing TreeMap the last element which was fetched
     * with the <code>next()</code> method.
     * @throws ConcurrentModificationException if the TreeMap was modified
     * @throws IllegalStateException if called when there is no last element
     */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements TreeIterator {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$out$();
            }
            
            public int get$type() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$type();
            }
            
            public int set$type(int val) {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  set$type(val);
            }
            
            public int postInc$type() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  postInc$type();
            }
            
            public int postDec$type() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  postDec$type();
            }
            
            public int get$knownMod() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  postDec$knownMod();
            }
            
            public fabric.util.TreeMap.Node get$last() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$last();
            }
            
            public fabric.util.TreeMap.Node set$last(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  set$last(val);
            }
            
            public fabric.util.TreeMap.Node get$next() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$next();
            }
            
            public fabric.util.TreeMap.Node set$next(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  set$next(val);
            }
            
            public fabric.util.TreeMap.Node get$max() {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  get$max();
            }
            
            public fabric.util.TreeMap.Node set$max(
              fabric.util.TreeMap.Node val) {
                return ((fabric.util.TreeMap.TreeIterator._Impl) fetch()).
                  set$max(val);
            }
            
            public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1) {
                return ((fabric.util.TreeMap.TreeIterator) fetch()).
                  fabric$util$TreeMap$TreeIterator$(arg1);
            }
            
            public fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1,
                                                fabric.util.TreeMap.Node arg2,
                                                fabric.util.TreeMap.Node arg3) {
                return ((fabric.util.TreeMap.TreeIterator) fetch()).
                  fabric$util$TreeMap$TreeIterator$(arg1, arg2, arg3);
            }
            
            public boolean hasNext() {
                return ((fabric.util.TreeMap.TreeIterator) fetch()).hasNext();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.TreeMap.TreeIterator) fetch()).next();
            }
            
            public void remove() {
                ((fabric.util.TreeMap.TreeIterator) fetch()).remove();
            }
            
            public _Proxy(TreeIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements TreeIterator {
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public int get$type() { return this.type; }
            
            public int set$type(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.type = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$type() {
                int tmp = this.get$type();
                this.set$type((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$type() {
                int tmp = this.get$type();
                this.set$type((int) (tmp - 1));
                return tmp;
            }
            
            private int type;
            
            public int get$knownMod() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.knownMod;
            }
            
            public int set$knownMod(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.knownMod = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$knownMod() {
                int tmp = this.get$knownMod();
                this.set$knownMod((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$knownMod() {
                int tmp = this.get$knownMod();
                this.set$knownMod((int) (tmp - 1));
                return tmp;
            }
            
            /** The number of modifications to the backing Map that we know about. */
            private int knownMod;
            
            public fabric.util.TreeMap.Node get$last() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.last;
            }
            
            public fabric.util.TreeMap.Node set$last(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.last = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The last Entry returned by a next() call. */
            private Node last;
            
            public fabric.util.TreeMap.Node get$next() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.next;
            }
            
            public fabric.util.TreeMap.Node set$next(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.next = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The next entry that should be returned by next(). */
            private Node next;
            
            public fabric.util.TreeMap.Node get$max() { return this.max; }
            
            public fabric.util.TreeMap.Node set$max(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.max = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private Node max;
            
            /**
     * Construct a new TreeIterator with the supplied type.
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     */
            public TreeIterator fabric$util$TreeMap$TreeIterator$(int type) {
                this.set$type((int) type);
                this.set$max(this.get$out$().get$nil());
                fabric$lang$Object$();
                this.set$next(this.get$out$().firstNode());
                this.set$knownMod((int) this.get$out$().get$modCount());
                return (TreeIterator) this.$getProxy();
            }
            
            /**
     * Construct a new TreeIterator with the supplied type. Iteration will
     * be from "first" (inclusive) to "max" (exclusive).
     *
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     * @param first where to start iteration, nil for empty iterator
     * @param max the cutoff for iteration, nil for all remaining nodes
     */
            public TreeIterator fabric$util$TreeMap$TreeIterator$(int type,
                                                                  Node first,
                                                                  Node max) {
                this.set$type((int) type);
                this.set$max(max);
                fabric$lang$Object$();
                this.set$next(first);
                this.set$knownMod((int) this.get$out$().get$modCount());
                return (TreeIterator) this.$getProxy();
            }
            
            /**
     * Returns true if the Iterator has more elements.
     * @return true if there are more elements
     */
            public boolean hasNext() {
                return !fabric.lang.Object._Proxy.idEquals(this.get$next(),
                                                           this.get$max());
            }
            
            /**
     * Returns the next element in the Iterator's sequential view.
     * @return the next element
     * @throws ConcurrentModificationException if the TreeMap was modified
     * @throws NoSuchElementException if there is none
     */
            public fabric.lang.Object next() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
                if (fabric.lang.Object._Proxy.idEquals(this.get$next(),
                                                       this.get$max()))
                    throw new fabric.util.NoSuchElementException();
                this.set$last(this.get$next());
                this.set$next(this.get$out$().successor(this.get$last()));
                if (this.get$type() ==
                      fabric.util.AbstractMap._Static._Proxy.$instance.
                      get$VALUES())
                    return this.get$last().get$value();
                else if (this.get$type() ==
                           fabric.util.AbstractMap._Static._Proxy.$instance.
                           get$KEYS())
                    return this.get$last().get$key();
                return this.get$last();
            }
            
            /**
     * Removes from the backing TreeMap the last element which was fetched
     * with the <code>next()</code> method.
     * @throws ConcurrentModificationException if the TreeMap was modified
     * @throws IllegalStateException if called when there is no last element
     */
            public void remove() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$last(), null))
                    throw new java.lang.IllegalStateException();
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
                this.get$out$().removeNode(this.get$last());
                this.set$last(null);
                this.postInc$knownMod();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (TreeIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.TreeIterator._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                out.writeInt(this.type);
                out.writeInt(this.knownMod);
                $writeRef($getStore(), this.last, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.next, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.max, refTypes, out, intraStoreRefs,
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.type = in.readInt();
                this.knownMod = in.readInt();
                this.last = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.next = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.max = (fabric.util.TreeMap.Node)
                             $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.TreeIterator._Impl src =
                  (fabric.util.TreeMap.TreeIterator._Impl) other;
                this.out$ = src.out$;
                this.type = src.type;
                this.knownMod = src.knownMod;
                this.last = src.last;
                this.next = src.next;
                this.max = src.max;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.TreeIterator._Static {
                public _Proxy(fabric.util.TreeMap.TreeIterator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.TreeIterator._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      TreeIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.TreeIterator._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.TreeIterator._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.TreeIterator._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.TreeIterator._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.TreeIterator._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 36, -28, -64, -34,
        9, 84, -94, 54, -127, 27, -91, 101, -91, -55, -96, 29, 101, -25, 61,
        -110, -106, -111, -67, -59, 59, 5, -76, 44, 85, -53, -69, -43 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * Implementation of {@link #subMap(Object, Object)} and other map
   * ranges. This class provides a view of a portion of the original backing
   * map, and throws {@link IllegalArgumentException} for attempts to
   * access beyond that range.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SubMap
      extends fabric.util.SortedMap, fabric.util.AbstractMap {
        public fabric.util.TreeMap get$out$();
        
        public fabric.lang.Object get$minKey();
        
        public fabric.lang.Object set$minKey(fabric.lang.Object val);
        
        public fabric.lang.Object get$maxKey();
        
        public fabric.lang.Object set$maxKey(fabric.lang.Object val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        /**
     * Create a SubMap representing the elements between minKey (inclusive)
     * and maxKey (exclusive). If minKey is nil, SubMap has no lower bound
     * (headMap). If maxKey is nil, the SubMap has no upper bound (tailMap).
     *
     * @param minKey the lower bound
     * @param maxKey the upper bound
     * @throws IllegalArgumentException if minKey &gt; maxKey
     */
        public SubMap
          fabric$util$TreeMap$SubMap$(fabric.lang.Object minKey, fabric.lang.Object maxKey);
        
        /**
     * Check if "key" is in within the range bounds for this SubMap. The
     * lower ("from") SubMap range is inclusive, and the upper ("to") bound
     * is exclusive. Package visible for use by nested classes.
     *
     * @param key the key to check
     * @return true if the key is in range
     */
        public boolean keyInRange(fabric.lang.Object key);
        
        public void clear();
        
        public fabric.util.Comparator comparator();
        
        public boolean containsKey(fabric.lang.Object key);
        
        public boolean containsValue(fabric.lang.Object value);
        
        public fabric.util.Set entrySet();
        
        public fabric.lang.Object firstKey();
        
        public fabric.lang.Object get(fabric.lang.Object key);
        
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        public fabric.util.Set keySet();
        
        public fabric.lang.Object lastKey();
        
        public fabric.lang.Object put(fabric.lang.Object key,
                                      fabric.lang.Object value);
        
        public fabric.lang.Object remove(fabric.lang.Object key);
        
        public int size();
        
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                            fabric.lang.Object toKey);
        
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        public fabric.util.Collection values();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$11 extends fabric.util.AbstractSet {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements Anonymous$11 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$11._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$11._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements Anonymous$11 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return this.out$;
                }
                
                private SubMap out$;
                
                public int size() { return this.get$out$().size(); }
                
                public fabric.util.Iterator iterator(
                  fabric.worker.Store store) {
                    Node first =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$minKey(),
                                                   true);
                    Node max =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$maxKey(),
                                                   false);
                    return (TreeIterator)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.util.TreeMap.TreeIterator)
                                  new fabric.util.TreeMap.TreeIterator._Impl(
                                    store, this.get$out$().get$out$()).
                                  $getProxy()).
                                   fabric$util$TreeMap$TreeIterator$(
                                     fabric.util.AbstractMap._Static._Proxy.$instance.
                                         get$ENTRIES(),
                                     first,
                                     max));
                }
                
                public void clear() { this.get$out$().clear(); }
                
                public boolean contains(fabric.lang.Object o) {
                    if (!(fabric.lang.Object._Proxy.
                            $getProxy(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(o)) instanceof Entry)) return false;
                    Entry me = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
                    fabric.lang.Object key = me.getKey();
                    if (!this.get$out$().keyInRange(key)) return false;
                    Node n = this.get$out$().get$out$().getNode(key);
                    return !fabric.lang.Object._Proxy.idEquals(
                                                        n,
                                                        this.get$out$(
                                                               ).get$out$(
                                                                   ).get$nil(
                                                                       )) &&
                      fabric.util.AbstractSet._Impl.equals(me.getValue(),
                                                           n.get$value());
                }
                
                public boolean remove(fabric.lang.Object o) {
                    if (!(fabric.lang.Object._Proxy.
                            $getProxy(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(o)) instanceof Entry)) return false;
                    Entry me = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
                    fabric.lang.Object key = me.getKey();
                    if (!this.get$out$().keyInRange(key)) return false;
                    Node n = this.get$out$().get$out$().getNode(key);
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     n,
                                                     this.get$out$(
                                                            ).get$out$(
                                                                ).get$nil()) &&
                          fabric.util.AbstractSet._Impl.equals(me.getValue(),
                                                               n.get$value())) {
                        this.get$out$().get$out$().removeNode(n);
                        return true;
                    }
                    return false;
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (Anonymous$11) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$11._Proxy(
                             this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                    this.out$ =
                      (fabric.util.TreeMap.SubMap)
                        $readRef(fabric.util.TreeMap.SubMap._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.TreeMap.SubMap.Anonymous$11._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$11._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$11._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$11.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$11.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$11.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$11.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$11._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$11._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$11._Static {
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store,
                                 long onum,
                                 int version,
                                 fabric.worker.metrics.
                                   ImmutableObjectSet associates, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.
                      io.
                      IOException,
                        java.
                      lang.
                      ClassNotFoundException {
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.TreeMap.SubMap.Anonymous$11.
                                 _Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 69, 43, 88,
            -19, 100, -87, -46, -107, 65, 43, -28, -26, -25, 103, 42, 56, -45,
            -56, -2, -77, 87, -66, 64, 6, -126, -39, 47, -27, -56, 110, -22,
            51 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public static interface Anonymous$12 extends fabric.util.AbstractSet {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements Anonymous$12 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$12._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$12._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements Anonymous$12 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return this.out$;
                }
                
                private SubMap out$;
                
                public int size() { return this.get$out$().size(); }
                
                public fabric.util.Iterator iterator(
                  fabric.worker.Store store) {
                    Node first =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$minKey(),
                                                   true);
                    Node max =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$maxKey(),
                                                   false);
                    return (TreeIterator)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.util.TreeMap.TreeIterator)
                                  new fabric.util.TreeMap.TreeIterator._Impl(
                                    store, this.get$out$().get$out$()).
                                  $getProxy()).
                                   fabric$util$TreeMap$TreeIterator$(
                                     fabric.util.AbstractMap._Static._Proxy.$instance.
                                         get$KEYS(),
                                     first,
                                     max));
                }
                
                public void clear() { this.get$out$().clear(); }
                
                public boolean contains(fabric.lang.Object o) {
                    if (!this.get$out$().keyInRange(o)) return false;
                    return !fabric.lang.Object._Proxy.idEquals(
                                                        this.get$out$(
                                                               ).get$out$(
                                                                   ).getNode(o),
                                                        this.get$out$(
                                                               ).get$out$(
                                                                   ).get$nil());
                }
                
                public boolean remove(fabric.lang.Object o) {
                    if (!this.get$out$().keyInRange(o)) return false;
                    Node n = this.get$out$().get$out$().getNode(o);
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     n,
                                                     this.get$out$(
                                                            ).get$out$(
                                                                ).get$nil())) {
                        this.get$out$().get$out$().removeNode(n);
                        return true;
                    }
                    return false;
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (Anonymous$12) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$12._Proxy(
                             this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                    this.out$ =
                      (fabric.util.TreeMap.SubMap)
                        $readRef(fabric.util.TreeMap.SubMap._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.TreeMap.SubMap.Anonymous$12._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$12._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$12._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$12.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$12.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$12.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$12.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$12._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$12._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$12._Static {
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store,
                                 long onum,
                                 int version,
                                 fabric.worker.metrics.
                                   ImmutableObjectSet associates, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.
                      io.
                      IOException,
                        java.
                      lang.
                      ClassNotFoundException {
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.TreeMap.SubMap.Anonymous$12.
                                 _Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 69, 43, 88,
            -19, 100, -87, -46, -107, 65, 43, -28, -26, -25, 103, 42, 56, -45,
            -56, -2, -77, 87, -66, 64, 6, -126, -39, 47, -27, -56, 110, -22,
            51 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public static interface Anonymous$13
          extends fabric.util.AbstractCollection {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy
            extends fabric.util.AbstractCollection._Proxy
              implements Anonymous$13 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$13._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$13._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl
            extends fabric.util.AbstractCollection._Impl implements Anonymous$13
            {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return this.out$;
                }
                
                private SubMap out$;
                
                public int size() { return this.get$out$().size(); }
                
                public fabric.util.Iterator iterator(
                  fabric.worker.Store store) {
                    Node first =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$minKey(),
                                                   true);
                    Node max =
                      this.get$out$().get$out$().lowestGreaterThan(
                                                   this.get$out$().get$maxKey(),
                                                   false);
                    return (TreeIterator)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.util.TreeMap.TreeIterator)
                                  new fabric.util.TreeMap.TreeIterator._Impl(
                                    store, this.get$out$().get$out$()).
                                  $getProxy()).
                                   fabric$util$TreeMap$TreeIterator$(
                                     fabric.util.AbstractMap._Static._Proxy.$instance.
                                         get$VALUES(),
                                     first,
                                     max));
                }
                
                public void clear() { this.get$out$().clear(); }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (Anonymous$13) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$13._Proxy(
                             this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                    this.out$ =
                      (fabric.util.TreeMap.SubMap)
                        $readRef(fabric.util.TreeMap.SubMap._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.TreeMap.SubMap.Anonymous$13._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$13._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$13._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$13.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$13.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$13.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$13.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$13._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$13._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$13._Static {
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store,
                                 long onum,
                                 int version,
                                 fabric.worker.metrics.
                                   ImmutableObjectSet associates, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.
                      io.
                      IOException,
                        java.
                      lang.
                      ClassNotFoundException {
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.TreeMap.SubMap.Anonymous$13.
                                 _Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { -64, -88, 97,
            -119, -1, 78, 5, 71, 6, -83, -80, 117, -76, -65, -111, 111, -121,
            46, -57, -50, -52, 87, -35, -103, 72, -29, 3, 42, 107, 17, 50, 77 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements SubMap {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).get$out$();
            }
            
            public fabric.lang.Object get$minKey() {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).get$minKey(
                                                                      );
            }
            
            public fabric.lang.Object set$minKey(fabric.lang.Object val) {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).set$minKey(
                                                                      val);
            }
            
            public fabric.lang.Object get$maxKey() {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).get$maxKey(
                                                                      );
            }
            
            public fabric.lang.Object set$maxKey(fabric.lang.Object val) {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).set$maxKey(
                                                                      val);
            }
            
            public fabric.util.Set get$entries() {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).get$entries(
                                                                      );
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                return ((fabric.util.TreeMap.SubMap._Impl) fetch()).set$entries(
                                                                      val);
            }
            
            public fabric.util.TreeMap.SubMap fabric$util$TreeMap$SubMap$(
              fabric.lang.Object arg1, fabric.lang.Object arg2) {
                return ((fabric.util.TreeMap.SubMap) fetch()).
                  fabric$util$TreeMap$SubMap$(arg1, arg2);
            }
            
            public boolean keyInRange(fabric.lang.Object arg1) {
                return ((fabric.util.TreeMap.SubMap) fetch()).keyInRange(arg1);
            }
            
            public fabric.util.Comparator comparator() {
                return ((fabric.util.TreeMap.SubMap) fetch()).comparator();
            }
            
            public fabric.lang.Object firstKey() {
                return ((fabric.util.TreeMap.SubMap) fetch()).firstKey();
            }
            
            public fabric.util.SortedMap headMap(fabric.lang.Object arg1) {
                return ((fabric.util.TreeMap.SubMap) fetch()).headMap(arg1);
            }
            
            public fabric.lang.Object lastKey() {
                return ((fabric.util.TreeMap.SubMap) fetch()).lastKey();
            }
            
            public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                fabric.lang.Object arg2) {
                return ((fabric.util.TreeMap.SubMap) fetch()).subMap(arg1,
                                                                     arg2);
            }
            
            public fabric.util.SortedMap tailMap(fabric.lang.Object arg1) {
                return ((fabric.util.TreeMap.SubMap) fetch()).tailMap(arg1);
            }
            
            public _Proxy(SubMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements SubMap {
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public fabric.lang.Object get$minKey() { return this.minKey; }
            
            public fabric.lang.Object set$minKey(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.minKey = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.lang.Object minKey;
            
            public fabric.lang.Object get$maxKey() { return this.maxKey; }
            
            public fabric.lang.Object set$maxKey(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.maxKey = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.lang.Object maxKey;
            
            public fabric.util.Set get$entries() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.entries;
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.entries = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * The cache for {@link #entrySet()}.
     */
            private fabric.util.Set entries;
            
            /**
     * Create a SubMap representing the elements between minKey (inclusive)
     * and maxKey (exclusive). If minKey is nil, SubMap has no lower bound
     * (headMap). If maxKey is nil, the SubMap has no upper bound (tailMap).
     *
     * @param minKey the lower bound
     * @param maxKey the upper bound
     * @throws IllegalArgumentException if minKey &gt; maxKey
     */
            public SubMap fabric$util$TreeMap$SubMap$(
              fabric.lang.Object minKey, fabric.lang.Object maxKey) {
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 minKey,
                                                 this.get$out$().get$nil()) &&
                      !fabric.lang.Object._Proxy.idEquals(
                                                   maxKey,
                                                   this.get$out$().get$nil()) &&
                      this.get$out$().compare(minKey, maxKey) >
                      0)
                    throw new java.lang.IllegalArgumentException(
                            "fromKey > toKey");
                this.set$minKey(minKey);
                this.set$maxKey(maxKey);
                fabric$lang$Object$();
                return (SubMap) this.$getProxy();
            }
            
            /**
     * Check if "key" is in within the range bounds for this SubMap. The
     * lower ("from") SubMap range is inclusive, and the upper ("to") bound
     * is exclusive. Package visible for use by nested classes.
     *
     * @param key the key to check
     * @return true if the key is in range
     */
            public boolean keyInRange(fabric.lang.Object key) {
                return (fabric.lang.Object._Proxy.idEquals(
                                                    this.get$minKey(),
                                                    this.get$out$().get$nil(
                                                                      )) ||
                          this.get$out$().compare(key, this.get$minKey()) >=
                          0) &&
                  (fabric.lang.Object._Proxy.idEquals(
                                               this.get$maxKey(),
                                               this.get$out$().get$nil()) ||
                     this.get$out$().compare(key, this.get$maxKey()) <
                     0);
            }
            
            public void clear() {
                Node next = this.get$out$().lowestGreaterThan(this.get$minKey(),
                                                              true);
                Node max = this.get$out$().lowestGreaterThan(this.get$maxKey(),
                                                             false);
                while (!fabric.lang.Object._Proxy.idEquals(next, max)) {
                    Node current = next;
                    next = this.get$out$().successor(current);
                    this.get$out$().removeNode(current);
                }
            }
            
            public fabric.util.Comparator comparator() {
                return this.get$out$().get$comparator();
            }
            
            public boolean containsKey(fabric.lang.Object key) {
                return keyInRange(key) && this.get$out$().containsKey(key);
            }
            
            public boolean containsValue(fabric.lang.Object value) {
                Node node = this.get$out$().lowestGreaterThan(this.get$minKey(),
                                                              true);
                Node max = this.get$out$().lowestGreaterThan(this.get$maxKey(),
                                                             false);
                while (!fabric.lang.Object._Proxy.idEquals(node, max)) {
                    if (fabric.util.AbstractMap._Impl.equals(value,
                                                             node.getValue()))
                        return true;
                    node = this.get$out$().successor(node);
                }
                return false;
            }
            
            public fabric.util.Set entrySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$entries(),
                                                       null))
                    this.
                      set$entries(
                        (fabric.util.AbstractSet)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.TreeMap.SubMap.Anonymous$11)
                               new fabric.util.TreeMap.SubMap.Anonymous$11.
                                 _Impl(this.$getStore(),
                                       (fabric.util.TreeMap.SubMap)
                                         this.$getProxy()).
                               $getProxy()).fabric$util$AbstractSet$()));
                return this.get$entries();
            }
            
            public fabric.lang.Object firstKey() {
                Node node = this.get$out$().lowestGreaterThan(this.get$minKey(),
                                                              true);
                if (fabric.lang.Object._Proxy.idEquals(
                                                node,
                                                this.get$out$().get$nil()) ||
                      !keyInRange(node.get$key()))
                    throw new fabric.util.NoSuchElementException();
                return node.get$key();
            }
            
            public fabric.lang.Object get(fabric.lang.Object key) {
                if (keyInRange(key)) return this.get$out$().get(key);
                return null;
            }
            
            public fabric.util.SortedMap headMap(fabric.lang.Object toKey) {
                if (!keyInRange(toKey))
                    throw new java.lang.IllegalArgumentException(
                            "key outside range");
                return (SubMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.SubMap)
                              new fabric.util.TreeMap.SubMap._Impl(
                                this.$getStore(), this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$SubMap$(this.get$minKey(),
                                                           toKey));
            }
            
            public fabric.util.Set keySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$keys(), null))
                    this.
                      set$keys(
                        (fabric.util.AbstractSet)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.TreeMap.SubMap.Anonymous$12)
                               new fabric.util.TreeMap.SubMap.Anonymous$12.
                                 _Impl(this.$getStore(),
                                       (fabric.util.TreeMap.SubMap)
                                         this.$getProxy()).
                               $getProxy()).fabric$util$AbstractSet$()));
                return this.get$keys();
            }
            
            public fabric.lang.Object lastKey() {
                Node node = this.get$out$().highestLessThan(this.get$maxKey());
                if (fabric.lang.Object._Proxy.idEquals(
                                                node,
                                                this.get$out$().get$nil()) ||
                      !keyInRange(node.get$key()))
                    throw new fabric.util.NoSuchElementException();
                return node.get$key();
            }
            
            public fabric.lang.Object put(fabric.lang.Object key,
                                          fabric.lang.Object value) {
                if (!keyInRange(key))
                    throw new java.lang.IllegalArgumentException(
                            "Key outside range");
                return this.get$out$().put(key, value);
            }
            
            public fabric.lang.Object remove(fabric.lang.Object key) {
                if (keyInRange(key)) return this.get$out$().remove(key);
                return null;
            }
            
            public int size() {
                Node node = this.get$out$().lowestGreaterThan(this.get$minKey(),
                                                              true);
                Node max = this.get$out$().lowestGreaterThan(this.get$maxKey(),
                                                             false);
                int count = 0;
                while (!fabric.lang.Object._Proxy.idEquals(node, max)) {
                    count++;
                    node = this.get$out$().successor(node);
                }
                return count;
            }
            
            public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                                fabric.lang.Object toKey) {
                if (!keyInRange(fromKey) || !keyInRange(toKey))
                    throw new java.lang.IllegalArgumentException(
                            "key outside range");
                return (SubMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.SubMap)
                              new fabric.util.TreeMap.SubMap._Impl(
                                this.$getStore(), this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$SubMap$(fromKey, toKey));
            }
            
            public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey) {
                if (!keyInRange(fromKey))
                    throw new java.lang.IllegalArgumentException(
                            "key outside range");
                return (SubMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.SubMap)
                              new fabric.util.TreeMap.SubMap._Impl(
                                this.$getStore(), this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$SubMap$(fromKey,
                                                           this.get$maxKey()));
            }
            
            public fabric.util.Collection values() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$values(), null))
                    this.
                      set$values(
                        (fabric.util.AbstractCollection)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.TreeMap.SubMap.Anonymous$13)
                               new fabric.util.TreeMap.SubMap.Anonymous$13.
                                 _Impl(this.$getStore(),
                                       (fabric.util.TreeMap.SubMap)
                                         this.$getProxy()).
                               $getProxy()).fabric$util$AbstractCollection$()));
                return this.get$values();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (SubMap) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.SubMap._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.minKey, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.maxKey, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.entries, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.minKey = (fabric.lang.Object)
                                $readRef(fabric.lang.Object._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(), in, store,
                                         intraStoreRefs, interStoreRefs);
                this.maxKey = (fabric.lang.Object)
                                $readRef(fabric.lang.Object._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(), in, store,
                                         intraStoreRefs, interStoreRefs);
                this.entries = (fabric.util.Set)
                                 $readRef(fabric.util.Set._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.SubMap._Impl src =
                  (fabric.util.TreeMap.SubMap._Impl) other;
                this.out$ = src.out$;
                this.minKey = src.minKey;
                this.maxKey = src.maxKey;
                this.entries = src.entries;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.SubMap._Static {
                public _Proxy(fabric.util.TreeMap.SubMap._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.SubMap._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      SubMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.SubMap._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.SubMap._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.SubMap._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.SubMap._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 69, -117, -38,
        -107, 127, 14, 102, 119, 62, 76, 31, -119, -68, 22, -55, -100, -100,
        -103, 101, 55, -7, -28, -19, -39, -115, 99, -65, -17, -121, -59, 6,
        -123 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static interface Anonymous$8 extends fabric.util.AbstractSet {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$8 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$8._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$8._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$8 {
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return (TreeIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.TreeIterator)
                              new fabric.util.TreeMap.TreeIterator._Impl(
                                store, this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$TreeIterator$(
                                 fabric.util.AbstractMap._Static._Proxy.$instance.
                                     get$ENTRIES()));
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public boolean contains(fabric.lang.Object o) {
                if (!(fabric.lang.Object._Proxy.
                        $getProxy(
                          (java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.
                            $unwrap(o)) instanceof Entry)) return false;
                Entry me = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
                Node n = this.get$out$().getNode(me.getKey());
                return !fabric.lang.Object._Proxy.idEquals(
                                                    n,
                                                    this.get$out$().get$nil(
                                                                      )) &&
                  fabric.util.AbstractSet._Impl.equals(me.getValue(),
                                                       n.get$value());
            }
            
            public boolean remove(fabric.lang.Object o) {
                if (!(fabric.lang.Object._Proxy.
                        $getProxy(
                          (java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.
                            $unwrap(o)) instanceof Entry)) return false;
                Entry me = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
                Node n = this.get$out$().getNode(me.getKey());
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 n,
                                                 this.get$out$().get$nil()) &&
                      fabric.util.AbstractSet._Impl.equals(me.getValue(),
                                                           n.get$value())) {
                    this.get$out$().removeNode(n);
                    return true;
                }
                return false;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$8) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$8._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.Anonymous$8._Impl src =
                  (fabric.util.TreeMap.Anonymous$8._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$8._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$8._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$8._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$8.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$8._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$8._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$8._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$8._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.Anonymous$8._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 69, 43, 88, -19,
        100, -87, -46, -107, 65, 43, -28, -26, -25, 103, 42, 56, -45, -56, -2,
        -77, 87, -66, 64, 6, -126, -39, 47, -27, -56, 110, -22, 51 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$9 extends fabric.util.AbstractSet {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object key);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$9 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$9._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$9._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$9 {
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return (TreeIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.TreeIterator)
                              new fabric.util.TreeMap.TreeIterator._Impl(
                                store, this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$TreeIterator$(
                                 fabric.util.AbstractMap._Static._Proxy.$instance.
                                     get$KEYS()));
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public boolean contains(fabric.lang.Object o) {
                return this.get$out$().containsKey(o);
            }
            
            public boolean remove(fabric.lang.Object key) {
                Node n = this.get$out$().getNode(key);
                if (fabric.lang.Object._Proxy.idEquals(
                                                n, this.get$out$().get$nil()))
                    return false;
                this.get$out$().removeNode(n);
                return true;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$9) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$9._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.Anonymous$9._Impl src =
                  (fabric.util.TreeMap.Anonymous$9._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$9._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$9._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$9._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$9.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$9._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$9._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$9._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$9._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.Anonymous$9._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 69, 43, 88, -19,
        100, -87, -46, -107, 65, 43, -28, -26, -25, 103, 42, 56, -45, -56, -2,
        -77, 87, -66, 64, 6, -126, -39, 47, -27, -56, 110, -22, 51 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$10 extends fabric.util.AbstractCollection
    {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements Anonymous$10 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$10._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$10._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements Anonymous$10 {
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return (TreeIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.TreeMap.TreeIterator)
                              new fabric.util.TreeMap.TreeIterator._Impl(
                                store, this.get$out$()).$getProxy()).
                               fabric$util$TreeMap$TreeIterator$(
                                 fabric.util.AbstractMap._Static._Proxy.$instance.
                                     get$VALUES()));
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$10) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$10._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.TreeMap.Anonymous$10._Impl src =
                  (fabric.util.TreeMap.Anonymous$10._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$10._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$10._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$10._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$10.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$10._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$10._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$10._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$10._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.Anonymous$10._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -64, -88, 97, -119,
        -1, 78, 5, 71, 6, -83, -80, 117, -76, -65, -111, 111, -121, 46, -57,
        -50, -52, 87, -35, -103, 72, -29, 3, 42, 107, 17, 50, 77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.TreeMap {
        public fabric.util.TreeMap.Node get$nil() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$nil();
        }
        
        public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$nil(val);
        }
        
        public fabric.util.TreeMap.Node get$root() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$root();
        }
        
        public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$root(val);
        }
        
        public int get$size() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$size();
        }
        
        public int set$size(int val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$size(val);
        }
        
        public int postInc$size() {
            return ((fabric.util.TreeMap._Impl) fetch()).postInc$size();
        }
        
        public int postDec$size() {
            return ((fabric.util.TreeMap._Impl) fetch()).postDec$size();
        }
        
        public fabric.util.Set get$entries() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$entries();
        }
        
        public fabric.util.Set set$entries(fabric.util.Set val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$entries(val);
        }
        
        public int get$modCount() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$modCount();
        }
        
        public int set$modCount(int val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$modCount(val);
        }
        
        public int postInc$modCount() {
            return ((fabric.util.TreeMap._Impl) fetch()).postInc$modCount();
        }
        
        public int postDec$modCount() {
            return ((fabric.util.TreeMap._Impl) fetch()).postDec$modCount();
        }
        
        public fabric.util.Comparator get$comparator() {
            return ((fabric.util.TreeMap._Impl) fetch()).get$comparator();
        }
        
        public fabric.util.Comparator set$comparator(
          fabric.util.Comparator val) {
            return ((fabric.util.TreeMap._Impl) fetch()).set$comparator(val);
        }
        
        public fabric.util.TreeMap fabric$util$TreeMap$() {
            return ((fabric.util.TreeMap) fetch()).fabric$util$TreeMap$();
        }
        
        public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator arg1) {
            return ((fabric.util.TreeMap) fetch()).fabric$util$TreeMap$(arg1);
        }
        
        public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map arg1) {
            return ((fabric.util.TreeMap) fetch()).fabric$util$TreeMap$(arg1);
        }
        
        public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap arg1) {
            return ((fabric.util.TreeMap) fetch()).fabric$util$TreeMap$(arg1);
        }
        
        public fabric.util.Comparator comparator() {
            return ((fabric.util.TreeMap) fetch()).comparator();
        }
        
        public fabric.lang.Object firstKey() {
            return ((fabric.util.TreeMap) fetch()).firstKey();
        }
        
        public fabric.util.SortedMap headMap(fabric.lang.Object arg1) {
            return ((fabric.util.TreeMap) fetch()).headMap(arg1);
        }
        
        public fabric.lang.Object lastKey() {
            return ((fabric.util.TreeMap) fetch()).lastKey();
        }
        
        public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                            fabric.lang.Object arg2) {
            return ((fabric.util.TreeMap) fetch()).subMap(arg1, arg2);
        }
        
        public fabric.util.SortedMap tailMap(fabric.lang.Object arg1) {
            return ((fabric.util.TreeMap) fetch()).tailMap(arg1);
        }
        
        public final int compare(fabric.lang.Object arg1,
                                 fabric.lang.Object arg2) {
            return ((fabric.util.TreeMap) fetch()).compare(arg1, arg2);
        }
        
        public final fabric.util.TreeMap.Node firstNode() {
            return ((fabric.util.TreeMap) fetch()).firstNode();
        }
        
        public final fabric.util.TreeMap.Node getNode(fabric.lang.Object arg1) {
            return ((fabric.util.TreeMap) fetch()).getNode(arg1);
        }
        
        public final fabric.util.TreeMap.Node highestLessThan(
          fabric.lang.Object arg1) {
            return ((fabric.util.TreeMap) fetch()).highestLessThan(arg1);
        }
        
        public final fabric.util.TreeMap.Node lowestGreaterThan(
          fabric.lang.Object arg1, boolean arg2) {
            return ((fabric.util.TreeMap) fetch()).lowestGreaterThan(arg1,
                                                                     arg2);
        }
        
        public final void putKeysLinear(fabric.util.Iterator arg1, int arg2) {
            ((fabric.util.TreeMap) fetch()).putKeysLinear(arg1, arg2);
        }
        
        public final void removeNode(fabric.util.TreeMap.Node arg1) {
            ((fabric.util.TreeMap) fetch()).removeNode(arg1);
        }
        
        public final fabric.util.TreeMap.Node successor(
          fabric.util.TreeMap.Node arg1) {
            return ((fabric.util.TreeMap) fetch()).successor(arg1);
        }
        
        public _Proxy(TreeMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.TreeMap {
        public fabric.util.TreeMap.Node get$nil() { return this.nil; }
        
        public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.nil = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        Node nil;
        
        public fabric.util.TreeMap.Node get$root() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.root;
        }
        
        public fabric.util.TreeMap.Node set$root(fabric.util.TreeMap.Node val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.root = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The root node of this TreeMap.
   */
        private Node root;
        
        public int get$size() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.size;
        }
        
        public int set$size(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.size = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * The size of this TreeMap. Package visible for use by nested classes.
   */
        int size;
        
        public fabric.util.Set get$entries() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.entries;
        }
        
        public fabric.util.Set set$entries(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.entries = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The cache for {@link #entrySet()}.
   */
        private transient fabric.util.Set entries;
        
        public int get$modCount() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.modCount;
        }
        
        public int set$modCount(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.modCount = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$modCount() {
            int tmp = this.get$modCount();
            this.set$modCount((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$modCount() {
            int tmp = this.get$modCount();
            this.set$modCount((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * Counts the number of modifications this TreeMap has undergone, used
   * by Iterators to know when to throw ConcurrentModificationExceptions.
   * Package visible for use by nested classes.
   */
        int modCount;
        
        public fabric.util.Comparator get$comparator() {
            return this.comparator;
        }
        
        public fabric.util.Comparator set$comparator(
          fabric.util.Comparator val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.comparator = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        fabric.util.Comparator comparator;
        
        /**
   * Instantiate a new TreeMap with no elements, using the keys' natural
   * ordering to sort. All entries in the map must have a key which implements
   * Comparable, and which are <i>mutually comparable</i>, otherwise map
   * operations may throw a {@link ClassCastException}. Attempts to use
   * a null key will throw a {@link NullPointerException}.
   *
   * @see Comparable
   */
        public fabric.util.TreeMap fabric$util$TreeMap$() {
            fabric$util$TreeMap$((fabric.util.Comparator)
                                   fabric.lang.Object._Proxy.$getProxy(null));
            return (fabric.util.TreeMap) this.$getProxy();
        }
        
        /**
   * Instantiate a new TreeMap with no elements, using the provided comparator
   * to sort. All entries in the map must have keys which are mutually
   * comparable by the Comparator, otherwise map operations may throw a
   * {@link ClassCastException}.
   *
   * @param c the sort order for the keys of this map, or null
   *        for the natural order
   */
        public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator c) {
            this.
              set$nil(
                (Node)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap.Node)
                       new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                       $getProxy()).
                        fabric$util$TreeMap$Node$(
                          null,
                          null,
                          fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK())));
            this.get$nil().set$parent(this.get$nil());
            this.get$nil().set$left(this.get$nil());
            this.get$nil().set$right(this.get$nil());
            this.set$comparator(c);
            fabric$util$AbstractMap$();
            ((fabric.util.TreeMap._Impl) this.fetch()).fabricateTree(0);
            return (fabric.util.TreeMap) this.$getProxy();
        }
        
        /**
   * Instantiate a new TreeMap, initializing it with all of the elements in
   * the provided Map.  The elements will be sorted using the natural
   * ordering of the keys. This algorithm runs in n*log(n) time. All entries
   * in the map must have keys which implement Comparable and are mutually
   * comparable, otherwise map operations may throw a
   * {@link ClassCastException}.
   *
   * @param map a Map, whose entries will be put into this TreeMap
   * @throws ClassCastException if the keys in the provided Map are not
   *         comparable
   * @throws NullPointerException if map is null
   * @see Comparable
   */
        public fabric.util.TreeMap fabric$util$TreeMap$(fabric.util.Map map) {
            fabric$util$TreeMap$((fabric.util.Comparator)
                                   fabric.lang.Object._Proxy.$getProxy(null));
            putAll(map);
            return (fabric.util.TreeMap) this.$getProxy();
        }
        
        /**
   * Instantiate a new TreeMap, initializing it with all of the elements in
   * the provided SortedMap.  The elements will be sorted using the same
   * comparator as in the provided SortedMap. This runs in linear time.
   *
   * @param sm a SortedMap, whose entries will be put into this TreeMap
   * @throws NullPointerException if sm is null
   */
        public fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap sm) {
            fabric$util$TreeMap$(sm.comparator());
            int pos = sm.size();
            fabric.util.Iterator
              itr =
              sm.
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            ((fabric.util.TreeMap._Impl) this.fetch()).fabricateTree(pos);
            Node node = firstNode();
            while (--pos >= 0) {
                Entry me = (Entry)
                             fabric.lang.Object._Proxy.$getProxy(itr.next());
                node.set$key(me.getKey());
                node.set$value(me.getValue());
                node = successor(node);
            }
            return (fabric.util.TreeMap) this.$getProxy();
        }
        
        /**
   * Clears the Map so it has no keys. This is O(1).
   */
        public void clear() {
            if (this.get$size() > 0) {
                this.postInc$modCount();
                this.set$root(this.get$nil());
                this.set$size((int) 0);
            }
        }
        
        /**
   * Return the comparator used to sort this map, or null if it is by
   * natural order.
   *
   * @return the map's comparator
   */
        public fabric.util.Comparator comparator() {
            return this.get$comparator();
        }
        
        /**
   * Returns true if the map contains a mapping for the given key.
   *
   * @param key the key to look for
   * @return true if the key has a mapping
   * @throws ClassCastException if key is not comparable to map elements
   * @throws NullPointerException if key is null and the comparator is not
   *         tolerant of nulls
   */
        public boolean containsKey(fabric.lang.Object key) {
            return !fabric.lang.Object._Proxy.idEquals(getNode(key),
                                                       this.get$nil());
        }
        
        /**
   * Returns true if the map contains at least one mapping to the given value.
   * This requires linear time.
   *
   * @param value the value to look for
   * @return true if the value appears in a mapping
   */
        public boolean containsValue(fabric.lang.Object value) {
            Node node = firstNode();
            while (!fabric.lang.Object._Proxy.idEquals(node, this.get$nil())) {
                if (fabric.util.AbstractMap._Impl.equals(value,
                                                         node.get$value()))
                    return true;
                node = successor(node);
            }
            return false;
        }
        
        /**
   * Returns a "set view" of this TreeMap's entries. The set is backed by
   * the TreeMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.<p>
   *
   * Note that the iterators for all three views, from keySet(), entrySet(),
   * and values(), traverse the TreeMap in sorted sequence.
   *
   * @return a set view of the entries
   * @see #keySet()
   * @see #values()
   * @see Map.Entry
   */
        public fabric.util.Set entrySet() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$entries(), null))
                this.
                  set$entries(
                    (fabric.util.AbstractSet)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.TreeMap.Anonymous$8)
                           new fabric.util.TreeMap.Anonymous$8._Impl(
                             this.$getStore(),
                             (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractSet$()));
            return this.get$entries();
        }
        
        /**
   * Returns the first (lowest) key in the map.
   *
   * @return the first key
   * @throws NoSuchElementException if the map is empty
   */
        public fabric.lang.Object firstKey() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$root(),
                                                   this.get$nil()))
                throw new fabric.util.NoSuchElementException();
            return firstNode().get$key();
        }
        
        /**
   * Return the value in this TreeMap associated with the supplied key,
   * or <code>null</code> if the key maps to nothing.  NOTE: Since the value
   * could also be null, you must use containsKey to see if this key
   * actually maps to something.
   *
   * @param key the key for which to fetch an associated value
   * @return what the key maps to, if present
   * @throws ClassCastException if key is not comparable to elements in the map
   * @throws NullPointerException if key is null but the comparator does not
   *         tolerate nulls
   * @see #put(Object, Object)
   * @see #containsKey(Object)
   */
        public fabric.lang.Object get(fabric.lang.Object key) {
            return getNode(key).get$value();
        }
        
        /**
   * Returns a view of this Map including all entries with keys less than
   * <code>toKey</code>. The returned map is backed by the original, so changes
   * in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoff. The returned map does not include
   * the endpoint; if you want inclusion, pass the successor element.
   *
   * @param toKey the (exclusive) cutoff point
   * @return a view of the map less than the cutoff
   * @throws ClassCastException if <code>toKey</code> is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if toKey is null, but the comparator does not
   *         tolerate null elements
   */
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey) {
            return (SubMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeMap.SubMap)
                          new fabric.util.TreeMap.SubMap._Impl(
                            this.$getStore(),
                            (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                      )).
                           fabric$util$TreeMap$SubMap$(this.get$nil(), toKey));
        }
        
        /**
   * Returns a "set view" of this TreeMap's keys. The set is backed by the
   * TreeMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.
   *
   * @return a set view of the keys
   * @see #values()
   * @see #entrySet()
   */
        public fabric.util.Set keySet() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$keys(), null))
                this.
                  set$keys(
                    (fabric.util.AbstractSet)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.TreeMap.Anonymous$9)
                           new fabric.util.TreeMap.Anonymous$9._Impl(
                             this.$getStore(),
                             (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractSet$()));
            return this.get$keys();
        }
        
        /**
   * Returns the last (highest) key in the map.
   *
   * @return the last key
   * @throws NoSuchElementException if the map is empty
   */
        public fabric.lang.Object lastKey() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$root(),
                                                   this.get$nil()))
                throw new fabric.util.NoSuchElementException("empty");
            return ((fabric.util.TreeMap._Impl) this.fetch()).lastNode().
              get$key();
        }
        
        /**
   * Puts the supplied value into the Map, mapped by the supplied key.
   * The value may be retrieved by any object which <code>equals()</code>
   * this key. NOTE: Since the prior value could also be null, you must
   * first use containsKey if you want to see if you are replacing the
   * key's mapping.
   *
   * @param key the key used to locate the value
   * @param value the value to be stored in the Map
   * @return the prior mapping of the key, or null if there was none
   * @throws ClassCastException if key is not comparable to current map keys
   * @throws NullPointerException if key is null, but the comparator does
   *         not tolerate nulls
   * @see #get(Object)
   * @see Object#equals(Object)
   */
        public fabric.lang.Object put(fabric.lang.Object key,
                                      fabric.lang.Object value) {
            Node current = this.get$root();
            Node parent = this.get$nil();
            int comparison = 0;
            while (!fabric.lang.Object._Proxy.idEquals(current,
                                                       this.get$nil())) {
                parent = current;
                comparison = compare(key, current.get$key());
                if (comparison > 0) current = current.get$right();
                else if (comparison < 0) current = current.get$left(); else
                    return current.setValue(value);
            }
            Node
              n =
              (Node)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.TreeMap.Node)
                     new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                     $getProxy()).
                      fabric$util$TreeMap$Node$(
                        key,
                        value,
                        fabric.util.TreeMap._Static._Proxy.$instance.get$RED(
                                                                       )));
            n.set$left(this.get$nil());
            n.set$right(this.get$nil());
            n.set$parent(this.get$nil());
            n.set$parent(parent);
            this.postInc$modCount();
            this.postInc$size();
            if (fabric.lang.Object._Proxy.idEquals(parent, this.get$nil())) {
                this.set$root(n);
                return null;
            }
            if (comparison > 0) parent.set$right(n); else parent.set$left(n);
            ((fabric.util.TreeMap._Impl) this.fetch()).insertFixup(n);
            return null;
        }
        
        /**
   * Copies all elements of the given map into this TreeMap.  If this map
   * already has a mapping for a key, the new mapping replaces the current
   * one.
   *
   * @param m the map to be added
   * @throws ClassCastException if a key in m is not comparable with keys
   *         in the map
   * @throws NullPointerException if a key in m is null, and the comparator
   *         does not tolerate nulls
   */
        public void putAll(fabric.util.Map m) {
            fabric.util.Iterator
              itr =
              m.
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = m.size();
            while (--pos >= 0) {
                Entry e = (Entry)
                            fabric.lang.Object._Proxy.$getProxy(itr.next());
                put(e.getKey(), e.getValue());
            }
        }
        
        /**
   * Removes from the TreeMap and returns the value which is mapped by the
   * supplied key. If the key maps to nothing, then the TreeMap remains
   * unchanged, and <code>null</code> is returned. NOTE: Since the value
   * could also be null, you must use containsKey to see if you are
   * actually removing a mapping.
   *
   * @param key the key used to locate the value to remove
   * @return whatever the key mapped to, if present
   * @throws ClassCastException if key is not comparable to current map keys
   * @throws NullPointerException if key is null, but the comparator does
   *         not tolerate nulls
   */
        public fabric.lang.Object remove(fabric.lang.Object key) {
            Node n = getNode(key);
            if (fabric.lang.Object._Proxy.idEquals(n, this.get$nil()))
                return null;
            fabric.lang.Object result = n.get$value();
            removeNode(n);
            return result;
        }
        
        /**
   * Returns the number of key-value mappings currently in this Map.
   *
   * @return the size
   */
        public int size() { return this.get$size(); }
        
        /**
   * Returns a view of this Map including all entries with keys greater or
   * equal to <code>fromKey</code> and less than <code>toKey</code> (a
   * half-open interval). The returned map is backed by the original, so
   * changes in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoffs. The returned map includes the low
   * endpoint but not the high; if you want to reverse this behavior on
   * either end, pass in the successor element.
   *
   * @param fromKey the (inclusive) low cutoff point
   * @param toKey the (exclusive) high cutoff point
   * @return a view of the map between the cutoffs
   * @throws ClassCastException if either cutoff is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if fromKey or toKey is null, but the
   *         comparator does not tolerate null elements
   * @throws IllegalArgumentException if fromKey is greater than toKey
   */
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                            fabric.lang.Object toKey) {
            return (SubMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeMap.SubMap)
                          new fabric.util.TreeMap.SubMap._Impl(
                            this.$getStore(),
                            (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                      )).
                           fabric$util$TreeMap$SubMap$(fromKey, toKey));
        }
        
        /**
   * Returns a view of this Map including all entries with keys greater or
   * equal to <code>fromKey</code>. The returned map is backed by the
   * original, so changes in one appear in the other. The submap will throw an
   * {@link IllegalArgumentException} for any attempt to access or add an
   * element beyond the specified cutoff. The returned map includes the
   * endpoint; if you want to exclude it, pass in the successor element.
   *
   * @param fromKey the (inclusive) low cutoff point
   * @return a view of the map above the cutoff
   * @throws ClassCastException if <code>fromKey</code> is not compatible with
   *         the comparator (or is not Comparable, for natural ordering)
   * @throws NullPointerException if fromKey is null, but the comparator
   *         does not tolerate null elements
   */
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey) {
            return (SubMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.TreeMap.SubMap)
                          new fabric.util.TreeMap.SubMap._Impl(
                            this.$getStore(),
                            (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                      )).
                           fabric$util$TreeMap$SubMap$(fromKey,
                                                       this.get$nil()));
        }
        
        /**
   * Returns a "collection view" (or "bag view") of this TreeMap's values.
   * The collection is backed by the TreeMap, so changes in one show up
   * in the other.  The collection supports element removal, but not element
   * addition.
   *
   * @return a bag view of the values
   * @see #keySet()
   * @see #entrySet()
   */
        public fabric.util.Collection values() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$values(), null))
                this.
                  set$values(
                    (fabric.util.AbstractCollection)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.TreeMap.Anonymous$10)
                           new fabric.util.TreeMap.Anonymous$10._Impl(
                             this.$getStore(),
                             (fabric.util.TreeMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractCollection$()));
            return this.get$values();
        }
        
        /**
   * Compares two elements by the set comparator, or by natural ordering.
   * Package visible for use by nested classes.
   *
   * @param o1 the first object
   * @param o2 the second object
   * @throws ClassCastException if o1 and o2 are not mutually comparable,
   *         or are not Comparable with natural ordering
   * @throws NullPointerException if o1 or o2 is null with natural ordering
   */
        public final int compare(fabric.lang.Object o1, fabric.lang.Object o2) {
            return fabric.lang.Object._Proxy.idEquals(this.get$comparator(),
                                                      null)
              ? ((java.lang.Comparable)
                   (java.lang.Object)
                     fabric.lang.WrappedJavaInlineable.$unwrap(o1)).
              compareTo((java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(o2))
              : this.get$comparator().compare(o1, o2);
        }
        
        /**
   * Maintain red-black balance after deleting a node.
   *
   * @param node the child of the node just deleted, possibly nil
   * @param parent the parent of the node just deleted, never nil
   */
        private void deleteFixup(Node node, Node parent) {
            while (!fabric.lang.Object._Proxy.idEquals(node, this.get$root()) &&
                     node.get$color() ==
                     fabric.util.TreeMap._Static._Proxy.$instance.get$BLACK()) {
                if (fabric.lang.Object._Proxy.idEquals(node,
                                                       parent.get$left())) {
                    Node sibling = parent.get$right();
                    if (sibling.get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.get$RED(
                                                                         )) {
                        sibling.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        parent.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        ((fabric.util.TreeMap._Impl) this.fetch()).rotateLeft(
                                                                     parent);
                        sibling = parent.get$right();
                    }
                    if (sibling.get$left().get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.
                          get$BLACK() &&
                          sibling.get$right().get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.
                          get$BLACK()) {
                        sibling.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        node = parent;
                        parent = parent.get$parent();
                    }
                    else {
                        if (sibling.get$right().get$color() ==
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK()) {
                            sibling.
                              get$left().
                              set$color(
                                (int)
                                  fabric.util.TreeMap._Static._Proxy.$instance.
                                  get$BLACK());
                            sibling.
                              set$color(
                                (int)
                                  fabric.util.TreeMap._Static._Proxy.$instance.
                                  get$RED());
                            ((fabric.util.TreeMap._Impl) this.fetch()).
                              rotateRight(sibling);
                            sibling = parent.get$right();
                        }
                        sibling.set$color((int) parent.get$color());
                        parent.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        sibling.
                          get$right().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        ((fabric.util.TreeMap._Impl) this.fetch()).rotateLeft(
                                                                     parent);
                        node = this.get$root();
                    }
                }
                else {
                    Node sibling =
                      parent.get$left();
                    if (sibling.get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.get$RED(
                                                                         )) {
                        sibling.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        parent.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        ((fabric.util.TreeMap._Impl) this.fetch()).rotateRight(
                                                                     parent);
                        sibling = parent.get$left();
                    }
                    if (sibling.get$right().get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.
                          get$BLACK() &&
                          sibling.get$left().get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.
                          get$BLACK()) {
                        sibling.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        node = parent;
                        parent = parent.get$parent();
                    }
                    else {
                        if (sibling.get$left().get$color() ==
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK()) {
                            sibling.
                              get$right().
                              set$color(
                                (int)
                                  fabric.util.TreeMap._Static._Proxy.$instance.
                                  get$BLACK());
                            sibling.
                              set$color(
                                (int)
                                  fabric.util.TreeMap._Static._Proxy.$instance.
                                  get$RED());
                            ((fabric.util.TreeMap._Impl) this.fetch()).
                              rotateLeft(sibling);
                            sibling = parent.get$left();
                        }
                        sibling.set$color((int) parent.get$color());
                        parent.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        sibling.
                          get$left().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        ((fabric.util.TreeMap._Impl) this.fetch()).rotateRight(
                                                                     parent);
                        node = this.get$root();
                    }
                }
            }
            node.set$color(
                   (int)
                     fabric.util.TreeMap._Static._Proxy.$instance.get$BLACK());
        }
        
        /**
   * Construct a perfectly balanced tree consisting of n "blank" nodes. This
   * permits a tree to be generated from pre-sorted input in linear time.
   *
   * @param count the number of blank nodes, non-negative
   */
        private void fabricateTree(final int count) {
            if (count == 0) {
                this.set$root(this.get$nil());
                this.set$size((int) 0);
                return;
            }
            this.
              set$root(
                (Node)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.TreeMap.Node)
                       new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                       $getProxy()).
                        fabric$util$TreeMap$Node$(
                          null,
                          null,
                          fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK())));
            this.get$root().set$left(this.get$nil());
            this.get$root().set$right(this.get$nil());
            this.get$root().set$parent(this.get$nil());
            this.set$size((int) count);
            Node row = this.get$root();
            int rowsize;
            for (rowsize = 2; rowsize + rowsize <= count; rowsize <<= 1) {
                Node parent = row;
                Node last = null;
                for (int i = 0; i < rowsize; i += 2) {
                    Node
                      left =
                      (Node)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.util.TreeMap.Node)
                             new fabric.util.TreeMap.Node._Impl(
                               this.$getStore()).$getProxy()).
                              fabric$util$TreeMap$Node$(
                                null,
                                null,
                                fabric.util.TreeMap._Static._Proxy.$instance.
                                    get$BLACK()));
                    Node
                      right =
                      (Node)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.util.TreeMap.Node)
                             new fabric.util.TreeMap.Node._Impl(
                               this.$getStore()).$getProxy()).
                              fabric$util$TreeMap$Node$(
                                null,
                                null,
                                fabric.util.TreeMap._Static._Proxy.$instance.
                                    get$BLACK()));
                    left.set$left(this.get$nil());
                    right.set$left(this.get$nil());
                    right.set$right(this.get$nil());
                    left.set$parent(parent);
                    left.set$right(right);
                    right.set$parent(parent);
                    parent.set$left(left);
                    Node next = parent.get$right();
                    parent.set$right(right);
                    parent = next;
                    if (!fabric.lang.Object._Proxy.idEquals(last, null))
                        last.set$right(left);
                    last = right;
                }
                row = row.get$left();
            }
            int overflow = count - rowsize;
            Node parent = row;
            int i;
            for (i = 0; i < overflow; i += 2) {
                Node
                  left =
                  (Node)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.util.TreeMap.Node)
                         new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                         $getProxy()).
                          fabric$util$TreeMap$Node$(
                            null,
                            null,
                            fabric.util.TreeMap._Static._Proxy.$instance.
                                get$RED()));
                Node
                  right =
                  (Node)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.util.TreeMap.Node)
                         new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                         $getProxy()).
                          fabric$util$TreeMap$Node$(
                            null,
                            null,
                            fabric.util.TreeMap._Static._Proxy.$instance.
                                get$RED()));
                left.set$left(this.get$nil());
                left.set$right(this.get$nil());
                left.set$parent(this.get$nil());
                right.set$left(this.get$nil());
                right.set$right(this.get$nil());
                right.set$parent(this.get$nil());
                left.set$parent(parent);
                right.set$parent(parent);
                parent.set$left(left);
                Node next = parent.get$right();
                parent.set$right(right);
                parent = next;
            }
            if (i - overflow == 0) {
                Node
                  left =
                  (Node)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.util.TreeMap.Node)
                         new fabric.util.TreeMap.Node._Impl(this.$getStore()).
                         $getProxy()).
                          fabric$util$TreeMap$Node$(
                            null,
                            null,
                            fabric.util.TreeMap._Static._Proxy.$instance.
                                get$RED()));
                left.set$left(this.get$nil());
                left.set$right(this.get$nil());
                left.set$parent(parent);
                parent.set$left(left);
                parent = parent.get$right();
                left.get$parent().set$right(this.get$nil());
            }
            while (!fabric.lang.Object._Proxy.idEquals(parent,
                                                       this.get$nil())) {
                Node next = parent.get$right();
                parent.set$right(this.get$nil());
                parent = next;
            }
        }
        
        /**
   * Returns the first sorted node in the map, or nil if empty. Package
   * visible for use by nested classes.
   *
   * @return the first node
   */
        public final Node firstNode() {
            Node node = this.get$root();
            while (!fabric.lang.Object._Proxy.idEquals(node.get$left(),
                                                       this.get$nil()))
                node = node.get$left();
            return node;
        }
        
        /**
   * Return the TreeMap.Node associated with key, or the nil node if no such
   * node exists in the tree. Package visible for use by nested classes.
   *
   * @param key the key to search for
   * @return the node where the key is found, or nil
   */
        public final Node getNode(fabric.lang.Object key) {
            Node current = this.get$root();
            while (!fabric.lang.Object._Proxy.idEquals(current,
                                                       this.get$nil())) {
                int comparison = compare(key, current.get$key());
                if (comparison > 0) current = current.get$right();
                else if (comparison < 0) current = current.get$left(); else
                    return current;
            }
            return current;
        }
        
        /**
   * Find the "highest" node which is &lt; key. If key is nil, return last
   * node. Package visible for use by nested classes.
   *
   * @param key the upper bound, exclusive
   * @return the previous node
   */
        public final Node highestLessThan(fabric.lang.Object key) {
            if (fabric.lang.Object._Proxy.idEquals(key, this.get$nil()))
                return ((fabric.util.TreeMap._Impl) this.fetch()).lastNode();
            Node last = this.get$nil();
            Node current = this.get$root();
            int comparison = 0;
            while (!fabric.lang.Object._Proxy.idEquals(current,
                                                       this.get$nil())) {
                last = current;
                comparison = compare(key, current.get$key());
                if (comparison > 0)
                    current = current.get$right();
                else if (comparison < 0)
                    current = current.get$left();
                else
                    return ((fabric.util.TreeMap._Impl)
                              this.fetch()).predecessor(last);
            }
            return comparison <= 0
              ? ((fabric.util.TreeMap._Impl) this.fetch()).predecessor(last)
              : last;
        }
        
        /**
   * Maintain red-black balance after inserting a new node.
   *
   * @param n the newly inserted node
   */
        private void insertFixup(Node n) {
            while (n.get$parent().get$color() ==
                     fabric.util.TreeMap._Static._Proxy.$instance.get$RED() &&
                     !fabric.lang.Object._Proxy.idEquals(
                                                  n.get$parent().get$parent(),
                                                  this.get$nil())) {
                if (fabric.lang.Object._Proxy.idEquals(
                                                n.get$parent(),
                                                n.get$parent().get$parent(
                                                                 ).get$left(
                                                                     ))) {
                    Node uncle = n.get$parent().get$parent().get$right();
                    if (uncle.get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.get$RED(
                                                                         )) {
                        n.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        uncle.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        uncle.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        n = uncle.get$parent();
                    }
                    else {
                        if (fabric.lang.Object._Proxy.idEquals(
                                                        n,
                                                        n.get$parent(
                                                            ).get$right())) {
                            n = n.get$parent();
                            ((fabric.util.TreeMap._Impl) this.fetch()).
                              rotateLeft(n);
                        }
                        n.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        n.
                          get$parent().
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        ((fabric.util.TreeMap._Impl) this.fetch()).
                          rotateRight(n.get$parent().get$parent());
                    }
                }
                else {
                    Node uncle =
                      n.get$parent().get$parent().get$left();
                    if (uncle.get$color() ==
                          fabric.util.TreeMap._Static._Proxy.$instance.get$RED(
                                                                         )) {
                        n.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        uncle.
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        uncle.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        n = uncle.get$parent();
                    }
                    else {
                        if (fabric.lang.Object._Proxy.idEquals(
                                                        n,
                                                        n.get$parent().get$left(
                                                                         ))) {
                            n = n.get$parent();
                            ((fabric.util.TreeMap._Impl) this.fetch()).
                              rotateRight(n);
                        }
                        n.
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$BLACK());
                        n.
                          get$parent().
                          get$parent().
                          set$color(
                            (int)
                              fabric.util.TreeMap._Static._Proxy.$instance.
                              get$RED());
                        ((fabric.util.TreeMap._Impl) this.fetch()).
                          rotateLeft(n.get$parent().get$parent());
                    }
                }
            }
            this.get$root().
              set$color(
                (int) fabric.util.TreeMap._Static._Proxy.$instance.get$BLACK());
        }
        
        /**
   * Returns the last sorted node in the map, or nil if empty.
   *
   * @return the last node
   */
        private Node lastNode() {
            Node node = this.get$root();
            while (!fabric.lang.Object._Proxy.idEquals(node.get$right(),
                                                       this.get$nil()))
                node = node.get$right();
            return node;
        }
        
        /**
   * Find the "lowest" node which is &gt;= key. If key is nil, return either
   * nil or the first node, depending on the parameter first.
   * Package visible for use by nested classes.
   *
   * @param key the lower bound, inclusive
   * @param first true to return the first element instead of nil for nil key
   * @return the next node
   */
        public final Node lowestGreaterThan(fabric.lang.Object key,
                                            boolean first) {
            if (fabric.lang.Object._Proxy.idEquals(key, this.get$nil()))
                return first ? firstNode() : this.get$nil();
            Node last = this.get$nil();
            Node current = this.get$root();
            int comparison = 0;
            while (!fabric.lang.Object._Proxy.idEquals(current,
                                                       this.get$nil())) {
                last = current;
                comparison = compare(key, current.get$key());
                if (comparison > 0) current = current.get$right();
                else if (comparison < 0) current = current.get$left(); else
                    return current;
            }
            return comparison > 0 ? successor(last) : last;
        }
        
        /**
   * Return the node preceding the given one, or nil if there isn't one.
   *
   * @param node the current node, not nil
   * @return the prior node in sorted order
   */
        private Node predecessor(Node node) {
            if (!fabric.lang.Object._Proxy.idEquals(node.get$left(),
                                                    this.get$nil())) {
                node = node.get$left();
                while (!fabric.lang.Object._Proxy.idEquals(node.get$right(),
                                                           this.get$nil()))
                    node = node.get$right();
                return node;
            }
            Node parent = node.get$parent();
            while (fabric.lang.Object._Proxy.idEquals(node,
                                                      parent.get$left())) {
                node = parent;
                parent = node.get$parent();
            }
            return parent;
        }
        
        /**
   * Construct a tree from sorted keys in linear time, with values of "".
   * Package visible for use by TreeSet.
   *
   * @param keys the iterator over the sorted keys
   * @param count the number of nodes to insert
   * @see TreeSet#TreeSet(SortedSet)
   */
        public final void putKeysLinear(fabric.util.Iterator keys, int count) {
            ((fabric.util.TreeMap._Impl) this.fetch()).fabricateTree(count);
            Node node = firstNode();
            while (--count >= 0) {
                node.set$key(keys.next());
                node.set$value(fabric.lang.WrappedJavaInlineable.$wrap(""));
                node = successor(node);
            }
        }
        
        /**
   * Remove node from tree. This will increment modCount and decrement size.
   * Node must exist in the tree. Package visible for use by nested classes.
   *
   * @param node the node to remove
   */
        public final void removeNode(Node node) {
            Node splice;
            Node child;
            this.postInc$modCount();
            this.postDec$size();
            if (fabric.lang.Object._Proxy.idEquals(node.get$left(),
                                                   this.get$nil())) {
                splice = node;
                child = node.get$right();
            } else if (fabric.lang.Object._Proxy.idEquals(node.get$right(),
                                                          this.get$nil())) {
                splice = node;
                child = node.get$left();
            } else {
                splice = node.get$left();
                while (!fabric.lang.Object._Proxy.idEquals(splice.get$right(),
                                                           this.get$nil()))
                    splice = splice.get$right();
                child = splice.get$left();
                node.set$key(splice.get$key());
                node.set$value(splice.get$value());
            }
            Node parent = splice.get$parent();
            if (!fabric.lang.Object._Proxy.idEquals(child, this.get$nil()))
                child.set$parent(parent);
            if (fabric.lang.Object._Proxy.idEquals(parent, this.get$nil())) {
                this.set$root(child);
                return;
            }
            if (fabric.lang.Object._Proxy.idEquals(splice, parent.get$left()))
                parent.set$left(child); else parent.set$right(child);
            if (splice.get$color() ==
                  fabric.util.TreeMap._Static._Proxy.$instance.get$BLACK())
                ((fabric.util.TreeMap._Impl) this.fetch()).deleteFixup(child,
                                                                       parent);
        }
        
        /**
   * Rotate node n to the left.
   *
   * @param node the node to rotate
   */
        private void rotateLeft(Node node) {
            Node child = node.get$right();
            node.set$right(child.get$left());
            if (!fabric.lang.Object._Proxy.idEquals(child.get$left(),
                                                    this.get$nil()))
                child.get$left().set$parent(node);
            child.set$parent(node.get$parent());
            if (!fabric.lang.Object._Proxy.idEquals(node.get$parent(),
                                                    this.get$nil())) {
                if (fabric.lang.Object._Proxy.idEquals(
                                                node,
                                                node.get$parent().get$left()))
                    node.get$parent().set$left(child); else
                    node.get$parent().set$right(child);
            } else this.set$root(child);
            child.set$left(node);
            node.set$parent(child);
        }
        
        /**
   * Rotate node n to the right.
   *
   * @param node the node to rotate
   */
        private void rotateRight(Node node) {
            Node child = node.get$left();
            node.set$left(child.get$right());
            if (!fabric.lang.Object._Proxy.idEquals(child.get$right(),
                                                    this.get$nil()))
                child.get$right().set$parent(node);
            child.set$parent(node.get$parent());
            if (!fabric.lang.Object._Proxy.idEquals(node.get$parent(),
                                                    this.get$nil())) {
                if (fabric.lang.Object._Proxy.idEquals(
                                                node,
                                                node.get$parent().get$right()))
                    node.get$parent().set$right(child); else
                    node.get$parent().set$left(child);
            } else this.set$root(child);
            child.set$right(node);
            node.set$parent(child);
        }
        
        /**
   * Return the node following the given one, or nil if there isn't one.
   * Package visible for use by nested classes.
   *
   * @param node the current node, not nil
   * @return the next node in sorted order
   */
        public final Node successor(Node node) {
            if (!fabric.lang.Object._Proxy.idEquals(node.get$right(),
                                                    this.get$nil())) {
                node = node.get$right();
                while (!fabric.lang.Object._Proxy.idEquals(node.get$left(),
                                                           this.get$nil()))
                    node = node.get$left();
                return node;
            }
            Node parent = node.get$parent();
            while (fabric.lang.Object._Proxy.idEquals(node,
                                                      parent.get$right())) {
                node = parent;
                parent = parent.get$parent();
            }
            return parent;
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.TreeMap) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.TreeMap._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.nil, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.root, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeInt(this.size);
            out.writeInt(this.modCount);
            $writeRef($getStore(), this.comparator, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.nil = (fabric.util.TreeMap.Node)
                         $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                  (fabric.common.RefTypeEnum) refTypes.next(),
                                  in, store, intraStoreRefs, interStoreRefs);
            this.root = (fabric.util.TreeMap.Node)
                          $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.size = in.readInt();
            this.modCount = in.readInt();
            this.comparator = (fabric.util.Comparator)
                                $readRef(fabric.util.Comparator._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(), in, store,
                                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.TreeMap._Impl src = (fabric.util.TreeMap._Impl) other;
            this.nil = src.nil;
            this.root = src.root;
            this.size = src.size;
            this.entries = src.entries;
            this.modCount = src.modCount;
            this.comparator = src.comparator;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$RED();
        
        public int set$RED(int val);
        
        public int postInc$RED();
        
        public int postDec$RED();
        
        public int get$BLACK();
        
        public int set$BLACK(int val);
        
        public int postInc$BLACK();
        
        public int postDec$BLACK();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.TreeMap._Static {
            public int get$RED() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).get$RED();
            }
            
            public int set$RED(int val) {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).set$RED(
                                                                       val);
            }
            
            public int postInc$RED() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).
                  postInc$RED();
            }
            
            public int postDec$RED() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).
                  postDec$RED();
            }
            
            public int get$BLACK() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).get$BLACK(
                                                                       );
            }
            
            public int set$BLACK(int val) {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).set$BLACK(
                                                                       val);
            }
            
            public int postInc$BLACK() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).
                  postInc$BLACK();
            }
            
            public int postDec$BLACK() {
                return ((fabric.util.TreeMap._Static._Impl) fetch()).
                  postDec$BLACK();
            }
            
            public _Proxy(fabric.util.TreeMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.TreeMap._Static $instance;
            
            static {
                fabric.
                  util.
                  TreeMap.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.TreeMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.TreeMap._Static._Impl.class);
                $instance = (fabric.util.TreeMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeMap._Static {
            public int get$RED() { return this.RED; }
            
            public int set$RED(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.RED = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$RED() {
                int tmp = this.get$RED();
                this.set$RED((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$RED() {
                int tmp = this.get$RED();
                this.set$RED((int) (tmp - 1));
                return tmp;
            }
            
            int RED;
            
            public int get$BLACK() { return this.BLACK; }
            
            public int set$BLACK(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.BLACK = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$BLACK() {
                int tmp = this.get$BLACK();
                this.set$BLACK((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$BLACK() {
                int tmp = this.get$BLACK();
                this.set$BLACK((int) (tmp - 1));
                return tmp;
            }
            
            int BLACK;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.RED);
                out.writeInt(this.BLACK);
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
                this.RED = in.readInt();
                this.BLACK = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm851 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled854 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff852 = 1;
                        boolean $doBackoff853 = true;
                        boolean $retry847 = true;
                        boolean $keepReads848 = false;
                        $label845: for (boolean $commit846 = false; !$commit846;
                                        ) {
                            if ($backoffEnabled854) {
                                if ($doBackoff853) {
                                    if ($backoff852 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff852));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e849) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff852 < 5000) $backoff852 *= 2;
                                }
                                $doBackoff853 = $backoff852 <= 32 ||
                                                  !$doBackoff853;
                            }
                            $commit846 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.TreeMap._Static._Proxy.
                                      $instance.
                                      set$RED((int) -1);
                                    fabric.util.TreeMap._Static._Proxy.
                                      $instance.
                                      set$BLACK((int) 1);
                                }
                                catch (final fabric.worker.
                                         RetryException $e849) {
                                    throw $e849;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e849) {
                                    throw $e849;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e849) {
                                    throw $e849;
                                }
                                catch (final Throwable $e849) {
                                    $tm851.getCurrentLog().checkRetrySignal();
                                    throw $e849;
                                }
                            }
                            catch (final fabric.worker.RetryException $e849) {
                                $commit846 = false;
                                continue $label845;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e849) {
                                $commit846 = false;
                                $retry847 = false;
                                $keepReads848 = $e849.keepReads;
                                if ($tm851.checkForStaleObjects()) {
                                    $retry847 = true;
                                    $keepReads848 = false;
                                    continue $label845;
                                }
                                fabric.common.TransactionID $currentTid850 =
                                  $tm851.getCurrentTid();
                                if ($e849.tid ==
                                      null ||
                                      !$e849.tid.isDescendantOf(
                                                   $currentTid850)) {
                                    throw $e849;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e849);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e849) {
                                $commit846 = false;
                                fabric.common.TransactionID $currentTid850 =
                                  $tm851.getCurrentTid();
                                if ($e849.tid.isDescendantOf($currentTid850))
                                    continue $label845;
                                if ($currentTid850.parent != null) {
                                    $retry847 = false;
                                    throw $e849;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e849) {
                                $commit846 = false;
                                if ($tm851.checkForStaleObjects())
                                    continue $label845;
                                $retry847 = false;
                                throw new fabric.worker.AbortException($e849);
                            }
                            finally {
                                if ($commit846) {
                                    fabric.common.TransactionID $currentTid850 =
                                      $tm851.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e849) {
                                        $commit846 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e849) {
                                        $commit846 = false;
                                        $retry847 = false;
                                        $keepReads848 = $e849.keepReads;
                                        if ($tm851.checkForStaleObjects()) {
                                            $retry847 = true;
                                            $keepReads848 = false;
                                            continue $label845;
                                        }
                                        if ($e849.tid ==
                                              null ||
                                              !$e849.tid.isDescendantOf(
                                                           $currentTid850))
                                            throw $e849;
                                        throw new fabric.worker.
                                                UserAbortException($e849);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e849) {
                                        $commit846 = false;
                                        $currentTid850 = $tm851.getCurrentTid();
                                        if ($currentTid850 != null) {
                                            if ($e849.tid.equals(
                                                            $currentTid850) ||
                                                  !$e849.tid.
                                                  isDescendantOf(
                                                    $currentTid850)) {
                                                throw $e849;
                                            }
                                        }
                                    }
                                } else if ($keepReads848) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit846) {
                                    {  }
                                    if ($retry847) { continue $label845; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 69, -117, -38, -107,
    127, 14, 102, 119, 62, 76, 31, -119, -68, 22, -55, -100, -100, -103, 101,
    55, -7, -28, -19, -39, -115, 99, -65, -17, -121, -59, 6, -123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
