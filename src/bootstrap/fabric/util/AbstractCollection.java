package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
        public native fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.AbstractCollection) fetch()).iterator(arg1);
        }
        
        public native fabric.util.Iterator iterator();
        
        public int size() {
            return ((fabric.util.AbstractCollection) fetch()).size();
        }
        
        public native boolean add(fabric.lang.Object arg1);
        
        public native boolean addAll(fabric.util.Collection arg1);
        
        public native void clear();
        
        public native boolean contains(fabric.lang.Object arg1);
        
        public native boolean containsAll(fabric.util.Collection arg1);
        
        public native boolean isEmpty();
        
        public native boolean remove(fabric.lang.Object arg1);
        
        public native boolean removeAll(fabric.util.Collection arg1);
        
        public native boolean removeAllInternal(fabric.util.Collection arg1);
        
        public native boolean retainAll(fabric.util.Collection arg1);
        
        public native boolean retainAllInternal(fabric.util.Collection arg1);
        
        public native fabric.lang.arrays.ObjectArray toArray();
        
        public native fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);
        
        public static final native boolean equals(fabric.lang.Object arg1,
                                                  fabric.lang.Object arg2);
        
        public static final native int hashCode(fabric.lang.Object arg1);
        
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
        public native fabric.util.AbstractCollection
          fabric$util$AbstractCollection$();
        
        /**
   * Return an Iterator over this collection. The iterator must provide the
   * hasNext and next methods and should in addition provide remove if the
   * collection is modifiable.
   *
   * @return an iterator
   */
        public abstract fabric.util.Iterator iterator(
          fabric.worker.Store store);
        
        public native fabric.util.Iterator iterator();
        
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
        public native boolean add(fabric.lang.Object o);
        
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
        public native boolean addAll(fabric.util.Collection c);
        
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
        public native void clear();
        
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
        public native boolean contains(fabric.lang.Object o);
        
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
        public native boolean containsAll(fabric.util.Collection c);
        
        /**
   * Test whether this collection is empty. This implementation returns
   * size() == 0.
   *
   * @return true if this collection is empty.
   * @see #size()
   */
        public native boolean isEmpty();
        
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
        public native boolean remove(fabric.lang.Object o);
        
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
        public native boolean removeAll(fabric.util.Collection c);
        
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
        public native boolean removeAllInternal(fabric.util.Collection c);
        
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
        public native boolean retainAll(fabric.util.Collection c);
        
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
        public native boolean retainAllInternal(fabric.util.Collection c);
        
        /**
   * Return an array containing the elements of this collection. This
   * implementation creates an Object array of size size() and then iterates
   * over the collection, setting each element of the array from the value
   * returned by the iterator. The returned array is safe, and is not backed
   * by the collection.
   *
   * @return an array containing the elements of this collection
   */
        public native fabric.lang.arrays.ObjectArray toArray();
        
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
        public native fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
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
        public native java.lang.String toString();
        
        /**
   * Compare two objects according to Collection semantics.
   *
   * @param o1 the first object
   * @param o2 the second object
   * @return o1 == null ? o2 == null : o1.equals(o2)
   */
        public static final native boolean equals(fabric.lang.Object o1, fabric.lang.Object o2);
        
        /**
   * Hash an object according to Collection semantics.
   *
   * @param o the object to hash
   * @return o1 == null ? 0 : o1.hashCode()
   */
        public static final native int hashCode(fabric.lang.Object o);
        
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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractCollection._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
