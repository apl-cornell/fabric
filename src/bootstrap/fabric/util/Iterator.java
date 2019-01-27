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
