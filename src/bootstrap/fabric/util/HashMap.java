package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface HashMap
  extends fabric.util.Map, fabric.util.AbstractMap
{
    
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
    public static interface HashEntry
      extends BasicMapEntry
    {
        
        public fabric.util.HashMap.HashEntry get$next();
        
        public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);
        
        /**
         * Simple constructor.
         * @param key the key
         * @param value the value
         */
        public HashEntry fabric$util$HashMap$HashEntry$(
          fabric.lang.Object key, fabric.lang.Object value);
        
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
          implements HashEntry
        {
            
            public fabric.util.HashMap.HashEntry get$next() {
                return ((fabric.util.HashMap.HashEntry._Impl) fetch()).get$next(
                                                                         );
            }
            
            public fabric.util.HashMap.HashEntry set$next(
              fabric.util.HashMap.HashEntry val) {
                return ((fabric.util.HashMap.HashEntry._Impl) fetch()).set$next(
                                                                         val);
            }
            
            public native fabric.util.HashMap.HashEntry
              fabric$util$HashMap$HashEntry$(fabric.lang.Object arg1,
                                             fabric.lang.Object arg2);
            
            public native void access();
            
            public native fabric.lang.Object cleanup();
            
            public _Proxy(HashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.AbstractMap.BasicMapEntry._Impl
          implements HashEntry
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
             * The next entry in the linked list. Package visible for use by
             subclass.
             */
            HashEntry next;
            
            /**
             * Simple constructor.
             * @param key the key
             * @param value the value
             */
            public native HashEntry fabric$util$HashMap$HashEntry$(
              fabric.lang.Object key, fabric.lang.Object value);
            
            /**
             * Called when this entry is accessed via {@link #put(Object,
             Object)}.
             * This version does nothing, but in LinkedHashMap, it must do some
             * bookkeeping for access-traversal mode.
             */
            public native void access();
            
            /**
             * Called when this entry is removed from the map. This version
             simply
             * returns the value, but in LinkedHashMap, it must also do
             bookkeeping.
             *
             * @return the value of this key as it is removed
             */
            public native fabric.lang.Object cleanup();
            
            public native fabric.lang.Object $initLabels();
            
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
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
              implements fabric.util.HashMap.HashEntry._Static
            {
                
                public _Proxy(fabric.util.HashMap.HashEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
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
                      _Impl impl =
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
              implements fabric.util.HashMap.HashEntry._Static
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
                             long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.HashMap.HashEntry._Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
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
    public fabric.util.HashMap fabric$util$HashMap$(int initialCapacity,
                                                    float loadFactor);
    
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
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
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
    public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
                         int idx, boolean callRemove);
    
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
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
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
        
        public fabric.util.HashMap.HashEntry set$next(
          fabric.util.HashMap.HashEntry val);
        
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
          implements HashIterator
        {
            
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
            
            public native fabric.util.HashMap.HashIterator
              fabric$util$HashMap$HashIterator$(int arg1);
            
            public native boolean hasNext();
            
            public native fabric.lang.Object next();
            
            public native void remove();
            
            public _Proxy(HashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements HashIterator
        {
            
            public fabric.util.HashMap get$out$() { return this.out$; }
            
            private fabric.util.HashMap out$;
            
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
            
            /**
             * The number of modifications to the backing HashMap that we know
             about.
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
             * The next entry that should be returned by next(). It is set to
             something
             * if we're iterating through a bucket that contains multiple linked
             * entries. It is null if next() needs to find a new bucket.
             */
            private HashEntry next;
            
            /**
             * Construct a new HashIterator with the supplied type.
             * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
             */
            public native HashIterator fabric$util$HashMap$HashIterator$(
              int type);
            
            /**
             * Returns true if the Iterator has more elements.
             * @return true if there are more elements
             */
            public native boolean hasNext();
            
            /**
             * Returns the next element in the Iterator's sequential view.
             * @return the next element
             * @throws ConcurrentModificationException if the HashMap was
             modified
             * @throws NoSuchElementException if there is none
             */
            public native fabric.lang.Object next();
            
            /**
             * Removes from the backing HashMap the last element which was
             fetched
             * with the <code>next()</code> method.
             * @throws ConcurrentModificationException if the HashMap was
             modified
             * @throws IllegalStateException if called when there is no last
             element
             */
            public native void remove();
            
            public native fabric.lang.Object $initLabels();
            
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
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.HashMap)
                              $readRef(fabric.util.HashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
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
              implements fabric.util.HashMap.HashIterator._Static
            {
                
                public _Proxy(fabric.util.HashMap.HashIterator._Static.
                                _Impl impl) {
                    super(impl);
                }
                
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
                      _Impl impl =
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
              implements fabric.util.HashMap.HashIterator._Static
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
                             long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.HashMap.HashIterator._Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractMap._Proxy
      implements fabric.util.HashMap
    {
        
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
        
        public native fabric.util.HashMap fabric$util$HashMap$();
        
        public native fabric.util.HashMap fabric$util$HashMap$(
          fabric.util.Map arg1);
        
        public native fabric.util.HashMap fabric$util$HashMap$(int arg1);
        
        public native fabric.util.HashMap fabric$util$HashMap$(int arg1,
                                                               float arg2);
        
        public native void addEntry(fabric.lang.Object arg1,
                                    fabric.lang.Object arg2, int arg3,
                                    boolean arg4);
        
        public final native fabric.util.HashMap.HashEntry getEntry(
          fabric.lang.Object arg1);
        
        public final native int hash(fabric.lang.Object arg1);
        
        public native fabric.util.Iterator iterator(fabric.worker.Store arg1,
                                                    int arg2);
        
        public native void putAllInternal(fabric.util.Map arg1);
        
        public _Proxy(HashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractMap._Impl
      implements fabric.util.HashMap
    {
        
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
         * The rounded product of the capacity and the load factor; when the
         number
         * of elements exceeds the threshold, the HashMap calls
         * <code>rehash()</code>.
         * @serial the threshold for rehashing
         */
        private int threshold;
        
        public float get$loadFactor() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.loadFactor;
        }
        
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
        
        /**
         * Load factor of this HashMap:  used in computing the threshold.
         * Package visible for use by HashSet.
         * @serial the load factor
         */
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
         * Construct a new HashMap with the default capacity (11) and the
         default
         * load factor (0.75).
         */
        public native fabric.util.HashMap fabric$util$HashMap$();
        
        /**
         * Construct a new HashMap from the given Map, with initial capacity
         * the greater of the size of <code>m</code> or the default of 11.
         * <p>
         *
         * Every element in Map m will be put into this new HashMap.
         *
         * @param m a Map whose key / value pairs will be put into the new
         HashMap.
         *        <b>NOTE: key / value pairs are not cloned in this
         constructor.</b>
         * @throws NullPointerException if m is null
         */
        public native fabric.util.HashMap fabric$util$HashMap$(
          fabric.util.Map m);
        
        /**
         * Construct a new HashMap with a specific inital capacity and
         * default load factor of 0.75.
         *
         * @param initialCapacity the initial capacity of this HashMap (&gt;=0)
         * @throws IllegalArgumentException if (initialCapacity &lt; 0)
         */
        public native fabric.util.HashMap fabric$util$HashMap$(
          int initialCapacity);
        
        /**
         * Construct a new HashMap with a specific inital capacity and load
         factor.
         *
         * @param initialCapacity the initial capacity (&gt;=0)
         * @param loadFactor the load factor (&gt; 0, not NaN)
         * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
         *                                     ! (loadFactor &gt; 0.0)
         */
        public native fabric.util.HashMap fabric$util$HashMap$(
          int initialCapacity, float loadFactor);
        
        /**
         * Returns the number of kay-value mappings currently in this Map.
         *
         * @return the size
         */
        public native int size();
        
        /**
         * Returns true if there are no key-value mappings currently in this
         Map.
         *
         * @return <code>size() == 0</code>
         */
        public native boolean isEmpty();
        
        /**
         * Return the value in this HashMap associated with the supplied key,
         * or <code>null</code> if the key maps to nothing.  NOTE: Since the
         value
         * could also be null, you must use containsKey to see if this key
         * actually maps to something.
         *
         * @param key the key for which to fetch an associated value
         * @return what the key maps to, if present
         * @see #put(Object, Object)
         * @see #containsKey(Object)
         */
        public native fabric.lang.Object get(fabric.lang.Object key);
        
        /**
         * Returns true if the supplied object <code>equals()</code> a key
         * in this HashMap.
         *
         * @param key the key to search for in this HashMap
         * @return true if the key is in the table
         * @see #containsValue(Object)
         */
        public native boolean containsKey(fabric.lang.Object key);
        
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
        public native fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        /**
         * Copies all elements of the given map into this hashtable.  If this
         table
         * already has a mapping for a key, the new mapping replaces the current
         * one.
         *
         * @param m the map to be hashed into this
         */
        public native void putAll(fabric.util.Map m);
        
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
        public native fabric.lang.Object remove(fabric.lang.Object key);
        
        /**
         * Clears the Map so it has no keys. This is O(1).
         */
        public native void clear();
        
        /**
         * Returns true if this HashMap contains a value <code>o</code>, such
         that
         * <code>o.equals(value)</code>.
         *
         * @param value the value to search for in this HashMap
         * @return true if at least one key maps to the value
         * @see #containsKey(Object)
         */
        public native boolean containsValue(fabric.lang.Object value);
        
        /**
         * Returns a "set view" of this HashMap's keys. The set is backed by the
         * HashMap, so changes in one show up in the other.  The set supports
         * element removal, but not element addition.
         *
         * @return a set view of the keys
         * @see #values()
         * @see #entrySet()
         */
        public native fabric.util.Set keySet();
        
        /**
         * Returns a "collection view" (or "bag view") of this HashMap's values.
         * The collection is backed by the HashMap, so changes in one show up
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
         * Returns a "set view" of this HashMap's entries. The set is backed by
         * the HashMap, so changes in one show up in the other.  The set
         supports
         * element removal, but not element addition.<p>
         *
         * Note that the iterators for all three views, from keySet(),
         entrySet(),
         * and values(), traverse the HashMap in the same sequence.
         *
         * @return a set view of the entries
         * @see #keySet()
         * @see #values()
         * @see Map.Entry
         */
        public native fabric.util.Set entrySet();
        
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
        public native void addEntry(fabric.lang.Object key,
                                    fabric.lang.Object value, int idx,
                                    boolean callRemove);
        
        /**
         * Helper method for entrySet(), which matches both key and value
         * simultaneously.
         *
         * @param o the entry to match
         * @return the matching entry, if found, or null
         * @see #entrySet()
         */
        public final native HashEntry getEntry(fabric.lang.Object o);
        
        /**
         * Helper method that returns an index in the buckets array for `key'
         * based on its hashCode().  Package visible for use by subclasses.
         *
         * @param key the key
         * @return the bucket number
         */
        public final native int hash(fabric.lang.Object key);
        
        /**
         * Generates a parameterized iterator.  Must be overrideable, since
         * LinkedHashMap iterates in a different order.
         *
         * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
         * @return the appropriate iterator
         */
        public native fabric.util.Iterator iterator(fabric.worker.Store store,
                                                    int type);
        
        /**
         * A simplified, more efficient internal implementation of putAll().
         clone() 
         * should not call putAll or put, in order to be compatible with the JDK
         
         * implementation with respect to subclasses.
         *
         * @param m the map to initialize this from
         */
        public native void putAllInternal(fabric.util.Map m);
        
        /**
         * Increases the size of the HashMap and rehashes all keys to new
         * array indices; this is called when the addition of a new value
         * would cause size() &gt; threshold. Note that the existing Entry
         * objects are reused in the new hash table.
         *
         * <p>This is not specified, but the new size is twice the current size
         * plus one; this number is not always prime, unfortunately.
         */
        private native void rehash();
        
        public native fabric.lang.Object $initLabels();
        
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
                     long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                                        refTypes.next(),
                                      in,
                                      store,
                                      intraStoreRefs,
                                      interStoreRefs);
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
          implements fabric.util.HashMap._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.HashMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.HashMap._Static._Impl.class);
                $instance = (fabric.util.HashMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashMap._Static
        {
            
            public int get$DEFAULT_CAPACITY() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.DEFAULT_CAPACITY;
            }
            
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
            
            /**
             * Default number of buckets. This is the value the JDK 1.3 uses.
             Some
             * early documentation specified this value as 101. That is
             incorrect.
             * Package visible for use by HashSet.
             */
            int DEFAULT_CAPACITY;
            
            public float get$DEFAULT_LOAD_FACTOR() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
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
            
            /**
             * The default load factor; this is explicitly specified by the
             spec.
             * Package visible for use by HashSet.
             */
            float DEFAULT_LOAD_FACTOR;
            
            public long get$serialVersionUID() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.serialVersionUID;
            }
            
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
            
            /**
             * Compatible with JDK 1.2.
             */
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
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.DEFAULT_CAPACITY = in.readInt();
                this.DEFAULT_LOAD_FACTOR = in.readFloat();
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashMap._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
