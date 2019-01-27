package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Interface that represents a collection of objects. This interface is the
 * root of the collection hierarchy, and does not provide any guarantees about
 * the order of its elements or whether or not duplicate elements are
 * permitted.
 * <p>
 * All methods of this interface that are defined to modify the collection are
 * defined as <dfn>optional</dfn>. An optional operation may throw an
 * UnsupportedOperationException if the data backing this collection does not
 * support such a modification. This may mean that the data structure is
 * immutable, or that it is read-only but may change ("unmodifiable"), or
 * that it is modifiable but of fixed size (such as an array), or any number
 * of other combinations.
 * <p>
 * A class that wishes to implement this interface should consider subclassing
 * AbstractCollection, which provides basic implementations of most of the
 * methods of this interface. Classes that are prepared to make guarantees
 * about ordering or about absence of duplicate elements should consider
 * implementing List or Set respectively, both of which are subinterfaces of
 * Collection.
 * <p>
 * A general-purpose implementation of the Collection interface should in most
 * cases provide at least two constructors: One which takes no arguments and
 * creates an empty collection, and one which takes a Collection as an argument
 * and returns a collection containing the same elements (that is, creates a
 * copy of the argument using its own implementation).
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see List
 * @see Set
 * @see Map
 * @see SortedSet
 * @see SortedMap
 * @see HashSet
 * @see TreeSet
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Collections
 * @see Arrays
 * @see AbstractCollection
 * @since 1.2
 * @status updated to 1.5 (minus generics)
 */
public interface Collection extends fabric.util.Iterable, fabric.lang.Object {
    /**
   * Add an element to this collection.
   *
   * @param o the object to add.
   * @return true if the collection was modified as a result of this action.
   * @throws UnsupportedOperationException if this collection does not
   *   support the add operation.
   * @throws ClassCastException if o cannot be added to this collection due
   *   to its type.
   * @throws NullPointerException if o is null and this collection doesn't
   *   support the addition of null values.
   * @throws IllegalArgumentException if o cannot be added to this
   *   collection for some other reason.
   */
    boolean add(fabric.lang.Object o);
    
    /**
   * Add the contents of a given collection to this collection.
   *
   * @param c the collection to add.
   * @return true if the collection was modified as a result of this action.
   * @throws UnsupportedOperationException if this collection does not
   *   support the addAll operation.
   * @throws ClassCastException if some element of c cannot be added to this
   *   collection due to its type.
   * @throws NullPointerException if some element of c is null and this
   *   collection does not support the addition of null values.
   * @throws NullPointerException if c itself is null.
   * @throws IllegalArgumentException if some element of c cannot be added
   *   to this collection for some other reason.
   */
    boolean addAll(fabric.util.Collection c);
    
    /**
   * Clear the collection, such that a subsequent call to isEmpty() would
   * return true.
   *
   * @throws UnsupportedOperationException if this collection does not
   *   support the clear operation.
   */
    void clear();
    
    /**
   * Test whether this collection contains a given object as one of its
   * elements.
   *
   * @param o the element to look for.
   * @return true if this collection contains at least one element e such that
   *   <code>o == null ? e == null : o.equals(e)</code>.
   * @throws ClassCastException if the type of o is not a valid type for this
   *   collection.
   * @throws NullPointerException if o is null and this collection doesn't
   *   support null values.
   */
    boolean contains(fabric.lang.Object o);
    
    /**
   * Test whether this collection contains every element in a given collection.
   *
   * @param c the collection to test for.
   * @return true if for every element o in c, contains(o) would return true.
   * @throws ClassCastException if the type of any element in c is not a valid
   *   type for this collection.
   * @throws NullPointerException if some element of c is null and this
   *   collection does not support null values.
   * @throws NullPointerException if c itself is null.
   */
    boolean containsAll(fabric.util.Collection c);
    
    /**
   * Test whether this collection is equal to some object. The Collection
   * interface does not explicitly require any behaviour from this method, and
   * it may be left to the default implementation provided by Object. The Set
   * and List interfaces do, however, require specific behaviour from this
   * method.
   * <p>
   * If an implementation of Collection, which is not also an implementation of
   * Set or List, should choose to implement this method, it should take care
   * to obey the contract of the equals method of Object. In particular, care
   * should be taken to return false when o is a Set or a List, in order to
   * preserve the symmetry of the relation.
   *
   * @param o the object to compare to this collection.
   * @return true if the o is equal to this collection.
   */
    boolean equals(fabric.lang.Object o);
    
    /**
   * Obtain a hash code for this collection. The Collection interface does not
   * explicitly require any behaviour from this method, and it may be left to
   * the default implementation provided by Object. The Set and List interfaces
   * do, however, require specific behaviour from this method.
   * <p>
   * If an implementation of Collection, which is not also an implementation of
   * Set or List, should choose to implement this method, it should take care
   * to obey the contract of the hashCode method of Object. Note that this
   * method renders it impossible to correctly implement both Set and List, as
   * the required implementations are mutually exclusive.
   *
   * @return a hash code for this collection.
   */
    int hashCode();
    
