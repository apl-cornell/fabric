package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * An object that maps keys onto values. Keys cannot be duplicated. This
 * interface replaces the obsolete {@link Dictionary} abstract class.
 * <p>
 *
 * The map has three collection views, which are backed by the map
 * (modifications on one show up on the other): a set of keys, a collection
 * of values, and a set of key-value mappings. Some maps have a guaranteed
 * order, but not all do.
 * <p>
 *
 * Note: Be careful about using mutable keys.  Behavior is unspecified if
 * a key's comparison behavior is changed after the fact.  As a corollary
 * to this rule, don't use a Map as one of its own keys or values, as it makes
 * hashCode and equals have undefined behavior.
 * <p>
 *
 * All maps are recommended to provide a no argument constructor, which builds
 * an empty map, and one that accepts a Map parameter and copies the mappings
 * (usually by putAll), to create an equivalent map.  Unfortunately, Java
 * cannot enforce these suggestions.
 * <p>
 *
 * The map may be unmodifiable, in which case unsupported operations will
 * throw an UnsupportedOperationException.  Note that some operations may be
 * safe, such as putAll(m) where m is empty, even if the operation would
 * normally fail with a non-empty argument.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see HashMap
 * @see TreeMap
 * @see Hashtable
 * @see SortedMap
 * @see Collection
 * @see Set
 * @since 1.2
 * @status updated to 1.4
 */
public interface Map extends fabric.lang.Object {
    /**
   * Remove all entries from this Map (optional operation).
   *
   * @throws UnsupportedOperationException if clear is not supported
   */
    void clear();
    
    /**
   * Returns true if this contains a mapping for the given key.
   *
   * @param key the key to search for
   * @return true if the map contains the key
   * @throws ClassCastException if the key is of an inappropriate type
   * @throws NullPointerException if key is <code>null</code> but the map
   *         does not permit null keys
   */
    boolean containsKey(fabric.lang.Object key);
    
    /**
   * Returns true if this contains at least one mapping with the given value.
   * In other words, returns true if a value v exists where
   * <code>(value == null ? v == null : value.equals(v))</code>. This usually
   * requires linear time.
   *
   * @param value the value to search for
   * @return true if the map contains the value
   * @throws ClassCastException if the type of the value is not a valid type
   *         for this map.
   * @throws NullPointerException if the value is null and the map doesn't
   *         support null values.
   */
    boolean containsValue(fabric.lang.Object value);
    
    /**
   * Returns a set view of the mappings in this Map.  Each element in the
   * set is a Map.Entry.  The set is backed by the map, so that changes in
   * one show up in the other.  Modifications made while an iterator is
   * in progress cause undefined behavior.  If the set supports removal,
   * these methods remove the underlying mapping from the map:
   * <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>.
   * Element addition, via <code>add</code> or <code>addAll</code>, is
   * not supported via this set.
   *
   * @return the set view of all mapping entries
   * @see Map.Entry
   */
    fabric.util.Set entrySet();
    
    /**
   * Compares the specified object with this map for equality. Returns
   * <code>true</code> if the other object is a Map with the same mappings,
   * that is,<br>
   * <code>o instanceof Map && entrySet().equals(((Map) o).entrySet();</code>
   * This allows comparison of maps, regardless of implementation.
   *
   * @param o the object to be compared
   * @return true if the object equals this map
   * @see Set#equals(Object)
   */
    boolean equals(fabric.lang.Object o);
    
    /**
   * Returns the value mapped by the given key. Returns <code>null</code> if
   * there is no mapping.  However, in Maps that accept null values, you
   * must rely on <code>containsKey</code> to determine if a mapping exists.
   *
   * @param key the key to look up
   * @return the value associated with the key, or null if key not in map
   * @throws ClassCastException if the key is an inappropriate type
   * @throws NullPointerException if this map does not accept null keys
   * @see #containsKey(Object)
   */
    fabric.lang.Object get(fabric.lang.Object key);
    
