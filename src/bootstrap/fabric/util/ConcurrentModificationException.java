package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public class ConcurrentModificationException
extends java.lang.RuntimeException
{
    /**
     * Compatible with JDK 1.2.
     */
    private static long serialVersionUID;
    
    public ConcurrentModificationException() { super(); }
}
