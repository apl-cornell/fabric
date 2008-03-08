package fabric.common.util;

import java.util.Collection;
import java.util.Set;

public interface LongKeyMap<V> {
  /**
   * Remove all entries from this LongKeyMap (optional operation).
   * 
   * @throws UnsupportedOperationException
   *                 if clear is not supported
   */
  void clear();

  /**
   * Returns true if this contains a mapping for the given key.
   * 
   * @param key
   *                the key to search for
   * @return true if the map contains the key
   */
  boolean containsKey(long key);

  /**
   * Returns true if this contains at least one mapping with the given value. In
   * other words, returns true if a value v exists where
   * <code>(value == null ? v == null : value.equals(v))</code>. This usually
   * requires linear time.
   * 
   * @param value
   *                the value to search for
   * @return true if the map contains the value
   * @throws ClassCastException
   *                 if the type of the value is not a valid type for this map.
   * @throws NullPointerException
   *                 if the value is null and the map doesn't support null
   *                 values.
   */
  boolean containsValue(V value);

  /**
   * Returns a set view of the mappings in this LongKeyMap. Each element in the
   * set is a LongKeyMap.Entry. The set is backed by the map, so that changes in
   * one show up in the other. Modifications made while an iterator is in
   * progress cause undefined behaviour. If the set supports removal, these
   * methods remove the underlying mapping from the map:
   * <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and
   * <code>clear</code>. Element addition, via <code>add</code> or
   * <code>addAll</code>, is not supported via this set.
   * 
   * @return the set view of all mapping entries
   * @see LongKeyMap.Entry
   */
  Set<LongKeyMap.Entry<V>> entrySet();

  /**
   * Compares the specified object with this map for equality. Returns
   * <code>true</code> if the other object is a LongKeyMap with the same
   * mappings, that is,<br>
   * <code>o instanceof LongKeyMap && entrySet().equals(((LongKeyMap) o).entrySet();</code>
   * This allows comparison of maps, regardless of implementation.
   * 
   * @param o
   *                the object to be compared
   * @return true if the object equals this map
   * @see Set#equals(Object)
   */
  boolean equals(Object o);

  /**
   * Returns the value mapped by the given key. Returns <code>null</code> if
   * there is no mapping. However, in LongKeyMaps that accept null values, you
   * must rely on <code>containsKey</code> to determine if a mapping exists.
   * 
   * @param key
   *                the key to look up
   * @return the value associated with the key, or null if key not in map
   * @see #containsKey(Object)
   */
  V get(long key);

  /**
   * Associates the given key to the given value (optional operation). If the
   * map already contains the key, its value is replaced. Be aware that in a map
   * that permits <code>null</code> values, a null return does not always
   * imply that the mapping was created.
   * 
   * @param key
   *                the key to map
   * @param value
   *                the value to be mapped
   * @return the previous value of the key, or null if there was no mapping
   * @throws UnsupportedOperationException
   *                 if the operation is not supported
   * @throws ClassCastException
   *                 if the value is of the wrong type
   * @throws IllegalArgumentException
   *                 if something about this value prevents it from existing in
   *                 this map
   * @throws NullPointerException
   *                 if the value is null and the map forbids null values
   * @see #containsKey(Object)
   */
  V put(long key, V value);

  /**
   * Returns the hash code for this map. This is the sum of all hashcodes for
   * each LongKeyMap.Entry object in entrySet. This allows comparison of maps,
   * regardless of implementation, and satisfies the contract of
   * Object.hashCode.
   * 
   * @return the hash code
   * @see LongKeyMap.Entry#hashCode()
   */
  int hashCode();

  /**
   * Returns true if the map contains no mappings.
   * 
   * @return true if the map is empty
   */
  boolean isEmpty();

  /**
   * Returns a set view of the keys in this LongKeyMap. The set is backed by the
   * map, so that changes in one show up in the other. Modifications made while
   * an iterator is in progress cause undefined behaviour. If the set supports
   * removal, these methods remove the underlying mapping from the map:
   * <code>Iterator.remove</code>, <code>LongSet.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and
   * <code>clear</code>. Element addition, via <code>add</code> or
   * <code>addAll</code>, is not supported via this set.
   * 
   * @return the set view of all keys
   */
  LongSet keySet();

