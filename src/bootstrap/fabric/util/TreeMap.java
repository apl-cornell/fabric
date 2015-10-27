package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface TreeMap
  extends fabric.util.SortedMap, fabric.util.AbstractMap
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
    public static interface Node
      extends BasicMapEntry
    {
        
        public int get$color();
        
        public int set$color(int val);
        
        public int postInc$color();
        
        public int postDec$color();
        
        public fabric.util.TreeMap.Node get$left();
        
        public fabric.util.TreeMap.Node set$left(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$right();
        
        public fabric.util.TreeMap.Node set$right(fabric.util.TreeMap.Node val);
        
        public fabric.util.TreeMap.Node get$parent();
        
        public fabric.util.TreeMap.Node set$parent(
          fabric.util.TreeMap.Node val);
        
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
        extends fabric.util.AbstractMap.BasicMapEntry._Proxy implements Node
        {
            
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
            
            public native fabric.util.TreeMap.Node fabric$util$TreeMap$Node$(
              fabric.lang.Object arg1, fabric.lang.Object arg2, int arg3);
            
            public _Proxy(Node._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl implements Node
        {
            
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
            public native Node fabric$util$TreeMap$Node$(
              fabric.lang.Object key, fabric.lang.Object value, int color);
            
            public native fabric.lang.Object $initLabels();
            
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
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.color = in.readInt();
                this.left = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.right = (fabric.util.TreeMap.Node)
                               $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(),
                                        in,
                                        store,
                                        intraStoreRefs,
                                        interStoreRefs);
                this.parent = (fabric.util.TreeMap.Node)
                                $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(),
                                         in,
                                         store,
                                         intraStoreRefs,
                                         interStoreRefs);
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
              implements fabric.util.TreeMap.Node._Static
            {
                
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
                      _Impl impl =
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
              implements fabric.util.TreeMap.Node._Static
            {
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, long label, long accessLabel,
                             java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.Node._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
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
     * @throws ClassCastException if key is not comparable to elements in the
     map
     * @throws NullPointerException if key is null but the comparator does not
     *         tolerate nulls
     * @see #put(Object, Object)
     * @see #containsKey(Object)
     */
    public fabric.lang.Object get(fabric.lang.Object key);
    
    /**
     * Returns a view of this Map including all entries with keys less than
     * <code>toKey</code>. The returned map is backed by the original, so
     changes
     * in one appear in the other. The submap will throw an
     * {@link IllegalArgumentException} for any attempt to access or add an
     * element beyond the specified cutoff. The returned map does not include
     * the endpoint; if you want inclusion, pass the successor element.
     *
     * @param toKey the (exclusive) cutoff point
     * @return a view of the map less than the cutoff
     * @throws ClassCastException if <code>toKey</code> is not compatible with
     *         the comparator (or is not Comparable, for natural ordering)
     * @throws NullPointerException if toKey is null, but the comparator does
     not
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
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
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
    public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                        fabric.lang.Object toKey);
    
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
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
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
                                                              Node first,
                                                              Node max);
        
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
          implements TreeIterator
        {
            
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
            
            public native fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1);
            
            public native fabric.util.TreeMap.TreeIterator
              fabric$util$TreeMap$TreeIterator$(int arg1,
                                                fabric.util.TreeMap.Node arg2,
                                                fabric.util.TreeMap.Node arg3);
            
            public native boolean hasNext();
            
            public native fabric.lang.Object next();
            
            public native void remove();
            
            public _Proxy(TreeIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements TreeIterator
        {
            
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public int get$type() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.type;
            }
            
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
            
            /**
             * The type of this Iterator: {@link #KEYS}, {@link #VALUES},
             * or {@link #ENTRIES}.
             */
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
            
            /** The number of modifications to the backing Map that we know
             about. */
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
            
            public fabric.util.TreeMap.Node get$max() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.max;
            }
            
            public fabric.util.TreeMap.Node set$max(
              fabric.util.TreeMap.Node val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.max = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
             * The last node visible to this iterator. This is used when
             iterating
             * on a SubMap.
             */
            private Node max;
            
            /**
             * Construct a new TreeIterator with the supplied type.
             * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
             */
            public native TreeIterator fabric$util$TreeMap$TreeIterator$(
              int type);
            
            /**
             * Construct a new TreeIterator with the supplied type. Iteration
             will
             * be from "first" (inclusive) to "max" (exclusive).
             *
             * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
             * @param first where to start iteration, nil for empty iterator
             * @param max the cutoff for iteration, nil for all remaining nodes
             */
            public native TreeIterator fabric$util$TreeMap$TreeIterator$(
              int type, Node first, Node max);
            
            /**
             * Returns true if the Iterator has more elements.
             * @return true if there are more elements
             */
            public native boolean hasNext();
            
            /**
             * Returns the next element in the Iterator's sequential view.
             * @return the next element
             * @throws ConcurrentModificationException if the TreeMap was
             modified
             * @throws NoSuchElementException if there is none
             */
            public native fabric.lang.Object next();
            
            /**
             * Removes from the backing TreeMap the last element which was
             fetched
             * with the <code>next()</code> method.
             * @throws ConcurrentModificationException if the TreeMap was
             modified
             * @throws IllegalStateException if called when there is no last
             element
             */
            public native void remove();
            
            public native fabric.lang.Object $initLabels();
            
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
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.type = in.readInt();
                this.knownMod = in.readInt();
                this.last = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.next = (fabric.util.TreeMap.Node)
                              $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.max = (fabric.util.TreeMap.Node)
                             $readRef(fabric.util.TreeMap.Node._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(),
                                      in,
                                      store,
                                      intraStoreRefs,
                                      interStoreRefs);
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
              implements fabric.util.TreeMap.TreeIterator._Static
            {
                
                public _Proxy(fabric.util.TreeMap.TreeIterator._Static.
                                _Impl impl) {
                    super(impl);
                }
                
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
                      _Impl impl =
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
              implements fabric.util.TreeMap.TreeIterator._Static
            {
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, long label, long accessLabel,
                             java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in,
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
      extends fabric.util.SortedMap, fabric.util.AbstractMap
    {
        
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
        public SubMap fabric$util$TreeMap$SubMap$(fabric.lang.Object minKey,
                                                  fabric.lang.Object maxKey);
        
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
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements SubMap
        {
            
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
            
            public native fabric.util.TreeMap.SubMap
              fabric$util$TreeMap$SubMap$(fabric.lang.Object arg1,
                                          fabric.lang.Object arg2);
            
            public native boolean keyInRange(fabric.lang.Object arg1);
            
            public native fabric.util.Comparator comparator();
            
            public native fabric.lang.Object firstKey();
            
            public native fabric.util.SortedMap headMap(
              fabric.lang.Object arg1);
            
            public native fabric.lang.Object lastKey();
            
            public native fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                       fabric.lang.Object arg2);
            
            public native fabric.util.SortedMap tailMap(
              fabric.lang.Object arg1);
            
            public _Proxy(SubMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements SubMap
        {
            
            public fabric.util.TreeMap get$out$() { return this.out$; }
            
            private fabric.util.TreeMap out$;
            
            public fabric.lang.Object get$minKey() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.minKey;
            }
            
            public fabric.lang.Object set$minKey(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.minKey = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
             * The lower range of this view, inclusive, or nil for unbounded.
             * Package visible for use by nested classes.
             */
            fabric.lang.Object minKey;
            
            public fabric.lang.Object get$maxKey() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.maxKey;
            }
            
            public fabric.lang.Object set$maxKey(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.maxKey = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
             * The upper range of this view, exclusive, or nil for unbounded.
             * Package visible for use by nested classes.
             */
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
             * Create a SubMap representing the elements between minKey
             (inclusive)
             * and maxKey (exclusive). If minKey is nil, SubMap has no lower
             bound
             * (headMap). If maxKey is nil, the SubMap has no upper bound
             (tailMap).
             *
             * @param minKey the lower bound
             * @param maxKey the upper bound
             * @throws IllegalArgumentException if minKey &gt; maxKey
             */
            public native SubMap fabric$util$TreeMap$SubMap$(
              fabric.lang.Object minKey, fabric.lang.Object maxKey);
            
            /**
             * Check if "key" is in within the range bounds for this SubMap. The
             * lower ("from") SubMap range is inclusive, and the upper ("to")
             bound
             * is exclusive. Package visible for use by nested classes.
             *
             * @param key the key to check
             * @return true if the key is in range
             */
            public native boolean keyInRange(fabric.lang.Object key);
            
            public native void clear();
            
            public native fabric.util.Comparator comparator();
            
            public native boolean containsKey(fabric.lang.Object key);
            
            public native boolean containsValue(fabric.lang.Object value);
            
            public native fabric.util.Set entrySet();
            
            public native fabric.lang.Object firstKey();
            
            public native fabric.lang.Object get(fabric.lang.Object key);
            
            public native fabric.util.SortedMap headMap(
              fabric.lang.Object toKey);
            
            public native fabric.util.Set keySet();
            
            public native fabric.lang.Object lastKey();
            
            public native fabric.lang.Object put(fabric.lang.Object key,
                                                 fabric.lang.Object value);
            
            public native fabric.lang.Object remove(fabric.lang.Object key);
            
            public native int size();
            
            public native fabric.util.SortedMap subMap(
              fabric.lang.Object fromKey, fabric.lang.Object toKey);
            
            public native fabric.util.SortedMap tailMap(
              fabric.lang.Object fromKey);
            
            public native fabric.util.Collection values();
            
            public native fabric.lang.Object $initLabels();
            
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
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.TreeMap)
                              $readRef(fabric.util.TreeMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.minKey = (fabric.lang.Object)
                                $readRef(fabric.lang.Object._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(),
                                         in,
                                         store,
                                         intraStoreRefs,
                                         interStoreRefs);
                this.maxKey = (fabric.lang.Object)
                                $readRef(fabric.lang.Object._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(),
                                         in,
                                         store,
                                         intraStoreRefs,
                                         interStoreRefs);
                this.entries = (fabric.util.Set)
                                 $readRef(fabric.util.Set._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(),
                                          in,
                                          store,
                                          intraStoreRefs,
                                          interStoreRefs);
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
              implements fabric.util.TreeMap.SubMap._Static
            {
                
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
                      _Impl impl =
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
              implements fabric.util.TreeMap.SubMap._Static
            {
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, long label, long accessLabel,
                             java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, label, accessLabel, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.TreeMap.SubMap._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.TreeMap
    {
        
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
        
        public native fabric.util.TreeMap fabric$util$TreeMap$();
        
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator arg1);
        
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Map arg1);
        
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap arg1);
        
        public native fabric.util.Comparator comparator();
        
        public native fabric.lang.Object firstKey();
        
        public native fabric.util.SortedMap headMap(fabric.lang.Object arg1);
        
        public native fabric.lang.Object lastKey();
        
        public native fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2);
        
        public native fabric.util.SortedMap tailMap(fabric.lang.Object arg1);
        
        public final native int compare(fabric.lang.Object arg1,
                                        fabric.lang.Object arg2);
        
        public final native fabric.util.TreeMap.Node firstNode();
        
        public final native fabric.util.TreeMap.Node getNode(
          fabric.lang.Object arg1);
        
        public final native fabric.util.TreeMap.Node highestLessThan(
          fabric.lang.Object arg1);
        
        public final native fabric.util.TreeMap.Node lowestGreaterThan(
          fabric.lang.Object arg1, boolean arg2);
        
        public final native void putKeysLinear(fabric.util.Iterator arg1,
                                               int arg2);
        
        public final native void removeNode(fabric.util.TreeMap.Node arg1);
        
        public final native fabric.util.TreeMap.Node successor(
          fabric.util.TreeMap.Node arg1);
        
        public _Proxy(TreeMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.TreeMap
    {
        
        public fabric.util.TreeMap.Node get$nil() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.nil;
        }
        
        public fabric.util.TreeMap.Node set$nil(fabric.util.TreeMap.Node val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.nil = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * Sentinal node, used to avoid null checks for corner cases and make
         the
         * delete rebalance code simpler. The rebalance code must never assign
         * the parent, left, or right of nil, but may safely reassign the color
         * to be black. This object must never be used as a key in a TreeMap, or
         * it will break bounds checking of a SubMap.
         */
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
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
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
        
        /**
         * This TreeMap's comparator, or null for natural ordering.
         * Package visible for use by nested classes.
         * @serial the comparator ordering this tree, or null
         */
        fabric.util.Comparator comparator;
        
        /**
         * Instantiate a new TreeMap with no elements, using the keys' natural
         * ordering to sort. All entries in the map must have a key which
         implements
         * Comparable, and which are <i>mutually comparable</i>, otherwise map
         * operations may throw a {@link ClassCastException}. Attempts to use
         * a null key will throw a {@link NullPointerException}.
         *
         * @see Comparable
         */
        public native fabric.util.TreeMap fabric$util$TreeMap$();
        
        /**
         * Instantiate a new TreeMap with no elements, using the provided
         comparator
         * to sort. All entries in the map must have keys which are mutually
         * comparable by the Comparator, otherwise map operations may throw a
         * {@link ClassCastException}.
         *
         * @param c the sort order for the keys of this map, or null
         *        for the natural order
         */
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Comparator c);
        
        /**
         * Instantiate a new TreeMap, initializing it with all of the elements
         in
         * the provided Map.  The elements will be sorted using the natural
         * ordering of the keys. This algorithm runs in n*log(n) time. All
         entries
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
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.Map map);
        
        /**
         * Instantiate a new TreeMap, initializing it with all of the elements
         in
         * the provided SortedMap.  The elements will be sorted using the same
         * comparator as in the provided SortedMap. This runs in linear time.
         *
         * @param sm a SortedMap, whose entries will be put into this TreeMap
         * @throws NullPointerException if sm is null
         */
        public native fabric.util.TreeMap fabric$util$TreeMap$(
          fabric.util.SortedMap sm);
        
        /**
         * Clears the Map so it has no keys. This is O(1).
         */
        public native void clear();
        
        /**
         * Return the comparator used to sort this map, or null if it is by
         * natural order.
         *
         * @return the map's comparator
         */
        public native fabric.util.Comparator comparator();
        
        /**
         * Returns true if the map contains a mapping for the given key.
         *
         * @param key the key to look for
         * @return true if the key has a mapping
         * @throws ClassCastException if key is not comparable to map elements
         * @throws NullPointerException if key is null and the comparator is not
         *         tolerant of nulls
         */
        public native boolean containsKey(fabric.lang.Object key);
        
        /**
         * Returns true if the map contains at least one mapping to the given
         value.
         * This requires linear time.
         *
         * @param value the value to look for
         * @return true if the value appears in a mapping
         */
        public native boolean containsValue(fabric.lang.Object value);
        
        /**
         * Returns a "set view" of this TreeMap's entries. The set is backed by
         * the TreeMap, so changes in one show up in the other.  The set
         supports
         * element removal, but not element addition.<p>
         *
         * Note that the iterators for all three views, from keySet(),
         entrySet(),
         * and values(), traverse the TreeMap in sorted sequence.
         *
         * @return a set view of the entries
         * @see #keySet()
         * @see #values()
         * @see Map.Entry
         */
        public native fabric.util.Set entrySet();
        
        /**
         * Returns the first (lowest) key in the map.
         *
         * @return the first key
         * @throws NoSuchElementException if the map is empty
         */
        public native fabric.lang.Object firstKey();
        
        /**
         * Return the value in this TreeMap associated with the supplied key,
         * or <code>null</code> if the key maps to nothing.  NOTE: Since the
         value
         * could also be null, you must use containsKey to see if this key
         * actually maps to something.
         *
         * @param key the key for which to fetch an associated value
         * @return what the key maps to, if present
         * @throws ClassCastException if key is not comparable to elements in
         the map
         * @throws NullPointerException if key is null but the comparator does
         not
         *         tolerate nulls
         * @see #put(Object, Object)
         * @see #containsKey(Object)
         */
        public native fabric.lang.Object get(fabric.lang.Object key);
        
        /**
         * Returns a view of this Map including all entries with keys less than
         * <code>toKey</code>. The returned map is backed by the original, so
         changes
         * in one appear in the other. The submap will throw an
         * {@link IllegalArgumentException} for any attempt to access or add an
         * element beyond the specified cutoff. The returned map does not
         include
         * the endpoint; if you want inclusion, pass the successor element.
         *
         * @param toKey the (exclusive) cutoff point
         * @return a view of the map less than the cutoff
         * @throws ClassCastException if <code>toKey</code> is not compatible
         with
         *         the comparator (or is not Comparable, for natural ordering)
         * @throws NullPointerException if toKey is null, but the comparator
         does not
         *         tolerate null elements
         */
        public native fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        /**
         * Returns a "set view" of this TreeMap's keys. The set is backed by the
         * TreeMap, so changes in one show up in the other.  The set supports
         * element removal, but not element addition.
         *
         * @return a set view of the keys
         * @see #values()
         * @see #entrySet()
         */
        public native fabric.util.Set keySet();
        
        /**
         * Returns the last (highest) key in the map.
         *
         * @return the last key
         * @throws NoSuchElementException if the map is empty
         */
        public native fabric.lang.Object lastKey();
        
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
         * @throws ClassCastException if key is not comparable to current map
         keys
         * @throws NullPointerException if key is null, but the comparator does
         *         not tolerate nulls
         * @see #get(Object)
         * @see Object#equals(Object)
         */
        public native fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        /**
         * Copies all elements of the given map into this TreeMap.  If this map
         * already has a mapping for a key, the new mapping replaces the current
         * one.
         *
         * @param m the map to be added
         * @throws ClassCastException if a key in m is not comparable with keys
         *         in the map
         * @throws NullPointerException if a key in m is null, and the
         comparator
         *         does not tolerate nulls
         */
        public native void putAll(fabric.util.Map m);
        
        /**
         * Removes from the TreeMap and returns the value which is mapped by the
         * supplied key. If the key maps to nothing, then the TreeMap remains
         * unchanged, and <code>null</code> is returned. NOTE: Since the value
         * could also be null, you must use containsKey to see if you are
         * actually removing a mapping.
         *
         * @param key the key used to locate the value to remove
         * @return whatever the key mapped to, if present
         * @throws ClassCastException if key is not comparable to current map
         keys
         * @throws NullPointerException if key is null, but the comparator does
         *         not tolerate nulls
         */
        public native fabric.lang.Object remove(fabric.lang.Object key);
        
        /**
         * Returns the number of key-value mappings currently in this Map.
         *
         * @return the size
         */
        public native int size();
        
        /**
         * Returns a view of this Map including all entries with keys greater or
         * equal to <code>fromKey</code> and less than <code>toKey</code> (a
         * half-open interval). The returned map is backed by the original, so
         * changes in one appear in the other. The submap will throw an
         * {@link IllegalArgumentException} for any attempt to access or add an
         * element beyond the specified cutoffs. The returned map includes the
         low
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
        public native fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                                   fabric.lang.Object toKey);
        
        /**
         * Returns a view of this Map including all entries with keys greater or
         * equal to <code>fromKey</code>. The returned map is backed by the
         * original, so changes in one appear in the other. The submap will
         throw an
         * {@link IllegalArgumentException} for any attempt to access or add an
         * element beyond the specified cutoff. The returned map includes the
         * endpoint; if you want to exclude it, pass in the successor element.
         *
         * @param fromKey the (inclusive) low cutoff point
         * @return a view of the map above the cutoff
         * @throws ClassCastException if <code>fromKey</code> is not compatible
         with
         *         the comparator (or is not Comparable, for natural ordering)
         * @throws NullPointerException if fromKey is null, but the comparator
         *         does not tolerate null elements
         */
        public native fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        /**
         * Returns a "collection view" (or "bag view") of this TreeMap's values.
         * The collection is backed by the TreeMap, so changes in one show up
         * in the other.  The collection supports element removal, but not
         element
         * addition.
         *
         * @return a bag view of the values
         * @see #keySet()
         * @see #entrySet()
         */
        public native fabric.util.Collection values();
        
        /**
         * Compares two elements by the set comparator, or by natural ordering.
         * Package visible for use by nested classes.
         *
         * @param o1 the first object
         * @param o2 the second object
         * @throws ClassCastException if o1 and o2 are not mutually comparable,
         *         or are not Comparable with natural ordering
         * @throws NullPointerException if o1 or o2 is null with natural
         ordering
         */
        public final native int compare(fabric.lang.Object o1,
                                        fabric.lang.Object o2);
        
        /**
         * Maintain red-black balance after deleting a node.
         *
         * @param node the child of the node just deleted, possibly nil
         * @param parent the parent of the node just deleted, never nil
         */
        private native void deleteFixup(Node node, Node parent);
        
        /**
         * Construct a perfectly balanced tree consisting of n "blank" nodes.
         This
         * permits a tree to be generated from pre-sorted input in linear time.
         *
         * @param count the number of blank nodes, non-negative
         */
        private native void fabricateTree(final int count);
        
        /**
         * Returns the first sorted node in the map, or nil if empty. Package
         * visible for use by nested classes.
         *
         * @return the first node
         */
        public final native Node firstNode();
        
        /**
         * Return the TreeMap.Node associated with key, or the nil node if no
         such
         * node exists in the tree. Package visible for use by nested classes.
         *
         * @param key the key to search for
         * @return the node where the key is found, or nil
         */
        public final native Node getNode(fabric.lang.Object key);
        
        /**
         * Find the "highest" node which is &lt; key. If key is nil, return last
         * node. Package visible for use by nested classes.
         *
         * @param key the upper bound, exclusive
         * @return the previous node
         */
        public final native Node highestLessThan(fabric.lang.Object key);
        
        /**
         * Maintain red-black balance after inserting a new node.
         *
         * @param n the newly inserted node
         */
        private native void insertFixup(Node n);
        
        /**
         * Returns the last sorted node in the map, or nil if empty.
         *
         * @return the last node
         */
        private native Node lastNode();
        
        /**
         * Find the "lowest" node which is &gt;= key. If key is nil, return
         either
         * nil or the first node, depending on the parameter first.
         * Package visible for use by nested classes.
         *
         * @param key the lower bound, inclusive
         * @param first true to return the first element instead of nil for nil
         key
         * @return the next node
         */
        public final native Node lowestGreaterThan(fabric.lang.Object key,
                                                   boolean first);
        
        /**
         * Return the node preceding the given one, or nil if there isn't one.
         *
         * @param node the current node, not nil
         * @return the prior node in sorted order
         */
        private native Node predecessor(Node node);
        
        /**
         * Construct a tree from sorted keys in linear time, with values of "".
         * Package visible for use by TreeSet.
         *
         * @param keys the iterator over the sorted keys
         * @param count the number of nodes to insert
         * @see TreeSet#TreeSet(SortedSet)
         */
        public final native void putKeysLinear(fabric.util.Iterator keys,
                                               int count);
        
        /**
         * Remove node from tree. This will increment modCount and decrement
         size.
         * Node must exist in the tree. Package visible for use by nested
         classes.
         *
         * @param node the node to remove
         */
        public final native void removeNode(Node node);
        
        /**
         * Rotate node n to the left.
         *
         * @param node the node to rotate
         */
        private native void rotateLeft(Node node);
        
        /**
         * Rotate node n to the right.
         *
         * @param node the node to rotate
         */
        private native void rotateRight(Node node);
        
        /**
         * Return the node following the given one, or nil if there isn't one.
         * Package visible for use by nested classes.
         *
         * @param node the current node, not nil
         * @return the next node in sorted order
         */
        public final native Node successor(Node node);
        
        public native fabric.lang.Object $initLabels();
        
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
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs, interStoreRefs);
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
                                           refTypes.next(),
                                         in,
                                         store,
                                         intraStoreRefs,
                                         interStoreRefs);
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
          implements fabric.util.TreeMap._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.TreeMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.TreeMap._Static._Impl.class);
                $instance = (fabric.util.TreeMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.TreeMap._Static
        {
            
            public int get$RED() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.RED;
            }
            
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
            
            /**
             * Color status of a node. Package visible for use by nested
             classes.
             */
            int RED;
            
            public int get$BLACK() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.BLACK;
            }
            
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
            
            /**
             * Color status of a node. Package visible for use by nested
             classes.
             */
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
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.RED = in.readInt();
                this.BLACK = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.TreeMap._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
