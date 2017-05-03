package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.Serializable;

/**
 * This class provides a hashtable-backed implementation of the
 * Set interface, with predictable traversal order.
 * <p>
 *
 * It uses a hash-bucket approach; that is, hash collisions are handled
 * by linking the new node off of the pre-existing node (or list of
 * nodes).  In this manner, techniques such as linear probing (which
 * can cause primary clustering) and rehashing (which does not fit very
 * well with Java's method of precomputing hash codes) are avoided.  In
 * addition, this maintains a doubly-linked list which tracks insertion
 * order.  Note that the insertion order is not modified if an
 * <code>add</code> simply reinserts an element in the set.
 * <p>
 *
 * One of the nice features of tracking insertion order is that you can
 * copy a set, and regardless of the implementation of the original,
 * produce the same results when iterating over the copy.  This is possible
 * without needing the overhead of <code>TreeSet</code>.
 * <p>
 *
 * Under ideal circumstances (no collisions), LinkedHashSet offers O(1) 
 * performance on most operations.  In the worst case (all elements map
 * to the same hash code -- very unlikely), most operations are O(n).
 * <p>
 *
 * LinkedHashSet accepts the null entry.  It is not synchronized, so if
 * you need multi-threaded access, consider using:<br>
 * <code>Set s = Collections.synchronizedSet(new LinkedHashSet(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Object#hashCode()
 * @see Collection
 * @see Set
 * @see HashSet
 * @see TreeSet
 * @see Collections#synchronizedSet(Set)
 * @since 1.4
 * @status updated to 1.4
 */
public interface LinkedHashSet extends fabric.util.Set, fabric.util.HashSet {
    /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$();
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(int initialCapacity);
    
    /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
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
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(fabric.util.Collection c);
    
    /**
   * Helper method which initializes the backing Map.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
    public fabric.util.HashMap init(int capacity, float load);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.HashSet._Proxy
      implements fabric.util.LinkedHashSet {
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$();
        
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1);
        
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1, float arg2);
        
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          fabric.util.Collection arg1);
        
        public _Proxy(LinkedHashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashSet._Impl
      implements fabric.util.LinkedHashSet {
        /**
   * Construct a new, empty HashSet whose backing HashMap has the default
   * capacity (11) and loadFacor (0.75).
   */
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$();
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @throws IllegalArgumentException if the capacity is negative
   */
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(int initialCapacity);
        
        /**
   * Construct a new, empty HashSet whose backing HashMap has the supplied
   * capacity and load factor.
   *
   * @param initialCapacity the initial capacity of the backing HashMap
   * @param loadFactor the load factor of the backing HashMap
   * @throws IllegalArgumentException if either argument is negative, or
   *         if loadFactor is POSITIVE_INFINITY or NaN
   */
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
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
        public native fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(fabric.util.Collection c);
        
        /**
   * Helper method which initializes the backing Map.
   *
   * @param capacity the initial capacity
   * @param load the initial load factor
   * @return the backing HashMap
   */
        public native fabric.util.HashMap init(int capacity, float load);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.LinkedHashSet._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedHashSet._Static {
            public long get$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.LinkedHashSet._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.LinkedHashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.LinkedHashSet._Static $instance;
            
            static {
                fabric.
                  util.
                  LinkedHashSet.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.LinkedHashSet._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.LinkedHashSet._Static._Impl.class);
                $instance = (fabric.util.LinkedHashSet._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashSet._Static {
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
                return new fabric.util.LinkedHashSet._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
