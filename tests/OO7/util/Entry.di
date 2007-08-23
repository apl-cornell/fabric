package OO7.util;

class Entry {
  Object element;
  Entry next;
  Entry previous;

  Entry(Object element, Entry next, Entry previous) {
    this.element = element;
    this.next = next;
    this.previous = previous;
  }
}
