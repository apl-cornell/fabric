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
                              $tm611 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled614 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff612 = 1;
                            boolean $doBackoff613 = true;
                            boolean $retry607 = true;
                            boolean $keepReads608 = false;
                            $label605: for (boolean $commit606 = false;
                                            !$commit606; ) {
                                if ($backoffEnabled614) {
                                    if ($doBackoff613) {
                                        if ($backoff612 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff612));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e609) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff612 < 5000)
                                            $backoff612 *= 2;
                                    }
                                    $doBackoff613 = $backoff612 <= 32 ||
                                                      !$doBackoff613;
                                }
                                $commit606 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.EmptySet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 1582296315990362920L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e609) {
                                        throw $e609;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e609) {
                                        throw $e609;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e609) {
                                        throw $e609;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e609) {
                                        throw $e609;
                                    }
                                    catch (final Throwable $e609) {
                                        $tm611.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e609;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e609) {
                                    $commit606 = false;
                                    continue $label605;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e609) {
                                    $commit606 = false;
                                    $retry607 = false;
                                    $keepReads608 = $e609.keepReads;
                                    if ($tm611.checkForStaleObjects()) {
                                        $retry607 = true;
                                        $keepReads608 = false;
                                        continue $label605;
                                    }
                                    fabric.common.TransactionID $currentTid610 =
                                      $tm611.getCurrentTid();
                                    if ($e609.tid ==
                                          null ||
                                          !$e609.tid.isDescendantOf(
                                                       $currentTid610)) {
                                        throw $e609;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e609);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e609) {
                                    $commit606 = false;
                                    fabric.common.TransactionID $currentTid610 =
                                      $tm611.getCurrentTid();
                                    if ($e609.tid.isDescendantOf(
                                                    $currentTid610))
                                        continue $label605;
                                    if ($currentTid610.parent != null) {
                                        $retry607 = false;
                                        throw $e609;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e609) {
                                    $commit606 = false;
                                    if ($tm611.checkForStaleObjects())
                                        continue $label605;
                                    fabric.common.TransactionID $currentTid610 =
                                      $tm611.getCurrentTid();
                                    if ($e609.tid.isDescendantOf(
                                                    $currentTid610)) {
                                        $retry607 = true;
                                    }
                                    else if ($currentTid610.parent != null) {
                                        $retry607 = false;
                                        throw $e609;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e609) {
                                    $commit606 = false;
                                    if ($tm611.checkForStaleObjects())
                                        continue $label605;
                                    $retry607 = false;
                                    throw new fabric.worker.AbortException(
                                            $e609);
                                }
                                finally {
                                    if ($commit606) {
                                        fabric.common.TransactionID
                                          $currentTid610 =
                                          $tm611.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e609) {
                                            $commit606 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e609) {
                                            $commit606 = false;
                                            $retry607 = false;
                                            $keepReads608 = $e609.keepReads;
                                            if ($tm611.checkForStaleObjects()) {
                                                $retry607 = true;
                                                $keepReads608 = false;
                                                continue $label605;
                                            }
                                            if ($e609.tid ==
                                                  null ||
                                                  !$e609.tid.isDescendantOf(
                                                               $currentTid610))
                                                throw $e609;
                                            throw new fabric.worker.
                                                    UserAbortException($e609);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e609) {
                                            $commit606 = false;
                                            $currentTid610 =
                                              $tm611.getCurrentTid();
                                            if ($currentTid610 != null) {
                                                if ($e609.tid.
                                                      equals($currentTid610) ||
                                                      !$e609.tid.
                                                      isDescendantOf(
                                                        $currentTid610)) {
                                                    throw $e609;
                                                }
                                            }
                                        }
                                    } else if ($keepReads608) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit606) {
                                        {  }
                                        if ($retry607) { continue $label605; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -62, -5, 0, -17,
        -10, 28, 48, -35, 10, -18, -69, 126, -17, 84, 1, 85, 32, 123, -46, -33,
        123, 95, -71, -87, -32, 86, -49, -103, 78, -86, -2, 109 };
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
                              $tm621 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled624 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff622 = 1;
                            boolean $doBackoff623 = true;
                            boolean $retry617 = true;
                            boolean $keepReads618 = false;
                            $label615: for (boolean $commit616 = false;
                                            !$commit616; ) {
                                if ($backoffEnabled624) {
                                    if ($doBackoff623) {
                                        if ($backoff622 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff622));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e619) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff622 < 5000)
                                            $backoff622 *= 2;
                                    }
                                    $doBackoff623 = $backoff622 <= 32 ||
                                                      !$doBackoff623;
                                }
                                $commit616 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.EmptyList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 8842843931221139166L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e619) {
                                        throw $e619;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e619) {
                                        throw $e619;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e619) {
                                        throw $e619;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e619) {
                                        throw $e619;
                                    }
                                    catch (final Throwable $e619) {
                                        $tm621.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e619;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e619) {
                                    $commit616 = false;
                                    continue $label615;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e619) {
                                    $commit616 = false;
                                    $retry617 = false;
                                    $keepReads618 = $e619.keepReads;
                                    if ($tm621.checkForStaleObjects()) {
                                        $retry617 = true;
                                        $keepReads618 = false;
                                        continue $label615;
                                    }
                                    fabric.common.TransactionID $currentTid620 =
                                      $tm621.getCurrentTid();
                                    if ($e619.tid ==
                                          null ||
                                          !$e619.tid.isDescendantOf(
                                                       $currentTid620)) {
                                        throw $e619;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e619);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e619) {
                                    $commit616 = false;
                                    fabric.common.TransactionID $currentTid620 =
                                      $tm621.getCurrentTid();
                                    if ($e619.tid.isDescendantOf(
                                                    $currentTid620))
                                        continue $label615;
                                    if ($currentTid620.parent != null) {
                                        $retry617 = false;
                                        throw $e619;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e619) {
                                    $commit616 = false;
                                    if ($tm621.checkForStaleObjects())
                                        continue $label615;
                                    fabric.common.TransactionID $currentTid620 =
                                      $tm621.getCurrentTid();
                                    if ($e619.tid.isDescendantOf(
                                                    $currentTid620)) {
                                        $retry617 = true;
                                    }
                                    else if ($currentTid620.parent != null) {
                                        $retry617 = false;
                                        throw $e619;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e619) {
                                    $commit616 = false;
                                    if ($tm621.checkForStaleObjects())
                                        continue $label615;
                                    $retry617 = false;
                                    throw new fabric.worker.AbortException(
                                            $e619);
                                }
                                finally {
                                    if ($commit616) {
                                        fabric.common.TransactionID
                                          $currentTid620 =
                                          $tm621.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e619) {
                                            $commit616 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e619) {
                                            $commit616 = false;
                                            $retry617 = false;
                                            $keepReads618 = $e619.keepReads;
                                            if ($tm621.checkForStaleObjects()) {
                                                $retry617 = true;
                                                $keepReads618 = false;
                                                continue $label615;
                                            }
                                            if ($e619.tid ==
                                                  null ||
                                                  !$e619.tid.isDescendantOf(
                                                               $currentTid620))
                                                throw $e619;
                                            throw new fabric.worker.
                                                    UserAbortException($e619);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e619) {
                                            $commit616 = false;
                                            $currentTid620 =
                                              $tm621.getCurrentTid();
                                            if ($currentTid620 != null) {
                                                if ($e619.tid.
                                                      equals($currentTid620) ||
                                                      !$e619.tid.
                                                      isDescendantOf(
                                                        $currentTid620)) {
                                                    throw $e619;
                                                }
                                            }
                                        }
                                    } else if ($keepReads618) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit616) {
                                        {  }
                                        if ($retry617) { continue $label615; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 90, 126, -53, 69,
        92, -33, -98, 109, -66, -29, 46, -26, -100, -69, 21, 65, 119, -75, 6,
        102, -54, 42, 14, -68, -127, -30, -95, -83, -26, 58, -106, 95 };
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
                              $tm631 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled634 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff632 = 1;
                            boolean $doBackoff633 = true;
                            boolean $retry627 = true;
                            boolean $keepReads628 = false;
                            $label625: for (boolean $commit626 = false;
                                            !$commit626; ) {
                                if ($backoffEnabled634) {
                                    if ($doBackoff633) {
                                        if ($backoff632 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff632));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e629) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff632 < 5000)
                                            $backoff632 *= 2;
                                    }
                                    $doBackoff633 = $backoff632 <= 32 ||
                                                      !$doBackoff633;
                                }
                                $commit626 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.EmptyMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 6428348081105594320L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e629) {
                                        throw $e629;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e629) {
                                        throw $e629;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e629) {
                                        throw $e629;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e629) {
                                        throw $e629;
                                    }
                                    catch (final Throwable $e629) {
                                        $tm631.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e629;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e629) {
                                    $commit626 = false;
                                    continue $label625;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e629) {
                                    $commit626 = false;
                                    $retry627 = false;
                                    $keepReads628 = $e629.keepReads;
                                    if ($tm631.checkForStaleObjects()) {
                                        $retry627 = true;
                                        $keepReads628 = false;
                                        continue $label625;
                                    }
                                    fabric.common.TransactionID $currentTid630 =
                                      $tm631.getCurrentTid();
                                    if ($e629.tid ==
                                          null ||
                                          !$e629.tid.isDescendantOf(
                                                       $currentTid630)) {
                                        throw $e629;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e629);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e629) {
                                    $commit626 = false;
                                    fabric.common.TransactionID $currentTid630 =
                                      $tm631.getCurrentTid();
                                    if ($e629.tid.isDescendantOf(
                                                    $currentTid630))
                                        continue $label625;
                                    if ($currentTid630.parent != null) {
                                        $retry627 = false;
                                        throw $e629;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e629) {
                                    $commit626 = false;
                                    if ($tm631.checkForStaleObjects())
                                        continue $label625;
                                    fabric.common.TransactionID $currentTid630 =
                                      $tm631.getCurrentTid();
                                    if ($e629.tid.isDescendantOf(
                                                    $currentTid630)) {
                                        $retry627 = true;
                                    }
                                    else if ($currentTid630.parent != null) {
                                        $retry627 = false;
                                        throw $e629;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e629) {
                                    $commit626 = false;
                                    if ($tm631.checkForStaleObjects())
                                        continue $label625;
                                    $retry627 = false;
                                    throw new fabric.worker.AbortException(
                                            $e629);
                                }
                                finally {
                                    if ($commit626) {
                                        fabric.common.TransactionID
                                          $currentTid630 =
                                          $tm631.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e629) {
                                            $commit626 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e629) {
                                            $commit626 = false;
                                            $retry627 = false;
                                            $keepReads628 = $e629.keepReads;
                                            if ($tm631.checkForStaleObjects()) {
                                                $retry627 = true;
                                                $keepReads628 = false;
                                                continue $label625;
                                            }
                                            if ($e629.tid ==
                                                  null ||
                                                  !$e629.tid.isDescendantOf(
                                                               $currentTid630))
                                                throw $e629;
                                            throw new fabric.worker.
                                                    UserAbortException($e629);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e629) {
                                            $commit626 = false;
                                            $currentTid630 =
                                              $tm631.getCurrentTid();
                                            if ($currentTid630 != null) {
                                                if ($e629.tid.
                                                      equals($currentTid630) ||
                                                      !$e629.tid.
                                                      isDescendantOf(
                                                        $currentTid630)) {
                                                    throw $e629;
                                                }
                                            }
                                        }
                                    } else if ($keepReads628) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit626) {
                                        {  }
                                        if ($retry627) { continue $label625; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -79, -41, 91, -66,
        114, 65, -83, -76, -40, 22, 95, -29, 61, 2, -108, 53, 82, -22, -123,
        -86, -79, 67, 33, -16, 92, 102, -61, 60, 28, 65, -113, 81 };
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
                              $tm641 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled644 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff642 = 1;
                            boolean $doBackoff643 = true;
                            boolean $retry637 = true;
                            boolean $keepReads638 = false;
                            $label635: for (boolean $commit636 = false;
                                            !$commit636; ) {
                                if ($backoffEnabled644) {
                                    if ($doBackoff643) {
                                        if ($backoff642 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff642));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e639) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff642 < 5000)
                                            $backoff642 *= 2;
                                    }
                                    $doBackoff643 = $backoff642 <= 32 ||
                                                      !$doBackoff643;
                                }
                                $commit636 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.CopiesList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 2739099268398711800L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e639) {
                                        throw $e639;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e639) {
                                        throw $e639;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e639) {
                                        throw $e639;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e639) {
                                        throw $e639;
                                    }
                                    catch (final Throwable $e639) {
                                        $tm641.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e639;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e639) {
                                    $commit636 = false;
                                    continue $label635;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e639) {
                                    $commit636 = false;
                                    $retry637 = false;
                                    $keepReads638 = $e639.keepReads;
                                    if ($tm641.checkForStaleObjects()) {
                                        $retry637 = true;
                                        $keepReads638 = false;
                                        continue $label635;
                                    }
                                    fabric.common.TransactionID $currentTid640 =
                                      $tm641.getCurrentTid();
                                    if ($e639.tid ==
                                          null ||
                                          !$e639.tid.isDescendantOf(
                                                       $currentTid640)) {
                                        throw $e639;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e639);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e639) {
                                    $commit636 = false;
                                    fabric.common.TransactionID $currentTid640 =
                                      $tm641.getCurrentTid();
                                    if ($e639.tid.isDescendantOf(
                                                    $currentTid640))
                                        continue $label635;
                                    if ($currentTid640.parent != null) {
                                        $retry637 = false;
                                        throw $e639;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e639) {
                                    $commit636 = false;
                                    if ($tm641.checkForStaleObjects())
                                        continue $label635;
                                    fabric.common.TransactionID $currentTid640 =
                                      $tm641.getCurrentTid();
                                    if ($e639.tid.isDescendantOf(
                                                    $currentTid640)) {
                                        $retry637 = true;
                                    }
                                    else if ($currentTid640.parent != null) {
                                        $retry637 = false;
                                        throw $e639;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e639) {
                                    $commit636 = false;
                                    if ($tm641.checkForStaleObjects())
                                        continue $label635;
                                    $retry637 = false;
                                    throw new fabric.worker.AbortException(
                                            $e639);
                                }
                                finally {
                                    if ($commit636) {
                                        fabric.common.TransactionID
                                          $currentTid640 =
                                          $tm641.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e639) {
                                            $commit636 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e639) {
                                            $commit636 = false;
                                            $retry637 = false;
                                            $keepReads638 = $e639.keepReads;
                                            if ($tm641.checkForStaleObjects()) {
                                                $retry637 = true;
                                                $keepReads638 = false;
                                                continue $label635;
                                            }
                                            if ($e639.tid ==
                                                  null ||
                                                  !$e639.tid.isDescendantOf(
                                                               $currentTid640))
                                                throw $e639;
                                            throw new fabric.worker.
                                                    UserAbortException($e639);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e639) {
                                            $commit636 = false;
                                            $currentTid640 =
                                              $tm641.getCurrentTid();
                                            if ($currentTid640 != null) {
                                                if ($e639.tid.
                                                      equals($currentTid640) ||
                                                      !$e639.tid.
                                                      isDescendantOf(
                                                        $currentTid640)) {
                                                    throw $e639;
                                                }
                                            }
                                        }
                                    } else if ($keepReads638) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit636) {
                                        {  }
                                        if ($retry637) { continue $label635; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 90, 126, -53, 69,
        92, -33, -98, 109, -66, -29, 46, -26, -100, -69, 21, 65, 119, -75, 6,
        102, -54, 42, 14, -68, -127, -30, -95, -83, -26, 58, -106, 95 };
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
                              $tm651 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled654 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff652 = 1;
                            boolean $doBackoff653 = true;
                            boolean $retry647 = true;
                            boolean $keepReads648 = false;
                            $label645: for (boolean $commit646 = false;
                                            !$commit646; ) {
                                if ($backoffEnabled654) {
                                    if ($doBackoff653) {
                                        if ($backoff652 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff652));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e649) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff652 < 5000)
                                            $backoff652 *= 2;
                                    }
                                    $doBackoff653 = $backoff652 <= 32 ||
                                                      !$doBackoff653;
                                }
                                $commit646 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.ReverseComparator.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 7207038068494060240L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e649) {
                                        throw $e649;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e649) {
                                        throw $e649;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e649) {
                                        throw $e649;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e649) {
                                        throw $e649;
                                    }
                                    catch (final Throwable $e649) {
                                        $tm651.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e649;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e649) {
                                    $commit646 = false;
                                    continue $label645;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e649) {
                                    $commit646 = false;
                                    $retry647 = false;
                                    $keepReads648 = $e649.keepReads;
                                    if ($tm651.checkForStaleObjects()) {
                                        $retry647 = true;
                                        $keepReads648 = false;
                                        continue $label645;
                                    }
                                    fabric.common.TransactionID $currentTid650 =
                                      $tm651.getCurrentTid();
                                    if ($e649.tid ==
                                          null ||
                                          !$e649.tid.isDescendantOf(
                                                       $currentTid650)) {
                                        throw $e649;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e649);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e649) {
                                    $commit646 = false;
                                    fabric.common.TransactionID $currentTid650 =
                                      $tm651.getCurrentTid();
                                    if ($e649.tid.isDescendantOf(
                                                    $currentTid650))
                                        continue $label645;
                                    if ($currentTid650.parent != null) {
                                        $retry647 = false;
                                        throw $e649;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e649) {
                                    $commit646 = false;
                                    if ($tm651.checkForStaleObjects())
                                        continue $label645;
                                    fabric.common.TransactionID $currentTid650 =
                                      $tm651.getCurrentTid();
                                    if ($e649.tid.isDescendantOf(
                                                    $currentTid650)) {
                                        $retry647 = true;
                                    }
                                    else if ($currentTid650.parent != null) {
                                        $retry647 = false;
                                        throw $e649;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e649) {
                                    $commit646 = false;
                                    if ($tm651.checkForStaleObjects())
                                        continue $label645;
                                    $retry647 = false;
                                    throw new fabric.worker.AbortException(
                                            $e649);
                                }
                                finally {
                                    if ($commit646) {
                                        fabric.common.TransactionID
                                          $currentTid650 =
                                          $tm651.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e649) {
                                            $commit646 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e649) {
                                            $commit646 = false;
                                            $retry647 = false;
                                            $keepReads648 = $e649.keepReads;
                                            if ($tm651.checkForStaleObjects()) {
                                                $retry647 = true;
                                                $keepReads648 = false;
                                                continue $label645;
                                            }
                                            if ($e649.tid ==
                                                  null ||
                                                  !$e649.tid.isDescendantOf(
                                                               $currentTid650))
                                                throw $e649;
                                            throw new fabric.worker.
                                                    UserAbortException($e649);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e649) {
                                            $commit646 = false;
                                            $currentTid650 =
                                              $tm651.getCurrentTid();
                                            if ($currentTid650 != null) {
                                                if ($e649.tid.
                                                      equals($currentTid650) ||
                                                      !$e649.tid.
                                                      isDescendantOf(
                                                        $currentTid650)) {
                                                    throw $e649;
                                                }
                                            }
                                        }
                                    } else if ($keepReads648) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit646) {
                                        {  }
                                        if ($retry647) { continue $label645; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -95, -82, -42, 99,
        -31, 71, 23, 32, 72, 117, 115, -30, -45, -101, -122, 47, 35, -35, 65,
        -115, -98, 5, 87, -30, 29, 35, 1, -95, -82, -106, -22, -86 };
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
            
            public static final byte[] $classHash = new byte[] { 37, 42, 62,
            -41, -28, -11, 115, 55, 15, -82, 35, 89, -4, -97, 122, -57, -81, 41,
            -119, -94, 69, 105, 82, -105, -53, 105, -40, 50, 119, -58, 82,
            106 };
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
                              $tm661 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled664 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff662 = 1;
                            boolean $doBackoff663 = true;
                            boolean $retry657 = true;
                            boolean $keepReads658 = false;
                            $label655: for (boolean $commit656 = false;
                                            !$commit656; ) {
                                if ($backoffEnabled664) {
                                    if ($doBackoff663) {
                                        if ($backoff662 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff662));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e659) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff662 < 5000)
                                            $backoff662 *= 2;
                                    }
                                    $doBackoff663 = $backoff662 <= 32 ||
                                                      !$doBackoff663;
                                }
                                $commit656 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.SingletonSet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 3193687207550431679L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e659) {
                                        throw $e659;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e659) {
                                        throw $e659;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e659) {
                                        throw $e659;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e659) {
                                        throw $e659;
                                    }
                                    catch (final Throwable $e659) {
                                        $tm661.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e659;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e659) {
                                    $commit656 = false;
                                    continue $label655;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e659) {
                                    $commit656 = false;
                                    $retry657 = false;
                                    $keepReads658 = $e659.keepReads;
                                    if ($tm661.checkForStaleObjects()) {
                                        $retry657 = true;
                                        $keepReads658 = false;
                                        continue $label655;
                                    }
                                    fabric.common.TransactionID $currentTid660 =
                                      $tm661.getCurrentTid();
                                    if ($e659.tid ==
                                          null ||
                                          !$e659.tid.isDescendantOf(
                                                       $currentTid660)) {
                                        throw $e659;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e659);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e659) {
                                    $commit656 = false;
                                    fabric.common.TransactionID $currentTid660 =
                                      $tm661.getCurrentTid();
                                    if ($e659.tid.isDescendantOf(
                                                    $currentTid660))
                                        continue $label655;
                                    if ($currentTid660.parent != null) {
                                        $retry657 = false;
                                        throw $e659;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e659) {
                                    $commit656 = false;
                                    if ($tm661.checkForStaleObjects())
                                        continue $label655;
                                    fabric.common.TransactionID $currentTid660 =
                                      $tm661.getCurrentTid();
                                    if ($e659.tid.isDescendantOf(
                                                    $currentTid660)) {
                                        $retry657 = true;
                                    }
                                    else if ($currentTid660.parent != null) {
                                        $retry657 = false;
                                        throw $e659;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e659) {
                                    $commit656 = false;
                                    if ($tm661.checkForStaleObjects())
                                        continue $label655;
                                    $retry657 = false;
                                    throw new fabric.worker.AbortException(
                                            $e659);
                                }
                                finally {
                                    if ($commit656) {
                                        fabric.common.TransactionID
                                          $currentTid660 =
                                          $tm661.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e659) {
                                            $commit656 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e659) {
                                            $commit656 = false;
                                            $retry657 = false;
                                            $keepReads658 = $e659.keepReads;
                                            if ($tm661.checkForStaleObjects()) {
                                                $retry657 = true;
                                                $keepReads658 = false;
                                                continue $label655;
                                            }
                                            if ($e659.tid ==
                                                  null ||
                                                  !$e659.tid.isDescendantOf(
                                                               $currentTid660))
                                                throw $e659;
                                            throw new fabric.worker.
                                                    UserAbortException($e659);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e659) {
                                            $commit656 = false;
                                            $currentTid660 =
                                              $tm661.getCurrentTid();
                                            if ($currentTid660 != null) {
                                                if ($e659.tid.
                                                      equals($currentTid660) ||
                                                      !$e659.tid.
                                                      isDescendantOf(
                                                        $currentTid660)) {
                                                    throw $e659;
                                                }
                                            }
                                        }
                                    } else if ($keepReads658) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit656) {
                                        {  }
                                        if ($retry657) { continue $label655; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -62, -5, 0, -17,
        -10, 28, 48, -35, 10, -18, -69, 126, -17, 84, 1, 85, 32, 123, -46, -33,
        123, 95, -71, -87, -32, 86, -49, -103, 78, -86, -2, 109 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
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
                              $tm671 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled674 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff672 = 1;
                            boolean $doBackoff673 = true;
                            boolean $retry667 = true;
                            boolean $keepReads668 = false;
                            $label665: for (boolean $commit666 = false;
                                            !$commit666; ) {
                                if ($backoffEnabled674) {
                                    if ($doBackoff673) {
                                        if ($backoff672 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff672));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e669) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff672 < 5000)
                                            $backoff672 *= 2;
                                    }
                                    $doBackoff673 = $backoff672 <= 32 ||
                                                      !$doBackoff673;
                                }
                                $commit666 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.SingletonList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 3093736618740652951L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e669) {
                                        throw $e669;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e669) {
                                        throw $e669;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e669) {
                                        throw $e669;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e669) {
                                        throw $e669;
                                    }
                                    catch (final Throwable $e669) {
                                        $tm671.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e669;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e669) {
                                    $commit666 = false;
                                    continue $label665;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e669) {
                                    $commit666 = false;
                                    $retry667 = false;
                                    $keepReads668 = $e669.keepReads;
                                    if ($tm671.checkForStaleObjects()) {
                                        $retry667 = true;
                                        $keepReads668 = false;
                                        continue $label665;
                                    }
                                    fabric.common.TransactionID $currentTid670 =
                                      $tm671.getCurrentTid();
                                    if ($e669.tid ==
                                          null ||
                                          !$e669.tid.isDescendantOf(
                                                       $currentTid670)) {
                                        throw $e669;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e669);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e669) {
                                    $commit666 = false;
                                    fabric.common.TransactionID $currentTid670 =
                                      $tm671.getCurrentTid();
                                    if ($e669.tid.isDescendantOf(
                                                    $currentTid670))
                                        continue $label665;
                                    if ($currentTid670.parent != null) {
                                        $retry667 = false;
                                        throw $e669;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e669) {
                                    $commit666 = false;
                                    if ($tm671.checkForStaleObjects())
                                        continue $label665;
                                    fabric.common.TransactionID $currentTid670 =
                                      $tm671.getCurrentTid();
                                    if ($e669.tid.isDescendantOf(
                                                    $currentTid670)) {
                                        $retry667 = true;
                                    }
                                    else if ($currentTid670.parent != null) {
                                        $retry667 = false;
                                        throw $e669;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e669) {
                                    $commit666 = false;
                                    if ($tm671.checkForStaleObjects())
                                        continue $label665;
                                    $retry667 = false;
                                    throw new fabric.worker.AbortException(
                                            $e669);
                                }
                                finally {
                                    if ($commit666) {
                                        fabric.common.TransactionID
                                          $currentTid670 =
                                          $tm671.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e669) {
                                            $commit666 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e669) {
                                            $commit666 = false;
                                            $retry667 = false;
                                            $keepReads668 = $e669.keepReads;
                                            if ($tm671.checkForStaleObjects()) {
                                                $retry667 = true;
                                                $keepReads668 = false;
                                                continue $label665;
                                            }
                                            if ($e669.tid ==
                                                  null ||
                                                  !$e669.tid.isDescendantOf(
                                                               $currentTid670))
                                                throw $e669;
                                            throw new fabric.worker.
                                                    UserAbortException($e669);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e669) {
                                            $commit666 = false;
                                            $currentTid670 =
                                              $tm671.getCurrentTid();
                                            if ($currentTid670 != null) {
                                                if ($e669.tid.
                                                      equals($currentTid670) ||
                                                      !$e669.tid.
                                                      isDescendantOf(
                                                        $currentTid670)) {
                                                    throw $e669;
                                                }
                                            }
                                        }
                                    } else if ($keepReads668) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit666) {
                                        {  }
                                        if ($retry667) { continue $label665; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 90, 126, -53, 69,
        92, -33, -98, 109, -66, -29, 46, -26, -100, -69, 21, 65, 119, -75, 6,
        102, -54, 42, 14, -68, -127, -30, -95, -83, -26, 58, -106, 95 };
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
            
            public static final byte[] $classHash = new byte[] { -42, -45, -56,
            74, 102, 26, -74, -109, -119, 75, 17, -46, -22, -8, -88, -98, -121,
            116, 28, -41, 62, -26, -88, -62, -71, 87, 92, 19, -89, -10, 28,
            -13 };
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
                              $tm681 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled684 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff682 = 1;
                            boolean $doBackoff683 = true;
                            boolean $retry677 = true;
                            boolean $keepReads678 = false;
                            $label675: for (boolean $commit676 = false;
                                            !$commit676; ) {
                                if ($backoffEnabled684) {
                                    if ($doBackoff683) {
                                        if ($backoff682 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff682));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e679) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff682 < 5000)
                                            $backoff682 *= 2;
                                    }
                                    $doBackoff683 = $backoff682 <= 32 ||
                                                      !$doBackoff683;
                                }
                                $commit676 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.SingletonMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -6979724477215052911L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e679) {
                                        throw $e679;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e679) {
                                        throw $e679;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e679) {
                                        throw $e679;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e679) {
                                        throw $e679;
                                    }
                                    catch (final Throwable $e679) {
                                        $tm681.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e679;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e679) {
                                    $commit676 = false;
                                    continue $label675;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e679) {
                                    $commit676 = false;
                                    $retry677 = false;
                                    $keepReads678 = $e679.keepReads;
                                    if ($tm681.checkForStaleObjects()) {
                                        $retry677 = true;
                                        $keepReads678 = false;
                                        continue $label675;
                                    }
                                    fabric.common.TransactionID $currentTid680 =
                                      $tm681.getCurrentTid();
                                    if ($e679.tid ==
                                          null ||
                                          !$e679.tid.isDescendantOf(
                                                       $currentTid680)) {
                                        throw $e679;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e679);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e679) {
                                    $commit676 = false;
                                    fabric.common.TransactionID $currentTid680 =
                                      $tm681.getCurrentTid();
                                    if ($e679.tid.isDescendantOf(
                                                    $currentTid680))
                                        continue $label675;
                                    if ($currentTid680.parent != null) {
                                        $retry677 = false;
                                        throw $e679;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e679) {
                                    $commit676 = false;
                                    if ($tm681.checkForStaleObjects())
                                        continue $label675;
                                    fabric.common.TransactionID $currentTid680 =
                                      $tm681.getCurrentTid();
                                    if ($e679.tid.isDescendantOf(
                                                    $currentTid680)) {
                                        $retry677 = true;
                                    }
                                    else if ($currentTid680.parent != null) {
                                        $retry677 = false;
                                        throw $e679;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e679) {
                                    $commit676 = false;
                                    if ($tm681.checkForStaleObjects())
                                        continue $label675;
                                    $retry677 = false;
                                    throw new fabric.worker.AbortException(
                                            $e679);
                                }
                                finally {
                                    if ($commit676) {
                                        fabric.common.TransactionID
                                          $currentTid680 =
                                          $tm681.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e679) {
                                            $commit676 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e679) {
                                            $commit676 = false;
                                            $retry677 = false;
                                            $keepReads678 = $e679.keepReads;
                                            if ($tm681.checkForStaleObjects()) {
                                                $retry677 = true;
                                                $keepReads678 = false;
                                                continue $label675;
                                            }
                                            if ($e679.tid ==
                                                  null ||
                                                  !$e679.tid.isDescendantOf(
                                                               $currentTid680))
                                                throw $e679;
                                            throw new fabric.worker.
                                                    UserAbortException($e679);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e679) {
                                            $commit676 = false;
                                            $currentTid680 =
                                              $tm681.getCurrentTid();
                                            if ($currentTid680 != null) {
                                                if ($e679.tid.
                                                      equals($currentTid680) ||
                                                      !$e679.tid.
                                                      isDescendantOf(
                                                        $currentTid680)) {
                                                    throw $e679;
                                                }
                                            }
                                        }
                                    } else if ($keepReads678) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit676) {
                                        {  }
                                        if ($retry677) { continue $label675; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -79, -41, 91, -66,
        114, 65, -83, -76, -40, 22, 95, -29, 61, 2, -108, 53, 82, -22, -123,
        -86, -79, 67, 33, -16, 92, 102, -61, 60, 28, 65, -113, 81 };
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
                              $tm691 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled694 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff692 = 1;
                            boolean $doBackoff693 = true;
                            boolean $retry687 = true;
                            boolean $keepReads688 = false;
                            $label685: for (boolean $commit686 = false;
                                            !$commit686; ) {
                                if ($backoffEnabled694) {
                                    if ($doBackoff693) {
                                        if ($backoff692 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff692));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e689) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff692 < 5000)
                                            $backoff692 *= 2;
                                    }
                                    $doBackoff693 = $backoff692 <= 32 ||
                                                      !$doBackoff693;
                                }
                                $commit686 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableCollection.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 1820017752578914078L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e689) {
                                        throw $e689;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e689) {
                                        throw $e689;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e689) {
                                        throw $e689;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e689) {
                                        throw $e689;
                                    }
                                    catch (final Throwable $e689) {
                                        $tm691.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e689;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e689) {
                                    $commit686 = false;
                                    continue $label685;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e689) {
                                    $commit686 = false;
                                    $retry687 = false;
                                    $keepReads688 = $e689.keepReads;
                                    if ($tm691.checkForStaleObjects()) {
                                        $retry687 = true;
                                        $keepReads688 = false;
                                        continue $label685;
                                    }
                                    fabric.common.TransactionID $currentTid690 =
                                      $tm691.getCurrentTid();
                                    if ($e689.tid ==
                                          null ||
                                          !$e689.tid.isDescendantOf(
                                                       $currentTid690)) {
                                        throw $e689;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e689);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e689) {
                                    $commit686 = false;
                                    fabric.common.TransactionID $currentTid690 =
                                      $tm691.getCurrentTid();
                                    if ($e689.tid.isDescendantOf(
                                                    $currentTid690))
                                        continue $label685;
                                    if ($currentTid690.parent != null) {
                                        $retry687 = false;
                                        throw $e689;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e689) {
                                    $commit686 = false;
                                    if ($tm691.checkForStaleObjects())
                                        continue $label685;
                                    fabric.common.TransactionID $currentTid690 =
                                      $tm691.getCurrentTid();
                                    if ($e689.tid.isDescendantOf(
                                                    $currentTid690)) {
                                        $retry687 = true;
                                    }
                                    else if ($currentTid690.parent != null) {
                                        $retry687 = false;
                                        throw $e689;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e689) {
                                    $commit686 = false;
                                    if ($tm691.checkForStaleObjects())
                                        continue $label685;
                                    $retry687 = false;
                                    throw new fabric.worker.AbortException(
                                            $e689);
                                }
                                finally {
                                    if ($commit686) {
                                        fabric.common.TransactionID
                                          $currentTid690 =
                                          $tm691.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e689) {
                                            $commit686 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e689) {
                                            $commit686 = false;
                                            $retry687 = false;
                                            $keepReads688 = $e689.keepReads;
                                            if ($tm691.checkForStaleObjects()) {
                                                $retry687 = true;
                                                $keepReads688 = false;
                                                continue $label685;
                                            }
                                            if ($e689.tid ==
                                                  null ||
                                                  !$e689.tid.isDescendantOf(
                                                               $currentTid690))
                                                throw $e689;
                                            throw new fabric.worker.
                                                    UserAbortException($e689);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e689) {
                                            $commit686 = false;
                                            $currentTid690 =
                                              $tm691.getCurrentTid();
                                            if ($currentTid690 != null) {
                                                if ($e689.tid.
                                                      equals($currentTid690) ||
                                                      !$e689.tid.
                                                      isDescendantOf(
                                                        $currentTid690)) {
                                                    throw $e689;
                                                }
                                            }
                                        }
                                    } else if ($keepReads688) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit686) {
                                        {  }
                                        if ($retry687) { continue $label685; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -3, -9, 49, -100,
        24, 0, 92, -7, -82, -52, 96, 108, -28, 13, -3, 45, 38, -45, -1, -105,
        -105, 89, -29, 118, -103, -18, -42, -79, 100, 36, -18, -79 };
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
        
        public static final byte[] $classHash = new byte[] { 37, 42, 62, -41,
        -28, -11, 115, 55, 15, -82, 35, 89, -4, -97, 122, -57, -81, 41, -119,
        -94, 69, 105, 82, -105, -53, 105, -40, 50, 119, -58, 82, 106 };
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
                              $tm701 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled704 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff702 = 1;
                            boolean $doBackoff703 = true;
                            boolean $retry697 = true;
                            boolean $keepReads698 = false;
                            $label695: for (boolean $commit696 = false;
                                            !$commit696; ) {
                                if ($backoffEnabled704) {
                                    if ($doBackoff703) {
                                        if ($backoff702 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff702));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e699) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff702 < 5000)
                                            $backoff702 *= 2;
                                    }
                                    $doBackoff703 = $backoff702 <= 32 ||
                                                      !$doBackoff703;
                                }
                                $commit696 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -283967356065247728L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e699) {
                                        throw $e699;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e699) {
                                        throw $e699;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e699) {
                                        throw $e699;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e699) {
                                        throw $e699;
                                    }
                                    catch (final Throwable $e699) {
                                        $tm701.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e699;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e699) {
                                    $commit696 = false;
                                    continue $label695;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e699) {
                                    $commit696 = false;
                                    $retry697 = false;
                                    $keepReads698 = $e699.keepReads;
                                    if ($tm701.checkForStaleObjects()) {
                                        $retry697 = true;
                                        $keepReads698 = false;
                                        continue $label695;
                                    }
                                    fabric.common.TransactionID $currentTid700 =
                                      $tm701.getCurrentTid();
                                    if ($e699.tid ==
                                          null ||
                                          !$e699.tid.isDescendantOf(
                                                       $currentTid700)) {
                                        throw $e699;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e699);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e699) {
                                    $commit696 = false;
                                    fabric.common.TransactionID $currentTid700 =
                                      $tm701.getCurrentTid();
                                    if ($e699.tid.isDescendantOf(
                                                    $currentTid700))
                                        continue $label695;
                                    if ($currentTid700.parent != null) {
                                        $retry697 = false;
                                        throw $e699;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e699) {
                                    $commit696 = false;
                                    if ($tm701.checkForStaleObjects())
                                        continue $label695;
                                    fabric.common.TransactionID $currentTid700 =
                                      $tm701.getCurrentTid();
                                    if ($e699.tid.isDescendantOf(
                                                    $currentTid700)) {
                                        $retry697 = true;
                                    }
                                    else if ($currentTid700.parent != null) {
                                        $retry697 = false;
                                        throw $e699;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e699) {
                                    $commit696 = false;
                                    if ($tm701.checkForStaleObjects())
                                        continue $label695;
                                    $retry697 = false;
                                    throw new fabric.worker.AbortException(
                                            $e699);
                                }
                                finally {
                                    if ($commit696) {
                                        fabric.common.TransactionID
                                          $currentTid700 =
                                          $tm701.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e699) {
                                            $commit696 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e699) {
                                            $commit696 = false;
                                            $retry697 = false;
                                            $keepReads698 = $e699.keepReads;
                                            if ($tm701.checkForStaleObjects()) {
                                                $retry697 = true;
                                                $keepReads698 = false;
                                                continue $label695;
                                            }
                                            if ($e699.tid ==
                                                  null ||
                                                  !$e699.tid.isDescendantOf(
                                                               $currentTid700))
                                                throw $e699;
                                            throw new fabric.worker.
                                                    UserAbortException($e699);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e699) {
                                            $commit696 = false;
                                            $currentTid700 =
                                              $tm701.getCurrentTid();
                                            if ($currentTid700 != null) {
                                                if ($e699.tid.
                                                      equals($currentTid700) ||
                                                      !$e699.tid.
                                                      isDescendantOf(
                                                        $currentTid700)) {
                                                    throw $e699;
                                                }
                                            }
                                        }
                                    } else if ($keepReads698) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit696) {
                                        {  }
                                        if ($retry697) { continue $label695; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -104, -87, 101,
        -21, 35, -66, -114, 24, -107, 99, -103, -88, -127, -10, 37, 58, -21, 25,
        -37, -91, 12, -121, -122, 112, 59, -108, -7, -113, 28, 10, -62, 16 };
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
                              $tm711 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled714 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff712 = 1;
                            boolean $doBackoff713 = true;
                            boolean $retry707 = true;
                            boolean $keepReads708 = false;
                            $label705: for (boolean $commit706 = false;
                                            !$commit706; ) {
                                if ($backoffEnabled714) {
                                    if ($doBackoff713) {
                                        if ($backoff712 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff712));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e709) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff712 < 5000)
                                            $backoff712 *= 2;
                                    }
                                    $doBackoff713 = $backoff712 <= 32 ||
                                                      !$doBackoff713;
                                }
                                $commit706 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableRandomAccessList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -2542308836966382001L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e709) {
                                        throw $e709;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e709) {
                                        throw $e709;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e709) {
                                        throw $e709;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e709) {
                                        throw $e709;
                                    }
                                    catch (final Throwable $e709) {
                                        $tm711.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e709;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e709) {
                                    $commit706 = false;
                                    continue $label705;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e709) {
                                    $commit706 = false;
                                    $retry707 = false;
                                    $keepReads708 = $e709.keepReads;
                                    if ($tm711.checkForStaleObjects()) {
                                        $retry707 = true;
                                        $keepReads708 = false;
                                        continue $label705;
                                    }
                                    fabric.common.TransactionID $currentTid710 =
                                      $tm711.getCurrentTid();
                                    if ($e709.tid ==
                                          null ||
                                          !$e709.tid.isDescendantOf(
                                                       $currentTid710)) {
                                        throw $e709;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e709);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e709) {
                                    $commit706 = false;
                                    fabric.common.TransactionID $currentTid710 =
                                      $tm711.getCurrentTid();
                                    if ($e709.tid.isDescendantOf(
                                                    $currentTid710))
                                        continue $label705;
                                    if ($currentTid710.parent != null) {
                                        $retry707 = false;
                                        throw $e709;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e709) {
                                    $commit706 = false;
                                    if ($tm711.checkForStaleObjects())
                                        continue $label705;
                                    fabric.common.TransactionID $currentTid710 =
                                      $tm711.getCurrentTid();
                                    if ($e709.tid.isDescendantOf(
                                                    $currentTid710)) {
                                        $retry707 = true;
                                    }
                                    else if ($currentTid710.parent != null) {
                                        $retry707 = false;
                                        throw $e709;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e709) {
                                    $commit706 = false;
                                    if ($tm711.checkForStaleObjects())
                                        continue $label705;
                                    $retry707 = false;
                                    throw new fabric.worker.AbortException(
                                            $e709);
                                }
                                finally {
                                    if ($commit706) {
                                        fabric.common.TransactionID
                                          $currentTid710 =
                                          $tm711.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e709) {
                                            $commit706 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e709) {
                                            $commit706 = false;
                                            $retry707 = false;
                                            $keepReads708 = $e709.keepReads;
                                            if ($tm711.checkForStaleObjects()) {
                                                $retry707 = true;
                                                $keepReads708 = false;
                                                continue $label705;
                                            }
                                            if ($e709.tid ==
                                                  null ||
                                                  !$e709.tid.isDescendantOf(
                                                               $currentTid710))
                                                throw $e709;
                                            throw new fabric.worker.
                                                    UserAbortException($e709);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e709) {
                                            $commit706 = false;
                                            $currentTid710 =
                                              $tm711.getCurrentTid();
                                            if ($currentTid710 != null) {
                                                if ($e709.tid.
                                                      equals($currentTid710) ||
                                                      !$e709.tid.
                                                      isDescendantOf(
                                                        $currentTid710)) {
                                                    throw $e709;
                                                }
                                            }
                                        }
                                    } else if ($keepReads708) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit706) {
                                        {  }
                                        if ($retry707) { continue $label705; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -72, -116, -96,
        -95, -11, -110, 32, -27, -3, -116, -18, -105, -74, -106, 70, -40, 42,
        42, -15, -29, 85, 95, 107, -103, -98, 5, -42, -51, -97, 6, -115, 58 };
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
        
        public static final byte[] $classHash = new byte[] { -125, -81, -1, -73,
        92, 63, -100, 53, -116, 123, 98, -85, -75, -35, 82, 4, -123, -5, 126,
        12, -108, 68, 32, -10, 62, 71, 50, 34, -19, 123, -33, -92 };
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
                
                public static final byte[] $classHash = new byte[] { 125, -77,
                40, 103, -75, -29, -72, -63, 43, 73, 70, 107, -16, -61, 86, -5,
                -22, 60, 126, 119, 57, 123, 123, 119, -43, 61, 67, 87, 42, 40,
                120, 19 };
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
                
                public static final byte[] $classHash = new byte[] { -102, 14,
                66, 64, 84, -108, 29, -39, -90, -9, 19, -87, -5, -5, -27, 27,
                55, -109, -98, -36, -116, 73, -89, -9, 104, -102, 126, -101,
                -123, 122, 101, 107 };
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
                                  $tm721 =
                                  fabric.worker.transaction.TransactionManager.
                                  getInstance();
                                boolean
                                  $backoffEnabled724 =
                                  fabric.worker.Worker.
                                    getWorker().config.
                                    txRetryBackoff;
                                int $backoff722 = 1;
                                boolean $doBackoff723 = true;
                                boolean $retry717 = true;
                                boolean $keepReads718 = false;
                                $label715: for (boolean $commit716 = false;
                                                !$commit716; ) {
                                    if ($backoffEnabled724) {
                                        if ($doBackoff723) {
                                            if ($backoff722 > 32) {
                                                while (true) {
                                                    try {
                                                        java.lang.Thread.
                                                          sleep(
                                                            java.lang.Math.
                                                                round(
                                                                  java.lang.Math.
                                                                      random() *
                                                                      $backoff722));
                                                        break;
                                                    }
                                                    catch (java.lang.
                                                             InterruptedException $e719) {
                                                        
                                                    }
                                                }
                                            }
                                            if ($backoff722 < 5000)
                                                $backoff722 *= 2;
                                        }
                                        $doBackoff723 = $backoff722 <= 32 ||
                                                          !$doBackoff723;
                                    }
                                    $commit716 = true;
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().startTransaction();
                                    try {
                                        try {
                                            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                              _Static.
                                              _Proxy.
                                              $instance.
                                              set$serialVersionUID(
                                                (long) 7854390611657943733L);
                                        }
                                        catch (final fabric.worker.
                                                 RetryException $e719) {
                                            throw $e719;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e719) {
                                            throw $e719;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e719) {
                                            throw $e719;
                                        }
                                        catch (final fabric.worker.metrics.
                                                 LockConflictException $e719) {
                                            throw $e719;
                                        }
                                        catch (final Throwable $e719) {
                                            $tm721.getCurrentLog().
                                              checkRetrySignal();
                                            throw $e719;
                                        }
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e719) {
                                        $commit716 = false;
                                        continue $label715;
                                    }
                                    catch (fabric.worker.
                                             TransactionAbortingException $e719) {
                                        $commit716 = false;
                                        $retry717 = false;
                                        $keepReads718 = $e719.keepReads;
                                        if ($tm721.checkForStaleObjects()) {
                                            $retry717 = true;
                                            $keepReads718 = false;
                                            continue $label715;
                                        }
                                        fabric.common.TransactionID
                                          $currentTid720 =
                                          $tm721.getCurrentTid();
                                        if ($e719.tid ==
                                              null ||
                                              !$e719.tid.isDescendantOf(
                                                           $currentTid720)) {
                                            throw $e719;
                                        }
                                        throw new fabric.worker.
                                                UserAbortException($e719);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e719) {
                                        $commit716 = false;
                                        fabric.common.TransactionID
                                          $currentTid720 =
                                          $tm721.getCurrentTid();
                                        if ($e719.tid.isDescendantOf(
                                                        $currentTid720))
                                            continue $label715;
                                        if ($currentTid720.parent != null) {
                                            $retry717 = false;
                                            throw $e719;
                                        }
                                        throw new InternalError(
                                                "Something is broken with " +
                                                    "transaction management. Got a signal to restart a " +
                                                    "different transaction than the one being managed.");
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e719) {
                                        $commit716 = false;
                                        if ($tm721.checkForStaleObjects())
                                            continue $label715;
                                        fabric.common.TransactionID
                                          $currentTid720 =
                                          $tm721.getCurrentTid();
                                        if ($e719.tid.isDescendantOf(
                                                        $currentTid720)) {
                                            $retry717 = true;
                                        }
                                        else if ($currentTid720.parent !=
                                                   null) {
                                            $retry717 = false;
                                            throw $e719;
                                        }
                                        else {
                                            throw new InternalError(
                                                    "Something is broken with transaction " +
                                                        "management. Got a signal for a lock conflict in a different " +
                                                        "transaction than the one being managed.");
                                        }
                                    }
                                    catch (final Throwable $e719) {
                                        $commit716 = false;
                                        if ($tm721.checkForStaleObjects())
                                            continue $label715;
                                        $retry717 = false;
                                        throw new fabric.worker.AbortException(
                                                $e719);
                                    }
                                    finally {
                                        if ($commit716) {
                                            fabric.common.TransactionID
                                              $currentTid720 =
                                              $tm721.getCurrentTid();
                                            try {
                                                fabric.worker.transaction.TransactionManager.
                                                  getInstance().
                                                  commitTransaction();
                                            }
                                            catch (final fabric.worker.
                                                     AbortException $e719) {
                                                $commit716 = false;
                                            }
                                            catch (final fabric.worker.
                                                     TransactionAbortingException $e719) {
                                                $commit716 = false;
                                                $retry717 = false;
                                                $keepReads718 = $e719.keepReads;
                                                if ($tm721.checkForStaleObjects(
                                                             )) {
                                                    $retry717 = true;
                                                    $keepReads718 = false;
                                                    continue $label715;
                                                }
                                                if ($e719.tid ==
                                                      null ||
                                                      !$e719.tid.
                                                      isDescendantOf(
                                                        $currentTid720))
                                                    throw $e719;
                                                throw new fabric.worker.
                                                        UserAbortException(
                                                        $e719);
                                            }
                                            catch (final fabric.worker.
                                                     TransactionRestartingException $e719) {
                                                $commit716 = false;
                                                $currentTid720 =
                                                  $tm721.getCurrentTid();
                                                if ($currentTid720 != null) {
                                                    if ($e719.tid.
                                                          equals(
                                                            $currentTid720) ||
                                                          !$e719.tid.
                                                          isDescendantOf(
                                                            $currentTid720)) {
                                                        throw $e719;
                                                    }
                                                }
                                            }
                                        } else if ($keepReads718) {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                        if (!$commit716) {
                                            {  }
                                            if ($retry717) {
                                                continue $label715;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { -71, -25, -46,
            -37, -14, -109, -3, -7, -115, -40, -106, 54, 4, 68, 101, 76, 72,
            -122, -10, 120, 5, 96, 67, 112, -115, 123, 60, 17, 50, -9, 75,
            -47 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "H4sIAAAAAAAAAK1YfWwUxxWfW9vnDwz+AgIGjDFXKr7uBK0iEUNUOHB8cNQWxqg1Spy5vTl78d7uZncOn0Od0lYtJCqWGhw3qAmpVDekqQNV1bSilaX80Y9EqaIGVUmqNg3/RElKUUurJFRKmr43s3e7tz47RM1JNzM3897Mm9/7nJu5Tqocm3RkaErTo3zMYk60i6YSyV5qOywd16njHIbZQXVRZWLq7QvpNoUoSVKvUsM0NJXqg4bDyZLkMXqcxgzGY/2HEp1HSa2KjN3UGeZEObonb5N2y9THhnSTu4fM2f+RzbHJ797T+NMK0jBAGjSjj1OuqXHT4CzPB0h9lmVTzHZ2p9MsPUCaDMbSfczWqK7dD4SmMUCaHW3IoDxnM+cQc0z9OBI2OzmL2eLMwiSKb4LYdk7lpg3iN0rxc1zTY0nN4Z1JEs5oTE8795EHSGWSVGV0OgSEy5OFW8TEjrEunAfyOg3EtDNUZQWWyhHNSHOyNshRvHHkABAAa3WW8WGzeFSlQWGCNEuRdGoMxfq4rRlDQFpl5uAUTlrn3RSIaiyqjtAhNsjJiiBdr1wCqloBC7JwsixIJnYCnbUGdObT1vUv7pw4YXQbCgmBzGmm6ih/DTC1BZgOsQyzmaEyyVi/KTlFl8+eVggB4mUBYknzi6/c+MKWtueelzSrytD0pI4xlQ+q06klL6+Ob9xRgWLUWKajoSmU3Fxotddd6cxbYO3LizviYrSw+Nyh33755NPsmkLqEiSsmnouC1bVpJpZS9OZfRczmE05SydILTPScbGeINUwTmoGk7M9mYzDeIJU6mIqbIrfAFEGtkCIqmGsGRmzMLYoHxbjvEUIWQlfUkXI0i6inP0bIS1niDL+E04OxIbNLIul9BwbBfOOwZdRWx2Ogd/amrpVNa2xmGOrMTtncA0o5by8PEiqA1pwQycKYlif7nZ5lL5xNBQCYNeqZpqlqANaci1mT68OTtFt6mlmD6r6xGyCtMyeE1ZTi5bugLUKXEKg6dXBGOHnnczt2Xfj4uCL0uKQ14WNk51SPKlNn3iRfiNrprWMRlM6O0itkt/7DG6P9TEOYtejr0UhekUhes2E8tH4+cSPhUmFHeF7xZPq4aQ7LJ3yjGln8yQUEtdeKvjF6WAJIxBhIIjUb+y7e/+9pzsqwIit0UrQK5JGgi7lBaIEjCj4yaDacOrt9y5NjZuec3ESmePzcznRZzuCGNqmytIQE73tN7XTZwdnxyMKxptaCIWcgrFCXGkLnlHiu52FOIhoVCXJIsSA6rhUCF51fNg2R70ZYRtLsGmWZoJgBQQUIXRXn/X4ay+98zmRXArRtsEXlkFRnT4Px80ahC83edgfthkDutcf7T37yPVTRwXwQLG+3IERbOPg2RRc2rS/+fx9f3rjr9N/VDxlcVJt2dpxcPi8uEzTR/AJwfe/+EU/xQnsIVrH3RjRXgwSFh69wRNuPrNEU/mg4TPbnv37RKPUtw4zEj2bbPn4Dbz5lXvIyRfveb9NbBNSMV15AHpkMga2eDvvtm06hnLkv3Zlzbnf0cfB9CGCOdr9TAQlIgAhQoPbBRZbRbstsPZ5bDokWquLFh/MB12YWD1jHIjNPNYav/OaDAlFY8Q91pUJCUeoz0+2P519V+kI/0Yh1QOkUeR0avAjFMIa2MEAZGUn7k4myeKS9dIMK9NJZ9HZVgcdwXds0A28UARjpMZxnbR8aTgAxCoEqRvi+T+IcvoPbv8DXG2xsF2aDxExuEOwrBftBmw2CiArcLiJYzzCqoiTWi2bzXHUvzhpMxQxjqiGjkCNBEruT+wtg32vrWXBf467uZidnnzoo+jEpLQ7WbCsn1Mz+Hlk0SKOXCzOzcMp6xY6RXB0vXVp/FdPjZ+SCb25NP3uM3LZZ1758PfRR6++UCa4V+qmDMCN+YWwweZODqrRDKrni8AL61zhJtJLbv89H/Al1orjZdyN55oZLRaYgLNYXAnIYwrQTSh983j5NfPVSOLi01+fPJ/u+eE2xXWRfaBCt5D1zsUCfN2cAvygKAs9Y796bc2O+MibQxLDtYFjg9Q/Ojjzwl0b1IcVUlG06jm1aClTZ6kt19kMSmnjcIlFtxeBDSOwPQDou0R50Hb7jX6LlgG/rMokGJsDsSRUqoUGf1YHf8bpVsF49wJBiGLzJU62Su4Ickfmi52FGiDiydpfvOFi3HMH3OwDojy0y+2bbvGGkD7CVi6la2q+FLJ6d6NGt68L2uK8eLS4eIya9gizo32QtopGWVqXCMGOLQCSOA6iSI3Gmch/Rcv3Y55wFwXw5cBZC4XqKqJMXHb7C/OAg01mDgyC5Um3//78MPgFH1tg7QQ2HNI2N0VGQ+cMeBVcTSzJ8PfShZsrZyPv3JQeFXzs+Aj/OfPGtSuL11wUBVMl1rfCI4KvxLmPwJK3nRCyvgiDgoLXwPdVQtoecPs8J/H/pzYHW3ZL/E9jm3zBLJpds8BkGZXJsrzllbXd27E5iRku8BMHD5b3HwWHIuzvLyS4sM6MIT4sKHe7yQe7vZxUAPI4/Oo8zig2k/tg821szggGT2JFnlv0NpEDxIXjkIEYhgtc6sHmYPmL9sub+SQRll8u1vkN99wCayJVTUFeU1GIgnCNnnBSG0KyfDkf3QoOFiXKd465/f5P5qPIknD7+MeGKgkpttML3OlJbJ7wHNXDKyD7Ojh4F1EevuH2r38y2ZHlL27/yq3Fl2cWWLuEzVOcLIpohsaTNMV0QfcNcNqlZV+WrrY+e4vPUxlnbbKqzCva/U9Hjf+aTb95YMuyeV7QK+b8y+byXTzfUHPb+f5XZQgr/F9TC0+qTE7X/SWsbxy2bJbRxOVrZUFrie7nAILvTlCjYSeu+zNJcRncVVLgr1/Kq3l55FZBKcn6rTkb/0uc+fdtN8M1h6+Kpxropv3yW1f+/K+zH/7nzGtTt1fuZcnub72Xr7o3bp05sbNp+/sHXv4fmV21guMUAAA=";
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
                              $tm731 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled734 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff732 = 1;
                            boolean $doBackoff733 = true;
                            boolean $retry727 = true;
                            boolean $keepReads728 = false;
                            $label725: for (boolean $commit726 = false;
                                            !$commit726; ) {
                                if ($backoffEnabled734) {
                                    if ($doBackoff733) {
                                        if ($backoff732 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff732));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e729) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff732 < 5000)
                                            $backoff732 *= 2;
                                    }
                                    $doBackoff733 = $backoff732 <= 32 ||
                                                      !$doBackoff733;
                                }
                                $commit726 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -1034234728574286014L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e729) {
                                        throw $e729;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e729) {
                                        throw $e729;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e729) {
                                        throw $e729;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e729) {
                                        throw $e729;
                                    }
                                    catch (final Throwable $e729) {
                                        $tm731.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e729;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e729) {
                                    $commit726 = false;
                                    continue $label725;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e729) {
                                    $commit726 = false;
                                    $retry727 = false;
                                    $keepReads728 = $e729.keepReads;
                                    if ($tm731.checkForStaleObjects()) {
                                        $retry727 = true;
                                        $keepReads728 = false;
                                        continue $label725;
                                    }
                                    fabric.common.TransactionID $currentTid730 =
                                      $tm731.getCurrentTid();
                                    if ($e729.tid ==
                                          null ||
                                          !$e729.tid.isDescendantOf(
                                                       $currentTid730)) {
                                        throw $e729;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e729);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e729) {
                                    $commit726 = false;
                                    fabric.common.TransactionID $currentTid730 =
                                      $tm731.getCurrentTid();
                                    if ($e729.tid.isDescendantOf(
                                                    $currentTid730))
                                        continue $label725;
                                    if ($currentTid730.parent != null) {
                                        $retry727 = false;
                                        throw $e729;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e729) {
                                    $commit726 = false;
                                    if ($tm731.checkForStaleObjects())
                                        continue $label725;
                                    fabric.common.TransactionID $currentTid730 =
                                      $tm731.getCurrentTid();
                                    if ($e729.tid.isDescendantOf(
                                                    $currentTid730)) {
                                        $retry727 = true;
                                    }
                                    else if ($currentTid730.parent != null) {
                                        $retry727 = false;
                                        throw $e729;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e729) {
                                    $commit726 = false;
                                    if ($tm731.checkForStaleObjects())
                                        continue $label725;
                                    $retry727 = false;
                                    throw new fabric.worker.AbortException(
                                            $e729);
                                }
                                finally {
                                    if ($commit726) {
                                        fabric.common.TransactionID
                                          $currentTid730 =
                                          $tm731.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e729) {
                                            $commit726 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e729) {
                                            $commit726 = false;
                                            $retry727 = false;
                                            $keepReads728 = $e729.keepReads;
                                            if ($tm731.checkForStaleObjects()) {
                                                $retry727 = true;
                                                $keepReads728 = false;
                                                continue $label725;
                                            }
                                            if ($e729.tid ==
                                                  null ||
                                                  !$e729.tid.isDescendantOf(
                                                               $currentTid730))
                                                throw $e729;
                                            throw new fabric.worker.
                                                    UserAbortException($e729);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e729) {
                                            $commit726 = false;
                                            $currentTid730 =
                                              $tm731.getCurrentTid();
                                            if ($currentTid730 != null) {
                                                if ($e729.tid.
                                                      equals($currentTid730) ||
                                                      !$e729.tid.
                                                      isDescendantOf(
                                                        $currentTid730)) {
                                                    throw $e729;
                                                }
                                            }
                                        }
                                    } else if ($keepReads728) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit726) {
                                        {  }
                                        if ($retry727) { continue $label725; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 73, 53, 74, 126,
        -101, -40, 68, 1, -12, 74, 57, 78, -84, -65, 125, 26, 31, -117, 116,
        -57, 76, -63, -99, 97, -52, -52, 5, 107, 22, -13, -1, 117 };
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
                              $tm741 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled744 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff742 = 1;
                            boolean $doBackoff743 = true;
                            boolean $retry737 = true;
                            boolean $keepReads738 = false;
                            $label735: for (boolean $commit736 = false;
                                            !$commit736; ) {
                                if ($backoffEnabled744) {
                                    if ($doBackoff743) {
                                        if ($backoff742 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff742));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e739) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff742 < 5000)
                                            $backoff742 *= 2;
                                    }
                                    $doBackoff743 = $backoff742 <= 32 ||
                                                      !$doBackoff743;
                                }
                                $commit736 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableSet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -9215047833775013803L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e739) {
                                        throw $e739;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e739) {
                                        throw $e739;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e739) {
                                        throw $e739;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e739) {
                                        throw $e739;
                                    }
                                    catch (final Throwable $e739) {
                                        $tm741.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e739;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e739) {
                                    $commit736 = false;
                                    continue $label735;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e739) {
                                    $commit736 = false;
                                    $retry737 = false;
                                    $keepReads738 = $e739.keepReads;
                                    if ($tm741.checkForStaleObjects()) {
                                        $retry737 = true;
                                        $keepReads738 = false;
                                        continue $label735;
                                    }
                                    fabric.common.TransactionID $currentTid740 =
                                      $tm741.getCurrentTid();
                                    if ($e739.tid ==
                                          null ||
                                          !$e739.tid.isDescendantOf(
                                                       $currentTid740)) {
                                        throw $e739;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e739);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e739) {
                                    $commit736 = false;
                                    fabric.common.TransactionID $currentTid740 =
                                      $tm741.getCurrentTid();
                                    if ($e739.tid.isDescendantOf(
                                                    $currentTid740))
                                        continue $label735;
                                    if ($currentTid740.parent != null) {
                                        $retry737 = false;
                                        throw $e739;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e739) {
                                    $commit736 = false;
                                    if ($tm741.checkForStaleObjects())
                                        continue $label735;
                                    fabric.common.TransactionID $currentTid740 =
                                      $tm741.getCurrentTid();
                                    if ($e739.tid.isDescendantOf(
                                                    $currentTid740)) {
                                        $retry737 = true;
                                    }
                                    else if ($currentTid740.parent != null) {
                                        $retry737 = false;
                                        throw $e739;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e739) {
                                    $commit736 = false;
                                    if ($tm741.checkForStaleObjects())
                                        continue $label735;
                                    $retry737 = false;
                                    throw new fabric.worker.AbortException(
                                            $e739);
                                }
                                finally {
                                    if ($commit736) {
                                        fabric.common.TransactionID
                                          $currentTid740 =
                                          $tm741.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e739) {
                                            $commit736 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e739) {
                                            $commit736 = false;
                                            $retry737 = false;
                                            $keepReads738 = $e739.keepReads;
                                            if ($tm741.checkForStaleObjects()) {
                                                $retry737 = true;
                                                $keepReads738 = false;
                                                continue $label735;
                                            }
                                            if ($e739.tid ==
                                                  null ||
                                                  !$e739.tid.isDescendantOf(
                                                               $currentTid740))
                                                throw $e739;
                                            throw new fabric.worker.
                                                    UserAbortException($e739);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e739) {
                                            $commit736 = false;
                                            $currentTid740 =
                                              $tm741.getCurrentTid();
                                            if ($currentTid740 != null) {
                                                if ($e739.tid.
                                                      equals($currentTid740) ||
                                                      !$e739.tid.
                                                      isDescendantOf(
                                                        $currentTid740)) {
                                                    throw $e739;
                                                }
                                            }
                                        }
                                    } else if ($keepReads738) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit736) {
                                        {  }
                                        if ($retry737) { continue $label735; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 34, -41, 47, -90,
        69, 100, -126, 70, 45, 108, 2, -11, 113, 114, -104, -47, -7, 43, -3,
        100, -96, -74, -10, -75, 85, -47, 88, 113, -102, 49, -49, -58 };
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
                              $tm751 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled754 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff752 = 1;
                            boolean $doBackoff753 = true;
                            boolean $retry747 = true;
                            boolean $keepReads748 = false;
                            $label745: for (boolean $commit746 = false;
                                            !$commit746; ) {
                                if ($backoffEnabled754) {
                                    if ($doBackoff753) {
                                        if ($backoff752 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff752));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e749) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff752 < 5000)
                                            $backoff752 *= 2;
                                    }
                                    $doBackoff753 = $backoff752 <= 32 ||
                                                      !$doBackoff753;
                                }
                                $commit746 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableSortedMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -8806743815996713206L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e749) {
                                        throw $e749;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e749) {
                                        throw $e749;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e749) {
                                        throw $e749;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e749) {
                                        throw $e749;
                                    }
                                    catch (final Throwable $e749) {
                                        $tm751.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e749;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e749) {
                                    $commit746 = false;
                                    continue $label745;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e749) {
                                    $commit746 = false;
                                    $retry747 = false;
                                    $keepReads748 = $e749.keepReads;
                                    if ($tm751.checkForStaleObjects()) {
                                        $retry747 = true;
                                        $keepReads748 = false;
                                        continue $label745;
                                    }
                                    fabric.common.TransactionID $currentTid750 =
                                      $tm751.getCurrentTid();
                                    if ($e749.tid ==
                                          null ||
                                          !$e749.tid.isDescendantOf(
                                                       $currentTid750)) {
                                        throw $e749;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e749);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e749) {
                                    $commit746 = false;
                                    fabric.common.TransactionID $currentTid750 =
                                      $tm751.getCurrentTid();
                                    if ($e749.tid.isDescendantOf(
                                                    $currentTid750))
                                        continue $label745;
                                    if ($currentTid750.parent != null) {
                                        $retry747 = false;
                                        throw $e749;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e749) {
                                    $commit746 = false;
                                    if ($tm751.checkForStaleObjects())
                                        continue $label745;
                                    fabric.common.TransactionID $currentTid750 =
                                      $tm751.getCurrentTid();
                                    if ($e749.tid.isDescendantOf(
                                                    $currentTid750)) {
                                        $retry747 = true;
                                    }
                                    else if ($currentTid750.parent != null) {
                                        $retry747 = false;
                                        throw $e749;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e749) {
                                    $commit746 = false;
                                    if ($tm751.checkForStaleObjects())
                                        continue $label745;
                                    $retry747 = false;
                                    throw new fabric.worker.AbortException(
                                            $e749);
                                }
                                finally {
                                    if ($commit746) {
                                        fabric.common.TransactionID
                                          $currentTid750 =
                                          $tm751.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e749) {
                                            $commit746 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e749) {
                                            $commit746 = false;
                                            $retry747 = false;
                                            $keepReads748 = $e749.keepReads;
                                            if ($tm751.checkForStaleObjects()) {
                                                $retry747 = true;
                                                $keepReads748 = false;
                                                continue $label745;
                                            }
                                            if ($e749.tid ==
                                                  null ||
                                                  !$e749.tid.isDescendantOf(
                                                               $currentTid750))
                                                throw $e749;
                                            throw new fabric.worker.
                                                    UserAbortException($e749);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e749) {
                                            $commit746 = false;
                                            $currentTid750 =
                                              $tm751.getCurrentTid();
                                            if ($currentTid750 != null) {
                                                if ($e749.tid.
                                                      equals($currentTid750) ||
                                                      !$e749.tid.
                                                      isDescendantOf(
                                                        $currentTid750)) {
                                                    throw $e749;
                                                }
                                            }
                                        }
                                    } else if ($keepReads748) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit746) {
                                        {  }
                                        if ($retry747) { continue $label745; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 97, 78, -97, 64,
        117, -53, -87, -82, -74, 79, -81, -13, -66, -51, 66, -12, -28, 36, 8,
        57, 115, -127, 118, -48, -29, -35, -61, 63, -87, -126, -54, 72 };
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
                              $tm761 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled764 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff762 = 1;
                            boolean $doBackoff763 = true;
                            boolean $retry757 = true;
                            boolean $keepReads758 = false;
                            $label755: for (boolean $commit756 = false;
                                            !$commit756; ) {
                                if ($backoffEnabled764) {
                                    if ($doBackoff763) {
                                        if ($backoff762 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff762));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e759) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff762 < 5000)
                                            $backoff762 *= 2;
                                    }
                                    $doBackoff763 = $backoff762 <= 32 ||
                                                      !$doBackoff763;
                                }
                                $commit756 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Collections.UnmodifiableSortedSet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -4929149591599911165L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e759) {
                                        throw $e759;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e759) {
                                        throw $e759;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e759) {
                                        throw $e759;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e759) {
                                        throw $e759;
                                    }
                                    catch (final Throwable $e759) {
                                        $tm761.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e759;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e759) {
                                    $commit756 = false;
                                    continue $label755;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e759) {
                                    $commit756 = false;
                                    $retry757 = false;
                                    $keepReads758 = $e759.keepReads;
                                    if ($tm761.checkForStaleObjects()) {
                                        $retry757 = true;
                                        $keepReads758 = false;
                                        continue $label755;
                                    }
                                    fabric.common.TransactionID $currentTid760 =
                                      $tm761.getCurrentTid();
                                    if ($e759.tid ==
                                          null ||
                                          !$e759.tid.isDescendantOf(
                                                       $currentTid760)) {
                                        throw $e759;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e759);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e759) {
                                    $commit756 = false;
                                    fabric.common.TransactionID $currentTid760 =
                                      $tm761.getCurrentTid();
                                    if ($e759.tid.isDescendantOf(
                                                    $currentTid760))
                                        continue $label755;
                                    if ($currentTid760.parent != null) {
                                        $retry757 = false;
                                        throw $e759;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e759) {
                                    $commit756 = false;
                                    if ($tm761.checkForStaleObjects())
                                        continue $label755;
                                    fabric.common.TransactionID $currentTid760 =
                                      $tm761.getCurrentTid();
                                    if ($e759.tid.isDescendantOf(
                                                    $currentTid760)) {
                                        $retry757 = true;
                                    }
                                    else if ($currentTid760.parent != null) {
                                        $retry757 = false;
                                        throw $e759;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e759) {
                                    $commit756 = false;
                                    if ($tm761.checkForStaleObjects())
                                        continue $label755;
                                    $retry757 = false;
                                    throw new fabric.worker.AbortException(
                                            $e759);
                                }
                                finally {
                                    if ($commit756) {
                                        fabric.common.TransactionID
                                          $currentTid760 =
                                          $tm761.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e759) {
                                            $commit756 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e759) {
                                            $commit756 = false;
                                            $retry757 = false;
                                            $keepReads758 = $e759.keepReads;
                                            if ($tm761.checkForStaleObjects()) {
                                                $retry757 = true;
                                                $keepReads758 = false;
                                                continue $label755;
                                            }
                                            if ($e759.tid ==
                                                  null ||
                                                  !$e759.tid.isDescendantOf(
                                                               $currentTid760))
                                                throw $e759;
                                            throw new fabric.worker.
                                                    UserAbortException($e759);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e759) {
                                            $commit756 = false;
                                            $currentTid760 =
                                              $tm761.getCurrentTid();
                                            if ($currentTid760 != null) {
                                                if ($e759.tid.
                                                      equals($currentTid760) ||
                                                      !$e759.tid.
                                                      isDescendantOf(
                                                        $currentTid760)) {
                                                    throw $e759;
                                                }
                                            }
                                        }
                                    } else if ($keepReads758) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit756) {
                                        {  }
                                        if ($retry757) { continue $label755; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -92, -18, 91, 39,
        7, 26, 28, 8, -68, -42, 18, -9, 123, 93, 115, 71, 77, 117, -87, -16, 36,
        58, -77, 82, 25, 110, -38, 11, -105, 66, 6, -89 };
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
                        fabric.worker.transaction.TransactionManager $tm771 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled774 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff772 = 1;
                        boolean $doBackoff773 = true;
                        boolean $retry767 = true;
                        boolean $keepReads768 = false;
                        $label765: for (boolean $commit766 = false; !$commit766;
                                        ) {
                            if ($backoffEnabled774) {
                                if ($doBackoff773) {
                                    if ($backoff772 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff772));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e769) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff772 < 5000) $backoff772 *= 2;
                                }
                                $doBackoff773 = $backoff772 <= 32 ||
                                                  !$doBackoff773;
                            }
                            $commit766 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
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
                                               new fabric.util.Collections.
                                                 EmptySet._Impl(
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
                                               new fabric.util.Collections.
                                                 EmptyMap._Impl(
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
                                catch (final fabric.worker.
                                         RetryException $e769) {
                                    throw $e769;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e769) {
                                    throw $e769;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e769) {
                                    throw $e769;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e769) {
                                    throw $e769;
                                }
                                catch (final Throwable $e769) {
                                    $tm771.getCurrentLog().checkRetrySignal();
                                    throw $e769;
                                }
                            }
                            catch (final fabric.worker.RetryException $e769) {
                                $commit766 = false;
                                continue $label765;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e769) {
                                $commit766 = false;
                                $retry767 = false;
                                $keepReads768 = $e769.keepReads;
                                if ($tm771.checkForStaleObjects()) {
                                    $retry767 = true;
                                    $keepReads768 = false;
                                    continue $label765;
                                }
                                fabric.common.TransactionID $currentTid770 =
                                  $tm771.getCurrentTid();
                                if ($e769.tid ==
                                      null ||
                                      !$e769.tid.isDescendantOf(
                                                   $currentTid770)) {
                                    throw $e769;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e769);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e769) {
                                $commit766 = false;
                                fabric.common.TransactionID $currentTid770 =
                                  $tm771.getCurrentTid();
                                if ($e769.tid.isDescendantOf($currentTid770))
                                    continue $label765;
                                if ($currentTid770.parent != null) {
                                    $retry767 = false;
                                    throw $e769;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e769) {
                                $commit766 = false;
                                if ($tm771.checkForStaleObjects())
                                    continue $label765;
                                fabric.common.TransactionID $currentTid770 =
                                  $tm771.getCurrentTid();
                                if ($e769.tid.isDescendantOf($currentTid770)) {
                                    $retry767 = true;
                                }
                                else if ($currentTid770.parent != null) {
                                    $retry767 = false;
                                    throw $e769;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e769) {
                                $commit766 = false;
                                if ($tm771.checkForStaleObjects())
                                    continue $label765;
                                $retry767 = false;
                                throw new fabric.worker.AbortException($e769);
                            }
                            finally {
                                if ($commit766) {
                                    fabric.common.TransactionID $currentTid770 =
                                      $tm771.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e769) {
                                        $commit766 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e769) {
                                        $commit766 = false;
                                        $retry767 = false;
                                        $keepReads768 = $e769.keepReads;
                                        if ($tm771.checkForStaleObjects()) {
                                            $retry767 = true;
                                            $keepReads768 = false;
                                            continue $label765;
                                        }
                                        if ($e769.tid ==
                                              null ||
                                              !$e769.tid.isDescendantOf(
                                                           $currentTid770))
                                            throw $e769;
                                        throw new fabric.worker.
                                                UserAbortException($e769);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e769) {
                                        $commit766 = false;
                                        $currentTid770 = $tm771.getCurrentTid();
                                        if ($currentTid770 != null) {
                                            if ($e769.tid.equals(
                                                            $currentTid770) ||
                                                  !$e769.tid.
                                                  isDescendantOf(
                                                    $currentTid770)) {
                                                throw $e769;
                                            }
                                        }
                                    }
                                } else if ($keepReads768) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit766) {
                                    {  }
                                    if ($retry767) { continue $label765; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 76, -47, -63, 60, -28,
    -118, -112, 16, -114, 98, -108, -16, 110, 11, 122, 89, -85, -125, -117, -14,
    108, 52, 13, -5, -127, 62, -59, -68, -126, 115, -18, 126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
