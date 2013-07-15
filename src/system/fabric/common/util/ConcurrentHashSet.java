package fabric.common.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements
    ConcurrentSet<E> {

  /**
   * The hash map that backs this data structure.
   */
  private final ConcurrentHashMap<E, E> map;

  public ConcurrentHashSet() {
    this.map = new ConcurrentHashMap<>();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return map.containsKey(o);
  }

  /**
   * {@inheritDoc}
   * 
   * The returned iterator is "weakly consistent". It willl never throw
   * ConcurrentModificationException, and guarantees to traverse elements as
   * they existed upon construction of the iterator, and may (but is not
   * guaranteed to) reflect any modifications subsequent to construction.
   */
  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public Object[] toArray() {
    return map.keySet().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return map.keySet().toArray(a);
  }

  @Override
  public boolean add(E e) {
    return map.put(e, e) == null;
  }

  @Override
  public boolean remove(Object o) {
    return map.remove(o) != null;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return map.keySet().containsAll(c);
  }

  @Override
  public void clear() {
    map.clear();
  }

}
