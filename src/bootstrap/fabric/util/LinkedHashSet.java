package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.io.Serializable;

/**
 * This class provides a hashtable-backed implementation of the
 * Set interface, with predictable traversal order.
 * <p>
 *
 * It uses a hash-bucket approach; that is, hash collisions are handled
 * by linking the new node off of the pre-existing node (or list of
 * nodes).  In this manner, techniques such as linear probing (which
 * can cause primary clustering) and rehashing (which does not fit very
 * well with Java's method of precomputing hash codes) are avoided.  In
 * addition, this maintains a doubly-linked list which tracks insertion
 * order.  Note that the insertion order is not modified if an
 * <code>add</code> simply reinserts an element in the set.
 * <p>
 *
 * One of the nice features of tracking insertion order is that you can
 * copy a set, and regardless of the implementation of the original,
 * produce the same results when iterating over the copy.  This is possible
 * without needing the overhead of <code>TreeSet</code>.
 * <p>
 *
 * Under ideal circumstances (no collisions), LinkedHashSet offers O(1) 
 * performance on most operations.  In the worst case (all elements map
 * to the same hash code -- very unlikely), most operations are O(n).
 * <p>
 *
 * LinkedHashSet accepts the null entry.  It is not synchronized, so if
 * you need multi-threaded access, consider using:<br>
 * <code>Set s = Collections.synchronizedSet(new LinkedHashSet(...));</code>
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
 * @see Set
 * @see HashSet
 * @see TreeSet
 * @see Collections#synchronizedSet(Set)
 * @since 1.4
 * @status updated to 1.4
 */
