package fabric.client.transaction;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Encapsulates a list of locks. This is a linked list where the list nodes are
 * exposed for performance.
 */
class LockList<T> implements Iterable<T> {
  public static class Node<T> {
    T data;
    Node<T> prev;
    Node<T> next;
  }

  Node<T> head;
  private Node<T> tail;
  private int modCount;

  public LockList() {
    this.head = this.tail = null;
    this.modCount = 0;
  }
  
  /**
   * Adds a node to the list.
   */
  public void add(Node<T> node) {
    if (tail != null) tail.next = node;
    tail = node;

    if (head == null) head = node;

    modCount++;
  }

  /**
   * Adds a new lock to the list.  Returns the newly created node.
   */
  public Node<T> add(T data) {
    Node<T> node = new Node<T>();
    node.data = data;
    node.prev = tail;
    node.next = null;
    
    add(node);

    return node;
  }

  /**
   * Adds a new lock to the list, ensuring that no locks are duplicated.
   */
  public Node<T> addOrGet(T data) {
    // Make sure we're not adding a duplicate.
    Node<T> curNode = head;
    while (curNode != null) {
      if (curNode.data == data) return curNode;
      curNode = curNode.next;
    }

    Node<T> node = new Node<T>();
    node.data = data;
    node.prev = tail;
    node.next = null;

    if (tail != null) tail.next = node;
    tail = node;

    if (head == null) head = node;

    modCount++;
    return node;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public T remove(Node<T> node) {
    if (node == head) {
      head = node.next;
    } else {
      node.prev.next = node.next;
    }

    if (node == tail) {
      tail = node.prev;
    } else {
      node.next.prev = node.prev;
    }
    
    node.prev = null;
    node.next = null;

    modCount++;
    return node.data;
  }

  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int expectedModCount = modCount;
      Node<T> prev = null;
      Node<T> next = head;

      public boolean hasNext() {
        if (modCount != expectedModCount)
          throw new ConcurrentModificationException();

        return next != null;
      }

      public T next() {
        if (modCount != expectedModCount)
          throw new ConcurrentModificationException();

        if (next == null) throw new NoSuchElementException();

        prev = next;
        next = next.next;
        return prev.data;
      }

      public void remove() {
        if (modCount != expectedModCount)
          throw new ConcurrentModificationException();

        if (prev == null) throw new IllegalStateException();

        LockList.this.remove(prev);
        expectedModCount++;
        prev = null;
      }
    };
  }
}
