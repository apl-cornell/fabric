package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * An abstract implementation of Map to make it easier to create your own
 * implementations. In order to create an unmodifiable Map, subclass
 * AbstractMap and implement the <code>entrySet</code> (usually via an
 * AbstractSet).  To make it modifiable, also implement <code>put</code>,
 * and have <code>entrySet().iterator(Store)</code> support <code>remove</code>.
 * <p>
 *
 * It is recommended that classes which extend this support at least the
 * no-argument constructor, and a constructor which accepts another Map.
 * Further methods in this class may be overridden if you have a more
 * efficient implementation.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Map
 * @see Collection
 * @see HashMap
 * @see LinkedHashMap
 * @see TreeMap
 * @see WeakHashMap
 * @see IdentityHashMap
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractMap extends fabric.util.Map, fabric.lang.Object {
    public fabric.util.Set get$keys();
    
    public fabric.util.Set set$keys(fabric.util.Set val);
    
    public fabric.util.Collection get$values();
    
    public fabric.util.Collection set$values(fabric.util.Collection val);
    
    /**
   * The main constructor, for use by subclasses.
   */
    public fabric.util.AbstractMap fabric$util$AbstractMap$();
    
    /**
   * Returns a set view of the mappings in this Map.  Each element in the
   * set must be an implementation of Map.Entry.  The set is backed by
   * the map, so that changes in one show up in the other.  Modifications
   * made while an iterator is in progress cause undefined behavior.  If
   * the set supports removal, these methods must be valid:
   * <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>.
   * Element addition is not supported via this set.
   *
   * @return the entry set
   * @see Map.Entry
   */
    public abstract fabric.util.Set entrySet();
    
    /**
   * Remove all entries from this Map (optional operation). This default
   * implementation calls entrySet().clear(). NOTE: If the entry set does
   * not permit clearing, then this will fail, too. Subclasses often
   * override this for efficiency.  Your implementation of entrySet() should
   * not call <code>AbstractMap.clear</code> unless you want an infinite loop.
   *
   * @throws UnsupportedOperationException if <code>entrySet().clear()</code>
   *         does not support clearing.
   * @see Set#clear()
   */
    public void clear();
    
    /**
   * Returns true if this contains a mapping for the given key. This
   * implementation does a linear search, O(n), over the
   * <code>entrySet()</code>, returning <code>true</code> if a match
   * is found, <code>false</code> if the iteration ends. Many subclasses
   * can implement this more efficiently.
   *
   * @param key the key to search for
   * @return true if the map contains the key
   * @throws NullPointerException if key is <code>null</code> but the map
   *         does not permit null keys
   * @see #containsValue(Object)
   */
    public boolean containsKey(fabric.lang.Object key);
    
    /**
   * Returns true if this contains at least one mapping with the given value.
   * This implementation does a linear search, O(n), over the
   * <code>entrySet()</code>, returning <code>true</code> if a match
   * is found, <code>false</code> if the iteration ends. A match is
   * defined as a value, v, where <code>(value == null ? v == null :
   * value.equals(v))</code>.  Subclasses are unlikely to implement
   * this more efficiently.
   *
   * @param value the value to search for
   * @return true if the map contains the value
   * @see #containsKey(Object)
   */
    public boolean containsValue(fabric.lang.Object value);
    
    /**
   * Compares the specified object with this map for equality. Returns
   * <code>true</code> if the other object is a Map with the same mappings,
   * that is,<br>
   * <code>o instanceof Map && entrySet().equals(((Map) o).entrySet();</code>
   *
   * @param o the object to be compared
   * @return true if the object equals this map
   * @see Set#equals(Object)
   */
    public boolean equals(fabric.lang.Object o);
    
    /**
   * Returns the value mapped by the given key. Returns <code>null</code> if
   * there is no mapping.  However, in Maps that accept null values, you
   * must rely on <code>containsKey</code> to determine if a mapping exists.
   * This iteration takes linear time, searching entrySet().iterator(Store) of
   * the key.  Many implementations override this method.
   *
   * @param key the key to look up
   * @return the value associated with the key, or null if key not in map
   * @throws NullPointerException if this map does not accept null keys
   * @see #containsKey(Object)
   */
    public fabric.lang.Object get(fabric.lang.Object key);
    
    /**
   * Returns the hash code for this map. As defined in Map, this is the sum
   * of all hashcodes for each Map.Entry object in entrySet, or basically
   * entrySet().hashCode().
   *
   * @return the hash code
   * @see Map.Entry#hashCode()
   * @see Set#hashCode()
   */
    public int hashCode();
    
    /**
   * Returns true if the map contains no mappings. This is implemented by
   * <code>size() == 0</code>.
   *
   * @return true if the map is empty
   * @see #size()
   */
    public boolean isEmpty();
    
    /**
   * Returns a set view of this map's keys. The set is backed by the map,
   * so changes in one show up in the other. Modifications while an iteration
   * is in progress produce undefined behavior. The set supports removal
   * if entrySet() does, but does not support element addition.
   * <p>
   *
   * This implementation creates an AbstractSet, where the iterator wraps
   * the entrySet iterator, size defers to the Map's size, and contains
   * defers to the Map's containsKey. The set is created on first use, and
   * returned on subsequent uses, although since no synchronization occurs,
   * there is a slight possibility of creating two sets.
   *
   * @return a Set view of the keys
   * @see Set#iterator(fabric.worker.Store)
   * @see #size()
   * @see #containsKey(Object)
   * @see #values()
   */
    public fabric.util.Set keySet();
    
    /**
   * Associates the given key to the given value (optional operation). If the
   * map already contains the key, its value is replaced. This implementation
   * simply throws an UnsupportedOperationException. Be aware that in a map
   * that permits <code>null</code> values, a null return does not always
   * imply that the mapping was created.
   *
   * @param key the key to map
   * @param value the value to be mapped
   * @return the previous value of the key, or null if there was no mapping
   * @throws UnsupportedOperationException if the operation is not supported
   * @throws ClassCastException if the key or value is of the wrong type
   * @throws IllegalArgumentException if something about this key or value
   *         prevents it from existing in this map
   * @throws NullPointerException if the map forbids null keys or values
   * @see #containsKey(Object)
   */
    public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
    
    /**
   * Copies all entries of the given map to this one (optional operation). If
   * the map already contains a key, its value is replaced. This implementation
   * simply iterates over the map's entrySet(), calling <code>put</code>,
   * so it is not supported if puts are not.
   *
   * @param m the mapping to load into this map
   * @throws UnsupportedOperationException if the operation is not supported
   *         by this map.
   * @throws ClassCastException if a key or value is of the wrong type for
   *         adding to this map.
   * @throws IllegalArgumentException if something about a key or value
   *         prevents it from existing in this map.
   * @throws NullPointerException if the map forbids null keys or values.
   * @throws NullPointerException if <code>m</code> is null.
   * @see #put(Object, Object)
   */
    public void putAll(fabric.util.Map m);
    
    /**
   * Removes the mapping for this key if present (optional operation). This
   * implementation iterates over the entrySet searching for a matching
   * key, at which point it calls the iterator's <code>remove</code> method.
   * It returns the result of <code>getValue()</code> on the entry, if found,
   * or null if no entry is found. Note that maps which permit null values
   * may also return null if the key was removed.  If the entrySet does not
   * support removal, this will also fail. This is O(n), so many
   * implementations override it for efficiency.
   *
   * @param key the key to remove
   * @return the value the key mapped to, or null if not present.
   *         Null may also be returned if null values are allowed
   *         in the map and the value of this mapping is null.
   * @throws UnsupportedOperationException if deletion is unsupported
   * @see Iterator#remove()
   */
    public fabric.lang.Object remove(fabric.lang.Object key);
    
    /**
   * Returns the number of key-value mappings in the map. If there are more
   * than Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is
   * implemented as <code>entrySet().size()</code>.
   *
   * @return the number of mappings
   * @see Set#size()
   */
    public int size();
    
    /**
   * Returns a String representation of this map. This is a listing of the
   * map entries (which are specified in Map.Entry as being
   * <code>getKey() + "=" + getValue()</code>), separated by a comma and
   * space (", "), and surrounded by braces ('{' and '}'). This implementation
   * uses a StringBuffer and iterates over the entrySet to build the String.
   * Note that this can fail with an exception if underlying keys or
   * values complete abruptly in toString().
   *
   * @return a String representation
   * @see Map.Entry#toString()
   */
    public java.lang.String toString();
    
    /**
   * Returns a collection or bag view of this map's values. The collection
   * is backed by the map, so changes in one show up in the other.
   * Modifications while an iteration is in progress produce undefined
   * behavior. The collection supports removal if entrySet() does, but
   * does not support element addition.
   * <p>
   *
   * This implementation creates an AbstractCollection, where the iterator
   * wraps the entrySet iterator, size defers to the Map's size, and contains
   * defers to the Map's containsValue. The collection is created on first
   * use, and returned on subsequent uses, although since no synchronization
   * occurs, there is a slight possibility of creating two collections.
   *
   * @return a Collection view of the values
   * @see Collection#iterator(fabric.worker.Store)
   * @see #size()
   * @see #containsValue(Object)
   * @see #keySet()
   */
    public fabric.util.Collection values();
    
    /**
   * A class which implements Map.Entry. It is shared by HashMap, TreeMap,
   * Hashtable, and Collections. It is not specified by the JDK, but makes
   * life much easier.
   *
   * @author Jon Zeppieri
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface BasicMapEntry extends Entry, fabric.lang.Object {
        public fabric.lang.Object get$key();
        
        public fabric.lang.Object set$key(fabric.lang.Object val);
        
        public fabric.lang.Object get$value();
        
        public fabric.lang.Object set$value(fabric.lang.Object val);
        
        /**
     * Basic constructor initializes the fields.
     * @param newKey the key
     * @param newValue the value
     */
        public BasicMapEntry
          fabric$util$AbstractMap$BasicMapEntry$(fabric.lang.Object newKey, fabric.lang.Object newValue);
        
        /**
     * Compares the specified object with this entry. Returns true only if
     * the object is a mapping of identical key and value. In other words,
     * this must be:<br>
     * <pre>(o instanceof Map.Entry)
     *       && (getKey() == null ? ((HashMap) o).getKey() == null
     *           : getKey().equals(((HashMap) o).getKey()))
     *       && (getValue() == null ? ((HashMap) o).getValue() == null
     *           : getValue().equals(((HashMap) o).getValue()))</pre>
     *
     * @param o the object to compare
     * @return <code>true</code> if it is equal
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Get the key corresponding to this entry.
     *
     * @return the key
     */
        public fabric.lang.Object getKey();
        
        /**
     * Get the value corresponding to this entry. If you already called
     * Iterator.remove(), the behavior undefined, but in this case it works.
     *
     * @return the value
     */
        public fabric.lang.Object getValue();
        
        /**
     * Returns the hash code of the entry.  This is defined as the exclusive-or
     * of the hashcodes of the key and value (using 0 for null). In other
     * words, this must be:<br>
     * <pre>(getKey() == null ? 0 : getKey().hashCode())
     *       ^ (getValue() == null ? 0 : getValue().hashCode())</pre>
     *
     * @return the hash code
     */
        public int hashCode();
        
        /**
     * Replaces the value with the specified object. This writes through
     * to the map, unless you have already called Iterator.remove(). It
     * may be overridden to restrict a null value.
     *
     * @param newVal the new value to store
     * @return the old value
     * @throws NullPointerException if the map forbids null values.
     * @throws UnsupportedOperationException if the map doesn't support
     *          <code>put()</code>.
     * @throws ClassCastException if the value is of a type unsupported
     *         by the map.
     * @throws IllegalArgumentException if something else about this
     *         value prevents it being stored in the map.
     */
        public fabric.lang.Object setValue(fabric.lang.Object newVal);
        
        /**
     * This provides a string representation of the entry. It is of the form
     * "key=value", where string concatenation is used on key and value.
     *
     * @return the string representation
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements BasicMapEntry {
            public fabric.lang.Object get$key() {
                return ((fabric.util.AbstractMap.BasicMapEntry._Impl) fetch()).
                  get$key();
            }
            
            public fabric.lang.Object set$key(fabric.lang.Object val) {
                return ((fabric.util.AbstractMap.BasicMapEntry._Impl) fetch()).
                  set$key(val);
            }
            
            public fabric.lang.Object get$value() {
                return ((fabric.util.AbstractMap.BasicMapEntry._Impl) fetch()).
                  get$value();
            }
            
            public fabric.lang.Object set$value(fabric.lang.Object val) {
                return ((fabric.util.AbstractMap.BasicMapEntry._Impl) fetch()).
                  set$value(val);
            }
            
            public fabric.util.AbstractMap.BasicMapEntry
              fabric$util$AbstractMap$BasicMapEntry$(fabric.lang.Object arg1,
                                                     fabric.lang.Object arg2) {
                return ((fabric.util.AbstractMap.BasicMapEntry) fetch()).
                  fabric$util$AbstractMap$BasicMapEntry$(arg1, arg2);
            }
            
            public final fabric.lang.Object getKey() {
                return ((fabric.util.AbstractMap.BasicMapEntry) fetch()).getKey(
                                                                           );
            }
            
            public final fabric.lang.Object getValue() {
                return ((fabric.util.AbstractMap.BasicMapEntry) fetch()).
                  getValue();
            }
            
            public fabric.lang.Object setValue(fabric.lang.Object arg1) {
                return ((fabric.util.AbstractMap.BasicMapEntry) fetch()).
                  setValue(arg1);
            }
            
            public _Proxy(BasicMapEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements BasicMapEntry {
            public fabric.lang.Object get$key() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.key;
            }
            
            public fabric.lang.Object set$key(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.key = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * The key. Package visible for direct manipulation.
     */
            fabric.lang.Object key;
            
            public fabric.lang.Object get$value() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.value;
            }
            
            public fabric.lang.Object set$value(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.value = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * The value. Package visible for direct manipulation.
     */
            fabric.lang.Object value;
            
            /**
     * Basic constructor initializes the fields.
     * @param newKey the key
     * @param newValue the value
     */
            public BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
              fabric.lang.Object newKey, fabric.lang.Object newValue) {
                fabric$lang$Object$();
                this.set$key(newKey);
                this.set$value(newValue);
                return (BasicMapEntry) this.$getProxy();
            }
            
            /**
     * Compares the specified object with this entry. Returns true only if
     * the object is a mapping of identical key and value. In other words,
     * this must be:<br>
     * <pre>(o instanceof Map.Entry)
     *       && (getKey() == null ? ((HashMap) o).getKey() == null
     *           : getKey().equals(((HashMap) o).getKey()))
     *       && (getValue() == null ? ((HashMap) o).getValue() == null
     *           : getValue().equals(((HashMap) o).getValue()))</pre>
     *
     * @param o the object to compare
     * @return <code>true</code> if it is equal
     */
            public final boolean equals(fabric.lang.Object o) {
                if (!(fabric.lang.Object._Proxy.
                        $getProxy(
                          (java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.
                            $unwrap(o)) instanceof Entry)) return false;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(o)) instanceof BasicMapEntry) {
                    BasicMapEntry e = (BasicMapEntry)
                                        fabric.lang.Object._Proxy.$getProxy(o);
                    return fabric.util.AbstractMap._Impl.equals(this.get$key(),
                                                                e.get$key()) &&
                      fabric.util.AbstractMap._Impl.equals(this.get$value(),
                                                           e.get$value());
                }
                Entry e = (Entry) fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.util.AbstractMap._Impl.equals(this.get$key(),
                                                            e.getKey()) &&
                  fabric.util.AbstractMap._Impl.equals(this.get$value(),
                                                       e.getValue());
            }
            
            /**
     * Get the key corresponding to this entry.
     *
     * @return the key
     */
            public final fabric.lang.Object getKey() { return this.get$key(); }
            
            /**
     * Get the value corresponding to this entry. If you already called
     * Iterator.remove(), the behavior undefined, but in this case it works.
     *
     * @return the value
     */
            public final fabric.lang.Object getValue() {
                return this.get$value();
            }
            
            /**
     * Returns the hash code of the entry.  This is defined as the exclusive-or
     * of the hashcodes of the key and value (using 0 for null). In other
     * words, this must be:<br>
     * <pre>(getKey() == null ? 0 : getKey().hashCode())
     *       ^ (getValue() == null ? 0 : getValue().hashCode())</pre>
     *
     * @return the hash code
     */
            public final int hashCode() {
                return fabric.util.AbstractMap._Impl.hashCode(this.get$key()) ^
                  fabric.util.AbstractMap._Impl.hashCode(this.get$value());
            }
            
            /**
     * Replaces the value with the specified object. This writes through
     * to the map, unless you have already called Iterator.remove(). It
     * may be overridden to restrict a null value.
     *
     * @param newVal the new value to store
     * @return the old value
     * @throws NullPointerException if the map forbids null values.
     * @throws UnsupportedOperationException if the map doesn't support
     *          <code>put()</code>.
     * @throws ClassCastException if the value is of a type unsupported
     *         by the map.
     * @throws IllegalArgumentException if something else about this
     *         value prevents it being stored in the map.
     */
            public fabric.lang.Object setValue(fabric.lang.Object newVal) {
                fabric.lang.Object r = this.get$value();
                this.set$value(newVal);
                return r;
            }
            
            /**
     * This provides a string representation of the entry. It is of the form
     * "key=value", where string concatenation is used on key and value.
     *
     * @return the string representation
     */
            public final java.lang.String toString() {
                return java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(this.get$key())) +
                "=" +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$value()));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (BasicMapEntry) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractMap.BasicMapEntry._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.key, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.value, refTypes, out,
                          intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.key = (fabric.lang.Object)
                             $readRef(fabric.lang.Object._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
                this.value = (fabric.lang.Object)
                               $readRef(fabric.lang.Object._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractMap.BasicMapEntry._Impl src =
                  (fabric.util.AbstractMap.BasicMapEntry._Impl) other;
                this.key = src.key;
                this.value = src.value;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractMap.BasicMapEntry._Static {
                public _Proxy(fabric.util.AbstractMap.BasicMapEntry._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractMap.BasicMapEntry.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      AbstractMap.
                      BasicMapEntry.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractMap.
                        BasicMapEntry.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractMap.BasicMapEntry._Static.
                            _Impl.class);
                    $instance = (fabric.util.AbstractMap.BasicMapEntry._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.BasicMapEntry._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.BasicMapEntry._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 110, -115, 117, 88,
        -10, -112, 23, 87, -52, -86, 95, 59, 41, -122, 84, 6, -43, -54, 112,
        -38, 114, 39, 70, 82, 73, 107, -85, 14, -14, 70, -9, -43 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$0 extends fabric.util.AbstractSet {
        public fabric.util.AbstractMap get$out$();
        
        /**
	 * Retrieves the number of keys in the backing map.
	 *
	 * @return The number of keys.
	 */
        public int size();
        
        /**
	 * Returns true if the backing map contains the
	 * supplied key.
	 *
	 * @param key The key to search for.
	 * @return True if the key was found, false otherwise.
	 */
        public boolean contains(fabric.lang.Object key);
        
        public static interface KeysIterator
          extends fabric.util.Iterator, fabric.lang.Object {
            public fabric.util.AbstractMap.Anonymous$0 get$out$();
            
            public KeysIterator fabric$util$AbstractMap$KeysIterator$(
              fabric.worker.Store store);
            
            public fabric.util.Iterator get$map_iterator();
            
            public fabric.util.Iterator set$map_iterator(fabric.util.Iterator val);
            
            /**
	   * Returns true if a call to <code>next()</code> will
	   * return another key.
	   *
	   * @return True if the iterator has not yet reached
	   *         the last key.
	   */
            public boolean hasNext();
            
            /**
	   * Returns the key from the next entry retrieved
	   * by the underlying <code>entrySet()</code> iterator.
	   *
	   * @return The next key.
	   */
            public fabric.lang.Object next();
            
            /**
	   * Removes the map entry which has a key equal
	   * to that returned by the last call to
	   * <code>next()</code>.
	   *
	   * @throws UnsupportedOperationException if the
	   *         map doesn't support removal.
	   */
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements KeysIterator {
                public fabric.util.AbstractMap.Anonymous$0 get$out$() {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                              _Impl) fetch()).get$out$();
                }
                
                public fabric.util.Iterator get$map_iterator() {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                              _Impl) fetch()).get$map_iterator();
                }
                
                public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val) {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                              _Impl) fetch()).set$map_iterator(val);
                }
                
                public fabric.util.AbstractMap.Anonymous$0.KeysIterator
                  fabric$util$AbstractMap$KeysIterator$(
                  fabric.worker.Store arg1) {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator)
                              fetch()).fabric$util$AbstractMap$KeysIterator$(
                                         arg1);
                }
                
                public boolean hasNext() {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator)
                              fetch()).hasNext();
                }
                
                public fabric.lang.Object next() {
                    return ((fabric.util.AbstractMap.Anonymous$0.KeysIterator)
                              fetch()).next();
                }
                
                public void remove() {
                    ((fabric.util.AbstractMap.Anonymous$0.KeysIterator)
                       fetch()).remove();
                }
                
                public _Proxy(KeysIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl
              implements KeysIterator {
                public fabric.util.AbstractMap.Anonymous$0 get$out$() {
                    return this.out$;
                }
                
                private Anonymous$0 out$;
                
                public KeysIterator fabric$util$AbstractMap$KeysIterator$(
                  fabric.worker.Store store) {
                    this.set$map_iterator(
                           this.get$out$().get$out$().entrySet().iterator(
                                                                   store));
                    fabric$lang$Object$();
                    return (KeysIterator) this.$getProxy();
                }
                
                public fabric.util.Iterator get$map_iterator() {
                    return this.map_iterator;
                }
                
                public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean transactionCreated = tm.registerWrite(this);
                    this.map_iterator = val;
                    if (transactionCreated) tm.commitTransaction();
                    return val;
                }
                
                private fabric.util.Iterator map_iterator;
                
                /**
	   * Returns true if a call to <code>next()</code> will
	   * return another key.
	   *
	   * @return True if the iterator has not yet reached
	   *         the last key.
	   */
                public boolean hasNext() {
                    return this.get$map_iterator().hasNext();
                }
                
                /**
	   * Returns the key from the next entry retrieved
	   * by the underlying <code>entrySet()</code> iterator.
	   *
	   * @return The next key.
	   */
                public fabric.lang.Object next() {
                    return ((Entry)
                              fabric.lang.Object._Proxy.$getProxy(
                                                          this.get$map_iterator(
                                                                 ).next())).
                      getKey();
                }
                
                /**
	   * Removes the map entry which has a key equal
	   * to that returned by the last call to
	   * <code>next()</code>.
	   *
	   * @throws UnsupportedOperationException if the
	   *         map doesn't support removal.
	   */
                public void remove() { this.get$map_iterator().remove(); }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (KeysIterator) this.$getProxy();
                }
                
                public _Impl(fabric.worker.Store $location,
                             final Anonymous$0 out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                             _Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                    $writeRef($getStore(), this.map_iterator, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                    this.out$ =
                      (fabric.util.AbstractMap.Anonymous$0)
                        $readRef(
                          fabric.util.AbstractMap.Anonymous$0._Proxy.class,
                          (fabric.common.RefTypeEnum) refTypes.next(), in,
                          store, intraStoreRefs, interStoreRefs);
                    this.map_iterator =
                      (fabric.util.Iterator)
                        $readRef(fabric.util.Iterator._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.AbstractMap.Anonymous$0.KeysIterator._Impl src =
                      (fabric.util.AbstractMap.Anonymous$0.KeysIterator._Impl)
                        other;
                    this.out$ = src.out$;
                    this.map_iterator = src.map_iterator;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                               _Static
                {
                    public _Proxy(fabric.util.AbstractMap.Anonymous$0.
                                    KeysIterator._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.AbstractMap.Anonymous$0.
                      KeysIterator._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          AbstractMap.
                          Anonymous$0.
                          KeysIterator.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            AbstractMap.
                            Anonymous$0.
                            KeysIterator.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                                _Static._Impl.class);
                        $instance =
                          (fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                            _Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.AbstractMap.Anonymous$0.KeysIterator.
                               _Static
                {
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store,
                                 long onum,
                                 int version,
                                 fabric.worker.metrics.
                                   ImmutableObjectSet associates, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.
                      io.
                      IOException,
                        java.
                      lang.
                      ClassNotFoundException {
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.AbstractMap.Anonymous$0.
                                 KeysIterator._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 13, -60, 17,
            -85, -30, 9, -106, -106, 65, 50, -70, 67, -57, -10, 77, 78, -82,
            -51, -65, 19, -12, 97, -37, -51, -12, 51, -125, 115, 62, -81, -50,
            -36 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        /**
	 * Returns an iterator which iterates over the keys
	 * in the backing map, using a wrapper around the
	 * iterator returned by <code>entrySet()</code>.
	 *
	 * @return An iterator over the keys.
	 */
        public fabric.util.Iterator iterator(final fabric.worker.Store store);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements Anonymous$0 {
            public fabric.util.AbstractMap get$out$() {
                return ((fabric.util.AbstractMap.Anonymous$0._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$0._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractSet._Impl
          implements Anonymous$0 {
            public fabric.util.AbstractMap get$out$() { return this.out$; }
            
            private fabric.util.AbstractMap out$;
            
            /**
	 * Retrieves the number of keys in the backing map.
	 *
	 * @return The number of keys.
	 */
            public int size() { return this.get$out$().size(); }
            
            /**
	 * Returns true if the backing map contains the
	 * supplied key.
	 *
	 * @param key The key to search for.
	 * @return True if the key was found, false otherwise.
	 */
            public boolean contains(fabric.lang.Object key) {
                return this.get$out$().containsKey(key);
            }
            
            /**
	 * Returns an iterator which iterates over the keys
	 * in the backing map, using a wrapper around the
	 * iterator returned by <code>entrySet()</code>.
	 *
	 * @return An iterator over the keys.
	 */
            public fabric.util.Iterator iterator(
              final fabric.worker.Store store) {
                return (KeysIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.AbstractMap.Anonymous$0.KeysIterator)
                              new fabric.util.AbstractMap.Anonymous$0.
                                KeysIterator._Impl(
                                store,
                                (fabric.util.AbstractMap.Anonymous$0)
                                  this.$getProxy()).
                              $getProxy()).
                               fabric$util$AbstractMap$KeysIterator$(store));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$0) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.AbstractMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractMap.Anonymous$0._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.AbstractMap)
                              $readRef(fabric.util.AbstractMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractMap.Anonymous$0._Impl src =
                  (fabric.util.AbstractMap.Anonymous$0._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractMap.Anonymous$0._Static {
                public _Proxy(fabric.util.AbstractMap.Anonymous$0._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractMap.Anonymous$0._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractMap.
                      Anonymous$0.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractMap.
                        Anonymous$0.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractMap.Anonymous$0._Static.
                            _Impl.class);
                    $instance = (fabric.util.AbstractMap.Anonymous$0._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$0._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.Anonymous$0._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 30, 75, -75, -90,
        -50, -93, -27, 64, 107, -79, -64, 52, 31, 56, 46, -119, -75, 108, -93,
        82, 30, 79, -28, -110, 111, -27, 100, 117, -82, -16, -56, -102 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static interface Anonymous$1 extends fabric.util.AbstractCollection {
        public fabric.util.AbstractMap get$out$();
        
        /**
	 * Returns the number of values stored in
	 * the backing map.
	 *
	 * @return The number of values.
	 */
        public int size();
        
        /**
	 * Returns true if the backing map contains
	 * the supplied value.
	 *
	 * @param value The value to search for.
	 * @return True if the value was found, false otherwise.
	 */
        public boolean contains(fabric.lang.Object value);
        
        public static interface ValuesIterator
          extends fabric.util.Iterator, fabric.lang.Object {
            public fabric.util.AbstractMap.Anonymous$1 get$out$();
            
            public ValuesIterator fabric$util$AbstractMap$ValuesIterator$(
              fabric.worker.Store store);
            
            public fabric.util.Iterator get$map_iterator();
            
            public fabric.util.Iterator set$map_iterator(fabric.util.Iterator val);
            
            /**
	   * Returns true if a call to <code>next()</call> will
	   * return another value.
	   *
	   * @return True if the iterator has not yet reached
	   * the last value.
	   */
            public boolean hasNext();
            
            /**
	   * Returns the value from the next entry retrieved
	   * by the underlying <code>entrySet()</code> iterator.
	   *
	   * @return The next value.
	   */
            public fabric.lang.Object next();
            
            /**
	   * Removes the map entry which has a key equal
	   * to that returned by the last call to
	   * <code>next()</code>.
	   *
	   * @throws UnsupportedOperationException if the
	   *         map doesn't support removal.
	   */
            public void remove();
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements ValuesIterator {
                public fabric.util.AbstractMap.Anonymous$1 get$out$() {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                              _Impl) fetch()).get$out$();
                }
                
                public fabric.util.Iterator get$map_iterator() {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                              _Impl) fetch()).get$map_iterator();
                }
                
                public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val) {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                              _Impl) fetch()).set$map_iterator(val);
                }
                
                public fabric.util.AbstractMap.Anonymous$1.ValuesIterator
                  fabric$util$AbstractMap$ValuesIterator$(
                  fabric.worker.Store arg1) {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator)
                              fetch()).fabric$util$AbstractMap$ValuesIterator$(
                                         arg1);
                }
                
                public boolean hasNext() {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator)
                              fetch()).hasNext();
                }
                
                public fabric.lang.Object next() {
                    return ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator)
                              fetch()).next();
                }
                
                public void remove() {
                    ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator)
                       fetch()).remove();
                }
                
                public _Proxy(ValuesIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static class _Impl extends fabric.lang.Object._Impl
              implements ValuesIterator {
                public fabric.util.AbstractMap.Anonymous$1 get$out$() {
                    return this.out$;
                }
                
                private Anonymous$1 out$;
                
                public ValuesIterator fabric$util$AbstractMap$ValuesIterator$(
                  fabric.worker.Store store) {
                    this.set$map_iterator(
                           this.get$out$().get$out$().entrySet().iterator(
                                                                   store));
                    fabric$lang$Object$();
                    return (ValuesIterator) this.$getProxy();
                }
                
                public fabric.util.Iterator get$map_iterator() {
                    return this.map_iterator;
                }
                
                public fabric.util.Iterator set$map_iterator(
                  fabric.util.Iterator val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean transactionCreated = tm.registerWrite(this);
                    this.map_iterator = val;
                    if (transactionCreated) tm.commitTransaction();
                    return val;
                }
                
                private fabric.util.Iterator map_iterator;
                
                /**
	   * Returns true if a call to <code>next()</call> will
	   * return another value.
	   *
	   * @return True if the iterator has not yet reached
	   * the last value.
	   */
                public boolean hasNext() {
                    return this.get$map_iterator().hasNext();
                }
                
                /**
	   * Returns the value from the next entry retrieved
	   * by the underlying <code>entrySet()</code> iterator.
	   *
	   * @return The next value.
	   */
                public fabric.lang.Object next() {
                    return ((Entry)
                              fabric.lang.Object._Proxy.$getProxy(
                                                          this.get$map_iterator(
                                                                 ).next())).
                      getValue();
                }
                
                /**
	   * Removes the map entry which has a key equal
	   * to that returned by the last call to
	   * <code>next()</code>.
	   *
	   * @throws UnsupportedOperationException if the
	   *         map doesn't support removal.
	   */
                public void remove() { this.get$map_iterator().remove(); }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (ValuesIterator) this.$getProxy();
                }
                
                public _Impl(fabric.worker.Store $location,
                             final Anonymous$1 out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.Anonymous$1.
                             ValuesIterator._Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                    $writeRef($getStore(), this.map_iterator, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                    this.out$ =
                      (fabric.util.AbstractMap.Anonymous$1)
                        $readRef(
                          fabric.util.AbstractMap.Anonymous$1._Proxy.class,
                          (fabric.common.RefTypeEnum) refTypes.next(), in,
                          store, intraStoreRefs, interStoreRefs);
                    this.map_iterator =
                      (fabric.util.Iterator)
                        $readRef(fabric.util.Iterator._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.AbstractMap.Anonymous$1.ValuesIterator._Impl
                      src =
                      (fabric.util.AbstractMap.Anonymous$1.ValuesIterator._Impl)
                        other;
                    this.out$ = src.out$;
                    this.map_iterator = src.map_iterator;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                               _Static
                {
                    public _Proxy(fabric.util.AbstractMap.Anonymous$1.
                                    ValuesIterator._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.AbstractMap.Anonymous$1.
                      ValuesIterator._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          AbstractMap.
                          Anonymous$1.
                          ValuesIterator.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            AbstractMap.
                            Anonymous$1.
                            ValuesIterator.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.AbstractMap.Anonymous$1.
                                ValuesIterator._Static._Impl.class);
                        $instance =
                          (fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                            _Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.AbstractMap.Anonymous$1.ValuesIterator.
                               _Static
                {
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store,
                                 long onum,
                                 int version,
                                 fabric.worker.metrics.
                                   ImmutableObjectSet associates, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.
                      io.
                      IOException,
                        java.
                      lang.
                      ClassNotFoundException {
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.AbstractMap.Anonymous$1.
                                 ValuesIterator._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { 13, -60, 17,
            -85, -30, 9, -106, -106, 65, 50, -70, 67, -57, -10, 77, 78, -82,
            -51, -65, 19, -12, 97, -37, -51, -12, 51, -125, 115, 62, -81, -50,
            -36 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        /**
	 * Returns an iterator which iterates over the
	 * values in the backing map, by using a wrapper
	 * around the iterator returned by <code>entrySet()</code>.
	 *
	 * @return An iterator over the values.
	 */
        public fabric.util.Iterator iterator(final fabric.worker.Store store);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractCollection._Proxy
          implements Anonymous$1 {
            public fabric.util.AbstractMap get$out$() {
                return ((fabric.util.AbstractMap.Anonymous$1._Impl) fetch()).
                  get$out$();
            }
            
            public _Proxy(Anonymous$1._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractCollection._Impl
          implements Anonymous$1 {
            public fabric.util.AbstractMap get$out$() { return this.out$; }
            
            private fabric.util.AbstractMap out$;
            
            /**
	 * Returns the number of values stored in
	 * the backing map.
	 *
	 * @return The number of values.
	 */
            public int size() { return this.get$out$().size(); }
            
            /**
	 * Returns true if the backing map contains
	 * the supplied value.
	 *
	 * @param value The value to search for.
	 * @return True if the value was found, false otherwise.
	 */
            public boolean contains(fabric.lang.Object value) {
                return this.get$out$().containsValue(value);
            }
            
            /**
	 * Returns an iterator which iterates over the
	 * values in the backing map, by using a wrapper
	 * around the iterator returned by <code>entrySet()</code>.
	 *
	 * @return An iterator over the values.
	 */
            public fabric.util.Iterator iterator(
              final fabric.worker.Store store) {
                return (ValuesIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.AbstractMap.Anonymous$1.ValuesIterator)
                              new fabric.util.AbstractMap.Anonymous$1.
                                ValuesIterator._Impl(
                                store,
                                (fabric.util.AbstractMap.Anonymous$1)
                                  this.$getProxy()).
                              $getProxy()).
                               fabric$util$AbstractMap$ValuesIterator$(store));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Anonymous$1) this.$getProxy();
            }
            
            private _Impl(fabric.worker.Store $location,
                          final fabric.util.AbstractMap out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractMap.Anonymous$1._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.AbstractMap)
                              $readRef(fabric.util.AbstractMap._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractMap.Anonymous$1._Impl src =
                  (fabric.util.AbstractMap.Anonymous$1._Impl) other;
                this.out$ = src.out$;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractMap.Anonymous$1._Static {
                public _Proxy(fabric.util.AbstractMap.Anonymous$1._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractMap.Anonymous$1._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractMap.
                      Anonymous$1.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractMap.
                        Anonymous$1.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractMap.Anonymous$1._Static.
                            _Impl.class);
                    $instance = (fabric.util.AbstractMap.Anonymous$1._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractMap.Anonymous$1._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.Anonymous$1._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 35, 33, -66, -28,
        -27, -41, -40, 40, 114, 19, -120, -8, -66, -3, 73, -21, -98, 53, -30,
        105, -44, -114, -22, 17, 69, -33, -111, -104, -82, 29, -19, 35 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractMap {
        public fabric.util.Set get$keys() {
            return ((fabric.util.AbstractMap._Impl) fetch()).get$keys();
        }
        
        public fabric.util.Set set$keys(fabric.util.Set val) {
            return ((fabric.util.AbstractMap._Impl) fetch()).set$keys(val);
        }
        
        public fabric.util.Collection get$values() {
            return ((fabric.util.AbstractMap._Impl) fetch()).get$values();
        }
        
        public fabric.util.Collection set$values(fabric.util.Collection val) {
            return ((fabric.util.AbstractMap._Impl) fetch()).set$values(val);
        }
        
        public fabric.util.AbstractMap fabric$util$AbstractMap$() {
            return ((fabric.util.AbstractMap) fetch()).fabric$util$AbstractMap$(
                                                         );
        }
        
        public fabric.util.Set entrySet() {
            return ((fabric.util.AbstractMap) fetch()).entrySet();
        }
        
        public void clear() { ((fabric.util.AbstractMap) fetch()).clear(); }
        
        public boolean containsKey(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractMap) fetch()).containsKey(arg1);
        }
        
        public boolean containsValue(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractMap) fetch()).containsValue(arg1);
        }
        
        public fabric.lang.Object get(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractMap) fetch()).get(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.util.AbstractMap) fetch()).isEmpty();
        }
        
        public fabric.util.Set keySet() {
            return ((fabric.util.AbstractMap) fetch()).keySet();
        }
        
        public fabric.lang.Object put(fabric.lang.Object arg1,
                                      fabric.lang.Object arg2) {
            return ((fabric.util.AbstractMap) fetch()).put(arg1, arg2);
        }
        
        public void putAll(fabric.util.Map arg1) {
            ((fabric.util.AbstractMap) fetch()).putAll(arg1);
        }
        
        public fabric.lang.Object remove(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractMap) fetch()).remove(arg1);
        }
        
        public int size() { return ((fabric.util.AbstractMap) fetch()).size(); }
        
        public fabric.util.Collection values() {
            return ((fabric.util.AbstractMap) fetch()).values();
        }
        
        public static final boolean equals(fabric.lang.Object arg1,
                                           fabric.lang.Object arg2) {
            return fabric.util.AbstractMap._Impl.equals(arg1, arg2);
        }
        
        public static final int hashCode(fabric.lang.Object arg1) {
            return fabric.util.AbstractMap._Impl.hashCode(arg1);
        }
        
        public _Proxy(AbstractMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractMap {
        public fabric.util.Set get$keys() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.keys;
        }
        
        public fabric.util.Set set$keys(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.keys = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The cache for {@link #keySet()}.
   */
        fabric.util.Set keys;
        
        public fabric.util.Collection get$values() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.values;
        }
        
        public fabric.util.Collection set$values(fabric.util.Collection val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.values = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The cache for {@link #values()}.
   */
        fabric.util.Collection values;
        
        /**
   * The main constructor, for use by subclasses.
   */
        public fabric.util.AbstractMap fabric$util$AbstractMap$() {
            fabric$lang$Object$();
            entrySet();
            keySet();
            values();
            return (fabric.util.AbstractMap) this.$getProxy();
        }
        
        /**
   * Returns a set view of the mappings in this Map.  Each element in the
   * set must be an implementation of Map.Entry.  The set is backed by
   * the map, so that changes in one show up in the other.  Modifications
   * made while an iterator is in progress cause undefined behavior.  If
   * the set supports removal, these methods must be valid:
   * <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>.
   * Element addition is not supported via this set.
   *
   * @return the entry set
   * @see Map.Entry
   */
        public abstract fabric.util.Set entrySet();
        
        /**
   * Remove all entries from this Map (optional operation). This default
   * implementation calls entrySet().clear(). NOTE: If the entry set does
   * not permit clearing, then this will fail, too. Subclasses often
   * override this for efficiency.  Your implementation of entrySet() should
   * not call <code>AbstractMap.clear</code> unless you want an infinite loop.
   *
   * @throws UnsupportedOperationException if <code>entrySet().clear()</code>
   *         does not support clearing.
   * @see Set#clear()
   */
        public void clear() { entrySet().clear(); }
        
        /**
   * Returns true if this contains a mapping for the given key. This
   * implementation does a linear search, O(n), over the
   * <code>entrySet()</code>, returning <code>true</code> if a match
   * is found, <code>false</code> if the iteration ends. Many subclasses
   * can implement this more efficiently.
   *
   * @param key the key to search for
   * @return true if the map contains the key
   * @throws NullPointerException if key is <code>null</code> but the map
   *         does not permit null keys
   * @see #containsValue(Object)
   */
        public boolean containsKey(fabric.lang.Object key) {
            fabric.util.Iterator
              entries =
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0)
                if (fabric.util.AbstractMap._Impl.
                      equals(
                        key,
                        ((Entry)
                           fabric.lang.Object._Proxy.$getProxy(entries.next())).
                            getKey()))
                    return true;
            return false;
        }
        
        /**
   * Returns true if this contains at least one mapping with the given value.
   * This implementation does a linear search, O(n), over the
   * <code>entrySet()</code>, returning <code>true</code> if a match
   * is found, <code>false</code> if the iteration ends. A match is
   * defined as a value, v, where <code>(value == null ? v == null :
   * value.equals(v))</code>.  Subclasses are unlikely to implement
   * this more efficiently.
   *
   * @param value the value to search for
   * @return true if the map contains the value
   * @see #containsKey(Object)
   */
        public boolean containsValue(fabric.lang.Object value) {
            fabric.util.Iterator
              entries =
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0)
                if (fabric.util.AbstractMap._Impl.
                      equals(
                        value,
                        ((Entry)
                           fabric.lang.Object._Proxy.$getProxy(entries.next())).
                            getValue()))
                    return true;
            return false;
        }
        
        /**
   * Compares the specified object with this map for equality. Returns
   * <code>true</code> if the other object is a Map with the same mappings,
   * that is,<br>
   * <code>o instanceof Map && entrySet().equals(((Map) o).entrySet();</code>
   *
   * @param o the object to be compared
   * @return true if the object equals this map
   * @see Set#equals(Object)
   */
        public boolean equals(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.
              idEquals(o, (fabric.util.AbstractMap) this.$getProxy()) ||
              fabric.lang.Object._Proxy.
              $getProxy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap(o)) instanceof fabric.util.Map &&
              entrySet().equals(
                           ((fabric.util.Map)
                              fabric.lang.Object._Proxy.$getProxy(o)).entrySet(
                                                                        ));
        }
        
        /**
   * Returns the value mapped by the given key. Returns <code>null</code> if
   * there is no mapping.  However, in Maps that accept null values, you
   * must rely on <code>containsKey</code> to determine if a mapping exists.
   * This iteration takes linear time, searching entrySet().iterator(Store) of
   * the key.  Many implementations override this method.
   *
   * @param key the key to look up
   * @return the value associated with the key, or null if key not in map
   * @throws NullPointerException if this map does not accept null keys
   * @see #containsKey(Object)
   */
        public fabric.lang.Object get(fabric.lang.Object key) {
            fabric.util.Iterator
              entries =
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0) {
                Entry entry =
                  (Entry) fabric.lang.Object._Proxy.$getProxy(entries.next());
                if (fabric.util.AbstractMap._Impl.equals(key, entry.getKey()))
                    return entry.getValue();
            }
            return null;
        }
        
        /**
   * Returns the hash code for this map. As defined in Map, this is the sum
   * of all hashcodes for each Map.Entry object in entrySet, or basically
   * entrySet().hashCode().
   *
   * @return the hash code
   * @see Map.Entry#hashCode()
   * @see Set#hashCode()
   */
        public int hashCode() { return entrySet().hashCode(); }
        
        /**
   * Returns true if the map contains no mappings. This is implemented by
   * <code>size() == 0</code>.
   *
   * @return true if the map is empty
   * @see #size()
   */
        public boolean isEmpty() { return size() == 0; }
        
        /**
   * Returns a set view of this map's keys. The set is backed by the map,
   * so changes in one show up in the other. Modifications while an iteration
   * is in progress produce undefined behavior. The set supports removal
   * if entrySet() does, but does not support element addition.
   * <p>
   *
   * This implementation creates an AbstractSet, where the iterator wraps
   * the entrySet iterator, size defers to the Map's size, and contains
   * defers to the Map's containsKey. The set is created on first use, and
   * returned on subsequent uses, although since no synchronization occurs,
   * there is a slight possibility of creating two sets.
   *
   * @return a Set view of the keys
   * @see Set#iterator(fabric.worker.Store)
   * @see #size()
   * @see #containsKey(Object)
   * @see #values()
   */
        public fabric.util.Set keySet() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$keys(), null))
                this.
                  set$keys(
                    (fabric.util.AbstractSet)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.AbstractMap.Anonymous$0)
                           new fabric.util.AbstractMap.Anonymous$0._Impl(
                             this.$getStore(),
                             (fabric.util.AbstractMap) this.$getProxy()).
                           $getProxy()).fabric$util$AbstractSet$()));
            return this.get$keys();
        }
        
        /**
   * Associates the given key to the given value (optional operation). If the
   * map already contains the key, its value is replaced. This implementation
   * simply throws an UnsupportedOperationException. Be aware that in a map
   * that permits <code>null</code> values, a null return does not always
   * imply that the mapping was created.
   *
   * @param key the key to map
   * @param value the value to be mapped
   * @return the previous value of the key, or null if there was no mapping
   * @throws UnsupportedOperationException if the operation is not supported
   * @throws ClassCastException if the key or value is of the wrong type
   * @throws IllegalArgumentException if something about this key or value
   *         prevents it from existing in this map
   * @throws NullPointerException if the map forbids null keys or values
   * @see #containsKey(Object)
   */
        public fabric.lang.Object put(fabric.lang.Object key,
                                      fabric.lang.Object value) {
            throw new java.lang.UnsupportedOperationException();
        }
        
        /**
   * Copies all entries of the given map to this one (optional operation). If
   * the map already contains a key, its value is replaced. This implementation
   * simply iterates over the map's entrySet(), calling <code>put</code>,
   * so it is not supported if puts are not.
   *
   * @param m the mapping to load into this map
   * @throws UnsupportedOperationException if the operation is not supported
   *         by this map.
   * @throws ClassCastException if a key or value is of the wrong type for
   *         adding to this map.
   * @throws IllegalArgumentException if something about a key or value
   *         prevents it from existing in this map.
   * @throws NullPointerException if the map forbids null keys or values.
   * @throws NullPointerException if <code>m</code> is null.
   * @see #put(Object, Object)
   */
        public void putAll(fabric.util.Map m) {
            fabric.util.Iterator
              entries =
              m.
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = m.size();
            while (--pos >= 0) {
                Entry entry =
                  (Entry) fabric.lang.Object._Proxy.$getProxy(entries.next());
                put(entry.getKey(), entry.getValue());
            }
        }
        
        /**
   * Removes the mapping for this key if present (optional operation). This
   * implementation iterates over the entrySet searching for a matching
   * key, at which point it calls the iterator's <code>remove</code> method.
   * It returns the result of <code>getValue()</code> on the entry, if found,
   * or null if no entry is found. Note that maps which permit null values
   * may also return null if the key was removed.  If the entrySet does not
   * support removal, this will also fail. This is O(n), so many
   * implementations override it for efficiency.
   *
   * @param key the key to remove
   * @return the value the key mapped to, or null if not present.
   *         Null may also be returned if null values are allowed
   *         in the map and the value of this mapping is null.
   * @throws UnsupportedOperationException if deletion is unsupported
   * @see Iterator#remove()
   */
        public fabric.lang.Object remove(fabric.lang.Object key) {
            fabric.util.Iterator
              entries =
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0) {
                Entry entry =
                  (Entry) fabric.lang.Object._Proxy.$getProxy(entries.next());
                if (fabric.util.AbstractMap._Impl.equals(key, entry.getKey())) {
                    fabric.lang.Object r = entry.getValue();
                    entries.remove();
                    return r;
                }
            }
            return null;
        }
        
        /**
   * Returns the number of key-value mappings in the map. If there are more
   * than Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is
   * implemented as <code>entrySet().size()</code>.
   *
   * @return the number of mappings
   * @see Set#size()
   */
        public int size() { return entrySet().size(); }
        
        /**
   * Returns a String representation of this map. This is a listing of the
   * map entries (which are specified in Map.Entry as being
   * <code>getKey() + "=" + getValue()</code>), separated by a comma and
   * space (", "), and surrounded by braces ('{' and '}'). This implementation
   * uses a StringBuffer and iterates over the entrySet to build the String.
   * Note that this can fail with an exception if underlying keys or
   * values complete abruptly in toString().
   *
   * @return a String representation
   * @see Map.Entry#toString()
   */
        public java.lang.String toString() {
            fabric.util.Iterator
              entries =
              entrySet().
              iterator(
                fabric.util.AbstractMap._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            java.lang.StringBuffer r = new java.lang.StringBuffer("{");
            for (int pos = size(); pos > 0; pos--) {
                Entry entry =
                  (Entry) fabric.lang.Object._Proxy.$getProxy(entries.next());
                r.append(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          entry.getKey()));
                r.append('=');
                r.append(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          entry.getValue()));
                if (pos > 1) r.append(", ");
            }
            r.append("}");
            return r.toString();
        }
        
        /**
   * Returns a collection or bag view of this map's values. The collection
   * is backed by the map, so changes in one show up in the other.
   * Modifications while an iteration is in progress produce undefined
   * behavior. The collection supports removal if entrySet() does, but
   * does not support element addition.
   * <p>
   *
   * This implementation creates an AbstractCollection, where the iterator
   * wraps the entrySet iterator, size defers to the Map's size, and contains
   * defers to the Map's containsValue. The collection is created on first
   * use, and returned on subsequent uses, although since no synchronization
   * occurs, there is a slight possibility of creating two collections.
   *
   * @return a Collection view of the values
   * @see Collection#iterator(fabric.worker.Store)
   * @see #size()
   * @see #containsValue(Object)
   * @see #keySet()
   */
        public fabric.util.Collection values() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$values(), null))
                this.
                  set$values(
                    (fabric.util.AbstractCollection)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.AbstractMap.Anonymous$1)
                           new fabric.util.AbstractMap.Anonymous$1._Impl(
                             this.$getStore(),
                             (fabric.util.AbstractMap) this.$getProxy()).
                           $getProxy()).fabric$util$AbstractCollection$()));
            return this.get$values();
        }
        
        /**
   * Compare two objects according to Collection semantics.
   *
   * @param o1 the first object
   * @param o2 the second object
   * @return o1 == o2 || (o1 != null && o1.equals(o2))
   */
        public static final boolean equals(fabric.lang.Object o1,
                                           fabric.lang.Object o2) {
            return fabric.lang.Object._Proxy.idEquals(o1, o2) ||
              !fabric.lang.Object._Proxy.idEquals(o1, null) && o1.equals(o2);
        }
        
        /**
   * Hash an object according to Collection semantics.
   *
   * @param o the object to hash
   * @return o1 == null ? 0 : o1.hashCode()
   */
        public static final int hashCode(fabric.lang.Object o) {
            return fabric.lang.Object._Proxy.idEquals(o, null)
              ? 0
              : ((java.lang.Object)
                   fabric.lang.WrappedJavaInlineable.$unwrap(o)).hashCode();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractMap._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.keys, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.values, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.keys = (fabric.util.Set)
                          $readRef(fabric.util.Set._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.values = (fabric.util.Collection)
                            $readRef(fabric.util.Collection._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.AbstractMap._Impl src = (fabric.util.AbstractMap._Impl)
                                                  other;
            this.keys = src.keys;
            this.values = src.values;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$KEYS();
        
        public int set$KEYS(int val);
        
        public int postInc$KEYS();
        
        public int postDec$KEYS();
        
        public int get$VALUES();
        
        public int set$VALUES(int val);
        
        public int postInc$VALUES();
        
        public int postDec$VALUES();
        
        public int get$ENTRIES();
        
        public int set$ENTRIES(int val);
        
        public int postInc$ENTRIES();
        
        public int postDec$ENTRIES();
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractMap._Static {
            public int get$KEYS() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  get$KEYS();
            }
            
            public int set$KEYS(int val) {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  set$KEYS(val);
            }
            
            public int postInc$KEYS() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postInc$KEYS();
            }
            
            public int postDec$KEYS() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postDec$KEYS();
            }
            
            public int get$VALUES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  get$VALUES();
            }
            
            public int set$VALUES(int val) {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  set$VALUES(val);
            }
            
            public int postInc$VALUES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postInc$VALUES();
            }
            
            public int postDec$VALUES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postDec$VALUES();
            }
            
            public int get$ENTRIES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  get$ENTRIES();
            }
            
            public int set$ENTRIES(int val) {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  set$ENTRIES(val);
            }
            
            public int postInc$ENTRIES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postInc$ENTRIES();
            }
            
            public int postDec$ENTRIES() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  postDec$ENTRIES();
            }
            
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.AbstractMap._Static._Impl) fetch()).
                  set$LOCAL_STORE(val);
            }
            
            public _Proxy(fabric.util.AbstractMap._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractMap._Static $instance;
            
            static {
                fabric.
                  util.
                  AbstractMap.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.AbstractMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractMap._Static._Impl.class);
                $instance = (fabric.util.AbstractMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractMap._Static {
            public int get$KEYS() { return this.KEYS; }
            
            public int set$KEYS(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.KEYS = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$KEYS() {
                int tmp = this.get$KEYS();
                this.set$KEYS((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$KEYS() {
                int tmp = this.get$KEYS();
                this.set$KEYS((int) (tmp - 1));
                return tmp;
            }
            
            int KEYS;
            
            public int get$VALUES() { return this.VALUES; }
            
            public int set$VALUES(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.VALUES = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$VALUES() {
                int tmp = this.get$VALUES();
                this.set$VALUES((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$VALUES() {
                int tmp = this.get$VALUES();
                this.set$VALUES((int) (tmp - 1));
                return tmp;
            }
            
            int VALUES;
            
            public int get$ENTRIES() { return this.ENTRIES; }
            
            public int set$ENTRIES(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.ENTRIES = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$ENTRIES() {
                int tmp = this.get$ENTRIES();
                this.set$ENTRIES((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$ENTRIES() {
                int tmp = this.get$ENTRIES();
                this.set$ENTRIES((int) (tmp - 1));
                return tmp;
            }
            
            int ENTRIES;
            
            public fabric.worker.Store get$LOCAL_STORE() {
                return this.LOCAL_STORE;
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.LOCAL_STORE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.worker.Store LOCAL_STORE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.KEYS);
                out.writeInt(this.VALUES);
                out.writeInt(this.ENTRIES);
                $writeInline(out, this.LOCAL_STORE);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.KEYS = in.readInt();
                this.VALUES = in.readInt();
                this.ENTRIES = in.readInt();
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractMap._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm559 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled562 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff560 = 1;
                        boolean $doBackoff561 = true;
                        boolean $retry555 = true;
                        boolean $keepReads556 = false;
                        $label553: for (boolean $commit554 = false; !$commit554;
                                        ) {
                            if ($backoffEnabled562) {
                                if ($doBackoff561) {
                                    if ($backoff560 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff560));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e557) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff560 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff560 =
                                          java.lang.Math.
                                            min(
                                              $backoff560 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff561 = $backoff560 <= 32 ||
                                                  !$doBackoff561;
                            }
                            $commit554 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.AbstractMap._Static._Proxy.
                                  $instance.
                                  set$KEYS((int) 0);
                                fabric.util.AbstractMap._Static._Proxy.
                                  $instance.
                                  set$VALUES((int) 1);
                                fabric.util.AbstractMap._Static._Proxy.
                                  $instance.
                                  set$ENTRIES((int) 2);
                                fabric.util.AbstractMap._Static._Proxy.
                                  $instance.
                                  set$LOCAL_STORE(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                            }
                            catch (final fabric.worker.RetryException $e557) {
                                $commit554 = false;
                                continue $label553;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e557) {
                                $commit554 = false;
                                $retry555 = false;
                                $keepReads556 = $e557.keepReads;
                                fabric.common.TransactionID $currentTid558 =
                                  $tm559.getCurrentTid();
                                if ($e557.tid ==
                                      null ||
                                      !$e557.tid.isDescendantOf(
                                                   $currentTid558)) {
                                    throw $e557;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e557);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e557) {
                                $commit554 = false;
                                fabric.common.TransactionID $currentTid558 =
                                  $tm559.getCurrentTid();
                                if ($e557.tid.isDescendantOf($currentTid558))
                                    continue $label553;
                                if ($currentTid558.parent != null) {
                                    $retry555 = false;
                                    throw $e557;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e557) {
                                $commit554 = false;
                                $retry555 = false;
                                if ($tm559.inNestedTxn()) {
                                    $keepReads556 = true;
                                }
                                throw $e557;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid558 =
                                  $tm559.getCurrentTid();
                                if ($commit554) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e557) {
                                        $commit554 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e557) {
                                        $commit554 = false;
                                        $retry555 = false;
                                        $keepReads556 = $e557.keepReads;
                                        if ($e557.tid ==
                                              null ||
                                              !$e557.tid.isDescendantOf(
                                                           $currentTid558))
                                            throw $e557;
                                        throw new fabric.worker.
                                                UserAbortException($e557);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e557) {
                                        $commit554 = false;
                                        $currentTid558 = $tm559.getCurrentTid();
                                        if ($currentTid558 != null) {
                                            if ($e557.tid.equals(
                                                            $currentTid558) ||
                                                  !$e557.tid.
                                                  isDescendantOf(
                                                    $currentTid558)) {
                                                throw $e557;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm559.inNestedTxn() &&
                                          $tm559.checkForStaleObjects()) {
                                        $retry555 = true;
                                        $keepReads556 = false;
                                    }
                                    if ($keepReads556) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e557) {
                                            $currentTid558 = $tm559.getCurrentTid();
                                            if ($currentTid558 != null &&
                                                  ($e557.tid.equals($currentTid558) || !$e557.tid.isDescendantOf($currentTid558))) {
                                                throw $e557;
                                            } else {
                                                $retry555 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit554) {
                                    {  }
                                    if ($retry555) { continue $label553; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 110, -115, 117, 88,
    -10, -112, 23, 87, -52, -86, 95, 59, 41, -122, 84, 6, -43, -54, 112, -38,
    114, 39, 70, 82, 73, 107, -85, 14, -14, 70, -9, -43 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