public interface LinkedHashSet extends fabric.util.Set, fabric.util.HashSet {
    /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$();
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(int initialCapacity);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
      int initialCapacity, float loadFactor);
    
    /**
   * Construct a new HashSet with the same elements as are in the supplied
   * collection (eliminating any duplicates, of course). The backing storage
   * has twice the size of the collection, or the default size of 11,
   * whichever is greater; and the default load factor (0.75).
   *
   * @param c a collection of initial set elements
   * @throws NullPointerException if c is null
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(fabric.util.Collection c);
    
    /**
   * Helper method which initializes the backing Map.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
    public fabric.util.HashMap init(int capacity, float load);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.HashSet._Proxy
      implements fabric.util.LinkedHashSet {
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$() {
            return ((fabric.util.LinkedHashSet) fetch()).
              fabric$util$LinkedHashSet$();
        }
        
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(int arg1) {
            return ((fabric.util.LinkedHashSet) fetch()).
              fabric$util$LinkedHashSet$(arg1);
        }
        
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1, float arg2) {
            return ((fabric.util.LinkedHashSet) fetch()).
              fabric$util$LinkedHashSet$(arg1, arg2);
        }
        
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          fabric.util.Collection arg1) {
            return ((fabric.util.LinkedHashSet) fetch()).
              fabric$util$LinkedHashSet$(arg1);
        }
        
        public _Proxy(LinkedHashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashSet._Impl
      implements fabric.util.LinkedHashSet {
        /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$() {
            fabric$util$HashSet$();
            return (fabric.util.LinkedHashSet) this.$getProxy();
        }
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int initialCapacity) {
            fabric$util$HashSet$(initialCapacity);
            return (fabric.util.LinkedHashSet) this.$getProxy();
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
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int initialCapacity, float loadFactor) {
            fabric$util$HashSet$(initialCapacity, loadFactor);
            return (fabric.util.LinkedHashSet) this.$getProxy();
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
        public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          fabric.util.Collection c) {
            fabric$util$HashSet$(c);
            return (fabric.util.LinkedHashSet) this.$getProxy();
        }
        
        /**
   * Helper method which initializes the backing Map.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
        public fabric.util.HashMap init(int capacity, float load) {
            return (fabric.util.LinkedHashMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.LinkedHashMap)
                          new fabric.util.LinkedHashMap._Impl(this.$getStore()).
                          $getProxy()).fabric$util$LinkedHashMap$(capacity,
                                                                  load));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.LinkedHashSet) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.LinkedHashSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashSet._Static {
            public long get$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.LinkedHashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.LinkedHashSet._Static $instance;
            
            static {
                fabric.
                  util.
                  LinkedHashSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.LinkedHashSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.LinkedHashSet._Static._Impl.class);
                $instance = (fabric.util.LinkedHashSet._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashSet._Static {
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
                return new fabric.util.LinkedHashSet._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm799 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled802 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff800 = 1;
                        boolean $doBackoff801 = true;
                        boolean $retry795 = true;
                        boolean $keepReads796 = false;
                        $label793: for (boolean $commit794 = false; !$commit794;
                                        ) {
                            if ($backoffEnabled802) {
                                if ($doBackoff801) {
                                    if ($backoff800 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff800));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e797) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff800 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff800 =
                                          java.lang.Math.
                                            min(
                                              $backoff800 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff801 = $backoff800 <= 32 ||
                                                  !$doBackoff801;
                            }
                            $commit794 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.LinkedHashSet._Static._Proxy.
                                  $instance.
                                  set$serialVersionUID((long)
                                                         -2851667679971038690L);
                            }
                            catch (final fabric.worker.RetryException $e797) {
                                $commit794 = false;
                                continue $label793;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e797) {
                                $commit794 = false;
                                $retry795 = false;
                                $keepReads796 = $e797.keepReads;
                                fabric.common.TransactionID $currentTid798 =
                                  $tm799.getCurrentTid();
                                if ($e797.tid ==
                                      null ||
                                      !$e797.tid.isDescendantOf(
                                                   $currentTid798)) {
                                    throw $e797;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e797);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e797) {
                                $commit794 = false;
                                fabric.common.TransactionID $currentTid798 =
                                  $tm799.getCurrentTid();
                                if ($e797.tid.isDescendantOf($currentTid798))
                                    continue $label793;
                                if ($currentTid798.parent != null) {
                                    $retry795 = false;
                                    throw $e797;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e797) {
                                $commit794 = false;
                                $retry795 = false;
                                if ($tm799.inNestedTxn()) {
                                    $keepReads796 = true;
                                }
                                throw $e797;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid798 =
                                  $tm799.getCurrentTid();
                                if ($commit794) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e797) {
                                        $commit794 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e797) {
                                        $commit794 = false;
                                        $retry795 = false;
                                        $keepReads796 = $e797.keepReads;
                                        if ($e797.tid ==
                                              null ||
                                              !$e797.tid.isDescendantOf(
                                                           $currentTid798))
                                            throw $e797;
                                        throw new fabric.worker.
                                                UserAbortException($e797);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e797) {
                                        $commit794 = false;
                                        $currentTid798 = $tm799.getCurrentTid();
                                        if ($currentTid798 != null) {
                                            if ($e797.tid.equals(
                                                            $currentTid798) ||
                                                  !$e797.tid.
                                                  isDescendantOf(
                                                    $currentTid798)) {
                                                throw $e797;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm799.inNestedTxn() &&
                                          $tm799.checkForStaleObjects()) {
                                        $retry795 = true;
                                        $keepReads796 = false;
                                    }
                                    if ($keepReads796) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e797) {
                                            $currentTid798 = $tm799.getCurrentTid();
                                            if ($currentTid798 != null &&
                                                  ($e797.tid.equals($currentTid798) || !$e797.tid.isDescendantOf($currentTid798))) {
                                                throw $e797;
                                            } else {
                                                $retry795 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit794) {
                                    {  }
                                    if ($retry795) { continue $label793; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, -78, 117, 24, 43,
    79, 26, 40, -3, 12, -31, 64, 62, 78, 127, 106, -19, -35, 121, 15, 98, 93,
    -44, -24, 5, 97, -92, 68, -25, 17, 95, -47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
