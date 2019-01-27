package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface SortedMap extends fabric.util.Map, fabric.lang.Object {
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
   * is not included; if you want this value to be included, pass its successor
   * object in to toKey.  For example, for Integers, you could request
   * <code>headMap(Integer.valueOf(limit.intValue() + 1))</code>.
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
   * <code>subMap(Integer.valueOf(lowlimit.intValue() + 1),
   * Integer.valueOf(highlimit.intValue() + 1))</code> to reverse
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
    fabric.util.SortedMap subMap(fabric.lang.Object fromKey, fabric.lang.Object toKey);
    
    /**
   * Returns a view of the portion of the map greater than or equal to
   * fromKey. The view is backed by this map, so changes in one show up
   * in the other. The submap supports all optional operations of the original.
   * <p>
   *
   * The returned map throws an IllegalArgumentException any time a key is
   * used which is out of the range of fromKey. Note that the endpoint, fromKey, is
   * included; if you do not want this value to be included, pass its successor object in
   * to fromKey.  For example, for Integers, you could request
   * <code>tailMap(Integer.valueOf(limit.intValue() + 1))</code>.
   *
   * @param fromKey the inclusive lower range of the submap
   * @return the submap
   * @throws ClassCastException if fromKey is not comparable to the map
   *         contents
   * @throws IllegalArgumentException if this is a subMap, and fromKey is out
   *         of range
   * @throws NullPointerException if fromKey is null but the map does not allow
   *         null keys
   */
    fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.SortedMap {
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
    
    public static final byte[] $classHash = new byte[] { -41, -121, 33, -54,
    -84, 59, -127, 5, -27, 15, 77, 13, 34, 54, 1, -24, -40, -43, -76, -53, -65,
    109, 93, 95, -10, 105, 90, 40, 74, -75, 0, 83 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
