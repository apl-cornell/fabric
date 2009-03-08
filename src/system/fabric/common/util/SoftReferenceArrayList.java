package fabric.common.util;

import java.lang.ref.SoftReference;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An array list of soft references. List elements are removed as the soft
 * references are broken.
 */
public class SoftReferenceArrayList<T> implements Iterable<T> {
  /**
   * The array backing this data structure.
   */
  private SoftReference<T>[] data;

  private int size;

  private int modCount;

  @SuppressWarnings("unchecked")
  public SoftReferenceArrayList() {
    data = new SoftReference[10];
    size = 0;
    modCount = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    if (size > 0) {
      modCount++;
      for (int i = 0; i < size; i++)
        data[i] = null;
      size = 0;
    }
  }

  public boolean add(T e) {
    if (e == null) throw new IllegalArgumentException();

    modCount++;
    if (size == data.length) {
      ensureCapacity(size + 1);
    }
    data[size++] = new SoftReference<T>(e);
    return true;
  }

  /**
   * Removes broken references. Assumes caller will increment modCount.
   */
  private void compactData() {
    int compactedSize = 0;

    for (int i = 0; i < size; i++) {
      if (data[i].get() != null) {
        data[compactedSize++] = data[i];
      }
    }

    size = compactedSize;
  }

  @SuppressWarnings("unchecked")
  private void ensureCapacity(int minCapacity) {
    compactData();

    int current = data.length;
    if (minCapacity > current) {
      int newCap = current * 2;
      if (minCapacity > newCap) newCap = minCapacity;

      SoftReference<T>[] newData = new SoftReference[newCap];
      for (int i = 0; i < size; i++) {
        newData[i] = data[i];
      }

      data = newData;
    }
  }

  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int knownMod = modCount;
      private int position = 0;
      private int size = SoftReferenceArrayList.this.size;
      private T nextElement = null;

      private void checkMod() {
        if (knownMod != modCount) throw new ConcurrentModificationException();
      }

      public boolean hasNext() {
        while (nextElement == null && position < size) {
          checkMod();
          nextElement = data[position++].get();
        }

        return nextElement != null;
      }

      public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        T result = nextElement;
        nextElement = null;
        return result;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
