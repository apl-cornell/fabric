package fabric.common.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * An abstract implementation of LongKeyMap to make it easier to create your own
 * implementations. In order to create an unmodifiable LongKeyMap, subclass
 * AbstractLongKeyMap and implement the <code>entrySet</code> (usually via an
 * AbstractSet). To make it modifiable, also implement <code>put</code>, and
 * have <code>entrySet().iterator()</code> support <code>remove</code>.
 * <p>
 * It is recommended that classes which extend this support at least the
 * no-argument constructor, and a constructor which accepts another LongKeyMap.
 * Further methods in this class may be overridden if you have a more efficient
 * implementation.
 */
public abstract class AbstractLongKeyMap<V> implements LongKeyMap<V> {
  /** An "enum" of iterator types. */
  // Package visible for use by subclasses.
  static final int VALUES = 1, ENTRIES = 2;

  /**
   * The cache for {@link #keySet()}.
   */
  // Package visible for use by subclasses.
  LongSet keys;

  /**
   * The cache for {@link #values()}.
   */
  // Package visible for use by subclasses.
  Collection<V> values;

  /**
   * The main constructor, for use by subclasses.
   */
  protected AbstractLongKeyMap() {
  }

  /**
   * Returns a set view of the mappings in this LongKeyMap. Each element in the
   * set must be an implementation of LongKeyMap.Entry. The set is backed by the
   * map, so that changes in one show up in the other. Modifications made while
   * an iterator is in progress cause undefined behaviour. If the set supports
   * removal, these methods must be valid: <code>Iterator.remove</code>,
   * <code>Set.remove</code>, <code>removeAll</code>,
   * <code>retainAll</code>, and <code>clear</code>. Element addition is
   * not supported via this set.
   * 
   * @return the entry set
   * @see Map.Entry
   */
  public abstract Set<LongKeyMap.Entry<V>> entrySet();

  /**
   * Remove all entries from this LongKeyMap (optional operation). This default
   * implementation calls entrySet().clear(). NOTE: If the entry set does not
   * permit clearing, then this will fail, too. Subclasses often override this
   * for efficiency. Your implementation of entrySet() should not call
   * <code>AbstractLongKeyMap.clear</code> unless you want an infinite loop.
   * 
   * @throws UnsupportedOperationException
   *                 if <code>entrySet().clear()</code> does not support
   *                 clearing.
   * @see Set#clear()
   */
  public void clear() {
    entrySet().clear();
  }

  /**
   * Returns true if this contains a mapping for the given key. This
   * implementation does a linear search, O(n), over the <code>entrySet()</code>,
   * returning <code>true</code> if a match is found, <code>false</code> if
   * the iteration ends. Many subclasses can implement this more efficiently.
   * 
   * @param key
   *                the key to search for
   * @return true if the map contains the key
   * @throws NullPointerException
   *                 if key is <code>null</code> but the map does not permit
   *                 null keys
   * @see #containsValue(Object)
   */
  public boolean containsKey(long key) {
    Iterator<LongKeyMap.Entry<V>> entries = entrySet().iterator();
    int pos = size();
    while (--pos >= 0)
      if (key == entries.next().getKey()) return true;
    return false;
  }

  /**
   * Returns true if this contains at least one mapping with the given value.
   * This implementation does a linear search, O(n), over the
   * <code>entrySet()</code>, returning <code>true</code> if a match is
   * found, <code>false</code> if the iteration ends. A match is defined as a
   * value, v, where <code>(value == null ? v == null :
   * value.equals(v))</code>.
   * Subclasses are unlikely to implement this more efficiently.
   * 
   * @param value
   *                the value to search for
   * @return true if the map contains the value
   * @see #containsKey(Object)
   */
  public boolean containsValue(V value) {
    Iterator<LongKeyMap.Entry<V>> entries = entrySet().iterator();
    int pos = size();
    while (--pos >= 0)
      if (equals(value, entries.next().getValue())) return true;
    return false;
  }

