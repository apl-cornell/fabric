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
                        fabric.worker.transaction.TransactionManager $tm791 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled794 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff792 = 1;
                        boolean $doBackoff793 = true;
                        boolean $retry787 = true;
                        boolean $keepReads788 = false;
                        $label785: for (boolean $commit786 = false; !$commit786;
                                        ) {
                            if ($backoffEnabled794) {
                                if ($doBackoff793) {
                                    if ($backoff792 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff792));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e789) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff792 < 5000) $backoff792 *= 2;
                                }
                                $doBackoff793 = $backoff792 <= 32 ||
                                                  !$doBackoff793;
                            }
                            $commit786 = true;
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
                                         RetryException $e789) {
                                    throw $e789;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e789) {
                                    throw $e789;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e789) {
                                    throw $e789;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e789) {
                                    throw $e789;
                                }
                                catch (final Throwable $e789) {
                                    $tm791.getCurrentLog().checkRetrySignal();
                                    throw $e789;
                                }
                            }
                            catch (final fabric.worker.RetryException $e789) {
                                $commit786 = false;
                                continue $label785;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e789) {
                                $commit786 = false;
                                $retry787 = false;
                                $keepReads788 = $e789.keepReads;
                                if ($tm791.checkForStaleObjects()) {
                                    $retry787 = true;
                                    $keepReads788 = false;
                                    continue $label785;
                                }
                                fabric.common.TransactionID $currentTid790 =
                                  $tm791.getCurrentTid();
                                if ($e789.tid ==
                                      null ||
                                      !$e789.tid.isDescendantOf(
                                                   $currentTid790)) {
                                    throw $e789;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e789);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e789) {
                                $commit786 = false;
                                fabric.common.TransactionID $currentTid790 =
                                  $tm791.getCurrentTid();
                                if ($e789.tid.isDescendantOf($currentTid790))
                                    continue $label785;
                                if ($currentTid790.parent != null) {
                                    $retry787 = false;
                                    throw $e789;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e789) {
                                $commit786 = false;
                                if ($tm791.checkForStaleObjects())
                                    continue $label785;
                                fabric.common.TransactionID $currentTid790 =
                                  $tm791.getCurrentTid();
                                if ($e789.tid.isDescendantOf($currentTid790)) {
                                    $retry787 = true;
                                }
                                else if ($currentTid790.parent != null) {
                                    $retry787 = false;
                                    throw $e789;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e789) {
                                $commit786 = false;
                                if ($tm791.checkForStaleObjects())
                                    continue $label785;
                                $retry787 = false;
                                throw new fabric.worker.AbortException($e789);
                            }
                            finally {
                                if ($commit786) {
                                    fabric.common.TransactionID $currentTid790 =
                                      $tm791.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e789) {
                                        $commit786 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e789) {
                                        $commit786 = false;
                                        $retry787 = false;
                                        $keepReads788 = $e789.keepReads;
                                        if ($tm791.checkForStaleObjects()) {
                                            $retry787 = true;
                                            $keepReads788 = false;
                                            continue $label785;
                                        }
                                        if ($e789.tid ==
                                              null ||
                                              !$e789.tid.isDescendantOf(
                                                           $currentTid790))
                                            throw $e789;
                                        throw new fabric.worker.
                                                UserAbortException($e789);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e789) {
                                        $commit786 = false;
                                        $currentTid790 = $tm791.getCurrentTid();
                                        if ($currentTid790 != null) {
                                            if ($e789.tid.equals(
                                                            $currentTid790) ||
                                                  !$e789.tid.
                                                  isDescendantOf(
                                                    $currentTid790)) {
                                                throw $e789;
                                            }
                                        }
                                    }
                                } else if ($keepReads788) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit786) {
                                    {  }
                                    if ($retry787) { continue $label785; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 118, -82, 6, -119, 81,
    123, 43, 84, 121, -18, -86, -83, 103, -58, -19, 118, -73, 65, -30, 100, -56,
    -87, -101, 53, -83, -78, 118, -39, -111, 31, 36, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
