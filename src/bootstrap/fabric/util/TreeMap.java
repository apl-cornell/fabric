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
        
        public static final byte[] $classHash = new byte[] { 24, -7, 37, 126,
        115, 35, -122, 23, -48, 0, 89, -16, 78, 50, -92, -103, 50, 22, 42, 71,
        -23, 119, 22, 11, -63, 45, -57, 89, 101, 45, 86, -108 };
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
        
        public static final byte[] $classHash = new byte[] { 24, -98, -39, -5,
        -36, 111, 75, -62, -54, 9, 54, -5, -7, -105, 9, 83, -31, -104, -110, 78,
        -82, -7, -80, 58, 43, -81, -1, 15, 66, 24, -85, 59 };
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
            
            public static final byte[] $classHash = new byte[] { 127, -77, 115,
            -61, 32, -101, -50, 113, -46, 54, -115, 37, 36, 24, -60, -77, 37,
            25, -118, -4, -60, 2, -103, 24, -73, -32, -106, 32, 20, 125, -116,
            -59 };
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
            
            public static final byte[] $classHash = new byte[] { 127, -77, 115,
            -61, 32, -101, -50, 113, -46, 54, -115, 37, 36, 24, -60, -77, 37,
            25, -118, -4, -60, 2, -103, 24, -73, -32, -106, 32, 20, 125, -116,
            -59 };
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
            
            public static final byte[] $classHash = new byte[] { -111, 60, 94,
            57, 107, -23, -9, -66, 11, -88, 6, -52, 51, -124, 45, -72, -78,
            -105, 13, -17, 109, -103, 76, -30, 0, -3, 90, 29, 63, -114, 10,
            118 };
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
        
        public static final byte[] $classHash = new byte[] { 80, 23, 67, 41, 20,
        -22, 97, -51, -121, -120, -26, -75, 12, 109, 75, 108, -73, 21, -113, 55,
        -14, 95, -87, 68, -72, 15, -120, 86, 48, -123, -122, -35 };
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
        
        public static final byte[] $classHash = new byte[] { 127, -77, 115, -61,
        32, -101, -50, 113, -46, 54, -115, 37, 36, 24, -60, -77, 37, 25, -118,
        -4, -60, 2, -103, 24, -73, -32, -106, 32, 20, 125, -116, -59 };
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
        
        public static final byte[] $classHash = new byte[] { 127, -77, 115, -61,
        32, -101, -50, 113, -46, 54, -115, 37, 36, 24, -60, -77, 37, 25, -118,
        -4, -60, 2, -103, 24, -73, -32, -106, 32, 20, 125, -116, -59 };
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
        
        public static final byte[] $classHash = new byte[] { -111, 60, 94, 57,
        107, -23, -9, -66, 11, -88, 6, -52, 51, -124, 45, -72, -78, -105, 13,
        -17, 109, -103, 76, -30, 0, -3, 90, 29, 63, -114, 10, 118 };
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
    
    public static final byte[] $classHash = new byte[] { 80, 23, 67, 41, 20,
    -22, 97, -51, -121, -120, -26, -75, 12, 109, 75, 108, -73, 21, -113, 55,
    -14, 95, -87, 68, -72, 15, -120, 86, 48, -123, -122, -35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
