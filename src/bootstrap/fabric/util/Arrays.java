package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface Arrays
  extends fabric.lang.Object
{
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
        
        public fabric.lang.arrays.ObjectArray set$a(
          fabric.lang.arrays.ObjectArray val);
        
        /**
         * Construct a list view of the array.
         * @param a the array to view
         * @throws NullPointerException if a is null
         */
        public ArrayList fabric$util$Arrays$ArrayList$(
          fabric.lang.arrays.ObjectArray a);
        
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
          implements ArrayList
        {
            
            public fabric.lang.arrays.ObjectArray get$a() {
                return ((fabric.util.Arrays.ArrayList._Impl) fetch()).get$a();
            }
            
            public fabric.lang.arrays.ObjectArray set$a(
              fabric.lang.arrays.ObjectArray val) {
                return ((fabric.util.Arrays.ArrayList._Impl) fetch()).set$a(
                                                                        val);
            }
            
            public native fabric.util.Arrays.ArrayList
              fabric$util$Arrays$ArrayList$(
              fabric.lang.arrays.ObjectArray arg1);
            
            public _Proxy(ArrayList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements ArrayList
        {
            
            public fabric.lang.arrays.ObjectArray get$a() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.a;
            }
            
            public fabric.lang.arrays.ObjectArray set$a(
              fabric.lang.arrays.ObjectArray val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.a = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
             * The array we are viewing.
             * @serial the array
             */
            private fabric.lang.arrays.ObjectArray a;
            
            /**
             * Construct a list view of the array.
             * @param a the array to view
             * @throws NullPointerException if a is null
             */
            public native ArrayList fabric$util$Arrays$ArrayList$(
              fabric.lang.arrays.ObjectArray a);
            
            /**
             * Returns the object at the specified index in
             * the array.
             *
             * @param index The index to retrieve an object from.
             * @return The object at the array index specified.
             */
            public native fabric.lang.Object get(int index);
            
            /**
             * Returns the size of the array.
             *
             * @return The size.
             */
            public native int size();
            
            /**
             * Replaces the object at the specified index
             * with the supplied element.
             *
             * @param index The index at which to place the new object.
             * @param element The new object.
             * @return The object replaced by this operation.
             */
            public native fabric.lang.Object set(int index,
                                                 fabric.lang.Object element);
            
            /**
             * Returns true if the array contains the
             * supplied object.
             *
             * @param o The object to look for.
             * @return True if the object was found.
             */
            public native boolean contains(fabric.lang.Object o);
            
            /**
             * Returns the first index at which the
             * object, o, occurs in the array.
             *
             * @param o The object to search for.
             * @return The first relevant index.
             */
            public native int indexOf(fabric.lang.Object o);
            
            /**
             * Returns the last index at which the
             * object, o, occurs in the array.
             *
             * @param o The object to search for.
             * @return The last relevant index.
             */
            public native int lastIndexOf(fabric.lang.Object o);
            
            /**
             * Transforms the list into an array of
             * objects, by simplying cloning the array
             * wrapped by this list.
             *
             * @return A clone of the internal array.
             */
            public native fabric.lang.arrays.ObjectArray toArray();
            
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
            public native fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray array);
            
            public native fabric.lang.Object $initLabels();
            
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
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
              implements fabric.util.Arrays.ArrayList._Static
            {
                
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
                      _Impl impl =
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
              implements fabric.util.Arrays.ArrayList._Static
            {
                
                public long get$serialVersionUID() {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerRead(this);
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
                
                /**
                 * Compatible with JDK 1.4.
                 */
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
                
                public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, observers, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                    this.serialVersionUID = in.readLong();
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Arrays.ArrayList._Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Arrays
    {
        
        public static native int binarySearch(fabric.lang.arrays.byteArray arg1,
                                              byte arg2);
        
        public static native int binarySearch(fabric.lang.arrays.charArray arg1,
                                              char arg2);
        
        public static native int binarySearch(
          fabric.lang.arrays.shortArray arg1, short arg2);
        
        public static native int binarySearch(fabric.lang.arrays.intArray arg1,
                                              int arg2);
        
        public static native int binarySearch(fabric.lang.arrays.longArray arg1,
                                              long arg2);
        
        public static native int binarySearch(
          fabric.lang.arrays.floatArray arg1, float arg2);
        
        public static native int binarySearch(
          fabric.lang.arrays.doubleArray arg1, double arg2);
        
        public static native int binarySearch(
          fabric.lang.arrays.ObjectArray arg1, fabric.lang.Object arg2);
        
        public static native int binarySearch(
          fabric.lang.arrays.ObjectArray arg1, fabric.lang.Object arg2,
          fabric.util.Comparator arg3);
        
        public static native boolean equals(
          fabric.lang.arrays.booleanArray arg1,
          fabric.lang.arrays.booleanArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.byteArray arg1,
                                            fabric.lang.arrays.byteArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.charArray arg1,
                                            fabric.lang.arrays.charArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.shortArray arg1,
                                            fabric.lang.arrays.shortArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.intArray arg1,
                                            fabric.lang.arrays.intArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.longArray arg1,
                                            fabric.lang.arrays.longArray arg2);
        
        public static native boolean equals(fabric.lang.arrays.floatArray arg1,
                                            fabric.lang.arrays.floatArray arg2);
        
        public static native boolean equals(
          fabric.lang.arrays.doubleArray arg1,
          fabric.lang.arrays.doubleArray arg2);
        
        public static native boolean equals(
          fabric.lang.arrays.ObjectArray arg1,
          fabric.lang.arrays.ObjectArray arg2);
        
        public static native void fill(fabric.lang.arrays.booleanArray arg1,
                                       boolean arg2);
        
        public static native void fill(fabric.lang.arrays.booleanArray arg1,
                                       int arg2, int arg3, boolean arg4);
        
        public static native void fill(fabric.lang.arrays.byteArray arg1,
                                       byte arg2);
        
        public static native void fill(fabric.lang.arrays.byteArray arg1,
                                       int arg2, int arg3, byte arg4);
        
        public static native void fill(fabric.lang.arrays.charArray arg1,
                                       char arg2);
        
        public static native void fill(fabric.lang.arrays.charArray arg1,
                                       int arg2, int arg3, char arg4);
        
        public static native void fill(fabric.lang.arrays.shortArray arg1,
                                       short arg2);
        
        public static native void fill(fabric.lang.arrays.shortArray arg1,
                                       int arg2, int arg3, short arg4);
        
        public static native void fill(fabric.lang.arrays.intArray arg1,
                                       int arg2);
        
        public static native void fill(fabric.lang.arrays.intArray arg1,
                                       int arg2, int arg3, int arg4);
        
        public static native void fill(fabric.lang.arrays.longArray arg1,
                                       long arg2);
        
        public static native void fill(fabric.lang.arrays.longArray arg1,
                                       int arg2, int arg3, long arg4);
        
        public static native void fill(fabric.lang.arrays.floatArray arg1,
                                       float arg2);
        
        public static native void fill(fabric.lang.arrays.floatArray arg1,
                                       int arg2, int arg3, float arg4);
        
        public static native void fill(fabric.lang.arrays.doubleArray arg1,
                                       double arg2);
        
        public static native void fill(fabric.lang.arrays.doubleArray arg1,
                                       int arg2, int arg3, double arg4);
        
        public static native void fill(fabric.lang.arrays.ObjectArray arg1,
                                       fabric.lang.Object arg2);
        
        public static native void fill(fabric.lang.arrays.ObjectArray arg1,
                                       int arg2, int arg3,
                                       fabric.lang.Object arg4);
        
        public static native void sort(fabric.lang.arrays.byteArray arg1);
        
        public static native void sort(fabric.lang.arrays.byteArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.charArray arg1);
        
        public static native void sort(fabric.lang.arrays.charArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.shortArray arg1);
        
        public static native void sort(fabric.lang.arrays.shortArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.intArray arg1);
        
        public static native void sort(fabric.lang.arrays.intArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.longArray arg1);
        
        public static native void sort(fabric.lang.arrays.longArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.floatArray arg1);
        
        public static native void sort(fabric.lang.arrays.floatArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.doubleArray arg1);
        
        public static native void sort(fabric.lang.arrays.doubleArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.ObjectArray arg1);
        
        public static native void sort(fabric.lang.arrays.ObjectArray arg1,
                                       fabric.util.Comparator arg2);
        
        public static native void sort(fabric.lang.arrays.ObjectArray arg1,
                                       int arg2, int arg3);
        
        public static native void sort(fabric.lang.arrays.ObjectArray arg1,
                                       int arg2, int arg3,
                                       fabric.util.Comparator arg4);
        
        public static native fabric.util.List asList(
          fabric.lang.arrays.ObjectArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.longArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.intArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.shortArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.charArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.byteArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.booleanArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.floatArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.doubleArray arg1);
        
        public static native int hashCode(fabric.lang.arrays.ObjectArray arg1);
        
        public static native int deepHashCode(
          fabric.lang.arrays.ObjectArray arg1);
        
        public static native boolean deepEquals(
          fabric.lang.arrays.ObjectArray arg1,
          fabric.lang.arrays.ObjectArray arg2);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.booleanArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.byteArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.charArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.shortArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.intArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.longArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.floatArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.doubleArray arg1);
        
        public static native java.lang.String toString(
          fabric.lang.arrays.ObjectArray arg1);
        
        public static native java.lang.String deepToString(
          fabric.lang.arrays.ObjectArray arg1);
        
        public static native void arraycopy(fabric.lang.arrays.ObjectArray arg1,
                                            int arg2,
                                            fabric.lang.arrays.ObjectArray arg3,
                                            int arg4, int arg5);
        
        public _Proxy(Arrays._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Arrays
    {
        
        /**
         * Perform a binary search of a byte array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.byteArray a,
                                              byte key);
        
        /**
         * Perform a binary search of a char array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.charArray a,
                                              char key);
        
        /**
         * Perform a binary search of a short array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.shortArray a,
                                              short key);
        
        /**
         * Perform a binary search of an int array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.intArray a,
                                              int key);
        
        /**
         * Perform a binary search of a long array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.longArray a,
                                              long key);
        
        /**
         * Perform a binary search of a float array for a key. The array must be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.floatArray a,
                                              float key);
        
        /**
         * Perform a binary search of a double array for a key. The array must
         be
         * sorted (as by the sort() method) - if it is not, the behaviour of
         this
         * method is undefined, and may be an infinite loop. If the array
         contains
         * the key more than once, any one of them may be found. Note: although
         the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         */
        public static native int binarySearch(fabric.lang.arrays.doubleArray a,
                                              double key);
        
        /**
         * Perform a binary search of an Object array for a key, using the
         natural
         * ordering of the elements. The array must be sorted (as by the sort()
         * method) - if it is not, the behaviour of this method is undefined,
         and may
         * be an infinite loop. Further, the key must be comparable with every
         item
         * in the array. If the array contains the key more than once, any one
         of
         * them may be found. Note: although the specification allows for an
         infinite
         * loop if the array is unsorted, it will not happen in this (JCL)
         * implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         * @throws ClassCastException if key could not be compared with one of
         the
         *         elements of a
         * @throws NullPointerException if a null element in a is compared
         */
        public static native int binarySearch(fabric.lang.arrays.ObjectArray a,
                                              fabric.lang.Object key);
        
        /**
         * Perform a binary search of an Object array for a key, using a
         supplied
         * Comparator. The array must be sorted (as by the sort() method with
         the
         * same Comparator) - if it is not, the behaviour of this method is
         * undefined, and may be an infinite loop. Further, the key must be
         * comparable with every item in the array. If the array contains the
         key
         * more than once, any one of them may be found. Note: although the
         * specification allows for an infinite loop if the array is unsorted,
         it
         * will not happen in this (JCL) implementation.
         *
         * @param a the array to search (must be sorted)
         * @param key the value to search for
         * @param c the comparator by which the array is sorted; or null to
         *        use the elements' natural order
         * @return the index at which the key was found, or -n-1 if it was not
         *         found, where n is the index of the first value higher than
         key or
         *         a.length if there is no such value.
         * @throws ClassCastException if key could not be compared with one of
         the
         *         elements of a
         * @throws NullPointerException if a null element is compared with
         natural
         *         ordering (only possible when c is null)
         */
        public static native int binarySearch(fabric.lang.arrays.ObjectArray a,
                                              fabric.lang.Object key,
                                              fabric.util.Comparator c);
        
        /**
         * Compare two boolean arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.booleanArray a1,
                                            fabric.lang.arrays.booleanArray a2);
        
        /**
         * Compare two byte arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.byteArray a1,
                                            fabric.lang.arrays.byteArray a2);
        
        /**
         * Compare two char arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.charArray a1,
                                            fabric.lang.arrays.charArray a2);
        
        /**
         * Compare two short arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.shortArray a1,
                                            fabric.lang.arrays.shortArray a2);
        
        /**
         * Compare two int arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.intArray a1,
                                            fabric.lang.arrays.intArray a2);
        
        /**
         * Compare two long arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.longArray a1,
                                            fabric.lang.arrays.longArray a2);
        
        /**
         * Compare two float arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.floatArray a1,
                                            fabric.lang.arrays.floatArray a2);
        
        /**
         * Compare two double arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a2 is of the same
         length
         *         as a1, and for each 0 <= i < a1.length, a1[i] == a2[i]
         */
        public static native boolean equals(fabric.lang.arrays.doubleArray a1,
                                            fabric.lang.arrays.doubleArray a2);
        
        /**
         * Compare two Object arrays for equality.
         *
         * @param a1 the first array to compare
         * @param a2 the second array to compare
         * @return true if a1 and a2 are both null, or if a1 is of the same
         length
         *         as a2, and for each 0 <= i < a.length, a1[i] == null ?
         *         a2[i] == null : a1[i].equals(a2[i]).
         */
        public static native boolean equals(fabric.lang.arrays.ObjectArray a1,
                                            fabric.lang.arrays.ObjectArray a2);
        
        /**
         * Fill an array with a boolean value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.booleanArray a,
                                       boolean val);
        
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
        public static native void fill(fabric.lang.arrays.booleanArray a,
                                       int fromIndex, int toIndex, boolean val);
        
        /**
         * Fill an array with a byte value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.byteArray a,
                                       byte val);
        
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
        public static native void fill(fabric.lang.arrays.byteArray a,
                                       int fromIndex, int toIndex, byte val);
        
        /**
         * Fill an array with a char value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.charArray a,
                                       char val);
        
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
        public static native void fill(fabric.lang.arrays.charArray a,
                                       int fromIndex, int toIndex, char val);
        
        /**
         * Fill an array with a short value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.shortArray a,
                                       short val);
        
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
        public static native void fill(fabric.lang.arrays.shortArray a,
                                       int fromIndex, int toIndex, short val);
        
        /**
         * Fill an array with an int value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.intArray a, int val);
        
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
        public static native void fill(fabric.lang.arrays.intArray a,
                                       int fromIndex, int toIndex, int val);
        
        /**
         * Fill an array with a long value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.longArray a,
                                       long val);
        
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
        public static native void fill(fabric.lang.arrays.longArray a,
                                       int fromIndex, int toIndex, long val);
        
        /**
         * Fill an array with a float value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.floatArray a,
                                       float val);
        
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
        public static native void fill(fabric.lang.arrays.floatArray a,
                                       int fromIndex, int toIndex, float val);
        
        /**
         * Fill an array with a double value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         */
        public static native void fill(fabric.lang.arrays.doubleArray a,
                                       double val);
        
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
        public static native void fill(fabric.lang.arrays.doubleArray a,
                                       int fromIndex, int toIndex, double val);
        
        /**
         * Fill an array with an Object value.
         *
         * @param a the array to fill
         * @param val the value to fill it with
         * @throws ClassCastException if val is not an instance of the element
         *         type of a.
         */
        public static native void fill(fabric.lang.arrays.ObjectArray a,
                                       fabric.lang.Object val);
        
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
        public static native void fill(fabric.lang.arrays.ObjectArray a,
                                       int fromIndex, int toIndex,
                                       fabric.lang.Object val);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the byte array to sort
         */
        public static native void sort(fabric.lang.arrays.byteArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the byte array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.byteArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.byteArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.byteArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.byteArray a);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.byteArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the char array to sort
         */
        public static native void sort(fabric.lang.arrays.charArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the char array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.charArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.charArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.charArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.charArray a);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.charArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the short array to sort
         */
        public static native void sort(fabric.lang.arrays.shortArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the short array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.shortArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.shortArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.shortArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.shortArray a);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.shortArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the int array to sort
         */
        public static native void sort(fabric.lang.arrays.intArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the int array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.intArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.intArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.intArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.intArray a);
        
        /**
         * Compares two integers in natural order, since a - b is inadequate.
         *
         * @param a the first int
         * @param b the second int
         * @return &lt; 0, 0, or &gt; 0 accorting to the comparison
         */
        private static native int compare(int a, int b);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.intArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the long array to sort
         */
        public static native void sort(fabric.lang.arrays.longArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the long array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.longArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.longArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.longArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.longArray a);
        
        /**
         * Compares two longs in natural order, since a - b is inadequate.
         *
         * @param a the first long
         * @param b the second long
         * @return &lt; 0, 0, or &gt; 0 accorting to the comparison
         */
        private static native int compare(long a, long b);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.longArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the float array to sort
         */
        public static native void sort(fabric.lang.arrays.floatArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the float array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.floatArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.floatArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.floatArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.floatArray a);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.floatArray array,
                                         int from, int count);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the double array to sort
         */
        public static native void sort(fabric.lang.arrays.doubleArray a);
        
        /**
         * Performs a stable sort on the elements, arranging them according to
         their
         * natural order.
         *
         * @param a the double array to sort
         * @param fromIndex the first index to sort (inclusive)
         * @param toIndex the last index to sort (exclusive)
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws ArrayIndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; a.length
         */
        public static native void sort(fabric.lang.arrays.doubleArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Finds the index of the median of three array elements.
         *
         * @param a the first index
         * @param b the second index
         * @param c the third index
         * @param d the array
         * @return the index (a, b, or c) which has the middle value of the
         three
         */
        private static native int med3(int a, int b, int c,
                                       fabric.lang.arrays.doubleArray d);
        
        /**
         * Swaps the elements at two locations of an array
         *
         * @param i the first index
         * @param j the second index
         * @param a the array
         */
        private static native void swap(int i, int j,
                                        fabric.lang.arrays.doubleArray a);
        
        /**
         * Swaps two ranges of an array.
         *
         * @param i the first range start
         * @param j the second range start
         * @param n the element count
         * @param a the array
         */
        private static native void vecswap(int i, int j, int n,
                                           fabric.lang.arrays.doubleArray a);
        
        /**
         * Performs a recursive modified quicksort.
         *
         * @param array the array to sort
         * @param from the start index (inclusive)
         * @param count the number of elements to sort
         */
        private static native void qsort(fabric.lang.arrays.doubleArray array,
                                         int from, int count);
        
        /**
         * Sort an array of Objects according to their natural ordering. The
         sort is
         * guaranteed to be stable, that is, equal elements will not be
         reordered.
         * The sort algorithm is a mergesort with the merge omitted if the last
         * element of one half comes before the first element of the other half.
         This
         * algorithm gives guaranteed O(n*log(n)) time, at the expense of making
         a
         * copy of the array.
         *
         * @param a the array to be sorted
         * @throws ClassCastException if any two elements are not mutually
         *         comparable
         * @throws NullPointerException if an element is null (since
         *         null.compareTo cannot work)
         * @see Comparable
         */
        public static native void sort(fabric.lang.arrays.ObjectArray a);
        
        /**
         * Sort an array of Objects according to a Comparator. The sort is
         * guaranteed to be stable, that is, equal elements will not be
         reordered.
         * The sort algorithm is a mergesort with the merge omitted if the last
         * element of one half comes before the first element of the other half.
         This
         * algorithm gives guaranteed O(n*log(n)) time, at the expense of making
         a
         * copy of the array.
         *
         * @param a the array to be sorted
         * @param c a Comparator to use in sorting the array; or null to
         indicate
         *        the elements' natural order
         * @throws ClassCastException if any two elements are not mutually
         *         comparable by the Comparator provided
         * @throws NullPointerException if a null element is compared with
         natural
         *         ordering (only possible when c is null)
         */
        public static native void sort(fabric.lang.arrays.ObjectArray a,
                                       fabric.util.Comparator c);
        
        /**
         * Sort an array of Objects according to their natural ordering. The
         sort is
         * guaranteed to be stable, that is, equal elements will not be
         reordered.
         * The sort algorithm is a mergesort with the merge omitted if the last
         * element of one half comes before the first element of the other half.
         This
         * algorithm gives guaranteed O(n*log(n)) time, at the expense of making
         a
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
        public static native void sort(fabric.lang.arrays.ObjectArray a,
                                       int fromIndex, int toIndex);
        
        /**
         * Sort an array of Objects according to a Comparator. The sort is
         * guaranteed to be stable, that is, equal elements will not be
         reordered.
         * The sort algorithm is a mergesort with the merge omitted if the last
         * element of one half comes before the first element of the other half.
         This
         * algorithm gives guaranteed O(n*log(n)) time, at the expense of making
         a
         * copy of the array.
         *
         * @param a the array to be sorted
         * @param fromIndex the index of the first element to be sorted
         * @param toIndex the index of the last element to be sorted plus one
         * @param c a Comparator to use in sorting the array; or null to
         indicate
         *        the elements' natural order
         * @throws ClassCastException if any two elements are not mutually
         *         comparable by the Comparator provided
         * @throws ArrayIndexOutOfBoundsException if fromIndex and toIndex
         *         are not in range.
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @throws NullPointerException if a null element is compared with
         natural
         *         ordering (only possible when c is null)
         */
        public static native void sort(fabric.lang.arrays.ObjectArray a,
                                       int fromIndex, int toIndex,
                                       fabric.util.Comparator c);
        
        /**
         * Returns a list "view" of the specified array. This method is intended
         to
         * make it easy to use the Collections API with existing array-based
         APIs and
         * programs. Changes in the list or the array show up in both places.
         The
         * list does not support element addition or removal, but does permit
         * value modification. The returned list implements both Serializable
         and
         * RandomAccess.
         *
         * @param a the array to return a view of (<code>null</code> not
         permitted)
         * @return a fixed-size list, changes to which "write through" to the
         array
         * 
         * @throws NullPointerException if <code>a</code> is <code>null</code>.
         * @see Serializable
         * @see RandomAccess
         * @see Arrays.ArrayList
         */
        public static native fabric.util.List asList(
          final fabric.lang.arrays.ObjectArray a);
        
        /** 
         * Returns the hashcode of an array of long numbers.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents longs in their wrapper class, <code>Long</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of long numbers for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.longArray v);
        
        /** 
         * Returns the hashcode of an array of integer numbers.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents ints in their wrapper class,
         <code>Integer</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of integer numbers for which the hash code should
         be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.intArray v);
        
        /** 
         * Returns the hashcode of an array of short numbers.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents shorts in their wrapper class,
         <code>Short</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of short numbers for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.shortArray v);
        
        /** 
         * Returns the hashcode of an array of characters.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents chars in their wrapper class,
         <code>Character</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of characters for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.charArray v);
        
        /** 
         * Returns the hashcode of an array of bytes.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents bytes in their wrapper class, <code>Byte</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of bytes for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.byteArray v);
        
        /** 
         * Returns the hashcode of an array of booleans.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents booleans in their wrapper class,
         * <code>Boolean</code>.  For <code>null</code>, 0 is returned.
         *
         * @param v an array of booleans for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.booleanArray v);
        
        /** 
         * Returns the hashcode of an array of floats.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents floats in their wrapper class,
         <code>Float</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of floats for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.floatArray v);
        
        /** 
         * Returns the hashcode of an array of doubles.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents doubles in their wrapper class,
         <code>Double</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of doubles for which the hash code should be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.doubleArray v);
        
        /** 
         * Returns the hashcode of an array of integer numbers.  If two arrays
         * are equal, according to <code>equals()</code>, they should have the
         * same hashcode.  The hashcode returned by the method is equal to that
         * obtained by the corresponding <code>List</code> object.  This has the
         same
         * data, but represents ints in their wrapper class,
         <code>Integer</code>.
         * For <code>null</code>, 0 is returned.
         *
         * @param v an array of integer numbers for which the hash code should
         be
         *          computed.
         * @return the hash code of the array, or 0 if null was given.
         * @since 1.5 
         */
        public static native int hashCode(fabric.lang.arrays.ObjectArray v);
        
        /** @since 1.5 */
        public static native int deepHashCode(fabric.lang.arrays.ObjectArray v);
        
        /** @since 1.5 */
        public static native boolean deepEquals(
          fabric.lang.arrays.ObjectArray v1, fabric.lang.arrays.ObjectArray v2);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.booleanArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.byteArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.charArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.shortArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.intArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.longArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.floatArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.doubleArray v);
        
        /**
         * Returns a String representation of the argument array.  Returns
         "null"
         * if <code>a</code> is null.
         * @param v the array to represent
         * @return a String representing this array
         * @since 1.5
         */
        public static native java.lang.String toString(
          fabric.lang.arrays.ObjectArray v);
        
        private static native void deepToString(
          fabric.lang.arrays.ObjectArray v, java.lang.StringBuilder b,
          fabric.util.HashSet seen);
        
        /** @since 1.5 */
        public static native java.lang.String deepToString(
          fabric.lang.arrays.ObjectArray v);
        
        public static native void arraycopy(fabric.lang.arrays.ObjectArray src,
                                            int srcPos,
                                            fabric.lang.arrays.ObjectArray dest,
                                            int destPos, int length);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
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
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Arrays._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.Arrays._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(fabric.util.Arrays._Static._Impl.class);
                $instance = (fabric.util.Arrays._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Arrays._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Arrays._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
