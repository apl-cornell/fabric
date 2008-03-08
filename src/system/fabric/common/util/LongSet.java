package fabric.common.util;

/**
 * A collection that contains no duplicates. In other words, for two set
 * elements e1 and e2, <code>e1 != e2</code>. There are additional
 * stipulations on <code>add</code>, <code>equals</code> and
 * <code>hashCode</code>, as well as the requirements that constructors do
 * not permit duplicate elements.
 * <p>
 * Note: Be careful about using mutable objects in sets. In particular, if a
 * mutable object changes to become equal to another set element, you have
 * violated the contract. As a special case of this, a Set is not allowed to be
 * an element of itself, without risking undefined behaviour.
 */
public interface LongSet extends LongCollection {
  /**
   * Adds the specified element to the set if it is not already present
   * (optional operation). In particular, the comparison algorithm is
   * <code>o == e</code>. LongSets need not permit all values, and may
   * document what exceptions will be thrown if a value is not permitted.
   * 
   * @param o
   *                the object to add
   * @return true if the object was not previously in the set
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   * @throws IllegalArgumentException
   *                 if some aspect of o prevents it from being added
   */
  boolean add(long o);

  /**
   * Adds all of the elements of the given collection to this set (optional
   * operation). If the argument is also a LongSet, this returns the
   * mathematical <i>union</i> of the two. The behaviour is unspecified if the
   * set is modified while this is taking place.
   * 
   * @param c
   *                the collection to add
   * @return true if the set changed as a result
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   * @throws IllegalArgumentException
   *                 if something about an element prevents it from being added
   * @throws NullPointerException
   *                 if the argument c is null
   * @see #add(Object)
   */
  boolean addAll(LongCollection c);

  /**
   * Removes all elements from this set (optional operation). This set will be
   * empty afterwords, unless an exception occurs.
   * 
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   */
  void clear();

  /**
   * Returns true if the set contains the specified element. In other words,
   * this looks for <code>o == e</code>.
   * 
   * @param o
   *                the object to look for
   * @return true if it is found in the set
   */
  boolean contains(long o);

  /**
   * Returns true if this set contains all elements in the specified collection.
   * If the argument is also a set, this is the <i>subset</i> relationship.
   * 
   * @param c
   *                the collection to check membership in
   * @return true if all elements in this set are in c
   * @throws NullPointerException
   *                 if c is null
   * @see #contains(Object)
   */
  boolean containsAll(LongCollection c);

  /**
   * Compares the specified object to this for equality. For sets, the object
   * must be a set, the two must have the same size, and every element in one
   * must be in the other.
   * 
   * @param o
   *                the object to compare to
   * @return true if it is an equal set
   */
  boolean equals(Object o);

  /**
   * Returns the hash code for this set. In order to satisfy the contract of
   * equals, this is the sum of the hashcode of all elements in the set.
   * 
   * @return the sum of the hashcodes of all set elements
   * @see #equals(Object)
   */
  int hashCode();

  /**
   * Returns true if the set contains no elements.
   * 
   * @return true if the set is empty
   */
  boolean isEmpty();

  /**
   * Returns an iterator over the set. The iterator has no specific order,
   * unless further specified.
   * 
   * @return a set iterator
   */
  LongIterator iterator();

  /**
   * Removes the specified element from this set (optional operation). If an
   * element e exists, <code>o == e</code>, it is removed from the set.
   * 
   * @param o
   *                the object to remove
   * @return true if the set changed (an object was removed)
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   */
  boolean remove(long o);

  /**
   * Removes from this set all elements contained in the specified collection
   * (optional operation). If the argument is a set, this returns the
   * <i>asymmetric set difference</i> of the two sets.
   * 
   * @param c
   *                the collection to remove from this set
   * @return true if this set changed as a result
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   * @throws NullPointerException
   *                 if c is null
   * @see #remove(Object)
   */
  boolean removeAll(LongCollection c);

  /**
   * Retains only the elements in this set that are also in the specified
   * collection (optional operation). If the argument is also a set, this
   * performs the <i>intersection</i> of the two sets.
   * 
   * @param c
   *                the collection to keep
   * @return true if this set was modified
   * @throws UnsupportedOperationException
   *                 if this operation is not allowed
   * @throws NullPointerException
   *                 if c is null
   * @see #remove(Object)
   */
  boolean retainAll(LongCollection c);

  /**
   * Returns the number of elements in the set. If there are more than
   * Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is the
   * <i>cardinality</i> of the set.
   * 
   * @return the number of elements
   */
  int size();

  /**
   * Returns an array containing the elements of this set. If the set makes a
   * guarantee about iteration order, the array has the same order. The array is
   * distinct from the set; modifying one does not affect the other.
   * 
   * @return an array of this set's elements
   * @see #toArray(Object[])
   */
  long[] toArray();

  /**
   * Returns an array containing the elements of this set, of the same runtime
   * type of the argument. If the given set is large enough, it is reused, and
   * 0 is inserted in the first unused slot. Otherwise, reflection is used to
   * build a new array. If the set makes a guarantee about iteration order, the
   * array has the same order. The array is distinct from the set; modifying one
   * does not affect the other.
   * 
   * @param a
   *                the array to determine the return type; if it is big enough
   *                it is used and returned
   * @return an array holding the elements of the set
   * @throws NullPointerException
   *                 if a is null
   * @see #toArray()
   */
  long[] toArray(long[] a);
}
