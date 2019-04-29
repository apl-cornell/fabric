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
        
        public static final byte[] $classHash = new byte[] { 26, -81, -43, -69,
        124, 0, 13, -109, -86, -41, 43, -76, -11, -40, 46, 45, 96, -91, 6, -7,
        -11, 92, -63, 105, 14, 91, 112, 9, 72, -53, 103, -3 };
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
            
            public static final byte[] $classHash = new byte[] { 23, -21, 75,
            -102, 60, -82, 68, -95, 113, -102, -109, 106, 59, -8, -127, -45,
            -101, -28, 84, -35, -43, -54, 22, -105, -27, -47, 82, -21, -75, -24,
            4, -100 };
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
        
        public static final byte[] $classHash = new byte[] { -120, 23, -15,
        -102, 14, -115, 24, -21, -110, -8, -113, 87, 16, 118, -72, -63, -75, 64,
        -43, -89, 13, 114, -113, 62, -81, 66, 76, 116, -104, 2, -17, -108 };
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
            
            public static final byte[] $classHash = new byte[] { 23, -21, 75,
            -102, 60, -82, 68, -95, 113, -102, -109, 106, 59, -8, -127, -45,
            -101, -28, 84, -35, -43, -54, 22, -105, -27, -47, 82, -21, -75, -24,
            4, -100 };
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
        
        public static final byte[] $classHash = new byte[] { 53, -62, 3, -24,
        88, 18, 30, -53, -46, 54, -62, 64, -62, 44, 59, 14, -126, 110, 89, -83,
        125, 12, -76, 20, -48, -35, -2, 93, -110, -33, -67, 121 };
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
    
    public static final byte[] $classHash = new byte[] { 26, -81, -43, -69, 124,
    0, 13, -109, -86, -41, 43, -76, -11, -40, 46, 45, 96, -91, 6, -7, -11, 92,
    -63, 105, 14, 91, 112, 9, 72, -53, 103, -3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
