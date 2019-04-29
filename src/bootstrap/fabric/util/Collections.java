package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.io.Serializable;

/**
 * Utility class consisting of static methods that operate on, or return
 * Collections. Contains methods to sort, search, reverse, fill and shuffle
 * Collections, methods to facilitate interoperability with legacy APIs that
 * are unaware of collections, a method to return a list which consists of
 * multiple copies of one element, and methods which "wrap" collections to give
 * them extra properties, such as thread-safety and unmodifiability.
 * <p>
 *
 * All methods which take a collection throw a {@link NullPointerException} if
 * that collection is null. Algorithms which can change a collection may, but
 * are not required, to throw the {@link UnsupportedOperationException} that
 * the underlying collection would throw during an attempt at modification.
 * For example,
 * <code>Collections.singleton("").addAll(Collections.EMPTY_SET)</code>
 * does not throw a exception, even though addAll is an unsupported operation
 * on a singleton; the reason for this is that addAll did not attempt to
 * modify the set.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Set
 * @see List
 * @see Map
 * @see Arrays
 * @since 1.2
 * @status updated to 1.4
 */
public interface Collections extends fabric.lang.Object {
    /**
   * The implementation of {@link #EMPTY_SET}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptySet
      extends java.io.Serializable, fabric.util.AbstractSet {
        /**
     * A private constructor adds overhead.
     */
        public EmptySet fabric$util$Collections$EmptySet$();
        
        /**
     * The size: always 0!
     * @return 0.
     */
        public int size();
        
