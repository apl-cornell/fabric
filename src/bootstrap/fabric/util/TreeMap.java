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
        
        public static final byte[] $classHash = new byte[] { 120, -77, 110, 40,
        33, 57, 11, -109, 113, 79, 82, -7, -8, -127, 117, -88, 110, 8, -104,
        -101, -22, -110, -91, 92, -91, 46, 86, -49, 91, 57, -28, 103 };
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
        
        public static final byte[] $classHash = new byte[] { -80, 94, 19, 3, 89,
        -120, 9, -73, -67, 90, 67, 106, 60, -14, 41, 62, 113, 3, 115, 83, 58,
        78, 64, -93, 40, -21, -123, -105, -121, 125, 54, -27 };
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
            
            public static final byte[] $classHash = new byte[] { 32, 50, -105,
            -77, 81, 113, 110, 39, -12, 69, -35, 116, -65, 21, 52, -12, -61,
            -13, -7, 102, -97, 71, -59, 68, -18, 74, 123, 71, -108, -48, 41,
            58 };
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
            
            public static final byte[] $classHash = new byte[] { 32, 50, -105,
            -77, 81, 113, 110, 39, -12, 69, -35, 116, -65, 21, 52, -12, -61,
            -13, -7, 102, -97, 71, -59, 68, -18, 74, 123, 71, -108, -48, 41,
            58 };
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
            
            public static final byte[] $classHash = new byte[] { 116, -3, 17,
            62, -1, -103, 39, -4, 45, 7, 126, 5, 111, -51, -53, 65, -50, -110,
            57, 23, -26, 37, -82, 12, -28, -16, 114, -97, 37, 105, -62, -98 };
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
        
        public static final byte[] $classHash = new byte[] { 16, -1, -112, -111,
        73, 43, -76, 17, 10, -52, 36, -124, -111, 74, 112, 84, -79, -6, 125,
        108, -78, 1, 101, 117, -39, -108, -88, -37, -48, -105, 14, 65 };
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
        
        public static final byte[] $classHash = new byte[] { 32, 50, -105, -77,
        81, 113, 110, 39, -12, 69, -35, 116, -65, 21, 52, -12, -61, -13, -7,
        102, -97, 71, -59, 68, -18, 74, 123, 71, -108, -48, 41, 58 };
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
        
        public static final byte[] $classHash = new byte[] { 32, 50, -105, -77,
        81, 113, 110, 39, -12, 69, -35, 116, -65, 21, 52, -12, -61, -13, -7,
        102, -97, 71, -59, 68, -18, 74, 123, 71, -108, -48, 41, 58 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1YfWwUxxV/d7bvbGOwMRiCAWPMlciE3ImPpiVOo8IB5pIjODZOFdPmOrc7Zy/e211m5+wzKS2tFIEiharFISQNJGqJmhAnUVul/SNCRWrTBqXKl6p+SUmJVNRUlEpRkn5ISembmb2v9fkCUi3dzNzMe2/e52/eeeYKNLgMejIkbZhRPuVQN7qTpBPJAcJcqsdN4rp7cTelzatPnHjvh3pXEIJJaNGIZVuGRsyU5XJYkNxPJkjMojw2PJjo2wdNmmDcRdwxDsF92/IMuh3bnBo1be5dMkv+wzfFph+5r+3HddA6Aq2GNcQJN7S4bXGa5yPQkqXZNGXuVl2n+ggstCjVhygziGkcRELbGoF21xi1CM8x6g5S1zYnBGG7m3Mok3cWNoX6NqrNchq3GarfptTPccOMJQ2X9yUhlDGoqbsH4OtQn4SGjElGkXBJsmBFTEqM7RT7SN5soJosQzRaYKkfNyydwyo/R9HiyJ1IgKzhLOVjdvGqeovgBrQrlUxijcaGODOsUSRtsHN4C4fOOYUiUaNDtHEySlMcbvDTDagjpGqSbhEsHDr8ZFISxqzTF7OyaF2567Zj91u7rCAEUGedaqbQvxGZunxMgzRDGbU0qhhb1iVPkCXnjgYBkLjDR6xofva197+4vuv8K4pmeRWaPen9VOMp7Ux6wZsr4r1b6oQajY7tGiIVKiyXUR3wTvryDmb7kqJEcRgtHJ4f/NW9h8/Sy0FoTkBIs81cFrNqoWZnHcOkrJ9alBFO9QQ0UUuPy/MEhHGdNCyqdvdkMi7lCag35VbIlt/RRRkUIVwUxrVhZezC2iF8TK7zDgAsxw+EAQJvAXz+LpzPAmzGiPfHxuwsjaXNHJ3E9I7hhxKmjcWwbpmh3azZzlTMZVqM5SxuIKXaV8bvZZTuJk4UVXD+f6LyQuu2yUAAHbpKs3WaJi5Gx8uUbQMmFsMu29QpS2nmsXMJWHTuUZktTSLDXcxS6Y8ARniFHxvKeadz23a8/3zqVZVpgtdzFxaXUk1F0VMtshWBaSpr59zIFtSsRZRRFIEpisA0E8hH46cTz8psCbmyrIrCWlDYrY5JeMZm2TwEAtKyxZJfXoBBHkfwQHxo6R36yh1fPdpTh/npTNZjyARpxF8tJYxJ4IpgCaS01iPv/fOFE4fsUt1wiMwq59mcohx7/G5itkZ1hLuS+HXd5MXUuUORoICSJkQ5TjAPMYG6/HdUlGVfAeKENxqSME/4gJjiqIBLzXyM2ZOlHRn+BWJoV5kgnOVTUKLjF4acU3947W+b5LtRANLWMsQdoryvrHiFsFZZpgtLvhfBRbq3Tw4cf/jKkX3S8UixptqFETHGsWgJVqvNHnjlwB///M6Z3wZLweIQdpgxgbWcl8YsvIp/Afz8V3xECYoNMSMQx73y7y7WvyOuXltSDpHARDRC3d3IsJW1dSNjkLRJRap83PqZDS/+/VibireJO8p7DNZ/uoDS/rJtcPjV+/7VJcUENPESlRxYIlPwtqgkeStjZErokf/mWysf/TU5hamP4OQaB6nEm4CXvUKpDg6LqhSUOOqUId4oyW6W4wbhHckM8uwWMfQod64oloT/LdgpHtVSto7EZh7vjN9+WcFCMVuFjNVVYOEeUlZIG89mPwr2hF4OQngE2uR7Tix+D0FYw0QZwRfZjXubSZhfcV75uqqnpK9YjSv8lVJ2rb9OSnCEa0Et1s2qNFRmKThHbwCE3gV4fRjX6LvwR+J0kXTu4nwA5OI2ybJGjmvF0CsdGRTLdRxvNiyiYPcmDvXYC0TEepOsvvwcvBxCTi5tGpgyCHiio1ICygIFeYzUyrmeetmmnPnW9Gl9z1Mb1IPcXvl87rBy2ed+98lvoicvXqgC0iGvcStdGML7Vs9qOHfLNqgU4IuXV26Jj18aVXeu8unnp35m98yF/rXad4NQV4zkrN6rkqmvMn7NjGLraO2tiGJ3MYqtwlPLMJAz+CI/5c3fK4+iQsGqYQiI5fZ8UVijELbAE/KYN0+XCatRacM1zr4khj1cFXiV+htgRhZBdsLrxejR6QevRo9Nq6iphnXNrJ6xnEc1rfKu+TKPRO6srnWL5Nj51xcOvfT0oSNBT89dHOqwY5Y2JCtd3Ife+BHAZ+erefO7c7hYDIOzHSpYLnrzn+Z26By4N2mzccqiQ/hsqGdumb8vkCroNUKwXwzYezcanMr3p3DH4nJsTXiHElyruQEbwcBP0Q1PevP09blBsBz35oeuLa/cGmc5MWBb3aCZ2CtKknu98Ivpy5hxE7ahVzOkF7V4CbW46s2Xrs8QwfIXb37nmuPZ7vlaAHxUAXyNcH6jhuUPiOEghtN7ndxqxofTto2Oseay/zzALSlv3nF99guW7d58+6faL74ellK/XcOm74jhQURmRrP2hES7I9VUX4MSLwB8brk3h69PdcES8ma4thw8WePsMTEc5zAvYlgGT5I0NV1lL+6VNf1ibxOi0vIqv0m8X8Za/Jf0zKU713fM8Xvkhln/q/D4nj/d2rj09PDvZXtd/NXbhN1rJmea5c1A2TrkMJoxpA1NqjVw5PQE6l2GCVhDYpI2nVIU38cYKQrx7QeqFStBBib60nJQ2ZrGXw1E49j/SCIpozPHxH9gZj5Y+u9Q496LsgtGl3Z3b3zkJ3cfsG78cMfb/Ocdmz/8xQf/yTzR//L2f9xxf//0G723/g+UzwbyGRIAAA==";
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
        
        public static final byte[] $classHash = new byte[] { 116, -3, 17, 62,
        -1, -103, 39, -4, 45, 7, 126, 5, 111, -51, -53, 65, -50, -110, 57, 23,
        -26, 37, -82, 12, -28, -16, 114, -97, 37, 105, -62, -98 };
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
                        fabric.worker.transaction.TransactionManager $tm829 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled832 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff830 = 1;
                        boolean $doBackoff831 = true;
                        boolean $retry825 = true;
                        boolean $keepReads826 = false;
                        $label823: for (boolean $commit824 = false; !$commit824;
                                        ) {
                            if ($backoffEnabled832) {
                                if ($doBackoff831) {
                                    if ($backoff830 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff830));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e827) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff830 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff830 =
                                          java.lang.Math.
                                            min(
                                              $backoff830 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff831 = $backoff830 <= 32 ||
                                                  !$doBackoff831;
                            }
                            $commit824 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.TreeMap._Static._Proxy.$instance.
                                  set$RED((int) -1);
                                fabric.util.TreeMap._Static._Proxy.$instance.
                                  set$BLACK((int) 1);
                            }
                            catch (final fabric.worker.RetryException $e827) {
                                $commit824 = false;
                                continue $label823;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e827) {
                                $commit824 = false;
                                $retry825 = false;
                                $keepReads826 = $e827.keepReads;
                                fabric.common.TransactionID $currentTid828 =
                                  $tm829.getCurrentTid();
                                if ($e827.tid ==
                                      null ||
                                      !$e827.tid.isDescendantOf(
                                                   $currentTid828)) {
                                    throw $e827;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e827);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e827) {
                                $commit824 = false;
                                fabric.common.TransactionID $currentTid828 =
                                  $tm829.getCurrentTid();
                                if ($e827.tid.isDescendantOf($currentTid828))
                                    continue $label823;
                                if ($currentTid828.parent != null) {
                                    $retry825 = false;
                                    throw $e827;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e827) {
                                $commit824 = false;
                                $retry825 = false;
                                if ($tm829.inNestedTxn()) {
                                    $keepReads826 = true;
                                }
                                throw $e827;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid828 =
                                  $tm829.getCurrentTid();
                                if ($commit824) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e827) {
                                        $commit824 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e827) {
                                        $commit824 = false;
                                        $retry825 = false;
                                        $keepReads826 = $e827.keepReads;
                                        if ($e827.tid ==
                                              null ||
                                              !$e827.tid.isDescendantOf(
                                                           $currentTid828))
                                            throw $e827;
                                        throw new fabric.worker.
                                                UserAbortException($e827);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e827) {
                                        $commit824 = false;
                                        $currentTid828 = $tm829.getCurrentTid();
                                        if ($currentTid828 != null) {
                                            if ($e827.tid.equals(
                                                            $currentTid828) ||
                                                  !$e827.tid.
                                                  isDescendantOf(
                                                    $currentTid828)) {
                                                throw $e827;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm829.inNestedTxn() &&
                                          $tm829.checkForStaleObjects()) {
                                        $retry825 = true;
                                        $keepReads826 = false;
                                    }
                                    if ($keepReads826) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e827) {
                                            $currentTid828 = $tm829.getCurrentTid();
                                            if ($currentTid828 != null &&
                                                  ($e827.tid.equals($currentTid828) || !$e827.tid.isDescendantOf($currentTid828))) {
                                                throw $e827;
                                            } else {
                                                $retry825 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit824) {
                                    {  }
                                    if ($retry825) { continue $label823; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 16, -1, -112, -111, 73,
    43, -76, 17, 10, -52, 36, -124, -111, 74, 112, 84, -79, -6, 125, 108, -78,
    1, 101, 117, -39, -108, -88, -37, -48, -105, 14, 65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
