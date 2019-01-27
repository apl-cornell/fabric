package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * A collection that contains no duplicates. In other words, for two set
 * elements e1 and e2, <code>e1.equals(e2)</code> returns false. There
 * are additional stipulations on <code>add</code>, <code>equals</code>
 * and <code>hashCode</code>, as well as the requirements that constructors
 * do not permit duplicate elements. The Set interface is incompatible with
 * List; you cannot implement both simultaneously.
 * <p>
 *
 * Note: Be careful about using mutable objects in sets.  In particular,
 * if a mutable object changes to become equal to another set element, you
 * have violated the contract.  As a special case of this, a Set is not
 * allowed to be an element of itself, without risking undefined behavior.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see SortedSet
 * @see HashSet
 * @see TreeSet
 * @see LinkedHashSet
 * @see AbstractSet
 * @see Collections#singleton(Object)
 * @see Collections#EMPTY_SET
 * @since 1.2
 * @status updated to 1.4
 */
public interface Set extends fabric.util.Collection, fabric.lang.Object {
    /**
   * Adds the specified element to the set if it is not already present
   * (optional operation). In particular, the comparison algorithm is
   * <code>o == null ? e == null : o.equals(e)</code>. Sets need not permit
   * all values, and may document what exceptions will be thrown if
   * a value is not permitted.
   *
   * @param o the object to add
   * @return true if the object was not previously in the set
   * @throws UnsupportedOperationException if this operation is not allowed
   * @throws ClassCastException if the class of o prevents it from being added
   * @throws IllegalArgumentException if some aspect of o prevents it from
   *         being added
   * @throws NullPointerException if null is not permitted in this set
   */
    boolean add(fabric.lang.Object o);
    
    /**
   * Adds all of the elements of the given collection to this set (optional
   * operation). If the argument is also a Set, this returns the mathematical
   * <i>union</i> of the two. The behavior is unspecified if the set is
   * modified while this is taking place.
   *
   * @param c the collection to add
   * @return true if the set changed as a result
   * @throws UnsupportedOperationException if this operation is not allowed
   * @throws ClassCastException if the class of an element prevents it from
   *         being added
   * @throws IllegalArgumentException if something about an element prevents
   *         it from being added
   * @throws NullPointerException if null is not permitted in this set, or
   *         if the argument c is null
   * @see #add(Object)
   */
    boolean addAll(fabric.util.Collection c);
    
    /**
   * Removes all elements from this set (optional operation). This set will
   * be empty afterwords, unless an exception occurs.
   *
   * @throws UnsupportedOperationException if this operation is not allowed
   */
    void clear();
    
    /**
   * Returns true if the set contains the specified element. In other words,
   * this looks for <code>o == null ? e == null : o.equals(e)</code>.
   *
   * @param o the object to look for
   * @return true if it is found in the set
   * @throws ClassCastException if the type of o is not a valid type
   *         for this set.
   * @throws NullPointerException if o is null and this set doesn't
   *         support null values.
   */
    boolean contains(fabric.lang.Object o);
    
    /**
   * Returns true if this set contains all elements in the specified
   * collection. If the argument is also a set, this is the <i>subset</i>
   * relationship.
   *
   * @param c the collection to check membership in
   * @return true if all elements in this set are in c
   * @throws NullPointerException if c is null
   * @throws ClassCastException if the type of any element in c is not
   *         a valid type for this set.
   * @throws NullPointerException if some element of c is null and this
   *         set doesn't support null values.
   * @see #contains(Object)
   */
    boolean containsAll(fabric.util.Collection c);
    
    /**
   * Compares the specified object to this for equality. For sets, the object
   * must be a set, the two must have the same size, and every element in
   * one must be in the other.
   *
   * @param o the object to compare to
   * @return true if it is an equal set
   */
    boolean equals(fabric.lang.Object o);
    
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
   * Returns an iterator over the set.  The iterator has no specific order,
   * unless further specified.
   *
   * @return a set iterator
   */
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Returns a local iterator over the set. The iterator has no specific order,
   * unless further specified.
   *
   * @return a set iterator
   */
    fabric.util.Iterator iterator();
    
    /**
   * Removes the specified element from this set (optional operation). If
   * an element e exists, <code>o == null ? e == null : o.equals(e)</code>,
   * it is removed from the set.
   *
   * @param o the object to remove
   * @return true if the set changed (an object was removed)
   * @throws UnsupportedOperationException if this operation is not allowed
   * @throws ClassCastException if the type of o is not a valid type
   *         for this set.
   * @throws NullPointerException if o is null and this set doesn't allow
   *         the removal of a null value.
   */
    boolean remove(fabric.lang.Object o);
    
