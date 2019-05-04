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
        
        public static final byte[] $classHash = new byte[] { 42, 82, -91, 31,
        -62, 72, 58, -58, -84, -52, -69, -35, -23, -74, -77, 30, 31, 39, -70,
        -115, 99, 45, -59, -119, -50, 79, -53, 19, -1, -26, -89, 80 };
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
        
        public static final byte[] $classHash = new byte[] { 103, 40, -17, 5,
        -80, 59, 116, 119, 12, -30, 61, -97, 38, -90, 125, -80, -39, -114, 110,
        23, 114, 23, 30, 42, -119, 98, -104, -54, 63, 81, -47, -52 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1YDWwcRxV+d7bPv40d59eu4zjOERE3uVNaERouaUlOcXPknJg4iYRDa+Z25+yN93a3s3P2ueCqgCqHIlmouEkqtUZVTUNTt0krVUigoCIBTX8EKqKFSlAipNJCiKBCFCQo5c3M3t/e2U0lLN/M3Mx7b968n2/e3OI1qHMZ9KZJyjAjfMqhbqSfpBLJQcJcqsdN4rpHcXZEa65NnH73nN4dhGASWjRi2ZahEXPEcjmsSJ4kEyRqUR49diQROwGNmmA8QNwxDsET+3IMehzbnBo1be5tUiH/oZuic2fuanuuBlqHodWwhjjhhha3LU5zfBhaMjSToszdq+tUH4aVFqX6EGUGMY17kNC2hqHdNUYtwrOMukeoa5sTgrDdzTqUyT3zk0J9G9VmWY3bDNVvU+pnuWFGk4bLY0kIpQ1q6u7dcC/UJqEubZJRJFybzJ8iKiVG+8U8kjcZqCZLE43mWWrHDUvnsNHPUThx+CASIGt9hvIxu7BVrUVwAtqVSiaxRqNDnBnWKJLW2VnchUPnkkKRqMEh2jgZpSMc1vvpBtUSUjVKswgWDmv8ZFIS+qzT57MSb107tHv2y9YBKwgB1Fmnmin0b0Cmbh/TEZqmjFoaVYwtfcnTZO2lU0EAJF7jI1Y03//Ke5/d1v3CZUVzYxWaw6mTVOMj2kJqxWtd8a27aoQaDY7tGiIUyk4uvTrorcRyDkb72oJEsRjJL75w5GdfuO88vRqEpgSENNvMZjCqVmp2xjFMyu6gFmWEUz0BjdTS43I9AfU4ThoWVbOH02mX8gTUmnIqZMvvaKI0ihAmqsexYaXt/NghfEyOcw4AtOEHagACfwPY8wH2CwCf+iaHgeiYnaHRlJmlkxjeUfxQwrSxKOYtM7Ttmu1MRV2mRVnW4gZSqvl8SFvjVBfJOECcCCri/L8F5sQJ2iYDATTuRs3WaYq46CkvavYNmpgYB2xTp2xEM2cvJWDVpYdl5DSKaHcxYqVtAujtLj9OlPLOZfftf++ZkVdU1Alez3QctikFlUfLFAwXvyW48CDmPIMWkV8RRKwIItZiIBeJzyeekmEUcmW+FSS3oOTPOCbhaZtlchAIyGOulvwluwngaNk6dOfnvnSqFz2YcyZr0ZeCNOxPoyL4JHBEMDdGtNaZd9+/cHraLiYUh3BFnldyijzt9duM2RrVEQeL4vt6yPMjl6bDQYExjQh/nGCAIpZ0+/coy9dYHvuENeqS0CxsQEyxlAesJj7G7MnijIyFFaJpV2EhjOVTUMLmniHn0d/8/E+3yAslj7CtJVA8RHmsJKuFsFaZvyuLtj/KKEW6350d/PZD12ZOSMMjxeZqG4ZFG8dsJjII7r9895u/f2vhV8GiszjUO8yYwCTPycOs/BD/Avj5r/iI3BQTokeEjnu40FMABkdsvaWoHEKEiTCFurvhY1bG1o20QVImFaHyn9ZP7Hj+L7Ntyt8mzijrMdj20QKK8x374L5X7vpntxQT0MQVVTRgkUzh3qqi5L2MkSmhR+6rv9zw8IvkUQx9RC3XuIdKIAp40SuUWsOhY8nsEgSd0tE3S+Ltst0hbCRFgFzbKZpeZdQuOS+qDv9V0S/u3GLMDkcXH+mM33ZVIUUhZoWMTVWQ4jgpSaebz2f+EewN/TQI9cPQJq97YvHjBPEOw2UYL2w37k0m4Yay9fLLV900sUJOdvnzpWRbf7YUEQrHglqMm1SCqPhCQ0gjdaGtzyHUv+/1b4nVVdK4q3MBkIPdkmWzbLeIZmuJgW/C0NWyDBOX53229XoQcb/F2ZT0ocpV0d5arlsH6nQeYGfc6yNVdIuL5jaOgUxUIO9dUhwOAk+jmDNe/0AVcf2euIZxy560BrA6qgyWQWZkEBcmvLqCnpp74MPI7JzKJ1V8ba6of0p5VAEmrXeDNGEOd9m03C6So/+dC9M//N70jCpO2stLif1WNvP0Gx+8Gjl75aUql1QNlonVDNMkDLMKDXIBDXLR689VMcxg9RhA4Go0MpksF+CQj4dCxB2stmW92DKA//8GuH0jjvsBmv9YZcvj1bcMimEfx4A3LGIW9sQKNSzGt8g9c9V5azxevGlFjS++DSsJJQBRwJ7VpXGcv8EV7KDDNixVo0pnLXxtbl4//N0dQS9NBnBT7yFRDkWbKh5AA7IsLyLKlasbdsXH3x5Vft/o29ZP/eTA4kt3bNEeDEJNAToq3gLlTLFywGhiFJ8y1tEy2Ogpz6VDaKZnAT5dq/qdPyj1n/SATCQfMHvYLkNDUpnLILe8dg0O25UXwsIL4Y+qr8LFiEsXNG7OA92PAG7t8/qOCo2XjPCQk02ZhpYrN0GTJ2i917eXCFzmWJPLrEk8xGytHyPuIXx8SqKEBxGiS+JayrZNSqxqZxSqvAyw66TX37nEGUXjVp5GsHzR649f32m+vsza/aK5F1PTyh8Fc6rdyylxxUXUFSeXOvzl7lLn+y1A7HWvv/zxzidYXvT6H1/f+b61zNqDosEnUojRjD1BqzmrdsI29GonQdwLvAOw+89e/8bHO4lged3rf3F9J3lkmbV50Zzh0Bw2LIMnSYqqxJzJiZKkIskkzCJ03VjlweX9BKDFf0IX3j64bc0Sj631FT/KeHzPzLc2rJs/9mv5XCg87xuxGk9nTbO0rCkZhxxG04Y8SqMqchzZPY5HKsFw9Ifo5NEeUxRPoPsUhfh2ThWVhWZG0nRmmfgpafHv6/4Vajh6RVbtaLme0U/+te5ijE+2/GHPd7Y8MX3xzVlrHVvX3feN1NmXb//8a6/+D7I3PzniEgAA";
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
    
    public static final byte[] $classHash = new byte[] { -71, -16, -56, -11,
    -95, 35, -30, -108, -22, 34, 87, -93, 18, -97, 77, -73, 116, -67, 51, 8,
    -74, 51, 58, 57, -64, 73, -123, 80, 90, 50, 56, -57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
