package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface Comparator extends fabric.lang.Object {
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
      implements fabric.util.Comparator {
        public int compare(fabric.lang.Object arg1, fabric.lang.Object arg2) {
            return ((fabric.util.Comparator) fetch()).compare(arg1, arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -25, 15, 76, -82, -109,
    3, 9, -123, 72, 86, -45, 39, -99, 93, 54, 20, 87, -77, -14, -39, 47, 41,
    121, -19, 6, 118, -124, -37, -69, 101, 7, -114 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
