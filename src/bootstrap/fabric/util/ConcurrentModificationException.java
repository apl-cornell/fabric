package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Exception that is thrown by the collections classes when it is detected that
 * a modification has been made to a data structure when this is not allowed,
 * such as when a collection is structurally modified while an Iterator is
 * operating over it. In cases where this can be detected, a
 * ConcurrentModificationException will be thrown. An Iterator that detects
 * this condition is referred to as fail-fast. Notice that this can occur
 * even in single-threaded designs, if you call methods out of order.
 *
 * @author Warren Levy (warrenl@cygnus.com)
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Iterator
 * @see ListIterator
 * @see Vector
 * @see LinkedList
 * @see HashSet
 * @see Hashtable
 * @see TreeMap
 * @see AbstractList
 * @since 1.2
 * @status updated to 1.4
 */
public class ConcurrentModificationException extends java.lang.RuntimeException
{
    private static long serialVersionUID = -3666751008965953603L;
    
    /**
   * Constructs a ConcurrentModificationException with no detail message.
   */
    public ConcurrentModificationException() { super(); }
    
    /**
   * Constructs a ConcurrentModificationException with a detail message.
   *
   * @param detail the detail message for the exception
   */
    public ConcurrentModificationException(java.lang.String detail) {
        super(detail);
    }
    
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
