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
        
        public static final byte[] $classHash = new byte[] { -90, 53, 117, -13,
        93, -107, -47, 123, 55, -125, -79, -53, 46, 63, 116, 44, 58, -31, 55,
        117, -27, -109, 39, -101, -88, 39, -55, -14, 123, -24, -49, 114 };
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
        
        public static final byte[] $classHash = new byte[] { 82, 111, -4, -112,
        -121, -84, -13, 110, 116, -72, -65, 106, 79, -72, 15, -39, -28, -57,
        -16, -117, 5, -34, -61, -71, -66, 18, -50, -95, -4, 62, -9, -34 };
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
                        fabric.worker.transaction.TransactionManager $tm789 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled792 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff790 = 1;
                        boolean $doBackoff791 = true;
                        boolean $retry785 = true;
                        boolean $keepReads786 = false;
                        $label783: for (boolean $commit784 = false; !$commit784;
                                        ) {
                            if ($backoffEnabled792) {
                                if ($doBackoff791) {
                                    if ($backoff790 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff790));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e787) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff790 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff790 =
                                          java.lang.Math.
                                            min(
                                              $backoff790 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff791 = $backoff790 <= 32 ||
                                                  !$doBackoff791;
                            }
                            $commit784 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.LinkedHashMap._Static._Proxy.
                                  $instance.
                                  set$serialVersionUID((long)
                                                         3801124242820219131L);
                            }
                            catch (final fabric.worker.RetryException $e787) {
                                $commit784 = false;
                                continue $label783;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e787) {
                                $commit784 = false;
                                $retry785 = false;
                                $keepReads786 = $e787.keepReads;
                                fabric.common.TransactionID $currentTid788 =
                                  $tm789.getCurrentTid();
                                if ($e787.tid ==
                                      null ||
                                      !$e787.tid.isDescendantOf(
                                                   $currentTid788)) {
                                    throw $e787;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e787);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e787) {
                                $commit784 = false;
                                fabric.common.TransactionID $currentTid788 =
                                  $tm789.getCurrentTid();
                                if ($e787.tid.isDescendantOf($currentTid788))
                                    continue $label783;
                                if ($currentTid788.parent != null) {
                                    $retry785 = false;
                                    throw $e787;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e787) {
                                $commit784 = false;
                                $retry785 = false;
                                if ($tm789.inNestedTxn()) {
                                    $keepReads786 = true;
                                }
                                throw $e787;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid788 =
                                  $tm789.getCurrentTid();
                                if ($commit784) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e787) {
                                        $commit784 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e787) {
                                        $commit784 = false;
                                        $retry785 = false;
                                        $keepReads786 = $e787.keepReads;
                                        if ($e787.tid ==
                                              null ||
                                              !$e787.tid.isDescendantOf(
                                                           $currentTid788))
                                            throw $e787;
                                        throw new fabric.worker.
                                                UserAbortException($e787);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e787) {
                                        $commit784 = false;
                                        $currentTid788 = $tm789.getCurrentTid();
                                        if ($currentTid788 != null) {
                                            if ($e787.tid.equals(
                                                            $currentTid788) ||
                                                  !$e787.tid.
                                                  isDescendantOf(
                                                    $currentTid788)) {
                                                throw $e787;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm789.inNestedTxn() &&
                                          $tm789.checkForStaleObjects()) {
                                        $retry785 = true;
                                        $keepReads786 = false;
                                    }
                                    if ($keepReads786) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e787) {
                                            $currentTid788 = $tm789.getCurrentTid();
                                            if ($currentTid788 != null &&
                                                  ($e787.tid.equals($currentTid788) || !$e787.tid.isDescendantOf($currentTid788))) {
                                                throw $e787;
                                            } else {
                                                $retry785 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit784) {
                                    {  }
                                    if ($retry785) { continue $label783; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 108, 118, 55, 41, -8,
    119, -60, 16, -70, -94, -71, -32, -53, 56, 95, 38, -123, 0, -3, -26, -10,
    104, -34, 64, 107, 56, -114, -62, 126, -20, -56, 56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
