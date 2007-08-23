package OO7.util;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

class ListItr extends Itr implements ListIterator {
  private final AbstractList list;

  ListItr(AbstractList list, int index) {
    super(list);
    this.list = list;
    cursor = index;
  }

  public boolean hasPrevious() {
    return cursor != 0;
  }

  public Object previous() {
    checkForComodification();
    try {
      int i = cursor - 1;
      Object previous = this.list.get(i);
      lastRet = cursor = i;
      return previous;
    } catch (IndexOutOfBoundsException e) {
      checkForComodification();
      throw new NoSuchElementException();
    }
  }

  public int nextIndex() {
    return cursor;
  }

  public int previousIndex() {
    return cursor - 1;
  }

  public void set(Object e) {
    if (lastRet == -1) throw new IllegalStateException();
    checkForComodification();

    try {
      this.list.set(lastRet, e);
      expectedModCount = this.list.modCount;
    } catch (IndexOutOfBoundsException ex) {
      throw new ConcurrentModificationException();
    }
  }

  public void add(Object e) {
    checkForComodification();

    try {
      this.list.add(cursor, e);
      cursor = cursor + 1;
      lastRet = -1;
      expectedModCount = this.list.modCount;
    } catch (IndexOutOfBoundsException ex) {
      throw new ConcurrentModificationException();
    }
  }
}
