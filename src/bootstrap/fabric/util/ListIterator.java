package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * An extended version of Iterator to support the extra features of Lists. The
 * elements may be accessed in forward or reverse order, elements may be
 * replaced as well as removed, and new elements may be inserted, during the
 * traversal of the list.
 * <p>
 *
 * A list with n elements provides n+1 iterator positions (the front, the end,
 * or between two elements). Note that <code>remove</code> and <code>set</code>
 * operate on the last element returned, whether it was by <code>next</code>
 * or <code>previous</code>.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see Iterator
 * @see Enumeration
 * @since 1.2
 * @status updated to 1.4
 */
public interface ListIterator
  extends fabric.util.Iterator, fabric.lang.Object
{
    
    /**
     * Tests whether there are elements remaining in the list in the forward
     * direction. In other words, next() will not fail with a
     * NoSuchElementException.
     *
     * @return true if the list continues in the forward direction
     */
    boolean hasNext();
    
    /**
     * Tests whether there are elements remaining in the list in the reverse
     * direction. In other words, previous() will not fail with a
     * NoSuchElementException.
     *
     * @return true if the list continues in the reverse direction
     */
    boolean hasPrevious();
    
    /**
     * Obtain the next element in the list in the forward direction. Repeated
     * calls to next may be used to iterate over the entire list, or calls to
     * next and previous may be used together to go forwards and backwards.
     * Alternating calls to next and previous will return the same element.
     *
     * @return the next element in the list in the forward direction
     * @throws NoSuchElementException if there are no more elements
     */
    fabric.lang.Object next();
    
    /**
     * Obtain the next element in the list in the reverse direction. Repeated
     * calls to previous may be used to iterate backwards over the entire list,
     * or calls to next and previous may be used together to go forwards and
     * backwards. Alternating calls to next and previous will return the same
     * element.
     *
     * @return the next element in the list in the reverse direction
     * @throws NoSuchElementException if there are no more elements
     */
    fabric.lang.Object previous();
    
    /**
     * Find the index of the element that would be returned by a call to next.
     * If hasNext() returns false, this returns the list size.
     *
     * @return the index of the element that would be returned by next()
     */
    int nextIndex();
    
    /**
     * Find the index of the element that would be returned by a call to
     * previous. If hasPrevious() returns false, this returns -1.
     *
     * @return the index of the element that would be returned by previous()
     */
    int previousIndex();
    
    /**
     * Insert an element into the list at the current position of the iterator
     * (optional operation). The element is inserted in between the element that
     * would be returned by previous and the element that would be returned by
     * next. After the insertion, a subsequent call to next is unaffected, but
     * a call to previous returns the item that was added. The values returned
     * by nextIndex() and previousIndex() are incremented.
     *
     * @param o the object to insert into the list
     * @throws ClassCastException if the object is of a type which cannot be
     added
     *         to this list.
     * @throws IllegalArgumentException if some other aspect of the object stops
     *         it being added to this list.
     * @throws UnsupportedOperationException if this ListIterator does not
     *         support the add operation.
     */
    void add(fabric.lang.Object o);
    
    /**
     * Remove from the list the element last returned by a call to next or
     * previous (optional operation). This method may only be called if neither
     * add nor remove have been called since the last call to next or previous.
     *
     * @throws IllegalStateException if neither next or previous have been
     *         called, or if add or remove has been called since the last call
     *         to next or previous
     * @throws UnsupportedOperationException if this ListIterator does not
     *         support the remove operation
     */
    void remove();
    
    /**
     * Replace the element last returned by a call to next or previous with a
     * given object (optional operation). This method may only be called if
     * neither add nor remove have been called since the last call to next or
     * previous.
     *
     * @param o the object to replace the element with
     * @throws ClassCastException the object is of a type which cannot be added
     *         to this list
     * @throws IllegalArgumentException some other aspect of the object stops
     *         it being added to this list
     * @throws IllegalStateException if neither next or previous have been
     *         called, or if add or remove has been called since the last call
     *         to next or previous
     * @throws UnsupportedOperationException if this ListIterator does not
     *         support the set operation
     */
    void set(fabric.lang.Object o);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator
    {
        
        public boolean hasNext() {
            return ((fabric.util.ListIterator) fetch()).hasNext();
        }
        
        public boolean hasPrevious() {
            return ((fabric.util.ListIterator) fetch()).hasPrevious();
        }
        
        public fabric.lang.Object next() {
            return ((fabric.util.ListIterator) fetch()).next();
        }
        
        public fabric.lang.Object previous() {
            return ((fabric.util.ListIterator) fetch()).previous();
        }
        
        public int nextIndex() {
            return ((fabric.util.ListIterator) fetch()).nextIndex();
        }
        
        public int previousIndex() {
            return ((fabric.util.ListIterator) fetch()).previousIndex();
        }
        
        public void add(fabric.lang.Object arg1) {
            ((fabric.util.ListIterator) fetch()).add(arg1);
        }
        
        public void remove() { ((fabric.util.ListIterator) fetch()).remove(); }
        
        public void set(fabric.lang.Object arg1) {
            ((fabric.util.ListIterator) fetch()).set(arg1);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
