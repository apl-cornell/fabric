package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
   * <code>headSet(Integer.valueOf(limit.intValue() + 1))</code>.
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
   * <code>subSet(Integer.valueOf(lowlimit.intValue() + 1),
   * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
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
   * <code>tailSet(Integer.valueOf(limit.intValue() + 1))</code>.
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
    
    public static final byte[] $classHash = new byte[] { -123, 72, 59, 22, -73,
    -88, 30, 0, 104, -40, -122, -19, -27, -85, -36, -67, 30, -75, -127, -72,
    -70, 12, -103, 56, -110, 106, -42, 99, -93, 12, 99, -102 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