  /**
   * Compares the specified object with this map for equality. Returns
   * <code>true</code> if the other object is a Map with the same mappings,
   * that is,<br>
   * <code>o instanceof Map && entrySet().equals(((Map) o).entrySet();</code>
   * 
   * @param o
   *                the object to be compared
   * @return true if the object equals this map
   * @see Set#equals(Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o) {
    return (o == this || (o instanceof LongKeyMap && entrySet().equals(
        ((LongKeyMap) o).entrySet())));
  }

  /**
   * Returns the value mapped by the given key. Returns <code>null</code> if
   * there is no mapping. However, in Maps that accept null values, you must
   * rely on <code>containsKey</code> to determine if a mapping exists. This
   * iteration takes linear time, searching entrySet().iterator() of the key.
   * Many implementations override this method.
   * 
   * @param key
   *                the key to look up
   * @return the value associated with the key, or null if key not in map
   * @see #containsKey(Object)
   */
  public V get(long key) {
    Iterator<LongKeyMap.Entry<V>> entries = entrySet().iterator();
    int pos = size();
    while (--pos >= 0) {
      LongKeyMap.Entry<V> entry = entries.next();
      if (key == entry.getKey()) return entry.getValue();
    }
    return null;
  }

  /**
   * Compare two objects according to Collection semantics.
   * 
   * @param o1
   *                the first object
   * @param o2
   *                the second object
   * @return o1 == null ? o2 == null : o1.equals(o2)
   */
  // Package visible for use throughout java.util.
  // It may be inlined since it is final.
  static final boolean equals(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }

  /**
   * Returns the hash code for this map. As defined in Map, this is the sum of
   * all hashcodes for each Map.Entry object in entrySet, or basically
   * entrySet().hashCode().
   * 
   * @return the hash code
   * @see Map.Entry#hashCode()
   * @see Set#hashCode()
   */
  @Override
  public int hashCode() {
    return entrySet().hashCode();
  }

  /**
   * Returns true if the map contains no mappings. This is implemented by
   * <code>size() == 0</code>.
   * 
   * @return true if the map is empty
   * @see #size()
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns a set view of this map's keys. The set is backed by the map, so
   * changes in one show up in the other. Modifications while an iteration is in
   * progress produce undefined behaviour. The set supports removal if
   * entrySet() does, but does not support element addition.
   * <p>
   * This implementation creates an AbstractSet, where the iterator wraps the
   * entrySet iterator, size defers to the Map's size, and contains defers to
   * the Map's containsKey. The set is created on first use, and returned on
   * subsequent uses, although since no synchronization occurs, there is a
   * slight possibility of creating two sets.
   * 
   * @return a Set view of the keys
   * @see Set#iterator()
   * @see #size()
   * @see #containsKey(Object)
   * @see #values()
   */
  public LongSet keySet() {
    if (keys == null) keys = new AbstractLongSet() {
      /**
       * Retrieves the number of keys in the backing map.
       * 
       * @return The number of keys.
       */
      @Override
      public int size() {
        return AbstractLongKeyMap.this.size();
      }

      /**
       * Returns true if the backing map contains the supplied key.
       * 
       * @param key
       *                The key to search for.
       * @return True if the key was found, false otherwise.
       */
      @Override
      public boolean contains(long key) {
        return containsKey(key);
      }

      /**
       * Returns an iterator which iterates over the keys in the backing map,
       * using a wrapper around the iterator returned by <code>entrySet()</code>.
       * 
       * @return An iterator over the keys.
       */
      @Override
      public LongIterator iterator() {
        return new LongIterator() {
          /**
           * The iterator returned by <code>entrySet()</code>.
           */
          private final Iterator<LongKeyMap.Entry<V>> map_iterator =
              entrySet().iterator();

          /**
           * Returns true if a call to <code>next()</code> will return another
           * key.
           * 
           * @return True if the iterator has not yet reached the last key.
           */
          public boolean hasNext() {
            return map_iterator.hasNext();
          }

          /**
           * Returns the key from the next entry retrieved by the underlying
           * <code>entrySet()</code> iterator.
           * 
           * @return The next key.
           */
          public long next() {
            return map_iterator.next().getKey();
          }

          /**
           * Removes the map entry which has a key equal to that returned by the
           * last call to <code>next()</code>.
           * 
           * @throws UnsupportedOperationException
           *                 if the map doesn't support removal.
           */
          public void remove() {
            map_iterator.remove();
          }
        };
      }
    };
    return keys;
  }

