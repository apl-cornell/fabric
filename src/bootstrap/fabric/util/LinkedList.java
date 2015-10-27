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
import java.lang.reflect.Array;

/**
 * Linked list implementation of the List interface. In addition to the
 * methods of the List interface, this class provides access to the first
 * and last list elements in O(1) time for easy stack, queue, or double-ended
 * queue (deque) creation. The list is doubly-linked, with traversal to a
 * given index starting from the end closest to the element.<p>
 *
 * LinkedList is not synchronized, so if you need multi-threaded access,
 * consider using:<br>
 * <code>List l = Collections.synchronizedList(new LinkedList(...));</code>
 * <p>
 *
 * The iterators are <i>fail-fast</i>, meaning that any structural
 * modification, except for <code>remove()</code> called on the iterator
 * itself, cause the iterator to throw a
 * {@link ConcurrentModificationException} rather than exhibit
 * non-deterministic behavior.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see List
 * @see ArrayList
 * @see Vector
 * @see Collections#synchronizedList(List)
 * @since 1.2
 * @status missing javadoc, but complete to 1.4
 */
public interface LinkedList
  extends fabric.util.List, fabric.util.AbstractSequentialList
{
    
    public fabric.util.LinkedList.Entry get$first();
    
    public fabric.util.LinkedList.Entry set$first(
      fabric.util.LinkedList.Entry val);
    
    public fabric.util.LinkedList.Entry get$last();
    
    public fabric.util.LinkedList.Entry set$last(
      fabric.util.LinkedList.Entry val);
    
    public int get$size();
    
    public int set$size(int val);
    
    public int postInc$size();
    
    public int postDec$size();
    
    /**
     * Class to represent an entry in the list. Holds a single element.
     */
    public static interface Entry
      extends fabric.lang.Object
    {
        
        public fabric.lang.Object get$data();
        
        public fabric.lang.Object set$data(fabric.lang.Object val);
        
        public fabric.util.LinkedList.Entry get$next();
        
        public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$previous();
        
        public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);
        
        /**
         * Construct an entry.
         * @param data the list element
         */
        public Entry fabric$util$LinkedList$Entry$(fabric.lang.Object data);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Entry
        {
            
            public fabric.lang.Object get$data() {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).get$data(
                                                                        );
            }
            
            public fabric.lang.Object set$data(fabric.lang.Object val) {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).set$data(
                                                                        val);
            }
            
            public fabric.util.LinkedList.Entry get$next() {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).get$next(
                                                                        );
            }
            
            public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val) {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).set$next(
                                                                        val);
            }
            
            public fabric.util.LinkedList.Entry get$previous() {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).
                  get$previous();
            }
            
            public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val) {
                return ((fabric.util.LinkedList.Entry._Impl) fetch()).
                  set$previous(val);
            }
            
            public native fabric.util.LinkedList.Entry
              fabric$util$LinkedList$Entry$(fabric.lang.Object arg1);
            
            public _Proxy(Entry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements Entry
        {
            
            public fabric.lang.Object get$data() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.data;
            }
            
            public fabric.lang.Object set$data(fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.data = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The element in the list. */
            fabric.lang.Object data;
            
            public fabric.util.LinkedList.Entry get$next() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.next;
            }
            
            public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.next = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The next list entry, null if this is last. */
            Entry next;
            
            public fabric.util.LinkedList.Entry get$previous() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.previous;
            }
            
            public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.previous = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** The previous list entry, null if this is first. */
            Entry previous;
            
            /**
             * Construct an entry.
             * @param data the list element
             */
            public native Entry fabric$util$LinkedList$Entry$(
              fabric.lang.Object data);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedList.Entry._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.data, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.next, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.previous, refTypes, out,
                          intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.data = (fabric.lang.Object)
                              $readRef(fabric.lang.Object._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.next =
                  (fabric.util.LinkedList.Entry)
                    $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.previous =
                  (fabric.util.LinkedList.Entry)
                    $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.LinkedList.Entry._Impl src =
                  (fabric.util.LinkedList.Entry._Impl) other;
                this.data = src.data;
                this.next = src.next;
                this.previous = src.previous;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedList.Entry._Static
            {
                
                public _Proxy(fabric.util.LinkedList.Entry._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedList.Entry._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      LinkedList.
                      Entry.
                      _Static.
                      _Impl impl =
                      (fabric.util.LinkedList.Entry._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.LinkedList.Entry._Static._Impl.class);
                    $instance = (fabric.util.LinkedList.Entry._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedList.Entry._Static
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
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.LinkedList.Entry._Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    /**
     * Obtain the Entry at a given position in a list. This method of course
     * takes linear time, but it is intelligent enough to take the shorter of
     the
     * paths to get to the Entry required. This implies that the first or last
     * entry in the list is obtained in constant time, which is a very desirable
     * property.
     * For speed and flexibility, range checking is not done in this method:
     * Incorrect values will be returned if (n &lt; 0) or (n &gt;= size).
     *
     * @param n the number of the entry to get
     * @return the entry at position n
     */
    public Entry getEntry(int n);
    
    /**
     * Remove an entry from the list. This will adjust size and deal with
     *  `first' and  `last' appropriatly.
     *
     * @param e the entry to remove
     */
    public void removeEntry(Entry e);
    
    /**
     * Create an empty linked list.
     */
    public fabric.util.LinkedList fabric$util$LinkedList$();
    
    /**
     * Create a linked list containing the elements, in order, of a given
     * collection.
     *
     * @param c the collection to populate this list from
     * @throws NullPointerException if c is null
     */
    public fabric.util.LinkedList fabric$util$LinkedList$(
      fabric.util.Collection c);
    
    /**
     * Returns the first element in the list.
     *
     * @return the first list element
     * @throws NoSuchElementException if the list is empty
     */
    public fabric.lang.Object getFirst();
    
    /**
     * Returns the last element in the list.
     *
     * @return the last list element
     * @throws NoSuchElementException if the list is empty
     */
    public fabric.lang.Object getLast();
    
    /**
     * Remove and return the first element in the list.
     *
     * @return the former first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public fabric.lang.Object removeFirst();
    
    /**
     * Remove and return the last element in the list.
     *
     * @return the former last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public fabric.lang.Object removeLast();
    
    /**
     * Insert an element at the first of the list.
     *
     * @param o the element to insert
     */
    public void addFirst(fabric.lang.Object o);
    
    /**
     * Insert an element at the last of the list.
     *
     * @param o the element to insert
     */
    public void addLast(fabric.lang.Object o);
    
    /**
     * Returns true if the list contains the given object. Comparison is done by
     * <code>o == null ? e = null : o.equals(e)</code>.
     *
     * @param o the element to look for
     * @return true if it is found
     */
    public boolean contains(fabric.lang.Object o);
    
    /**
     * Returns the size of the list.
     *
     * @return the list size
     */
    public int size();
    
    /**
     * Adds an element to the end of the list.
     *
     * @param o the entry to add
     * @return true, as it always succeeds
     */
    public boolean add(fabric.lang.Object o);
    
    /**
     * Removes the entry at the lowest index in the list that matches the given
     * object, comparing by <code>o == null ? e = null : o.equals(e)</code>.
     *
     * @param o the object to remove
     * @return true if an instance of the object was removed
     */
    public boolean remove(fabric.lang.Object o);
    
    /**
     * Append the elements of the collection in iteration order to the end of
     * this list. If this list is modified externally (for example, if this
     * list is the collection), behavior is unspecified.
     *
     * @param c the collection to append
     * @return true if the list was modified
     * @throws NullPointerException if c is null
     */
    public boolean addAll(fabric.util.Collection c);
    
    /**
     * Insert the elements of the collection in iteration order at the given
     * index of this list. If this list is modified externally (for example,
     * if this list is the collection), behavior is unspecified.
     *
     * @param c the collection to append
     * @return true if the list was modified
     * @throws NullPointerException if c is null
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public boolean addAll(int index, fabric.util.Collection c);
    
    /**
     * Remove all elements from this list.
     */
    public void clear();
    
    /**
     * Return the element at index.
     *
     * @param index the place to look
     * @return the element at index
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object get(int index);
    
    /**
     * Replace the element at the given location in the list.
     *
     * @param index which index to change
     * @param o the new element
     * @return the prior element
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    /**
     * Inserts an element in the given position in the list.
     *
     * @param index where to insert the element
     * @param o the element to insert
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public void add(int index, fabric.lang.Object o);
    
    /**
     * Removes the element at the given position from the list.
     *
     * @param index the location of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public fabric.lang.Object remove(int index);
    
    /**
     * Returns the first index where the element is located in the list, or -1.
     *
     * @param o the element to look for
     * @return its position, or -1 if not found
     */
    public int indexOf(fabric.lang.Object o);
    
    /**
     * Returns the last index where the element is located in the list, or -1.
     *
     * @param o the element to look for
     * @return its position, or -1 if not found
     */
    public int lastIndexOf(fabric.lang.Object o);
    
    /**
     * Obtain a ListIterator over this list, starting at a given index. The
     * ListIterator returned by this method supports the add, remove and set
     * methods.
     *
     * @param index the index of the element to be returned by the first call to
     *        next(), or size() to be initially positioned at the end of the
     list
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                 int index);
    
    /**
     * Returns an array which contains the elements of the list in order.
     *
     * @return an array containing the list elements
     */
    public fabric.lang.arrays.ObjectArray toArray();
    
    /**
     * Returns an Array whose component type is the runtime component type of
     * the passed-in Array.  The returned Array is populated with all of the
     * elements in this LinkedList.  If the passed-in Array is not large enough
     * to store all of the elements in this List, a new Array will be created 
     * and returned; if the passed-in Array is <i>larger</i> than the size
     * of this List, then size() index will be set to null.
     *
     * @param a the passed-in Array
     * @return an array representation of this list
     * @throws ArrayStoreException if the runtime type of a does not allow
     *         an element in this list
     * @throws NullPointerException if a is null
     */
    public fabric.lang.arrays.ObjectArray toArray(
      fabric.lang.arrays.ObjectArray a);
    
    /**
     * A ListIterator over the list. This class keeps track of its
     * position in the list and the two list entries it is between.
     *
     * @author Original author unknown
     * @author Eric Blake (ebb9@email.byu.edu)
     */
    public static interface LinkedListItr
      extends fabric.util.ListIterator, fabric.lang.Object
    {
        
        public fabric.util.LinkedList get$out$();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public fabric.util.LinkedList.Entry get$next();
        
        public fabric.util.LinkedList.Entry set$next(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$previous();
        
        public fabric.util.LinkedList.Entry set$previous(
          fabric.util.LinkedList.Entry val);
        
        public fabric.util.LinkedList.Entry get$lastReturned();
        
        public fabric.util.LinkedList.Entry set$lastReturned(
          fabric.util.LinkedList.Entry val);
        
        public int get$position();
        
        public int set$position(int val);
        
        public int postInc$position();
        
        public int postDec$position();
        
        /**
         * Initialize the iterator.
         *
         * @param index the initial index
         */
        public LinkedListItr fabric$util$LinkedList$LinkedListItr$(int index);
        
        /**
         * Returns the index of the next element.
         *
         * @return the next index
         */
        public int nextIndex();
        
        /**
         * Returns the index of the previous element.
         *
         * @return the previous index
         */
        public int previousIndex();
        
        /**
         * Returns true if more elements exist via next.
         *
         * @return true if next will succeed
         */
        public boolean hasNext();
        
        /**
         * Returns true if more elements exist via previous.
         *
         * @return true if previous will succeed
         */
        public boolean hasPrevious();
        
        /**
         * Returns the next element.
         *
         * @return the next element
         * @throws ConcurrentModificationException if the list was modified
         * @throws NoSuchElementException if there is no next
         */
        public fabric.lang.Object next();
        
        /**
         * Returns the previous element.
         *
         * @return the previous element
         * @throws ConcurrentModificationException if the list was modified
         * @throws NoSuchElementException if there is no previous
         */
        public fabric.lang.Object previous();
        
        /**
         * Remove the most recently returned element from the list.
         *
         * @throws ConcurrentModificationException if the list was modified
         * @throws IllegalStateException if there was no last element
         */
        public void remove();
        
        /**
         * Adds an element between the previous and next, and advance to the
         next.
         *
         * @param o the element to add
         * @throws ConcurrentModificationException if the list was modified
         */
        public void add(fabric.lang.Object o);
        
        /**
         * Changes the contents of the element most recently returned.
         *
         * @param o the new element
         * @throws ConcurrentModificationException if the list was modified
         * @throws IllegalStateException if there was no last element
         */
        public void set(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements LinkedListItr
        {
            
            public fabric.util.LinkedList get$out$() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$out$();
            }
            
            public int get$knownMod() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  postDec$knownMod();
            }
            
            public fabric.util.LinkedList.Entry get$next() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$next();
            }
            
            public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val) {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  set$next(val);
            }
            
            public fabric.util.LinkedList.Entry get$previous() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$previous();
            }
            
            public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val) {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  set$previous(val);
            }
            
            public fabric.util.LinkedList.Entry get$lastReturned() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$lastReturned();
            }
            
            public fabric.util.LinkedList.Entry set$lastReturned(
              fabric.util.LinkedList.Entry val) {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  set$lastReturned(val);
            }
            
            public int get$position() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  get$position();
            }
            
            public int set$position(int val) {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  set$position(val);
            }
            
            public int postInc$position() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  postInc$position();
            }
            
            public int postDec$position() {
                return ((fabric.util.LinkedList.LinkedListItr._Impl) fetch()).
                  postDec$position();
            }
            
            public native fabric.util.LinkedList.LinkedListItr
              fabric$util$LinkedList$LinkedListItr$(int arg1);
            
            public native int nextIndex();
            
            public native int previousIndex();
            
            public native boolean hasNext();
            
            public native boolean hasPrevious();
            
            public native fabric.lang.Object next();
            
            public native fabric.lang.Object previous();
            
            public native void remove();
            
            public native void add(fabric.lang.Object arg1);
            
            public native void set(fabric.lang.Object arg1);
            
            public _Proxy(LinkedListItr._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements LinkedListItr
        {
            
            public fabric.util.LinkedList get$out$() { return this.out$; }
            
            private fabric.util.LinkedList out$;
            
            public int get$knownMod() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.knownMod;
            }
            
            public int set$knownMod(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.knownMod = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$knownMod() {
                int tmp = this.get$knownMod();
                this.set$knownMod((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$knownMod() {
                int tmp = this.get$knownMod();
                this.set$knownMod((int) (tmp - 1));
                return tmp;
            }
            
            /** Number of modifications we know about. */
            private int knownMod;
            
            public fabric.util.LinkedList.Entry get$next() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.next;
            }
            
            public fabric.util.LinkedList.Entry set$next(
              fabric.util.LinkedList.Entry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.next = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** Entry that will be returned by next(). */
            private Entry next;
            
            public fabric.util.LinkedList.Entry get$previous() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.previous;
            }
            
            public fabric.util.LinkedList.Entry set$previous(
              fabric.util.LinkedList.Entry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.previous = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** Entry that will be returned by previous(). */
            private Entry previous;
            
            public fabric.util.LinkedList.Entry get$lastReturned() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.lastReturned;
            }
            
            public fabric.util.LinkedList.Entry set$lastReturned(
              fabric.util.LinkedList.Entry val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.lastReturned = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            /** Entry that will be affected by remove() or set(). */
            private Entry lastReturned;
            
            public int get$position() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.position;
            }
            
            public int set$position(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.position = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$position() {
                int tmp = this.get$position();
                this.set$position((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$position() {
                int tmp = this.get$position();
                this.set$position((int) (tmp - 1));
                return tmp;
            }
            
            /** Index of `next'. */
            private int position;
            
            /**
             * Initialize the iterator.
             *
             * @param index the initial index
             */
            public native LinkedListItr fabric$util$LinkedList$LinkedListItr$(
              int index);
            
            /**
             * Checks for iterator consistency.
             *
             * @throws ConcurrentModificationException if the list was modified
             */
            private native void checkMod();
            
            /**
             * Returns the index of the next element.
             *
             * @return the next index
             */
            public native int nextIndex();
            
            /**
             * Returns the index of the previous element.
             *
             * @return the previous index
             */
            public native int previousIndex();
            
            /**
             * Returns true if more elements exist via next.
             *
             * @return true if next will succeed
             */
            public native boolean hasNext();
            
            /**
             * Returns true if more elements exist via previous.
             *
             * @return true if previous will succeed
             */
            public native boolean hasPrevious();
            
            /**
             * Returns the next element.
             *
             * @return the next element
             * @throws ConcurrentModificationException if the list was modified
             * @throws NoSuchElementException if there is no next
             */
            public native fabric.lang.Object next();
            
            /**
             * Returns the previous element.
             *
             * @return the previous element
             * @throws ConcurrentModificationException if the list was modified
             * @throws NoSuchElementException if there is no previous
             */
            public native fabric.lang.Object previous();
            
            /**
             * Remove the most recently returned element from the list.
             *
             * @throws ConcurrentModificationException if the list was modified
             * @throws IllegalStateException if there was no last element
             */
            public native void remove();
            
            /**
             * Adds an element between the previous and next, and advance to the
             next.
             *
             * @param o the element to add
             * @throws ConcurrentModificationException if the list was modified
             */
            public native void add(fabric.lang.Object o);
            
            /**
             * Changes the contents of the element most recently returned.
             *
             * @param o the new element
             * @throws ConcurrentModificationException if the list was modified
             * @throws IllegalStateException if there was no last element
             */
            public native void set(fabric.lang.Object o);
            
            public native fabric.lang.Object $initLabels();
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.LinkedList out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedList.LinkedListItr._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                out.writeInt(this.knownMod);
                $writeRef($getStore(), this.next, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeRef($getStore(), this.previous, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.lastReturned, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                out.writeInt(this.position);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.LinkedList)
                              $readRef(fabric.util.LinkedList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
                this.knownMod = in.readInt();
                this.next =
                  (fabric.util.LinkedList.Entry)
                    $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.previous =
                  (fabric.util.LinkedList.Entry)
                    $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.lastReturned =
                  (fabric.util.LinkedList.Entry)
                    $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.position = in.readInt();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.LinkedList.LinkedListItr._Impl src =
                  (fabric.util.LinkedList.LinkedListItr._Impl) other;
                this.out$ = src.out$;
                this.knownMod = src.knownMod;
                this.next = src.next;
                this.previous = src.previous;
                this.lastReturned = src.lastReturned;
                this.position = src.position;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.LinkedList.LinkedListItr._Static
            {
                
                public _Proxy(fabric.util.LinkedList.LinkedListItr._Static.
                                _Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.LinkedList.LinkedListItr._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      LinkedList.
                      LinkedListItr.
                      _Static.
                      _Impl impl =
                      (fabric.
                        util.
                        LinkedList.
                        LinkedListItr.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.LinkedList.LinkedListItr._Static.
                            _Impl.class);
                    $instance = (fabric.util.LinkedList.LinkedListItr._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.LinkedList.LinkedListItr._Static
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
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.LinkedList.LinkedListItr._Static.
                      _Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSequentialList._Proxy
      implements fabric.util.LinkedList
    {
        
        public fabric.util.LinkedList.Entry get$first() {
            return ((fabric.util.LinkedList._Impl) fetch()).get$first();
        }
        
        public fabric.util.LinkedList.Entry set$first(
          fabric.util.LinkedList.Entry val) {
            return ((fabric.util.LinkedList._Impl) fetch()).set$first(val);
        }
        
        public fabric.util.LinkedList.Entry get$last() {
            return ((fabric.util.LinkedList._Impl) fetch()).get$last();
        }
        
        public fabric.util.LinkedList.Entry set$last(
          fabric.util.LinkedList.Entry val) {
            return ((fabric.util.LinkedList._Impl) fetch()).set$last(val);
        }
        
        public int get$size() {
            return ((fabric.util.LinkedList._Impl) fetch()).get$size();
        }
        
        public int set$size(int val) {
            return ((fabric.util.LinkedList._Impl) fetch()).set$size(val);
        }
        
        public int postInc$size() {
            return ((fabric.util.LinkedList._Impl) fetch()).postInc$size();
        }
        
        public int postDec$size() {
            return ((fabric.util.LinkedList._Impl) fetch()).postDec$size();
        }
        
        public native fabric.util.LinkedList.Entry getEntry(int arg1);
        
        public native void removeEntry(fabric.util.LinkedList.Entry arg1);
        
        public native fabric.util.LinkedList fabric$util$LinkedList$();
        
        public native fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection arg1);
        
        public native fabric.lang.Object getFirst();
        
        public native fabric.lang.Object getLast();
        
        public native fabric.lang.Object removeFirst();
        
        public native fabric.lang.Object removeLast();
        
        public native void addFirst(fabric.lang.Object arg1);
        
        public native void addLast(fabric.lang.Object arg1);
        
        public _Proxy(LinkedList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSequentialList._Impl
      implements fabric.util.LinkedList
    {
        
        public fabric.util.LinkedList.Entry get$first() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.first;
        }
        
        public fabric.util.LinkedList.Entry set$first(
          fabric.util.LinkedList.Entry val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.first = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The first element in the list.
         */
        Entry first;
        
        public fabric.util.LinkedList.Entry get$last() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.last;
        }
        
        public fabric.util.LinkedList.Entry set$last(
          fabric.util.LinkedList.Entry val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.last = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The last element in the list.
         */
        Entry last;
        
        public int get$size() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.size;
        }
        
        public int set$size(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.size = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$size() {
            int tmp = this.get$size();
            this.set$size((int) (tmp - 1));
            return tmp;
        }
        
        /**
         * The current length of the list.
         */
        int size;
        
        /**
         * Obtain the Entry at a given position in a list. This method of course
         * takes linear time, but it is intelligent enough to take the shorter
         of the
         * paths to get to the Entry required. This implies that the first or
         last
         * entry in the list is obtained in constant time, which is a very
         desirable
         * property.
         * For speed and flexibility, range checking is not done in this method:
         * Incorrect values will be returned if (n &lt; 0) or (n &gt;= size).
         *
         * @param n the number of the entry to get
         * @return the entry at position n
         */
        public native Entry getEntry(int n);
        
        /**
         * Remove an entry from the list. This will adjust size and deal with
         *  `first' and  `last' appropriatly.
         *
         * @param e the entry to remove
         */
        public native void removeEntry(Entry e);
        
        /**
         * Checks that the index is in the range of possible elements
         (inclusive).
         *
         * @param index the index to check
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size
         */
        private native void checkBoundsInclusive(int index);
        
        /**
         * Checks that the index is in the range of existing elements
         (exclusive).
         *
         * @param index the index to check
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size
         */
        private native void checkBoundsExclusive(int index);
        
        /**
         * Create an empty linked list.
         */
        public native fabric.util.LinkedList fabric$util$LinkedList$();
        
        /**
         * Create a linked list containing the elements, in order, of a given
         * collection.
         *
         * @param c the collection to populate this list from
         * @throws NullPointerException if c is null
         */
        public native fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection c);
        
        /**
         * Returns the first element in the list.
         *
         * @return the first list element
         * @throws NoSuchElementException if the list is empty
         */
        public native fabric.lang.Object getFirst();
        
        /**
         * Returns the last element in the list.
         *
         * @return the last list element
         * @throws NoSuchElementException if the list is empty
         */
        public native fabric.lang.Object getLast();
        
        /**
         * Remove and return the first element in the list.
         *
         * @return the former first element in the list
         * @throws NoSuchElementException if the list is empty
         */
        public native fabric.lang.Object removeFirst();
        
        /**
         * Remove and return the last element in the list.
         *
         * @return the former last element in the list
         * @throws NoSuchElementException if the list is empty
         */
        public native fabric.lang.Object removeLast();
        
        /**
         * Insert an element at the first of the list.
         *
         * @param o the element to insert
         */
        public native void addFirst(fabric.lang.Object o);
        
        /**
         * Insert an element at the last of the list.
         *
         * @param o the element to insert
         */
        public native void addLast(fabric.lang.Object o);
        
        /**
         * Inserts an element at the end of the list.
         *
         * @param e the entry to add
         */
        private native void addLastEntry(Entry e);
        
        /**
         * Returns true if the list contains the given object. Comparison is
         done by
         * <code>o == null ? e = null : o.equals(e)</code>.
         *
         * @param o the element to look for
         * @return true if it is found
         */
        public native boolean contains(fabric.lang.Object o);
        
        /**
         * Returns the size of the list.
         *
         * @return the list size
         */
        public native int size();
        
        /**
         * Adds an element to the end of the list.
         *
         * @param o the entry to add
         * @return true, as it always succeeds
         */
        public native boolean add(fabric.lang.Object o);
        
        /**
         * Removes the entry at the lowest index in the list that matches the
         given
         * object, comparing by <code>o == null ? e = null : o.equals(e)</code>.
         *
         * @param o the object to remove
         * @return true if an instance of the object was removed
         */
        public native boolean remove(fabric.lang.Object o);
        
        /**
         * Append the elements of the collection in iteration order to the end
         of
         * this list. If this list is modified externally (for example, if this
         * list is the collection), behavior is unspecified.
         *
         * @param c the collection to append
         * @return true if the list was modified
         * @throws NullPointerException if c is null
         */
        public native boolean addAll(fabric.util.Collection c);
        
        /**
         * Insert the elements of the collection in iteration order at the given
         * index of this list. If this list is modified externally (for example,
         * if this list is the collection), behavior is unspecified.
         *
         * @param c the collection to append
         * @return true if the list was modified
         * @throws NullPointerException if c is null
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public native boolean addAll(int index, fabric.util.Collection c);
        
        /**
         * Remove all elements from this list.
         */
        public native void clear();
        
        /**
         * Return the element at index.
         *
         * @param index the place to look
         * @return the element at index
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object get(int index);
        
        /**
         * Replace the element at the given location in the list.
         *
         * @param index which index to change
         * @param o the new element
         * @return the prior element
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public native fabric.lang.Object set(int index, fabric.lang.Object o);
        
        /**
         * Inserts an element in the given position in the list.
         *
         * @param index where to insert the element
         * @param o the element to insert
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public native void add(int index, fabric.lang.Object o);
        
        /**
         * Removes the element at the given position from the list.
         *
         * @param index the location of the element to remove
         * @return the removed element
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public native fabric.lang.Object remove(int index);
        
        /**
         * Returns the first index where the element is located in the list, or
         -1.
         *
         * @param o the element to look for
         * @return its position, or -1 if not found
         */
        public native int indexOf(fabric.lang.Object o);
        
        /**
         * Returns the last index where the element is located in the list, or
         -1.
         *
         * @param o the element to look for
         * @return its position, or -1 if not found
         */
        public native int lastIndexOf(fabric.lang.Object o);
        
        /**
         * Obtain a ListIterator over this list, starting at a given index. The
         * ListIterator returned by this method supports the add, remove and set
         * methods.
         *
         * @param index the index of the element to be returned by the first
         call to
         *        next(), or size() to be initially positioned at the end of the
         list
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         */
        public native fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);
        
        /**
         * Returns an array which contains the elements of the list in order.
         *
         * @return an array containing the list elements
         */
        public native fabric.lang.arrays.ObjectArray toArray();
        
        /**
         * Returns an Array whose component type is the runtime component type
         of
         * the passed-in Array.  The returned Array is populated with all of the
         * elements in this LinkedList.  If the passed-in Array is not large
         enough
         * to store all of the elements in this List, a new Array will be
         created 
         * and returned; if the passed-in Array is <i>larger</i> than the size
         * of this List, then size() index will be set to null.
         *
         * @param a the passed-in Array
         * @return an array representation of this list
         * @throws ArrayStoreException if the runtime type of a does not allow
         *         an element in this list
         * @throws NullPointerException if a is null
         */
        public native fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray a);
        
        /**
         * Serializes this object to the given stream.
         *
         * @param s the stream to write to
         * @throws IOException if the underlying stream fails
         * @serialData the size of the list (int), followed by all the elements
         *             (Object) in proper order
         */
        private native void writeObject(java.io.ObjectOutputStream s)
              throws java.io.IOException;
        
        /**
         * Deserializes this object from the given stream.
         *
         * @param s the stream to read from
         * @throws ClassNotFoundException if the underlying stream fails
         * @throws IOException if the underlying stream fails
         * @serialData the size of the list (int), followed by all the elements
         *             (Object) in proper order
         */
        private native void readObject(java.io.ObjectInputStream s)
              throws java.io.IOException, java.lang.ClassNotFoundException;
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.LinkedList._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.first, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.last, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeInt(this.size);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.first = (fabric.util.LinkedList.Entry)
                           $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.last = (fabric.util.LinkedList.Entry)
                          $readRef(fabric.util.LinkedList.Entry._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.size = in.readInt();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.LinkedList._Impl src =
              (fabric.util.LinkedList._Impl) other;
            this.first = src.first;
            this.last = src.last;
            this.size = src.size;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.LinkedList._Static
        {
            
            public long get$serialVersionUID() {
                return ((fabric.util.LinkedList._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.LinkedList._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.LinkedList._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.LinkedList._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.LinkedList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.LinkedList._Static $instance;
            
            static {
                fabric.
                  util.
                  LinkedList.
                  _Static.
                  _Impl impl =
                  (fabric.util.LinkedList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.LinkedList._Static._Impl.class);
                $instance = (fabric.util.LinkedList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList._Static
        {
            
            public long get$serialVersionUID() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.serialVersionUID;
            }
            
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
            
            /**
             * Compatible with JDK 1.2.
             */
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedList._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
