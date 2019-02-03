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
        
        public static final byte[] $classHash = new byte[] { 3, -74, 69, 118,
        109, -126, 120, 10, 23, 78, 56, -45, -46, -70, -93, -19, 41, -56, -23,
        -102, 88, 88, 40, -118, 39, 25, -26, 85, 13, -100, 81, 70 };
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
        
        public static final byte[] $classHash = new byte[] { 72, 114, 22, -42,
        -1, -34, -63, -46, -55, 23, -107, -11, 96, 45, 106, 98, 120, -39, -44,
        -69, -22, -116, 33, -70, 30, -76, 61, -32, 106, -40, -113, 3 };
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
                        fabric.worker.transaction.TransactionManager $tm811 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled814 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff812 = 1;
                        boolean $doBackoff813 = true;
                        boolean $retry807 = true;
                        boolean $keepReads808 = false;
                        $label805: for (boolean $commit806 = false; !$commit806;
                                        ) {
                            if ($backoffEnabled814) {
                                if ($doBackoff813) {
                                    if ($backoff812 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff812));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e809) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff812 < 5000) $backoff812 *= 2;
                                }
                                $doBackoff813 = $backoff812 <= 32 ||
                                                  !$doBackoff813;
                            }
                            $commit806 = true;
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
                                         RetryException $e809) {
                                    throw $e809;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e809) {
                                    throw $e809;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e809) {
                                    throw $e809;
                                }
                                catch (final Throwable $e809) {
                                    $tm811.getCurrentLog().checkRetrySignal();
                                    throw $e809;
                                }
                            }
                            catch (final fabric.worker.RetryException $e809) {
                                $commit806 = false;
                                continue $label805;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e809) {
                                $commit806 = false;
                                $retry807 = false;
                                $keepReads808 = $e809.keepReads;
                                if ($tm811.checkForStaleObjects()) {
                                    $retry807 = true;
                                    $keepReads808 = false;
                                    continue $label805;
                                }
                                fabric.common.TransactionID $currentTid810 =
                                  $tm811.getCurrentTid();
                                if ($e809.tid ==
                                      null ||
                                      !$e809.tid.isDescendantOf(
                                                   $currentTid810)) {
                                    throw $e809;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e809);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e809) {
                                $commit806 = false;
                                fabric.common.TransactionID $currentTid810 =
                                  $tm811.getCurrentTid();
                                if ($e809.tid.isDescendantOf($currentTid810))
                                    continue $label805;
                                if ($currentTid810.parent != null) {
                                    $retry807 = false;
                                    throw $e809;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e809) {
                                $commit806 = false;
                                if ($tm811.checkForStaleObjects())
                                    continue $label805;
                                $retry807 = false;
                                throw new fabric.worker.AbortException($e809);
                            }
                            finally {
                                if ($commit806) {
                                    fabric.common.TransactionID $currentTid810 =
                                      $tm811.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e809) {
                                        $commit806 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e809) {
                                        $commit806 = false;
                                        $retry807 = false;
                                        $keepReads808 = $e809.keepReads;
                                        if ($tm811.checkForStaleObjects()) {
                                            $retry807 = true;
                                            $keepReads808 = false;
                                            continue $label805;
                                        }
                                        if ($e809.tid ==
                                              null ||
                                              !$e809.tid.isDescendantOf(
                                                           $currentTid810))
                                            throw $e809;
                                        throw new fabric.worker.
                                                UserAbortException($e809);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e809) {
                                        $commit806 = false;
                                        $currentTid810 = $tm811.getCurrentTid();
                                        if ($currentTid810 != null) {
                                            if ($e809.tid.equals(
                                                            $currentTid810) ||
                                                  !$e809.tid.
                                                  isDescendantOf(
                                                    $currentTid810)) {
                                                throw $e809;
                                            }
                                        }
                                    }
                                } else if ($keepReads808) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit806) {
                                    {  }
                                    if ($retry807) { continue $label805; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 0, -98, -14, 98, -92,
    -63, 98, -35, -124, 85, 123, -10, 80, -7, 40, -24, 55, 73, -35, 19, 16, 40,
    84, -62, -46, -50, -18, -95, 123, -17, -124, -63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
