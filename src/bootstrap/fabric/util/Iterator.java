package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface Iterator
  extends fabric.lang.Object
{
    
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
      implements fabric.util.Iterator
    {
        
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
    
}
