package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface Map
  extends fabric.lang.Object
{
    
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
    public static interface Entry
      extends fabric.lang.Object
    {
        
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
         * @throws UnsupportedOperationException if the operation is not
         supported
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
          implements Entry
        {
            
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
        
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Map
    {
        
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
    
}
