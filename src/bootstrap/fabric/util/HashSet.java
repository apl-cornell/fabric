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
 * This class provides a HashMap-backed implementation of the Set interface.
 * <p>
 *
 * Most operations are O(1), assuming no hash collisions.  In the worst
 * case (where all hashes collide), operations are O(n). Setting the
 * initial capacity too low will force many resizing operations, but
 * setting the initial capacity too high (or loadfactor too low) leads
 * to wasted memory and slower iteration.
 * <p>
 *
 * HashSet accepts the null key and null values.  It is not synchronized,
 * so if you need multi-threaded access, consider using:<br>
 * <code>Set s = Collections.synchronizedSet(new HashSet(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Jon Zeppieri
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Set
 * @see TreeSet
 * @see Collections#synchronizedSet(Set)
 * @see HashMap
 * @see LinkedHashSet
 * @since 1.2
 * @status updated to 1.4
 */
public interface HashSet extends fabric.util.Set, fabric.util.AbstractSet {
    public fabric.util.HashMap get$map();
    
    public fabric.util.HashMap set$map(fabric.util.HashMap val);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
    public fabric.util.HashSet fabric$util$HashSet$();
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity, float loadFactor);
    
    /**
   * Construct a new HashSet with the same elements as are in the supplied
   * collection (eliminating any duplicates, of course). The backing storage
   * has twice the size of the collection, or the default size of 11,
   * whichever is greater; and the default load factor (0.75).
   *
   * @param c a collection of initial set elements
   * @throws NullPointerException if c is null
   */
    public fabric.util.HashSet fabric$util$HashSet$(fabric.util.Collection c);
    
    /**
   * Adds the given Object to the set if it is not already in the Set.
   * This set permits a null element.
   *
   * @param o the Object to add to this Set
   * @return true if the set did not already contain o
   */
    public boolean add(fabric.lang.Object o);
    
    /**
   * Empties this Set of all elements; this takes constant time.
   */
    public void clear();
    
    /**
   * Returns true if the supplied element is in this Set.
   *
   * @param o the Object to look for
   * @return true if it is in the set
   */
    public boolean contains(fabric.lang.Object o);
    
    /**
   * Returns true if this set has no elements in it.
   *
   * @return <code>size() == 0</code>.
   */
    public boolean isEmpty();
    
    /**
   * Returns an Iterator over the elements of this Set, which visits the
   * elements in no particular order.  For this class, the Iterator allows
   * removal of elements. The iterator is fail-fast, and will throw a
   * ConcurrentModificationException if the set is modified externally.
   *
   * @return a set iterator
   * @see ConcurrentModificationException
   */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Removes the supplied Object from this Set if it is in the Set.
   *
   * @param o the object to remove
   * @return true if an element was removed
   */
    public boolean remove(fabric.lang.Object o);
    
    /**
   * Returns the number of elements in this Set (its cardinality).
   *
   * @return the size of the set
   */
    public int size();
    
    /**
   * Helper method which initializes the backing Map. Overridden by
   * LinkedHashSet for correct semantics.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
    public fabric.util.HashMap init(int capacity, float load);
    
    /**
   * Deserializes this object from the given stream.
   *
   * @param s the stream to read from
   * @throws ClassNotFoundException if the underlying stream fails
   * @throws IOException if the underlying stream fails
   * @serialData the <i>capacity</i> (int) and <i>loadFactor</i> (float)
   *             of the backing store, followed by the set size (int),
   *             then a listing of its elements (Object) in no order
   */
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.HashSet {
        public fabric.util.HashMap get$map() {
            return ((fabric.util.HashSet._Impl) fetch()).get$map();
        }
        
        public fabric.util.HashMap set$map(fabric.util.HashMap val) {
            return ((fabric.util.HashSet._Impl) fetch()).set$map(val);
        }
        
        public fabric.util.HashSet fabric$util$HashSet$() {
            return ((fabric.util.HashSet) fetch()).fabric$util$HashSet$();
        }
        
        public fabric.util.HashSet fabric$util$HashSet$(int arg1) {
            return ((fabric.util.HashSet) fetch()).fabric$util$HashSet$(arg1);
        }
        
        public fabric.util.HashSet fabric$util$HashSet$(int arg1, float arg2) {
            return ((fabric.util.HashSet) fetch()).fabric$util$HashSet$(arg1,
                                                                        arg2);
        }
        
        public fabric.util.HashSet fabric$util$HashSet$(
          fabric.util.Collection arg1) {
            return ((fabric.util.HashSet) fetch()).fabric$util$HashSet$(arg1);
        }
        
        public fabric.util.HashMap init(int arg1, float arg2) {
            return ((fabric.util.HashSet) fetch()).init(arg1, arg2);
        }
        
        public _Proxy(HashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.HashSet {
        public fabric.util.HashMap get$map() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.map;
        }
        
        public fabric.util.HashMap set$map(fabric.util.HashMap val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.map = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The HashMap which backs this Set.
   */
        private fabric.util.HashMap map;
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
        public fabric.util.HashSet fabric$util$HashSet$() {
            fabric$util$HashSet$(
              fabric.util.HashMap._Static._Proxy.$instance.get$DEFAULT_CAPACITY(
                                                             ),
              fabric.util.HashMap._Static._Proxy.$instance.
                  get$DEFAULT_LOAD_FACTOR());
            return (fabric.util.HashSet) this.$getProxy();
        }
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
        public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity) {
            fabric$util$HashSet$(
              initialCapacity,
              fabric.util.HashMap._Static._Proxy.$instance.
                  get$DEFAULT_LOAD_FACTOR());
            return (fabric.util.HashSet) this.$getProxy();
        }
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
        public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity,
                                                        float loadFactor) {
            fabric$lang$Object$();
            this.set$map(init(initialCapacity, loadFactor));
            return (fabric.util.HashSet) this.$getProxy();
        }
        
        /**
   * Construct a new HashSet with the same elements as are in the supplied
   * collection (eliminating any duplicates, of course). The backing storage
   * has twice the size of the collection, or the default size of 11,
   * whichever is greater; and the default load factor (0.75).
   *
   * @param c a collection of initial set elements
   * @throws NullPointerException if c is null
   */
        public fabric.util.HashSet fabric$util$HashSet$(
          fabric.util.Collection c) {
            fabric$util$HashSet$(
              java.lang.Math.
                  max(
                    2 * c.size(),
                    fabric.util.HashMap._Static._Proxy.$instance.
                        get$DEFAULT_CAPACITY()));
            addAll(c);
            return (fabric.util.HashSet) this.$getProxy();
        }
        
        /**
   * Adds the given Object to the set if it is not already in the Set.
   * This set permits a null element.
   *
   * @param o the Object to add to this Set
   * @return true if the set did not already contain o
   */
        public boolean add(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.
              idEquals(
                this.get$map().put(o,
                                   fabric.lang.WrappedJavaInlineable.$wrap("")),
                null);
        }
        
        /**
   * Empties this Set of all elements; this takes constant time.
   */
        public void clear() { this.get$map().clear(); }
        
        /**
   * Returns true if the supplied element is in this Set.
   *
   * @param o the Object to look for
   * @return true if it is in the set
   */
        public boolean contains(fabric.lang.Object o) {
            return this.get$map().containsKey(o);
        }
        
        /**
   * Returns true if this set has no elements in it.
   *
   * @return <code>size() == 0</code>.
   */
        public boolean isEmpty() { return this.get$map().get$size() == 0; }
        
        /**
   * Returns an Iterator over the elements of this Set, which visits the
   * elements in no particular order.  For this class, the Iterator allows
   * removal of elements. The iterator is fail-fast, and will throw a
   * ConcurrentModificationException if the set is modified externally.
   *
   * @return a set iterator
   * @see ConcurrentModificationException
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store) {
            return this.get$map().
              iterator(
                store,
                fabric.util.AbstractMap._Static._Proxy.$instance.get$KEYS());
        }
        
        /**
   * Removes the supplied Object from this Set if it is in the Set.
   *
   * @param o the object to remove
   * @return true if an element was removed
   */
        public boolean remove(fabric.lang.Object o) {
            return !fabric.lang.Object._Proxy.idEquals(this.get$map().remove(o),
                                                       null);
        }
        
        /**
   * Returns the number of elements in this Set (its cardinality).
   *
   * @return the size of the set
   */
        public int size() { return this.get$map().get$size(); }
        
        /**
   * Helper method which initializes the backing Map. Overridden by
   * LinkedHashSet for correct semantics.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
        public fabric.util.HashMap init(int capacity, float load) {
            return (fabric.util.HashMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.HashMap)
                          new fabric.util.HashMap._Impl(this.$getStore()).
                          $getProxy()).fabric$util$HashMap$(capacity, load));
        }
        
        /**
   * Deserializes this object from the given stream.
   *
   * @param s the stream to read from
   * @throws ClassNotFoundException if the underlying stream fails
   * @throws IOException if the underlying stream fails
   * @serialData the <i>capacity</i> (int) and <i>loadFactor</i> (float)
   *             of the backing store, followed by the set size (int),
   *             then a listing of its elements (Object) in no order
   */
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.HashSet) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.HashSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.map, refTypes, out, intraStoreRefs,
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
            this.map = (fabric.util.HashMap)
                         $readRef(fabric.util.HashMap._Proxy.class,
                                  (fabric.common.RefTypeEnum) refTypes.next(),
                                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.HashSet._Impl src = (fabric.util.HashSet._Impl) other;
            this.map = src.map;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HashSet._Static {
            public long get$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.HashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.HashSet._Static $instance;
            
            static {
                fabric.
                  util.
                  HashSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.HashSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.HashSet._Static._Impl.class);
                $instance = (fabric.util.HashSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashSet._Static {
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
                return new fabric.util.HashSet._Static._Proxy(this);
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
                                    fabric.util.HashSet._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -5024744406713321676L);
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
    
    public static final byte[] $classHash = new byte[] { -101, 52, -65, -42, 91,
    30, 62, -82, 36, -33, -48, -75, -111, -94, -90, 0, 112, 53, -3, 46, 84, 79,
    78, -66, -89, -128, 32, -8, -115, 108, -12, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
