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
                        fabric.worker.transaction.TransactionManager $tm579 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled582 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff580 = 1;
                        boolean $doBackoff581 = true;
                        boolean $retry575 = true;
                        boolean $keepReads576 = false;
                        $label573: for (boolean $commit574 = false; !$commit574;
                                        ) {
                            if ($backoffEnabled582) {
                                if ($doBackoff581) {
                                    if ($backoff580 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff580));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e577) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff580 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff580 =
                                          java.lang.Math.
                                            min(
                                              $backoff580 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff581 = $backoff580 <= 32 ||
                                                  !$doBackoff581;
                            }
                            $commit574 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.ArrayList._Static._Proxy.$instance.
                                  set$serialVersionUID((long)
                                                         8683452581122892189L);
                                fabric.util.ArrayList._Static._Proxy.$instance.
                                  set$DEFAULT_CAPACITY((int) 10);
                            }
                            catch (final fabric.worker.RetryException $e577) {
                                $commit574 = false;
                                continue $label573;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e577) {
                                $commit574 = false;
                                $retry575 = false;
                                $keepReads576 = $e577.keepReads;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid ==
                                      null ||
                                      !$e577.tid.isDescendantOf(
                                                   $currentTid578)) {
                                    throw $e577;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e577);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e577) {
                                $commit574 = false;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid.isDescendantOf($currentTid578))
                                    continue $label573;
                                if ($currentTid578.parent != null) {
                                    $retry575 = false;
                                    throw $e577;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e577) {
                                $commit574 = false;
                                $retry575 = false;
                                if ($tm579.inNestedTxn()) {
                                    $keepReads576 = true;
                                }
                                throw $e577;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($commit574) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e577) {
                                        $commit574 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e577) {
                                        $commit574 = false;
                                        $retry575 = false;
                                        $keepReads576 = $e577.keepReads;
                                        if ($e577.tid ==
                                              null ||
                                              !$e577.tid.isDescendantOf(
                                                           $currentTid578))
                                            throw $e577;
                                        throw new fabric.worker.
                                                UserAbortException($e577);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e577) {
                                        $commit574 = false;
                                        $currentTid578 = $tm579.getCurrentTid();
                                        if ($currentTid578 != null) {
                                            if ($e577.tid.equals(
                                                            $currentTid578) ||
                                                  !$e577.tid.
                                                  isDescendantOf(
                                                    $currentTid578)) {
                                                throw $e577;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm579.inNestedTxn() &&
                                          $tm579.checkForStaleObjects()) {
                                        $retry575 = true;
                                        $keepReads576 = false;
                                    }
                                    if ($keepReads576) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e577) {
                                            $currentTid578 = $tm579.getCurrentTid();
                                            if ($currentTid578 != null &&
                                                  ($e577.tid.equals($currentTid578) || !$e577.tid.isDescendantOf($currentTid578))) {
                                                throw $e577;
                                            } else {
                                                $retry575 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit574) {
                                    {  }
                                    if ($retry575) { continue $label573; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 23, 53, 31, -53, 73, 3,
    -24, -112, -50, -104, 47, 88, 77, -120, 14, -62, 53, 59, -95, -78, 95, 94,
    -19, 29, 62, 3, -72, -122, 57, -26, 112, -94 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