        /**
     * Returns an iterator that does not iterate.
     * @return A non-iterating iterator.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
     * The empty set never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects which are to be compared
     *          against the members of this set.
     * @return <code>true</code> if c is empty.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Equal only if the other set is empty.
     * @param o The object to compare with this set.
     * @return <code>true</code> if o is an empty instance of <code>Set</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * The hashcode is always 0.
     * @return 0.
     */
        public int hashCode();
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param o The object to remove.
     * @return <code>false</code>.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this set.
     * @return <code>false</code>.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this set.
     * @return <code>false</code>.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements EmptySet {
            public fabric.util.Collections.EmptySet
              fabric$util$Collections$EmptySet$() {
                return ((fabric.util.Collections.EmptySet) fetch()).
                  fabric$util$Collections$EmptySet$();
            }
            
            public _Proxy(EmptySet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractSet._Impl
          implements EmptySet {
            /**
     * A private constructor adds overhead.
     */
            public EmptySet fabric$util$Collections$EmptySet$() {
                fabric$util$AbstractSet$();
                return (EmptySet) this.$getProxy();
            }
            
            /**
     * The size: always 0!
     * @return 0.
     */
            public int size() { return 0; }
            
            /**
     * Returns an iterator that does not iterate.
     * @return A non-iterating iterator.
     */
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_LIST().iterator(store);
            }
            
            /**
     * The empty set never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
            public boolean contains(fabric.lang.Object o) { return false; }
            
            /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects which are to be compared
     *          against the members of this set.
     * @return <code>true</code> if c is empty.
     */
            public boolean containsAll(fabric.util.Collection c) {
                return c.isEmpty();
            }
            
            /**
     * Equal only if the other set is empty.
     * @param o The object to compare with this set.
     * @return <code>true</code> if o is an empty instance of <code>Set</code>.
     */
            public boolean equals(fabric.lang.Object o) {
                return fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.util.Set &&
                  ((fabric.util.Set) fabric.lang.Object._Proxy.$getProxy(o)).
                  isEmpty();
            }
            
            /**
     * The hashcode is always 0.
     * @return 0.
     */
            public int hashCode() { return 0; }
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param o The object to remove.
     * @return <code>false</code>.
     */
            public boolean remove(fabric.lang.Object o) { return false; }
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this set.
     * @return <code>false</code>.
     */
            public boolean removeAll(fabric.util.Collection c) { return false; }
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this set.
     * @return <code>false</code>.
     */
            public boolean retainAll(fabric.util.Collection c) { return false; }
            
            /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                return (fabric.lang.arrays.ObjectArray)
                         new fabric.lang.arrays.ObjectArray._Impl(
                           this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                               this.get$$updateLabel(),
                                               this.get$$updateLabel(
                                                      ).confPolicy(),
                                               fabric.lang.Object._Proxy.class,
                                               0).$getProxy();
            }
            
            /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray a) {
                if (a.get$length() > 0) a.set(0, null);
                return a;
            }
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public java.lang.String toString() { return "[]"; }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (EmptySet) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptySet._Proxy(this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.EmptySet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptySet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptySet._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptySet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.Collections.EmptySet._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptySet._Static._Impl.class);
                    $instance = (fabric.util.Collections.EmptySet._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptySet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.EmptySet._Static._Proxy(
                             this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm599 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled602 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff600 = 1;
                            boolean $doBackoff601 = true;
                            boolean $retry595 = true;
                            boolean $keepReads596 = false;
                            $label593: for (boolean $commit594 = false;
                                            !$commit594; ) {
                                if ($backoffEnabled602) {
                                    if ($doBackoff601) {
                                        if ($backoff600 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff600));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e597) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff600 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff600 =
                                              java.lang.Math.
                                                min(
                                                  $backoff600 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff601 = $backoff600 <= 32 ||
                                                      !$doBackoff601;
                                }
                                $commit594 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.EmptySet._Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 1582296315990362920L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e597) {
                                    $commit594 = false;
                                    continue $label593;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e597) {
                                    $commit594 = false;
                                    $retry595 = false;
                                    $keepReads596 = $e597.keepReads;
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($e597.tid ==
                                          null ||
                                          !$e597.tid.isDescendantOf(
                                                       $currentTid598)) {
                                        throw $e597;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e597);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e597) {
                                    $commit594 = false;
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($e597.tid.isDescendantOf(
                                                    $currentTid598))
                                        continue $label593;
                                    if ($currentTid598.parent != null) {
                                        $retry595 = false;
                                        throw $e597;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e597) {
                                    $commit594 = false;
                                    $retry595 = false;
                                    if ($tm599.inNestedTxn()) {
                                        $keepReads596 = true;
                                    }
                                    throw $e597;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($commit594) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e597) {
                                            $commit594 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e597) {
                                            $commit594 = false;
                                            $retry595 = false;
                                            $keepReads596 = $e597.keepReads;
                                            if ($e597.tid ==
                                                  null ||
                                                  !$e597.tid.isDescendantOf(
                                                               $currentTid598))
                                                throw $e597;
                                            throw new fabric.worker.
                                                    UserAbortException($e597);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e597) {
                                            $commit594 = false;
                                            $currentTid598 =
                                              $tm599.getCurrentTid();
                                            if ($currentTid598 != null) {
                                                if ($e597.tid.
                                                      equals($currentTid598) ||
                                                      !$e597.tid.
                                                      isDescendantOf(
                                                        $currentTid598)) {
                                                    throw $e597;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm599.inNestedTxn() &&
                                              $tm599.checkForStaleObjects()) {
                                            $retry595 = true;
                                            $keepReads596 = false;
                                        }
                                        if ($keepReads596) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e597) {
                                                $currentTid598 = $tm599.getCurrentTid();
                                                if ($currentTid598 != null &&
                                                      ($e597.tid.equals($currentTid598) || !$e597.tid.isDescendantOf($currentTid598))) {
                                                    throw $e597;
                                                } else {
                                                    $retry595 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit594) {
                                        {  }
                                        if ($retry595) { continue $label593; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -73, 79, -12, -9,
        15, -104, -109, -79, 55, -96, -3, -12, -61, -45, -114, -30, 127, -84,
        116, -37, -70, 63, -15, 101, 116, 40, -83, -98, 87, -89, 71, -38 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #EMPTY_LIST}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptyList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        /**
     * A private constructor adds overhead.
     */
        public EmptyList fabric$util$Collections$EmptyList$();
        
        /**
     * The size is always 0.
     * @return 0.
     */
        public int size();
        
        /**
     * No matter the index, it is out of bounds.  This
     * method never returns, throwing an exception instead.
     *
     * @param index The index of the element to retrieve.
     * @return the object at the specified index.
     * @throws IndexOutOfBoundsException as any given index
     *         is outside the bounds of an empty array.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects, which should be compared
     *          against the members of this list.
     * @return <code>true</code> if c is also empty. 
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Equal only if the other list is empty.
     * @param o The object to compare against this list.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>List</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * The hashcode is always 1.
     * @return 1.
     */
        public int hashCode();
        
        /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param o The object to remove.
     * @return -1.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this list.
     * @return <code>false</code>.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this list.
     * @return <code>false</code>.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements EmptyList {
            public fabric.util.Collections.EmptyList
              fabric$util$Collections$EmptyList$() {
                return ((fabric.util.Collections.EmptyList) fetch()).
                  fabric$util$Collections$EmptyList$();
            }
            
            public _Proxy(EmptyList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements EmptyList {
            /**
     * A private constructor adds overhead.
     */
            public EmptyList fabric$util$Collections$EmptyList$() {
                fabric$util$AbstractList$();
                return (EmptyList) this.$getProxy();
            }
            
            /**
     * The size is always 0.
     * @return 0.
     */
            public int size() { return 0; }
            
            /**
     * No matter the index, it is out of bounds.  This
     * method never returns, throwing an exception instead.
     *
     * @param index The index of the element to retrieve.
     * @return the object at the specified index.
     * @throws IndexOutOfBoundsException as any given index
     *         is outside the bounds of an empty array.
     */
            public fabric.lang.Object get(int index) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            
            /**
     * Never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
            public boolean contains(fabric.lang.Object o) { return false; }
            
            /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects, which should be compared
     *          against the members of this list.
     * @return <code>true</code> if c is also empty. 
     */
            public boolean containsAll(fabric.util.Collection c) {
                return c.isEmpty();
            }
            
            /**
     * Equal only if the other list is empty.
     * @param o The object to compare against this list.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>List</code>.
     */
            public boolean equals(fabric.lang.Object o) {
                return fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.util.List &&
                  ((fabric.util.List) fabric.lang.Object._Proxy.$getProxy(o)).
                  isEmpty();
            }
            
            /**
     * The hashcode is always 1.
     * @return 1.
     */
            public int hashCode() { return 1; }
            
            /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
            public int indexOf(fabric.lang.Object o) { return -1; }
            
            /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
            public int lastIndexOf(fabric.lang.Object o) { return -1; }
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param o The object to remove.
     * @return -1.
     */
            public boolean remove(fabric.lang.Object o) { return false; }
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this list.
     * @return <code>false</code>.
     */
            public boolean removeAll(fabric.util.Collection c) { return false; }
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this list.
     * @return <code>false</code>.
     */
            public boolean retainAll(fabric.util.Collection c) { return false; }
            
            /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                return (fabric.lang.arrays.ObjectArray)
                         new fabric.lang.arrays.ObjectArray._Impl(
                           this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                               this.get$$updateLabel(),
                                               this.get$$updateLabel(
                                                      ).confPolicy(),
                                               fabric.lang.Object._Proxy.class,
                                               0).$getProxy();
            }
            
            /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray a) {
                if (a.get$length() > 0) a.set(0, null);
                return a;
            }
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public java.lang.String toString() { return "[]"; }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (EmptyList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptyList._Proxy(this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.EmptyList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptyList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptyList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptyList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        EmptyList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptyList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.EmptyList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptyList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.EmptyList._Static._Proxy(
                             this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm609 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled612 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff610 = 1;
                            boolean $doBackoff611 = true;
                            boolean $retry605 = true;
                            boolean $keepReads606 = false;
                            $label603: for (boolean $commit604 = false;
                                            !$commit604; ) {
                                if ($backoffEnabled612) {
                                    if ($doBackoff611) {
                                        if ($backoff610 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff610));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e607) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff610 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff610 =
                                              java.lang.Math.
                                                min(
                                                  $backoff610 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff611 = $backoff610 <= 32 ||
                                                      !$doBackoff611;
                                }
                                $commit604 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.EmptyList._Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 8842843931221139166L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e607) {
                                    $commit604 = false;
                                    continue $label603;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e607) {
                                    $commit604 = false;
                                    $retry605 = false;
                                    $keepReads606 = $e607.keepReads;
                                    fabric.common.TransactionID $currentTid608 =
                                      $tm609.getCurrentTid();
                                    if ($e607.tid ==
                                          null ||
                                          !$e607.tid.isDescendantOf(
                                                       $currentTid608)) {
                                        throw $e607;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e607);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e607) {
                                    $commit604 = false;
                                    fabric.common.TransactionID $currentTid608 =
                                      $tm609.getCurrentTid();
                                    if ($e607.tid.isDescendantOf(
                                                    $currentTid608))
                                        continue $label603;
                                    if ($currentTid608.parent != null) {
                                        $retry605 = false;
                                        throw $e607;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e607) {
                                    $commit604 = false;
                                    $retry605 = false;
                                    if ($tm609.inNestedTxn()) {
                                        $keepReads606 = true;
                                    }
                                    throw $e607;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid608 =
                                      $tm609.getCurrentTid();
                                    if ($commit604) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e607) {
                                            $commit604 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e607) {
                                            $commit604 = false;
                                            $retry605 = false;
                                            $keepReads606 = $e607.keepReads;
                                            if ($e607.tid ==
                                                  null ||
                                                  !$e607.tid.isDescendantOf(
                                                               $currentTid608))
                                                throw $e607;
                                            throw new fabric.worker.
                                                    UserAbortException($e607);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e607) {
                                            $commit604 = false;
                                            $currentTid608 =
                                              $tm609.getCurrentTid();
                                            if ($currentTid608 != null) {
                                                if ($e607.tid.
                                                      equals($currentTid608) ||
                                                      !$e607.tid.
                                                      isDescendantOf(
                                                        $currentTid608)) {
                                                    throw $e607;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm609.inNestedTxn() &&
                                              $tm609.checkForStaleObjects()) {
                                            $retry605 = true;
                                            $keepReads606 = false;
                                        }
                                        if ($keepReads606) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e607) {
                                                $currentTid608 = $tm609.getCurrentTid();
                                                if ($currentTid608 != null &&
                                                      ($e607.tid.equals($currentTid608) || !$e607.tid.isDescendantOf($currentTid608))) {
                                                    throw $e607;
                                                } else {
                                                    $retry605 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit604) {
                                        {  }
                                        if ($retry605) { continue $label603; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 12, 14, 85, -102,
        -8, 93, 34, -21, 10, 39, -125, -97, 66, -94, -102, -74, 90, -98, -119,
        -77, 66, 32, -85, -9, 85, -31, 72, 71, -81, -40, 23, 30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #EMPTY_MAP}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptyMap
      extends java.io.Serializable, fabric.util.AbstractMap {
        /**
     * A private constructor adds overhead.
     */
        public EmptyMap fabric$util$Collections$EmptyMap$();
        
        /**
     * There are no entries.
     * @return The empty set.
     */
        public fabric.util.Set entrySet();
        
        /**
     * No entries!
     * @param key The key to search for.
     * @return <code>false</code>.
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * No entries!
     * @param value The value to search for.
     * @return <code>false</code>.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Equal to all empty maps.
     * @param o The object o to compare against this map.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>Map</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * No mappings, so this returns null.
     * @param o The key of the object to retrieve.
     * @return null. 
     */
        public fabric.lang.Object get(fabric.lang.Object o);
        
        /**
     * The hashcode is always 0.
     * @return 0.
     */
        public int hashCode();
        
        /**
     * No entries.
     * @return The empty set.
     */
        public fabric.util.Set keySet();
        
        /**
     * Remove always succeeds, with null result.
     * @param o The key of the mapping to remove.
     * @return null, as there is never a mapping for o.
     */
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        /**
     * Size is always 0.
     * @return 0.
     */
        public int size();
        
        /**
     * No entries. Technically, EMPTY_SET, while more specific than a general
     * Collection, will work. Besides, that's what the JDK uses!
     * @return The empty set.
     */
        public fabric.util.Collection values();
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements EmptyMap {
            public fabric.util.Collections.EmptyMap
              fabric$util$Collections$EmptyMap$() {
                return ((fabric.util.Collections.EmptyMap) fetch()).
                  fabric$util$Collections$EmptyMap$();
            }
            
            public _Proxy(EmptyMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements EmptyMap {
            /**
     * A private constructor adds overhead.
     */
            public EmptyMap fabric$util$Collections$EmptyMap$() {
                fabric$util$AbstractMap$();
                return (EmptyMap) this.$getProxy();
            }
            
            /**
     * There are no entries.
     * @return The empty set.
     */
            public fabric.util.Set entrySet() {
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
            }
            
            /**
     * No entries!
     * @param key The key to search for.
     * @return <code>false</code>.
     */
            public boolean containsKey(fabric.lang.Object key) { return false; }
            
            /**
     * No entries!
     * @param value The value to search for.
     * @return <code>false</code>.
     */
            public boolean containsValue(fabric.lang.Object value) {
                return false;
            }
            
            /**
     * Equal to all empty maps.
     * @param o The object o to compare against this map.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>Map</code>.
     */
            public boolean equals(fabric.lang.Object o) {
                return fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.util.Map &&
                  ((fabric.util.Map) fabric.lang.Object._Proxy.$getProxy(o)).
                  isEmpty();
            }
            
            /**
     * No mappings, so this returns null.
     * @param o The key of the object to retrieve.
     * @return null. 
     */
            public fabric.lang.Object get(fabric.lang.Object o) { return null; }
            
            /**
     * The hashcode is always 0.
     * @return 0.
     */
            public int hashCode() { return 0; }
            
            /**
     * No entries.
     * @return The empty set.
     */
            public fabric.util.Set keySet() {
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
            }
            
            /**
     * Remove always succeeds, with null result.
     * @param o The key of the mapping to remove.
     * @return null, as there is never a mapping for o.
     */
            public fabric.lang.Object remove(fabric.lang.Object o) {
                return null;
            }
            
            /**
     * Size is always 0.
     * @return 0.
     */
            public int size() { return 0; }
            
            /**
     * No entries. Technically, EMPTY_SET, while more specific than a general
     * Collection, will work. Besides, that's what the JDK uses!
     * @return The empty set.
     */
            public fabric.util.Collection values() {
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
            }
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public java.lang.String toString() { return "[]"; }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (EmptyMap) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptyMap._Proxy(this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.EmptyMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptyMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptyMap._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptyMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.Collections.EmptyMap._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptyMap._Static._Impl.class);
                    $instance = (fabric.util.Collections.EmptyMap._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptyMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.EmptyMap._Static._Proxy(
                             this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm619 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled622 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff620 = 1;
                            boolean $doBackoff621 = true;
                            boolean $retry615 = true;
                            boolean $keepReads616 = false;
                            $label613: for (boolean $commit614 = false;
                                            !$commit614; ) {
                                if ($backoffEnabled622) {
                                    if ($doBackoff621) {
                                        if ($backoff620 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff620));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e617) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff620 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff620 =
                                              java.lang.Math.
                                                min(
                                                  $backoff620 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff621 = $backoff620 <= 32 ||
                                                      !$doBackoff621;
                                }
                                $commit614 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.EmptyMap._Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 6428348081105594320L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e617) {
                                    $commit614 = false;
                                    continue $label613;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e617) {
                                    $commit614 = false;
                                    $retry615 = false;
                                    $keepReads616 = $e617.keepReads;
                                    fabric.common.TransactionID $currentTid618 =
                                      $tm619.getCurrentTid();
                                    if ($e617.tid ==
                                          null ||
                                          !$e617.tid.isDescendantOf(
                                                       $currentTid618)) {
                                        throw $e617;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e617);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e617) {
                                    $commit614 = false;
                                    fabric.common.TransactionID $currentTid618 =
                                      $tm619.getCurrentTid();
                                    if ($e617.tid.isDescendantOf(
                                                    $currentTid618))
                                        continue $label613;
                                    if ($currentTid618.parent != null) {
                                        $retry615 = false;
                                        throw $e617;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e617) {
                                    $commit614 = false;
                                    $retry615 = false;
                                    if ($tm619.inNestedTxn()) {
                                        $keepReads616 = true;
                                    }
                                    throw $e617;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid618 =
                                      $tm619.getCurrentTid();
                                    if ($commit614) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e617) {
                                            $commit614 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e617) {
                                            $commit614 = false;
                                            $retry615 = false;
                                            $keepReads616 = $e617.keepReads;
                                            if ($e617.tid ==
                                                  null ||
                                                  !$e617.tid.isDescendantOf(
                                                               $currentTid618))
                                                throw $e617;
                                            throw new fabric.worker.
                                                    UserAbortException($e617);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e617) {
                                            $commit614 = false;
                                            $currentTid618 =
                                              $tm619.getCurrentTid();
                                            if ($currentTid618 != null) {
                                                if ($e617.tid.
                                                      equals($currentTid618) ||
                                                      !$e617.tid.
                                                      isDescendantOf(
                                                        $currentTid618)) {
                                                    throw $e617;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm619.inNestedTxn() &&
                                              $tm619.checkForStaleObjects()) {
                                            $retry615 = true;
                                            $keepReads616 = false;
                                        }
                                        if ($keepReads616) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e617) {
                                                $currentTid618 = $tm619.getCurrentTid();
                                                if ($currentTid618 != null &&
                                                      ($e617.tid.equals($currentTid618) || !$e617.tid.isDescendantOf($currentTid618))) {
                                                    throw $e617;
                                                } else {
                                                    $retry615 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit614) {
                                        {  }
                                        if ($retry615) { continue $label613; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -42, -124, 0, -127,
        17, -63, -32, -7, -3, -26, -95, -36, 124, -35, -96, -13, -44, 98, 80,
        -15, -57, 117, -83, 35, -59, 76, 63, -108, 76, -51, -22, -82 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #nCopies(int, Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface CopiesList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        public int get$n();
        
        public int set$n(int val);
        
        public int postInc$n();
        
        public int postDec$n();
        
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Constructs the list.
     *
     * @param n the count
     * @param o the object
     * @throws IllegalArgumentException if n &lt; 0
     */
        public CopiesList fabric$util$Collections$CopiesList$(int n,
                                                              fabric.lang.Object o);
        
        /**
     * The size is fixed.
     * @return The size of the list.
     */
        public int size();
        
        /**
     * The same element is returned.
     * @param index The index of the element to be returned (irrelevant
     *        as the list contains only copies of <code>element</code>).
     * @return The element used by this list.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * This list only contains one element.
     * @param o The object to search for.
     * @return <code>true</code> if o is the element used by this list.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * The index is either 0 or -1.
     * @param o The object to find the index of.
     * @return 0 if <code>o == element</code>, -1 if not.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * The index is either n-1 or -1.
     * @param o The object to find the last index of.
     * @return The last index in the list if <code>o == element</code>,
     *         -1 if not.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * A subList is just another CopiesList.
     * @param from The starting bound of the sublist.
     * @param to The ending bound of the sublist.
     * @return A list of copies containing <code>from - to</code>
     *         elements, all of which are equal to the element
     *         used by this list.
     */
        public fabric.util.List subList(int from, int to);
        
        /**
     * The array is easy.
     * @return An array of size n filled with copies of
     *         the element used by this list.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * The string is easy to generate.
     * @return A string representation of the list.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements CopiesList {
            public int get$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  get$n();
            }
            
            public int set$n(int val) {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  set$n(val);
            }
            
            public int postInc$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  postInc$n();
            }
            
            public int postDec$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  postDec$n();
            }
            
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  set$element(val);
            }
            
            public fabric.util.Collections.CopiesList
              fabric$util$Collections$CopiesList$(int arg1,
                                                  fabric.lang.Object arg2) {
                return ((fabric.util.Collections.CopiesList) fetch()).
                  fabric$util$Collections$CopiesList$(arg1, arg2);
            }
            
            public _Proxy(CopiesList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements CopiesList {
            public int get$n() { return this.n; }
            
            public int set$n(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.n = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$n() {
                int tmp = this.get$n();
                this.set$n((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$n() {
                int tmp = this.get$n();
                this.set$n((int) (tmp - 1));
                return tmp;
            }
            
            private int n;
            
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object element;
            
            /**
     * Constructs the list.
     *
     * @param n the count
     * @param o the object
     * @throws IllegalArgumentException if n &lt; 0
     */
            public CopiesList fabric$util$Collections$CopiesList$(
              int n, fabric.lang.Object o) {
                if (n < 0) throw new java.lang.IllegalArgumentException();
                this.set$n((int) n);
                this.set$element(o);
                fabric$util$AbstractList$();
                return (CopiesList) this.$getProxy();
            }
            
            /**
     * The size is fixed.
     * @return The size of the list.
     */
            public int size() { return this.get$n(); }
            
            /**
     * The same element is returned.
     * @param index The index of the element to be returned (irrelevant
     *        as the list contains only copies of <code>element</code>).
     * @return The element used by this list.
     */
            public fabric.lang.Object get(int index) {
                if (index < 0 || index >= this.get$n())
                    throw new java.lang.IndexOutOfBoundsException();
                return this.get$element();
            }
            
            /**
     * This list only contains one element.
     * @param o The object to search for.
     * @return <code>true</code> if o is the element used by this list.
     */
            public boolean contains(fabric.lang.Object o) {
                return this.get$n() >
                  0 &&
                  fabric.util.AbstractCollection._Impl.equals(
                                                         o, this.get$element());
            }
            
            /**
     * The index is either 0 or -1.
     * @param o The object to find the index of.
     * @return 0 if <code>o == element</code>, -1 if not.
     */
            public int indexOf(fabric.lang.Object o) {
                return this.get$n() >
                  0 &&
                  fabric.util.AbstractCollection._Impl.equals(
                                                         o, this.get$element())
                  ? 0
                  : -1;
            }
            
            /**
     * The index is either n-1 or -1.
     * @param o The object to find the last index of.
     * @return The last index in the list if <code>o == element</code>,
     *         -1 if not.
     */
            public int lastIndexOf(fabric.lang.Object o) {
                return fabric.util.AbstractCollection._Impl.equals(
                                                              o,
                                                              this.get$element(
                                                                     ))
                  ? this.get$n() -
                  1
                  : -1;
            }
            
            /**
     * A subList is just another CopiesList.
     * @param from The starting bound of the sublist.
     * @param to The ending bound of the sublist.
     * @return A list of copies containing <code>from - to</code>
     *         elements, all of which are equal to the element
     *         used by this list.
     */
            public fabric.util.List subList(int from, int to) {
                if (from < 0 || to > this.get$n())
                    throw new java.lang.IndexOutOfBoundsException();
                return (CopiesList)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.CopiesList)
                              new fabric.util.Collections.CopiesList._Impl(
                                this.$getStore()).$getProxy()).
                               fabric$util$Collections$CopiesList$(
                                 to - from, this.get$element()));
            }
            
            /**
     * The array is easy.
     * @return An array of size n filled with copies of
     *         the element used by this list.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                fabric.lang.arrays.ObjectArray a =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(
                      this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          fabric.lang.Object._Proxy.class,
                                          this.get$n()).$getProxy();
                fabric.util.Arrays._Impl.fill(a, this.get$element());
                return a;
            }
            
            /**
     * The string is easy to generate.
     * @return A string representation of the list.
     */
            public java.lang.String toString() {
                java.lang.StringBuffer r = new java.lang.StringBuffer("{");
                for (int i = this.get$n() - 1; --i > 0; )
                    r.append(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$element(
                                                                     ))).
                      append(", ");
                r.append(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$element())).
                  append("}");
                return r.toString();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (CopiesList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.CopiesList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.n);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.n = in.readInt();
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.CopiesList._Impl src =
                  (fabric.util.Collections.CopiesList._Impl) other;
                this.n = src.n;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.CopiesList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.CopiesList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.CopiesList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      CopiesList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        CopiesList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.CopiesList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.CopiesList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.CopiesList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.CopiesList._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm629 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled632 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff630 = 1;
                            boolean $doBackoff631 = true;
                            boolean $retry625 = true;
                            boolean $keepReads626 = false;
                            $label623: for (boolean $commit624 = false;
                                            !$commit624; ) {
                                if ($backoffEnabled632) {
                                    if ($doBackoff631) {
                                        if ($backoff630 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff630));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e627) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff630 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff630 =
                                              java.lang.Math.
                                                min(
                                                  $backoff630 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff631 = $backoff630 <= 32 ||
                                                      !$doBackoff631;
                                }
                                $commit624 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.CopiesList._Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 2739099268398711800L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e627) {
                                    $commit624 = false;
                                    continue $label623;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e627) {
                                    $commit624 = false;
                                    $retry625 = false;
                                    $keepReads626 = $e627.keepReads;
                                    fabric.common.TransactionID $currentTid628 =
                                      $tm629.getCurrentTid();
                                    if ($e627.tid ==
                                          null ||
                                          !$e627.tid.isDescendantOf(
                                                       $currentTid628)) {
                                        throw $e627;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e627);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e627) {
                                    $commit624 = false;
                                    fabric.common.TransactionID $currentTid628 =
                                      $tm629.getCurrentTid();
                                    if ($e627.tid.isDescendantOf(
                                                    $currentTid628))
                                        continue $label623;
                                    if ($currentTid628.parent != null) {
                                        $retry625 = false;
                                        throw $e627;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e627) {
                                    $commit624 = false;
                                    $retry625 = false;
                                    if ($tm629.inNestedTxn()) {
                                        $keepReads626 = true;
                                    }
                                    throw $e627;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid628 =
                                      $tm629.getCurrentTid();
                                    if ($commit624) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e627) {
                                            $commit624 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e627) {
                                            $commit624 = false;
                                            $retry625 = false;
                                            $keepReads626 = $e627.keepReads;
                                            if ($e627.tid ==
                                                  null ||
                                                  !$e627.tid.isDescendantOf(
                                                               $currentTid628))
                                                throw $e627;
                                            throw new fabric.worker.
                                                    UserAbortException($e627);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e627) {
                                            $commit624 = false;
                                            $currentTid628 =
                                              $tm629.getCurrentTid();
                                            if ($currentTid628 != null) {
                                                if ($e627.tid.
                                                      equals($currentTid628) ||
                                                      !$e627.tid.
                                                      isDescendantOf(
                                                        $currentTid628)) {
                                                    throw $e627;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm629.inNestedTxn() &&
                                              $tm629.checkForStaleObjects()) {
                                            $retry625 = true;
                                            $keepReads626 = false;
                                        }
                                        if ($keepReads626) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e627) {
                                                $currentTid628 = $tm629.getCurrentTid();
                                                if ($currentTid628 != null &&
                                                      ($e627.tid.equals($currentTid628) || !$e627.tid.isDescendantOf($currentTid628))) {
                                                    throw $e627;
                                                } else {
                                                    $retry625 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit624) {
                                        {  }
                                        if ($retry625) { continue $label623; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 12, 14, 85, -102,
        -8, 93, 34, -21, 10, 39, -125, -97, 66, -94, -102, -74, 90, -98, -119,
        -77, 66, 32, -85, -9, 85, -31, 72, 71, -81, -40, 23, 30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #reverseOrder()}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface ReverseComparator
      extends fabric.util.Comparator, java.io.Serializable, fabric.lang.Object {
        /**
     * A private constructor adds overhead.
     */
        public ReverseComparator fabric$util$Collections$ReverseComparator$();
        
        /**
     * Compare two objects in reverse natural order.
     *
     * @param a the first object
     * @param b the second object
     * @return &lt;, ==, or &gt; 0 according to b.compareTo(a)
     */
        public int compare(fabric.lang.Object a, fabric.lang.Object b);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements ReverseComparator {
            public fabric.util.Collections.ReverseComparator
              fabric$util$Collections$ReverseComparator$() {
                return ((fabric.util.Collections.ReverseComparator) fetch()).
                  fabric$util$Collections$ReverseComparator$();
            }
            
            public int compare(fabric.lang.Object arg1,
                               fabric.lang.Object arg2) {
                return ((fabric.util.Collections.ReverseComparator) fetch()).
                  compare(arg1, arg2);
            }
            
            public _Proxy(ReverseComparator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements ReverseComparator {
            /**
     * A private constructor adds overhead.
     */
            public ReverseComparator fabric$util$Collections$ReverseComparator$(
              ) {
                fabric$lang$Object$();
                return (ReverseComparator) this.$getProxy();
            }
            
            /**
     * Compare two objects in reverse natural order.
     *
     * @param a the first object
     * @param b the second object
     * @return &lt;, ==, or &gt; 0 according to b.compareTo(a)
     */
            public int compare(fabric.lang.Object a, fabric.lang.Object b) {
                return ((java.lang.Comparable)
                          (java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.$unwrap(b)).
                  compareTo((java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(a));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (ReverseComparator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.ReverseComparator._Proxy(
                         this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.ReverseComparator._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.ReverseComparator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.ReverseComparator.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      ReverseComparator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        ReverseComparator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.ReverseComparator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.ReverseComparator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.ReverseComparator._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.ReverseComparator.
                             _Static._Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm639 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled642 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff640 = 1;
                            boolean $doBackoff641 = true;
                            boolean $retry635 = true;
                            boolean $keepReads636 = false;
                            $label633: for (boolean $commit634 = false;
                                            !$commit634; ) {
                                if ($backoffEnabled642) {
                                    if ($doBackoff641) {
                                        if ($backoff640 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff640));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e637) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff640 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff640 =
                                              java.lang.Math.
                                                min(
                                                  $backoff640 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff641 = $backoff640 <= 32 ||
                                                      !$doBackoff641;
                                }
                                $commit634 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.ReverseComparator.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 7207038068494060240L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e637) {
                                    $commit634 = false;
                                    continue $label633;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e637) {
                                    $commit634 = false;
                                    $retry635 = false;
                                    $keepReads636 = $e637.keepReads;
                                    fabric.common.TransactionID $currentTid638 =
                                      $tm639.getCurrentTid();
                                    if ($e637.tid ==
                                          null ||
                                          !$e637.tid.isDescendantOf(
                                                       $currentTid638)) {
                                        throw $e637;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e637);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e637) {
                                    $commit634 = false;
                                    fabric.common.TransactionID $currentTid638 =
                                      $tm639.getCurrentTid();
                                    if ($e637.tid.isDescendantOf(
                                                    $currentTid638))
                                        continue $label633;
                                    if ($currentTid638.parent != null) {
                                        $retry635 = false;
                                        throw $e637;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e637) {
                                    $commit634 = false;
                                    $retry635 = false;
                                    if ($tm639.inNestedTxn()) {
                                        $keepReads636 = true;
                                    }
                                    throw $e637;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid638 =
                                      $tm639.getCurrentTid();
                                    if ($commit634) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e637) {
                                            $commit634 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e637) {
                                            $commit634 = false;
                                            $retry635 = false;
                                            $keepReads636 = $e637.keepReads;
                                            if ($e637.tid ==
                                                  null ||
                                                  !$e637.tid.isDescendantOf(
                                                               $currentTid638))
                                                throw $e637;
                                            throw new fabric.worker.
                                                    UserAbortException($e637);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e637) {
                                            $commit634 = false;
                                            $currentTid638 =
                                              $tm639.getCurrentTid();
                                            if ($currentTid638 != null) {
                                                if ($e637.tid.
                                                      equals($currentTid638) ||
                                                      !$e637.tid.
                                                      isDescendantOf(
                                                        $currentTid638)) {
                                                    throw $e637;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm639.inNestedTxn() &&
                                              $tm639.checkForStaleObjects()) {
                                            $retry635 = true;
                                            $keepReads636 = false;
                                        }
                                        if ($keepReads636) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e637) {
                                                $currentTid638 = $tm639.getCurrentTid();
                                                if ($currentTid638 != null &&
                                                      ($e637.tid.equals($currentTid638) || !$e637.tid.isDescendantOf($currentTid638))) {
                                                    throw $e637;
                                                } else {
                                                    $retry635 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit634) {
                                        {  }
                                        if ($retry635) { continue $label633; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -48, 87, -30, -79,
        14, -28, 51, -29, -5, -7, 32, -80, -48, -113, -73, -42, -56, 5, 33,
        -100, -126, -55, -110, 65, -42, 17, 74, 78, -71, 127, -17, -44 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #singleton(Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonSet
      extends java.io.Serializable, fabric.util.AbstractSet {
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Construct a singleton.
     * @param o the element
     */
        public SingletonSet fabric$util$Collections$SingletonSet$(
          fabric.lang.Object o);
        
        /**
     * The size: always 1!
     * @return 1.
     */
        public int size();
        
        /**
     * Returns an iterator over the lone element.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the element of the singleton.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * The hash is just that of the element.
     * 
     * @return The hashcode of the element.
     */
        public int hashCode();
        
        /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$2
          extends fabric.util.Iterator, fabric.lang.Object {
            public fabric.util.Collections.SingletonSet get$out$();
            
            public boolean get$hasNext();
            
            public boolean set$hasNext(boolean val);
            
            /**
         * Returns <code>true</code> if elements still remain to be
         * iterated through.
         *
         * @return <code>true</code> if the element has not yet been returned.
         */
            public boolean hasNext();
            
            /**
         * Returns the element.
         *
         * @return The element used by this singleton.
         * @throws NoSuchElementException if the object
         *         has already been retrieved.
         */
            public fabric.lang.Object next();
            
            /**
         * Removes the element from the singleton.
         * As this set is immutable, this will always
         * throw an exception.
         *
         * @throws UnsupportedOperationException as the
         *         singleton set doesn't support
         *         <code>remove()</code>.
         */
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements Anonymous$2 {
                public fabric.util.Collections.SingletonSet get$out$() {
                    return ((fabric.util.Collections.SingletonSet.Anonymous$2.
                              _Impl) fetch()).get$out$();
                }
                
                public boolean get$hasNext() {
                    return ((fabric.util.Collections.SingletonSet.Anonymous$2.
                              _Impl) fetch()).get$hasNext();
                }
                
                public boolean set$hasNext(boolean val) {
                    return ((fabric.util.Collections.SingletonSet.Anonymous$2.
                              _Impl) fetch()).set$hasNext(val);
                }
                
                public boolean hasNext() {
                    return ((fabric.util.Collections.SingletonSet.Anonymous$2)
                              fetch()).hasNext();
                }
                
                public fabric.lang.Object next() {
                    return ((fabric.util.Collections.SingletonSet.Anonymous$2)
                              fetch()).next();
                }
                
                public void remove() {
                    ((fabric.util.Collections.SingletonSet.Anonymous$2)
                       fetch()).remove();
                }
                
                public _Proxy(Anonymous$2._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl
              implements Anonymous$2 {
                public fabric.util.Collections.SingletonSet get$out$() {
                    return this.out$;
                }
                
                private SingletonSet out$;
                
                public boolean get$hasNext() {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerRead(this);
                    return this.hasNext;
                }
                
                public boolean set$hasNext(boolean val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean transactionCreated = tm.registerWrite(this);
                    this.hasNext = val;
                    if (transactionCreated) tm.commitTransaction();
                    return val;
                }
                
                /**
         * Flag to indicate whether or not the element has
         * been retrieved.
         */
                private boolean hasNext = true;
                
                /**
         * Returns <code>true</code> if elements still remain to be
         * iterated through.
         *
         * @return <code>true</code> if the element has not yet been returned.
         */
                public boolean hasNext() { return this.get$hasNext(); }
                
                /**
         * Returns the element.
         *
         * @return The element used by this singleton.
         * @throws NoSuchElementException if the object
         *         has already been retrieved.
         */
                public fabric.lang.Object next() {
                    if (this.get$hasNext()) {
                        this.set$hasNext(false);
                        return this.get$out$().get$element();
                    } else
                        throw new fabric.util.NoSuchElementException();
                }
                
                /**
         * Removes the element from the singleton.
         * As this set is immutable, this will always
         * throw an exception.
         *
         * @throws UnsupportedOperationException as the
         *         singleton set doesn't support
         *         <code>remove()</code>.
         */
                public void remove() {
                    throw new java.lang.UnsupportedOperationException();
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (Anonymous$2) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SingletonSet out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.SingletonSet.Anonymous$2.
                             _Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                    out.writeBoolean(this.hasNext);
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
                    this.out$ =
                      (fabric.util.Collections.SingletonSet)
                        $readRef(
                          fabric.util.Collections.SingletonSet._Proxy.class,
                          (fabric.common.RefTypeEnum) refTypes.next(), in,
                          store, intraStoreRefs, interStoreRefs);
                    this.hasNext = in.readBoolean();
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.Collections.SingletonSet.Anonymous$2._Impl src =
                      (fabric.util.Collections.SingletonSet.Anonymous$2._Impl)
                        other;
                    this.out$ = src.out$;
                    this.hasNext = src.hasNext;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.Collections.SingletonSet.Anonymous$2.
                               _Static
                {
                    public _Proxy(fabric.util.Collections.SingletonSet.
                                    Anonymous$2._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.Collections.SingletonSet.
                      Anonymous$2._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          Collections.
                          SingletonSet.
                          Anonymous$2.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            Collections.
                            SingletonSet.
                            Anonymous$2.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.Collections.SingletonSet.Anonymous$2.
                                _Static._Impl.class);
                        $instance =
                          (fabric.util.Collections.SingletonSet.Anonymous$2.
                            _Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.Collections.SingletonSet.Anonymous$2.
                               _Static
                {
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.SingletonSet.
                                 Anonymous$2._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { -28, -25, 99,
            -124, 57, -68, 3, -36, 78, -126, -73, -66, -89, -113, -97, -99, 119,
            29, 7, 31, -85, -53, -62, 36, 115, -58, 35, -86, 41, 2, -120, 80 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements SingletonSet {
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.SingletonSet._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonSet._Impl) fetch()).
                  set$element(val);
            }
            
            public fabric.util.Collections.SingletonSet
              fabric$util$Collections$SingletonSet$(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.SingletonSet) fetch()).
                  fabric$util$Collections$SingletonSet$(arg1);
            }
            
            public _Proxy(SingletonSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractSet._Impl
          implements SingletonSet {
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.lang.Object element;
            
            /**
     * Construct a singleton.
     * @param o the element
     */
            public SingletonSet fabric$util$Collections$SingletonSet$(
              fabric.lang.Object o) {
                this.set$element(o);
                fabric$util$AbstractSet$();
                return (SingletonSet) this.$getProxy();
            }
            
            /**
     * The size: always 1!
     * @return 1.
     */
            public int size() { return 1; }
            
            /**
     * Returns an iterator over the lone element.
     */
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return (fabric.util.Iterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.SingletonSet.Anonymous$2)
                              new fabric.util.Collections.SingletonSet.
                                Anonymous$2._Impl(
                                store,
                                (fabric.util.Collections.SingletonSet)
                                  this.$getProxy()).
                              $getProxy()).fabric$lang$Object$());
            }
            
            /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the element of the singleton.
     */
            public boolean contains(fabric.lang.Object o) {
                return fabric.util.AbstractCollection._Impl.equals(
                                                              o,
                                                              this.get$element(
                                                                     ));
            }
            
            /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
            public boolean containsAll(fabric.util.Collection c) {
                fabric.util.Iterator
                  i =
                  c.
                  iterator(
                    fabric.util.AbstractCollection._Static._Proxy.$instance.
                        get$LOCAL_STORE());
                int pos = c.size();
                while (--pos >= 0)
                    if (!fabric.util.AbstractCollection._Impl.
                          equals(i.next(), this.get$element()))
                        return false;
                return true;
            }
            
            /**
     * The hash is just that of the element.
     * 
     * @return The hashcode of the element.
     */
            public int hashCode() {
                return fabric.util.AbstractCollection._Impl.hashCode(
                                                              this.get$element(
                                                                     ));
            }
            
            /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                return fabric.lang.arrays.internal.Compat.
                  convert(this.$getStore(), this.get$$updateLabel(),
                          this.get$$updateLabel().confPolicy(),
                          new fabric.lang.Object[] { this.get$element() });
            }
            
            /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets.
     */
            public java.lang.String toString() {
                return "[" +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$element())) +
                "]";
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (SingletonSet) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonSet._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonSet._Impl src =
                  (fabric.util.Collections.SingletonSet._Impl) other;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonSet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonSet._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonSet._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonSet._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.SingletonSet._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm649 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled652 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff650 = 1;
                            boolean $doBackoff651 = true;
                            boolean $retry645 = true;
                            boolean $keepReads646 = false;
                            $label643: for (boolean $commit644 = false;
                                            !$commit644; ) {
                                if ($backoffEnabled652) {
                                    if ($doBackoff651) {
                                        if ($backoff650 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff650));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e647) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff650 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff650 =
                                              java.lang.Math.
                                                min(
                                                  $backoff650 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff651 = $backoff650 <= 32 ||
                                                      !$doBackoff651;
                                }
                                $commit644 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.SingletonSet.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 3193687207550431679L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e647) {
                                    $commit644 = false;
                                    continue $label643;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e647) {
                                    $commit644 = false;
                                    $retry645 = false;
                                    $keepReads646 = $e647.keepReads;
                                    fabric.common.TransactionID $currentTid648 =
                                      $tm649.getCurrentTid();
                                    if ($e647.tid ==
                                          null ||
                                          !$e647.tid.isDescendantOf(
                                                       $currentTid648)) {
                                        throw $e647;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e647);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e647) {
                                    $commit644 = false;
                                    fabric.common.TransactionID $currentTid648 =
                                      $tm649.getCurrentTid();
                                    if ($e647.tid.isDescendantOf(
                                                    $currentTid648))
                                        continue $label643;
                                    if ($currentTid648.parent != null) {
                                        $retry645 = false;
                                        throw $e647;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e647) {
                                    $commit644 = false;
                                    $retry645 = false;
                                    if ($tm649.inNestedTxn()) {
                                        $keepReads646 = true;
                                    }
                                    throw $e647;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid648 =
                                      $tm649.getCurrentTid();
                                    if ($commit644) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e647) {
                                            $commit644 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e647) {
                                            $commit644 = false;
                                            $retry645 = false;
                                            $keepReads646 = $e647.keepReads;
                                            if ($e647.tid ==
                                                  null ||
                                                  !$e647.tid.isDescendantOf(
                                                               $currentTid648))
                                                throw $e647;
                                            throw new fabric.worker.
                                                    UserAbortException($e647);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e647) {
                                            $commit644 = false;
                                            $currentTid648 =
                                              $tm649.getCurrentTid();
                                            if ($currentTid648 != null) {
                                                if ($e647.tid.
                                                      equals($currentTid648) ||
                                                      !$e647.tid.
                                                      isDescendantOf(
                                                        $currentTid648)) {
                                                    throw $e647;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm649.inNestedTxn() &&
                                              $tm649.checkForStaleObjects()) {
                                            $retry645 = true;
                                            $keepReads646 = false;
                                        }
                                        if ($keepReads646) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e647) {
                                                $currentTid648 = $tm649.getCurrentTid();
                                                if ($currentTid648 != null &&
                                                      ($e647.tid.equals($currentTid648) || !$e647.tid.isDescendantOf($currentTid648))) {
                                                    throw $e647;
                                                } else {
                                                    $retry645 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit644) {
                                        {  }
                                        if ($retry645) { continue $label643; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -73, 79, -12, -9,
        15, -104, -109, -79, 55, -96, -3, -12, -61, -45, -114, -30, 127, -84,
        116, -37, -70, 63, -15, 101, 116, 40, -83, -98, 87, -89, 71, -38 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK0YDYxUxXl2727vh4M7/uWAA4+FlB93g20weq0IKz8ri1w4wPaIXGffzt49ePve8t4sLNaztk0DadpLI0jPRK8mglR6gmKJpAZrWq0aGlOpqaixkKa2UkpSUltJU2u/b2Z23+7bd+td6iU739zM9818/9/3ZuQqqXNs0pGmSd2I8H1Z5kTW0mQ80UVth6ViBnWcLbDaq02ojR/+8FiqPUiCCdKsUdMydY0avabDyaTETrqHRk3Go1s3xzu3k0YNCddTp5+T4PbVeZvMz1rGvj7D4uqSivMfXho99KMdradqSEsPadHNbk65rsUsk7M87yHNGZZJMttZlUqxVA+ZbDKW6ma2Tg39PkC0zB4yxdH7TMpzNnM2M8cy9iDiFCeXZba4s7CI7FvAtp3TuGUD+62S/RzXjWhCd3hngoTSOjNSzm7yAKlNkLq0QfsAcUaiIEVUnBhdi+uA3qQDm3aaaqxAUrtLN1OczPNSFCUObwAEIK3PMN5vFa+qNSkskCmSJYOafdFubutmH6DWWTm4hZO2UQ8FpIYs1XbRPtbLyQ1evC65BViNQi1Iwsl0L5o4CWzW5rFZibWu3v3lwW+Y680gCQDPKaYZyH8DELV7iDazNLOZqTFJ2LwkcZjOOHsgSAggT/cgS5zn7792x7L2l16TOLN9cDYldzKN92pHkpPenBNbfGsNstGQtRwdXaFMcmHVLrXTmc+Ct88onoibkcLmS5t//bUHj7MrQdIUJyHNMnIZ8KrJmpXJ6gaz1zGT2ZSzVJw0MjMVE/txUg/zhG4yubopnXYYj5NaQyyFLPE/qCgNR6CK6mGum2mrMM9S3i/m+SwhZCb8SA0hoZWEnH6TkLqHCDnyDicbov1WhkWTRo7tBfeOwo9RW+uPQtzaunaTZmX3RR1bi9o5k+uAKdel8MCpAdoCCZ0IsJH9fI/LI/etewMBUOw8zUqxJHXASspjVncZEBTrLSPF7F7NGDwbJ1PPPiK8phE93QFvFXoJgKXneHNEKe2h3Oo11070npMeh7RKbZyEJXvSmiXshbshbgzGLbObcWCvGWMqAlkqAllqJJCPxIbjPxWuE3JEjBVPbIYTb8salKctO5MngYAQb5qgF7eAxXdBJoFk0by4+967vn6gA6yWz+6tBfshatgbOm7CicOMQjz0ai37P/zXycMDlhtEIEtFbFdSYmx2eHVlWxpLQe5zj18yn57uPTsQDmJeaYSUxyk4JeSPdu8dZTHaWch3qI26BJmAOqAGbhWSVBPvt6297orwgUk4TJHugMryMChS5Ve6s49deOPyF0URKWTVlpL0C4bqLIlkPKxFxOxkV/dbbMYA7/2hroMPX92/XSgeMBb4XRjGMQYRTCF0Lfu7r+1+5+IfjrwVdI3FSX3W1vdAYOeFMJM/hb8A/P6LP4xHXEAIWTmmcsH8YjLI4tWLXOZK3W+rmbFSelqnSYOhq/ynZeHy038bbJX2NmBFas8myz77AHd91mry4LkdH7eLYwIaliVXgS6azHVT3ZNX2Tbdh3zkv3V+7iOv0sfA9SFTOfp9TCQfIhRChAVvFrq4SYzLPXtfwqFDamuOWA86lXl/LRZQ1xl7oiOPtsVuvyJDv+iMeMaNPqG/jZbEyc3HM/8MdoReCZL6HtIqajc1+TYK6Qv8oAeqrxNTiwkysWy/vJLKstFZDLY53kAoudYbBm7KgTli47xJer50HFDELFTSWsjbQ4QcPaygjrtTszhOyweImNwmSBaIcREOi4Uia3C6hGM+wu6Hk0Y9k8lxtL+4aSk0K47oerZBLwRG3hq/00f3XbaegfjZo2ouO3Doe59GBg9Jv5ONyYKK3qCURjYn4sqJ4t483HJjtVsExdq/nBx44ScD+2XhnlJeZteYuczTv//kN5GhS6/7JPFaw5IJuFUoZUVRp01EKrbux4Q8uUDBaT46Xe+v0wBOVxbUV88MlmEmF1jTwT1U7UD/iEj/EFuzvEVAsJavZjYcbufgNbpJjXyR/yDyP0PV8gsKvlzCf0kgBQpsyVKjW5FijwsuUGCsERkzLOi+82iXuaO1acImR759aDi16ejyoIreNeBdqpd2721E81Z8A2wUnakbh5euzL01tuuDPmneeZ5rvdhPbRx5fd0i7aEgqSkGXEU7XE7UWR5mTTaDbt7cUhZs84uKFRnpDlDoUXCIkwo6pY7hulOFyaQylnrSXMD1mI0CIV0lD4q+jXKyULpQGL08PFr7EXZ52VGUYAKeNR04f4aQY1sUvGuMEkDlCmVzSUPX8uUqaVIHxRWMeX3NX5zdVfbE4k4ui4X4Z5VKCwju5KRGVyHlEe4W4OEM8HBFwV+MIhwOmUoxkORFBc+MLkagPHimqpjea9m7mB3phsJfjJ3yoBYsDFQR/Ds45Dlp0DkTHUQxQEt7zrjaxL02PzUshJY+QMgzsxUMjEsNgoRIePLfn6kG13u/X0WyQRz2g2SqFjt+Zq1PWpbBqOkn0zJgaCIhzw4rODA+mZDkfgX3jNm0M/xbfaF4celQFZEfxeEgJxMKIq8yxGfrD/3Eg4oTmkPIqcsKvjc+8ZDkXQXfGlsAPlFl7ygOw2Ctfur0x+ArC//P+fE9Fy5dRMhzjys4OD6+keQHCu4fG98jVfZO4HAMHIlbov3EcuWpM2BOsSV7lTeOXZ91Nnz5uqwx3heIEsS/j1y8cn7i3BPi66YWPzrxqibv003ly0zZg4tgsrm8VjfA721C2h9QEMI/9v98MEP2V9/dn8cxguONvvGxAofnsB3y/IuTM/5VJIhTET/dhRYpZDCzT75K5HA4VaWvMiURDi+4BPkie0H1fVDIzKKtEb1WDPo9hl0Nbn0Vh215X6l2SDFKOBGuK+6t4nmvVNl7FYdfQqumIRMF5lpd5mQjKDjL+wUZRHUIDv2ZpeCO8QUZktyr4D1jC7LfVtk7j8M5SA7ckm+FPhKVbFRUQT8J5wF7K4C9FxU8Pj4JkeQpBZ8Ym4TvV9m7iMMFyNxh3dR5giaZIfA2Qmw2l73yKMlnjvIkhNttkIRm+7xUqXdTLfYyO/LBhmXTR3mluqHiJVvRnRhuaZg5vPVtmZEKb6KNCdKQzhlG6edjyTyUtVlaF0I2yo/JrAB/BmFLZIDWC4Hg/08S4zJEqsTA//4q9Nsm5fNTwqqkw22qcVCSQBJntOVsfJ8f+cfM66GGLZfEswjofP7zmz76uGXo4LO3PP7JR7/63eAfvznC3/v5ymuMf+Hp4XuOrXv3fze9sL03GAAA";
    }
    
    /**
   * The implementation of {@link #singletonList(Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Construct a singleton.
     * @param o the element
     */
        public SingletonList fabric$util$Collections$SingletonList$(
          fabric.lang.Object o);
        
        /**
     * The size: always 1!
     * @return 1.
     */
        public int size();
        
        /**
     * Only index 0 is valid.
     * @param index The index of the element
     *        to retrieve.
     * @return The singleton's element if the
     *         index is 0.
     * @throws IndexOutOfBoundsException if
     *         index is not 0.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the singleton element.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Speed up the hashcode computation.
     *
     * @return The hashcode of the list, based
     *         on the hashcode of the singleton element.
     */
        public int hashCode();
        
        /**
     * Either the list has it or not.
     *
     * @param o The object to find the first index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Either the list has it or not.
     *
     * @param o The object to find the last index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * Sublists are limited in scope.
     * 
     * @param from The starting bound for the sublist.
     * @param to The ending bound for the sublist.
     * @return Either an empty list if both bounds are
     *         0 or 1, or this list if the bounds are 0 and 1.
     * @throws IllegalArgumentException if <code>from > to</code>
     * @throws IndexOutOfBoundsException if either bound is greater
     *         than 1.
     */
        public fabric.util.List subList(int from, int to);
        
        /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets. 
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements SingletonList {
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.SingletonList._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonList._Impl) fetch()).
                  set$element(val);
            }
            
            public fabric.util.Collections.SingletonList
              fabric$util$Collections$SingletonList$(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.SingletonList) fetch()).
                  fabric$util$Collections$SingletonList$(arg1);
            }
            
            public _Proxy(SingletonList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements SingletonList {
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object element;
            
            /**
     * Construct a singleton.
     * @param o the element
     */
            public SingletonList fabric$util$Collections$SingletonList$(
              fabric.lang.Object o) {
                this.set$element(o);
                fabric$util$AbstractList$();
                return (SingletonList) this.$getProxy();
            }
            
            /**
     * The size: always 1!
     * @return 1.
     */
            public int size() { return 1; }
            
            /**
     * Only index 0 is valid.
     * @param index The index of the element
     *        to retrieve.
     * @return The singleton's element if the
     *         index is 0.
     * @throws IndexOutOfBoundsException if
     *         index is not 0.
     */
            public fabric.lang.Object get(int index) {
                if (index == 0) return this.get$element();
                throw new java.lang.IndexOutOfBoundsException();
            }
            
            /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the singleton element.
     */
            public boolean contains(fabric.lang.Object o) {
                return fabric.util.AbstractCollection._Impl.equals(
                                                              o,
                                                              this.get$element(
                                                                     ));
            }
            
            /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
            public boolean containsAll(fabric.util.Collection c) {
                fabric.util.Iterator
                  i =
                  c.
                  iterator(
                    fabric.util.AbstractList._Static._Proxy.$instance.
                        get$LOCAL_STORE());
                int pos = c.size();
                while (--pos >= 0)
                    if (!fabric.util.AbstractCollection._Impl.
                          equals(i.next(), this.get$element()))
                        return false;
                return true;
            }
            
            /**
     * Speed up the hashcode computation.
     *
     * @return The hashcode of the list, based
     *         on the hashcode of the singleton element.
     */
            public int hashCode() {
                return 31 +
                  fabric.util.AbstractCollection._Impl.hashCode(
                                                         this.get$element());
            }
            
            /**
     * Either the list has it or not.
     *
     * @param o The object to find the first index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
            public int indexOf(fabric.lang.Object o) {
                return fabric.util.AbstractCollection._Impl.equals(
                                                              o,
                                                              this.get$element(
                                                                     ))
                  ? 0
                  : -1;
            }
            
            /**
     * Either the list has it or not.
     *
     * @param o The object to find the last index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
            public int lastIndexOf(fabric.lang.Object o) {
                return fabric.util.AbstractCollection._Impl.equals(
                                                              o,
                                                              this.get$element(
                                                                     ))
                  ? 0
                  : -1;
            }
            
            /**
     * Sublists are limited in scope.
     * 
     * @param from The starting bound for the sublist.
     * @param to The ending bound for the sublist.
     * @return Either an empty list if both bounds are
     *         0 or 1, or this list if the bounds are 0 and 1.
     * @throws IllegalArgumentException if <code>from > to</code>
     * @throws IndexOutOfBoundsException if either bound is greater
     *         than 1.
     */
            public fabric.util.List subList(int from, int to) {
                if (from == to && (to == 0 || to == 1))
                    return fabric.util.Collections._Static._Proxy.$instance.
                      get$EMPTY_LIST();
                if (from == 0 && to == 1)
                    return (SingletonList) this.$getProxy();
                if (from > to) throw new java.lang.IllegalArgumentException();
                throw new java.lang.IndexOutOfBoundsException();
            }
            
            /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                return fabric.lang.arrays.internal.Compat.
                  convert(this.$getStore(), this.get$$updateLabel(),
                          this.get$$updateLabel().confPolicy(),
                          new fabric.lang.Object[] { this.get$element() });
            }
            
            /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets. 
     */
            public java.lang.String toString() {
                return "[" +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$element())) +
                "]";
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (SingletonList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonList._Impl src =
                  (fabric.util.Collections.SingletonList._Impl) other;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonList.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.SingletonList._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm659 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled662 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff660 = 1;
                            boolean $doBackoff661 = true;
                            boolean $retry655 = true;
                            boolean $keepReads656 = false;
                            $label653: for (boolean $commit654 = false;
                                            !$commit654; ) {
                                if ($backoffEnabled662) {
                                    if ($doBackoff661) {
                                        if ($backoff660 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff660));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e657) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff660 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff660 =
                                              java.lang.Math.
                                                min(
                                                  $backoff660 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff661 = $backoff660 <= 32 ||
                                                      !$doBackoff661;
                                }
                                $commit654 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.SingletonList.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 3093736618740652951L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e657) {
                                    $commit654 = false;
                                    continue $label653;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e657) {
                                    $commit654 = false;
                                    $retry655 = false;
                                    $keepReads656 = $e657.keepReads;
                                    fabric.common.TransactionID $currentTid658 =
                                      $tm659.getCurrentTid();
                                    if ($e657.tid ==
                                          null ||
                                          !$e657.tid.isDescendantOf(
                                                       $currentTid658)) {
                                        throw $e657;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e657);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e657) {
                                    $commit654 = false;
                                    fabric.common.TransactionID $currentTid658 =
                                      $tm659.getCurrentTid();
                                    if ($e657.tid.isDescendantOf(
                                                    $currentTid658))
                                        continue $label653;
                                    if ($currentTid658.parent != null) {
                                        $retry655 = false;
                                        throw $e657;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e657) {
                                    $commit654 = false;
                                    $retry655 = false;
                                    if ($tm659.inNestedTxn()) {
                                        $keepReads656 = true;
                                    }
                                    throw $e657;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid658 =
                                      $tm659.getCurrentTid();
                                    if ($commit654) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e657) {
                                            $commit654 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e657) {
                                            $commit654 = false;
                                            $retry655 = false;
                                            $keepReads656 = $e657.keepReads;
                                            if ($e657.tid ==
                                                  null ||
                                                  !$e657.tid.isDescendantOf(
                                                               $currentTid658))
                                                throw $e657;
                                            throw new fabric.worker.
                                                    UserAbortException($e657);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e657) {
                                            $commit654 = false;
                                            $currentTid658 =
                                              $tm659.getCurrentTid();
                                            if ($currentTid658 != null) {
                                                if ($e657.tid.
                                                      equals($currentTid658) ||
                                                      !$e657.tid.
                                                      isDescendantOf(
                                                        $currentTid658)) {
                                                    throw $e657;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm659.inNestedTxn() &&
                                              $tm659.checkForStaleObjects()) {
                                            $retry655 = true;
                                            $keepReads656 = false;
                                        }
                                        if ($keepReads656) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e657) {
                                                $currentTid658 = $tm659.getCurrentTid();
                                                if ($currentTid658 != null &&
                                                      ($e657.tid.equals($currentTid658) || !$e657.tid.isDescendantOf($currentTid658))) {
                                                    throw $e657;
                                                } else {
                                                    $retry655 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit654) {
                                        {  }
                                        if ($retry655) { continue $label653; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 12, 14, 85, -102,
        -8, 93, 34, -21, 10, 39, -125, -97, 66, -94, -102, -74, 90, -98, -119,
        -77, 66, 32, -85, -9, 85, -31, 72, 71, -81, -40, 23, 30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #singletonMap(Object, Object)}. This class
   * name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonMap
      extends java.io.Serializable, fabric.util.AbstractMap {
        public fabric.lang.Object get$k();
        
        public fabric.lang.Object set$k(fabric.lang.Object val);
        
        public fabric.lang.Object get$v();
        
        public fabric.lang.Object set$v(fabric.lang.Object val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        /**
     * Construct a singleton.
     * @param key the key
     * @param value the value
     */
        public SingletonMap
          fabric$util$Collections$SingletonMap$(fabric.lang.Object key, fabric.lang.Object value);
        
        /**
     * There is a single immutable entry.
     *
     * @return A singleton containing the map entry.
     */
        public fabric.util.Set entrySet();
        
        /**
     * Single entry.
     *
     * @param key The key to look for.
     * @return <code>true</code> if the key is the same as the one used by
     *         this map.
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * Single entry.
     *
     * @param value The value to look for.
     * @return <code>true</code> if the value is the same as the one used by
     *         this map.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Single entry.
     *
     * @param key The key of the value to be retrieved.
     * @return The singleton value if the key is the same as the
     *         singleton key, null otherwise.
     */
        public fabric.lang.Object get(fabric.lang.Object key);
        
        /**
     * Calculate the hashcode directly.
     *
     * @return The hashcode computed from the singleton key
     *         and the singleton value.
     */
        public int hashCode();
        
        /**
     * Return the keyset.
     *
     * @return A singleton containing the key.
     */
        public fabric.util.Set keySet();
        
        /**
     * The size: always 1!
     *
     * @return 1.
     */
        public int size();
        
        /**
     * Return the values. Technically, a singleton, while more specific than
     * a general Collection, will work. Besides, that's what the JDK uses!
     *
     * @return A singleton containing the value.
     */
        public fabric.util.Collection values();
        
        /**
     * Obvious string.
     *
     * @return A string containing the string representations of the key
     *         and its associated value.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static interface Anonymous$3 extends BasicMapEntry {
            public fabric.util.Collections.SingletonMap get$out$();
            
            /**
           * Sets the value of the map entry to the supplied value.
           * An exception is always thrown, as the map is immutable.
           *
           * @param o The new value.
           * @return The old value.
           * @throws UnsupportedOperationException as setting the value
           *         is not supported.
           */
            public fabric.lang.Object setValue(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy
            extends fabric.util.AbstractMap.BasicMapEntry._Proxy
              implements Anonymous$3 {
                public fabric.util.Collections.SingletonMap get$out$() {
                    return ((fabric.util.Collections.SingletonMap.Anonymous$3.
                              _Impl) fetch()).get$out$();
                }
                
                public _Proxy(Anonymous$3._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl
            extends fabric.util.AbstractMap.BasicMapEntry._Impl
              implements Anonymous$3 {
                public fabric.util.Collections.SingletonMap get$out$() {
                    return this.out$;
                }
                
                private SingletonMap out$;
                
                /**
           * Sets the value of the map entry to the supplied value.
           * An exception is always thrown, as the map is immutable.
           *
           * @param o The new value.
           * @return The old value.
           * @throws UnsupportedOperationException as setting the value
           *         is not supported.
           */
                public fabric.lang.Object setValue(fabric.lang.Object o) {
                    throw new java.lang.UnsupportedOperationException();
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (Anonymous$3) this.$getProxy();
                }
                
                private _Impl(fabric.worker.Store $location,
                              final SingletonMap out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.SingletonMap.Anonymous$3.
                             _Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
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
                    this.out$ =
                      (fabric.util.Collections.SingletonMap)
                        $readRef(
                          fabric.util.Collections.SingletonMap._Proxy.class,
                          (fabric.common.RefTypeEnum) refTypes.next(), in,
                          store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.Collections.SingletonMap.Anonymous$3._Impl src =
                      (fabric.util.Collections.SingletonMap.Anonymous$3._Impl)
                        other;
                    this.out$ = src.out$;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.Collections.SingletonMap.Anonymous$3.
                               _Static
                {
                    public _Proxy(fabric.util.Collections.SingletonMap.
                                    Anonymous$3._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.Collections.SingletonMap.
                      Anonymous$3._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          Collections.
                          SingletonMap.
                          Anonymous$3.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            Collections.
                            SingletonMap.
                            Anonymous$3.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.Collections.SingletonMap.Anonymous$3.
                                _Static._Impl.class);
                        $instance =
                          (fabric.util.Collections.SingletonMap.Anonymous$3.
                            _Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.Collections.SingletonMap.Anonymous$3.
                               _Static
                {
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.SingletonMap.
                                 Anonymous$3._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 70, 97, -118,
            -112, 33, 78, -115, 69, 88, 66, 29, 52, 102, -121, -28, -87, 76, 70,
            37, 58, 120, -108, -76, -112, 46, 49, -58, 38, -41, -57, 31, -113 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements SingletonMap {
            public fabric.lang.Object get$k() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$k();
            }
            
            public fabric.lang.Object set$k(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$k(val);
            }
            
            public fabric.lang.Object get$v() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$v();
            }
            
            public fabric.lang.Object set$v(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$v(val);
            }
            
            public fabric.util.Set get$entries() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$entries();
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$entries(val);
            }
            
            public fabric.util.Collections.SingletonMap
              fabric$util$Collections$SingletonMap$(fabric.lang.Object arg1,
                                                    fabric.lang.Object arg2) {
                return ((fabric.util.Collections.SingletonMap) fetch()).
                  fabric$util$Collections$SingletonMap$(arg1, arg2);
            }
            
            public _Proxy(SingletonMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements SingletonMap {
            public fabric.lang.Object get$k() { return this.k; }
            
            public fabric.lang.Object set$k(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.k = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object k;
            
            public fabric.lang.Object get$v() { return this.v; }
            
            public fabric.lang.Object set$v(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.v = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object v;
            
            public fabric.util.Set get$entries() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.entries;
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.entries = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the entry set.
     */
            private transient fabric.util.Set entries;
            
            /**
     * Construct a singleton.
     * @param key the key
     * @param value the value
     */
            public SingletonMap fabric$util$Collections$SingletonMap$(
              fabric.lang.Object key, fabric.lang.Object value) {
                this.set$k(key);
                this.set$v(value);
                fabric$util$AbstractMap$();
                return (SingletonMap) this.$getProxy();
            }
            
            /**
     * There is a single immutable entry.
     *
     * @return A singleton containing the map entry.
     */
            public fabric.util.Set entrySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$entries(),
                                                       null))
                    this.
                      set$entries(
                        fabric.util.Collections._Impl.
                            singleton(
                              (BasicMapEntry)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.util.Collections.SingletonMap.Anonymous$3)
                                     new fabric.util.Collections.SingletonMap.
                                       Anonymous$3._Impl(
                                       this.$getStore(),
                                       (fabric.util.Collections.SingletonMap)
                                         this.$getProxy()).
                                     $getProxy()).
                                      fabric$util$AbstractMap$BasicMapEntry$(
                                        this.get$k(), this.get$v()))));
                return this.get$entries();
            }
            
            /**
     * Single entry.
     *
     * @param key The key to look for.
     * @return <code>true</code> if the key is the same as the one used by
     *         this map.
     */
            public boolean containsKey(fabric.lang.Object key) {
                return fabric.util.AbstractMap._Impl.equals(key, this.get$k());
            }
            
            /**
     * Single entry.
     *
     * @param value The value to look for.
     * @return <code>true</code> if the value is the same as the one used by
     *         this map.
     */
            public boolean containsValue(fabric.lang.Object value) {
                return fabric.util.AbstractMap._Impl.equals(value,
                                                            this.get$v());
            }
            
            /**
     * Single entry.
     *
     * @param key The key of the value to be retrieved.
     * @return The singleton value if the key is the same as the
     *         singleton key, null otherwise.
     */
            public fabric.lang.Object get(fabric.lang.Object key) {
                return fabric.util.AbstractMap._Impl.equals(key, this.get$k())
                  ? this.get$v()
                  : null;
            }
            
            /**
     * Calculate the hashcode directly.
     *
     * @return The hashcode computed from the singleton key
     *         and the singleton value.
     */
            public int hashCode() {
                return fabric.util.AbstractMap._Impl.hashCode(this.get$k()) ^
                  fabric.util.AbstractMap._Impl.hashCode(this.get$v());
            }
            
            /**
     * Return the keyset.
     *
     * @return A singleton containing the key.
     */
            public fabric.util.Set keySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$keys(), null))
                    this.set$keys(
                           fabric.util.Collections._Impl.singleton(
                                                           this.get$k()));
                return this.get$keys();
            }
            
            /**
     * The size: always 1!
     *
     * @return 1.
     */
            public int size() { return 1; }
            
            /**
     * Return the values. Technically, a singleton, while more specific than
     * a general Collection, will work. Besides, that's what the JDK uses!
     *
     * @return A singleton containing the value.
     */
            public fabric.util.Collection values() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$values(), null))
                    this.set$values(
                           fabric.util.Collections._Impl.singleton(
                                                           this.get$v()));
                return this.get$values();
            }
            
            /**
     * Obvious string.
     *
     * @return A string containing the string representations of the key
     *         and its associated value.
     */
            public java.lang.String toString() {
                return "{" +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(this.get$k())) +
                "=" +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(this.get$v())) +
                "}";
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (SingletonMap) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonMap._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.k, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.v, refTypes, out, intraStoreRefs,
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
                this.k = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
                this.v = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonMap._Impl src =
                  (fabric.util.Collections.SingletonMap._Impl) other;
                this.k = src.k;
                this.v = src.v;
                this.entries = src.entries;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonMap._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonMap._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonMap._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.SingletonMap._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm669 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled672 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff670 = 1;
                            boolean $doBackoff671 = true;
                            boolean $retry665 = true;
                            boolean $keepReads666 = false;
                            $label663: for (boolean $commit664 = false;
                                            !$commit664; ) {
                                if ($backoffEnabled672) {
                                    if ($doBackoff671) {
                                        if ($backoff670 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff670));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e667) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff670 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff670 =
                                              java.lang.Math.
                                                min(
                                                  $backoff670 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff671 = $backoff670 <= 32 ||
                                                      !$doBackoff671;
                                }
                                $commit664 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.SingletonMap.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -6979724477215052911L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e667) {
                                    $commit664 = false;
                                    continue $label663;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e667) {
                                    $commit664 = false;
                                    $retry665 = false;
                                    $keepReads666 = $e667.keepReads;
                                    fabric.common.TransactionID $currentTid668 =
                                      $tm669.getCurrentTid();
                                    if ($e667.tid ==
                                          null ||
                                          !$e667.tid.isDescendantOf(
                                                       $currentTid668)) {
                                        throw $e667;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e667);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e667) {
                                    $commit664 = false;
                                    fabric.common.TransactionID $currentTid668 =
                                      $tm669.getCurrentTid();
                                    if ($e667.tid.isDescendantOf(
                                                    $currentTid668))
                                        continue $label663;
                                    if ($currentTid668.parent != null) {
                                        $retry665 = false;
                                        throw $e667;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e667) {
                                    $commit664 = false;
                                    $retry665 = false;
                                    if ($tm669.inNestedTxn()) {
                                        $keepReads666 = true;
                                    }
                                    throw $e667;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid668 =
                                      $tm669.getCurrentTid();
                                    if ($commit664) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e667) {
                                            $commit664 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e667) {
                                            $commit664 = false;
                                            $retry665 = false;
                                            $keepReads666 = $e667.keepReads;
                                            if ($e667.tid ==
                                                  null ||
                                                  !$e667.tid.isDescendantOf(
                                                               $currentTid668))
                                                throw $e667;
                                            throw new fabric.worker.
                                                    UserAbortException($e667);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e667) {
                                            $commit664 = false;
                                            $currentTid668 =
                                              $tm669.getCurrentTid();
                                            if ($currentTid668 != null) {
                                                if ($e667.tid.
                                                      equals($currentTid668) ||
                                                      !$e667.tid.
                                                      isDescendantOf(
                                                        $currentTid668)) {
                                                    throw $e667;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm669.inNestedTxn() &&
                                              $tm669.checkForStaleObjects()) {
                                            $retry665 = true;
                                            $keepReads666 = false;
                                        }
                                        if ($keepReads666) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e667) {
                                                $currentTid668 = $tm669.getCurrentTid();
                                                if ($currentTid668 != null &&
                                                      ($e667.tid.equals($currentTid668) || !$e667.tid.isDescendantOf($currentTid668))) {
                                                    throw $e667;
                                                } else {
                                                    $retry665 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit664) {
                                        {  }
                                        if ($retry665) { continue $label663; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -42, -124, 0, -127,
        17, -63, -32, -7, -3, -26, -95, -36, 124, -35, -96, -13, -44, 98, 80,
        -15, -57, 117, -83, 35, -59, 76, 63, -108, 76, -51, -22, -82 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #unmodifiableCollection(Collection)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableCollection
      extends fabric.util.Collection, java.io.Serializable, fabric.lang.Object {
        public fabric.util.Collection get$c();
        
        public fabric.util.Collection set$c(fabric.util.Collection val);
        
        /**
     * Wrap a given collection.
     * @param c the collection to wrap
     * @throws NullPointerException if c is null
     */
        public UnmodifiableCollection
          fabric$util$Collections$UnmodifiableCollection$(fabric.util.Collection c);
        
        /**
     * Blocks the addition of elements to the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o the object to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the add operation.
     */
        public boolean add(fabric.lang.Object o);
        
        /**
     * Blocks the addition of a collection of elements to the underlying
     * collection.  This method never returns, throwing an exception instead.
     *
     * @param c the collection to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the <code>addAll</code> operation.
     */
        public boolean addAll(fabric.util.Collection c);
        
        /**
     * Blocks the clearing of the underlying collection.  This method never
     * returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection does
     *         not support the <code>clear()</code> operation.
     */
        public void clear();
        
        /**
     * Test whether the underlying collection contains a given object as one of its
     * elements.
     *
     * @param o the element to look for.
     * @return <code>true</code> if the underlying collection contains at least
     *         one element e such that
     *         <code>o == null ? e == null : o.equals(e)</code>.
     * @throws ClassCastException if the type of o is not a valid type for the
     *         underlying collection.
     * @throws NullPointerException if o is null and the underlying collection
     *         doesn't support null values.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * Test whether the underlying collection contains every element in a given
     * collection.
     *
     * @param c1 the collection to test for.
     * @return <code>true</code> if for every element o in c, contains(o) would
     *         return <code>true</code>.
     * @throws ClassCastException if the type of any element in c is not a valid
     *   type for the underlying collection.
     * @throws NullPointerException if some element of c is null and the underlying
     *   collection does not support null values.
     * @throws NullPointerException if c itself is null.
     */
        public boolean containsAll(fabric.util.Collection c1);
        
        /**
     * Tests whether the underlying collection is empty, that is,
     * if size() == 0.
     *
     * @return <code>true</code> if this collection contains no elements.
     */
        public boolean isEmpty();
        
        /**
     * Obtain an Iterator over the underlying collection, which maintains
     * its unmodifiable nature.
     *
     * @return an UnmodifiableIterator over the elements of the underlying
     *         collection, in any order.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public fabric.util.Iterator iterator();
        
        /**
     * Blocks the removal of an object from the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to remove.
     * @return <code>true</code> if the object was removed (i.e. the underlying
     *         collection returned 1 or more instances of o).
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>remove()</code> operation.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Blocks the removal of a collection of objects from the underlying
     * collection.  This method never returns, throwing an exception
     * instead.
     *
     * @param c The collection of objects to remove.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>removeAll()</code> operation.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Blocks the removal of all elements from the underlying collection,
     * except those in the supplied collection.  This method never returns,
     * throwing an exception instead.
     *
     * @param c The collection of objects to retain.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>retainAll()</code> operation.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * Retrieves the number of elements in the underlying collection.
     *
     * @return the number of elements in the collection.
     */
        public int size();
        
        /**
     * Copy the current contents of the underlying collection into an array.
     *
     * @return an array of type Object[] with a length equal to the size of the
     *         underlying collection and containing the elements currently in
     *         the underlying collection, in any order.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Copy the current contents of the underlying collection into an array.  If
     * the array passed as an argument has length less than the size of the
     * underlying collection, an array of the same run-time type as a, with a length
     * equal to the size of the underlying collection, is allocated using reflection.
     * Otherwise, a itself is used.  The elements of the underlying collection are
     * copied into it, and if there is space in the array, the following element is
     * set to null. The resultant array is returned.
     * Note: The fact that the following element is set to null is only useful
     * if it is known that this collection does not contain any null elements.
     *
     * @param a the array to copy this collection into.
     * @return an array containing the elements currently in the underlying
     *         collection, in any order.
     * @throws ArrayStoreException if the type of any element of the
     *         collection is not a subtype of the element type of a.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * A textual representation of the unmodifiable collection.
     *
     * @return The unmodifiable collection in the form of a <code>String</code>.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableCollection {
            public fabric.util.Collection get$c() {
                return ((fabric.util.Collections.UnmodifiableCollection._Impl)
                          fetch()).get$c();
            }
            
            public fabric.util.Collection set$c(fabric.util.Collection val) {
                return ((fabric.util.Collections.UnmodifiableCollection._Impl)
                          fetch()).set$c(val);
            }
            
            public fabric.util.Collections.UnmodifiableCollection
              fabric$util$Collections$UnmodifiableCollection$(
              fabric.util.Collection arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).
                  fabric$util$Collections$UnmodifiableCollection$(arg1);
            }
            
            public boolean add(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).add(arg1);
            }
            
            public boolean addAll(fabric.util.Collection arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).addAll(arg1);
            }
            
            public void clear() {
                ((fabric.util.Collections.UnmodifiableCollection) fetch()).
                  clear();
            }
            
            public boolean contains(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).contains(arg1);
            }
            
            public boolean containsAll(fabric.util.Collection arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).containsAll(arg1);
            }
            
            public boolean isEmpty() {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).isEmpty();
            }
            
            public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).iterator(arg1);
            }
            
            public fabric.util.Iterator iterator() {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).iterator();
            }
            
            public boolean remove(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).remove(arg1);
            }
            
            public boolean removeAll(fabric.util.Collection arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).removeAll(arg1);
            }
            
            public boolean retainAll(fabric.util.Collection arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).retainAll(arg1);
            }
            
            public int size() {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).size();
            }
            
            public fabric.lang.arrays.ObjectArray toArray() {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).toArray();
            }
            
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray arg1) {
                return ((fabric.util.Collections.UnmodifiableCollection)
                          fetch()).toArray(arg1);
            }
            
            public _Proxy(UnmodifiableCollection._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableCollection {
            public fabric.util.Collection get$c() { return this.c; }
            
            public fabric.util.Collection set$c(fabric.util.Collection val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.c = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.util.Collection c;
            
            /**
     * Wrap a given collection.
     * @param c the collection to wrap
     * @throws NullPointerException if c is null
     */
            public UnmodifiableCollection
              fabric$util$Collections$UnmodifiableCollection$(
              fabric.util.Collection c) {
                this.set$c(c);
                if (fabric.lang.Object._Proxy.idEquals(c, null))
                    throw new java.lang.NullPointerException();
                fabric$lang$Object$();
                return (UnmodifiableCollection) this.$getProxy();
            }
            
            /**
     * Blocks the addition of elements to the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o the object to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the add operation.
     */
            public boolean add(fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the addition of a collection of elements to the underlying
     * collection.  This method never returns, throwing an exception instead.
     *
     * @param c the collection to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the <code>addAll</code> operation.
     */
            public boolean addAll(fabric.util.Collection c) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the clearing of the underlying collection.  This method never
     * returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection does
     *         not support the <code>clear()</code> operation.
     */
            public void clear() {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Test whether the underlying collection contains a given object as one of its
     * elements.
     *
     * @param o the element to look for.
     * @return <code>true</code> if the underlying collection contains at least
     *         one element e such that
     *         <code>o == null ? e == null : o.equals(e)</code>.
     * @throws ClassCastException if the type of o is not a valid type for the
     *         underlying collection.
     * @throws NullPointerException if o is null and the underlying collection
     *         doesn't support null values.
     */
            public boolean contains(fabric.lang.Object o) {
                return this.get$c().contains(o);
            }
            
            /**
     * Test whether the underlying collection contains every element in a given
     * collection.
     *
     * @param c1 the collection to test for.
     * @return <code>true</code> if for every element o in c, contains(o) would
     *         return <code>true</code>.
     * @throws ClassCastException if the type of any element in c is not a valid
     *   type for the underlying collection.
     * @throws NullPointerException if some element of c is null and the underlying
     *   collection does not support null values.
     * @throws NullPointerException if c itself is null.
     */
            public boolean containsAll(fabric.util.Collection c1) {
                return this.get$c().containsAll(c1);
            }
            
            /**
     * Tests whether the underlying collection is empty, that is,
     * if size() == 0.
     *
     * @return <code>true</code> if this collection contains no elements.
     */
            public boolean isEmpty() { return this.get$c().isEmpty(); }
            
            /**
     * Obtain an Iterator over the underlying collection, which maintains
     * its unmodifiable nature.
     *
     * @return an UnmodifiableIterator over the elements of the underlying
     *         collection, in any order.
     */
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return ((UnmodifiableIterator)
                          new fabric.util.Collections.UnmodifiableIterator.
                            _Impl(store).
                          $getProxy()).
                  fabric$util$Collections$UnmodifiableIterator$(
                    this.get$c().iterator(store));
            }
            
            public fabric.util.Iterator iterator() {
                fabric.worker.Store store =
                  fabric.util.Collections._Static._Proxy.$instance.
                  get$LOCAL_STORE();
                return iterator(store);
            }
            
            /**
     * Blocks the removal of an object from the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to remove.
     * @return <code>true</code> if the object was removed (i.e. the underlying
     *         collection returned 1 or more instances of o).
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>remove()</code> operation.
     */
            public boolean remove(fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the removal of a collection of objects from the underlying
     * collection.  This method never returns, throwing an exception
     * instead.
     *
     * @param c The collection of objects to remove.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>removeAll()</code> operation.
     */
            public boolean removeAll(fabric.util.Collection c) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the removal of all elements from the underlying collection,
     * except those in the supplied collection.  This method never returns,
     * throwing an exception instead.
     *
     * @param c The collection of objects to retain.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>retainAll()</code> operation.
     */
            public boolean retainAll(fabric.util.Collection c) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Retrieves the number of elements in the underlying collection.
     *
     * @return the number of elements in the collection.
     */
            public int size() { return this.get$c().size(); }
            
            /**
     * Copy the current contents of the underlying collection into an array.
     *
     * @return an array of type Object[] with a length equal to the size of the
     *         underlying collection and containing the elements currently in
     *         the underlying collection, in any order.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                return this.get$c().toArray();
            }
            
            /**
     * Copy the current contents of the underlying collection into an array.  If
     * the array passed as an argument has length less than the size of the
     * underlying collection, an array of the same run-time type as a, with a length
     * equal to the size of the underlying collection, is allocated using reflection.
     * Otherwise, a itself is used.  The elements of the underlying collection are
     * copied into it, and if there is space in the array, the following element is
     * set to null. The resultant array is returned.
     * Note: The fact that the following element is set to null is only useful
     * if it is known that this collection does not contain any null elements.
     *
     * @param a the array to copy this collection into.
     * @return an array containing the elements currently in the underlying
     *         collection, in any order.
     * @throws ArrayStoreException if the type of any element of the
     *         collection is not a subtype of the element type of a.
     */
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray a) {
                return this.get$c().toArray(a);
            }
            
            /**
     * A textual representation of the unmodifiable collection.
     *
     * @return The unmodifiable collection in the form of a <code>String</code>.
     */
            public java.lang.String toString() {
                return this.get$c().toString();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableCollection) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableCollection.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.c, refTypes, out, intraStoreRefs,
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
                this.c = (fabric.util.Collection)
                           $readRef(fabric.util.Collection._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableCollection._Impl src =
                  (fabric.util.Collections.UnmodifiableCollection._Impl) other;
                this.c = src.c;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableCollection._Static
            {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableCollection.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableCollection._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableCollection.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableCollection.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableCollection.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableCollection._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableCollection._Static
            {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableCollection.
                             _Static._Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm679 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled682 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff680 = 1;
                            boolean $doBackoff681 = true;
                            boolean $retry675 = true;
                            boolean $keepReads676 = false;
                            $label673: for (boolean $commit674 = false;
                                            !$commit674; ) {
                                if ($backoffEnabled682) {
                                    if ($doBackoff681) {
                                        if ($backoff680 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff680));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e677) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff680 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff680 =
                                              java.lang.Math.
                                                min(
                                                  $backoff680 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff681 = $backoff680 <= 32 ||
                                                      !$doBackoff681;
                                }
                                $commit674 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableCollection.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 1820017752578914078L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e677) {
                                    $commit674 = false;
                                    continue $label673;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e677) {
                                    $commit674 = false;
                                    $retry675 = false;
                                    $keepReads676 = $e677.keepReads;
                                    fabric.common.TransactionID $currentTid678 =
                                      $tm679.getCurrentTid();
                                    if ($e677.tid ==
                                          null ||
                                          !$e677.tid.isDescendantOf(
                                                       $currentTid678)) {
                                        throw $e677;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e677);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e677) {
                                    $commit674 = false;
                                    fabric.common.TransactionID $currentTid678 =
                                      $tm679.getCurrentTid();
                                    if ($e677.tid.isDescendantOf(
                                                    $currentTid678))
                                        continue $label673;
                                    if ($currentTid678.parent != null) {
                                        $retry675 = false;
                                        throw $e677;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e677) {
                                    $commit674 = false;
                                    $retry675 = false;
                                    if ($tm679.inNestedTxn()) {
                                        $keepReads676 = true;
                                    }
                                    throw $e677;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid678 =
                                      $tm679.getCurrentTid();
                                    if ($commit674) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e677) {
                                            $commit674 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e677) {
                                            $commit674 = false;
                                            $retry675 = false;
                                            $keepReads676 = $e677.keepReads;
                                            if ($e677.tid ==
                                                  null ||
                                                  !$e677.tid.isDescendantOf(
                                                               $currentTid678))
                                                throw $e677;
                                            throw new fabric.worker.
                                                    UserAbortException($e677);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e677) {
                                            $commit674 = false;
                                            $currentTid678 =
                                              $tm679.getCurrentTid();
                                            if ($currentTid678 != null) {
                                                if ($e677.tid.
                                                      equals($currentTid678) ||
                                                      !$e677.tid.
                                                      isDescendantOf(
                                                        $currentTid678)) {
                                                    throw $e677;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm679.inNestedTxn() &&
                                              $tm679.checkForStaleObjects()) {
                                            $retry675 = true;
                                            $keepReads676 = false;
                                        }
                                        if ($keepReads676) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e677) {
                                                $currentTid678 = $tm679.getCurrentTid();
                                                if ($currentTid678 != null &&
                                                      ($e677.tid.equals($currentTid678) || !$e677.tid.isDescendantOf($currentTid678))) {
                                                    throw $e677;
                                                } else {
                                                    $retry675 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit674) {
                                        {  }
                                        if ($retry675) { continue $label673; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 61, -24, -11, -59,
        -82, 113, -88, -72, -15, 80, 5, 81, -39, 76, 44, -104, -107, 52, 41, 57,
        114, 123, 56, 52, -25, 38, -51, -26, -1, -96, 71, -118 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of the various iterator methods in the
   * unmodifiable classes.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableIterator
      extends fabric.util.Iterator, fabric.lang.Object {
        public fabric.util.Iterator get$i();
        
        public fabric.util.Iterator set$i(fabric.util.Iterator val);
        
        /**
     * Only trusted code creates a wrapper.
     * @param i the wrapped iterator
     */
        public UnmodifiableIterator
          fabric$util$Collections$UnmodifiableIterator$(fabric.util.Iterator i);
        
        /**
     * Obtains the next element in the underlying collection.
     *
     * @return the next element in the collection.
     * @throws NoSuchElementException if there are no more elements.
     */
        public fabric.lang.Object next();
        
        /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>next()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>next()</code>.
     *
     * @return <code>true</code> if there is at least one more element in the underlying
     *         collection.
     */
        public boolean hasNext();
        
        /**
     * Blocks the removal of elements from the underlying collection by the
     * iterator.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the removal of elements by its iterator.
     */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableIterator {
            public fabric.util.Iterator get$i() {
                return ((fabric.util.Collections.UnmodifiableIterator._Impl)
                          fetch()).get$i();
            }
            
            public fabric.util.Iterator set$i(fabric.util.Iterator val) {
                return ((fabric.util.Collections.UnmodifiableIterator._Impl)
                          fetch()).set$i(val);
            }
            
            public fabric.util.Collections.UnmodifiableIterator
              fabric$util$Collections$UnmodifiableIterator$(
              fabric.util.Iterator arg1) {
                return ((fabric.util.Collections.UnmodifiableIterator) fetch()).
                  fabric$util$Collections$UnmodifiableIterator$(arg1);
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.Collections.UnmodifiableIterator) fetch()).
                  next();
            }
            
            public boolean hasNext() {
                return ((fabric.util.Collections.UnmodifiableIterator) fetch()).
                  hasNext();
            }
            
            public void remove() {
                ((fabric.util.Collections.UnmodifiableIterator) fetch()).remove(
                                                                           );
            }
            
            public _Proxy(UnmodifiableIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableIterator {
            public fabric.util.Iterator get$i() { return this.i; }
            
            public fabric.util.Iterator set$i(fabric.util.Iterator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.i = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Iterator i;
            
            /**
     * Only trusted code creates a wrapper.
     * @param i the wrapped iterator
     */
            public UnmodifiableIterator
              fabric$util$Collections$UnmodifiableIterator$(
              fabric.util.Iterator i) {
                this.set$i(i);
                fabric$lang$Object$();
                return (UnmodifiableIterator) this.$getProxy();
            }
            
            /**
     * Obtains the next element in the underlying collection.
     *
     * @return the next element in the collection.
     * @throws NoSuchElementException if there are no more elements.
     */
            public fabric.lang.Object next() { return this.get$i().next(); }
            
            /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>next()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>next()</code>.
     *
     * @return <code>true</code> if there is at least one more element in the underlying
     *         collection.
     */
            public boolean hasNext() { return this.get$i().hasNext(); }
            
            /**
     * Blocks the removal of elements from the underlying collection by the
     * iterator.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the removal of elements by its iterator.
     */
            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableIterator._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.i, refTypes, out, intraStoreRefs,
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
                this.i = (fabric.util.Iterator)
                           $readRef(fabric.util.Iterator._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableIterator._Impl src =
                  (fabric.util.Collections.UnmodifiableIterator._Impl) other;
                this.i = src.i;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableIterator._Static {
                public _Proxy(fabric.util.Collections.UnmodifiableIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableIterator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableIterator._Static {
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
                    return new fabric.util.Collections.UnmodifiableIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -28, -25, 99, -124,
        57, -68, 3, -36, 78, -126, -73, -66, -89, -113, -97, -99, 119, 29, 7,
        31, -85, -53, -62, 36, 115, -58, 35, -86, 41, 2, -120, 80 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #unmodifiableList(List)} for sequential
   * lists. This class name is required for compatibility with Sun's JDK
   * serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableList
      extends fabric.util.List, UnmodifiableCollection {
        public fabric.util.List get$list();
        
        public fabric.util.List set$list(fabric.util.List val);
        
        /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
        public UnmodifiableList fabric$util$Collections$UnmodifiableList$(fabric.util.List l);
        
        /**
     * Blocks the addition of an element to the underlying
     * list at a specific index.  This method never returns,
     * throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param o the object to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>add()</code> operation.
     */
        public void add(int index, fabric.lang.Object o);
        
        /**
     * Blocks the addition of a collection of elements to the
     * underlying list at a specific index.  This method never
     * returns, throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param c the collections of objects to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>addAll()</code> operation.
     */
        public boolean addAll(int index, fabric.util.Collection c);
        
        /**
     * Returns <code>true</code> if the object, o, is an instance of
     * <code>List</code> with the same size and elements
     * as the underlying list.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is equivalent to the underlying list.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Retrieves the element at a given index in the underlying list.
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Computes the hash code for the underlying list.
     * The exact computation is described in the documentation
     * of the <code>List</code> interface.
     *
     * @return The hash code of the underlying list.
     * @see List#hashCode()
     */
        public int hashCode();
        
        /**
     * Obtain the first index at which a given object is to be found in the
     * underlying list.
     *
     * @param o the object to search for
     * @return the least integer n such that <code>o == null ? get(n) == null :
     *         o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Obtain the last index at which a given object is to be found in the
     * underlying list.
     *
     * @return the greatest integer n such that <code>o == null ? get(n) == null
     *         : o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
   * Obtains a list iterator over the underlying list, starting at the beginning
   * and maintaining the unmodifiable nature of this list.
   *
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the beginning.
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store);
        
        /**
   * Obtains a list iterator over the underlying list, starting at the specified
   * index and maintaining the unmodifiable nature of this list.  An initial call
   * to <code>next()</code> will retrieve the element at the specified index,
   * and an initial call to <code>previous()</code> will retrieve the element
   * at index - 1.
   *
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from.
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the specified index.
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);
        
        /**
     * Blocks the removal of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to remove.
     * @return the removed element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>remove()</code>
     *         operation.
     */
        public fabric.lang.Object remove(int index);
        
        /**
     * Blocks the replacement of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to replace.
     * @param o The new object to place at the specified index.
     * @return the replaced element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>set()</code>
     *         operation.
     */
        public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        /**
     * Obtain a List view of a subsection of the underlying list, from
     * fromIndex (inclusive) to toIndex (exclusive). If the two indices
     * are equal, the sublist is empty. The returned list will be
     * unmodifiable, like this list.  Changes to the elements of the
     * returned list will be reflected in the underlying list. No structural
     * modifications can take place in either list.
     *
     * @param fromIndex the index that the returned list should start from
     *        (inclusive).
     * @param toIndex the index that the returned list should go to (exclusive).
     * @return a List backed by a subsection of the underlying list.
     * @throws IndexOutOfBoundsException if fromIndex &lt; 0
     *         || toIndex &gt; size() || fromIndex &gt; toIndex.
     */
        public fabric.util.List subList(int fromIndex, int toIndex);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableCollection._Proxy
          implements UnmodifiableList {
            public fabric.util.List get$list() {
                return ((fabric.util.Collections.UnmodifiableList._Impl)
                          fetch()).get$list();
            }
            
            public fabric.util.List set$list(fabric.util.List val) {
                return ((fabric.util.Collections.UnmodifiableList._Impl)
                          fetch()).set$list(val);
            }
            
            public fabric.util.Collections.UnmodifiableList
              fabric$util$Collections$UnmodifiableList$(fabric.util.List arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  fabric$util$Collections$UnmodifiableList$(arg1);
            }
            
            public void add(int arg1, fabric.lang.Object arg2) {
                ((fabric.util.Collections.UnmodifiableList) fetch()).add(arg1,
                                                                         arg2);
            }
            
            public boolean addAll(int arg1, fabric.util.Collection arg2) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  addAll(arg1, arg2);
            }
            
            public fabric.lang.Object get(int arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  get(arg1);
            }
            
            public int indexOf(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  indexOf(arg1);
            }
            
            public int lastIndexOf(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  lastIndexOf(arg1);
            }
            
            public fabric.util.ListIterator listIterator(
              fabric.worker.Store arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  listIterator(arg1);
            }
            
            public fabric.util.ListIterator listIterator(
              fabric.worker.Store arg1, int arg2) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  listIterator(arg1, arg2);
            }
            
            public fabric.lang.Object remove(int arg1) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  remove(arg1);
            }
            
            public fabric.lang.Object set(int arg1, fabric.lang.Object arg2) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  set(arg1, arg2);
            }
            
            public fabric.util.List subList(int arg1, int arg2) {
                return ((fabric.util.Collections.UnmodifiableList) fetch()).
                  subList(arg1, arg2);
            }
            
            public _Proxy(UnmodifiableList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableCollection._Impl
          implements UnmodifiableList {
            public fabric.util.List get$list() { return this.list; }
            
            public fabric.util.List set$list(fabric.util.List val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.list = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.util.List list;
            
            /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
            public UnmodifiableList fabric$util$Collections$UnmodifiableList$(
              fabric.util.List l) {
                this.set$list(l);
                fabric$util$Collections$UnmodifiableCollection$(l);
                return (UnmodifiableList) this.$getProxy();
            }
            
            /**
     * Blocks the addition of an element to the underlying
     * list at a specific index.  This method never returns,
     * throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param o the object to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>add()</code> operation.
     */
            public void add(int index, fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the addition of a collection of elements to the
     * underlying list at a specific index.  This method never
     * returns, throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param c the collections of objects to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>addAll()</code> operation.
     */
            public boolean addAll(int index, fabric.util.Collection c) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Returns <code>true</code> if the object, o, is an instance of
     * <code>List</code> with the same size and elements
     * as the underlying list.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is equivalent to the underlying list.
     */
            public boolean equals(fabric.lang.Object o) {
                return this.get$list().equals(o);
            }
            
            /**
     * Retrieves the element at a given index in the underlying list.
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
            public fabric.lang.Object get(int index) {
                return this.get$list().get(index);
            }
            
            /**
     * Computes the hash code for the underlying list.
     * The exact computation is described in the documentation
     * of the <code>List</code> interface.
     *
     * @return The hash code of the underlying list.
     * @see List#hashCode()
     */
            public int hashCode() { return this.get$list().hashCode(); }
            
            /**
     * Obtain the first index at which a given object is to be found in the
     * underlying list.
     *
     * @param o the object to search for
     * @return the least integer n such that <code>o == null ? get(n) == null :
     *         o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
            public int indexOf(fabric.lang.Object o) {
                return this.get$list().indexOf(o);
            }
            
            /**
     * Obtain the last index at which a given object is to be found in the
     * underlying list.
     *
     * @return the greatest integer n such that <code>o == null ? get(n) == null
     *         : o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
            public int lastIndexOf(fabric.lang.Object o) {
                return this.get$list().lastIndexOf(o);
            }
            
            /**
   * Obtains a list iterator over the underlying list, starting at the beginning
   * and maintaining the unmodifiable nature of this list.
   *
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the beginning.
   */
            public fabric.util.ListIterator listIterator(
              fabric.worker.Store store) {
                return (UnmodifiableListIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableListIterator)
                              new fabric.util.Collections.
                                UnmodifiableListIterator._Impl(store).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableListIterator$(
                                 this.get$list().listIterator(store)));
            }
            
            /**
   * Obtains a list iterator over the underlying list, starting at the specified
   * index and maintaining the unmodifiable nature of this list.  An initial call
   * to <code>next()</code> will retrieve the element at the specified index,
   * and an initial call to <code>previous()</code> will retrieve the element
   * at index - 1.
   *
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from.
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the specified index.
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
            public fabric.util.ListIterator listIterator(
              fabric.worker.Store store, int index) {
                return (UnmodifiableListIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableListIterator)
                              new fabric.util.Collections.
                                UnmodifiableListIterator._Impl(store).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableListIterator$(
                                 this.get$list().listIterator(store, index)));
            }
            
            /**
     * Blocks the removal of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to remove.
     * @return the removed element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>remove()</code>
     *         operation.
     */
            public fabric.lang.Object remove(int index) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the replacement of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to replace.
     * @param o The new object to place at the specified index.
     * @return the replaced element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>set()</code>
     *         operation.
     */
            public fabric.lang.Object set(int index, fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Obtain a List view of a subsection of the underlying list, from
     * fromIndex (inclusive) to toIndex (exclusive). If the two indices
     * are equal, the sublist is empty. The returned list will be
     * unmodifiable, like this list.  Changes to the elements of the
     * returned list will be reflected in the underlying list. No structural
     * modifications can take place in either list.
     *
     * @param fromIndex the index that the returned list should start from
     *        (inclusive).
     * @param toIndex the index that the returned list should go to (exclusive).
     * @return a List backed by a subsection of the underlying list.
     * @throws IndexOutOfBoundsException if fromIndex &lt; 0
     *         || toIndex &gt; size() || fromIndex &gt; toIndex.
     */
            public fabric.util.List subList(int fromIndex, int toIndex) {
                return fabric.util.Collections._Impl.unmodifiableList(
                                                       $getStore(),
                                                       this.get$list(
                                                              ).subList(
                                                                  fromIndex,
                                                                  toIndex));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableList._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.list, refTypes, out, intraStoreRefs,
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
                this.list = (fabric.util.List)
                              $readRef(fabric.util.List._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableList._Impl src =
                  (fabric.util.Collections.UnmodifiableList._Impl) other;
                this.list = src.list;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableList.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableList._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableList._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableList._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm689 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled692 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff690 = 1;
                            boolean $doBackoff691 = true;
                            boolean $retry685 = true;
                            boolean $keepReads686 = false;
                            $label683: for (boolean $commit684 = false;
                                            !$commit684; ) {
                                if ($backoffEnabled692) {
                                    if ($doBackoff691) {
                                        if ($backoff690 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff690));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e687) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff690 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff690 =
                                              java.lang.Math.
                                                min(
                                                  $backoff690 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff691 = $backoff690 <= 32 ||
                                                      !$doBackoff691;
                                }
                                $commit684 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableList.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -283967356065247728L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e687) {
                                    $commit684 = false;
                                    continue $label683;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e687) {
                                    $commit684 = false;
                                    $retry685 = false;
                                    $keepReads686 = $e687.keepReads;
                                    fabric.common.TransactionID $currentTid688 =
                                      $tm689.getCurrentTid();
                                    if ($e687.tid ==
                                          null ||
                                          !$e687.tid.isDescendantOf(
                                                       $currentTid688)) {
                                        throw $e687;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e687);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e687) {
                                    $commit684 = false;
                                    fabric.common.TransactionID $currentTid688 =
                                      $tm689.getCurrentTid();
                                    if ($e687.tid.isDescendantOf(
                                                    $currentTid688))
                                        continue $label683;
                                    if ($currentTid688.parent != null) {
                                        $retry685 = false;
                                        throw $e687;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e687) {
                                    $commit684 = false;
                                    $retry685 = false;
                                    if ($tm689.inNestedTxn()) {
                                        $keepReads686 = true;
                                    }
                                    throw $e687;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid688 =
                                      $tm689.getCurrentTid();
                                    if ($commit684) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e687) {
                                            $commit684 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e687) {
                                            $commit684 = false;
                                            $retry685 = false;
                                            $keepReads686 = $e687.keepReads;
                                            if ($e687.tid ==
                                                  null ||
                                                  !$e687.tid.isDescendantOf(
                                                               $currentTid688))
                                                throw $e687;
                                            throw new fabric.worker.
                                                    UserAbortException($e687);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e687) {
                                            $commit684 = false;
                                            $currentTid688 =
                                              $tm689.getCurrentTid();
                                            if ($currentTid688 != null) {
                                                if ($e687.tid.
                                                      equals($currentTid688) ||
                                                      !$e687.tid.
                                                      isDescendantOf(
                                                        $currentTid688)) {
                                                    throw $e687;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm689.inNestedTxn() &&
                                              $tm689.checkForStaleObjects()) {
                                            $retry685 = true;
                                            $keepReads686 = false;
                                        }
                                        if ($keepReads686) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e687) {
                                                $currentTid688 = $tm689.getCurrentTid();
                                                if ($currentTid688 != null &&
                                                      ($e687.tid.equals($currentTid688) || !$e687.tid.isDescendantOf($currentTid688))) {
                                                    throw $e687;
                                                } else {
                                                    $retry685 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit684) {
                                        {  }
                                        if ($retry685) { continue $label683; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -26, 61, -79, -105,
        -44, 103, -68, 119, 9, -17, -105, -54, -7, -119, -86, 86, 123, 103, -26,
        -6, 93, -32, -37, 5, -28, -124, -101, -28, 90, -15, 32, 108 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1ZDZAURxXunTvujwv3w/8FjuPYUOEnuwWxVHIBhIULC3tyxQGao8ild7b3brjZmWWm924vEStRI2gKUmWOn5SCZRVqhBMqQVQ0WJQVJYilJlJRElHKIhhFysK/xN/4Xk/vzt7sD2yVVF2/2e73ut/7+r2ve4axm2SCbZH2OI1qeoCPJJkd6KTRcKSbWjaLhXRq25uht0+dWBk+8PZXY60KUSKkXqWGaWgq1fsMm5NJkR10iAYNxoNbNoU7tpFaFQ3XUXuAE2Xb6rRF2pKmPtKvm1wukjf//kXB0YOPNL5YQRp6SYNm9HDKNTVkGpyleS+pT7BElFn2qliMxXpJk8FYrIdZGtW1x0DRNHpJs631G5SnLGZvYrapD6Fis51KMkusmelE901w20qp3LTA/UbH/RTX9GBEs3lHhFTFNabH7J3k46QyQibEddoPitMimSiCYsZgJ/aDep0GblpxqrKMSeWgZsQ4meO1yEbs3wAKYFqdYHzAzC5VaVDoIM2OSzo1+oM93NKMflCdYKZgFU5aik4KSjVJqg7SftbHyQyvXrczBFq1AhY04WSqV03MBHvW4tmznN26+eEH9z1urDMU4gOfY0zV0f8aMGr1GG1icWYxQ2WOYf3CyAE67ewehRBQnupRdnS+9bFbH1rceu4VR+fuAjobozuYyvvUo9FJr84KLVhWgW7UJE1bw1QYF7nY1W450pFOQrZPy86Ig4HM4LlNP3z4iWPshkLqwqRKNfVUArKqSTUTSU1n1kPMYBblLBYmtcyIhcR4mFTDc0QzmNO7MR63GQ+TSl10VZniN0AUhykQomp41oy4mXlOUj4gntNJQkgT/JEKQpoHifLwG/D7GaJ0tHOyIThgJlgwqqfYMKR3EP4YtdSBINStpan3qWZyJGhbatBKGVwDTaffCR481QEtiNAOgBvJ/+90afS+cdjnA2DnqGaMRakNuyQzZnW3DkWxztRjzOpT9X1nw2Ty2edE1tRiptuQrQIXH+z0LC9H5NqOplavvXWi76KTcWgrYePkXsc9Zzdz3PNvMRJmTItrNKozLGxwsR7rKgBMFQCmGvOlA6Ej4eMifapsUWfZWeth1geSOuVx00qkic8nQpwi7MVKsOuDwCYwb/2Cnu3rH93TDjuXTg5Xwh6iqt9bPi7phOGJQk30qQ273/77yQO7TLeQOPHn1Xe+JdZnuxcvy1RZDPjPnX5hGz3dd3aXX0FuqQXa4xQSEzik1bvGuDrtyHAeojEhQiYiBlTHoQxR1fEByxx2e0QeTMKm2UkJBMvjoKDL5T3Jw7/8ye/vFwdJhlkbcii4h/GOnGrGyRpE3Ta52G+2GAO9K4e6n91/c/c2ATxozCu0oB/bEFQxhfI1rade2Xn5N78+eklxN4uT6qSlDUFxp0UwTe/BPx/8/Rf/sCaxAyUwc0jyQVuWEJK49HzXuVIp+O+Ge5ac/uO+Rme/dehx0LPI4ttP4PbPXE2euPjIO61iGp+KR5MLoKvm8N1kd+ZVlkVH0I/0k6/Nfu48PQypD2xla48xQUBEAELEDi4VWNwn2iWesfdh0+6gNUv0K3Y+93fiIeomY29w7AstoRU3nPLPJiPOMbdA+W+lOXWy9Fjib0p71Q8UUt1LGsX5TQ2+lQKFQR70wglsh2RnhNw1bnz8aeocHR3ZYpvlLYScZb1l4NIOPKM2Ptc5me8kDgAxE0HqBO7eD9x9XcozODo5ie2UtI+IhweEyTzRzsdmgQCyAh8XcuQjvAFxUqslEimO+y9WWgQXFlvcfLbCfQg2eUt4TQHsuy0tAfUzJM9dtmf0s+8F9o06eedcTubl3Q9ybZwLiljyLrFuGlaZW2oVYdH5u5O7vvv8rt3O4d08/qhda6QSX3/9Pz8OHLp6oQCRV+qmQ8CNApT3ZzGtQ0ynAJaHifLgaSmPF8B0XWFMffi4MgOfKDuhMhWwzD08IrIeW4QT6cKTKWKDsFmRznqooIeN8sSeK+XkHA9zSkV404Vwzi52wxJQHv3E6JHYxi8vUWTRrYWkkNdgd7JJuCt51/cucal0y+fqjdnLQoNv9Tu7MsezrFf7a11jFx6ar35OIRXZOsm7yY436hhfHXUWg4u4sXlcjbRl0arM1MhRoiwfkfIDufvpZkEe/g4Yizzs5MuFFtu+EvRFsenlZIGz+X7cfH8p2va7/nwkG8VEnO9e8P40UVYel3LfHUYBh05VMhXVNTU9HpY6OdFeKXd7k8gNSZHMi79XyRpFsYaTCngjyaR4s0xx5L+Aw39iaKb3kiP83VECN9EJlFRBY7FCq1YOmVqsEEpLIZiXibLqV1KeLoISNvF8PNDkG1KeuC0e+HMwE/y0wpdDUeVi5V0l4n0SmzTsFMS7StcLhVwdNU2dUaNQ1PPA5VeJEmJSdpUXNZpEpOwsHnVO5hti1qdLRLQXm09DRGxnijoBfbKQ622w7hWirPmplN8pz3U0OSPlqTtyfVDMOlrC9QPYPAPJ188ESReEHE7fpj8QpXO2lA3l+Y0mk6SsKe53rluHS4x9EZtDnNQMUHsgBC9HbmoWwPtfRFn3tJRD5fmNJikpzTJS5SslnH8emy9BhsNRxNIb40V998ML60SirH9dyrNl+S5MXpLym2X4frKE7y9gc4yTiXCg8vBt/F8Ji0+HtxFbypXl+Y8mK6T84G39z/DSZMlLw6Y1yKxAD7yZsBKs/O0SwX4Pm1NghveaMGfiNSezzgzv/SajIBiwEBzrIZaFROme6ciNb5QHB5pclvLnxeHI4eozbv2fLxHmBWy+7wkT+84V4d7mZUTZNCIlLS8KNHlUyt4yCOxnJQJ4DZuLwL0WS5hDrCiHLYR1w0TZfErK0fJcR5Nnpdx7Rxsw6BbV5RL+v4nNJSBguwQB3w8rbyfKR7ukvKc859HEL2XrnTvvgP/bEs5fw+YKsJmdimau+V2FApgDq0dh9fNSniovADR5Ucqx4gHkunajxNhNbK4Dkfk1Q+MRGmXOoW3AraQx7/uWrPnpRT6IOSVvkbsLfKeTX43V0Mvs6FsbFk8t8o1uRt53fGl34khDzfQjW34hvjRlvwjXRkhNPKXruS/OOc9VSYvFNRForfManRTirxBwTgxwsUQh/P+zo/EOlJGjgb/eFRi3uJQGIATu5KtgoQthS8rC/7QY+8v0d6tqNl8V34nwhL6+/IWDl/pfGq7908Ef/eMzx7Y+3n/9n9uvvjnh2qc+f633Vpv+PxU+dS9MGQAA";
    }
    
    /**
   * The implementation of {@link #unmodifiableList(List)} for random-access
   * lists. This class name is required for compatibility with Sun's JDK
   * serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableRandomAccessList
      extends fabric.util.RandomAccess, UnmodifiableList {
        /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
        public UnmodifiableRandomAccessList
          fabric$util$Collections$UnmodifiableRandomAccessList$(
          fabric.util.List l);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableList._Proxy
          implements UnmodifiableRandomAccessList {
            public fabric.util.Collections.UnmodifiableRandomAccessList
              fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List arg1) {
                return ((fabric.util.Collections.UnmodifiableRandomAccessList)
                          fetch()).
                  fabric$util$Collections$UnmodifiableRandomAccessList$(arg1);
            }
            
            public _Proxy(UnmodifiableRandomAccessList._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.Collections.UnmodifiableList._Impl
          implements UnmodifiableRandomAccessList {
            /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
            public UnmodifiableRandomAccessList
              fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List l) {
                fabric$util$Collections$UnmodifiableList$(l);
                return (UnmodifiableRandomAccessList) this.$getProxy();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableRandomAccessList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableRandomAccessList.
                         _Proxy(this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.util.Collections.UnmodifiableRandomAccessList.
                           _Static
            {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.
                                UnmodifiableRandomAccessList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableRandomAccessList._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableRandomAccessList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableRandomAccessList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableRandomAccessList.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableRandomAccessList.
                        _Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.util.Collections.UnmodifiableRandomAccessList.
                           _Static
            {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.
                             UnmodifiableRandomAccessList._Static._Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm699 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled702 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff700 = 1;
                            boolean $doBackoff701 = true;
                            boolean $retry695 = true;
                            boolean $keepReads696 = false;
                            $label693: for (boolean $commit694 = false;
                                            !$commit694; ) {
                                if ($backoffEnabled702) {
                                    if ($doBackoff701) {
                                        if ($backoff700 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff700));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e697) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff700 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff700 =
                                              java.lang.Math.
                                                min(
                                                  $backoff700 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff701 = $backoff700 <= 32 ||
                                                      !$doBackoff701;
                                }
                                $commit694 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableRandomAccessList.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -2542308836966382001L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e697) {
                                    $commit694 = false;
                                    continue $label693;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e697) {
                                    $commit694 = false;
                                    $retry695 = false;
                                    $keepReads696 = $e697.keepReads;
                                    fabric.common.TransactionID $currentTid698 =
                                      $tm699.getCurrentTid();
                                    if ($e697.tid ==
                                          null ||
                                          !$e697.tid.isDescendantOf(
                                                       $currentTid698)) {
                                        throw $e697;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e697);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e697) {
                                    $commit694 = false;
                                    fabric.common.TransactionID $currentTid698 =
                                      $tm699.getCurrentTid();
                                    if ($e697.tid.isDescendantOf(
                                                    $currentTid698))
                                        continue $label693;
                                    if ($currentTid698.parent != null) {
                                        $retry695 = false;
                                        throw $e697;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e697) {
                                    $commit694 = false;
                                    $retry695 = false;
                                    if ($tm699.inNestedTxn()) {
                                        $keepReads696 = true;
                                    }
                                    throw $e697;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid698 =
                                      $tm699.getCurrentTid();
                                    if ($commit694) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e697) {
                                            $commit694 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e697) {
                                            $commit694 = false;
                                            $retry695 = false;
                                            $keepReads696 = $e697.keepReads;
                                            if ($e697.tid ==
                                                  null ||
                                                  !$e697.tid.isDescendantOf(
                                                               $currentTid698))
                                                throw $e697;
                                            throw new fabric.worker.
                                                    UserAbortException($e697);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e697) {
                                            $commit694 = false;
                                            $currentTid698 =
                                              $tm699.getCurrentTid();
                                            if ($currentTid698 != null) {
                                                if ($e697.tid.
                                                      equals($currentTid698) ||
                                                      !$e697.tid.
                                                      isDescendantOf(
                                                        $currentTid698)) {
                                                    throw $e697;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm699.inNestedTxn() &&
                                              $tm699.checkForStaleObjects()) {
                                            $retry695 = true;
                                            $keepReads696 = false;
                                        }
                                        if ($keepReads696) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e697) {
                                                $currentTid698 = $tm699.getCurrentTid();
                                                if ($currentTid698 != null &&
                                                      ($e697.tid.equals($currentTid698) || !$e697.tid.isDescendantOf($currentTid698))) {
                                                    throw $e697;
                                                } else {
                                                    $retry695 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit694) {
                                        {  }
                                        if ($retry695) { continue $label693; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -62, 2, 13, -21,
        -100, -74, 8, -97, 100, -83, -36, -121, 50, -96, -29, 38, -53, 54, 30,
        46, -114, -96, -97, -74, -82, -68, 92, -32, -52, -36, 95, -4 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link UnmodifiableList#listIterator(Store)}.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableListIterator
      extends fabric.util.ListIterator, UnmodifiableIterator {
        public fabric.util.ListIterator get$li();
        
        public fabric.util.ListIterator set$li(fabric.util.ListIterator val);
        
        /**
     * Only trusted code creates a wrapper.
     * @param li the wrapped iterator
     */
        public UnmodifiableListIterator
          fabric$util$Collections$UnmodifiableListIterator$(fabric.util.ListIterator li);
        
        /**
     * Blocks the addition of an object to the list underlying this iterator.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to add.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>add()</code> operation.
     */
        public void add(fabric.lang.Object o);
        
        /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>previous()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>previous()</code>.
     *
     * @return <code>true</code> if there is at least one more element prior to the
     *         current position in the underlying list.
     */
        public boolean hasPrevious();
        
        /**
     * Find the index of the element that would be returned by a call to next.
     * If <code>hasNext()</code> returns <code>false</code>, this returns the list size.
     *
     * @return the index of the element that would be returned by
     *         <code>next()</code>.
     */
        public int nextIndex();
        
        /**
     * Obtains the previous element in the underlying list.
     *
     * @return the previous element in the list.
     * @throws NoSuchElementException if there are no more prior elements.
     */
        public fabric.lang.Object previous();
        
        /**
     * Find the index of the element that would be returned by a call to
     * previous. If <code>hasPrevious()</code> returns <code>false</code>,
     * this returns -1.
     *
     * @return the index of the element that would be returned by
     *         <code>previous()</code>.
     */
        public int previousIndex();
        
        /**
     * Blocks the replacement of an element in the list underlying this
     * iterator.  This method never returns, throwing an exception instead.
     *
     * @param o The new object to replace the existing one.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>set()</code> operation.
     */
        public void set(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableIterator._Proxy
          implements UnmodifiableListIterator {
            public fabric.util.ListIterator get$li() {
                return ((fabric.util.Collections.UnmodifiableListIterator._Impl)
                          fetch()).get$li();
            }
            
            public fabric.util.ListIterator set$li(
              fabric.util.ListIterator val) {
                return ((fabric.util.Collections.UnmodifiableListIterator._Impl)
                          fetch()).set$li(val);
            }
            
            public fabric.util.Collections.UnmodifiableListIterator
              fabric$util$Collections$UnmodifiableListIterator$(
              fabric.util.ListIterator arg1) {
                return ((fabric.util.Collections.UnmodifiableListIterator)
                          fetch()).
                  fabric$util$Collections$UnmodifiableListIterator$(arg1);
            }
            
            public void add(fabric.lang.Object arg1) {
                ((fabric.util.Collections.UnmodifiableListIterator) fetch()).
                  add(arg1);
            }
            
            public boolean hasPrevious() {
                return ((fabric.util.Collections.UnmodifiableListIterator)
                          fetch()).hasPrevious();
            }
            
            public int nextIndex() {
                return ((fabric.util.Collections.UnmodifiableListIterator)
                          fetch()).nextIndex();
            }
            
            public fabric.lang.Object previous() {
                return ((fabric.util.Collections.UnmodifiableListIterator)
                          fetch()).previous();
            }
            
            public int previousIndex() {
                return ((fabric.util.Collections.UnmodifiableListIterator)
                          fetch()).previousIndex();
            }
            
            public void set(fabric.lang.Object arg1) {
                ((fabric.util.Collections.UnmodifiableListIterator) fetch()).
                  set(arg1);
            }
            
            public _Proxy(UnmodifiableListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.Collections.UnmodifiableIterator._Impl
          implements UnmodifiableListIterator {
            public fabric.util.ListIterator get$li() { return this.li; }
            
            public fabric.util.ListIterator set$li(
              fabric.util.ListIterator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.li = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.ListIterator li;
            
            /**
     * Only trusted code creates a wrapper.
     * @param li the wrapped iterator
     */
            public UnmodifiableListIterator
              fabric$util$Collections$UnmodifiableListIterator$(
              fabric.util.ListIterator li) {
                this.set$li(li);
                fabric$util$Collections$UnmodifiableIterator$(li);
                return (UnmodifiableListIterator) this.$getProxy();
            }
            
            /**
     * Blocks the addition of an object to the list underlying this iterator.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to add.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>add()</code> operation.
     */
            public void add(fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>previous()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>previous()</code>.
     *
     * @return <code>true</code> if there is at least one more element prior to the
     *         current position in the underlying list.
     */
            public boolean hasPrevious() { return this.get$li().hasPrevious(); }
            
            /**
     * Find the index of the element that would be returned by a call to next.
     * If <code>hasNext()</code> returns <code>false</code>, this returns the list size.
     *
     * @return the index of the element that would be returned by
     *         <code>next()</code>.
     */
            public int nextIndex() { return this.get$li().nextIndex(); }
            
            /**
     * Obtains the previous element in the underlying list.
     *
     * @return the previous element in the list.
     * @throws NoSuchElementException if there are no more prior elements.
     */
            public fabric.lang.Object previous() {
                return this.get$li().previous();
            }
            
            /**
     * Find the index of the element that would be returned by a call to
     * previous. If <code>hasPrevious()</code> returns <code>false</code>,
     * this returns -1.
     *
     * @return the index of the element that would be returned by
     *         <code>previous()</code>.
     */
            public int previousIndex() { return this.get$li().previousIndex(); }
            
            /**
     * Blocks the replacement of an element in the list underlying this
     * iterator.  This method never returns, throwing an exception instead.
     *
     * @param o The new object to replace the existing one.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>set()</code> operation.
     */
            public void set(fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableListIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableListIterator.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.li, refTypes, out, intraStoreRefs,
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
                this.li = (fabric.util.ListIterator)
                            $readRef(fabric.util.ListIterator._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableListIterator._Impl src =
                  (fabric.util.Collections.UnmodifiableListIterator._Impl)
                    other;
                this.li = src.li;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.util.Collections.UnmodifiableListIterator.
                           _Static
            {
                public _Proxy(fabric.util.Collections.UnmodifiableListIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableListIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableListIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableListIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableListIterator.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableListIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.util.Collections.UnmodifiableListIterator.
                           _Static
            {
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
                    return new fabric.util.Collections.UnmodifiableListIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 96, -82, -47, 80,
        12, -100, -126, 9, 7, -88, -66, 124, -84, -107, 114, -95, -64, 127, 61,
        -73, -33, 38, 91, 95, -117, 37, 40, 0, 100, -10, 114, 69 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #unmodifiableMap(Map)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableMap
      extends fabric.util.Map, java.io.Serializable, fabric.lang.Object {
        public fabric.util.Map get$m();
        
        public fabric.util.Map set$m(fabric.util.Map val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        public fabric.util.Set get$keys();
        
        public fabric.util.Set set$keys(fabric.util.Set val);
        
        public fabric.util.Collection get$values();
        
        public fabric.util.Collection set$values(fabric.util.Collection val);
        
        /**
     * Wrap a given map.
     * @param m the map to wrap
     * @throws NullPointerException if m is null
     */
        public UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(fabric.util.Map m);
        
        /**
     * Blocks the clearing of entries from the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>clear()</code> operation.
     */
        public void clear();
        
        /**
     * Returns <code>true</code> if the underlying map contains a mapping for
     * the given key.
     *
     * @param key the key to search for
     * @return <code>true</code> if the map contains the key
     * @throws ClassCastException if the key is of an inappropriate type
     * @throws NullPointerException if key is <code>null</code> but the map
     *         does not permit null keys
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * Returns <code>true</code> if the underlying map contains at least one mapping with
     * the given value.  In other words, it returns <code>true</code> if a value v exists where
     * <code>(value == null ? v == null : value.equals(v))</code>. This usually
     * requires linear time.
     *
     * @param value the value to search for
     * @return <code>true</code> if the map contains the value
     * @throws ClassCastException if the type of the value is not a valid type
     *         for this map.
     * @throws NullPointerException if the value is null and the map doesn't
     *         support null values.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Returns a unmodifiable set view of the entries in the underlying map.
     * Each element in the set is a unmodifiable variant of <code>Map.Entry</code>.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the objects.
     *
     * @return the unmodifiable set view of all mapping entries.
     * @see Map.Entry
     */
        public fabric.util.Set entrySet();
        
        /**
     * The implementation of {@link UnmodifiableMap#entrySet()}. This class
     * name is required for compatibility with Sun's JDK serializability.
     *
     * @author Eric Blake (ebb9@email.byu.edu)
     */
        public static interface UnmodifiableEntrySet
          extends java.io.Serializable, UnmodifiableSet {
            public static interface UnmodifiableMapEntry
              extends Entry, fabric.lang.Object {
                public fabric.util.Map.Entry get$e();
                
                public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);
                
                /**
         * Returns <code>true</code> if the object, o, is also a map entry
         * with an identical key and value.
         * 
         * @param o the object to compare.
         * @return <code>true</code> if o is an equivalent map entry.
         */
                public boolean equals(fabric.lang.Object o);
                
                /**
         * Returns the key of this map entry.
         * 
         * @return the key.
         */
                public fabric.lang.Object getKey();
                
                /**
         * Returns the value of this map entry.
         * 
         * @return the value.
         */
                public fabric.lang.Object getValue();
                
                /**
         * Computes the hash code of this map entry. The computation is
         * described in the <code>Map</code> interface documentation.
         * 
         * @return the hash code of this entry.
         * @see Map#hashCode()
         */
                public int hashCode();
                
                /**
         * Blocks the alteration of the value of this map entry. This method
         * never returns, throwing an exception instead.
         * 
         * @param value The new value.
         * @throws UnsupportedOperationException as an unmodifiable map entry
         *           does not support the <code>setValue()</code> operation.
         */
                public fabric.lang.Object setValue(fabric.lang.Object value);
                
                /**
         * Returns a textual representation of the map entry.
         * 
         * @return The map entry as a <code>String</code>.
         */
                public java.lang.String toString();
                
                public fabric.lang.Object $initLabels();
                
                public static class _Proxy extends fabric.lang.Object._Proxy
                  implements UnmodifiableMapEntry {
                    public fabric.util.Map.Entry get$e() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry.
                                  _Impl) fetch()).get$e();
                    }
                    
                    public fabric.util.Map.Entry set$e(
                      fabric.util.Map.Entry val) {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry.
                                  _Impl) fetch()).set$e(val);
                    }
                    
                    public fabric.lang.Object getKey() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry)
                                  fetch()).getKey();
                    }
                    
                    public fabric.lang.Object getValue() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry)
                                  fetch()).getValue();
                    }
                    
                    public fabric.lang.Object setValue(
                      fabric.lang.Object arg1) {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry)
                                  fetch()).setValue(arg1);
                    }
                    
                    public _Proxy(UnmodifiableMapEntry._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                public static final class _Impl extends fabric.lang.Object._Impl
                  implements UnmodifiableMapEntry {
                    public fabric.util.Map.Entry get$e() { return this.e; }
                    
                    public fabric.util.Map.Entry set$e(
                      fabric.util.Map.Entry val) {
                        fabric.worker.transaction.TransactionManager tm =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean transactionCreated = tm.registerWrite(this);
                        this.e = val;
                        if (transactionCreated) tm.commitTransaction();
                        return val;
                    }
                    
                    private Entry e;
                    
                    private UnmodifiableMapEntry
                      fabric$util$Collections$UnmodifiableMapEntry$(Entry e) {
                        this.set$e(e);
                        fabric$lang$Object$();
                        return (UnmodifiableMapEntry) this.$getProxy();
                    }
                    
                    /**
         * Returns <code>true</code> if the object, o, is also a map entry
         * with an identical key and value.
         * 
         * @param o the object to compare.
         * @return <code>true</code> if o is an equivalent map entry.
         */
                    public boolean equals(fabric.lang.Object o) {
                        return this.get$e().equals(o);
                    }
                    
                    /**
         * Returns the key of this map entry.
         * 
         * @return the key.
         */
                    public fabric.lang.Object getKey() {
                        return this.get$e().getKey();
                    }
                    
                    /**
         * Returns the value of this map entry.
         * 
         * @return the value.
         */
                    public fabric.lang.Object getValue() {
                        return this.get$e().getValue();
                    }
                    
                    /**
         * Computes the hash code of this map entry. The computation is
         * described in the <code>Map</code> interface documentation.
         * 
         * @return the hash code of this entry.
         * @see Map#hashCode()
         */
                    public int hashCode() { return this.get$e().hashCode(); }
                    
                    /**
         * Blocks the alteration of the value of this map entry. This method
         * never returns, throwing an exception instead.
         * 
         * @param value The new value.
         * @throws UnsupportedOperationException as an unmodifiable map entry
         *           does not support the <code>setValue()</code> operation.
         */
                    public fabric.lang.Object setValue(
                      fabric.lang.Object value) {
                        throw new java.lang.UnsupportedOperationException();
                    }
                    
                    /**
         * Returns a textual representation of the map entry.
         * 
         * @return The map entry as a <code>String</code>.
         */
                    public java.lang.String toString() {
                        return this.get$e().toString();
                    }
                    
                    public fabric.lang.Object $initLabels() {
                        this.
                          set$$updateLabel(
                            fabric.lang.security.LabelUtil._Impl.noComponents(
                                                                   ));
                        this.set$$accessPolicy(
                               fabric.lang.security.LabelUtil._Impl.bottomConf(
                                                                      ));
                        return (UnmodifiableMapEntry) this.$getProxy();
                    }
                    
                    public _Impl(fabric.worker.Store $location) {
                        super($location);
                    }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet.UnmodifiableMapEntry.
                                 _Proxy(this);
                    }
                    
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                        $writeRef($getStore(), this.e, refTypes, out,
                                  intraStoreRefs, interStoreRefs);
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                        this.e = (fabric.util.Map.Entry)
                                   $readRef(fabric.util.Map.Entry._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                    }
                    
                    public void $copyAppStateFrom(
                      fabric.lang.Object._Impl other) {
                        super.$copyAppStateFrom(other);
                        fabric.
                          util.
                          Collections.
                          UnmodifiableMap.
                          UnmodifiableEntrySet.
                          UnmodifiableMapEntry.
                          _Impl
                          src =
                          (fabric.util.Collections.UnmodifiableMap.
                            UnmodifiableEntrySet.UnmodifiableMapEntry._Impl)
                            other;
                        this.e = src.e;
                    }
                }
                
                interface _Static extends fabric.lang.Object, Cloneable {
                    final class _Proxy
                    extends fabric.
                      lang.
                      Object.
                      _Proxy
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.UnmodifiableMapEntry.
                                   _Static
                    {
                        public _Proxy(fabric.util.Collections.UnmodifiableMap.
                                        UnmodifiableEntrySet.
                                        UnmodifiableMapEntry._Static.
                                        _Impl impl) { super(impl); }
                        
                        public _Proxy(fabric.worker.Store store, long onum) {
                            super(store, onum);
                        }
                        
                        public static final fabric.util.Collections.
                          UnmodifiableMap.UnmodifiableEntrySet.
                          UnmodifiableMapEntry._Static $instance;
                        
                        static {
                            fabric.
                              util.
                              Collections.
                              UnmodifiableMap.
                              UnmodifiableEntrySet.
                              UnmodifiableMapEntry.
                              _Static.
                              _Impl
                              impl =
                              (fabric.
                                util.
                                Collections.
                                UnmodifiableMap.
                                UnmodifiableEntrySet.
                                UnmodifiableMapEntry.
                                _Static.
                                _Impl)
                                fabric.lang.Object._Static._Proxy.
                                $makeStaticInstance(
                                  fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet.UnmodifiableMapEntry.
                                    _Static._Impl.class);
                            $instance =
                              (fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet.UnmodifiableMapEntry.
                                _Static) impl.$getProxy();
                            impl.$init();
                        }
                    }
                    
                    class _Impl
                    extends fabric.
                      lang.
                      Object.
                      _Impl
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.UnmodifiableMapEntry.
                                   _Static
                    {
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
                                       ImmutableObjectSet associates,
                                     long expiry,
                                     fabric.worker.Store labelStore,
                                     long labelOnum,
                                     fabric.worker.Store accessPolicyStore,
                                     long accessPolicyOnum,
                                     java.io.ObjectInput in,
                                     java.util.Iterator refTypes,
                                     java.util.Iterator intraStoreRefs,
                                     java.util.Iterator interStoreRefs)
                              throws java.
                          io.
                          IOException,
                            java.
                          lang.
                          ClassNotFoundException {
                            super(store, onum, version, associates, expiry,
                                  labelStore, labelOnum, accessPolicyStore,
                                  accessPolicyOnum, in, refTypes,
                                  intraStoreRefs, interStoreRefs);
                        }
                        
                        public _Impl(fabric.worker.Store store) {
                            super(store);
                        }
                        
                        protected fabric.lang.Object._Proxy $makeProxy() {
                            return new fabric.util.Collections.UnmodifiableMap.
                                     UnmodifiableEntrySet.UnmodifiableMapEntry.
                                     _Static._Proxy(this);
                        }
                        
                        private void $init() {  }
                    }
                    
                }
                
                public static final byte[] $classHash = new byte[] { 110, -15,
                32, 50, -46, -12, -62, 16, -47, 19, -7, -96, -82, -21, -62, 2,
                -105, -19, -60, 61, -87, 73, -5, -11, -12, 126, 54, -25, 114,
                78, -84, 74 };
                public static final java.lang.String jlc$CompilerVersion$fabil =
                  "0.3.0";
                public static final long jlc$SourceLastModified$fabil =
                  1525719795000L;
                public static final java.lang.String jlc$ClassType$fabil =
                  "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";
            }
            
            /**
       * Wrap a given set.
       * @param s the set to wrap
       */
            public UnmodifiableEntrySet
              fabric$util$Collections$UnmodifiableEntrySet$(fabric.util.Set s);
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public fabric.lang.arrays.ObjectArray toArray();
            
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray array);
            
            public fabric.lang.Object $initLabels();
            
            public static interface Anonymous$4 extends UnmodifiableIterator {
                public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  get$out$();
                
                /**
           * Obtains the next element from the underlying set of
           * map entries.
           *
           * @return the next element in the collection.
           * @throws NoSuchElementException if there are no more elements.
           */
                public fabric.lang.Object next();
                
                public fabric.lang.Object $initLabels();
                
                public static class _Proxy
                extends fabric.util.Collections.UnmodifiableIterator._Proxy
                  implements Anonymous$4 {
                    public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                      get$out$() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.Anonymous$4._Impl)
                                  fetch()).get$out$();
                    }
                    
                    public _Proxy(Anonymous$4._Impl impl) { super(impl); }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                public static class _Impl
                extends fabric.util.
                  Collections.UnmodifiableIterator._Impl implements Anonymous$4 {
                    public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                      get$out$() {
                        return this.out$;
                    }
                    
                    private UnmodifiableEntrySet out$;
                    
                    /**
           * Obtains the next element from the underlying set of
           * map entries.
           *
           * @return the next element in the collection.
           * @throws NoSuchElementException if there are no more elements.
           */
                    public fabric.lang.Object next() {
                        final Entry e =
                          (Entry)
                            fabric.lang.Object._Proxy.$getProxy(super.next());
                        return (UnmodifiableMapEntry)
                                 fabric.lang.Object._Proxy.
                                 $getProxy(
                                   ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry.
                                      _Impl)
                                      ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry)
                                         new fabric.util.Collections.
                                           UnmodifiableMap.UnmodifiableEntrySet.
                                           UnmodifiableMapEntry._Impl(
                                           this.$getStore()).
                                         $getProxy()).fetch()).
                                       fabric$util$Collections$UnmodifiableMapEntry$(
                                         e));
                    }
                    
                    public fabric.lang.Object $initLabels() {
                        this.
                          set$$updateLabel(
                            fabric.lang.security.LabelUtil._Impl.noComponents(
                                                                   ));
                        this.set$$accessPolicy(
                               fabric.lang.security.LabelUtil._Impl.bottomConf(
                                                                      ));
                        return (Anonymous$4) this.$getProxy();
                    }
                    
                    private _Impl(fabric.worker.Store $location,
                                  final UnmodifiableEntrySet out$) {
                        super($location);
                        this.out$ = out$;
                    }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet.Anonymous$4._Proxy(this);
                    }
                    
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                        $writeRef($getStore(), this.out$, refTypes, out,
                                  intraStoreRefs, interStoreRefs);
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                        this.out$ =
                          (fabric.
                            util.
                            Collections.
                            UnmodifiableMap.
                            UnmodifiableEntrySet)
                            $readRef(
                              fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet._Proxy.class,
                              (fabric.common.RefTypeEnum) refTypes.next(), in,
                              store, intraStoreRefs, interStoreRefs);
                    }
                    
                    public void $copyAppStateFrom(
                      fabric.lang.Object._Impl other) {
                        super.$copyAppStateFrom(other);
                        fabric.
                          util.
                          Collections.
                          UnmodifiableMap.
                          UnmodifiableEntrySet.
                          Anonymous$4.
                          _Impl
                          src =
                          (fabric.util.Collections.UnmodifiableMap.
                            UnmodifiableEntrySet.Anonymous$4._Impl) other;
                        this.out$ = src.out$;
                    }
                }
                
                interface _Static extends fabric.lang.Object, Cloneable {
                    final class _Proxy
                    extends fabric.
                      lang.
                      Object.
                      _Proxy
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.Anonymous$4._Static
                    {
                        public _Proxy(fabric.util.Collections.UnmodifiableMap.
                                        UnmodifiableEntrySet.Anonymous$4.
                                        _Static._Impl impl) { super(impl); }
                        
                        public _Proxy(fabric.worker.Store store, long onum) {
                            super(store, onum);
                        }
                        
                        public static final fabric.util.Collections.
                          UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4.
                          _Static $instance;
                        
                        static {
                            fabric.
                              util.
                              Collections.
                              UnmodifiableMap.
                              UnmodifiableEntrySet.
                              Anonymous$4.
                              _Static.
                              _Impl
                              impl =
                              (fabric.
                                util.
                                Collections.
                                UnmodifiableMap.
                                UnmodifiableEntrySet.
                                Anonymous$4.
                                _Static.
                                _Impl)
                                fabric.lang.Object._Static._Proxy.
                                $makeStaticInstance(
                                  fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet.Anonymous$4._Static.
                                    _Impl.class);
                            $instance =
                              (fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet.Anonymous$4._Static)
                                impl.$getProxy();
                            impl.$init();
                        }
                    }
                    
                    class _Impl
                    extends fabric.
                      lang.
                      Object.
                      _Impl
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.Anonymous$4._Static
                    {
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
                                       ImmutableObjectSet associates,
                                     long expiry,
                                     fabric.worker.Store labelStore,
                                     long labelOnum,
                                     fabric.worker.Store accessPolicyStore,
                                     long accessPolicyOnum,
                                     java.io.ObjectInput in,
                                     java.util.Iterator refTypes,
                                     java.util.Iterator intraStoreRefs,
                                     java.util.Iterator interStoreRefs)
                              throws java.
                          io.
                          IOException,
                            java.
                          lang.
                          ClassNotFoundException {
                            super(store, onum, version, associates, expiry,
                                  labelStore, labelOnum, accessPolicyStore,
                                  accessPolicyOnum, in, refTypes,
                                  intraStoreRefs, interStoreRefs);
                        }
                        
                        public _Impl(fabric.worker.Store store) {
                            super(store);
                        }
                        
                        protected fabric.lang.Object._Proxy $makeProxy() {
                            return new fabric.util.Collections.UnmodifiableMap.
                                     UnmodifiableEntrySet.Anonymous$4._Static.
                                     _Proxy(this);
                        }
                        
                        private void $init() {  }
                    }
                    
                }
                
                public static final byte[] $classHash = new byte[] { 98, -11,
                -110, 34, 114, -121, -15, 84, 107, 96, 76, 118, -61, -57, 15,
                -70, 114, 65, -37, -82, -53, -104, -116, -90, 53, -127, -28, -7,
                -103, 108, 104, -6 };
                public static final java.lang.String jlc$CompilerVersion$fabil =
                  "0.3.0";
                public static final long jlc$SourceLastModified$fabil =
                  1525719795000L;
                public static final java.lang.String jlc$ClassType$fabil =
                  "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";
            }
            
            public static class _Proxy
            extends fabric.util.Collections.UnmodifiableSet._Proxy
              implements UnmodifiableEntrySet {
                public fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(
                  fabric.util.Set arg1) {
                    return ((fabric.util.Collections.UnmodifiableMap.
                              UnmodifiableEntrySet) fetch()).
                      fabric$util$Collections$UnmodifiableEntrySet$(arg1);
                }
                
                public _Proxy(UnmodifiableEntrySet._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static final class _Impl
            extends fabric.util.
              Collections.UnmodifiableSet._Impl implements UnmodifiableEntrySet {
                /**
       * Wrap a given set.
       * @param s the set to wrap
       */
                public UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(
                  fabric.util.Set s) {
                    fabric$util$Collections$UnmodifiableSet$(s);
                    return (UnmodifiableEntrySet) this.$getProxy();
                }
                
                public fabric.util.Iterator iterator(
                  fabric.worker.Store store) {
                    return (UnmodifiableIterator)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.Anonymous$4)
                                  new fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet.Anonymous$4._Impl(
                                    store,
                                    (fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet)
                                      this.$getProxy()).
                                  $getProxy()).
                                   fabric$util$Collections$UnmodifiableIterator$(
                                     this.get$c().iterator(store)));
                }
                
                public fabric.lang.arrays.ObjectArray toArray() {
                    fabric.lang.arrays.ObjectArray mapEntryResult =
                      super.toArray();
                    fabric.lang.arrays.ObjectArray result = null;
                    if (!fabric.lang.Object._Proxy.idEquals(mapEntryResult,
                                                            null)) {
                        result =
                          (fabric.lang.arrays.ObjectArray)
                            new fabric.lang.arrays.ObjectArray._Impl(
                              this.$getStore()).
                            fabric$lang$arrays$ObjectArray$(
                              this.get$$updateLabel(),
                              this.get$$updateLabel().confPolicy(),
                              fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet.UnmodifiableMapEntry.
                                _Proxy.class, mapEntryResult.get$length()).
                            $getProxy();
                        for (int i = 0; i < mapEntryResult.get$length(); i++) {
                            Entry
                              r =
                              (Entry)
                                fabric.lang.Object._Proxy.
                                $getProxy((fabric.lang.Object)
                                            mapEntryResult.get(i));
                            result.
                              set(
                                i,
                                (UnmodifiableMapEntry)
                                  fabric.lang.Object._Proxy.
                                  $getProxy(
                                    ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry.
                                       _Impl)
                                       ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry)
                                          new fabric.util.Collections.
                                            UnmodifiableMap.
                                            UnmodifiableEntrySet.
                                            UnmodifiableMapEntry._Impl(
                                            this.$getStore()).
                                          $getProxy()).fetch()).
                                        fabric$util$Collections$UnmodifiableMapEntry$(
                                          r)));
                        }
                    }
                    return result;
                }
                
                public fabric.lang.arrays.ObjectArray toArray(
                  fabric.lang.arrays.ObjectArray array) {
                    super.toArray(array);
                    if (!fabric.lang.Object._Proxy.idEquals(array, null)) {
                        for (int i = 0; i < array.get$length(); i++) {
                            array.
                              set(
                                i,
                                (UnmodifiableMapEntry)
                                  fabric.lang.Object._Proxy.
                                  $getProxy(
                                    ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry.
                                       _Impl)
                                       ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableMapEntry)
                                          new fabric.util.Collections.
                                            UnmodifiableMap.
                                            UnmodifiableEntrySet.
                                            UnmodifiableMapEntry._Impl(
                                            this.$getStore()).
                                          $getProxy()).fetch()).
                                        fabric$util$Collections$UnmodifiableMapEntry$(
                                          (fabric.util.Map.Entry)
                                            fabric.lang.Object._Proxy.
                                            $getProxy((fabric.lang.Object)
                                                        array.get(i)))));
                        }
                    }
                    return array;
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (UnmodifiableEntrySet) this.$getProxy();
                }
                
                public _Impl(fabric.worker.Store $location) {
                    super($location);
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableMap.
                             UnmodifiableEntrySet._Proxy(this);
                }
                
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
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                public long get$serialVersionUID();
                
                public long set$serialVersionUID(long val);
                
                public long postInc$serialVersionUID();
                
                public long postDec$serialVersionUID();
                
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.Collections.UnmodifiableMap.
                               UnmodifiableEntrySet._Static
                {
                    public long get$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          get$serialVersionUID();
                    }
                    
                    public long set$serialVersionUID(long val) {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          set$serialVersionUID(val);
                    }
                    
                    public long postInc$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          postInc$serialVersionUID();
                    }
                    
                    public long postDec$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          postDec$serialVersionUID();
                    }
                    
                    public _Proxy(fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.Collections.UnmodifiableMap.
                      UnmodifiableEntrySet._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          Collections.
                          UnmodifiableMap.
                          UnmodifiableEntrySet.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            Collections.
                            UnmodifiableMap.
                            UnmodifiableEntrySet.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet._Static._Impl.class);
                        $instance =
                          (fabric.util.Collections.UnmodifiableMap.
                            UnmodifiableEntrySet._Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.Collections.UnmodifiableMap.
                               UnmodifiableEntrySet._Static
                {
                    public long get$serialVersionUID() {
                        return this.serialVersionUID;
                    }
                    
                    public long set$serialVersionUID(long val) {
                        fabric.worker.transaction.TransactionManager tm =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
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
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                        out.writeLong(this.serialVersionUID);
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                        this.serialVersionUID = in.readLong();
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet._Static._Proxy(this);
                    }
                    
                    private void $init() {
                        {
                            {
                                fabric.worker.transaction.TransactionManager
                                  $tm709 =
                                  fabric.worker.transaction.TransactionManager.
                                  getInstance();
                                boolean
                                  $backoffEnabled712 =
                                  fabric.worker.Worker.
                                    getWorker().config.
                                    txRetryBackoff;
                                long $backoff710 = 1;
                                boolean $doBackoff711 = true;
                                boolean $retry705 = true;
                                boolean $keepReads706 = false;
                                $label703: for (boolean $commit704 = false;
                                                !$commit704; ) {
                                    if ($backoffEnabled712) {
                                        if ($doBackoff711) {
                                            if ($backoff710 > 32) {
                                                while (true) {
                                                    try {
                                                        java.lang.Thread.
                                                          sleep(
                                                            java.lang.Math.
                                                                round(
                                                                  java.lang.Math.
                                                                      random() *
                                                                      $backoff710));
                                                        break;
                                                    }
                                                    catch (java.lang.
                                                             InterruptedException $e707) {
                                                        
                                                    }
                                                }
                                            }
                                            if ($backoff710 <
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff)
                                                $backoff710 =
                                                  java.lang.Math.
                                                    min(
                                                      $backoff710 * 2,
                                                      fabric.worker.Worker.
                                                        getWorker().config.
                                                        maxBackoff);
                                        }
                                        $doBackoff711 = $backoff710 <= 32 ||
                                                          !$doBackoff711;
                                    }
                                    $commit704 = true;
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().startTransaction();
                                    try {
                                        fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 7854390611657943733L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e707) {
                                        $commit704 = false;
                                        continue $label703;
                                    }
                                    catch (fabric.worker.
                                             TransactionAbortingException $e707) {
                                        $commit704 = false;
                                        $retry705 = false;
                                        $keepReads706 = $e707.keepReads;
                                        fabric.common.TransactionID
                                          $currentTid708 =
                                          $tm709.getCurrentTid();
                                        if ($e707.tid ==
                                              null ||
                                              !$e707.tid.isDescendantOf(
                                                           $currentTid708)) {
                                            throw $e707;
                                        }
                                        throw new fabric.worker.
                                                UserAbortException($e707);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e707) {
                                        $commit704 = false;
                                        fabric.common.TransactionID
                                          $currentTid708 =
                                          $tm709.getCurrentTid();
                                        if ($e707.tid.isDescendantOf(
                                                        $currentTid708))
                                            continue $label703;
                                        if ($currentTid708.parent != null) {
                                            $retry705 = false;
                                            throw $e707;
                                        }
                                        throw new InternalError(
                                                "Something is broken with " +
                                                    "transaction management. Got a signal to restart a " +
                                                    "different transaction than the one being managed.");
                                    }
                                    catch (final Throwable $e707) {
                                        $commit704 = false;
                                        $retry705 = false;
                                        if ($tm709.inNestedTxn()) {
                                            $keepReads706 = true;
                                        }
                                        throw $e707;
                                    }
                                    finally {
                                        fabric.common.TransactionID
                                          $currentTid708 =
                                          $tm709.getCurrentTid();
                                        if ($commit704) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.
                                                  getInstance().
                                                  commitTransaction();
                                            }
                                            catch (final fabric.worker.
                                                     AbortException $e707) {
                                                $commit704 = false;
                                            }
                                            catch (final fabric.worker.
                                                     TransactionAbortingException $e707) {
                                                $commit704 = false;
                                                $retry705 = false;
                                                $keepReads706 = $e707.keepReads;
                                                if ($e707.tid ==
                                                      null ||
                                                      !$e707.tid.
                                                      isDescendantOf(
                                                        $currentTid708))
                                                    throw $e707;
                                                throw new fabric.worker.
                                                        UserAbortException(
                                                        $e707);
                                            }
                                            catch (final fabric.worker.
                                                     TransactionRestartingException $e707) {
                                                $commit704 = false;
                                                $currentTid708 =
                                                  $tm709.getCurrentTid();
                                                if ($currentTid708 != null) {
                                                    if ($e707.tid.
                                                          equals(
                                                            $currentTid708) ||
                                                          !$e707.tid.
                                                          isDescendantOf(
                                                            $currentTid708)) {
                                                        throw $e707;
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            if (!$tm709.inNestedTxn() &&
                                                  $tm709.checkForStaleObjects(
                                                           )) {
                                                $retry705 = true;
                                                $keepReads706 = false;
                                            }
                                            if ($keepReads706) {
                                                try {
                                                    fabric.worker.transaction.TransactionManager.
                                                      getInstance().
                                                      abortTransactionUpdates();
                                                }
                                                catch (final fabric.worker.
                                                         TransactionRestartingException $e707) {
                                                    $currentTid708 =
                                                      $tm709.getCurrentTid();
                                                    if ($currentTid708 !=
                                                          null &&
                                                          ($e707.tid.
                                                             equals(
                                                               $currentTid708) ||
                                                             !$e707.tid.
                                                             isDescendantOf(
                                                               $currentTid708))) {
                                                        throw $e707;
                                                    } else {
                                                        $retry705 = true;
                                                    }
                                                }
                                            } else {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                            }
                                        }
                                        if (!$commit704) {
                                            {  }
                                            if ($retry705) {
                                                continue $label703;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 64, 76, 118,
            -82, -54, 2, 21, -90, -60, 96, 12, 79, -52, 86, -65, 8, -17, -20,
            123, -94, 102, -34, -27, 108, 14, 125, 120, -117, 48, -99, -14,
            18 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        /**
     * Returns <code>true</code> if the object, o, is also an instance
     * of <code>Map</code> with an equal set of map entries.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is an equivalent map.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Returns the value associated with the supplied key or
     * null if no such mapping exists.  An ambiguity can occur
     * if null values are accepted by the underlying map.
     * In this case, <code>containsKey()</code> can be used
     * to separate the two possible cases of a null result.
     *
     * @param key The key to look up.
     * @return the value associated with the key, or null if key not in map.
     * @throws ClassCastException if the key is an inappropriate type.
     * @throws NullPointerException if this map does not accept null keys.
     * @see #containsKey(Object)
     */
        public fabric.lang.Object get(fabric.lang.Object key);
        
        /**
     * Blocks the addition of a new entry to the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @param key The new key.
     * @param value The new value.
     * @return the previous value of the key, or null if there was no mapping.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>put()</code> operation.
     */
        public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
        
        /**
     * Computes the hash code for the underlying map, as the sum
     * of the hash codes of all entries.
     *
     * @return The hash code of the underlying map.
     * @see Map.Entry#hashCode()
     */
        public int hashCode();
        
        /**
     * Returns <code>true</code> if the underlying map contains no entries.
     *
     * @return <code>true</code> if the map is empty.
     */
        public boolean isEmpty();
        
        /**
     * Returns a unmodifiable set view of the keys in the underlying map.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the set view of all keys.
     */
        public fabric.util.Set keySet();
        
        /**
     * Blocks the addition of the entries in the supplied map.
     * This method never returns, throwing an exception instead.
     *
     * @param m The map, the entries of which should be added
     *          to the underlying map.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>putAll</code> operation.
     */
        public void putAll(fabric.util.Map m);
        
        /**
     * Blocks the removal of an entry from the map.
     * This method never returns, throwing an exception instead.
     *
     * @param o The key of the entry to remove.
     * @return The value the key was associated with, or null
     *         if no such mapping existed.  Null is also returned
     *         if the removed entry had a null key.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>remove</code> operation.
     */
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        /**
     * Returns the number of key-value mappings in the underlying map.
     * If there are more than Integer.MAX_VALUE mappings, Integer.MAX_VALUE
     * is returned.
     *
     * @return the number of mappings.
     */
        public int size();
        
        /**
     * Returns a textual representation of the map.
     *
     * @return The map in the form of a <code>String</code>.
     */
        public java.lang.String toString();
        
        /**
     * Returns a unmodifiable collection view of the values in the underlying map.
     * The collection is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the collection view of all values.
     */
        public fabric.util.Collection values();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableMap {
            public fabric.util.Map get$m() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$m();
            }
            
            public fabric.util.Map set$m(fabric.util.Map val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$m(val);
            }
            
            public fabric.util.Set get$entries() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$entries();
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$entries(val);
            }
            
            public fabric.util.Set get$keys() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$keys();
            }
            
            public fabric.util.Set set$keys(fabric.util.Set val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$keys(val);
            }
            
            public fabric.util.Collection get$values() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$values();
            }
            
            public fabric.util.Collection set$values(
              fabric.util.Collection val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$values(val);
            }
            
            public fabric.util.Collections.UnmodifiableMap
              fabric$util$Collections$UnmodifiableMap$(fabric.util.Map arg1) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  fabric$util$Collections$UnmodifiableMap$(arg1);
            }
            
            public void clear() {
                ((fabric.util.Collections.UnmodifiableMap) fetch()).clear();
            }
            
            public boolean containsKey(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  containsKey(arg1);
            }
            
            public boolean containsValue(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  containsValue(arg1);
            }
            
            public fabric.util.Set entrySet() {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  entrySet();
            }
            
            public fabric.lang.Object get(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  get(arg1);
            }
            
            public fabric.lang.Object put(fabric.lang.Object arg1,
                                          fabric.lang.Object arg2) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  put(arg1, arg2);
            }
            
            public boolean isEmpty() {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  isEmpty();
            }
            
            public fabric.util.Set keySet() {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  keySet();
            }
            
            public void putAll(fabric.util.Map arg1) {
                ((fabric.util.Collections.UnmodifiableMap) fetch()).putAll(
                                                                      arg1);
            }
            
            public fabric.lang.Object remove(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  remove(arg1);
            }
            
            public int size() {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).size(
                                                                             );
            }
            
            public fabric.util.Collection values() {
                return ((fabric.util.Collections.UnmodifiableMap) fetch()).
                  values();
            }
            
            public _Proxy(UnmodifiableMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableMap {
            public fabric.util.Map get$m() { return this.m; }
            
            public fabric.util.Map set$m(fabric.util.Map val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.m = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Map m;
            
            public fabric.util.Set get$entries() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.entries;
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.entries = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the entry set.
     */
            private transient fabric.util.Set entries;
            
            public fabric.util.Set get$keys() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.keys;
            }
            
            public fabric.util.Set set$keys(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.keys = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the key set.
     */
            private transient fabric.util.Set keys;
            
            public fabric.util.Collection get$values() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.values;
            }
            
            public fabric.util.Collection set$values(
              fabric.util.Collection val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.values = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the value collection.
     */
            private transient fabric.util.Collection values;
            
            /**
     * Wrap a given map.
     * @param m the map to wrap
     * @throws NullPointerException if m is null
     */
            public UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(
              fabric.util.Map m) {
                this.set$m(m);
                if (fabric.lang.Object._Proxy.idEquals(m, null))
                    throw new java.lang.NullPointerException();
                fabric$lang$Object$();
                return (UnmodifiableMap) this.$getProxy();
            }
            
            /**
     * Blocks the clearing of entries from the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>clear()</code> operation.
     */
            public void clear() {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Returns <code>true</code> if the underlying map contains a mapping for
     * the given key.
     *
     * @param key the key to search for
     * @return <code>true</code> if the map contains the key
     * @throws ClassCastException if the key is of an inappropriate type
     * @throws NullPointerException if key is <code>null</code> but the map
     *         does not permit null keys
     */
            public boolean containsKey(fabric.lang.Object key) {
                return this.get$m().containsKey(key);
            }
            
            /**
     * Returns <code>true</code> if the underlying map contains at least one mapping with
     * the given value.  In other words, it returns <code>true</code> if a value v exists where
     * <code>(value == null ? v == null : value.equals(v))</code>. This usually
     * requires linear time.
     *
     * @param value the value to search for
     * @return <code>true</code> if the map contains the value
     * @throws ClassCastException if the type of the value is not a valid type
     *         for this map.
     * @throws NullPointerException if the value is null and the map doesn't
     *         support null values.
     */
            public boolean containsValue(fabric.lang.Object value) {
                return this.get$m().containsValue(value);
            }
            
            /**
     * Returns a unmodifiable set view of the entries in the underlying map.
     * Each element in the set is a unmodifiable variant of <code>Map.Entry</code>.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the objects.
     *
     * @return the unmodifiable set view of all mapping entries.
     * @see Map.Entry
     */
            public fabric.util.Set entrySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$entries(),
                                                       null))
                    this.
                      set$entries(
                        (UnmodifiableEntrySet)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet)
                               new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet._Impl(this.$getStore()).
                               $getProxy()).
                                fabric$util$Collections$UnmodifiableEntrySet$(
                                  this.get$m().entrySet())));
                return this.get$entries();
            }
            
            /**
     * Returns <code>true</code> if the object, o, is also an instance
     * of <code>Map</code> with an equal set of map entries.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is an equivalent map.
     */
            public boolean equals(fabric.lang.Object o) {
                return this.get$m().equals(o);
            }
            
            /**
     * Returns the value associated with the supplied key or
     * null if no such mapping exists.  An ambiguity can occur
     * if null values are accepted by the underlying map.
     * In this case, <code>containsKey()</code> can be used
     * to separate the two possible cases of a null result.
     *
     * @param key The key to look up.
     * @return the value associated with the key, or null if key not in map.
     * @throws ClassCastException if the key is an inappropriate type.
     * @throws NullPointerException if this map does not accept null keys.
     * @see #containsKey(Object)
     */
            public fabric.lang.Object get(fabric.lang.Object key) {
                return this.get$m().get(key);
            }
            
            /**
     * Blocks the addition of a new entry to the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @param key The new key.
     * @param value The new value.
     * @return the previous value of the key, or null if there was no mapping.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>put()</code> operation.
     */
            public fabric.lang.Object put(fabric.lang.Object key,
                                          fabric.lang.Object value) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Computes the hash code for the underlying map, as the sum
     * of the hash codes of all entries.
     *
     * @return The hash code of the underlying map.
     * @see Map.Entry#hashCode()
     */
            public int hashCode() { return this.get$m().hashCode(); }
            
            /**
     * Returns <code>true</code> if the underlying map contains no entries.
     *
     * @return <code>true</code> if the map is empty.
     */
            public boolean isEmpty() { return this.get$m().isEmpty(); }
            
            /**
     * Returns a unmodifiable set view of the keys in the underlying map.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the set view of all keys.
     */
            public fabric.util.Set keySet() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$keys(), null))
                    this.
                      set$keys(
                        (UnmodifiableSet)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.Collections.UnmodifiableSet)
                               new fabric.util.Collections.UnmodifiableSet.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$util$Collections$UnmodifiableSet$(
                                  this.get$m().keySet())));
                return this.get$keys();
            }
            
            /**
     * Blocks the addition of the entries in the supplied map.
     * This method never returns, throwing an exception instead.
     *
     * @param m The map, the entries of which should be added
     *          to the underlying map.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>putAll</code> operation.
     */
            public void putAll(fabric.util.Map m) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Blocks the removal of an entry from the map.
     * This method never returns, throwing an exception instead.
     *
     * @param o The key of the entry to remove.
     * @return The value the key was associated with, or null
     *         if no such mapping existed.  Null is also returned
     *         if the removed entry had a null key.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>remove</code> operation.
     */
            public fabric.lang.Object remove(fabric.lang.Object o) {
                throw new java.lang.UnsupportedOperationException();
            }
            
            /**
     * Returns the number of key-value mappings in the underlying map.
     * If there are more than Integer.MAX_VALUE mappings, Integer.MAX_VALUE
     * is returned.
     *
     * @return the number of mappings.
     */
            public int size() { return this.get$m().size(); }
            
            /**
     * Returns a textual representation of the map.
     *
     * @return The map in the form of a <code>String</code>.
     */
            public java.lang.String toString() { return this.get$m().toString(); }
            
            /**
     * Returns a unmodifiable collection view of the values in the underlying map.
     * The collection is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the collection view of all values.
     */
            public fabric.util.Collection values() {
                if (fabric.lang.Object._Proxy.idEquals(this.get$values(), null))
                    this.
                      set$values(
                        (UnmodifiableCollection)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.util.Collections.UnmodifiableCollection)
                               new fabric.util.Collections.
                                 UnmodifiableCollection._Impl(this.$getStore()).
                               $getProxy()).
                                fabric$util$Collections$UnmodifiableCollection$(
                                  this.get$m().values())));
                return this.get$values();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableMap) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableMap._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.m, refTypes, out, intraStoreRefs,
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
                this.m = (fabric.util.Map)
                           $readRef(fabric.util.Map._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableMap._Impl src =
                  (fabric.util.Collections.UnmodifiableMap._Impl) other;
                this.m = src.m;
                this.entries = src.entries;
                this.keys = src.keys;
                this.values = src.values;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableMap.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableMap._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableMap._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableMap._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm719 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled722 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff720 = 1;
                            boolean $doBackoff721 = true;
                            boolean $retry715 = true;
                            boolean $keepReads716 = false;
                            $label713: for (boolean $commit714 = false;
                                            !$commit714; ) {
                                if ($backoffEnabled722) {
                                    if ($doBackoff721) {
                                        if ($backoff720 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff720));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e717) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff720 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff720 =
                                              java.lang.Math.
                                                min(
                                                  $backoff720 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff721 = $backoff720 <= 32 ||
                                                      !$doBackoff721;
                                }
                                $commit714 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableMap.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -1034234728574286014L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e717) {
                                    $commit714 = false;
                                    continue $label713;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e717) {
                                    $commit714 = false;
                                    $retry715 = false;
                                    $keepReads716 = $e717.keepReads;
                                    fabric.common.TransactionID $currentTid718 =
                                      $tm719.getCurrentTid();
                                    if ($e717.tid ==
                                          null ||
                                          !$e717.tid.isDescendantOf(
                                                       $currentTid718)) {
                                        throw $e717;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e717);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e717) {
                                    $commit714 = false;
                                    fabric.common.TransactionID $currentTid718 =
                                      $tm719.getCurrentTid();
                                    if ($e717.tid.isDescendantOf(
                                                    $currentTid718))
                                        continue $label713;
                                    if ($currentTid718.parent != null) {
                                        $retry715 = false;
                                        throw $e717;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e717) {
                                    $commit714 = false;
                                    $retry715 = false;
                                    if ($tm719.inNestedTxn()) {
                                        $keepReads716 = true;
                                    }
                                    throw $e717;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid718 =
                                      $tm719.getCurrentTid();
                                    if ($commit714) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e717) {
                                            $commit714 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e717) {
                                            $commit714 = false;
                                            $retry715 = false;
                                            $keepReads716 = $e717.keepReads;
                                            if ($e717.tid ==
                                                  null ||
                                                  !$e717.tid.isDescendantOf(
                                                               $currentTid718))
                                                throw $e717;
                                            throw new fabric.worker.
                                                    UserAbortException($e717);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e717) {
                                            $commit714 = false;
                                            $currentTid718 =
                                              $tm719.getCurrentTid();
                                            if ($currentTid718 != null) {
                                                if ($e717.tid.
                                                      equals($currentTid718) ||
                                                      !$e717.tid.
                                                      isDescendantOf(
                                                        $currentTid718)) {
                                                    throw $e717;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm719.inNestedTxn() &&
                                              $tm719.checkForStaleObjects()) {
                                            $retry715 = true;
                                            $keepReads716 = false;
                                        }
                                        if ($keepReads716) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e717) {
                                                $currentTid718 = $tm719.getCurrentTid();
                                                if ($currentTid718 != null &&
                                                      ($e717.tid.equals($currentTid718) || !$e717.tid.isDescendantOf($currentTid718))) {
                                                    throw $e717;
                                                } else {
                                                    $retry715 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit714) {
                                        {  }
                                        if ($retry715) { continue $label713; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -40, -1, 84, 26,
        105, -44, 50, 85, 6, -69, 119, 26, 84, 68, -90, 64, -65, 50, 66, -70,
        -36, 108, -25, 93, -32, -91, -120, -118, 52, 111, 100, 55 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #unmodifiableSet(Set)}. This class
   * name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSet
      extends fabric.util.Set, UnmodifiableCollection {
        /**
     * Wrap a given set.
     * @param s the set to wrap
     * @throws NullPointerException if s is null
     */
        public UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(fabric.util.Set s);
        
        /**
     * Returns <code>true</code> if the object, o, is also an instance of
     * <code>Set</code> of the same size and with the same entries.
     *
     * @return <code>true</code> if o is an equivalent set.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Computes the hash code of this set, as the sum of the
     * hash codes of all elements within the set.
     *
     * @return the hash code of the set.
     */
        public int hashCode();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableCollection._Proxy
          implements UnmodifiableSet {
            public fabric.util.Collections.UnmodifiableSet
              fabric$util$Collections$UnmodifiableSet$(fabric.util.Set arg1) {
                return ((fabric.util.Collections.UnmodifiableSet) fetch()).
                  fabric$util$Collections$UnmodifiableSet$(arg1);
            }
            
            public _Proxy(UnmodifiableSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableCollection._Impl
          implements UnmodifiableSet {
            /**
     * Wrap a given set.
     * @param s the set to wrap
     * @throws NullPointerException if s is null
     */
            public UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(
              fabric.util.Set s) {
                fabric$util$Collections$UnmodifiableCollection$(s);
                return (UnmodifiableSet) this.$getProxy();
            }
            
            /**
     * Returns <code>true</code> if the object, o, is also an instance of
     * <code>Set</code> of the same size and with the same entries.
     *
     * @return <code>true</code> if o is an equivalent set.
     */
            public boolean equals(fabric.lang.Object o) {
                return this.get$c().equals(o);
            }
            
            /**
     * Computes the hash code of this set, as the sum of the
     * hash codes of all elements within the set.
     *
     * @return the hash code of the set.
     */
            public int hashCode() { return this.get$c().hashCode(); }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableSet) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSet._Proxy(this);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableSet.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSet._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSet._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableSet._Static.
                             _Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm729 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled732 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff730 = 1;
                            boolean $doBackoff731 = true;
                            boolean $retry725 = true;
                            boolean $keepReads726 = false;
                            $label723: for (boolean $commit724 = false;
                                            !$commit724; ) {
                                if ($backoffEnabled732) {
                                    if ($doBackoff731) {
                                        if ($backoff730 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff730));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e727) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff730 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff730 =
                                              java.lang.Math.
                                                min(
                                                  $backoff730 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff731 = $backoff730 <= 32 ||
                                                      !$doBackoff731;
                                }
                                $commit724 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableSet.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -9215047833775013803L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e727) {
                                    $commit724 = false;
                                    continue $label723;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e727) {
                                    $commit724 = false;
                                    $retry725 = false;
                                    $keepReads726 = $e727.keepReads;
                                    fabric.common.TransactionID $currentTid728 =
                                      $tm729.getCurrentTid();
                                    if ($e727.tid ==
                                          null ||
                                          !$e727.tid.isDescendantOf(
                                                       $currentTid728)) {
                                        throw $e727;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e727);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e727) {
                                    $commit724 = false;
                                    fabric.common.TransactionID $currentTid728 =
                                      $tm729.getCurrentTid();
                                    if ($e727.tid.isDescendantOf(
                                                    $currentTid728))
                                        continue $label723;
                                    if ($currentTid728.parent != null) {
                                        $retry725 = false;
                                        throw $e727;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e727) {
                                    $commit724 = false;
                                    $retry725 = false;
                                    if ($tm729.inNestedTxn()) {
                                        $keepReads726 = true;
                                    }
                                    throw $e727;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid728 =
                                      $tm729.getCurrentTid();
                                    if ($commit724) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e727) {
                                            $commit724 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e727) {
                                            $commit724 = false;
                                            $retry725 = false;
                                            $keepReads726 = $e727.keepReads;
                                            if ($e727.tid ==
                                                  null ||
                                                  !$e727.tid.isDescendantOf(
                                                               $currentTid728))
                                                throw $e727;
                                            throw new fabric.worker.
                                                    UserAbortException($e727);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e727) {
                                            $commit724 = false;
                                            $currentTid728 =
                                              $tm729.getCurrentTid();
                                            if ($currentTid728 != null) {
                                                if ($e727.tid.
                                                      equals($currentTid728) ||
                                                      !$e727.tid.
                                                      isDescendantOf(
                                                        $currentTid728)) {
                                                    throw $e727;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm729.inNestedTxn() &&
                                              $tm729.checkForStaleObjects()) {
                                            $retry725 = true;
                                            $keepReads726 = false;
                                        }
                                        if ($keepReads726) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e727) {
                                                $currentTid728 = $tm729.getCurrentTid();
                                                if ($currentTid728 != null &&
                                                      ($e727.tid.equals($currentTid728) || !$e727.tid.isDescendantOf($currentTid728))) {
                                                    throw $e727;
                                                } else {
                                                    $retry725 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit724) {
                                        {  }
                                        if ($retry725) { continue $label723; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 94, 2, -78, -78,
        -62, 127, -75, 55, 111, -29, 58, 25, -44, -104, -89, 80, -71, 17, 47,
        -75, -15, 56, 101, 60, -72, -46, 3, -76, -65, 8, -3, 88 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #unmodifiableSortedMap(SortedMap)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSortedMap
      extends fabric.util.SortedMap, UnmodifiableMap {
        public fabric.util.SortedMap get$sm();
        
        public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);
        
        /**
     * Wrap a given map.
     * @param sm the map to wrap
     * @throws NullPointerException if sm is null
     */
        public UnmodifiableSortedMap
          fabric$util$Collections$UnmodifiableSortedMap$(fabric.util.SortedMap sm);
        
        /**
     * Returns the comparator used in sorting the underlying map,
     * or null if it is the keys' natural ordering.
     *
     * @return the sorting comparator.
     */
        public fabric.util.Comparator comparator();
        
        /**
     * Returns the first (lowest sorted) key in the map.
     *
     * @return the first key.
     * @throws NoSuchElementException if this map is empty.
     */
        public fabric.lang.Object firstKey();
        
        /**
     * Returns a unmodifiable view of the portion of the map strictly less
     * than toKey. The view is backed by the underlying map, so changes in
     * one show up in the other.  The submap supports all optional operations
     * of the original.  This operation is equivalent to
     * <code>subMap(firstKey(), toKey)</code>.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of toKey. Note that the endpoint, toKey,
     * is not included; if you want this value to be included, pass its successor
     * object in to toKey.  For example, for Integers, you could request
     * <code>headMap(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if toKey is not comparable to the map contents.
     * @throws IllegalArgumentException if this is a subMap, and toKey is out
     *         of range.
     * @throws NullPointerException if toKey is null but the map does not allow
     *         null keys.
     */
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        /**
     * Returns the last (highest sorted) key in the map.
     *
     * @return the last key.
     * @throws NoSuchElementException if this map is empty.
     */
        public fabric.lang.Object lastKey();
        
        /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey, and strictly less than toKey. The view is backed by
     * the underlying map, so changes in one show up in the other. The submap
     * supports all optional operations of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey and toKey. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you could request
     * <code>subMap(Integer.valueOf(lowlimit.intValue() + 1),
     * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromKey the inclusive lower range of the submap.
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if fromKey or toKey is not comparable to
     *         the map contents.
     * @throws IllegalArgumentException if this is a subMap, and fromKey or
     *         toKey is out of range.
     * @throws NullPointerException if fromKey or toKey is null but the map
     *         does not allow null keys.
     */
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey, fabric.lang.Object toKey);
        
        /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey. The view is backed by the underlying map, so changes
     * in one show up in the other. The submap supports all optional operations
     * of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey. Note that the endpoint, fromKey, is
     * included; if you do not want this value to be included, pass its successor object in
     * to fromKey.  For example, for Integers, you could request
     * <code>tailMap(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param fromKey the inclusive lower range of the submap
     * @return the submap
     * @throws ClassCastException if fromKey is not comparable to the map
     *         contents
     * @throws IllegalArgumentException if this is a subMap, and fromKey is out
     *         of range
     * @throws NullPointerException if fromKey is null but the map does not allow
     *         null keys
     */
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableMap._Proxy
          implements UnmodifiableSortedMap {
            public fabric.util.SortedMap get$sm() {
                return ((fabric.util.Collections.UnmodifiableSortedMap._Impl)
                          fetch()).get$sm();
            }
            
            public fabric.util.SortedMap set$sm(fabric.util.SortedMap val) {
                return ((fabric.util.Collections.UnmodifiableSortedMap._Impl)
                          fetch()).set$sm(val);
            }
            
            public fabric.util.Collections.UnmodifiableSortedMap
              fabric$util$Collections$UnmodifiableSortedMap$(
              fabric.util.SortedMap arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).
                  fabric$util$Collections$UnmodifiableSortedMap$(arg1);
            }
            
            public fabric.util.Comparator comparator() {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).comparator();
            }
            
            public fabric.lang.Object firstKey() {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).firstKey();
            }
            
            public fabric.util.SortedMap headMap(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).headMap(arg1);
            }
            
            public fabric.lang.Object lastKey() {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).lastKey();
            }
            
            public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                fabric.lang.Object arg2) {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).subMap(arg1, arg2);
            }
            
            public fabric.util.SortedMap tailMap(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedMap)
                          fetch()).tailMap(arg1);
            }
            
            public _Proxy(UnmodifiableSortedMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableMap._Impl
          implements UnmodifiableSortedMap {
            public fabric.util.SortedMap get$sm() { return this.sm; }
            
            public fabric.util.SortedMap set$sm(fabric.util.SortedMap val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.sm = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.SortedMap sm;
            
            /**
     * Wrap a given map.
     * @param sm the map to wrap
     * @throws NullPointerException if sm is null
     */
            public UnmodifiableSortedMap
              fabric$util$Collections$UnmodifiableSortedMap$(
              fabric.util.SortedMap sm) {
                this.set$sm(sm);
                fabric$util$Collections$UnmodifiableMap$(sm);
                return (UnmodifiableSortedMap) this.$getProxy();
            }
            
            /**
     * Returns the comparator used in sorting the underlying map,
     * or null if it is the keys' natural ordering.
     *
     * @return the sorting comparator.
     */
            public fabric.util.Comparator comparator() {
                return this.get$sm().comparator();
            }
            
            /**
     * Returns the first (lowest sorted) key in the map.
     *
     * @return the first key.
     * @throws NoSuchElementException if this map is empty.
     */
            public fabric.lang.Object firstKey() {
                return this.get$sm().firstKey();
            }
            
            /**
     * Returns a unmodifiable view of the portion of the map strictly less
     * than toKey. The view is backed by the underlying map, so changes in
     * one show up in the other.  The submap supports all optional operations
     * of the original.  This operation is equivalent to
     * <code>subMap(firstKey(), toKey)</code>.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of toKey. Note that the endpoint, toKey,
     * is not included; if you want this value to be included, pass its successor
     * object in to toKey.  For example, for Integers, you could request
     * <code>headMap(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if toKey is not comparable to the map contents.
     * @throws IllegalArgumentException if this is a subMap, and toKey is out
     *         of range.
     * @throws NullPointerException if toKey is null but the map does not allow
     *         null keys.
     */
            public fabric.util.SortedMap headMap(fabric.lang.Object toKey) {
                return (UnmodifiableSortedMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedMap)
                              new fabric.util.Collections.UnmodifiableSortedMap.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedMap$(
                                 this.get$sm().headMap(toKey)));
            }
            
            /**
     * Returns the last (highest sorted) key in the map.
     *
     * @return the last key.
     * @throws NoSuchElementException if this map is empty.
     */
            public fabric.lang.Object lastKey() {
                return this.get$sm().lastKey();
            }
            
            /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey, and strictly less than toKey. The view is backed by
     * the underlying map, so changes in one show up in the other. The submap
     * supports all optional operations of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey and toKey. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you could request
     * <code>subMap(Integer.valueOf(lowlimit.intValue() + 1),
     * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromKey the inclusive lower range of the submap.
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if fromKey or toKey is not comparable to
     *         the map contents.
     * @throws IllegalArgumentException if this is a subMap, and fromKey or
     *         toKey is out of range.
     * @throws NullPointerException if fromKey or toKey is null but the map
     *         does not allow null keys.
     */
            public fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                                fabric.lang.Object toKey) {
                return (UnmodifiableSortedMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedMap)
                              new fabric.util.Collections.UnmodifiableSortedMap.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedMap$(
                                 this.get$sm().subMap(fromKey, toKey)));
            }
            
            /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey. The view is backed by the underlying map, so changes
     * in one show up in the other. The submap supports all optional operations
     * of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey. Note that the endpoint, fromKey, is
     * included; if you do not want this value to be included, pass its successor object in
     * to fromKey.  For example, for Integers, you could request
     * <code>tailMap(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param fromKey the inclusive lower range of the submap
     * @return the submap
     * @throws ClassCastException if fromKey is not comparable to the map
     *         contents
     * @throws IllegalArgumentException if this is a subMap, and fromKey is out
     *         of range
     * @throws NullPointerException if fromKey is null but the map does not allow
     *         null keys
     */
            public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey) {
                return (UnmodifiableSortedMap)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedMap)
                              new fabric.util.Collections.UnmodifiableSortedMap.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedMap$(
                                 this.get$sm().tailMap(fromKey)));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableSortedMap) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSortedMap._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.sm, refTypes, out, intraStoreRefs,
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
                this.sm = (fabric.util.SortedMap)
                            $readRef(fabric.util.SortedMap._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableSortedMap._Impl src =
                  (fabric.util.Collections.UnmodifiableSortedMap._Impl) other;
                this.sm = src.sm;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableSortedMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedMap.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableSortedMap._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSortedMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSortedMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSortedMap._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSortedMap._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSortedMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableSortedMap.
                             _Static._Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm739 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled742 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff740 = 1;
                            boolean $doBackoff741 = true;
                            boolean $retry735 = true;
                            boolean $keepReads736 = false;
                            $label733: for (boolean $commit734 = false;
                                            !$commit734; ) {
                                if ($backoffEnabled742) {
                                    if ($doBackoff741) {
                                        if ($backoff740 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff740));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e737) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff740 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff740 =
                                              java.lang.Math.
                                                min(
                                                  $backoff740 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff741 = $backoff740 <= 32 ||
                                                      !$doBackoff741;
                                }
                                $commit734 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableSortedMap.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -8806743815996713206L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e737) {
                                    $commit734 = false;
                                    continue $label733;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e737) {
                                    $commit734 = false;
                                    $retry735 = false;
                                    $keepReads736 = $e737.keepReads;
                                    fabric.common.TransactionID $currentTid738 =
                                      $tm739.getCurrentTid();
                                    if ($e737.tid ==
                                          null ||
                                          !$e737.tid.isDescendantOf(
                                                       $currentTid738)) {
                                        throw $e737;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e737);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e737) {
                                    $commit734 = false;
                                    fabric.common.TransactionID $currentTid738 =
                                      $tm739.getCurrentTid();
                                    if ($e737.tid.isDescendantOf(
                                                    $currentTid738))
                                        continue $label733;
                                    if ($currentTid738.parent != null) {
                                        $retry735 = false;
                                        throw $e737;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e737) {
                                    $commit734 = false;
                                    $retry735 = false;
                                    if ($tm739.inNestedTxn()) {
                                        $keepReads736 = true;
                                    }
                                    throw $e737;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid738 =
                                      $tm739.getCurrentTid();
                                    if ($commit734) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e737) {
                                            $commit734 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e737) {
                                            $commit734 = false;
                                            $retry735 = false;
                                            $keepReads736 = $e737.keepReads;
                                            if ($e737.tid ==
                                                  null ||
                                                  !$e737.tid.isDescendantOf(
                                                               $currentTid738))
                                                throw $e737;
                                            throw new fabric.worker.
                                                    UserAbortException($e737);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e737) {
                                            $commit734 = false;
                                            $currentTid738 =
                                              $tm739.getCurrentTid();
                                            if ($currentTid738 != null) {
                                                if ($e737.tid.
                                                      equals($currentTid738) ||
                                                      !$e737.tid.
                                                      isDescendantOf(
                                                        $currentTid738)) {
                                                    throw $e737;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm739.inNestedTxn() &&
                                              $tm739.checkForStaleObjects()) {
                                            $retry735 = true;
                                            $keepReads736 = false;
                                        }
                                        if ($keepReads736) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e737) {
                                                $currentTid738 = $tm739.getCurrentTid();
                                                if ($currentTid738 != null &&
                                                      ($e737.tid.equals($currentTid738) || !$e737.tid.isDescendantOf($currentTid738))) {
                                                    throw $e737;
                                                } else {
                                                    $retry735 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit734) {
                                        {  }
                                        if ($retry735) { continue $label733; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 17, 64, 103, -17,
        64, -37, -41, -46, -93, -25, 92, 122, 77, 65, 17, -78, -117, 113, 20, 7,
        -57, -27, -94, -97, 112, 7, 106, -45, 70, 40, 68, 13 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * The implementation of {@link #synchronizedSortedMap(SortedMap)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSortedSet
      extends fabric.util.SortedSet, UnmodifiableSet {
        public fabric.util.SortedSet get$ss();
        
        public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);
        
        /**
     * Wrap a given set.
     * @param ss the set to wrap
     * @throws NullPointerException if ss is null
     */
        public UnmodifiableSortedSet
          fabric$util$Collections$UnmodifiableSortedSet$(fabric.util.SortedSet ss);
        
        /**
     * Returns the comparator used in sorting the underlying set,
     * or null if it is the elements' natural ordering.
     *
     * @return the sorting comparator
     */
        public fabric.util.Comparator comparator();
        
        /**
     * Returns the first (lowest sorted) element in the underlying
     * set.
     *
     * @return the first element.
     * @throws NoSuchElementException if the set is empty.
     */
        public fabric.lang.Object first();
        
        /**
     * Returns a unmodifiable view of the portion of the set strictly
     * less than toElement. The view is backed by the underlying set,
     * so changes in one show up in the other.  The subset supports
     * all optional operations of the original.  This operation
     * is equivalent to <code>subSet(first(), toElement)</code>.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of toElement. Note that the endpoint, toElement,
     * is not included; if you want this value included, pass its successor object in to
     * toElement.  For example, for Integers, you could request
     * <code>headSet(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param toElement the exclusive upper range of the subset
     * @return the subset.
     * @throws ClassCastException if toElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and toElement is out
     *         of range.
     * @throws NullPointerException if toElement is null but the set does not
     *         allow null elements.
     */
        public fabric.util.SortedSet headSet(fabric.lang.Object toElement);
        
        /**
     * Returns the last (highest sorted) element in the underlying
     * set.
     *
     * @return the last element.
     * @throws NoSuchElementException if the set is empty.
     */
        public fabric.lang.Object last();
        
        /**
     * Returns a unmodifiable view of the portion of the set greater than or
     * equal to fromElement, and strictly less than toElement. The view is backed by
     * the underlying set, so changes in one show up in the other. The subset
     * supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement and toElement. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you can request
     * <code>subSet(Integer.valueOf(lowlimit.intValue() + 1),
     * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromElement the inclusive lower range of the subset.
     * @param toElement the exclusive upper range of the subset.
     * @return the subset.
     * @throws ClassCastException if fromElement or toElement is not comparable
     *         to the set contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement or
     *         toElement is out of range.
     * @throws NullPointerException if fromElement or toElement is null but the
     *         set does not allow null elements.
     */
        public fabric.util.SortedSet subSet(fabric.lang.Object fromElement, fabric.lang.Object toElement);
        
        /**
     * Returns a unmodifiable view of the portion of the set greater than or equal to
     * fromElement. The view is backed by the underlying set, so changes in one show up
     * in the other. The subset supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement. Note that the endpoint,
     * fromElement, is included; if you do not want this value to be included, pass its
     * successor object in to fromElement.  For example, for Integers, you could request
     * <code>tailSet(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param fromElement the inclusive lower range of the subset
     * @return the subset.
     * @throws ClassCastException if fromElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement is
     *         out of range.
     * @throws NullPointerException if fromElement is null but the set does not
     *         allow null elements.
     */
        public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableSet._Proxy
          implements UnmodifiableSortedSet {
            public fabric.util.SortedSet get$ss() {
                return ((fabric.util.Collections.UnmodifiableSortedSet._Impl)
                          fetch()).get$ss();
            }
            
            public fabric.util.SortedSet set$ss(fabric.util.SortedSet val) {
                return ((fabric.util.Collections.UnmodifiableSortedSet._Impl)
                          fetch()).set$ss(val);
            }
            
            public fabric.util.Collections.UnmodifiableSortedSet
              fabric$util$Collections$UnmodifiableSortedSet$(
              fabric.util.SortedSet arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).
                  fabric$util$Collections$UnmodifiableSortedSet$(arg1);
            }
            
            public fabric.util.Comparator comparator() {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).comparator();
            }
            
            public fabric.lang.Object first() {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).first();
            }
            
            public fabric.util.SortedSet headSet(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).headSet(arg1);
            }
            
            public fabric.lang.Object last() {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).last();
            }
            
            public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                                fabric.lang.Object arg2) {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).subSet(arg1, arg2);
            }
            
            public fabric.util.SortedSet tailSet(fabric.lang.Object arg1) {
                return ((fabric.util.Collections.UnmodifiableSortedSet)
                          fetch()).tailSet(arg1);
            }
            
            public _Proxy(UnmodifiableSortedSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableSet._Impl
          implements UnmodifiableSortedSet {
            public fabric.util.SortedSet get$ss() { return this.ss; }
            
            public fabric.util.SortedSet set$ss(fabric.util.SortedSet val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.ss = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.SortedSet ss;
            
            /**
     * Wrap a given set.
     * @param ss the set to wrap
     * @throws NullPointerException if ss is null
     */
            public UnmodifiableSortedSet
              fabric$util$Collections$UnmodifiableSortedSet$(
              fabric.util.SortedSet ss) {
                this.set$ss(ss);
                fabric$util$Collections$UnmodifiableSet$(ss);
                return (UnmodifiableSortedSet) this.$getProxy();
            }
            
            /**
     * Returns the comparator used in sorting the underlying set,
     * or null if it is the elements' natural ordering.
     *
     * @return the sorting comparator
     */
            public fabric.util.Comparator comparator() {
                return this.get$ss().comparator();
            }
            
            /**
     * Returns the first (lowest sorted) element in the underlying
     * set.
     *
     * @return the first element.
     * @throws NoSuchElementException if the set is empty.
     */
            public fabric.lang.Object first() { return this.get$ss().first(); }
            
            /**
     * Returns a unmodifiable view of the portion of the set strictly
     * less than toElement. The view is backed by the underlying set,
     * so changes in one show up in the other.  The subset supports
     * all optional operations of the original.  This operation
     * is equivalent to <code>subSet(first(), toElement)</code>.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of toElement. Note that the endpoint, toElement,
     * is not included; if you want this value included, pass its successor object in to
     * toElement.  For example, for Integers, you could request
     * <code>headSet(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param toElement the exclusive upper range of the subset
     * @return the subset.
     * @throws ClassCastException if toElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and toElement is out
     *         of range.
     * @throws NullPointerException if toElement is null but the set does not
     *         allow null elements.
     */
            public fabric.util.SortedSet headSet(fabric.lang.Object toElement) {
                return (UnmodifiableSortedSet)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedSet)
                              new fabric.util.Collections.UnmodifiableSortedSet.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedSet$(
                                 this.get$ss().headSet(toElement)));
            }
            
            /**
     * Returns the last (highest sorted) element in the underlying
     * set.
     *
     * @return the last element.
     * @throws NoSuchElementException if the set is empty.
     */
            public fabric.lang.Object last() { return this.get$ss().last(); }
            
            /**
     * Returns a unmodifiable view of the portion of the set greater than or
     * equal to fromElement, and strictly less than toElement. The view is backed by
     * the underlying set, so changes in one show up in the other. The subset
     * supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement and toElement. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you can request
     * <code>subSet(Integer.valueOf(lowlimit.intValue() + 1),
     * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromElement the inclusive lower range of the subset.
     * @param toElement the exclusive upper range of the subset.
     * @return the subset.
     * @throws ClassCastException if fromElement or toElement is not comparable
     *         to the set contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement or
     *         toElement is out of range.
     * @throws NullPointerException if fromElement or toElement is null but the
     *         set does not allow null elements.
     */
            public fabric.util.SortedSet subSet(fabric.lang.Object fromElement,
                                                fabric.lang.Object toElement) {
                return (UnmodifiableSortedSet)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedSet)
                              new fabric.util.Collections.UnmodifiableSortedSet.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedSet$(
                                 this.get$ss().subSet(fromElement, toElement)));
            }
            
            /**
     * Returns a unmodifiable view of the portion of the set greater than or equal to
     * fromElement. The view is backed by the underlying set, so changes in one show up
     * in the other. The subset supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement. Note that the endpoint,
     * fromElement, is included; if you do not want this value to be included, pass its
     * successor object in to fromElement.  For example, for Integers, you could request
     * <code>tailSet(Integer.valueOf(limit.intValue() + 1))</code>.
     *
     * @param fromElement the inclusive lower range of the subset
     * @return the subset.
     * @throws ClassCastException if fromElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement is
     *         out of range.
     * @throws NullPointerException if fromElement is null but the set does not
     *         allow null elements.
     */
            public fabric.util.SortedSet tailSet(
              fabric.lang.Object fromElement) {
                return (UnmodifiableSortedSet)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableSortedSet)
                              new fabric.util.Collections.UnmodifiableSortedSet.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableSortedSet$(
                                 this.get$ss().tailSet(fromElement)));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (UnmodifiableSortedSet) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSortedSet._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.ss, refTypes, out, intraStoreRefs,
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
                this.ss = (fabric.util.SortedSet)
                            $readRef(fabric.util.SortedSet._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableSortedSet._Impl src =
                  (fabric.util.Collections.UnmodifiableSortedSet._Impl) other;
                this.ss = src.ss;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableSortedSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedSet.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableSortedSet._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSortedSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSortedSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSortedSet._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSortedSet._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSortedSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    out.writeLong(this.serialVersionUID);
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
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableSortedSet.
                             _Static._Proxy(this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm749 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled752 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff750 = 1;
                            boolean $doBackoff751 = true;
                            boolean $retry745 = true;
                            boolean $keepReads746 = false;
                            $label743: for (boolean $commit744 = false;
                                            !$commit744; ) {
                                if ($backoffEnabled752) {
                                    if ($doBackoff751) {
                                        if ($backoff750 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff750));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e747) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff750 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff750 =
                                              java.lang.Math.
                                                min(
                                                  $backoff750 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff751 = $backoff750 <= 32 ||
                                                      !$doBackoff751;
                                }
                                $commit744 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Collections.UnmodifiableSortedSet.
                                      _Static.
                                      _Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -4929149591599911165L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e747) {
                                    $commit744 = false;
                                    continue $label743;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e747) {
                                    $commit744 = false;
                                    $retry745 = false;
                                    $keepReads746 = $e747.keepReads;
                                    fabric.common.TransactionID $currentTid748 =
                                      $tm749.getCurrentTid();
                                    if ($e747.tid ==
                                          null ||
                                          !$e747.tid.isDescendantOf(
                                                       $currentTid748)) {
                                        throw $e747;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e747);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e747) {
                                    $commit744 = false;
                                    fabric.common.TransactionID $currentTid748 =
                                      $tm749.getCurrentTid();
                                    if ($e747.tid.isDescendantOf(
                                                    $currentTid748))
                                        continue $label743;
                                    if ($currentTid748.parent != null) {
                                        $retry745 = false;
                                        throw $e747;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e747) {
                                    $commit744 = false;
                                    $retry745 = false;
                                    if ($tm749.inNestedTxn()) {
                                        $keepReads746 = true;
                                    }
                                    throw $e747;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid748 =
                                      $tm749.getCurrentTid();
                                    if ($commit744) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e747) {
                                            $commit744 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e747) {
                                            $commit744 = false;
                                            $retry745 = false;
                                            $keepReads746 = $e747.keepReads;
                                            if ($e747.tid ==
                                                  null ||
                                                  !$e747.tid.isDescendantOf(
                                                               $currentTid748))
                                                throw $e747;
                                            throw new fabric.worker.
                                                    UserAbortException($e747);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e747) {
                                            $commit744 = false;
                                            $currentTid748 =
                                              $tm749.getCurrentTid();
                                            if ($currentTid748 != null) {
                                                if ($e747.tid.
                                                      equals($currentTid748) ||
                                                      !$e747.tid.
                                                      isDescendantOf(
                                                        $currentTid748)) {
                                                    throw $e747;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm749.inNestedTxn() &&
                                              $tm749.checkForStaleObjects()) {
                                            $retry745 = true;
                                            $keepReads746 = false;
                                        }
                                        if ($keepReads746) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e747) {
                                                $currentTid748 = $tm749.getCurrentTid();
                                                if ($currentTid748 != null &&
                                                      ($e747.tid.equals($currentTid748) || !$e747.tid.isDescendantOf($currentTid748))) {
                                                    throw $e747;
                                                } else {
                                                    $retry745 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit744) {
                                        {  }
                                        if ($retry745) { continue $label743; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 107, -93, 70, 46,
        -3, 10, 29, -102, 80, 1, 50, 19, 53, 72, -54, -112, 76, 108, -54, -28,
        -112, -27, -91, 121, 24, 104, 86, -9, -85, 86, -81, -86 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections {
        public static final int compare(fabric.lang.Object arg1,
                                        fabric.lang.Object arg2,
                                        fabric.util.Comparator arg3) {
            return fabric.util.Collections._Impl.compare(arg1, arg2, arg3);
        }
        
        public static int binarySearch(fabric.util.List arg1,
                                       fabric.lang.Object arg2) {
            return fabric.util.Collections._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.util.List arg1,
                                       fabric.lang.Object arg2,
                                       fabric.util.Comparator arg3) {
            return fabric.util.Collections._Impl.binarySearch(arg1, arg2, arg3);
        }
        
        public static void copy(fabric.util.List arg1, fabric.util.List arg2) {
            fabric.util.Collections._Impl.copy(arg1, arg2);
        }
        
        public static void fill(fabric.util.List arg1,
                                fabric.lang.Object arg2) {
            fabric.util.Collections._Impl.fill(arg1, arg2);
        }
        
        public static int indexOfSubList(fabric.util.List arg1,
                                         fabric.util.List arg2) {
            return fabric.util.Collections._Impl.indexOfSubList(arg1, arg2);
        }
        
        public static int lastIndexOfSubList(fabric.util.List arg1,
                                             fabric.util.List arg2) {
            return fabric.util.Collections._Impl.lastIndexOfSubList(arg1, arg2);
        }
        
        public static fabric.util.ArrayList list(fabric.util.Enumeration arg1) {
            return fabric.util.Collections._Impl.list(arg1);
        }
        
        public static fabric.lang.Object max(fabric.util.Collection arg1) {
            return fabric.util.Collections._Impl.max(arg1);
        }
        
        public static fabric.lang.Object max(fabric.util.Collection arg1,
                                             fabric.util.Comparator arg2) {
            return fabric.util.Collections._Impl.max(arg1, arg2);
        }
        
        public static fabric.lang.Object min(fabric.util.Collection arg1) {
            return fabric.util.Collections._Impl.min(arg1);
        }
        
        public static fabric.lang.Object min(fabric.util.Collection arg1,
                                             fabric.util.Comparator arg2) {
            return fabric.util.Collections._Impl.min(arg1, arg2);
        }
        
        public static fabric.util.List nCopies(int arg1,
                                               fabric.lang.Object arg2) {
            return fabric.util.Collections._Impl.nCopies(arg1, arg2);
        }
        
        public static boolean replaceAll(fabric.util.List arg1,
                                         fabric.lang.Object arg2,
                                         fabric.lang.Object arg3) {
            return fabric.util.Collections._Impl.replaceAll(arg1, arg2, arg3);
        }
        
        public static void reverse(fabric.util.List arg1) {
            fabric.util.Collections._Impl.reverse(arg1);
        }
        
        public static fabric.util.Comparator reverseOrder() {
            return fabric.util.Collections._Impl.reverseOrder();
        }
        
        public static void rotate(fabric.util.List arg1, int arg2) {
            fabric.util.Collections._Impl.rotate(arg1, arg2);
        }
        
        public static void shuffle(fabric.util.List arg1) {
            fabric.util.Collections._Impl.shuffle(arg1);
        }
        
        public static void shuffle(fabric.util.List arg1,
                                   fabric.util.Random arg2) {
            fabric.util.Collections._Impl.shuffle(arg1, arg2);
        }
        
        public static fabric.util.Set singleton(fabric.lang.Object arg1) {
            return fabric.util.Collections._Impl.singleton(arg1);
        }
        
        public static fabric.util.List singletonList(fabric.lang.Object arg1) {
            return fabric.util.Collections._Impl.singletonList(arg1);
        }
        
        public static fabric.util.Map singletonMap(fabric.lang.Object arg1,
                                                   fabric.lang.Object arg2) {
            return fabric.util.Collections._Impl.singletonMap(arg1, arg2);
        }
        
        public static void sort(fabric.util.List arg1) {
            fabric.util.Collections._Impl.sort(arg1);
        }
        
        public static void sort(fabric.util.List arg1,
                                fabric.util.Comparator arg2) {
            fabric.util.Collections._Impl.sort(arg1, arg2);
        }
        
        public static void swap(fabric.util.List arg1, int arg2, int arg3) {
            fabric.util.Collections._Impl.swap(arg1, arg2, arg3);
        }
        
        public static fabric.util.Collection unmodifiableCollection(
          fabric.worker.Store arg1, fabric.util.Collection arg2) {
            return fabric.util.Collections._Impl.unmodifiableCollection(arg1,
                                                                        arg2);
        }
        
        public static fabric.util.List unmodifiableList(
          fabric.worker.Store arg1, fabric.util.List arg2) {
            return fabric.util.Collections._Impl.unmodifiableList(arg1, arg2);
        }
        
        public static fabric.util.Map unmodifiableMap(fabric.worker.Store arg1,
                                                      fabric.util.Map arg2) {
            return fabric.util.Collections._Impl.unmodifiableMap(arg1, arg2);
        }
        
        public static fabric.util.Set unmodifiableSet(fabric.worker.Store arg1,
                                                      fabric.util.Set arg2) {
            return fabric.util.Collections._Impl.unmodifiableSet(arg1, arg2);
        }
        
        public static fabric.util.SortedMap unmodifiableSortedMap(
          fabric.worker.Store arg1, fabric.util.SortedMap arg2) {
            return fabric.util.Collections._Impl.unmodifiableSortedMap(arg1,
                                                                       arg2);
        }
        
        public static fabric.util.SortedSet unmodifiableSortedSet(
          fabric.worker.Store arg1, fabric.util.SortedSet arg2) {
            return fabric.util.Collections._Impl.unmodifiableSortedSet(arg1,
                                                                       arg2);
        }
        
        public _Proxy(Collections._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections {
        /**
   * Determines if a list should be treated as a sequential-access one.
   * Rather than the old method of JDK 1.3 of assuming only instanceof
   * AbstractSequentialList should be sequential, this uses the new method
   * of JDK 1.4 of assuming anything that does NOT implement RandomAccess
   * and exceeds a large (unspecified) size should be sequential.
   *
   * @param l the list to check
   * @return <code>true</code> if it should be treated as sequential-access
   */
        private static boolean isSequential(fabric.util.List l) {
            return !(fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(l)) instanceof fabric.util.RandomAccess) &&
              l.
              size() >
              fabric.util.Collections._Static._Proxy.$instance.
              get$LARGE_LIST_SIZE();
        }
        
        /**
   * This class is non-instantiable.
   */
        private _Impl(fabric.worker.Store $location) { super($location); }
        
        /**
   * Compare two objects with or without a Comparator. If c is null, uses the
   * natural ordering. Slightly slower than doing it inline if the JVM isn't
   * clever, but worth it for removing a duplicate of the search code.
   * Note: This code is also used in Arrays (for sort as well as search).
   */
        public static final int compare(fabric.lang.Object o1,
                                        fabric.lang.Object o2,
                                        fabric.util.Comparator c) {
            return fabric.lang.Object._Proxy.idEquals(c, null)
              ? ((java.lang.Comparable)
                   (java.lang.Object)
                     fabric.lang.WrappedJavaInlineable.$unwrap(o1)).
              compareTo((java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(o2))
              : c.compare(o1, o2);
        }
        
        /**
   * Perform a binary search of a List for a key, using the natural ordering of
   * the elements. The list must be sorted (as by the sort() method) - if it is
   * not, the behavior of this method is undefined, and may be an infinite
   * loop. Further, the key must be comparable with every item in the list. If
   * the list contains the key more than once, any one of them may be found.
   * <p>
   *
   * This algorithm behaves in log(n) time for {@link RandomAccess} lists,
   * and uses a linear search with O(n) link traversals and log(n) comparisons
   * with {@link AbstractSequentialList} lists. Note: although the
   * specification allows for an infinite loop if the list is unsorted, it will
   * not happen in this (Classpath) implementation.
   *
   * @param l the list to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of l
   * @throws NullPointerException if a null element has compareTo called
   * @see #sort(List)
   */
        public static int binarySearch(fabric.util.List l,
                                       fabric.lang.Object key) {
            return fabric.util.Collections._Impl.binarySearch(l, key, null);
        }
        
        /**
   * Perform a binary search of a List for a key, using a supplied Comparator.
   * The list must be sorted (as by the sort() method with the same Comparator)
   * - if it is not, the behavior of this method is undefined, and may be an
   * infinite loop. Further, the key must be comparable with every item in the
   * list. If the list contains the key more than once, any one of them may be
   * found. If the comparator is null, the elements' natural ordering is used.
   * <p>
   *
   * This algorithm behaves in log(n) time for {@link RandomAccess} lists,
   * and uses a linear search with O(n) link traversals and log(n) comparisons
   * with {@link AbstractSequentialList} lists. Note: although the
   * specification allows for an infinite loop if the list is unsorted, it will
   * not happen in this (Classpath) implementation.
   *
   * @param l the list to search (must be sorted)
   * @param key the value to search for
   * @param c the comparator by which the list is sorted
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of l
   * @throws NullPointerException if a null element is compared with natural
   *         ordering (only possible when c is null)
   * @see #sort(List, Comparator)
   */
        public static int binarySearch(fabric.util.List l,
                                       fabric.lang.Object key,
                                       fabric.util.Comparator c) {
            int pos = 0;
            int low = 0;
            int hi = l.size() - 1;
            if (fabric.util.Collections._Impl.isSequential(l)) {
                fabric.util.ListIterator
                  itr =
                  l.
                  listIterator(
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$LOCAL_STORE());
                int i = 0;
                fabric.lang.Object o = itr.next();
                boolean forward = true;
                while (low <= hi) {
                    pos = low + hi >>> 1;
                    if (i < pos) {
                        if (!forward) itr.next();
                        for (; i != pos; i++, o = itr.next()) ;
                        forward = true;
                    } else {
                        if (forward) itr.previous();
                        for (; i != pos; i--, o = itr.previous()) ;
                        forward = false;
                    }
                    final int d = fabric.util.Collections._Impl.compare(o, key,
                                                                        c);
                    if (d == 0) return pos; else if (d > 0) hi = pos - 1; else
                        low = ++pos;
                }
            }
            else {
                while (low <= hi) {
                    pos =
                      low + hi >>> 1;
                    final int d =
                      fabric.util.Collections._Impl.compare(l.get(pos), key, c);
                    if (d == 0) return pos; else if (d > 0) hi = pos - 1; else
                        low = ++pos;
                }
            }
            return -pos - 1;
        }
        
        /**
   * Copy one list to another. If the destination list is longer than the
   * source list, the remaining elements are unaffected. This method runs in
   * linear time.
   *
   * @param dest the destination list
   * @param source the source list
   * @throws IndexOutOfBoundsException if the destination list is shorter
   *         than the source list (the destination will be unmodified)
   * @throws UnsupportedOperationException if dest.listIterator(store) does not
   *         support the set operation
   */
        public static void copy(fabric.util.List dest,
                                fabric.util.List source) {
            int pos = source.size();
            if (dest.size() < pos)
                throw new java.lang.IndexOutOfBoundsException(
                        "Source does not fit in dest");
            fabric.util.Iterator
              i1 =
              source.
              iterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            fabric.util.ListIterator
              i2 =
              dest.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            while (--pos >= 0) {
                i2.next();
                i2.set(i1.next());
            }
        }
        
        /**
   * Replace every element of a list with a given value. This method runs in
   * linear time.
   *
   * @param l the list to fill.
   * @param val the object to vill the list with.
   * @throws UnsupportedOperationException if l.listIterator(store) does not
   *         support the set operation.
   */
        public static void fill(fabric.util.List l, fabric.lang.Object val) {
            fabric.util.ListIterator
              itr =
              l.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            for (int i = l.size() - 1; i >= 0; --i) {
                itr.next();
                itr.set(val);
            }
        }
        
        /**
   * Returns the starting index where the specified sublist first occurs
   * in a larger list, or -1 if there is no matching position. If
   * <code>target.size() &gt; source.size()</code>, this returns -1,
   * otherwise this implementation uses brute force, checking for
   * <code>source.sublist(i, i + target.size()).equals(target)</code>
   * for all possible i.
   *
   * @param source the list to search
   * @param target the sublist to search for
   * @return the index where found, or -1
   * @since 1.4
   */
        public static int indexOfSubList(fabric.util.List source,
                                         fabric.util.List target) {
            int ssize = source.size();
            for (int i = 0, j = target.size(); j <= ssize; i++, j++)
                if (source.subList(i, j).equals(target)) return i;
            return -1;
        }
        
        /**
   * Returns the starting index where the specified sublist last occurs
   * in a larger list, or -1 if there is no matching position. If
   * <code>target.size() &gt; source.size()</code>, this returns -1,
   * otherwise this implementation uses brute force, checking for
   * <code>source.sublist(i, i + target.size()).equals(target)</code>
   * for all possible i.
   *
   * @param source the list to search
   * @param target the sublist to search for
   * @return the index where found, or -1
   * @since 1.4
   */
        public static int lastIndexOfSubList(fabric.util.List source,
                                             fabric.util.List target) {
            int ssize = source.size();
            for (int i = ssize - target.size(), j = ssize; i >= 0; i--, j--)
                if (source.subList(i, j).equals(target)) return i;
            return -1;
        }
        
        /**
   * Returns an ArrayList holding the elements visited by a given
   * Enumeration. This method exists for interoperability between legacy
   * APIs and the new Collection API.
   *
   * @param e the enumeration to put in a list
   * @return a list containing the enumeration elements
   * @see ArrayList
   * @since 1.4
   */
        public static fabric.util.ArrayList list(fabric.util.Enumeration e) {
            fabric.util.ArrayList
              l =
              (fabric.util.ArrayList)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.ArrayList)
                     new fabric.
                       util.
                       ArrayList.
                       _Impl(
                       fabric.util.Collections._Static._Proxy.$instance.
                           $getStore()).
                     $getProxy()).fabric$util$ArrayList$());
            while (e.hasMoreElements()) l.add(e.nextElement());
            return l;
        }
        
        /**
   * Find the maximum element in a Collection, according to the natural
   * ordering of the elements. This implementation iterates over the
   * Collection, so it works in linear time.
   *
   * @param c the Collection to find the maximum element of
   * @return the maximum element of c
   * @exception NoSuchElementException if c is empty
   * @exception ClassCastException if elements in c are not mutually comparable
   * @exception NullPointerException if null.compareTo is called
   */
        public static fabric.lang.Object max(fabric.util.Collection c) {
            return fabric.util.Collections._Impl.max(c, null);
        }
        
        /**
   * Find the maximum element in a Collection, according to a specified
   * Comparator. This implementation iterates over the Collection, so it
   * works in linear time.
   *
   * @param c the Collection to find the maximum element of
   * @param order the Comparator to order the elements by, or null for natural
   *        ordering
   * @return the maximum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null is compared by natural ordering
   *        (only possible when order is null)
   */
        public static fabric.lang.Object max(fabric.util.Collection c,
                                             fabric.util.Comparator order) {
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            fabric.lang.Object max = itr.next();
            int csize = c.size();
            for (int i = 1; i < csize; i++) {
                fabric.lang.Object o = itr.next();
                if (fabric.util.Collections._Impl.compare(max, o, order) < 0)
                    max = o;
            }
            return max;
        }
        
        /**
   * Find the minimum element in a Collection, according to the natural
   * ordering of the elements. This implementation iterates over the
   * Collection, so it works in linear time.
   *
   * @param c the Collection to find the minimum element of
   * @return the minimum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null.compareTo is called
   */
        public static fabric.lang.Object min(fabric.util.Collection c) {
            return fabric.util.Collections._Impl.min(c, null);
        }
        
        /**
   * Find the minimum element in a Collection, according to a specified
   * Comparator. This implementation iterates over the Collection, so it
   * works in linear time.
   *
   * @param c the Collection to find the minimum element of
   * @param order the Comparator to order the elements by, or null for natural
   *        ordering
   * @return the minimum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null is compared by natural ordering
   *        (only possible when order is null)
   */
        public static fabric.lang.Object min(fabric.util.Collection c,
                                             fabric.util.Comparator order) {
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            fabric.lang.Object min = itr.next();
            int csize = c.size();
            for (int i = 1; i < csize; i++) {
                fabric.lang.Object o = itr.next();
                if (fabric.util.Collections._Impl.compare(min, o, order) > 0)
                    min = o;
            }
            return min;
        }
        
        /**
   * Creates an immutable list consisting of the same object repeated n times.
   * The returned object is tiny, consisting of only a single reference to the
   * object and a count of the number of elements. It is Serializable, and
   * implements RandomAccess. You can use it in tandem with List.addAll for
   * fast list construction.
   *
   * @param n the number of times to repeat the object
   * @param o the object to repeat
   * @return a List consisting of n copies of o
   * @throws IllegalArgumentException if n &lt; 0
   * @see List#addAll(Collection)
   * @see Serializable
   * @see RandomAccess
   */
        public static fabric.util.List nCopies(final int n,
                                               final fabric.lang.Object o) {
            return (CopiesList)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.CopiesList)
                          new fabric.
                            util.
                            Collections.
                            CopiesList.
                            _Impl(
                            fabric.util.Collections._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).fabric$util$Collections$CopiesList$(n,
                                                                           o));
        }
        
        /**
   * Replace all instances of one object with another in the specified list.
   * The list does not change size. An element e is replaced if
   * <code>oldval == null ? e == null : oldval.equals(e)</code>.
   *
   * @param list the list to iterate over
   * @param oldval the element to replace
   * @param newval the new value for the element
   * @return <code>true</code> if a replacement occurred.
   * @throws UnsupportedOperationException if the list iterator does not allow
   *         for the set operation
   * @throws ClassCastException if newval is of a type which cannot be added
   *         to the list
   * @throws IllegalArgumentException if some other aspect of newval stops
   *         it being added to the list
   * @since 1.4
   */
        public static boolean replaceAll(fabric.util.List list,
                                         fabric.lang.Object oldval,
                                         fabric.lang.Object newval) {
            fabric.util.ListIterator
              itr =
              list.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            boolean replace_occured = false;
            for (int i = list.size(); --i >= 0; )
                if (fabric.util.AbstractCollection._Impl.equals(oldval,
                                                                itr.next())) {
                    itr.set(newval);
                    replace_occured = true;
                }
            return replace_occured;
        }
        
        /**
   * Reverse a given list. This method works in linear time.
   *
   * @param l the list to reverse
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static void reverse(fabric.util.List l) {
            fabric.util.ListIterator
              i1 =
              l.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos1 = 1;
            int pos2 = l.size();
            fabric.util.ListIterator
              i2 =
              l.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                pos2);
            while (pos1 < pos2) {
                fabric.lang.Object o = i1.next();
                i1.set(i2.previous());
                i2.set(o);
                ++pos1;
                --pos2;
            }
        }
        
        /**
   * Get a comparator that implements the reverse of natural ordering. In
   * other words, this sorts Comparable objects opposite of how their
   * compareTo method would sort. This makes it easy to sort into reverse
   * order, by simply passing Collections.reverseOrder() to the sort method.
   * The return value of this method is Serializable.
   *
   * @return a comparator that imposes reverse natural ordering
   * @see Comparable
   * @see Serializable
   */
        public static fabric.util.Comparator reverseOrder() {
            return fabric.util.Collections._Static._Proxy.$instance.
              get$rcInstance();
        }
        
        /**
   * Rotate the elements in a list by a specified distance. After calling this
   * method, the element now at index <code>i</code> was formerly at index
   * <code>(i - distance) mod list.size()</code>. The list size is unchanged.
   * <p>
   *
   * For example, suppose a list contains <code>[t, a, n, k, s]</code>. After
   * either <code>Collections.rotate(l, 4)</code> or
   * <code>Collections.rotate(l, -1)</code>, the new contents are
   * <code>[s, t, a, n, k]</code>. This can be applied to sublists to rotate
   * just a portion of the list. For example, to move element <code>a</code>
   * forward two positions in the original example, use
   * <code>Collections.rotate(l.subList(1, 3+1), -1)</code>, which will
   * result in <code>[t, n, k, a, s]</code>.
   * <p>
   *
   * If the list is small or implements {@link RandomAccess}, the
   * implementation exchanges the first element to its destination, then the
   * displaced element, and so on until a circuit has been completed. The
   * process is repeated if needed on the second element, and so forth, until
   * all elements have been swapped.  For large non-random lists, the
   * implementation breaks the list into two sublists at index
   * <code>-distance mod size</code>, calls {@link #reverse(List)} on the
   * pieces, then reverses the overall list.
   *
   * @param list the list to rotate
   * @param distance the distance to rotate by; unrestricted in value
   * @throws UnsupportedOperationException if the list does not support set
   * @since 1.4
   */
        public static void rotate(fabric.util.List list, int distance) {
            int size = list.size();
            if (size == 0) return;
            distance %= size;
            if (distance == 0) return;
            if (distance < 0) distance += size;
            if (fabric.util.Collections._Impl.isSequential(list)) {
                fabric.util.Collections._Impl.reverse(list);
                fabric.util.Collections._Impl.reverse(list.subList(0,
                                                                   distance));
                fabric.util.Collections._Impl.reverse(list.subList(distance,
                                                                   size));
            } else {
                int a = size;
                int lcm = distance;
                int b = a % lcm;
                while (b != 0) {
                    a = lcm;
                    lcm = b;
                    b = a % lcm;
                }
                while (--lcm >= 0) {
                    fabric.lang.Object o = list.get(lcm);
                    for (int i = lcm + distance; i != lcm;
                         i = (i + distance) % size)
                        o = list.set(i, o);
                    list.set(lcm, o);
                }
            }
        }
        
        /**
   * Shuffle a list according to a default source of randomness. The algorithm
   * used iterates backwards over the list, swapping each element with an
   * element randomly selected from the elements in positions less than or
   * equal to it (using r.nextInt(int)).
   * <p>
   *
   * This algorithm would result in a perfectly fair shuffle (that is, each
   * element would have an equal chance of ending up in any position) if r were
   * a perfect source of randomness. In practice the results are merely very
   * close to perfect.
   * <p>
   *
   * This method operates in linear time. To do this on large lists which do
   * not implement {@link RandomAccess}, a temporary array is used to acheive
   * this speed, since it would be quadratic access otherwise.
   *
   * @param l the list to shuffle
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static void shuffle(fabric.util.List l) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$defaultRandom(),
                    null)) {
                synchronized (fabric.util.Collections.class)  {
                    if (fabric.lang.Object._Proxy.
                          idEquals(
                            fabric.util.Collections._Static._Proxy.$instance.
                                get$defaultRandom(),
                            null))
                        fabric.util.Collections._Static._Proxy.$instance.
                          set$defaultRandom(
                            (fabric.util.Random)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                ((fabric.util.Random)
                                   new fabric.
                                     util.
                                     Random.
                                     _Impl(
                                     fabric.util.Collections._Static._Proxy.
                                       $instance.
                                         $getStore()).
                                   $getProxy()).fabric$util$Random$()));
                }
            }
            fabric.util.Collections._Impl.
              shuffle(
                l,
                fabric.util.Collections._Static._Proxy.$instance.
                    get$defaultRandom());
        }
        
        /**
   * Shuffle a list according to a given source of randomness. The algorithm
   * used iterates backwards over the list, swapping each element with an
   * element randomly selected from the elements in positions less than or
   * equal to it (using r.nextInt(int)).
   * <p>
   *
   * This algorithm would result in a perfectly fair shuffle (that is, each
   * element would have an equal chance of ending up in any position) if r were
   * a perfect source of randomness. In practise (eg if r = new Random()) the
   * results are merely very close to perfect.
   * <p>
   *
   * This method operates in linear time. To do this on large lists which do
   * not implement {@link RandomAccess}, a temporary array is used to acheive
   * this speed, since it would be quadratic access otherwise.
   *
   * @param l the list to shuffle
   * @param r the source of randomness to use for the shuffle
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static void shuffle(fabric.util.List l, fabric.util.Random r) {
            int lsize = l.size();
            fabric.util.ListIterator
              i =
              l.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                lsize);
            boolean sequential = fabric.util.Collections._Impl.isSequential(l);
            fabric.lang.arrays.ObjectArray a = null;
            if (sequential) a = l.toArray();
            for (int pos = lsize - 1; pos > 0; --pos) {
                int swap = r.nextInt(pos + 1);
                fabric.lang.Object o;
                if (sequential) {
                    o = (fabric.lang.Object) a.get(swap);
                    a.set(swap, i.previous());
                } else
                    o = l.set(swap, i.previous());
                i.set(o);
            }
        }
        
        /**
   * Obtain an immutable Set consisting of a single element. The return value
   * of this method is Serializable.
   *
   * @param o the single element
   * @return an immutable Set containing only o
   * @see Serializable
   */
        public static fabric.util.Set singleton(fabric.lang.Object o) {
            return (SingletonSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.SingletonSet)
                          new fabric.
                            util.
                            Collections.
                            SingletonSet.
                            _Impl(
                            fabric.util.Collections._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).fabric$util$Collections$SingletonSet$(
                                         o));
        }
        
        /**
   * Obtain an immutable List consisting of a single element. The return value
   * of this method is Serializable, and implements RandomAccess.
   *
   * @param o the single element
   * @return an immutable List containing only o
   * @see Serializable
   * @see RandomAccess
   * @since 1.3
   */
        public static fabric.util.List singletonList(fabric.lang.Object o) {
            return (SingletonList)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.SingletonList)
                          new fabric.
                            util.
                            Collections.
                            SingletonList.
                            _Impl(
                            fabric.util.Collections._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).fabric$util$Collections$SingletonList$(
                                         o));
        }
        
        /**
   * Obtain an immutable Map consisting of a single key-value pair.
   * The return value of this method is Serializable.
   *
   * @param key the single key
   * @param value the single value
   * @return an immutable Map containing only the single key-value pair
   * @see Serializable
   * @since 1.3
   */
        public static fabric.util.Map singletonMap(fabric.lang.Object key,
                                                   fabric.lang.Object value) {
            return (SingletonMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.SingletonMap)
                          new fabric.
                            util.
                            Collections.
                            SingletonMap.
                            _Impl(
                            fabric.util.Collections._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).fabric$util$Collections$SingletonMap$(
                                         key, value));
        }
        
        /**
   * Sort a list according to the natural ordering of its elements. The list
   * must be modifiable, but can be of fixed size. The sort algorithm is
   * precisely that used by Arrays.sort(Object[]), which offers guaranteed
   * nlog(n) performance. This implementation dumps the list into an array,
   * sorts the array, and then iterates over the list setting each element from
   * the array.
   *
   * @param l the List to sort (<code>null</code> not permitted)
   * @throws ClassCastException if some items are not mutually comparable
   * @throws UnsupportedOperationException if the List is not modifiable
   * @throws NullPointerException if the list is <code>null</code>, or contains
   *     some element that is <code>null</code>.
   * @see Arrays#sort(Object[])
   */
        public static void sort(fabric.util.List l) {
            fabric.util.Collections._Impl.sort(l, null);
        }
        
        /**
   * Sort a list according to a specified Comparator. The list must be
   * modifiable, but can be of fixed size. The sort algorithm is precisely that
   * used by Arrays.sort(Object[], Comparator), which offers guaranteed
   * nlog(n) performance. This implementation dumps the list into an array,
   * sorts the array, and then iterates over the list setting each element from
   * the array.
   *
   * @param l the List to sort (<code>null</code> not permitted)
   * @param c the Comparator specifying the ordering for the elements, or
   *        <code>null</code> for natural ordering
   * @throws ClassCastException if c will not compare some pair of items
   * @throws UnsupportedOperationException if the List is not modifiable
   * @throws NullPointerException if the List is <code>null</code> or 
   *         <code>null</code> is compared by natural ordering (only possible 
   *         when c is <code>null</code>)
   *         
   * @see Arrays#sort(Object[], Comparator)
   */
        public static void sort(fabric.util.List l, fabric.util.Comparator c) {
            fabric.lang.arrays.ObjectArray a = l.toArray();
            fabric.util.Arrays._Impl.sort(a, c);
            fabric.util.ListIterator
              i =
              l.
              listIterator(
                fabric.util.Collections._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            for (int pos = 0, alen = a.get$length(); pos < alen; pos++) {
                i.next();
                i.set((fabric.lang.Object) a.get(pos));
            }
        }
        
        /**
   * Swaps the elements at the specified positions within the list. Equal
   * positions have no effect.
   *
   * @param l the list to work on
   * @param i the first index to swap
   * @param j the second index
   * @throws UnsupportedOperationException if list.set is not supported
   * @throws IndexOutOfBoundsException if either i or j is &lt; 0 or &gt;=
   *         list.size()
   * @since 1.4
   */
        public static void swap(fabric.util.List l, int i, int j) {
            l.set(i, l.set(j, l.get(i)));
        }
        
        /**
   * Returns an unmodifiable view of the given collection. This allows
   * "read-only" access, although changes in the backing collection show up
   * in this view. Attempts to modify the collection directly or via iterators
   * will fail with {@link UnsupportedOperationException}.  Although this view
   * prevents changes to the structure of the collection and its elements, the values
   * referenced by the objects in the collection can still be modified.
   * <p>
   *
   * Since the collection might be a List or a Set, and those have incompatible
   * equals and hashCode requirements, this relies on Object's implementation
   * rather than passing those calls on to the wrapped collection. The returned
   * Collection implements Serializable, but can only be serialized if
   * the collection it wraps is likewise Serializable.
   *
   * @param c the collection to wrap
   * @return a read-only view of the collection
   * @see Serializable
   */
        public static fabric.util.Collection unmodifiableCollection(
          fabric.worker.Store store, fabric.util.Collection c) {
            return (UnmodifiableCollection)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableCollection)
                          new fabric.
                            util.
                            Collections.
                            UnmodifiableCollection.
                            _Impl(
                            fabric.util.Collections._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).
                           fabric$util$Collections$UnmodifiableCollection$(c));
        }
        
        /**
   * Returns an unmodifiable view of the given list. This allows
   * "read-only" access, although changes in the backing list show up
   * in this view. Attempts to modify the list directly, via iterators, or
   * via sublists, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the list and
   * its elements, the values referenced by the objects in the list can
   * still be modified.   
   * <p>
   *
   * The returned List implements Serializable, but can only be serialized if
   * the list it wraps is likewise Serializable. In addition, if the wrapped
   * list implements RandomAccess, this does too.
   *
   * @param l the list to wrap
   * @return a read-only view of the list
   * @see Serializable
   * @see RandomAccess
   */
        public static fabric.util.List unmodifiableList(
          fabric.worker.Store store, fabric.util.List l) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l)) instanceof fabric.util.RandomAccess)
                return (UnmodifiableRandomAccessList)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.Collections.UnmodifiableRandomAccessList)
                              new fabric.util.Collections.
                                UnmodifiableRandomAccessList._Impl(store).
                              $getProxy()).
                               fabric$util$Collections$UnmodifiableRandomAccessList$(
                                 l));
            return (UnmodifiableList)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableList)
                          new fabric.util.Collections.UnmodifiableList._Impl(
                            store).$getProxy()).
                           fabric$util$Collections$UnmodifiableList$(l));
        }
        
        /**
   * Returns an unmodifiable view of the given map. This allows "read-only"
   * access, although changes in the backing map show up in this view.
   * Attempts to modify the map directly, or via collection views or their
   * iterators will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the map and its
   * entries, the values referenced by the objects in the map can still be
   * modified.   
   * <p>
   *
   * The returned Map implements Serializable, but can only be serialized if
   * the map it wraps is likewise Serializable.
   *
   * @param m the map to wrap
   * @return a read-only view of the map
   * @see Serializable
   */
        public static fabric.util.Map unmodifiableMap(fabric.worker.Store store,
                                                      fabric.util.Map m) {
            return (UnmodifiableMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableMap)
                          new fabric.util.Collections.UnmodifiableMap._Impl(
                            store).$getProxy()).
                           fabric$util$Collections$UnmodifiableMap$(m));
        }
        
        /**
   * Returns an unmodifiable view of the given set. This allows
   * "read-only" access, although changes in the backing set show up
   * in this view. Attempts to modify the set directly or via iterators
   * will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the set and its
   * entries, the values referenced by the objects in the set can still be
   * modified.   
   * <p>
   *
   * The returned Set implements Serializable, but can only be serialized if
   * the set it wraps is likewise Serializable.
   *
   * @param s the set to wrap
   * @return a read-only view of the set
   * @see Serializable
   */
        public static fabric.util.Set unmodifiableSet(fabric.worker.Store store,
                                                      fabric.util.Set s) {
            return (UnmodifiableSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableSet)
                          new fabric.util.Collections.UnmodifiableSet._Impl(
                            store).$getProxy()).
                           fabric$util$Collections$UnmodifiableSet$(s));
        }
        
        /**
   * Returns an unmodifiable view of the given sorted map. This allows
   * "read-only" access, although changes in the backing map show up in this
   * view. Attempts to modify the map directly, via subviews, via collection
   * views, or iterators, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the map and its
   * entries, the values referenced by the objects in the map can still be
   * modified.   
   * <p>
   *
   * The returned SortedMap implements Serializable, but can only be
   * serialized if the map it wraps is likewise Serializable.
   *
   * @param m the map to wrap
   * @return a read-only view of the map
   * @see Serializable
   */
        public static fabric.util.SortedMap unmodifiableSortedMap(
          fabric.worker.Store store, fabric.util.SortedMap m) {
            return (UnmodifiableSortedMap)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableSortedMap)
                          new fabric.util.Collections.UnmodifiableSortedMap.
                            _Impl(store).
                          $getProxy()).
                           fabric$util$Collections$UnmodifiableSortedMap$(m));
        }
        
        /**
   * Returns an unmodifiable view of the given sorted set. This allows
   * "read-only" access, although changes in the backing set show up
   * in this view. Attempts to modify the set directly, via subsets, or via
   * iterators, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the set and its
   * entries, the values referenced by the objects in the set can still be
   * modified.   
   * <p>
   *
   * The returns SortedSet implements Serializable, but can only be
   * serialized if the set it wraps is likewise Serializable.
   *
   * @param s the set to wrap
   * @return a read-only view of the set
   * @see Serializable
   */
        public static fabric.util.SortedSet unmodifiableSortedSet(
          fabric.worker.Store store, fabric.util.SortedSet s) {
            return (UnmodifiableSortedSet)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Collections.UnmodifiableSortedSet)
                          new fabric.util.Collections.UnmodifiableSortedSet.
                            _Impl(store).
                          $getProxy()).
                           fabric$util$Collections$UnmodifiableSortedSet$(s));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.Collections) this.$getProxy();
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.Collections._Proxy(this);
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
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        public int get$LARGE_LIST_SIZE();
        
        public int set$LARGE_LIST_SIZE(int val);
        
        public int postInc$LARGE_LIST_SIZE();
        
        public int postDec$LARGE_LIST_SIZE();
        
        public fabric.util.Set get$EMPTY_SET();
        
        public fabric.util.Set set$EMPTY_SET(fabric.util.Set val);
        
        public fabric.util.List get$EMPTY_LIST();
        
        public fabric.util.List set$EMPTY_LIST(fabric.util.List val);
        
        public fabric.util.Map get$EMPTY_MAP();
        
        public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val);
        
        public fabric.util.Collections.ReverseComparator get$rcInstance();
        
        public fabric.util.Collections.ReverseComparator set$rcInstance(
          fabric.util.Collections.ReverseComparator val);
        
        public fabric.util.Random get$defaultRandom();
        
        public fabric.util.Random set$defaultRandom(fabric.util.Random val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Collections._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$LOCAL_STORE(val);
            }
            
            public int get$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$LARGE_LIST_SIZE();
            }
            
            public int set$LARGE_LIST_SIZE(int val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$LARGE_LIST_SIZE(val);
            }
            
            public int postInc$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  postInc$LARGE_LIST_SIZE();
            }
            
            public int postDec$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  postDec$LARGE_LIST_SIZE();
            }
            
            public fabric.util.Set get$EMPTY_SET() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_SET();
            }
            
            public fabric.util.Set set$EMPTY_SET(fabric.util.Set val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_SET(val);
            }
            
            public fabric.util.List get$EMPTY_LIST() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_LIST();
            }
            
            public fabric.util.List set$EMPTY_LIST(fabric.util.List val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_LIST(val);
            }
            
            public fabric.util.Map get$EMPTY_MAP() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_MAP();
            }
            
            public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_MAP(val);
            }
            
            public fabric.util.Collections.ReverseComparator get$rcInstance() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$rcInstance();
            }
            
            public fabric.util.Collections.ReverseComparator set$rcInstance(
              fabric.util.Collections.ReverseComparator val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$rcInstance(val);
            }
            
            public fabric.util.Random get$defaultRandom() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$defaultRandom();
            }
            
            public fabric.util.Random set$defaultRandom(
              fabric.util.Random val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$defaultRandom(val);
            }
            
            public _Proxy(fabric.util.Collections._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.Collections._Static $instance;
            
            static {
                fabric.
                  util.
                  Collections.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.Collections._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.Collections._Static._Impl.class);
                $instance = (fabric.util.Collections._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return this.LOCAL_STORE;
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.LOCAL_STORE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.worker.Store LOCAL_STORE;
            
            public int get$LARGE_LIST_SIZE() { return this.LARGE_LIST_SIZE; }
            
            public int set$LARGE_LIST_SIZE(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.LARGE_LIST_SIZE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$LARGE_LIST_SIZE() {
                int tmp = this.get$LARGE_LIST_SIZE();
                this.set$LARGE_LIST_SIZE((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$LARGE_LIST_SIZE() {
                int tmp = this.get$LARGE_LIST_SIZE();
                this.set$LARGE_LIST_SIZE((int) (tmp - 1));
                return tmp;
            }
            
            private int LARGE_LIST_SIZE;
            
            public fabric.util.Set get$EMPTY_SET() { return this.EMPTY_SET; }
            
            public fabric.util.Set set$EMPTY_SET(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_SET = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.Set EMPTY_SET;
            
            public fabric.util.List get$EMPTY_LIST() { return this.EMPTY_LIST; }
            
            public fabric.util.List set$EMPTY_LIST(fabric.util.List val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_LIST = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.List EMPTY_LIST;
            
            public fabric.util.Map get$EMPTY_MAP() { return this.EMPTY_MAP; }
            
            public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_MAP = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.Map EMPTY_MAP;
            
            public fabric.util.Collections.ReverseComparator get$rcInstance() {
                return this.rcInstance;
            }
            
            public fabric.util.Collections.ReverseComparator set$rcInstance(
              fabric.util.Collections.ReverseComparator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.rcInstance = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Collections.ReverseComparator rcInstance;
            
            public fabric.util.Random get$defaultRandom() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.defaultRandom;
            }
            
            public fabric.util.Random set$defaultRandom(
              fabric.util.Random val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.defaultRandom = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
   * Cache a single Random object for use by shuffle(List). This improves
   * performance as well as ensuring that sequential calls to shuffle() will
   * not result in the same shuffle order occurring: the resolution of
   * System.currentTimeMillis() is not sufficient to guarantee a unique seed.
   */
            private fabric.util.Random defaultRandom;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeInline(out, this.LOCAL_STORE);
                out.writeInt(this.LARGE_LIST_SIZE);
                $writeRef($getStore(), this.EMPTY_SET, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.EMPTY_LIST, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.EMPTY_MAP, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.rcInstance, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.defaultRandom, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
                this.LARGE_LIST_SIZE = in.readInt();
                this.EMPTY_SET = (fabric.util.Set)
                                   $readRef(fabric.util.Set._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                this.EMPTY_LIST = (fabric.util.List)
                                    $readRef(fabric.util.List._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
                this.EMPTY_MAP = (fabric.util.Map)
                                   $readRef(fabric.util.Map._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                this.rcInstance =
                  (fabric.util.Collections.ReverseComparator)
                    $readRef(
                      fabric.util.Collections.ReverseComparator._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.defaultRandom = (fabric.util.Random)
                                       $readRef(fabric.util.Random._Proxy.class,
                                                (fabric.common.RefTypeEnum)
                                                  refTypes.next(), in, store,
                                                intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm759 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled762 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff760 = 1;
                        boolean $doBackoff761 = true;
                        boolean $retry755 = true;
                        boolean $keepReads756 = false;
                        $label753: for (boolean $commit754 = false; !$commit754;
                                        ) {
                            if ($backoffEnabled762) {
                                if ($doBackoff761) {
                                    if ($backoff760 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff760));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e757) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff760 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff760 =
                                          java.lang.Math.
                                            min(
                                              $backoff760 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff761 = $backoff760 <= 32 ||
                                                  !$doBackoff761;
                            }
                            $commit754 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$LOCAL_STORE(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$LARGE_LIST_SIZE((int) 16);
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$EMPTY_SET(
                                    (fabric.util.Collections.EmptySet)
                                      fabric.lang.Object._Proxy.
                                      $getProxy(
                                        ((fabric.util.Collections.EmptySet)
                                           new fabric.util.Collections.EmptySet.
                                             _Impl(
                                             fabric.util.Collections._Static._Proxy.$instance.$getStore(
                                                                                                )).
                                           $getProxy()).
                                            fabric$util$Collections$EmptySet$(
                                              )));
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$EMPTY_LIST(
                                    (fabric.util.Collections.EmptyList)
                                      fabric.lang.Object._Proxy.
                                      $getProxy(
                                        ((fabric.util.Collections.EmptyList)
                                           new fabric.util.Collections.
                                             EmptyList._Impl(
                                             fabric.util.Collections._Static._Proxy.$instance.$getStore(
                                                                                                )).
                                           $getProxy()).
                                            fabric$util$Collections$EmptyList$(
                                              )));
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$EMPTY_MAP(
                                    (fabric.util.Collections.EmptyMap)
                                      fabric.lang.Object._Proxy.
                                      $getProxy(
                                        ((fabric.util.Collections.EmptyMap)
                                           new fabric.util.Collections.EmptyMap.
                                             _Impl(
                                             fabric.util.Collections._Static._Proxy.$instance.$getStore(
                                                                                                )).
                                           $getProxy()).
                                            fabric$util$Collections$EmptyMap$(
                                              )));
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$rcInstance(
                                    (fabric.util.Collections.ReverseComparator)
                                      fabric.lang.Object._Proxy.
                                      $getProxy(
                                        ((fabric.util.Collections.ReverseComparator)
                                           new fabric.util.Collections.
                                             ReverseComparator._Impl(
                                             fabric.util.Collections._Static._Proxy.$instance.$getStore(
                                                                                                )).
                                           $getProxy()).
                                            fabric$util$Collections$ReverseComparator$(
                                              )));
                                fabric.util.Collections._Static._Proxy.
                                  $instance.
                                  set$defaultRandom(null);
                            }
                            catch (final fabric.worker.RetryException $e757) {
                                $commit754 = false;
                                continue $label753;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e757) {
                                $commit754 = false;
                                $retry755 = false;
                                $keepReads756 = $e757.keepReads;
                                fabric.common.TransactionID $currentTid758 =
                                  $tm759.getCurrentTid();
                                if ($e757.tid ==
                                      null ||
                                      !$e757.tid.isDescendantOf(
                                                   $currentTid758)) {
                                    throw $e757;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e757);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e757) {
                                $commit754 = false;
                                fabric.common.TransactionID $currentTid758 =
                                  $tm759.getCurrentTid();
                                if ($e757.tid.isDescendantOf($currentTid758))
                                    continue $label753;
                                if ($currentTid758.parent != null) {
                                    $retry755 = false;
                                    throw $e757;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e757) {
                                $commit754 = false;
                                $retry755 = false;
                                if ($tm759.inNestedTxn()) {
                                    $keepReads756 = true;
                                }
                                throw $e757;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid758 =
                                  $tm759.getCurrentTid();
                                if ($commit754) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e757) {
                                        $commit754 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e757) {
                                        $commit754 = false;
                                        $retry755 = false;
                                        $keepReads756 = $e757.keepReads;
                                        if ($e757.tid ==
                                              null ||
                                              !$e757.tid.isDescendantOf(
                                                           $currentTid758))
                                            throw $e757;
                                        throw new fabric.worker.
                                                UserAbortException($e757);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e757) {
                                        $commit754 = false;
                                        $currentTid758 = $tm759.getCurrentTid();
                                        if ($currentTid758 != null) {
                                            if ($e757.tid.equals(
                                                            $currentTid758) ||
                                                  !$e757.tid.
                                                  isDescendantOf(
                                                    $currentTid758)) {
                                                throw $e757;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm759.inNestedTxn() &&
                                          $tm759.checkForStaleObjects()) {
                                        $retry755 = true;
                                        $keepReads756 = false;
                                    }
                                    if ($keepReads756) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e757) {
                                            $currentTid758 = $tm759.getCurrentTid();
                                            if ($currentTid758 != null &&
                                                  ($e757.tid.equals($currentTid758) || !$e757.tid.isDescendantOf($currentTid758))) {
                                                throw $e757;
                                            } else {
                                                $retry755 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit754) {
                                    {  }
                                    if ($retry755) { continue $label753; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -109, 83, -3, 80, 65,
    -97, -13, 82, 15, 36, -112, -128, 65, -92, 109, -66, 44, 119, 82, 91, -81,
    -93, -82, 68, -14, 20, -70, 16, -53, 38, -51, 47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
