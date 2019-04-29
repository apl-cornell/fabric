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
                        fabric.worker.transaction.TransactionManager $tm539 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled542 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff540 = 1;
                        boolean $doBackoff541 = true;
                        boolean $retry535 = true;
                        boolean $keepReads536 = false;
                        $label533: for (boolean $commit534 = false; !$commit534;
                                        ) {
                            if ($backoffEnabled542) {
                                if ($doBackoff541) {
                                    if ($backoff540 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff540));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e537) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff540 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff540 =
                                          java.lang.Math.
                                            min(
                                              $backoff540 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff541 = $backoff540 <= 32 ||
                                                  !$doBackoff541;
                            }
                            $commit534 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.AbstractCollection._Static._Proxy.
                                  $instance.
                                  set$LOCAL_STORE(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                            }
                            catch (final fabric.worker.RetryException $e537) {
                                $commit534 = false;
                                continue $label533;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e537) {
                                $commit534 = false;
                                $retry535 = false;
                                $keepReads536 = $e537.keepReads;
                                fabric.common.TransactionID $currentTid538 =
                                  $tm539.getCurrentTid();
                                if ($e537.tid ==
                                      null ||
                                      !$e537.tid.isDescendantOf(
                                                   $currentTid538)) {
                                    throw $e537;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e537);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e537) {
                                $commit534 = false;
                                fabric.common.TransactionID $currentTid538 =
                                  $tm539.getCurrentTid();
                                if ($e537.tid.isDescendantOf($currentTid538))
                                    continue $label533;
                                if ($currentTid538.parent != null) {
                                    $retry535 = false;
                                    throw $e537;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e537) {
                                $commit534 = false;
                                $retry535 = false;
                                if ($tm539.inNestedTxn()) {
                                    $keepReads536 = true;
                                }
                                throw $e537;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid538 =
                                  $tm539.getCurrentTid();
                                if ($commit534) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e537) {
                                        $commit534 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e537) {
                                        $commit534 = false;
                                        $retry535 = false;
                                        $keepReads536 = $e537.keepReads;
                                        if ($e537.tid ==
                                              null ||
                                              !$e537.tid.isDescendantOf(
                                                           $currentTid538))
                                            throw $e537;
                                        throw new fabric.worker.
                                                UserAbortException($e537);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e537) {
                                        $commit534 = false;
                                        $currentTid538 = $tm539.getCurrentTid();
                                        if ($currentTid538 != null) {
                                            if ($e537.tid.equals(
                                                            $currentTid538) ||
                                                  !$e537.tid.
                                                  isDescendantOf(
                                                    $currentTid538)) {
                                                throw $e537;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm539.inNestedTxn() &&
                                          $tm539.checkForStaleObjects()) {
                                        $retry535 = true;
                                        $keepReads536 = false;
                                    }
                                    if ($keepReads536) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e537) {
                                            $currentTid538 = $tm539.getCurrentTid();
                                            if ($currentTid538 != null &&
                                                  ($e537.tid.equals($currentTid538) || !$e537.tid.isDescendantOf($currentTid538))) {
                                                throw $e537;
                                            } else {
                                                $retry535 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit534) {
                                    {  }
                                    if ($retry535) { continue $label533; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 1, -110, -103, 117, 95,
    8, -16, -76, 60, 32, -33, -121, 9, 3, -125, 22, -111, 26, -108, -32, 93,
    -49, -112, -31, 92, 18, 9, 106, -97, -70, 19, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZe2wUxxmfPT/PGPzgbcAYcBDPOwjpA0wAc4Vw4RKQDSg1BWe9N2cv3ts9dufgIAGRpBWEP2iTGAJKIFJFlYa4EEWhqI2oaJWEIJpEJVFCVFGo2rQgipqobUrTR/p9M3MP7z3sU6nlnW9vZ76Z3/ecb2f7b5EyxyZTI2qXbvjYjhh1fCvVrmBorWo7NBwwVMdZB087tWGlwUPXXww3eognRKo11bRMXVONTtNhZERoi7pN9ZuU+de3BVs2Eq+GjKtUp4cRz8blCZs0xSxjR7dhMblI1vwHZ/v7nt1c+2oJqekgNbrZzlSmawHLZDTBOkh1lEa7qO20hsM03EHqTErD7dTWVUPfCQMts4PUO3q3qbK4TZ026ljGNhxY78Rj1OZrJh8ifAtg23GNWTbArxXw40w3/CHdYS0hUh7RqRF2tpLdpDREyiKG2g0Dx4SSUvj5jP6V+ByGV+kA046oGk2ylPbqZpiRyW6OlMTNq2EAsFZEKeuxUkuVmio8IPUCkqGa3f52ZutmNwwts+KwCiMNeSeFQZUxVetVu2knI+Pc49aKLhjl5WpBFkZGu4fxmcBmDS6bZVjr1oOLDzxirjI9RAHMYaoZiL8SmBpdTG00Qm1qalQwVs8KHVLHnN3nIQQGj3YNFmPOPPrZsjmN594WYybkGLOmawvVWKd2vGvEryYGZi4sQRiVMcvR0RUGSM6tulb2tCRi4O1jUjNipy/Zea7trW/uOUFvekhVkJRrlhGPglfVaVY0phvUvo+a1FYZDQeJl5rhAO8Pkgq4D+kmFU/XRCIOZUFSavBH5Rb/DSqKwBSoogq4182IlbyPqayH3ydihJBauIgC/z8npHUi3E8hpOwwI23+HitK/V1GnG4H9/bDRVVb6/FD3Nq6NlezYjv8jq357bjJdBgpngvhW7vA11WNAWIDtAaS+gBN7P8yawJlqd2uKKDmyZoVpl2qAzaT/rN8rQEhssoywtTu1IwDZ4Nk5Nkj3Ie86PcO+C7XkgJ2n+jOGJm8ffHlKz472XlR+B/ySiUy0ihQCttmowRg1RhbPshWPshW/UrCFzgWfJm7ULnDYy01VzXMtShmqCxi2dEEURQu2CjOz+cHy/dCRoGkUT2zfdP9D++bWgJOG9teinaEoc3uEEonniDcqRAXnVrN3uufnzq0y0oHEyPNWTGezYkxOtWtJdvSaBhyYHr6WU3q6c6zu5o9mF+8kPqYCs4JeaTRvcaAWG1J5j3URlmIDEMdqAZ2JZNVFeuxre3pJ9z6I7CpF46AynIB5Cnz3vbY0cvv3ljAN5Nkdq3JSMPtlLVkRDROVsNjty6t+3U2pTDuyuG1zxy8tXcjVzyMmJZrwWZsAxDJKoSwZX/n7a0fX/3N8Q88aWMxUh6Ldxm6luCy1H0Jfwpc/8ELwxIfIIXkHJApoSmVE2K48vQ0tgx/a15vRq2wHtHVLoOip/yr5q75p/90oFaY24AnQnk2mTP4BOnn45eTPRc3/72RT6NouDul9ZceJlLeyPTMrbat7kAciccuTTpyXj0Kng8Jy9F3Up6DCNcH4Qa8m+tiLm/nu/ruwWaq0NbElMO70/9K3EfTvtjh73++IbDkpoj5lC/iHFNyxPwGNSNM7j4R/ZtnavmbHlLRQWr5Fq6abIMK6QvcoAM2YScgH4bI8AH9AzdUsXu0pGJtojsOMpZ1R0E618A9jsb7KuH4wnFAEeNRSV+B6y5CyhcBbYY0/jvsHRnDdlRCIfxmEWeZxtvp2MzkiixhxBuzLQYoKRQRkJWwFoKHejQaZ+gGfMHZjAwLrQm0hjrb161pW8FnGc3ISJkAt1t2L7V97eDvIj7HuxMaj9FEbgwevJ3FSKUqU2giJR7/q5G707OS7ssQb4BPSFRjMtNy2j2xtyEBjjMpX7HBC6Xjj/cdC6/5wXxREtQP3MBXmPHojz789y99h69dyLEheJkVm2vQbdTIwDUKlpySVfU+wGuxtMtduzlpYaD3k26x7GQXRPfolx7ov3DfdO1pDylJ+VZWATiQqWWgR1XZFOpXc90Av2pKKd6Lir8XrlngV2ckfSLTr0TWzWlQhRs0bUUPTlYpJ3lc0kfdVswd+xsL9G3CZj1ILgzejCZqzt6Hm9NQ21KY6nGWJXB9DaCVCVrx8RAFFB6LzSqXlHVypsuSXsgvpZLW1XK+GC0gKi8UoNCu1Bnl+0rS2UdlOntQdnJXdwnMLYqF3kIQuFnSujwCY/NQtv2QpVbSqqHZL1agj+PszRAKf+u5DIW4vwEy3C/psjtiKJxpqaTzhibOzgJ93KMZExtcjg1qra1HocbYJt9P6L6+/V/6DvSJPCJe4qZlvUdl8ogXOb7WcJ6VMZtNKbQK51j5x1O7Xv/hrr0eifNBRkrgLTKXfzTCtYGQ6mOSPlmcfyDLPkkfH9Tzky5cL10Yd0yf2DFzbyIcwZMFLPBdbL4N4qlh8Zq7W6oJyWOMVHRZlkFVM5fosIESG/zivKQvFyc6spyQ9PiQgn41n/VIAXGew6YPdmUQp9Xg771P5YI+Gi5Q+6hrkn5QHHRkeV/Sd4YWBt8v0MelP8pImQaqtnOZoXSbpYdzCTINLnC9se9Jero4QZDlNUlPDskG+/msJwtI8wo2L0GOkhWkk9cKsKkRWL3hrKTPFwceWZ6T9GARDnSmAPifYPMq1G5J8IW8qAGuNyHVzpN0cnH4kaVR0nFD86JzBfp+gc3rELK6syIaYzvywoaKkFwhpGm3pJHiYCMLlbSzCJ+5UAD7RWzegLi1adTaRvNCnwHXP6Bk3ytpT3HQkaVbUrUIj7lUADpPHe9AJSug5/EXDmEmTAql+Yz9kmp50Lv3ZLHQ7MFQ/roAyivYfMhIXQplEE9FTTW/d4OulTmEzPmZpC8UpWvOckzSw0Xo+vcFpPgDNle5rjE4B9N1gJB5FyU9noUem8uDoblZAM0tbK5znUo0g+p0AkzeQciCHZJ2F6dTZIlI+vDQMsZfC/R9js2fIWMwix884Jue67ULqgzeJQqwd1+8Pf5s843b4pXLfQSdMfDT/qs3Lw2fdJIfa5XiOSMuVeU+u88+mh9w4s5BVmeH8UdQdu2WNMFI4H85Km2nTJ643olpOOL9Ocu2r2LzTzxccP2EG4UUKsahMIjo4FXJc4Vyg5rd4mR6DzZf5GaW75ScCdcoTTMkUgg9Yp1kYSmOonhZGTAsk+JxRrKy9GJlaVgaAEkOF4etuuVLfe3p4ifpSlUipw7ahNAZoLmj58puGX6q1BfoG4VNDa+dAG8SWG1aDlEeZ4DKrmKVnfCC9oikmVvaEEISWTZL+tCQ0twXHPekAjJhHaGMS8dlWl/ZBYjyDCGLz0v64+KwI8tpSU8NKZ0o0wv0zcBmChR+zBKfxXJYI6Mj62XFJSEeYpEFAO8VQpb2SRrLI2HOyFmKgEpcr7Ej5EyWpFp+wT3pqfbzhot5TwEVYFQrPghRujWuGjkr35qk00HVu+yGpO/fEbFwpkuSvjUkXxQSLSkgEZ4bKF8Ho/aoTk/ACvNEvgeybn32sREeZk/I8WFJfvTUAm/Q45+snjM6z0elcVmfoSXfyWM1lWOPrf9I7CbJD5reEKmMxA0j89A34748ZtOIzpXpFUfAMS7QCijuM06A4N0KCcquBMSIVWA/MQJ/BbkOG1KNKGYb4jZ+PO//y9jb5ZXrrvFvFaCyJuXpI/HOyk9fW9x0da+35IkxTzX0Xdv03vd++61675YXfjqy5b9vHF6s1B8AAA==";
}
