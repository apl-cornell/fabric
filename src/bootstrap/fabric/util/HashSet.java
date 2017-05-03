package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class provides a HashMap-backed implementation of the Set interface.
 * <p>
 *
 * Most operations are O(1), assuming no hash collisions.  In the worst
 * case (where all hashes collide), operations are O(n). Setting the
 * initial capacity too low will force many resizing operations, but
 * setting the initial capacity too high (or loadfactor too low) leads
 * to wasted memory and slower iteration.
 * <p>
 *
 * HashSet accepts the null key and null values.  It is not synchronized,
 * so if you need multi-threaded access, consider using:<br>
 * <code>Set s = Collections.synchronizedSet(new HashSet(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Jon Zeppieri
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Set
 * @see TreeSet
 * @see Collections#synchronizedSet(Set)
 * @see HashMap
 * @see LinkedHashSet
 * @since 1.2
 * @status updated to 1.4
 */
public interface HashSet extends fabric.util.Set, fabric.util.AbstractSet {
    public fabric.util.HashMap get$map();
    
    public fabric.util.HashMap set$map(fabric.util.HashMap val);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
    public fabric.util.HashSet fabric$util$HashSet$();
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
    public fabric.util.HashSet fabric$util$HashSet$(int initialCapacity, float loadFactor);
    
    /**
   * Construct a new HashSet with the same elements as are in the supplied
   * collection (eliminating any duplicates, of course). The backing storage
   * has twice the size of the collection, or the default size of 11,
   * whichever is greater; and the default load factor (0.75).
   *
   * @param c a collection of initial set elements
   * @throws NullPointerException if c is null
   */
    public fabric.util.HashSet fabric$util$HashSet$(fabric.util.Collection c);
    
    /**
   * Adds the given Object to the set if it is not already in the Set.
   * This set permits a null element.
   *
   * @param o the Object to add to this Set
   * @return true if the set did not already contain o
   */
    public boolean add(fabric.lang.Object o);
    
    /**
   * Empties this Set of all elements; this takes constant time.
   */
    public void clear();
    
    /**
   * Returns true if the supplied element is in this Set.
   *
   * @param o the Object to look for
   * @return true if it is in the set
   */
    public boolean contains(fabric.lang.Object o);
    
    /**
   * Returns true if this set has no elements in it.
   *
   * @return <code>size() == 0</code>.
   */
    public boolean isEmpty();
    
    /**
   * Returns an Iterator over the elements of this Set, which visits the
   * elements in no particular order.  For this class, the Iterator allows
   * removal of elements. The iterator is fail-fast, and will throw a
   * ConcurrentModificationException if the set is modified externally.
   *
   * @return a set iterator
   * @see ConcurrentModificationException
   */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Removes the supplied Object from this Set if it is in the Set.
   *
   * @param o the object to remove
   * @return true if an element was removed
   */
    public boolean remove(fabric.lang.Object o);
    
    /**
   * Returns the number of elements in this Set (its cardinality).
   *
   * @return the size of the set
   */
    public int size();
    
    /**
   * Helper method which initializes the backing Map. Overridden by
   * LinkedHashSet for correct semantics.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
    public fabric.util.HashMap init(int capacity, float load);
    
    /**
   * Deserializes this object from the given stream.
   *
   * @param s the stream to read from
   * @throws ClassNotFoundException if the underlying stream fails
   * @throws IOException if the underlying stream fails
   * @serialData the <i>capacity</i> (int) and <i>loadFactor</i> (float)
   *             of the backing store, followed by the set size (int),
   *             then a listing of its elements (Object) in no order
   */
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSet._Proxy
      implements fabric.util.HashSet {
        public fabric.util.HashMap get$map() {
            return ((fabric.util.HashSet._Impl) fetch()).get$map();
        }
        
        public fabric.util.HashMap set$map(fabric.util.HashMap val) {
            return ((fabric.util.HashSet._Impl) fetch()).set$map(val);
        }
        
        public native fabric.util.HashSet fabric$util$HashSet$();
        
        public native fabric.util.HashSet fabric$util$HashSet$(int arg1);
        
        public native fabric.util.HashSet fabric$util$HashSet$(int arg1,
                                                               float arg2);
        
        public native fabric.util.HashSet fabric$util$HashSet$(
          fabric.util.Collection arg1);
        
        public native fabric.util.HashMap init(int arg1, float arg2);
        
        public _Proxy(HashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSet._Impl
      implements fabric.util.HashSet {
        public fabric.util.HashMap get$map() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.map;
        }
        
        public fabric.util.HashMap set$map(fabric.util.HashMap val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.map = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The HashMap which backs this Set.
   */
        private fabric.util.HashMap map;
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
        public native fabric.util.HashSet fabric$util$HashSet$();
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
        public native fabric.util.HashSet fabric$util$HashSet$(int initialCapacity);
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
        public native fabric.util.HashSet fabric$util$HashSet$(
          int initialCapacity, float loadFactor);
        
        /**
   * Construct a new HashSet with the same elements as are in the supplied
   * collection (eliminating any duplicates, of course). The backing storage
   * has twice the size of the collection, or the default size of 11,
   * whichever is greater; and the default load factor (0.75).
   *
   * @param c a collection of initial set elements
   * @throws NullPointerException if c is null
   */
        public native fabric.util.HashSet fabric$util$HashSet$(fabric.util.Collection c);
        
        /**
   * Adds the given Object to the set if it is not already in the Set.
   * This set permits a null element.
   *
   * @param o the Object to add to this Set
   * @return true if the set did not already contain o
   */
        public native boolean add(fabric.lang.Object o);
        
        /**
   * Empties this Set of all elements; this takes constant time.
   */
        public native void clear();
        
        /**
   * Returns true if the supplied element is in this Set.
   *
   * @param o the Object to look for
   * @return true if it is in the set
   */
        public native boolean contains(fabric.lang.Object o);
        
        /**
   * Returns true if this set has no elements in it.
   *
   * @return <code>size() == 0</code>.
   */
        public native boolean isEmpty();
        
        /**
   * Returns an Iterator over the elements of this Set, which visits the
   * elements in no particular order.  For this class, the Iterator allows
   * removal of elements. The iterator is fail-fast, and will throw a
   * ConcurrentModificationException if the set is modified externally.
   *
   * @return a set iterator
   * @see ConcurrentModificationException
   */
        public native fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
   * Removes the supplied Object from this Set if it is in the Set.
   *
   * @param o the object to remove
   * @return true if an element was removed
   */
        public native boolean remove(fabric.lang.Object o);
        
        /**
   * Returns the number of elements in this Set (its cardinality).
   *
   * @return the size of the set
   */
        public native int size();
        
        /**
   * Helper method which initializes the backing Map. Overridden by
   * LinkedHashSet for correct semantics.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
        public native fabric.util.HashMap init(int capacity, float load);
        
        /**
   * Deserializes this object from the given stream.
   *
   * @param s the stream to read from
   * @throws ClassNotFoundException if the underlying stream fails
   * @throws IOException if the underlying stream fails
   * @serialData the <i>capacity</i> (int) and <i>loadFactor</i> (float)
   *             of the backing store, followed by the set size (int),
   *             then a listing of its elements (Object) in no order
   */
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.HashSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.map, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.map = (fabric.util.HashMap)
                         $readRef(fabric.util.HashMap._Proxy.class,
                                  (fabric.common.RefTypeEnum) refTypes.next(),
                                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.HashSet._Impl src = (fabric.util.HashSet._Impl) other;
            this.map = src.map;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.HashSet._Static {
            public long get$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.HashSet._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.HashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.HashSet._Static $instance;
            
            static {
                fabric.
                  util.
                  HashSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.HashSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.HashSet._Static._Impl.class);
                $instance = (fabric.util.HashSet._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.HashSet._Static {
            public long get$serialVersionUID() { return this.serialVersionUID; }
            
            public long set$serialVersionUID(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.serialVersionUID = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp - 1));
                return tmp;
            }
            
            private long serialVersionUID;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.serialVersionUID);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.HashSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
