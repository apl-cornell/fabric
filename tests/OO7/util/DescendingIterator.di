package OO7.util;

/** Adapter to provide descending iterators via LinkedListItr.previous */
class DescendingIterator implements Iterator {
  /**
   * 
   */
  private final LinkedList list;

  /**
   * @param list
   */
  DescendingIterator(LinkedList list) {
    this.list = list;
  }

  final LinkedListItr itr = new LinkedListItr(this.list, this.list.size());

  public boolean hasNext() {
    return itr.hasPrevious();
  }

  public Object next() {
    return itr.previous();
  }

  public void remove() {
    itr.remove();
  }
}
