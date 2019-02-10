package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
  extends fabric.util.List, fabric.util.RandomAccess, fabric.util.AbstractList {
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    public fabric.lang.arrays.ObjectArray get$data();
    
    public fabric.lang.arrays.ObjectArray set$data(fabric.lang.arrays.ObjectArray val);
    
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
    public fabric.util.ArrayList fabric$util$ArrayList$(fabric.util.Collection c);
    
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
   * Retain in this vector only the elements contained in the given collection.
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
      implements fabric.util.ArrayList {
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
        
        public fabric.util.ArrayList fabric$util$ArrayList$(int arg1) {
            return ((fabric.util.ArrayList) fetch()).fabric$util$ArrayList$(
                                                       arg1);
        }
        
        public fabric.util.ArrayList fabric$util$ArrayList$() {
            return ((fabric.util.ArrayList) fetch()).fabric$util$ArrayList$();
        }
        
        public fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection arg1) {
            return ((fabric.util.ArrayList) fetch()).fabric$util$ArrayList$(
                                                       arg1);
        }
        
        public void trimToSize() {
            ((fabric.util.ArrayList) fetch()).trimToSize();
        }
        
        public void ensureCapacity(int arg1) {
            ((fabric.util.ArrayList) fetch()).ensureCapacity(arg1);
        }
        
        public _Proxy(ArrayList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.ArrayList {
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
        public fabric.util.ArrayList fabric$util$ArrayList$(int capacity) {
            fabric$util$AbstractList$();
            if (capacity < 0) throw new java.lang.IllegalArgumentException();
            this.set$data(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           fabric.lang.Object._Proxy.class,
                                           capacity).$getProxy());
            return (fabric.util.ArrayList) this.$getProxy();
        }
        
        /**
   * Construct a new ArrayList with the default capacity (16).
   */
        public fabric.util.ArrayList fabric$util$ArrayList$() {
            fabric$util$ArrayList$(
              fabric.util.ArrayList._Static._Proxy.$instance.
                  get$DEFAULT_CAPACITY());
            return (fabric.util.ArrayList) this.$getProxy();
        }
        
        /**
   * Construct a new ArrayList, and initialize it with the elements
   * in the supplied Collection. The initial capacity is 110% of the
   * Collection's size.
   *
   * @param c the collection whose elements will initialize this list
   * @throws NullPointerException if c is null
   */
        public fabric.util.ArrayList fabric$util$ArrayList$(
          fabric.util.Collection c) {
            fabric$util$ArrayList$((int) (c.size() * 1.1F));
            addAll(c);
            return (fabric.util.ArrayList) this.$getProxy();
        }
        
        /**
   * Trims the capacity of this List to be equal to its size;
   * a memory saver.
   */
        public void trimToSize() {
            this.get$data().setLength((int) this.get$size());
        }
        
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
        public void ensureCapacity(int minCapacity) {
            int current = this.get$data().get$length();
            if (minCapacity > current)
                this.get$data().setLength((int)
                                            java.lang.Math.max(current * 2,
                                                               minCapacity));
        }
        
        /**
   * Returns the number of elements in this list.
   *
   * @return the list size
   */
        public int size() { return this.get$size(); }
        
        /**
   * Checks if the list is empty.
   *
   * @return true if there are no elements
   */
        public boolean isEmpty() { return this.get$size() == 0; }
        
        /**
   * Returns true iff element is in this ArrayList.
   *
   * @param e the element whose inclusion in the List is being tested
   * @return true if the list contains e
   */
        public boolean contains(fabric.lang.Object e) { return indexOf(e) != -1; }
        
        /**
   * Returns the lowest index at which element appears in this List, or
   * -1 if it does not appear.
   *
   * @param e the element whose inclusion in the List is being tested
   * @return the index where e was found
   */
        public int indexOf(fabric.lang.Object e) {
            for (int i = 0; i < this.get$size(); i++)
                if (fabric.util.AbstractCollection._Impl.equals(
                                                           e,
                                                           (fabric.lang.Object)
                                                             this.get$data(
                                                                    ).get(i)))
                    return i;
            return -1;
        }
        
        /**
   * Returns the highest index at which element appears in this List, or
   * -1 if it does not appear.
   *
   * @param e the element whose inclusion in the List is being tested
   * @return the index where e was found
   */
        public int lastIndexOf(fabric.lang.Object e) {
            for (int i = this.get$size() - 1; i >= 0; i--)
                if (fabric.util.AbstractCollection._Impl.equals(
                                                           e,
                                                           (fabric.lang.Object)
                                                             this.get$data(
                                                                    ).get(i)))
                    return i;
            return -1;
        }
        
        /**
   * Retrieves the element at the user-supplied index.
   *
   * @param index the index of the element we are fetching
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object get(int index) {
            ((fabric.util.ArrayList._Impl) this.fetch()).checkBoundExclusive(
                                                           index);
            return (fabric.lang.Object) this.get$data().get(index);
        }
        
        /**
   * Sets the element at the specified index.  The new element, e,
   * can be an object of any type or null.
   *
   * @param index the index at which the element is being set
   * @param e the element to be set
   * @return the element previously at the specified index
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= 0
   */
        public fabric.lang.Object set(int index, fabric.lang.Object e) {
            ((fabric.util.ArrayList._Impl) this.fetch()).checkBoundExclusive(
                                                           index);
            fabric.lang.Object result = (fabric.lang.Object)
                                          this.get$data().get(index);
            this.get$data().set(index, e);
            return result;
        }
        
        /**
   * Appends the supplied element to the end of this list.
   * The element, e, can be an object of any type or null.
   *
   * @param e the element to be appended to this list
   * @return true, the add will always succeed
   */
        public boolean add(fabric.lang.Object e) {
            this.postInc$modCount();
            if (this.get$size() == this.get$data().get$length())
                ensureCapacity(this.get$size() + 1);
            this.get$data().set(this.postInc$size(), e);
            return true;
        }
        
        /**
   * Adds the supplied element at the specified index, shifting all
   * elements currently at that index or higher one to the right.
   * The element, e, can be an object of any type or null.
   *
   * @param index the index at which the element is being added
   * @param e the item being added
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public void add(int index, fabric.lang.Object e) {
            ((fabric.util.ArrayList._Impl) this.fetch()).checkBoundInclusive(
                                                           index);
            this.postInc$modCount();
            if (this.get$size() == this.get$data().get$length())
                ensureCapacity(this.get$size() + 1);
            if (index != this.get$size())
                fabric.util.Arrays._Impl.arraycopy(this.get$data(), index,
                                                   this.get$data(), index + 1,
                                                   this.get$size() - index);
            this.get$data().set(index, e);
            this.postInc$size();
        }
        
        /**
   * Removes the element at the user-supplied index.
   *
   * @param index the index of the element to be removed
   * @return the removed Object
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object remove(int index) {
            ((fabric.util.ArrayList._Impl) this.fetch()).checkBoundExclusive(
                                                           index);
            fabric.lang.Object r = (fabric.lang.Object)
                                     this.get$data().get(index);
            this.postInc$modCount();
            if (index != this.set$size(this.get$size() - 1))
                fabric.util.Arrays._Impl.arraycopy(this.get$data(), index + 1,
                                                   this.get$data(), index,
                                                   this.get$size() - index);
            this.get$data().set(this.get$size(), null);
            return r;
        }
        
        /**
   * Removes all elements from this List
   */
        public void clear() {
            if (this.get$size() > 0) {
                this.postInc$modCount();
                for (int i = 0; i < this.get$size(); i++)
                    this.get$data().set(i, null);
                this.set$size((int) 0);
            }
        }
        
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
        public boolean addAll(fabric.util.Collection c) {
            return addAll(this.get$size(), c);
        }
        
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
        public boolean addAll(int index, fabric.util.Collection c) {
            ((fabric.util.ArrayList._Impl) this.fetch()).checkBoundInclusive(
                                                           index);
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int csize = c.size();
            this.postInc$modCount();
            if (csize + this.get$size() > this.get$data().get$length())
                ensureCapacity(this.get$size() + csize);
            int end = index + csize;
            if (this.get$size() > 0 && index != this.get$size())
                fabric.util.Arrays._Impl.arraycopy(this.get$data(), index,
                                                   this.get$data(), end,
                                                   this.get$size() - index);
            this.set$size((int) (this.get$size() + csize));
            for (; index < end; index++) this.get$data().set(index, itr.next());
            return csize > 0;
        }
        
        /**
   * Removes all elements in the half-open interval [fromIndex, toIndex).
   * Does nothing when toIndex is equal to fromIndex.
   *
   * @param fromIndex the first index which will be removed
   * @param toIndex one greater than the last index which will be removed
   * @throws IndexOutOfBoundsException if fromIndex &gt; toIndex
   */
        public void removeRange(int fromIndex, int toIndex) {
            int change = toIndex - fromIndex;
            if (change > 0) {
                this.postInc$modCount();
                fabric.util.Arrays._Impl.arraycopy(this.get$data(), toIndex,
                                                   this.get$data(), fromIndex,
                                                   this.get$size() - toIndex);
                this.set$size((int) (this.get$size() - change));
            } else if (change < 0)
                throw new java.lang.IndexOutOfBoundsException();
        }
        
        /**
   * Checks that the index is in the range of possible elements (inclusive).
   *
   * @param index the index to check
   * @throws IndexOutOfBoundsException if index &gt; size
   */
        private void checkBoundInclusive(int index) {
            if (index > this.get$size())
                throw new java.lang.IndexOutOfBoundsException(
                        "Index: " + index + ", Size: " + this.get$size());
        }
        
        /**
   * Checks that the index is in the range of existing elements (exclusive).
   *
   * @param index the index to check
   * @throws IndexOutOfBoundsException if index &gt;= size
   */
        private void checkBoundExclusive(int index) {
            if (index >= this.get$size())
                throw new java.lang.IndexOutOfBoundsException(
                        "Index: " + index + ", Size: " + this.get$size());
        }
        
        /**
   * Remove from this list all elements contained in the given collection.
   * This is not public, due to Sun's API, but this performs in linear
   * time while the default behavior of AbstractList would be quadratic.
   *
   * @param c the collection to filter out
   * @return true if this list changed
   * @throws NullPointerException if c is null
   */
        public boolean removeAllInternal(fabric.util.Collection c) {
            int i;
            int j;
            for (i = 0; i < this.get$size(); i++)
                if (c.contains((fabric.lang.Object) this.get$data().get(i)))
                    break;
            if (i == this.get$size()) return false;
            this.postInc$modCount();
            for (j = i++; i < this.get$size(); i++)
                if (!c.contains((fabric.lang.Object) this.get$data().get(i)))
                    this.get$data().set(j++,
                                        (fabric.lang.Object)
                                          this.get$data().get(i));
            this.set$size((int) (this.get$size() - (i - j)));
            return true;
        }
        
        /**
   * Retain in this vector only the elements contained in the given collection.
   * This is not public, due to Sun's API, but this performs in linear
   * time while the default behavior of AbstractList would be quadratic.
   *
   * @param c the collection to filter by
   * @return true if this vector changed
   * @throws NullPointerException if c is null
   * @since 1.2
   */
        public boolean retainAllInternal(fabric.util.Collection c) {
            int i;
            int j;
            for (i = 0; i < this.get$size(); i++)
                if (!c.contains((fabric.lang.Object) this.get$data().get(i)))
                    break;
            if (i == this.get$size()) return false;
            this.postInc$modCount();
            for (j = i++; i < this.get$size(); i++)
                if (c.contains((fabric.lang.Object) this.get$data().get(i)))
                    this.get$data().set(j++,
                                        (fabric.lang.Object)
                                          this.get$data().get(i));
            this.set$size((int) (this.get$size() - (i - j)));
            return true;
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.ArrayList) this.$getProxy();
        }
        
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
            this.size = in.readInt();
            this.data = (fabric.lang.arrays.ObjectArray)
                          $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.ArrayList._Impl src = (fabric.util.ArrayList._Impl)
                                                other;
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
          implements fabric.util.ArrayList._Static {
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
                  _Impl
                  impl =
                  (fabric.util.ArrayList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.ArrayList._Static._Impl.class);
                $instance = (fabric.util.ArrayList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.ArrayList._Static {
            public long get$serialVersionUID() { return this.serialVersionUID; }
            
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
            
            private long serialVersionUID;
            
            public int get$DEFAULT_CAPACITY() { return this.DEFAULT_CAPACITY; }
            
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
                this.serialVersionUID = in.readLong();
                this.DEFAULT_CAPACITY = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.ArrayList._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm557 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled560 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff558 = 1;
                        boolean $doBackoff559 = true;
                        boolean $retry553 = true;
                        boolean $keepReads554 = false;
                        $label551: for (boolean $commit552 = false; !$commit552;
                                        ) {
                            if ($backoffEnabled560) {
                                if ($doBackoff559) {
                                    if ($backoff558 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff558));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e555) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff558 < 5000) $backoff558 *= 2;
                                }
                                $doBackoff559 = $backoff558 <= 32 ||
                                                  !$doBackoff559;
                            }
                            $commit552 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.ArrayList._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 8683452581122892189L);
                                    fabric.util.ArrayList._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_CAPACITY((int) 10);
                                }
                                catch (final fabric.worker.
                                         RetryException $e555) {
                                    throw $e555;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e555) {
                                    throw $e555;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e555) {
                                    throw $e555;
                                }
                                catch (final Throwable $e555) {
                                    $tm557.getCurrentLog().checkRetrySignal();
                                    throw $e555;
                                }
                            }
                            catch (final fabric.worker.RetryException $e555) {
                                $commit552 = false;
                                continue $label551;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e555) {
                                $commit552 = false;
                                $retry553 = false;
                                $keepReads554 = $e555.keepReads;
                                if ($tm557.checkForStaleObjects()) {
                                    $retry553 = true;
                                    $keepReads554 = false;
                                    continue $label551;
                                }
                                fabric.common.TransactionID $currentTid556 =
                                  $tm557.getCurrentTid();
                                if ($e555.tid ==
                                      null ||
                                      !$e555.tid.isDescendantOf(
                                                   $currentTid556)) {
                                    throw $e555;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e555);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e555) {
                                $commit552 = false;
                                fabric.common.TransactionID $currentTid556 =
                                  $tm557.getCurrentTid();
                                if ($e555.tid.isDescendantOf($currentTid556))
                                    continue $label551;
                                if ($currentTid556.parent != null) {
                                    $retry553 = false;
                                    throw $e555;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e555) {
                                $commit552 = false;
                                if ($tm557.checkForStaleObjects())
                                    continue $label551;
                                $retry553 = false;
                                throw new fabric.worker.AbortException($e555);
                            }
                            finally {
                                if ($commit552) {
                                    fabric.common.TransactionID $currentTid556 =
                                      $tm557.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e555) {
                                        $commit552 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e555) {
                                        $commit552 = false;
                                        $retry553 = false;
                                        $keepReads554 = $e555.keepReads;
                                        if ($tm557.checkForStaleObjects()) {
                                            $retry553 = true;
                                            $keepReads554 = false;
                                            continue $label551;
                                        }
                                        if ($e555.tid ==
                                              null ||
                                              !$e555.tid.isDescendantOf(
                                                           $currentTid556))
                                            throw $e555;
                                        throw new fabric.worker.
                                                UserAbortException($e555);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e555) {
                                        $commit552 = false;
                                        $currentTid556 = $tm557.getCurrentTid();
                                        if ($currentTid556 != null) {
                                            if ($e555.tid.equals(
                                                            $currentTid556) ||
                                                  !$e555.tid.
                                                  isDescendantOf(
                                                    $currentTid556)) {
                                                throw $e555;
                                            }
                                        }
                                    }
                                } else if ($keepReads554) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit552) {
                                    {  }
                                    if ($retry553) { continue $label551; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, 87, -50, 117, -81,
    -16, 69, -26, -50, -96, 37, 63, 8, -75, -48, 56, -43, -2, 33, 79, -23, 56,
    -103, -18, -15, 65, 14, 15, -77, -87, 30, -114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
