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
        
        public static final byte[] $classHash = new byte[] { 69, -21, -100, 101,
        -43, -16, 4, 79, -9, -7, 95, 103, 94, 10, 51, -58, -117, -13, 2, 3, -20,
        67, -87, -51, 44, -114, 106, -85, -56, 65, -15, 16 };
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
        
        public static final byte[] $classHash = new byte[] { 14, 36, -30, -48,
        121, 55, -99, 63, 60, -66, 68, -14, -77, -118, -30, 86, 86, 86, -51, -8,
        -24, 92, -116, -41, 90, 31, 16, -109, -76, 57, -9, 100 };
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
        
        public static final byte[] $classHash = new byte[] { -55, 32, -33, -77,
        -98, 123, -28, 0, 121, 83, -104, 14, 75, -86, 18, -4, -89, -73, -58, -7,
        1, 0, 35, -44, 2, -2, 2, -79, -48, -28, -1, -33 };
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
        
        public static final byte[] $classHash = new byte[] { 68, 111, 6, -101,
        -34, -113, 23, 116, -103, -128, 99, 69, -67, 40, 90, -84, -1, 121, 47,
        119, 68, -85, 37, -122, 22, -51, 108, -74, 116, 48, 17, 118 };
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
        
        public static final byte[] $classHash = new byte[] { -55, 32, -33, -77,
        -98, 123, -28, 0, 121, 83, -104, 14, 75, -86, 18, -4, -89, -73, -58, -7,
        1, 0, 35, -44, 2, -2, 2, -79, -48, -28, -1, -33 };
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
                        fabric.worker.transaction.TransactionManager $tm769 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled772 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff770 = 1;
                        boolean $doBackoff771 = true;
                        boolean $retry765 = true;
                        boolean $keepReads766 = false;
                        $label763: for (boolean $commit764 = false; !$commit764;
                                        ) {
                            if ($backoffEnabled772) {
                                if ($doBackoff771) {
                                    if ($backoff770 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff770));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e767) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff770 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff770 =
                                          java.lang.Math.
                                            min(
                                              $backoff770 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff771 = $backoff770 <= 32 ||
                                                  !$doBackoff771;
                            }
                            $commit764 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.HashMap._Static._Proxy.$instance.
                                  set$DEFAULT_CAPACITY((int) 11);
                                fabric.util.HashMap._Static._Proxy.$instance.
                                  set$DEFAULT_LOAD_FACTOR((float) 0.75F);
                                fabric.util.HashMap._Static._Proxy.$instance.
                                  set$serialVersionUID((long)
                                                         362498820763181265L);
                            }
                            catch (final fabric.worker.RetryException $e767) {
                                $commit764 = false;
                                continue $label763;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e767) {
                                $commit764 = false;
                                $retry765 = false;
                                $keepReads766 = $e767.keepReads;
                                fabric.common.TransactionID $currentTid768 =
                                  $tm769.getCurrentTid();
                                if ($e767.tid ==
                                      null ||
                                      !$e767.tid.isDescendantOf(
                                                   $currentTid768)) {
                                    throw $e767;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e767);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e767) {
                                $commit764 = false;
                                fabric.common.TransactionID $currentTid768 =
                                  $tm769.getCurrentTid();
                                if ($e767.tid.isDescendantOf($currentTid768))
                                    continue $label763;
                                if ($currentTid768.parent != null) {
                                    $retry765 = false;
                                    throw $e767;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e767) {
                                $commit764 = false;
                                $retry765 = false;
                                if ($tm769.inNestedTxn()) {
                                    $keepReads766 = true;
                                }
                                throw $e767;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid768 =
                                  $tm769.getCurrentTid();
                                if ($commit764) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e767) {
                                        $commit764 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e767) {
                                        $commit764 = false;
                                        $retry765 = false;
                                        $keepReads766 = $e767.keepReads;
                                        if ($e767.tid ==
                                              null ||
                                              !$e767.tid.isDescendantOf(
                                                           $currentTid768))
                                            throw $e767;
                                        throw new fabric.worker.
                                                UserAbortException($e767);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e767) {
                                        $commit764 = false;
                                        $currentTid768 = $tm769.getCurrentTid();
                                        if ($currentTid768 != null) {
                                            if ($e767.tid.equals(
                                                            $currentTid768) ||
                                                  !$e767.tid.
                                                  isDescendantOf(
                                                    $currentTid768)) {
                                                throw $e767;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm769.inNestedTxn() &&
                                          $tm769.checkForStaleObjects()) {
                                        $retry765 = true;
                                        $keepReads766 = false;
                                    }
                                    if ($keepReads766) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e767) {
                                            $currentTid768 = $tm769.getCurrentTid();
                                            if ($currentTid768 != null &&
                                                  ($e767.tid.equals($currentTid768) || !$e767.tid.isDescendantOf($currentTid768))) {
                                                throw $e767;
                                            } else {
                                                $retry765 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit764) {
                                    {  }
                                    if ($retry765) { continue $label763; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -73, 106, -5, 67, 89,
    118, 75, 114, -75, -118, 29, 84, -85, 44, -43, -18, 5, 120, -73, 5, 6, -104,
    -116, -92, -114, -74, -64, 12, -116, -117, 88, -120 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
