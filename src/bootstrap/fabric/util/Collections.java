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
                                        fabric.util.Collections.EmptySet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 1582296315990362920L);
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
        
        public static final byte[] $classHash = new byte[] { -20, 55, -11, -99,
        -39, -3, 76, -40, -51, -49, -62, -4, -115, -24, 59, -44, -120, 55, 43,
        85, 42, -101, -4, -13, 70, -37, 100, 50, 123, 108, -2, -31 };
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
                                        fabric.util.Collections.EmptyList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 8842843931221139166L);
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
        
        public static final byte[] $classHash = new byte[] { -73, 125, 7, -87,
        -20, -74, -10, 93, -95, 84, 39, 97, -48, -86, -94, 11, 79, -110, 99,
        112, 57, 32, 39, 68, -11, 37, 93, 61, -85, -58, 20, -43 };
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
                                        fabric.util.Collections.EmptyMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 6428348081105594320L);
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
        
        public static final byte[] $classHash = new byte[] { -94, -128, -58, 12,
        -28, 44, 37, 3, 114, -7, -126, 110, -93, 12, 14, 13, -117, -45, 5, -108,
        -8, 98, 119, -19, -17, -107, 110, -76, -88, 14, -7, 18 };
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
                                        fabric.util.Collections.CopiesList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 2739099268398711800L);
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
        
        public static final byte[] $classHash = new byte[] { -73, 125, 7, -87,
        -20, -74, -10, 93, -95, 84, 39, 97, -48, -86, -94, 11, 79, -110, 99,
        112, 57, 32, 39, 68, -11, 37, 93, 61, -85, -58, 20, -43 };
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
                                        fabric.util.Collections.ReverseComparator.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 7207038068494060240L);
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
        
        public static final byte[] $classHash = new byte[] { 75, -22, -85, 74,
        -113, 23, 47, 7, -32, 61, 41, -82, -8, -31, 92, 84, -116, -24, -62, -97,
        -13, 85, 61, 1, -14, -13, -25, -57, 104, -99, -32, 118 };
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
            
            public static final byte[] $classHash = new byte[] { -43, -42, 42,
            68, 7, -9, 43, 66, -78, -92, -18, 103, -112, 95, 109, 71, 61, -101,
            54, -15, 93, -49, -107, -70, -23, -112, 120, 59, 59, -117, -15,
            -116 };
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
                                        fabric.util.Collections.SingletonSet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 3193687207550431679L);
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
        
        public static final byte[] $classHash = new byte[] { -20, 55, -11, -99,
        -39, -3, 76, -40, -51, -49, -62, -4, -115, -24, 59, -44, -120, 55, 43,
        85, 42, -101, -4, -13, 70, -37, 100, 50, 123, 108, -2, -31 };
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
                                        fabric.util.Collections.SingletonList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 3093736618740652951L);
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
        
        public static final byte[] $classHash = new byte[] { -73, 125, 7, -87,
        -20, -74, -10, 93, -95, 84, 39, 97, -48, -86, -94, 11, 79, -110, 99,
        112, 57, 32, 39, 68, -11, 37, 93, 61, -85, -58, 20, -43 };
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
            
            public static final byte[] $classHash = new byte[] { -101, 23, -30,
            -31, -55, 102, -120, 51, -52, 69, 37, -11, -12, -53, 3, 20, 58, 53,
            -88, 54, 39, -114, 35, 33, 102, 112, -73, -63, -117, -128, 114,
            126 };
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
                                        fabric.util.Collections.SingletonMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -6979724477215052911L);
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
        
        public static final byte[] $classHash = new byte[] { -94, -128, -58, 12,
        -28, 44, 37, 3, 114, -7, -126, 110, -93, 12, 14, 13, -117, -45, 5, -108,
        -8, 98, 119, -19, -17, -107, 110, -76, -88, 14, -7, 18 };
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
                                        fabric.util.Collections.UnmodifiableCollection.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) 1820017752578914078L);
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
        
        public static final byte[] $classHash = new byte[] { 21, -6, -10, 48,
        -4, -3, 103, 6, -2, 101, 123, -89, 114, 46, 67, 62, -126, 23, -41, -49,
        102, 89, 38, 125, 90, 43, 46, -83, -124, 30, 17, -49 };
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
        
        public static final byte[] $classHash = new byte[] { -43, -42, 42, 68,
        7, -9, 43, 66, -78, -92, -18, 103, -112, 95, 109, 71, 61, -101, 54, -15,
        93, -49, -107, -70, -23, -112, 120, 59, 59, -117, -15, -116 };
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
                                        fabric.util.Collections.UnmodifiableList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -283967356065247728L);
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
        
        public static final byte[] $classHash = new byte[] { 5, 100, 4, -41,
        -67, -22, 103, 90, 19, -16, 13, 34, 102, 77, -42, -7, 36, -95, 29, 72,
        20, -32, 32, -97, 18, -121, -28, 78, 34, -95, -54, -88 };
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
                              $tm721 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled724 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
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
                                        fabric.util.Collections.UnmodifiableRandomAccessList.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -2542308836966382001L);
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
                                    catch (final Throwable $e719) {
                                        $tm721.getCurrentLog().checkRetrySignal(
                                                                 );
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
                                    fabric.common.TransactionID $currentTid720 =
                                      $tm721.getCurrentTid();
                                    if ($e719.tid ==
                                          null ||
                                          !$e719.tid.isDescendantOf(
                                                       $currentTid720)) {
                                        throw $e719;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e719);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e719) {
                                    $commit716 = false;
                                    fabric.common.TransactionID $currentTid720 =
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
                                              getInstance().commitTransaction();
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
                                            if ($tm721.checkForStaleObjects()) {
                                                $retry717 = true;
                                                $keepReads718 = false;
                                                continue $label715;
                                            }
                                            if ($e719.tid ==
                                                  null ||
                                                  !$e719.tid.isDescendantOf(
                                                               $currentTid720))
                                                throw $e719;
                                            throw new fabric.worker.
                                                    UserAbortException($e719);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e719) {
                                            $commit716 = false;
                                            $currentTid720 =
                                              $tm721.getCurrentTid();
                                            if ($currentTid720 != null) {
                                                if ($e719.tid.
                                                      equals($currentTid720) ||
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
                                        if ($retry717) { continue $label715; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -95, 92, -86, -116,
        -29, -80, 68, 11, 70, 66, -30, 66, 101, -13, -23, -91, -98, 30, 41,
        -122, -5, -62, 112, 13, 30, 87, 12, 36, -1, 14, 111, 85 };
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
        
        public static final byte[] $classHash = new byte[] { -113, -101, 84, 97,
        37, 110, 96, -3, -55, 6, -17, -101, 24, -36, 100, -114, -5, 100, -12,
        -19, -102, 8, 64, -28, -37, -106, -65, 10, 58, -18, -59, 80 };
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
                
                public static final byte[] $classHash = new byte[] { 51, 103,
                61, 10, 35, 111, 56, -6, 112, -109, 127, 106, 22, 114, 79, 37,
                -70, -113, -51, -86, -9, 125, -107, 89, -124, -25, 91, -24, -72,
                52, 45, -114 };
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
                
                public static final byte[] $classHash = new byte[] { 2, 70, 33,
                49, -32, 9, 101, 8, -98, 104, -64, -62, -126, 52, -92, 98, -88,
                62, 16, 38, 83, 126, -59, -92, -124, 70, -85, -61, 127, 94, 64,
                80 };
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
                                  $tm731 =
                                  fabric.worker.transaction.TransactionManager.
                                  getInstance();
                                boolean
                                  $backoffEnabled734 =
                                  fabric.worker.Worker.
                                    getWorker().config.
                                    txRetryBackoff;
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
                                            fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.
                                              _Static.
                                              _Proxy.
                                              $instance.
                                              set$serialVersionUID(
                                                (long) 7854390611657943733L);
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
                                        catch (final Throwable $e729) {
                                            $tm731.getCurrentLog().
                                              checkRetrySignal();
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
                                        fabric.common.TransactionID
                                          $currentTid730 =
                                          $tm731.getCurrentTid();
                                        if ($e729.tid ==
                                              null ||
                                              !$e729.tid.isDescendantOf(
                                                           $currentTid730)) {
                                            throw $e729;
                                        }
                                        throw new fabric.worker.
                                                UserAbortException($e729);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e729) {
                                        $commit726 = false;
                                        fabric.common.TransactionID
                                          $currentTid730 =
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
                                                  getInstance().
                                                  commitTransaction();
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
                                                if ($tm731.checkForStaleObjects(
                                                             )) {
                                                    $retry727 = true;
                                                    $keepReads728 = false;
                                                    continue $label725;
                                                }
                                                if ($e729.tid ==
                                                      null ||
                                                      !$e729.tid.
                                                      isDescendantOf(
                                                        $currentTid730))
                                                    throw $e729;
                                                throw new fabric.worker.
                                                        UserAbortException(
                                                        $e729);
                                            }
                                            catch (final fabric.worker.
                                                     TransactionRestartingException $e729) {
                                                $commit726 = false;
                                                $currentTid730 =
                                                  $tm731.getCurrentTid();
                                                if ($currentTid730 != null) {
                                                    if ($e729.tid.
                                                          equals(
                                                            $currentTid730) ||
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
                                            if ($retry727) {
                                                continue $label725;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { -45, -125, 54,
            -79, 104, 67, -29, -19, -74, -27, 51, 62, -102, 25, 72, 76, 8, 20,
            12, -109, -34, 20, -21, -13, 46, 86, 74, 73, 52, -94, -112, -98 };
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
                                        fabric.util.Collections.UnmodifiableMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -1034234728574286014L);
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
        
        public static final byte[] $classHash = new byte[] { -66, 105, 112, -10,
        50, -121, 43, -66, 93, -116, -34, 2, 114, 93, 2, 13, -51, -82, 5, 57,
        -111, 108, 102, -54, 119, -57, -4, 76, -104, -114, -59, 36 };
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
                                        fabric.util.Collections.UnmodifiableSet.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -9215047833775013803L);
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
        
        public static final byte[] $classHash = new byte[] { 51, -119, 65, -120,
        0, 39, 92, 12, 102, -29, 45, -48, 8, 65, -23, -24, -33, -83, -7, 95,
        -17, 54, -88, -81, 7, 67, 63, -28, 66, -89, 100, 28 };
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
                                        fabric.util.Collections.UnmodifiableSortedMap.
                                          _Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -8806743815996713206L);
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
        
        public static final byte[] $classHash = new byte[] { 12, -57, -65, -65,
        -4, -94, 31, 115, 96, 8, -119, -102, 93, -84, -85, 108, -111, -34, 47,
        -83, -73, -25, 50, -128, 112, -66, 98, -94, 2, 104, 55, 52 };
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
                              $tm771 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled774 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff772 = 1;
                            boolean $doBackoff773 = true;
                            boolean $retry767 = true;
                            boolean $keepReads768 = false;
                            $label765: for (boolean $commit766 = false;
                                            !$commit766; ) {
                                if ($backoffEnabled774) {
                                    if ($doBackoff773) {
                                        if ($backoff772 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff772));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e769) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff772 < 5000)
                                            $backoff772 *= 2;
                                    }
                                    $doBackoff773 = $backoff772 <= 32 ||
                                                      !$doBackoff773;
                                }
                                $commit766 = true;
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
                                    catch (final Throwable $e769) {
                                        $tm771.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e769;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e769) {
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
                                    if ($e769.tid.isDescendantOf(
                                                    $currentTid770))
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
                                catch (final Throwable $e769) {
                                    $commit766 = false;
                                    if ($tm771.checkForStaleObjects())
                                        continue $label765;
                                    $retry767 = false;
                                    throw new fabric.worker.AbortException(
                                            $e769);
                                }
                                finally {
                                    if ($commit766) {
                                        fabric.common.TransactionID
                                          $currentTid770 =
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
                                            $currentTid770 =
                                              $tm771.getCurrentTid();
                                            if ($currentTid770 != null) {
                                                if ($e769.tid.
                                                      equals($currentTid770) ||
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
        
        public static final byte[] $classHash = new byte[] { -82, -56, 28, 43,
        -70, 89, 94, 15, 46, -4, -57, 73, -45, 64, -97, -23, -18, 7, -79, 35,
        -34, 22, -112, -10, 49, -83, -76, -100, 31, -15, -7, 54 };
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
                        fabric.worker.transaction.TransactionManager $tm781 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled784 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff782 = 1;
                        boolean $doBackoff783 = true;
                        boolean $retry777 = true;
                        boolean $keepReads778 = false;
                        $label775: for (boolean $commit776 = false; !$commit776;
                                        ) {
                            if ($backoffEnabled784) {
                                if ($doBackoff783) {
                                    if ($backoff782 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff782));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e779) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff782 < 5000) $backoff782 *= 2;
                                }
                                $doBackoff783 = $backoff782 <= 32 ||
                                                  !$doBackoff783;
                            }
                            $commit776 = true;
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
                                         RetryException $e779) {
                                    throw $e779;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e779) {
                                    throw $e779;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e779) {
                                    throw $e779;
                                }
                                catch (final Throwable $e779) {
                                    $tm781.getCurrentLog().checkRetrySignal();
                                    throw $e779;
                                }
                            }
                            catch (final fabric.worker.RetryException $e779) {
                                $commit776 = false;
                                continue $label775;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e779) {
                                $commit776 = false;
                                $retry777 = false;
                                $keepReads778 = $e779.keepReads;
                                if ($tm781.checkForStaleObjects()) {
                                    $retry777 = true;
                                    $keepReads778 = false;
                                    continue $label775;
                                }
                                fabric.common.TransactionID $currentTid780 =
                                  $tm781.getCurrentTid();
                                if ($e779.tid ==
                                      null ||
                                      !$e779.tid.isDescendantOf(
                                                   $currentTid780)) {
                                    throw $e779;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e779);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e779) {
                                $commit776 = false;
                                fabric.common.TransactionID $currentTid780 =
                                  $tm781.getCurrentTid();
                                if ($e779.tid.isDescendantOf($currentTid780))
                                    continue $label775;
                                if ($currentTid780.parent != null) {
                                    $retry777 = false;
                                    throw $e779;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e779) {
                                $commit776 = false;
                                if ($tm781.checkForStaleObjects())
                                    continue $label775;
                                $retry777 = false;
                                throw new fabric.worker.AbortException($e779);
                            }
                            finally {
                                if ($commit776) {
                                    fabric.common.TransactionID $currentTid780 =
                                      $tm781.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e779) {
                                        $commit776 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e779) {
                                        $commit776 = false;
                                        $retry777 = false;
                                        $keepReads778 = $e779.keepReads;
                                        if ($tm781.checkForStaleObjects()) {
                                            $retry777 = true;
                                            $keepReads778 = false;
                                            continue $label775;
                                        }
                                        if ($e779.tid ==
                                              null ||
                                              !$e779.tid.isDescendantOf(
                                                           $currentTid780))
                                            throw $e779;
                                        throw new fabric.worker.
                                                UserAbortException($e779);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e779) {
                                        $commit776 = false;
                                        $currentTid780 = $tm781.getCurrentTid();
                                        if ($currentTid780 != null) {
                                            if ($e779.tid.equals(
                                                            $currentTid780) ||
                                                  !$e779.tid.
                                                  isDescendantOf(
                                                    $currentTid780)) {
                                                throw $e779;
                                            }
                                        }
                                    }
                                } else if ($keepReads778) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit776) {
                                    {  }
                                    if ($retry777) { continue $label775; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -78, 86, -26, 76, -37,
    64, 112, -7, -36, -21, -128, -72, 66, -10, -83, 43, -114, 68, -114, -48,
    -36, -76, -123, -108, 24, 101, -77, -6, -25, -94, -93, -71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
