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
                              $tm589 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled592 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            long $backoff590 = 1;
                            boolean $doBackoff591 = true;
                            boolean $retry585 = true;
                            boolean $keepReads586 = false;
                            $label583: for (boolean $commit584 = false;
                                            !$commit584; ) {
                                if ($backoffEnabled592) {
                                    if ($doBackoff591) {
                                        if ($backoff590 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep(
                                                        java.lang.Math.
                                                            round(
                                                              java.lang.Math.
                                                                  random() *
                                                                  $backoff590));
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e587) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff590 <
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff)
                                            $backoff590 =
                                              java.lang.Math.
                                                min(
                                                  $backoff590 * 2,
                                                  fabric.worker.Worker.
                                                    getWorker().config.
                                                    maxBackoff);
                                    }
                                    $doBackoff591 = $backoff590 <= 32 ||
                                                      !$doBackoff591;
                                }
                                $commit584 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    fabric.util.Arrays.ArrayList._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) -2764017481108945198L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e587) {
                                    $commit584 = false;
                                    continue $label583;
                                }
                                catch (fabric.worker.
                                         TransactionAbortingException $e587) {
                                    $commit584 = false;
                                    $retry585 = false;
                                    $keepReads586 = $e587.keepReads;
                                    fabric.common.TransactionID $currentTid588 =
                                      $tm589.getCurrentTid();
                                    if ($e587.tid ==
                                          null ||
                                          !$e587.tid.isDescendantOf(
                                                       $currentTid588)) {
                                        throw $e587;
                                    }
                                    throw new fabric.worker.UserAbortException(
                                            $e587);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e587) {
                                    $commit584 = false;
                                    fabric.common.TransactionID $currentTid588 =
                                      $tm589.getCurrentTid();
                                    if ($e587.tid.isDescendantOf(
                                                    $currentTid588))
                                        continue $label583;
                                    if ($currentTid588.parent != null) {
                                        $retry585 = false;
                                        throw $e587;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final Throwable $e587) {
                                    $commit584 = false;
                                    $retry585 = false;
                                    if ($tm589.inNestedTxn()) {
                                        $keepReads586 = true;
                                    }
                                    throw $e587;
                                }
                                finally {
                                    fabric.common.TransactionID $currentTid588 =
                                      $tm589.getCurrentTid();
                                    if ($commit584) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e587) {
                                            $commit584 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionAbortingException $e587) {
                                            $commit584 = false;
                                            $retry585 = false;
                                            $keepReads586 = $e587.keepReads;
                                            if ($e587.tid ==
                                                  null ||
                                                  !$e587.tid.isDescendantOf(
                                                               $currentTid588))
                                                throw $e587;
                                            throw new fabric.worker.
                                                    UserAbortException($e587);
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e587) {
                                            $commit584 = false;
                                            $currentTid588 =
                                              $tm589.getCurrentTid();
                                            if ($currentTid588 != null) {
                                                if ($e587.tid.
                                                      equals($currentTid588) ||
                                                      !$e587.tid.
                                                      isDescendantOf(
                                                        $currentTid588)) {
                                                    throw $e587;
                                                }
                                            }
                                        }
                                    } else {
                                        if (!$tm589.inNestedTxn() &&
                                              $tm589.checkForStaleObjects()) {
                                            $retry585 = true;
                                            $keepReads586 = false;
                                        }
                                        if ($keepReads586) {
                                            try {
                                                fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                            }
                                            catch (final fabric.worker.TransactionRestartingException $e587) {
                                                $currentTid588 = $tm589.getCurrentTid();
                                                if ($currentTid588 != null &&
                                                      ($e587.tid.equals($currentTid588) || !$e587.tid.isDescendantOf($currentTid588))) {
                                                    throw $e587;
                                                } else {
                                                    $retry585 = true;
                                                }
                                            }
                                        } else {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                        }
                                    }
                                    if (!$commit584) {
                                        {  }
                                        if ($retry585) { continue $label583; }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 27, 106, -122, -74,
        113, 61, 102, -84, 87, 72, -48, 109, -81, -107, 62, 33, 58, -83, -21,
        -54, 75, -83, -95, 124, 1, 98, 1, -122, -46, 2, -69, 123 };
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
    
    public static final byte[] $classHash = new byte[] { 98, 112, 26, 60, -70,
    -117, 13, 58, -51, 86, -57, 123, 10, -63, 99, -60, 55, 80, -82, -84, -50,
    36, 44, 101, -92, -83, 93, 85, 46, -40, 121, -36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260588000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
