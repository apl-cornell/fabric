package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.Serializable;

/**
 * Utility class consisting of static methods that operate on, or return
 * Collections. Contains methods to sort, search, reverse, fill and shuffle
 * Collections, methods to facilitate interoperability with legacy APIs that
 * are unaware of collections, a method to return a list which consists of
 * multiple copies of one element, and methods which "wrap" collections to give
 * them extra properties, such as thread-safety and unmodifiability.
 * <p>
 *
 * All methods which take a collection throw a {@link NullPointerException} if
 * that collection is null. Algorithms which can change a collection may, but
 * are not required, to throw the {@link UnsupportedOperationException} that
 * the underlying collection would throw during an attempt at modification.
 * For example,
 * <code>Collections.singleton("").addAll(Collections.EMPTY_SET)</code>
 * does not throw a exception, even though addAll is an unsupported operation
 * on a singleton; the reason for this is that addAll did not attempt to
 * modify the set.
 *
 * @author Original author unknown
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see Set
 * @see List
 * @see Map
 * @see Arrays
 * @since 1.2
 * @status updated to 1.4
 */
public interface Collections extends fabric.lang.Object {
    /**
   * The implementation of {@link #EMPTY_SET}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptySet
      extends java.io.Serializable, fabric.util.AbstractSet {
        /**
     * A private constructor adds overhead.
     */
        public EmptySet fabric$util$Collections$EmptySet$();
        
        /**
     * The size: always 0!
     * @return 0.
     */
        public int size();
        
