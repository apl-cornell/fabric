package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Interface for lists of objects that can be returned in sequence. Successive
 * objects are obtained by the nextElement method.
 * <p>
 * As of Java 1.2, the Iterator interface provides the same functionality, but
 * with shorter method names and a new optional method to remove items from the
 * list. If writing for 1.2, consider using Iterator instead. Enumerations over
 * the new collections classes, for use with legacy APIs that require them, can
 * be obtained by the enumeration method in class Collections.
 *
 * @author Warren Levy (warrenl@cygnus.com)
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Iterator
 * @see Hashtable
 * @see Vector
 * @since 1.0
 * @status updated to 1.4
 */
public interface Enumeration extends fabric.lang.Object {
    /**
   * Tests whether there are elements remaining in the enumeration.
   *
   * @return true if there is at least one more element in the enumeration,
   *         that is, if the next call to nextElement will not throw a
   *         NoSuchElementException.
   */
    boolean hasMoreElements();
    
    /**
   * Obtain the next element in the enumeration.
   *
   * @return the next element in the enumeration
   * @throws NoSuchElementException if there are no more elements
   */
    fabric.lang.Object nextElement();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Enumeration {
        public boolean hasMoreElements() {
            return ((fabric.util.Enumeration) fetch()).hasMoreElements();
        }
        
        public fabric.lang.Object nextElement() {
            return ((fabric.util.Enumeration) fetch()).nextElement();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 107, 18, 30, -80, 126,
    70, 21, -8, -15, 9, 9, -48, -39, -79, 53, -106, 18, -94, 119, -82, -75, 110,
    70, 56, 104, 108, 78, -64, -94, 114, 101, -54 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
