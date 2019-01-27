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
        
        public static final byte[] $classHash = new byte[] { 45, -91, -8, -86,
        -122, -52, 102, 88, 17, 107, -69, -8, -20, -19, 113, -28, 86, -16, 77,
        -49, 77, 21, 107, -87, -13, 122, 49, 124, 88, 6, 62, 19 };
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
            
            public static final byte[] $classHash = new byte[] { 38, 127, -121,
            -114, 3, 47, -43, 55, -59, 68, -76, -120, -1, 14, 42, -17, 73, 75,
            114, 71, -92, 96, 80, -74, 81, 75, 95, -87, 90, 76, 53, -27 };
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
        
        public static final byte[] $classHash = new byte[] { 89, -9, -37, -57,
        80, -104, -52, 73, -73, 99, 111, 31, 9, 84, -86, 39, 24, -6, -76, -29,
        -57, 8, 66, 3, -84, -36, 78, -63, 113, -30, 25, -23 };
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
            
            public static final byte[] $classHash = new byte[] { 38, 127, -121,
            -114, 3, 47, -43, 55, -59, 68, -76, -120, -1, 14, 42, -17, 73, 75,
            114, 71, -92, 96, 80, -74, 81, 75, 95, -87, 90, 76, 53, -27 };
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
        
        public static final byte[] $classHash = new byte[] { 35, 14, 35, 34,
        -83, -16, -57, -14, 69, 42, 12, 21, -8, 104, 69, 43, 111, 68, -70, 22,
        -121, 21, -90, 19, 101, -45, -44, -79, 97, -18, 77, 102 };
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
                        fabric.worker.transaction.TransactionManager $tm571 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled574 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff572 = 1;
                        boolean $doBackoff573 = true;
                        boolean $retry567 = true;
                        boolean $keepReads568 = false;
                        $label565: for (boolean $commit566 = false; !$commit566;
                                        ) {
                            if ($backoffEnabled574) {
                                if ($doBackoff573) {
                                    if ($backoff572 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff572));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e569) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff572 < 5000) $backoff572 *= 2;
                                }
                                $doBackoff573 = $backoff572 <= 32 ||
                                                  !$doBackoff573;
                            }
                            $commit566 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
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
                                catch (final fabric.worker.
                                         RetryException $e569) {
                                    throw $e569;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e569) {
                                    throw $e569;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e569) {
                                    throw $e569;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e569) {
                                    throw $e569;
                                }
                                catch (final Throwable $e569) {
                                    $tm571.getCurrentLog().checkRetrySignal();
                                    throw $e569;
                                }
                            }
                            catch (final fabric.worker.RetryException $e569) {
                                $commit566 = false;
                                continue $label565;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e569) {
                                $commit566 = false;
                                $retry567 = false;
                                $keepReads568 = $e569.keepReads;
                                if ($tm571.checkForStaleObjects()) {
                                    $retry567 = true;
                                    $keepReads568 = false;
                                    continue $label565;
                                }
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid ==
                                      null ||
                                      !$e569.tid.isDescendantOf(
                                                   $currentTid570)) {
                                    throw $e569;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e569);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e569) {
                                $commit566 = false;
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid.isDescendantOf($currentTid570))
                                    continue $label565;
                                if ($currentTid570.parent != null) {
                                    $retry567 = false;
                                    throw $e569;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e569) {
                                $commit566 = false;
                                if ($tm571.checkForStaleObjects())
                                    continue $label565;
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid.isDescendantOf($currentTid570)) {
                                    $retry567 = true;
                                }
                                else if ($currentTid570.parent != null) {
                                    $retry567 = false;
                                    throw $e569;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e569) {
                                $commit566 = false;
                                if ($tm571.checkForStaleObjects())
                                    continue $label565;
                                $retry567 = false;
                                throw new fabric.worker.AbortException($e569);
                            }
                            finally {
                                if ($commit566) {
                                    fabric.common.TransactionID $currentTid570 =
                                      $tm571.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e569) {
                                        $commit566 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e569) {
                                        $commit566 = false;
                                        $retry567 = false;
                                        $keepReads568 = $e569.keepReads;
                                        if ($tm571.checkForStaleObjects()) {
                                            $retry567 = true;
                                            $keepReads568 = false;
                                            continue $label565;
                                        }
                                        if ($e569.tid ==
                                              null ||
                                              !$e569.tid.isDescendantOf(
                                                           $currentTid570))
                                            throw $e569;
                                        throw new fabric.worker.
                                                UserAbortException($e569);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e569) {
                                        $commit566 = false;
                                        $currentTid570 = $tm571.getCurrentTid();
                                        if ($currentTid570 != null) {
                                            if ($e569.tid.equals(
                                                            $currentTid570) ||
                                                  !$e569.tid.
                                                  isDescendantOf(
                                                    $currentTid570)) {
                                                throw $e569;
                                            }
                                        }
                                    }
                                } else if ($keepReads568) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit566) {
                                    {  }
                                    if ($retry567) { continue $label565; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 45, -91, -8, -86, -122,
    -52, 102, 88, 17, 107, -69, -8, -20, -19, 113, -28, 86, -16, 77, -49, 77,
    21, 107, -87, -13, 122, 49, 124, 88, 6, 62, 19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
