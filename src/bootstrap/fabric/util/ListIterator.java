package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {
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
   * @throws ClassCastException if the object is of a type which cannot be added
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
      implements fabric.util.ListIterator {
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
    
    public static final byte[] $classHash = new byte[] { 17, 81, -20, 61, -6,
    49, 11, -114, 118, 46, 32, -111, -94, 81, 115, 49, 42, 14, 57, 106, 76, 50,
    -111, 17, -126, -83, 104, -49, 54, 53, 69, 66 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
