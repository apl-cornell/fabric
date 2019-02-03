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
                                        fabric.util.Arrays.ArrayList._Static.
                                          _Proxy.
                                          $instance.
                                          set$serialVersionUID(
                                            (long) -2764017481108945198L);
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
        
        public static final byte[] $classHash = new byte[] { -64, -116, -68,
        -100, -85, 99, 35, 86, -28, -30, -90, -113, 103, 67, 95, 0, -31, -68,
        -49, -123, -61, 59, -12, 82, 125, -41, -4, -57, -32, -51, 43, 78 };
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
    
    public static final byte[] $classHash = new byte[] { 39, -65, 43, -72, 18,
    -103, -79, 9, 117, 68, 0, 68, -42, 123, -21, -51, -81, -27, 124, 114, -101,
    -3, -17, -22, 60, -73, 8, 126, -109, -114, 66, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260588000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
