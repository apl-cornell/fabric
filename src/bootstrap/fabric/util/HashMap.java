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
        
        public static final byte[] $classHash = new byte[] { 102, 116, 59, -90,
        -35, -81, 45, 38, 76, 96, 58, 7, -5, 86, -112, -114, 95, 83, 59, 0,
        -116, 60, 15, 8, 46, 79, -123, 49, -92, -79, -112, -101 };
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
        
        public static final byte[] $classHash = new byte[] { 80, -21, -102, -87,
        124, 82, -53, 83, 104, -80, 110, -16, 80, -46, 8, -91, -110, 87, 127,
        -96, -19, -80, 3, 2, -44, 11, -48, -92, -77, 40, 76, -38 };
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
        
        public static final byte[] $classHash = new byte[] { 10, -58, 21, 38,
        63, 20, -86, 32, -119, 2, 119, -113, 34, -24, -23, -94, 30, -85, -28,
        34, 88, -111, 17, 22, -15, 110, 74, 49, 35, -28, 74, -105 };
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
        
        public static final byte[] $classHash = new byte[] { -15, -8, -62, 82,
        -76, -70, -80, 47, -9, -79, 13, 60, -16, 75, 41, 8, -73, 122, -99, 56,
        108, -18, 24, -78, -98, -48, -123, -89, 99, 118, 71, -88 };
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
        
        public static final byte[] $classHash = new byte[] { 10, -58, 21, 38,
        63, 20, -86, 32, -119, 2, 119, -113, 34, -24, -23, -94, 30, -85, -28,
        34, 88, -111, 17, 22, -15, 110, 74, 49, 35, -28, 74, -105 };
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
                        fabric.worker.transaction.TransactionManager $tm747 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled750 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff748 = 1;
                        boolean $doBackoff749 = true;
                        boolean $retry743 = true;
                        boolean $keepReads744 = false;
                        $label741: for (boolean $commit742 = false; !$commit742;
                                        ) {
                            if ($backoffEnabled750) {
                                if ($doBackoff749) {
                                    if ($backoff748 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff748));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e745) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff748 < 5000) $backoff748 *= 2;
                                }
                                $doBackoff749 = $backoff748 <= 32 ||
                                                  !$doBackoff749;
                            }
                            $commit742 = true;
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
                                         RetryException $e745) {
                                    throw $e745;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e745) {
                                    throw $e745;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e745) {
                                    throw $e745;
                                }
                                catch (final Throwable $e745) {
                                    $tm747.getCurrentLog().checkRetrySignal();
                                    throw $e745;
                                }
                            }
                            catch (final fabric.worker.RetryException $e745) {
                                $commit742 = false;
                                continue $label741;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e745) {
                                $commit742 = false;
                                $retry743 = false;
                                $keepReads744 = $e745.keepReads;
                                if ($tm747.checkForStaleObjects()) {
                                    $retry743 = true;
                                    $keepReads744 = false;
                                    continue $label741;
                                }
                                fabric.common.TransactionID $currentTid746 =
                                  $tm747.getCurrentTid();
                                if ($e745.tid ==
                                      null ||
                                      !$e745.tid.isDescendantOf(
                                                   $currentTid746)) {
                                    throw $e745;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e745);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e745) {
                                $commit742 = false;
                                fabric.common.TransactionID $currentTid746 =
                                  $tm747.getCurrentTid();
                                if ($e745.tid.isDescendantOf($currentTid746))
                                    continue $label741;
                                if ($currentTid746.parent != null) {
                                    $retry743 = false;
                                    throw $e745;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e745) {
                                $commit742 = false;
                                if ($tm747.checkForStaleObjects())
                                    continue $label741;
                                $retry743 = false;
                                throw new fabric.worker.AbortException($e745);
                            }
                            finally {
                                if ($commit742) {
                                    fabric.common.TransactionID $currentTid746 =
                                      $tm747.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e745) {
                                        $commit742 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e745) {
                                        $commit742 = false;
                                        $retry743 = false;
                                        $keepReads744 = $e745.keepReads;
                                        if ($tm747.checkForStaleObjects()) {
                                            $retry743 = true;
                                            $keepReads744 = false;
                                            continue $label741;
                                        }
                                        if ($e745.tid ==
                                              null ||
                                              !$e745.tid.isDescendantOf(
                                                           $currentTid746))
                                            throw $e745;
                                        throw new fabric.worker.
                                                UserAbortException($e745);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e745) {
                                        $commit742 = false;
                                        $currentTid746 = $tm747.getCurrentTid();
                                        if ($currentTid746 != null) {
                                            if ($e745.tid.equals(
                                                            $currentTid746) ||
                                                  !$e745.tid.
                                                  isDescendantOf(
                                                    $currentTid746)) {
                                                throw $e745;
                                            }
                                        }
                                    }
                                } else if ($keepReads744) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit742) {
                                    {  }
                                    if ($retry743) { continue $label741; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 7, 83, 16, 8, 88, 117,
    74, -34, 94, 63, -83, 122, -65, -29, -95, 25, -122, -41, 12, -4, -115, 72,
    31, -43, -68, 42, -107, 63, 123, 52, -121, 38 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
