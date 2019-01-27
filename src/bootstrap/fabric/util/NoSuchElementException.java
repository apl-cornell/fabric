package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public class NoSuchElementException extends java.lang.RuntimeException {
    private static long serialVersionUID = 6769829250639411880L;
    
    /**
   * Constructs a NoSuchElementException with no detail message.
   */
    public NoSuchElementException() { super(); }
    
    /**
   * Constructs a NoSuchElementException with a detail message.
   *
   * @param detail the detail message for the exception
   */
    public NoSuchElementException(java.lang.String detail) { super(detail); }
    
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
