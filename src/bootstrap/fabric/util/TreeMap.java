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
        
        public static interface Anonymous$3 extends fabric.util.AbstractSet {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements Anonymous$3 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$3._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$3._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements Anonymous$3 {
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
                    return (Anonymous$3) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$3._Proxy(
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
                    fabric.util.TreeMap.SubMap.Anonymous$3._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$3._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$3._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$3.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$3.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$3.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$3.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$3._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$3._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$3._Static {
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
                        return new fabric.util.TreeMap.SubMap.Anonymous$3.
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
        
        public static interface Anonymous$4 extends fabric.util.AbstractSet {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public boolean contains(fabric.lang.Object o);
            
            public boolean remove(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.util.AbstractSet._Proxy
              implements Anonymous$4 {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$4._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$4._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.util.AbstractSet._Impl
              implements Anonymous$4 {
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
                    return (Anonymous$4) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$4._Proxy(
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
                    fabric.util.TreeMap.SubMap.Anonymous$4._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$4._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$4._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$4.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$4.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$4.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$4.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$4._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$4._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$4._Static {
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
                        return new fabric.util.TreeMap.SubMap.Anonymous$4.
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
        
        public static interface Anonymous$5
          extends fabric.util.AbstractCollection {
            public fabric.util.TreeMap.SubMap get$out$();
            
            public int size();
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public void clear();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy
            extends fabric.util.AbstractCollection._Proxy implements Anonymous$5
            {
                public fabric.util.TreeMap.SubMap get$out$() {
                    return ((fabric.util.TreeMap.SubMap.Anonymous$5._Impl)
                              fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$5._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl
            extends fabric.util.AbstractCollection._Impl implements Anonymous$5
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
                    return (Anonymous$5) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SubMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap.Anonymous$5._Proxy(
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
                    fabric.util.TreeMap.SubMap.Anonymous$5._Impl src =
                      (fabric.util.TreeMap.SubMap.Anonymous$5._Impl) other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy extends fabric.lang.Object._Proxy
                  implements fabric.util.TreeMap.SubMap.Anonymous$5._Static {
                    public _Proxy(fabric.util.TreeMap.SubMap.Anonymous$5.
                                    _Static._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.TreeMap.SubMap.Anonymous$5.
                      _Static $instance;
                    
                    static {
                        fabric.
                          util.
                          TreeMap.
                          SubMap.
                          Anonymous$5.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            TreeMap.
                            SubMap.
                            Anonymous$5.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.TreeMap.SubMap.Anonymous$5._Static.
                                _Impl.class);
                        $instance =
                          (fabric.util.TreeMap.SubMap.Anonymous$5._Static)
                            impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl extends fabric.lang.Object._Impl
                  implements fabric.util.TreeMap.SubMap.Anonymous$5._Static {
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
                        return new fabric.util.TreeMap.SubMap.Anonymous$5.
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
                            ((fabric.util.TreeMap.SubMap.Anonymous$3)
                               new fabric.util.TreeMap.SubMap.Anonymous$3._Impl(
                                 this.$getStore(),
                                 (fabric.util.TreeMap.SubMap) this.$getProxy()).
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
                            ((fabric.util.TreeMap.SubMap.Anonymous$4)
                               new fabric.util.TreeMap.SubMap.Anonymous$4._Impl(
                                 this.$getStore(),
                                 (fabric.util.TreeMap.SubMap) this.$getProxy()).
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
                            ((fabric.util.TreeMap.SubMap.Anonymous$5)
                               new fabric.util.TreeMap.SubMap.Anonymous$5._Impl(
                                 this.$getStore(),
                                 (fabric.util.TreeMap.SubMap) this.$getProxy()).
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
    
    public static interface Anonymous$0 extends fabric.util.AbstractSet {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$0 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$0._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$0._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$0 {
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
                return (Anonymous$0) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$0._Proxy(this);
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
                fabric.util.TreeMap.Anonymous$0._Impl src =
                  (fabric.util.TreeMap.Anonymous$0._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$0._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$0._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$0._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$0.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$0._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$0._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$0._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$0._Static {
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
                    return new fabric.util.TreeMap.Anonymous$0._Static._Proxy(
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
          "H4sIAAAAAAAAAK1Ya2xcxRWeXdvrR0zsOC/iJI7jLKnihN0GSxXUgLC3eSzZEMuOoXEo7uy9s/aN7957mTtrrymhJFKVFKFIbZzwEARVMioPA1Ir2h+tK6TSNoiqVWlV4AchSKRA0/xAFaU/Wug5M3d3795dL0SqpZ2ZnTnnzHl+c9YLV0iDy0lPhqYNMyZmHebGdtN0MjVEucv0hEld9yDsjmvL6pNnP/yx3hUm4RRp1ahlW4ZGzXHLFWR56gidpnGLifjocLL/MGnWkHEvdScFCR8ezHPS7djm7IRpC++SCvlntsfnHrmn/Sd1pG2MtBnWiKDC0BK2JVhejJHWLMumGXcHdJ3pY2SFxZg+wrhBTeM+ILStMdLhGhMWFTnO3GHm2uY0Ena4OYdxeWdhE9W3QW2e04TNQf12pX5OGGY8ZbiiP0UiGYOZunsveYDUp0hDxqQTQLgmVbAiLiXGd+M+kLcYoCbPUI0VWOqnDEsXZFOQo2hxdB8QAGtjlolJu3hVvUVhg3QolUxqTcRHBDesCSBtsHNwiyCdSwoFoiaHalN0go0Lcm2QbkgdAVWzdAuyCLI6SCYlQcw6AzHzRevKHTef+o611wqTEOisM81E/ZuAqSvANMwyjDNLY4qxtTd1lq5ZPBkmBIhXB4gVzc/v//i2HV2vnFc066vQHEgfYZoY1+bTy/+0IbHtpjpUo8mxXQNTocxyGdUh76Q/70C2rylKxMNY4fCV4d8eevA5djlMWpIkotlmLgtZtUKzs45hMr6HWYxTwfQkaWaWnpDnSdII65RhMbV7IJNxmUiSelNuRWz5HVyUARHookZYG1bGLqwdKiblOu8QQrrgQxoJCWmE9D4P805CrrtDkD3xSTvL4mkzx2YgvePwYZRrk3GoW25o12u2Mxt3uRbnOUsYQKn2lfEHOWP7qRMDFZz/n6g8at0+EwqBQzdpts7S1IXoeJkyOGRCMey1TZ3xcc08tZgkKxcfk9nSjBnuQpZKf4QgwhuC2ODnncsN7vr4xfHXVaYhr+cuKC6lmoqip1p0AIBpNmvn3OhXQbNWLKMYAFMMgGkhlI8lziWfl9kScWVZFYW1grCvOyYVGZtn8yQUkpatkvzyAgjyFIAH4EPrtpFv3f7tkz11kJ/OTD2EDEmjwWopYUwSVhRKYFxrO/Hhv146e9Qu1Y0g0YpyruTEcuwJuonbGtMB7krie7vpy+OLR6NhhJJmQDlBIQ8BMrqCd5SVZX8B4tAbDSmyDH1ATTwq4FKLmOT2TGlHhn85Dh0qE9BZAQUlOt4y4jz51h8+6pPvRgFI23yIO8JEv694UVibLNMVJd9jcIHunUeHTp+5cuKwdDxQbKl2YRTHBBQthWq1+ffO3/v2uxfm/xIuBUuQRocb01DLeWnMis/hLwSfz/CDJYgbOAMQJ7zy7y7Wv4NXby0pB0hgAhqB7m501MraupExaNpkmCr/abtu58v/ONWu4m3CjvIeJzu+WEBpf90gefD1ez7tkmJCGr5EJQeWyBS8rSxJHuCczqIe+WNvbHzsd/RJSH0AJ9e4j0m8CXnZi0qtFmRllYLCo04Z4hsk2fVy3InekcxEnn0Nhx7lzg3Fkgi+BbvxUS1l61h84YnOxK2XFSwUsxVlbK4CC3dSXyHd8Fz2k3BP5Ddh0jhG2uV7Ti1xJwVYg0QZgxfZTXibKXJN2Xn566qekv5iNW4IVorv2mCdlOAI1kiN6xZVGiqzFJyDNwiJvEfIH0dhDb5r/ARPV0rnrsqHiFzcLFm2yHErDtukI8O47BVws2FRBbvbBamHXiCK6z5ZffkleAWJOLm0aUDKAOBhR6UE+AJF8hCpjUs99bJNmT8+d04/8PRO9SB3lD+fu6xc9oW//vf3sUcvvlYFpCNe41a6MAL3ba5oOPfLNqgU4IuXN96UmLo0oe7cFNAvSP3s/oXX9mzVfhgmdcVIVvRe5Uz95fFr4QxaR+tgWRS7i1FsQ0+tg0D2wYt8zJtn/FFUKFg1DCFcfiNfFNaEwpZ7Qqa92fEJq1FpozXO7sLhgFAFXqX+hriRBZCd9noxdnLuoc9jp+ZU1FTDuqWiZ/TzqKZV3nWNzCPMnc21bpEcuz946egvnjl6IuzpuVeQOuiYpQ2pchf3gzduBG985M2LS7gYh+FKhyLLL735Z0s7dAncm7H5FOOxEXg21DO3LtgXSBX0GiE4ggP03k2GYPL9Kdyxyo+tSf9hxSXV/LIe1L2FkK3f9Wb36vyCLNybzS+XaLkaZzLxobgbNBOaR0lyyMsHnO6GFJy2Db2aIdtAi0HQ4oI3v3p1hiDLr715cWlDAgHu8JyPiB9TiF8jvsdqWH4Ch/shvt5z5VYzvjFt2+AYq5r9XwG19sH0qTe/e3X2I8sFb37rC+3Hr8el1B/UsOk0Dg8DVHOWtacl/H2/mupbQOIhiOCMN2tXpzqypL357i+Xg4/XOHsChzOCLIsaliFSNM1MV9kLe75fAbjXBzC1vsqPFO+nspZ4lc1f2rdj9RI/UK6t+OeFx/fiubamtedG35T9dvFncDO0s5mcafq7A9864nCWMaQNzapXcOT0I9DbBxJQQzhJm55SFPMQI0WB355WvZkcCom+1o8yA2n4GUE1AQ1RjWzvzHH8H83CP9f+O9J08KLsk8HH3bu2f/OK/uwbZwa2v/+3DyZ6b/zz+c9+etevboscfzt+6bz1977/AWgNoM07EgAA";
    }
    
    public static interface Anonymous$1 extends fabric.util.AbstractSet {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object key);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$1 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$1._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$1._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$1 {
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
                return (Anonymous$1) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$1._Proxy(this);
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
                fabric.util.TreeMap.Anonymous$1._Impl src =
                  (fabric.util.TreeMap.Anonymous$1._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$1._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$1._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$1._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$1.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$1._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$1._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$1._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$1._Static {
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
                    return new fabric.util.TreeMap.Anonymous$1._Static._Proxy(
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
    
    public static interface Anonymous$2 extends fabric.util.AbstractCollection {
        public fabric.util.TreeMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements Anonymous$2 {
            public fabric.util.TreeMap get$out$() {
                return ((fabric.util.TreeMap.Anonymous$2._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$2._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements Anonymous$2 {
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
                return (Anonymous$2) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.TreeMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap.Anonymous$2._Proxy(this);
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
                fabric.util.TreeMap.Anonymous$2._Impl src =
                  (fabric.util.TreeMap.Anonymous$2._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.TreeMap.Anonymous$2._Static {
                public _Proxy(fabric.util.TreeMap.Anonymous$2._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.TreeMap.Anonymous$2._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      TreeMap.
                      Anonymous$2.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.TreeMap.Anonymous$2._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.TreeMap.Anonymous$2._Static._Impl.class);
                    $instance = (fabric.util.TreeMap.Anonymous$2._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.TreeMap.Anonymous$2._Static {
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
                    return new fabric.util.TreeMap.Anonymous$2._Static._Proxy(
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
                        ((fabric.util.TreeMap.Anonymous$0)
                           new fabric.util.TreeMap.Anonymous$0._Impl(
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
                        ((fabric.util.TreeMap.Anonymous$1)
                           new fabric.util.TreeMap.Anonymous$1._Impl(
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
                        ((fabric.util.TreeMap.Anonymous$2)
                           new fabric.util.TreeMap.Anonymous$2._Impl(
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
                        fabric.worker.transaction.TransactionManager $tm560 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled563 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff561 = 1;
                        boolean $doBackoff562 = true;
                        boolean $retry556 = true;
                        boolean $keepReads557 = false;
                        $label554: for (boolean $commit555 = false; !$commit555;
                                        ) {
                            if ($backoffEnabled563) {
                                if ($doBackoff562) {
                                    if ($backoff561 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff561));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e558) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff561 < 5000) $backoff561 *= 2;
                                }
                                $doBackoff562 = $backoff561 <= 32 ||
                                                  !$doBackoff562;
                            }
                            $commit555 = true;
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
                                         RetryException $e558) {
                                    throw $e558;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e558) {
                                    throw $e558;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e558) {
                                    throw $e558;
                                }
                                catch (final Throwable $e558) {
                                    $tm560.getCurrentLog().checkRetrySignal();
                                    throw $e558;
                                }
                            }
                            catch (final fabric.worker.RetryException $e558) {
                                $commit555 = false;
                                continue $label554;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e558) {
                                $commit555 = false;
                                $retry556 = false;
                                $keepReads557 = $e558.keepReads;
                                if ($tm560.checkForStaleObjects()) {
                                    $retry556 = true;
                                    $keepReads557 = false;
                                    continue $label554;
                                }
                                fabric.common.TransactionID $currentTid559 =
                                  $tm560.getCurrentTid();
                                if ($e558.tid ==
                                      null ||
                                      !$e558.tid.isDescendantOf(
                                                   $currentTid559)) {
                                    throw $e558;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e558);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e558) {
                                $commit555 = false;
                                fabric.common.TransactionID $currentTid559 =
                                  $tm560.getCurrentTid();
                                if ($e558.tid.isDescendantOf($currentTid559))
                                    continue $label554;
                                if ($currentTid559.parent != null) {
                                    $retry556 = false;
                                    throw $e558;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e558) {
                                $commit555 = false;
                                if ($tm560.checkForStaleObjects())
                                    continue $label554;
                                $retry556 = false;
                                throw new fabric.worker.AbortException($e558);
                            }
                            finally {
                                if ($commit555) {
                                    fabric.common.TransactionID $currentTid559 =
                                      $tm560.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e558) {
                                        $commit555 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e558) {
                                        $commit555 = false;
                                        $retry556 = false;
                                        $keepReads557 = $e558.keepReads;
                                        if ($tm560.checkForStaleObjects()) {
                                            $retry556 = true;
                                            $keepReads557 = false;
                                            continue $label554;
                                        }
                                        if ($e558.tid ==
                                              null ||
                                              !$e558.tid.isDescendantOf(
                                                           $currentTid559))
                                            throw $e558;
                                        throw new fabric.worker.
                                                UserAbortException($e558);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e558) {
                                        $commit555 = false;
                                        $currentTid559 = $tm560.getCurrentTid();
                                        if ($currentTid559 != null) {
                                            if ($e558.tid.equals(
                                                            $currentTid559) ||
                                                  !$e558.tid.
                                                  isDescendantOf(
                                                    $currentTid559)) {
                                                throw $e558;
                                            }
                                        }
                                    }
                                } else if ($keepReads557) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit555) {
                                    {  }
                                    if ($retry556) { continue $label554; }
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
