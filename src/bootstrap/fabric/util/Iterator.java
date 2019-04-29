package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * An object which iterates over a collection. An Iterator is used to return
 * the items once only, in sequence, by successive calls to the next method.
 * It is also possible to remove elements from the underlying collection by
 * using the optional remove method. Iterator is intended as a replacement
 * for the Enumeration interface of previous versions of Java, which did not
 * have the remove method and had less conveniently named methods.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see ListIterator
 * @see Enumeration
 * @since 1.2
 * @status updated to 1.4
 */
public interface Iterator extends fabric.lang.Object {
    /**
   * Tests whether there are elements remaining in the collection. In other
   * words, calling <code>next()</code> will not throw an exception.
   *
   * @return true if there is at least one more element in the collection
   */
    boolean hasNext();
    
    /**
   * Obtain the next element in the collection.
   *
   * @return the next element in the collection
   * @throws NoSuchElementException if there are no more elements
   */
    fabric.lang.Object next();
    
    /**
   * Remove from the underlying collection the last element returned by next
   * (optional operation). This method can be called only once after each
   * call to <code>next()</code>. It does not affect what will be returned
   * by subsequent calls to next.
   *
   * @throws IllegalStateException if next has not yet been called or remove
   *         has already been called since the last call to next.
   * @throws UnsupportedOperationException if this Iterator does not support
   *         the remove operation.
   */
    void remove();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator {
        public boolean hasNext() {
            return ((fabric.util.Iterator) fetch()).hasNext();
        }
        
        public fabric.lang.Object next() {
            return ((fabric.util.Iterator) fetch()).next();
        }
        
        public void remove() { ((fabric.util.Iterator) fetch()).remove(); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -91, -64, 28, -27, 35,
    120, 50, -22, -41, 82, -67, 124, 6, 118, 30, -16, 75, 13, 50, -79, -93, -16,
    44, 15, 101, -29, 61, 96, 38, 92, 74, 97 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
