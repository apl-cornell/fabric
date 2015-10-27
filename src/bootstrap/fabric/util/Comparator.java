package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * Interface for objects that specify an ordering between objects. The ordering
 * should be <em>total</em>, such that any two objects of the correct type
 * can be compared, and the comparison is reflexive, anti-symmetric, and
 * transitive.  It is also recommended that the comparator be <em>consistent
 * with equals</em>, although this is not a strict requirement. A relation
 * is consistent with equals if these two statements always have the same
 * results (if no exceptions occur):<br>
 * <code>compare((Object) e1, (Object) e2) == 0</code> and
 * <code>e1.equals((Object) e2)</code><br>
 * Comparators that violate consistency with equals may cause strange behavior
 * in sorted lists and sets.  For example, a case-sensitive dictionary order
 * comparison of Strings is consistent with equals, but if it is
 * case-insensitive it is not, because "abc" and "ABC" compare as equal even
 * though "abc".equals("ABC") returns false.
 * <P>
 * In general, Comparators should be Serializable, because when they are passed
 * to Serializable data structures such as SortedMap or SortedSet, the entire
 * data structure will only serialize correctly if the comparator is
 * Serializable.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Comparable
 * @see TreeMap
 * @see TreeSet
 * @see SortedMap
 * @see SortedSet
 * @see Arrays#sort(Object[], Comparator)
 * @see java.io.Serializable
 * @since 1.2
 * @status updated to 1.4
 */
public interface Comparator
  extends fabric.lang.Object
{
    
    /**
     * Return an integer that is negative, zero or positive depending on whether
     * the first argument is less than, equal to or greater than the second
     * according to this ordering. This method should obey the following
     * contract:
     * <ul>
     *   <li>if compare(a, b) &lt; 0 then compare(b, a) &gt; 0</li>
     *   <li>if compare(a, b) throws an exception, so does compare(b, a)</li>
     *   <li>if compare(a, b) &lt; 0 and compare(b, c) &lt; 0 then compare(a, c)
     *       &lt; 0</li>
     *   <li>if compare(a, b) == 0 then compare(a, c) and compare(b, c) must
     *       have the same sign</li>
     * </ul>
     * To be consistent with equals, the following additional constraint is
     * in place:
     * <ul>
     *   <li>if a.equals(b) or both a and b are null, then
     *       compare(a, b) == 0.</li>
     * </ul><p>
     *
     * Although it is permissible for a comparator to provide an order
     * inconsistent with equals, that should be documented.
     *
     * @param o1 the first object
     * @param o2 the second object
     * @return the comparison
     * @throws ClassCastException if the elements are not of types that can be
     *         compared by this ordering.
     */
    int compare(fabric.lang.Object o1, fabric.lang.Object o2);
    
    /**
     * Return true if the object is equal to this object.  To be
     * considered equal, the argument object must satisfy the constraints
     * of <code>Object.equals()</code>, be a Comparator, and impose the
     * same ordering as this Comparator. The default implementation
     * inherited from Object is usually adequate.
     *
     * @param obj The object
     * @return true if it is a Comparator that imposes the same order
     * @see Object#equals(Object)
     */
    boolean equals(fabric.lang.Object obj);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Comparator
    {
        
        public int compare(fabric.lang.Object arg1, fabric.lang.Object arg2) {
            return ((fabric.util.Comparator) fetch()).compare(arg1, arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
