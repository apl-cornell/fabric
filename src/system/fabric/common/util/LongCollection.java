package fabric.common.util;

/**
 * Interface that represents a collection of longs. This interface is the root
 * of the collection hierarchy, and does not provide any guarantees about the
 * order of its elements or whether or not duplicate elements are permitted.
 * <p>
 * All methods of this interface that are defined to modify the collection are
 * defined as <i>optional</i>. An optional operation may throw an
 * UnsupportedOperationException if the data backing this collection does not
 * support such a modification. This may mean that the data structure is
 * immutable, or that it is read-only but may change ("unmodifiable"), or that
 * it is modifiable but of fixed size (such as an array), or any number of other
 * combinations.
 * <p>
 * A class that wishes to implement this interface should consider subclassing
 * AbstractLongCollection, which provides basic implementations of most of the
 * methods of this interface. Classes that are prepared to make guarantees about
 * ordering or about absence of duplicate elements should consider implementing
 * LongList or LongSet respectively, both of which are subinterfaces of
 * LongCollection.
 * <p>
 * A general-purpose implementation of the LongCollection interface should in
 * most cases provide at least two constructors: One which takes no arguments
 * and creates an empty collection, and one which takes a LongCollection as an
 * argument and returns a collection containing the same elements (that is,
 * creates a copy of the argument using its own implementation).
 */
public interface LongCollection extends LongIterable {
  /**
   * Add an element to this collection.
   * 
   * @param o
   *                the value to add.
   * @return true if the collection was modified as a result of this action.
   * @throws UnsupportedOperationException
   *                 if this collection does not support the add operation.
   * @throws ClassCastException
   *                 if o cannot be added to this collection due to its type.
   * @throws IllegalArgumentException
   *                 if o cannot be added to this collection for some other
   *                 reason.
   */
  boolean add(long o);

  /**
   * Add the contents of a given collection to this collection.
   * 
   * @param c
   *                the collection to add.
   * @return true if the collection was modified as a result of this action.
   * @throws UnsupportedOperationException
   *                 if this collection does not support the addAll operation.
   * @throws NullPointerException
   *                 if c itself is null.
   * @throws IllegalArgumentException
   *                 if some element of c cannot be added to this collection for
   *                 some other reason.
   */
  boolean addAll(LongCollection c);

  /**
   * Clear the collection, such that a subsequent call to isEmpty() would return
   * true.
   * 
   * @throws UnsupportedOperationException
   *                 if this collection does not support the clear operation.
   */
  void clear();

  /**
   * Test whether this collection contains a given value as one of its elements.
   * 
   * @param o
   *                the element to look for.
   * @return true if this collection contains at least one element e such that
   *         <code>o == e</code>.
   */
  boolean contains(long o);

  /**
   * Test whether this collection contains every element in a given collection.
   * 
   * @param c
   *                the collection to test for.
   * @return true if for every element o in c, contains(o) would return true.
   * @throws NullPointerException
   *                 if c itself is null.
   */
  boolean containsAll(LongCollection c);

  /**
   * Test whether this collection is equal to some object. The LongCollection
   * interface does not explicitly require any behaviour from this method, and
   * it may be left to the default implementation provided by Object. The
   * LongSet and LongList interfaces do, however, require specific behaviour
   * from this method.
   * <p>
   * If an implementation of LongCollection, which is not also an implementation
   * of LongSet or LongList, should choose to implement this method, it should
   * take care to obey the contract of the equals method of Object. In
   * particular, care should be taken to return false when o is a LongSet or a
   * LongList, in order to preserve the symmetry of the relation.
   * 
   * @param o
   *                the object to compare to this collection.
   * @return true if the o is equal to this collection.
   */
  boolean equals(Object o);

  /**
   * Obtain a hash code for this collection. The LongCollection interface does
   * not explicitly require any behaviour from this method, and it may be left
   * to the default implementation provided by Object. The LongSet and LongList
   * interfaces do, however, require specific behaviour from this method.
   * <p>
   * If an implementation of LongCollection, which is not also an implementation
   * of LongSet or LongList, should choose to implement this method, it should
   * take care to obey the contract of the hashCode method of Object. Note that
   * this method renders it impossible to correctly implement both LongSet and
   * LongList, as the required implementations are mutually exclusive.
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
   * Obtain a LongIterator over this collection.
   * 
   * @return a LongIterator over the elements of this collection, in any order.
   */
  LongIterator iterator();

  /**
   * Remove a single occurrence of a value from this collection. That is, remove
   * an element e, if one exists, such that <code>o == e</code>.
   * 
   * @param o
   *                the value to remove.
   * @return true if the collection changed as a result of this call, that is,
   *         if the collection contained at least one occurrence of o.
   * @throws UnsupportedOperationException
   *                 if this collection does not support the remove operation.
   */
  boolean remove(long o);

  /**
   * Remove all elements of a given collection from this collection. That is,
   * remove every element e such that c.contains(e).
   * 
   * @param c
   *                The collection of objects to be removed.
   * @return true if this collection was modified as a result of this call.
   * @throws UnsupportedOperationException
   *                 if this collection does not support the removeAll
   *                 operation.
   * @throws NullPointerException
   *                 if c itself is null.
   */
  boolean removeAll(LongCollection c);

  /**
   * Remove all elements of this collection that are not contained in a given
   * collection. That is, remove every element e such that !c.contains(e).
   * 
   * @param c
   *                The collection of objects to be retained.
   * @return true if this collection was modified as a result of this call.
   * @throws UnsupportedOperationException
   *                 if this collection does not support the retainAll
   *                 operation.
   * @throws NullPointerException
   *                 if c itself is null.
   */
  boolean retainAll(LongCollection c);

  /**
   * Get the number of elements in this collection.
   * 
   * @return the number of elements in the collection.
   */
  int size();

  /**
   * Copy the current contents of this collection into an array.
   * 
   * @return an array of type long[] and length equal to the size of this
   *         collection, containing the elements currently in this collection,
   *         in any order.
   */
  long[] toArray();

  /**
   * Copy the current contents of this collection into an array. If the array
   * passed as an argument has length less than the size of this collection, an
   * array of the same run-time type as a, and length equal to the size of this
   * collection, is allocated using Reflection. Otherwise, a itself is used. The
   * elements of this collection are copied into it, and if there is space in
   * the array, the following element is set to 0. The resultant array is
   * returned. Note: The fact that the following element is set to 0 is only
   * useful if it is known that this collection does not contain any 0 elements.
   * 
   * @param a
   *                the array to copy this collection into.
   * @return an array containing the elements currently in this collection, in
   *         any order.
   */
  long[] toArray(long[] a);
}
