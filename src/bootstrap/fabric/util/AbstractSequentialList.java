package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * Abstract superclass to make it easier to implement the List interface when
 * backed by a sequential-access store, such as a linked list. For random
 * access data, use AbstractList. This class implements the random access
 * methods (<code>get</code>, <code>set</code>, <code>add</code>, and
 * <code>remove</code>) atop the list iterator, opposite of AbstractList's
 * approach of implementing the iterator atop random access.
 * <p>
 *
 * To implement a list, you need an implementation for <code>size()</code>
 * and <code>listIterator</code>.  With just <code>hasNext</code>,
 * <code>next</code>, <code>hasPrevious</code>, <code>previous</code>,
 * <code>nextIndex</code>, and <code>previousIndex</code>, you have an
 * unmodifiable list. For a modifiable one, add <code>set</code>, and for
 * a variable-size list, add <code>add</code> and <code>remove</code>.
 * <p>
 *
 * The programmer should provide a no-argument constructor, and one that
 * accepts another Collection, as recommended by the Collection interface.
 * Unfortunately, there is no way to enforce this in Java.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see AbstractList
 * @see AbstractCollection
 * @see ListIterator
 * @see LinkedList
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractSequentialList
  extends fabric.util.AbstractList
{
    
    /**
     * The main constructor, for use by subclasses.
     */
    public fabric.util.AbstractSequentialList
      fabric$util$AbstractSequentialList$();
    
    /**
     * Returns a ListIterator over the list, starting from position index.
     * Subclasses must provide an implementation of this method.
     *
     * @param index the starting position of the list
     * @return the list iterator
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public abstract fabric.util.ListIterator listIterator(
      fabric.worker.Store store, int index);
    
    /**
     * Insert an element into the list at a given position (optional operation).
     * This shifts all existing elements from that position to the end one
     * index to the right. This version of add has no return, since it is
     * assumed to always succeed if there is no exception. This iteration
     * uses listIterator(index).add(o).
     *
     * @param index the location to insert the item
     * @param o the object to insert
     * @throws UnsupportedOperationException if this list does not support the
     *         add operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws ClassCastException if o cannot be added to this list due to its
     *         type
     * @throws IllegalArgumentException if o cannot be added to this list for
     *         some other reason.
     * @throws NullPointerException if o is null and the list does not permit
     *         the addition of null values.
     */
    public void add(int index, fabric.lang.Object o);
    
    /**
     * Insert the contents of a collection into the list at a given position
     * (optional operation). Shift all elements at that position to the right
     * by the number of elements inserted. This operation is undefined if
     * this list is modified during the operation (for example, if you try
     * to insert a list into itself).
     * <p>
     *
     * This implementation grabs listIterator(index), then proceeds to use add
     * for each element returned by c's iterator. Sun's online specs are wrong,
     * claiming that this also calls next(): listIterator.add() correctly
     * skips the added element.
     *
     * @param index the location to insert the collection
     * @param c the collection to insert
     * @return true if the list was modified by this action, that is, if c is
     *         non-empty
     * @throws UnsupportedOperationException if this list does not support the
     *         addAll operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws ClassCastException if some element of c cannot be added to this
     *         list due to its type
     * @throws IllegalArgumentException if some element of c cannot be added
     *         to this list for some other reason
     * @throws NullPointerException if the specified collection is null
     * @throws NullPointerException if an object, o, in c is null and the list
     *         does not permit the addition of null values.
     * @see #add(int, Object)
     */
    public boolean addAll(int index, fabric.util.Collection c);
    
    /**
     * Get the element at a given index in this list. This implementation
     * returns listIterator(index).next().
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object get(int index);
    
    /**
     * Obtain an Iterator over this list, whose sequence is the list order. This
     * implementation returns listIterator(store).
     *
     * @return an Iterator over the elements of this list, in order
     */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
     * Remove the element at a given position in this list (optional operation).
     * Shifts all remaining elements to the left to fill the gap. This
     * implementation uses listIterator(index) and ListIterator.remove().
     *
     * @param index the position within the list of the object to remove
     * @return the object that was removed
     * @throws UnsupportedOperationException if this list does not support the
     *         remove operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object remove(int index);
    
    /**
     * Replace an element of this list with another object (optional operation).
     * This implementation uses listIterator(index) and ListIterator.set(o).
     *
     * @param index the position within this list of the element to be replaced
     * @param o the object to replace it with
     * @return the object that was replaced
     * @throws UnsupportedOperationException if this list does not support the
     *         set operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     * @throws ClassCastException if o cannot be added to this list due to its
     *         type
     * @throws IllegalArgumentException if o cannot be added to this list for
     *         some other reason
     * @throws NullPointerException if o is null and the list does not allow
     *         a value to be set to null.
     */
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.AbstractSequentialList
    {
        
        public native fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$();
        
        public _Proxy(AbstractSequentialList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.AbstractSequentialList
    {
        
        /**
         * The main constructor, for use by subclasses.
         */
        public native fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$();
        
        /**
         * Returns a ListIterator over the list, starting from position index.
         * Subclasses must provide an implementation of this method.
         *
         * @param index the starting position of the list
         * @return the list iterator
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public abstract fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);
        
        /**
         * Insert an element into the list at a given position (optional
         operation).
         * This shifts all existing elements from that position to the end one
         * index to the right. This version of add has no return, since it is
         * assumed to always succeed if there is no exception. This iteration
         * uses listIterator(index).add(o).
         *
         * @param index the location to insert the item
         * @param o the object to insert
         * @throws UnsupportedOperationException if this list does not support
         the
         *         add operation
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         * @throws ClassCastException if o cannot be added to this list due to
         its
         *         type
         * @throws IllegalArgumentException if o cannot be added to this list
         for
         *         some other reason.
         * @throws NullPointerException if o is null and the list does not
         permit
         *         the addition of null values.
         */
        public native void add(int index, fabric.lang.Object o);
        
        /**
         * Insert the contents of a collection into the list at a given position
         * (optional operation). Shift all elements at that position to the
         right
         * by the number of elements inserted. This operation is undefined if
         * this list is modified during the operation (for example, if you try
         * to insert a list into itself).
         * <p>
         *
         * This implementation grabs listIterator(index), then proceeds to use
         add
         * for each element returned by c's iterator. Sun's online specs are
         wrong,
         * claiming that this also calls next(): listIterator.add() correctly
         * skips the added element.
         *
         * @param index the location to insert the collection
         * @param c the collection to insert
         * @return true if the list was modified by this action, that is, if c
         is
         *         non-empty
         * @throws UnsupportedOperationException if this list does not support
         the
         *         addAll operation
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         * @throws ClassCastException if some element of c cannot be added to
         this
         *         list due to its type
         * @throws IllegalArgumentException if some element of c cannot be added
         *         to this list for some other reason
         * @throws NullPointerException if the specified collection is null
         * @throws NullPointerException if an object, o, in c is null and the
         list
         *         does not permit the addition of null values.
         * @see #add(int, Object)
         */
        public native boolean addAll(int index, fabric.util.Collection c);
        
        /**
         * Get the element at a given index in this list. This implementation
         * returns listIterator(index).next().
         *
         * @param index the index of the element to be returned
         * @return the element at index index in this list
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object get(int index);
        
        /**
         * Obtain an Iterator over this list, whose sequence is the list order.
         This
         * implementation returns listIterator(store).
         *
         * @return an Iterator over the elements of this list, in order
         */
        public native fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
         * Remove the element at a given position in this list (optional
         operation).
         * Shifts all remaining elements to the left to fill the gap. This
         * implementation uses listIterator(index) and ListIterator.remove().
         *
         * @param index the position within the list of the object to remove
         * @return the object that was removed
         * @throws UnsupportedOperationException if this list does not support
         the
         *         remove operation
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object remove(int index);
        
        /**
         * Replace an element of this list with another object (optional
         operation).
         * This implementation uses listIterator(index) and ListIterator.set(o).
         *
         * @param index the position within this list of the element to be
         replaced
         * @param o the object to replace it with
         * @return the object that was replaced
         * @throws UnsupportedOperationException if this list does not support
         the
         *         set operation
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         * @throws ClassCastException if o cannot be added to this list due to
         its
         *         type
         * @throws IllegalArgumentException if o cannot be added to this list
         for
         *         some other reason
         * @throws NullPointerException if o is null and the list does not allow
         *         a value to be set to null.
         */
        public native fabric.lang.Object set(int index, fabric.lang.Object o);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractSequentialList._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSequentialList._Static
        {
            
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.AbstractSequentialList._Static._Impl)
                          fetch()).get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.AbstractSequentialList._Static._Impl)
                          fetch()).set$LOCAL_STORE(val);
            }
            
            public _Proxy(fabric.util.AbstractSequentialList._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractSequentialList._Static
              $instance;
            
            static {
                fabric.
                  util.
                  AbstractSequentialList.
                  _Static.
                  _Impl impl =
                  (fabric.util.AbstractSequentialList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractSequentialList._Static._Impl.class);
                $instance = (fabric.util.AbstractSequentialList._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSequentialList._Static
        {
            
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
                $writeInline(out, this.LOCAL_STORE);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
 fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractSequentialList._Static._Proxy(
                  this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
