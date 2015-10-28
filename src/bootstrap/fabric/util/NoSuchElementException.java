package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * Exception thrown when an attempt is made to access an element that does not
 * exist. This exception is thrown by the Enumeration, Iterator and
 * ListIterator classes if the nextElement, next or previous method goes
 * beyond the end of the list of elements that are being accessed. It is also
 * thrown by Vector and Stack when attempting to access the first or last
 * element of an empty collection.
 *
 * @author Warren Levy (warrenl@cygnus.com)
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Enumeration
 * @see Iterator
 * @see ListIterator
 * @see Enumeration#nextElement()
 * @see Iterator#next()
 * @see ListIterator#previous()
 * @since 1.0
 * @status updated to 1.4
 */
public class NoSuchElementException
extends java.lang.RuntimeException
{
    /**
     * Compatible with JDK 1.0.
     */
    private static long serialVersionUID;
    
    public NoSuchElementException() { super(); }
}
