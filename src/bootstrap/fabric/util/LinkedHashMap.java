package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * This class provides a hashtable-backed implementation of the
 * Map interface, with predictable traversal order.
 * <p>
 *
 * It uses a hash-bucket approach; that is, hash collisions are handled
 * by linking the new node off of the pre-existing node (or list of
 * nodes).  In this manner, techniques such as linear probing (which
 * can cause primary clustering) and rehashing (which does not fit very
 * well with Java's method of precomputing hash codes) are avoided.  In
 * addition, this maintains a doubly-linked list which tracks either
 * insertion or access order.
 * <p>
 *
 * In insertion order, calling <code>put</code> adds the key to the end of
 * traversal, unless the key was already in the map; changing traversal order
 * requires removing and reinserting a key.  On the other hand, in access
 * order, all calls to <code>put</code> and <code>get</code> cause the
 * accessed key to move to the end of the traversal list.  Note that any
 * accesses to the map's contents via its collection views and iterators do
 * not affect the map's traversal order, since the collection views do not
 * call <code>put</code> or <code>get</code>.
 * <p>
 *
 * One of the nice features of tracking insertion order is that you can
 * copy a hashtable, and regardless of the implementation of the original,
 * produce the same results when iterating over the copy.  This is possible
 * without needing the overhead of <code>TreeMap</code>.
 * <p>
 *
 * When using this {@link #LinkedHashMap(int, float, boolean) constructor},
 * you can build an access-order mapping.  This can be used to implement LRU
 * caches, for example.  By overriding {@link #removeEldestEntry(Map.Entry)},
 * you can also control the removal of the oldest entry, and thereby do
 * things like keep the map at a fixed size.
 * <p>
 *
 * Under ideal circumstances (no collisions), LinkedHashMap offers O(1) 
 * performance on most operations (<code>containsValue()</code> is,
 * of course, O(n)).  In the worst case (all keys map to the same 
 * hash code -- very unlikely), most operations are O(n).  Traversal is
 * faster than in HashMap (proportional to the map size, and not the space
 * allocated for the map), but other operations may be slower because of the
 * overhead of the maintaining the traversal order list.
 * <p>
 *
 * LinkedHashMap accepts the null key and null values.  It is not
 * synchronized, so if you need multi-threaded access, consider using:<br>
 * <code>Map m = Collections.synchronizedMap(new LinkedHashMap(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Object#hashCode()
 * @see Collection
 * @see Map
 * @see HashMap
 * @see TreeMap
 * @see Hashtable
 * @since 1.4
 * @status updated to 1.4
 */
public interface LinkedHashMap
  extends fabric.util.HashMap
{
    
    public fabric.util.LinkedHashMap.LinkedHashEntry get$root();
    
    public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
      fabric.util.LinkedHashMap.LinkedHashEntry val);
    
    public boolean get$accessOrder();
    
    public boolean set$accessOrder(boolean val);
    
    /**
     * Class to represent an entry in the hash table. Holds a single key-value
     * pair and the doubly-linked insertion order list.
     */
    public static interface LinkedHashEntry
      extends HashEntry
    {
        
        public fabric.util.LinkedHashMap get$out$();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$pred();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$succ();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        /**
         * Simple constructor.
         *
         * @param key the key
         * @param value the value
         */
        public LinkedHashEntry fabric$util$LinkedHashMap$LinkedHashEntry$(
          fabric.lang.Object key, fabric.lang.Object value);
        
        /**
         * Called when this entry is accessed via put or get. This version does
         * the necessary bookkeeping to keep the doubly-linked list in order,
         * after moving this element to the newest position in access order.
         */
        public void access();
        
        /**
         * Called when this entry is removed from the map. This version does
         * the necessary bookkeeping to keep the doubly-linked list in order.
         *
         * @return the value of this key as it is removed
         */
        public fabric.lang.Object cleanup();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.HashMap.HashEntry._Proxy
          implements LinkedHashEntry
        {
            
            public fabric.util.LinkedHashMap get$out$() {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry._Impl)
                          fetch()).get$out$();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$pred() {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry._Impl)
                          fetch()).get$pred();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry._Impl)
                          fetch()).set$pred(val);
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$succ() {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry._Impl)
                          fetch()).get$succ();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry._Impl)
                          fetch()).set$succ(val);
            }
            
            public native fabric.util.LinkedHashMap.LinkedHashEntry
              fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object arg1, fabric.lang.Object arg2);
            
            public _Proxy(LinkedHashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.HashMap.HashEntry._Impl
          implements LinkedHashEntry
        {
            
            public fabric.util.LinkedHashMap get$out$() { return this.out$; }
            
            private fabric.util.LinkedHashMap out$;
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$pred() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.pred;
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$pred(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.pred = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
             * The predecessor in the iteration list. If this entry is the root
             * (eldest), pred points to the newest entry.
             */
            LinkedHashEntry pred;
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$succ() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.succ;
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$succ(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.succ = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The successor in the iteration list, null if this is the newest.
             */
            LinkedHashEntry succ;
            
            /**
             * Simple constructor.
             *
             * @param key the key
             * @param value the value
             */
            public native LinkedHashEntry
              fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object key, fabric.lang.Object value);
            
            /**
             * Called when this entry is accessed via put or get. This version
             does
             * the necessary bookkeeping to keep the doubly-linked list in
             order,
             * after moving this element to the newest position in access order.
             */
            public native void access();
            
            /**
             * Called when this entry is removed from the map. This version does
             * the necessary bookkeeping to keep the doubly-linked list in
             order.
             *
             * @return the value of this key as it is removed
             */
            public native fabric.lang.Object cleanup();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedHashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedHashMap.LinkedHashEntry._Proxy(
                  this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.pred, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.succ, refTypes, out, intraStoreRefs,
                          interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.LinkedHashMap)
                              $readRef(fabric.util.LinkedHashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.pred =
                  (fabric.util.LinkedHashMap.LinkedHashEntry)
                    $readRef(
                      fabric.util.LinkedHashMap.LinkedHashEntry._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.succ =
                  (fabric.util.LinkedHashMap.LinkedHashEntry)
                    $readRef(
                      fabric.util.LinkedHashMap.LinkedHashEntry._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.LinkedHashMap.LinkedHashEntry._Impl src =
                  (fabric.util.LinkedHashMap.LinkedHashEntry._Impl) other;
                this.out$ = src.out$;
                this.pred = src.pred;
                this.succ = src.succ;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static
            {
                
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedHashMap.LinkedHashEntry.
                  _Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      LinkedHashMap.
                      LinkedHashEntry.
                      _Static.
                      _Impl impl =
                      (fabric.
                        util.
                        LinkedHashMap.
                        LinkedHashEntry.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.LinkedHashMap.LinkedHashEntry._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.LinkedHashMap.LinkedHashEntry._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static
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
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.LinkedHashMap.LinkedHashEntry.
                      _Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    /**
     * Construct a new insertion-ordered LinkedHashMap with the default
     * capacity (11) and the default load factor (0.75).
     */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
    
    /**
     * Construct a new insertion-ordered LinkedHashMap from the given Map,
     * with initial capacity the greater of the size of <code>m</code> or
     * the default of 11.
     * <p>
     *
     * Every element in Map m will be put into this new HashMap, in the
     * order of m's iterator.
     *
     * @param m a Map whose key / value pairs will be put into
     *          the new HashMap.  <b>NOTE: key / value pairs
     *          are not cloned in this constructor.</b>
     * @throws NullPointerException if m is null
     */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      fabric.util.Map m);
    
    /**
     * Construct a new insertion-ordered LinkedHashMap with a specific
     * inital capacity and default load factor of 0.75.
     *
     * @param initialCapacity the initial capacity of this HashMap (&gt;= 0)
     * @throws IllegalArgumentException if (initialCapacity &lt; 0)
     */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity);
    
    /**
     * Construct a new insertion-orderd LinkedHashMap with a specific
     * inital capacity and load factor.
     *
     * @param initialCapacity the initial capacity (&gt;= 0)
     * @param loadFactor the load factor (&gt; 0, not NaN)
     * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
     *                                     ! (loadFactor &gt; 0.0)
     */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor);
    
    /**
     * Construct a new LinkedHashMap with a specific inital capacity, load
     * factor, and ordering mode.
     *
     * @param initialCapacity the initial capacity (&gt;=0)
     * @param loadFactor the load factor (&gt;0, not NaN)
     * @param accessOrder true for access-order, false for insertion-order
     * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
     *                                     ! (loadFactor &gt; 0.0)
     */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
      int initialCapacity, float loadFactor, boolean accessOrder);
    
    /**
     * Clears the Map so it has no keys. This is O(1).
     */
    public void clear();
    
    /**
     * Returns <code>true</code> if this HashMap contains a value
     * <code>o</code>, such that <code>o.equals(value)</code>.
     *
     * @param value the value to search for in this HashMap
     * @return <code>true</code> if at least one key maps to the value
     */
    public boolean containsValue(fabric.lang.Object value);
    
    /**
     * Return the value in this Map associated with the supplied key,
     * or <code>null</code> if the key maps to nothing.  If this is an
     * access-ordered Map and the key is found, this performs structural
     * modification, moving the key to the newest end of the list. NOTE:
     * Since the value could also be null, you must use containsKey to
     * see if this key actually maps to something.
     *
     * @param key the key for which to fetch an associated value
     * @return what the key maps to, if present
     * @see #put(Object, Object)
     * @see #containsKey(Object)
     */
    public fabric.lang.Object get(fabric.lang.Object key);
    
    /**
     * Returns <code>true</code> if this map should remove the eldest entry.
     * This method is invoked by all calls to <code>put</code> and
     * <code>putAll</code> which place a new entry in the map, providing
     * the implementer an opportunity to remove the eldest entry any time
     * a new one is added.  This can be used to save memory usage of the
     * hashtable, as well as emulating a cache, by deleting stale entries.
     * <p>
     *
     * For example, to keep the Map limited to 100 entries, override as follows:
     * <pre>
     * private static final int MAX_ENTRIES = 100;
     * protected boolean removeEldestEntry(Map.Entry eldest)
     * {
     *   return size() &gt; MAX_ENTRIES;
     * }
     * </pre><p>
     *
     * Typically, this method does not modify the map, but just uses the
     * return value as an indication to <code>put</code> whether to proceed.
     * However, if you override it to modify the map, you must return false
     * (indicating that <code>put</code> should leave the modified map alone),
     * or you face unspecified behavior.  Remember that in access-order mode,
     * even calling <code>get</code> is a structural modification, but using
     * the collections views (such as <code>keySet</code>) is not.
     * <p>
     *
     * This method is called after the eldest entry has been inserted, so
     * if <code>put</code> was called on a previously empty map, the eldest
     * entry is the one you just put in! The default implementation just
     * returns <code>false</code>, so that this map always behaves like
     * a normal one with unbounded growth.
     *
     * @param eldest the eldest element which would be removed if this
     *        returns true. For an access-order map, this is the least
     *        recently accessed; for an insertion-order map, this is the
     *        earliest element inserted.
     * @return true if <code>eldest</code> should be removed
     */
    public boolean removeEldestEntry(Entry eldest);
    
    /**
     * Helper method called by <code>put</code>, which creates and adds a
     * new Entry, followed by performing bookkeeping (like removeEldestEntry).
     *
     * @param key the key of the new Entry
     * @param value the value
     * @param idx the index in buckets where the new Entry belongs
     * @param callRemove whether to call the removeEldestEntry method
     * @see #put(Object, Object)
     * @see #removeEldestEntry(Map.Entry)
     * @see LinkedHashEntry#LinkedHashEntry(Object, Object)
     */
    public void addEntry(fabric.lang.Object key, fabric.lang.Object value,
                         int idx, boolean callRemove);
    
    /**
     * Helper method, called by clone() to reset the doubly-linked list.
     *
     * @param m the map to add entries from
     * @see #clone()
     */
    public void putAllInternal(fabric.util.Map m);
    
    /**
     * Generates a parameterized iterator. This allows traversal to follow
     * the doubly-linked list instead of the random bin order of HashMap.
     *
     * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
     * @return the appropriate iterator
     */
    public fabric.util.Iterator iterator(fabric.worker.Store store,
                                         final int type);
    
    public static interface LinkedHashIterator
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
        public fabric.util.LinkedHashMap get$out$();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$current();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$last();
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
          fabric.util.LinkedHashMap.LinkedHashEntry val);
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$type();
        
        public int set$type(int val);
        
        public int postInc$type();
        
        public int postDec$type();
        
        public LinkedHashIterator fabric$util$LinkedHashMap$LinkedHashIterator$(
          int type);
        
        /**
         * Returns true if the Iterator has more elements.
         *
         * @return true if there are more elements
         */
        public boolean hasNext();
        
        /**
         * Returns the next element in the Iterator's sequential view.
         *
         * @return the next element
         * @throws ConcurrentModificationException if the HashMap was modified
         * @throws NoSuchElementException if there is none
         */
        public fabric.lang.Object next();
        
        /**
         * Removes from the backing HashMap the last element which was fetched
         * with the <code>next()</code> method.
         *
         * @throws ConcurrentModificationException if the HashMap was modified
         * @throws IllegalStateException if called when there is no last element
         */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements LinkedHashIterator
        {
            
            public fabric.util.LinkedHashMap get$out$() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).get$out$();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$current() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).get$current();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).set$current(val);
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$last() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).get$last();
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).set$last(val);
            }
            
            public int get$knownMod() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).postDec$knownMod();
            }
            
            public int get$type() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).get$type();
            }
            
            public int set$type(int val) {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).set$type(val);
            }
            
            public int postInc$type() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).postInc$type();
            }
            
            public int postDec$type() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator._Impl)
                          fetch()).postDec$type();
            }
            
            public native fabric.util.LinkedHashMap.LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int arg1);
            
            public native boolean hasNext();
            
            public native fabric.lang.Object next();
            
            public native void remove();
            
            public _Proxy(LinkedHashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements LinkedHashIterator
        {
            
            public fabric.util.LinkedHashMap get$out$() { return this.out$; }
            
            private fabric.util.LinkedHashMap out$;
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$current() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.current;
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$current(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.current = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The current Entry. */
            LinkedHashEntry current;
            
            public fabric.util.LinkedHashMap.LinkedHashEntry get$last() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.last;
            }
            
            public fabric.util.LinkedHashMap.LinkedHashEntry set$last(
              fabric.util.LinkedHashMap.LinkedHashEntry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.last = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The previous Entry returned by next(). */
            LinkedHashEntry last;
            
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
            
            /** The number of known modifications to the backing Map. */
            int knownMod;
            
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
            
            int type;
            
            public native LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int type);
            
            /**
             * Returns true if the Iterator has more elements.
             *
             * @return true if there are more elements
             */
            public native boolean hasNext();
            
            /**
             * Returns the next element in the Iterator's sequential view.
             *
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
             *
             * @throws ConcurrentModificationException if the HashMap was
             modified
             * @throws IllegalStateException if called when there is no last
             element
             */
            public native void remove();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedHashMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedHashMap.LinkedHashIterator._Proxy(
                  this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.current, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.last, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                out.writeInt(this.knownMod);
                out.writeInt(this.type);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.LinkedHashMap)
                              $readRef(fabric.util.LinkedHashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.current =
                  (fabric.util.LinkedHashMap.LinkedHashEntry)
                    $readRef(
                      fabric.util.LinkedHashMap.LinkedHashEntry._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.last =
                  (fabric.util.LinkedHashMap.LinkedHashEntry)
                    $readRef(
                      fabric.util.LinkedHashMap.LinkedHashEntry._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.knownMod = in.readInt();
                this.type = in.readInt();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.LinkedHashMap.LinkedHashIterator._Impl src =
                  (fabric.util.LinkedHashMap.LinkedHashIterator._Impl) other;
                this.out$ = src.out$;
                this.current = src.current;
                this.last = src.last;
                this.knownMod = src.knownMod;
                this.type = src.type;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static
            {
                
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedHashMap.
                  LinkedHashIterator._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      LinkedHashMap.
                      LinkedHashIterator.
                      _Static.
                      _Impl impl =
                      (fabric.
                        util.
                        LinkedHashMap.
                        LinkedHashIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.LinkedHashMap.LinkedHashIterator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.LinkedHashMap.LinkedHashIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static
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
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.LinkedHashMap.LinkedHashIterator.
                      _Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.HashMap._Proxy
      implements fabric.util.LinkedHashMap
    {
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$root() {
            return ((fabric.util.LinkedHashMap._Impl) fetch()).get$root();
        }
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
          fabric.util.LinkedHashMap.LinkedHashEntry val) {
            return ((fabric.util.LinkedHashMap._Impl) fetch()).set$root(val);
        }
        
        public boolean get$accessOrder() {
            return ((fabric.util.LinkedHashMap._Impl) fetch()).get$accessOrder(
                                                                 );
        }
        
        public boolean set$accessOrder(boolean val) {
            return ((fabric.util.LinkedHashMap._Impl) fetch()).set$accessOrder(
                                                                 val);
        }
        
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
        
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          fabric.util.Map arg1);
        
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1);
        
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2);
        
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2, boolean arg3);
        
        public native boolean removeEldestEntry(fabric.util.Map.Entry arg1);
        
        public _Proxy(LinkedHashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashMap._Impl
      implements fabric.util.LinkedHashMap
    {
        
        public fabric.util.LinkedHashMap.LinkedHashEntry get$root() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.root;
        }
        
        public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
          fabric.util.LinkedHashMap.LinkedHashEntry val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.root = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The oldest Entry to begin iteration at.
         */
        LinkedHashEntry root;
        
        public boolean get$accessOrder() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.accessOrder;
        }
        
        public boolean set$accessOrder(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.accessOrder = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The iteration order of this linked hash map: <code>true</code> for
         * access-order, <code>false</code> for insertion-order.
         *
         * @serial true for access order traversal
         */
        boolean accessOrder;
        
        /**
         * Construct a new insertion-ordered LinkedHashMap with the default
         * capacity (11) and the default load factor (0.75).
         */
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$();
        
        /**
         * Construct a new insertion-ordered LinkedHashMap from the given Map,
         * with initial capacity the greater of the size of <code>m</code> or
         * the default of 11.
         * <p>
         *
         * Every element in Map m will be put into this new HashMap, in the
         * order of m's iterator.
         *
         * @param m a Map whose key / value pairs will be put into
         *          the new HashMap.  <b>NOTE: key / value pairs
         *          are not cloned in this constructor.</b>
         * @throws NullPointerException if m is null
         */
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          fabric.util.Map m);
        
        /**
         * Construct a new insertion-ordered LinkedHashMap with a specific
         * inital capacity and default load factor of 0.75.
         *
         * @param initialCapacity the initial capacity of this HashMap (&gt;= 0)
         * @throws IllegalArgumentException if (initialCapacity &lt; 0)
         */
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity);
        
        /**
         * Construct a new insertion-orderd LinkedHashMap with a specific
         * inital capacity and load factor.
         *
         * @param initialCapacity the initial capacity (&gt;= 0)
         * @param loadFactor the load factor (&gt; 0, not NaN)
         * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
         *                                     ! (loadFactor &gt; 0.0)
         */
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity, float loadFactor);
        
        /**
         * Construct a new LinkedHashMap with a specific inital capacity, load
         * factor, and ordering mode.
         *
         * @param initialCapacity the initial capacity (&gt;=0)
         * @param loadFactor the load factor (&gt;0, not NaN)
         * @param accessOrder true for access-order, false for insertion-order
         * @throws IllegalArgumentException if (initialCapacity &lt; 0) ||
         *                                     ! (loadFactor &gt; 0.0)
         */
        public native fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity, float loadFactor, boolean accessOrder);
        
        /**
         * Clears the Map so it has no keys. This is O(1).
         */
        public native void clear();
        
        /**
         * Returns <code>true</code> if this HashMap contains a value
         * <code>o</code>, such that <code>o.equals(value)</code>.
         *
         * @param value the value to search for in this HashMap
         * @return <code>true</code> if at least one key maps to the value
         */
        public native boolean containsValue(fabric.lang.Object value);
        
        /**
         * Return the value in this Map associated with the supplied key,
         * or <code>null</code> if the key maps to nothing.  If this is an
         * access-ordered Map and the key is found, this performs structural
         * modification, moving the key to the newest end of the list. NOTE:
         * Since the value could also be null, you must use containsKey to
         * see if this key actually maps to something.
         *
         * @param key the key for which to fetch an associated value
         * @return what the key maps to, if present
         * @see #put(Object, Object)
         * @see #containsKey(Object)
         */
        public native fabric.lang.Object get(fabric.lang.Object key);
        
        /**
         * Returns <code>true</code> if this map should remove the eldest entry.
         * This method is invoked by all calls to <code>put</code> and
         * <code>putAll</code> which place a new entry in the map, providing
         * the implementer an opportunity to remove the eldest entry any time
         * a new one is added.  This can be used to save memory usage of the
         * hashtable, as well as emulating a cache, by deleting stale entries.
         * <p>
         *
         * For example, to keep the Map limited to 100 entries, override as
         follows:
         * <pre>
         * private static final int MAX_ENTRIES = 100;
         * protected boolean removeEldestEntry(Map.Entry eldest)
         * {
         *   return size() &gt; MAX_ENTRIES;
         * }
         * </pre><p>
         *
         * Typically, this method does not modify the map, but just uses the
         * return value as an indication to <code>put</code> whether to proceed.
         * However, if you override it to modify the map, you must return false
         * (indicating that <code>put</code> should leave the modified map
         alone),
         * or you face unspecified behavior.  Remember that in access-order
         mode,
         * even calling <code>get</code> is a structural modification, but using
         * the collections views (such as <code>keySet</code>) is not.
         * <p>
         *
         * This method is called after the eldest entry has been inserted, so
         * if <code>put</code> was called on a previously empty map, the eldest
         * entry is the one you just put in! The default implementation just
         * returns <code>false</code>, so that this map always behaves like
         * a normal one with unbounded growth.
         *
         * @param eldest the eldest element which would be removed if this
         *        returns true. For an access-order map, this is the least
         *        recently accessed; for an insertion-order map, this is the
         *        earliest element inserted.
         * @return true if <code>eldest</code> should be removed
         */
        public native boolean removeEldestEntry(Entry eldest);
        
        /**
         * Helper method called by <code>put</code>, which creates and adds a
         * new Entry, followed by performing bookkeeping (like
         removeEldestEntry).
         *
         * @param key the key of the new Entry
         * @param value the value
         * @param idx the index in buckets where the new Entry belongs
         * @param callRemove whether to call the removeEldestEntry method
         * @see #put(Object, Object)
         * @see #removeEldestEntry(Map.Entry)
         * @see LinkedHashEntry#LinkedHashEntry(Object, Object)
         */
        public native void addEntry(fabric.lang.Object key,
                                    fabric.lang.Object value, int idx,
                                    boolean callRemove);
        
        /**
         * Helper method, called by clone() to reset the doubly-linked list.
         *
         * @param m the map to add entries from
         * @see #clone()
         */
        public native void putAllInternal(fabric.util.Map m);
        
        /**
         * Generates a parameterized iterator. This allows traversal to follow
         * the doubly-linked list instead of the random bin order of HashMap.
         *
         * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
         * @return the appropriate iterator
         */
        public native fabric.util.Iterator iterator(fabric.worker.Store store,
                                                    final int type);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.LinkedHashMap._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.root, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeBoolean(this.accessOrder);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.root =
              (fabric.util.LinkedHashMap.LinkedHashEntry)
                $readRef(fabric.util.LinkedHashMap.LinkedHashEntry._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.accessOrder = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.LinkedHashMap._Impl src =
              (fabric.util.LinkedHashMap._Impl) other;
            this.root = src.root;
            this.accessOrder = src.accessOrder;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashMap._Static
        {
            
            public long get$serialVersionUID() {
                return ((fabric.util.LinkedHashMap._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.LinkedHashMap._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.LinkedHashMap._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.LinkedHashMap._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.LinkedHashMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.LinkedHashMap._Static $instance;
            
            static {
                fabric.
                  util.
                  LinkedHashMap.
                  _Static.
                  _Impl impl =
                  (fabric.util.LinkedHashMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.LinkedHashMap._Static._Impl.class);
                $instance = (fabric.util.LinkedHashMap._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashMap._Static
        {
            
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
             * Compatible with JDK 1.4.
             */
            private long serialVersionUID;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.serialVersionUID);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedHashMap._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
