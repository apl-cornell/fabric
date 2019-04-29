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
