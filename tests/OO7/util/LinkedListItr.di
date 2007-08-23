package OO7.util;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

class LinkedListItr implements ListIterator {
  private final LinkedList list;
  private Entry lastReturned = this.list.header;
  private Entry next;
  private int nextIndex;
  private int expectedModCount = this.list.modCount;

  LinkedListItr(LinkedList list, int index) {
    this.list = list;
    if (index < 0 || index > this.list.size)
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
          + this.list.size);
    if (index < (this.list.size >> 1)) {
      next = list.header.next;
      for (nextIndex = 0; nextIndex < index; nextIndex = nextIndex + 1)
        next = next.next;
    } else {
      next = this.list.header;
      for (nextIndex = this.list.size; nextIndex > index; nextIndex = nextIndex - 1)
        next = next.previous;
    }
  }

  public boolean hasNext() {
    return nextIndex != this.list.size;
  }

  public Object next() {
    checkForComodification();
    if (nextIndex == this.list.size) throw new NoSuchElementException();

    lastReturned = next;
    next = next.next;
    nextIndex = nextIndex + 1;
    return lastReturned.element;
  }

  public boolean hasPrevious() {
    return nextIndex != 0;
  }

  public Object previous() {
    if (nextIndex == 0) throw new NoSuchElementException();

    lastReturned = next = next.previous;
    nextIndex = nextIndex - 1;
    checkForComodification();
    return lastReturned.element;
  }

  public int nextIndex() {
    return nextIndex;
  }

  public int previousIndex() {
    return nextIndex - 1;
  }

  public void remove() {
    checkForComodification();
    Entry lastNext = lastReturned.next;
    try {
      this.list.remove(lastReturned);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException();
    }
    if (next == lastReturned)
      next = lastNext;
    else nextIndex = nextIndex - 1;
    lastReturned = this.list.header;
    expectedModCount = expectedModCount + 1;
  }

  public void set(Object e) {
    if (lastReturned == this.list.header) throw new IllegalStateException();
    checkForComodification();
    lastReturned.element = e;
  }

  public void add(Object e) {
    checkForComodification();
    lastReturned = this.list.header;
    this.list.addBefore(e, next);
    nextIndex = nextIndex + 1;
    expectedModCount = expectedModCount + 1;
  }

  final void checkForComodification() {
    if (this.list.modCount != expectedModCount)
      throw new ConcurrentModificationException();
  }
}
