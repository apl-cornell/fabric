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
        
        public static final byte[] $classHash = new byte[] { -20, 81, -13, 98,
        77, 94, 18, 92, -19, 20, -94, -44, -108, -18, 126, 4, -51, -75, 40,
        -124, -25, 111, 101, 1, -99, 111, 71, 124, -4, -60, 69, 22 };
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
        
        public static final byte[] $classHash = new byte[] { -116, 55, 118, -88,
        109, -54, 107, -52, 18, -109, 16, 127, -85, -42, -72, 77, -67, -27, -89,
        60, 120, 11, -124, 6, 88, -116, -88, -85, -29, 8, -64, -77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1ZfWwUxxWfOxv7/AE2BvNh/IW5kBiIT9CqVeIkqrlAuHDEFgbSGjXu3t6cvbC3e+zOmcMtNG0agVJEq/DRJErIHyGCBpe0qVASVa5Q1TYhpFFLEQlqE5AiWiJKaNqQIDUpfW9m7mt9t/jUWNr31jvzZn7vzXtv3syNXSFTbIt0xJSIpnexbQlqd61UIqFwn2LZNBrUFdteB18H1Zry0IFLh6OtXuINk1pVMUxDUxV90LAZmRbepIwoAYOywPq1oe6NpEpFwVWKPcyId+PylEXaE6a+bUg3mZxkwvj7Fwf2/eSh+pfKSN0AqdOMfqYwTQ2aBqMpNkBq4zQeoZbdE43S6ACZblAa7aeWpujaKHQ0jQHSYGtDhsKSFrXXUtvUR7Bjg51MUIvPmf6I8E2AbSVVZloAv17ATzJND4Q1m3WHSUVMo3rU3kJ2kPIwmRLTlSHoOCuc1iLARwysxO/QvVoDmFZMUWlapHyzZkQZaXNKZDT2r4YOIFoZp2zYzExVbijwgTQISLpiDAX6maUZQ9B1ipmEWRhpKjoodPIlFHWzMkQHGZnj7NcnmqBXFTcLijDS6OzGR4I1a3KsWc5qXXngrj3fNlYZXuIBzFGq6ojfB0KtDqG1NEYtaqhUCNYuCh9QZo3v8hICnRsdnUWfl7/z0deWtJ54XfSZV6BPb2QTVdmgeigy7U/Nwc47yhCGL2HaGrpCnuZ8VftkS3cqAd4+KzMiNnalG0+s/f03Hn6BXvaS6hCpUE09GQevmq6a8YSmU+s+alBLYTQaIlXUiAZ5e4hUwntYM6j42huL2ZSFSLnOP1WY/H8wUQyGQBNVwrtmxMz0e0Jhw/w9lSCEzIaHlBHiOULI4hZ4v07IvEZGVgeGzTgNRPQk3QruHYCHKpY6HIC4tTT1dtVMbAvYlhqwkgbToKf4LpQHpDpYCzS0uwBG4osdLoXo67d6PGDYNtWM0ohiwypJj1nep0NQrDL1KLUGVX3PeIjMGH+Se00VeroN3srt4oGVbnbmiFzZfcnlKz46NnhKeBzKSrMxMl/AE6uZA8+/Ip5g2zCiAVstBlQXpKguSFFjnlRX8GDoKPebCpsHWGa4WhjuzoSusJhpxVPE4+G6zeTyfApY7s2QRmDc2s7+b97/rV0dsGSpxNZyWDDs6nfGTTbbhOBNgWAYVOt2XvrkxQPbzWwEMeKfENgTJTEwO5yGskyVRiHxZYdf1K4cHxzf7vdiUqmCfMcU8EhIHq3OOfICtDud7NAaU8KkBm2g6NiUzlDVbNgyt2a/cAeYhqRB+AIaywGQ58m7+xPPvPPWB1/iO0g6pdbl5N5+yrpzwhgHq+MBOz1r+3UWpdDv3Sf69u6/snMjNzz0WFBoQj/SIISvAnFrWo++vuXc+fcOnfFmF4uRyoSljUBUp7gy02/Anwee/+KDwYgfkENKDspE0J7JBAmcemEWXK7vrTfiZlSLaUpEp+gqn9XdsvT4P/bUi/XW4YuwnkWW3HyA7Pe5y8nDpx76tJUP41FxT8oaMNtNJLoZ2ZF7LEvhoZD63umWJ19TngHXhzRla6OUZx7CDUL4Ci7jtrid06WOti8j6RDWas54vDPpr8TdM+uMA4Gxp5uC91wWcZ9xRhxjfoG436DkxMmyF+LXvB0Vv/OSygFSzzduxWAbFMhd4AcDsPXaQfkxTKbmtedvo2LP6M4EW7MzEHKmdYZBNt/AO/bG92rh+cJxwBBz0Ugr4QHHmfee5L/A1hkJpDNTHsJf7uQiCzhdiKSTG7IMXxcxzEdY+jBSpcXjSYbrz2daDJWKzUueDVAIwSKvD91bwPZ9lhaH+BmRGy7dte+xG1179gm/E1XJggmFQa6MqEz4lFP5vCmYZb7bLFxi5d9f3P6rI9t3il27IX+PXWEk4z87+/mbXU9cOFkgg5frpkjA9Sk32yC5h8HSaIaipzKG96LhZ8ndcqbkJMfwOd7q5e+wpYp8rpldmSoS7Mwb54LlcQvQTTU9C/Sfk7vJrFWMqBnvUaHe4+1NaKKWYuUSN8+h7+87GO19fqlXBtIKWGhZ02bRYe6cP6EWX8MrxGxIXLjcckdw88UhYek2x7TO3j9dM3byvoXq415SlvH9CWVpvlB3vsdXWxSqamNdnt+3Z8zPk8MySASQjJsfkLw91+/FtlBwYYUxFrtknEGXNgXJAOPnF1gcPy6Ov2AF4M9ieDCDvAYHagTEUwHx+5KfmyRy2DwqEsmIrqmpfFNUy4Hekfy00xML67LJpY1X15AReL7mPXpkZCK7l5EyOIAUUq4dMMwjpPWHko8UUQ5JbKIaKJKU3CyuhkcYBP81+KgjLrpwsgUgD1GWDq4GGVyYqLtEok6HYn41VkjHW2DqWwlp+1Tyd0vTEUX+KvnZSek4ykd9xEXHR5HsYMQn9zq70JpVRkxTp4pRSKclMBWE1PweyYuFUxGdUKRN8rk31Sm9CrMK19EiwSH9kYvKe5E8xkhNWuUenXvtzkLqLYBZ7yakY1jyvtLUQ5FeyUMlLNlTLvifRrIfYppuSSq6XRQ67PIe2OQXVAve8Vlp0FHkP5Jfm1xieM6l7XkkB8HRhhV7OAinLx6DxTJBH+A+Ivne0nCjyOOS7y7B5GMu4I8hOQyBANsjTfXGimL3w5ADwFZLvqw07CiyVPLFJWA/7oL9ZSQ/B3eHTZ6FboIf3T0KiWqq4P5rpeFHkY8l/7AE/L92wX8Cyavg7haNmyO0qLsvghFNQhZ2Sl5dGnQUqZK8bFLQf8xHfc0F+kkkv4EiTUB3yzOIfpSQW1dJ3lkaehS5TfKOEtD/0QU9rwZOcfSYJt3Qt8Cgj8DUf5P8TGnoUeTPkv9hcqnmnEvbX5CcgWhlJj9MYsXrKFVh8+BN4uTx1uHrc8f9H1wXZarzMjGn4z/Hzl8+PbXlGL+rKMf7I5yq2nkLO/GSNe/ulIOszT8U+OB5G2qXHZKnGAn+P3dfcOKUV2hfxDAc8WjB3fgrSC7ggdHxL75cLFyQevGVn6LuT58XK3RqDIkLRr6Nny9SynJJIYTkUlYglYHnzT8/ibsFXrAF4fRG8fiETb1I1qQKavWgUCMHCXddPq+L5/3Lpe1jJFfhTKgiiDS4+iw4UU1yZAWrRyj7PHsI6awR/LarpQUZinwo+aVJpYjzfNTPXXS6geR6NtKy9nJgb4YhnwLsb0j+amnYUeQVyV+aVILwVLi0+ZDAqcjHTPGTRYHVyGmYVG2PReyzkMerBe/8pDQNUeSa5Fcnp+EMl7ZGJNNg0/drhsbCSoSKQnEU8kpV9rJZqj27yLU0NjdB9pxX4LZc/najBn9LD11cvaSxyE35nAm/pkm5YwfrfLMPrn9bpNL07zJVYeKLJXU99xYr570iYdGYxs1ZJe60ElzdZtA0Rwc4fiJD/J4m0aMNUozogf+1c+M2Cf2kEfKuTXoiNrMUlYXl7ac4VTQlLfyRcOzfs69X+NZd4NezYPH23V8dORJ/Y/ObDXvrv3v07Ctrxi8evitV84OKr+8+cvR934lf/g+XjRFzvBwAAA==";
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
        
        public static final byte[] $classHash = new byte[] { 12, 4, 106, 4, -72,
        87, -107, 126, 115, 27, -9, 37, 93, 5, -38, 93, 41, -45, -119, 104, -69,
        39, -128, 28, 25, -97, -74, 101, 46, -46, 114, -64 };
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
        
        public static final byte[] $classHash = new byte[] { -116, 55, 118, -88,
        109, -54, 107, -52, 18, -109, 16, 127, -85, -42, -72, 77, -67, -27, -89,
        60, 120, 11, -124, 6, 88, -116, -88, -85, -29, 8, -64, -77 };
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
        
        public static final byte[] $classHash = new byte[] { -81, 13, -50, 57,
        29, -117, 12, 75, 22, 125, 44, 57, 45, -19, 104, 117, -80, 26, 78, 29,
        -75, -46, -67, 80, 73, -46, 109, -107, -90, 31, 45, 118 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1XXWxURRQ+uy39o9AfpEChP5SVhAK7QX2BKpEuFFYWaFrAUKLr7N3Z9tK7917unS0LUgMaLaLhQQtCIjzhH1YwRmKMacKDIooxSozKg8oLEYM8GKPyoOKZmbt7d++21Qeb7Mx05pyZM98555tzx27BNNuCtiSJq1qQ7TWpHewi8Ui0m1g2TYQ1YttbcTamTC+NHLvxWqLZD/4oVCtEN3RVIVpMtxnMjO4iQySkUxba1hPp2AmVClfcQOwBBv6dnRkLWk1D29uvGcw5pGj/o0tDoy89WvtOCdT0QY2q9zLCVCVs6IxmWB9Up2gqTi17TSJBE31Qp1Oa6KWWSjR1Hwoaeh/U22q/TljaonYPtQ1tiAvW22mTWuLM7CQ330CzrbTCDAvNr5Xmp5mqhaKqzTqiUJZUqZawd8MTUBqFaUmN9KNgQzR7i5DYMdTF51G8SkUzrSRRaFaldFDVEwxavBq5Gwc2ogCqlqcoGzByR5XqBCegXpqkEb0/1MssVe9H0WlGGk9h0DjppihUYRJlkPTTGIO5XrluuYRSlQIWrsJgtldM7IQ+a/T4LM9btzbff+RxfYPuBx/anKCKxu2vQKVmj1IPTVKL6gqVitXt0WOkYfyQHwCFZ3uEpcx7+395cFnzhUtSZv4EMlviu6jCYsrp+MwvF4SXrCzhZlSYhq3yUCi4ufBqt7PSkTEx2htyO/LFYHbxQs/FHQfO0Jt+qIpAmWJo6RRGVZ1ipExVo9Z6qlOLMJqIQCXVE2GxHoFyHEdVncrZLcmkTVkESjUxVWaI/xGiJG7BISrHsaonjezYJGxAjDMmAMzBH5QAlH4FcPg29mcBRloYbAwNGCkaimtpugfDO4Q/SixlIIR5a6nKcsUw94ZsSwlZaZ2pKCnn5eXRUg3RwhvaQTTD/H+3y3Dra/f4fAhsi2IkaJzY6CUnYjq7NUyKDYaWoFZM0Y6MR2DW+AkRNZU80m2MVoGLDz29wMsR+bqj6c51v5yNXZYRx3Ud2BgskeZJb+aZF+ihQ0gZNIweJOg6THYLqnliBZGqgkhVY75MMHwq8qaInzJbJFpu22rcdpWpEZY0rFQGfD5xx7uEvjgK3T6IdIKMUb2k95GHHjvUhq7LmHtK0YlcNODNH5d1IjgimBQxpWbkxu/njg0bbiYxCBQleLEmT9A2L2CWodAEEqC7fXsrOR8bHw74OblUIu8xgpGJJNLsPaMgUTuypMfRmBaF6RwDovGlLFNVsQHL2OPOiECYyZt6GRMcLI+Bgi8f6DVPfvv5T/eKlyRLrTV5HNxLWUdeOvPNakTi1rnYb7UoRbnvjne/ePTWyE4BPEosmujAAG/dIHj60u6rP3x/+iu/6ywG5aalDmF2Z8Rl6u7gnw9/f/MfT0o+wXuk5rBDCK05RjD50Ytd4/JjcJueMhJqUiVxjfJQ+bPm7hXnfz5SK/2t4YxEz4Jl/76BOz+vEw5cfvSPZrGNT+FvkwugKyYJb5a78xrLInu5HZmDV5pOfExOYugjXdnqPioYCAQgIDx4j8BiuWhXeNbu402bRGtBLuK95N/FX1E3GPtCYy83hlfflPmfC0a+x8IJ8n87ycuTe86kfvO3lX3kh/I+qBUPONHZdoIchnHQh0+wHXYmozCjYL3wOZVvR0cu2RZ4EyHvWG8auLyDYy7Nx1Uy8mXgIBDzOEhdSN7vInlfdfoxvjrL5O1dGR+IwSqhski0i3mzRABZwoftjPMRL4EYVKqpVJpx/4uTlmLFYovSZzuyGzp5W2TtBNh3W2oK82fIeXjpodHDd4JHRmXcyepkUVGBkK8jKxRx5AxxbgZPWTjVKUKj68dzwx+8PjwiX+/6wrd2nZ5OvfX1X58Fj1/7ZAImL9UMScC1mamw4c1qhq5RdaJlcsD7OfANzqvZ7PRVecDnRatfjGdj3hQ+HlmS4KuNWRnJ+aoRzFWc6AuxOA+9w58JzcBaOMMBapqsaBLgnH5y9FRiyysr/E4arUM3O5Wta1sJx7moIt8k6kQ3Ia7dbFoZHrzeL3Fu8RzrlX5j09gn6xcrL/ihJBf5RcVpoVJHYbxXWRRra31rQdS35sAX1NCJ/fsAh3Y4/fz8qJePwoRulWAsnYJvYlOsEd70MWiXrgxwVwamrAMCri0P524wnW/YjpZfBHj2eadP/ccb4BNSZqbjmqpkCiGpcjbSnD7pjUf3Tv7CyKx3IpOzVlCyVjbmCksTPjko2l1ToCSORDYpVwQMMn7XOInNu7UMSvA7ZiJUWtD4y2j8daf/chJUeJMsvj9X+cLpP538/vnW7ptibT9v0gymB1RdZVESp5otMWBQV1zzOXDOmaRKlJmO3xkTFK/Op5QS/pCevr5x2exJCte5RR+3jt7ZUzUVc05t+0ZUX7nPpEosbpJpTct/TPLGZaZFk6q4aaV8WkzRPYU3zrsDsiXvhP0HpcQzGIRSgv83IkBuzDUyRBrTFv8kH/t1zu2yiq3XRBGEwLaem/H5yqbnqjc2DC9bufzWQPrtxs1N56+Md0eupI6+2rJ86B/SmpZlKhAAAA==";
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
            
            public static final byte[] $classHash = new byte[] { -37, 75, -2,
            -70, -64, -31, 32, 61, 121, 36, -59, -30, 6, -24, -5, 60, 36, 20, 3,
            87, 53, 113, -95, 22, 73, -76, 96, -62, 72, -1, -40, -9 };
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
        
        public static final byte[] $classHash = new byte[] { -20, 81, -13, 98,
        77, 94, 18, 92, -19, 20, -94, -44, -108, -18, 126, 4, -51, -75, 40,
        -124, -25, 111, 101, 1, -99, 111, 71, 124, -4, -60, 69, 22 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK0YbWwUx3XubJ8/MNgYMMHYhpgDlY/cibSiStwmgQsfF87FxYa2prG7tzdnL+ztHrtzcKQYpW0qUNVaVeJQIgW3akxpqAspKSpSRBpVpElEGzUINaRVCj+aJtShahS1RVXa9L2Zudu7vfXFVmPp5o1n3pt53+/tTNwkVbZFOpJKXNND7ECa2qFNSjwa61YsmyYiumLbvbA6oM6qjB5952Si3U/8MVKvKoZpaKqiDxg2I3Niu5V9StigLLxje7RzF6lVkXCLYg8x4t+1IWuRpWlTPzCom0xeUnL+46vDo9/rbzxbQRr6SINm9DCFaWrENBjNsj5Sn6KpOLXs9YkETfSRuQaliR5qaYquPQSIptFHmmxt0FBYxqL2dmqb+j5EbLIzaWrxO3OLyL4JbFsZlZkWsN8o2M8wTQ/HNJt1xkggqVE9Ye8lh0hljFQldWUQEJtjOSnC/MTwJlwH9DoN2LSSikpzJJV7NCPByBI3RV7i4FZAANLqFGVDZv6qSkOBBdIkWNIVYzDcwyzNGATUKjMDtzDSMuWhgFSTVtQ9yiAdYOQ2N1632AKsWq4WJGFkgRuNnwQ2a3HZrMBaNz/3mZGvGlsMP/EBzwmq6sh/DRC1u4i20yS1qKFSQVi/KnZUab5wxE8IIC9wIQucXxx877417S+8LHAWe+Bsi++mKhtQx+NzXmuNrLyrAtmoSZu2hq5QJDm3arfc6cymwdub8yfiZii3+cL2X3/p4VN00k/qoiSgmnomBV41VzVTaU2n1mZqUEthNBEltdRIRPh+lFTDPKYZVKxuSyZtyqKkUudLAZP/DypKwhGoomqYa0bSzM3TChvi82yaELIQfqSCkMC9hJx7jZCqRwkZf4ORreEhM0XDcT1D94N7h+FHFUsdCkPcWpp6h2qmD4RtSw1bGYNpgCnWhfDAqQ7aAgntELCR/niPyyL3jft9PlDsEtVM0Lhig5Wkx2zo1iEotph6gloDqj5yIUrmXXiCe00teroN3sr14gNLt7pzRCHtaGbDxvdOD1wSHoe0Um2MBAV7wpoF7AV7IG50ykyjhzJgrx5jKgRZKgRZasKXDUXGoj/hrhOweYzlT6yHE+9O6wpLmlYqS3w+Lt58Ts9vAYvvgUwCyaJ+Zc+DD3zlSAdYLZveXwn2Q9SgO3SchBOFmQLxMKA2HH7nn2eODptOEIEsJbFdSomx2eHWlWWqNAG5zzl+1VLl3MCF4aAf80otpDymgFNC/mh331EUo525fIfaqIqRWagDRcetXJKqY0OWud9Z4T4wB4cm4Q6oLBeDPFV+tid9/OqrNz7Ji0guqzYUpF8wVGdBJONhDTxm5zq677UoBbw3j3U/9vjNw7u44gFjmdeFQRwjEMEKhK5pffPlvW9c+9P4Fb9jLEaq05a2DwI7y4WZ+yH8+eD3X/xhPOICQsjKEZkLluaTQRqvXuEwV+h+O4yUmdCSmhLXKbrKBw3L1557d6RR2FuHFaE9i6z56AOc9UUbyMOX+v/Vzo/xqViWHAU6aCLXzXNOXm9ZygHkI/u1y21PvKQcB9eHTGVrD1GefAhXCOEWvJPr4g4+rnXtfQqHDqGtVr7ut0vz/iYsoI4z9oUnnmyJ3DMpQj/vjHjG7R6hv1MpiJM7T6X+4e8IvOgn1X2kkdduxWA7FUhf4Ad9UH3tiFyMkdlF+8WVVJSNznywtboDoeBadxg4KQfmiI3zOuH5wnFAEYtQSZsgbx8j5MRRCTXcnZfGcX7WR/jkbk6yjI8rcFjJFVmB01UM8xF2P4zUaqlUhqH9+U2roVmxedezE3ohMPKO6P0euu+2tBTEzz5Zc+mR0W99GBoZFX4nGpNlJb1BIY1oTviVs/m9Wbjl9nK3cIpNb58Zfu7Hw4dF4W4qLrMbjUzqp7//z29Cx66/4pHEK3VTJOBGrpR1eZ3WEaHYqu8T8qNlEs730OkWb536cHpvTn3VVKcpajCOtQDcQ9YO9I+Q8A++tchdBDhr2XJmw+EeBl6jGYqezfPvR/6bZS2/KuHFAv4LAsmXY0uUGs0M5XtccIEcY7XImG5C951Fu7RN1aZxm4x/fXQsse3EWr+M3o3gXbKXdu6tRfOWfAN08c7UicPrk213Rfa8NSjMu8R1rRv76a6JVzavUB/1k4p8wJW0w8VEncVhVmdR6OaN3qJgW5pXLM9I94FCT4BDnJHQLnQMx51KTCaUsdqV5nyOx3RxhGSZPMj7NoWR5cKFgujlwanaj6DDS39egll41gLg/BlCTvZK+MA0JYDKFUhn4rqmZotVUicPikoYcfuatzh7y+zxxd1MFAv+z3qZFhDcz0iFJkPKJdyngYfzwMOkhL+cQjgcUqViIMnzEp6fWgxfcfDMkzG937T2UCvUA4U/HzvFQc1ZGC4j+DdwyDJSozHKO4h8gBb2nFG5iXstXmpYDi29j5BnFkvom5EaOAkR8My/P1INjvd+u4xkIzgcBslkLba9zFodN02dKoaXTGuAodmE/GxMwuGZyYQkByXcN23TNnu3+lzx/NJjZUR+EofHGJmVE3m9zj9bv+slHlScQCshZ29I+MeZiYckf5DwyvQC8KkyeydwGANrDSn2UAS+svD/jBffbXDpCkKe/YGEIzPjG0m+I+Hh6fE9UWbvNA4nwZGYydtPLFeuOgPm5FuiV3n15K1FF4I3boka436BKED8+8S1ycuz207zr5tK/OjEq+rcTzelLzNFDy6cyfriWl0Dv9cJaT8kIYR/5P/5YIbsL7+7P45jOMddnvGxDodnsR1y/YuT895VxI9THj89uRYpoFNjULxKZHA4W6avMgQRDs85BNk8e375fZDLzLyt4b1WBPo9il0Nbn0Rh51ZT6n6hRgFnHDX5feW8bwXy+y9hMOvoFVTkYkcc40Oc6IR5JxlvYIMojoAh/7clLB/ZkGGJA9K+IXpBdnvyuxdxuESJAdmirdCD4kKNkqqoJeES4C9dcDe8xKempmESPK0hE9NT8I3y+xdw+EqZO6gZmgspsSpzvG6IDbri155pOQLp3gSwu0WSEKLPV6q5LupGrlIx9/aumbBFK9Ut5W8ZEu602MNNQvHdrwuMlLuTbQ2RmqSGV0v/HwsmAfSFk1qXMha8TGZ5uAvIGyBDNB6IeD8/1lg3IBIFRj431+5fluEfF5KWB+3maWoDJTEkfgZLRkL3+cn3l94K1DTe50/i4DOl777+ffjXf1NX745/4dXRv92qPK35z7xyNsm9R03Nx/84OLG5v8BHS41hTcYAAA=";
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
        
        public static final byte[] $classHash = new byte[] { -116, 55, 118, -88,
        109, -54, 107, -52, 18, -109, 16, 127, -85, -42, -72, 77, -67, -27, -89,
        60, 120, 11, -124, 6, 88, -116, -88, -85, -29, 8, -64, -77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK0ZC4xUV/XO7DL7hV2WssAWlt9Awm8mVFNtt1Z3p1BGZmXDAqmLdnnz5s7uY9+8N33vzu5AS1v8BNIAGrtFalqMEQPCSqMNodauAsqnQpqUNK1GbYlKLEFC0YjEgnjOvXfmzbz5wMaSzDlv3z3n3vM/511GrpBxtkXmxJWopgfYpiS1A8uVaDjSpVg2jYV0xbbXwNteta4yvPvD/bFWL/FGSL2qGKahqYrea9iMTIhsVAaVoEFZcO3qcNt6UqMi4wrF7mfEu74jbZFZSVPf1KebTB5SsP8Li4LD33288WcVpKGHNGhGN1OYpoZMg9E06yH1CZqIUstuj8VorIdMNCiNdVNLU3RtMxCaRg9psrU+Q2Epi9qrqW3qg0jYZKeS1OJnZl6i+CaIbaVUZlogfqMQP8U0PRjRbNYWIb64RvWY/QR5mlRGyLi4rvQBYXMko0WQ7xhcju+BvFYDMa24otIMS+WAZsQYmenmyGrsXwkEwFqVoKzfzB5VaSjwgjQJkXTF6At2M0sz+oB0nJmCUxhpKbkpEFUnFXVA6aO9jEx103WJJaCq4WZBFkYmu8n4TuCzFpfPcrx15UsP7XrSWGF4iQdkjlFVR/mrganVxbSaxqlFDZUKxvqFkd1K8+h2LyFAPNlFLGiOPnXtC4tbj50RNPcWoVkV3UhV1qvui054e3powQMVKEZ10rQ1DIU8zblXu+RKWzoJ0d6c3REXA5nFY6tPffnZg/Syl9SGiU819VQComqiaiaSmk6tR6lBLYXRWJjUUCMW4uthUgXPEc2g4u2qeNymLEwqdf7KZ/K/wURx2AJNVAXPmhE3M89JhfXz53SSEDIFfqSCEN/HhJy5CngdIa/tYmRlsN9M0GBUT9EhCO8g/Khiqf1ByFtLU5eoZnJT0LbUoJUymAaU4r1QHiTVwVqgoR0AMZKf7HZplL5xyOMBw85UzRiNKjZ4SUZMR5cOSbHC1GPU6lX1XaNhMmn0RR41NRjpNkQrt4sHPD3dXSNyeYdTHcuuHe49KyIOeaXZGJknxBPezBHP3w15o1NmGpjVIF89JlUAylQAytSIJx0I7Q0f4rHjs3mSZbeshy0fTOoKi5tWIk08Hq7fPZyfHwMuH4BSAvvWL+j+6hc3bJ8DbksnhyrBgUjqd+eOU3HC8KRAQvSqDds+vP7K7i2mk0WM+AuSu5ATk3OO21iWqdIYFD9n+4WzlCO9o1v8XiwsNVDzmAJRCQWk1X1GXpK2ZQoeWmNchNShDRQdlzJVqpb1W+aQ84YHwQQETSIe0FguAXmt/Fx38uXfvXXpU7yLZMpqQ0797aasLSeVcbMGnrQTHduvsSgFuj/t6Xr+hSvb1nPDA8XcYgf6EYYghRXIXdP65pknfv/B+/ve8TrOYqQqaWmDkNlprszE2/DPA7//4g8TEl8ghrIcksVgVrYaJPHo+Y5wufG31kiYMS2uKVGdYqjcbJi39MjfdzUKf+vwRljPIovvvIHzfloHefbs4/9u5dt4VOxLjgEdMlHsJjk7t1uWsgnlSG89P+PF08rLEPpQqmxtM+XVh3CDEO7B+7gtlnC41LX2aQRzhLWm8/deu7DwL8cO6gRjT3DkpZbQw5dF7meDEfeYXST31yk5eXLfwcS/vHN8J72kqoc08uatGGydAvUL4qAH2q8dki8jZHzeen4rFX2jLZts092JkHOsOw2cmgPPSI3PtSLyReCAIaahkZZD4f4KIT9fL3EAVyclEd6T9hD+8CBnmcvhfAQLuCEr8HEhw3qE4w8jNVoikWLof37SIphWbD72rINhCJy8NvxIEdt3WVoC8mdQNl26ffi524FdwyLuxGQyt2A4yOUR0wk/cjw/Nw2nzC53CudY/rdXtvziwJZtonM35ffZZUYq8ZN3b50L7LnwZpEqXqmbogA3cqPcn7VpE9p0NtgyCrY8JfHRIjZdUdymXm5TBJ/P2LCK6jRBDcZJJ0OMyA6CQRIQQcKXprk7AZcvXc53CB5mEDqaoejprBJeVKJZdvSdEm/KUSIvm6RYot9oZiA76UIcZASrQcF0U82cAvRTcxvhasWImYl2FWZSvt6CLpxRaqTj7tv3teG9sVU/WuqVib4MAlHO3Y509RgJBd8LnXyKdVL2wuUZD4QGLvaJSJjpOtZN/ePOkTcfna9+x0sqsrlZMDrnM7XlZ2StRWHyN9bk5eWsrPl58eoAs/cT8voaiZtzY8iJvALHCmMsclVEj2gf+GcnJ+gvUzI3IoB0ni885EcP+UuOKn5HmA1ZFepws8kgug2i/0fij+5SBehyvmQqqmtqOt8mtXKjqxJfcodkcX3sMmspBOBA3lg4RbssIYgeYaRCk5nnUm4WyLCVkDc6JF5SQjkEZqEayLJY4vml1cjx2xDf9ZkyumxFsBlE7qO8V3cWk3seHPptQn5ZK/BoKaeUkBtZrkpcxvwF8ba9jNzPIfg6I9Wy0drF/FAVNU2dKkYxndCQLxHyq50S62PTCVkGJKZ31ClTv5qLD/KieiEcLqPyHgTfYqQuo3K7zoXeUUw96NO+EUKON0rsHZt6yOIR+NjHd5cx3y+z9gME3wNv9St2fwi+oXhwlkqRo4ScaBL4+K2xyY0sNyW+PoZQO1BG+IMIfgjRBA2EplfFS8ruh4NPEPLr6RJXjE12ZPEKfOLmGGT/aRnZX0UwAjEDbZCF7yD/Ajj8PCEnPytxy9jkR5ZpEk8qLb/XmVmGnBr1ehkl3kBwBBxgp6L8Q1dmU2NuNkXkx0ZLMc1mgFh/JuTUqMSHxqYZshyUeN/dZcPJMmunERwDfZjJv1hwbHHNG6AWXxLj7Vv7b0wb9V+6IWYN961VDuFHIx9cPj9+xmH+QVyJFxV4VK37uq/wNi/vko4LWZ8/2VXD7z1CWp+WOM1I6P+5ZIHPGnlX80lswyXuLFp170dwDido15/48HaZgZoTdGcGap9OjT5xk8Wj9myJMYRzCiYE7zgMaXcGZMJYfMDyyTwEnwgUZ2BcegzBunRRrTYINXIk4aHLzy0Tee+XWbuA4A8w2KsoRDbHHOHEZwOXLF0sybDsXSHk9E6JnxpbkiHLkxKn7i7JLpVZu4zgr9BymCnul4tolLNQ8CFUTMOZIN4/QLw/SnxmbBoiy2mJj9+dhtfLrN1AcA1qu18zNBZRolTndJ2Qm+Pzbwal6lNK3COKummRe4tcb8rLdjX0G7rv4srFk0tcbU4t+O8PyXd4b0P1lL1r3xMlKXORXhMh1fGUrudeOeQ8+5IWjWtcyxpxASFy6jZom6MDjOCIuPy3OIXHC6kqKPCvCm7gFqcvuL8h26M2sxSVZbsHP6YlZeH/6oz8c8oNX/WaC/wuDWeTHZ8ZPJD47cC5pucbnzn07mudoxf3P5Su+4bvsR0HDv2l+tir/wM+RQDfbRoAAA==";
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
            
            public static final byte[] $classHash = new byte[] { 104, 18, 51,
            -24, -63, -92, -64, 86, 76, 46, -36, -56, -75, -112, 105, 102, 85,
            -74, 125, -121, 74, -32, 60, 85, -106, 40, -49, -78, -78, 116, -16,
            -57 };
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
        
        public static final byte[] $classHash = new byte[] { 12, 4, 106, 4, -72,
        87, -107, 126, 115, 27, -9, 37, 93, 5, -38, 93, 41, -45, -119, 104, -69,
        39, -128, 28, 25, -97, -74, 101, 46, -46, 114, -64 };
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
        
        public static final byte[] $classHash = new byte[] { -65, -107, 78, 67,
        -84, 31, -42, -94, -74, -47, 5, 46, -10, -46, 22, -26, -17, -72, -45,
        -53, 40, 37, 41, -119, -84, -13, -94, 80, 28, 108, -45, 126 };
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
        
        public static final byte[] $classHash = new byte[] { -37, 75, -2, -70,
        -64, -31, 32, 61, 121, 36, -59, -30, 6, -24, -5, 60, 36, 20, 3, 87, 53,
        113, -95, 22, 73, -76, 96, -62, 72, -1, -40, -9 };
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
        
        public static final byte[] $classHash = new byte[] { 84, 51, 45, 38,
        -107, -36, 76, -91, 107, -87, 93, -49, -90, 18, -73, 2, 23, 103, 122,
        78, 82, 93, -2, -38, 96, 40, 68, -94, -84, 63, 36, -75 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
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
        
        public static final byte[] $classHash = new byte[] { 26, -83, -4, -113,
        98, -77, 107, 117, 13, -71, -121, 74, -110, -55, -44, -92, -19, 78, 55,
        1, 69, 7, -30, 115, -110, 67, 125, 111, -6, 17, 101, -93 };
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
        
        public static final byte[] $classHash = new byte[] { -27, 110, -2, -35,
        -111, 69, 91, -123, -16, -43, -11, -54, -48, -78, 124, 97, 44, -47,
        -128, 89, -29, 43, -26, 19, 15, 103, 76, 87, -125, -77, 8, -79 };
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
                
                public static final byte[] $classHash = new byte[] { -94, -37,
                -113, 94, 40, 70, 79, 58, 3, -11, -3, -12, 39, 103, 123, 100, 8,
                -2, -23, -45, -114, 106, -23, -60, 12, -94, 57, -31, 91, -18,
                -66, -115 };
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
                
                public static final byte[] $classHash = new byte[] { -93, 63,
                59, -68, -93, 18, 99, 71, 29, -48, 24, -108, -27, 47, 126, -119,
                -101, 38, 33, -42, 94, 54, 72, -116, -88, 108, -112, 95, 20,
                -17, 81, 26 };
                public static final java.lang.String jlc$CompilerVersion$fabil =
                  "0.3.0";
                public static final long jlc$SourceLastModified$fabil =
                  1525719795000L;
                public static final java.lang.String jlc$ClassType$fabil =
                  "H4sIAAAAAAAAAK1Xa2wUVRQ+O223DwotpRQobSllaQKU3QCGBIsPWCldWaT2gaEE1rszd9uhszPDzF26RSFoYvABxEBBiMIfMAoWMCbEPzbhhw8IxqgxqD9U/pCgSJQYHz8UPffOPqctkmiTuffuveece+55fOd05BYU2RY0xUhU1fxsyKS2v41EQ+EOYtlUCWrEtrtxNyJPKgwdufGG0iCBFIZymeiGrspEi+g2gynhbWQHCeiUBXo6Q62boVTmjO3E7mcgbV6dtKDRNLShPs1gqUvGyD+8KDD8ytbKdwqgohcqVL2LEabKQUNnNMl6oTxO41Fq2asUhSq9MFWnVOmilko0dScSGnovVNlqn05YwqJ2J7UNbQcnrLITJrXEnelNrr6BalsJmRkWql/pqJ9gqhYIqzZrDYM3plJNsbfDbigMQ1FMI31IWBNOvyIgJAba+D6Sl6mophUjMk2zFA6ousJgjpsj82LfOiRA1uI4Zf1G5qpCneAGVDkqaUTvC3QxS9X7kLTISOAtDGonFIpEJSaRB0gfjTCY6abrcI6QqlSYhbMwmO4mE5LQZ7Uun+V469ZjKw88pbfrEnhQZ4XKGte/BJkaXEydNEYtqsvUYSxfGD5CakaflwCQeLqL2KF59+nbD7c0XLzk0Mweh2ZDdBuVWUQ+FZ3yWV1wwYoCrkaJadgqD4W8lwuvdqROWpMmRntNRiI/9KcPL3Z+uGnPGXpTgrIQeGVDS8QxqqbKRtxUNWqtpTq1CKNKCEqprgTFeQiKcR1WdersbojFbMpCUKiJLa8hfqOJYiiCm6gY16oeM9Jrk7B+sU6aANCMH5QCVNeAtP9ngGl3QHpRY7Au0G/EaSCqJegghncAP0osuT+AeWup8mLZMIcCtiUHrITOVKR09p3Ho6YaWgtfaPtRDfP/FZfk2lcOejxo2DmyodAosdFLqYhZ3aFhUrQbmkKtiKwdGA3BtNFjImpKeaTbGK3CLh70dJ0bI3J5hxOr19w+F7niRBznTZmNQbujnuPNHPV8PXrcUNSYSqIaXU/MvN9rdGYNdVHmW4VINhQ3ErbvPnxCOc87PyKZH5FsxJP0B0+E3hLh5bVFHmZuLcdb7zc1wmKGFU+CxyNMUC34hSYYFQOINggo5Qu6tjz65PNNBRjQ5mAh+piT+tzplQWlEK4I5kxErth747fzR3YZ2URj4BuT/2M5ef42ue1pGTJVEB+z4hc2kguR0V0+iWNPKcIiIxi4iDEN7jvy8rg1jYncGkVhmMRtQDR+lAayMtZvGYPZHREnU/hQ5YQMN5ZLQQGnD3SZx7/65PtlotCkkbciB6LRaa052c6FVYi8npq1fbdFKdJ9c7Tj0OFbezcLwyPFvPEu9PExiFlOML0N67lL27/+7ttTX0hZZzEoNi11ByZ/Ujxm6t/458HvDv94zvINPiNyB1N40ZgBDJNf3ZxVbqIQ5aHyZ8X8JRd+PFDp+FvDHcd6FrT8u4Ds/qzVsOfK1t8bhBiPzEtX1oBZMgcPp2Ulr7IsMsT1SD7zef2xj8hxDH1EM1vdSQVAeVLRy5WazmDlf8k8LqNWxMJSIW+xGJdwM4pbQJwt50OTY/e6TO64q0wbL9fZsO4NjLxWG3zwpgM0mbDmMuaOAzQbSU7GLT0T/1Vq8n4gQXEvVIpOgehsI0GwRKV7sdbbwdRmGCbnnefXbadItWbSts6dUjnXuhMqC3C45tR8XebkkBOCaIhZ3EhFWC3aQDr0A1aL/SDtepufTjP5WJ30gFisFCzzxNjMhwXCkBJfLmR4s6oTB8gXMSjELsPH18tEmiYn4GXgNRNRTcXYQmTkvZojIMdRkERP1U/URIgG6NSzwyeUDa8vcUp9VX5hXqMn4mev/vWx/+i1y+PAvjfVEmYvlPC+uWNa2fWiwco6+NrN+hXBget9zp1zXPq5qU+vH7m8tlk+KEFBxpNjurp8ptZ8/5VZFJtSvTvPi40ZL/IPGtCLxSC9NJqa87zowOW4bvDw5SPJjLAyLqwyJeR8aj6dI+wumdZzl7Mn+LABg0PHbjyd/VWp7OfR7neiXRzNchdHsRvOf/F8VG4ySPvOpuZjE7yYD51j38dZjqbmg/f2vuhdzhQ+bGEwyafqKguTKNUE3aYk7uV0CCIvMMpmj9PppPpuOfg+PXV9Xcv0CbqcmWP+E0rxnTtRUTLjRM+XohZneupSLHWxhKblAkLO2mtaNKaKN5Q68GCKSUW9c8AZPccn8aY+hwL3vA4F/xUX5qsVQ9q/LfeC7iFGRdnMonltwuL/9I38MuMPb0n3NVFH0c6NJx9qfe9klby2/tOZw9cDu194tXnu1a3L2/e9qb0cqf7p8dp/ACRAdWmMDgAA";
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
            
            public static final byte[] $classHash = new byte[] { 111, -116,
            -118, 1, -118, 3, 41, -126, -30, 36, -43, 99, -38, 19, 25, 59, 83,
            16, -55, 124, -5, -15, 8, -3, -30, -37, 39, 24, 7, -104, 34, -120 };
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
        
        public static final byte[] $classHash = new byte[] { 126, 36, -14, -90,
        -65, -10, -44, -73, -102, -13, -104, 50, 22, 83, -112, 107, -90, -60,
        42, 114, 79, 1, -70, -91, 121, 110, 127, 95, -101, 38, -112, -100 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1ZDWwUxxWeW/8bB/9iwAHbmMMNhtwJiFqC2wS48ONy1BYG1BoVd703Z2/Y2z1258yZACFJU0hVIUrNXwJULYYk1IX+BFGloSVt0oCoogalP/QHUFuaREBVGiWkagt9b3bu13uHr4qlm7eeeW/me2/ee/Nmd/gGKbBM0hSUe1TNwwbC1PIslnva/B2yadGAT5MtayX0ditj8tv2vPt8oF4ikp+UKbJu6Koia926xchY/6Nyv+zVKfOuWtHWuoaUKCi4VLb6GJHWLIyapDFsaAO9msHEIiPm3z3DO7h3bcUP8kh5FylX9U4mM1XxGTqjUdZFykI01ENNa0EgQANdpFKnNNBJTVXW1I3AaOhdpMpSe3WZRUxqraCWofUjY5UVCVOTrxnrRPgGwDYjCjNMgF9hw48wVfP6VYu1+klhUKVawFpPtpB8PykIanIvMNb6Y1p4+YzexdgP7KUqwDSDskJjIvnrVD3ASEO6RFxj9zJgANGiEGV9RnypfF2GDlJlQ9JkvdfbyUxV7wXWAiMCqzBSl3FSYCoOy8o6uZd2MzIhna/DHgKuEm4WFGFkXDobnwn2rC5tz5J268bnPr3jMX2pLhEXYA5QRUP8xSBUnya0ggapSXWF2oJlLf49cu3p7RIhwDwujdnmObXp5vyZ9WfO2jz3OvC09zxKFdatDPWMfWuSb/qDeQijOGxYKrpCiuZ8VzvESGs0DN5eG58RBz2xwTMrfvGFrcfoNYmUtpFCxdAiIfCqSsUIhVWNmkuoTk2Z0UAbKaF6wMfH20gRPPtVndq97cGgRVkbydd4V6HB/wcTBWEKNFERPKt60Ig9h2XWx5+jYUJIJfxIHiE17xHphWZCquuJFH6AkWXePiNEvT1ahG4A9/bCj8qm0ueFuDVV5X7FCA94LVPxmhGdqcBp99vKA1INrAUaWh6AEf54p4si+ooNLhcYtkExArRHtmCXhMcs7NAgKJYaWoCa3Yq243QbqT69n3tNCXq6Bd7K7eKCnZ6UniOSZQcjCxfdPN593vY4lBVmY+QTNjx7N5PguVfpISOgBlW5R6PLZdz6MgwrDyQqDySqYVfU4zvU9h3uPYUWD7P4pGUw6bywJrOgYYaixOXiGtZweb4QbPo6SCaQL8qmd37xs1/a3gQbFw1vyIctRFZ3evQkck4bPMkQEt1K+bZ3PzyxZ7ORiCNG3CPCe6QkhmdTurlMQ6EBSH+J6Vsa5ZPdpze7JUwtJZD1mAx+CSmkPn2NlDBtjaU8tEaBn4xBG8gaDsXyVCnrM40NiR7uBmOxqbI9Ao2VBpBny890hg/+7s335vBzJJZYy5MycCdlrUnBjJOV87CtTNh+pUkp8P1pX8c3dt/YtoYbHjimOi3oxtYHQSxD9Brm02fXX7x8aehtKbFZjBSFTbUfYjvKlam8A38u+N3GH4YkdiCFxOwT6aAxng/CuHRzAlwmD0RX+U/5tFknr++osPdbgx7beiaZefcJEv0TF5Kt59fequfTuBQ8mRIGTLDZ6a46MfMC05QHEEf0iQuT978hHwTXh2RlqRspzz+EG4TwHZzNbXE/b2eljT2ATZNtrUm8H6uI9NS/GM/QhDN2eYcP1PkeumZHf9wZcY4pDtG/Wk6Kk9nHQh9ITYWvS6Soi1Tw41vW2WoZMhj4QRccwJZPdPrJPSnjqYepfXK0xoNtUnogJC2bHgaJrAPPyI3Ppbbn244DhpiIRloCqXsqpO4/C/oSjlaHsa2Jugh/mMdFpvK2GZvp3JB5+NjCMB9hAcRIiRoKRRjuP19pBtQrFi98VkM5BJu8qu0RB9t3mGoI4qdfHLt0++BX73h2DNp+Z9cmU0eUB8kydn3Cl7yHrxuFVaZkW4VLLH7nxOYfv7B5m312V6WetIv0SOi7v/nvLz37rpxzyOP5mmEn4ApulE/GbVqFNp0AtpxOpPXzBZ3nYNOlzjaVuE2xeThmQ1eIM42D3JN8esApgd11TihqEUUTrO6BCXcK+mUHFO3ZUMCGMlPWLZXqLBqfm4fWODHnU4KaSXMzLDTghKKWI24Iguy4G2DOOTDnHwS94ID783ezXqcDYJzsLUHPpADOX0cHOP/qjKjuA8G5RDLnCtrsgGrt/4MKJ5smaE0KqsJ+TApxK9Y61w4JY0bvtvxDieUlXL5ClG1zBJ2WtHxSwuQTLI/BsOsK1fDE7zQQ73xwIjgMliKaAbetKAbh5ExlOQ/AoScHDwXaj8ySRKpeBCqLu1Ni8UqM5RF3vuX8JpJIuleuTX7Qt+5qrx3LDWnLpnO/uHz43JJmZZdE8uLZdcT1J1WoNTWnlpoUbm/6ypTM2pi6uT6w6sOwqbcE/VGyxyT8bMR+2caYkXamuewCgG8FZ9iY5dDbhE2Ekftsl3Gjy7izlJvuBBwzrsQYnG48gG8nEntF0O+NUgmoVArDkR5NVdJcvlRMdELQF9N9zlmjp7OMbcNmCyMFigZ3A86yQJwDSB6B+O431ICTei2AYi2R+n8u6FAG9bB5YqQiKHJY0IOZFRF7F4ugKhHIeMp77FM+Fj+ppTxH8PUsmu/F5muMjBElirVM5LE0/Yt6DANsozuZwAP4w0QaOCroU7mZAEWeFHTTXU2A/+7is34ri1qHsTnAeHXE1eLVEXbud9IA6pjqrUR67N+C3shNAxS5LujfRueNx7KMDWNzhJFiPAQHxGm32gn3VLg9QwjsniFodU64uUiVoGU5WP6HWcCfxOY4xC5dH5E1K6PJp8C6MpH2Xhb0XG7QUeSsoD/LAforWaD/BJtTjOT12ibf5YTbC4uCp+6/KuirueFGkTOCvpwZt5Q4NnclwL+eBfwb2JwB8OFIZvDg5zWbiPRctaAFuYFHkXybPnt7dH7+ZpaxX2FzDvy8T7b6fEaAOiWePFVnTqpMAhxfATw3Bb2SmyooclnQi6NT5fdZxv6Iza8hTarWolCYDWR0ezgNawaJdPD7gh7JDTaKDAl6aHSw/5Jl7Co2lyBYoXrNlmcmw5JDRPrmLUGv5oYaRf4q6KVRBatdoFzPAv3v2LzDawS2QONvZJ9xgg53mJqXiPTtdkHn5gYdRT4l6Kwc8swHWaDfwuYfAN2kIaOfZoxWqO1rXiXS4WcEfTw36CiyRdDo6HzlduYxF8HOfzH7BQo+X8gUlueJNLRC0AW5YUaR+YLOGxVmV1GWsRJsJMgwzLA/LMQKqAp+BeHlU9LAiPLJScN6gHeRSEfnCNqYm4Yo0iDoxNFpmG2sFpvy+E0PWRzLVFzyCpGer7bp0Tu5oUaR24J+NDrUk7OMNWAzAapOt6qrzC/3ULtS2BWFq376i2yxZeMzvPjG4Tq44t3r8D5efB1SfK/RoavLZo7L8C5+wojvdULu+KHy4vGHVv2Wv1KOf/kp8ZPiYETTkt+QJT0Xhk0aVLlRS+z3ZWGudDPom6QDRBISxO9y2xzTYR9tDvyvJfF2pi6RWOoiJn5lHH5//EeFxSuv8De7YNbGLe5/Hv3ph2+fevb9fbNrO3euO/pai9nuevnIgP5493PNOw/8DzZTTlf9HAAA";
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
        
        public static final byte[] $classHash = new byte[] { -55, -113, 41, 44,
        24, -46, -118, -27, 94, 22, 11, 31, -83, -61, -101, 11, 99, -27, 45, -8,
        24, -32, -117, 6, 113, 31, 39, 88, -59, 71, -100, 76 };
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
        
        public static final byte[] $classHash = new byte[] { -63, -41, -42, 124,
        -76, -37, 92, 16, -82, -33, -89, -17, 127, -87, -83, -45, 37, 115, 47,
        -91, 11, 33, -37, -113, -34, 109, 76, 77, -93, 125, 84, -74 };
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
        
        public static final byte[] $classHash = new byte[] { 89, 8, 15, -106,
        94, 22, -125, 48, 61, -43, -10, -35, -113, -11, 96, 88, 0, 97, 92, 21,
        -66, -99, -27, -82, 0, -72, -1, -39, 75, -79, -122, 5 };
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
    
    public static final byte[] $classHash = new byte[] { -43, -115, -113, -121,
    29, -84, 0, -31, -45, -82, -77, 5, 76, 35, 68, -73, -38, 98, 58, -59, -11,
    -48, 110, -117, -28, -102, -52, 4, 40, 33, 87, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
