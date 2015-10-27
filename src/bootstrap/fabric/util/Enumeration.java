package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface Enumeration
  extends fabric.lang.Object
{
    
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
      implements fabric.util.Enumeration
    {
        
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
    
}
