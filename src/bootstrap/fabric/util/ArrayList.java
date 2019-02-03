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
                        fabric.worker.transaction.TransactionManager $tm601 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled604 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff602 = 1;
                        boolean $doBackoff603 = true;
                        boolean $retry597 = true;
                        boolean $keepReads598 = false;
                        $label595: for (boolean $commit596 = false; !$commit596;
                                        ) {
                            if ($backoffEnabled604) {
                                if ($doBackoff603) {
                                    if ($backoff602 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff602));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e599) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff602 < 5000) $backoff602 *= 2;
                                }
                                $doBackoff603 = $backoff602 <= 32 ||
                                                  !$doBackoff603;
                            }
                            $commit596 = true;
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
                                catch (final Throwable $e599) {
                                    $tm601.getCurrentLog().checkRetrySignal();
                                    throw $e599;
                                }
                            }
                            catch (final fabric.worker.RetryException $e599) {
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
                                if ($e599.tid.isDescendantOf($currentTid600))
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
                            catch (final Throwable $e599) {
                                $commit596 = false;
                                if ($tm601.checkForStaleObjects())
                                    continue $label595;
                                $retry597 = false;
                                throw new fabric.worker.AbortException($e599);
                            }
                            finally {
                                if ($commit596) {
                                    fabric.common.TransactionID $currentTid600 =
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
                                        $currentTid600 = $tm601.getCurrentTid();
                                        if ($currentTid600 != null) {
                                            if ($e599.tid.equals(
                                                            $currentTid600) ||
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
    
    public static final byte[] $classHash = new byte[] { -93, 121, 15, -49, 18,
    -85, -26, 32, 43, 24, -23, 126, 39, -122, 57, -83, 92, -22, 10, -67, -99,
    -18, -40, -1, -127, 41, -13, -74, 67, 102, 17, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1afXBU1RW/uwmbDwL5AsQYQoAFy1dWtIKYVkkWAltWiCRQDdX07du7yZO3763v3Q0bK4wyU7VWcaYGip1CcaRSNei0HVs6nVTrVAvq6Gg7asfx449qUUor49QynSo95967X293H9mpmXnnvLx7z72/c+4555773o6fIVNsi8yPKRFN72CjCWp39CiRULhXsWwaDeqKbffD00F1amVo/6mj0TYv8YZJnaoYpqGpij5o2IxMD9+ijCgBg7LA1i2hzu2kRkXBDYo9zIh3e3fKIu0JUx8d0k0mJykYf9/SwNgPb274RQWpHyD1mtHHFKapQdNgNMUGSF2cxiPUsruiURodII0GpdE+ammKrt0GHU1jgDTZ2pChsKRF7S3UNvUR7NhkJxPU4nOmHyJ8E2BbSZWZFsBvEPCTTNMDYc1mnWHii2lUj9q3kt2kMkymxHRlCDrOCqe1CPARAz34HLrXagDTiikqTYtU7tCMKCNznRIZjf0boQOIVsUpGzYzU1UaCjwgTQKSrhhDgT5macYQdJ1iJmEWRlpKDgqdqhOKukMZooOMzHb26xVN0KuGmwVFGJnp7MZHgjVrcaxZzmqd2fS1vd8xNhhe4gHMUarqiL8ahNocQltojFrUUKkQrFsS3q/MmrjHSwh0nunoLPr8+vaza5a1PXtC9LmkSJ/NkVuoygbVI5Hpr7UGF6+uQBjVCdPW0BXyNOer2itbOlMJ8PZZmRGxsSPd+OyWF26843F62ktqQ8SnmnoyDl7VqJrxhKZTaz01qKUwGg2RGmpEg7w9RKrgPqwZVDzdHIvZlIVIpc4f+Uz+P5goBkOgiargXjNiZvo+obBhfp9KEEKq4CIeQrzLCFkzF+6XEzLlLCOhwLAZp4GInqQ7wb0DcFHFUocDELeWpi5XzcRowLbUgJU0mAY9xXOhfJdlKaPo1x0AIvFlDpZC5A07PR4w6lzVjNKIYsMKSW/p7tUhIDaYepRag6q+dyJEmice4h5Tg15ug6dym3hglVud+SFXdizZve7sk4MvCW9DWWkycF4BTqxkBhzgqcMA6oCU1AEpadyT6ggeCj3B/cRn84DKDFEHQ1yd0BUWM614ing8XJ8ZXJ4PC8u7A9IGjFu3uO+mb3z7nvkV4JmJnZW4WNDV74yTbHYJwZ0Czj+o1t996rOn9u8ysxHDiL8gkAslMRDnO41jmSqNQqLLDr+kXXl6cGKX34tJpAbyG1PAAyFZtDnnyAvIznRyQ2tMCZOpaANFx6Z0Rqplw5a5M/uEL/p0JE1i/dFYDoA8L369L3HwrVc+uoLvGOkUWp+Ta/so68wJWxysngdoY9b2/Ral0O+dA70P7jtz93ZueOixoNiEfqRBCFcF4tS0vnvi1r+89+6RP3uzi8WIL5GM6Jqa4ro0noc/D1xf4IWxhw+QQwYOyrhvzwR+AmdelMUGKUCHNATQbf9WI25GtZimRHSKnvLf+oUrnv773gax3Do8EcazyLILD5B9fnE3ueOlm//dxofxqLgFZe2X7SbyWnN25EwkpO58fc5Df1QOgudDVrK12yhPNITbg/AFvJzbYjmnKxxtX0UyX1irlT+vtAtzfA9ulllfHAiM/7gleM1pEeoZX8Qx5hUJ9W1KTphc/nj8X975vue9pGqANPB9WjHYNgWSFbjBAOy0dlA+DJNpee35u6bYIjozsdbqjIOcaZ1RkE0xcI+98b5WOL5wHDDERWiktXBdQYjvWckfxNbmBNIZKQ/hN1dzkQWcLkKymBuygpGqhKWNgGcxzElY7jBSo8XjSYZOwKdbCtWJzcucbVD8wEpvDa0tsgC9lhaHGBqRmyy9Z+ze8x17x4TziUpkQUExkCsjqhE+5TQ+bwpmmec2C5fo+dtTu377s113i526KX9fXWck48fe+PzljgPvnyySuSt1UyThBm6ZlfmGRUe8CjbE5ZI3FzFsqIRh8fYaJNciWZMx5Np1PV1bw/2Dwa7ermCo/0Yu2C3VRbaOkQqo5IqBqkVQzXBdA2AelPzeIqCuLw7Kw0GlMuN5cbwaOc73JN+TMx4T0Yr3m0oCmg1XEATfkvzFIoC+KQAh6S+cHqVOSv5c/vRRhSngBXMcZwPYbXl2Ea72ytFzF0/4PzonPMBZMeZ0/GT8vdOvT5vzJN+gKrFQ4EHlLLULK+m8ApmvZV2+GtVwvUlI227JU4wE/58SB/KMrJS+jGHEks+E5CQrFcxOHSI78aaLnRVIJhF7ZIXBFx7JIK6p41+8GS7uc168XcIgt2mGoqfDwKdTY0gUndyxbnZx2CVCCImeFcgi9Ip50iqKDYgrGIToppjG0jrWoI66CUfGjEVEiaWZHZmDXEQUyVahDSAbFZxRr+OOkd073j89Z3VwxwdDwhfnOnzR2fux68ZPrl+k/sBLKjKbRMFxLV+oM39rqLUonDaN/rwNol2szCQt67L17nZpuwPJbbCyKpo5bc+GrPmFfwlb8uyRymaB2zPhw/988pjxieQf52SBnH0/s8gNuRV3WBY2LenW2bmtWxQjasa7VAhm3t6Cu8qcUqdKvqMc2TN2KLr5pyu8UtMe8BxmJpbrdITqOXBm8/udGVUwl5FVcG2EhPC25Mdy02E2iTqskEkiKDIu+aNOKyx3uiSPBz7qmMtK7UfyANRtwi5+tIs/U535s6AcqiyF6wbI8l+R3FeeKigyRfCa86VVyUV60KXtJ0gOlK8FLkiMkKmvSv5weVqgyGHJf3TBBUm74KxcF8zWyMIBkT7qounjSB4uX9MWuG4lpO6o5PvL0xRF9kn+wOTW6+cubb9EMs7g7AYlW7/ZB4VEsWKncsTUosW0waprDyH1qyWfWZ42KDJD8ullBNKEi0rPIDnOyHRq2HDyDSoJRdXYKD59upgKWKvtJaRhoeSXlKcCirRIPmNyC/KCS9sJJL/PrelKOdEBmPC45EfLw4wij0p+eHKYX3Vpew3Ji3BK0ex18QQbLeZBVRHT1KliFNNmAVyPENL4lOT7ytMGRcYk3zspJ4rwUd92UekdJG8wUi0PpbzTn4qBb4MLgDe9LfkL5YFHkeclf6YM8H91Af8hkvdwPYwoTW2OlXSj+XD9jpAZ10t+VXnYUWSV5CvKwH7GBfs/kZxiZCps9ix0Afxgew9UJa3vSn6yLPxc5ITkz00Kv8g+n7ngP4fkLBwPhyivdyLFcMNe7ZkJ8Mclv7883Chyn+R3lcbtzdb2m7LGP18avIfr+B8Ab7uAR6P7CWlfLPmM8sCjSLPk0ybvNJ5qF9y1SCoAtxKNlgzURTDcSvD5TZKvKA83ilwm+ZIyje5pdAHfjKQuC77oJjWP4Gs54t8k+crywKPIlZIHJu/pnhYX3K1IoIryWTRujtCS/gJe6oHqcuExycsr7bjIYcldSrtcZAtc2hYiaeMHIjihl7Q3FAOeOASqKvn68kCjSI/kayZl76Mc3TIX5B1ILgV7g5906XpJP0cnvZ2QJQnJQ+VBR5ENkndP3s8F/itd8K9Ccpkr/jrsfBVMvh8qy+OS31cCP6e5p2U4ACYsk0ERT6OO92dT5Vjfl3zP5BUTgbDGRTG0kmc1bFkiEOAsO0SLORZ/EQcFs+cxQgLHJXerdQrfwnGRMcnvL61EQSCHXPBvRLKWkWZ1mKo7us2kEQ0Zqp60tRF3PX5DyOXzBF/xRXl6oMjnkn9Whh5bXPToR3Jdnh7rUi56cDC4i71MyJUrJa+fpLeRoq9lCgJ6uwvem5BsY6RR+A3ERAhfaRpK0djIoD0FIdIk+KrTBWhxzBsuhCriggr3IM8gR4WF7wVQ8YwzB9BBDX9tq+TVZWUcLlIluae0L+SC3OHSFkcSg3D0a4bGwkpEvg6KpCBBZA7o+BnskiJfouVvItTgH+iRDzYum1niK/Tsgl+pSLknD9VXX3Ro65vi7XX69w41YVIdS+p67ueinHtfwqIxjZuqRnw8SnBVLNAi5x0FnAqRoTaehOiRhHwqeuB/I9xwLZxwnZ3v2boiNrMUlWXexvHZWpIW/vhm/NOLzvmq+9/nn0HBmu2PjNa/2vTEh+1LZ3+0+9K7Vh/71se1Ewf/8db5Oxd/+qtgrDH+P11tMW8UJAAA";
}
