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
                        fabric.worker.transaction.TransactionManager $tm591 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled594 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff592 = 1;
                        boolean $doBackoff593 = true;
                        boolean $retry587 = true;
                        boolean $keepReads588 = false;
                        $label585: for (boolean $commit586 = false; !$commit586;
                                        ) {
                            if ($backoffEnabled594) {
                                if ($doBackoff593) {
                                    if ($backoff592 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff592));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e589) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff592 < 5000) $backoff592 *= 2;
                                }
                                $doBackoff593 = $backoff592 <= 32 ||
                                                  !$doBackoff593;
                            }
                            $commit586 = true;
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
                                         RetryException $e589) {
                                    throw $e589;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e589) {
                                    throw $e589;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e589) {
                                    throw $e589;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e589) {
                                    throw $e589;
                                }
                                catch (final Throwable $e589) {
                                    $tm591.getCurrentLog().checkRetrySignal();
                                    throw $e589;
                                }
                            }
                            catch (final fabric.worker.RetryException $e589) {
                                $commit586 = false;
                                continue $label585;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e589) {
                                $commit586 = false;
                                $retry587 = false;
                                $keepReads588 = $e589.keepReads;
                                if ($tm591.checkForStaleObjects()) {
                                    $retry587 = true;
                                    $keepReads588 = false;
                                    continue $label585;
                                }
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid ==
                                      null ||
                                      !$e589.tid.isDescendantOf(
                                                   $currentTid590)) {
                                    throw $e589;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e589);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e589) {
                                $commit586 = false;
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid.isDescendantOf($currentTid590))
                                    continue $label585;
                                if ($currentTid590.parent != null) {
                                    $retry587 = false;
                                    throw $e589;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e589) {
                                $commit586 = false;
                                if ($tm591.checkForStaleObjects())
                                    continue $label585;
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid.isDescendantOf($currentTid590)) {
                                    $retry587 = true;
                                }
                                else if ($currentTid590.parent != null) {
                                    $retry587 = false;
                                    throw $e589;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e589) {
                                $commit586 = false;
                                if ($tm591.checkForStaleObjects())
                                    continue $label585;
                                $retry587 = false;
                                throw new fabric.worker.AbortException($e589);
                            }
                            finally {
                                if ($commit586) {
                                    fabric.common.TransactionID $currentTid590 =
                                      $tm591.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e589) {
                                        $commit586 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e589) {
                                        $commit586 = false;
                                        $retry587 = false;
                                        $keepReads588 = $e589.keepReads;
                                        if ($tm591.checkForStaleObjects()) {
                                            $retry587 = true;
                                            $keepReads588 = false;
                                            continue $label585;
                                        }
                                        if ($e589.tid ==
                                              null ||
                                              !$e589.tid.isDescendantOf(
                                                           $currentTid590))
                                            throw $e589;
                                        throw new fabric.worker.
                                                UserAbortException($e589);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e589) {
                                        $commit586 = false;
                                        $currentTid590 = $tm591.getCurrentTid();
                                        if ($currentTid590 != null) {
                                            if ($e589.tid.equals(
                                                            $currentTid590) ||
                                                  !$e589.tid.
                                                  isDescendantOf(
                                                    $currentTid590)) {
                                                throw $e589;
                                            }
                                        }
                                    }
                                } else if ($keepReads588) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit586) {
                                    {  }
                                    if ($retry587) { continue $label585; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, 109, -87, -76, -57,
    115, 105, -92, -78, -24, 113, 38, 100, -35, 127, 75, -60, -68, -62, 78, 75,
    -82, -58, -76, -123, 32, -78, -56, 47, 77, 19, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