    /**
   * Associates the given key to the given value (optional operation). If the
   * map already contains the key, its value is replaced. Be aware that in
   * a map that permits <code>null</code> values, a null return does not
   * always imply that the mapping was created.
   *
   * @param key the key to map
   * @param value the value to be mapped
   * @return the previous value of the key, or null if there was no mapping
   * @throws UnsupportedOperationException if the operation is not supported
   * @throws ClassCastException if the key or value is of the wrong type
   * @throws IllegalArgumentException if something about this key or value
   *         prevents it from existing in this map
   * @throws NullPointerException if either the key or the value is null,
   *         and the map forbids null keys or values
   * @see #containsKey(Object)
   */
    fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
    
    /**
   * Returns the hash code for this map. This is the sum of all hashcodes
   * for each Map.Entry object in entrySet.  This allows comparison of maps,
   * regardless of implementation, and satisfies the contract of
   * Object.hashCode.
   *
   * @return the hash code
   * @see Map.Entry#hashCode()
   */
    int hashCode();
    
    /**
   * Returns true if the map contains no mappings.
   *
   * @return true if the map is empty
   */
    boolean isEmpty();
    
    /**
   * Returns a set view of the keys in this Map.  The set is backed by the
   * map, so that changes in one show up in the other.  Modifications made
   * while an iterator is in progress cause undefined behavior.  If the set
   * supports removal, these methods remove the underlying mapping from
   * the map: <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>.
   * Element addition, via <code>add</code> or <code>addAll</code>, is
   * not supported via this set.
   *
   * @return the set view of all keys
   */
    fabric.util.Set keySet();
    
    /**
   * Copies all entries of the given map to this one (optional operation). If
   * the map already contains a key, its value is replaced.
   *
   * @param m the mapping to load into this map
   * @throws UnsupportedOperationException if the operation is not supported
   * @throws ClassCastException if a key or value is of the wrong type
   * @throws IllegalArgumentException if something about a key or value
   *         prevents it from existing in this map
   * @throws NullPointerException if the map forbids null keys or values, or
   *         if <code>m</code> is null.
   * @see #put(Object, Object)
   */
    void putAll(fabric.util.Map m);
    
    /**
   * Removes the mapping for this key if present (optional operation). If
   * the key is not present, this returns null. Note that maps which permit
   * null values may also return null if the key was removed.
   *
   * @param key the key to remove
   * @return the value the key mapped to, or null if not present.
   * @throws UnsupportedOperationException if deletion is unsupported
   * @throws NullPointerException if the key is null and this map doesn't
   *         support null keys.
   * @throws ClassCastException if the type of the key is not a valid type
   *         for this map.
   */
    fabric.lang.Object remove(fabric.lang.Object key);
    
    /**
   * Returns the number of key-value mappings in the map. If there are more
   * than Integer.MAX_VALUE mappings, return Integer.MAX_VALUE.
   *
   * @return the number of mappings
   */
    int size();
    
    /**
   * Returns a collection (or bag) view of the values in this Map.  The
   * collection is backed by the map, so that changes in one show up in
   * the other.  Modifications made while an iterator is in progress cause
   * undefined behavior.  If the collection supports removal, these methods
   * remove the underlying mapping from the map: <code>Iterator.remove</code>,
   * <code>Collection.remove</code>, <code>removeAll</code>,
   * <code>retainAll</code>, and <code>clear</code>. Element addition, via
   * <code>add</code> or <code>addAll</code>, is not supported via this
   * collection.
   *
   * @return the collection view of all values
   */
    fabric.util.Collection values();
    
    /**
   * A map entry (key-value pair). The Map.entrySet() method returns a set
   * view of these objects; there is no other valid way to come across them.
   * These objects are only valid for the duration of an iteration; in other
   * words, if you mess with one after modifying the map, you are asking
   * for undefined behavior.
   *
   * @author Original author unknown
   * @author Eric Blake (ebb9@email.byu.edu)
   * @see Map
   * @see Map#entrySet()
   * @since 1.2
   * @status updated to 1.4
   */
    public static interface Entry extends fabric.lang.Object {
        /**
     * Get the key corresponding to this entry.
     *
     * @return the key
     */
        fabric.lang.Object getKey();
        
