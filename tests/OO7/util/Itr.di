package OO7.util;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

class Itr implements Iterator {
  private final AbstractList list;

  Itr(AbstractList list) {
    this.list = list;
  }

  /**
   * Index of element to be returned by subsequent call to next.
   */
  int cursor = 0;

  /**
   * Index of element returned by most recent call to next or previous. Reset to
   * -1 if this element is deleted by a call to remove.
   */
  int lastRet = -1;

  /**
   * The modCount value that the iterator believes that the backing List should
   * have. If this expectation is violated, the iterator has detected concurrent
   * modification.
   */
  int expectedModCount = this.list.modCount;

  public boolean hasNext() {
    return cursor != this.list.size();
  }

  public Object next() {
    checkForComodification();
    try {
      Object next = this.list.get(cursor);
      lastRet = cursor;
      cursor = cursor+1;
      return next;
    } catch (IndexOutOfBoundsException e) {
      checkForComodification();
      throw new NoSuchElementException();
    }
  }

  public void remove() {
    if (lastRet == -1) throw new IllegalStateException();
    checkForComodification();

    try {
      this.list.remove(lastRet);
      if (lastRet < cursor) cursor = cursor - 1;
      lastRet = -1;
      expectedModCount = this.list.modCount;
    } catch (IndexOutOfBoundsException e) {
      throw new ConcurrentModificationException();
    }
  }

  final void checkForComodification() {
    if (this.list.modCount != expectedModCount)
      throw new ConcurrentModificationException();
  }
}
