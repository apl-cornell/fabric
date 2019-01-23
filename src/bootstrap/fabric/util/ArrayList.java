package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * An array-backed implementation of the List interface.  This implements
 * all optional list operations, and permits null elements, so that it is
 * better than Vector, which it replaces. Random access is roughly constant
 * time, and iteration is roughly linear time, so it is nice and fast, with
 * less overhead than a LinkedList.
 * <p>
 *
 * Each list has a capacity, and as the array reaches that capacity it
 * is automatically transferred to a larger array. You also have access to
 * ensureCapacity and trimToSize to control the backing array's size, avoiding
 * reallocation or wasted memory.
 * <p>
 *
 * ArrayList is not synchronized, so if you need multi-threaded access,
 * consider using:<br>
 * <code>List l = Collections.synchronizedList(new ArrayList(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Jon A. Zeppieri
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see LinkedList
 * @see Vector
 * @see Collections#synchronizedList(List)
 * @see AbstractList
 * @status updated to 1.4
 */
public interface ArrayList
  extends fabric.util.List, fabric.util.RandomAccess, fabric.util.AbstractList
{
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.lang.arrays.ObjectArray get$data();
    
    public fabric.lang.arrays.ObjectArray set$data(
      fabric.lang.arrays.ObjectArray val);
    
    /**
     * Construct a new ArrayList with the supplied initial capacity.
     *
     * @param capacity initial capacity of this ArrayList
     * @throws IllegalArgumentException if capacity is negative
     */
    public fabric.util.ArrayList fabric$util$ArrayList$(int capacity);
    
    /**
     * Construct a new ArrayList with the default capacity (16).
     */
    public fabric.util.ArrayList fabric$util$ArrayList$();
    
    /**
     * Construct a new ArrayList, and initialize it with the elements
     * in the supplied Collection. The initial capacity is 110% of the
     * Collection's size.
     *
     * @param c the collection whose elements will initialize this list
     * @throws NullPointerException if c is null
     */
    public fabric.util.ArrayList fabric$util$ArrayList$(
      fabric.util.Collection c);
    
    /**
     * Trims the capacity of this List to be equal to its size;
     * a memory saver.
     */
    public void trimToSize();
    
    /**
     * Guarantees that this list will have at least enough capacity to
     * hold minCapacity elements. This implementation will grow the list to
     * max(current * 2, minCapacity) if (minCapacity &gt; current). The JCL says
     * explictly that "this method increases its capacity to minCap", while
     * the JDK 1.3 online docs specify that the list will grow to at least the
     * size specified.
     *
     * @param minCapacity the minimum guaranteed capacity
     */
    public void ensureCapacity(int minCapacity);
    
    /**
     * Returns the number of elements in this list.
     *
     * @return the list size
     */
    public int size();
    
    /**
     * Checks if the list is empty.
     *
     * @return true if there are no elements
     */
    public boolean isEmpty();
    
    /**
     * Returns true iff element is in this ArrayList.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return true if the list contains e
     */
    public boolean contains(fabric.lang.Object e);
    
    /**
     * Returns the lowest index at which element appears in this List, or
     * -1 if it does not appear.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return the index where e was found
     */
    public int indexOf(fabric.lang.Object e);
    
    /**
     * Returns the highest index at which element appears in this List, or
     * -1 if it does not appear.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return the index where e was found
     */
    public int lastIndexOf(fabric.lang.Object e);
    
    /**
     * Retrieves the element at the user-supplied index.
     *
     * @param index the index of the element we are fetching
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object get(int index);
    
    /**
     * Sets the element at the specified index.  The new element, e,
     * can be an object of any type or null.
     *
     * @param index the index at which the element is being set
     * @param e the element to be set
     * @return the element previously at the specified index
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= 0
     */
    public fabric.lang.Object set(int index, fabric.lang.Object e);
    
    /**
     * Appends the supplied element to the end of this list.
     * The element, e, can be an object of any type or null.
     *
     * @param e the element to be appended to this list
     * @return true, the add will always succeed
     */
    public boolean add(fabric.lang.Object e);
    
    /**
     * Adds the supplied element at the specified index, shifting all
     * elements currently at that index or higher one to the right.
     * The element, e, can be an object of any type or null.
     *
     * @param index the index at which the element is being added
     * @param e the item being added
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public void add(int index, fabric.lang.Object e);
    
    /**
     * Removes the element at the user-supplied index.
     *
     * @param index the index of the element to be removed
     * @return the removed Object
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object remove(int index);
    
    /**
     * Removes all elements from this List
     */
    public void clear();
    
    /**
     * Add each element in the supplied Collection to this List. It is undefined
     * what happens if you modify the list while this is taking place; for
     * example, if the collection contains this list.  c can contain objects
     * of any type, as well as null values.
     *
     * @param c a Collection containing elements to be added to this List
     * @return true if the list was modified, in other words c is not empty
     * @throws NullPointerException if c is null
     */
    public boolean addAll(fabric.util.Collection c);
    
    /**
     * Add all elements in the supplied collection, inserting them beginning
     * at the specified index.  c can contain objects of any type, as well
     * as null values.
     *
     * @param index the index at which the elements will be inserted
     * @param c the Collection containing the elements to be inserted
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; 0
     * @throws NullPointerException if c is null
     */
    public boolean addAll(int index, fabric.util.Collection c);
    
    /**
     * Removes all elements in the half-open interval [fromIndex, toIndex).
     * Does nothing when toIndex is equal to fromIndex.
     *
     * @param fromIndex the first index which will be removed
     * @param toIndex one greater than the last index which will be removed
     * @throws IndexOutOfBoundsException if fromIndex &gt; toIndex
     */
    public void removeRange(int fromIndex, int toIndex);
    
    /**
     * Remove from this list all elements contained in the given collection.
     * This is not public, due to Sun's API, but this performs in linear
     * time while the default behavior of AbstractList would be quadratic.
     *
     * @param c the collection to filter out
     * @return true if this list changed
     * @throws NullPointerException if c is null
     */
    public boolean removeAllInternal(fabric.util.Collection c);
    
    /**
     * Retain in this vector only the elements contained in the given
     collection.
     * This is not public, due to Sun's API, but this performs in linear
     * time while the default behavior of AbstractList would be quadratic.
     *
     * @param c the collection to filter by
     * @return true if this vector changed
     * @throws NullPointerException if c is null
     * @since 1.2
     */
    public boolean retainAllInternal(fabric.util.Collection c);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.ArrayList
    {
        
        public int get$size() {
            return ((fabric.util.ArrayList._Impl) fetch()).get$size();
        }
        
        public int set$size(int val) {
            return ((fabric.util.ArrayList._Impl) fetch()).set$size(val);
        }
        
        public int postInc$size() {
            return ((fabric.util.ArrayList._Impl) fetch()).postInc$size();
        }
        
        public int postDec$size() {
            return ((fabric.util.ArrayList._Impl) fetch()).postDec$size();
        }
        
        public fabric.lang.arrays.ObjectArray get$data() {
            return ((fabric.util.ArrayList._Impl) fetch()).get$data();
        }
        
        public fabric.lang.arrays.ObjectArray set$data(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.util.ArrayList._Impl) fetch()).set$data(val);
        }
        
        public native fabric.util.ArrayList fabric$util$ArrayList$(int arg1);
        
        public native fabric.util.ArrayList fabric$util$ArrayList$();
        
        public native fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection arg1);
        
        public native void trimToSize();
        
        public native void ensureCapacity(int arg1);
        
        public _Proxy(ArrayList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.ArrayList
    {
        
        public int get$size() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.size;
        }
        
        public int set$size(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.size = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp - 1));
            return tmp;
        }
        
        /**
         * The number of elements in this list.
         * @serial the list size
         */
        private int size;
        
        public fabric.lang.arrays.ObjectArray get$data() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.data;
        }
        
        public fabric.lang.arrays.ObjectArray set$data(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.data = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * Where the data is stored.
         */
        private fabric.lang.arrays.ObjectArray data;
        
        /**
         * Construct a new ArrayList with the supplied initial capacity.
         *
         * @param capacity initial capacity of this ArrayList
         * @throws IllegalArgumentException if capacity is negative
         */
        public native fabric.util.ArrayList fabric$util$ArrayList$(
          int capacity);
        
        /**
         * Construct a new ArrayList with the default capacity (16).
         */
        public native fabric.util.ArrayList fabric$util$ArrayList$();
        
        /**
         * Construct a new ArrayList, and initialize it with the elements
         * in the supplied Collection. The initial capacity is 110% of the
         * Collection's size.
         *
         * @param c the collection whose elements will initialize this list
         * @throws NullPointerException if c is null
         */
        public native fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection c);
        
        /**
         * Trims the capacity of this List to be equal to its size;
         * a memory saver.
         */
        public native void trimToSize();
        
        /**
         * Guarantees that this list will have at least enough capacity to
         * hold minCapacity elements. This implementation will grow the list to
         * max(current * 2, minCapacity) if (minCapacity &gt; current). The JCL
         says
         * explictly that "this method increases its capacity to minCap", while
         * the JDK 1.3 online docs specify that the list will grow to at least
         the
         * size specified.
         *
         * @param minCapacity the minimum guaranteed capacity
         */
        public native void ensureCapacity(int minCapacity);
        
        /**
         * Returns the number of elements in this list.
         *
         * @return the list size
         */
        public native int size();
        
        /**
         * Checks if the list is empty.
         *
         * @return true if there are no elements
         */
        public native boolean isEmpty();
        
        /**
         * Returns true iff element is in this ArrayList.
         *
         * @param e the element whose inclusion in the List is being tested
         * @return true if the list contains e
         */
        public native boolean contains(fabric.lang.Object e);
        
        /**
         * Returns the lowest index at which element appears in this List, or
         * -1 if it does not appear.
         *
         * @param e the element whose inclusion in the List is being tested
         * @return the index where e was found
         */
        public native int indexOf(fabric.lang.Object e);
        
        /**
         * Returns the highest index at which element appears in this List, or
         * -1 if it does not appear.
         *
         * @param e the element whose inclusion in the List is being tested
         * @return the index where e was found
         */
        public native int lastIndexOf(fabric.lang.Object e);
        
        /**
         * Retrieves the element at the user-supplied index.
         *
         * @param index the index of the element we are fetching
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object get(int index);
        
        /**
         * Sets the element at the specified index.  The new element, e,
         * can be an object of any type or null.
         *
         * @param index the index at which the element is being set
         * @param e the element to be set
         * @return the element previously at the specified index
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= 0
         */
        public native fabric.lang.Object set(int index, fabric.lang.Object e);
        
        /**
         * Appends the supplied element to the end of this list.
         * The element, e, can be an object of any type or null.
         *
         * @param e the element to be appended to this list
         * @return true, the add will always succeed
         */
        public native boolean add(fabric.lang.Object e);
        
        /**
         * Adds the supplied element at the specified index, shifting all
         * elements currently at that index or higher one to the right.
         * The element, e, can be an object of any type or null.
         *
         * @param index the index at which the element is being added
         * @param e the item being added
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public native void add(int index, fabric.lang.Object e);
        
        /**
         * Removes the element at the user-supplied index.
         *
         * @param index the index of the element to be removed
         * @return the removed Object
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object remove(int index);
        
        /**
         * Removes all elements from this List
         */
        public native void clear();
        
        /**
         * Add each element in the supplied Collection to this List. It is
         undefined
         * what happens if you modify the list while this is taking place; for
         * example, if the collection contains this list.  c can contain objects
         * of any type, as well as null values.
         *
         * @param c a Collection containing elements to be added to this List
         * @return true if the list was modified, in other words c is not empty
         * @throws NullPointerException if c is null
         */
        public native boolean addAll(fabric.util.Collection c);
        
        /**
         * Add all elements in the supplied collection, inserting them beginning
         * at the specified index.  c can contain objects of any type, as well
         * as null values.
         *
         * @param index the index at which the elements will be inserted
         * @param c the Collection containing the elements to be inserted
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; 0
         * @throws NullPointerException if c is null
         */
        public native boolean addAll(int index, fabric.util.Collection c);
        
        /**
         * Removes all elements in the half-open interval [fromIndex, toIndex).
         * Does nothing when toIndex is equal to fromIndex.
         *
         * @param fromIndex the first index which will be removed
         * @param toIndex one greater than the last index which will be removed
         * @throws IndexOutOfBoundsException if fromIndex &gt; toIndex
         */
        public native void removeRange(int fromIndex, int toIndex);
        
        /**
         * Checks that the index is in the range of possible elements
         (inclusive).
         *
         * @param index the index to check
         * @throws IndexOutOfBoundsException if index &gt; size
         */
        private native void checkBoundInclusive(int index);
        
        /**
         * Checks that the index is in the range of existing elements
         (exclusive).
         *
         * @param index the index to check
         * @throws IndexOutOfBoundsException if index &gt;= size
         */
        private native void checkBoundExclusive(int index);
        
        /**
         * Remove from this list all elements contained in the given collection.
         * This is not public, due to Sun's API, but this performs in linear
         * time while the default behavior of AbstractList would be quadratic.
         *
         * @param c the collection to filter out
         * @return true if this list changed
         * @throws NullPointerException if c is null
         */
        public native boolean removeAllInternal(fabric.util.Collection c);
        
        /**
         * Retain in this vector only the elements contained in the given
         collection.
         * This is not public, due to Sun's API, but this performs in linear
         * time while the default behavior of AbstractList would be quadratic.
         *
         * @param c the collection to filter by
         * @return true if this vector changed
         * @throws NullPointerException if c is null
         * @since 1.2
         */
        public native boolean retainAllInternal(fabric.util.Collection c);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.ArrayList._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.size);
            $writeRef($getStore(), this.data, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.size = in.readInt();
            this.data = (fabric.lang.arrays.ObjectArray)
                          $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.ArrayList._Impl src =
              (fabric.util.ArrayList._Impl) other;
            this.size = src.size;
            this.data = src.data;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        public int get$DEFAULT_CAPACITY();
        
        public int set$DEFAULT_CAPACITY(int val);
        
        public int postInc$DEFAULT_CAPACITY();
        
        public int postDec$DEFAULT_CAPACITY();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.ArrayList._Static
        {
            
            public long get$serialVersionUID() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public int get$DEFAULT_CAPACITY() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  get$DEFAULT_CAPACITY();
            }
            
            public int set$DEFAULT_CAPACITY(int val) {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  set$DEFAULT_CAPACITY(val);
            }
            
            public int postInc$DEFAULT_CAPACITY() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  postInc$DEFAULT_CAPACITY();
            }
            
            public int postDec$DEFAULT_CAPACITY() {
                return ((fabric.util.ArrayList._Static._Impl) fetch()).
                  postDec$DEFAULT_CAPACITY();
            }
            
            public _Proxy(fabric.util.ArrayList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.ArrayList._Static $instance;
            
            static {
                fabric.
                  util.
                  ArrayList.
                  _Static.
                  _Impl impl =
                  (fabric.util.ArrayList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.ArrayList._Static._Impl.class);
                $instance = (fabric.util.ArrayList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.ArrayList._Static
        {
            
            public long get$serialVersionUID() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.serialVersionUID;
            }
            
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
            
            /**
             * Compatible with JDK 1.2
             */
            private long serialVersionUID;
            
            public int get$DEFAULT_CAPACITY() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.DEFAULT_CAPACITY;
            }
            
            public int set$DEFAULT_CAPACITY(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_CAPACITY = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$DEFAULT_CAPACITY() {
                int tmp = this.get$DEFAULT_CAPACITY();
                this.set$DEFAULT_CAPACITY((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$DEFAULT_CAPACITY() {
                int tmp = this.get$DEFAULT_CAPACITY();
                this.set$DEFAULT_CAPACITY((int) (tmp - 1));
                return tmp;
            }
            
            /**
             * The default capacity for new ArrayLists.
             */
            private int DEFAULT_CAPACITY;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.serialVersionUID);
                out.writeInt(this.DEFAULT_CAPACITY);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
                this.DEFAULT_CAPACITY = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.ArrayList._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
