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
                        fabric.worker.transaction.TransactionManager $tm779 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled782 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff780 = 1;
                        boolean $doBackoff781 = true;
                        boolean $retry775 = true;
                        boolean $keepReads776 = false;
                        $label773: for (boolean $commit774 = false; !$commit774;
                                        ) {
                            if ($backoffEnabled782) {
                                if ($doBackoff781) {
                                    if ($backoff780 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff780));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e777) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff780 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff780 =
                                          java.lang.Math.
                                            min(
                                              $backoff780 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff781 = $backoff780 <= 32 ||
                                                  !$doBackoff781;
                            }
                            $commit774 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.HashSet._Static._Proxy.$instance.
                                  set$serialVersionUID((long)
                                                         -5024744406713321676L);
                            }
                            catch (final fabric.worker.RetryException $e777) {
                                $commit774 = false;
                                continue $label773;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e777) {
                                $commit774 = false;
                                $retry775 = false;
                                $keepReads776 = $e777.keepReads;
                                fabric.common.TransactionID $currentTid778 =
                                  $tm779.getCurrentTid();
                                if ($e777.tid ==
                                      null ||
                                      !$e777.tid.isDescendantOf(
                                                   $currentTid778)) {
                                    throw $e777;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e777);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e777) {
                                $commit774 = false;
                                fabric.common.TransactionID $currentTid778 =
                                  $tm779.getCurrentTid();
                                if ($e777.tid.isDescendantOf($currentTid778))
                                    continue $label773;
                                if ($currentTid778.parent != null) {
                                    $retry775 = false;
                                    throw $e777;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e777) {
                                $commit774 = false;
                                $retry775 = false;
                                if ($tm779.inNestedTxn()) {
                                    $keepReads776 = true;
                                }
                                throw $e777;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid778 =
                                  $tm779.getCurrentTid();
                                if ($commit774) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e777) {
                                        $commit774 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e777) {
                                        $commit774 = false;
                                        $retry775 = false;
                                        $keepReads776 = $e777.keepReads;
                                        if ($e777.tid ==
                                              null ||
                                              !$e777.tid.isDescendantOf(
                                                           $currentTid778))
                                            throw $e777;
                                        throw new fabric.worker.
                                                UserAbortException($e777);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e777) {
                                        $commit774 = false;
                                        $currentTid778 = $tm779.getCurrentTid();
                                        if ($currentTid778 != null) {
                                            if ($e777.tid.equals(
                                                            $currentTid778) ||
                                                  !$e777.tid.
                                                  isDescendantOf(
                                                    $currentTid778)) {
                                                throw $e777;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm779.inNestedTxn() &&
                                          $tm779.checkForStaleObjects()) {
                                        $retry775 = true;
                                        $keepReads776 = false;
                                    }
                                    if ($keepReads776) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e777) {
                                            $currentTid778 = $tm779.getCurrentTid();
                                            if ($currentTid778 != null &&
                                                  ($e777.tid.equals($currentTid778) || !$e777.tid.isDescendantOf($currentTid778))) {
                                                throw $e777;
                                            } else {
                                                $retry775 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit774) {
                                    {  }
                                    if ($retry775) { continue $label773; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 112, 73, 10, -57, 123,
    55, 109, 47, -39, 20, -46, -16, -82, 67, -105, 44, 19, -63, -26, -111, -31,
    30, 91, -35, 29, -67, -67, -34, -7, 38, 107, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
