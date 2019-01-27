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
