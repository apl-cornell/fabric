package OO7.util;

import java.util.NoSuchElementException;

final class AbstractListIterator implements ListIterator {
  /**
   * 
   */
  private final SubList list;

  private final int index;

  private ListIterator i;

  AbstractListIterator(SubList list, int index) {
    this.list = list;
    this.index = index;
    i = this.list.l.listIterator(index + this.list.offset);
  }

  public boolean hasNext() {
    return nextIndex() < this.list.size;
  }

  public Object next() {
    if (hasNext())
      return i.next();
    else throw new NoSuchElementException();
  }

  public boolean hasPrevious() {
    return previousIndex() >= 0;
  }

  public Object previous() {
    if (hasPrevious())
      return i.previous();
    else throw new NoSuchElementException();
  }

  public int nextIndex() {
    return i.nextIndex() - this.list.offset;
  }

  public int previousIndex() {
    return i.previousIndex() - this.list.offset;
  }

  public void remove() {
    i.remove();
    this.list.expectedModCount = list.l.modCount;
    this.list.size = this.list.size - 1;
    this.list.modCount = this.list.modCount + 1;
  }

  public void set(Object e) {
    i.set(e);
  }

  public void add(Object e) {
    i.add(e);
    this.list.expectedModCount = list.l.modCount;
    this.list.size = this.list.size + 1;
    this.list.modCount = this.list.modCount + 1;
  }
}
