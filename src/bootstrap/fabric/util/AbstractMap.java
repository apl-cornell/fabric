package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface AbstractMap
  extends fabric.util.Map, fabric.lang.Object
{
    
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
    public fabric.lang.Object put(fabric.lang.Object key,
                                  fabric.lang.Object value);
    
    /**
     * Copies all entries of the given map to this one (optional operation). If
     * the map already contains a key, its value is replaced. This
     implementation
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
    public static interface BasicMapEntry
      extends Entry, fabric.lang.Object
    {
        
        public fabric.lang.Object get$key();
        
        public fabric.lang.Object set$key(fabric.lang.Object val);
        
        public fabric.lang.Object get$value();
        
        public fabric.lang.Object set$value(fabric.lang.Object val);
        
        /**
         * Basic constructor initializes the fields.
         * @param newKey the key
         * @param newValue the value
         */
        public BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
          fabric.lang.Object newKey, fabric.lang.Object newValue);
        
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
         * Returns the hash code of the entry.  This is defined as the
         exclusive-or
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
          implements BasicMapEntry
        {
            
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
            
            public native fabric.util.AbstractMap.BasicMapEntry
              fabric$util$AbstractMap$BasicMapEntry$(fabric.lang.Object arg1,
                                                     fabric.lang.Object arg2);
            
            public final native fabric.lang.Object getKey();
            
            public final native fabric.lang.Object getValue();
            
            public native fabric.lang.Object setValue(fabric.lang.Object arg1);
            
            public _Proxy(BasicMapEntry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements BasicMapEntry
        {
            
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
            public native BasicMapEntry fabric$util$AbstractMap$BasicMapEntry$(
              fabric.lang.Object newKey, fabric.lang.Object newValue);
            
            /**
             * Compares the specified object with this entry. Returns true only
             if
             * the object is a mapping of identical key and value. In other
             words,
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
            public final native boolean equals(fabric.lang.Object o);
            
            /**
             * Get the key corresponding to this entry.
             *
             * @return the key
             */
            public final native fabric.lang.Object getKey();
            
            /**
             * Get the value corresponding to this entry. If you already called
             * Iterator.remove(), the behavior undefined, but in this case it
             works.
             *
             * @return the value
             */
            public final native fabric.lang.Object getValue();
            
            /**
             * Returns the hash code of the entry.  This is defined as the
             exclusive-or
             * of the hashcodes of the key and value (using 0 for null). In
             other
             * words, this must be:<br>
             * <pre>(getKey() == null ? 0 : getKey().hashCode())
             *       ^ (getValue() == null ? 0 : getValue().hashCode())</pre>
             *
             * @return the hash code
             */
            public final native int hashCode();
            
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
            public native fabric.lang.Object setValue(
              fabric.lang.Object newVal);
            
            /**
             * This provides a string representation of the entry. It is of the
             form
             * "key=value", where string concatenation is used on key and value.
             *
             * @return the string representation
             */
            public final native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
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
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.key = (fabric.lang.Object)
                             $readRef(fabric.lang.Object._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(),
                                      in,
                                      store,
                                      intraStoreRefs,
                                      interStoreRefs);
                this.value = (fabric.lang.Object)
                               $readRef(fabric.lang.Object._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(),
                                        in,
                                        store,
                                        intraStoreRefs,
                                        interStoreRefs);
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
              implements fabric.util.AbstractMap.BasicMapEntry._Static
            {
                
                public _Proxy(fabric.util.AbstractMap.BasicMapEntry._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractMap.BasicMapEntry.
                  _Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractMap.
                      BasicMapEntry.
                      _Static.
                      _Impl impl =
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
              implements fabric.util.AbstractMap.BasicMapEntry._Static
            {
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, observers, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractMap.BasicMapEntry._Static.
                      _Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractMap
    {
        
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
        
        public native fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        public fabric.util.Set entrySet() {
            return ((fabric.util.AbstractMap) fetch()).entrySet();
        }
        
        public native void clear();
        
        public native boolean containsKey(fabric.lang.Object arg1);
        
        public native boolean containsValue(fabric.lang.Object arg1);
        
        public native fabric.lang.Object get(fabric.lang.Object arg1);
        
        public native boolean isEmpty();
        
        public native fabric.util.Set keySet();
        
        public native fabric.lang.Object put(fabric.lang.Object arg1,
                                             fabric.lang.Object arg2);
        
        public native void putAll(fabric.util.Map arg1);
        
        public native fabric.lang.Object remove(fabric.lang.Object arg1);
        
        public native int size();
        
        public native fabric.util.Collection values();
        
        public static final native boolean equals(fabric.lang.Object arg1,
                                                  fabric.lang.Object arg2);
        
        public static final native int hashCode(fabric.lang.Object arg1);
        
        public _Proxy(AbstractMap._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractMap
    {
        
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
        public native fabric.util.AbstractMap fabric$util$AbstractMap$();
        
        /**
         * Returns a set view of the mappings in this Map.  Each element in the
         * set must be an implementation of Map.Entry.  The set is backed by
         * the map, so that changes in one show up in the other.  Modifications
         * made while an iterator is in progress cause undefined behavior.  If
         * the set supports removal, these methods must be valid:
         * <code>Iterator.remove</code>, <code>Set.remove</code>,
         * <code>removeAll</code>, <code>retainAll</code>, and
         <code>clear</code>.
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
         * override this for efficiency.  Your implementation of entrySet()
         should
         * not call <code>AbstractMap.clear</code> unless you want an infinite
         loop.
         *
         * @throws UnsupportedOperationException if
         <code>entrySet().clear()</code>
         *         does not support clearing.
         * @see Set#clear()
         */
        public native void clear();
        
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
        public native boolean containsKey(fabric.lang.Object key);
        
        /**
         * Returns true if this contains at least one mapping with the given
         value.
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
        public native boolean containsValue(fabric.lang.Object value);
        
        /**
         * Compares the specified object with this map for equality. Returns
         * <code>true</code> if the other object is a Map with the same
         mappings,
         * that is,<br>
         * <code>o instanceof Map && entrySet().equals(((Map)
         o).entrySet();</code>
         *
         * @param o the object to be compared
         * @return true if the object equals this map
         * @see Set#equals(Object)
         */
        public native boolean equals(fabric.lang.Object o);
        
        /**
         * Returns the value mapped by the given key. Returns <code>null</code>
         if
         * there is no mapping.  However, in Maps that accept null values, you
         * must rely on <code>containsKey</code> to determine if a mapping
         exists.
         * This iteration takes linear time, searching
         entrySet().iterator(Store) of
         * the key.  Many implementations override this method.
         *
         * @param key the key to look up
         * @return the value associated with the key, or null if key not in map
         * @throws NullPointerException if this map does not accept null keys
         * @see #containsKey(Object)
         */
        public native fabric.lang.Object get(fabric.lang.Object key);
        
        /**
         * Returns the hash code for this map. As defined in Map, this is the
         sum
         * of all hashcodes for each Map.Entry object in entrySet, or basically
         * entrySet().hashCode().
         *
         * @return the hash code
         * @see Map.Entry#hashCode()
         * @see Set#hashCode()
         */
        public native int hashCode();
        
        /**
         * Returns true if the map contains no mappings. This is implemented by
         * <code>size() == 0</code>.
         *
         * @return true if the map is empty
         * @see #size()
         */
        public native boolean isEmpty();
        
        /**
         * Returns a set view of this map's keys. The set is backed by the map,
         * so changes in one show up in the other. Modifications while an
         iteration
         * is in progress produce undefined behavior. The set supports removal
         * if entrySet() does, but does not support element addition.
         * <p>
         *
         * This implementation creates an AbstractSet, where the iterator wraps
         * the entrySet iterator, size defers to the Map's size, and contains
         * defers to the Map's containsKey. The set is created on first use, and
         * returned on subsequent uses, although since no synchronization
         occurs,
         * there is a slight possibility of creating two sets.
         *
         * @return a Set view of the keys
         * @see Set#iterator(fabric.worker.Store)
         * @see #size()
         * @see #containsKey(Object)
         * @see #values()
         */
        public native fabric.util.Set keySet();
        
        /**
         * Associates the given key to the given value (optional operation). If
         the
         * map already contains the key, its value is replaced. This
         implementation
         * simply throws an UnsupportedOperationException. Be aware that in a
         map
         * that permits <code>null</code> values, a null return does not always
         * imply that the mapping was created.
         *
         * @param key the key to map
         * @param value the value to be mapped
         * @return the previous value of the key, or null if there was no
         mapping
         * @throws UnsupportedOperationException if the operation is not
         supported
         * @throws ClassCastException if the key or value is of the wrong type
         * @throws IllegalArgumentException if something about this key or value
         *         prevents it from existing in this map
         * @throws NullPointerException if the map forbids null keys or values
         * @see #containsKey(Object)
         */
        public native fabric.lang.Object put(fabric.lang.Object key,
                                             fabric.lang.Object value);
        
        /**
         * Copies all entries of the given map to this one (optional operation).
         If
         * the map already contains a key, its value is replaced. This
         implementation
         * simply iterates over the map's entrySet(), calling <code>put</code>,
         * so it is not supported if puts are not.
         *
         * @param m the mapping to load into this map
         * @throws UnsupportedOperationException if the operation is not
         supported
         *         by this map.
         * @throws ClassCastException if a key or value is of the wrong type for
         *         adding to this map.
         * @throws IllegalArgumentException if something about a key or value
         *         prevents it from existing in this map.
         * @throws NullPointerException if the map forbids null keys or values.
         * @throws NullPointerException if <code>m</code> is null.
         * @see #put(Object, Object)
         */
        public native void putAll(fabric.util.Map m);
        
        /**
         * Removes the mapping for this key if present (optional operation).
         This
         * implementation iterates over the entrySet searching for a matching
         * key, at which point it calls the iterator's <code>remove</code>
         method.
         * It returns the result of <code>getValue()</code> on the entry, if
         found,
         * or null if no entry is found. Note that maps which permit null values
         * may also return null if the key was removed.  If the entrySet does
         not
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
        public native fabric.lang.Object remove(fabric.lang.Object key);
        
        /**
         * Returns the number of key-value mappings in the map. If there are
         more
         * than Integer.MAX_VALUE mappings, return Integer.MAX_VALUE. This is
         * implemented as <code>entrySet().size()</code>.
         *
         * @return the number of mappings
         * @see Set#size()
         */
        public native int size();
        
        /**
         * Returns a String representation of this map. This is a listing of the
         * map entries (which are specified in Map.Entry as being
         * <code>getKey() + "=" + getValue()</code>), separated by a comma and
         * space (", "), and surrounded by braces ('{' and '}'). This
         implementation
         * uses a StringBuffer and iterates over the entrySet to build the
         String.
         * Note that this can fail with an exception if underlying keys or
         * values complete abruptly in toString().
         *
         * @return a String representation
         * @see Map.Entry#toString()
         */
        public native java.lang.String toString();
        
        /**
         * Returns a collection or bag view of this map's values. The collection
         * is backed by the map, so changes in one show up in the other.
         * Modifications while an iteration is in progress produce undefined
         * behavior. The collection supports removal if entrySet() does, but
         * does not support element addition.
         * <p>
         *
         * This implementation creates an AbstractCollection, where the iterator
         * wraps the entrySet iterator, size defers to the Map's size, and
         contains
         * defers to the Map's containsValue. The collection is created on first
         * use, and returned on subsequent uses, although since no
         synchronization
         * occurs, there is a slight possibility of creating two collections.
         *
         * @return a Collection view of the values
         * @see Collection#iterator(fabric.worker.Store)
         * @see #size()
         * @see #containsValue(Object)
         * @see #keySet()
         */
        public native fabric.util.Collection values();
        
        /**
         * Compare two objects according to Collection semantics.
         *
         * @param o1 the first object
         * @param o2 the second object
         * @return o1 == o2 || (o1 != null && o1.equals(o2))
         */
        public static final native boolean equals(fabric.lang.Object o1,
                                                  fabric.lang.Object o2);
        
        /**
         * Hash an object according to Collection semantics.
         *
         * @param o the object to hash
         * @return o1 == null ? 0 : o1.hashCode()
         */
        public static final native int hashCode(fabric.lang.Object o);
        
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
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.keys = (fabric.util.Set)
                          $readRef(fabric.util.Set._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.values = (fabric.util.Collection)
                            $readRef(fabric.util.Collection._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(),
                                     in,
                                     store,
                                     intraStoreRefs,
                                     interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.AbstractMap._Impl src =
              (fabric.util.AbstractMap._Impl) other;
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
          implements fabric.util.AbstractMap._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.AbstractMap._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractMap._Static._Impl.class);
                $instance = (fabric.util.AbstractMap._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractMap._Static
        {
            
            public int get$KEYS() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.KEYS;
            }
            
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
            
            /** An "enum" of iterator types. */
            int KEYS;
            
            public int get$VALUES() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.VALUES;
            }
            
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
            
            /** An "enum" of iterator types. */
            int VALUES;
            
            public int get$ENTRIES() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.ENTRIES;
            }
            
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
            
            /** An "enum" of iterator types. */
            int ENTRIES;
            
            public fabric.worker.Store get$LOCAL_STORE() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
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
 fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.KEYS = in.readInt();
                this.VALUES = in.readInt();
                this.ENTRIES = in.readInt();
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractMap._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
