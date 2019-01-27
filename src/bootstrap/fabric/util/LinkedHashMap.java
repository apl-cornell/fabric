package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface LinkedHashMap extends fabric.util.HashMap {
    public fabric.util.LinkedHashMap.LinkedHashEntry get$root();
    
    public fabric.util.LinkedHashMap.LinkedHashEntry set$root(
      fabric.util.LinkedHashMap.LinkedHashEntry val);
    
    public boolean get$accessOrder();
    
    public boolean set$accessOrder(boolean val);
    
    /**
   * Class to represent an entry in the hash table. Holds a single key-value
   * pair and the doubly-linked insertion order list.
   */
    public static interface LinkedHashEntry extends HashEntry {
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
        public LinkedHashEntry
          fabric$util$LinkedHashMap$LinkedHashEntry$(fabric.lang.Object key, fabric.lang.Object value);
        
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
          implements LinkedHashEntry {
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
            
            public fabric.util.LinkedHashMap.LinkedHashEntry
              fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object arg1, fabric.lang.Object arg2) {
                return ((fabric.util.LinkedHashMap.LinkedHashEntry) fetch()).
                  fabric$util$LinkedHashMap$LinkedHashEntry$(arg1, arg2);
            }
            
            public _Proxy(LinkedHashEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.HashMap.HashEntry._Impl
          implements LinkedHashEntry {
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
            
            /** The successor in the iteration list, null if this is the newest. */
            LinkedHashEntry succ;
            
            /**
     * Simple constructor.
     *
     * @param key the key
     * @param value the value
     */
            public LinkedHashEntry fabric$util$LinkedHashMap$LinkedHashEntry$(
              fabric.lang.Object key, fabric.lang.Object value) {
                fabric$util$HashMap$HashEntry$(key, value);
                if (fabric.lang.Object._Proxy.idEquals(
                                                this.get$out$().get$root(),
                                                null)) {
                    this.get$out$().set$root((LinkedHashEntry)
                                               this.$getProxy());
                    this.set$pred((LinkedHashEntry) this.$getProxy());
                } else {
                    this.set$pred(this.get$out$().get$root().get$pred());
                    this.get$pred().set$succ((LinkedHashEntry)
                                               this.$getProxy());
                    this.get$out$().get$root().set$pred((LinkedHashEntry)
                                                          this.$getProxy());
                }
                return (LinkedHashEntry) this.$getProxy();
            }
            
            /**
     * Called when this entry is accessed via put or get. This version does
     * the necessary bookkeeping to keep the doubly-linked list in order,
     * after moving this element to the newest position in access order.
     */
            public void access() {
                if (this.get$out$().get$accessOrder() &&
                      !fabric.lang.Object._Proxy.idEquals(this.get$succ(),
                                                          null)) {
                    this.get$out$().postInc$modCount();
                    if (fabric.lang.Object._Proxy.idEquals(
                                                    (LinkedHashEntry)
                                                      this.$getProxy(),
                                                    this.get$out$().get$root(
                                                                      ))) {
                        this.get$out$().set$root(this.get$succ());
                        this.get$pred().set$succ((LinkedHashEntry)
                                                   this.$getProxy());
                        this.set$succ(null);
                    } else {
                        this.get$pred().set$succ(this.get$succ());
                        this.get$succ().set$pred(this.get$pred());
                        this.set$succ(null);
                        this.set$pred(this.get$out$().get$root().get$pred());
                        this.get$pred().set$succ((LinkedHashEntry)
                                                   this.$getProxy());
                        this.get$out$().get$root().set$pred((LinkedHashEntry)
                                                              this.$getProxy());
                    }
                }
            }
            
            /**
     * Called when this entry is removed from the map. This version does
     * the necessary bookkeeping to keep the doubly-linked list in order.
     *
     * @return the value of this key as it is removed
     */
            public fabric.lang.Object cleanup() {
                if (fabric.lang.Object._Proxy.idEquals(
                                                (LinkedHashEntry)
                                                  this.$getProxy(),
                                                this.get$out$().get$root())) {
                    this.get$out$().set$root(this.get$succ());
                    if (!fabric.lang.Object._Proxy.idEquals(this.get$succ(),
                                                            null))
                        this.get$succ().set$pred(this.get$pred());
                } else if (fabric.lang.Object._Proxy.idEquals(this.get$succ(),
                                                              null)) {
                    this.get$pred().set$succ(null);
                    this.get$out$().get$root().set$pred(this.get$pred());
                } else {
                    this.get$pred().set$succ(this.get$succ());
                    this.get$succ().set$pred(this.get$pred());
                }
                return this.get$value();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (LinkedHashEntry) this.$getProxy();
            }
            
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
                this.out$ = (fabric.util.LinkedHashMap)
                              $readRef(fabric.util.LinkedHashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
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
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static {
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashEntry._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedHashMap.LinkedHashEntry.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      LinkedHashMap.
                      LinkedHashEntry.
                      _Static.
                      _Impl
                      impl =
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
              implements fabric.util.LinkedHashMap.LinkedHashEntry._Static {
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
                    return new fabric.util.LinkedHashMap.LinkedHashEntry.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -69, 120, -77, 116,
        -7, -101, -114, 37, 60, 73, -53, -91, -104, 51, -84, -98, -45, 12, 78,
        36, 15, -38, 72, -24, 48, 31, -123, 12, 30, 93, 41, -122 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
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
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(fabric.util.Map m);
    
    /**
   * Construct a new insertion-ordered LinkedHashMap with a specific
   * inital capacity and default load factor of 0.75.
   *
   * @param initialCapacity the initial capacity of this HashMap (&gt;= 0)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0)
   */
    public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(int initialCapacity);
    
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
    public void addEntry(
      fabric.lang.Object key, fabric.lang.Object value, int idx, boolean callRemove);
    
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
      extends fabric.util.Iterator, fabric.lang.Object {
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
        
        public LinkedHashIterator fabric$util$LinkedHashMap$LinkedHashIterator$(int type);
        
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
          implements LinkedHashIterator {
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
            
            public fabric.util.LinkedHashMap.LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int arg1) {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator) fetch()).
                  fabric$util$LinkedHashMap$LinkedHashIterator$(arg1);
            }
            
            public boolean hasNext() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator) fetch()).
                  hasNext();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.LinkedHashMap.LinkedHashIterator) fetch()).
                  next();
            }
            
            public void remove() {
                ((fabric.util.LinkedHashMap.LinkedHashIterator) fetch()).remove(
                                                                           );
            }
            
            public _Proxy(LinkedHashIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements LinkedHashIterator {
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
            
            int type;
            
            public LinkedHashIterator
              fabric$util$LinkedHashMap$LinkedHashIterator$(int type) {
                this.set$type((int) type);
                fabric$lang$Object$();
                this.set$current(this.get$out$().get$root());
                this.set$knownMod((int) this.get$out$().get$modCount());
                return (LinkedHashIterator) this.$getProxy();
            }
            
            /**
     * Returns true if the Iterator has more elements.
     *
     * @return true if there are more elements
     */
            public boolean hasNext() {
                return !fabric.lang.Object._Proxy.idEquals(this.get$current(),
                                                           null);
            }
            
            /**
     * Returns the next element in the Iterator's sequential view.
     *
     * @return the next element
     * @throws ConcurrentModificationException if the HashMap was modified
     * @throws NoSuchElementException if there is none
     */
            public fabric.lang.Object next() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
                if (fabric.lang.Object._Proxy.idEquals(this.get$current(),
                                                       null))
                    throw new fabric.util.NoSuchElementException();
                this.set$last(this.get$current());
                this.set$current(this.get$current().get$succ());
                return this.
                  get$type() ==
                  fabric.util.AbstractMap._Static._Proxy.$instance.
                  get$VALUES()
                  ? this.
                  get$last().
                  get$value()
                  : (this.
                       get$type() ==
                       fabric.util.AbstractMap._Static._Proxy.$instance.
                       get$KEYS()
                       ? this.get$last().get$key()
                       : this.get$last());
            }
            
            /**
     * Removes from the backing HashMap the last element which was fetched
     * with the <code>next()</code> method.
     *
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
                return (LinkedHashIterator) this.$getProxy();
            }
            
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
                this.out$ = (fabric.util.LinkedHashMap)
                              $readRef(fabric.util.LinkedHashMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
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
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static {
                public _Proxy(fabric.util.LinkedHashMap.LinkedHashIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedHashMap.
                  LinkedHashIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      LinkedHashMap.
                      LinkedHashIterator.
                      _Static.
                      _Impl
                      impl =
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
              implements fabric.util.LinkedHashMap.LinkedHashIterator._Static {
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
                    return new fabric.util.LinkedHashMap.LinkedHashIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -84, 43, 3, 56,
        -107, -13, 50, 56, -115, 108, -117, -69, -118, -120, -46, -42, 35, 56,
        22, 43, -19, 22, -75, 20, 4, -110, -4, 55, 65, -30, 95, 56 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.HashMap._Proxy
      implements fabric.util.LinkedHashMap {
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
        
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$() {
            return ((fabric.util.LinkedHashMap) fetch()).
              fabric$util$LinkedHashMap$();
        }
        
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          fabric.util.Map arg1) {
            return ((fabric.util.LinkedHashMap) fetch()).
              fabric$util$LinkedHashMap$(arg1);
        }
        
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(int arg1) {
            return ((fabric.util.LinkedHashMap) fetch()).
              fabric$util$LinkedHashMap$(arg1);
        }
        
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2) {
            return ((fabric.util.LinkedHashMap) fetch()).
              fabric$util$LinkedHashMap$(arg1, arg2);
        }
        
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int arg1, float arg2, boolean arg3) {
            return ((fabric.util.LinkedHashMap) fetch()).
              fabric$util$LinkedHashMap$(arg1, arg2, arg3);
        }
        
        public boolean removeEldestEntry(fabric.util.Map.Entry arg1) {
            return ((fabric.util.LinkedHashMap) fetch()).removeEldestEntry(
                                                           arg1);
        }
        
        public _Proxy(LinkedHashMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashMap._Impl
      implements fabric.util.LinkedHashMap {
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
        
        public boolean get$accessOrder() { return this.accessOrder; }
        
        public boolean set$accessOrder(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.accessOrder = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        boolean accessOrder;
        
        /**
   * Construct a new insertion-ordered LinkedHashMap with the default
   * capacity (11) and the default load factor (0.75).
   */
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$() {
            this.set$accessOrder(false);
            fabric$util$HashMap$();
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
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
          fabric.util.Map m) {
            this.set$accessOrder(false);
            fabric$util$HashMap$(m);
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
        /**
   * Construct a new insertion-ordered LinkedHashMap with a specific
   * inital capacity and default load factor of 0.75.
   *
   * @param initialCapacity the initial capacity of this HashMap (&gt;= 0)
   * @throws IllegalArgumentException if (initialCapacity &lt; 0)
   */
        public fabric.util.LinkedHashMap fabric$util$LinkedHashMap$(
          int initialCapacity) {
            this.set$accessOrder(false);
            fabric$util$HashMap$(initialCapacity);
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
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
          int initialCapacity, float loadFactor) {
            this.set$accessOrder(false);
            fabric$util$HashMap$(initialCapacity, loadFactor);
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
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
          int initialCapacity, float loadFactor, boolean accessOrder) {
            this.set$accessOrder(accessOrder);
            fabric$util$HashMap$(initialCapacity, loadFactor);
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
        /**
   * Clears the Map so it has no keys. This is O(1).
   */
        public void clear() {
            super.clear();
            this.set$root(null);
        }
        
        /**
   * Returns <code>true</code> if this HashMap contains a value
   * <code>o</code>, such that <code>o.equals(value)</code>.
   *
   * @param value the value to search for in this HashMap
   * @return <code>true</code> if at least one key maps to the value
   */
        public boolean containsValue(fabric.lang.Object value) {
            LinkedHashEntry e = this.get$root();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(value, e.get$value()))
                    return true;
                e = e.get$succ();
            }
            return false;
        }
        
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
        public fabric.lang.Object get(fabric.lang.Object key) {
            int idx = hash(key);
            HashEntry e = (HashEntry) this.get$buckets().get(idx);
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractMap._Impl.equals(key, e.get$key())) {
                    e.access();
                    return e.get$value();
                }
                e = e.get$next();
            }
            return null;
        }
        
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
        public boolean removeEldestEntry(Entry eldest) { return false; }
        
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
                             int idx, boolean callRemove) {
            LinkedHashEntry
              e =
              (LinkedHashEntry)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashMap.LinkedHashEntry)
                     new fabric.util.LinkedHashMap.LinkedHashEntry._Impl(
                       this.$getStore(),
                       (fabric.util.LinkedHashMap) this.$getProxy()).$getProxy(
                                                                       )).
                      fabric$util$LinkedHashMap$LinkedHashEntry$(key, value));
            e.set$next((HashEntry) this.get$buckets().get(idx));
            this.get$buckets().set(idx, e);
            if (callRemove && removeEldestEntry(this.get$root()))
                remove(this.get$root().get$key());
        }
        
        /**
   * Helper method, called by clone() to reset the doubly-linked list.
   *
   * @param m the map to add entries from
   * @see #clone()
   */
        public void putAllInternal(fabric.util.Map m) {
            this.set$root(null);
            super.putAllInternal(m);
        }
        
        /**
   * Generates a parameterized iterator. This allows traversal to follow
   * the doubly-linked list instead of the random bin order of HashMap.
   *
   * @param type {@link #KEYS}, {@link #VALUES}, or {@link #ENTRIES}
   * @return the appropriate iterator
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store,
                                             final int type) {
            return (LinkedHashIterator)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.LinkedHashMap.LinkedHashIterator)
                          new fabric.util.LinkedHashMap.LinkedHashIterator.
                            _Impl(store,
                                  (fabric.util.LinkedHashMap) this.$getProxy()).
                          $getProxy()).
                           fabric$util$LinkedHashMap$LinkedHashIterator$(type));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.LinkedHashMap) this.$getProxy();
        }
        
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
          implements fabric.util.LinkedHashMap._Static {
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
                  _Impl
                  impl =
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
          implements fabric.util.LinkedHashMap._Static {
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
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedHashMap._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm801 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled804 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff802 = 1;
                        boolean $doBackoff803 = true;
                        boolean $retry797 = true;
                        boolean $keepReads798 = false;
                        $label795: for (boolean $commit796 = false; !$commit796;
                                        ) {
                            if ($backoffEnabled804) {
                                if ($doBackoff803) {
                                    if ($backoff802 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff802));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e799) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff802 < 5000) $backoff802 *= 2;
                                }
                                $doBackoff803 = $backoff802 <= 32 ||
                                                  !$doBackoff803;
                            }
                            $commit796 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.LinkedHashMap._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 3801124242820219131L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e799) {
                                    throw $e799;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e799) {
                                    throw $e799;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e799) {
                                    throw $e799;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e799) {
                                    throw $e799;
                                }
                                catch (final Throwable $e799) {
                                    $tm801.getCurrentLog().checkRetrySignal();
                                    throw $e799;
                                }
                            }
                            catch (final fabric.worker.RetryException $e799) {
                                $commit796 = false;
                                continue $label795;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e799) {
                                $commit796 = false;
                                $retry797 = false;
                                $keepReads798 = $e799.keepReads;
                                if ($tm801.checkForStaleObjects()) {
                                    $retry797 = true;
                                    $keepReads798 = false;
                                    continue $label795;
                                }
                                fabric.common.TransactionID $currentTid800 =
                                  $tm801.getCurrentTid();
                                if ($e799.tid ==
                                      null ||
                                      !$e799.tid.isDescendantOf(
                                                   $currentTid800)) {
                                    throw $e799;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e799);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e799) {
                                $commit796 = false;
                                fabric.common.TransactionID $currentTid800 =
                                  $tm801.getCurrentTid();
                                if ($e799.tid.isDescendantOf($currentTid800))
                                    continue $label795;
                                if ($currentTid800.parent != null) {
                                    $retry797 = false;
                                    throw $e799;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e799) {
                                $commit796 = false;
                                if ($tm801.checkForStaleObjects())
                                    continue $label795;
                                fabric.common.TransactionID $currentTid800 =
                                  $tm801.getCurrentTid();
                                if ($e799.tid.isDescendantOf($currentTid800)) {
                                    $retry797 = true;
                                }
                                else if ($currentTid800.parent != null) {
                                    $retry797 = false;
                                    throw $e799;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e799) {
                                $commit796 = false;
                                if ($tm801.checkForStaleObjects())
                                    continue $label795;
                                $retry797 = false;
                                throw new fabric.worker.AbortException($e799);
                            }
                            finally {
                                if ($commit796) {
                                    fabric.common.TransactionID $currentTid800 =
                                      $tm801.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e799) {
                                        $commit796 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e799) {
                                        $commit796 = false;
                                        $retry797 = false;
                                        $keepReads798 = $e799.keepReads;
                                        if ($tm801.checkForStaleObjects()) {
                                            $retry797 = true;
                                            $keepReads798 = false;
                                            continue $label795;
                                        }
                                        if ($e799.tid ==
                                              null ||
                                              !$e799.tid.isDescendantOf(
                                                           $currentTid800))
                                            throw $e799;
                                        throw new fabric.worker.
                                                UserAbortException($e799);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e799) {
                                        $commit796 = false;
                                        $currentTid800 = $tm801.getCurrentTid();
                                        if ($currentTid800 != null) {
                                            if ($e799.tid.equals(
                                                            $currentTid800) ||
                                                  !$e799.tid.
                                                  isDescendantOf(
                                                    $currentTid800)) {
                                                throw $e799;
                                            }
                                        }
                                    }
                                } else if ($keepReads798) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit796) {
                                    {  }
                                    if ($retry797) { continue $label795; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 49, -34, 44, -35, 8,
    -44, 94, -84, 77, -70, 101, 70, 70, 53, -90, -65, 31, 96, 17, -66, -101,
    -70, 66, -113, 120, 43, -52, 73, 102, 47, 26, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
