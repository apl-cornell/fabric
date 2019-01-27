package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * This class contains various static utility methods performing operations on
 * arrays, and a method to provide a List "view" of an array to facilitate
 * using arrays with Collection-based APIs. All methods throw a
 * {@link NullPointerException} if the parameter array is null.
 * <p>
 *
 * Implementations may use their own algorithms, but must obey the general
 * properties; for example, the sort must be stable and n*log(n) complexity.
 * Sun's implementation of sort, and therefore ours, is a tuned quicksort,
 * adapted from Jon L. Bentley and M. Douglas McIlroy's "Engineering a Sort
 * Function", Software-Practice and Experience, Vol. 23(11) P. 1249-1265
 * (November 1993). This algorithm offers n*log(n) performance on many data
 * sets that cause other quicksorts to degrade to quadratic performance.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Comparable
 * @see Comparator
 * @since 1.2
 * @status updated to 1.4
 */
public interface Arrays extends fabric.lang.Object {
    /**
   * Inner class used by {@link #asList(Object[])} to provide a list interface
   * to an array. The name, though it clashes with java.util.ArrayList, is
   * Sun's choice for Serialization purposes. Element addition and removal
   * is prohibited, but values can be modified.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   * @status updated to 1.4
   */
    public static interface ArrayList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        public fabric.lang.arrays.ObjectArray get$a();
        
        public fabric.lang.arrays.ObjectArray set$a(fabric.lang.arrays.ObjectArray val);
        
        /**
     * Construct a list view of the array.
     * @param a the array to view
     * @throws NullPointerException if a is null
     */
        public ArrayList fabric$util$Arrays$ArrayList$(fabric.lang.arrays.ObjectArray a);
        
        /**
     * Returns the object at the specified index in
     * the array.
     *
     * @param index The index to retrieve an object from.
     * @return The object at the array index specified.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Returns the size of the array.
     *
     * @return The size.
     */
        public int size();
        
        /**
     * Replaces the object at the specified index
     * with the supplied element.
     *
     * @param index The index at which to place the new object.
     * @param element The new object.
     * @return The object replaced by this operation.
     */
        public fabric.lang.Object set(int index, fabric.lang.Object element);
        
