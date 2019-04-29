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
