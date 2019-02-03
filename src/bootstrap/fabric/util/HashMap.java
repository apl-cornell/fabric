package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class provides a hashtable-backed implementation of the
 * Map interface.
 * <p>
 *
 * It uses a hash-bucket approach; that is, hash collisions are handled
 * by linking the new node off of the pre-existing node (or list of
 * nodes).  In this manner, techniques such as linear probing (which
 * can cause primary clustering) and rehashing (which does not fit very
 * well with Java's method of precomputing hash codes) are avoided.
 * <p>
 *
 * Under ideal circumstances (no collisions), HashMap offers O(1)
 * performance on most operations (<code>containsValue()</code> is,
 * of course, O(n)).  In the worst case (all keys map to the same
 * hash code -- very unlikely), most operations are O(n).
 * <p>
 *
 * HashMap is part of the JDK1.2 Collections API.  It differs from
 * Hashtable in that it accepts the null key and null values, and it
 * does not support "Enumeration views." Also, it is not synchronized;
 * if you plan to use it in multiple threads, consider using:<br>
 * <code>Map m = Collections.synchronizedMap(new HashMap(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * <code>ConcurrentModificationException</code> rather than exhibit
 * non-deterministic behavior.
 *
 * @author Jon Zeppieri
 * @author Jochen Hoenicke
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Object#hashCode()
 * @see Collection
 * @see Map
 * @see TreeMap
 * @see LinkedHashMap
 * @see IdentityHashMap
 * @see Hashtable
 * @since 1.2
 * @status updated to 1.4
 */
public interface HashMap extends fabric.util.Map, fabric.util.AbstractMap {
    public int get$threshold();
    
    public int set$threshold(int val);
    
    public int postInc$threshold();
    
    public int postDec$threshold();
    
    public float get$loadFactor();
    
    public float set$loadFactor(float val);
    
    public float postInc$loadFactor();
    
    public float postDec$loadFactor();
    
    public fabric.lang.arrays.ObjectArray get$buckets();
    
    public fabric.lang.arrays.ObjectArray set$buckets(
      fabric.lang.arrays.ObjectArray val);
    
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.util.Set get$entries();
    
    public fabric.util.Set set$entries(fabric.util.Set val);
    
    /**
   * Class to represent an entry in the hash table. Holds a single key-value
   * pair. Package visible for use by subclass.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface HashEntry extends BasicMapEntry {
        public fabric.util.HashMap.HashEntry get$next();
        
        public fabric.util.HashMap.HashEntry set$next(fabric.util.HashMap.HashEntry val);
        
        /**
     * Simple constructor.
     * @param key the key
     * @param value the value
     */
        public HashEntry fabric$util$HashMap$HashEntry$(fabric.lang.Object key,
                                                        fabric.lang.Object value);
        
        /**
     * Called when this entry is accessed via {@link #put(Object, Object)}.
     * This version does nothing, but in LinkedHashMap, it must do some
     * bookkeeping for access-traversal mode.
     */
        public void access();
        
        /**
     * Called when this entry is removed from the map. This version simply
     * returns the value, but in LinkedHashMap, it must also do bookkeeping.
     *
     * @return the value of this key as it is removed
     */
        public fabric.lang.Object cleanup();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractMap.BasicMapEntry._Proxy
          implements HashEntry {
            public fabric.util.HashMap.HashEntry get$next() {
                return ((fabric.util.HashMap.HashEntry._Impl) fetch()).get$next(
                                                                         );
            }
            
            public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val) {
                return ((fabric.util.HashMap.HashEntry._Impl) fetch()).set$next(
                                                                         val);
            }
            
            public fabric.util.HashMap.HashEntry fabric$util$HashMap$HashEntry$(
              fabric.lang.Object arg1, fabric.lang.Object arg2) {
                return ((fabric.util.HashMap.HashEntry) fetch()).
                  fabric$util$HashMap$HashEntry$(arg1, arg2);
            }
            
            public void access() {
                ((fabric.util.HashMap.HashEntry) fetch()).access();
            }
            
            public fabric.lang.Object cleanup() {
                return ((fabric.util.HashMap.HashEntry) fetch()).cleanup();
            }
            
            public _Proxy(HashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl implements HashEntry
        {
            public fabric.util.HashMap.HashEntry get$next() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.next;
            }
            
            public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.next = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * The next entry in the linked list. Package visible for use by subclass.
     */
            HashEntry next;
            
            /**
     * Simple constructor.
     * @param key the key
     * @param value the value
     */
            public HashEntry fabric$util$HashMap$HashEntry$(
              fabric.lang.Object key, fabric.lang.Object value) {
                fabric$util$AbstractMap$BasicMapEntry$(key, value);
                return (HashEntry) this.$getProxy();
            }
            
            /**
     * Called when this entry is accessed via {@link #put(Object, Object)}.
     * This version does nothing, but in LinkedHashMap, it must do some
     * bookkeeping for access-traversal mode.
     */
            public void access() {  }
            
            /**
     * Called when this entry is removed from the map. This version simply
     * returns the value, but in LinkedHashMap, it must also do bookkeeping.
     *
     * @return the value of this key as it is removed
     */
            public fabric.lang.Object cleanup() { return this.get$value(); }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (HashEntry) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap.HashEntry._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.next, refTypes, out, intraStoreRefs,
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
                this.next =
                  (fabric.util.HashMap.HashEntry)
                    $readRef(fabric.util.HashMap.HashEntry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.HashMap.HashEntry._Impl src =
                  (fabric.util.HashMap.HashEntry._Impl) other;
                this.next = src.next;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.HashEntry._Static {
                public _Proxy(fabric.util.HashMap.HashEntry._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.HashMap.HashEntry._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      HashMap.
                      HashEntry.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.HashMap.HashEntry._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.HashMap.HashEntry._Static._Impl.class);
                    $instance = (fabric.util.HashMap.HashEntry._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.HashEntry._Static {
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
                    return new fabric.util.HashMap.HashEntry._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 76, -53, -104, -9,
        63, -45, 112, 8, -50, 100, -121, 32, -104, 74, 53, -54, 111, -10, -75,
        81, 121, -67, -87, -40, -7, 70, 42, -66, -8, 66, -54, 23 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * Construct a new HashMap with the default capacity (11) and the default
   * load factor (0.75).
   */
    public fabric.util.HashMap fabric$util$HashMap$();
    
    /**
   * Construct a new HashMap from the given Map, with initial capacity
   * the greater of the size of <code>m</code> or the default of 11.
   * <p>
   *
   * Every element in Map m will be put into this new HashMap.
   *
   * @param m a Map whose key / value pairs will be put into the new HashMap.
   *        <b>NOTE: key / value pairs are not cloned in this constructor.</b>
   * @throws NullPointerException if m is null
   */
    public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map m);
    
    /**
   * Construct a new HashMap with a specific inital capacity and
   * default load factor of 0.75.
   *
   * @param initialCapacity the initial capacity of this HashMap (&gt;=0)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0)
   */
    public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity);
    
    /**
   * Construct a new HashMap with a specific inital capacity and load factor.
   *
   * @param initialCapacity the initial capacity (&gt;=0)
   * @param loadFactor the load factor (&gt; 0, not NaN)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
   *                                     ! (loadFactor &gt; 0.0)
   */
    public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity, float loadFactor);
    
    /**
   * Returns the number of kay-value mappings currently in this Map.
   *
   * @return the size
   */
    public int size();
    
    /**
   * Returns true if there are no key-value mappings currently in this Map.
   *
   * @return <code>size() == 0</code>
   */
    public boolean isEmpty();
    
    /**
   * Return the value in this HashMap associated with the supplied key,
   * or <code>null</code> if the key maps to nothing.  NOTE: Since the value
   * could also be null, you must use containsKey to see if this key
   * actually maps to something.
   *
   * @param key the key for which to fetch an associated value
   * @return what the key maps to, if present
   * @see #put(Object, Object)
   * @see #containsKey(Object)
   */
    public fabric.lang.Object get(fabric.lang.Object key);
    
    /**
   * Returns true if the supplied object <code>equals()</code> a key
   * in this HashMap.
   *
   * @param key the key to search for in this HashMap
   * @return true if the key is in the table
   * @see #containsValue(Object)
   */
    public boolean containsKey(fabric.lang.Object key);
    
    /**
   * Puts the supplied value into the Map, mapped by the supplied key.
   * The value may be retrieved by any object which <code>equals()</code>
   * this key. NOTE: Since the prior value could also be null, you must
   * first use containsKey if you want to see if you are replacing the
   * key's mapping.
   *
   * @param key the key used to locate the value
   * @param value the value to be stored in the HashMap
   * @return the prior mapping of the key, or null if there was none
   * @see #get(Object)
   * @see Object#equals(Object)
   */
    public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
    
    /**
   * Copies all elements of the given map into this hashtable.  If this table
   * already has a mapping for a key, the new mapping replaces the current
   * one.
   *
   * @param m the map to be hashed into this
   */
    public void putAll(fabric.util.Map m);
    
    /**
   * Removes from the HashMap and returns the value which is mapped by the
   * supplied key. If the key maps to nothing, then the HashMap remains
   * unchanged, and <code>null</code> is returned. NOTE: Since the value
   * could also be null, you must use containsKey to see if you are
   * actually removing a mapping.
   *
   * @param key the key used to locate the value to remove
   * @return whatever the key mapped to, if present
   */
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    /**
   * Clears the Map so it has no keys. This is O(1).
   */
    public void clear();
    
    /**
   * Returns true if this HashMap contains a value <code>o</code>, such that
   * <code>o.equals(value)</code>.
   *
   * @param value the value to search for in this HashMap
   * @return true if at least one key maps to the value
   * @see #containsKey(Object)
   */
    public boolean containsValue(fabric.lang.Object value);
    
    /**
   * Returns a "set view" of this HashMap's keys. The set is backed by the
   * HashMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.
   *
   * @return a set view of the keys
   * @see #values()
   * @see #entrySet()
   */
    public fabric.util.Set keySet();
    
    /**
   * Returns a "collection view" (or "bag view") of this HashMap's values.
   * The collection is backed by the HashMap, so changes in one show up
   * in the other.  The collection supports element removal, but not element
   * addition.
   *
   * @return a bag view of the values
   * @see #keySet()
   * @see #entrySet()
   */
    public fabric.util.Collection values();
    
    /**
   * Returns a "set view" of this HashMap's entries. The set is backed by
   * the HashMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.<p>
   *
   * Note that the iterators for all three views, from keySet(), entrySet(),
   * and values(), traverse the HashMap in the same sequence.
   *
   * @return a set view of the entries
   * @see #keySet()
   * @see #values()
   * @see Map.Entry
   */
    public fabric.util.Set entrySet();
    
    /**
   * Helper method for put, that creates and adds a new Entry.  This is
   * overridden in LinkedHashMap for bookkeeping purposes.
   *
   * @param key the key of the new Entry
   * @param value the value
   * @param idx the index in buckets where the new Entry belongs
   * @param callRemove whether to call the removeEldestEntry method
   * @see #put(Object, Object)
   */
    public void addEntry(
      fabric.lang.Object key, fabric.lang.Object value, int idx, boolean callRemove);
    
    /**
   * Helper method for entrySet(), which matches both key and value
   * simultaneously.
   *
   * @param o the entry to match
   * @return the matching entry, if found, or null
   * @see #entrySet()
   */
    public HashEntry getEntry(fabric.lang.Object o);
    
    /**
   * Helper method that returns an index in the buckets array for `key'
   * based on its hashCode().  Package visible for use by subclasses.
   *
   * @param key the key
   * @return the bucket number
   */
    public int hash(fabric.lang.Object key);
    
    /**
   * Generates a parameterized iterator.  Must be overrideable, since
   * LinkedHashMap iterates in a different order.
   *
   * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
   * @return the appropriate iterator
   */
    public fabric.util.Iterator iterator(fabric.worker.Store store, int type);
    
    /**
   * A simplified, more efficient internal implementation of putAll(). clone() 
   * should not call putAll or put, in order to be compatible with the JDK 
   * implementation with respect to subclasses.
   *
   * @param m the map to initialize this from
   */
    public void putAllInternal(fabric.util.Map m);
    
    /**
   * Iterate over HashMap's entries.
   * This implementation is parameterized to give a sequential view of
   * keys, values, or entries.
   *
   * @author Jon Zeppieri
   */
    public static interface HashIterator
      extends fabric.util.Iterator, fabric.lang.Object {
        public fabric.util.HashMap get$out$();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$count();
        
        public int set$count(int val);
        
        public int postInc$count();
        
        public int postDec$count();
        
        public int get$idx();
        
        public int set$idx(int val);
        
        public int postInc$idx();
        
        public int postDec$idx();
        
        public fabric.util.HashMap.HashEntry get$last();
        
        public fabric.util.HashMap.HashEntry set$last(
          fabric.util.HashMap.HashEntry val);
        
        public fabric.util.HashMap.HashEntry get$next();
        
        public fabric.util.HashMap.HashEntry set$next(fabric.util.HashMap.HashEntry val);
        
        /**
     * Construct a new HashIterator with the supplied type.
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     */
        public HashIterator fabric$util$HashMap$HashIterator$(int type);
        
        /**
     * Returns true if the Iterator has more elements.
     * @return true if there are more elements
     */
        public boolean hasNext();
        
        /**
     * Returns the next element in the Iterator's sequential view.
     * @return the next element
     * @throws ConcurrentModificationException if the HashMap was modified
     * @throws NoSuchElementException if there is none
     */
        public fabric.lang.Object next();
        
        /**
     * Removes from the backing HashMap the last element which was fetched
     * with the <code>next()</code> method.
     * @throws ConcurrentModificationException if the HashMap was modified
     * @throws IllegalStateException if called when there is no last element
     */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements HashIterator {
            public fabric.util.HashMap get$out$() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$out$();
            }
            
            public int get$type() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$type();
            }
            
            public int set$type(int val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$type(val);
            }
            
            public int postInc$type() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postInc$type();
            }
            
            public int postDec$type() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postDec$type();
            }
            
            public int get$knownMod() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postDec$knownMod();
            }
            
            public int get$count() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$count();
            }
            
            public int set$count(int val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$count(val);
            }
            
            public int postInc$count() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postInc$count();
            }
            
            public int postDec$count() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postDec$count();
            }
            
            public int get$idx() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$idx();
            }
            
            public int set$idx(int val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$idx(val);
            }
            
            public int postInc$idx() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postInc$idx();
            }
            
            public int postDec$idx() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  postDec$idx();
            }
            
            public fabric.util.HashMap.HashEntry get$last() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$last();
            }
            
            public fabric.util.HashMap.HashEntry set$last(
              fabric.util.HashMap.HashEntry val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$last(val);
            }
            
            public fabric.util.HashMap.HashEntry get$next() {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  get$next();
            }
            
            public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val) {
                return ((fabric.util.HashMap.HashIterator._Impl) fetch()).
                  set$next(val);
            }
            
            public fabric.util.HashMap.HashIterator
              fabric$util$HashMap$HashIterator$(int arg1) {
                return ((fabric.util.HashMap.HashIterator) fetch()).
                  fabric$util$HashMap$HashIterator$(arg1);
            }
            
            public boolean hasNext() {
                return ((fabric.util.HashMap.HashIterator) fetch()).hasNext();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.HashMap.HashIterator) fetch()).next();
            }
            
            public void remove() {
                ((fabric.util.HashMap.HashIterator) fetch()).remove();
            }
            
            public _Proxy(HashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements HashIterator {
            public fabric.util.HashMap get$out$() { return this.out$; }
            
            private fabric.util.HashMap out$;
            
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
            
            /**
     * The number of modifications to the backing HashMap that we know about.
     */
            private int knownMod;
            
            public int get$count() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.count;
            }
            
            public int set$count(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.count = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$count() {
                int tmp = this.get$count();
                this.set$count((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$count() {
                int tmp = this.get$count();
                this.set$count((int) (tmp - 1));
                return tmp;
            }
            
            /** The number of elements remaining to be returned by next(). */
            private int count;
            
            public int get$idx() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.idx;
            }
            
            public int set$idx(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.idx = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$idx() {
                int tmp = this.get$idx();
                this.set$idx((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$idx() {
                int tmp = this.get$idx();
                this.set$idx((int) (tmp - 1));
                return tmp;
            }
            
            /** Current index in the physical hash table. */
            private int idx;
            
            public fabric.util.HashMap.HashEntry get$last() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.last;
            }
            
            public fabric.util.HashMap.HashEntry set$last(
              fabric.util.HashMap.HashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.last = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The last Entry returned by a next() call. */
            private HashEntry last;
            
            public fabric.util.HashMap.HashEntry get$next() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.next;
            }
            
            public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.next = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * The next entry that should be returned by next(). It is set to something
     * if we're iterating through a bucket that contains multiple linked
     * entries. It is null if next() needs to find a new bucket.
     */
            private HashEntry next;
            
            /**
     * Construct a new HashIterator with the supplied type.
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     */
            public HashIterator fabric$util$HashMap$HashIterator$(int type) {
                this.set$type((int) type);
                fabric$lang$Object$();
                this.set$knownMod((int) this.get$out$().get$modCount());
                this.set$count((int) this.get$out$().get$size());
                this.set$idx((int) this.get$out$().get$buckets().get$length());
                return (HashIterator) this.$getProxy();
            }
            
            /**
     * Returns true if the Iterator has more elements.
     * @return true if there are more elements
     */
            public boolean hasNext() { return this.get$count() > 0; }
            
            /**
     * Returns the next element in the Iterator's sequential view.
     * @return the next element
     * @throws ConcurrentModificationException if the HashMap was modified
     * @throws NoSuchElementException if there is none
     */
            public fabric.lang.Object next() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
                if (this.get$count() == 0)
                    throw new fabric.util.NoSuchElementException();
                this.postDec$count();
                HashEntry e = this.get$next();
                while (fabric.lang.Object._Proxy.idEquals(e, null))
                    e =
                      (HashEntry)
                        this.get$out$().get$buckets().get(
                                                        this.set$idx(
                                                               this.get$idx() -
                                                                   1));
                this.set$next(e.get$next());
                this.set$last(e);
                if (this.get$type() ==
                      fabric.util.AbstractMap._Static._Proxy.$instance.
                      get$VALUES())
                    return e.get$value();
                if (this.get$type() ==
                      fabric.util.AbstractMap._Static._Proxy.$instance.get$KEYS(
                                                                         ))
                    return e.get$key();
                return e;
            }
            
            /**
     * Removes from the backing HashMap the last element which was fetched
     * with the <code>next()</code> method.
     * @throws ConcurrentModificationException if the HashMap was modified
     * @throws IllegalStateException if called when there is no last element
     */
            public void remove() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
                if (fabric.lang.Object._Proxy.idEquals(this.get$last(), null))
                    throw new java.lang.IllegalStateException();
                this.get$out$().remove(this.get$last().get$key());
                this.set$last(null);
                this.postInc$knownMod();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (HashIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.HashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap.HashIterator._Proxy(this);
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
                out.writeInt(this.count);
                out.writeInt(this.idx);
                $writeRef($getStore(), this.last, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.next, refTypes, out, intraStoreRefs,
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
                this.out$ = (fabric.util.HashMap)
                              $readRef(fabric.util.HashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.type = in.readInt();
                this.knownMod = in.readInt();
                this.count = in.readInt();
                this.idx = in.readInt();
                this.last =
                  (fabric.util.HashMap.HashEntry)
                    $readRef(fabric.util.HashMap.HashEntry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.next =
                  (fabric.util.HashMap.HashEntry)
                    $readRef(fabric.util.HashMap.HashEntry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.HashMap.HashIterator._Impl src =
                  (fabric.util.HashMap.HashIterator._Impl) other;
                this.out$ = src.out$;
                this.type = src.type;
                this.knownMod = src.knownMod;
                this.count = src.count;
                this.idx = src.idx;
                this.last = src.last;
                this.next = src.next;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.HashIterator._Static {
                public _Proxy(fabric.util.HashMap.HashIterator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.HashMap.HashIterator._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      HashMap.
                      HashIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.HashMap.HashIterator._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.HashMap.HashIterator._Static._Impl.class);
                    $instance = (fabric.util.HashMap.HashIterator._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.HashIterator._Static {
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
                    return new fabric.util.HashMap.HashIterator._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -78, 54, 125, 126,
        15, 17, -73, 94, 119, 62, 117, 7, 43, -52, -121, 81, 57, -4, -56, 105,
        112, -8, -67, 62, 35, 21, 24, 1, -106, 85, -123, -45 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static interface Anonymous$5 extends fabric.util.AbstractSet {
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$5 {
            public fabric.util.HashMap get$out$() {
                return ((fabric.util.HashMap.Anonymous$5._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$5._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$5 {
            public fabric.util.HashMap get$out$() { return this.out$; }
            
            private fabric.util.HashMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return this.get$out$().
                  iterator(
                    store,
                    fabric.util.AbstractMap._Static._Proxy.$instance.get$KEYS(
                                                                       ));
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public boolean contains(fabric.lang.Object o) {
                return this.get$out$().containsKey(o);
            }
            
            public boolean remove(fabric.lang.Object o) {
                int oldsize = this.get$out$().get$size();
                this.get$out$().remove(o);
                return oldsize != this.get$out$().get$size();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$5) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap.Anonymous$5._Proxy(this);
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
                this.out$ = (fabric.util.HashMap)
                              $readRef(fabric.util.HashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.HashMap.Anonymous$5._Impl src =
                  (fabric.util.HashMap.Anonymous$5._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$5._Static {
                public _Proxy(fabric.util.HashMap.Anonymous$5._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.HashMap.Anonymous$5._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      HashMap.
                      Anonymous$5.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.HashMap.Anonymous$5._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.HashMap.Anonymous$5._Static._Impl.class);
                    $instance = (fabric.util.HashMap.Anonymous$5._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$5._Static {
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
                    return new fabric.util.HashMap.Anonymous$5._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 50, 15, 116, 31,
        98, -31, -79, 32, 100, -18, 41, -108, -22, -48, 30, -54, -101, 22, 83,
        64, 53, 99, -29, -48, -25, 53, 21, -10, -44, -63, -120, 47 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$6 extends fabric.util.AbstractCollection {
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements Anonymous$6 {
            public fabric.util.HashMap get$out$() {
                return ((fabric.util.HashMap.Anonymous$6._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$6._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements Anonymous$6 {
            public fabric.util.HashMap get$out$() { return this.out$; }
            
            private fabric.util.HashMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return this.get$out$().
                  iterator(
                    store,
                    fabric.util.AbstractMap._Static._Proxy.$instance.get$VALUES(
                                                                       ));
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$6) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap.Anonymous$6._Proxy(this);
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
                this.out$ = (fabric.util.HashMap)
                              $readRef(fabric.util.HashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.HashMap.Anonymous$6._Impl src =
                  (fabric.util.HashMap.Anonymous$6._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$6._Static {
                public _Proxy(fabric.util.HashMap.Anonymous$6._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.HashMap.Anonymous$6._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      HashMap.
                      Anonymous$6.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.HashMap.Anonymous$6._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.HashMap.Anonymous$6._Static._Impl.class);
                    $instance = (fabric.util.HashMap.Anonymous$6._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$6._Static {
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
                    return new fabric.util.HashMap.Anonymous$6._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -23, -7, 37, 114,
        -122, 111, 84, 113, -67, 111, 12, 18, 35, -68, 68, 70, 107, 114, -30,
        45, -63, 52, 116, 18, 37, -118, -72, -96, -126, -77, 64, -64 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$7 extends fabric.util.AbstractSet {
        public fabric.util.HashMap get$out$();
        
        public int size();
        
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public void clear();
        
        public boolean contains(fabric.lang.Object o);
        
        public boolean remove(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$7 {
            public fabric.util.HashMap get$out$() {
                return ((fabric.util.HashMap.Anonymous$7._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$7._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$7 {
            public fabric.util.HashMap get$out$() { return this.out$; }
            
            private fabric.util.HashMap out$;
            
            public int size() { return this.get$out$().get$size(); }
            
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return this.
                  get$out$().
                  iterator(
                    store,
                    fabric.util.AbstractMap._Static._Proxy.$instance.
                        get$ENTRIES());
            }
            
            public void clear() { this.get$out$().clear(); }
            
            public boolean contains(fabric.lang.Object o) {
                return !fabric.lang.Object._Proxy.idEquals(
                                                    this.get$out$().getEntry(o),
                                                    null);
            }
            
            public boolean remove(fabric.lang.Object o) {
                HashEntry e = this.get$out$().getEntry(o);
                if (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                    this.get$out$().remove(e.get$key());
                    return true;
                }
                return false;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$7) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.HashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap.Anonymous$7._Proxy(this);
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
                this.out$ = (fabric.util.HashMap)
                              $readRef(fabric.util.HashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.HashMap.Anonymous$7._Impl src =
                  (fabric.util.HashMap.Anonymous$7._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.HashMap.Anonymous$7._Static {
                public _Proxy(fabric.util.HashMap.Anonymous$7._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.HashMap.Anonymous$7._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      HashMap.
                      Anonymous$7.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.HashMap.Anonymous$7._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.HashMap.Anonymous$7._Static._Impl.class);
                    $instance = (fabric.util.HashMap.Anonymous$7._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.HashMap.Anonymous$7._Static {
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
                    return new fabric.util.HashMap.Anonymous$7._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 50, 15, 116, 31,
        98, -31, -79, 32, 100, -18, 41, -108, -22, -48, 30, -54, -101, 22, 83,
        64, 53, 99, -29, -48, -25, 53, 21, -10, -44, -63, -120, 47 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.HashMap {
        public int get$threshold() {
            return ((fabric.util.HashMap._Impl) fetch()).get$threshold();
        }
        
        public int set$threshold(int val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$threshold(val);
        }
        
        public int postInc$threshold() {
            return ((fabric.util.HashMap._Impl) fetch()).postInc$threshold();
        }
        
        public int postDec$threshold() {
            return ((fabric.util.HashMap._Impl) fetch()).postDec$threshold();
        }
        
        public float get$loadFactor() {
            return ((fabric.util.HashMap._Impl) fetch()).get$loadFactor();
        }
        
        public float set$loadFactor(float val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$loadFactor(val);
        }
        
        public float postInc$loadFactor() {
            return ((fabric.util.HashMap._Impl) fetch()).postInc$loadFactor();
        }
        
        public float postDec$loadFactor() {
            return ((fabric.util.HashMap._Impl) fetch()).postDec$loadFactor();
        }
        
        public fabric.lang.arrays.ObjectArray get$buckets() {
            return ((fabric.util.HashMap._Impl) fetch()).get$buckets();
        }
        
        public fabric.lang.arrays.ObjectArray set$buckets(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$buckets(val);
        }
        
        public int get$modCount() {
            return ((fabric.util.HashMap._Impl) fetch()).get$modCount();
        }
        
        public int set$modCount(int val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$modCount(val);
        }
        
        public int postInc$modCount() {
            return ((fabric.util.HashMap._Impl) fetch()).postInc$modCount();
        }
        
        public int postDec$modCount() {
            return ((fabric.util.HashMap._Impl) fetch()).postDec$modCount();
        }
        
        public int get$size() {
            return ((fabric.util.HashMap._Impl) fetch()).get$size();
        }
        
        public int set$size(int val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$size(val);
        }
        
        public int postInc$size() {
            return ((fabric.util.HashMap._Impl) fetch()).postInc$size();
        }
        
        public int postDec$size() {
            return ((fabric.util.HashMap._Impl) fetch()).postDec$size();
        }
        
        public fabric.util.Set get$entries() {
            return ((fabric.util.HashMap._Impl) fetch()).get$entries();
        }
        
        public fabric.util.Set set$entries(fabric.util.Set val) {
            return ((fabric.util.HashMap._Impl) fetch()).set$entries(val);
        }
        
        public fabric.util.HashMap fabric$util$HashMap$() {
            return ((fabric.util.HashMap) fetch()).fabric$util$HashMap$();
        }
        
        public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map arg1) {
            return ((fabric.util.HashMap) fetch()).fabric$util$HashMap$(arg1);
        }
        
        public fabric.util.HashMap fabric$util$HashMap$(int arg1) {
            return ((fabric.util.HashMap) fetch()).fabric$util$HashMap$(arg1);
        }
        
        public fabric.util.HashMap fabric$util$HashMap$(int arg1, float arg2) {
            return ((fabric.util.HashMap) fetch()).fabric$util$HashMap$(arg1,
                                                                        arg2);
        }
        
        public void addEntry(fabric.lang.Object arg1, fabric.lang.Object arg2,
                             int arg3, boolean arg4) {
            ((fabric.util.HashMap) fetch()).addEntry(arg1, arg2, arg3, arg4);
        }
        
        public final fabric.util.HashMap.HashEntry getEntry(
          fabric.lang.Object arg1) {
            return ((fabric.util.HashMap) fetch()).getEntry(arg1);
        }
        
        public final int hash(fabric.lang.Object arg1) {
            return ((fabric.util.HashMap) fetch()).hash(arg1);
        }
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1,
                                             int arg2) {
            return ((fabric.util.HashMap) fetch()).iterator(arg1, arg2);
        }
        
        public void putAllInternal(fabric.util.Map arg1) {
            ((fabric.util.HashMap) fetch()).putAllInternal(arg1);
        }
        
        public _Proxy(HashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.HashMap {
        public int get$threshold() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.threshold;
        }
        
        public int set$threshold(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.threshold = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$threshold() {
            int tmp = this.get$threshold();
            this.set$threshold((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$threshold() {
            int tmp = this.get$threshold();
            this.set$threshold((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * The rounded product of the capacity and the load factor; when the number
   * of elements exceeds the threshold, the HashMap calls
   * <code>rehash()</code>.
   * @serial the threshold for rehashing
   */
        private int threshold;
        
        public float get$loadFactor() { return this.loadFactor; }
        
        public float set$loadFactor(float val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.loadFactor = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public float postInc$loadFactor() {
            float tmp = this.get$loadFactor();
            this.set$loadFactor((float) (tmp + 1));
            return tmp;
        }
        
        public float postDec$loadFactor() {
            float tmp = this.get$loadFactor();
            this.set$loadFactor((float) (tmp - 1));
            return tmp;
        }
        
        float loadFactor;
        
        public fabric.lang.arrays.ObjectArray get$buckets() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.buckets;
        }
        
        public fabric.lang.arrays.ObjectArray set$buckets(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.buckets = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * Array containing the actual key-value mappings.
   * Package visible for use by nested and subclasses.
   */
        fabric.lang.arrays.ObjectArray buckets;
        
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
   * Counts the number of modifications this HashMap has undergone, used
   * by Iterators to know when to throw ConcurrentModificationExceptions.
   * Package visible for use by nested and subclasses.
   */
        int modCount;
        
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
   * The size of this HashMap:  denotes the number of key-value pairs.
   * Package visible for use by nested and subclasses.
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
        private fabric.util.Set entries;
        
        /**
   * Construct a new HashMap with the default capacity (11) and the default
   * load factor (0.75).
   */
        public fabric.util.HashMap fabric$util$HashMap$() {
            fabric$util$HashMap$(
              fabric.util.HashMap._Static._Proxy.$instance.get$DEFAULT_CAPACITY(
                                                             ),
              fabric.util.HashMap._Static._Proxy.$instance.
                  get$DEFAULT_LOAD_FACTOR());
            return (fabric.util.HashMap) this.$getProxy();
        }
        
        /**
   * Construct a new HashMap from the given Map, with initial capacity
   * the greater of the size of <code>m</code> or the default of 11.
   * <p>
   *
   * Every element in Map m will be put into this new HashMap.
   *
   * @param m a Map whose key / value pairs will be put into the new HashMap.
   *        <b>NOTE: key / value pairs are not cloned in this constructor.</b>
   * @throws NullPointerException if m is null
   */
        public fabric.util.HashMap fabric$util$HashMap$(fabric.util.Map m) {
            fabric$util$HashMap$(
              java.lang.Math.
                  max(
                    m.size() * 2,
                    fabric.util.HashMap._Static._Proxy.$instance.
                        get$DEFAULT_CAPACITY()),
              fabric.util.HashMap._Static._Proxy.$instance.
                  get$DEFAULT_LOAD_FACTOR());
            putAll(m);
            return (fabric.util.HashMap) this.$getProxy();
        }
        
        /**
   * Construct a new HashMap with a specific inital capacity and
   * default load factor of 0.75.
   *
   * @param initialCapacity the initial capacity of this HashMap (&gt;=0)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0)
   */
        public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity) {
            fabric$util$HashMap$(
              initialCapacity,
              fabric.util.HashMap._Static._Proxy.$instance.
                  get$DEFAULT_LOAD_FACTOR());
            return (fabric.util.HashMap) this.$getProxy();
        }
        
        /**
   * Construct a new HashMap with a specific inital capacity and load factor.
   *
   * @param initialCapacity the initial capacity (&gt;=0)
   * @param loadFactor the load factor (&gt; 0, not NaN)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
   *                                     ! (loadFactor &gt; 0.0)
   */
        public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity,
                                                        float loadFactor) {
            if (!(loadFactor > 0))
                throw new java.lang.IllegalArgumentException("Illegal Load: " +
                                                               loadFactor);
            this.set$loadFactor((float) loadFactor);
            fabric$util$AbstractMap$();
            if (initialCapacity < 0)
                throw new java.lang.IllegalArgumentException(
                        "Illegal Capacity: " + initialCapacity);
            if (initialCapacity == 0) initialCapacity = 1;
            fabric.lang.security.Label label =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            this.set$buckets(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore(
                              )).fabric$lang$arrays$ObjectArray$(
                                   label, label.confPolicy(),
                                   fabric.util.HashMap.HashEntry._Proxy.class,
                                   initialCapacity).$getProxy());
            this.set$threshold((int) (int) (initialCapacity * loadFactor));
            return (fabric.util.HashMap) this.$getProxy();
        }
        
        /**
   * Returns the number of kay-value mappings currently in this Map.
   *
   * @return the size
   */
        public int size() { return this.get$size(); }
        
        /**
   * Returns true if there are no key-value mappings currently in this Map.
   *
   * @return <code>size() == 0</code>
   */
        public boolean isEmpty() { return this.get$size() == 0; }
        
        /**
   * Return the value in this HashMap associated with the supplied key,
   * or <code>null</code> if the key maps to nothing.  NOTE: Since the value
   * could also be null, you must use containsKey to see if this key
   * actually maps to something.
   *
   * @param key the key for which to fetch an associated value
   * @return what the key maps to, if present
   * @see #put(Object, Object)
   * @see #containsKey(Object)
   */
        public fabric.lang.Object get(fabric.lang.Object key) {
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(key, e.get$key()))
                    return e.get$value();
                e = e.get$next();
            }
            return null;
        }
        
        /**
   * Returns true if the supplied object <code>equals()</code> a key
   * in this HashMap.
   *
   * @param key the key to search for in this HashMap
   * @return true if the key is in the table
   * @see #containsValue(Object)
   */
        public boolean containsKey(fabric.lang.Object key) {
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(key, e.get$key()))
                    return true;
                e = e.get$next();
            }
            return false;
        }
        
        /**
   * Puts the supplied value into the Map, mapped by the supplied key.
   * The value may be retrieved by any object which <code>equals()</code>
   * this key. NOTE: Since the prior value could also be null, you must
   * first use containsKey if you want to see if you are replacing the
   * key's mapping.
   *
   * @param key the key used to locate the value
   * @param value the value to be stored in the HashMap
   * @return the prior mapping of the key, or null if there was none
   * @see #get(Object)
   * @see Object#equals(Object)
   */
        public fabric.lang.Object put(fabric.lang.Object key,
                                      fabric.lang.Object value) {
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(key, e.get$key())) {
                    e.access();
                    fabric.lang.Object r = e.get$value();
                    e.set$value(value);
                    return r;
                } else
                    e = e.get$next();
            }
            this.postInc$modCount();
            if (this.set$size(this.get$size() + 1) > this.get$threshold()) {
                ((fabric.util.HashMap._Impl) this.fetch()).rehash();
                idx = hash(key);
            }
            addEntry(key, value, idx, true);
            return null;
        }
        
        /**
   * Copies all elements of the given map into this hashtable.  If this table
   * already has a mapping for a key, the new mapping replaces the current
   * one.
   *
   * @param m the map to be hashed into this
   */
        public void putAll(fabric.util.Map m) {
            fabric.util.Iterator
              itr =
              m.
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            while (itr.hasNext()) {
                Entry e = (Entry)
                            fabric.lang.Object._Proxy.$getProxy(itr.next());
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(e)) instanceof BasicMapEntry) {
                    BasicMapEntry entry =
                      (BasicMapEntry) fabric.lang.Object._Proxy.$getProxy(e);
                    put(entry.get$key(), entry.get$value());
                }
                else
                    put(e.getKey(), e.getValue());
            }
        }
        
        /**
   * Removes from the HashMap and returns the value which is mapped by the
   * supplied key. If the key maps to nothing, then the HashMap remains
   * unchanged, and <code>null</code> is returned. NOTE: Since the value
   * could also be null, you must use containsKey to see if you are
   * actually removing a mapping.
   *
   * @param key the key used to locate the value to remove
   * @return whatever the key mapped to, if present
   */
        public fabric.lang.Object remove(fabric.lang.Object key) {
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            HashEntry last = null;
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(key, e.get$key())) {
                    this.postInc$modCount();
                    if (fabric.lang.Object._Proxy.idEquals(last, null))
                        this.get$buckets().set(idx, e.get$next()); else
                        last.set$next(e.get$next());
                    this.postDec$size();
                    return e.cleanup();
                }
                last = e;
                e = e.get$next();
            }
            return null;
        }
        
        /**
   * Clears the Map so it has no keys. This is O(1).
   */
        public void clear() {
            if (this.get$size() != 0) {
                this.postInc$modCount();
                for (int i = 0; i < this.get$buckets().get$length(); i++)
                    this.get$buckets().set(i, null);
                this.set$size((int) 0);
            }
        }
        
        /**
   * Returns true if this HashMap contains a value <code>o</code>, such that
   * <code>o.equals(value)</code>.
   *
   * @param value the value to search for in this HashMap
   * @return true if at least one key maps to the value
   * @see #containsKey(Object)
   */
        public boolean containsValue(fabric.lang.Object value) {
            for (int i = this.get$buckets().get$length() - 1; i >= 0; i--) {
                HashEntry e = (HashEntry) this.get$buckets().get(i);
                while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                    if (fabric.util.AbstractMap._Impl.equals(value,
                                                             e.get$value()))
                        return true;
                    e = e.get$next();
                }
            }
            return false;
        }
        
        /**
   * Returns a "set view" of this HashMap's keys. The set is backed by the
   * HashMap, so changes in one show up in the other.  The set supports
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
                        ((fabric.util.HashMap.Anonymous$5)
                           new fabric.util.HashMap.Anonymous$5._Impl(
                             this.$getStore(),
                             (fabric.util.HashMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractSet$()));
            return this.get$keys();
        }
        
        /**
   * Returns a "collection view" (or "bag view") of this HashMap's values.
   * The collection is backed by the HashMap, so changes in one show up
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
                        ((fabric.util.HashMap.Anonymous$6)
                           new fabric.util.HashMap.Anonymous$6._Impl(
                             this.$getStore(),
                             (fabric.util.HashMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractCollection$()));
            return this.get$values();
        }
        
        /**
   * Returns a "set view" of this HashMap's entries. The set is backed by
   * the HashMap, so changes in one show up in the other.  The set supports
   * element removal, but not element addition.<p>
   *
   * Note that the iterators for all three views, from keySet(), entrySet(),
   * and values(), traverse the HashMap in the same sequence.
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
                        ((fabric.util.HashMap.Anonymous$7)
                           new fabric.util.HashMap.Anonymous$7._Impl(
                             this.$getStore(),
                             (fabric.util.HashMap) this.$getProxy()).$getProxy(
                                                                       )).
                            fabric$util$AbstractSet$()));
            return this.get$entries();
        }
        
        /**
   * Helper method for put, that creates and adds a new Entry.  This is
   * overridden in LinkedHashMap for bookkeeping purposes.
   *
   * @param key the key of the new Entry
   * @param value the value
   * @param idx the index in buckets where the new Entry belongs
   * @param callRemove whether to call the removeEldestEntry method
   * @see #put(Object, Object)
   */
        public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
                             int idx, boolean callRemove) {
            HashEntry
              e =
              (HashEntry)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.HashMap.HashEntry)
                     new fabric.util.HashMap.HashEntry._Impl(this.$getStore()).
                     $getProxy()).fabric$util$HashMap$HashEntry$(key, value));
            e.set$next((HashEntry) this.get$buckets().get(idx));
            this.get$buckets().set(idx, e);
        }
        
        /**
   * Helper method for entrySet(), which matches both key and value
   * simultaneously.
   *
   * @param o the entry to match
   * @return the matching entry, if found, or null
   * @see #entrySet()
   */
        public final HashEntry getEntry(fabric.lang.Object o) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.
                        $unwrap(o)) instanceof Entry)) return null;
            Entry me = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
            fabric.lang.Object key = me.getKey();
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(e.get$key(), key))
                    return fabric.util.AbstractMap._Impl.equals(e.get$value(),
                                                                me.getValue())
                      ? e
                      : null;
                e = e.get$next();
            }
            return null;
        }
        
        /**
   * Helper method that returns an index in the buckets array for `key'
   * based on its hashCode().  Package visible for use by subclasses.
   *
   * @param key the key
   * @return the bucket number
   */
        public final int hash(fabric.lang.Object key) {
            return fabric.lang.Object._Proxy.idEquals(key, null)
              ? 0
              : java.lang.Math.
              abs(
                ((java.lang.Object)
                   fabric.lang.WrappedJavaInlineable.$unwrap(key)).hashCode() %
                    this.get$buckets().get$length());
        }
        
        /**
   * Generates a parameterized iterator.  Must be overrideable, since
   * LinkedHashMap iterates in a different order.
   *
   * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
   * @return the appropriate iterator
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store,
                                             int type) {
            return (HashIterator)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.HashMap.HashIterator)
                          new fabric.util.HashMap.HashIterator._Impl(
                            store, (fabric.util.HashMap) this.$getProxy()).
                          $getProxy()).fabric$util$HashMap$HashIterator$(type));
        }
        
        /**
   * A simplified, more efficient internal implementation of putAll(). clone() 
   * should not call putAll or put, in order to be compatible with the JDK 
   * implementation with respect to subclasses.
   *
   * @param m the map to initialize this from
   */
        public void putAllInternal(fabric.util.Map m) {
            fabric.util.Iterator
              itr =
              m.
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            this.set$size((int) 0);
            while (itr.hasNext()) {
                this.postInc$size();
                Entry e = (Entry)
                            fabric.lang.Object._Proxy.$getProxy(itr.next());
                fabric.lang.Object key = e.getKey();
                int idx = hash(key);
                addEntry(key, e.getValue(), idx, false);
            }
        }
        
        /**
   * Increases the size of the HashMap and rehashes all keys to new
   * array indices; this is called when the addition of a new value
   * would cause size() &gt; threshold. Note that the existing Entry
   * objects are reused in the new hash table.
   *
   * <p>This is not specified, but the new size is twice the current size
   * plus one; this number is not always prime, unfortunately.
   */
        private void rehash() {
            fabric.lang.arrays.ObjectArray oldBuckets = this.get$buckets();
            int newcapacity = this.get$buckets().get$length() * 2 + 1;
            this.set$threshold((int)
                                 (int) (newcapacity * this.get$loadFactor()));
            this.set$buckets(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore(
                              )).fabric$lang$arrays$ObjectArray$(
                                   this.get$$updateLabel(),
                                   this.get$$updateLabel().confPolicy(),
                                   fabric.util.HashMap.HashEntry._Proxy.class,
                                   newcapacity).$getProxy());
            for (int i = oldBuckets.get$length() - 1; i >= 0; i--) {
                HashEntry e = (HashEntry) oldBuckets.get(i);
                while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                    int idx = hash(e.get$key());
                    HashEntry dest = (HashEntry) this.get$buckets().get(idx);
                    HashEntry next = e.get$next();
                    e.set$next((HashEntry) this.get$buckets().get(idx));
                    this.get$buckets().set(idx, e);
                    e = next;
                }
            }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.HashMap) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.HashMap._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.threshold);
            out.writeFloat(this.loadFactor);
            $writeRef($getStore(), this.buckets, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeInt(this.modCount);
            out.writeInt(this.size);
            $writeRef($getStore(), this.entries, refTypes, out, intraStoreRefs,
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
            this.threshold = in.readInt();
            this.loadFactor = in.readFloat();
            this.buckets =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.modCount = in.readInt();
            this.size = in.readInt();
            this.entries = (fabric.util.Set)
                             $readRef(fabric.util.Set._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.HashMap._Impl src = (fabric.util.HashMap._Impl) other;
            this.threshold = src.threshold;
            this.loadFactor = src.loadFactor;
            this.buckets = src.buckets;
            this.modCount = src.modCount;
            this.size = src.size;
            this.entries = src.entries;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$DEFAULT_CAPACITY();
        
        public int set$DEFAULT_CAPACITY(int val);
        
        public int postInc$DEFAULT_CAPACITY();
        
        public int postDec$DEFAULT_CAPACITY();
        
        public float get$DEFAULT_LOAD_FACTOR();
        
        public float set$DEFAULT_LOAD_FACTOR(float val);
        
        public float postInc$DEFAULT_LOAD_FACTOR();
        
        public float postDec$DEFAULT_LOAD_FACTOR();
        
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HashMap._Static {
            public int get$DEFAULT_CAPACITY() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  get$DEFAULT_CAPACITY();
            }
            
            public int set$DEFAULT_CAPACITY(int val) {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  set$DEFAULT_CAPACITY(val);
            }
            
            public int postInc$DEFAULT_CAPACITY() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postInc$DEFAULT_CAPACITY();
            }
            
            public int postDec$DEFAULT_CAPACITY() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postDec$DEFAULT_CAPACITY();
            }
            
            public float get$DEFAULT_LOAD_FACTOR() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  get$DEFAULT_LOAD_FACTOR();
            }
            
            public float set$DEFAULT_LOAD_FACTOR(float val) {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  set$DEFAULT_LOAD_FACTOR(val);
            }
            
            public float postInc$DEFAULT_LOAD_FACTOR() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postInc$DEFAULT_LOAD_FACTOR();
            }
            
            public float postDec$DEFAULT_LOAD_FACTOR() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postDec$DEFAULT_LOAD_FACTOR();
            }
            
            public long get$serialVersionUID() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.HashMap._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.HashMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.HashMap._Static $instance;
            
            static {
                fabric.
                  util.
                  HashMap.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.HashMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.HashMap._Static._Impl.class);
                $instance = (fabric.util.HashMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMap._Static {
            public int get$DEFAULT_CAPACITY() { return this.DEFAULT_CAPACITY; }
            
            public int set$DEFAULT_CAPACITY(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_CAPACITY = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$DEFAULT_CAPACITY() {
                int tmp = this.get$DEFAULT_CAPACITY();
                this.set$DEFAULT_CAPACITY((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$DEFAULT_CAPACITY() {
                int tmp = this.get$DEFAULT_CAPACITY();
                this.set$DEFAULT_CAPACITY((int) (tmp - 1));
                return tmp;
            }
            
            int DEFAULT_CAPACITY;
            
            public float get$DEFAULT_LOAD_FACTOR() {
                return this.DEFAULT_LOAD_FACTOR;
            }
            
            public float set$DEFAULT_LOAD_FACTOR(float val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_LOAD_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public float postInc$DEFAULT_LOAD_FACTOR() {
                float tmp = this.get$DEFAULT_LOAD_FACTOR();
                this.set$DEFAULT_LOAD_FACTOR((float) (tmp + 1));
                return tmp;
            }
            
            public float postDec$DEFAULT_LOAD_FACTOR() {
                float tmp = this.get$DEFAULT_LOAD_FACTOR();
                this.set$DEFAULT_LOAD_FACTOR((float) (tmp - 1));
                return tmp;
            }
            
            float DEFAULT_LOAD_FACTOR;
            
            public long get$serialVersionUID() { return this.serialVersionUID; }
            
            public long set$serialVersionUID(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.serialVersionUID = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp - 1));
                return tmp;
            }
            
            private long serialVersionUID;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.DEFAULT_CAPACITY);
                out.writeFloat(this.DEFAULT_LOAD_FACTOR);
                out.writeLong(this.serialVersionUID);
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
                this.DEFAULT_CAPACITY = in.readInt();
                this.DEFAULT_LOAD_FACTOR = in.readFloat();
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm791 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled794 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff792 = 1;
                        boolean $doBackoff793 = true;
                        boolean $retry787 = true;
                        boolean $keepReads788 = false;
                        $label785: for (boolean $commit786 = false; !$commit786;
                                        ) {
                            if ($backoffEnabled794) {
                                if ($doBackoff793) {
                                    if ($backoff792 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff792));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e789) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff792 < 5000) $backoff792 *= 2;
                                }
                                $doBackoff793 = $backoff792 <= 32 ||
                                                  !$doBackoff793;
                            }
                            $commit786 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.HashMap._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_CAPACITY((int) 11);
                                    fabric.util.HashMap._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_LOAD_FACTOR((float) 0.75F);
                                    fabric.util.HashMap._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 362498820763181265L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e789) {
                                    throw $e789;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e789) {
                                    throw $e789;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e789) {
                                    throw $e789;
                                }
                                catch (final Throwable $e789) {
                                    $tm791.getCurrentLog().checkRetrySignal();
                                    throw $e789;
                                }
                            }
                            catch (final fabric.worker.RetryException $e789) {
                                $commit786 = false;
                                continue $label785;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e789) {
                                $commit786 = false;
                                $retry787 = false;
                                $keepReads788 = $e789.keepReads;
                                if ($tm791.checkForStaleObjects()) {
                                    $retry787 = true;
                                    $keepReads788 = false;
                                    continue $label785;
                                }
                                fabric.common.TransactionID $currentTid790 =
                                  $tm791.getCurrentTid();
                                if ($e789.tid ==
                                      null ||
                                      !$e789.tid.isDescendantOf(
                                                   $currentTid790)) {
                                    throw $e789;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e789);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e789) {
                                $commit786 = false;
                                fabric.common.TransactionID $currentTid790 =
                                  $tm791.getCurrentTid();
                                if ($e789.tid.isDescendantOf($currentTid790))
                                    continue $label785;
                                if ($currentTid790.parent != null) {
                                    $retry787 = false;
                                    throw $e789;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e789) {
                                $commit786 = false;
                                if ($tm791.checkForStaleObjects())
                                    continue $label785;
                                $retry787 = false;
                                throw new fabric.worker.AbortException($e789);
                            }
                            finally {
                                if ($commit786) {
                                    fabric.common.TransactionID $currentTid790 =
                                      $tm791.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e789) {
                                        $commit786 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e789) {
                                        $commit786 = false;
                                        $retry787 = false;
                                        $keepReads788 = $e789.keepReads;
                                        if ($tm791.checkForStaleObjects()) {
                                            $retry787 = true;
                                            $keepReads788 = false;
                                            continue $label785;
                                        }
                                        if ($e789.tid ==
                                              null ||
                                              !$e789.tid.isDescendantOf(
                                                           $currentTid790))
                                            throw $e789;
                                        throw new fabric.worker.
                                                UserAbortException($e789);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e789) {
                                        $commit786 = false;
                                        $currentTid790 = $tm791.getCurrentTid();
                                        if ($currentTid790 != null) {
                                            if ($e789.tid.equals(
                                                            $currentTid790) ||
                                                  !$e789.tid.
                                                  isDescendantOf(
                                                    $currentTid790)) {
                                                throw $e789;
                                            }
                                        }
                                    }
                                } else if ($keepReads788) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit786) {
                                    {  }
                                    if ($retry787) { continue $label785; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 113, -94, -35, 102,
    -45, -99, 72, -29, -67, 33, -9, -59, 46, 56, 70, 48, 41, -125, 26, 11, -85,
    -69, -78, -90, 86, -19, 61, 102, -93, 48, -37, 100 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