        /**
     * Returns true if the array contains the
     * supplied object.
     *
     * @param o The object to look for.
     * @return True if the object was found.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * Returns the first index at which the
     * object, o, occurs in the array.
     *
     * @param o The object to search for.
     * @return The first relevant index.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Returns the last index at which the
     * object, o, occurs in the array.
     *
     * @param o The object to search for.
     * @return The last relevant index.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * Transforms the list into an array of
     * objects, by simplying cloning the array
     * wrapped by this list.
     *
     * @return A clone of the internal array.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Copies the objects from this list into
     * the supplied array.  The supplied array
     * is shrunk or enlarged to the size of the
     * internal array, and filled with its objects.
     *
     * @param array The array to fill with the objects in this list.
     * @return The array containing the objects in this list,
     *         which may or may not be == to array.
     */
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray array);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements ArrayList {
            public fabric.lang.arrays.ObjectArray get$a() {
                return ((fabric.util.Arrays.ArrayList._Impl) fetch()).get$a();
            }
            
            public fabric.lang.arrays.ObjectArray set$a(
              fabric.lang.arrays.ObjectArray val) {
                return ((fabric.util.Arrays.ArrayList._Impl) fetch()).set$a(
                                                                        val);
            }
            
            public fabric.util.Arrays.ArrayList fabric$util$Arrays$ArrayList$(
              fabric.lang.arrays.ObjectArray arg1) {
                return ((fabric.util.Arrays.ArrayList) fetch()).
                  fabric$util$Arrays$ArrayList$(arg1);
            }
            
            public _Proxy(ArrayList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements ArrayList {
            public fabric.lang.arrays.ObjectArray get$a() { return this.a; }
            
            public fabric.lang.arrays.ObjectArray set$a(
              fabric.lang.arrays.ObjectArray val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.a = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.arrays.ObjectArray a;
            
            /**
     * Construct a list view of the array.
     * @param a the array to view
     * @throws NullPointerException if a is null
     */
            public ArrayList fabric$util$Arrays$ArrayList$(
              fabric.lang.arrays.ObjectArray a) {
                if (fabric.lang.Object._Proxy.idEquals(a, null))
                    throw new java.lang.NullPointerException();
                this.set$a(a);
                fabric$util$AbstractList$();
                return (ArrayList) this.$getProxy();
            }
            
            /**
     * Returns the object at the specified index in
     * the array.
     *
     * @param index The index to retrieve an object from.
     * @return The object at the array index specified.
     */
            public fabric.lang.Object get(int index) {
                return (fabric.lang.Object) this.get$a().get(index);
            }
            
            /**
     * Returns the size of the array.
     *
     * @return The size.
     */
            public int size() { return this.get$a().get$length(); }
            
            /**
     * Replaces the object at the specified index
     * with the supplied element.
     *
     * @param index The index at which to place the new object.
     * @param element The new object.
     * @return The object replaced by this operation.
     */
            public fabric.lang.Object set(int index,
                                          fabric.lang.Object element) {
                fabric.lang.Object old = (fabric.lang.Object)
                                           this.get$a().get(index);
                this.get$a().set(index, element);
                return old;
            }
            
            /**
     * Returns true if the array contains the
     * supplied object.
     *
     * @param o The object to look for.
     * @return True if the object was found.
     */
            public boolean contains(fabric.lang.Object o) {
                return lastIndexOf(o) >= 0;
            }
            
            /**
     * Returns the first index at which the
     * object, o, occurs in the array.
     *
     * @param o The object to search for.
     * @return The first relevant index.
     */
            public int indexOf(fabric.lang.Object o) {
                int size = this.get$a().get$length();
                for (int i = 0; i < size; i++)
                    if (fabric.util.Arrays.ArrayList._Impl.
                          equals(o, (fabric.lang.Object) this.get$a().get(i)))
                        return i;
                return -1;
            }
            
            /**
     * Returns the last index at which the
     * object, o, occurs in the array.
     *
     * @param o The object to search for.
     * @return The last relevant index.
     */
            public int lastIndexOf(fabric.lang.Object o) {
                int i = this.get$a().get$length();
                while (--i >= 0)
                    if (fabric.util.Arrays.ArrayList._Impl.
                          equals(o, (fabric.lang.Object) this.get$a().get(i)))
                        return i;
                return -1;
            }
            
            /**
     * Transforms the list into an array of
     * objects, by simplying cloning the array
     * wrapped by this list.
     *
     * @return A clone of the internal array.
     */
            public fabric.lang.arrays.ObjectArray toArray() {
                fabric.lang.arrays.ObjectArray result =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(
                      this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          fabric.lang.Object._Proxy.class,
                                          this.get$a().get$length()).$getProxy(
                                                                       );
                for (int i = 0; i < this.get$a().get$length(); i++)
                    result.set(i, (fabric.lang.Object) this.get$a().get(i));
                return result;
            }
            
            /**
     * Copies the objects from this list into
     * the supplied array.  The supplied array
     * is shrunk or enlarged to the size of the
     * internal array, and filled with its objects.
     *
     * @param array The array to fill with the objects in this list.
     * @return The array containing the objects in this list,
     *         which may or may not be == to array.
     */
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray array) {
                int size = this.get$a().get$length();
                if (array.get$length() < size) array.setLength((int) size);
                else if (array.get$length() > size) array.set(size, null);
                fabric.util.Arrays._Impl.arraycopy(this.get$a(), 0, array, 0,
                                                   size);
                return array;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (ArrayList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Arrays.ArrayList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.a, refTypes, out, intraStoreRefs,
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
                this.a = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Arrays.ArrayList._Impl src =
                  (fabric.util.Arrays.ArrayList._Impl) other;
                this.a = src.a;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Arrays.ArrayList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Arrays.ArrayList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Arrays.ArrayList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Arrays.ArrayList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Arrays.ArrayList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Arrays.ArrayList._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Arrays.ArrayList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Arrays.
                      ArrayList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.Arrays.ArrayList._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Arrays.ArrayList._Static._Impl.class);
                    $instance = (fabric.util.Arrays.ArrayList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Arrays.ArrayList._Static {
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
                    return new fabric.util.Arrays.ArrayList._Static._Proxy(
                             this);
                }
                
                private void $init() {
                    {
                        {
                            fabric.worker.transaction.TransactionManager
                              $tm601 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled604 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff602 = 1;
                            boolean $doBackoff603 = true;
                            boolean $retry597 = true;
                            boolean $keepReads598 = false;
                            $label595: for (boolean $commit596 = false;
                                            !$commit596; ) {
                                if ($backoffEnabled604) {
                                    if ($doBackoff603) {
                                        if ($backoff602 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff602));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e599) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff602 < 5000)
                                            $backoff602 *= 2;
                                    }
                                    $doBackoff603 = $backoff602 <= 32 ||
                                                      !$doBackoff603;
                                }
                                $commit596 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    try {
                                        fabric.util.Arrays.ArrayList._Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -2764017481108945198L);
                                    }
                                    catch (final fabric.worker.
                                             RetryException $e599) {
                                        throw $e599;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e599) {
                                        throw $e599;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e599) {
                                        throw $e599;
                                    }
                                    catch (final fabric.worker.metrics.
                                             LockConflictException $e599) {
                                        throw $e599;
                                    }
                                    catch (final Throwable $e599) {
                                        $tm601.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e599;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e599) {
                                    $commit596 = false;
                                    continue $label595;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e599) {
                                    $commit596 = false;
                                    $retry597 = false;
                                    $keepReads598 = $e599.keepReads;
                                    if ($tm601.checkForStaleObjects()) {
                                        $retry597 = true;
                                        $keepReads598 = false;
                                        continue $label595;
                                    }
                                    fabric.common.TransactionID $currentTid600 =
                                      $tm601.getCurrentTid();
                                    if ($e599.tid ==
                                          null ||
                                          !$e599.tid.isDescendantOf(
                                                       $currentTid600)) {
                                        throw $e599;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e599);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e599) {
                                    $commit596 = false;
                                    fabric.common.TransactionID $currentTid600 =
                                      $tm601.getCurrentTid();
                                    if ($e599.tid.isDescendantOf(
                                                    $currentTid600))
                                        continue $label595;
                                    if ($currentTid600.parent != null) {
                                        $retry597 = false;
                                        throw $e599;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e599) {
                                    $commit596 = false;
                                    if ($tm601.checkForStaleObjects())
                                        continue $label595;
                                    fabric.common.TransactionID $currentTid600 =
                                      $tm601.getCurrentTid();
                                    if ($e599.tid.isDescendantOf(
                                                    $currentTid600)) {
                                        $retry597 = true;
                                    }
                                    else if ($currentTid600.parent != null) {
                                        $retry597 = false;
                                        throw $e599;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e599) {
                                    $commit596 = false;
                                    if ($tm601.checkForStaleObjects())
                                        continue $label595;
                                    $retry597 = false;
                                    throw new fabric.worker.AbortException(
                                            $e599);
                                }
                                finally {
                                    if ($commit596) {
                                        fabric.common.TransactionID
                                          $currentTid600 =
                                          $tm601.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e599) {
                                            $commit596 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e599) {
                                            $commit596 = false;
                                            $retry597 = false;
                                            $keepReads598 = $e599.keepReads;
                                            if ($tm601.checkForStaleObjects()) {
                                                $retry597 = true;
                                                $keepReads598 = false;
                                                continue $label595;
                                            }
                                            if ($e599.tid ==
                                                  null ||
                                                  !$e599.tid.isDescendantOf(
                                                               $currentTid600))
                                                throw $e599;
                                            throw new fabric.worker.
                                                    UserAbortException($e599);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e599) {
                                            $commit596 = false;
                                            $currentTid600 =
                                              $tm601.getCurrentTid();
                                            if ($currentTid600 != null) {
                                                if ($e599.tid.
                                                      equals($currentTid600) ||
                                                      !$e599.tid.
                                                      isDescendantOf(
                                                        $currentTid600)) {
                                                    throw $e599;
                                                }
                                            }
                                        }
                                    } else if ($keepReads598) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit596) {
                                        {  }
                                        if ($retry597) { continue $label595; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -110, 72, 107, -5,
        -20, 31, 111, -104, -46, -99, 34, 48, -78, 71, -67, -110, 12, 71, -1,
        102, 77, 123, -50, 38, -82, 83, 40, -34, -49, -91, -112, -42 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260588000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Arrays {
        public static int binarySearch(fabric.lang.arrays.byteArray arg1,
                                       byte arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.charArray arg1,
                                       char arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.shortArray arg1,
                                       short arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.intArray arg1,
                                       int arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.longArray arg1,
                                       long arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.floatArray arg1,
                                       float arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.doubleArray arg1,
                                       double arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.ObjectArray arg1,
                                       fabric.lang.Object arg2) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2);
        }
        
        public static int binarySearch(fabric.lang.arrays.ObjectArray arg1,
                                       fabric.lang.Object arg2,
                                       fabric.util.Comparator arg3) {
            return fabric.util.Arrays._Impl.binarySearch(arg1, arg2, arg3);
        }
        
        public static boolean equals(fabric.lang.arrays.booleanArray arg1,
                                     fabric.lang.arrays.booleanArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.byteArray arg1,
                                     fabric.lang.arrays.byteArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.charArray arg1,
                                     fabric.lang.arrays.charArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.shortArray arg1,
                                     fabric.lang.arrays.shortArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.intArray arg1,
                                     fabric.lang.arrays.intArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.longArray arg1,
                                     fabric.lang.arrays.longArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.floatArray arg1,
                                     fabric.lang.arrays.floatArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.doubleArray arg1,
                                     fabric.lang.arrays.doubleArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static boolean equals(fabric.lang.arrays.ObjectArray arg1,
                                     fabric.lang.arrays.ObjectArray arg2) {
            return fabric.util.Arrays._Impl.equals(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.booleanArray arg1,
                                boolean arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.booleanArray arg1, int arg2,
                                int arg3, boolean arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.byteArray arg1, byte arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.byteArray arg1, int arg2,
                                int arg3, byte arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.charArray arg1, char arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.charArray arg1, int arg2,
                                int arg3, char arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.shortArray arg1,
                                short arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.shortArray arg1, int arg2,
                                int arg3, short arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.intArray arg1, int arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.intArray arg1, int arg2,
                                int arg3, int arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.longArray arg1, long arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.longArray arg1, int arg2,
                                int arg3, long arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.floatArray arg1,
                                float arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.floatArray arg1, int arg2,
                                int arg3, float arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.doubleArray arg1,
                                double arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.doubleArray arg1, int arg2,
                                int arg3, double arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void fill(fabric.lang.arrays.ObjectArray arg1,
                                fabric.lang.Object arg2) {
            fabric.util.Arrays._Impl.fill(arg1, arg2);
        }
        
        public static void fill(fabric.lang.arrays.ObjectArray arg1, int arg2,
                                int arg3, fabric.lang.Object arg4) {
            fabric.util.Arrays._Impl.fill(arg1, arg2, arg3, arg4);
        }
        
        public static void sort(fabric.lang.arrays.byteArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.byteArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.charArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.charArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.shortArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.shortArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.intArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.intArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.longArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.longArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.floatArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.floatArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.doubleArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.doubleArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.ObjectArray arg1) {
            fabric.util.Arrays._Impl.sort(arg1);
        }
        
        public static void sort(fabric.lang.arrays.ObjectArray arg1,
                                fabric.util.Comparator arg2) {
            fabric.util.Arrays._Impl.sort(arg1, arg2);
        }
        
        public static void sort(fabric.lang.arrays.ObjectArray arg1, int arg2,
                                int arg3) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3);
        }
        
        public static void sort(fabric.lang.arrays.ObjectArray arg1, int arg2,
                                int arg3, fabric.util.Comparator arg4) {
            fabric.util.Arrays._Impl.sort(arg1, arg2, arg3, arg4);
        }
        
        public static fabric.util.List asList(
          fabric.lang.arrays.ObjectArray arg1) {
            return fabric.util.Arrays._Impl.asList(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.longArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.intArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.shortArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.charArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.byteArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.booleanArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.floatArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.doubleArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int hashCode(fabric.lang.arrays.ObjectArray arg1) {
            return fabric.util.Arrays._Impl.hashCode(arg1);
        }
        
        public static int deepHashCode(fabric.lang.arrays.ObjectArray arg1) {
            return fabric.util.Arrays._Impl.deepHashCode(arg1);
        }
        
        public static boolean deepEquals(fabric.lang.arrays.ObjectArray arg1,
                                         fabric.lang.arrays.ObjectArray arg2) {
            return fabric.util.Arrays._Impl.deepEquals(arg1, arg2);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.booleanArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.byteArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.charArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.shortArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.intArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.longArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.floatArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.doubleArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.arrays.ObjectArray arg1) {
            return fabric.util.Arrays._Impl.toString(arg1);
        }
        
        public static java.lang.String deepToString(
          fabric.lang.arrays.ObjectArray arg1) {
            return fabric.util.Arrays._Impl.deepToString(arg1);
        }
        
        public static void arraycopy(fabric.lang.arrays.ObjectArray arg1,
                                     int arg2,
                                     fabric.lang.arrays.ObjectArray arg3,
                                     int arg4, int arg5) {
            fabric.util.Arrays._Impl.arraycopy(arg1, arg2, arg3, arg4, arg5);
        }
        
        public _Proxy(Arrays._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Arrays {
        /**
   * This class is non-instantiable.
   */
        private _Impl(fabric.worker.Store $location) { super($location); }
        
        /**
   * Perform a binary search of a byte array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.byteArray a,
                                       byte key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final byte d = (byte) a.get(mid);
                if (d == key) return mid; else if (d > key) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of a char array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.charArray a,
                                       char key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final char d = (char) a.get(mid);
                if (d == key) return mid; else if (d > key) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of a short array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.shortArray a,
                                       short key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final short d = (short) a.get(mid);
                if (d == key) return mid; else if (d > key) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of an int array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.intArray a, int key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final int d = (int) a.get(mid);
                if (d == key) return mid; else if (d > key) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of a long array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.longArray a,
                                       long key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final long d = (long) a.get(mid);
                if (d == key) return mid; else if (d > key) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of a float array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.floatArray a,
                                       float key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final int r = java.lang.Float.compare((float) a.get(mid), key);
                if (r == 0) return mid; else if (r > 0) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of a double array for a key. The array must be
   * sorted (as by the sort() method) - if it is not, the behaviour of this
   * method is undefined, and may be an infinite loop. If the array contains
   * the key more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   */
        public static int binarySearch(fabric.lang.arrays.doubleArray a,
                                       double key) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final int r = java.lang.Double.compare((double) a.get(mid),
                                                       key);
                if (r == 0) return mid; else if (r > 0) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Perform a binary search of an Object array for a key, using the natural
   * ordering of the elements. The array must be sorted (as by the sort()
   * method) - if it is not, the behaviour of this method is undefined, and may
   * be an infinite loop. Further, the key must be comparable with every item
   * in the array. If the array contains the key more than once, any one of
   * them may be found. Note: although the specification allows for an infinite
   * loop if the array is unsorted, it will not happen in this (JCL)
   * implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of a
   * @throws NullPointerException if a null element in a is compared
   */
        public static int binarySearch(fabric.lang.arrays.ObjectArray a,
                                       fabric.lang.Object key) {
            return fabric.util.Arrays._Impl.binarySearch(a, key, null);
        }
        
        /**
   * Perform a binary search of an Object array for a key, using a supplied
   * Comparator. The array must be sorted (as by the sort() method with the
   * same Comparator) - if it is not, the behaviour of this method is
   * undefined, and may be an infinite loop. Further, the key must be
   * comparable with every item in the array. If the array contains the key
   * more than once, any one of them may be found. Note: although the
   * specification allows for an infinite loop if the array is unsorted, it
   * will not happen in this (JCL) implementation.
   *
   * @param a the array to search (must be sorted)
   * @param key the value to search for
   * @param c the comparator by which the array is sorted; or null to
   *        use the elements' natural order
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value.
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of a
   * @throws NullPointerException if a null element is compared with natural
   *         ordering (only possible when c is null)
   */
        public static int binarySearch(fabric.lang.arrays.ObjectArray a,
                                       fabric.lang.Object key,
                                       fabric.util.Comparator c) {
            int low = 0;
            int hi = a.get$length() - 1;
            int mid = 0;
            while (low <= hi) {
                mid = low + hi >>> 1;
                final int d =
                  fabric.util.Collections._Impl.compare(key,
                                                        (fabric.lang.Object)
                                                          a.get(mid), c);
                if (d == 0) return mid; else if (d < 0) hi = mid - 1; else
                    low = ++mid;
            }
            return -mid - 1;
        }
        
        /**
   * Compare two boolean arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.booleanArray a1,
                                     fabric.lang.arrays.booleanArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((boolean) a1.get(i) != (boolean) a2.get(i))
                        return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two byte arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.byteArray a1,
                                     fabric.lang.arrays.byteArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((byte) a1.get(i) != (byte) a2.get(i)) return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two char arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.charArray a1,
                                     fabric.lang.arrays.charArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((char) a1.get(i) != (char) a2.get(i)) return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two short arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.shortArray a1,
                                     fabric.lang.arrays.shortArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((short) a1.get(i) != (short) a2.get(i)) return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two int arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.intArray a1,
                                     fabric.lang.arrays.intArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((int) a1.get(i) != (int) a2.get(i)) return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two long arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.longArray a1,
                                     fabric.lang.arrays.longArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if ((long) a1.get(i) != (long) a2.get(i)) return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two float arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.floatArray a1,
                                     fabric.lang.arrays.floatArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if (java.lang.Float.compare((float) a1.get(i),
                                                (float) a2.get(i)) != 0)
                        return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two double arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a2 is of the same length
   *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
   */
        public static boolean equals(fabric.lang.arrays.doubleArray a1,
                                     fabric.lang.arrays.doubleArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if (java.lang.Double.compare((double) a1.get(i),
                                                 (double) a2.get(i)) != 0)
                        return false;
                return true;
            }
            return false;
        }
        
        /**
   * Compare two Object arrays for equality.
   *
   * @param a1 the first array to compare
   * @param a2 the second array to compare
   * @return true if a1 and a2 are both null, or if a1 is of the same length
   *         as a2, and for each 0 <= i < a.length, a1[i] == null ?
   *         a2[i] == null : a1[i].equals(a2[i]).
   */
        public static boolean equals(fabric.lang.arrays.ObjectArray a1,
                                     fabric.lang.arrays.ObjectArray a2) {
            if (fabric.lang.Object._Proxy.idEquals(a1, a2)) return true;
            if (fabric.lang.Object._Proxy.idEquals(null, a1) ||
                  fabric.lang.Object._Proxy.idEquals(null, a2))
                return false;
            if (a1.get$length() == a2.get$length()) {
                int i = a1.get$length();
                while (--i >= 0)
                    if (!fabric.util.AbstractCollection._Impl.
                          equals((fabric.lang.Object) a1.get(i),
                                 (fabric.lang.Object) a2.get(i)))
                        return false;
                return true;
            }
            return false;
        }
        
        /**
   * Fill an array with a boolean value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.booleanArray a,
                                boolean val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a boolean value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.booleanArray a,
                                int fromIndex, int toIndex, boolean val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a byte value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.byteArray a, byte val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a byte value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.byteArray a, int fromIndex,
                                int toIndex, byte val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a char value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.charArray a, char val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a char value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.charArray a, int fromIndex,
                                int toIndex, char val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a short value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.shortArray a, short val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a short value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.shortArray a, int fromIndex,
                                int toIndex, short val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with an int value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.intArray a, int val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with an int value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.intArray a, int fromIndex,
                                int toIndex, int val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a long value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.longArray a, long val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a long value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.longArray a, int fromIndex,
                                int toIndex, long val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a float value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.floatArray a, float val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a float value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.floatArray a, int fromIndex,
                                int toIndex, float val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with a double value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   */
        public static void fill(fabric.lang.arrays.doubleArray a, double val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with a double value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.doubleArray a, int fromIndex,
                                int toIndex, double val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Fill an array with an Object value.
   *
   * @param a the array to fill
   * @param val the value to fill it with
   * @throws ClassCastException if val is not an instance of the element
   *         type of a.
   */
        public static void fill(fabric.lang.arrays.ObjectArray a,
                                fabric.lang.Object val) {
            fabric.util.Arrays._Impl.fill(a, 0, a.get$length(), val);
        }
        
        /**
   * Fill a range of an array with an Object value.
   *
   * @param a the array to fill
   * @param fromIndex the index to fill from, inclusive
   * @param toIndex the index to fill to, exclusive
   * @param val the value to fill with
   * @throws ClassCastException if val is not an instance of the element
   *         type of a.
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void fill(fabric.lang.arrays.ObjectArray a, int fromIndex,
                                int toIndex, fabric.lang.Object val) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            for (int i = fromIndex; i < toIndex; i++) a.set(i, val);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the byte array to sort
   */
        public static void sort(fabric.lang.arrays.byteArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the byte array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.byteArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.byteArray d) {
            return (byte) d.get(a) <
              (byte) d.get(b)
              ? ((byte)
                   d.get(b) <
                   (byte) d.get(c)
                   ? b
                   : ((byte) d.get(a) < (byte) d.get(c) ? c : a))
              : ((byte) d.get(b) > (byte) d.get(c)
                   ? b
                   : ((byte) d.get(a) > (byte) d.get(c) ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j, fabric.lang.arrays.byteArray a) {
            byte c = (byte) a.get(i);
            a.set(i, (byte) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.byteArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.byteArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j > from && (byte) array.get(j - 1) >
                           (byte) array.get(j);
                         j--)
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp = (byte)
                                   array.get(b) -
                                   (byte) array.get(from)) <= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp = (byte)
                                   array.get(c) -
                                   (byte) array.get(from)) >= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the char array to sort
   */
        public static void sort(fabric.lang.arrays.charArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the char array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.charArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.charArray d) {
            return (char) d.get(a) <
              (char) d.get(b)
              ? ((char)
                   d.get(b) <
                   (char) d.get(c)
                   ? b
                   : ((char) d.get(a) < (char) d.get(c) ? c : a))
              : ((char) d.get(b) > (char) d.get(c)
                   ? b
                   : ((char) d.get(a) > (char) d.get(c) ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j, fabric.lang.arrays.charArray a) {
            char c = (char) a.get(i);
            a.set(i, (char) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.charArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.charArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j > from && (char) array.get(j - 1) >
                           (char) array.get(j);
                         j--)
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp = (char)
                                   array.get(b) -
                                   (char) array.get(from)) <= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp = (char)
                                   array.get(c) -
                                   (char) array.get(from)) >= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the short array to sort
   */
        public static void sort(fabric.lang.arrays.shortArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the short array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.shortArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.shortArray d) {
            return (short) d.get(a) <
              (short) d.get(b)
              ? ((short)
                   d.get(b) <
                   (short) d.get(c)
                   ? b
                   : ((short) d.get(a) < (short) d.get(c) ? c : a))
              : ((short) d.get(b) > (short) d.get(c)
                   ? b
                   : ((short) d.get(a) > (short) d.get(c) ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j,
                                 fabric.lang.arrays.shortArray a) {
            short c = (short) a.get(i);
            a.set(i, (short) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.shortArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.shortArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j > from && (short) array.get(j - 1) >
                           (short) array.get(j);
                         j--)
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp = (short)
                                   array.get(b) -
                                   (short) array.get(from)) <= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp = (short)
                                   array.get(c) -
                                   (short) array.get(from)) >= 0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the int array to sort
   */
        public static void sort(fabric.lang.arrays.intArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the int array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.intArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.intArray d) {
            return (int) d.get(a) <
              (int) d.get(b)
              ? ((int)
                   d.get(b) <
                   (int) d.get(c)
                   ? b
                   : ((int) d.get(a) < (int) d.get(c) ? c : a))
              : ((int) d.get(b) > (int) d.get(c)
                   ? b
                   : ((int) d.get(a) > (int) d.get(c) ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j, fabric.lang.arrays.intArray a) {
            int c = (int) a.get(i);
            a.set(i, (int) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.intArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Compares two integers in natural order, since a - b is inadequate.
   *
   * @param a the first int
   * @param b the second int
   * @return &lt; 0, 0, or &gt; 0 accorting to the comparison
   */
        private static int compare(int a, int b) {
            return a < b ? -1 : (a == b ? 0 : 1);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.intArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j > from && (int) array.get(j - 1) >
                           (int) array.get(j);
                         j--)
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp =
                            fabric.util.Arrays._Impl.compare(
                                                       (int) array.get(b),
                                                       (int)
                                                         array.get(from))) <=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp =
                            fabric.util.Arrays._Impl.compare(
                                                       (int) array.get(c),
                                                       (int)
                                                         array.get(from))) >=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the long array to sort
   */
        public static void sort(fabric.lang.arrays.longArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the long array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.longArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.longArray d) {
            return (long) d.get(a) <
              (long) d.get(b)
              ? ((long)
                   d.get(b) <
                   (long) d.get(c)
                   ? b
                   : ((long) d.get(a) < (long) d.get(c) ? c : a))
              : ((long) d.get(b) > (long) d.get(c)
                   ? b
                   : ((long) d.get(a) > (long) d.get(c) ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j, fabric.lang.arrays.longArray a) {
            long c = (long) a.get(i);
            a.set(i, (long) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.longArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Compares two longs in natural order, since a - b is inadequate.
   *
   * @param a the first long
   * @param b the second long
   * @return &lt; 0, 0, or &gt; 0 accorting to the comparison
   */
        private static int compare(long a, long b) {
            return a < b ? -1 : (a == b ? 0 : 1);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.longArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j > from && (long) array.get(j - 1) >
                           (long) array.get(j);
                         j--)
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp =
                            fabric.util.Arrays._Impl.compare(
                                                       (long) array.get(b),
                                                       (long)
                                                         array.get(from))) <=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp =
                            fabric.util.Arrays._Impl.compare(
                                                       (long) array.get(c),
                                                       (long)
                                                         array.get(from))) >=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the float array to sort
   */
        public static void sort(fabric.lang.arrays.floatArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the float array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.floatArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.floatArray d) {
            return java.lang.Float.compare((float) d.get(a), (float) d.get(b)) <
              0
              ? (java.lang.Float.compare((float) d.get(b), (float) d.get(c)) <
                   0
                   ? b
                   : (java.lang.Float.compare((float)
                                                d.get(a), (float) d.get(c)) < 0
                        ? c
                        : a))
              : (java.lang.Float.compare((float) d.get(b), (float) d.get(c)) >
                   0
                   ? b
                   : (java.lang.Float.compare((float)
                                                d.get(a), (float) d.get(c)) > 0
                        ? c
                        : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j,
                                 fabric.lang.arrays.floatArray a) {
            float c = (float) a.get(i);
            a.set(i, (float) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.floatArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.floatArray array, int from,
                                  int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j >
                           from &&
                           java.lang.Float.compare((float)
                                                     array.get(j - 1),
                                                   (float) array.get(j)) > 0;
                         j--) {
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                    }
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp = java.lang.Float.compare((float)
                                                           array.get(b),
                                                         (float)
                                                           array.get(from))) <=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp = java.lang.Float.compare((float)
                                                           array.get(c),
                                                         (float)
                                                           array.get(from))) >=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the double array to sort
   */
        public static void sort(fabric.lang.arrays.doubleArray a) {
            fabric.util.Arrays._Impl.qsort(a, 0, a.get$length());
        }
        
        /**
   * Performs a stable sort on the elements, arranging them according to their
   * natural order.
   *
   * @param a the double array to sort
   * @param fromIndex the first index to sort (inclusive)
   * @param toIndex the last index to sort (exclusive)
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; a.length
   */
        public static void sort(fabric.lang.arrays.doubleArray a, int fromIndex,
                                int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException();
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.util.Arrays._Impl.qsort(a, fromIndex, toIndex - fromIndex);
        }
        
        /**
   * Finds the index of the median of three array elements.
   *
   * @param a the first index
   * @param b the second index
   * @param c the third index
   * @param d the array
   * @return the index (a, b, or c) which has the middle value of the three
   */
        private static int med3(int a, int b, int c,
                                fabric.lang.arrays.doubleArray d) {
            return java.lang.Double.compare((double) d.get(a),
                                            (double) d.get(b)) <
              0
              ? (java.lang.Double.compare((double)
                                            d.get(b), (double) d.get(c)) <
                   0
                   ? b
                   : (java.lang.Double.compare((double)
                                                 d.get(a), (double) d.get(c)) <
                        0 ? c : a))
              : (java.lang.Double.compare((double) d.get(b),
                                          (double) d.get(c)) >
                   0
                   ? b
                   : (java.lang.Double.compare((double)
                                                 d.get(a), (double) d.get(c)) >
                        0 ? c : a));
        }
        
        /**
   * Swaps the elements at two locations of an array
   *
   * @param i the first index
   * @param j the second index
   * @param a the array
   */
        private static void swap(int i, int j,
                                 fabric.lang.arrays.doubleArray a) {
            double c = (double) a.get(i);
            a.set(i, (double) a.get(j));
            a.set(j, c);
        }
        
        /**
   * Swaps two ranges of an array.
   *
   * @param i the first range start
   * @param j the second range start
   * @param n the element count
   * @param a the array
   */
        private static void vecswap(int i, int j, int n,
                                    fabric.lang.arrays.doubleArray a) {
            for (; n > 0; i++, j++, n--) fabric.util.Arrays._Impl.swap(i, j, a);
        }
        
        /**
   * Performs a recursive modified quicksort.
   *
   * @param array the array to sort
   * @param from the start index (inclusive)
   * @param count the number of elements to sort
   */
        private static void qsort(fabric.lang.arrays.doubleArray array,
                                  int from, int count) {
            if (count <= 7) {
                for (int i = from + 1; i < from + count; i++)
                    for (int j = i;
                         j >
                           from &&
                           java.lang.Double.compare((double)
                                                      array.get(j - 1),
                                                    (double) array.get(j)) > 0;
                         j--) {
                        fabric.util.Arrays._Impl.swap(j, j - 1, array);
                    }
                return;
            }
            int mid = count / 2;
            int lo = from;
            int hi = from + count - 1;
            if (count > 40) {
                int s = count / 8;
                lo = fabric.util.Arrays._Impl.med3(lo, lo + s, lo + 2 * s,
                                                   array);
                mid = fabric.util.Arrays._Impl.med3(mid - s, mid, mid + s,
                                                    array);
                hi = fabric.util.Arrays._Impl.med3(hi - 2 * s, hi - s, hi,
                                                   array);
            }
            mid = fabric.util.Arrays._Impl.med3(lo, mid, hi, array);
            int a;
            int b;
            int c;
            int d;
            int comp;
            fabric.util.Arrays._Impl.swap(from, mid, array);
            a = (b = from);
            c = (d = from + count - 1);
            while (true) {
                while (b <=
                         c &&
                         (comp = java.lang.Double.compare((double)
                                                            array.get(b),
                                                          (double)
                                                            array.get(from))) <=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(a, b, array);
                        a++;
                    }
                    b++;
                }
                while (c >=
                         b &&
                         (comp = java.lang.Double.compare((double)
                                                            array.get(c),
                                                          (double)
                                                            array.get(from))) >=
                         0) {
                    if (comp == 0) {
                        fabric.util.Arrays._Impl.swap(c, d, array);
                        d--;
                    }
                    c--;
                }
                if (b > c) break;
                fabric.util.Arrays._Impl.swap(b, c, array);
                b++;
                c--;
            }
            hi = from + count;
            int span;
            span = java.lang.Math.min(a - from, b - a);
            fabric.util.Arrays._Impl.vecswap(from, b - span, span, array);
            span = java.lang.Math.min(d - c, hi - d - 1);
            fabric.util.Arrays._Impl.vecswap(b, hi - span, span, array);
            span = b - a;
            if (span > 1) fabric.util.Arrays._Impl.qsort(array, from, span);
            span = d - c;
            if (span > 1)
                fabric.util.Arrays._Impl.qsort(array, hi - span, span);
        }
        
        /**
   * Sort an array of Objects according to their natural ordering. The sort is
   * guaranteed to be stable, that is, equal elements will not be reordered.
   * The sort algorithm is a mergesort with the merge omitted if the last
   * element of one half comes before the first element of the other half. This
   * algorithm gives guaranteed O(n*log(n)) time, at the expense of making a
   * copy of the array.
   *
   * @param a the array to be sorted
   * @throws ClassCastException if any two elements are not mutually
   *         comparable
   * @throws NullPointerException if an element is null (since
   *         null.compareTo cannot work)
   * @see Comparable
   */
        public static void sort(fabric.lang.arrays.ObjectArray a) {
            fabric.util.Arrays._Impl.sort(a, 0, a.get$length(), null);
        }
        
        /**
   * Sort an array of Objects according to a Comparator. The sort is
   * guaranteed to be stable, that is, equal elements will not be reordered.
   * The sort algorithm is a mergesort with the merge omitted if the last
   * element of one half comes before the first element of the other half. This
   * algorithm gives guaranteed O(n*log(n)) time, at the expense of making a
   * copy of the array.
   *
   * @param a the array to be sorted
   * @param c a Comparator to use in sorting the array; or null to indicate
   *        the elements' natural order
   * @throws ClassCastException if any two elements are not mutually
   *         comparable by the Comparator provided
   * @throws NullPointerException if a null element is compared with natural
   *         ordering (only possible when c is null)
   */
        public static void sort(fabric.lang.arrays.ObjectArray a,
                                fabric.util.Comparator c) {
            fabric.util.Arrays._Impl.sort(a, 0, a.get$length(), c);
        }
        
        /**
   * Sort an array of Objects according to their natural ordering. The sort is
   * guaranteed to be stable, that is, equal elements will not be reordered.
   * The sort algorithm is a mergesort with the merge omitted if the last
   * element of one half comes before the first element of the other half. This
   * algorithm gives guaranteed O(n*log(n)) time, at the expense of making a
   * copy of the array.
   *
   * @param a the array to be sorted
   * @param fromIndex the index of the first element to be sorted
   * @param toIndex the index of the last element to be sorted plus one
   * @throws ClassCastException if any two elements are not mutually
   *         comparable
   * @throws NullPointerException if an element is null (since
   *         null.compareTo cannot work)
   * @throws ArrayIndexOutOfBoundsException if fromIndex and toIndex
   *         are not in range.
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   */
        public static void sort(fabric.lang.arrays.ObjectArray a, int fromIndex,
                                int toIndex) {
            fabric.util.Arrays._Impl.sort(a, fromIndex, toIndex, null);
        }
        
        /**
   * Sort an array of Objects according to a Comparator. The sort is
   * guaranteed to be stable, that is, equal elements will not be reordered.
   * The sort algorithm is a mergesort with the merge omitted if the last
   * element of one half comes before the first element of the other half. This
   * algorithm gives guaranteed O(n*log(n)) time, at the expense of making a
   * copy of the array.
   *
   * @param a the array to be sorted
   * @param fromIndex the index of the first element to be sorted
   * @param toIndex the index of the last element to be sorted plus one
   * @param c a Comparator to use in sorting the array; or null to indicate
   *        the elements' natural order
   * @throws ClassCastException if any two elements are not mutually
   *         comparable by the Comparator provided
   * @throws ArrayIndexOutOfBoundsException if fromIndex and toIndex
   *         are not in range.
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @throws NullPointerException if a null element is compared with natural
   *         ordering (only possible when c is null)
   */
        public static void sort(fabric.lang.arrays.ObjectArray a, int fromIndex,
                                int toIndex, fabric.util.Comparator c) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException("fromIndex " +
                                                               fromIndex +
                                                               " > toIndex " +
                                                               toIndex);
            if (fromIndex < 0)
                throw new java.lang.ArrayIndexOutOfBoundsException();
            for (int chunk = fromIndex; chunk < toIndex; chunk += 6) {
                int end = java.lang.Math.min(chunk + 6, toIndex);
                for (int i = chunk + 1; i < end; i++) {
                    if (fabric.util.Collections._Impl.compare(
                                                        (fabric.lang.Object)
                                                          a.get(i - 1),
                                                        (fabric.lang.Object)
                                                          a.get(i), c) >
                          0) {
                        int j = i;
                        fabric.lang.Object elem = (fabric.lang.Object) a.get(j);
                        do  {
                            a.set(j, (fabric.lang.Object) a.get(j - 1));
                            j--;
                        }while(j >
                                 chunk &&
                                 fabric.util.Collections._Impl.
                                 compare((fabric.lang.Object) a.get(j - 1),
                                         elem, c) > 0); 
                        a.set(j, elem);
                    }
                }
            }
            int len = toIndex - fromIndex;
            if (len <= 6) return;
            fabric.lang.arrays.ObjectArray src = a;
            fabric.lang.arrays.ObjectArray
              dest =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  fabric.util.Arrays._Static._Proxy.$instance.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  fabric.util.Arrays._Static._Proxy.$instance.get$$updateLabel(
                                                                ),
                  fabric.util.Arrays._Static._Proxy.$instance.get$$updateLabel(
                                                                ).confPolicy(),
                  fabric.lang.Object._Proxy.class, len).$getProxy();
            fabric.lang.arrays.ObjectArray t = null;
            int srcDestDiff = -fromIndex;
            for (int size = 6; size < len; size <<= 1) {
                for (int start = fromIndex; start < toIndex;
                     start += size << 1) {
                    int mid = start + size;
                    int end = java.lang.Math.min(toIndex, mid + size);
                    if (mid >=
                          end ||
                          fabric.util.Collections._Impl.compare(
                                                          (fabric.lang.Object)
                                                            src.get(mid - 1),
                                                          (fabric.lang.Object)
                                                            src.get(mid), c) <=
                          0) {
                        java.lang.System.
                          arraycopy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(src),
                            start,
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(dest),
                            start + srcDestDiff, end - start);
                    }
                    else if (fabric.util.Collections._Impl.
                               compare((fabric.lang.Object) src.get(start),
                                       (fabric.lang.Object) src.get(end - 1),
                                       c) > 0) {
                        java.lang.System.
                          arraycopy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(src),
                            start,
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(dest),
                            end - size + srcDestDiff, size);
                        java.lang.System.
                          arraycopy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(src),
                            mid,
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(dest),
                            start + srcDestDiff, end - mid);
                    }
                    else {
                        int p1 = start;
                        int p2 = mid;
                        int i = start + srcDestDiff;
                        while (p1 < mid && p2 < end) {
                            dest.
                              set(
                                i++,
                                (fabric.lang.Object)
                                  src.
                                  get(
                                    fabric.util.Collections._Impl.
                                        compare((fabric.lang.Object)
                                                  src.get(p1),
                                                (fabric.lang.Object)
                                                  src.get(p2), c) <= 0
                                        ? p1++
                                        : p2++));
                        }
                        if (p1 < mid)
                            java.lang.System.
                              arraycopy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      src),
                                p1,
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      dest), i,
                                mid - p1);
                        else
                            java.lang.System.
                              arraycopy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      src),
                                p2,
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      dest), i,
                                end - p2);
                    }
                }
                t = src;
                src = dest;
                dest = t;
                fromIndex += srcDestDiff;
                toIndex += srcDestDiff;
                srcDestDiff = -srcDestDiff;
            }
            if (!fabric.lang.Object._Proxy.idEquals(src, a)) {
                java.lang.System.arraycopy(
                                   (java.lang.Object)
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         src),
                                   0,
                                   (java.lang.Object)
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         a),
                                   srcDestDiff, toIndex);
            }
        }
        
        /**
   * Returns a list "view" of the specified array. This method is intended to
   * make it easy to use the Collections API with existing array-based APIs and
   * programs. Changes in the list or the array show up in both places. The
   * list does not support element addition or removal, but does permit
   * value modification. The returned list implements both Serializable and
   * RandomAccess.
   *
   * @param a the array to return a view of (<code>null</code> not permitted)
   * @return a fixed-size list, changes to which "write through" to the array
   *
   * @throws NullPointerException if <code>a</code> is <code>null</code>.
   * @see Serializable
   * @see RandomAccess
   * @see Arrays.ArrayList
   */
        public static fabric.util.List asList(
          final fabric.lang.arrays.ObjectArray a) {
            return (ArrayList)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.Arrays.ArrayList)
                          new fabric.
                            util.
                            Arrays.
                            ArrayList.
                            _Impl(
                            fabric.util.Arrays._Static._Proxy.$instance.
                                $getStore()).
                          $getProxy()).fabric$util$Arrays$ArrayList$(a));
        }
        
        /**
   * Returns the hashcode of an array of long numbers.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents longs in their wrapper class, <code>Long</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of long numbers for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.longArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i) {
                int elt = (int) ((long) v.get(i) ^ (long) v.get(i) >>> 32);
                result = 31 * result + elt;
            }
            return result;
        }
        
        /**
   * Returns the hashcode of an array of integer numbers.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents ints in their wrapper class, <code>Integer</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of integer numbers for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.intArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result + (int) v.get(i);
            return result;
        }
        
        /**
   * Returns the hashcode of an array of short numbers.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents shorts in their wrapper class, <code>Short</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of short numbers for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.shortArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result + (short) v.get(i);
            return result;
        }
        
        /**
   * Returns the hashcode of an array of characters.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents chars in their wrapper class, <code>Character</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of characters for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.charArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result + (char) v.get(i);
            return result;
        }
        
        /**
   * Returns the hashcode of an array of bytes.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents bytes in their wrapper class, <code>Byte</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of bytes for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.byteArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result + (byte) v.get(i);
            return result;
        }
        
        /**
   * Returns the hashcode of an array of booleans.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents booleans in their wrapper class,
   * <code>Boolean</code>.  For <code>null</code>, 0 is returned.
   *
   * @param v an array of booleans for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.booleanArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result + ((boolean) v.get(i) ? 1231 : 1237);
            return result;
        }
        
        /**
   * Returns the hashcode of an array of floats.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents floats in their wrapper class, <code>Float</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of floats for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.floatArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i)
                result = 31 * result +
                           java.lang.Float.floatToIntBits((float) v.get(i));
            return result;
        }
        
        /**
   * Returns the hashcode of an array of doubles.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents doubles in their wrapper class, <code>Double</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of doubles for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.doubleArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i) {
                long l = java.lang.Double.doubleToLongBits((double) v.get(i));
                int elt = (int) (l ^ l >>> 32);
                result = 31 * result + elt;
            }
            return result;
        }
        
        /**
   * Returns the hashcode of an array of integer numbers.  If two arrays
   * are equal, according to <code>equals()</code>, they should have the
   * same hashcode.  The hashcode returned by the method is equal to that
   * obtained by the corresponding <code>List</code> object.  This has the same
   * data, but represents ints in their wrapper class, <code>Integer</code>.
   * For <code>null</code>, 0 is returned.
   *
   * @param v an array of integer numbers for which the hash code should be
   *          computed.
   * @return the hash code of the array, or 0 if null was given.
   * @since 1.5
   */
        public static int hashCode(fabric.lang.arrays.ObjectArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i) {
                int elt =
                  fabric.lang.Object._Proxy.idEquals((fabric.lang.Object)
                                                       v.get(i), null)
                  ? 0
                  : ((java.lang.Object)
                       fabric.lang.WrappedJavaInlineable.$unwrap(
                                                           (fabric.lang.Object)
                                                             v.get(i))).
                  hashCode();
                result = 31 * result + elt;
            }
            return result;
        }
        
        /** @since 1.5 */
        public static int deepHashCode(fabric.lang.arrays.ObjectArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return 0;
            int result = 1;
            for (int i = 0; i < v.get$length(); ++i) {
                int elt;
                if (fabric.lang.Object._Proxy.idEquals((fabric.lang.Object)
                                                         v.get(i), null))
                    elt = 0;
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.booleanArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.booleanArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.byteArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.byteArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.charArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.charArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.shortArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.shortArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.intArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.intArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.longArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.longArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.floatArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.floatArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.doubleArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.doubleArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.lang.Object)
                                   v.
                                   get(
                                     i))) instanceof fabric.lang.arrays.ObjectArray)
                    elt =
                      fabric.util.Arrays._Impl.
                        hashCode(
                          (fabric.lang.arrays.ObjectArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        (fabric.lang.Object)
                                                          v.get(i)));
                else
                    elt =
                      ((java.lang.Object)
                         fabric.lang.WrappedJavaInlineable.
                         $unwrap((fabric.lang.Object) v.get(i))).hashCode();
                result = 31 * result + elt;
            }
            return result;
        }
        
        /** @since 1.5 */
        public static boolean deepEquals(fabric.lang.arrays.ObjectArray v1,
                                         fabric.lang.arrays.ObjectArray v2) {
            if (fabric.lang.Object._Proxy.idEquals(v1, null))
                return fabric.lang.Object._Proxy.idEquals(v2, null);
            if (fabric.lang.Object._Proxy.idEquals(v2, null) ||
                  v1.get$length() != v2.get$length())
                return false;
            for (int i = 0; i < v1.get$length(); ++i) {
                fabric.lang.Object e1 = (fabric.lang.Object) v1.get(i);
                fabric.lang.Object e2 = (fabric.lang.Object) v2.get(i);
                if (fabric.lang.Object._Proxy.idEquals(e1, e2)) continue;
                if (fabric.lang.Object._Proxy.idEquals(e1, null) ||
                      fabric.lang.Object._Proxy.idEquals(e2, null))
                    return false;
                boolean check;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            e1)) instanceof fabric.lang.arrays.booleanArray &&
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            e2)) instanceof fabric.lang.arrays.booleanArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.booleanArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.booleanArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.byteArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.byteArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.byteArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.byteArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.charArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.charArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.charArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.charArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.shortArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.shortArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.shortArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.shortArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.intArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.intArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.intArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.intArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.longArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.longArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.longArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.longArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.floatArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.floatArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.floatArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.floatArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.doubleArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.doubleArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.doubleArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.doubleArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e1)) instanceof fabric.lang.arrays.ObjectArray &&
                           fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 e2)) instanceof fabric.lang.arrays.ObjectArray)
                    check =
                      fabric.util.Arrays._Impl.
                        equals((fabric.lang.arrays.ObjectArray)
                                 fabric.lang.Object._Proxy.$getProxy(e1),
                               (fabric.lang.arrays.ObjectArray)
                                 fabric.lang.Object._Proxy.$getProxy(e2));
                else
                    check = e1.equals(e2);
                if (!check) return false;
            }
            return true;
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.booleanArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((boolean) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.byteArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((byte) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.charArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((char) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.shortArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((short) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(fabric.lang.arrays.intArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((int) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.longArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((long) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.floatArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((float) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.doubleArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append((double) v.get(i));
            }
            b.append("]");
            return b.toString();
        }
        
        /**
   * Returns a String representation of the argument array.  Returns "null"
   * if <code>a</code> is null.
   * @param v the array to represent
   * @return a String representing this array
   * @since 1.5
   */
        public static java.lang.String toString(
          fabric.lang.arrays.ObjectArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            java.lang.StringBuilder b = new java.lang.StringBuilder("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                b.append(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          (fabric.lang.Object)
                                                            v.get(i)));
            }
            b.append("]");
            return b.toString();
        }
        
        private static void deepToString(fabric.lang.arrays.ObjectArray v,
                                         java.lang.StringBuilder b,
                                         fabric.util.HashSet seen) {
            b.append("[");
            for (int i = 0; i < v.get$length(); ++i) {
                if (i > 0) b.append(", ");
                fabric.lang.Object elt = (fabric.lang.Object) v.get(i);
                if (fabric.lang.Object._Proxy.idEquals(elt, null))
                    b.append("null");
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.booleanArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.booleanArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.byteArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.byteArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.charArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.charArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.shortArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.shortArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.intArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.intArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.longArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.longArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.floatArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.floatArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.doubleArray)
                    b.
                      append(
                        fabric.util.Arrays._Impl.
                            toString(
                              (fabric.lang.arrays.doubleArray)
                                fabric.lang.Object._Proxy.$getProxy(elt)));
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 elt)) instanceof fabric.lang.arrays.ObjectArray) {
                    fabric.lang.arrays.ObjectArray os =
                      (fabric.lang.arrays.ObjectArray)
                        fabric.lang.Object._Proxy.$getProxy(elt);
                    if (seen.contains(os))
                        b.append("[...]");
                    else {
                        seen.add(os);
                        fabric.util.Arrays._Impl.deepToString(os, b, seen);
                    }
                }
                else
                    b.append((java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.$unwrap(elt));
            }
            b.append("]");
        }
        
        /** @since 1.5 */
        public static java.lang.String deepToString(
          fabric.lang.arrays.ObjectArray v) {
            if (fabric.lang.Object._Proxy.idEquals(v, null)) return "null";
            fabric.util.HashSet
              seen =
              (fabric.util.HashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.HashSet)
                     new fabric.util.HashSet._Impl(
                       fabric.util.Arrays._Static._Proxy.$instance.$getStore()).
                     $getProxy()).fabric$util$HashSet$());
            java.lang.StringBuilder b = new java.lang.StringBuilder();
            fabric.util.Arrays._Impl.deepToString(v, b, seen);
            return b.toString();
        }
        
        public static void arraycopy(fabric.lang.arrays.ObjectArray src,
                                     int srcPos,
                                     fabric.lang.arrays.ObjectArray dest,
                                     int destPos, int length) {
            if (srcPos < destPos) {
                for (int i = length - 1; i <= 0; i--) {
                    dest.set(destPos + i,
                             (fabric.lang.Object) src.get(srcPos + i));
                }
            } else {
                for (int i = 0; i < length; i++) {
                    dest.set(destPos + i,
                             (fabric.lang.Object) src.get(srcPos + i));
                }
            }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.Arrays) this.$getProxy();
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.Arrays._Proxy(this);
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Arrays._Static {
            public _Proxy(fabric.util.Arrays._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.Arrays._Static $instance;
            
            static {
                fabric.
                  util.
                  Arrays.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.Arrays._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(fabric.util.Arrays._Static._Impl.class);
                $instance = (fabric.util.Arrays._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Arrays._Static {
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
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Arrays._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 11, -122, -95, 59, -46,
    15, -49, 19, 119, -120, -72, 36, -11, -52, 7, -30, 33, 37, -88, 54, -28, 38,
    -71, 87, 77, -62, -89, -51, 60, -29, -110, 103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260588000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