    /**
   * Test whether this collection is empty, that is, if size() == 0.
   *
   * @return true if this collection contains no elements.
   */
    boolean isEmpty();
    
    /**
   * Obtain an Iterator over this collection.
   *
   * @return an Iterator over the elements of this collection, in any order.
   */
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Remove a single occurrence of an object from this collection. That is,
   * remove an element e, if one exists, such that <code>o == null ? e == null
   *   : o.equals(e)</code>.
   *
   * @param o the object to remove.
   * @return true if the collection changed as a result of this call, that is,
   *   if the collection contained at least one occurrence of o.
   * @throws UnsupportedOperationException if this collection does not
   *   support the remove operation.
   * @throws ClassCastException if the type of o is not a valid type
   *   for this collection.
   * @throws NullPointerException if o is null and the collection doesn't
   *   support null values.
   */
    boolean remove(fabric.lang.Object o);
    
    /**
   * Remove all elements of a given collection from this collection. That is,
   * remove every element e such that c.contains(e).
   *
   * @param c The collection of objects to be removed.
   * @return true if this collection was modified as a result of this call.
   * @throws UnsupportedOperationException if this collection does not
   *   support the removeAll operation.
   * @throws ClassCastException if the type of any element in c is not a valid
   *   type for this collection.
   * @throws NullPointerException if some element of c is null and this
   *   collection does not support removing null values.
   * @throws NullPointerException if c itself is null.
   */
    boolean removeAll(fabric.util.Collection c);
    
    /**
   * Remove all elements of this collection that are not contained in a given
   * collection. That is, remove every element e such that !c.contains(e).
   *
   * @param c The collection of objects to be retained.
   * @return true if this collection was modified as a result of this call.
   * @throws UnsupportedOperationException if this collection does not
   *   support the retainAll operation.
   * @throws ClassCastException if the type of any element in c is not a valid
   *   type for this collection.
   * @throws NullPointerException if some element of c is null and this
   *   collection does not support retaining null values.
   * @throws NullPointerException if c itself is null.
   */
    boolean retainAll(fabric.util.Collection c);
    
    /**
   * Get the number of elements in this collection.
   *
   * @return the number of elements in the collection.
   */
    int size();
    
    /**
   * Copy the current contents of this collection into an array.
   *
   * @return an array of type Object[] and length equal to the size of this
   *   collection, containing the elements currently in this collection, in
   *   any order.
   */
    fabric.lang.arrays.ObjectArray toArray();
    
    /**
   * Copy the current contents of this collection into an array. If the array
   * passed as an argument has length less than the size of this collection, an
   * array of the same run-time type as a, and length equal to the size of this
   * collection, is allocated using Reflection. Otherwise, a itself is used.
   * The elements of this collection are copied into it, and if there is space
   * in the array, the following element is set to null. The resultant array is
   * returned.
   * Note: The fact that the following element is set to null is only useful
   * if it is known that this collection does not contain any null elements.
   *
   * @param a the array to copy this collection into.
   * @return an array containing the elements currently in this collection, in
   *   any order.
   * @throws ArrayStoreException if the type of any element of the
   *   collection is not a subtype of the element type of a.
   */
    fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collection {
        public boolean add(fabric.lang.Object arg1) {
            return ((fabric.util.Collection) fetch()).add(arg1);
        }
        
        public boolean addAll(fabric.util.Collection arg1) {
            return ((fabric.util.Collection) fetch()).addAll(arg1);
        }
        
        public void clear() { ((fabric.util.Collection) fetch()).clear(); }
        
        public boolean contains(fabric.lang.Object arg1) {
            return ((fabric.util.Collection) fetch()).contains(arg1);
        }
        
        public boolean containsAll(fabric.util.Collection arg1) {
            return ((fabric.util.Collection) fetch()).containsAll(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.Collection) fetch()).isEmpty();
        }
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.Collection) fetch()).iterator(arg1);
        }
        
        public boolean remove(fabric.lang.Object arg1) {
            return ((fabric.util.Collection) fetch()).remove(arg1);
        }
        
        public boolean removeAll(fabric.util.Collection arg1) {
            return ((fabric.util.Collection) fetch()).removeAll(arg1);
        }
        
        public boolean retainAll(fabric.util.Collection arg1) {
            return ((fabric.util.Collection) fetch()).retainAll(arg1);
        }
        
        public int size() { return ((fabric.util.Collection) fetch()).size(); }
        
        public fabric.lang.arrays.ObjectArray toArray() {
            return ((fabric.util.Collection) fetch()).toArray();
        }
        
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.util.Collection) fetch()).toArray(arg1);
        }
        
        public fabric.util.Iterator iterator() {
            return ((fabric.util.Collection) fetch()).iterator();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 24, 125, 102, 119, 49,
    99, -113, 46, -42, 118, -60, 86, 47, 46, -87, -118, -3, 4, -115, -41, -40,
    -40, -99, -43, -55, -117, 88, 32, -101, -2, 10, -82 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
