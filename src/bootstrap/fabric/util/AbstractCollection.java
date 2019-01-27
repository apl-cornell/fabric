package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.lang.reflect.Array;

/**
 * A basic implementation of most of the methods in the Collection interface to
 * make it easier to create a collection. To create an unmodifiable Collection,
 * just subclass AbstractCollection and provide implementations of the
 * iterator(store) and size() methods. The Iterator returned by iterator(store) need only
 * provide implementations of hasNext() and next() (that is, it may throw an
 * UnsupportedOperationException if remove() is called). To create a modifiable
 * Collection, you must in addition provide an implementation of the
 * add(Object) method and the Iterator returned by iterator(store) must provide an
 * implementation of remove(). Other methods should be overridden if the
 * backing data structure allows for a more efficient implementation. The
 * precise implementation used by AbstractCollection is documented, so that
 * subclasses can tell which methods could be implemented more efficiently.
 * <p>
 *
 * The programmer should provide a no-argument constructor, and one that
 * accepts another Collection, as recommended by the Collection interface.
 * Unfortunately, there is no way to enforce this in Java.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see AbstractSet
 * @see AbstractList
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractCollection
  extends fabric.util.Collection, fabric.lang.Object {
    /**
   * The main constructor, for use by subclasses.
   */
    public fabric.util.AbstractCollection fabric$util$AbstractCollection$();
    
    /**
   * Return an Iterator over this collection. The iterator must provide the
   * hasNext and next methods and should in addition provide remove if the
   * collection is modifiable.
   *
   * @return an iterator
   */
    public abstract fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public fabric.util.Iterator iterator();
    
    /**
   * Return the number of elements in this collection. If there are more than
   * Integer.MAX_VALUE elements, return Integer.MAX_VALUE.
   *
   * @return the size
   */
    public abstract int size();
    
    /**
   * Add an object to the collection (optional operation). This implementation
   * always throws an UnsupportedOperationException - it should be
   * overridden if the collection is to be modifiable. If the collection
   * does not accept duplicates, simply return false. Collections may specify
   * limitations on what may be added.
   *
   * @param o the object to add
   * @return true if the add operation caused the Collection to change
   * @throws UnsupportedOperationException if the add operation is not
   *         supported on this collection
   * @throws NullPointerException if the collection does not support null
   * @throws ClassCastException if the object is of the wrong type
   * @throws IllegalArgumentException if some aspect of the object prevents
   *         it from being added
   */
    public boolean add(fabric.lang.Object o);
    
    /**
   * Add all the elements of a given collection to this collection (optional
   * operation). This implementation obtains an Iterator over the given
   * collection and iterates over it, adding each element with the
   * add(Object) method (thus this method will fail with an
   * UnsupportedOperationException if the add method does). The behavior is
   * unspecified if the specified collection is modified during the iteration,
   * including the special case of trying addAll(this) on a non-empty
   * collection.
   *
   * @param c the collection to add the elements of to this collection
   * @return true if the add operation caused the Collection to change
   * @throws UnsupportedOperationException if the add operation is not
   *         supported on this collection
   * @throws NullPointerException if the specified collection is null
   * @throws ClassCastException if the type of any element in c is
   *         not a valid type for addition.
   * @throws IllegalArgumentException if some aspect of any element
   *         in c prevents it being added.
   * @throws NullPointerException if any element in c is null and this
   *         collection doesn't allow null values.
   * @see #add(Object)
   */
    public boolean addAll(fabric.util.Collection c);
    
    /**
   * Remove all elements from the collection (optional operation). This
   * implementation obtains an iterator over the collection and calls next
   * and remove on it repeatedly (thus this method will fail with an
   * UnsupportedOperationException if the Iterator's remove method does)
   * until there are no more elements to remove.
   * Many implementations will have a faster way of doing this.
   *
   * @throws UnsupportedOperationException if the Iterator returned by
   *         iterator does not provide an implementation of remove
   * @see Iterator#remove()
   */
    public void clear();
    
    /**
   * Test whether this collection contains a given object. That is, if the
   * collection has an element e such that (o == null ? e == null :
   * o.equals(e)). This implementation obtains an iterator over the collection
   * and iterates over it, testing each element for equality with the given
   * object. If it is equal, true is returned. Otherwise false is returned when
   * the end of the collection is reached.
   *
   * @param o the object to remove from this collection
   * @return true if this collection contains an object equal to o
   */
    public boolean contains(fabric.lang.Object o);
    
    /**
   * Tests whether this collection contains all the elements in a given
   * collection. This implementation iterates over the given collection,
   * testing whether each element is contained in this collection. If any one
   * is not, false is returned. Otherwise true is returned.
   *
   * @param c the collection to test against
   * @return true if this collection contains all the elements in the given
   *         collection
   * @throws NullPointerException if the given collection is null
   * @see #contains(Object)
   */
    public boolean containsAll(fabric.util.Collection c);
    
    /**
   * Test whether this collection is empty. This implementation returns
   * size() == 0.
   *
   * @return true if this collection is empty.
   * @see #size()
   */
    public boolean isEmpty();
    
    /**
   * Remove a single instance of an object from this collection (optional
   * operation). That is, remove one element e such that
   * <code>(o == null ? e == null : o.equals(e))</code>, if such an element
   * exists. This implementation obtains an iterator over the collection
   * and iterates over it, testing each element for equality with the given
   * object. If it is equal, it is removed by the iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does). After the first element has been
   * removed, true is returned; if the end of the collection is reached, false
   * is returned.
   *
   * @param o the object to remove from this collection
   * @return true if the remove operation caused the Collection to change, or
   *         equivalently if the collection did contain o.
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @see Iterator#remove()
   */
    public boolean remove(fabric.lang.Object o);
    
    /**
   * Remove from this collection all its elements that are contained in a given
   * collection (optional operation). This implementation iterates over this
   * collection, and for each element tests if it is contained in the given
   * collection. If so, it is removed by the Iterator's remove method (thus
   * this method will fail with an UnsupportedOperationException if the
   * Iterator's remove method does).
   *
   * @param c the collection to remove the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
    public boolean removeAll(fabric.util.Collection c);
    
    /**
   * Remove from this collection all its elements that are contained in a given
   * collection (optional operation). This implementation iterates over this
   * collection, and for each element tests if it is contained in the given
   * collection. If so, it is removed by the Iterator's remove method (thus
   * this method will fail with an UnsupportedOperationException if the
   * Iterator's remove method does). This method is necessary for ArrayList,
   * which cannot publicly override removeAll but can optimize this call.
   *
   * @param c the collection to remove the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
    public boolean removeAllInternal(fabric.util.Collection c);
    
    /**
   * Remove from this collection all its elements that are not contained in a
   * given collection (optional operation). This implementation iterates over
   * this collection, and for each element tests if it is contained in the
   * given collection. If not, it is removed by the Iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does).
   *
   * @param c the collection to retain the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
    public boolean retainAll(fabric.util.Collection c);
    
    /**
   * Remove from this collection all its elements that are not contained in a
   * given collection (optional operation). This implementation iterates over
   * this collection, and for each element tests if it is contained in the
   * given collection. If not, it is removed by the Iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does). This method is necessary for
   * ArrayList, which cannot publicly override retainAll but can optimize
   * this call.
   *
   * @param c the collection to retain the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
    public boolean retainAllInternal(fabric.util.Collection c);
    
    /**
   * Return an array containing the elements of this collection. This
   * implementation creates an Object array of size size() and then iterates
   * over the collection, setting each element of the array from the value
   * returned by the iterator. The returned array is safe, and is not backed
   * by the collection.
   *
   * @return an array containing the elements of this collection
   */
    public fabric.lang.arrays.ObjectArray toArray();
    
    /**
   * Copy the collection into a given array if it will fit, or into a
   * dynamically created array of the same run-time type as the given array if
   * not. If there is space remaining in the array, the first element after the
   * end of the collection is set to null (this is only useful if the
   * collection is known to contain no null elements, however). This
   * implementation first tests whether the given array is large enough to hold
   * all the elements of the collection. If not, the reflection API is used to
   * allocate a new array of the same run-time type. Next an iterator is
   * obtained over the collection and the elements are placed in the array as
   * they are returned by the iterator. Finally the first spare element, if
   * any, of the array is set to null, and the created array is returned.
   * The returned array is safe; it is not backed by the collection. Note that
   * null may not mark the last element, if the collection allows null
   * elements.
   *
   * @param a the array to copy into, or of the correct run-time type
   * @return the array that was produced
   * @throws NullPointerException if the given array is null
   * @throws ArrayStoreException if the type of the array precludes holding
   *         one of the elements of the Collection
   */
    public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    /**
   * Creates a String representation of the Collection. The string returned is
   * of the form "[a, b, ...]" where a and b etc are the results of calling
   * toString on the elements of the collection. This implementation obtains an
   * Iterator over the Collection and adds each element to a StringBuffer as it
   * is returned by the iterator. "<this>" is inserted when the collection
   * contains itself (only works for direct containment, not for collections
   * inside collections).
   *
   * @return a String representation of the Collection
   */
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractCollection {
        public fabric.util.AbstractCollection fabric$util$AbstractCollection$(
          ) {
            return ((fabric.util.AbstractCollection) fetch()).
              fabric$util$AbstractCollection$();
        }
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.AbstractCollection) fetch()).iterator(arg1);
        }
        
        public fabric.util.Iterator iterator() {
            return ((fabric.util.AbstractCollection) fetch()).iterator();
        }
        
        public int size() {
            return ((fabric.util.AbstractCollection) fetch()).size();
        }
        
        public boolean add(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractCollection) fetch()).add(arg1);
        }
        
        public boolean addAll(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).addAll(arg1);
        }
        
        public void clear() {
            ((fabric.util.AbstractCollection) fetch()).clear();
        }
        
        public boolean contains(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractCollection) fetch()).contains(arg1);
        }
        
        public boolean containsAll(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).containsAll(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.AbstractCollection) fetch()).isEmpty();
        }
        
        public boolean remove(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractCollection) fetch()).remove(arg1);
        }
        
        public boolean removeAll(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).removeAll(arg1);
        }
        
        public boolean removeAllInternal(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).removeAllInternal(
                                                                arg1);
        }
        
        public boolean retainAll(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).retainAll(arg1);
        }
        
        public boolean retainAllInternal(fabric.util.Collection arg1) {
            return ((fabric.util.AbstractCollection) fetch()).retainAllInternal(
                                                                arg1);
        }
        
        public fabric.lang.arrays.ObjectArray toArray() {
            return ((fabric.util.AbstractCollection) fetch()).toArray();
        }
        
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.util.AbstractCollection) fetch()).toArray(arg1);
        }
        
        public static final boolean equals(fabric.lang.Object arg1,
                                           fabric.lang.Object arg2) {
            return fabric.util.AbstractCollection._Impl.equals(arg1, arg2);
        }
        
        public static final int hashCode(fabric.lang.Object arg1) {
            return fabric.util.AbstractCollection._Impl.hashCode(arg1);
        }
        
        public _Proxy(AbstractCollection._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractCollection {
        /**
   * The main constructor, for use by subclasses.
   */
        public fabric.util.AbstractCollection fabric$util$AbstractCollection$(
          ) {
            fabric$lang$Object$();
            return (fabric.util.AbstractCollection) this.$getProxy();
        }
        
        /**
   * Return an Iterator over this collection. The iterator must provide the
   * hasNext and next methods and should in addition provide remove if the
   * collection is modifiable.
   *
   * @return an iterator
   */
        public abstract fabric.util.Iterator iterator(
          fabric.worker.Store store);
        
        public fabric.util.Iterator iterator() {
            fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            return iterator(local);
        }
        
        /**
   * Return the number of elements in this collection. If there are more than
   * Integer.MAX_VALUE elements, return Integer.MAX_VALUE.
   *
   * @return the size
   */
        public abstract int size();
        
        /**
   * Add an object to the collection (optional operation). This implementation
   * always throws an UnsupportedOperationException - it should be
   * overridden if the collection is to be modifiable. If the collection
   * does not accept duplicates, simply return false. Collections may specify
   * limitations on what may be added.
   *
   * @param o the object to add
   * @return true if the add operation caused the Collection to change
   * @throws UnsupportedOperationException if the add operation is not
   *         supported on this collection
   * @throws NullPointerException if the collection does not support null
   * @throws ClassCastException if the object is of the wrong type
   * @throws IllegalArgumentException if some aspect of the object prevents
   *         it from being added
   */
        public boolean add(fabric.lang.Object o) {
            throw new java.lang.UnsupportedOperationException();
        }
        
        /**
   * Add all the elements of a given collection to this collection (optional
   * operation). This implementation obtains an Iterator over the given
   * collection and iterates over it, adding each element with the
   * add(Object) method (thus this method will fail with an
   * UnsupportedOperationException if the add method does). The behavior is
   * unspecified if the specified collection is modified during the iteration,
   * including the special case of trying addAll(this) on a non-empty
   * collection.
   *
   * @param c the collection to add the elements of to this collection
   * @return true if the add operation caused the Collection to change
   * @throws UnsupportedOperationException if the add operation is not
   *         supported on this collection
   * @throws NullPointerException if the specified collection is null
   * @throws ClassCastException if the type of any element in c is
   *         not a valid type for addition.
   * @throws IllegalArgumentException if some aspect of any element
   *         in c prevents it being added.
   * @throws NullPointerException if any element in c is null and this
   *         collection doesn't allow null values.
   * @see #add(Object)
   */
        public boolean addAll(fabric.util.Collection c) {
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            boolean modified = false;
            int pos = c.size();
            while (--pos >= 0) modified |= add(itr.next());
            return modified;
        }
        
        /**
   * Remove all elements from the collection (optional operation). This
   * implementation obtains an iterator over the collection and calls next
   * and remove on it repeatedly (thus this method will fail with an
   * UnsupportedOperationException if the Iterator's remove method does)
   * until there are no more elements to remove.
   * Many implementations will have a faster way of doing this.
   *
   * @throws UnsupportedOperationException if the Iterator returned by
   *         iterator does not provide an implementation of remove
   * @see Iterator#remove()
   */
        public void clear() {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0) {
                itr.next();
                itr.remove();
            }
        }
        
        /**
   * Test whether this collection contains a given object. That is, if the
   * collection has an element e such that (o == null ? e == null :
   * o.equals(e)). This implementation obtains an iterator over the collection
   * and iterates over it, testing each element for equality with the given
   * object. If it is equal, true is returned. Otherwise false is returned when
   * the end of the collection is reached.
   *
   * @param o the object to remove from this collection
   * @return true if this collection contains an object equal to o
   */
        public boolean contains(fabric.lang.Object o) {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0)
                if (fabric.util.AbstractCollection._Impl.equals(o, itr.next()))
                    return true;
            return false;
        }
        
        /**
   * Tests whether this collection contains all the elements in a given
   * collection. This implementation iterates over the given collection,
   * testing whether each element is contained in this collection. If any one
   * is not, false is returned. Otherwise true is returned.
   *
   * @param c the collection to test against
   * @return true if this collection contains all the elements in the given
   *         collection
   * @throws NullPointerException if the given collection is null
   * @see #contains(Object)
   */
        public boolean containsAll(fabric.util.Collection c) {
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = c.size();
            while (--pos >= 0) if (!contains(itr.next())) return false;
            return true;
        }
        
        /**
   * Test whether this collection is empty. This implementation returns
   * size() == 0.
   *
   * @return true if this collection is empty.
   * @see #size()
   */
        public boolean isEmpty() { return size() == 0; }
        
        /**
   * Remove a single instance of an object from this collection (optional
   * operation). That is, remove one element e such that
   * <code>(o == null ? e == null : o.equals(e))</code>, if such an element
   * exists. This implementation obtains an iterator over the collection
   * and iterates over it, testing each element for equality with the given
   * object. If it is equal, it is removed by the iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does). After the first element has been
   * removed, true is returned; if the end of the collection is reached, false
   * is returned.
   *
   * @param o the object to remove from this collection
   * @return true if the remove operation caused the Collection to change, or
   *         equivalently if the collection did contain o.
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @see Iterator#remove()
   */
        public boolean remove(fabric.lang.Object o) {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0)
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                itr.next())) {
                    itr.remove();
                    return true;
                }
            return false;
        }
        
        /**
   * Remove from this collection all its elements that are contained in a given
   * collection (optional operation). This implementation iterates over this
   * collection, and for each element tests if it is contained in the given
   * collection. If so, it is removed by the Iterator's remove method (thus
   * this method will fail with an UnsupportedOperationException if the
   * Iterator's remove method does).
   *
   * @param c the collection to remove the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
        public boolean removeAll(fabric.util.Collection c) {
            return removeAllInternal(c);
        }
        
        /**
   * Remove from this collection all its elements that are contained in a given
   * collection (optional operation). This implementation iterates over this
   * collection, and for each element tests if it is contained in the given
   * collection. If so, it is removed by the Iterator's remove method (thus
   * this method will fail with an UnsupportedOperationException if the
   * Iterator's remove method does). This method is necessary for ArrayList,
   * which cannot publicly override removeAll but can optimize this call.
   *
   * @param c the collection to remove the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
        public boolean removeAllInternal(fabric.util.Collection c) {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            boolean modified = false;
            int pos = size();
            while (--pos >= 0)
                if (c.contains(itr.next())) {
                    itr.remove();
                    modified = true;
                }
            return modified;
        }
        
        /**
   * Remove from this collection all its elements that are not contained in a
   * given collection (optional operation). This implementation iterates over
   * this collection, and for each element tests if it is contained in the
   * given collection. If not, it is removed by the Iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does).
   *
   * @param c the collection to retain the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
        public boolean retainAll(fabric.util.Collection c) {
            return retainAllInternal(c);
        }
        
        /**
   * Remove from this collection all its elements that are not contained in a
   * given collection (optional operation). This implementation iterates over
   * this collection, and for each element tests if it is contained in the
   * given collection. If not, it is removed by the Iterator's remove method
   * (thus this method will fail with an UnsupportedOperationException if
   * the Iterator's remove method does). This method is necessary for
   * ArrayList, which cannot publicly override retainAll but can optimize
   * this call.
   *
   * @param c the collection to retain the elements of
   * @return true if the remove operation caused the Collection to change
   * @throws UnsupportedOperationException if this collection's Iterator
   *         does not support the remove method
   * @throws NullPointerException if the collection, c, is null.
   * @see Iterator#remove()
   */
        public boolean retainAllInternal(fabric.util.Collection c) {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            boolean modified = false;
            int pos = size();
            while (--pos >= 0)
                if (!c.contains(itr.next())) {
                    itr.remove();
                    modified = true;
                }
            return modified;
        }
        
        /**
   * Return an array containing the elements of this collection. This
   * implementation creates an Object array of size size() and then iterates
   * over the collection, setting each element of the array from the value
   * returned by the iterator. The returned array is safe, and is not backed
   * by the collection.
   *
   * @return an array containing the elements of this collection
   */
        public fabric.lang.arrays.ObjectArray toArray() {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int size = size();
            fabric.lang.arrays.ObjectArray a =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.lang.Object._Proxy.class,
                                      size).$getProxy();
            for (int pos = 0; pos < size; pos++) a.set(pos, itr.next());
            return a;
        }
        
        /**
   * Copy the collection into a given array if it will fit, or into a
   * dynamically created array of the same run-time type as the given array if
   * not. If there is space remaining in the array, the first element after the
   * end of the collection is set to null (this is only useful if the
   * collection is known to contain no null elements, however). This
   * implementation first tests whether the given array is large enough to hold
   * all the elements of the collection. If not, the reflection API is used to
   * allocate a new array of the same run-time type. Next an iterator is
   * obtained over the collection and the elements are placed in the array as
   * they are returned by the iterator. Finally the first spare element, if
   * any, of the array is set to null, and the created array is returned.
   * The returned array is safe; it is not backed by the collection. Note that
   * null may not mark the last element, if the collection allows null
   * elements.
   *
   * @param a the array to copy into, or of the correct run-time type
   * @return the array that was produced
   * @throws NullPointerException if the given array is null
   * @throws ArrayStoreException if the type of the array precludes holding
   *         one of the elements of the Collection
   */
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a) {
            int size = size();
            if (a.get$length() < size) a.setLength((int) size);
            else if (a.get$length() > size) a.set(size, null);
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            for (int pos = 0; pos < size; pos++) a.set(pos, itr.next());
            return a;
        }
        
        /**
   * Creates a String representation of the Collection. The string returned is
   * of the form "[a, b, ...]" where a and b etc are the results of calling
   * toString on the elements of the collection. This implementation obtains an
   * Iterator over the Collection and adds each element to a StringBuffer as it
   * is returned by the iterator. "<this>" is inserted when the collection
   * contains itself (only works for direct containment, not for collections
   * inside collections).
   *
   * @return a String representation of the Collection
   */
        public java.lang.String toString() {
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractCollection._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            java.lang.StringBuffer r = new java.lang.StringBuffer("[");
            boolean hasNext = itr.hasNext();
            while (hasNext) {
                fabric.lang.Object o = itr.next();
                if (fabric.lang.Object._Proxy.idEquals(
                                                o,
                                                (fabric.util.AbstractCollection)
                                                  this.$getProxy()))
                    r.append("<this>");
                else
                    r.append((java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.$unwrap(o));
                hasNext = itr.hasNext();
                if (hasNext) r.append(", ");
            }
            r.append("]");
            return r.toString();
        }
        
        /**
   * Compare two objects according to Collection semantics.
   *
   * @param o1 the first object
   * @param o2 the second object
   * @return o1 == null ? o2 == null : o1.equals(o2)
   */
        public static final boolean equals(fabric.lang.Object o1,
                                           fabric.lang.Object o2) {
            return fabric.lang.Object._Proxy.idEquals(o1, null)
              ? fabric.lang.Object._Proxy.idEquals(o2, null)
              : o1.equals(o2);
        }
        
        /**
   * Hash an object according to Collection semantics.
   *
   * @param o the object to hash
   * @return o1 == null ? 0 : o1.hashCode()
   */
        public static final int hashCode(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.idEquals(o, null)
              ? 0
              : ((java.lang.Object)
                   fabric.lang.WrappedJavaInlineable.$unwrap(o)).hashCode();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractCollection._Proxy(this);
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
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractCollection._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.AbstractCollection._Static._Impl) fetch()).
                  get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.AbstractCollection._Static._Impl) fetch()).
                  set$LOCAL_STORE(val);
            }
            
            public _Proxy(fabric.util.AbstractCollection._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractCollection._Static
              $instance;
            
            static {
                fabric.
                  util.
                  AbstractCollection.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.AbstractCollection._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractCollection._Static._Impl.class);
                $instance = (fabric.util.AbstractCollection._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractCollection._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return this.LOCAL_STORE;
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.LOCAL_STORE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.worker.Store LOCAL_STORE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeInline(out, this.LOCAL_STORE);
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
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractCollection._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm551 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled554 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff552 = 1;
                        boolean $doBackoff553 = true;
                        boolean $retry547 = true;
                        boolean $keepReads548 = false;
                        $label545: for (boolean $commit546 = false; !$commit546;
                                        ) {
                            if ($backoffEnabled554) {
                                if ($doBackoff553) {
                                    if ($backoff552 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff552));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e549) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff552 < 5000) $backoff552 *= 2;
                                }
                                $doBackoff553 = $backoff552 <= 32 ||
                                                  !$doBackoff553;
                            }
                            $commit546 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.AbstractCollection._Static.
                                      _Proxy.
                                      $instance.
                                      set$LOCAL_STORE(
                                        fabric.worker.Worker.getWorker().
                                            getLocalStore());
                                }
                                catch (final fabric.worker.
                                         RetryException $e549) {
                                    throw $e549;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e549) {
                                    throw $e549;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e549) {
                                    throw $e549;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e549) {
                                    throw $e549;
                                }
                                catch (final Throwable $e549) {
                                    $tm551.getCurrentLog().checkRetrySignal();
                                    throw $e549;
                                }
                            }
                            catch (final fabric.worker.RetryException $e549) {
                                $commit546 = false;
                                continue $label545;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e549) {
                                $commit546 = false;
                                $retry547 = false;
                                $keepReads548 = $e549.keepReads;
                                if ($tm551.checkForStaleObjects()) {
                                    $retry547 = true;
                                    $keepReads548 = false;
                                    continue $label545;
                                }
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid ==
                                      null ||
                                      !$e549.tid.isDescendantOf(
                                                   $currentTid550)) {
                                    throw $e549;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e549);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e549) {
                                $commit546 = false;
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid.isDescendantOf($currentTid550))
                                    continue $label545;
                                if ($currentTid550.parent != null) {
                                    $retry547 = false;
                                    throw $e549;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e549) {
                                $commit546 = false;
                                if ($tm551.checkForStaleObjects())
                                    continue $label545;
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid.isDescendantOf($currentTid550)) {
                                    $retry547 = true;
                                }
                                else if ($currentTid550.parent != null) {
                                    $retry547 = false;
                                    throw $e549;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e549) {
                                $commit546 = false;
                                if ($tm551.checkForStaleObjects())
                                    continue $label545;
                                $retry547 = false;
                                throw new fabric.worker.AbortException($e549);
                            }
                            finally {
                                if ($commit546) {
                                    fabric.common.TransactionID $currentTid550 =
                                      $tm551.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e549) {
                                        $commit546 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e549) {
                                        $commit546 = false;
                                        $retry547 = false;
                                        $keepReads548 = $e549.keepReads;
                                        if ($tm551.checkForStaleObjects()) {
                                            $retry547 = true;
                                            $keepReads548 = false;
                                            continue $label545;
                                        }
                                        if ($e549.tid ==
                                              null ||
                                              !$e549.tid.isDescendantOf(
                                                           $currentTid550))
                                            throw $e549;
                                        throw new fabric.worker.
                                                UserAbortException($e549);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e549) {
                                        $commit546 = false;
                                        $currentTid550 = $tm551.getCurrentTid();
                                        if ($currentTid550 != null) {
                                            if ($e549.tid.equals(
                                                            $currentTid550) ||
                                                  !$e549.tid.
                                                  isDescendantOf(
                                                    $currentTid550)) {
                                                throw $e549;
                                            }
                                        }
                                    }
                                } else if ($keepReads548) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit546) {
                                    {  }
                                    if ($retry547) { continue $label545; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -103, -44, -85, 20,
    -109, 121, -2, 62, 108, -49, 26, 98, -56, -84, -26, 81, -122, 95, -126, 114,
    -104, -24, 113, 106, -116, -99, -34, 57, 28, 102, 61, -115 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