        /**
     * Returns an iterator that does not iterate.
     * @return A non-iterating iterator.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
     * The empty set never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects which are to be compared
     *          against the members of this set.
     * @return <code>true</code> if c is empty.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Equal only if the other set is empty.
     * @param o The object to compare with this set.
     * @return <code>true</code> if o is an empty instance of <code>Set</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * The hashcode is always 0.
     * @return 0.
     */
        public int hashCode();
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param o The object to remove.
     * @return <code>false</code>.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this set.
     * @return <code>false</code>.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this set.
     * @return <code>false</code>.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements EmptySet {
            public native fabric.util.Collections.EmptySet
              fabric$util$Collections$EmptySet$();
            
            public _Proxy(EmptySet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractSet._Impl
          implements EmptySet {
            /**
     * A private constructor adds overhead.
     */
            public native EmptySet fabric$util$Collections$EmptySet$();
            
            /**
     * The size: always 0!
     * @return 0.
     */
            public native int size();
            
            /**
     * Returns an iterator that does not iterate.
     * @return A non-iterating iterator.
     */
            public native fabric.util.Iterator iterator(fabric.worker.Store store);
            
            /**
     * The empty set never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects which are to be compared
     *          against the members of this set.
     * @return <code>true</code> if c is empty.
     */
            public native boolean containsAll(fabric.util.Collection c);
            
            /**
     * Equal only if the other set is empty.
     * @param o The object to compare with this set.
     * @return <code>true</code> if o is an empty instance of <code>Set</code>.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * The hashcode is always 0.
     * @return 0.
     */
            public native int hashCode();
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param o The object to remove.
     * @return <code>false</code>.
     */
            public native boolean remove(fabric.lang.Object o);
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this set.
     * @return <code>false</code>.
     */
            public native boolean removeAll(fabric.util.Collection c);
            
            /**
     * Always succeeds with a <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this set.
     * @return <code>false</code>.
     */
            public native boolean retainAll(fabric.util.Collection c);
            
            /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
            public native fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray a);
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptySet._Proxy(this);
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
              implements fabric.util.Collections.EmptySet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptySet._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptySet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptySet._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptySet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.Collections.EmptySet._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptySet._Static._Impl.class);
                    $instance = (fabric.util.Collections.EmptySet._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptySet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.EmptySet._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #EMPTY_LIST}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptyList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        /**
     * A private constructor adds overhead.
     */
        public EmptyList fabric$util$Collections$EmptyList$();
        
        /**
     * The size is always 0.
     * @return 0.
     */
        public int size();
        
        /**
     * No matter the index, it is out of bounds.  This
     * method never returns, throwing an exception instead.
     *
     * @param index The index of the element to retrieve.
     * @return the object at the specified index.
     * @throws IndexOutOfBoundsException as any given index
     *         is outside the bounds of an empty array.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects, which should be compared
     *          against the members of this list.
     * @return <code>true</code> if c is also empty. 
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Equal only if the other list is empty.
     * @param o The object to compare against this list.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>List</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * The hashcode is always 1.
     * @return 1.
     */
        public int hashCode();
        
        /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param o The object to remove.
     * @return -1.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this list.
     * @return <code>false</code>.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this list.
     * @return <code>false</code>.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements EmptyList {
            public native fabric.util.Collections.EmptyList
              fabric$util$Collections$EmptyList$();
            
            public _Proxy(EmptyList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements EmptyList {
            /**
     * A private constructor adds overhead.
     */
            public native EmptyList fabric$util$Collections$EmptyList$();
            
            /**
     * The size is always 0.
     * @return 0.
     */
            public native int size();
            
            /**
     * No matter the index, it is out of bounds.  This
     * method never returns, throwing an exception instead.
     *
     * @param index The index of the element to retrieve.
     * @return the object at the specified index.
     * @throws IndexOutOfBoundsException as any given index
     *         is outside the bounds of an empty array.
     */
            public native fabric.lang.Object get(int index);
            
            /**
     * Never contains anything.
     * @param o The object to search for.
     * @return <code>false</code>.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * This is true only if the given collection is also empty.
     * @param c The collection of objects, which should be compared
     *          against the members of this list.
     * @return <code>true</code> if c is also empty. 
     */
            public native boolean containsAll(fabric.util.Collection c);
            
            /**
     * Equal only if the other list is empty.
     * @param o The object to compare against this list.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>List</code>.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * The hashcode is always 1.
     * @return 1.
     */
            public native int hashCode();
            
            /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
            public native int indexOf(fabric.lang.Object o);
            
            /**
     * Returns -1.
     * @param o The object to search for.
     * @return -1.
     */
            public native int lastIndexOf(fabric.lang.Object o);
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param o The object to remove.
     * @return -1.
     */
            public native boolean remove(fabric.lang.Object o);
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be removed from this list.
     * @return <code>false</code>.
     */
            public native boolean removeAll(fabric.util.Collection c);
            
            /**
     * Always succeeds with <code>false</code> result.
     * @param c The collection of objects which should
     *          all be retained within this list.
     * @return <code>false</code>.
     */
            public native boolean retainAll(fabric.util.Collection c);
            
            /**
     * The array is always empty.
     * @return A new array with a size of 0.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * We don't even need to use reflection!
     * @param a An existing array, which can be empty.
     * @return The original array with any existing
     *         initial element set to null.
     */
            public native fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray a);
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptyList._Proxy(this);
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
              implements fabric.util.Collections.EmptyList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptyList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptyList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptyList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        EmptyList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptyList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.EmptyList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptyList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.EmptyList._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #EMPTY_MAP}. This class name is required
   * for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface EmptyMap
      extends java.io.Serializable, fabric.util.AbstractMap {
        /**
     * A private constructor adds overhead.
     */
        public EmptyMap fabric$util$Collections$EmptyMap$();
        
        /**
     * There are no entries.
     * @return The empty set.
     */
        public fabric.util.Set entrySet();
        
        /**
     * No entries!
     * @param key The key to search for.
     * @return <code>false</code>.
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * No entries!
     * @param value The value to search for.
     * @return <code>false</code>.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Equal to all empty maps.
     * @param o The object o to compare against this map.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>Map</code>.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * No mappings, so this returns null.
     * @param o The key of the object to retrieve.
     * @return null. 
     */
        public fabric.lang.Object get(fabric.lang.Object o);
        
        /**
     * The hashcode is always 0.
     * @return 0.
     */
        public int hashCode();
        
        /**
     * No entries.
     * @return The empty set.
     */
        public fabric.util.Set keySet();
        
        /**
     * Remove always succeeds, with null result.
     * @param o The key of the mapping to remove.
     * @return null, as there is never a mapping for o.
     */
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        /**
     * Size is always 0.
     * @return 0.
     */
        public int size();
        
        /**
     * No entries. Technically, EMPTY_SET, while more specific than a general
     * Collection, will work. Besides, that's what the JDK uses!
     * @return The empty set.
     */
        public fabric.util.Collection values();
        
        /**
     * The string never changes.
     *
     * @return the string "[]".
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements EmptyMap {
            public native fabric.util.Collections.EmptyMap
              fabric$util$Collections$EmptyMap$();
            
            public _Proxy(EmptyMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements EmptyMap {
            /**
     * A private constructor adds overhead.
     */
            public native EmptyMap fabric$util$Collections$EmptyMap$();
            
            /**
     * There are no entries.
     * @return The empty set.
     */
            public native fabric.util.Set entrySet();
            
            /**
     * No entries!
     * @param key The key to search for.
     * @return <code>false</code>.
     */
            public native boolean containsKey(fabric.lang.Object key);
            
            /**
     * No entries!
     * @param value The value to search for.
     * @return <code>false</code>.
     */
            public native boolean containsValue(fabric.lang.Object value);
            
            /**
     * Equal to all empty maps.
     * @param o The object o to compare against this map.
     * @return <code>true</code> if o is also an empty instance of
     *         <code>Map</code>.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * No mappings, so this returns null.
     * @param o The key of the object to retrieve.
     * @return null. 
     */
            public native fabric.lang.Object get(fabric.lang.Object o);
            
            /**
     * The hashcode is always 0.
     * @return 0.
     */
            public native int hashCode();
            
            /**
     * No entries.
     * @return The empty set.
     */
            public native fabric.util.Set keySet();
            
            /**
     * Remove always succeeds, with null result.
     * @param o The key of the mapping to remove.
     * @return null, as there is never a mapping for o.
     */
            public native fabric.lang.Object remove(fabric.lang.Object o);
            
            /**
     * Size is always 0.
     * @return 0.
     */
            public native int size();
            
            /**
     * No entries. Technically, EMPTY_SET, while more specific than a general
     * Collection, will work. Besides, that's what the JDK uses!
     * @return The empty set.
     */
            public native fabric.util.Collection values();
            
            /**
     * The string never changes.
     *
     * @return the string "[]".
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.EmptyMap._Proxy(this);
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
              implements fabric.util.Collections.EmptyMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.EmptyMap._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.EmptyMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.EmptyMap._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      EmptyMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.Collections.EmptyMap._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.EmptyMap._Static._Impl.class);
                    $instance = (fabric.util.Collections.EmptyMap._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.EmptyMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.EmptyMap._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #nCopies(int, Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface CopiesList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        public int get$n();
        
        public int set$n(int val);
        
        public int postInc$n();
        
        public int postDec$n();
        
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Constructs the list.
     *
     * @param n the count
     * @param o the object
     * @throws IllegalArgumentException if n &lt; 0
     */
        public CopiesList fabric$util$Collections$CopiesList$(int n,
                                                              fabric.lang.Object o);
        
        /**
     * The size is fixed.
     * @return The size of the list.
     */
        public int size();
        
        /**
     * The same element is returned.
     * @param index The index of the element to be returned (irrelevant
     *        as the list contains only copies of <code>element</code>).
     * @return The element used by this list.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * This list only contains one element.
     * @param o The object to search for.
     * @return <code>true</code> if o is the element used by this list.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * The index is either 0 or -1.
     * @param o The object to find the index of.
     * @return 0 if <code>o == element</code>, -1 if not.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * The index is either n-1 or -1.
     * @param o The object to find the last index of.
     * @return The last index in the list if <code>o == element</code>,
     *         -1 if not.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * A subList is just another CopiesList.
     * @param from The starting bound of the sublist.
     * @param to The ending bound of the sublist.
     * @return A list of copies containing <code>from - to</code>
     *         elements, all of which are equal to the element
     *         used by this list.
     */
        public fabric.util.List subList(int from, int to);
        
        /**
     * The array is easy.
     * @return An array of size n filled with copies of
     *         the element used by this list.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * The string is easy to generate.
     * @return A string representation of the list.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements CopiesList {
            public int get$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  get$n();
            }
            
            public int set$n(int val) {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  set$n(val);
            }
            
            public int postInc$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  postInc$n();
            }
            
            public int postDec$n() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  postDec$n();
            }
            
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.CopiesList._Impl) fetch()).
                  set$element(val);
            }
            
            public native fabric.util.Collections.CopiesList
              fabric$util$Collections$CopiesList$(int arg1,
                                                  fabric.lang.Object arg2);
            
            public _Proxy(CopiesList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements CopiesList {
            public int get$n() { return this.n; }
            
            public int set$n(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.n = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$n() {
                int tmp = this.get$n();
                this.set$n((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$n() {
                int tmp = this.get$n();
                this.set$n((int) (tmp - 1));
                return tmp;
            }
            
            private int n;
            
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object element;
            
            /**
     * Constructs the list.
     *
     * @param n the count
     * @param o the object
     * @throws IllegalArgumentException if n &lt; 0
     */
            public native CopiesList fabric$util$Collections$CopiesList$(
              int n, fabric.lang.Object o);
            
            /**
     * The size is fixed.
     * @return The size of the list.
     */
            public native int size();
            
            /**
     * The same element is returned.
     * @param index The index of the element to be returned (irrelevant
     *        as the list contains only copies of <code>element</code>).
     * @return The element used by this list.
     */
            public native fabric.lang.Object get(int index);
            
            /**
     * This list only contains one element.
     * @param o The object to search for.
     * @return <code>true</code> if o is the element used by this list.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * The index is either 0 or -1.
     * @param o The object to find the index of.
     * @return 0 if <code>o == element</code>, -1 if not.
     */
            public native int indexOf(fabric.lang.Object o);
            
            /**
     * The index is either n-1 or -1.
     * @param o The object to find the last index of.
     * @return The last index in the list if <code>o == element</code>,
     *         -1 if not.
     */
            public native int lastIndexOf(fabric.lang.Object o);
            
            /**
     * A subList is just another CopiesList.
     * @param from The starting bound of the sublist.
     * @param to The ending bound of the sublist.
     * @return A list of copies containing <code>from - to</code>
     *         elements, all of which are equal to the element
     *         used by this list.
     */
            public native fabric.util.List subList(int from, int to);
            
            /**
     * The array is easy.
     * @return An array of size n filled with copies of
     *         the element used by this list.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * The string is easy to generate.
     * @return A string representation of the list.
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.CopiesList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.n);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.n = in.readInt();
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.CopiesList._Impl src =
                  (fabric.util.Collections.CopiesList._Impl) other;
                this.n = src.n;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.CopiesList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.CopiesList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.CopiesList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.CopiesList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      CopiesList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        CopiesList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.CopiesList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.CopiesList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.CopiesList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.CopiesList._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #reverseOrder()}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface ReverseComparator
      extends fabric.util.Comparator, java.io.Serializable, fabric.lang.Object {
        /**
     * A private constructor adds overhead.
     */
        public ReverseComparator fabric$util$Collections$ReverseComparator$();
        
        /**
     * Compare two objects in reverse natural order.
     *
     * @param a the first object
     * @param b the second object
     * @return &lt;, ==, or &gt; 0 according to b.compareTo(a)
     */
        public int compare(fabric.lang.Object a, fabric.lang.Object b);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements ReverseComparator {
            public native fabric.util.Collections.ReverseComparator
              fabric$util$Collections$ReverseComparator$();
            
            public native int compare(fabric.lang.Object arg1,
                                      fabric.lang.Object arg2);
            
            public _Proxy(ReverseComparator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements ReverseComparator {
            /**
     * A private constructor adds overhead.
     */
            public native ReverseComparator
              fabric$util$Collections$ReverseComparator$();
            
            /**
     * Compare two objects in reverse natural order.
     *
     * @param a the first object
     * @param b the second object
     * @return &lt;, ==, or &gt; 0 according to b.compareTo(a)
     */
            public native int compare(fabric.lang.Object a,
                                      fabric.lang.Object b);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.ReverseComparator._Proxy(
                         this);
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
              implements fabric.util.Collections.ReverseComparator._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.ReverseComparator._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.ReverseComparator._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.ReverseComparator.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      ReverseComparator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        ReverseComparator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.ReverseComparator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.ReverseComparator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.ReverseComparator._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.ReverseComparator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #singleton(Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonSet
      extends java.io.Serializable, fabric.util.AbstractSet {
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Construct a singleton.
     * @param o the element
     */
        public SingletonSet fabric$util$Collections$SingletonSet$(
          fabric.lang.Object o);
        
        /**
     * The size: always 1!
     * @return 1.
     */
        public int size();
        
        /**
     * Returns an iterator over the lone element.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the element of the singleton.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * The hash is just that of the element.
     * 
     * @return The hashcode of the element.
     */
        public int hashCode();
        
        /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractSet._Proxy
          implements SingletonSet {
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.SingletonSet._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonSet._Impl) fetch()).
                  set$element(val);
            }
            
            public native fabric.util.Collections.SingletonSet
              fabric$util$Collections$SingletonSet$(fabric.lang.Object arg1);
            
            public _Proxy(SingletonSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractSet._Impl
          implements SingletonSet {
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.lang.Object element;
            
            /**
     * Construct a singleton.
     * @param o the element
     */
            public native SingletonSet fabric$util$Collections$SingletonSet$(
              fabric.lang.Object o);
            
            /**
     * The size: always 1!
     * @return 1.
     */
            public native int size();
            
            /**
     * Returns an iterator over the lone element.
     */
            public native fabric.util.Iterator iterator(fabric.worker.Store store);
            
            /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the element of the singleton.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
            public native boolean containsAll(fabric.util.Collection c);
            
            /**
     * The hash is just that of the element.
     * 
     * @return The hashcode of the element.
     */
            public native int hashCode();
            
            /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets.
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonSet._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonSet._Impl src =
                  (fabric.util.Collections.SingletonSet._Impl) other;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonSet._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonSet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonSet._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonSet._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonSet._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.SingletonSet._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #singletonList(Object)}. This class name
   * is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonList
      extends java.io.Serializable, fabric.util.RandomAccess,
              fabric.util.AbstractList
    {
        public fabric.lang.Object get$element();
        
        public fabric.lang.Object set$element(fabric.lang.Object val);
        
        /**
     * Construct a singleton.
     * @param o the element
     */
        public SingletonList fabric$util$Collections$SingletonList$(
          fabric.lang.Object o);
        
        /**
     * The size: always 1!
     * @return 1.
     */
        public int size();
        
        /**
     * Only index 0 is valid.
     * @param index The index of the element
     *        to retrieve.
     * @return The singleton's element if the
     *         index is 0.
     * @throws IndexOutOfBoundsException if
     *         index is not 0.
     */
        public fabric.lang.Object get(int index);
        
        /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the singleton element.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
        public boolean containsAll(fabric.util.Collection c);
        
        /**
     * Speed up the hashcode computation.
     *
     * @return The hashcode of the list, based
     *         on the hashcode of the singleton element.
     */
        public int hashCode();
        
        /**
     * Either the list has it or not.
     *
     * @param o The object to find the first index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Either the list has it or not.
     *
     * @param o The object to find the last index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
     * Sublists are limited in scope.
     * 
     * @param from The starting bound for the sublist.
     * @param to The ending bound for the sublist.
     * @return Either an empty list if both bounds are
     *         0 or 1, or this list if the bounds are 0 and 1.
     * @throws IllegalArgumentException if <code>from > to</code>
     * @throws IndexOutOfBoundsException if either bound is greater
     *         than 1.
     */
        public fabric.util.List subList(int from, int to);
        
        /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets. 
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements SingletonList {
            public fabric.lang.Object get$element() {
                return ((fabric.util.Collections.SingletonList._Impl) fetch()).
                  get$element();
            }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonList._Impl) fetch()).
                  set$element(val);
            }
            
            public native fabric.util.Collections.SingletonList
              fabric$util$Collections$SingletonList$(fabric.lang.Object arg1);
            
            public _Proxy(SingletonList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractList._Impl
          implements SingletonList {
            public fabric.lang.Object get$element() { return this.element; }
            
            public fabric.lang.Object set$element(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.element = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object element;
            
            /**
     * Construct a singleton.
     * @param o the element
     */
            public native SingletonList fabric$util$Collections$SingletonList$(
              fabric.lang.Object o);
            
            /**
     * The size: always 1!
     * @return 1.
     */
            public native int size();
            
            /**
     * Only index 0 is valid.
     * @param index The index of the element
     *        to retrieve.
     * @return The singleton's element if the
     *         index is 0.
     * @throws IndexOutOfBoundsException if
     *         index is not 0.
     */
            public native fabric.lang.Object get(int index);
            
            /**
     * The set only contains one element.
     *
     * @param o The object to search for.
     * @return <code>true</code> if o == the singleton element.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * This is true if the other collection only contains the element.
     *
     * @param c A collection to compare against this singleton.
     * @return <code>true</code> if c only contains either no elements or
     *         elements equal to the element in this singleton.
     */
            public native boolean containsAll(fabric.util.Collection c);
            
            /**
     * Speed up the hashcode computation.
     *
     * @return The hashcode of the list, based
     *         on the hashcode of the singleton element.
     */
            public native int hashCode();
            
            /**
     * Either the list has it or not.
     *
     * @param o The object to find the first index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
            public native int indexOf(fabric.lang.Object o);
            
            /**
     * Either the list has it or not.
     *
     * @param o The object to find the last index of.
     * @return 0 if o is the singleton element, -1 if not.
     */
            public native int lastIndexOf(fabric.lang.Object o);
            
            /**
     * Sublists are limited in scope.
     * 
     * @param from The starting bound for the sublist.
     * @param to The ending bound for the sublist.
     * @return Either an empty list if both bounds are
     *         0 or 1, or this list if the bounds are 0 and 1.
     * @throws IllegalArgumentException if <code>from > to</code>
     * @throws IndexOutOfBoundsException if either bound is greater
     *         than 1.
     */
            public native fabric.util.List subList(int from, int to);
            
            /**
     * Returning an array is simple.
     *
     * @return An array containing the element.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * Obvious string.
     *
     * @return The string surrounded by enclosing
     *         square brackets. 
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.element, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.element = (fabric.lang.Object)
                                 $readRef(fabric.lang.Object._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
                                          intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonList._Impl src =
                  (fabric.util.Collections.SingletonList._Impl) other;
                this.element = src.element;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonList._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonList.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonList._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.SingletonList._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #singletonMap(Object, Object)}. This class
   * name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SingletonMap
      extends java.io.Serializable, fabric.util.AbstractMap {
        public fabric.lang.Object get$k();
        
        public fabric.lang.Object set$k(fabric.lang.Object val);
        
        public fabric.lang.Object get$v();
        
        public fabric.lang.Object set$v(fabric.lang.Object val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        /**
     * Construct a singleton.
     * @param key the key
     * @param value the value
     */
        public SingletonMap
          fabric$util$Collections$SingletonMap$(fabric.lang.Object key, fabric.lang.Object value);
        
        /**
     * There is a single immutable entry.
     *
     * @return A singleton containing the map entry.
     */
        public fabric.util.Set entrySet();
        
        /**
     * Single entry.
     *
     * @param key The key to look for.
     * @return <code>true</code> if the key is the same as the one used by
     *         this map.
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * Single entry.
     *
     * @param value The value to look for.
     * @return <code>true</code> if the value is the same as the one used by
     *         this map.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Single entry.
     *
     * @param key The key of the value to be retrieved.
     * @return The singleton value if the key is the same as the
     *         singleton key, null otherwise.
     */
        public fabric.lang.Object get(fabric.lang.Object key);
        
        /**
     * Calculate the hashcode directly.
     *
     * @return The hashcode computed from the singleton key
     *         and the singleton value.
     */
        public int hashCode();
        
        /**
     * Return the keyset.
     *
     * @return A singleton containing the key.
     */
        public fabric.util.Set keySet();
        
        /**
     * The size: always 1!
     *
     * @return 1.
     */
        public int size();
        
        /**
     * Return the values. Technically, a singleton, while more specific than
     * a general Collection, will work. Besides, that's what the JDK uses!
     *
     * @return A singleton containing the value.
     */
        public fabric.util.Collection values();
        
        /**
     * Obvious string.
     *
     * @return A string containing the string representations of the key
     *         and its associated value.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractMap._Proxy
          implements SingletonMap {
            public fabric.lang.Object get$k() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$k();
            }
            
            public fabric.lang.Object set$k(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$k(val);
            }
            
            public fabric.lang.Object get$v() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$v();
            }
            
            public fabric.lang.Object set$v(fabric.lang.Object val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$v(val);
            }
            
            public fabric.util.Set get$entries() {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  get$entries();
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                return ((fabric.util.Collections.SingletonMap._Impl) fetch()).
                  set$entries(val);
            }
            
            public native fabric.util.Collections.SingletonMap
              fabric$util$Collections$SingletonMap$(fabric.lang.Object arg1,
                                                    fabric.lang.Object arg2);
            
            public _Proxy(SingletonMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.util.AbstractMap._Impl
          implements SingletonMap {
            public fabric.lang.Object get$k() { return this.k; }
            
            public fabric.lang.Object set$k(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.k = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object k;
            
            public fabric.lang.Object get$v() { return this.v; }
            
            public fabric.lang.Object set$v(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.v = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object v;
            
            public fabric.util.Set get$entries() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.entries;
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.entries = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the entry set.
     */
            private transient fabric.util.Set entries;
            
            /**
     * Construct a singleton.
     * @param key the key
     * @param value the value
     */
            public native SingletonMap
              fabric$util$Collections$SingletonMap$(fabric.lang.Object key, fabric.lang.Object value);
            
            /**
     * There is a single immutable entry.
     *
     * @return A singleton containing the map entry.
     */
            public native fabric.util.Set entrySet();
            
            /**
     * Single entry.
     *
     * @param key The key to look for.
     * @return <code>true</code> if the key is the same as the one used by
     *         this map.
     */
            public native boolean containsKey(fabric.lang.Object key);
            
            /**
     * Single entry.
     *
     * @param value The value to look for.
     * @return <code>true</code> if the value is the same as the one used by
     *         this map.
     */
            public native boolean containsValue(fabric.lang.Object value);
            
            /**
     * Single entry.
     *
     * @param key The key of the value to be retrieved.
     * @return The singleton value if the key is the same as the
     *         singleton key, null otherwise.
     */
            public native fabric.lang.Object get(fabric.lang.Object key);
            
            /**
     * Calculate the hashcode directly.
     *
     * @return The hashcode computed from the singleton key
     *         and the singleton value.
     */
            public native int hashCode();
            
            /**
     * Return the keyset.
     *
     * @return A singleton containing the key.
     */
            public native fabric.util.Set keySet();
            
            /**
     * The size: always 1!
     *
     * @return 1.
     */
            public native int size();
            
            /**
     * Return the values. Technically, a singleton, while more specific than
     * a general Collection, will work. Besides, that's what the JDK uses!
     *
     * @return A singleton containing the value.
     */
            public native fabric.util.Collection values();
            
            /**
     * Obvious string.
     *
     * @return A string containing the string representations of the key
     *         and its associated value.
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.SingletonMap._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.k, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.v, refTypes, out, intraStoreRefs,
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
                this.k = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
                this.v = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.SingletonMap._Impl src =
                  (fabric.util.Collections.SingletonMap._Impl) other;
                this.k = src.k;
                this.v = src.v;
                this.entries = src.entries;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.SingletonMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.SingletonMap._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.SingletonMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.SingletonMap._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      SingletonMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        SingletonMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.SingletonMap._Static.
                            _Impl.class);
                    $instance = (fabric.util.Collections.SingletonMap._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.SingletonMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.SingletonMap._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableCollection(Collection)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableCollection
      extends fabric.util.Collection, java.io.Serializable, fabric.lang.Object {
        public fabric.util.Collection get$c();
        
        public fabric.util.Collection set$c(fabric.util.Collection val);
        
        /**
     * Wrap a given collection.
     * @param c the collection to wrap
     * @throws NullPointerException if c is null
     */
        public UnmodifiableCollection
          fabric$util$Collections$UnmodifiableCollection$(fabric.util.Collection c);
        
        /**
     * Blocks the addition of elements to the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o the object to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the add operation.
     */
        public boolean add(fabric.lang.Object o);
        
        /**
     * Blocks the addition of a collection of elements to the underlying
     * collection.  This method never returns, throwing an exception instead.
     *
     * @param c the collection to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the <code>addAll</code> operation.
     */
        public boolean addAll(fabric.util.Collection c);
        
        /**
     * Blocks the clearing of the underlying collection.  This method never
     * returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection does
     *         not support the <code>clear()</code> operation.
     */
        public void clear();
        
        /**
     * Test whether the underlying collection contains a given object as one of its
     * elements.
     *
     * @param o the element to look for.
     * @return <code>true</code> if the underlying collection contains at least
     *         one element e such that
     *         <code>o == null ? e == null : o.equals(e)</code>.
     * @throws ClassCastException if the type of o is not a valid type for the
     *         underlying collection.
     * @throws NullPointerException if o is null and the underlying collection
     *         doesn't support null values.
     */
        public boolean contains(fabric.lang.Object o);
        
        /**
     * Test whether the underlying collection contains every element in a given
     * collection.
     *
     * @param c1 the collection to test for.
     * @return <code>true</code> if for every element o in c, contains(o) would
     *         return <code>true</code>.
     * @throws ClassCastException if the type of any element in c is not a valid
     *   type for the underlying collection.
     * @throws NullPointerException if some element of c is null and the underlying
     *   collection does not support null values.
     * @throws NullPointerException if c itself is null.
     */
        public boolean containsAll(fabric.util.Collection c1);
        
        /**
     * Tests whether the underlying collection is empty, that is,
     * if size() == 0.
     *
     * @return <code>true</code> if this collection contains no elements.
     */
        public boolean isEmpty();
        
        /**
     * Obtain an Iterator over the underlying collection, which maintains
     * its unmodifiable nature.
     *
     * @return an UnmodifiableIterator over the elements of the underlying
     *         collection, in any order.
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        public fabric.util.Iterator iterator();
        
        /**
     * Blocks the removal of an object from the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to remove.
     * @return <code>true</code> if the object was removed (i.e. the underlying
     *         collection returned 1 or more instances of o).
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>remove()</code> operation.
     */
        public boolean remove(fabric.lang.Object o);
        
        /**
     * Blocks the removal of a collection of objects from the underlying
     * collection.  This method never returns, throwing an exception
     * instead.
     *
     * @param c The collection of objects to remove.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>removeAll()</code> operation.
     */
        public boolean removeAll(fabric.util.Collection c);
        
        /**
     * Blocks the removal of all elements from the underlying collection,
     * except those in the supplied collection.  This method never returns,
     * throwing an exception instead.
     *
     * @param c The collection of objects to retain.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>retainAll()</code> operation.
     */
        public boolean retainAll(fabric.util.Collection c);
        
        /**
     * Retrieves the number of elements in the underlying collection.
     *
     * @return the number of elements in the collection.
     */
        public int size();
        
        /**
     * Copy the current contents of the underlying collection into an array.
     *
     * @return an array of type Object[] with a length equal to the size of the
     *         underlying collection and containing the elements currently in
     *         the underlying collection, in any order.
     */
        public fabric.lang.arrays.ObjectArray toArray();
        
        /**
     * Copy the current contents of the underlying collection into an array.  If
     * the array passed as an argument has length less than the size of the
     * underlying collection, an array of the same run-time type as a, with a length
     * equal to the size of the underlying collection, is allocated using reflection.
     * Otherwise, a itself is used.  The elements of the underlying collection are
     * copied into it, and if there is space in the array, the following element is
     * set to null. The resultant array is returned.
     * Note: The fact that the following element is set to null is only useful
     * if it is known that this collection does not contain any null elements.
     *
     * @param a the array to copy this collection into.
     * @return an array containing the elements currently in the underlying
     *         collection, in any order.
     * @throws ArrayStoreException if the type of any element of the
     *         collection is not a subtype of the element type of a.
     */
        public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
        
        /**
     * A textual representation of the unmodifiable collection.
     *
     * @return The unmodifiable collection in the form of a <code>String</code>.
     */
        public java.lang.String toString();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableCollection {
            public fabric.util.Collection get$c() {
                return ((fabric.util.Collections.UnmodifiableCollection._Impl)
                          fetch()).get$c();
            }
            
            public fabric.util.Collection set$c(fabric.util.Collection val) {
                return ((fabric.util.Collections.UnmodifiableCollection._Impl)
                          fetch()).set$c(val);
            }
            
            public native fabric.util.Collections.UnmodifiableCollection
              fabric$util$Collections$UnmodifiableCollection$(
              fabric.util.Collection arg1);
            
            public native boolean add(fabric.lang.Object arg1);
            
            public native boolean addAll(fabric.util.Collection arg1);
            
            public native void clear();
            
            public native boolean contains(fabric.lang.Object arg1);
            
            public native boolean containsAll(fabric.util.Collection arg1);
            
            public native boolean isEmpty();
            
            public native fabric.util.Iterator iterator(
              fabric.worker.Store arg1);
            
            public native fabric.util.Iterator iterator();
            
            public native boolean remove(fabric.lang.Object arg1);
            
            public native boolean removeAll(fabric.util.Collection arg1);
            
            public native boolean retainAll(fabric.util.Collection arg1);
            
            public native int size();
            
            public native fabric.lang.arrays.ObjectArray toArray();
            
            public native fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray arg1);
            
            public _Proxy(UnmodifiableCollection._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableCollection {
            public fabric.util.Collection get$c() { return this.c; }
            
            public fabric.util.Collection set$c(fabric.util.Collection val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.c = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.util.Collection c;
            
            /**
     * Wrap a given collection.
     * @param c the collection to wrap
     * @throws NullPointerException if c is null
     */
            public native UnmodifiableCollection
              fabric$util$Collections$UnmodifiableCollection$(fabric.util.Collection c);
            
            /**
     * Blocks the addition of elements to the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o the object to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the add operation.
     */
            public native boolean add(fabric.lang.Object o);
            
            /**
     * Blocks the addition of a collection of elements to the underlying
     * collection.  This method never returns, throwing an exception instead.
     *
     * @param c the collection to add.
     * @return <code>true</code> if the collection was modified as a result of this action.
     * @throws UnsupportedOperationException as an unmodifiable collection does not
     *         support the <code>addAll</code> operation.
     */
            public native boolean addAll(fabric.util.Collection c);
            
            /**
     * Blocks the clearing of the underlying collection.  This method never
     * returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection does
     *         not support the <code>clear()</code> operation.
     */
            public native void clear();
            
            /**
     * Test whether the underlying collection contains a given object as one of its
     * elements.
     *
     * @param o the element to look for.
     * @return <code>true</code> if the underlying collection contains at least
     *         one element e such that
     *         <code>o == null ? e == null : o.equals(e)</code>.
     * @throws ClassCastException if the type of o is not a valid type for the
     *         underlying collection.
     * @throws NullPointerException if o is null and the underlying collection
     *         doesn't support null values.
     */
            public native boolean contains(fabric.lang.Object o);
            
            /**
     * Test whether the underlying collection contains every element in a given
     * collection.
     *
     * @param c1 the collection to test for.
     * @return <code>true</code> if for every element o in c, contains(o) would
     *         return <code>true</code>.
     * @throws ClassCastException if the type of any element in c is not a valid
     *   type for the underlying collection.
     * @throws NullPointerException if some element of c is null and the underlying
     *   collection does not support null values.
     * @throws NullPointerException if c itself is null.
     */
            public native boolean containsAll(fabric.util.Collection c1);
            
            /**
     * Tests whether the underlying collection is empty, that is,
     * if size() == 0.
     *
     * @return <code>true</code> if this collection contains no elements.
     */
            public native boolean isEmpty();
            
            /**
     * Obtain an Iterator over the underlying collection, which maintains
     * its unmodifiable nature.
     *
     * @return an UnmodifiableIterator over the elements of the underlying
     *         collection, in any order.
     */
            public native fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            public native fabric.util.Iterator iterator();
            
            /**
     * Blocks the removal of an object from the underlying collection.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to remove.
     * @return <code>true</code> if the object was removed (i.e. the underlying
     *         collection returned 1 or more instances of o).
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>remove()</code> operation.
     */
            public native boolean remove(fabric.lang.Object o);
            
            /**
     * Blocks the removal of a collection of objects from the underlying
     * collection.  This method never returns, throwing an exception
     * instead.
     *
     * @param c The collection of objects to remove.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>removeAll()</code> operation.
     */
            public native boolean removeAll(fabric.util.Collection c);
            
            /**
     * Blocks the removal of all elements from the underlying collection,
     * except those in the supplied collection.  This method never returns,
     * throwing an exception instead.
     *
     * @param c The collection of objects to retain.
     * @return <code>true</code> if the collection was modified.
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the <code>retainAll()</code> operation.
     */
            public native boolean retainAll(fabric.util.Collection c);
            
            /**
     * Retrieves the number of elements in the underlying collection.
     *
     * @return the number of elements in the collection.
     */
            public native int size();
            
            /**
     * Copy the current contents of the underlying collection into an array.
     *
     * @return an array of type Object[] with a length equal to the size of the
     *         underlying collection and containing the elements currently in
     *         the underlying collection, in any order.
     */
            public native fabric.lang.arrays.ObjectArray toArray();
            
            /**
     * Copy the current contents of the underlying collection into an array.  If
     * the array passed as an argument has length less than the size of the
     * underlying collection, an array of the same run-time type as a, with a length
     * equal to the size of the underlying collection, is allocated using reflection.
     * Otherwise, a itself is used.  The elements of the underlying collection are
     * copied into it, and if there is space in the array, the following element is
     * set to null. The resultant array is returned.
     * Note: The fact that the following element is set to null is only useful
     * if it is known that this collection does not contain any null elements.
     *
     * @param a the array to copy this collection into.
     * @return an array containing the elements currently in the underlying
     *         collection, in any order.
     * @throws ArrayStoreException if the type of any element of the
     *         collection is not a subtype of the element type of a.
     */
            public native fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
            
            /**
     * A textual representation of the unmodifiable collection.
     *
     * @return The unmodifiable collection in the form of a <code>String</code>.
     */
            public native java.lang.String toString();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableCollection.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.c, refTypes, out, intraStoreRefs,
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
                this.c = (fabric.util.Collection)
                           $readRef(fabric.util.Collection._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableCollection._Impl src =
                  (fabric.util.Collections.UnmodifiableCollection._Impl) other;
                this.c = src.c;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableCollection._Static
            {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableCollection.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableCollection.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableCollection._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableCollection.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableCollection.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableCollection.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableCollection._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableCollection._Static
            {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableCollection.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of the various iterator methods in the
   * unmodifiable classes.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableIterator
      extends fabric.util.Iterator, fabric.lang.Object {
        public fabric.util.Iterator get$i();
        
        public fabric.util.Iterator set$i(fabric.util.Iterator val);
        
        /**
     * Only trusted code creates a wrapper.
     * @param i the wrapped iterator
     */
        public UnmodifiableIterator
          fabric$util$Collections$UnmodifiableIterator$(fabric.util.Iterator i);
        
        /**
     * Obtains the next element in the underlying collection.
     *
     * @return the next element in the collection.
     * @throws NoSuchElementException if there are no more elements.
     */
        public fabric.lang.Object next();
        
        /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>next()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>next()</code>.
     *
     * @return <code>true</code> if there is at least one more element in the underlying
     *         collection.
     */
        public boolean hasNext();
        
        /**
     * Blocks the removal of elements from the underlying collection by the
     * iterator.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the removal of elements by its iterator.
     */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableIterator {
            public fabric.util.Iterator get$i() {
                return ((fabric.util.Collections.UnmodifiableIterator._Impl)
                          fetch()).get$i();
            }
            
            public fabric.util.Iterator set$i(fabric.util.Iterator val) {
                return ((fabric.util.Collections.UnmodifiableIterator._Impl)
                          fetch()).set$i(val);
            }
            
            public native fabric.util.Collections.UnmodifiableIterator
              fabric$util$Collections$UnmodifiableIterator$(
              fabric.util.Iterator arg1);
            
            public native fabric.lang.Object next();
            
            public native boolean hasNext();
            
            public native void remove();
            
            public _Proxy(UnmodifiableIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableIterator {
            public fabric.util.Iterator get$i() { return this.i; }
            
            public fabric.util.Iterator set$i(fabric.util.Iterator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.i = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Iterator i;
            
            /**
     * Only trusted code creates a wrapper.
     * @param i the wrapped iterator
     */
            public native UnmodifiableIterator
              fabric$util$Collections$UnmodifiableIterator$(fabric.util.Iterator i);
            
            /**
     * Obtains the next element in the underlying collection.
     *
     * @return the next element in the collection.
     * @throws NoSuchElementException if there are no more elements.
     */
            public native fabric.lang.Object next();
            
            /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>next()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>next()</code>.
     *
     * @return <code>true</code> if there is at least one more element in the underlying
     *         collection.
     */
            public native boolean hasNext();
            
            /**
     * Blocks the removal of elements from the underlying collection by the
     * iterator.
     *
     * @throws UnsupportedOperationException as an unmodifiable collection
     *         does not support the removal of elements by its iterator.
     */
            public native void remove();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableIterator._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.i, refTypes, out, intraStoreRefs,
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
                this.i = (fabric.util.Iterator)
                           $readRef(fabric.util.Iterator._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableIterator._Impl src =
                  (fabric.util.Collections.UnmodifiableIterator._Impl) other;
                this.i = src.i;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableIterator._Static {
                public _Proxy(fabric.util.Collections.UnmodifiableIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableIterator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableIterator._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
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
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableList(List)} for sequential
   * lists. This class name is required for compatibility with Sun's JDK
   * serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableList
      extends fabric.util.List, UnmodifiableCollection {
        public fabric.util.List get$list();
        
        public fabric.util.List set$list(fabric.util.List val);
        
        /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
        public UnmodifiableList fabric$util$Collections$UnmodifiableList$(fabric.util.List l);
        
        /**
     * Blocks the addition of an element to the underlying
     * list at a specific index.  This method never returns,
     * throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param o the object to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>add()</code> operation.
     */
        public void add(int index, fabric.lang.Object o);
        
        /**
     * Blocks the addition of a collection of elements to the
     * underlying list at a specific index.  This method never
     * returns, throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param c the collections of objects to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>addAll()</code> operation.
     */
        public boolean addAll(int index, fabric.util.Collection c);
        
        /**
     * Returns <code>true</code> if the object, o, is an instance of
     * <code>List</code> with the same size and elements
     * as the underlying list.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is equivalent to the underlying list.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Retrieves the element at a given index in the underlying list.
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Computes the hash code for the underlying list.
     * The exact computation is described in the documentation
     * of the <code>List</code> interface.
     *
     * @return The hash code of the underlying list.
     * @see List#hashCode()
     */
        public int hashCode();
        
        /**
     * Obtain the first index at which a given object is to be found in the
     * underlying list.
     *
     * @param o the object to search for
     * @return the least integer n such that <code>o == null ? get(n) == null :
     *         o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
        public int indexOf(fabric.lang.Object o);
        
        /**
     * Obtain the last index at which a given object is to be found in the
     * underlying list.
     *
     * @return the greatest integer n such that <code>o == null ? get(n) == null
     *         : o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
        public int lastIndexOf(fabric.lang.Object o);
        
        /**
   * Obtains a list iterator over the underlying list, starting at the beginning
   * and maintaining the unmodifiable nature of this list.
   *
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the beginning.
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store);
        
        /**
   * Obtains a list iterator over the underlying list, starting at the specified
   * index and maintaining the unmodifiable nature of this list.  An initial call
   * to <code>next()</code> will retrieve the element at the specified index,
   * and an initial call to <code>previous()</code> will retrieve the element
   * at index - 1.
   *
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from.
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the specified index.
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);
        
        /**
     * Blocks the removal of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to remove.
     * @return the removed element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>remove()</code>
     *         operation.
     */
        public fabric.lang.Object remove(int index);
        
        /**
     * Blocks the replacement of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to replace.
     * @param o The new object to place at the specified index.
     * @return the replaced element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>set()</code>
     *         operation.
     */
        public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        /**
     * Obtain a List view of a subsection of the underlying list, from
     * fromIndex (inclusive) to toIndex (exclusive). If the two indices
     * are equal, the sublist is empty. The returned list will be
     * unmodifiable, like this list.  Changes to the elements of the
     * returned list will be reflected in the underlying list. No structural
     * modifications can take place in either list.
     *
     * @param fromIndex the index that the returned list should start from
     *        (inclusive).
     * @param toIndex the index that the returned list should go to (exclusive).
     * @return a List backed by a subsection of the underlying list.
     * @throws IndexOutOfBoundsException if fromIndex &lt; 0
     *         || toIndex &gt; size() || fromIndex &gt; toIndex.
     */
        public fabric.util.List subList(int fromIndex, int toIndex);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableCollection._Proxy
          implements UnmodifiableList {
            public fabric.util.List get$list() {
                return ((fabric.util.Collections.UnmodifiableList._Impl)
                          fetch()).get$list();
            }
            
            public fabric.util.List set$list(fabric.util.List val) {
                return ((fabric.util.Collections.UnmodifiableList._Impl)
                          fetch()).set$list(val);
            }
            
            public native fabric.util.Collections.UnmodifiableList
              fabric$util$Collections$UnmodifiableList$(fabric.util.List arg1);
            
            public native void add(int arg1, fabric.lang.Object arg2);
            
            public native boolean addAll(int arg1, fabric.util.Collection arg2);
            
            public native fabric.lang.Object get(int arg1);
            
            public native int indexOf(fabric.lang.Object arg1);
            
            public native int lastIndexOf(fabric.lang.Object arg1);
            
            public native fabric.util.ListIterator listIterator(
              fabric.worker.Store arg1);
            
            public native fabric.util.ListIterator listIterator(
              fabric.worker.Store arg1, int arg2);
            
            public native fabric.lang.Object remove(int arg1);
            
            public native fabric.lang.Object set(int arg1,
                                                 fabric.lang.Object arg2);
            
            public native fabric.util.List subList(int arg1, int arg2);
            
            public _Proxy(UnmodifiableList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableCollection._Impl
          implements UnmodifiableList {
            public fabric.util.List get$list() { return this.list; }
            
            public fabric.util.List set$list(fabric.util.List val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.list = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.util.List list;
            
            /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
            public native UnmodifiableList
              fabric$util$Collections$UnmodifiableList$(fabric.util.List l);
            
            /**
     * Blocks the addition of an element to the underlying
     * list at a specific index.  This method never returns,
     * throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param o the object to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>add()</code> operation.
     */
            public native void add(int index, fabric.lang.Object o);
            
            /**
     * Blocks the addition of a collection of elements to the
     * underlying list at a specific index.  This method never
     * returns, throwing an exception instead.
     *
     * @param index The index at which to place the new element.
     * @param c the collections of objects to add.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list doesn't support the <code>addAll()</code> operation.
     */
            public native boolean addAll(int index, fabric.util.Collection c);
            
            /**
     * Returns <code>true</code> if the object, o, is an instance of
     * <code>List</code> with the same size and elements
     * as the underlying list.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is equivalent to the underlying list.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * Retrieves the element at a given index in the underlying list.
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
            public native fabric.lang.Object get(int index);
            
            /**
     * Computes the hash code for the underlying list.
     * The exact computation is described in the documentation
     * of the <code>List</code> interface.
     *
     * @return The hash code of the underlying list.
     * @see List#hashCode()
     */
            public native int hashCode();
            
            /**
     * Obtain the first index at which a given object is to be found in the
     * underlying list.
     *
     * @param o the object to search for
     * @return the least integer n such that <code>o == null ? get(n) == null :
     *         o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
            public native int indexOf(fabric.lang.Object o);
            
            /**
     * Obtain the last index at which a given object is to be found in the
     * underlying list.
     *
     * @return the greatest integer n such that <code>o == null ? get(n) == null
     *         : o.equals(get(n))</code>, or -1 if there is no such index.
     * @throws ClassCastException if the type of o is not a valid
     *         type for the underlying list.
     * @throws NullPointerException if o is null and the underlying
     *         list does not support null values.
     */
            public native int lastIndexOf(fabric.lang.Object o);
            
            /**
   * Obtains a list iterator over the underlying list, starting at the beginning
   * and maintaining the unmodifiable nature of this list.
   *
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the beginning.
   */
            public native fabric.util.ListIterator listIterator(fabric.worker.Store store);
            
            /**
   * Obtains a list iterator over the underlying list, starting at the specified
   * index and maintaining the unmodifiable nature of this list.  An initial call
   * to <code>next()</code> will retrieve the element at the specified index,
   * and an initial call to <code>previous()</code> will retrieve the element
   * at index - 1.
   *
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from.
   * @return a <code>UnmodifiableListIterator</code> over the elements of the
   *         underlying list, in order, starting at the specified index.
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
            public native fabric.util.ListIterator listIterator(
              fabric.worker.Store store, int index);
            
            /**
     * Blocks the removal of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to remove.
     * @return the removed element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>remove()</code>
     *         operation.
     */
            public native fabric.lang.Object remove(int index);
            
            /**
     * Blocks the replacement of the element at the specified index.
     * This method never returns, throwing an exception instead.
     *
     * @param index The index of the element to replace.
     * @param o The new object to place at the specified index.
     * @return the replaced element.
     * @throws UnsupportedOperationException as an unmodifiable
     *         list does not support the <code>set()</code>
     *         operation.
     */
            public native fabric.lang.Object set(int index, fabric.lang.Object o);
            
            /**
     * Obtain a List view of a subsection of the underlying list, from
     * fromIndex (inclusive) to toIndex (exclusive). If the two indices
     * are equal, the sublist is empty. The returned list will be
     * unmodifiable, like this list.  Changes to the elements of the
     * returned list will be reflected in the underlying list. No structural
     * modifications can take place in either list.
     *
     * @param fromIndex the index that the returned list should start from
     *        (inclusive).
     * @param toIndex the index that the returned list should go to (exclusive).
     * @return a List backed by a subsection of the underlying list.
     * @throws IndexOutOfBoundsException if fromIndex &lt; 0
     *         || toIndex &gt; size() || fromIndex &gt; toIndex.
     */
            public native fabric.util.List subList(int fromIndex, int toIndex);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableList._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.list, refTypes, out, intraStoreRefs,
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
                this.list = (fabric.util.List)
                              $readRef(fabric.util.List._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableList._Impl src =
                  (fabric.util.Collections.UnmodifiableList._Impl) other;
                this.list = src.list;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableList._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableList._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableList.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableList._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableList._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableList._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableList._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableList(List)} for random-access
   * lists. This class name is required for compatibility with Sun's JDK
   * serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableRandomAccessList
      extends fabric.util.RandomAccess, UnmodifiableList {
        /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
        public UnmodifiableRandomAccessList
          fabric$util$Collections$UnmodifiableRandomAccessList$(
          fabric.util.List l);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableList._Proxy
          implements UnmodifiableRandomAccessList {
            public native fabric.util.Collections.UnmodifiableRandomAccessList
              fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List arg1);
            
            public _Proxy(UnmodifiableRandomAccessList._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.Collections.UnmodifiableList._Impl
          implements UnmodifiableRandomAccessList {
            /**
     * Wrap a given list.
     * @param l the list to wrap
     * @throws NullPointerException if l is null
     */
            public native UnmodifiableRandomAccessList
              fabric$util$Collections$UnmodifiableRandomAccessList$(
              fabric.util.List l);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableRandomAccessList.
                         _Proxy(this);
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
            
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.util.Collections.UnmodifiableRandomAccessList.
                           _Static
            {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.
                              UnmodifiableRandomAccessList._Static._Impl)
                              fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.
                                UnmodifiableRandomAccessList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableRandomAccessList._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableRandomAccessList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableRandomAccessList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableRandomAccessList.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableRandomAccessList.
                        _Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.util.Collections.UnmodifiableRandomAccessList.
                           _Static
            {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.
                             UnmodifiableRandomAccessList._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link UnmodifiableList#listIterator(Store)}.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableListIterator
      extends fabric.util.ListIterator, UnmodifiableIterator {
        public fabric.util.ListIterator get$li();
        
        public fabric.util.ListIterator set$li(fabric.util.ListIterator val);
        
        /**
     * Only trusted code creates a wrapper.
     * @param li the wrapped iterator
     */
        public UnmodifiableListIterator
          fabric$util$Collections$UnmodifiableListIterator$(fabric.util.ListIterator li);
        
        /**
     * Blocks the addition of an object to the list underlying this iterator.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to add.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>add()</code> operation.
     */
        public void add(fabric.lang.Object o);
        
        /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>previous()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>previous()</code>.
     *
     * @return <code>true</code> if there is at least one more element prior to the
     *         current position in the underlying list.
     */
        public boolean hasPrevious();
        
        /**
     * Find the index of the element that would be returned by a call to next.
     * If <code>hasNext()</code> returns <code>false</code>, this returns the list size.
     *
     * @return the index of the element that would be returned by
     *         <code>next()</code>.
     */
        public int nextIndex();
        
        /**
     * Obtains the previous element in the underlying list.
     *
     * @return the previous element in the list.
     * @throws NoSuchElementException if there are no more prior elements.
     */
        public fabric.lang.Object previous();
        
        /**
     * Find the index of the element that would be returned by a call to
     * previous. If <code>hasPrevious()</code> returns <code>false</code>,
     * this returns -1.
     *
     * @return the index of the element that would be returned by
     *         <code>previous()</code>.
     */
        public int previousIndex();
        
        /**
     * Blocks the replacement of an element in the list underlying this
     * iterator.  This method never returns, throwing an exception instead.
     *
     * @param o The new object to replace the existing one.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>set()</code> operation.
     */
        public void set(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableIterator._Proxy
          implements UnmodifiableListIterator {
            public fabric.util.ListIterator get$li() {
                return ((fabric.util.Collections.UnmodifiableListIterator._Impl)
                          fetch()).get$li();
            }
            
            public fabric.util.ListIterator set$li(
              fabric.util.ListIterator val) {
                return ((fabric.util.Collections.UnmodifiableListIterator._Impl)
                          fetch()).set$li(val);
            }
            
            public native fabric.util.Collections.UnmodifiableListIterator
              fabric$util$Collections$UnmodifiableListIterator$(
              fabric.util.ListIterator arg1);
            
            public native void add(fabric.lang.Object arg1);
            
            public native boolean hasPrevious();
            
            public native int nextIndex();
            
            public native fabric.lang.Object previous();
            
            public native int previousIndex();
            
            public native void set(fabric.lang.Object arg1);
            
            public _Proxy(UnmodifiableListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.Collections.UnmodifiableIterator._Impl
          implements UnmodifiableListIterator {
            public fabric.util.ListIterator get$li() { return this.li; }
            
            public fabric.util.ListIterator set$li(
              fabric.util.ListIterator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.li = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.ListIterator li;
            
            /**
     * Only trusted code creates a wrapper.
     * @param li the wrapped iterator
     */
            public native UnmodifiableListIterator
              fabric$util$Collections$UnmodifiableListIterator$(fabric.util.ListIterator li);
            
            /**
     * Blocks the addition of an object to the list underlying this iterator.
     * This method never returns, throwing an exception instead.
     *
     * @param o The object to add.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>add()</code> operation.
     */
            public native void add(fabric.lang.Object o);
            
            /**
     * Tests whether there are still elements to be retrieved from the
     * underlying collection by <code>previous()</code>.  When this method
     * returns <code>true</code>, an exception will not be thrown on calling
     * <code>previous()</code>.
     *
     * @return <code>true</code> if there is at least one more element prior to the
     *         current position in the underlying list.
     */
            public native boolean hasPrevious();
            
            /**
     * Find the index of the element that would be returned by a call to next.
     * If <code>hasNext()</code> returns <code>false</code>, this returns the list size.
     *
     * @return the index of the element that would be returned by
     *         <code>next()</code>.
     */
            public native int nextIndex();
            
            /**
     * Obtains the previous element in the underlying list.
     *
     * @return the previous element in the list.
     * @throws NoSuchElementException if there are no more prior elements.
     */
            public native fabric.lang.Object previous();
            
            /**
     * Find the index of the element that would be returned by a call to
     * previous. If <code>hasPrevious()</code> returns <code>false</code>,
     * this returns -1.
     *
     * @return the index of the element that would be returned by
     *         <code>previous()</code>.
     */
            public native int previousIndex();
            
            /**
     * Blocks the replacement of an element in the list underlying this
     * iterator.  This method never returns, throwing an exception instead.
     *
     * @param o The new object to replace the existing one.
     * @throws UnsupportedOperationException as the iterator of an unmodifiable
     *         list does not support the <code>set()</code> operation.
     */
            public native void set(fabric.lang.Object o);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableListIterator.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.li, refTypes, out, intraStoreRefs,
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
                this.li = (fabric.util.ListIterator)
                            $readRef(fabric.util.ListIterator._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableListIterator._Impl src =
                  (fabric.util.Collections.UnmodifiableListIterator._Impl)
                    other;
                this.li = src.li;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.util.Collections.UnmodifiableListIterator.
                           _Static
            {
                public _Proxy(fabric.util.Collections.UnmodifiableListIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableListIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableListIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableListIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableListIterator.
                            _Static._Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableListIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.util.Collections.UnmodifiableListIterator.
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
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableListIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableMap(Map)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableMap
      extends fabric.util.Map, java.io.Serializable, fabric.lang.Object {
        public fabric.util.Map get$m();
        
        public fabric.util.Map set$m(fabric.util.Map val);
        
        public fabric.util.Set get$entries();
        
        public fabric.util.Set set$entries(fabric.util.Set val);
        
        public fabric.util.Set get$keys();
        
        public fabric.util.Set set$keys(fabric.util.Set val);
        
        public fabric.util.Collection get$values();
        
        public fabric.util.Collection set$values(fabric.util.Collection val);
        
        /**
     * Wrap a given map.
     * @param m the map to wrap
     * @throws NullPointerException if m is null
     */
        public UnmodifiableMap fabric$util$Collections$UnmodifiableMap$(fabric.util.Map m);
        
        /**
     * Blocks the clearing of entries from the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>clear()</code> operation.
     */
        public void clear();
        
        /**
     * Returns <code>true</code> if the underlying map contains a mapping for
     * the given key.
     *
     * @param key the key to search for
     * @return <code>true</code> if the map contains the key
     * @throws ClassCastException if the key is of an inappropriate type
     * @throws NullPointerException if key is <code>null</code> but the map
     *         does not permit null keys
     */
        public boolean containsKey(fabric.lang.Object key);
        
        /**
     * Returns <code>true</code> if the underlying map contains at least one mapping with
     * the given value.  In other words, it returns <code>true</code> if a value v exists where
     * <code>(value == null ? v == null : value.equals(v))</code>. This usually
     * requires linear time.
     *
     * @param value the value to search for
     * @return <code>true</code> if the map contains the value
     * @throws ClassCastException if the type of the value is not a valid type
     *         for this map.
     * @throws NullPointerException if the value is null and the map doesn't
     *         support null values.
     */
        public boolean containsValue(fabric.lang.Object value);
        
        /**
     * Returns a unmodifiable set view of the entries in the underlying map.
     * Each element in the set is a unmodifiable variant of <code>Map.Entry</code>.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the objects.
     *
     * @return the unmodifiable set view of all mapping entries.
     * @see Map.Entry
     */
        public fabric.util.Set entrySet();
        
        /**
     * The implementation of {@link UnmodifiableMap#entrySet()}. This class
     * name is required for compatibility with Sun's JDK serializability.
     *
     * @author Eric Blake (ebb9@email.byu.edu)
     */
        public static interface UnmodifiableEntrySet
          extends java.io.Serializable, UnmodifiableSet {
            public static interface UnmodifiableMapEntry
              extends Entry, fabric.lang.Object {
                public fabric.util.Map.Entry get$e();
                
                public fabric.util.Map.Entry set$e(fabric.util.Map.Entry val);
                
                /**
         * Returns <code>true</code> if the object, o, is also a map entry
         * with an identical key and value.
         * 
         * @param o the object to compare.
         * @return <code>true</code> if o is an equivalent map entry.
         */
                public boolean equals(fabric.lang.Object o);
                
                /**
         * Returns the key of this map entry.
         * 
         * @return the key.
         */
                public fabric.lang.Object getKey();
                
                /**
         * Returns the value of this map entry.
         * 
         * @return the value.
         */
                public fabric.lang.Object getValue();
                
                /**
         * Computes the hash code of this map entry. The computation is
         * described in the <code>Map</code> interface documentation.
         * 
         * @return the hash code of this entry.
         * @see Map#hashCode()
         */
                public int hashCode();
                
                /**
         * Blocks the alteration of the value of this map entry. This method
         * never returns, throwing an exception instead.
         * 
         * @param value The new value.
         * @throws UnsupportedOperationException as an unmodifiable map entry
         *           does not support the <code>setValue()</code> operation.
         */
                public fabric.lang.Object setValue(fabric.lang.Object value);
                
                /**
         * Returns a textual representation of the map entry.
         * 
         * @return The map entry as a <code>String</code>.
         */
                public java.lang.String toString();
                
                public fabric.lang.Object $initLabels();
                
                public static class _Proxy extends fabric.lang.Object._Proxy
                  implements UnmodifiableMapEntry {
                    public fabric.util.Map.Entry get$e() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry.
                                  _Impl) fetch()).get$e();
                    }
                    
                    public fabric.util.Map.Entry set$e(
                      fabric.util.Map.Entry val) {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet.UnmodifiableMapEntry.
                                  _Impl) fetch()).set$e(val);
                    }
                    
                    public native fabric.lang.Object getKey();
                    
                    public native fabric.lang.Object getValue();
                    
                    public native fabric.lang.Object setValue(
                      fabric.lang.Object arg1);
                    
                    public _Proxy(UnmodifiableMapEntry._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                }
                
                public static final class _Impl extends fabric.lang.Object._Impl
                  implements UnmodifiableMapEntry {
                    public fabric.util.Map.Entry get$e() { return this.e; }
                    
                    public fabric.util.Map.Entry set$e(
                      fabric.util.Map.Entry val) {
                        fabric.worker.transaction.TransactionManager tm =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean transactionCreated = tm.registerWrite(this);
                        this.e = val;
                        if (transactionCreated) tm.commitTransaction();
                        return val;
                    }
                    
                    private Entry e;
                    
                    private native UnmodifiableMapEntry
                      fabric$util$Collections$UnmodifiableMapEntry$(Entry e);
                    
                    /**
         * Returns <code>true</code> if the object, o, is also a map entry
         * with an identical key and value.
         * 
         * @param o the object to compare.
         * @return <code>true</code> if o is an equivalent map entry.
         */
                    public native boolean equals(fabric.lang.Object o);
                    
                    /**
         * Returns the key of this map entry.
         * 
         * @return the key.
         */
                    public native fabric.lang.Object getKey();
                    
                    /**
         * Returns the value of this map entry.
         * 
         * @return the value.
         */
                    public native fabric.lang.Object getValue();
                    
                    /**
         * Computes the hash code of this map entry. The computation is
         * described in the <code>Map</code> interface documentation.
         * 
         * @return the hash code of this entry.
         * @see Map#hashCode()
         */
                    public native int hashCode();
                    
                    /**
         * Blocks the alteration of the value of this map entry. This method
         * never returns, throwing an exception instead.
         * 
         * @param value The new value.
         * @throws UnsupportedOperationException as an unmodifiable map entry
         *           does not support the <code>setValue()</code> operation.
         */
                    public native fabric.lang.Object setValue(fabric.lang.Object value);
                    
                    /**
         * Returns a textual representation of the map entry.
         * 
         * @return The map entry as a <code>String</code>.
         */
                    public native java.lang.String toString();
                    
                    public native fabric.lang.Object $initLabels();
                    
                    public _Impl(fabric.worker.Store $location) {
                        super($location);
                    }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet.UnmodifiableMapEntry.
                                 _Proxy(this);
                    }
                    
                    public void $serialize(java.io.ObjectOutput out,
                                           java.util.List refTypes,
                                           java.util.List intraStoreRefs,
                                           java.util.List interStoreRefs)
                          throws java.io.IOException {
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                        $writeRef($getStore(), this.e, refTypes, out,
                                  intraStoreRefs, interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store, long onum,
                                 int version, fabric.worker.Store labelStore,
                                 long labelOnum,
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
                        this.e = (fabric.util.Map.Entry)
                                   $readRef(fabric.util.Map.Entry._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                    }
                    
                    public void $copyAppStateFrom(
                      fabric.lang.Object._Impl other) {
                        super.$copyAppStateFrom(other);
                        fabric.
                          util.
                          Collections.
                          UnmodifiableMap.
                          UnmodifiableEntrySet.
                          UnmodifiableMapEntry.
                          _Impl
                          src =
                          (fabric.util.Collections.UnmodifiableMap.
                            UnmodifiableEntrySet.UnmodifiableMapEntry._Impl)
                            other;
                        this.e = src.e;
                    }
                }
                
                interface _Static extends fabric.lang.Object, Cloneable {
                    final class _Proxy
                    extends fabric.
                      lang.
                      Object.
                      _Proxy
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.UnmodifiableMapEntry.
                                   _Static
                    {
                        public _Proxy(fabric.util.Collections.UnmodifiableMap.
                                        UnmodifiableEntrySet.
                                        UnmodifiableMapEntry._Static.
                                        _Impl impl) { super(impl); }
                        
                        public _Proxy(fabric.worker.Store store, long onum) {
                            super(store, onum);
                        }
                        
                        public static final fabric.util.Collections.
                          UnmodifiableMap.UnmodifiableEntrySet.
                          UnmodifiableMapEntry._Static $instance;
                        
                        static {
                            fabric.
                              util.
                              Collections.
                              UnmodifiableMap.
                              UnmodifiableEntrySet.
                              UnmodifiableMapEntry.
                              _Static.
                              _Impl
                              impl =
                              (fabric.
                                util.
                                Collections.
                                UnmodifiableMap.
                                UnmodifiableEntrySet.
                                UnmodifiableMapEntry.
                                _Static.
                                _Impl)
                                fabric.lang.Object._Static._Proxy.
                                $makeStaticInstance(
                                  fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet.UnmodifiableMapEntry.
                                    _Static._Impl.class);
                            $instance =
                              (fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet.UnmodifiableMapEntry.
                                _Static) impl.$getProxy();
                            impl.$init();
                        }
                    }
                    
                    class _Impl
                    extends fabric.
                      lang.
                      Object.
                      _Impl
                      implements fabric.util.Collections.UnmodifiableMap.
                                   UnmodifiableEntrySet.UnmodifiableMapEntry.
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
                        
                        public _Impl(fabric.worker.Store store, long onum,
                                     int version,
                                     fabric.worker.Store labelStore,
                                     long labelOnum,
                                     fabric.worker.Store accessPolicyStore,
                                     long accessPolicyOnum,
                                     java.io.ObjectInput in,
                                     java.util.Iterator refTypes,
                                     java.util.Iterator intraStoreRefs,
                                     java.util.Iterator interStoreRefs)
                              throws java.io.IOException,
                            java.lang.ClassNotFoundException {
                            super(store, onum, version, labelStore, labelOnum,
                                  accessPolicyStore, accessPolicyOnum, in,
                                  refTypes, intraStoreRefs, interStoreRefs);
                        }
                        
                        public _Impl(fabric.worker.Store store) {
                            super(store);
                        }
                        
                        protected fabric.lang.Object._Proxy $makeProxy() {
                            return new fabric.util.Collections.UnmodifiableMap.
                                     UnmodifiableEntrySet.UnmodifiableMapEntry.
                                     _Static._Proxy(this);
                        }
                        
                        private void $init() {  }
                    }
                    
                }
                
            }
            
            /**
       * Wrap a given set.
       * @param s the set to wrap
       */
            public UnmodifiableEntrySet
              fabric$util$Collections$UnmodifiableEntrySet$(fabric.util.Set s);
            
            public fabric.util.Iterator iterator(fabric.worker.Store store);
            
            public fabric.lang.arrays.ObjectArray toArray();
            
            public fabric.lang.arrays.ObjectArray toArray(
              fabric.lang.arrays.ObjectArray array);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy
            extends fabric.util.Collections.UnmodifiableSet._Proxy
              implements UnmodifiableEntrySet {
                public native fabric.util.Collections.UnmodifiableMap.UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(
                  fabric.util.Set arg1);
                
                public _Proxy(UnmodifiableEntrySet._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static final class _Impl
            extends fabric.util.
              Collections.UnmodifiableSet._Impl implements UnmodifiableEntrySet {
                /**
       * Wrap a given set.
       * @param s the set to wrap
       */
                public native UnmodifiableEntrySet
                  fabric$util$Collections$UnmodifiableEntrySet$(
                  fabric.util.Set s);
                
                public native fabric.util.Iterator iterator(
                  fabric.worker.Store store);
                
                public native fabric.lang.arrays.ObjectArray toArray();
                
                public native fabric.lang.arrays.ObjectArray toArray(
                  fabric.lang.arrays.ObjectArray array);
                
                public native fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location) {
                    super($location);
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.Collections.UnmodifiableMap.
                             UnmodifiableEntrySet._Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
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
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                public long get$serialVersionUID();
                
                public long set$serialVersionUID(long val);
                
                public long postInc$serialVersionUID();
                
                public long postDec$serialVersionUID();
                
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.Collections.UnmodifiableMap.
                               UnmodifiableEntrySet._Static
                {
                    public long get$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          get$serialVersionUID();
                    }
                    
                    public long set$serialVersionUID(long val) {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          set$serialVersionUID(val);
                    }
                    
                    public long postInc$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          postInc$serialVersionUID();
                    }
                    
                    public long postDec$serialVersionUID() {
                        return ((fabric.util.Collections.UnmodifiableMap.
                                  UnmodifiableEntrySet._Static._Impl) fetch()).
                          postDec$serialVersionUID();
                    }
                    
                    public _Proxy(fabric.util.Collections.UnmodifiableMap.
                                    UnmodifiableEntrySet._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.Collections.UnmodifiableMap.
                      UnmodifiableEntrySet._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          Collections.
                          UnmodifiableMap.
                          UnmodifiableEntrySet.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            Collections.
                            UnmodifiableMap.
                            UnmodifiableEntrySet.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.Collections.UnmodifiableMap.
                                UnmodifiableEntrySet._Static._Impl.class);
                        $instance =
                          (fabric.util.Collections.UnmodifiableMap.
                            UnmodifiableEntrySet._Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.Collections.UnmodifiableMap.
                               UnmodifiableEntrySet._Static
                {
                    public long get$serialVersionUID() {
                        return this.serialVersionUID;
                    }
                    
                    public long set$serialVersionUID(long val) {
                        fabric.worker.transaction.TransactionManager tm =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
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
                        super.$serialize(out, refTypes, intraStoreRefs,
                                         interStoreRefs);
                        out.writeLong(this.serialVersionUID);
                    }
                    
                    public _Impl(fabric.worker.Store store, long onum,
                                 int version, fabric.worker.Store labelStore,
                                 long labelOnum,
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
                        return new fabric.util.Collections.UnmodifiableMap.
                                 UnmodifiableEntrySet._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
        }
        
        /**
     * Returns <code>true</code> if the object, o, is also an instance
     * of <code>Map</code> with an equal set of map entries.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is an equivalent map.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Returns the value associated with the supplied key or
     * null if no such mapping exists.  An ambiguity can occur
     * if null values are accepted by the underlying map.
     * In this case, <code>containsKey()</code> can be used
     * to separate the two possible cases of a null result.
     *
     * @param key The key to look up.
     * @return the value associated with the key, or null if key not in map.
     * @throws ClassCastException if the key is an inappropriate type.
     * @throws NullPointerException if this map does not accept null keys.
     * @see #containsKey(Object)
     */
        public fabric.lang.Object get(fabric.lang.Object key);
        
        /**
     * Blocks the addition of a new entry to the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @param key The new key.
     * @param value The new value.
     * @return the previous value of the key, or null if there was no mapping.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>put()</code> operation.
     */
        public fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
        
        /**
     * Computes the hash code for the underlying map, as the sum
     * of the hash codes of all entries.
     *
     * @return The hash code of the underlying map.
     * @see Map.Entry#hashCode()
     */
        public int hashCode();
        
        /**
     * Returns <code>true</code> if the underlying map contains no entries.
     *
     * @return <code>true</code> if the map is empty.
     */
        public boolean isEmpty();
        
        /**
     * Returns a unmodifiable set view of the keys in the underlying map.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the set view of all keys.
     */
        public fabric.util.Set keySet();
        
        /**
     * Blocks the addition of the entries in the supplied map.
     * This method never returns, throwing an exception instead.
     *
     * @param m The map, the entries of which should be added
     *          to the underlying map.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>putAll</code> operation.
     */
        public void putAll(fabric.util.Map m);
        
        /**
     * Blocks the removal of an entry from the map.
     * This method never returns, throwing an exception instead.
     *
     * @param o The key of the entry to remove.
     * @return The value the key was associated with, or null
     *         if no such mapping existed.  Null is also returned
     *         if the removed entry had a null key.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>remove</code> operation.
     */
        public fabric.lang.Object remove(fabric.lang.Object o);
        
        /**
     * Returns the number of key-value mappings in the underlying map.
     * If there are more than Integer.MAX_VALUE mappings, Integer.MAX_VALUE
     * is returned.
     *
     * @return the number of mappings.
     */
        public int size();
        
        /**
     * Returns a textual representation of the map.
     *
     * @return The map in the form of a <code>String</code>.
     */
        public java.lang.String toString();
        
        /**
     * Returns a unmodifiable collection view of the values in the underlying map.
     * The collection is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the collection view of all values.
     */
        public fabric.util.Collection values();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements UnmodifiableMap {
            public fabric.util.Map get$m() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$m();
            }
            
            public fabric.util.Map set$m(fabric.util.Map val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$m(val);
            }
            
            public fabric.util.Set get$entries() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$entries();
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$entries(val);
            }
            
            public fabric.util.Set get$keys() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$keys();
            }
            
            public fabric.util.Set set$keys(fabric.util.Set val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$keys(val);
            }
            
            public fabric.util.Collection get$values() {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).get$values();
            }
            
            public fabric.util.Collection set$values(
              fabric.util.Collection val) {
                return ((fabric.util.Collections.UnmodifiableMap._Impl)
                          fetch()).set$values(val);
            }
            
            public native fabric.util.Collections.UnmodifiableMap
              fabric$util$Collections$UnmodifiableMap$(fabric.util.Map arg1);
            
            public native void clear();
            
            public native boolean containsKey(fabric.lang.Object arg1);
            
            public native boolean containsValue(fabric.lang.Object arg1);
            
            public native fabric.util.Set entrySet();
            
            public native fabric.lang.Object get(fabric.lang.Object arg1);
            
            public native fabric.lang.Object put(fabric.lang.Object arg1,
                                                 fabric.lang.Object arg2);
            
            public native boolean isEmpty();
            
            public native fabric.util.Set keySet();
            
            public native void putAll(fabric.util.Map arg1);
            
            public native fabric.lang.Object remove(fabric.lang.Object arg1);
            
            public native int size();
            
            public native fabric.util.Collection values();
            
            public _Proxy(UnmodifiableMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements UnmodifiableMap {
            public fabric.util.Map get$m() { return this.m; }
            
            public fabric.util.Map set$m(fabric.util.Map val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.m = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Map m;
            
            public fabric.util.Set get$entries() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.entries;
            }
            
            public fabric.util.Set set$entries(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.entries = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the entry set.
     */
            private transient fabric.util.Set entries;
            
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
     * Cache the key set.
     */
            private transient fabric.util.Set keys;
            
            public fabric.util.Collection get$values() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.values;
            }
            
            public fabric.util.Collection set$values(
              fabric.util.Collection val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.values = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
     * Cache the value collection.
     */
            private transient fabric.util.Collection values;
            
            /**
     * Wrap a given map.
     * @param m the map to wrap
     * @throws NullPointerException if m is null
     */
            public native UnmodifiableMap
              fabric$util$Collections$UnmodifiableMap$(fabric.util.Map m);
            
            /**
     * Blocks the clearing of entries from the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>clear()</code> operation.
     */
            public native void clear();
            
            /**
     * Returns <code>true</code> if the underlying map contains a mapping for
     * the given key.
     *
     * @param key the key to search for
     * @return <code>true</code> if the map contains the key
     * @throws ClassCastException if the key is of an inappropriate type
     * @throws NullPointerException if key is <code>null</code> but the map
     *         does not permit null keys
     */
            public native boolean containsKey(fabric.lang.Object key);
            
            /**
     * Returns <code>true</code> if the underlying map contains at least one mapping with
     * the given value.  In other words, it returns <code>true</code> if a value v exists where
     * <code>(value == null ? v == null : value.equals(v))</code>. This usually
     * requires linear time.
     *
     * @param value the value to search for
     * @return <code>true</code> if the map contains the value
     * @throws ClassCastException if the type of the value is not a valid type
     *         for this map.
     * @throws NullPointerException if the value is null and the map doesn't
     *         support null values.
     */
            public native boolean containsValue(fabric.lang.Object value);
            
            /**
     * Returns a unmodifiable set view of the entries in the underlying map.
     * Each element in the set is a unmodifiable variant of <code>Map.Entry</code>.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the objects.
     *
     * @return the unmodifiable set view of all mapping entries.
     * @see Map.Entry
     */
            public native fabric.util.Set entrySet();
            
            /**
     * Returns <code>true</code> if the object, o, is also an instance
     * of <code>Map</code> with an equal set of map entries.
     *
     * @param o The object to compare.
     * @return <code>true</code> if o is an equivalent map.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * Returns the value associated with the supplied key or
     * null if no such mapping exists.  An ambiguity can occur
     * if null values are accepted by the underlying map.
     * In this case, <code>containsKey()</code> can be used
     * to separate the two possible cases of a null result.
     *
     * @param key The key to look up.
     * @return the value associated with the key, or null if key not in map.
     * @throws ClassCastException if the key is an inappropriate type.
     * @throws NullPointerException if this map does not accept null keys.
     * @see #containsKey(Object)
     */
            public native fabric.lang.Object get(fabric.lang.Object key);
            
            /**
     * Blocks the addition of a new entry to the underlying map.
     * This method never returns, throwing an exception instead.
     *
     * @param key The new key.
     * @param value The new value.
     * @return the previous value of the key, or null if there was no mapping.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>put()</code> operation.
     */
            public native fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);
            
            /**
     * Computes the hash code for the underlying map, as the sum
     * of the hash codes of all entries.
     *
     * @return The hash code of the underlying map.
     * @see Map.Entry#hashCode()
     */
            public native int hashCode();
            
            /**
     * Returns <code>true</code> if the underlying map contains no entries.
     *
     * @return <code>true</code> if the map is empty.
     */
            public native boolean isEmpty();
            
            /**
     * Returns a unmodifiable set view of the keys in the underlying map.
     * The set is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the set view of all keys.
     */
            public native fabric.util.Set keySet();
            
            /**
     * Blocks the addition of the entries in the supplied map.
     * This method never returns, throwing an exception instead.
     *
     * @param m The map, the entries of which should be added
     *          to the underlying map.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>putAll</code> operation.
     */
            public native void putAll(fabric.util.Map m);
            
            /**
     * Blocks the removal of an entry from the map.
     * This method never returns, throwing an exception instead.
     *
     * @param o The key of the entry to remove.
     * @return The value the key was associated with, or null
     *         if no such mapping existed.  Null is also returned
     *         if the removed entry had a null key.
     * @throws UnsupportedOperationException as an unmodifiable
     *         map does not support the <code>remove</code> operation.
     */
            public native fabric.lang.Object remove(fabric.lang.Object o);
            
            /**
     * Returns the number of key-value mappings in the underlying map.
     * If there are more than Integer.MAX_VALUE mappings, Integer.MAX_VALUE
     * is returned.
     *
     * @return the number of mappings.
     */
            public native int size();
            
            /**
     * Returns a textual representation of the map.
     *
     * @return The map in the form of a <code>String</code>.
     */
            public native java.lang.String toString();
            
            /**
     * Returns a unmodifiable collection view of the values in the underlying map.
     * The collection is backed by the map, so that changes in one show up in the other.
     * Modifications made while an iterator is in progress cause undefined
     * behavior.  These modifications are again limited to the values of
     * the keys.
     *
     * @return the collection view of all values.
     */
            public native fabric.util.Collection values();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableMap._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.m, refTypes, out, intraStoreRefs,
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
                this.m = (fabric.util.Map)
                           $readRef(fabric.util.Map._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableMap._Impl src =
                  (fabric.util.Collections.UnmodifiableMap._Impl) other;
                this.m = src.m;
                this.entries = src.entries;
                this.keys = src.keys;
                this.values = src.values;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableMap._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableMap._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableMap.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableMap._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableMap._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableMap._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableSet(Set)}. This class
   * name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSet
      extends fabric.util.Set, UnmodifiableCollection {
        /**
     * Wrap a given set.
     * @param s the set to wrap
     * @throws NullPointerException if s is null
     */
        public UnmodifiableSet fabric$util$Collections$UnmodifiableSet$(fabric.util.Set s);
        
        /**
     * Returns <code>true</code> if the object, o, is also an instance of
     * <code>Set</code> of the same size and with the same entries.
     *
     * @return <code>true</code> if o is an equivalent set.
     */
        public boolean equals(fabric.lang.Object o);
        
        /**
     * Computes the hash code of this set, as the sum of the
     * hash codes of all elements within the set.
     *
     * @return the hash code of the set.
     */
        public int hashCode();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableCollection._Proxy
          implements UnmodifiableSet {
            public native fabric.util.Collections.UnmodifiableSet
              fabric$util$Collections$UnmodifiableSet$(fabric.util.Set arg1);
            
            public _Proxy(UnmodifiableSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableCollection._Impl
          implements UnmodifiableSet {
            /**
     * Wrap a given set.
     * @param s the set to wrap
     * @throws NullPointerException if s is null
     */
            public native UnmodifiableSet
              fabric$util$Collections$UnmodifiableSet$(fabric.util.Set s);
            
            /**
     * Returns <code>true</code> if the object, o, is also an instance of
     * <code>Set</code> of the same size and with the same entries.
     *
     * @return <code>true</code> if o is an equivalent set.
     */
            public native boolean equals(fabric.lang.Object o);
            
            /**
     * Computes the hash code of this set, as the sum of the
     * hash codes of all elements within the set.
     *
     * @return the hash code of the set.
     */
            public native int hashCode();
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSet._Proxy(this);
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
              implements fabric.util.Collections.UnmodifiableSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).postInc$serialVersionUID();
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSet._Static.
                              _Impl) fetch()).postDec$serialVersionUID();
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSet._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.UnmodifiableSet.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSet._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSet._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableSet._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #unmodifiableSortedMap(SortedMap)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSortedMap
      extends fabric.util.SortedMap, UnmodifiableMap {
        public fabric.util.SortedMap get$sm();
        
        public fabric.util.SortedMap set$sm(fabric.util.SortedMap val);
        
        /**
     * Wrap a given map.
     * @param sm the map to wrap
     * @throws NullPointerException if sm is null
     */
        public UnmodifiableSortedMap
          fabric$util$Collections$UnmodifiableSortedMap$(fabric.util.SortedMap sm);
        
        /**
     * Returns the comparator used in sorting the underlying map,
     * or null if it is the keys' natural ordering.
     *
     * @return the sorting comparator.
     */
        public fabric.util.Comparator comparator();
        
        /**
     * Returns the first (lowest sorted) key in the map.
     *
     * @return the first key.
     * @throws NoSuchElementException if this map is empty.
     */
        public fabric.lang.Object firstKey();
        
        /**
     * Returns a unmodifiable view of the portion of the map strictly less
     * than toKey. The view is backed by the underlying map, so changes in
     * one show up in the other.  The submap supports all optional operations
     * of the original.  This operation is equivalent to
     * <code>subMap(firstKey(), toKey)</code>.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of toKey. Note that the endpoint, toKey,
     * is not included; if you want this value to be included, pass its successor
     * object in to toKey.  For example, for Integers, you could request
     * <code>headMap(new Integer(limit.intValue() + 1))</code>.
     *
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if toKey is not comparable to the map contents.
     * @throws IllegalArgumentException if this is a subMap, and toKey is out
     *         of range.
     * @throws NullPointerException if toKey is null but the map does not allow
     *         null keys.
     */
        public fabric.util.SortedMap headMap(fabric.lang.Object toKey);
        
        /**
     * Returns the last (highest sorted) key in the map.
     *
     * @return the last key.
     * @throws NoSuchElementException if this map is empty.
     */
        public fabric.lang.Object lastKey();
        
        /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey, and strictly less than toKey. The view is backed by
     * the underlying map, so changes in one show up in the other. The submap
     * supports all optional operations of the original.
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
     * @param fromKey the inclusive lower range of the submap.
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if fromKey or toKey is not comparable to
     *         the map contents.
     * @throws IllegalArgumentException if this is a subMap, and fromKey or
     *         toKey is out of range.
     * @throws NullPointerException if fromKey or toKey is null but the map
     *         does not allow null keys.
     */
        public fabric.util.SortedMap subMap(fabric.lang.Object fromKey, fabric.lang.Object toKey);
        
        /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey. The view is backed by the underlying map, so changes
     * in one show up in the other. The submap supports all optional operations
     * of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey. Note that the endpoint, fromKey, is
     * included; if you do not want this value to be included, pass its successor object in
     * to fromKey.  For example, for Integers, you could request
     * <code>tailMap(new Integer(limit.intValue() + 1))</code>.
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
        public fabric.util.SortedMap tailMap(fabric.lang.Object fromKey);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableMap._Proxy
          implements UnmodifiableSortedMap {
            public fabric.util.SortedMap get$sm() {
                return ((fabric.util.Collections.UnmodifiableSortedMap._Impl)
                          fetch()).get$sm();
            }
            
            public fabric.util.SortedMap set$sm(fabric.util.SortedMap val) {
                return ((fabric.util.Collections.UnmodifiableSortedMap._Impl)
                          fetch()).set$sm(val);
            }
            
            public native fabric.util.Collections.UnmodifiableSortedMap
              fabric$util$Collections$UnmodifiableSortedMap$(
              fabric.util.SortedMap arg1);
            
            public native fabric.util.Comparator comparator();
            
            public native fabric.lang.Object firstKey();
            
            public native fabric.util.SortedMap headMap(
              fabric.lang.Object arg1);
            
            public native fabric.lang.Object lastKey();
            
            public native fabric.util.SortedMap subMap(fabric.lang.Object arg1,
                                                       fabric.lang.Object arg2);
            
            public native fabric.util.SortedMap tailMap(
              fabric.lang.Object arg1);
            
            public _Proxy(UnmodifiableSortedMap._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableMap._Impl
          implements UnmodifiableSortedMap {
            public fabric.util.SortedMap get$sm() { return this.sm; }
            
            public fabric.util.SortedMap set$sm(fabric.util.SortedMap val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.sm = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.SortedMap sm;
            
            /**
     * Wrap a given map.
     * @param sm the map to wrap
     * @throws NullPointerException if sm is null
     */
            public native UnmodifiableSortedMap
              fabric$util$Collections$UnmodifiableSortedMap$(fabric.util.SortedMap sm);
            
            /**
     * Returns the comparator used in sorting the underlying map,
     * or null if it is the keys' natural ordering.
     *
     * @return the sorting comparator.
     */
            public native fabric.util.Comparator comparator();
            
            /**
     * Returns the first (lowest sorted) key in the map.
     *
     * @return the first key.
     * @throws NoSuchElementException if this map is empty.
     */
            public native fabric.lang.Object firstKey();
            
            /**
     * Returns a unmodifiable view of the portion of the map strictly less
     * than toKey. The view is backed by the underlying map, so changes in
     * one show up in the other.  The submap supports all optional operations
     * of the original.  This operation is equivalent to
     * <code>subMap(firstKey(), toKey)</code>.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of toKey. Note that the endpoint, toKey,
     * is not included; if you want this value to be included, pass its successor
     * object in to toKey.  For example, for Integers, you could request
     * <code>headMap(new Integer(limit.intValue() + 1))</code>.
     *
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if toKey is not comparable to the map contents.
     * @throws IllegalArgumentException if this is a subMap, and toKey is out
     *         of range.
     * @throws NullPointerException if toKey is null but the map does not allow
     *         null keys.
     */
            public native fabric.util.SortedMap headMap(fabric.lang.Object toKey);
            
            /**
     * Returns the last (highest sorted) key in the map.
     *
     * @return the last key.
     * @throws NoSuchElementException if this map is empty.
     */
            public native fabric.lang.Object lastKey();
            
            /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey, and strictly less than toKey. The view is backed by
     * the underlying map, so changes in one show up in the other. The submap
     * supports all optional operations of the original.
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
     * @param fromKey the inclusive lower range of the submap.
     * @param toKey the exclusive upper range of the submap.
     * @return the submap.
     * @throws ClassCastException if fromKey or toKey is not comparable to
     *         the map contents.
     * @throws IllegalArgumentException if this is a subMap, and fromKey or
     *         toKey is out of range.
     * @throws NullPointerException if fromKey or toKey is null but the map
     *         does not allow null keys.
     */
            public native fabric.util.SortedMap subMap(
              fabric.lang.Object fromKey, fabric.lang.Object toKey);
            
            /**
     * Returns a unmodifiable view of the portion of the map greater than or
     * equal to fromKey. The view is backed by the underlying map, so changes
     * in one show up in the other. The submap supports all optional operations
     * of the original.
     * <p>
     *
     * The returned map throws an IllegalArgumentException any time a key is
     * used which is out of the range of fromKey. Note that the endpoint, fromKey, is
     * included; if you do not want this value to be included, pass its successor object in
     * to fromKey.  For example, for Integers, you could request
     * <code>tailMap(new Integer(limit.intValue() + 1))</code>.
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
            public native fabric.util.SortedMap tailMap(
              fabric.lang.Object fromKey);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSortedMap._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.sm, refTypes, out, intraStoreRefs,
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
                this.sm = (fabric.util.SortedMap)
                            $readRef(fabric.util.SortedMap._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableSortedMap._Impl src =
                  (fabric.util.Collections.UnmodifiableSortedMap._Impl) other;
                this.sm = src.sm;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableSortedMap._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedMap.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedMap.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableSortedMap._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSortedMap.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSortedMap.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSortedMap._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSortedMap._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSortedMap._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableSortedMap.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
   * The implementation of {@link #synchronizedSortedMap(SortedMap)}. This
   * class name is required for compatibility with Sun's JDK serializability.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface UnmodifiableSortedSet
      extends fabric.util.SortedSet, UnmodifiableSet {
        public fabric.util.SortedSet get$ss();
        
        public fabric.util.SortedSet set$ss(fabric.util.SortedSet val);
        
        /**
     * Wrap a given set.
     * @param ss the set to wrap
     * @throws NullPointerException if ss is null
     */
        public UnmodifiableSortedSet
          fabric$util$Collections$UnmodifiableSortedSet$(fabric.util.SortedSet ss);
        
        /**
     * Returns the comparator used in sorting the underlying set,
     * or null if it is the elements' natural ordering.
     *
     * @return the sorting comparator
     */
        public fabric.util.Comparator comparator();
        
        /**
     * Returns the first (lowest sorted) element in the underlying
     * set.
     *
     * @return the first element.
     * @throws NoSuchElementException if the set is empty.
     */
        public fabric.lang.Object first();
        
        /**
     * Returns a unmodifiable view of the portion of the set strictly
     * less than toElement. The view is backed by the underlying set,
     * so changes in one show up in the other.  The subset supports
     * all optional operations of the original.  This operation
     * is equivalent to <code>subSet(first(), toElement)</code>.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of toElement. Note that the endpoint, toElement,
     * is not included; if you want this value included, pass its successor object in to
     * toElement.  For example, for Integers, you could request
     * <code>headSet(new Integer(limit.intValue() + 1))</code>.
     *
     * @param toElement the exclusive upper range of the subset
     * @return the subset.
     * @throws ClassCastException if toElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and toElement is out
     *         of range.
     * @throws NullPointerException if toElement is null but the set does not
     *         allow null elements.
     */
        public fabric.util.SortedSet headSet(fabric.lang.Object toElement);
        
        /**
     * Returns the last (highest sorted) element in the underlying
     * set.
     *
     * @return the last element.
     * @throws NoSuchElementException if the set is empty.
     */
        public fabric.lang.Object last();
        
        /**
     * Returns a unmodifiable view of the portion of the set greater than or
     * equal to fromElement, and strictly less than toElement. The view is backed by
     * the underlying set, so changes in one show up in the other. The subset
     * supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement and toElement. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you can request
     * <code>subSet(new Integer(lowlimit.intValue() + 1),
     * new Integer(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromElement the inclusive lower range of the subset.
     * @param toElement the exclusive upper range of the subset.
     * @return the subset.
     * @throws ClassCastException if fromElement or toElement is not comparable
     *         to the set contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement or
     *         toElement is out of range.
     * @throws NullPointerException if fromElement or toElement is null but the
     *         set does not allow null elements.
     */
        public fabric.util.SortedSet subSet(fabric.lang.Object fromElement, fabric.lang.Object toElement);
        
        /**
     * Returns a unmodifiable view of the portion of the set greater than or equal to
     * fromElement. The view is backed by the underlying set, so changes in one show up
     * in the other. The subset supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement. Note that the endpoint,
     * fromElement, is included; if you do not want this value to be included, pass its
     * successor object in to fromElement.  For example, for Integers, you could request
     * <code>tailSet(new Integer(limit.intValue() + 1))</code>.
     *
     * @param fromElement the inclusive lower range of the subset
     * @return the subset.
     * @throws ClassCastException if fromElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement is
     *         out of range.
     * @throws NullPointerException if fromElement is null but the set does not
     *         allow null elements.
     */
        public fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.Collections.UnmodifiableSet._Proxy
          implements UnmodifiableSortedSet {
            public fabric.util.SortedSet get$ss() {
                return ((fabric.util.Collections.UnmodifiableSortedSet._Impl)
                          fetch()).get$ss();
            }
            
            public fabric.util.SortedSet set$ss(fabric.util.SortedSet val) {
                return ((fabric.util.Collections.UnmodifiableSortedSet._Impl)
                          fetch()).set$ss(val);
            }
            
            public native fabric.util.Collections.UnmodifiableSortedSet
              fabric$util$Collections$UnmodifiableSortedSet$(
              fabric.util.SortedSet arg1);
            
            public native fabric.util.Comparator comparator();
            
            public native fabric.lang.Object first();
            
            public native fabric.util.SortedSet headSet(
              fabric.lang.Object arg1);
            
            public native fabric.lang.Object last();
            
            public native fabric.util.SortedSet subSet(fabric.lang.Object arg1,
                                                       fabric.lang.Object arg2);
            
            public native fabric.util.SortedSet tailSet(
              fabric.lang.Object arg1);
            
            public _Proxy(UnmodifiableSortedSet._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.util.Collections.UnmodifiableSet._Impl
          implements UnmodifiableSortedSet {
            public fabric.util.SortedSet get$ss() { return this.ss; }
            
            public fabric.util.SortedSet set$ss(fabric.util.SortedSet val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.ss = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.SortedSet ss;
            
            /**
     * Wrap a given set.
     * @param ss the set to wrap
     * @throws NullPointerException if ss is null
     */
            public native UnmodifiableSortedSet
              fabric$util$Collections$UnmodifiableSortedSet$(fabric.util.SortedSet ss);
            
            /**
     * Returns the comparator used in sorting the underlying set,
     * or null if it is the elements' natural ordering.
     *
     * @return the sorting comparator
     */
            public native fabric.util.Comparator comparator();
            
            /**
     * Returns the first (lowest sorted) element in the underlying
     * set.
     *
     * @return the first element.
     * @throws NoSuchElementException if the set is empty.
     */
            public native fabric.lang.Object first();
            
            /**
     * Returns a unmodifiable view of the portion of the set strictly
     * less than toElement. The view is backed by the underlying set,
     * so changes in one show up in the other.  The subset supports
     * all optional operations of the original.  This operation
     * is equivalent to <code>subSet(first(), toElement)</code>.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of toElement. Note that the endpoint, toElement,
     * is not included; if you want this value included, pass its successor object in to
     * toElement.  For example, for Integers, you could request
     * <code>headSet(new Integer(limit.intValue() + 1))</code>.
     *
     * @param toElement the exclusive upper range of the subset
     * @return the subset.
     * @throws ClassCastException if toElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and toElement is out
     *         of range.
     * @throws NullPointerException if toElement is null but the set does not
     *         allow null elements.
     */
            public native fabric.util.SortedSet headSet(fabric.lang.Object toElement);
            
            /**
     * Returns the last (highest sorted) element in the underlying
     * set.
     *
     * @return the last element.
     * @throws NoSuchElementException if the set is empty.
     */
            public native fabric.lang.Object last();
            
            /**
     * Returns a unmodifiable view of the portion of the set greater than or
     * equal to fromElement, and strictly less than toElement. The view is backed by
     * the underlying set, so changes in one show up in the other. The subset
     * supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement and toElement. Note that the
     * lower endpoint is included, but the upper is not; if you want to
     * change the inclusion or exclusion of an endpoint, pass its successor
     * object in instead.  For example, for Integers, you can request
     * <code>subSet(new Integer(lowlimit.intValue() + 1),
     * new Integer(highlimit.intValue() + 1))</code> to reverse
     * the inclusiveness of both endpoints.
     *
     * @param fromElement the inclusive lower range of the subset.
     * @param toElement the exclusive upper range of the subset.
     * @return the subset.
     * @throws ClassCastException if fromElement or toElement is not comparable
     *         to the set contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement or
     *         toElement is out of range.
     * @throws NullPointerException if fromElement or toElement is null but the
     *         set does not allow null elements.
     */
            public native fabric.util.SortedSet
              subSet(fabric.lang.Object fromElement, fabric.lang.Object toElement);
            
            /**
     * Returns a unmodifiable view of the portion of the set greater than or equal to
     * fromElement. The view is backed by the underlying set, so changes in one show up
     * in the other. The subset supports all optional operations of the original.
     * <p>
     *
     * The returned set throws an IllegalArgumentException any time an element is
     * used which is out of the range of fromElement. Note that the endpoint,
     * fromElement, is included; if you do not want this value to be included, pass its
     * successor object in to fromElement.  For example, for Integers, you could request
     * <code>tailSet(new Integer(limit.intValue() + 1))</code>.
     *
     * @param fromElement the inclusive lower range of the subset
     * @return the subset.
     * @throws ClassCastException if fromElement is not comparable to the set
     *         contents.
     * @throws IllegalArgumentException if this is a subSet, and fromElement is
     *         out of range.
     * @throws NullPointerException if fromElement is null but the set does not
     *         allow null elements.
     */
            public native fabric.util.SortedSet tailSet(
              fabric.lang.Object fromElement);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections.UnmodifiableSortedSet._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.ss, refTypes, out, intraStoreRefs,
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
                this.ss = (fabric.util.SortedSet)
                            $readRef(fabric.util.SortedSet._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.Collections.UnmodifiableSortedSet._Impl src =
                  (fabric.util.Collections.UnmodifiableSortedSet._Impl) other;
                this.ss = src.ss;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            public long get$serialVersionUID();
            
            public long set$serialVersionUID(long val);
            
            public long postInc$serialVersionUID();
            
            public long postDec$serialVersionUID();
            
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.Collections.UnmodifiableSortedSet._Static {
                public long get$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).get$serialVersionUID();
                }
                
                public long set$serialVersionUID(long val) {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).set$serialVersionUID(val);
                }
                
                public long postInc$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).postInc$serialVersionUID(
                                                        );
                }
                
                public long postDec$serialVersionUID() {
                    return ((fabric.util.Collections.UnmodifiableSortedSet.
                              _Static._Impl) fetch()).postDec$serialVersionUID(
                                                        );
                }
                
                public _Proxy(fabric.util.Collections.UnmodifiableSortedSet.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.Collections.
                  UnmodifiableSortedSet._Static $instance;
                
                static {
                    fabric.
                      util.
                      Collections.
                      UnmodifiableSortedSet.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        Collections.
                        UnmodifiableSortedSet.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.Collections.UnmodifiableSortedSet._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.Collections.UnmodifiableSortedSet._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.Collections.UnmodifiableSortedSet._Static {
                public long get$serialVersionUID() {
                    return this.serialVersionUID;
                }
                
                public long set$serialVersionUID(long val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
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
                    return new fabric.util.Collections.UnmodifiableSortedSet.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collections {
        public static final native int compare(fabric.lang.Object arg1,
                                               fabric.lang.Object arg2,
                                               fabric.util.Comparator arg3);
        
        public static native int binarySearch(fabric.util.List arg1,
                                              fabric.lang.Object arg2);
        
        public static native int binarySearch(fabric.util.List arg1,
                                              fabric.lang.Object arg2,
                                              fabric.util.Comparator arg3);
        
        public static native void copy(fabric.util.List arg1,
                                       fabric.util.List arg2);
        
        public static native void fill(fabric.util.List arg1,
                                       fabric.lang.Object arg2);
        
        public static native int indexOfSubList(fabric.util.List arg1,
                                                fabric.util.List arg2);
        
        public static native int lastIndexOfSubList(fabric.util.List arg1,
                                                    fabric.util.List arg2);
        
        public static native fabric.util.ArrayList list(
          fabric.util.Enumeration arg1);
        
        public static native fabric.lang.Object max(
          fabric.util.Collection arg1);
        
        public static native fabric.lang.Object max(
          fabric.util.Collection arg1, fabric.util.Comparator arg2);
        
        public static native fabric.lang.Object min(
          fabric.util.Collection arg1);
        
        public static native fabric.lang.Object min(
          fabric.util.Collection arg1, fabric.util.Comparator arg2);
        
        public static native fabric.util.List nCopies(int arg1,
                                                      fabric.lang.Object arg2);
        
        public static native boolean replaceAll(fabric.util.List arg1,
                                                fabric.lang.Object arg2,
                                                fabric.lang.Object arg3);
        
        public static native void reverse(fabric.util.List arg1);
        
        public static native fabric.util.Comparator reverseOrder();
        
        public static native void rotate(fabric.util.List arg1, int arg2);
        
        public static native void shuffle(fabric.util.List arg1);
        
        public static native void shuffle(fabric.util.List arg1,
                                          fabric.util.Random arg2);
        
        public static native fabric.util.Set singleton(fabric.lang.Object arg1);
        
        public static native fabric.util.List singletonList(
          fabric.lang.Object arg1);
        
        public static native fabric.util.Map singletonMap(
          fabric.lang.Object arg1, fabric.lang.Object arg2);
        
        public static native void sort(fabric.util.List arg1);
        
        public static native void sort(fabric.util.List arg1,
                                       fabric.util.Comparator arg2);
        
        public static native void swap(fabric.util.List arg1, int arg2,
                                       int arg3);
        
        public static native fabric.util.Collection unmodifiableCollection(
          fabric.worker.Store arg1, fabric.util.Collection arg2);
        
        public static native fabric.util.List unmodifiableList(
          fabric.worker.Store arg1, fabric.util.List arg2);
        
        public static native fabric.util.Map unmodifiableMap(
          fabric.worker.Store arg1, fabric.util.Map arg2);
        
        public static native fabric.util.Set unmodifiableSet(
          fabric.worker.Store arg1, fabric.util.Set arg2);
        
        public static native fabric.util.SortedMap unmodifiableSortedMap(
          fabric.worker.Store arg1, fabric.util.SortedMap arg2);
        
        public static native fabric.util.SortedSet unmodifiableSortedSet(
          fabric.worker.Store arg1, fabric.util.SortedSet arg2);
        
        public _Proxy(Collections._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collections {
        /**
   * Determines if a list should be treated as a sequential-access one.
   * Rather than the old method of JDK 1.3 of assuming only instanceof
   * AbstractSequentialList should be sequential, this uses the new method
   * of JDK 1.4 of assuming anything that does NOT implement RandomAccess
   * and exceeds a large (unspecified) size should be sequential.
   *
   * @param l the list to check
   * @return <code>true</code> if it should be treated as sequential-access
   */
        private static native boolean isSequential(fabric.util.List l);
        
        /**
   * Compare two objects with or without a Comparator. If c is null, uses the
   * natural ordering. Slightly slower than doing it inline if the JVM isn't
   * clever, but worth it for removing a duplicate of the search code.
   * Note: This code is also used in Arrays (for sort as well as search).
   */
        public static final native int compare(
          fabric.lang.Object o1, fabric.lang.Object o2, fabric.util.Comparator c);
        
        /**
   * Perform a binary search of a List for a key, using the natural ordering of
   * the elements. The list must be sorted (as by the sort() method) - if it is
   * not, the behavior of this method is undefined, and may be an infinite
   * loop. Further, the key must be comparable with every item in the list. If
   * the list contains the key more than once, any one of them may be found.
   * <p>
   *
   * This algorithm behaves in log(n) time for {@link RandomAccess} lists,
   * and uses a linear search with O(n) link traversals and log(n) comparisons
   * with {@link AbstractSequentialList} lists. Note: although the
   * specification allows for an infinite loop if the list is unsorted, it will
   * not happen in this (Classpath) implementation.
   *
   * @param l the list to search (must be sorted)
   * @param key the value to search for
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of l
   * @throws NullPointerException if a null element has compareTo called
   * @see #sort(List)
   */
        public static native int binarySearch(fabric.util.List l, fabric.lang.Object key);
        
        /**
   * Perform a binary search of a List for a key, using a supplied Comparator.
   * The list must be sorted (as by the sort() method with the same Comparator)
   * - if it is not, the behavior of this method is undefined, and may be an
   * infinite loop. Further, the key must be comparable with every item in the
   * list. If the list contains the key more than once, any one of them may be
   * found. If the comparator is null, the elements' natural ordering is used.
   * <p>
   *
   * This algorithm behaves in log(n) time for {@link RandomAccess} lists,
   * and uses a linear search with O(n) link traversals and log(n) comparisons
   * with {@link AbstractSequentialList} lists. Note: although the
   * specification allows for an infinite loop if the list is unsorted, it will
   * not happen in this (Classpath) implementation.
   *
   * @param l the list to search (must be sorted)
   * @param key the value to search for
   * @param c the comparator by which the list is sorted
   * @return the index at which the key was found, or -n-1 if it was not
   *         found, where n is the index of the first value higher than key or
   *         a.length if there is no such value
   * @throws ClassCastException if key could not be compared with one of the
   *         elements of l
   * @throws NullPointerException if a null element is compared with natural
   *         ordering (only possible when c is null)
   * @see #sort(List, Comparator)
   */
        public static native int
          binarySearch(fabric.util.List l, fabric.lang.Object key, fabric.util.Comparator c);
        
        /**
   * Copy one list to another. If the destination list is longer than the
   * source list, the remaining elements are unaffected. This method runs in
   * linear time.
   *
   * @param dest the destination list
   * @param source the source list
   * @throws IndexOutOfBoundsException if the destination list is shorter
   *         than the source list (the destination will be unmodified)
   * @throws UnsupportedOperationException if dest.listIterator(store) does not
   *         support the set operation
   */
        public static native void copy(fabric.util.List dest, fabric.util.List source);
        
        /**
   * Replace every element of a list with a given value. This method runs in
   * linear time.
   *
   * @param l the list to fill.
   * @param val the object to vill the list with.
   * @throws UnsupportedOperationException if l.listIterator(store) does not
   *         support the set operation.
   */
        public static native void fill(fabric.util.List l, fabric.lang.Object val);
        
        /**
   * Returns the starting index where the specified sublist first occurs
   * in a larger list, or -1 if there is no matching position. If
   * <code>target.size() &gt; source.size()</code>, this returns -1,
   * otherwise this implementation uses brute force, checking for
   * <code>source.sublist(i, i + target.size()).equals(target)</code>
   * for all possible i.
   *
   * @param source the list to search
   * @param target the sublist to search for
   * @return the index where found, or -1
   * @since 1.4
   */
        public static native int indexOfSubList(fabric.util.List source, fabric.util.List target);
        
        /**
   * Returns the starting index where the specified sublist last occurs
   * in a larger list, or -1 if there is no matching position. If
   * <code>target.size() &gt; source.size()</code>, this returns -1,
   * otherwise this implementation uses brute force, checking for
   * <code>source.sublist(i, i + target.size()).equals(target)</code>
   * for all possible i.
   *
   * @param source the list to search
   * @param target the sublist to search for
   * @return the index where found, or -1
   * @since 1.4
   */
        public static native int lastIndexOfSubList(fabric.util.List source, fabric.util.List target);
        
        /**
   * Returns an ArrayList holding the elements visited by a given
   * Enumeration. This method exists for interoperability between legacy
   * APIs and the new Collection API.
   *
   * @param e the enumeration to put in a list
   * @return a list containing the enumeration elements
   * @see ArrayList
   * @since 1.4
   */
        public static native fabric.util.ArrayList list(fabric.util.Enumeration e);
        
        /**
   * Find the maximum element in a Collection, according to the natural
   * ordering of the elements. This implementation iterates over the
   * Collection, so it works in linear time.
   *
   * @param c the Collection to find the maximum element of
   * @return the maximum element of c
   * @exception NoSuchElementException if c is empty
   * @exception ClassCastException if elements in c are not mutually comparable
   * @exception NullPointerException if null.compareTo is called
   */
        public static native fabric.lang.Object max(fabric.util.Collection c);
        
        /**
   * Find the maximum element in a Collection, according to a specified
   * Comparator. This implementation iterates over the Collection, so it
   * works in linear time.
   *
   * @param c the Collection to find the maximum element of
   * @param order the Comparator to order the elements by, or null for natural
   *        ordering
   * @return the maximum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null is compared by natural ordering
   *        (only possible when order is null)
   */
        public static native fabric.lang.Object max(fabric.util.Collection c,
                                                    fabric.util.Comparator order);
        
        /**
   * Find the minimum element in a Collection, according to the natural
   * ordering of the elements. This implementation iterates over the
   * Collection, so it works in linear time.
   *
   * @param c the Collection to find the minimum element of
   * @return the minimum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null.compareTo is called
   */
        public static native fabric.lang.Object min(fabric.util.Collection c);
        
        /**
   * Find the minimum element in a Collection, according to a specified
   * Comparator. This implementation iterates over the Collection, so it
   * works in linear time.
   *
   * @param c the Collection to find the minimum element of
   * @param order the Comparator to order the elements by, or null for natural
   *        ordering
   * @return the minimum element of c
   * @throws NoSuchElementException if c is empty
   * @throws ClassCastException if elements in c are not mutually comparable
   * @throws NullPointerException if null is compared by natural ordering
   *        (only possible when order is null)
   */
        public static native fabric.lang.Object min(fabric.util.Collection c,
                                                    fabric.util.Comparator order);
        
        /**
   * Creates an immutable list consisting of the same object repeated n times.
   * The returned object is tiny, consisting of only a single reference to the
   * object and a count of the number of elements. It is Serializable, and
   * implements RandomAccess. You can use it in tandem with List.addAll for
   * fast list construction.
   *
   * @param n the number of times to repeat the object
   * @param o the object to repeat
   * @return a List consisting of n copies of o
   * @throws IllegalArgumentException if n &lt; 0
   * @see List#addAll(Collection)
   * @see Serializable
   * @see RandomAccess
   */
        public static native fabric.util.List nCopies(final int n,
                                                      final fabric.lang.Object o);
        
        /**
   * Replace all instances of one object with another in the specified list.
   * The list does not change size. An element e is replaced if
   * <code>oldval == null ? e == null : oldval.equals(e)</code>.
   *
   * @param list the list to iterate over
   * @param oldval the element to replace
   * @param newval the new value for the element
   * @return <code>true</code> if a replacement occurred.
   * @throws UnsupportedOperationException if the list iterator does not allow
   *         for the set operation
   * @throws ClassCastException if newval is of a type which cannot be added
   *         to the list
   * @throws IllegalArgumentException if some other aspect of newval stops
   *         it being added to the list
   * @since 1.4
   */
        public static native boolean replaceAll(
          fabric.util.List list, fabric.lang.Object oldval, fabric.lang.Object newval);
        
        /**
   * Reverse a given list. This method works in linear time.
   *
   * @param l the list to reverse
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static native void reverse(fabric.util.List l);
        
        /**
   * Get a comparator that implements the reverse of natural ordering. In
   * other words, this sorts Comparable objects opposite of how their
   * compareTo method would sort. This makes it easy to sort into reverse
   * order, by simply passing Collections.reverseOrder() to the sort method.
   * The return value of this method is Serializable.
   *
   * @return a comparator that imposes reverse natural ordering
   * @see Comparable
   * @see Serializable
   */
        public static native fabric.util.Comparator reverseOrder();
        
        /**
   * Rotate the elements in a list by a specified distance. After calling this
   * method, the element now at index <code>i</code> was formerly at index
   * <code>(i - distance) mod list.size()</code>. The list size is unchanged.
   * <p>
   *
   * For example, suppose a list contains <code>[t, a, n, k, s]</code>. After
   * either <code>Collections.rotate(l, 4)</code> or
   * <code>Collections.rotate(l, -1)</code>, the new contents are
   * <code>[s, t, a, n, k]</code>. This can be applied to sublists to rotate
   * just a portion of the list. For example, to move element <code>a</code>
   * forward two positions in the original example, use
   * <code>Collections.rotate(l.subList(1, 3+1), -1)</code>, which will
   * result in <code>[t, n, k, a, s]</code>.
   * <p>
   *
   * If the list is small or implements {@link RandomAccess}, the
   * implementation exchanges the first element to its destination, then the
   * displaced element, and so on until a circuit has been completed. The
   * process is repeated if needed on the second element, and so forth, until
   * all elements have been swapped.  For large non-random lists, the
   * implementation breaks the list into two sublists at index
   * <code>-distance mod size</code>, calls {@link #reverse(List)} on the
   * pieces, then reverses the overall list.
   *
   * @param list the list to rotate
   * @param distance the distance to rotate by; unrestricted in value
   * @throws UnsupportedOperationException if the list does not support set
   * @since 1.4
   */
        public static native void rotate(fabric.util.List list, int distance);
        
        /**
   * Shuffle a list according to a default source of randomness. The algorithm
   * used iterates backwards over the list, swapping each element with an
   * element randomly selected from the elements in positions less than or
   * equal to it (using r.nextInt(int)).
   * <p>
   *
   * This algorithm would result in a perfectly fair shuffle (that is, each
   * element would have an equal chance of ending up in any position) if r were
   * a perfect source of randomness. In practice the results are merely very
   * close to perfect.
   * <p>
   *
   * This method operates in linear time. To do this on large lists which do
   * not implement {@link RandomAccess}, a temporary array is used to acheive
   * this speed, since it would be quadratic access otherwise.
   *
   * @param l the list to shuffle
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static native void shuffle(fabric.util.List l);
        
        /**
   * Shuffle a list according to a given source of randomness. The algorithm
   * used iterates backwards over the list, swapping each element with an
   * element randomly selected from the elements in positions less than or
   * equal to it (using r.nextInt(int)).
   * <p>
   *
   * This algorithm would result in a perfectly fair shuffle (that is, each
   * element would have an equal chance of ending up in any position) if r were
   * a perfect source of randomness. In practise (eg if r = new Random()) the
   * results are merely very close to perfect.
   * <p>
   *
   * This method operates in linear time. To do this on large lists which do
   * not implement {@link RandomAccess}, a temporary array is used to acheive
   * this speed, since it would be quadratic access otherwise.
   *
   * @param l the list to shuffle
   * @param r the source of randomness to use for the shuffle
   * @throws UnsupportedOperationException if l.listIterator(Store) does not
   *         support the set operation
   */
        public static native void shuffle(fabric.util.List l, fabric.util.Random r);
        
        /**
   * Obtain an immutable Set consisting of a single element. The return value
   * of this method is Serializable.
   *
   * @param o the single element
   * @return an immutable Set containing only o
   * @see Serializable
   */
        public static native fabric.util.Set singleton(fabric.lang.Object o);
        
        /**
   * Obtain an immutable List consisting of a single element. The return value
   * of this method is Serializable, and implements RandomAccess.
   *
   * @param o the single element
   * @return an immutable List containing only o
   * @see Serializable
   * @see RandomAccess
   * @since 1.3
   */
        public static native fabric.util.List singletonList(fabric.lang.Object o);
        
        /**
   * Obtain an immutable Map consisting of a single key-value pair.
   * The return value of this method is Serializable.
   *
   * @param key the single key
   * @param value the single value
   * @return an immutable Map containing only the single key-value pair
   * @see Serializable
   * @since 1.3
   */
        public static native fabric.util.Map singletonMap(
          fabric.lang.Object key, fabric.lang.Object value);
        
        /**
   * Sort a list according to the natural ordering of its elements. The list
   * must be modifiable, but can be of fixed size. The sort algorithm is
   * precisely that used by Arrays.sort(Object[]), which offers guaranteed
   * nlog(n) performance. This implementation dumps the list into an array,
   * sorts the array, and then iterates over the list setting each element from
   * the array.
   *
   * @param l the List to sort (<code>null</code> not permitted)
   * @throws ClassCastException if some items are not mutually comparable
   * @throws UnsupportedOperationException if the List is not modifiable
   * @throws NullPointerException if the list is <code>null</code>, or contains
   *     some element that is <code>null</code>.
   * @see Arrays#sort(Object[])
   */
        public static native void sort(fabric.util.List l);
        
        /**
   * Sort a list according to a specified Comparator. The list must be
   * modifiable, but can be of fixed size. The sort algorithm is precisely that
   * used by Arrays.sort(Object[], Comparator), which offers guaranteed
   * nlog(n) performance. This implementation dumps the list into an array,
   * sorts the array, and then iterates over the list setting each element from
   * the array.
   *
   * @param l the List to sort (<code>null</code> not permitted)
   * @param c the Comparator specifying the ordering for the elements, or
   *        <code>null</code> for natural ordering
   * @throws ClassCastException if c will not compare some pair of items
   * @throws UnsupportedOperationException if the List is not modifiable
   * @throws NullPointerException if the List is <code>null</code> or 
   *         <code>null</code> is compared by natural ordering (only possible 
   *         when c is <code>null</code>)
   *         
   * @see Arrays#sort(Object[], Comparator)
   */
        public static native void sort(fabric.util.List l, fabric.util.Comparator c);
        
        /**
   * Swaps the elements at the specified positions within the list. Equal
   * positions have no effect.
   *
   * @param l the list to work on
   * @param i the first index to swap
   * @param j the second index
   * @throws UnsupportedOperationException if list.set is not supported
   * @throws IndexOutOfBoundsException if either i or j is &lt; 0 or &gt;=
   *         list.size()
   * @since 1.4
   */
        public static native void swap(fabric.util.List l, int i, int j);
        
        /**
   * Returns an unmodifiable view of the given collection. This allows
   * "read-only" access, although changes in the backing collection show up
   * in this view. Attempts to modify the collection directly or via iterators
   * will fail with {@link UnsupportedOperationException}.  Although this view
   * prevents changes to the structure of the collection and its elements, the values
   * referenced by the objects in the collection can still be modified.
   * <p>
   *
   * Since the collection might be a List or a Set, and those have incompatible
   * equals and hashCode requirements, this relies on Object's implementation
   * rather than passing those calls on to the wrapped collection. The returned
   * Collection implements Serializable, but can only be serialized if
   * the collection it wraps is likewise Serializable.
   *
   * @param c the collection to wrap
   * @return a read-only view of the collection
   * @see Serializable
   */
        public static native fabric.util.Collection
          unmodifiableCollection(fabric.worker.Store store, fabric.util.Collection c);
        
        /**
   * Returns an unmodifiable view of the given list. This allows
   * "read-only" access, although changes in the backing list show up
   * in this view. Attempts to modify the list directly, via iterators, or
   * via sublists, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the list and
   * its elements, the values referenced by the objects in the list can
   * still be modified.   
   * <p>
   *
   * The returned List implements Serializable, but can only be serialized if
   * the list it wraps is likewise Serializable. In addition, if the wrapped
   * list implements RandomAccess, this does too.
   *
   * @param l the list to wrap
   * @return a read-only view of the list
   * @see Serializable
   * @see RandomAccess
   */
        public static native fabric.util.List unmodifiableList(
          fabric.worker.Store store, fabric.util.List l);
        
        /**
   * Returns an unmodifiable view of the given map. This allows "read-only"
   * access, although changes in the backing map show up in this view.
   * Attempts to modify the map directly, or via collection views or their
   * iterators will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the map and its
   * entries, the values referenced by the objects in the map can still be
   * modified.   
   * <p>
   *
   * The returned Map implements Serializable, but can only be serialized if
   * the map it wraps is likewise Serializable.
   *
   * @param m the map to wrap
   * @return a read-only view of the map
   * @see Serializable
   */
        public static native fabric.util.Map unmodifiableMap(
          fabric.worker.Store store, fabric.util.Map m);
        
        /**
   * Returns an unmodifiable view of the given set. This allows
   * "read-only" access, although changes in the backing set show up
   * in this view. Attempts to modify the set directly or via iterators
   * will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the set and its
   * entries, the values referenced by the objects in the set can still be
   * modified.   
   * <p>
   *
   * The returned Set implements Serializable, but can only be serialized if
   * the set it wraps is likewise Serializable.
   *
   * @param s the set to wrap
   * @return a read-only view of the set
   * @see Serializable
   */
        public static native fabric.util.Set unmodifiableSet(
          fabric.worker.Store store, fabric.util.Set s);
        
        /**
   * Returns an unmodifiable view of the given sorted map. This allows
   * "read-only" access, although changes in the backing map show up in this
   * view. Attempts to modify the map directly, via subviews, via collection
   * views, or iterators, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the map and its
   * entries, the values referenced by the objects in the map can still be
   * modified.   
   * <p>
   *
   * The returned SortedMap implements Serializable, but can only be
   * serialized if the map it wraps is likewise Serializable.
   *
   * @param m the map to wrap
   * @return a read-only view of the map
   * @see Serializable
   */
        public static native fabric.util.SortedMap
          unmodifiableSortedMap(fabric.worker.Store store, fabric.util.SortedMap m);
        
        /**
   * Returns an unmodifiable view of the given sorted set. This allows
   * "read-only" access, although changes in the backing set show up
   * in this view. Attempts to modify the set directly, via subsets, or via
   * iterators, will fail with {@link UnsupportedOperationException}.
   * Although this view prevents changes to the structure of the set and its
   * entries, the values referenced by the objects in the set can still be
   * modified.   
   * <p>
   *
   * The returns SortedSet implements Serializable, but can only be
   * serialized if the set it wraps is likewise Serializable.
   *
   * @param s the set to wrap
   * @return a read-only view of the set
   * @see Serializable
   */
        public static native fabric.util.SortedSet unmodifiableSortedSet(
          fabric.worker.Store store, fabric.util.SortedSet s);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.Collections._Proxy(this);
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
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        public int get$LARGE_LIST_SIZE();
        
        public int set$LARGE_LIST_SIZE(int val);
        
        public int postInc$LARGE_LIST_SIZE();
        
        public int postDec$LARGE_LIST_SIZE();
        
        public fabric.util.Set get$EMPTY_SET();
        
        public fabric.util.Set set$EMPTY_SET(fabric.util.Set val);
        
        public fabric.util.List get$EMPTY_LIST();
        
        public fabric.util.List set$EMPTY_LIST(fabric.util.List val);
        
        public fabric.util.Map get$EMPTY_MAP();
        
        public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val);
        
        public fabric.util.Collections.ReverseComparator get$rcInstance();
        
        public fabric.util.Collections.ReverseComparator set$rcInstance(
          fabric.util.Collections.ReverseComparator val);
        
        public fabric.util.Random get$defaultRandom();
        
        public fabric.util.Random set$defaultRandom(fabric.util.Random val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Collections._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$LOCAL_STORE(val);
            }
            
            public int get$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$LARGE_LIST_SIZE();
            }
            
            public int set$LARGE_LIST_SIZE(int val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$LARGE_LIST_SIZE(val);
            }
            
            public int postInc$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  postInc$LARGE_LIST_SIZE();
            }
            
            public int postDec$LARGE_LIST_SIZE() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  postDec$LARGE_LIST_SIZE();
            }
            
            public fabric.util.Set get$EMPTY_SET() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_SET();
            }
            
            public fabric.util.Set set$EMPTY_SET(fabric.util.Set val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_SET(val);
            }
            
            public fabric.util.List get$EMPTY_LIST() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_LIST();
            }
            
            public fabric.util.List set$EMPTY_LIST(fabric.util.List val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_LIST(val);
            }
            
            public fabric.util.Map get$EMPTY_MAP() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$EMPTY_MAP();
            }
            
            public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$EMPTY_MAP(val);
            }
            
            public fabric.util.Collections.ReverseComparator get$rcInstance() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$rcInstance();
            }
            
            public fabric.util.Collections.ReverseComparator set$rcInstance(
              fabric.util.Collections.ReverseComparator val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$rcInstance(val);
            }
            
            public fabric.util.Random get$defaultRandom() {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  get$defaultRandom();
            }
            
            public fabric.util.Random set$defaultRandom(
              fabric.util.Random val) {
                return ((fabric.util.Collections._Static._Impl) fetch()).
                  set$defaultRandom(val);
            }
            
            public _Proxy(fabric.util.Collections._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.Collections._Static $instance;
            
            static {
                fabric.
                  util.
                  Collections.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.Collections._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.Collections._Static._Impl.class);
                $instance = (fabric.util.Collections._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collections._Static {
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
            
            public int get$LARGE_LIST_SIZE() { return this.LARGE_LIST_SIZE; }
            
            public int set$LARGE_LIST_SIZE(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.LARGE_LIST_SIZE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$LARGE_LIST_SIZE() {
                int tmp = this.get$LARGE_LIST_SIZE();
                this.set$LARGE_LIST_SIZE((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$LARGE_LIST_SIZE() {
                int tmp = this.get$LARGE_LIST_SIZE();
                this.set$LARGE_LIST_SIZE((int) (tmp - 1));
                return tmp;
            }
            
            private int LARGE_LIST_SIZE;
            
            public fabric.util.Set get$EMPTY_SET() { return this.EMPTY_SET; }
            
            public fabric.util.Set set$EMPTY_SET(fabric.util.Set val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_SET = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.Set EMPTY_SET;
            
            public fabric.util.List get$EMPTY_LIST() { return this.EMPTY_LIST; }
            
            public fabric.util.List set$EMPTY_LIST(fabric.util.List val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_LIST = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.List EMPTY_LIST;
            
            public fabric.util.Map get$EMPTY_MAP() { return this.EMPTY_MAP; }
            
            public fabric.util.Map set$EMPTY_MAP(fabric.util.Map val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EMPTY_MAP = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.util.Map EMPTY_MAP;
            
            public fabric.util.Collections.ReverseComparator get$rcInstance() {
                return this.rcInstance;
            }
            
            public fabric.util.Collections.ReverseComparator set$rcInstance(
              fabric.util.Collections.ReverseComparator val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.rcInstance = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.util.Collections.ReverseComparator rcInstance;
            
            public fabric.util.Random get$defaultRandom() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.defaultRandom;
            }
            
            public fabric.util.Random set$defaultRandom(
              fabric.util.Random val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.defaultRandom = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /**
   * Cache a single Random object for use by shuffle(List). This improves
   * performance as well as ensuring that sequential calls to shuffle() will
   * not result in the same shuffle order occurring: the resolution of
   * System.currentTimeMillis() is not sufficient to guarantee a unique seed.
   */
            private fabric.util.Random defaultRandom;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeInline(out, this.LOCAL_STORE);
                out.writeInt(this.LARGE_LIST_SIZE);
                $writeRef($getStore(), this.EMPTY_SET, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.EMPTY_LIST, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.EMPTY_MAP, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.rcInstance, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.defaultRandom, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
                this.LARGE_LIST_SIZE = in.readInt();
                this.EMPTY_SET = (fabric.util.Set)
                                   $readRef(fabric.util.Set._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                this.EMPTY_LIST = (fabric.util.List)
                                    $readRef(fabric.util.List._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
                this.EMPTY_MAP = (fabric.util.Map)
                                   $readRef(fabric.util.Map._Proxy.class,
                                            (fabric.common.RefTypeEnum)
                                              refTypes.next(), in, store,
                                            intraStoreRefs, interStoreRefs);
                this.rcInstance =
                  (fabric.util.Collections.ReverseComparator)
                    $readRef(
                      fabric.util.Collections.ReverseComparator._Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
                this.defaultRandom = (fabric.util.Random)
                                       $readRef(fabric.util.Random._Proxy.class,
                                                (fabric.common.RefTypeEnum)
                                                  refTypes.next(), in, store,
                                                intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Collections._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
