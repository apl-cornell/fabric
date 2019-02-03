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
                        fabric.worker.transaction.TransactionManager $tm561 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled564 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff562 = 1;
                        boolean $doBackoff563 = true;
                        boolean $retry557 = true;
                        boolean $keepReads558 = false;
                        $label555: for (boolean $commit556 = false; !$commit556;
                                        ) {
                            if ($backoffEnabled564) {
                                if ($doBackoff563) {
                                    if ($backoff562 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff562));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e559) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff562 < 5000) $backoff562 *= 2;
                                }
                                $doBackoff563 = $backoff562 <= 32 ||
                                                  !$doBackoff563;
                            }
                            $commit556 = true;
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
                                         RetryException $e559) {
                                    throw $e559;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e559) {
                                    throw $e559;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e559) {
                                    throw $e559;
                                }
                                catch (final Throwable $e559) {
                                    $tm561.getCurrentLog().checkRetrySignal();
                                    throw $e559;
                                }
                            }
                            catch (final fabric.worker.RetryException $e559) {
                                $commit556 = false;
                                continue $label555;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e559) {
                                $commit556 = false;
                                $retry557 = false;
                                $keepReads558 = $e559.keepReads;
                                if ($tm561.checkForStaleObjects()) {
                                    $retry557 = true;
                                    $keepReads558 = false;
                                    continue $label555;
                                }
                                fabric.common.TransactionID $currentTid560 =
                                  $tm561.getCurrentTid();
                                if ($e559.tid ==
                                      null ||
                                      !$e559.tid.isDescendantOf(
                                                   $currentTid560)) {
                                    throw $e559;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e559);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e559) {
                                $commit556 = false;
                                fabric.common.TransactionID $currentTid560 =
                                  $tm561.getCurrentTid();
                                if ($e559.tid.isDescendantOf($currentTid560))
                                    continue $label555;
                                if ($currentTid560.parent != null) {
                                    $retry557 = false;
                                    throw $e559;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e559) {
                                $commit556 = false;
                                if ($tm561.checkForStaleObjects())
                                    continue $label555;
                                $retry557 = false;
                                throw new fabric.worker.AbortException($e559);
                            }
                            finally {
                                if ($commit556) {
                                    fabric.common.TransactionID $currentTid560 =
                                      $tm561.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e559) {
                                        $commit556 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e559) {
                                        $commit556 = false;
                                        $retry557 = false;
                                        $keepReads558 = $e559.keepReads;
                                        if ($tm561.checkForStaleObjects()) {
                                            $retry557 = true;
                                            $keepReads558 = false;
                                            continue $label555;
                                        }
                                        if ($e559.tid ==
                                              null ||
                                              !$e559.tid.isDescendantOf(
                                                           $currentTid560))
                                            throw $e559;
                                        throw new fabric.worker.
                                                UserAbortException($e559);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e559) {
                                        $commit556 = false;
                                        $currentTid560 = $tm561.getCurrentTid();
                                        if ($currentTid560 != null) {
                                            if ($e559.tid.equals(
                                                            $currentTid560) ||
                                                  !$e559.tid.
                                                  isDescendantOf(
                                                    $currentTid560)) {
                                                throw $e559;
                                            }
                                        }
                                    }
                                } else if ($keepReads558) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit556) {
                                    {  }
                                    if ($retry557) { continue $label555; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 107, 85, 7, 114, 44,
    -14, -76, 0, 122, -121, -113, 77, 4, 96, -3, 29, -78, 109, -29, 93, 102,
    -62, -113, -60, -113, 19, 119, 51, 47, 83, -98, 9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
