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
        
        public static final byte[] $classHash = new byte[] { 79, 99, 17, -48,
        -9, 25, 37, -93, 114, -117, -69, -62, 60, 5, 93, 10, 5, -56, 125, -48,
        -127, -39, -42, 75, -74, 99, 7, -49, 24, -124, 78, -18 };
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
            
            public static final byte[] $classHash = new byte[] { 77, 70, -56,
            122, 13, -42, 86, -72, 124, -17, 38, 102, -119, 52, 44, 94, -116,
            59, 4, 9, -82, -95, 119, 9, -99, 127, -101, -107, 67, -37, -117,
            -109 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "H4sIAAAAAAAAAK1YfWwcRxV/d77YPseJHeerdRLHHxdXCeYuKVSoHJTGR91cc2msOIlUR+SY25uzN97b3e7O2eekoQUBCRRFqHXSljZRkYKA4KYSUEBCkfoHhVQFJBAqRRUQkCqKQqgqxIcQUN6b2bvb2zubVCLSzYxn3pt5H7/3m9ks3oAVrgODBZbTjbiYt7kbH2O5dGacOS7PpwzmugdxNqutjKTPvfnVfF8Ywhno1JhpmbrGjKzpClidOcZmWcLkInHoQDp5BKIaKe5h7rSA8JHRsgP9tmXMTxmW8A5p2P/sexILTxzt/mYLdE1Cl25OCCZ0LWWZgpfFJHQWeTHHHXd3Ps/zk7DG5Dw/wR2dGfpxFLTMSehx9SmTiZLD3QPctYxZEuxxSzZ35JmVSTLfQrOdkiYsB83vVuaXhG4kMrorkhloLejcyLsPwicgkoEVBYNNoeCGTMWLhNwxMUbzKN6ho5lOgWm8ohKZ0c28gK1BjarHsb0ogKptRS6mrepREZPhBPQokwxmTiUmhKObUyi6wirhKQJ6l9wUhdptps2wKZ4VcEtQblwtoVRUhoVUBKwPismdMGe9gZz5snXj/g+dOWHuMcMQQpvzXDPI/nZU6gsoHeAF7nBT40qxc0fmHNtw5XQYAIXXB4SVzHcfevvukb4XryqZTU1k9ueOcU1ktYu51T/bnNp+ZwuZ0W5brk5QqPNcZnXcW0mWbUT7huqOtBivLL544IcPPHKJXw9DRxpaNcsoFRFVazSraOsGd+7lJneY4Pk0RLmZT8n1NLThOKObXM3uLxRcLtIQMeRUqyX/xhAVcAsKURuOdbNgVcY2E9NyXLYBIIQ/QLfRG7jtbux7AAaeFbA3MW0VeSJnlPgcwjuBP84cbTqBdevo2ns1y55PuI6WcEqm0FFSzSvnd+cQ60wT+5gdRzPs/+92ZbK+ey4UwsBu1aw8zzEXs+QhZnTcwKLYYxl57mQ148yVNKy98pRETZSQ7iJaZVxCmOnNQY7w6y6URu95+3L2FYU40vXCJmCnMk9l02debDeS1HzRKrmxnbG9fN5NC8og1rwDnVRfcWSsODLWYqgcT11If0PCqNWV9VbdvRN3/6BtMFGwnGIZQiHp6jqpL0/E7M8gqyBxdG6f+Nh9Hz892ILAtecilFAUjQXLqEY+aRwxrI2s1nXqzb89f+6kVSsoAbGGOm/UpDodDMbNsTSeRx6sbb+jn72QvXIyFiaOiSL9CYYARS7pC55RV6/JCvdRNFZkYCXFgBm0VCGsDjHtWHO1GYmH1dT0KGhQsAIGStr88IR9/rWf/vF98kKpMGyXj4onuEj6qpo265L1u6YW+4MO5yj36yfHHz9749QRGXiUGGp2YIzaFFYzkyD4zNUHf/Xb31z8RbiaLChLF9a8g/9C+PsP/WieJqhHXk55bNBfpQObDhyumYTEYCA5ocVu7JBZtPJ6QWc5gxNA/tW1bdcLfzrTrbJs4IyKmQMj/3uD2vyto/DIK0f/3ie3CWl0MdXCVhNTbLe2tvNux2HzZEf5kz/f8tSP2HkEPHKVqx/nin48zJJR6wUM3URdkWivTPQuqTYi250ULS+m9Pcd1Ayq8G6W82G38aoYozu3htnJxOIzvam7riu2qGKW9hhowhaHma+cbr9U/Gt4sPWlMLRNQre87pkpDjNkPITLJF7YbsqbzMCquvX6y1fdNMlqTW4O1ovv2GC11FgKxyRN4w5VIAppGAj6wW0Y9S0Ag3/2+tdoda1N7bpyCOQgKVWGZDtMzXYVSAFttqPPIhQFRPVisSQILPKUHcheRWZn9QrveYld509shRRlJlXFUvuBqoWbyMI2tEwD2H4Dx/8E2PqdJhZ+dAkLafgRgbHRTWZUDIvgYyZG49vlmeXmuiFBfEwvQaXnQ1CIhqNlxNGWpd4p8o118VMLF/L7v7JLvSZ66u/+e8xS8blX//3j+JPXXm5ys7R6r87aqfRaHmh4Le+Tb7ga/K5d33JnauaNKXXm1oB9Qemv71t8+d5h7bEwtFRx1vBwrFdK1qOrw+H47jUP1mGsv5rBFsrgOIZsHWKrV/UDP/FnUOaAmm2BKg5QwloPOXOWM8Od+ATiRvH+rcGLUm75wDKckKXmsIBtassY5SPmZxn/jR2rAXOi6lYHKHSGhgGGXvL6bze4tTSy7FLOQGTVxynqbfQtr3/Ot+Ey7kwvs3aMGoaFOs3c+/Fzpgn3jTt6Ea+5We+ZzE8vfP6d+JkFhUn1LTHU8Jz366jvCXncKlkqVBkDy50iNcb+8PzJ73/t5KmwZ+p+tDJnWQZnZrNob8Sg7AKIXfL680tEm5p8Y1xJ5RmvP3dzcZ1bZm2eGnzEREwKqgfRHg+iRN9xRd/NEbqUf3cBbHvL63//7vwjld95/es359+nl1n7LDUPI1AdXrRmVaEZXnKps9D1WUvPN/OkD81IAQzf4fUD784TUun3+t6b8+SLy6w9Rs2jAlbGdFMXGZbjhpQ7Ucas+Atd3giI3E1NPiW8D1st9QN+8Y29I+uX+Iy4peG/Gjy9yxe62jdeOPRL+QiufrRG8Y1ZKBmG/7L2jVtthxd06URUXd227J5AZ3zXKGaCOunUWSXxJUyckqC/npbx6602J6RMb8mh/yBZ/MvGf7S2H7wm36IYs/59Y1ePr3r18Pceemu48Ln3jxz9QjISvfzluej5h58+m3r90cf/C1M7zSK4EQAA";
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
        
        public static final byte[] $classHash = new byte[] { 71, 21, -8, -49,
        -98, 120, 80, -104, -26, -119, -29, -69, -60, 80, -125, 86, 11, 33, -98,
        98, 55, -99, -25, -23, -123, 112, 100, -55, 104, -62, -52, 28 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1Ya2xURRQ+uy3bJ7QUClJKW8pK5OGuaKLRqrFdBVYWaVqKWtR19t7Z9tK7917nztItivERApLYH1oRE+kvjK+qiYn6w5AQgw+i8RUD+kMlMb6C/DDGR4yvMzN3X7cPMbHJzszOnHPmzHl852ynzsE8l0FnmqQMM8LHHOpGNpJUPNFLmEv1mElcdzvuJrW6yvih757W24IQTEC9RizbMjRiJi2Xw4LELrKbRC3KowN98a6dUKMJxs3EHeYQ3NmTY9Dh2ObYkGlz75Jp8h9bF514/I7GlyugYRAaDKufE25oMdviNMcHoT5DMynK3G5dp/ogLLQo1fspM4hp7EFC2xqEJtcYsgjPMur2Udc2dwvCJjfrUCbvzG8K9W1Um2U1bjNUv1Gpn+WGGU0YLu9KQChtUFN374J7oTIB89ImGULCJYn8K6JSYnSj2EfyWgPVZGmi0TxL5Yhh6Rza/RyFF4e3IAGyVmUoH7YLV1VaBDegSalkEmso2s+ZYQ0h6Tw7i7dwaJlVKBJVO0QbIUM0yeECP12vOkKqGmkWwcKh2U8mJaHPWnw+K/HWuZuuHr/b2mwFIYA661Qzhf7VyNTmY+qjacqopVHFWL82cYgsOXYgCIDEzT5iRfPaPT9et77t+DuKZvkMNNtSu6jGk9rR1IKPWmNrrqwQalQ7tmuIUCh7ufRqr3fSlXMw2pcUJIrDSP7weN9bt973HD0bhNo4hDTbzGYwqhZqdsYxTMo2UYsywqkehxpq6TF5HocqXCcMi6rdbem0S3kcKk25FbLldzRRGkUIE1Xh2rDSdn7tED4s1zkHAJbjB6oAAhrAmnO4/h2g/VUOW6LDdoZGU2aWjmJ4R/FDCdOGo5i3zNAu1mxnLOoyLcqyFjeQUu2rx3enMNaJxrcSJ4JqOP+vuJzQvnE0EEDDtmu2TlPERS95EdPTa2JSbLZNnbKkZo4fi8OiY0/IqKkRke5itEq7BNDTrX6MKOWdyPbc8OOLyXdVxAlez2wcVin1lDdL1At3I0iNZeysG74EtasXKRVBkIogSE0FcpHYZPx5GTkhV6ZYQWA9CrzKMQlP2yyTg0BAvm6x5JeXoMNHEEgQK+rX9N9+450HOiswVp3RSnSZIA37M6eIN3FcEUyHpNaw/7tfXjq01y7mEIfwtNSezilSs9NvKmZrVEfoK4pf20FeSR7bGw4KWKlBxOMEYxLho81/R1mKduXhTlhjXgLqhA2IKY7yGFXLh5k9WtyRIbBADE0qGoSxfApKpLym3zny6fvfXyZrSB5UG0rQt5/yrpJEFsIaZMouLNp+O6MU6T4/3PvoY+f275SGR4pVM10YFmMME5hg5tps3zt3ffblF0c/CRadxaHKYcZuzOucfMzCv/EvgJ+/xEeko9gQM4JyzIOCjgIWOOLq1UXlEBVMRCbU3Q0PWBlbN9IGSZlUhMofDRdueOWH8UblbxN3lPUYrP93AcX9ZT1w37t3/NomxQQ0UZWKBiySKahbVJTczRgZE3rk7v94xRNvkyMY+ghUrrGHSuwJeNErlGrmsHSWpBLHLdLNl0rSi+W4QVhICgB5drkYOpVJWwtp4a8NG0WRLUbsYHTqyZbYtWcVPBQiVshYOQM87CAlyXTpc5mfg52hN4NQNQiNsr4Ti+8gCHEYLINYod2Yt5mA+WXn5dVWlZauQka2+rOl5Fp/rhRhCdeCWqxrVXqo6EJDNAojBQAqFgMkb8H1Cly/LE4XSeMuzgVALq6WLKvkuFoMa6Qhg2K5luPNhkUU/K7jUIm9QVisL5MZmJuFl0PIyaZMA8MGQU90WEpAiaMgh55aMVvpl23L0QcmJvVtT21QBbqpvJzeYGUzL5z6873I4TMnZwDrkNfIFS+sxPtWTmtAt8q2qOjgM2dXXBkb+XpI3dnu089P/ezWqZObVmuPBKGi4MlpvVg5U1e5/2oZxVbS2l7mxY6CFxuEpZahI9GTHQlv7in1okLCGd0QEMvrcwVh1ULYAk9Itzd3lQibI9MG5ji7WQzbuEryGfKvlxkZBNrdXm9GD0wc/DsyPqG8phrYVdN6yFIe1cTKu+bLOBKxs3KuWyTHxm9f2vv6M3v3Bz09N3OowA5aviFRbuJ1aI35gPHhzTfOYmIx9E03qGCJe3NsdoP6sK/Jwz4BCBEFCPJomb81kBroc3hglxiwFa/20MyVVLd6thLTbViBUrZtUmLN9H4RDQMAF/V5c8d/e79gaffmZef9/kXe+0dtNkJZpB9LJ53DAHwOA+wRg40GMDiVNTh/x+LS+hL3DmVx8ZlBvqUVtRvEN5z15tPnZYZAvp0WLKe8+cPzy6sH5zjbJ4Z7OdSFDcvgCZKipqTDX6pQV9J0SjzGjFg+Q1/s/UrTYifo0a+3rG+epSe+YNrvZo/vxcmG6qWTA6dle1f4BVaD3VM6a5qlhahkHXIYTRvyDTWqLDlyOoh6l/gDEUNM8k0HFMXDiNyKQnwbV21A0V2zNQxYeyWRlNGSZeK/AVM/Lf0tVL39jOzC0KQdm5p/+2Ay13v4m4e+ev1E74M76lZOpq448u33+xz95PAb77X+AxFYUjWlEAAA";
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
            
            public static final byte[] $classHash = new byte[] { 77, 70, -56,
            122, 13, -42, 86, -72, 124, -17, 38, 102, -119, 52, 44, 94, -116,
            59, 4, 9, -82, -95, 119, 9, -99, 127, -101, -107, 67, -37, -117,
            -109 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1525719795000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "H4sIAAAAAAAAAK1YfWwcRxV/d3Zsn+PYjhMnqZM4jnNNleDekUCFyvHR+BonV58bK+dEqiNyzO3O+Tbe293uztnnpKEFAUkpilDrpC1tIiEFAcWkElIFEoroH0BTBSqBUPmogPBHRVGIUEF8CFHKezN732eTSkS6mfHMezPv4/d+M5ulW7DKc2E4yzKGGRELDvciYyyTSE4y1+N63GSeN4WzaW11a+LCW1/TB4MQTEKXxizbMjRmpi1PQHfyBJtjUYuL6JHDidgxCGmkeJB5OQHBY6NFF4Yc21yYMW3hH9Kw//n3RRefPt777RbomYYew0oJJgwtbluCF8U0dOV5PsNdb5+uc30a1lqc6ynuGsw0TqKgbU1Dn2fMWEwUXO4d5p5tzpFgn1dwuCvPLE2S+Taa7RY0Ybtofq8yvyAMM5o0PBFLQlvW4KbuPQyfgtYkrMqabAYFNyRLXkTljtExmkfxTgPNdLNM4yWV1lnD0gVsq9coexweRwFUbc9zkbPLR7VaDCegT5lkMmsmmhKuYc2g6Cq7gKcIGFh2UxTqcJg2y2Z4WsCmerlJtYRSIRkWUhHQXy8md8KcDdTlrCpbtx78yLlT1kErCAG0WeeaSfZ3oNJgndJhnuUutzSuFLt2Jy+wDVfPBgFQuL9OWMl855G37xsZfPmaktncROZQ5gTXRFq7nOn+6Zb4rntbyIwOx/YMgkKN5zKrk/5KrOgg2jeUd6TFSGnx5cM/euixF/jNIHQmoE2zzUIeUbVWs/OOYXL3ALe4ywTXExDilh6X6wlox3HSsLiaPZTNelwkoNWUU222/BtDlMUtKETtODasrF0aO0zk5LjoAEAAf4BuBycBDnrYbwAYvS5gPJqz8zyaMQt8HuEdxR9nrpaLYt26hna3ZjsLUc/Vom7BEgZKqnnl/L4MYp1pYoI5ETTD+f9uVyTre+cDAQzsNs3WeYZ5mCUfMaOTJhbFQdvUuZvWzHNXE7Du6rMSNSFCuodolXEJYKa31HNEte5iYXT/21fS1xXiSNcPm4C9yjyVzSrzwvuQpBbydsEL7wkfZeitlxCUQ6x6F7qowiLIWRHkrKVAMRK/lPimBFKbJyuuvH8X7v9hx2Qia7v5IgQC0tn1Ul+eifmfRV5B6ujalfrEA588O9yC0HXmWymlKBquL6QK/SRwxLA60lrPmbf+/uKF03alpASEGyq9UZMqdbg+cq6tcR2ZsLL97iH2Uvrq6XCQWCaEBCgYQhTZZLD+jJqKjZXYj6KxKgmrKQbMpKUSZXWKnGvPV2YkIrqp6VPgoGDVGSiJ86Mp5+IvX/vjB+SVUuLYnioyTnERq6pr2qxHVvDaSuynXM5R7jfPTD51/taZYzLwKLGj2YFhauNYz0yC4HPXHv7V7357+efBcrKgKF1Y+y7+C+DvP/SjeZqgHpk57vPBUJkQHDpwZ8UkpAYT6Qkt9sJHrLytG1mDZUxOAPl3z517XvrTuV6VZRNnVMxcGPnfG1Tm7xiFx64f/8eg3Cag0dVUCVtFTPHdusrO+1yXLZAdxU//bOuzr7CLCHhkK884yRUB+Zglo/oF7LiNyiLRAZnoPVJtRLbvp2j5MaW/76FmWIV3i5wPeo2XxRjduhXMTkeXnh+If+ym4osyZmmP7U344iirKqe9L+T/Fhxu+2EQ2qehV174zBKSBRAu03hle3F/MglratZrr19118TKNbmlvl6qjq2vlgpP4ZikadypCkQhDQNBP7gLA7Id4P5tfh+i1XUOteuLAZCDmFTZIdud1OxSgRTQ7rjGHEJRQMjI5wuCwCJP2Y3slWdO2ijxnp/Y9dWJLZGizKSqWGo/VLYwTBa240kzAON3Iz7eAbjv+00svH8ZC2n4cYGxMSxmlgxrxedMmMZ75ZnF5roBQXxMb0GlV4WgAA1Hi4ijrcu9VOQr6/JnFi/ph766R70n+mpv//1WIf+t19/5ceSZG682uVva/Hdn5VR6L29veC9PyFdcBX43bm69Nz775ow6c1udffXS35hYevXATu3JILSUcdbwdKxVitWiq9Pl+PK1pmowNlTOYAtlcAozsQkgPq760X9VZ1DmgJo766q4jhLW+ciZt91Z7kZSiBvF+3fUX5Ryy4dW4IQ0NUcF3KW2DFM+wtUsU3tnhyvQTJUd66TdNqNDu7Fo/uL3v29wbHlsOYWMidiqjVTI3+iG3/+6asMVHMqtsHaCGoalmmPeg/hJ04T9Jl0jjxfdnP9U5mcXv/Bu5NyiQqX6ntjR8KSv1lHfFPK4NbJYqDa2r3SK1Bj7w4unv/f102eCvqmH0MqMbZucWc2ivRGDcg/A/jf8/rVlok2N3hhXUvmJ379ye3GdX2FtgRp8xrRaFFQfpH0+SInAI4rAm2N0Of9GAQ4M+33/e/OPVNb7ffft+ffZFdY+T82jCFSX5+05VWqmn1zqbHR9zjb0Zp4MohkH0Iyc30+9N09IJeX3E7fnyZdWWHuSmicErA4bliGSLMNNKXeqKKC7ttTlrYDY3dzkg8L/vNXiP+CX3xwf6V/mY2JTw384+HpXLvV0bLx05BfyIVz+dA3hOzNbMM3qC7tq3Oa4PGtIN0Lq+nZk9zS6U3WVYi6ok26dVxJfxtQpCfrrORnBgXJzSsoMFFz6b5Klv278Z1vH1A35HsWoDU2MXTu55vWj333kzzuzj39w5PgXY62hK1+ZD1189Lnz8TeeeOq/hsTozb4RAAA=";
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
        
        public static final byte[] $classHash = new byte[] { 39, -81, -24, 125,
        -102, -92, 35, -120, 33, -55, -97, -84, 79, -26, 79, -108, 97, -36,
        -107, 24, 92, 43, 86, 113, -118, -51, -55, -33, 63, -76, -81, -109 };
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
                        fabric.worker.transaction.TransactionManager $tm581 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled584 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff582 = 1;
                        boolean $doBackoff583 = true;
                        boolean $retry577 = true;
                        boolean $keepReads578 = false;
                        $label575: for (boolean $commit576 = false; !$commit576;
                                        ) {
                            if ($backoffEnabled584) {
                                if ($doBackoff583) {
                                    if ($backoff582 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff582));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e579) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff582 < 5000) $backoff582 *= 2;
                                }
                                $doBackoff583 = $backoff582 <= 32 ||
                                                  !$doBackoff583;
                            }
                            $commit576 = true;
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
                                         RetryException $e579) {
                                    throw $e579;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e579) {
                                    throw $e579;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e579) {
                                    throw $e579;
                                }
                                catch (final Throwable $e579) {
                                    $tm581.getCurrentLog().checkRetrySignal();
                                    throw $e579;
                                }
                            }
                            catch (final fabric.worker.RetryException $e579) {
                                $commit576 = false;
                                continue $label575;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e579) {
                                $commit576 = false;
                                $retry577 = false;
                                $keepReads578 = $e579.keepReads;
                                if ($tm581.checkForStaleObjects()) {
                                    $retry577 = true;
                                    $keepReads578 = false;
                                    continue $label575;
                                }
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid ==
                                      null ||
                                      !$e579.tid.isDescendantOf(
                                                   $currentTid580)) {
                                    throw $e579;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e579);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e579) {
                                $commit576 = false;
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid.isDescendantOf($currentTid580))
                                    continue $label575;
                                if ($currentTid580.parent != null) {
                                    $retry577 = false;
                                    throw $e579;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e579) {
                                $commit576 = false;
                                if ($tm581.checkForStaleObjects())
                                    continue $label575;
                                $retry577 = false;
                                throw new fabric.worker.AbortException($e579);
                            }
                            finally {
                                if ($commit576) {
                                    fabric.common.TransactionID $currentTid580 =
                                      $tm581.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e579) {
                                        $commit576 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e579) {
                                        $commit576 = false;
                                        $retry577 = false;
                                        $keepReads578 = $e579.keepReads;
                                        if ($tm581.checkForStaleObjects()) {
                                            $retry577 = true;
                                            $keepReads578 = false;
                                            continue $label575;
                                        }
                                        if ($e579.tid ==
                                              null ||
                                              !$e579.tid.isDescendantOf(
                                                           $currentTid580))
                                            throw $e579;
                                        throw new fabric.worker.
                                                UserAbortException($e579);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e579) {
                                        $commit576 = false;
                                        $currentTid580 = $tm581.getCurrentTid();
                                        if ($currentTid580 != null) {
                                            if ($e579.tid.equals(
                                                            $currentTid580) ||
                                                  !$e579.tid.
                                                  isDescendantOf(
                                                    $currentTid580)) {
                                                throw $e579;
                                            }
                                        }
                                    }
                                } else if ($keepReads578) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit576) {
                                    {  }
                                    if ($retry577) { continue $label575; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 79, 99, 17, -48, -9,
    25, 37, -93, 114, -117, -69, -62, 60, 5, 93, 10, 5, -56, 125, -48, -127,
    -39, -42, 75, -74, 99, 7, -49, 24, -124, 78, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