  /**
   * Copies all entries of the given map to this one (optional operation). If
   * the map already contains a key, its value is replaced.
   * 
   * @param m
   *                the mapping to load into this map
   * @throws UnsupportedOperationException
   *                 if the operation is not supported
   * @throws ClassCastException
   *                 if a value is of the wrong type
   * @throws IllegalArgumentException
   *                 if something about a value prevents it from existing in
   *                 this map
   * @throws NullPointerException
   *                 if the map forbids null values or if <code>m</code> is
   *                 null.
   * @see #put(Object, Object)
   */
  void putAll(LongKeyMap<V> m);

  /**
   * Removes the mapping for this key if present (optional operation). If the
   * key is not present, this returns null. Note that maps which permit null
   * values may also return null if the key was removed.
   * 
   * @param key
   *                the key to remove
   * @return the value the key mapped to, or null if not present.
   * @throws UnsupportedOperationException
   *                 if deletion is unsupported
   */
  V remove(long key);

  /**
   * Returns the number of key-value mappings in the map. If there are more than
   * Integer.MAX_VALUE mappings, return Integer.MAX_VALUE.
   * 
   * @return the number of mappings
   */
  int size();

  /**
   * Returns a collection (or bag) view of the values in this Map. The
   * collection is backed by the map, so that changes in one show up in the
   * other. Modifications made while an iterator is in progress cause undefined
   * behaviour. If the collection supports removal, these methods remove the
   * underlying mapping from the map: <code>Iterator.remove</code>,
   * <code>Collection.remove</code>, <code>removeAll</code>,
   * <code>retainAll</code>, and <code>clear</code>. Element addition, via
   * <code>add</code> or <code>addAll</code>, is not supported via this
   * collection.
   * 
   * @return the collection view of all values
   */
  Collection<V> values();

  /**
   * A map entry (key-value pair). The LongKeyMap.entrySet() method returns a
   * set view of these objects; there is no other valid way to come across them.
   * These objects are only valid for the duration of an iteration; in other
   * words, if you mess with one after modifying the map, you are asking for
   * undefined behaviour.
   */
  interface Entry<V> {
    /**
     * Get the key corresponding to this entry.
     * 
     * @return the key
     */
    long getKey();

    /**
     * Get the value corresponding to this entry. If you already called
     * Iterator.remove(), this is undefined.
     * 
     * @return the value
     */
    V getValue();

    /**
     * Replaces the value with the specified object (optional operation). This
     * writes through to the map, and is undefined if you already called
     * Iterator.remove().
     * 
     * @param value
     *                the new value to store
     * @return the old value
     * @throws UnsupportedOperationException
     *                 if the operation is not supported
     * @throws ClassCastException
     *                 if the value is of the wrong type
     * @throws IllegalArgumentException
     *                 if something about the value prevents it from existing in
     *                 this map
     * @throws NullPointerException
     *                 if the map forbids null values
     */
    V setValue(V value);

    /**
     * Returns the hash code of the entry. This is defined as the exclusive-or
     * of the hashcodes of the key and value (using 0 for <code>null</code>).
     * In other words, this must be:
     * <p>
     * 
     * <pre>
     * (getKey() == null ? 0 : getKey().hashCode())
     *     &circ; (getValue() == null ? 0 : getValue().hashCode())
     * </pre>
     * 
     * @return the hash code
     */
    int hashCode();

    /**
     * Compares the specified object with this entry. Returns true only if the
     * object is a mapping of identical key and value. In other words, this must
     * be:
     * <p>
     * 
     * <pre>
     * (o instanceof LongKeyMap.Entry)
     *     &amp;&amp; getKey() == ((Map.Entry) o).getKey()
     *     &amp;&amp; (getValue() == null ? ((Map.Entry) o).getValue() == null : getValue()
     *         .equals(((Map.Entry) o).getValue()))
     * </pre>
     * 
     * @param o
     *                the object to compare
     * @return <code>true</code> if it is equal
     */
    boolean equals(Object o);
  }
}
