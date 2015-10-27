package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * A map which guarantees its key's iteration order. The entries in the
 * map are related by the <i>natural ordering</i> of the keys if they
 * are Comparable, or by the provided Comparator.  Additional operations
 * take advantage of the sorted nature of the map.
 * <p>
 *
 * All keys entered in the map must be mutually comparable; in other words,
 * <code>k1.compareTo(k2)</code> or <code>comparator.compare(k1, k2)</code>
 * must not throw a ClassCastException. The ordering must be <i>consistent
 * with equals</i> (see {@link Comparator} for this definition), if the
 * map is to obey the general contract of the Map interface.  If not,
 * the results are well-defined, but probably not what you wanted.
 * <p>
 *
 * It is recommended that all implementing classes provide four constructors:
 * 1) one that takes no arguments and builds an empty map sorted by natural
 * order of the keys; 2) one that takes a Comparator for the sorting order;
 * 3) one that takes a Map and sorts according to the natural order of its
 * keys; and 4) one that takes a SortedMap and sorts by the same comparator.
 * Unfortunately, the Java language does not provide a way to enforce this.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Map
 * @see TreeMap
 * @see SortedSet
 * @see Comparable
 * @see Comparator
 * @see Collection
 * @see ClassCastException
 * @since 1.2
 * @status updated to 1.4
 */
public interface SortedMap
  extends fabric.util.Map, fabric.lang.Object
{
    
    /**
     * Returns the comparator used in sorting this map, or null if it is
     * the keys' natural ordering.
     *
     * @return the sorting comparator
     */
    fabric.util.Comparator comparator();
    
    /**
     * Returns the first (lowest sorted) key in the map.
     *
     * @return the first key
     * @throws NoSuchElementException if this map is empty.
     */
    fabric.lang.Object firstKey();
    
    /**
     * Returns a view of the portion of the map strictly less than toKey. The
     * view is backed by this map, so changes in one show up in the other.
     * The submap supports all optional operations of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of toKey. Note that the endpoint, toKey,
     * is not included; if you want this value to be included, pass its
     successor
     * object in to toKey.  For example, for Integers, you could request
     * <code>headMap(new Integer(limit.intValue() + 1))</code>.
     *
     * @param toKey the exclusive upper range of the submap
     * @return the submap
     * @throws ClassCastException if toKey is not comparable to the map contents
     * @throws IllegalArgumentException if this is a subMap, and toKey is out
     *         of range
     * @throws NullPointerException if toKey is null but the map does not allow
     *         null keys
     */
    fabric.util.SortedMap headMap(fabric.lang.Object toKey);
    
    /**
     * Returns the last (highest sorted) key in the map.
     *
     * @return the last key
     * @throws NoSuchElementException if this map is empty.
     */
    fabric.lang.Object lastKey();
    
    /**
     * Returns a view of the portion of the map greater than or equal to
     * fromKey, and strictly less than toKey. The view is backed by this map,
     * so changes in one show up in the other. The submap supports all
     * optional operations of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey and toKey. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you could request
     * <code>subMap(new Integer(lowlimit.intValue() + 1),
     * new Integer(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromKey the inclusive lower range of the submap
     * @param toKey the exclusive upper range of the submap
     * @return the submap
     * @throws ClassCastException if fromKey or toKey is not comparable to
     *         the map contents
     * @throws IllegalArgumentException if this is a subMap, and fromKey or
     *         toKey is out of range
     * @throws NullPointerException if fromKey or toKey is null but the map
     *         does not allow null keys
     */
    fabric.util.SortedMap subMap(fabric.lang.Object fromKey,
                                 fabric.lang.Object toKey);
    
    /**
     * Returns a view of the portion of the map greater than or equal to
     * fromKey. The view is backed by this map, so changes in one show up
     * in the other. The submap supports all optional operations of the
     original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey. Note that the endpoint,
     fromKey, is
     * included; if you do not want this value to be included, pass its
     successor object in
     * to fromKey.  For example, for Integers, you could request
     * <code>tailMap(new Integer(limit.intValue() + 1))</code>.
     *
     * @param fromKey the inclusive lower range of the submap
     * @return the submap
     * @throws ClassCastException if fromKey is not comparable to the map
     *         contents
     * @throws IllegalArgumentException if this is a subMap, and fromKey is out
     *         of range
     * @throws NullPointerException if fromKey is null but the map does not
     allow
     *         null keys
     */
    fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.SortedMap
    {
        
        public fabric.util.Comparator comparator() {
            return ((fabric.util.SortedMap) fetch()).comparator();
        }
        
        public fabric.lang.Object firstKey() {
            return ((fabric.util.SortedMap) fetch()).firstKey();
        }
        
        public fabric.util.SortedMap headMap(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).headMap(arg1);
        }
        
        public fabric.lang.Object lastKey() {
            return ((fabric.util.SortedMap) fetch()).lastKey();
        }
        
        public fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                            fabric.lang.Object arg2) {
            return ((fabric.util.SortedMap) fetch()).subMap(arg1, arg2);
        }
        
        public fabric.util.SortedMap tailMap(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).tailMap(arg1);
        }
        
        public void clear() { ((fabric.util.SortedMap) fetch()).clear(); }
        
        public boolean containsKey(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).containsKey(arg1);
        }
        
        public boolean containsValue(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).containsValue(arg1);
        }
        
        public fabric.util.Set entrySet() {
            return ((fabric.util.SortedMap) fetch()).entrySet();
        }
        
        public fabric.lang.Object get(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).get(arg1);
        }
        
        public fabric.lang.Object put(fabric.lang.Object arg1,
                                      fabric.lang.Object arg2) {
            return ((fabric.util.SortedMap) fetch()).put(arg1, arg2);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.SortedMap) fetch()).isEmpty();
        }
        
        public fabric.util.Set keySet() {
            return ((fabric.util.SortedMap) fetch()).keySet();
        }
        
        public void putAll(fabric.util.Map arg1) {
            ((fabric.util.SortedMap) fetch()).putAll(arg1);
        }
        
        public fabric.lang.Object remove(fabric.lang.Object arg1) {
            return ((fabric.util.SortedMap) fetch()).remove(arg1);
        }
        
        public int size() { return ((fabric.util.SortedMap) fetch()).size(); }
        
        public fabric.util.Collection values() {
            return ((fabric.util.SortedMap) fetch()).values();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
