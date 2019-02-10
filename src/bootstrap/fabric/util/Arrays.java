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
                              $tm567 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled570 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff568 = 1;
                            boolean $doBackoff569 = true;
                            boolean $retry563 = true;
                            boolean $keepReads564 = false;
                            $label561: for (boolean $commit562 = false;
                                            !$commit562; ) {
                                if ($backoffEnabled570) {
                                    if ($doBackoff569) {
                                        if ($backoff568 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff568));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e565) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff568 < 5000)
                                            $backoff568 *= 2;
                                    }
                                    $doBackoff569 = $backoff568 <= 32 ||
                                                      !$doBackoff569;
                                }
                                $commit562 = true;
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
                                             RetryException $e565) {
                                        throw $e565;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e565) {
                                        throw $e565;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e565) {
                                        throw $e565;
                                    }
                                    catch (final Throwable $e565) {
                                        $tm567.getCurrentLog().checkRetrySignal(
                                                                 );
                                        throw $e565;
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e565) {
                                    $commit562 = false;
                                    continue $label561;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e565) {
                                    $commit562 = false;
                                    $retry563 = false;
                                    $keepReads564 = $e565.keepReads;
                                    if ($tm567.checkForStaleObjects()) {
                                        $retry563 = true;
                                        $keepReads564 = false;
                                        continue $label561;
                                    }
                                    fabric.common.TransactionID $currentTid566 =
                                      $tm567.getCurrentTid();
                                    if ($e565.tid ==
                                          null ||
                                          !$e565.tid.isDescendantOf(
                                                       $currentTid566)) {
                                        throw $e565;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e565);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e565) {
                                    $commit562 = false;
                                    fabric.common.TransactionID $currentTid566 =
                                      $tm567.getCurrentTid();
                                    if ($e565.tid.isDescendantOf(
                                                    $currentTid566))
                                        continue $label561;
                                    if ($currentTid566.parent != null) {
                                        $retry563 = false;
                                        throw $e565;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e565) {
                                    $commit562 = false;
                                    if ($tm567.checkForStaleObjects())
                                        continue $label561;
                                    $retry563 = false;
                                    throw new fabric.worker.AbortException(
                                            $e565);
                                }
                                finally {
                                    if ($commit562) {
                                        fabric.common.TransactionID
                                          $currentTid566 =
                                          $tm567.getCurrentTid();
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e565) {
                                            $commit562 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e565) {
                                            $commit562 = false;
                                            $retry563 = false;
                                            $keepReads564 = $e565.keepReads;
                                            if ($tm567.checkForStaleObjects()) {
                                                $retry563 = true;
                                                $keepReads564 = false;
                                                continue $label561;
                                            }
                                            if ($e565.tid ==
                                                  null ||
                                                  !$e565.tid.isDescendantOf(
                                                               $currentTid566))
                                                throw $e565;
                                            throw new fabric.worker.
                                                    UserAbortException($e565);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e565) {
                                            $commit562 = false;
                                            $currentTid566 =
                                              $tm567.getCurrentTid();
                                            if ($currentTid566 != null) {
                                                if ($e565.tid.
                                                      equals($currentTid566) ||
                                                      !$e565.tid.
                                                      isDescendantOf(
                                                        $currentTid566)) {
                                                    throw $e565;
                                                }
                                            }
                                        }
                                    } else if ($keepReads564) {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit562) {
                                        {  }
                                        if ($retry563) { continue $label561; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 94, -7, -45, 89,
        90, 20, 85, 24, 80, 78, -86, -91, -64, 33, -40, -73, -52, -70, -102, 17,
        68, 86, -19, 85, -86, -44, -111, -114, -128, -69, 126, -56 };
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
    
    public static final byte[] $classHash = new byte[] { 85, -95, 89, -109, -61,
    -44, -128, -8, 101, -28, -119, -23, 109, -79, 67, -11, -7, -37, -81, -33,
    48, -7, -75, 55, 31, 38, -9, -106, -73, -62, -22, 42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260588000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