    /**
   * Removes from this set all elements contained in the specified collection
   * (optional operation). If the argument is a set, this returns the
   * <i>asymmetric set difference</i> of the two sets.
   *
   * @param c the collection to remove from this set
   * @return true if this set changed as a result
   * @throws UnsupportedOperationException if this operation is not allowed
   * @throws NullPointerException if c is null
   * @throws ClassCastException if the type of any element in c is not
   *         a valid type for this set.
   * @throws NullPointerException if some element of c is null and this
   *         set doesn't support removing null values.
   * @see #remove(Object)
   */
    boolean removeAll(fabric.util.Collection c);
    
    /**
   * Retains only the elements in this set that are also in the specified
   * collection (optional operation). If the argument is also a set, this
   * performs the <i>intersection</i> of the two sets.
   *
   * @param c the collection to keep
   * @return true if this set was modified
   * @throws UnsupportedOperationException if this operation is not allowed
   * @throws NullPointerException if c is null
   * @throws ClassCastException if the type of any element in c is not
   *         a valid type for this set.
   * @throws NullPointerException if some element of c is null and this
   *         set doesn't support retaining null values.
   * @see #remove(Object)
   */
    boolean retainAll(fabric.util.Collection c);
    
    /**
   * Returns the number of elements in the set. If there are more
   * than Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is
   * the <i>cardinality</i> of the set.
   *
   * @return the number of elements
   */
    int size();
    
    /**
   * Returns an array containing the elements of this set. If the set
   * makes a guarantee about iteration order, the array has the same
   * order. The array is distinct from the set; modifying one does not
   * affect the other.
   *
   * @return an array of this set's elements
   * @see #toArray(Object[])
   */
    fabric.lang.arrays.ObjectArray toArray();
    
    /**
   * Returns an array containing the elements of this set, of the same runtime
   * type of the argument. If the given set is large enough, it is reused,
   * and null is inserted in the first unused slot. Otherwise, reflection
   * is used to build a new array. If the set makes a guarantee about iteration
   * order, the array has the same order. The array is distinct from the set;
   * modifying one does not affect the other.
   *
   * @param a the array to determine the return type; if it is big enough
   *        it is used and returned
   * @return an array holding the elements of the set
   * @throws ArrayStoreException if the runtime type of a is not a supertype
   *         of all elements in the set
   * @throws NullPointerException if a is null
   * @see #toArray()
   */
    fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Set {
        public boolean add(fabric.lang.Object arg1) {
            return ((fabric.util.Set) fetch()).add(arg1);
        }
        
        public boolean addAll(fabric.util.Collection arg1) {
            return ((fabric.util.Set) fetch()).addAll(arg1);
        }
        
        public void clear() { ((fabric.util.Set) fetch()).clear(); }
        
        public boolean contains(fabric.lang.Object arg1) {
            return ((fabric.util.Set) fetch()).contains(arg1);
        }
        
        public boolean containsAll(fabric.util.Collection arg1) {
            return ((fabric.util.Set) fetch()).containsAll(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.Set) fetch()).isEmpty();
        }
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.Set) fetch()).iterator(arg1);
        }
        
        public fabric.util.Iterator iterator() {
            return ((fabric.util.Set) fetch()).iterator();
        }
        
        public boolean remove(fabric.lang.Object arg1) {
            return ((fabric.util.Set) fetch()).remove(arg1);
        }
        
        public boolean removeAll(fabric.util.Collection arg1) {
            return ((fabric.util.Set) fetch()).removeAll(arg1);
        }
        
        public boolean retainAll(fabric.util.Collection arg1) {
            return ((fabric.util.Set) fetch()).retainAll(arg1);
        }
        
        public int size() { return ((fabric.util.Set) fetch()).size(); }
        
        public fabric.lang.arrays.ObjectArray toArray() {
            return ((fabric.util.Set) fetch()).toArray();
        }
        
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.util.Set) fetch()).toArray(arg1);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -20, -126, -1, 16, 19,
    -77, -44, 45, 113, 46, 30, 113, -7, 46, 49, 27, -6, -46, 1, -94, 96, -109,
    116, 36, -64, -105, 40, -89, -113, -70, -65, 7 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