  /**
   * Associates the given key to the given value (optional operation). If the
   * map already contains the key, its value is replaced. This implementation
   * simply throws an UnsupportedOperationException. Be aware that in a map that
   * permits <code>null</code> values, a null return does not always imply
   * that the mapping was created.
   * 
   * @param key
   *                the key to map
   * @param value
   *                the value to be mapped
   * @return the previous value of the key, or null if there was no mapping
   * @throws UnsupportedOperationException
   *                 if the operation is not supported
   * @throws ClassCastException
   *                 if the key or value is of the wrong type
   * @throws IllegalArgumentException
   *                 if something about this key or value prevents it from
   *                 existing in this map
   * @throws NullPointerException
   *                 if the map forbids null keys or values
   * @see #containsKey(Object)
   */
  public V put(long key, V value) {
    throw new UnsupportedOperationException();
  }

  /**
   * Copies all entries of the given map to this one (optional operation). If
   * the map already contains a key, its value is replaced. This implementation
   * simply iterates over the map's entrySet(), calling <code>put</code>, so
   * it is not supported if puts are not.
   * 
   * @param m
   *                the mapping to load into this map
   * @throws UnsupportedOperationException
   *                 if the operation is not supported by this map.
   * @throws ClassCastException
   *                 if a key or value is of the wrong type for adding to this
   *                 map.
   * @throws IllegalArgumentException
   *                 if something about a key or value prevents it from existing
   *                 in this map.
   * @throws NullPointerException
   *                 if the map forbids null keys or values.
   * @throws NullPointerException
   *                 if <code>m</code> is null.
   * @see #put(Object, Object)
   */
  public void putAll(LongKeyMap<V> m) {
    Iterator<LongKeyMap.Entry<V>> entries = m.entrySet().iterator();
    int pos = m.size();
    while (--pos >= 0) {
      LongKeyMap.Entry<V> entry = entries.next();
      put(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Removes the mapping for this key if present (optional operation). This
   * implementation iterates over the entrySet searching for a matching key, at
   * which point it calls the iterator's <code>remove</code> method. It
   * returns the result of <code>getValue()</code> on the entry, if found, or
   * null if no entry is found. Note that maps which permit null values may also
   * return null if the key was removed. If the entrySet does not support
   * removal, this will also fail. This is O(n), so many implementations
   * override it for efficiency.
   * 
   * @param key
   *                the key to remove
   * @return the value the key mapped to, or null if not present. Null may also
   *         be returned if null values are allowed in the map and the value of
   *         this mapping is null.
   * @throws UnsupportedOperationException
   *                 if deletion is unsupported
   * @see Iterator#remove()
   */
  public V remove(long key) {
    Iterator<LongKeyMap.Entry<V>> entries = entrySet().iterator();
    int pos = size();
    while (--pos >= 0) {
      LongKeyMap.Entry<V> entry = entries.next();
      if (key == entry.getKey()) {
        // Must get the value before we remove it from iterator.
        V r = entry.getValue();
        entries.remove();
        return r;
      }
    }
    return null;
  }

  /**
   * Returns the number of key-value mappings in the map. If there are more than
   * Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is implemented
   * as <code>entrySet().size()</code>.
   * 
   * @return the number of mappings
   * @see Set#size()
   */
  public int size() {
    return entrySet().size();
  }

  /**
   * Returns a String representation of this map. This is a listing of the map
   * entries (which are specified in Map.Entry as being
   * <code>getKey() + "=" + getValue()</code>), separated by a comma and
   * space (", "), and surrounded by braces ('{' and '}'). This implementation
   * uses a StringBuffer and iterates over the entrySet to build the String.
   * Note that this can fail with an exception if underlying keys or values
   * complete abruptly in toString().
   * 
   * @return a String representation
   * @see Map.Entry#toString()
   */
  @Override
  public String toString() {
    Iterator<LongKeyMap.Entry<V>> entries = entrySet().iterator();
    StringBuffer r = new StringBuffer("{");
    for (int pos = size(); pos > 0; pos--) {
      LongKeyMap.Entry<V> entry = entries.next();
      r.append(entry.getKey());
      r.append('=');
      r.append(entry.getValue());
      if (pos > 1) r.append(", ");
    }
    r.append("}");
    return r.toString();
  }

  /**
   * Returns a collection or bag view of this map's values. The collection is
   * backed by the map, so changes in one show up in the other. Modifications
   * while an iteration is in progress produce undefined behavior. The
   * collection supports removal if entrySet() does, but does not support
   * element addition.
   * <p>
   * This implementation creates an AbstractCollection, where the iterator wraps
   * the entrySet iterator, size defers to the Map's size, and contains defers
   * to the Map's containsValue. The collection is created on first use, and
   * returned on subsequent uses, although since no synchronization occurs,
   * there is a slight possibility of creating two collections.
   * 
   * @return a Collection view of the values
   * @see Collection#iterator()
   * @see #size()
   * @see #containsValue(Object)
   * @see #keySet()
   */
  public Collection<V> values() {
    if (values == null) values = new AbstractCollection<V>() {
      /**
       * Returns the number of values stored in the backing map.
       * 
       * @return The number of values.
       */
      @Override
      public int size() {
        return AbstractLongKeyMap.this.size();
      }

      /**
       * Returns true if the backing map contains the supplied value.
       * 
       * @param value
       *                The value to search for.
       * @return True if the value was found, false otherwise.
       */
      @SuppressWarnings("unchecked")
      @Override
      public boolean contains(Object value) {
        return containsValue((V) value);
      }

      /**
       * Returns an iterator which iterates over the values in the backing map,
       * by using a wrapper around the iterator returned by
       * <code>entrySet()</code>.
       * 
       * @return An iterator over the values.
       */
      @Override
      public Iterator<V> iterator() {
        return new Iterator<V>() {
          /**
           * The iterator returned by <code>entrySet()</code>.
           */
          private final Iterator<LongKeyMap.Entry<V>> map_iterator =
              entrySet().iterator();

          /**
           * Returns true if a call to <code>next()</call> will
           * return another value.
           *
           * @return True if the iterator has not yet reached
           * the last value.
           */
          public boolean hasNext() {
            return map_iterator.hasNext();
          }

          /**
           * Returns the value from the next entry retrieved by the underlying
           * <code>entrySet()</code> iterator.
           * 
           * @return The next value.
           */
          public V next() {
            return map_iterator.next().getValue();
          }

          /**
           * Removes the map entry which has a key equal to that returned by the
           * last call to <code>next()</code>.
           * 
           * @throws UnsupportedOperationException
           *                 if the map doesn't support removal.
           */
          public void remove() {
            map_iterator.remove();
          }
        };
      }
    };
    return values;
  }

  /**
   * Hash an object according to Collection semantics.
   * 
   * @param o
   *                the object to hash
   * @return o1 == null ? 0 : o1.hashCode()
   */
  // Package visible for use throughout java.util.
  // It may be inlined since it is final.
  static final int hashCode(long o) {
    return (int) (o ^ (o >>> 32));
  }

  /**
   * Hash an object according to Collection semantics.
   * 
   * @param o
   *                the object to hash
   * @return o1 == null ? 0 : o1.hashCode()
   */
  // Package visible for use throughout java.util.
  // It may be inlined since it is final.
  static final int hashCode(Object o) {
    return o == null ? 0 : o.hashCode();
  }

  /**
   * A class which implements Map.Entry. It is shared by HashMap, TreeMap,
   * Hashtable, and Collections. It is not specified by the JDK, but makes life
   * much easier.
   * 
   * @author Jon Zeppieri
   * @author Eric Blake (ebb9@email.byu.edu)
   */
  // XXX - FIXME Use fully qualified implements as gcj 3.1 workaround.
  // Bug still exists in 3.4.1
  static class BasicMapEntry<V> implements LongKeyMap.Entry<V> {
    /**
     * The key. Package visible for direct manipulation.
     */
    long key;

    /**
     * The value. Package visible for direct manipulation.
     */
    V value;

    /**
     * Basic constructor initializes the fields.
     * 
     * @param newKey
     *                the key
     * @param newValue
     *                the value
     */
    BasicMapEntry(long newKey, V newValue) {
      key = newKey;
      value = newValue;
    }

    /**
     * Compares the specified object with this entry. Returns true only if the
     * object is a mapping of identical key and value. In other words, this must
     * be:<br>
     * 
     * <pre>
     * (o instanceof Map.Entry)
     *     &amp;&amp; (getKey() == null ? ((HashMap) o).getKey() == null : getKey().equals(
     *         ((HashMap) o).getKey()))
     *     &amp;&amp; (getValue() == null ? ((HashMap) o).getValue() == null : getValue()
     *         .equals(((HashMap) o).getValue()))
     * </pre>
     * 
     * @param o
     *                the object to compare
     * @return <code>true</code> if it is equal
     */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean equals(Object o) {
      if (!(o instanceof LongKeyMap.Entry)) return false;
      // Optimize for our own entries.
      if (o instanceof BasicMapEntry) {
        BasicMapEntry<V> e = (BasicMapEntry<V>) o;
        return (key == e.key && AbstractLongKeyMap.equals(value, e.value));
      }
      LongKeyMap.Entry<V> e = (LongKeyMap.Entry<V>) o;
      return (key == e.getKey() && AbstractLongKeyMap.equals(value, e
          .getValue()));
    }

    /**
     * Get the key corresponding to this entry.
     * 
     * @return the key
     */
    public final long getKey() {
      return key;
    }

    /**
     * Get the value corresponding to this entry. If you already called
     * Iterator.remove(), the behavior undefined, but in this case it works.
     * 
     * @return the value
     */
    public final V getValue() {
      return value;
    }

    /**
     * Returns the hash code of the entry. This is defined as the exclusive-or
     * of the hashcodes of the key and value (using 0 for null). In other words,
     * this must be:<br>
     * 
     * <pre>
     * (getKey() == null ? 0 : getKey().hashCode())
     *     &circ; (getValue() == null ? 0 : getValue().hashCode())
     * </pre>
     * 
     * @return the hash code
     */
    @Override
    public final int hashCode() {
      return (AbstractLongKeyMap.hashCode(key) ^ AbstractLongKeyMap
          .hashCode(value));
    }

    /**
     * Replaces the value with the specified object. This writes through to the
     * map, unless you have already called Iterator.remove(). It may be
     * overridden to restrict a null value.
     * 
     * @param newVal
     *                the new value to store
     * @return the old value
     * @throws NullPointerException
     *                 if the map forbids null values.
     * @throws UnsupportedOperationException
     *                 if the map doesn't support <code>put()</code>.
     * @throws ClassCastException
     *                 if the value is of a type unsupported by the map.
     * @throws IllegalArgumentException
     *                 if something else about this value prevents it being
     *                 stored in the map.
     */
    public V setValue(V newVal) {
      V r = value;
      value = newVal;
      return r;
    }

    /**
     * This provides a string representation of the entry. It is of the form
     * "key=value", where string concatenation is used on key and value.
     * 
     * @return the string representation
     */
    @Override
    public final String toString() {
      return key + "=" + value;
    }
  } // class BasicMapEntry
}
