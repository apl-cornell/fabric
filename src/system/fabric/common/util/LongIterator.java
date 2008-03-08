package fabric.common.util;

/**
 * An object which iterates over a collection. A LongIterator is used to return
 * the items once only, in sequence, by successive calls to the next method. It
 * is also possible to remove elements from the underlying collection by using
 * the optional remove method.
 */
public interface LongIterator {
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
   * @throws NoSuchElementException
   *                 if there are no more elements
   */
  long next();

  /**
   * Remove from the underlying collection the last element returned by next
   * (optional operation). This method can be called only once after each call
   * to <code>next()</code>. It does not affect what will be returned by
   * subsequent calls to next.
   * 
   * @throws IllegalStateException
   *                 if next has not yet been called or remove has already been
   *                 called since the last call to next.
   * @throws UnsupportedOperationException
   *                 if this Iterator does not support the remove operation.
   */
  void remove();
}
