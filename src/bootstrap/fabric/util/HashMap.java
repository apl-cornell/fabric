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
        
        public static final byte[] $classHash = new byte[] { -124, -90, 50, 122,
        27, -120, 69, -113, 88, -53, 17, -62, 117, -20, -31, 55, 15, 41, -104,
        55, 69, -9, 61, 78, -93, 22, 13, 47, 102, 96, -36, 108 };
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
        
        public static final byte[] $classHash = new byte[] { -109, 43, -15, 101,
        27, 36, 125, 27, 42, 103, -21, -8, 100, 45, -88, -96, 113, 20, -105,
        -28, -120, -124, -107, -28, 6, -71, 97, -106, 116, -82, -54, -100 };
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
        
        public static final byte[] $classHash = new byte[] { 31, 80, -44, -5,
        72, -79, 13, -77, -22, 1, -65, -103, -79, 65, 10, -30, -22, 20, -84,
        -36, -81, 118, -66, -17, 0, 72, 54, -53, -45, -31, -2, 0 };
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
        
        public static final byte[] $classHash = new byte[] { -77, 9, -77, -110,
        -35, -92, -125, 4, 23, 35, -61, 71, -22, -102, 99, 11, -21, 35, 30,
        -128, 26, -65, -102, 100, 88, 70, 40, -115, 101, -48, 18, -55 };
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
        
        public static final byte[] $classHash = new byte[] { 31, 80, -44, -5,
        72, -79, 13, -77, -22, 1, -65, -103, -79, 65, 10, -30, -22, 20, -84,
        -36, -81, 118, -66, -17, 0, 72, 54, -53, -45, -31, -2, 0 };
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
                        fabric.worker.transaction.TransactionManager $tm781 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled784 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff782 = 1;
                        boolean $doBackoff783 = true;
                        boolean $retry777 = true;
                        boolean $keepReads778 = false;
                        $label775: for (boolean $commit776 = false; !$commit776;
                                        ) {
                            if ($backoffEnabled784) {
                                if ($doBackoff783) {
                                    if ($backoff782 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff782));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e779) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff782 < 5000) $backoff782 *= 2;
                                }
                                $doBackoff783 = $backoff782 <= 32 ||
                                                  !$doBackoff783;
                            }
                            $commit776 = true;
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
                                         RetryException $e779) {
                                    throw $e779;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e779) {
                                    throw $e779;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e779) {
                                    throw $e779;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e779) {
                                    throw $e779;
                                }
                                catch (final Throwable $e779) {
                                    $tm781.getCurrentLog().checkRetrySignal();
                                    throw $e779;
                                }
                            }
                            catch (final fabric.worker.RetryException $e779) {
                                $commit776 = false;
                                continue $label775;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e779) {
                                $commit776 = false;
                                $retry777 = false;
                                $keepReads778 = $e779.keepReads;
                                if ($tm781.checkForStaleObjects()) {
                                    $retry777 = true;
                                    $keepReads778 = false;
                                    continue $label775;
                                }
                                fabric.common.TransactionID $currentTid780 =
                                  $tm781.getCurrentTid();
                                if ($e779.tid ==
                                      null ||
                                      !$e779.tid.isDescendantOf(
                                                   $currentTid780)) {
                                    throw $e779;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e779);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e779) {
                                $commit776 = false;
                                fabric.common.TransactionID $currentTid780 =
                                  $tm781.getCurrentTid();
                                if ($e779.tid.isDescendantOf($currentTid780))
                                    continue $label775;
                                if ($currentTid780.parent != null) {
                                    $retry777 = false;
                                    throw $e779;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e779) {
                                $commit776 = false;
                                if ($tm781.checkForStaleObjects())
                                    continue $label775;
                                fabric.common.TransactionID $currentTid780 =
                                  $tm781.getCurrentTid();
                                if ($e779.tid.isDescendantOf($currentTid780)) {
                                    $retry777 = true;
                                }
                                else if ($currentTid780.parent != null) {
                                    $retry777 = false;
                                    throw $e779;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e779) {
                                $commit776 = false;
                                if ($tm781.checkForStaleObjects())
                                    continue $label775;
                                $retry777 = false;
                                throw new fabric.worker.AbortException($e779);
                            }
                            finally {
                                if ($commit776) {
                                    fabric.common.TransactionID $currentTid780 =
                                      $tm781.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e779) {
                                        $commit776 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e779) {
                                        $commit776 = false;
                                        $retry777 = false;
                                        $keepReads778 = $e779.keepReads;
                                        if ($tm781.checkForStaleObjects()) {
                                            $retry777 = true;
                                            $keepReads778 = false;
                                            continue $label775;
                                        }
                                        if ($e779.tid ==
                                              null ||
                                              !$e779.tid.isDescendantOf(
                                                           $currentTid780))
                                            throw $e779;
                                        throw new fabric.worker.
                                                UserAbortException($e779);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e779) {
                                        $commit776 = false;
                                        $currentTid780 = $tm781.getCurrentTid();
                                        if ($currentTid780 != null) {
                                            if ($e779.tid.equals(
                                                            $currentTid780) ||
                                                  !$e779.tid.
                                                  isDescendantOf(
                                                    $currentTid780)) {
                                                throw $e779;
                                            }
                                        }
                                    }
                                } else if ($keepReads778) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit776) {
                                    {  }
                                    if ($retry777) { continue $label775; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -55, 32, 9, -52, 3,
    -100, 82, 52, 66, -1, 39, 30, -38, 29, -90, 73, -1, 91, -19, 96, -96, -37,
    12, -113, 56, -53, -20, 34, 40, -29, -35, -60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