        /**
     * Get the value corresponding to this entry. If you already called
     * Iterator.remove(), this is undefined.
     *
     * @return the value
     */
        fabric.lang.Object getValue();
        
        /**
     * Replaces the value with the specified object (optional operation).
     * This writes through to the map, and is undefined if you already
     * called Iterator.remove().
     *
     * @param value the new value to store
     * @return the old value
     * @throws UnsupportedOperationException if the operation is not supported
     * @throws ClassCastException if the value is of the wrong type
     * @throws IllegalArgumentException if something about the value
     *         prevents it from existing in this map
     * @throws NullPointerException if the map forbids null values
     */
        fabric.lang.Object setValue(fabric.lang.Object value);
        
        /**
     * Returns the hash code of the entry.  This is defined as the
     * exclusive-or of the hashcodes of the key and value (using 0 for
     * <code>null</code>). In other words, this must be:
     * 
<p><pre>(getKey() == null ? 0 : getKey().hashCode())
^ (getValue() == null ? 0 : getValue().hashCode())</pre>
     *
     * @return the hash code
     */
        int hashCode();
        
        /**
     * Compares the specified object with this entry. Returns true only if
     * the object is a mapping of identical key and value. In other words,
     * this must be:
     * 
<p><pre>(o instanceof Map.Entry)
&& (getKey() == null ? ((Map.Entry) o).getKey() == null
                     : getKey().equals(((Map.Entry) o).getKey()))
&& (getValue() == null ? ((Map.Entry) o).getValue() == null
                       : getValue().equals(((Map.Entry) o).getValue()))</pre>
     *
     * @param o the object to compare
     *
     * @return <code>true</code> if it is equal
     */
        boolean equals(fabric.lang.Object o);
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Entry {
            public fabric.lang.Object getKey() {
                return ((fabric.util.Map.Entry) fetch()).getKey();
            }
            
            public fabric.lang.Object getValue() {
                return ((fabric.util.Map.Entry) fetch()).getValue();
            }
            
            public fabric.lang.Object setValue(fabric.lang.Object arg1) {
                return ((fabric.util.Map.Entry) fetch()).setValue(arg1);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final byte[] $classHash = new byte[] { -38, 76, -128, 71,
        113, -13, -126, -2, 70, 91, -105, 22, -49, -127, -50, 105, -97, 120,
        -33, 122, 39, -13, -33, 64, -15, 108, 57, -55, -31, 34, 121, -8 };
        java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
        long jlc$SourceLastModified$fabil = 1525719795000L;
        java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Map {
        public void clear() { ((fabric.util.Map) fetch()).clear(); }
        
        public boolean containsKey(fabric.lang.Object arg1) {
            return ((fabric.util.Map) fetch()).containsKey(arg1);
        }
        
        public boolean containsValue(fabric.lang.Object arg1) {
            return ((fabric.util.Map) fetch()).containsValue(arg1);
        }
        
        public fabric.util.Set entrySet() {
            return ((fabric.util.Map) fetch()).entrySet();
        }
        
        public fabric.lang.Object get(fabric.lang.Object arg1) {
            return ((fabric.util.Map) fetch()).get(arg1);
        }
        
        public fabric.lang.Object put(fabric.lang.Object arg1,
                                      fabric.lang.Object arg2) {
            return ((fabric.util.Map) fetch()).put(arg1, arg2);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.Map) fetch()).isEmpty();
        }
        
        public fabric.util.Set keySet() {
            return ((fabric.util.Map) fetch()).keySet();
        }
        
        public void putAll(fabric.util.Map arg1) {
            ((fabric.util.Map) fetch()).putAll(arg1);
        }
        
        public fabric.lang.Object remove(fabric.lang.Object arg1) {
            return ((fabric.util.Map) fetch()).remove(arg1);
        }
        
        public int size() { return ((fabric.util.Map) fetch()).size(); }
        
        public fabric.util.Collection values() {
            return ((fabric.util.Map) fetch()).values();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -38, 76, -128, 71, 113,
    -13, -126, -2, 70, 91, -105, 22, -49, -127, -50, 105, -97, 120, -33, 122,
    39, -13, -33, 64, -15, 108, 57, -55, -31, 34, 121, -8 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
