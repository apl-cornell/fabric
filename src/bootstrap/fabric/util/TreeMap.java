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
        
        public static final byte[] $classHash = new byte[] { 113, -93, 0, -67,
        -86, -15, -44, -75, 91, -6, -91, -35, -70, -39, -124, 91, -22, 104, 101,
        -8, 46, -19, 91, 84, -124, 71, -96, 16, 37, 95, 1, 28 };
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
        
        public static final byte[] $classHash = new byte[] { 32, -86, -31, -77,
        16, -51, -50, -56, 40, -88, 112, 49, -60, -84, -86, 57, 69, -22, 29,
        -46, 77, -70, -128, -95, 20, 56, 68, 82, -18, -59, -21, -111 };
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
            
            public static final byte[] $classHash = new byte[] { 89, 48, 67,
            -125, -117, -76, 13, 116, -25, -86, -40, 105, -43, 32, -97, -46, 90,
            26, -38, -120, 24, -73, 125, 31, 81, -43, -127, 46, 3, 94, -83,
            -74 };
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
            
            public static final byte[] $classHash = new byte[] { 89, 48, 67,
            -125, -117, -76, 13, 116, -25, -86, -40, 105, -43, 32, -97, -46, 90,
            26, -38, -120, 24, -73, 125, 31, 81, -43, -127, 46, 3, 94, -83,
            -74 };
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
            
            public static final byte[] $classHash = new byte[] { -20, -50, -14,
            50, -101, -96, 32, -2, 58, 109, 33, 86, 30, 51, 31, 116, -1, 105,
            -114, -15, 85, 4, 7, 13, 20, -81, 106, -65, -79, 3, 87, -121 };
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
        
        public static final byte[] $classHash = new byte[] { -66, -124, 75, 13,
        -100, -27, 32, -124, 17, -89, 4, 49, -42, -25, 53, -92, 120, -86, 88,
        -84, -44, -104, -75, 20, -58, 8, -48, -42, -37, -105, 19, 92 };
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
        
        public static final byte[] $classHash = new byte[] { 89, 48, 67, -125,
        -117, -76, 13, 116, -25, -86, -40, 105, -43, 32, -97, -46, 90, 26, -38,
        -120, 24, -73, 125, 31, 81, -43, -127, 46, 3, 94, -83, -74 };
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
        
        public static final byte[] $classHash = new byte[] { 89, 48, 67, -125,
        -117, -76, 13, 116, -25, -86, -40, 105, -43, 32, -97, -46, 90, 26, -38,
        -120, 24, -73, 125, 31, 81, -43, -127, 46, 3, 94, -83, -74 };
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
        
        public static final byte[] $classHash = new byte[] { -20, -50, -14, 50,
        -101, -96, 32, -2, 58, 109, 33, 86, 30, 51, 31, 116, -1, 105, -114, -15,
        85, 4, 7, 13, 20, -81, 106, -65, -79, 3, 87, -121 };
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
                        fabric.worker.transaction.TransactionManager $tm841 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled844 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff842 = 1;
                        boolean $doBackoff843 = true;
                        boolean $retry837 = true;
                        boolean $keepReads838 = false;
                        $label835: for (boolean $commit836 = false; !$commit836;
                                        ) {
                            if ($backoffEnabled844) {
                                if ($doBackoff843) {
                                    if ($backoff842 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff842));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e839) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff842 < 5000) $backoff842 *= 2;
                                }
                                $doBackoff843 = $backoff842 <= 32 ||
                                                  !$doBackoff843;
                            }
                            $commit836 = true;
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
                                         RetryException $e839) {
                                    throw $e839;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e839) {
                                    throw $e839;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e839) {
                                    throw $e839;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e839) {
                                    throw $e839;
                                }
                                catch (final Throwable $e839) {
                                    $tm841.getCurrentLog().checkRetrySignal();
                                    throw $e839;
                                }
                            }
                            catch (final fabric.worker.RetryException $e839) {
                                $commit836 = false;
                                continue $label835;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e839) {
                                $commit836 = false;
                                $retry837 = false;
                                $keepReads838 = $e839.keepReads;
                                if ($tm841.checkForStaleObjects()) {
                                    $retry837 = true;
                                    $keepReads838 = false;
                                    continue $label835;
                                }
                                fabric.common.TransactionID $currentTid840 =
                                  $tm841.getCurrentTid();
                                if ($e839.tid ==
                                      null ||
                                      !$e839.tid.isDescendantOf(
                                                   $currentTid840)) {
                                    throw $e839;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e839);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e839) {
                                $commit836 = false;
                                fabric.common.TransactionID $currentTid840 =
                                  $tm841.getCurrentTid();
                                if ($e839.tid.isDescendantOf($currentTid840))
                                    continue $label835;
                                if ($currentTid840.parent != null) {
                                    $retry837 = false;
                                    throw $e839;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e839) {
                                $commit836 = false;
                                if ($tm841.checkForStaleObjects())
                                    continue $label835;
                                fabric.common.TransactionID $currentTid840 =
                                  $tm841.getCurrentTid();
                                if ($e839.tid.isDescendantOf($currentTid840)) {
                                    $retry837 = true;
                                }
                                else if ($currentTid840.parent != null) {
                                    $retry837 = false;
                                    throw $e839;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e839) {
                                $commit836 = false;
                                if ($tm841.checkForStaleObjects())
                                    continue $label835;
                                $retry837 = false;
                                throw new fabric.worker.AbortException($e839);
                            }
                            finally {
                                if ($commit836) {
                                    fabric.common.TransactionID $currentTid840 =
                                      $tm841.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e839) {
                                        $commit836 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e839) {
                                        $commit836 = false;
                                        $retry837 = false;
                                        $keepReads838 = $e839.keepReads;
                                        if ($tm841.checkForStaleObjects()) {
                                            $retry837 = true;
                                            $keepReads838 = false;
                                            continue $label835;
                                        }
                                        if ($e839.tid ==
                                              null ||
                                              !$e839.tid.isDescendantOf(
                                                           $currentTid840))
                                            throw $e839;
                                        throw new fabric.worker.
                                                UserAbortException($e839);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e839) {
                                        $commit836 = false;
                                        $currentTid840 = $tm841.getCurrentTid();
                                        if ($currentTid840 != null) {
                                            if ($e839.tid.equals(
                                                            $currentTid840) ||
                                                  !$e839.tid.
                                                  isDescendantOf(
                                                    $currentTid840)) {
                                                throw $e839;
                                            }
                                        }
                                    }
                                } else if ($keepReads838) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit836) {
                                    {  }
                                    if ($retry837) { continue $label835; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -66, -124, 75, 13,
    -100, -27, 32, -124, 17, -89, 4, 49, -42, -25, 53, -92, 120, -86, 88, -84,
    -44, -104, -75, 20, -58, 8, -48, -42, -37, -105, 19, 92 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
