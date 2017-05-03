package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * A set which guarantees its iteration order. The elements in the set
 * are related by the <i>natural ordering</i> if they are Comparable, or
 * by the provided Comparator.  Additional operations take advantage of
 * the sorted nature of the set.
 * <p>
 *
 * All elements entered in the set must be mutually comparable; in other words,
 * <code>k1.compareTo(k2)</code> or <code>comparator.compare(k1, k2)</code>
 * must not throw a ClassCastException. The ordering must be <i>consistent
 * with equals</i> (see {@link Comparator} for this definition), if the
 * set is to obey the general contract of the Set interface.  If not,
 * the results are well-defined, but probably not what you wanted.
 * <p>
 *
 * It is recommended that all implementing classes provide four constructors:
 * 1) one that takes no arguments and builds an empty set sorted by natural
 * order of the elements; 2) one that takes a Comparator for the sorting order;
 * 3) one that takes a Set and sorts according to the natural order of its
 * elements; and 4) one that takes a SortedSet and sorts by the same
 * comparator. Unfortunately, the Java language does not provide a way to
 * enforce this.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Set
 * @see TreeSet
 * @see SortedMap
 * @see Collection
 * @see Comparable
 * @see Comparator
 * @see ClassCastException
 * @since 1.2
 * @status updated to 1.4
 */
public interface SortedSet extends fabric.util.Set, fabric.lang.Object {
    /**
   * Returns the comparator used in sorting this set, or null if it is
   * the elements' natural ordering.
   *
   * @return the sorting comparator
   */
    fabric.util.Comparator comparator();
    
    /**
   * Returns the first (lowest sorted) element in the set.
   *
   * @return the first element
   * @throws NoSuchElementException if the set is empty.
   */
    fabric.lang.Object first();
    
    /**
   * Returns a view of the portion of the set strictly less than toElement. The
   * view is backed by this set, so changes in one show up in the other.
   * The subset supports all optional operations of the original.
   * <p>
   *
   * The returned set throws an IllegalArgumentException any time an element is
   * used which is out of the range of toElement. Note that the endpoint, toElement,
   * is not included; if you want this value included, pass its successor object in to
   * toElement.  For example, for Integers, you could request
   * <code>headSet(new Integer(limit.intValue() + 1))</code>.
   *
   * @param toElement the exclusive upper range of the subset
   * @return the subset
   * @throws ClassCastException if toElement is not comparable to the set
   *         contents
   * @throws IllegalArgumentException if this is a subSet, and toElement is out
   *         of range
   * @throws NullPointerException if toElement is null but the set does not
   *         allow null elements
   */
    fabric.util.SortedSet headSet(fabric.lang.Object toElement);
    
    /**
   * Returns the last (highest sorted) element in the set.
   *
   * @return the last element
   * @throws NoSuchElementException if the set is empty.
   */
    fabric.lang.Object last();
    
    /**
   * Returns a view of the portion of the set greater than or equal to
   * fromElement, and strictly less than toElement. The view is backed by
   * this set, so changes in one show up in the other. The subset supports all
   * optional operations of the original.
   * <p>
   *
   * The returned set throws an IllegalArgumentException any time an element is
   * used which is out of the range of fromElement and toElement. Note that the
   * lower endpoint is included, but the upper is not; if you want to
   * change the inclusion or exclusion of an endpoint, pass its successor
   * object in instead.  For example, for Integers, you can request
   * <code>subSet(new Integer(lowlimit.intValue() + 1),
   * new Integer(highlimit.intValue() + 1))</code> to reverse
   * the inclusiveness of both endpoints.
   *
   * @param fromElement the inclusive lower range of the subset
   * @param toElement the exclusive upper range of the subset
   * @return the subset
   * @throws ClassCastException if fromElement or toElement is not comparable
   *         to the set contents
   * @throws IllegalArgumentException if this is a subSet, and fromElement or
   *         toElement is out of range
   * @throws NullPointerException if fromElement or toElement is null but the
   *         set does not allow null elements
   */
    fabric.util.SortedSet subSet(fabric.lang.Object fromElement, fabric.lang.Object toElement);
    
    /**
   * Returns a view of the portion of the set greater than or equal to
   * fromElement. The view is backed by this set, so changes in one show up
   * in the other. The subset supports all optional operations of the original.
   * <p>
   *
   * The returned set throws an IllegalArgumentException any time an element is
   * used which is out of the range of fromElement. Note that the endpoint,
   * fromElement, is included; if you do not want this value to be included, pass its
   * successor object in to fromElement.  For example, for Integers, you could request
   * <code>tailSet(new Integer(limit.intValue() + 1))</code>.
   *
   * @param fromElement the inclusive lower range of the subset
   * @return the subset
   * @throws ClassCastException if fromElement is not comparable to the set
   *         contents
   * @throws IllegalArgumentException if this is a subSet, and fromElement is
   *         out of range
   * @throws NullPointerException if fromElement is null but the set does not
   *         allow null elements
   */
    fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.SortedSet {
        public fabric.util.Comparator comparator() {
            return ((fabric.util.SortedSet) fetch()).comparator();
        }
        
        public fabric.lang.Object first() {
            return ((fabric.util.SortedSet) fetch()).first();
        }
        
        public fabric.util.SortedSet headSet(fabric.lang.Object arg1) {
            return ((fabric.util.SortedSet) fetch()).headSet(arg1);
        }
        
        public fabric.lang.Object last() {
            return ((fabric.util.SortedSet) fetch()).last();
        }
        
        public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                            fabric.lang.Object arg2) {
            return ((fabric.util.SortedSet) fetch()).subSet(arg1, arg2);
        }
        
        public fabric.util.SortedSet tailSet(fabric.lang.Object arg1) {
            return ((fabric.util.SortedSet) fetch()).tailSet(arg1);
        }
        
        public boolean add(fabric.lang.Object arg1) {
            return ((fabric.util.SortedSet) fetch()).add(arg1);
        }
        
        public boolean addAll(fabric.util.Collection arg1) {
            return ((fabric.util.SortedSet) fetch()).addAll(arg1);
        }
        
        public void clear() { ((fabric.util.SortedSet) fetch()).clear(); }
        
        public boolean contains(fabric.lang.Object arg1) {
            return ((fabric.util.SortedSet) fetch()).contains(arg1);
        }
        
        public boolean containsAll(fabric.util.Collection arg1) {
            return ((fabric.util.SortedSet) fetch()).containsAll(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.SortedSet) fetch()).isEmpty();
        }
        
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.SortedSet) fetch()).iterator(arg1);
        }
        
        public fabric.util.Iterator iterator() {
            return ((fabric.util.SortedSet) fetch()).iterator();
        }
        
        public boolean remove(fabric.lang.Object arg1) {
            return ((fabric.util.SortedSet) fetch()).remove(arg1);
        }
        
        public boolean removeAll(fabric.util.Collection arg1) {
            return ((fabric.util.SortedSet) fetch()).removeAll(arg1);
        }
        
        public boolean retainAll(fabric.util.Collection arg1) {
            return ((fabric.util.SortedSet) fetch()).retainAll(arg1);
        }
        
        public int size() { return ((fabric.util.SortedSet) fetch()).size(); }
        
        public fabric.lang.arrays.ObjectArray toArray() {
            return ((fabric.util.SortedSet) fetch()).toArray();
        }
        
        public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.util.SortedSet) fetch()).toArray(arg1);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
