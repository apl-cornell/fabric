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
    
    public static final byte[] $classHash = new byte[] { 45, 117, -36, 88, -95,
    57, -107, 122, -25, 42, -16, -39, 96, -34, 115, 78, 126, -57, -39, -82, -17,
    105, -102, -2, -114, 0, 101, -63, -4, -122, -105, 112 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
